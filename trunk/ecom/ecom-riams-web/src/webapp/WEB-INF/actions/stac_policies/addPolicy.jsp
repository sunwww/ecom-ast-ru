<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm" title="Список полисов"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+1' params="id" action="/entityParentView-stac_ssl"
                          name="Текущий СЛС"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:section>
            <msh:sectionTitle>Полисы пациента</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiply" name="policies" action="entitySubclassView-mis_medPolicy.do" idField="id" noDataMessage="Нет полисов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='3'>
                                <msh:toolbar>
                                    <a href='javascript:removePolicy()'>Удалить полис из случая</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="Информация о полисе" property="text"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

        <msh:section>
            <msh:sectionTitle>Список доступных полисов</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiple" name="toAdd" action="entitySubclassView-mis_medPolicy.do" idField="id" noDataMessage="Нет полисов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='3'>
                                <msh:toolbar>
                                    <a href='javascript:addPolicy()'>Добавить полис в случай</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="Информация о полисе" property="text"/>
                </msh:table>
            </msh:sectionContent>
            <ecom:webQuery name="toAdd1" nativeSql="select mp.id
            ,coalesce(mp.series,'')||' '||coalesce(mp.polnumber,'')||' '||
coalesce(ri.name) from medpolicy mp 
left join reg_ic ri on ri.id=mp.company_id 
left join kinsman k on k.kinsman_id=mp.patient_id
left join medcase mc on mc.kinsman_id=k.id
left join patient p on p.id=mc.patient_id
left join medcase_medpolicy mcp on mcp.policies_id=mp.id 
where mc.id='${param.id}' 
and (cast(to_char(mc.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int))<2
group by mp.id, mp.series, mp.polnumber,ri.name
having count(case when mcp.medcase_id='${param.id}' then 1 else null end)=0
"/>
<msh:tableNotEmpty name="toAdd1">
            <msh:sectionTitle>Список доступных полисов представителя</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiple" name="toAdd1" action="entitySubclassView-mis_medPolicy.do" idField="1" noDataMessage="Нет полисов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='3'>
                                <msh:toolbar>
                                    <a href='javascript:addPolicy()'>Добавить полис в случай</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="Информация о полисе" property="2"/>
                </msh:table>
            </msh:sectionContent>
</msh:tableNotEmpty>            
        </msh:section>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            //Element.addClassName($('mainMenuUsers'), "selected");

            function addPolicy() {
                var ids = theTableArrow.getInsertedIdsAsParams("id") ;
                if (ids) {
                    window.location = 'stac_policiesAdd.do?sslId=${param.id}&' + ids;
                } else {
                    alert("Нет выделенных полисов");
                }
            }

            function removePolicy() {
                var ids = theTableArrow.getInsertedIdsAsParams("id") ;
                if (ids) {
                    window.location = 'stac_policiesRemove.do?sslId=${param.id}&' + ids;
                } else {
                    alert("Нет выделенных полисов");
                }
            }

        </script>
    </tiles:put>


</tiles:insert>