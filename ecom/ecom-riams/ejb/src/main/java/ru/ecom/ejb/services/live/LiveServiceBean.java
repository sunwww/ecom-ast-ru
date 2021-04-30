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
		return managerFactory ;
	}
	
	public String getUsername() {
		return context.getCallerPrincipal().getName() ;
	}
	

	
	private @PersistenceUnit EntityManagerFactory managerFactory;	
	private @Resource SessionContext context;
	

	
}
