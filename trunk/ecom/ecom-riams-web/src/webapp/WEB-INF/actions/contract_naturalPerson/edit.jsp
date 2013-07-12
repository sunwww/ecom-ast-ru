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
          <msh:autoComplete property="patient" label="Пациент" vocName="patient" horizontalFill="true" size="150" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
      
      <msh:section createRoles="/Policy/Mis/Contract/MedContract/Create" createUrl="entityParentPrepareCreate-contract_medContract_person.do?id=${param.id}" 
      	shortList="js-contract_medContract-list_by_customer.do?short=Short&id=${param.id}" title="Список последних 10 договоров заказчика">
      	<ecom:webQuery name="medContracts" nativeSql="
      	select mc.id as mcid ,ca.id||' '||case when sp.id=mc.customer_id then '' else cp.id||coalesce(cpp.lastname,'')  end
,mc.dateFrom as mcdateFrom 
,mc.dateTo as mcdateTo,pl.name as plname 
from MedContract mc 
left join ServedPerson sp on mc.id=contract_id left join ContractPerson cp on cp.id=sp.person_id 
left join Patient cpp on cpp.id=cp.patient_id left join ContractAccount ca on ca.servedPerson_id=sp.id 
left join PriceList pl on pl.id=mc.priceList_id 
where mc.customer_id='${param.id}' 
order by mc.dateFrom desc
      	" maxResult="10"/>
      	<msh:table name="medContracts" viewUrl="entityView-contract_medContract.do?short=Short" action="entityView-contract_medContract.do" idField="1">
      		<msh:tableColumn property="2" columnName="№счета по обслуживаемой персоне"/>
      		<msh:tableColumn property="3" columnName="Дата начала"/>
      		<msh:tableColumn property="4" columnName="Дата окончания"/>
      		<msh:tableColumn property="5" columnName="Прейкурант"/>
      		<msh:tableColumn property="5" columnName="Прейкурант"/>
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
      <msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-contract_naturalPerson" name="Удалить" title="Удалить" roles="" guid="91812d50-84d8-428a-a624-d1c59d868dfa" />
    </msh:sideMenu>
    <tags:contractMenu currentAction="naturalPerson" />
  </tiles:put>
</tiles:insert>

