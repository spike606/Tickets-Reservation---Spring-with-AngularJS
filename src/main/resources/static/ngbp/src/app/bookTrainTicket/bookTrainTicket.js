angular.module( 'ngBoilerplate.bookTrainTicket', [
  'ui.router',
  'placeholders',
  'ngBoilerplate.trainTicketsList',
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
    },
    data:{ pageTitle: 'What is It?' }
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
    },
    data:{ pageTitle: 'What is It?' }
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
    },
    data:{ pageTitle: 'What is It?' }
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
.factory("trainTicketOrderListService", function($resource, trainTicketsListService){
	var service = {};
    service.getTrainTicketOrderList = function() {
    var TrainTicketOrderList = $resource("/TicketsService/rest/trainTicketOrders");
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
  $scope.trainTicketsList = trainTicketsList;
  $scope.bookTrainTicket = function(rid){
  $state.go("bookTrainTicketOrder",{ridparam : rid}, { reload : true });
  };
})
.controller( 'bookTrainTicketOrderCtrl', function bookTrainTicketOrderCtrl( $scope,$stateParams,trainTicketOrderService,$state) {
  if($stateParams.ridparam){
		$scope.ridparam = $stateParams.ridparam;
  }else{
  $state.go("home",{ reload : true });
  }
  console.log($scope.ridparam);
  $scope.makeTrainOrder = function(){
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
  $scope.trainTicketOrderList = trainTicketOrderList;
  $scope.trainTicketsList = trainTicketsList;
  $scope.deleteButtonDisabled = false;
  console.log($scope.trainTicketOrderList);
  console.log($scope.trainTicketsList);
   for (i = 0; i < $scope.trainTicketOrderList.length; i++) {
	for(j=0; j < $scope.trainTicketsList.length ; j++){
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
;