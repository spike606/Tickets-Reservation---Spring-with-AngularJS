angular.module('ngBoilerplate.account',['ui.router','ngResource','ngBoilerplate.badRequest','base64'])
.config(function($stateProvider){
	$stateProvider.state('login', {
		url:'/login',
		views:{
			'main':{
				templateUrl:'account/login.tpl.html' ,
				controller: 'LoginCtrl'
			}
		}
	})
	.state('register', {
		url:'/register',
		views:{
			'main':{
				templateUrl:'account/register.tpl.html' ,
				controller: 'RegisterCtrl'

			}
		}
	})
	.state('registerAdmin', {
		url:'/registerAdmin',
		views:{
			'main':{
				templateUrl:'account/registerAdmin.tpl.html' ,
				controller: 'RegisterAdminCtrl'

			}
		}
	})
    .state('usersList', {
            url:'/usersList',
            views: {
                'main': {
                    templateUrl:'account/usersList.tpl.html',
                    controller: 'UsersListCtrl'
                }
            },
            resolve: {
                accounts: function(accountService) {
                    return accountService.getAllAccounts();
                }
            }
    });
})
.factory("sessionService", function($http,$base64,$state,accountService){
	var session = {};
	session.login = function(data) {
        return $http.post("/TicketsService/login", "username=" + data.login + "&password=" + data.password, {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        } ).then(function(data) {
            sessionStorage.setItem("session", {});
            accountService.getMyAccount();
        }, function(data) {
//            alert("Error logging in");
            sessionStorage.setItem("invalidLogin", true);

        });
    };
    session.logout = function(data) {
        return $http.post("/TicketsService/logout","").then(function(data) {
            sessionStorage.removeItem("role");
            sessionStorage.removeItem("session", {});
            accountService.ClearMyAccount();

    $state.go('home');			
        }, function(data) {
            alert("Error logging out");
    $state.go('home');			
        });
    };
//	session.logout = function(data){		
//		localStorage.removeItem("session");
//	};
    session.isLoggedIn = function(){		
		return sessionStorage.getItem("session") !== null;
	};
	return session;
	
})
.factory("accountService", function($resource, $rootScope){
	var service = {};
	service.register = function(account, success, failure){
		var Account = $resource('/TicketsService/rest/accounts');
		Account.save({},account,success,failure);
	};
	service.getMyAccount = function(){
		var Account = $resource('/TicketsService/rest/accounts/myAccount');
        return Account.get().$promise.then(function(data) {
        $rootScope.accountInfo = data;
//        $rootScope.accountRole = data.accountRole;
//            return data;
          });
	};
	service.ClearMyAccount = function(){
        $rootScope.accountInfo = {};
//            return data;
	};
	service.registerAdmin = function(account, success, failure){
		var Account = $resource('/TicketsService/rest/accounts/newAdmin');
		Account.save({},account,success,failure);
	};
	service.doesUserExists = function (account, success, failure){
		var Account = $resource('/TicketsService/rest/accounts');
		var data = Account.get({login:account.login}, function(){
			var accounts = data.accounts;
			if(accounts.length !== 0){
				success(accounts[0]);

			}else{
				failure();
			}
		},
		failure);
		
	};
    service.getAccountById = function(accountId) {
        var Account = $resource("/TicketsService/rest/accounts/:paramAccountId");
        return Account.get({paramAccountId:accountId}).$promise;
    };
    service.deleteAccount = function(rid) {
        var Account = $resource("/TicketsService/rest/accounts/:accountId");
        return Account.remove({accountId:rid}).$promise;
    };
    service.getAllAccounts = function() {
        var Account = $resource("/TicketsService/rest/accounts");
        return Account.get().$promise.then(function(data) {
          return data.accounts;
        });
    };    
	return service;
})
.controller("LoginCtrl",function($scope, sessionService, $state,accountService,$rootScope){
	$scope.$emit('changeTitle', 'LOG_IN');
	$scope.validLogging = true;
	$scope.login = function(){
//		accountService.doesUserExists($scope.account, function(account){
			sessionService.login($scope.account).then(function(){
    $scope.invalidLogin = sessionStorage.getItem("invalidLogin");
    sessionStorage.removeItem("invalidLogin");
    if(!$scope.invalidLogin){
    $state.go('home');
    }
			
		},
		function(){
//			$scope.validLogging = false;

			alert("error logging in user");
		});
	};
})
.controller("RegisterCtrl",function($scope, sessionService, $state,accountService, ValidationService,$rootScope){
	$scope.$emit('changeTitle', 'SIGN_UP');

    var myValidation = new ValidationService();
    $scope.showButtonFlag = true;
	$scope.register = function(){
	$scope.userAlreadyExists = false;
		$scope.showButtonFlag = false;
		accountService.register($scope.account,
				function(returnedData){
			sessionService.login($scope.account).then(function(){
				$state.go('home');			
			});
			
		},
		function(){
	$scope.userAlreadyExists = true;
    $scope.showButtonFlag = true;
//			$state.go('badRequest');
		});

	};
})
.controller("RegisterAdminCtrl",function($scope, sessionService, $state,accountService, ValidationService,$rootScope){
	$scope.$emit('changeTitle', 'SIGN_UP_ADMIN');

    var myValidation = new ValidationService();
    $scope.showButtonFlag = true;
	$scope.registerAdmin = function(){
	$scope.userAlreadyExists = false;
		$scope.showButtonFlag = false;
		accountService.registerAdmin($scope.account,
				function(returnedData){
				$state.go('home');			
		
		},
		function(){
	$scope.userAlreadyExists = true;
    $scope.showButtonFlag = true;
//			$state.go('badRequest');
		});

	};
})
.controller("UsersListCtrl", function($scope,$state, accounts,accountService) {
	$scope.$emit('changeTitle', 'USERS_LIST');
	$scope.accounts = accounts;
    $scope.deleteButtonDisabled = false;
    $scope.deleteAccount = function(rid) {
    $scope.deleteButtonDisabled = true;
    accountService.deleteAccount(rid).then(function(){
        $state.go("usersList",{},{ reload : true });
    });
    };
});