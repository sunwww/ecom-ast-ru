package ru.nuzmsh.ejb.formpersistence;

import ru.nuzmsh.commons.formpersistence.IEntityFormManagerContext;

import javax.ejb.SessionContext;

/**
 * @author esinev
 * Date: 12.05.2006
 * Time: 14:07:10
 */
public class SessionEJBFormManagerContext implements IEntityFormManagerContext {

    public SessionEJBFormManagerContext(SessionContext aContext) {
        theContext = aContext ;
    }

    public boolean isUserInRole(String aRoleName) {
        return theContext.isCallerInRole(aRoleName) ;
    }


    private final SessionContext theContext ;

}
