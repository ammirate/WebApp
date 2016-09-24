
var app = angular.module('todoapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    
    // PLUGIN

]);
 
app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/list.html',
        controller: 'ListCtrl'
    }).when('/create', {
        templateUrl: 'views/create.html',
        controller: 'CreateCtrl'
    })
    
    .otherwise({
        redirectTo: '/'
    })
});
 
app.controller('ListCtrl', function ($scope, $http) {

    $http.get('/api/v1/todos').success(function (data) {
        $scope.todos = data;	
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
 
    $scope.todoStatusChanged = function (todo) {
        console.log(todo);
        $http.put('/api/v1/todos/' + todo.id, todo).success(function (data) {
            console.log('status changed');
            toastr.options={"progressBar": true,"closeButton": true, "showDuration": "100"};
            toastr.info('Status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
    
    $scope.remove = function (id) {
    	console.log("Remove " + id);
    	$http.get('/api/v1/todos/delete/' + id)
    	.success(function(resp) { })
    	.error(function(resp) { console.log(resp); });
    }
});
 
app.controller('CreateCtrl', function ($scope, $http, $location) {
    $scope.todo = {
        done: false
    };
 
    $scope.createTodo = function () {
        console.log($scope.todo);
        $http.post('/api/v1/todos', $scope.todo)
        
        .success(function (data) {
        	 toastr.success('Task created');
            $location.path('/');
            
        }).error(function (data, status) {
        	alert("Error:" + data);
            console.log('Error ' + data)
        })
    }
});