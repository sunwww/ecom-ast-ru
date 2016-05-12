<%@ page import="ru.ecom.jaas.web.action.service.ServiceImportPolicesListForm"%>
<%@ page import="ru.ecom.jaas.ejb.service.ISecPolicyImportService"%>
<%@ page import="java.io.LineNumberReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ru.ecom.jaas.web.action.service.ServiceImportRolesForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <pre>
        <%
            ISecPolicyImportService service = (ISecPolicyImportService) request.getAttribute("service") ;

            ServiceImportPolicesListForm form = (ServiceImportPolicesListForm) request.getAttribute("form") ;
            if(form!=null) {
                LineNumberReader in = new LineNumberReader(new InputStreamReader(form.getFile().getInputStream()));
                
                String line ;
                while( (line=in.readLine())!=null) {
                    out.println(line+" ...") ;
                    out.flush() ;
                    service.importPolicy(line) ;
                }
                in.close() ;
            } else {
                Collection list = (Collection) request.getAttribute("list") ;
                ServiceImportRolesForm form2 = (ServiceImportRolesForm) request.getAttribute("form2") ;
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    String line = (String) it.next() ;
                    out.println(line+" ...") ;
                    out.flush() ;
                    service.addToRole(form2.getRoleId(), line) ;
                }
            }
        %>
        </pre>
