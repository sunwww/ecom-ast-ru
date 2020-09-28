<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" title="Лекарственное средство" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" roles="/Policy/Mis/Prescription/DrugPrescription/Create" key="ALT+N" action="/entityParentPrepareCreate-pres_drugPrescription" name="Назначение лекарства" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать" >
      <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentList-pres_prescription" name="К списку назначений" title="Список назначений" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid,pl.id as plid,dr.name as drname
    	,p.planStartDate,p.planStartTime
	,p.planEndDate,p.planEndTime,vdm.name as vdmname
	, p.frequency ||' '||coalesce(cast(p.frequencyUnit_id as varchar),vfu.name,'') as pfrec
	, p.orderTime ||' '||coalesce(cast(p.orderType_id as varchar),vpot.name,'') as pord
	, p.amount ||' '||coalesce(cast(p.amountUnit_id as varchar),vdau.name,'') as pam
	, p.duration ||' '||coalesce(cast(p.durationUnit_id as varchar),vdu.name,'') as pdur
	from Prescription p
	left join PrescriptionList pl on pl.id=p.prescriptionList_id
	left join vocdrug as dr on dr.id=p.vocDrug_id
	left join vocdrugmethod as vdm on vdm.id=p.method_id
	left join vocfrequencyunit as vfu on vfu.id=p.frequencyunit_id
	left join vocPrescriptOrderType as vpot on vpot.id=p.orderType_id
	left join vocDrugAmountUnit as vdau on vdau.id=p.amountUnit_id
	left join vocDurationUnit as vdu on vdu.id=p.durationUnit_id
	where pl.id=${param.id} and p.DTYPE='DrugPrescription' order by p.planStartDate"/>
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

