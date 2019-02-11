package ru.ecom.ejb.services.live;

import ru.ecom.ejb.services.live.domain.LiveTransaction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class LiveContext {

	public LiveContext(EntityManagerFactory aFactory, String aUsername) {
		theManagerFactory = aFactory ;
		theUsername = aUsername ;
	}
	
	/** Manager */
	@Comment("Manager")
	public EntityManager getManager() {
		return theManager;
	}

	public void setManager(EntityManager aManager) {
		theManager = aManager;
	}

	/** Manager */
	private EntityManager theManager;
	/** Фабрика соединений */
	@Comment("Фабрика соединений")
	public EntityManagerFactory getManagerFactory() {
		return theManagerFactory;
	}

//	public void setManagerFactory(EntityManagerFactory aManagerFactory) {
//		theManagerFactory = aManagerFactory;
//	}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {
		return theUsername;
	}

//	public void setUsername(String aUsername) {
//		theUsername = aUsername;
//	}
	

	public void setTransaction(LiveTransaction aTransaction) {
		theTransaction = aTransaction;
	}

	public LiveTransaction getTransaction() {
		return theTransaction ;
	}
	
	private LiveTransaction theTransaction ;
	
	
	/** Пользователь */
	private final String theUsername;
	/** Фабрика соединений */
	private final EntityManagerFactory theManagerFactory;
}
