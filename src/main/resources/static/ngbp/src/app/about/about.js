angular.module( 'ngBoilerplate.about', [
  'ui.router',
  'placeholders',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'about', {
    url: '/about',
    views: {
      "main": {
        controller: 'AboutCtrl',
        templateUrl: 'about/about.tpl.html'
      }
    }
  });
})

.controller( 'AboutCtrl', function AboutCtrl( $scope,$rootScope,$translate ) {
  $scope.$emit('changeTitle', 'ABOUT');
})

;
