package org.consenlabs.tokencore.foundation.utils;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public final class JsonUtil {

	private static ObjectMapper objectMapper;

	public static final ObjectMapper getInstance() {
		if (objectMapper == null) {
			synchronized (JsonUtil.class) {
				if (objectMapper == null) {
					objectMapper = new ObjectMapper();

					objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
					objectMapper.setSerializationInclusion(Include.NON_NULL);
					objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

					SimpleModule simpleModule = new SimpleModule();
					simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
					simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
					simpleModule.addSerializer(Integer.class, ToStringSerializer.instance);

//					objectMapper.registerModule(simpleModule);
				}
			}
		}
		return objectMapper;
	}

	/** 反序列化 **/
	public static <T> T readValue(String content, Class<T> valueType) {
		try {
			return getInstance().readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 序列化 **/
	public static String writeValue(Object value) {
		try {
			return getInstance().writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 带格式的序列化 **/
	public static String writeValuePretty(Object value) {
		try {
			return getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
