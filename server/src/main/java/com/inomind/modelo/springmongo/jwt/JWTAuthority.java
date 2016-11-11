/**
 * 
 */
package com.inomind.modelo.springmongo.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GSuaki
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthority implements GrantedAuthority {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8637767377636315482L;
	
	private String authority;
	
	public JWTAuthority(GrantedAuthority auth) {
		this.authority = auth.getAuthority();
	}
	
	public static List<JWTAuthority> createFromString(String... values) {
		List<JWTAuthority> result = new ArrayList<JWTAuthority>();
		
		for (String v : values) {
			result.add(new JWTAuthority(v));
		}
		
		return result;
	}
}
