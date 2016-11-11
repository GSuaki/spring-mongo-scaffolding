/**
 * 
 */
package com.inomind.modelo.springmongo;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.inomind.modelo.springmongo.dto.UserDTO;
import com.inomind.modelo.springmongo.security.DefaultEntryPointHandler;
import com.inomind.modelo.springmongo.security.DefaultUser;
import com.inomind.modelo.springmongo.security.UserLoginService;
import com.inomind.modelo.springmongo.security.UserRoles;
import com.inomind.modelo.springmongo.security.auth.StatelessAuthenticationFilter;
import com.inomind.modelo.springmongo.security.auth.token.JWTAuthenticationFailureHandler;
import com.inomind.modelo.springmongo.security.auth.token.JWTAuthenticationSuccessHandler;
import com.inomind.modelo.springmongo.security.auth.token.JWTLogoutSuccessHandler;
import com.inomind.modelo.springmongo.security.auth.token.TokenAuthenticationService;

/**
 * @author GSuaki
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
	
	@Value("{" + ModeloProperties.SECRET + "}")
	private String secret;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	public static void main(String[] args) {
		String senha = "123";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode(senha));
	}

	@Bean
	public static RoleHierarchyVoter roleVoter() {
		return new RoleHierarchyVoter(roleHierarchy());
	}

	@Bean
	public static RoleHierarchyImpl roleHierarchy() {
		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

		StringBuilder roles = new StringBuilder(UserRoles.ROLE_INOMIND + " > " + UserRoles.ROLE_MASTER)
				.append(" and ")
				.append(UserRoles.ROLE_MASTER + " > " + UserRoles.ROLE_ADMIN)
				.append(" and ")
				.append(UserRoles.ROLE_ADMIN + " > " + UserRoles.ROLE_USER);

		hierarchy.setHierarchy(roles.toString());

		return hierarchy;
	}

	@Bean
	public AffirmativeBased accessDecisionManager() {
		
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
		decisionVoters.add(webExpressionVoter());
		decisionVoters.add(roleVoter());
		
		return new AffirmativeBased(decisionVoters);
	}

	private WebExpressionVoter webExpressionVoter() {
		WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(expressionHandler());
		return webExpressionVoter;
	}

	@Bean
	public DefaultWebSecurityExpressionHandler expressionHandler() {
		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy());
		return expressionHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userLoginService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.accessDecisionManager(accessDecisionManager());
		
		this.configurePublicEndpoints(http);
		
		http
			.authorizeRequests()
			.anyRequest()
			.fullyAuthenticated()
			.and()
			.exceptionHandling();
		
		this.configureCsrf(http);
		this.configureSession(http);
		this.configureEntryPoint(http);
		this.configureAuthentication(http);
	}

	/**
	 * @param http
	 * @throws Exception 
	 */
	private void configurePublicEndpoints(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/public", 
					"/swagger-ui.html", "/webjars/**", "/configuration/*", "/swagger-resources", "/swagger-resources/**/*", "/v2/api-docs").permitAll()
			.antMatchers("/arquivo/**").permitAll()
			.antMatchers(HttpMethod.GET, "/change-password").permitAll()
			.antMatchers(HttpMethod.PUT, "/usuario/alterarsenha").permitAll()
			.antMatchers(HttpMethod.POST, "/usuario/esquecisenha").permitAll();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userLoginService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setHideUserNotFoundExceptions(false);
		return authenticationProvider;
	}

	private void configureAuthentication(HttpSecurity http) throws Exception {
		
		JWTAuthenticationSuccessHandler athenticationSuccessHandler  = new JWTAuthenticationSuccessHandler(tokenAuthenticationService, p -> new UserDTO((DefaultUser) p));
		JWTAuthenticationFailureHandler authenticationFailureHandler = new JWTAuthenticationFailureHandler();
		JWTLogoutSuccessHandler 		logoutSuccessHandler 		 = new JWTLogoutSuccessHandler(secret, tokenAuthenticationService);
		
		http.formLogin()
			.usernameParameter("email")
			.loginProcessingUrl("/login")
			.successHandler(athenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
			.and()
			.exceptionHandling()
			.and().anonymous()
			.and()
			.addFilterBefore(configureTokenFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/login").permitAll();

		http.logout().logoutUrl("/logout")
			.logoutSuccessHandler(logoutSuccessHandler);
	}
	
	private StatelessAuthenticationFilter configureTokenFilter() {
		
		RequestMatcher publicReq = new AntPathRequestMatcher("/public/**");
        RequestMatcher arquivoReq = new AntPathRequestMatcher("/arquivo/**");
        RequestMatcher senhaReq = new AntPathRequestMatcher("/usuario/esquecisenha", POST.name());
        RequestMatcher changeReq = new AntPathRequestMatcher("/change-password", GET.name());
        
        RequestMatcher ignored = new OrRequestMatcher(publicReq, arquivoReq, senhaReq, changeReq);
        
        return new StatelessAuthenticationFilter(tokenAuthenticationService, ignored);
	}

	private void configureCsrf(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}

	private void configureSession(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
	}

	private void configureEntryPoint(HttpSecurity http) throws Exception {
		http.exceptionHandling()
			.authenticationEntryPoint(new DefaultEntryPointHandler());
	}
}
