package ru.ecom.web.servlet;

import org.apache.log4j.Logger;
import ru.nuzmsh.util.PropertyUtil;

import javax.servlet.http.HttpServlet;
import java.beans.Introspector;

/**
 * Удаление хэшей
 */
public class DestroyServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(DestroyServlet.class);

    @Override
	public void destroy() {
		LOG.info("  Destoying PropertyUtil hash...") ;
		PropertyUtil.destroy() ;
		LOG.info("  Destroying Introspector's caches...") ;
		PropertyUtil.destroy() ;
		Introspector.flushCaches() ;
	}

}
