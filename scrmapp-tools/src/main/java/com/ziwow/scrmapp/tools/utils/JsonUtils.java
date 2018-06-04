package com.ziwow.scrmapp.tools.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
/**
 * 
 * ClassName: JsonUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-11 下午5:28:02 <br/>
 *
 * @author Daniel.Wang
 * @version 
 * @since JDK 1.6
 */
public class JsonUtils {

	public static final ObjectMapper mapper = new ObjectMapper();

	private JsonUtils() {
	}

	public static String object2Json(Object object) throws IOException {
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(Include.NON_NULL); 
		return mapper.writeValueAsString(object);
	}

	public static <T> T json2Object(String json, Class<T> clz) throws IOException{
		return mapper.readValue(json, clz);
	}

}
