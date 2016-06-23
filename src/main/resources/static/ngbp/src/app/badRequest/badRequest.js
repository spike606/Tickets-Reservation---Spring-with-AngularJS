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
    },
    data:{ pageTitle: 'badRequest' }
  });
})
.controller( 'badRequestCtrl', function badRequestController( $scope , sessionService) {
});

