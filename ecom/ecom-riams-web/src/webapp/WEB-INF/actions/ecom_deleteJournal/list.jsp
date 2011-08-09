<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Config" >Журнал удаленных данных</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
    <tiles:put name='body' type='string' >
        <msh:table navigationAction="js-ecom_deleteJournal-listNext.do" name="list" action="entityView-ecom_deleteJournal.do" idField="id">
            <msh:tableColumn property="deleteDate" columnName="Дата удаления"/>
            <msh:tableColumn property="deleteTime" columnName="Время	"/>
            <msh:tableColumn property="loginName" columnName="Пользователь"/>
            <msh:tableColumn property="className" columnName="Объект"/>
        </msh:table>
    </tiles:put>
</tiles:insert>