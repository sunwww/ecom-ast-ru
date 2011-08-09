package ru.ecom.web.map;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.entityform.map.MapClassLoader;

public class MapFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse aResponse, FilterChain aChain) throws IOException, ServletException {
		WebMapClassLoaderHelper helper = new WebMapClassLoaderHelper() ;
		helper.setMapClassLoader((HttpServletRequest) request);
        
		aChain.doFilter(request, aResponse);
        
		helper.unsetMapClassLoader();
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
