package com.inomind.modelo.springmongo;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.inomind.modelo.springmongo.exception.ModeloException;
import com.inomind.modelo.springmongo.exception.WebException;

@Configuration
@ComponentScan
@Order(LOWEST_PRECEDENCE)
public class ApplicationServlet extends WebMvcConfigurerAdapter {

	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}

	@Bean
	public HttpPutFormContentFilter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new HandlerExceptionResolver() {

			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
				
			    ModelAndView model = null;
			    
			    if (ex.getClass().isAssignableFrom(BindException.class)) {
				
			        BindException bindex = (BindException) ex;
					
			        final StringBuilder message = new StringBuilder();
					bindex.getAllErrors()
					    .forEach(a -> message.append(a.getDefaultMessage()).append("\n"));
					
					try {
						response.sendError(HttpStatus.BAD_REQUEST.value(), message.toString());
					} catch (IOException e) {
					    model = null;
					}
					
					model = new ModelAndView();
				}
				
			    if (ex instanceof WebException) {
					
			        WebException webex = (WebException) ex;
					
			        try {
						response.sendError(webex.getStatus().value(), ex.getMessage());
					} catch (IOException e) {
					    model = null;
					}
					
			        model = new ModelAndView();
				}

			    if (ex instanceof ModeloException) {
			        
			        ModeloException webex = (ModeloException) ex;
			        
			        try {
			            response.sendError(webex.getStatusCode().intValue(), ex.getMessage());
			        } catch (IOException e) {
			            model = null;
			        }
			        
			        model = new ModelAndView();
			    }
			    
				return model;
			}
		});

		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

}
