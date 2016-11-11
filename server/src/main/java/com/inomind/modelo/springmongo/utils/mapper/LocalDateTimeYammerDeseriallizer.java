/**
 * 
 */
package com.inomind.modelo.springmongo.utils.mapper;

import java.io.IOException;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author GSuaki
 *
 */
public class LocalDateTimeYammerDeseriallizer extends JsonDeserializer<LocalDateTime> {

    public LocalDateTime deserialize(final JsonParser jp, final DeserializationContext ctx) throws IOException {
	    return DateTimeFormat.forPattern("YYYY/MM/DD HH:mm:ss Z").parseLocalDateTime(jp.getText());
    }
}

