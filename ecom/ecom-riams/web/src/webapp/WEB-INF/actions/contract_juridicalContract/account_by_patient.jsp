<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
				<% if (request.getParameter("short")!=null) {%>
  <input type="button" onclick="showACCOUNTGetAccount()" value="ОБНОВИТЬ ПО ПЕРВИЧНОЙ ИНФОРМАЦИИ"/>
  <input type="button" onclick="getDefinition('js-contract_juridicalContract-account_view_by_patient.do?short=Short&id=${param.id}','.do')" value="ОБНОВИТЬ"/>
			<%} %>
	
			<msh:section title="Услуги по пациенту" createUrl="js-smo_medService-add.do?id=${param.id}" createRoles="/Policy/Mis/MedCase/MedService/Create">
			
			<msh:sectionContent>
			<ecom:webQuery name="medicalService" nativeSql="
select cams.mainparent,cams.lastname||' '||cams.firstname||' '||cams.middlename
,to_char(cams.birthday,'dd.mm.yyyy') as birthday
				,pp.code as ppcode
, pp.name as ppinfo
				,mkb.code as mkbcode
,cams.cost,cams.countMedService 
	, cams.countMedService*cams.cost as sumNoAccraulMedService 
	,wp.lastname||' '||substring(wp.firstname,1,1)||' '||substring(wp.firstname,1,1) as wpinfo
	,cams.isDeath
	,ca.accountNumber ||' '||CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: ' ||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY')
				when cp.dtype='JuridicalPerson' 
				  then coalesce(vjp.name||' ','')||case   
					when reg.id is not null then 'Страх.комп. '
					when lpu.id is not null then 'ЛПУ '
					when vjp.code='SILOVIK' then 'Сил. стр-ра:'
					else 'Юрид.лицо: ' end ||cp.name
				END as account
				,cams.Guarantee_id
				,ms.code||' '||ms.name as msname
				,'js-contract_juridicalContract-account_group_by_patient.do?short=Short&id='||ca.id as f14sls
				,case when '${param.short}'!='Short' then cams.id else null end as f15
				,cams.datefrom, cams.dateto
			from ContractAccountMedService cams
			left join medservice ms on ms.id=cams.serviceIn
			left join contractaccount ca on ca.id=cams.account_id
			left join WorkFunction wf on wf.id=cams.doctor
			left join worker w on w.id=wf.worker_id
			left join patient wp on wp.id=w.person_id
			left join PriceMedService pms on pms.id=cams.medService_id
			left join PricePosition pp on pp.id=pms.pricePosition_id
			left join MedContract mc on mc.id=ca.contract_id
			left join ContractPerson cp on cp.id=mc.customer_id 
			left join VocJuridicalPerson vjp on vjp.id=cp.juridicalPersonType_id
			left join patient p on p.id=cp.patient_id
    		left join MisLpu lpu on lpu.id=cp.lpu_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join VocIdc10 mkb on mkb.id=cams.diagnosis
			where cams.mainparent='${param.id}'
			order by cams.datefrom,wp.lastname,pp.code
			"/>
				
				<msh:table name="medicalService" 
				action="javascript:void(0)"
				
				 idField="1">
				 	<msh:tableColumn property="sn" columnName="#"/>
	      <msh:tableButton property="15" buttonFunction="getDefinition" buttonName="Просмотр данных о счете" buttonShortName="С" hideIfEmpty="true" role="/Policy/Mis/Patient/View"
	      />
	      <msh:tableButton property="16" buttonFunction="getDefinition" buttonName="Просмотр данных о счете" buttonShortName="С" hideIfEmpty="true" role="/Policy/Mis/Patient/View"
	      />
					<msh:tableColumn columnName="Счет" property="12" />
					<msh:tableColumn columnName="Гаран. документ" property="13" />
					<msh:tableColumn columnName="Фамилия" property="2" />
					<msh:tableColumn columnName="Дата рождения" property="3" />
					<msh:tableColumn columnName="Дата c" property="17" />
					<msh:tableColumn columnName="Дата по" property="18" />
					
					<msh:tableColumn columnName="Код" property="4" />
					<msh:tableColumn columnName="Наименование" property="5" />
					<msh:tableColumn columnName="Диагноз" property="6" />
					<msh:tableColumn columnName="Тариф" property="7" />
					<msh:tableColumn columnName="Общ. кол-во" property="8" isCalcAmount="true"/>
					<msh:tableColumn columnName="Стоимость" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Специалист" property="10" />
					<msh:tableColumn columnName="Летальный исход" property="11" />
					<msh:tableColumn columnName="Внут. услуга" property="14" />
					
				</msh:table>
				</msh:sectionContent>
			</msh:section>
			

	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="mis_medCaseForm" title="Список услуг по счету" />
	</tiles:put>
	<tiles:put name="side" type="string">
	</tiles:put>
</tiles:insert>