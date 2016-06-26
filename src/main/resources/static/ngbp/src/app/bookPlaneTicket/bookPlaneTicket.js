angular.module( 'ngBoilerplate.bookPlaneTicket', [
  'ui.router',
  'placeholders',
  'ngBoilerplate.planeTicketsList',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'bookPlaneTicket', {
    url: '/bookPlaneTicket',
    views: {
      "main": {
        controller: 'bookPlaneTicketCtrl',
        templateUrl: 'bookPlaneTicket/bookPlaneTicket.tpl.html'
      }
    },
    resolve: {
	planeTicketsList: function(planeTicketsListService){
            return planeTicketsListService.getPlaneTicketsList();
        }
    }
  });
  $stateProvider.state( 'bookPlaneTicketOrder', {
    url: '/bookPlaneTicketOrder',
    views: {
      "main": {
        controller: 'bookPlaneTicketOrderCtrl',
        templateUrl: 'bookPlaneTicket/bookPlaneTicketOrder.tpl.html'
      }
    },
    params: {
        ridparam: null
    }
  });
  $stateProvider.state( 'myPlaneOrders', {
    url: '/myPlaneOrders',
    views: {
      "main": {
        controller: 'myPlaneOrdersCtrl',
        templateUrl: 'bookPlaneTicket/myPlaneOrders.tpl.html'
      }
    },
    params: {
        ridparam: null
    },
    resolve: {
    myPlaneTicketOrderList: function(planeTicketOrderListService){
                return planeTicketOrderListService.getMyPlaneTicketOrderList();
            },
    planeTicketsList: function(planeTicketsListService){
            return planeTicketsListService.getPlaneTicketsList();
        }
        }
        
  });
  $stateProvider.state( 'bookPlaneTicketOrderList', {
    url: '/bookPlaneTicketOrderList',
    views: {
    "main": {
    controller: 'bookPlaneTicketOrderListCtrl',
    templateUrl: 'bookPlaneTicket/bookPlaneTicketOrderList.tpl.html'
    }
    },
    resolve: {
	planeTicketOrderList: function(planeTicketOrderListService){
            return planeTicketOrderListService.getPlaneTicketOrderList();
        },
	planeTicketsList: function(planeTicketsListService){
        return planeTicketsListService.getPlaneTicketsList();
    }
    },
    params: {
        ridparam: null
    }
  });  
})
.factory("planeTicketOrderService", function($resource,planeTicketService){
	var service = {};
	service.addPlaneTicketOrder = function(rid,planeTicketOrder, success, failure){
		var PlaneTicketOrder = $resource('/TicketsService/rest/planeTicketOrders');
		planeTicketOrder.planeTicketId = rid;
		planeTicketOrder.ownerId = rid;
		PlaneTicketOrder.save({},planeTicketOrder,success,failure);
	};
	return service;
})
.factory("planeTicketOrderListService", function($resource, planeTicketsListService){
	var service = {};
    service.getPlaneTicketOrderList = function() {
    var PlaneTicketOrderList = $resource("/TicketsService/rest/planeTicketOrders");
    return PlaneTicketOrderList.get().$promise.then(function(data) {
    return data.planeTicketOrders;
    });
    };
    service.getMyPlaneTicketOrderList = function() {
    var PlaneTicketOrderList = $resource("/TicketsService/rest/planeTicketOrders/myPlaneOrders");
    return PlaneTicketOrderList.get().$promise.then(function(data) {
    return data.planeTicketOrders;
    });
    };    
    service.deletePlaneTicketOrder = function(rid) {
        var PlaneTicketOrder = $resource("/TicketsService/rest/planeTicketOrders/:planeTicketOrderId");
        return PlaneTicketOrder.remove({planeTicketOrderId:rid}).$promise;
    };
	return service;
})
.controller( 'bookPlaneTicketCtrl', function bookPlaneTicketCtrl( $scope,$state,planeTicketsList) {
  $scope.$emit('changeTitle', 'BOOK_PLANE_TICKET');
  $scope.planeTicketsList = planeTicketsList;
  $scope.bookPlaneTicket = function(rid){
  $state.go("bookPlaneTicketOrder",{ridparam : rid}, { reload : true });
  };
})
.controller( 'bookPlaneTicketOrderCtrl', function bookPlaneTicketOrderCtrl( $scope,$stateParams,accountService,planeTicketOrderService,$state, ValidationService,$rootScope) {
  $scope.$emit('changeTitle', 'BOOK_PLANE_TICKET');
  $scope.planeTicketOrder = {};
  if(($rootScope.accountInfo.accountRole == 'ADMIN' || $rootScope.accountInfo.accountRole == 'USER') && ($rootScope.accountInfo !== undefined)){
$scope.planeTicketOrder.firstname = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.secondname = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.lastname = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.country = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.state = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.city = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.street = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.email = $rootScope.accountInfo.firstname;
$scope.planeTicketOrder.telephone = $rootScope.accountInfo.firstname;
}  
  

  var myValidation = new ValidationService();
  $scope.showButtonFlag = true;  
	if($stateParams.ridparam){
		$scope.ridparam = $stateParams.ridparam;
  }else{
  $state.go("home",{ reload : true });
  }
  console.log($scope.ridparam);
  $scope.makePlaneOrder = function(){
  $scope.showButtonFlag = false;  
		planeTicketOrderService.addPlaneTicketOrder($scope.ridparam,$scope.planeTicketOrder,
				function(returnedData){
			$state.go('home');
		},
		function(){
			$state.go('badRequest');
		});
  };

})
.controller( 'bookPlaneTicketOrderListCtrl', function bookPlaneTicketOrderListCtrl( $scope,$state,planeTicketOrderList,planeTicketsList, planeTicketOrderListService) {
  $scope.$emit('changeTitle', 'BOOK_PLANE_TICKET_ORDER_LIST');
  $scope.planeTicketOrderList = planeTicketOrderList;
  $scope.planeTicketsList = planeTicketsList;
  $scope.deleteButtonDisabled = false;
  console.log($scope.planeTicketOrderList);
  console.log($scope.planeTicketsList);
   for (i = 0; i < $scope.planeTicketOrderList.length; i++) {
	for(j=0; j < $scope.planeTicketsList.length ; j++){
		if($scope.planeTicketsList[j].rid == $scope.planeTicketOrderList[i].planeTicketId){
			$scope.planeTicketOrderList[i].planeTicket = $scope.planeTicketsList[j];
			break;
		}
	}
	}
   $scope.deletePlaneTicketOrder = function(rid) {
   $scope.deleteButtonDisabled = true;
   planeTicketOrderListService.deletePlaneTicketOrder(rid).then(function(){
        $state.go("bookPlaneTicketOrderList",{},{ reload : true });
    });
    };
})
.controller( 'myPlaneOrdersCtrl', function myPlaneOrdersCtrl( $scope,$state,myPlaneTicketOrderList,planeTicketsList, planeTicketOrderListService) {
  $scope.$emit('changeTitle', 'MY_TRAIN_ORDERS');
  $scope.myPlaneTicketOrderList = myPlaneTicketOrderList;
  $scope.planeTicketsList = planeTicketsList;
   for (i = 0; i < $scope.myPlaneTicketOrderList.length; i++) {
	for(j=0; j < $scope.planeTicketsList.length ; j++){
		if($scope.planeTicketsList[j].rid == $scope.myPlaneTicketOrderList[i].planeTicketId){
			$scope.myPlaneTicketOrderList[i].planeTicket = $scope.planeTicketsList[j];
			break;
		}
	}
	}
})
;