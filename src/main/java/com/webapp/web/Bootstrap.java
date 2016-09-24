package com.webapp.web;

import static spark.SparkBase.setIpAddress;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.webapp.core.TaskController;

/**
 * Developed by following the tutorial on 
 * https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
 * 
 * @author ammirate
 *
 */
public class Bootstrap {
	
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;
 
    public static void main(String[] args) throws Exception {
        init();
    }
    
    
    private static void init() throws Exception  {
    	setIpAddress(IP_ADDRESS);
        setPort(PORT);
        staticFileLocation("/public");
        new TaskAPI(new TaskController(mongo()));
    }
    
 
    private static DB mongo() throws Exception {
        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        if (host == null) {
            MongoClient mongoClient = new MongoClient("localhost");
            System.out.println("#### Host is null, using localhost");
            System.out.println(mongoClient.toString() + "\n#######");
            return mongoClient.getDB("todoapp");
        }
        int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String dbname = System.getenv("OPENSHIFT_APP_NAME");
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        DB db = mongoClient.getDB(dbname);
        if (db.authenticate(username, password.toCharArray())) {
            return db;
        } else {
            throw new RuntimeException("Not able to authenticate with MongoDB");
        }
    }
}
