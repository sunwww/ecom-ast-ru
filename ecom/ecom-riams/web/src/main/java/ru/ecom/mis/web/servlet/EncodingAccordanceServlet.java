package ru.ecom.mis.web.servlet;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.vocentity.IVocEntityService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
@Deprecated
//not use
public class EncodingAccordanceServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(EncodingAccordanceServlet.class);

	@Override
	public void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(arg0, arg1);
	}

	@Override
	public void service(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws IOException {
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
            limit = Integer.parseInt(aRequest.getParameter("limit"));
		} catch (Exception e) {}

		if(callback!=null) {
			out.print(callback+"(") ;
		}
		try {
			IVocEntityService service = Injection.find(aRequest).getService(IVocEntityService.class);
			String vocEntityClass = "EncodingAccordance" ;
			String json = service.loadJsonValues(vocEntityClass, start, limit, orderBy, !descending) ; 
			out.println(json);

		} catch (NamingException e) {
			LOG.error(e.getMessage(),e);
		}
		if(callback!=null) {
			out.println(");") ;
		}
	}
}