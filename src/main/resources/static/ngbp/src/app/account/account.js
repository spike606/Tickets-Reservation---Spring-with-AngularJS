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
.factory("sessionService", function($http,$base64){
	var session = {};
    session.login = function(data) {
        return $http.post("/TicketsService/login", "username=" + data.login + "&password=" + data.password, {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        } ).then(function(data) {
            alert("login successful");
            console.log(data);
            localStorage.setItem("session", {});
        }, function(data) {
            alert("error logging in");
        });
    };
    session.logout = function(data) {
        return $http.post("/TicketsService/logout","").then(function(data) {
            alert("logut successful");
            localStorage.removeItem("session", {});
        }, function(data) {
            alert("error logging out in");
        });
    };
//	session.logout = function(data){		
//		localStorage.removeItem("session");
//	};
	session.isLoggedIn = function(){		
		return localStorage.getItem("session") !== null;
	};
	return session;
	
})
.factory("accountService", function($resource){
	var service = {};
	service.register = function(account, success, failure){
		var Account = $resource('/TicketsService/rest/accounts');
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
.controller("LoginCtrl",function($scope, sessionService, $state,accountService){
	$scope.$emit('changeTitle', 'LOG_IN');
	$scope.login = function(){
		accountService.doesUserExists($scope.account, function(account){
			sessionService.login($scope.account).then(function(){
				$state.go('home');
			});
		},
		function(){
			alert("error logging in user");
		});
	};
})
.controller("RegisterCtrl",function($scope, sessionService, $state,accountService, ValidationService){
	$scope.$emit('changeTitle', 'SIGN_UP');
    var myValidation = new ValidationService();
    $scope.showButtonFlag = true;
	$scope.register = function(){
		$scope.showButtonFlag = false;
		accountService.register($scope.account,
				function(returnedData){
			sessionService.login($scope.account).then(function(){
				$state.go('home');			
			});
			
		},
		function(){
			$state.go('badRequest');
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