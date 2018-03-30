<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Справочник подвидов случая</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocEntrySubType" name="Создать" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="tariffList" nativeSql="select voc.id, voc.code||' '||coalesce(voc.name,'') as name,voc.tariffCode, usl.code||' '||usl.name as uslName
                ,case when voc.isArchival='1' then 'color:red' end as f5_color
    from VocE2EntrySubType voc
    left join VocE2FondV006 usl on usl.id=voc.uslok_id
    " />
                <msh:table  name="tariffList" action="entityView-e2_vocEntrySubType.do" idField="1" disableKeySupport="true" styleRow="5">
                    <msh:tableColumn columnName="Тип записи" property="2" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Код тарифа" property="3" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Условия оказания" property="4" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>