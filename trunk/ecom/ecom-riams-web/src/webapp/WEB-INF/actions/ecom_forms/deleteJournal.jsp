<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Config">Список форм</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
    <tiles:put name='body' type='string' >
    	<ecom:webQuery name="delJournal" nativeSql="select id,serialization,deleteDate,deleteTime,loginName,className from DeleteJournal"/>
        <msh:table name="delJournal" action="js-ecom_forms-delJournal.do" idField="1">
            <msh:tableColumn property="3" columnName="Дата удаления"/>
            <msh:tableColumn property="4" columnName="Время	"/>
            <msh:tableColumn property="5" columnName="Пользователь"/>
            <msh:tableColumn property="6" columnName="Объект"/>
            <pre>
            <msh:tableColumn property="2" columnName="Описание"/>
            </pre>
        </msh:table>
    </tiles:put>
</tiles:insert>