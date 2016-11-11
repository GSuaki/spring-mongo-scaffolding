package com.inomind.modelo.springmongo.audit;

import static com.inomind.modelo.springmongo.security.UserUtils.getUserLogged;

import org.springframework.data.domain.AuditorAware;

import com.inomind.modelo.springmongo.security.DefaultUser;

public class AuditorAwareImpl implements AuditorAware<DefaultUser> {

    @Override
    public DefaultUser getCurrentAuditor() {
        return getUserLogged();
    }
}
