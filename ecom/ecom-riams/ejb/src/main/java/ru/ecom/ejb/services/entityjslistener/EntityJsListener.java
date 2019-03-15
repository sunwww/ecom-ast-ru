package ru.ecom.ejb.services.entityjslistener;

import ru.ecom.ejb.util.injection.EjbInjection;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

@Deprecated
public class EntityJsListener {

	@PostUpdate
	public void update(Object aObject) {
		findService().postUpdate(aObject);
	}

	@PostPersist
	public void create(Object aObject) {
		findService().postPersist(aObject);
	}
	
	@PreRemove
	public void remove(Object aObject) {
		findService().preRemove(aObject);
	}
	
	private IEntityJsListenerService findService() {
		return EjbInjection.getInstance().getLocalService(IEntityJsListenerService.class);
	}
}
