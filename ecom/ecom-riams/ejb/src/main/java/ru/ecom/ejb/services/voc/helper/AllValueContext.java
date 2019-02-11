package ru.ecom.ejb.services.voc.helper;

import ru.nuzmsh.util.voc.VocAdditional;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

/**
 * 
 */
public class AllValueContext {

	public AllValueContext(VocAdditional aAdditional, EntityManager aManager, SessionContext aContext) {
		theEntityManager = aManager ;
		theVocAdditional = aAdditional;
		theSessionContext = aContext ;
	}
	
    /** Дополнительные параметры при выборе из справочника */
    public VocAdditional getVocAdditional() { return theVocAdditional ; }

    /** Менеджер */
    public EntityManager getEntityManager() { return theEntityManager ; }
    
    public SessionContext getSessionContext() {return theSessionContext ;} 
    
    
    private final SessionContext theSessionContext ;
    /** Менеджер */
    private final EntityManager theEntityManager ;
    /** Дополнительные параметры при выборе из справочника */
    private final VocAdditional theVocAdditional ;
}
