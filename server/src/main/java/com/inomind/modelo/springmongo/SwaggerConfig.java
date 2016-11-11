/**
 * 
 */
package com.inomind.modelo.springmongo;

import static com.google.common.base.Predicates.or;
import static com.inomind.modelo.springmongo.ApplicationProfile.DEV;
import static com.inomind.modelo.springmongo.ApplicationProfile.PROD;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;

/**
 * @author GSuaki
 *
 */
@Configuration
@Profile({DEV, PROD})
public class SwaggerConfig {
	
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
	            .apis(RequestHandlerSelectors.any())
	            .paths(paths())    
	            .build()
            .pathMapping("/")
            .apiInfo(apiInfo());
    }

    @Bean
    public SecurityConfiguration security() {
      return new SecurityConfiguration(
          "ClientID",
          "ClientSecret",
          "Pedidus",
          "Pedidus",
          "Basic c2VzYzpzZXNj",
          ApiKeyVehicle.HEADER, 
          "Authorization", 
          "");
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.description("Sistema Pedidus 2.0")
    			.license("API License")
    			.licenseUrl("API License URL")
    			.termsOfServiceUrl("Terms")
    			.title("Pedidus 2.0 API Doc")
    			.version("0.0.1")
    			.build();
    }
    
	@SuppressWarnings("unchecked")
	private Predicate<String> paths() {
		return or(regex("/*.*"));
	}
}
