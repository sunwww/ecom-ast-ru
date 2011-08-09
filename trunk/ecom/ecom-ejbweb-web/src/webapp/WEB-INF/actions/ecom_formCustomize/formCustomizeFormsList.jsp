<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
       <h1>Список измененных форм</h1>

        <msh:table name="list" action="formCustomizeElementsList.do" idField="name">
            <msh:tableColumn columnName="Форма" property="name"/>
        </msh:table>

     </tiles:put>


</tiles:insert>
