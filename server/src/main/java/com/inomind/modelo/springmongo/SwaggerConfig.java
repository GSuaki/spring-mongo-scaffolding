/**
 * 
 */
package com.inomind.modelo.springmongo;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.inomind.modelo.springmongo.profile.Development;
import com.inomind.modelo.springmongo.profile.Production;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;

/**
 * @author GSuaki
 *
 */
@Production
@Development
@Configuration
public class SwaggerConfig {
	
    @Bean
    public Docket api(){
        return new Docket(SWAGGER_2)
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
          "Modelo",
          "Modelo",
          "Bearer eyJhbGciOiJIUzI1NiJ9."
          + "eyJpZCI6IjU4MjYyMzMyNDdkMDFkMjIyODZkZWMwNSIsIm5vbWUiOiJHYWJyaWVsIiwiYXRpdm8iOnRydWUsInVzZXJuYW1lIjoiR2FicmllbCIsInBhc3N3b3JkIjpudWxsLCJ0aXBvVXN1YXJpbyI6IklOT01JTkQiLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9JTk9NSU5EIn1dLCJhY2NvdW50Tm9uRXhwaXJlZCI6dHJ1ZSwiYWNjb3VudE5vbkxvY2tlZCI6dHJ1ZSwiY3JlZGVudGlhbHNOb25FeHBpcmVkIjp0cnVlLCJlbmFibGVkIjp0cnVlfQ."
          + "GiGhRUMoRkOonsUWhg2dfu0DjZFlQ0nUDPNNf9n6D0I",
          ApiKeyVehicle.HEADER, 
          "Authorization", 
          "");
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.description("SPring MongoDB Scaffolding")
    			.license("API License")
    			.licenseUrl("API License URL")
    			.termsOfServiceUrl("Terms")
    			.title("Spring MongoDB API Doc")
    			.version("0.0.1")
    			.build();
    }
    
	@SuppressWarnings("unchecked")
	private Predicate<String> paths() {
		return or(regex("/*.*"));
	}
}
