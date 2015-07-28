<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ЛПУ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' roles="/Policy/Mis/MisLpu/Create" params="" action="/entityPrepareCreate-mis_lpu" name="Добавить ЛПУ" />

        </msh:sideMenu>
        <msh:sideMenu title="Перейти">
	      <msh:sideLink key="ALT+1" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" roles="/Policy/Mis/WorkPlace/View"/>
        <msh:sideLink roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment" action="/entityList-mis_copyingEquipment" name="Копировальное оборудование" title="Показать сведения по копировальному оборудованию" guid="27fe8bc3-ae8d-4e8b-88f2-d23a337f614b" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
    <ecom:webQuery name="list" nativeSql=" select vte.name, msp.amount, count(e.id)
    from mislpu lpu
    left join medicalequipmentposition msp on msp.standard_id=lpu.medicalstandard_id
    left join voctypeequip vte on vte.id=msp.equipmenttype_id
    left join equipment e on e.typeequip_id = msp.equipmenttype_id 
    where lpu.id=${param.id}
    group by vte.name, msp.amount
    "></ecom:webQuery>
    
        <msh:table name="list" action="/javascript:void()" idField="1">
            <msh:tableColumn columnName="Тип оборудования" property="1" />
            <msh:tableColumn columnName="Требуемое кол-во" property="2" />
            <msh:tableColumn columnName="Кол-во по факту" property="3" />
        </msh:table>
    </tiles:put>

</tiles:insert>