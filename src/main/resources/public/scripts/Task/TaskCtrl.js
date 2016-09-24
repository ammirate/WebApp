app.controller('TaskCtrl', function($scope, $http, $location) {

	// get all tasks
	$http.get('/api/v1/todos').success(function(data) {
		$scope.todos = data;
	}).error(function(data, status) {
		console.log('Error ' + data)
	});

	// change the status of the task
	$scope.todoStatusChanged = function(todo) {

		$http.put('/api/v1/todos/' + todo.id, todo).success(function(data) {
			// console.log('status changed');
			toastr.options = {
				"progressBar" : true,
				"closeButton" : true,
				"showDuration" : "100"
			};
			toastr.info('Status changed');
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}

	// delete the task
	$scope.remove = function(id) {
		// console.log("Remove " + id);
		$http.get('/api/v1/todos/delete/' + id).success(function(resp) {
			// nothing
		}).error(function(resp) {
			console.log(resp);
		});
	}

	// new task to save
	$scope.newTodo = {
		done : false
	};

	// save the task
	$scope.createTodo = function() {
		// console.log($scope.newTodo);
		$http.post('/api/v1/todos', $scope.newTodo)

		.success(function(data) {
			toastr.success('Task created');
			$location.path('/');

		}).error(function(data, status) {
			alert("Error:" + data);
			console.log('Error ' + data)
		})
	}
});