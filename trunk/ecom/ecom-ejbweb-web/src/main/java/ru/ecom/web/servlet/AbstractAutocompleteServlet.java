package ru.ecom.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author esinev 18.08.2006 1:53:34
 */
public abstract class AbstractAutocompleteServlet extends HttpServlet {
    private final static Log LOG = LogFactory.getLog(AbstractAutocompleteServlet.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;



    public abstract void printFindByQuery(HttpServletRequest aRequest, String aQuery, int aCount, PrintWriter aOut) throws Exception ;

    public abstract void printNext(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception ;

    public abstract void printPrevious(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception ;

    public static String getNextDir(HttpServletRequest aRequest) {
            String uri = aRequest.getRequestURI() ;
            int lastSlash = uri.lastIndexOf('/') ;
            String vocname = lastSlash!=-1 ? vocname = uri.substring(lastSlash+1) :"ERROR_GETTING_VOCNAME_IN_SERVLET" ;
            return vocname ;
        }

    protected void p(PrintWriter out, String aId, String aName) {
        out.println(" <row>") ;
        out.print("  <id>") ;
        out.print(aId) ;
        out.println("</id>") ;
        out.print("<name>") ;
        if(aName.indexOf('<')>-1) {
            aName = aName.replace('<',' ') ;
        }
        out.print(aName) ;
        out.println("</name>") ;
        out.println(" </row>") ;
    }

    protected void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
        aRequest.setCharacterEncoding("UTF-8");
        aResponse.setContentType("text/xml; charset=utf-8");

        String direction = aRequest.getParameter("direction") ;
        String id = aRequest.getParameter("id") ;
//        if(aRequest.getParameter("parent")!=null) {
//            id=id+";"+aRequest.getParameter("parent") ;
//        }
        if (CAN_TRACE) LOG.trace("id = " + id);
        String query = aRequest.getParameter("query");
        if (CAN_TRACE) LOG.trace("query = " + query);
        boolean isForward = !"backward".equals(direction) ;
        if (CAN_TRACE) LOG.trace("isForward = " + isForward);
        int COUNT = 10 ;
        PrintWriter out = aResponse.getWriter() ;
        out.println("<?xml version='1.0' encoding='utf-8'  ?>") ;
        out.println("<result>") ;
        out.print("<requestId>") ;
        out.print(id) ;
        out.println("</requestId>") ;

        try {

            if(query!=null) {
                printFindByQuery(aRequest, query, COUNT, out) ;
            } else {
                if(isForward) {
                    printNext(aRequest, id, COUNT, out) ;
                } else {
                    printPrevious(aRequest, id, COUNT, out) ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace() ;
            throw new ServletException("ERROR: "+e, e) ;
        }
        out.println("</result>") ;
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }


}
