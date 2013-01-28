<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ШАГ 1.</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    <msh:row>
	        <msh:textField property="lastname"/>
	    </msh:row>
	    <msh:row>
	        <msh:textField property="firstname"/>
	    </msh:row>
	    <msh:row>
	        <msh:textField property="middlename"/>
	    </msh:row>
	    <msh:row>
	        <msh:textField property="birthday"/>
	    </msh:row>
    </tiles:put>
</tiles:insert>