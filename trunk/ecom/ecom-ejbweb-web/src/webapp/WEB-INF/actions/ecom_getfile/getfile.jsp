<%@ page import="ru.nuzmsh.web.util.StringSafeEncode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


<tiles:put name='title' type='string'>
	<msh:title>Загрузка файла...</msh:title>
</tiles:put>
	
<tiles:put name='body' type='string'>
	<p>
	Скоро начнется загрузка файла.
	</p>
	<p>
	Если загрузка не началась после нескольких секунд ожидания, воспользуйтесь  
	<a href='<%=request.getAttribute("url")%>'>этой ссылкой</a>.
	</p>
	
	<%
		Object next_url = request.getSession().getAttribute("next_url") ;
		if(next_url!=null) {
		%>
			<div style='margin: 2em 0 0 10em'>
			<form> <input type='button' value='Продолжить' onclick="window.location='<%=next_url%>'" />
			</form>
			</div>
		<%	
		}
	%>
</tiles:put>

<tiles:put name='side' type='string'>
</tiles:put>

<tiles:put name="javascript" type="string">
    <script type="text/javascript">
    	function nextUrl() {
			<% Object next_url = request.getSession().getAttribute("next_url") ; %>
    		<% if(next_url!=null) {	%>
	    		window.location = '<%=next_url%>' ;
    		<% } %>
    	}
    	
    	function loadfile() {
    		window.location = '<%=request.getAttribute("url")%>' ;
    		setTimeout(nextUrl, 2000) ;
    	}
    	
    	window.onload = loadfile ;
    </script>
</tiles:put>

</tiles:insert>