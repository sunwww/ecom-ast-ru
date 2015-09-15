<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-mis_medicalEquipment.do" defaultField="typeEquipName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpu"/>
            <msh:panel>
             <msh:row>
                    <msh:textField property="groupName" label='Группа бухгалтерии' horizontalFill="true" fieldColSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="lpu" label='Подразделение' vocName="vocLpuHospOtdAll" horizontalFill="true" fieldColSpan="4"/>
                </msh:row>
                <msh:row> 
                <msh:row>
                	<msh:textField property="name" label="Название" size="100" />
                </msh:row>
                <msh:row>
               <msh:textField property="inventoryNumber" label="Инвентарный номер" size="50"/>
               </msh:row>
               <msh:row>
                    <msh:autoComplete property="typeEquip" label='Тип' vocName="vocTypeEquip" horizontalFill="true" fieldColSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="marka" label='Марка' vocName="vocMarka" horizontalFill="true" fieldColSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="createYear" label="Год выпуска" size="50"/>
                </msh:row>
                <msh:row>    
                    <msh:textField property='startDate' label='Дата ввода в эксплуатацию' size="50" />
                </msh:row>
                <msh:row>
               <msh:textField property="price" label="Цена" size="50"/>
               </msh:row>
               <msh:row>
               <msh:textField property="startWearout" label="Начальный износ" size="50" />
               </msh:row>
               <msh:row>
               <msh:textField property="wearout" label="Износ" size="50"/>
               </msh:row>
               <msh:row>
               <msh:textField property="residualValue" label="Остаточная стоимость" size="50"/>
               </msh:row>
               <msh:ifFormTypeIsView formName="mis_medicalEquipmentForm">
               <msh:row>
               <td colspan="1" align="right" >
               <label>Износ (%)</label>
               </td>
               <td colspan="3">
               <input readonly="readonly" type='text' name= "wearoutPercent" id="wearoutPercent" value="" size="50">
               </td>
               </msh:row>
               </msh:ifFormTypeIsView>
               <msh:textField property="projectName" label="Программа (мероприятие)" size="50"/>
               </msh:row>
                <msh:row>
                    <msh:autoComplete property="creater" label="Производитель" vocName="vocCreater"/>
                    </msh:row><msh:row>
                    <msh:autoComplete property="provider" label="Поставщик" vocName="vocProvider"/>
                </msh:row>
                <msh:row>
                   <msh:textArea property="info" label="Примечание" fieldColSpan="3"/>
                </msh:row>
                <msh:ifFormTypeIsView formName="mis_medicalEquipmentForm">
				<msh:row>
			<msh:section>
			<msh:sectionTitle>Оборудование используется в других отделениях</msh:sectionTitle>
			<msh:sectionContent>
			<ecom:webQuery name="list" nativeSql="select lpu.id, lpu.name from equipment_mislpu el 
			left join mislpu lpu on lpu.id=el.otherlpu_id and el.equipment_id=${param.id}
			order by lpu.name"/>
			<msh:table name="list" action="entityView-mis_lpu.do" idField="1">
			            <msh:tableColumn columnName="Подразделение" property="2"/>
			            </msh:table>
			</msh:sectionContent>
			</msh:section>
		</msh:row>
	</msh:ifFormTypeIsView>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_medicalEquipmentForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_medicalEquipment" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_medicalEquipmentForm">
                <msh:sideLink key='ALT+DEL' params="id" action="entityParentDeleteGoParentView-mis_medicalEquipment" name="Удалить" confirm="Удалить оборудование?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_medicalEquipmentForm" />
    </tiles:put>
<tiles:put name="javascript" type="string">
      <script type="text/javascript">
      onload=function() {
    	  alert("HELLO");
    	  var val = (+$('wearout').value) /(+$('price').value)*100;
    	  $('wearoutPercent').value=""+(val.toFixed(2))+" %";
      }
      </script>
      </tiles:put>

</tiles:insert>
