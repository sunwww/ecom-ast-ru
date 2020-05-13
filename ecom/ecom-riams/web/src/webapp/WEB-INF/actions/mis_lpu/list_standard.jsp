<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Оснащение по стандарту" />
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>
        </msh:sideMenu>
        <msh:sideMenu title="Перейти">
	      <msh:sideLink key="ALT+1" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" roles="/Policy/Mis/WorkPlace/View"/>
        <msh:sideLink roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment" action="/entityList-mis_copyingEquipment" name="Копировальное оборудование" title="Показать сведения по копировальному оборудованию" />
        <msh:sideLink roles="/Policy/Mis/Voc/VocTypeEquip" params="id" action="/js-mis_lpu-addOtherEquipment" name="Добавить аренд. оборудование" title="Добавить аренд. оборудование" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
    <ecom:webQuery name="list" nativeSql=" select vte.name
    , max(case when msp.amount>0 then ''||msp.amount when msp.comment is not null then msp.comment else ''||msp.amount end) as amount
    , cast( sum (case when e.amount>0 then e.amount when e.id is not null then 1 else 0 end) +sum (case when e1.amount>0 then e1.amount when e1.id is not null then 1 else 0 end)  as varchar) as sum1
	,cast(msp.amount-(sum (case when e.amount>0 then e.amount when e.id is not null then 1 else 0 end) +sum (case when e1.amount>0 then e1.amount when e1.id is not null then 1 else 0 end) )as varchar)  as diff

    from mislpu lpu
    left join medicalequipmentposition msp on msp.standard_id=lpu.medicalstandard_id
    left join voctypeequip vte on vte.id=msp.equipmenttype_id
    left join equipment e on e.typeequip_id = msp.equipmenttype_id and e.lpu_id=lpu.id 
    left join equipment_mislpu el on el.otherlpu_id=lpu.id
    left join equipment e1 on e1.id=el.equipment_id and e1.typeequip_id=msp.equipmenttype_id
    where lpu.id=${param.id} 
    group by vte.name, msp.amount
    "></ecom:webQuery>
    
        <msh:table printToExcelButton="Сохранить в excel" name="list" action="/javascript:void()" idField="1">
            <msh:tableColumn columnName="Тип оборудования" property="1" />
            <msh:tableColumn columnName="Требуемое кол-во" property="2" />
            <msh:tableColumn columnName="Кол-во по факту" property="3" />
            <msh:tableColumn columnName="Сколько не хватает" property="4" />
        </msh:table>
    </tiles:put>

</tiles:insert>