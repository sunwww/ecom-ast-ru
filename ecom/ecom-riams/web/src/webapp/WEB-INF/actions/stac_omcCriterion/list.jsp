<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Случай смерти" />
    </tiles:put>
    <tiles:put name="side" type="string" >
        <msh:sideMenu title="Дополнительно">
            <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="listCrit" nativeSql="select c.id, vc.name from OmcCriterion c
        left join VocClassificationCriterion vc on vc.id=c.criterion_id
        where c.medcase_id=${param.id}" />
        <msh:section title="Критерии" createUrl="entityParentPrepareCreate-stac_omcCriterion.do?id=${param.id}">
            <msh:table name="listCrit" action="entityParentView-stac_omcCriterion.do" idField="1" >
                <msh:tableColumn columnName="Название" property="2" />
            </msh:table>
            </msh:section>

    </tiles:put>
    <tiles:put name="side" type="string">


    </tiles:put>
</tiles:insert>

