/**
 * 
 */
package com.inomind.modelo.springmongo.utils.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author GSuaki
 *
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

	private static final String PATTERN = "dd/MM/yyyy";
	private SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
	
	@Override
	public void serialize(LocalDate arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		arg1.writeString(sdf.format(arg0.toDate()));
	}
}