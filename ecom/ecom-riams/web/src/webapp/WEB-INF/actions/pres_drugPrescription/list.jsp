<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" title="Лекарственное средство" guid="610fe86e-69f6-4ad0-a1dd-14423082c0c3" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="Добавить" title="Добавить">
      <msh:sideLink guid="helloSideLinkNew" params="id" roles="/Policy/Mis/Prescription/DrugPrescription/Create" key="ALT+N" action="/entityParentPrepareCreate-pres_drugPrescription" name="Назначение лекарства" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать" guid="7fd7a5d3-45f2-4551-a7e3-01528a0a9c00" >
      <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentList-pres_prescription" name="К списку назначений" title="Список назначений" guid="3jg7-f85c-4e87-b447-419887e" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid,pl.id as plid,dr.name as drname,p.planStartDate,p.planStartTime,p.planEndDate,p.planEndTime,vdm.name as vdmname , p.frequency ||' '||ifnull(p.frequencyUnit_id,'',vfu.name), p.orderTime ||' '||ifnull(p.orderType_id,'',vpot.name), p.amount ||' '||ifnull(p.amountUnit_id,'',vdau.name), p.duration ||' '||ifnull(p.durationUnit_id,'',vdu.name) from Prescription p left join PrescriptionList pl on pl.id=p.prescriptionList_id left join vocdrugclassify as dr on dr.id=p.drug_id left join vocdrugmethod as vdm on vdm.id=p.method_id left join vocfrequencyunit as vfu on vfu.id=p.frequencyunit_id left join vocPrescriptOrderType as vpot on vpot.id=p.orderType_id left join vocDrugAmountUnit as vdau on vdau.id=p.amountUnit_id left join vocDurationUnit as vdu on vdu.id=p.durationUnit_id where pl.id=${param.id} and p.DTYPE='DrugPrescription' order by p.planStartDate"/>
  	<msh:tableNotEmpty name="pres">
  	<msh:toolbar >
	                	<tbody>
	                		<tr>
	                			<th class='linkButtons' colspan="6">
	                				<msh:toolbar>
	                					<input type='button' value='Изменить начало назначений' onclick="javascript:updatePlanStartDate()" />
	                					<input type='button' value='Изменить окончание назначений' onclick="javascript:updatePlanEndDate()" />
	                					<input type='button' value='Удалить назначения' onclick="javascript:deletePrescription()" />	                					
	                				</msh:toolbar>
	                			</th>
	                		</tr>
	                		<tr>
	                			<th class='linkButtons' colspan="6">
	                				<msh:toolbar>
	                					<msh:textField property="planDate" label="Дата"/>
	                					<msh:textField property="planTime" label="Время"/>
	                				</msh:toolbar>
	                			</th>
	                		</tr>
	                	</tbody>
  	</msh:toolbar>
  	</msh:tableNotEmpty>
    <msh:section>
    	<msh:sectionTitle>Список лекарственных назначений</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table selection="true" name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Лек.средство"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="6" columnName="Дата окончания"/>
    			<msh:tableColumn property="8" columnName="Способ введения"/>
    			<msh:tableColumn property="9" columnName="Частота"/>
    			<msh:tableColumn property="10" columnName="Время приема"/>
    			<msh:tableColumn property="11" columnName="Кол-во на один прием"/>
    			<msh:tableColumn property="12" columnName="Продолжительность"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
  </tiles:put>
  <tiles:put type="string" name="javascript">
  	<script type="text/javascript">
  		if ($('planDate')) new dateutil.DateField($('planDate')) ;
  		if ($('planTime')) new timeutil.TimeField($('planTime'));
  		function updatePlanStartDate() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids") ;
	  		if (ids) {
  				document.location.href = "js-pres_drugPrescription-updatePlanStartDate.do?id=${param.id}&"+ids+"&plandate="+$('planDate').value+"&plantime="+$('planTime').value;
  			} else {
  				alert("Нет выделенных назначений") ;
  			}
  		}
  		function updatePlanEndDate() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids") ;
	  		if (ids) {
	  			alert( "js-pres_drugPrescription-updatePlanEndDate.do?id=${param.id}&"+ids+"&plandate="+$('planDate').value+"&plantime="+$('planTime').value);
  				document.location.href = "js-pres_drugPrescription-updatePlanEndDate.do?id=${param.id}&"+ids+"&plandate="+$('planDate').value+"&plantime="+$('planTime').value;
  			} else {
  				alert("Нет выделенных назначений") ;
  			}
  		}
  		function deletePrescription() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids") ;
	  		if (ids) {
	  		  			document.location.href = "js-pres_drugPrescription-deletePrescription.do?id=${param.id}&"+ids;
  			} else {
  				alert("Нет выделенных назначений") ;
  			}	  		  			
  		}
  	</script>
  </tiles:put>
</tiles:insert>

