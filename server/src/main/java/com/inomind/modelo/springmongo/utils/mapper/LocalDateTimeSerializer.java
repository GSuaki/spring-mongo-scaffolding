/**
 * 
 */
package com.inomind.modelo.springmongo.utils.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author GSuaki
 *
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime>{
	
	private static final String pattern = "dd/MM/yyyy hh:mm:ss";
	private SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	
	@Override
	public void serialize(LocalDateTime arg0, JsonGenerator arg1,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		String format = sdf.format(arg0.toDate());
		arg1.writeString(format);
	}

}