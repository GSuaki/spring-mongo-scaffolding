/**
 * 
 */
package com.inomind.modelo.springmongo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author GSuaki
 *
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
	public static Object getBean(Class<?> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	private static ApplicationContext getApplicationContext() {
		return context;
	}

}
