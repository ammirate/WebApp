package com.webapp.web;
 
import com.google.gson.Gson;
import spark.Response;
import spark.ResponseTransformer;
 
import java.util.HashMap;
 
public class JsonTransformer implements ResponseTransformer {
 
    private Gson gson = new Gson();
 
    @Override
    public String render(Object model) {
    	String result = "";
    	if (model instanceof Response) {
    		Response resp = (Response) model;
    		result = resp.toString();
    	} else {
    		 result = gson.toJson(model);
    	}
    	return result;
    }
 
}