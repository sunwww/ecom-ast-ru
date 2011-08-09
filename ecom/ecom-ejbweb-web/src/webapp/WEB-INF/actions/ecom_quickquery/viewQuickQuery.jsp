<%@ page import="java.util.*" %>
<%@ page import="ru.nuzmsh.web.util.StringSafeEncode" %>
<%@ page import="ru.ecom.ejb.services.quickquery.response.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
		
	<tiles:put name='body' type='string'>
		<%
			QuickQueryResponse r = (QuickQueryResponse)request.getAttribute("response")  ;
			out.println("<h1>"+r.getName()+"</h1>") ;
			Iterator queries = r.getQueries().iterator() ;
			while(queries.hasNext()) {
				QuickQuery query = (QuickQuery)queries.next() ;
				out.println("<h2>"+query.getName()+"</h2>") ;
				out.println("<table border='1' class='tabview sel tableArrow'>") ;
				Iterator rows = query.getRows().iterator();
				while(rows.hasNext()) {
					out.println("<tr>") ;
					QuickQueryRow row = (QuickQueryRow)rows.next() ;
					Iterator cells = row.getCells().iterator();
					while(cells.hasNext()) {
						Object value = cells.next();
						out.print("<td>"+value+"</td>") ;
					}
					out.println("</tr>") ;
				}
				out.println("</table") ;
			}
		%>
	</tiles:put>
	
	<tiles:put name='side' type='string'>
	</tiles:put>
	
	<tiles:put name="javascript" type="string">
	    <script type="text/javascript">
	    </script>
	</tiles:put>

</tiles:insert>