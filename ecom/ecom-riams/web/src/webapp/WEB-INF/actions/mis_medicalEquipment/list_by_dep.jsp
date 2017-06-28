<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="javax.naming.Context"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
<%ActionUtil.getValueBySql("select ml.id , ml.name from workfunction wf left join worker w on w.id=wf.worker_id left join mislpu ml on ml.id = w.lpu_id where wf.secuser_id="+request.getAttribute("secUserId")
	, "depId", "depName", request);
String sqlAdd = "";
String filter = request.getParameter("filter");

if (filter!=null&&filter.equals("empty")) {
	sqlAdd = " and e.typeEquip_id is null";
} else if (filter!=null&&filter.equals("notEmpty")) {
	sqlAdd = " and e.typeEquip_id is not null";
}
request.setAttribute("sqlAdd", sqlAdd);
%>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Список оборудования</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string' >
<msh:section>
<tags:equipment_changeType name="ChangeMe" />
<input type="button"  onclick="document.location='js-mis_medicalEquipment-listByDep.do?filter=empty'" value="Отобразить без проставленного типа">
<input type="button"  onclick="document.location='js-mis_medicalEquipment-listByDep.do?filter=notEmpty'" value="Отобразить только с проставленнными типами">
<input type="button"  onclick="document.location='js-mis_medicalEquipment-listByDep.do'" value="Отобразить все">
<input type="button"  onclick="document.location='js-mis_lpu-addOtherEquipment.do?id=${depId}'" value="Добавить оборудование из других отделений">
<msh:sectionTitle>Список всего оборудования по отделению ${depName}</msh:sectionTitle>
<msh:sectionContent>
<ecom:webQuery name="list"  nameFldSql="list_sql" nativeSql="select e.id, e.name as name, e.inventoryNumber as invNumber, vte.name as type from equipment e
left join voctypeequip vte on vte.id = e.typeequip_id
left join mislpu lpu on lpu.id=e.lpu_id
where e.dtype='MedicalEquipment' and e.lpu_id=${depId} ${sqlAdd}
order by e.name, e.inventoryNumber"/>
<msh:table name="list" action="entityEdit-mis_medicalEquipment.do" idField="1">
            <msh:tableColumn columnName="Оборудование" property="2"/>
            <msh:tableColumn columnName="Инв. номер" property="3"/>
            <msh:tableColumn columnName="Вид оборудования" property="4"/>
            <msh:tableButton property="1" buttonFunction="showChangeMeDuplicateDocument" addParam="0);this.style.display='none';javascript:void(0" buttonShortName="Изменить тип"/>
        </msh:table>
</msh:sectionContent>
</msh:section>

        
    </tiles:put>
<tiles:put name="javascript" type="string">
<script type="text/javascript" src="./dwr/EquipmentService.js"></script>
<script type="text/javascript" >

</script>
</tiles:put>
</tiles:insert>