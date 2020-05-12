package com.walter.transactions.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
	
	public static String convertObjectToJson(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
	public static JsonNode convertJsonToObject(final String json) {
	    try {
	    	ObjectMapper mapper = new ObjectMapper();
	    	JsonNode jsonNode = mapper.readTree(json);
	    	return jsonNode;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  

}
