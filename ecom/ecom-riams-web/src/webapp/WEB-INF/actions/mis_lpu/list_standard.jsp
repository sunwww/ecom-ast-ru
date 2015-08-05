<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Оснащение по стандарту" guid="e51b1bad-82ba-4906-9829-7d9148b1174a" />
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>
        </msh:sideMenu>
        <msh:sideMenu title="Перейти">
	      <msh:sideLink key="ALT+1" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" roles="/Policy/Mis/WorkPlace/View"/>
        <msh:sideLink roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment" action="/entityList-mis_copyingEquipment" name="Копировальное оборудование" title="Показать сведения по копировальному оборудованию" guid="27fe8bc3-ae8d-4e8b-88f2-d23a337f614b" />
        <msh:sideLink roles="/Policy/Mis/Voc/VocTypeEquip" params="id" action="/js-mis_lpu-addOtherEquipment" name="Добавить аренд. оборудование" title="Добавить аренд. оборудование" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
    <ecom:webQuery name="list" nativeSql=" select vte.name, msp.amount, count(e.id), msp.amount-count(e.id) as diff
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
            <msh:tableColumn columnName="Сколько не хватает" property="4" />
        </msh:table>
    </tiles:put>

</tiles:insert>