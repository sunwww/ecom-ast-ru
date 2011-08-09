package ru.ecom.web.servlet;

import java.beans.Introspector;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import ru.nuzmsh.util.PropertyUtil;

/**
 * Удаление хэшей
 */
public class DestroyServlet extends HttpServlet {

	private final static Logger LOG = Logger.getLogger(DestroyServlet.class);
	//private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    @Override
	public void destroy() {
		LOG.info("  Destoying PropertyUtil hash...") ;
		PropertyUtil.destroy() ;
		LOG.info("  Destroying Introspector's caches...") ;
		PropertyUtil.destroy() ;
		Introspector.flushCaches() ;
	}

}
