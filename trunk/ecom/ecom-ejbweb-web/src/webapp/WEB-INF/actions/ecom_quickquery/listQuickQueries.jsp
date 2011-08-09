<%@ page import="java.util.*" %>
<%@ page import="ru.nuzmsh.web.util.StringSafeEncode" %>
<%@ page import="ru.ecom.ejb.services.quickquery.response.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
		
	<tiles:put name='body' type='string'>
		<ul>
		<%
			Map map = (Map)request.getAttribute("map");
			Iterator keys = map.keySet().iterator() ;
			while(keys.hasNext()) {
				Object key = keys.next() ;
				Object name = map.get(key) ;
				%>
				<li><a href='ecom_executeQuickQuery.do?id=<%=key%>'><%=name%></a></li>
				<%
				out.println("") ;
			}
		%>
		</ul>
	</tiles:put>
	
	<tiles:put name="javascript" type="string">
	    <script type="text/javascript">
	    </script>
	</tiles:put>

<tiles:put name='title' type='string'>
	<msh:title>Список отчетов</msh:title>
</tiles:put>

</tiles:insert>