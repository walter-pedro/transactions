package com.walter.transactions.utils;

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

}
