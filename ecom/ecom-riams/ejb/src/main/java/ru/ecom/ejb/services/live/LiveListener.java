package ru.ecom.ejb.services.live;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.live.domain.LiveEntity;
import ru.ecom.ejb.services.live.domain.LiveProperty;
import ru.ecom.ejb.services.live.domain.LiveTransaction;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.jboss.live.LiveTransactionContext;
import ru.nuzmsh.util.PropertyUtil;

import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

public class LiveListener {
	
	private static final Logger LOG = Logger.getLogger(LiveListener.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	private static final int ACTION_CREATE = 1 ;
	private static final int ACTION_UPDATE = 2 ;
	private static final int ACTION_REMOVE = 3 ;
	
	private void update(int aAction, Object aObject) throws NamingException {
		if(CAN_DEBUG) LOG.debug("updating "+aObject+" - action = "+aAction) ;
		LiveTransactionContext ctx = LiveTransactionContext.get() ;
		
		if(ctx==null) {
			try {
				throw new IllegalStateException("TEST") ;
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
			throw new IllegalStateException("LiveTransactionInterceptor не установлен") ;
		}
		
		LiveTransaction transaction ;
		EntityManager manager ;		
		if(ctx.getEntityManager()==null) { // начинаем транзакцию
			
			// получение EntityManager'а // FIXME использовать ThreadLocal из JBoss
			ILiveService service = EjbInjection.getInstance().getLocalService(ILiveService.class) ;
			ctx.setEntityManager(service.getEntityManagerFactory().createEntityManager()) ;
			manager = ctx.getEntityManager() ;
			
			transaction = new LiveTransaction() ;
			transaction.setStartTime(new Timestamp(new Date().getTime())) ;
			transaction.setUsername(service.getUsername());
			
			ctx.setLiveTransaction(transaction) ;
			
			manager.persist(transaction) ;
		} else {
			transaction = (LiveTransaction) ctx.getLiveTransaction() ;
			manager = ctx.getEntityManager() ;
		}
		
		
		if(aObject!=null) {
			Class clazz = aObject.getClass() ;
			if(CAN_DEBUG) LOG.debug("Saving "+clazz) ;
			LiveEntity entity = new LiveEntity() ;
			entity.setClassname(clazz.getName()) ;
			Object entityId = theEntityHelper.getEntityId(aObject) ;
			entity.setEntityId(entityId!=null ? entityId.toString() : null);
			entity.setTransaction(transaction) ;
			entity.setAction(aAction);
			
			manager.persist(entity) ;
			
			for(Method method :  clazz.getMethods()) {
				String name = method.getName() ;
				if((name.startsWith("get") || name.startsWith("is")) && !method.isAnnotationPresent(Transient.class) && !"getClass".equals(name) && !method.isAnnotationPresent(OneToMany.class) && method.getParameterTypes().length == 0) {
					String value ;
					try {
						Object obj = method.invoke(aObject) ;
						if(obj instanceof Collection) continue ;
						if(obj!=null) {
							if(obj.getClass().isAnnotationPresent(Entity.class)) {
								// замена объекта на его идентификатор !!!
								value = String.valueOf(theEntityHelper.getEntityId(obj));
							} else {
								value = obj.toString() ;
							}
						} else {
							value = null ;
						}
					} catch (Exception e) {
						value = e.getMessage() ;
						LOG.error("Ошибка вызова метода: "+method,e) ;
					}
					String propertyName = PropertyUtil.getPropertyName(method) ;
					LiveProperty property = new LiveProperty() ;
					property.setEntity(entity) ;
					property.setProperty(propertyName) ;
					property.setPropertyValue(value) ;
					manager.persist(property) ;
				}
			}
			
			manager.flush();
			manager.clear() ;
		}
		transaction.setEndTime(new Timestamp(new Date().getTime())) ;
	}
	/*
	@PostUpdate
	public void update(Object aObject) throws NamingException {
		update(ACTION_UPDATE, aObject) ;
	}

	@PostPersist
	public void create(Object aObject) throws NamingException {
		update(ACTION_CREATE, aObject) ;
	}
	*/
	/*
	@PreRemove
	public void remove(Object aObject) throws NamingException {
		update(ACTION_REMOVE, aObject) ;
	}
	*/
	private EntityHelper theEntityHelper = EntityHelper.getInstance() ;
	
}
