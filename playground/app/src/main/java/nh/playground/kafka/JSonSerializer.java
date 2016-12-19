package nh.playground.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSonSerializer implements Serializer<Object> {
	
	private Gson gson;

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		this.gson = new GsonBuilder().create();
		
	}

	@Override
	public byte[] serialize(String topic, Object data) {
		return gson.toJson(data).getBytes();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
