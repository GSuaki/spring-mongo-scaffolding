/**
 * 
 */
package com.inomind.modelo.springmongo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.inomind.modelo.springmongo.ApplicationSecurity;

/**
 * @author GSuaki
 * this @class define user roles hierarchy to use in @Secured annotation
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		handler.setRoleHierarchy(ApplicationSecurity.roleHierarchy());
		return handler;
	}

	@Override
	  protected AccessDecisionManager accessDecisionManager() {
	    AffirmativeBased manager = (AffirmativeBased) super.accessDecisionManager();
	    manager.getDecisionVoters().add(ApplicationSecurity.roleVoter());
	    return manager;
	  }

}
