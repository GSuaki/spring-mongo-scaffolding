/**
 * 
 */
package com.inomind.modelo.springmongo.editor;

import java.beans.PropertyEditorSupport;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

/**
 * @author GSuaki
 *
 */
public class LocalTimePropertyEditor extends PropertyEditorSupport {
	

	private DateTimeFormatter formatter;

	public LocalTimePropertyEditor(String pattern) {
		formatter = DateTimeFormat.forPattern(pattern);
	}

	@Override
	public void setAsText(String value) throws IllegalArgumentException {
		if(!value.equals("")){
			setValue(formatter.parseLocalTime(StringUtils.trimAllWhitespace(value)));
		}
	}

	@Override
	public String getAsText() {
		LocalTime date = (LocalTime) getValue();
		return formatter.print(date);
	}
}
