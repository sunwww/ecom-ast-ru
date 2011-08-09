package ru.ecom.ejb.services.live;

import javax.persistence.EntityManagerFactory;

public interface ILiveService {

	EntityManagerFactory getEntityManagerFactory() ;
	
	String getUsername() ;
	
}
