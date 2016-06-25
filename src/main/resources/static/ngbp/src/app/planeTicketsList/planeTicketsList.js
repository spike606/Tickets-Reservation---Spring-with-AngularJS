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
.controller("planeTicketsListCtrl",function planeTicketsListCtrl($scope, sessionService, $state,planeTicketsListService,planeTicketsList){
	$scope.$emit('changeTitle', 'PLANE_TICKETS_LIST');
	$scope.planeTicketsList = planeTicketsList;
	$scope.deleteButtonDisabled = false;
	$scope.editButtonDisabled = false;
    $scope.deletePlaneTicket = function(rid) {
    $scope.deleteButtonDisabled = true;
    $scope.editButtonDisabled = true;
	planeTicketsListService.deletePlaneTicket(rid).then(function(){
        $state.go("planeTicketsList",{},{ reload : true });
    });
    };
    $scope.editPlaneTicket = function(rid){
    $scope.editButtonDisabled = true;
    $scope.deleteButtonDisabled = true;
    $state.go("newPlaneTicket",{ridparam : rid}, { reload : true });
    };
})
;