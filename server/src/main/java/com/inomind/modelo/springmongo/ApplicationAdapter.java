/**
 * 
 */
package com.inomind.modelo.springmongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author GSuaki
 *
 */
@Configuration
@EnableWebMvc
public class ApplicationAdapter extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("styles/**").addResourceLocations("builds/release/styles/");
		registry.addResourceHandler("scripts/**").addResourceLocations("builds/release/scripts/");
		registry.addResourceHandler("assets/**").addResourceLocations("builds/release/assets/");
		registry.addResourceHandler("images/**").addResourceLocations("builds/release/images/");
		registry.addResourceHandler("font/**").addResourceLocations("builds/release/font/");
		
		registry
			.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/")
			.setCachePeriod(0);
		
		registry
			.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/")
			.setCachePeriod(0);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:builds/release/index.html");
	    registry.addViewController("").setViewName("forward:builds/release/index.html");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setSuffix(".html");
		return resolver;
	}

}
