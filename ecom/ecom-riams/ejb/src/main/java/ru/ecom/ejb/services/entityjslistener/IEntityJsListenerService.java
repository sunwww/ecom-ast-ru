package ru.ecom.ejb.services.entityjslistener;

@Deprecated
public interface IEntityJsListenerService {

	void postUpdate(Object aEntity);
	void postPersist(Object aEntity);
	void preRemove(Object aEntity) ;
}
