package com.inomind.modelo.springmongo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

	public static DefaultUser getUserLogged() {
		return (DefaultUser) getLoggedAsPrincipal();
	}

	private static Object getLoggedAsPrincipal() {
		if (SecurityContextHolder.getContext() == null) {
			return null;
		}
		
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return null;
		}
		
		return SecurityContextHolder.getContext().getAuthentication().getDetails();
	}

	public static boolean isUserLogged() {
		return getLoggedAsPrincipal() != null && DefaultUser.class.isAssignableFrom(getLoggedAsPrincipal().getClass());
	}

	public static void setUserAuthenticated(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
