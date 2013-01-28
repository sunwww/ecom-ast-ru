package ru.ecom.web.dwr;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.login.ILoginService;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.web.messages.UserMessage;

/**
 * Для aucotomplete
 */
public class VocServiceJs {
    public String getNameById(HttpServletRequest aRequest, String aVocName, String aId, String aParentId) throws NamingException, VocServiceException {
        return EntityInjection.find(aRequest).getVocService().getNameById(aId, aVocName, new VocAdditional(aParentId)) ;
    }
    public boolean checkMessage(HttpServletRequest aRequest, Long aIdMessage) throws NamingException {
    	ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	serviceLogin.checkMessage(aIdMessage) ;
    	UserMessage.removeFromSession(aRequest,aIdMessage) ;
    	return true ;
    }
    public boolean hiddenMessage(HttpServletRequest aRequest, Long aIdMessage) throws NamingException {
    	ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	serviceLogin.hideMessage(aIdMessage) ;
    	return true ;
    }

}
