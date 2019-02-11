package ru.ecom.ejb.services.live;

import org.jboss.annotation.security.SecurityDomain;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Local(ILiveService.class)
@Stateless
@SecurityDomain("other")
public class LiveServiceBean implements ILiveService{

	public EntityManagerFactory getEntityManagerFactory() {
		return theManagerFactory ;
	}
	
	public String getUsername() {
		return theContext.getCallerPrincipal().getName() ;
	}
	

	
	private @PersistenceUnit EntityManagerFactory theManagerFactory;	
	private @Resource SessionContext theContext;
	

	
}
