package ru.ecom.ejb.services.live;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PostRemove;
import javax.persistence.PreRemove;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.live.domain.LiveEntity;
import ru.ecom.ejb.services.live.domain.LiveProperty;
import ru.ecom.ejb.services.live.domain.LiveTransaction;
import ru.ecom.ejb.services.live.domain.journal.DeleteJournal;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.jboss.live.LiveTransactionContext;
import ru.nuzmsh.util.PropertyUtil;

public class DeleteListener {
	private final static Logger LOG = Logger.getLogger(DeleteListener.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	EntityManager theManager ;
	StringBuilder theSerialization ;
	String theUsername ;
	String theClassHibernate ;
	String theEntityId ;
	
	@PostRemove
	public void check_remove(Object aObject) {
		System.out.println("post delete....") ;
		
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
		
		System.out.println("-----object="+entityId) ;
		String clazzHib = theEntityHelper.getHibernateName(clazz) ;
		List<DeleteJournal>list = theManager.createQuery("from DeleteJournal where objectId=:entityId and className=:className and loginName=:username order by id desc")
			.setParameter("entityId", String.valueOf(entityId))
			.setParameter("className", clazzHib)
			.setParameter("username",theUsername)
			.setMaxResults(1).getResultList();
		if (!list.isEmpty()) {
			System.out.println("-------------edit status") ;
			DeleteJournal deleteJournal = list.iterator().next();
			deleteJournal.setStatus(Long.valueOf(1)) ;
			theManager.persist(deleteJournal) ;

		}
	}
	@PreRemove
	public void remove(Object aObject) throws NamingException {
		//System.out.println("username="+theContext.getCallerPrincipal());
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
							&& (method.getParameterTypes()==null ||  method.getParameterTypes().length==0)) {
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
			System.out.println("        -pre------DeleteListener."+clazz.getName()+"."+theUsername+"."+entityId) ;
			String keyAttribute = new StringBuilder().append("DeleteListener.")
				.append(clazz.getName()).append(".").append(theUsername)
				.append(".").append(entityId).toString() ;
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
			dj.setStatus(Long.valueOf(0)) ;
			theManager.persist(dj) ;

		}
		
		
	}
	
	
	private EntityHelper theEntityHelper = EntityHelper.getInstance() ;
	//@Resource SessionContext theContext ;

}
