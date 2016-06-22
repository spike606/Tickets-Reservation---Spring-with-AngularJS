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
.controller( 'bookPlaneTicketCtrl', function bookPlaneTicketCtrl( $scope,$state,planeTicketsList) {
  $scope.planeTicketsList = planeTicketsList;
  $scope.bookPlaneTicket = function(rid){
  $state.go("bookPlaneTicketOrder",{ridparam : rid}, { reload : true });
  };
})
.controller( 'bookPlaneTicketOrderCtrl', function bookPlaneTicketOrderCtrl( $scope,$stateParams,planeTicketOrderService,$state) {
  if($stateParams.ridparam){
		$scope.ridparam = $stateParams.ridparam;
  }else{
  $state.go("home",{ reload : true });
  }
  console.log($scope.ridparam);
  $scope.makePlaneOrder = function(){
		planeTicketOrderService.addPlaneTicketOrder($scope.ridparam,$scope.planeTicketOrder,
				function(returnedData){
			$state.go('home');
		},
		function(){
			alert('Error adding planeticket');
		});
  };
})
;