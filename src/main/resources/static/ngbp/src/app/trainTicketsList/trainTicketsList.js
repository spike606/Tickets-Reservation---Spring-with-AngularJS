angular.module( 'ngBoilerplate.trainTicketsList', [
  'ui.router',
  'placeholders',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'trainTicketsList', {
    url: '/trainTicketsList',
    views: {
      "main": {
        controller: 'trainTicketsListCtrl',
        templateUrl: 'trainTicketsList/trainTicketsList.tpl.html'
      }
    },
    resolve: {
	trainTicketsList: function(trainTicketsListService){
            return trainTicketsListService.getTrainTicketsList();
        }
    }
  });
})
.factory("trainTicketsListService", function($resource){
	var service = {};
    service.getTrainTicketsList = function() {
        var TrainTicketsList = $resource("/TicketsService/rest/trainTickets");
        return TrainTicketsList.get().$promise.then(function(data) {
          return data.trainTickets;
        });
    };
    service.getTrainTicketById = function(rid) {
        var TrainTicket = $resource("/TicketsService/rest/trainTickets/:trainTicketId");
        return TrainTicket.get({trainTicketId:rid}).$promise;
    };
    service.deleteTrainTicket = function(rid) {
        var TrainTicket = $resource("/TicketsService/rest/trainTickets/:trainTicketId");
        return TrainTicket.remove({trainTicketId:rid}).$promise;
    };
	return service;
})
.controller("trainTicketsListCtrl",function trainTicketsListCtrl($scope, sessionService, $state,trainTicketsListService,trainTicketsList){
	$scope.$emit('changeTitle', 'TRAIN_TICKETS_LIST');
	$scope.trainTicketsList = trainTicketsList;
	$scope.deleteButtonDisabled = false;
	$scope.editButtonDisabled = false;
    $scope.deleteTrainTicket = function(rid) {
    $scope.deleteButtonDisabled = true;
    $scope.editButtonDisabled = true;
	trainTicketsListService.deleteTrainTicket(rid).then(function(){
        $state.go("trainTicketsList",{},{ reload : true });
    });
    };
    $scope.editTrainTicket = function(rid){
    $scope.deleteButtonDisabled = true;
    $scope.editButtonDisabled = true;
    $state.go("newTrainTicket",{ridparam : rid}, { reload : true });
    };
})
;