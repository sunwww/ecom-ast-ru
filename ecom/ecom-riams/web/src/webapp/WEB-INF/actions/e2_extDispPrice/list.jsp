<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Цена ДД по возрасту и полу</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_extDispPrice" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="e2_extDispPrice_st"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <ecom:webQuery name="entryList" nativeSql="select edp.id ,v016.name as f2_dName
            , vs.name as sex
                 ,edp.ages as ages
                ,edp.cost as cost
                ,edp.dateFrom, edp.dateTo as dateTo
                ,case when edp.dateTo is not null and edp.dateTo>current_date then 'color:red' else '' end as color
                from ExtDispPrice edp
                left join vocSex vs on vs.id=edp.sex_id
                left join VocE2FondV016 v016 on v016.id=edp.dispType_id
                order by edp.dateFrom, v016.name desc "/>
            <msh:section title='Результат поиска'>
                <msh:table  name="entryList" action="entityView-e2_extDispPrice.do" idField="1" disableKeySupport="true" styleRow="6">
                    <msh:tableColumn columnName="id" property="1"  />
                    <msh:tableColumn columnName="Тип ДД" property="2" width="50" />
                    <msh:tableColumn columnName="Пол" identificator="false" property="3" />
                    <msh:tableColumn columnName="Возраста" identificator="false" property="4" />
                    <msh:tableColumn columnName="Цена" identificator="false" property="5" />
                    <msh:tableColumn columnName="Дата начала" identificator="false" property="6" />
                    <msh:tableColumn columnName="Дата конца" identificator="false" property="7" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>