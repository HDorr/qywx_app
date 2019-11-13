package com.ziwow.scrmapp.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
	public static final ObjectMapper mapper = new ObjectMapper();



	private JsonUtil() {
	}

	/**
	 * 反序列化
	 * 
	 * @param s
	 * @return
	 */
	public static Object deserialize(String s) {
		return deserialize(s, Object.class);
	}

	/**
	 * 反序列化
	 * 
	 * @param s
	 * @param cls
	 * @return
	 */
	public static Object deserialize(String s, Class<?> cls) {
		Object vo = null;
		if (s != null && s.trim().length() > 0) {
			try {
				vo = JSON.parseObject(s, cls);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return vo;
	}

	/**
	 * 序列化
	 * 
	 * @param obj
	 * @return
	 */
	public static String serialize(Object obj) {
		String jsonString = null;
		if (obj != null) {
			try {
				jsonString = JSON.toJSONString(obj,
						SerializerFeature.WriteDateUseDateFormat);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return jsonString;
	}

	/**
	 * 实体转map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map toMap(Object obj) {
		if (obj instanceof Map) {
			return (Map) obj;
		} else {
			return (Map) deserialize(serialize(obj));
		}
	}

	/**
	 * 将list内的元素转成MAP
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map> listItemToMap(List list) {
		return (List<Map>) deserialize(serialize(list));
	}

	/**
	 * Json字符串特殊字符处理
	 * 
	 * @param s
	 * @return String
	 */
	public static String filterJsonString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String object2Json(Object object) throws IOException {
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);

		return mapper.writeValueAsString(object);
	}

	public static <T> T json2Object(String json, Class<T> clz)
			throws IOException {
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return mapper.readValue(json, clz);
	}

	@SuppressWarnings("unchecked")
	public static String getKeyFromJson(String jsonStr, String provinceId) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator<Object> it = jsonObject.keys();
		String key = "";
		List<String> value = new ArrayList<String>();
		while (it.hasNext()) {
			String strkey = (String) it.next();
			value = (List<String>) jsonObject.get(strkey);
			if (value.contains(provinceId)) {
				key = strkey;
				break;
			}
		}

		return key;
	}
}