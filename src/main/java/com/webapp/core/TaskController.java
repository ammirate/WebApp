package com.webapp.core;

import com.google.gson.Gson;
import com.mongodb.*;
import com.webapp.db.TaskDAO;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class TaskController {

    private TaskDAO dao;
    private final String COLLECTION_NAME = "todos";

    public TaskController(DB db) {
        this.dao = new TaskDAO(db, COLLECTION_NAME);
    }

    public List<Task> findAll() {
        return dao.readAll();
    }

    public void createNewTodo(String body) {
        Task todo = new Gson().fromJson(body, Task.class);
        dao.create(todo);
    }

    public Task find(String id) {
    	return dao.read(id);
    }

    public Task update(String todoId, String body) {
        Task todo = new Gson().fromJson(body, Task.class);
        dao.update(todoId, todo);
        return this.find(todoId);
    }
    
    
    public boolean remove(String todoId) {
    	return dao.delete(todoId);
    }
}
