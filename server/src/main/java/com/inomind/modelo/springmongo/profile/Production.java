package com.inomind.modelo.springmongo.profile;

import static com.inomind.modelo.springmongo.ApplicationProfile.HOMOLOG;
import static com.inomind.modelo.springmongo.ApplicationProfile.PROD;
import static com.inomind.modelo.springmongo.ApplicationProfile.QA;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

@Profile({PROD, QA, HOMOLOG})
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Production {

}
