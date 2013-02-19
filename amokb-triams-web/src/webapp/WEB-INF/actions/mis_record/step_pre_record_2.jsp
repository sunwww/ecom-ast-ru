<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">${infoRecord} Шаг 3. Выбор специальности врача</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    tr.listFunctions {
    margin-top: 1px; margin-bottom: 1px;
    }
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    
	    <div>

	    </div>
	    	    <div id='div1' style="float: left; height: 300px">
	    <msh:table name="listFunctions" action="${path_rec}3.do" idField="1" hideTitle="true">
	    	<msh:tableColumn property="3" columnName="Наименование специальности"/>
	    </msh:table>
	    </div>

    </tiles:put>

</tiles:insert>