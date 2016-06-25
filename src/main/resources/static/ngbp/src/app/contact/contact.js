angular.module( 'ngBoilerplate.contact', [
  'ui.router',
  'placeholders',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'contact', {
    url: '/contact',
    views: {
      "main": {
        controller: 'ContactCtrl',
        templateUrl: 'contact/contact.tpl.html'
      }
    },
    data:{ pageTitle: 'What is It?' }
  });
})

.controller( 'ContactCtrl', function ContactCtrl( $scope ) {
	$scope.$emit('changeTitle', 'CONTACT');
})

;
