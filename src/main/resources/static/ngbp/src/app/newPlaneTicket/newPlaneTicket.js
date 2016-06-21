angular.module( 'ngBoilerplate.newPlaneTicket', [
  'ui.router',
  'placeholders',
  'ui.bootstrap',
  'ngBoilerplate.planeTicketsList'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'newPlaneTicket', {
    url: '/newPlaneTicket',
    views: {
      "main": {
        controller: 'newPlaneTicketCtrl',
        templateUrl: 'newPlaneTicket/newPlaneTicket.tpl.html'
      }
    },
    params: {
        ridparam: null
    },
    resolve: {
        planeticket: function(planeTicketsListService, $stateParams){
        return planeTicketsListService.getPlaneTicketById($stateParams.ridparam);
        }
    },
    data:{ pageTitle: 'What is It?' }
  });
})

.factory("planeTicketService", function($resource){
	var service = {};
	service.addPlaneTicket = function(planeTicket, success, failure){
		var PlaneTicket = $resource('/TicketsService/rest/planeTickets');
		PlaneTicket.save({},planeTicket,success,failure);
	};
    service.getPlaneTicketById = function(rid) {
        var PlaneTicket = $resource("/TicketsService/rest/planeTickets/:planeTicketId");
        return PlaneTicket.get({planeTicketId:rid}).$promise;
    };
	return service;
})
.factory('updatePlaneTicketService', function ($resource) {
      var data = $resource("/TicketsService/rest/planeTickets/:planeTicketId", {planeTicketId: '@rid'}, {
      update:{
          method:'PUT'
          }
      });
      return data;
  })
.controller( 'newPlaneTicketCtrl', function newPlaneTicketCtrl( $scope ) {
  // This is simple a demo for UI Boostrap.
  $scope.dropdownDemoItems = [
    "The first choice!",
    "And another choice for you.",
    "but wait! A third!"
  ];
})
.controller("newPlaneTicketCtrl",function($scope, sessionService, $state,planeTicketService,planeticket,$stateParams,updatePlaneTicketService){
	$scope.planeticket = planeticket;
    $scope.ridparam = $stateParams.ridparam;
	$scope.planeTicketSubmit = function(){
		if($scope.ridparam === null){
			planeTicketService.addPlaneTicket($scope.planeticket,
					function(returnedData){
				$state.go('planeTicketsList');
			},
			function(){
				alert('Error adding planeticket');
			});
		}else{
			updatePlaneTicketService.update({rid:$scope.ridparam},$scope.planeticket,
					function(returnedData){
				$state.go('planeTicketsList');
			},
			function(){
				alert('Error adding planeticket');
			});
		}
	};
})
;