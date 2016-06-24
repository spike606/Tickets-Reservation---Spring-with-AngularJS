angular.module( 'ngBoilerplate', [
  'templates-app',
  'templates-common',
  'ngBoilerplate.home',
  'ngBoilerplate.about',
  'ngBoilerplate.account',
  'ngBoilerplate.contact',
  'ngBoilerplate.newPlaneTicket',
  'ngBoilerplate.newTrainTicket',
  'ngBoilerplate.planeTicketsList',
  'ngBoilerplate.trainTicketsList',
  'ngBoilerplate.bookPlaneTicket',
  'ngBoilerplate.bookTrainTicket',
  'ngBoilerplate.badRequest',
  'ghiscoding.validation',
  'pascalprecht.translate',
  'ui.router'
])

.config( function myAppConfig ( $stateProvider, $urlRouterProvider, $translateProvider ) {
  $urlRouterProvider.otherwise( '/home' );
  $translateProvider.useStaticFilesLoader({
  prefix: '/TicketsService/assets/',
  suffix: '.json'
  });
  $translateProvider.preferredLanguage('en');
  $translateProvider.useSanitizeValueStrategy(null);
})

.run( function run () {
})

.controller( 'AppCtrl', function AppCtrl ( $scope, $location,sessionService ) {
  $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
    if ( angular.isDefined( toState.data.pageTitle ) ) {
      $scope.pageTitle = toState.data.pageTitle ;
    }
  });
	$scope.isLoggedIn = sessionService.isLoggedIn;
	$scope.logout = sessionService.logout;
	$scope.logoImage = 'logo.png';
});



