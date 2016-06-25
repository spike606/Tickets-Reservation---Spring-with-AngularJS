angular.module( 'ngBoilerplate.badRequest', [
  'ui.router',
  'plusOne'
])
.config(function config( $stateProvider ) {
  $stateProvider.state( 'badRequest', {
    url: '/badRequest',
    views: {
      "main": {
        controller: 'badRequestCtrl',
        templateUrl: 'badRequest/badRequest.tpl.html'
      }
    }
  });
})
.controller( 'badRequestCtrl', function badRequestController( $scope , sessionService) {
	$scope.$emit('changeTitle', 'BAD_REQUEST');
});

