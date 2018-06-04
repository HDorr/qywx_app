/**
 * Project Name:activity-service
 * File Name:ObjectMappingCustomer.java
 * Package Name:com.ziwow.activity.util
 * Date:2014-12-1下午7:38:07
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.tools.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * ClassName: ObjectMappingCustomer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-12-1 下午7:38:07 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public class ObjectMappingCustomer extends ObjectMapper{
    
public static ObjectMappingCustomer instrance=new ObjectMappingCustomer();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7380398726455263582L;

	public ObjectMappingCustomer() {
		super();
		
		/*
		this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		this.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		this.setSerializationInclusion(Include.NON_NULL);
		
		//this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
		 // 允许单引号
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
        // 字段和值都加引号
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		*/
        
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		
		// 忽略未知属性错误 
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jgen,
					SerializerProvider provider) throws IOException,
					JsonProcessingException {
					jgen.writeString("");
			}
		});
        
        
        
        // 解决hibernate lazy load 造成无限循环解析json报500的错误
        //registerModule(new Hibernate3Module());
	}
	
	public static String writeJson(String key,Object val) {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put(key, val);
			return instrance.writeValueAsString(map);
		} catch (JsonProcessingException e) {
		}
		return "-1";
	}
	
	public static String writeJson(Object obj) {
		try {
			return instrance.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
		}
		return "-1";
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

	}
}

