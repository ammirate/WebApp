package com.webapp.web;

import com.google.gson.Gson;
import com.webapp.core.TaskController;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class TaskAPI {

    private static final String API_CONTEXT = "/api/v1";

    private final TaskController todoService;

    public TaskAPI(TaskController todoService) {
        this.todoService = todoService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/todos", 
        		"application/json", 
        		(request, response) -> {
		            todoService.createNewTodo(request.body());
		            response.status(201);
		            return response;
		        }, 
        		new JsonTransformer());

        get(API_CONTEXT + "/todos/:id", "application/json",
        		(request, response)-> todoService.find(request.params(":id")), 
        		new JsonTransformer());

        get(API_CONTEXT + "/todos", "application/json", 
        		(request, response)-> todoService.findAll(), 
        		new JsonTransformer());

        put(API_CONTEXT + "/todos/:id", "application/json", 
        		(request, response)-> todoService.update(request.params(":id"), request.body()), 
        		new JsonTransformer());
        
        get(API_CONTEXT + "/todos/delete/:id", 
        		"application/json",
        		(request, response)-> todoService.remove(request.params(":id")), 
        		new JsonTransformer());
    }


}
