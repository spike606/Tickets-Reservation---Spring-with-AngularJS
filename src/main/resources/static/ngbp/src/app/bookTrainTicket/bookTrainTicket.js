angular.module( 'ngBoilerplate.bookTrainTicket', [
  'ui.router',
  'placeholders',
  'ngBoilerplate.trainTicketsList',
  'ngBoilerplate.newTrainTicket',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'bookTrainTicket', {
    url: '/bookTrainTicket',
    views: {
      "main": {
        controller: 'bookTrainTicketCtrl',
        templateUrl: 'bookTrainTicket/bookTrainTicket.tpl.html'
      }
    },
    resolve: {
	trainTicketsList: function(trainTicketsListService){
            return trainTicketsListService.getTrainTicketsList();
        }
    }
  });
  $stateProvider.state( 'bookTrainTicketOrder', {
    url: '/bookTrainTicketOrder',
    views: {
      "main": {
        controller: 'bookTrainTicketOrderCtrl',
        templateUrl: 'bookTrainTicket/bookTrainTicketOrder.tpl.html'
      }
    },
    params: {
        ridparam: null
    }
  });
  $stateProvider.state( 'myTrainOrders', {
    url: '/myTrainOrders',
    views: {
      "main": {
        controller: 'myTrainOrdersCtrl',
        templateUrl: 'bookTrainTicket/myTrainOrders.tpl.html'
      }
    },
    params: {
        ridparam: null
    },
    resolve: {
    myTrainTicketOrderList: function(trainTicketOrderListService){
                return trainTicketOrderListService.getMyTrainTicketOrderList();
            },
    trainTicketsList: function(trainTicketsListService){
            return trainTicketsListService.getTrainTicketsList();
        }
        }
        
  });
  $stateProvider.state( 'bookTrainTicketOrderList', {
    url: '/bookTrainTicketOrderList',
    views: {
    "main": {
    controller: 'bookTrainTicketOrderListCtrl',
    templateUrl: 'bookTrainTicket/bookTrainTicketOrderList.tpl.html'
    }
    },
    resolve: {
	trainTicketOrderList: function(trainTicketOrderListService){
            return trainTicketOrderListService.getTrainTicketOrderList();
        },
	trainTicketsList: function(trainTicketsListService){
        return trainTicketsListService.getTrainTicketsList();
    }
    },
    params: {
        ridparam: null
    }
  });   
})
.factory("trainTicketOrderService", function($resource,trainTicketService){
	var service = {};
	service.addTrainTicketOrder = function(rid,trainTicketOrder, success, failure){
		var TrainTicketOrder = $resource('/TicketsService/rest/trainTicketOrders');
		trainTicketOrder.trainTicketId = rid;
		TrainTicketOrder.save({},trainTicketOrder,success,failure);
	};
	return service;
})
.factory("trainTicketOrderListService", function($resource, trainTicketsListService,trainTicketService){
	var service = {};
    service.getTrainTicketOrderList = function() {
    var TrainTicketOrderList = $resource("/TicketsService/rest/trainTicketOrders");
    return TrainTicketOrderList.get().$promise.then(function(data) {
    return data.trainTicketOrders;
    });
    };
    service.getMyTrainTicketOrderList = function() {
    var TrainTicketOrderList = $resource("/TicketsService/rest/trainTicketOrders/myTrainOrders");
    return TrainTicketOrderList.get().$promise.then(function(data) {
    return data.trainTicketOrders;
    });
    }; 
    service.deleteTrainTicketOrder = function(rid) {
        var TrainTicketOrder = $resource("/TicketsService/rest/trainTicketOrders/:trainTicketOrderId");
        return TrainTicketOrder.remove({trainTicketOrderId:rid}).$promise;
    };
	return service;
})
.controller( 'bookTrainTicketCtrl', function bookTrainTicketCtrl( $scope,$state,trainTicketsList) {
  $scope.$emit('changeTitle', 'BOOK_TRAIN_TICKET');
  $scope.trainTicketsList = trainTicketsList;
  $scope.bookTrainTicket = function(rid){
  $state.go("bookTrainTicketOrder",{ridparam : rid}, { reload : true });
  };
})
.controller( 'bookTrainTicketOrderCtrl', function bookTrainTicketOrderCtrl( $scope,$stateParams,trainTicketOrderService,$state, ValidationService,$rootScope) {
  $scope.$emit('changeTitle', 'BOOK_TRAIN_TICKET');
  $scope.trainTicketOrder = {};
  if(($rootScope.accountInfo.accountRole == 'ADMIN' || $rootScope.accountInfo.accountRole == 'USER') && ($rootScope.accountInfo !== undefined)){
$scope.trainTicketOrder.firstname = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.secondname = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.lastname = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.country = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.state = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.city = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.street = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.email = $rootScope.accountInfo.firstname;
$scope.trainTicketOrder.telephone = $rootScope.accountInfo.firstname;
}  
  
  var myValidation = new ValidationService();
  $scope.showButtonFlag = true; 
	if($stateParams.ridparam){
		$scope.ridparam = $stateParams.ridparam;
  }else{
  $state.go("home",{ reload : true });
  }
  console.log($scope.ridparam);
  $scope.makeTrainOrder = function(){
  $scope.showButtonFlag = false; 
  trainTicketOrderService.addTrainTicketOrder($scope.ridparam,$scope.trainTicketOrder,
			function(returnedData){
		$state.go('home');
	},
	function(){
		$state.go('badRequest');
	});
  };
})
.controller( 'bookTrainTicketOrderListCtrl', function bookTrainTicketOrderListCtrl( $scope,$state,trainTicketOrderList,trainTicketsList, trainTicketOrderListService) {
  $scope.$emit('changeTitle', 'BOOK_TRAIN_TICKET_ORDER_LIST');
  $scope.trainTicketOrderList = trainTicketOrderList;
  $scope.trainTicketsList = trainTicketsList;
  $scope.deleteButtonDisabled = false;
  console.log($scope.trainTicketOrderList);
  console.log($scope.trainTicketsList);
   for (i = 0; i < $scope.trainTicketOrderList.length; i++) {
	for(j=0; j < $scope.trainTicketOrderList.length ; j++){
		if($scope.trainTicketsList[j].rid == $scope.trainTicketOrderList[i].trainTicketId){
			$scope.trainTicketOrderList[i].trainTicket = $scope.trainTicketsList[j];
			break;
		}
	}
	}
   $scope.deleteTrainTicketOrder = function(rid) {
   $scope.deleteButtonDisabled = true;
   trainTicketOrderListService.deleteTrainTicketOrder(rid).then(function(){
        $state.go("bookTrainTicketOrderList",{},{ reload : true });
    });
    };
})
.controller( 'myTrainOrdersCtrl', function myTrainOrdersCtrl( $scope,$state,myTrainTicketOrderList,trainTicketsList, trainTicketOrderListService) {
  $scope.$emit('changeTitle', 'MY_TRAIN_ORDERS');
  $scope.myTrainTicketOrderList = myTrainTicketOrderList;
  $scope.trainTicketsList = trainTicketsList;
   for (i = 0; i < $scope.myTrainTicketOrderList.length; i++) {
	for(j=0; j < $scope.trainTicketsList.length ; j++){
		if($scope.trainTicketsList[j].rid == $scope.myTrainTicketOrderList[i].trainTicketId){
			$scope.myTrainTicketOrderList[i].trainTicket = $scope.trainTicketsList[j];
			break;
		}
	}
	}
})
;