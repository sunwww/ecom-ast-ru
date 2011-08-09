<%@ page import="ru.nuzmsh.web.messages.ErrorMessage"%>
<%@ page import="javax.servlet.ServletException"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.Enumeration"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="side" type="string">
    <msh:sideMenu>
    	<msh:sideLink key='ALT+1' params="" action=' javascript:backException(".do")' name="Отмена"/>
    	<msh:sideLink key='ALT+2' action=' javascript:showException(".do")' roles="/Policy/Stac/CustomizeMode/Edit" name="Ошибка" title="Показать ошибку"/>
    </msh:sideMenu>
    
    </tiles:put>

    <tiles:put name="body" type="string">
        <input type="hidden" value="1" id='type' name='type'/>
<div name="divException" id="divException" style="display: none;">
       <h1>Ошибка: </h1>
       <msh:ifInRole roles="/Policy/Config/IdeMode/Enabled">
       <msh:ifInIdeMode>
       <script type="text/javascript">
       $('type').value = "0";
       </script>
       </msh:ifInIdeMode>
       </msh:ifInRole>

        <%
            if(request.getAttribute("javax.servlet.error.message")!=null) {
                %>
                    <div class='error'>
                        <%=request.getAttribute("javax.servlet.error.message")%>
                    </div>
                <%
            }
        %>

        <pre style="font-size: 12px">
        <%
            PrintWriter writer = new PrintWriter(out);
            if(exception!=null) {

                Throwable t  ;
                ErrorMessage m = (ErrorMessage) request.getAttribute(ErrorMessage.class+".ERROR_MESSAGE") ;
                if(m!=null) {
                    t = m.getException() ;
                    while(t!=null) {
                        t.printStackTrace(writer) ;
                        t = t.getCause() ;
                    }
                }

                out.println("---------------------------------") ;
                t = exception ;
                if (exception instanceof ServletException)
                {
                    Throwable rootCause = ((ServletException) exception).getRootCause();
                    if (rootCause != null)
                     t = rootCause;
                }

                while(t!=null) {
                    t.printStackTrace(writer) ;
                    t = t.getCause() ;
                }


            }
        %>
        </pre>

        <h2>Аттрибуты</h2>
        <table border='1' class='tabview sel'>
            <tr>
                <th>Аттрибут</th>
                <th>Значение</th>
            </tr>
        <%
            Enumeration attributes = request.getAttributeNames() ;
            while(attributes.hasMoreElements()) {
                String key = (String) attributes.nextElement() ;
                out.println("<tr><td>") ;
                out.println(key) ;
                out.println("</td><td>") ;
                out.println(request.getAttribute(key)) ;
                out.println("</td></tr>") ;
            }
        %>
        </table>
      </div>  
    </tiles:put>
    <tiles:put name="javascript" type="string">
     <script type="text/javascript">
        if ($('type').value=='1') backException();
		function showException() {
			$("divException").style.display="block";
			
		}
		function backException() {
			window.history.back();
		}
     </script>
    </tiles:put>

</tiles:insert>
