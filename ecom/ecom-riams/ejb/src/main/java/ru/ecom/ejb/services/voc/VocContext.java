package ru.ecom.ejb.services.voc;

import lombok.Getter;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

@Getter
public class VocContext {
	
	public VocContext(EntityManager aManager, SessionContext aContext) {
		entityManager = aManager ;
		sessionContext = aContext ;
		
	}

    private final EntityManager entityManager;
    private final SessionContext sessionContext ;
}
