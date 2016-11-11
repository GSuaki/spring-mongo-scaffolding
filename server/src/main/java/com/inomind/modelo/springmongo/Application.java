/**
 * 
 */
package com.inomind.modelo.springmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author GSuaki
 *
 */
@PropertySources({ @PropertySource("classpath:/config/template_application.properties"),
	@PropertySource(value = "${MODELO_CONFIG_LOCATION}", ignoreResourceNotFound = true) })
@EnableSwagger2
@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
