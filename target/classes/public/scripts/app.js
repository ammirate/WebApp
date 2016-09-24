var app = angular.module('webApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    
    // PLUGIN

]);
 
app.config(function ($routeProvider) {
	
    $routeProvider.when('/', {
        templateUrl: 'views/Task/list.html',
        controller: 'TaskCtrl'
    }).when('/create', {
        templateUrl: 'views/Task/create.html',
        controller: 'TaskCtrl'
    })
    
    .otherwise({
        redirectTo: '/'
    })
    
});



