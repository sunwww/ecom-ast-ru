package ru.ecom.ejb.services.live;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.live.domain.journal.DeleteJournal;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.jboss.live.LiveTransactionContext;
import ru.nuzmsh.util.PropertyUtil;

import javax.persistence.*;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DeleteListener {
	private static final Logger LOG = Logger.getLogger(DeleteListener.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	EntityManager theManager ;
	StringBuilder theSerialization ;
	String theUsername ;
	String theClassHibernate ;
	String theEntityId ;
	
	@PostRemove
	public void check_remove(Object aObject) {
		ILiveService service = EjbInjection.getInstance().getLocalService(ILiveService.class) ;
		LiveTransactionContext ctx = LiveTransactionContext.get() ;
		//String username = service.getUsername() ;

		if(ctx.getEntityManager()==null) { // начинаем транзакцию
			ctx.setEntityManager(service.getEntityManagerFactory().createEntityManager()) ;
			theManager = ctx.getEntityManager() ;
		} else {
			theManager = ctx.getEntityManager() ;
		}
		Class clazz = aObject.getClass() ;
		
		theUsername = service.getUsername() ;
		Object entityId = theEntityHelper.getEntityId(aObject) ;
		
		String clazzHib = theEntityHelper.getHibernateName(clazz) ;
		List<DeleteJournal>list = theManager.createQuery("from DeleteJournal where objectId=:entityId and className=:className and loginName=:username order by id desc")
			.setParameter("entityId", String.valueOf(entityId))
			.setParameter("className", clazzHib)
			.setParameter("username",theUsername)
			.setMaxResults(1).getResultList();
		if (!list.isEmpty()) {
			DeleteJournal deleteJournal = list.iterator().next();
			deleteJournal.setStatus(1L) ;
			theManager.persist(deleteJournal) ;

		}
	}
	@PreRemove
	public void remove(Object aObject) {
		if (aObject !=null) {
			ILiveService service = EjbInjection.getInstance().getLocalService(ILiveService.class) ;
			LiveTransactionContext ctx = LiveTransactionContext.get() ;
			String username = service.getUsername() ;
			
					
			if(ctx.getEntityManager()==null) { // начинаем транзакцию
				ctx.setEntityManager(service.getEntityManagerFactory().createEntityManager()) ;
				theManager = ctx.getEntityManager() ;
			} else {
				theManager = ctx.getEntityManager() ;
			}
			Class clazz = aObject.getClass() ;
			String clazzHib = theEntityHelper.getHibernateName(clazz) ;
			if(CAN_DEBUG) LOG.debug("Saving "+clazz) ;
			Object entityId = theEntityHelper.getEntityId(aObject) ;
			
			StringBuilder ret = new StringBuilder() ;
			try {
				// перед удалением
				ret.append("<").append(clazzHib).append(" clazz='").append(clazz).append("'").append(" id='").append(entityId).append("'>") ;
				for(Method method :  clazz.getMethods()) {
					String name = method.getName() ;
					if(        (name.startsWith("get") || name.startsWith("is")) 
							&& !method.isAnnotationPresent(Transient.class)
							&& !"getClass".equals(name)
							&& !method.isAnnotationPresent(OneToMany.class)
							&& (Objects.equals(method.getParameterTypes(), null) ||  method.getParameterTypes().length==0)) {
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
						
						ret.append("<").append(propertyName).append(">") ;
						ret.append(value!=null?value:"") ;
						ret.append("</").append(propertyName).append(">") ;
						
					}
				}				
				ret.append("</").append(clazzHib).append(">") ;
				
				//manager.createNativeQuery("select top 1 $$Add^ZDeleteJournal(:username,:classname,:entityId,:comment) from VocSex")
				//	.setParameter("username", username)
				//	.setParameter("classname",theEntityHelper.getHibernateName(clazz))
				//	.setParameter("entityId", entityId)
				//	.setParameter("comment", "fdsfsdfsdf").getResultList();
			} catch (Exception e) {
				LOG.error("ERROR... "+e.getMessage()) ;
			}
			DeleteJournal dj = new DeleteJournal() ;
		//	String keyAttribute = new StringBuilder().append("DeleteListener.")
		//		.append(clazz.getName()).append(".").append(theUsername)
		//		.append(".").append(entityId).toString() ;
			theSerialization = new StringBuilder() ;
			theSerialization.append(ret) ;
			theUsername = username ;
			theClassHibernate = clazzHib ;
			theEntityId = String.valueOf(entityId) ;
			
			dj.setSerialization(ret.toString()) ;
			dj.setLoginName(username) ;
			dj.setClassName(clazzHib) ;
			String id = String.valueOf(entityId); 
			dj.setObjectId(id) ;
			
			Date date = new Date() ;
			
			dj.setDeleteDate(new java.sql.Date(date.getTime())) ;
			dj.setDeleteTime(new java.sql.Time(date.getTime())) ;
			dj.setStatus(0L) ;
			theManager.persist(dj) ;

		}
		
		
	}
	
	
	private EntityHelper theEntityHelper = EntityHelper.getInstance() ;
	//@Resource SessionContext theContext ;

}
