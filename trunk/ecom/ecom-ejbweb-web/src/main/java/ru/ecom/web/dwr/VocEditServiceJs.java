package ru.ecom.web.dwr;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.voc.IVocEditService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocValue;

public class VocEditServiceJs {
    
	public String createVocValue(HttpServletRequest aRequest, String aVocKey
            , String aId, String aName, String aParentId) throws NamingException, RemoteException, CreateException {
    	VocValue value = new VocValue(aId, aName) ;
    	VocAdditional add = new VocAdditional(aParentId) ; 
    	Object id =  Injection.find(aRequest).getService(IVocEditService.class)
    		.createVocValue(aVocKey, value, add) ;
    	return id!=null ? id.toString() : null ;
    }

    public void changeVocValue(HttpServletRequest aRequest, String aVocKey
            , String aId, String aName) throws NamingException, RemoteException, CreateException {
        throw new IllegalStateException("Пока не реализовано") ;
    }

    public boolean isVocEditabled(HttpServletRequest aRequest, String aVocKey) throws NamingException, RemoteException, CreateException {
        //return Injection.find(aRequest).getService(IVocEditService.class).isVocEditabled(aVocKey) ;
    	return false ;
    }
}
