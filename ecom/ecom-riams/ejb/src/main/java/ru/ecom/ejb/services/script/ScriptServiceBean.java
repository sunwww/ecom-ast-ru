package ru.ecom.ejb.services.script;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.mozilla.javascript.*;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.util.injection.EjbInjection;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Stateless
@Remote(IScriptService.class)
@Local(IScriptService.class)
@SecurityDomain("other")
public class ScriptServiceBean  implements IScriptService {

	private static final Logger LOG = Logger.getLogger(ScriptServiceBean.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();

	@Override
	public Object invoke(String aServiceName, String aMethodName, Object[] args) {

		String jsResourceName = "/META-INF/scriptService/"+aServiceName+".js" ;
		try {
			InputStream inputStream = ecomConfig.getInputStream(jsResourceName, EjbEcomConfig.SCRIPT_SERVICE_PREFIX, true) ;
			if(inputStream!=null) {
				Context jsContext = Context.enter();
				try (Reader in = new InputStreamReader(inputStream, "utf-8")) {
					Scriptable scope = jsContext.initStandardObjects();
						Script script = jsContext.compileReader(in, jsResourceName, 1,null);
						script.exec(jsContext, scope);
						
						Object o = scope.get(aMethodName, scope);
						if(o instanceof Function) {
							Function f = (Function) o ;
							
							Object result = f.call(jsContext, scope, scope, createArguments(args));
							if (CAN_DEBUG) LOG.debug("invoke: result = " + result);
							Object ret ;
							if(result instanceof NativeJavaObject) {
								ret = ((NativeJavaObject) result).unwrap() ;
							} else {
								ret = result ;
							}
							return ret ;
						} else {
							throw new IllegalArgumentException("Нет функции "+aMethodName+" у сервиса "+aServiceName+" : "+o) ; 
						}
				} finally {
					Context.exit();
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException("Ошибка открытия ресурса "+jsResourceName) ;
		}
		
		return "Hello" ;
	}

	

	private Object[] createArguments(Object[] aInputArguments) {
		ScriptServiceContext ctx = new ScriptServiceContext(manager, context, ejbInjection) ;
		Object[] args = new Object[aInputArguments.length+1] ;
		args[0] = ctx ;
		for(int i=0; i<aInputArguments.length; i++) {
			args[i+1] = aInputArguments[i];
		}
		return args ;
		
	}
	
	EjbEcomConfig ecomConfig = EjbEcomConfig.getInstance(); 
	@PersistenceContext
	EntityManager manager;

	@Resource
	SessionContext context;
	
	private final EjbInjection ejbInjection = EjbInjection.getInstance();
	
}
