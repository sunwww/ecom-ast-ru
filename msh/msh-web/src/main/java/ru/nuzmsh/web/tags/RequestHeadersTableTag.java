package ru.nuzmsh.web.tags;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @jsp.tag name = "requestHeadersTable"
 *            display-name = "Печать заголовков http"
 *            body-content = "scriptless"
 *             description = "Печать заголовков http"
 */
public class RequestHeadersTableTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {

        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter out = getJspContext().getOut();

        out.println("<table border='1' class='sel'><tr><th>header</th><th>value</th></tr>") ;
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = (String) headers.nextElement();
            out.println("<tr><td>") ;
            out.println(key) ;
            out.println("</td><td>") ;
            out.println(request.getHeader(key)) ;
            out.println("</td></tr>") ;
        }
        printCell(out, request.getAuthType(), ".getAuthType()");
        printCell(out, request.getCharacterEncoding(), ".getCharacterEncoding()");
        printCell(out, request.getContentLength(), ".getContentLength()");
        printCell(out, request.getContentType(), ".getContentType()");
        printCell(out, request.getContextPath(), ".getContextPath()");
        printCell(out, request.getLocalAddr(), ".getLocalAddr()");
        printCell(out, request.getPathInfo(), ".getPathInfo()");
        printCell(out, request.getPathTranslated(), ".getPathTranslated()");
        printCell(out, request.getQueryString(), ".getQueryString()");
        printCell(out, request.getRequestURI(), ".getRequestURI()");
        printCell(out, request.getRequestURL(), ".getRequestURL()");
        printCell(out, request.getScheme(), ".getScheme()");
        printCell(out, request.getServletPath(), ".getServletPath()");
        out.println("</table>") ;
    }

    private static void printCell(JspWriter out, Object aValue, String aName) throws IOException {
        out.println("<tr><td>") ;
        out.println(aName) ;
        out.println("</td><td>") ;
        out.println(aValue) ;
        out.println("</td></tr>") ;
    }

}
