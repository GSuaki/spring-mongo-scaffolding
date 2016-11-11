package com.inomind.modelo.springmongo.domain;

import com.inomind.modelo.springmongo.security.DefaultUser;

public interface SecurityDomain {

	void checkAcao(DefaultUser user) throws Exception;
	
}
