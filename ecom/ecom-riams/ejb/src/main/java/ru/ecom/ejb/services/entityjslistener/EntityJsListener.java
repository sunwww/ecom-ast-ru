package ru.ecom.ejb.services.entityjslistener;

import ru.ecom.ejb.util.injection.EjbInjection;

import javax.naming.NamingException;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

public class EntityJsListener {

	@PostUpdate
	public void update(Object aObject) throws NamingException {
		findService().postUpdate(aObject);
	}

	@PostPersist
	public void create(Object aObject) throws NamingException {
		findService().postPersist(aObject);
	}
	
	@PreRemove
	public void remove(Object aObject) throws NamingException {
		findService().preRemove(aObject);
	}
	
	private IEntityJsListenerService findService() {
		return EjbInjection.getInstance().getLocalService(IEntityJsListenerService.class);
	}
}
