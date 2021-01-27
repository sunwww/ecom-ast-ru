<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Справочник базовых тарифов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocBaseTariff" name="Сформировать новое"
                          roles="/Policy/E2/Create"/>
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="e2_vocBaseTariff_st"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="tariffList" nativeSql="select t.id, t.value, t.startDate, t.finishDate
    ,vocTariff.name as f5_tariffType
    ,case when t.finishDate is null or t.finishDate>current_date then 'color:grenn' else 'color:red' end as f6_styleRow
    from VocCoefficient t
    left join VocE2BaseTariffType vocTariff on vocTariff.id=t.type_id
    where t.dtype='VocE2BaseTariff'
    order by t.startDate desc, vocTariff.name limit 25
    "/>
                <msh:table name="tariffList" action="entityView-e2_vocBaseTariff.do" idField="1"
                           disableKeySupport="true" styleRow="6">
                    <msh:tableColumn columnName="Тип тарифа" property="5"/>
                    <msh:tableColumn columnName="Значение тарифа" property="2"/>
                    <msh:tableColumn columnName="Дата начала действия" property="3"/>
                    <msh:tableColumn columnName="Дата окончания действия" property="4"/>
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>