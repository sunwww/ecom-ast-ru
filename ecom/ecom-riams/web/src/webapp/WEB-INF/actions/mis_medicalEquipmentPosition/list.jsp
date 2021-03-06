<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Список оборудования</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-mis_medicalEquipmentPosition" name="Добавить оборудование" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
<msh:section>
<msh:sectionContent>
<ecom:webQuery name="list" nativeSql="select e.id, e.name, vet.name as vname from medicalEquipmentPosition mep
left join equipment e on e.id=mep.equipment_id
left join vocequiptype vet on vet.id=e.typeEquip order by e.name"/>
<msh:table name="list" action="entityView-mis_medicalEquipment.do" idField="1">
            <msh:tableColumn columnName="Название" property="2"/>
            <msh:tableColumn columnName="Тип" property="3"/>
           </msh:table>
</msh:sectionContent>
</msh:section>
        </tiles:put>

</tiles:insert>