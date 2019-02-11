package ru.ecom.web.map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
