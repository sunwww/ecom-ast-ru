<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocBaseTariff" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="tariffList" nativeSql="select t.id, t.value, t.startDate, t.finishDate, vbst.code||' '||vbst.name as bedType
    ,vocTariff.name as tariffType, vs.code||' '||vs.name as f7_vidSluch
    ,case when t.finishDate is null or t.finishDate>current_date then 'color:grenn' else 'color:red' end as f8_styleRow
    from VocCoefficient t
    left join vocbedsubtype vbst on vbst.id=t.stacType_id
    left join VocE2FondV015 v015 on v015.id=t.speciality_id
    left join VocE2BaseTariffType vocTariff on vocTariff.id=t.type_id
    left join Voce2VidSluch vs on vs.id=t.vidsluch_id
    where t.dtype='VocE2BaseTariff'
    order by t.startDate desc, vocTariff.name
    " />
                <msh:table  name="tariffList" action="entityView-e2_vocBaseTariff.do" idField="1" disableKeySupport="true" styleRow="8">
                    <msh:tableColumn columnName="Тип тарифа" property="6" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Тип коек" property="5" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Вид случая" property="7" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Значение тарифа" property="2" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Дата начала действия" property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Дата окончания действия" property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>