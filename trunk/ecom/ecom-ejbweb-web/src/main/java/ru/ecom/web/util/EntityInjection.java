package ru.ecom.web.util;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.nuzmsh.util.voc.IVocService;

/**
 *
 */
public class EntityInjection {

    private static EntityInjection INSTANCE = null ;
    private Injection theInjection = null ;

    public static EntityInjection find(HttpServletRequest aRequest) throws NamingException {
        if(INSTANCE==null) {
            INSTANCE = new EntityInjection();
        }

        INSTANCE.theInjection = Injection.find(aRequest) ;
        return INSTANCE ;
    }

    private EntityInjection() throws NamingException {
    }


    public IEntityFormService getEntityFormService() throws NamingException {
        return (IEntityFormService) theInjection.getService("EntityFormService") ;
    }


    public IParentEntityFormService getParentEntityFormService() throws NamingException {
        return (IParentEntityFormService) theInjection.getService("ParentEntityFormService") ;
    }

    public IVocService getVocService() throws NamingException {
        return (IVocService) theInjection.getService("VocValueService") ;
    }

}
