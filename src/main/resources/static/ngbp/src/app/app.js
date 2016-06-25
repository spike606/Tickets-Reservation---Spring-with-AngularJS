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

.run( function run ($rootScope, $translate) {
    // serves as a cache
    var currentTitleKey = '';

    $rootScope.$on('changeTitle', function(e, titleKey) {
        // update if parameter is defined, else reuse
        currentTitleKey = (titleKey || currentTitleKey);
        $translate(currentTitleKey).then(function(result){
        $rootScope.Title = result;
        });
    });
})

.controller( 'AppCtrl', function AppCtrl ( $scope, $location,sessionService, $translate) {
  $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
//    if ( angular.isDefined( toState.data.pageTitle ) ) {
//      $scope.pageTitle = toState.data.pageTitle ;
//    }
  });
	$scope.isLoggedIn = sessionService.isLoggedIn;
	$scope.logout = sessionService.logout;
	$scope.logoImage = 'logo.png';
	$scope.changeLanguage = function (langKey) {
    $translate.use(langKey);
     $scope.$emit('changeTitle');

		};
});



