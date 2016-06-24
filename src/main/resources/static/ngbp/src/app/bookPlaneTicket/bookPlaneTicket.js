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
    },
    data:{ pageTitle: 'What is It?' }
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
    },
    data:{ pageTitle: 'What is It?' }
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
    },
    data:{ pageTitle: 'What is It?' }
  });  
})
.factory("planeTicketOrderService", function($resource,planeTicketService){
	var service = {};
	service.addPlaneTicketOrder = function(rid,planeTicketOrder, success, failure){
		var PlaneTicketOrder = $resource('/TicketsService/rest/planeTicketOrders');
		planeTicketOrder.planeTicketId = rid;
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
    service.deletePlaneTicketOrder = function(rid) {
        var PlaneTicketOrder = $resource("/TicketsService/rest/planeTicketOrders/:planeTicketOrderId");
        return PlaneTicketOrder.remove({planeTicketOrderId:rid}).$promise;
    };
	return service;
})
.controller( 'bookPlaneTicketCtrl', function bookPlaneTicketCtrl( $scope,$state,planeTicketsList) {
  $scope.planeTicketsList = planeTicketsList;
  $scope.bookPlaneTicket = function(rid){
  $state.go("bookPlaneTicketOrder",{ridparam : rid}, { reload : true });
  };
})
.controller( 'bookPlaneTicketOrderCtrl', function bookPlaneTicketOrderCtrl( $scope,$stateParams,planeTicketOrderService,$state, ValidationService) {
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
;