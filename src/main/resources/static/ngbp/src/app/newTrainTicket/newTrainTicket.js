angular.module( 'ngBoilerplate.newTrainTicket', [
  'ui.router',
  'placeholders',
  'ui.bootstrap',
  'ngBoilerplate.trainTicketsList',
  'jkuri.timepicker',
  'moment-picker'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'newTrainTicket', {
    url: '/newTrainTicket',
    views: {
      "main": {
        controller: 'newTrainTicketCtrl',
        templateUrl: 'newTrainTicket/newTrainTicket.tpl.html'
      }
    },
    params: {
        ridparam: null
    },
    resolve: {
        trainticket: function(trainTicketsListService, $stateParams){
        return trainTicketsListService.getTrainTicketById($stateParams.ridparam);
        }
    },
    data:{ pageTitle: 'What is It?' }
  });
})
.factory("trainTicketService", function($resource){
	var service = {};
	service.addTrainTicket = function(trainTicket, success, failure){
		var TrainTicket = $resource('/TicketsService/rest/trainTickets');
		TrainTicket.save({},trainTicket,success,failure);
	};
    service.getTrainTicketById = function(rid) {
        var TrainTicket = $resource("/TicketsService/rest/trainTickets/:trainTicketId");
        return TrainTicket.get({trainTicketId:rid}).$promise;
    };
	return service;
})
.factory('updateTrainTicketService', function ($resource) {
      var data = $resource("/TicketsService/rest/trainTickets/:trainTicketId", {trainTicketId: '@rid'}, {
      update:{
          method:'PUT'
          }
      });
      return data;
  })
.controller( 'newTrainTicketCtrl', function newTrainTicketCtrl( $scope ) {
  // This is simple a demo for UI Boostrap.
  $scope.dropdownDemoItems = [
    "The first choice!",
    "And another choice for you.",
    "but wait! A third!"
  ];
})
.controller("newTrainTicketCtrl",function($scope, sessionService, $state,trainTicketService,trainticket,$stateParams,updateTrainTicketService, ValidationService){
    var myValidation = new ValidationService();
	$scope.trainticket = trainticket;
    $scope.ridparam = $stateParams.ridparam;
    $scope.showButtonFlag = true;
	$scope.trainTicketSubmit = function(){
		$scope.showButtonFlag = false;
		if($scope.ridparam === null){
			trainTicketService.addTrainTicket($scope.trainticket,
					function(returnedData){
				$state.go('trainTicketsList');
			},
			function(){
				$state.go('badRequest');
			});
		}else{
			updateTrainTicketService.update({rid:$scope.ridparam},$scope.trainticket,
					function(returnedData){
				$state.go('trainTicketsList');
			},
			function(){
				$state.go('badRequest');
			});
		}
	};
})
;