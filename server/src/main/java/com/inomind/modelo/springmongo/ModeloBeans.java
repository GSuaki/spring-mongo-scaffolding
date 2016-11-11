/**
 * 
 */
package com.inomind.modelo.springmongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inomind.modelo.springmongo.audit.AuditorAwareImpl;
import com.inomind.modelo.springmongo.security.DefaultUser;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * @author GSuaki
 *
 */

@Configuration
public class ModeloBeans {

    private static final String MONGODB_CLIENT_DBNAME = "mongodb.client.dbname";
    private static final String MONGODB_CLIENT_HOST   = "mongodb.client.host";
    
    @Autowired
    private Environment env;

    @Bean
    public ObjectMapper jsonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return Jackson2ObjectMapperBuilder
                .json()
                .modules(new Jdk8Module(), new JavaTimeModule())
                .build();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public Mongo mongoClient() {
        return new MongoClient(env.getRequiredProperty(MONGODB_CLIENT_HOST));
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), env.getRequiredProperty(MONGODB_CLIENT_DBNAME));
    }
    
    @Bean
    public AuditorAware<DefaultUser> auditorAware() {
        return new AuditorAwareImpl();
    }
    
    @Bean
    public SwaggerConfig swagger() {
        return new SwaggerConfig();
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
}
