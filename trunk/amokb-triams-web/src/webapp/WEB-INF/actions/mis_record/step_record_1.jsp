<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="side" type="string">
      
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ОФОРМЛЕНИЕ ПРЕДВАРИТЕЛЬНОЙ ЗАПИСИ. Шаг 2. Выбор подразделения</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    tr.dep {
    	display: block;
    	width: 97% ;
    }
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    <msh:table name="dep listFunctions" action="step_record_2.do" idField="1" hideTitle="true">
	    	<msh:tableColumn property="2" columnName="Название отделения"/>
	    </msh:table>
	    
    </tiles:put>

</tiles:insert>