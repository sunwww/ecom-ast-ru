package ru.nuzmsh.web.struts.forms.customize;

import org.apache.log4j.Logger;
import ru.nuzmsh.web.struts.forms.customize.impl.xml.XmlFormCustomizeService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FormCustomizeServletConfig extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(FormCustomizeServletConfig.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;

    

    @Override
	protected void service(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
    	aResponse.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = aResponse.getWriter() ;
    	IFormCustomizeService service = FormCustomizeServiceHolder.getService();
    	out.println("<h5>IFormCustomizeService = "+service+"</h5>") ;
    	if(service!=null) {
        	try {
        		out.println("<table border='1' cellpadding='4' cellspacing='0' class='formCustomize'>");
        		out.println("<tr><th>name</th><th>label</th><th>visibled</th><th>default</th></tr>") ;
    			for( FormInfo info : service.listCustomizedForms()) {
    				out.println("<tr class='formCustomizeForm'>") ;
    				out.println("<th style='background-color: #ddd; text-align: left' colspan='4'>"+info.getName()+"</th>");
    				out.println("</tr>") ;
    				for(FormElementInfo elm : service.listCustomizedElements(info.getName())) {
    					out.print("<tr>") ;
    					out.print("<td class='formCustomizeElm'>"+elm.getName()+"</td>") ;
    					out.print("<td>"+elm.getLabel()+"</td>") ;
    					out.print("<td>"+elm.getVisible()+"</td>") ;
    					out.print("<td>"+elm.getDefaultValue()+"</td>") ;
    					out.print("</tr>") ;
    				}
    			}
        		out.println("</table>");
    		} catch (FormCustomizeException e) {
    			throw new RuntimeException(e) ;
    		}
    	}
	}

	public void init(ServletConfig servletConfig) throws ServletException {
    	LOG.info("initializing ...") ;
        if (CAN_TRACE) LOG.info("init() " + servletConfig);
        String catalinaHome = System.getProperty("catalina.home") ;
        String configHome = "/opt/tomcat/conf" ;
        if(catalinaHome==null) {
            LOG.warn("Нет системного свойства catalina.home. Будет использоваться /opt/tomcat/conf");
        } else {
            configHome = new StringBuilder().append(catalinaHome).append("/conf/").toString() ;
        }
        XmlFormCustomizeService service = new XmlFormCustomizeService();
        if (CAN_TRACE) LOG.debug("init: configHome = " + configHome); 

        service.setConfigDir(configHome);

        service.start() ;
        FormCustomizeServiceHolder.putService(service);
    }

    public void destroy() {
        if (CAN_TRACE) LOG.info("destroying ... ");

        IFormCustomizeService service = FormCustomizeServiceHolder.getService();
        if(service!=null) service.stop() ;
        FormCustomizeServiceHolder.removeService();

    }

}
