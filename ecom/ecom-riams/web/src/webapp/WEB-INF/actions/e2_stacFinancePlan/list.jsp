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
        <msh:sideMenu title="Добавить" >
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_stacFinancePlan" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <ecom:webQuery name="entryList" nativeSql="select fp.id
            ,fp.startDate, mhp.code||' '||mhp.name as profile
            ,ml.name as department
            ,ksg.code||' '||ksg.name as ksg
            ,fp.count
            ,fp.cost
             from financePlan fp
             left join vocksg ksg on ksg.id=fp.ksg_id
             left join VocE2MedHelpProfile mhp on mhp.id=fp.profile_id
             left join mislpu ml on ml.id=fp.department_id
              where fp.dtype='HospitalFinancePlan'
              order by startDate "/>
            <msh:section title='Результат поиска'>
                <msh:table  name="entryList" action="entityView-e2_stacFinancePlan.do" idField="1" disableKeySupport="true" styleRow="6">
                    <msh:tableColumn columnName="Период" property="2" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Профиль" property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Отделение" property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="КСГ" property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Кол-во случаев" property="6" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Цена" property="7" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>