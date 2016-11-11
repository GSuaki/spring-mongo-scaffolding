/**
 * 
 */
package com.inomind.modelo.springmongo.utils.mapper;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author GSuaki
 *
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate>{

	@Override
	public LocalDate deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {

		String date = jp.getText();
		
		DateTimeFormatterFactory factory = new DateTimeFormatterFactory("dd/MM/yyyy");
		DateTimeFormatter parser = factory.createDateTimeFormatter(); 
		
		return parser.parseLocalDate(date);
	}
}
