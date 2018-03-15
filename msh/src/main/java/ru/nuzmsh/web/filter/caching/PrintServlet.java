package ru.nuzmsh.web.filter.caching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintServlet extends HttpServlet {

	private final static Log LOG = LogFactory
		.getLog(RewritingHttpServletRequest.class);
	private final static boolean CAN_TRACE = LOG.isTraceEnabled();
	
	@Override
	protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
		OutputStream out = aResponse.getOutputStream() ;
		if(CAN_TRACE) LOG.trace("skin: "+aRequest.getPathTranslated());
		File file = new File(aRequest.getPathTranslated()) ;
		if(file.exists()) {
			aResponse.setHeader("Content-Length", String.valueOf(file.length())) ;
			byte[] buf = new byte[(int)file.length()] ;
			
			FileInputStream in = new FileInputStream(file) ;
			try {
				in.read(buf);
				out.write(buf) ;
			} finally {
				in.close() ;
			}
		} else {
			throw new FileNotFoundException(aRequest.getRequestURI()) ;
		}
	}
	

}
