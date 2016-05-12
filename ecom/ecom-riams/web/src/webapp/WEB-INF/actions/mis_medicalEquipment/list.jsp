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
            <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>
            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-mis_medicalEquipment" name="Добавить мед. оборудование" />
            <msh:sideLink key='ALT+M' params="id" action="/js-mis_lpu-addOtherEquipment" name="Добавить оборудование из других отделений" />
            
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
<msh:section>
<msh:sectionTitle>Список оборудования по ЛПУ</msh:sectionTitle>
<msh:sectionContent>
<ecom:webQuery name="list" nativeSql="select id, name, inventoryNumber from equipment where dtype='MedicalEquipment' and lpu_id=${param.id}
order by name, inventoryNumber"/>
<msh:table name="list" action="entityParentView-mis_medicalEquipment.do" idField="1">
            <msh:tableColumn columnName="Оборудование" property="2"/>
            <msh:tableColumn columnName="Инв. номер" property="3"/>
        </msh:table>
</msh:sectionContent>
</msh:section>
<msh:section>
 <msh:sectionTitle>Используемое оборудование из других отделений</msh:sectionTitle>
<msh:sectionContent>
<ecom:webQuery name="otherList" nativeSql="select e.id, e.name, e.inventoryNumber from equipment_mislpu el
left join equipment e on e.id=el.equipment_id
where e.dtype='MedicalEquipment' and el.otherlpu_id=${param.id}
order by e.name, e.inventoryNumber"/>
<msh:table name="otherList" action="entityParentView-mis_medicalEquipment.do" idField="1">
            <msh:tableColumn columnName="Оборудование" property="2"/>
            <msh:tableColumn columnName="Инв. номер" property="3"/>
        </msh:table>
</msh:sectionContent>
</msh:section>
        </tiles:put>

</tiles:insert>