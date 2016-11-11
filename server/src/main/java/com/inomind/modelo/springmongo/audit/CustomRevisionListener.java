package com.inomind.modelo.springmongo.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomRevisionListener implements RevisionListener{

	@Override
	public void newRevision(Object entity) {
//		CustomRevision cr =  (CustomRevision) entity;
//
//		if(UserUtils.isUserLogged()){
//			DefaultUser usuarioLogado = UserUtils.getUserLogged();
//			usuarioLogado = usuarioLogado != null ? usuarioLogado : new DefaultUser();
//
//			cr.setUserId(usuarioLogado.getId());
//			cr.setUserName(usuarioLogado.getNome());
//		}
//		cr.setDataAlteracao(LocalDateTime.now().toDate());
	}

}
