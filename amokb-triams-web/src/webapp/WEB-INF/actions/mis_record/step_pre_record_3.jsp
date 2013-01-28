<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ОФОРМЛЕНИЕ ПРЕДВАРИТЕЛЬНОЙ ЗАПИСИ. Шаг 4. Выбор врача</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    <msh:table name="listFunctions" action="step_pre_record_4.do" idField="1" hideTitle="true">
	    	<msh:tableColumn property="2" columnName="ФИО врача"/>
	    </msh:table>

	    </div>
    </tiles:put>

</tiles:insert>