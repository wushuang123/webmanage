package com.xifar.common.util.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonImpl implements IJson {

	private static final Logger log = LoggerFactory.getLogger(JsonImpl.class);

	private static Gson gson = null;

	static class NullDateAdapterFactory implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (Objects.isNull(json)) {
					return null;
				}
				Date date = (Date) format.parse(json.getAsString());
				return date;
			} catch (Exception e) {
				log.error("JSON转换出现异常,{}", e.getMessage());
				return null;
			}
		}
	}

	static {
		GsonBuilder gb = new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gb.registerTypeAdapter(Date.class, new NullDateAdapterFactory());
		gb.serializeNulls();
		gson = gb.create();
	}

	@Override
	public <T> String toJson(T t) {
		return gson.toJson(t);
	}

	@Override
	public <T> T fromJson(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	@Override
	public <T> T fromJson(String json, Type type) {
		return gson.fromJson(json, type);
	}

	/*********************************************
	 * fastJson
	 ************************************************/

	// /** 转为JSON字符串 **/
	// public String toJson(Object obj) {
	// JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:SSS";
	// String str = JSON.toJSONString(obj,
	// SerializerFeature.WriteDateUseDateFormat);
	// return str;
	// }
	//
	// /** 解析JSON串 **/
	// public <T> T fromJson(String str, Class<T> clazz) {
	// return JSON.parseObject(str, clazz);
	// }
	//
	// /** 解析泛型对象 **/
	// public <T> T fromJson(String json, Type typeOfT) {
	// return JSON.parseObject(json, typeOfT);
	// }

}
