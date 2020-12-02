<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
    </tiles:put>
    <tiles:put name="side" type="string"></tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/expert_listBySls.do" defaultField="beginDate" method="GET"></msh:form>
        <msh:section>
            <msh:sectionTitle>Направления на ВК</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery name="list"
                               nativeSql="select c.id,c.orderDate from clinicexpertcard c
                       left join medcase slo on slo.id=c.medcase_id
                       where slo.parent_id=${param.id}"/>
                <msh:table name="list" action="entityParentView-expert_ker.do" idField="1" noDataMessage="Нет данных">
                    <msh:tableColumn columnName="ИД" property="1" />
                    <msh:tableColumn columnName="Дата направления" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
    </tiles:put>
</tiles:insert>