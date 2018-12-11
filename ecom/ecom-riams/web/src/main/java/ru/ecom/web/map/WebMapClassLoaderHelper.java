package ru.ecom.web.map;

import ru.ecom.ejb.services.entityform.map.MapClassLoader;

import javax.servlet.http.HttpServletRequest;

public class WebMapClassLoaderHelper {

	public void setMapClassLoader(HttpServletRequest aRequest) {
		orig = Thread.currentThread().getContextClassLoader() ;
		Thread.currentThread().setContextClassLoader(new MapClassLoader(orig, true));
	}

	public void unsetMapClassLoader() {
        Thread.currentThread().setContextClassLoader(orig);
	}

	public Class loadClass(String aClassname) throws ClassNotFoundException {
		return Thread.currentThread().getContextClassLoader().loadClass(aClassname);
	}

	ClassLoader orig = Thread.currentThread().getContextClassLoader() ;

}
