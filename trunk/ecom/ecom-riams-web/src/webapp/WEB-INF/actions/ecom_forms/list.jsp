<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Config">Список форм</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
    <tiles:put name='body' type='string' >
        <msh:table name="list" action="js-ecom_forms-redirectToForm.do" idField="name">
            <msh:tableColumn columnName="<a href='js-ecom_forms-listAllStrutsForms.do?sort=name'>Название</a>" property="name" />
            <msh:tableColumn columnName="<a href='js-ecom_forms-listAllStrutsForms.do?sort=comment'>Комментарий</a>" property="comment" />
            <msh:tableColumn columnName="<a href='js-ecom_forms-listAllStrutsForms.do?sort=type'>Класс формы</a>" property="type" />
        </msh:table>
    </tiles:put>
</tiles:insert>