package ru.ecom.ejb.util.injection;

import javax.naming.InitialContext;

import ru.ecom.ejb.services.script.IScriptService;
import sun.awt.windows.ThemeReader;

public class EjbInjection {

	public static EjbInjection getInstance(String aAppName) {
		return new EjbInjection(aAppName); 
	}
	public static EjbInjection getInstance() {
		return new EjbInjection(null); 
	}
	public EjbInjection(String aAppname) {
		if (aAppname!=null && !aAppname.equals("")) {
			theAppname=aAppname;
		} else {
			EjbEcomConfig theConfig = EjbEcomConfig.getInstance();
			theAppname=theConfig.get("default.appname","riams-app");
			
		}
	}


	
	private <T> T getService(Class<T> aServiceClass, String aSuffix)  {
		return (T) getService(aServiceClass.getSimpleName(), aSuffix) ;
	}

	/**
	 * Получение сервиса по интерфейсу
	 * @param <T>
	 * @param aInterfaceSimpleName
	 * @return
	 */
	public Object getService(String aInterfaceSimpleName, String aSuffix)  {
		String name = aInterfaceSimpleName.substring(1);
		try {
			InitialContext ctx = new InitialContext();
			try {
				// FIXME получение названия приложения
				
				//String appName = theConfig.get("default.appname","riams-app");
				
				String jndi = theAppname + "/" + name + "Bean/"+aSuffix ;
				//System.out.println("jndi = "+jndi) ;
				return ctx.lookup(jndi);
			} finally {
				ctx.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Ошибка инициализации контекста: "+e.getMessage(),e ) ;
		}
	}
	
	public <T> T getLocalService(Class<T> aServiceClass)  {
		return getService(aServiceClass, "local") ;
	}
	
	public Object getLocalService(String aInterfaceSimplename)  {
		return getService(aInterfaceSimplename, "local") ;
	}

	public <T> T getRemoteService(Class<T> aServiceClass)  {
		return getService(aServiceClass, "remote") ;
	}

	public Object invoke(String aServiceName, String aMethodName, Object[] args) {
		IScriptService scriptService = getLocalService(IScriptService.class) ;
		return scriptService.invoke(aServiceName, aMethodName, args);
	}
	
	//private final EjbEcomConfig theConfig = EjbEcomConfig.getInstance();
	private final String theAppname ;
}
