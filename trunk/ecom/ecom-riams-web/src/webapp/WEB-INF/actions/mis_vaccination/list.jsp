<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Vaccination">Список вакцинаций</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/Vaccination/Create" key='ALT+N' params="" action="/entityPrepareCreate-mis_vaccination" name="Добавить вакцинацию"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
                <msh:table name="list" action="entityView-mis_Vaccination.do" idField="id" disableKeySupport="true">
                    <msh:tableColumn columnName="Дата выполнения" property="executeDate"/>
                    <msh:tableColumn columnName="Вакцина" property="vaccine"/>
                    <msh:tableColumn columnName="Серия" property="series"/>                    
                    <msh:tableColumn columnName="Срок годности до" property="expirationDate"/>
                    <msh:tableColumn columnName="Фаза" property="phase"/>
                 </msh:table>
    </tiles:put>

</tiles:insert>