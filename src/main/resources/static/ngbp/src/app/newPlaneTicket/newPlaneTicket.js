angular.module( 'ngBoilerplate.newPlaneTicket', [
  'ui.router',
  'placeholders',
  'ui.bootstrap',
  'ngBoilerplate.planeTicketsList',
  'jkuri.timepicker',
  'moment-picker'
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
.controller("newPlaneTicketCtrl",function newPlaneTicketCtrl($scope, sessionService, $state,planeTicketService,planeticket,$stateParams,updatePlaneTicketService, ValidationService){
	$scope.$emit('changeTitle', 'NEW_PLANE_TICKET');
	var myValidation = new ValidationService();
	$scope.planeticket = planeticket;
    $scope.ridparam = $stateParams.ridparam;
    $scope.showButtonFlag = true;
	$scope.planeTicketSubmit = function(){
		$scope.showButtonFlag = false;
		if($scope.ridparam === null){
			planeTicketService.addPlaneTicket($scope.planeticket,
					function(returnedData){
				$state.go('planeTicketsList');
			},
			function(){
				$state.go('badRequest');
			});
		}else{
			updatePlaneTicketService.update({rid:$scope.ridparam},$scope.planeticket,
					function(returnedData){
				$state.go('planeTicketsList');
			},
			function(){
				$state.go('badRequest');
			});
		}
	};
})
;