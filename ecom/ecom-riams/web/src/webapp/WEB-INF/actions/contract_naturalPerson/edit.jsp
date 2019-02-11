<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-contract_naturalPerson.do" defaultField="patientName" guid="6f65ff8f-8528-410f-96ac-cc08872b5901">
      <msh:hidden property="id" guid="e6bcfe59-6541-4e49-afb8-15a0f7059230" />
      <msh:hidden property="saveType" guid="81d410cd-ac37-4309-9fe7-a8d0943c5ae1" />
      <msh:panel guid="41b857b2-e843-401e-8a0f-33f2fbd66417">
        <msh:row guid="b18bbff8-d1ea-4681-bd11-5a712a9fac54">
          <msh:autoComplete property="patient" 
          viewAction="entityView-mis_patient.do" shortViewAction="entityShortView-mis_patient.do"
          label="Пациент" vocName="patient" horizontalFill="true" size="150" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
      
      <msh:section createRoles="/Policy/Mis/Contract/MedContract/Create" createUrl="entityParentPrepareCreate-contract_medContract_person.do?id=${param.id}" 
      	shortList="js-contract_medContract-list_accrual_by_customer.do?short=Short&id=${param.id}" title="Список последних 10 договоров заказчика">
      	<ecom:webQuery name="medContracts" nativeSql="
      	select mc.id as mcid ,mc.contractNumber as mccontractNumber
,mc.dateFrom as mcdateFrom 
,mc.dateTo as mcdateTo,pl.name as plname 
,(select sum(ca.balanceSum)
			from ContractAccount ca
			where ca.contract_id=mc.id) as sumbalance
,max (case when mc.customer_id='${param.id}' then 'Заказчик' when sp1.person_id='${param.id}' then 'Обслуживаемая персона' else 'Неопределено' end) as f7_personType
from MedContract mc 
left join ServedPerson sp on mc.id=contract_id left join ContractPerson cp on cp.id=sp.person_id 
left join Patient cpp on cpp.id=cp.patient_id left join ContractAccount ca on ca.contract_id=mc.id
left join contractAccountMedservice cams on cams.account_id=ca.id
left join servedPerson sp1 on sp1.id = cams.servedperson_id
left join PriceList pl on pl.id=mc.priceList_id 
where (mc.customer_id='${param.id}' or sp1.person_id='${param.id}') and (mc.isDeleted is null or mc.isDeleted='0')
group by mc.id,mc.dateFrom,mc.dateTo,mc.contractNumber,pl.name 
order by mc.dateFrom desc
      	" maxResult="10"/>
      	<msh:table name="medContracts" viewUrl="entityView-contract_medContract.do?short=Short" action="entityView-contract_medContract.do" idField="1">
      		<msh:tableColumn property="7" columnName="Роль персоны"/>
      		<msh:tableColumn property="2" columnName="№ договора"/>
      		<msh:tableColumn property="3" columnName="Дата начала"/>
      		<msh:tableColumn property="4" columnName="Дата окончания"/>
      		<msh:tableColumn property="6" columnName="Оплачено по договору"/>
      		<msh:tableColumn property="5" columnName="Прейкурант"/>
      	</msh:table>
      	      	
      </msh:section>
      			<ecom:webQuery name="lastVisit1" nativeSql="select 
    	m.id,m.dateStart as dateFrom
    	,coalesce(vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
    	,vwf1.name||' '||wp1.lastname||' '||wp1.firstname||' '||wp1.middlename) as worker
    	from medCase m
    	left join VocServiceStream vss on vss.id=m.serviceStream_id
    	left join ContractPerson cp on cp.patient_id=m.patient_id
    	left join workfunction wf on wf.id=m.workFunctionExecute_id
    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id
    	left join workfunction wf1 on wf1.id=m.workFunctionPlan_id
    	left join vocworkfunction vwf1 on vwf1.id=wf1.workFunction_id
    	left join WorkCalendarDay wcd on wcd.id=m.datePlan_id
    	left join worker w on w.id=wf.worker_id
    	left join patient wp on wp.id=w.person_id
    	left join worker w1 on w1.id=wf1.worker_id
    	left join patient wp1 on wp1.id=w1.person_id
    	where cp.id=${param.id} and (m.DTYPE='Visit' or m.dtype='ShortMedCase')
    	and m.dateStart is not null
    	and vss.code='CHARGED'
    	order by m.dateStart desc
    	" maxResult="5" />
     <msh:section title="Последнее посещение <a href='print-begunok.do?s=SmoVisitService&amp;m=printDirectionByPatient&patientId=${param.id}' target='_blank'>бегунок</a>" 
     viewRoles="/Policy/Mis/MedCase/Direction/View" shortList="js-mis_patient-viewDirection.do?id=${param.id}">
    	<msh:table name="lastVisit1" action="entitySubclassView-mis_medCase.do" idField="1">
	    	<msh:tableColumn property="2" columnName="Дата"/>
    		<msh:tableColumn property="3" columnName="Специалист"/>
    	</msh:table>
     </msh:section>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Contract" beginForm="contract_naturalPersonForm"  />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="dea2e8ca-c7db-4592-a9ec-727f6d330ad1">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-contract_naturalPerson" name="Изменить" title="Изменить" roles="" guid="6050d629-20dd-4945-83f5-f0821ad67497" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-contract_naturalPerson" name="Удалить" title="Удалить" roles="" guid="91812d50-84d8-428a-a624-d1c59d868dfa" />
    </msh:sideMenu>
    <tags:contractMenu currentAction="naturalPerson" />
  </tiles:put>
</tiles:insert>

