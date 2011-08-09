<%@ page import="ru.nuzmsh.web.util.StringSafeEncode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


<tiles:put name='title' type='string'>
	<msh:title>${title}</msh:title>
</tiles:put>
	
<tiles:put name='body' type='string'>
<pre style="font-size: 130%">
${code} 
</pre>
</tiles:put>

<tiles:put name='side' type='string'>
</tiles:put>

<tiles:put name="javascript" type="string">
    <script type="text/javascript">
    </script>
</tiles:put>

</tiles:insert>