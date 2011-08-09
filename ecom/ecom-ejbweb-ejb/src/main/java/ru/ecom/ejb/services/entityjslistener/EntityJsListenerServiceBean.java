package ru.ecom.ejb.services.entityjslistener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

import ru.ecom.ejb.services.util.JBossConfigUtil;
import ru.ecom.ejb.util.injection.EjbInjection;
import sun.reflect.generics.scope.Scope;

@Local(IEntityJsListenerService.class)
@Stateless
public class EntityJsListenerServiceBean implements IEntityJsListenerService {

	private final static Logger LOG = Logger
			.getLogger(EntityJsListenerServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
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
		System.out.println("execute = "+aFunctionName+" "+aEntity);

		if(aEntity==null) throw new IllegalArgumentException("Нет параметра aEntity") ;
		try {
			File jsDir = new File("/tmp/test.js") ; //EjbInjection.getInstance().getEntityListenerJsDir() ;
			File file = new File(jsDir, aEntity.getClass().getSimpleName()+".js");
			if(file.exists()) {
				Context jsContext = Context.enter() ;
				Scriptable scope = jsContext.initStandardObjects();
				try {
					InputStreamReader in = new InputStreamReader(new FileInputStream(file),"utf-8") ;
					try {
						Script script = jsContext.compileReader(in, file.getName(), 0, null);
						script.exec(jsContext, scope);
					} finally {
						in.close();
					}
					Object finded = scope.get(aFunctionName, scope) ;
					if(finded instanceof Function) {
						Function f = (Function) scope.get(aFunctionName, scope) ;
						ListenerContext ctx = new ListenerContext(theManager, theSessionContext) ;
						Object[] args = new Object[] {aEntity, ctx} ;
						Object ret = f.call(jsContext, scope, scope, args);
						if(ret instanceof Undefined) {
							Undefined un = (Undefined) ret ;
							System.out.println(un.readResolve()) ;
						}
						System.out.println("f = "+ret) ;
					}
					if (CAN_DEBUG)
						LOG.debug("execute: aFunctionName = " + finded); 
					
				} finally {
					Context.exit() ;
				}
			} else {
				System.out.println("file "+file+" not found");
			}
		} catch (Exception e) {
			LOG.error("Ошибка ",e) ;
			throw new RuntimeException(e);
		}
		
	}
	
	private @PersistenceContext EntityManager theManager ;
	private @Resource SessionContext theSessionContext ;

}
