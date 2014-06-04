package org.orcsun.sunspace.utils;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestfulUtils {

	public static RestTemplate rest = new RestTemplate();
	static{
		rest.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
	    rest.getMessageConverters().add(new StringHttpMessageConverter());
	}
}
