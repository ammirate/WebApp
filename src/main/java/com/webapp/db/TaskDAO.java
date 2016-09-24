package com.webapp.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.webapp.core.Task;

public class TaskDAO {

	private final DB db;
	private final DBCollection collection;

	public TaskDAO(DB db, String collection) {
		this.db = db;
		this.collection = db.getCollection(collection); // todos
	}

	private DBObject prepare(Task t) {
		BasicDBObject obj = new BasicDBObject("title", t.getTitle());
		obj.append("createdOn", new Date());
		obj.append("done", t.isDone());
		return obj;
	}

	public boolean create(Task t) {
		try {
			BasicDBObject obj = (BasicDBObject) prepare(t);
			collection.insert(obj);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public Task read(String id) {
		try {

			DBObject obj = collection.findOne(new BasicDBObject("_id", new ObjectId(id)));
			return new Task((BasicDBObject) obj);

		} catch (Exception e) {
			return null;
		}
	}

	public List<Task> readAll() {
		try {

			List<Task> toReturn = new ArrayList<>();
			DBCursor cur = collection.find();
			while (cur.hasNext()) {
				toReturn.add(new Task((BasicDBObject) cur.next()));
			}
			return toReturn;

		} catch (Exception e) {
			return null;
		}
	}

	public boolean update(String id, Task t) {
		try {

			// update (DBObject ID, DBObject Todo)
			DBObject dbId = new BasicDBObject("_id", new ObjectId(id));
			DBObject newObj = (BasicDBObject) prepare(t);
			collection.update(dbId, new BasicDBObject("$set", newObj));
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(String id) {
		try {
			
			collection.remove(new BasicDBObject("_id", new ObjectId(id)));
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

}
