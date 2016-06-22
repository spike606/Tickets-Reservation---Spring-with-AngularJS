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
			alert('Error adding trainticket');
		});
  };
})
;