package com.webapp.core;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by shekhargulati on 09/06/14.
 */
public class Task {

    private String id;
    private String title;
    private boolean done;
    private Date createdOn = new Date();

    public Task(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.title = dbObject.getString("title");
        this.done = dbObject.getBoolean("done");
        this.createdOn = dbObject.getDate("createdOn");
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }
}
