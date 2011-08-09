<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
       <h1>Список измененных полей в форме <span><%=request.getAttribute("formName")%></span></h1>

        <msh:table name="list" action="formCustomizeEditRedirect.do" decorator="decorator">
            <msh:tableColumn columnName="Поля" property="name"/>
            <msh:tableColumn columnName="Подпись к полю" property="label"/>
            <msh:tableColumn columnName="Показывать" property="visible"/>
            <msh:tableColumn columnName="Значение по-умолчанию" property="defaultValue"/>
        </msh:table>

     </tiles:put>

     <tiles:put name="style" type="string">
         <style type="text/css">
             td.label {
                 text-align: left ;
             }
         </style>
     </tiles:put>

</tiles:insert>
