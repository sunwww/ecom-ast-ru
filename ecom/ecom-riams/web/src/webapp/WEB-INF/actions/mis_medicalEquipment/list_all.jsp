<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Список оборудования</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string' >
<msh:section>
<msh:sectionTitle>Список всего оборудования</msh:sectionTitle>
<msh:sectionContent>
<ecom:webQuery name="list" nativeSql="select e.id, e.name, e.inventoryNumber, lpu.name from equipment e
left join mislpu lpu on lpu.id=e.lpu_id
where dtype='MedicalEquipment'
order by e.name"/>
<msh:table name="list" action="entityView-mis_medicalEquipment.do" idField="1">
            <msh:tableColumn columnName="Оборудование" property="2"/>
            <msh:tableColumn columnName="Инв. номер" property="3"/>
        </msh:table>
</msh:sectionContent>
</msh:section>

        
    </tiles:put>

</tiles:insert>