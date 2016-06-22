angular.module( 'ngBoilerplate.planeTicketsList', [
  'ui.router',
  'placeholders',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'planeTicketsList', {
    url: '/planeTicketsList',
    views: {
      "main": {
        controller: 'planeTicketsListCtrl',
        templateUrl: 'planeTicketsList/planeTicketsList.tpl.html'
      }
    },
    resolve: {
	planeTicketsList: function(planeTicketsListService){
            return planeTicketsListService.getPlaneTicketsList();
        }
    },
    data:{ pageTitle: 'What is It?' }
  });
})
.factory("planeTicketsListService", function($resource){
	var service = {};
    service.getPlaneTicketsList = function() {
        var PlaneTicketsList = $resource("/TicketsService/rest/planeTickets");
        return PlaneTicketsList.get().$promise.then(function(data) {
          return data.planeTickets;
        });
    };
    service.getPlaneTicketById = function(rid) {
        var PlaneTicket = $resource("/TicketsService/rest/planeTickets/:planeTicketId");
        return PlaneTicket.get({planeTicketId:rid});
    };
    service.deletePlaneTicket = function(rid) {
        var PlaneTicket = $resource("/TicketsService/rest/planeTickets/:planeTicketId");
        return PlaneTicket.remove({planeTicketId:rid}).$promise;
    };
	return service;
})
.controller( 'planeTicketsListCtrl', function planeTicketsListCtrl( $scope ) {
  // This is simple a demo for UI Boostrap.
  $scope.dropdownDemoItems = [
    "The first choice!",
    "And another choice for you.",
    "but wait! A third!"
  ];
})
.controller("planeTicketsListCtrl",function($scope, sessionService, $state,planeTicketsListService,planeTicketsList){
    $scope.planeTicketsList = planeTicketsList;
    $scope.deletePlaneTicket = function(rid) {
	planeTicketsListService.deletePlaneTicket(rid).then(function(){
        $state.go("planeTicketsList",{},{ reload : true });
    });
    };
    $scope.editPlaneTicket = function(rid){
    $state.go("newPlaneTicket",{ridparam : rid}, { reload : true });
    };
})
;