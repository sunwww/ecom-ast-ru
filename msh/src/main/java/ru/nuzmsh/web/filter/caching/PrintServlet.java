package ru.nuzmsh.web.filter.caching;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class PrintServlet extends HttpServlet {

	private final static Logger LOG = Logger.getLogger(RewritingHttpServletRequest.class);
	private final static boolean CAN_TRACE = LOG.isDebugEnabled();
	
	@Override
	protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
		OutputStream out = aResponse.getOutputStream() ;
		if(CAN_TRACE) LOG.info("skin: "+aRequest.getPathTranslated());
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
