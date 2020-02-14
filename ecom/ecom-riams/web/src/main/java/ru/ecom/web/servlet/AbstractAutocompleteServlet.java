package ru.ecom.web.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author esinev 18.08.2006 1:53:34
 */
public abstract class AbstractAutocompleteServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AbstractAutocompleteServlet.class) ;

    public abstract void printFindByQuery(HttpServletRequest aRequest, String aQuery, int aCount, PrintWriter aOut) throws Exception ;

    public abstract void printNext(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception ;

    public abstract void printPrevious(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception ;

    public static String getNextDir(HttpServletRequest aRequest) {
            String uri = aRequest.getRequestURI() ;
            int lastSlash = uri.lastIndexOf('/') ;
            return lastSlash!=-1 ? uri.substring(lastSlash+1) :"ERROR_GETTING_VOCNAME_IN_SERVLET" ;
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
        boolean isJson = "json".equalsIgnoreCase(aRequest.getParameter("type"));
        aResponse.setContentType(isJson? "application/json; charset=utf-8" : "text/xml; charset=utf-8");

        String direction = aRequest.getParameter("direction") ;
        String id = aRequest.getParameter("id") ;
        String query = aRequest.getParameter("query");
        boolean isForward = !"backward".equals(direction) ;
        int COUNT = 10 ;
        PrintWriter out = aResponse.getWriter() ;

        if (isJson) {
            out.print("{\"requestId\":\""+id+"\",");
        } else { //xml
            out.println("<?xml version='1.0' encoding='utf-8'  ?>") ;
            out.println("<result>") ;
            out.print("<requestId>") ;
            out.print(id) ;
            out.println("</requestId>") ;
        }

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
            LOG.error(e.getMessage(),e) ;
            throw new ServletException("ERROR: "+e, e) ;
        }
        out.println(isJson? "}" : "</result>") ;
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }
}