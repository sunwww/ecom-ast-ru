package ru.nuzmsh.web.filter.caching; 

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CacheFilter implements Filter {

	//private static final long TEN_YEARS = 1000 * 60 * 60 * 24 * 10 ;
	
	private final static Log LOG = LogFactory.getLog(CacheFilter.class);
	private final static boolean CAN_TRACE = LOG.isTraceEnabled();
	
    public void doFilter(ServletRequest rawRequest, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse)response;
        HttpServletRequest request = (HttpServletRequest) rawRequest ;
        if(CAN_TRACE) {
				LOG.trace("doFilter: request.getPathInfo()       = " + request.getPathInfo()); 
				LOG.trace("doFilter: request.getPathTranslated() = " + request.getPathTranslated()); 
				LOG.trace("doFilter: request.getQueryString()    = " + request.getQueryString()); 
				LOG.trace("doFilter: request.getRequestURI()     = " + request.getRequestURI()); 
				LOG.trace("doFilter: request.getRequestURL()     = " + request.getRequestURL()); 
		        LOG.trace("doFilter:           If-Modified-Since = '" + request.getDateHeader("If-Modified-Since")+"'") ;
        }
        // 
        HttpServletRequest req ;
        
        // logLoginUserInvironment(request);
        
        if(request.getRequestURI().indexOf("-CA")>0) {
        	if(CAN_TRACE) LOG.trace("Rewriting url "+request.getRequestURI()) ;
        	res.setDateHeader("Date", getDate(0L) );
        	res.setDateHeader("Last-Modified", getDate(-10L) );
            res.setDateHeader("Expires", getDate(100L));
            res.setHeader("ETag", "\"80392742496bc71:4b9\"" );            
            //res.setHeader("Cache-Control" , "post-check=120,pre-check=240") ;
            res.setHeader("Cache-Control" , "max-age=9000,max-age=9000") ;
            
            //res.setHeader("Cache-Control", "public, post-check=7200,pre-check=43200") ;
            req = new RewritingHttpServletRequest(request) ; //, theUniqueId) ;
        } else {
        	req = request ;
        }
        chain.doFilter(req, response);
    }

    private static long getDate(long aHour) {
    	return System.currentTimeMillis()+((aHour*3600L)*1000L) ;
    }
    
    public void destroy() {
    	theUniqueId = null ;
    	CacheUniqueUtil.setUniqueId(null) ;
    }
    
    public void init(FilterConfig aConfig) {
    	theUniqueId = "-CA"+Long.toHexString(System.currentTimeMillis()) ;
    	CacheUniqueUtil.setUniqueId(theUniqueId) ;
    	LOG.info("unique = "+theUniqueId) ;
    }

	private void logLoginUserInvironment(HttpServletRequest aRequest) {
        LOG.info("Login user env : {") ;
        for(Map.Entry entry : createUserEnvironmentInfo(aRequest).entrySet() ) {
        	LOG.info("    "+entry.getKey()+" = "+entry.getValue()) ;
        }
        LOG.info("}") ;
		
	}

    private Properties createUserEnvironmentInfo(HttpServletRequest aRequest) {
    	Properties prop = new Properties() ;
    	Enumeration headers = aRequest.getHeaderNames();
    	while(headers.hasMoreElements()) {
    		String header = (String) headers.nextElement() ;
    		prop.setProperty(header, aRequest.getHeader(header));
    	}
    	return prop ;
    }
    
    private String theUniqueId ; 
}
