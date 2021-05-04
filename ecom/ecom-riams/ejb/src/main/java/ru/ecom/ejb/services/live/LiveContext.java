package ru.ecom.ejb.services.live;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.live.domain.LiveTransaction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
@Getter
@Setter
public class LiveContext {

	public LiveContext(EntityManagerFactory aFactory, String aUsername) {
		managerFactory = aFactory ;
		username = aUsername ;
	}

	/** Manager */
	private EntityManager manager;
	/** Фабрика соединений */

	private LiveTransaction transaction ;
	
	/** Пользователь */
	private final String username;
	/** Фабрика соединений */
	private final EntityManagerFactory managerFactory;
}
