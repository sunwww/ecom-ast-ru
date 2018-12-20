package ru.ecom.ejb.services.entityjslistener;

import org.apache.log4j.Logger;
import org.mozilla.javascript.*;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@Local(IEntityJsListenerService.class)
@Stateless
public class EntityJsListenerServiceBean implements IEntityJsListenerService {

	private static final Logger LOG = Logger
			.getLogger(EntityJsListenerServiceBean.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public void postPersist(Object aEntity) {
		execute(aEntity, "postPersist") ;
	}

	
	public void postUpdate(Object aEntity) {
		execute(aEntity, "postUpdate") ;
	}

	public void preRemove(Object aEntity) {
		execute(aEntity, "preRemove") ;
	}
	
	private void execute(Object aEntity, String aFunctionName) {
		LOG.info("execute = "+aFunctionName+" "+aEntity);

		if(aEntity==null) throw new IllegalArgumentException("Нет параметра aEntity") ;
		try {
			File jsDir = new File("/tmp/test.js") ; //EjbInjection.getInstance().getEntityListenerJsDir() ;
			File file = new File(jsDir, aEntity.getClass().getSimpleName()+".js");
			if(file.exists()) {
				Context jsContext = Context.enter() ;
				Scriptable scope = jsContext.initStandardObjects();
				try (InputStreamReader in = new InputStreamReader(new FileInputStream(file),"utf-8")) {
					Script script = jsContext.compileReader(in, file.getName(), 0, null);
					script.exec(jsContext, scope);
					Object finded = scope.get(aFunctionName, scope) ;
					if(finded instanceof Function) {
						Function f = (Function) scope.get(aFunctionName, scope) ;
						ListenerContext ctx = new ListenerContext(theManager, theSessionContext) ;
						Object[] args = new Object[] {aEntity, ctx} ;
						Object ret = f.call(jsContext, scope, scope, args);
						if(ret instanceof Undefined) {
							Undefined un = (Undefined) ret ;
							LOG.info(un.readResolve()) ;
						}
						LOG.info("f = "+ret) ;
					}
					if (CAN_DEBUG) LOG.debug("execute: aFunctionName = " + finded);
					
				} finally {
					Context.exit() ;
				}
			} else {
				LOG.warn("file "+file+" not found");
			}
		} catch (Exception e) {
			LOG.error("Ошибка ",e) ;
			throw new RuntimeException(e);
		}
		
	}
	
	private @PersistenceContext EntityManager theManager ;
	private @Resource SessionContext theSessionContext ;

}
