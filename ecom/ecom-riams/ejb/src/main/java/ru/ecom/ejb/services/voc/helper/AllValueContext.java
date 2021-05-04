package ru.ecom.ejb.services.voc.helper;

import lombok.Getter;
import ru.nuzmsh.util.voc.VocAdditional;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

/**
 * 
 */
@Getter
public class AllValueContext {

	public AllValueContext(VocAdditional aAdditional, EntityManager aManager, SessionContext aContext) {
		entityManager = aManager ;
		vocAdditional = aAdditional;
		sessionContext = aContext ;
	}
	
    private final SessionContext sessionContext ;
    /** Менеджер */
    private final EntityManager entityManager ;
    /** Дополнительные параметры при выборе из справочника */
    private final VocAdditional vocAdditional ;
}
