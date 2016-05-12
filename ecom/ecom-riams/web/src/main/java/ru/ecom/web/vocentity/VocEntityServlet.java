package ru.ecom.web.vocentity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.vocentity.IVocEntityService;
import ru.ecom.web.servlet.AbstractAutocompleteServlet;
import ru.ecom.web.util.Injection;

public class VocEntityServlet extends HttpServlet {

	private final static Logger LOG = Logger.getLogger(VocEntityServlet.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	
	@Override
	public void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(arg0, arg1);
	}

	@Override
	public void service(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		aResponse.setCharacterEncoding("utf-8");
		Enumeration en = aRequest.getParameterNames() ;
		while(en.hasMoreElements()) {
			String key = (String) en.nextElement() ;
			System.out.println(key+" = "+aRequest.getParameter(key)) ;
		}
		 
		PrintWriter out = aResponse.getWriter();
		String callback = aRequest.getParameter("callback");
		int start = 0 ;
		int limit = 25 ;
		String orderBy = aRequest.getParameter("sort") ;
		boolean descending = "DESC".equals(aRequest.getParameter("dir")) ;
		
		
		try {
			start = Integer.parseInt(aRequest.getParameter("start"));
		} catch (Exception e) {}
		try {
			limit = Integer.parseInt(aRequest.getParameter("limit"));
		} catch (Exception e) {}
		
		if(callback!=null) {
			out.print(callback+"(") ;
		}
		try {
			IVocEntityService service = Injection.find(aRequest).getService(IVocEntityService.class);
			String vocEntityClass = AbstractAutocompleteServlet.getNextDir(aRequest);
			String json = service.loadJsonValues(vocEntityClass, start, limit, orderBy, !descending) ; 
			out.println(json);
			//System.out.println(json);
			
		} catch (NamingException e) {
			LOG.error(e.getMessage(),e);
		}
//		out
//				.print("{\"totalCount\":\"1\",    \"topics\":[{\"post_id\":\"53657\"                ,\"topic_title\":\"Visibility Problem in 1.1\"                ,\"topic_id\":\"10890\"                ,\"author\":\"mystix\"                ,\"post_time\":\"1186816484\"                ,\"post_text\":\"firstly, i think the correct format for the tag menus ...\"                ,\"forum_title\":\"Bugs\"                ,\"forumid\":\"3\"                ,\"reply_count\":\"4\"               }            ]}               ");
		if(callback!=null) {
			out.println(");") ;
		}
	}

}
