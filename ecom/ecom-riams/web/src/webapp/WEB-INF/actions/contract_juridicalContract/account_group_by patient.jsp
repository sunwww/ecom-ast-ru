<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
			<msh:section title="Медицинские услуги с групприровкой по пациентам."
			createRoles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/JuridicalMedService/Create" 
			createUrl="entityParentPrepareCreate-contract_juridicalAccountMedService.do?id=${param.id}"
			>
			<msh:sectionTitle> 
			<msh:link action="js-contract_juridicalContract-account_view.do?id=${param.id}">Перейти к полному списку</msh:link>
			<msh:link action="js-contract_juridicalContract-account_print.do?id=${param.id}">Печать</msh:link>
			</msh:sectionTitle>
			<msh:sectionContent>
			<ecom:webQuery name="medicalService" nativeSql="
select cams.mainparent,cams.lastname,cams.firstname,cams.middlename
,to_char(cams.birthday,'dd.mm.yyyy') as birthday
,sum(cams.countMedService) as countmedservice
	, sum(cams.countMedService*round((cams.cost*(100-coalesce(mc.discountDefault,'0'))/100),0)) as sumNoAccraulMedService
	,cg.LimitMoney
	,cams.polnumber
	,list(''||cams.id) as f10_allIds
			from ContractAccountMedService cams
			left join medservice ms on ms.id=cams.serviceIn
			left join contractaccount ca on ca.id=cams.account_id
			left join medcontract mc on mc.id=ca.contract_id
			left join WorkFunction wf on wf.id=cams.doctor
			left join worker w on w.id=wf.worker_id
			left join patient wp on wp.id=w.person_id
			left join PriceMedService pms on pms.id=cams.medService_id
			left join PricePosition pp on pp.id=pms.pricePosition_id
			left join ContractPerson cp on cp.id=mc.customer_id 
			 left join VocJuridicalPerson vjp on vjp.id=cp.juridicalPersonType_id
			left join patient p on p.id=cp.patient_id
    			left join MisLpu lpu on lpu.id=cp.lpu_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join ContractGuarantee cg on cg.id=cams.Guarantee_id
			where cams.account_id='${param.id}'
			and (cams.isDelete='0' or cams.isDelete is null)
			group by cams.mainparent,cams.lastname,cams.firstname,cams.middlename,cams.birthday,cg.LimitMoney,cams.polnumber
			order by cams.lastname,cams.firstname,cams.middlename
			"/>
				
				<msh:table name="medicalService" 
				action="js-contract_juridicalContract-account_view_by_patient.do"
				viewUrl="js-contract_juridicalContract-account_view_by_patient.do?short=Short"
				 idField="1">
				 <msh:tableColumn property="sn" columnName="#"/>
				 <msh:tableButton property="10" buttonShortName="Удалить" buttonFunction="deleteCAMS"/>
					<msh:tableColumn columnName="Фамилия" property="2" />
					<msh:tableColumn columnName="Имя" property="3" />
					<msh:tableColumn columnName="Отчество" property="4" />
					<msh:tableColumn columnName="Дата рождения" property="5" />
					<msh:tableColumn columnName="Полис" property="9" />
					<msh:tableColumn columnName="Общ. кол-во" property="6" />
					<msh:tableColumn columnName="Стоимость" isCalcAmount="true" property="7" />				
					<msh:tableColumn columnName="Лимит по гарант. письму" isCalcAmount="true" property="8" />				
				</msh:table>
				</msh:sectionContent>
			</msh:section>
			

	</tiles:put>
	
	    <tiles:put name="javascript" type="string">
	    <script type='text/javascript' src='./dwr/interface/ContractService.js'></script>
	     <script type="text/javascript">
	     function deleteCAMS(ids) {
	    	 if (confirm("Удалить все записи по группе?")) {
	    		 
	    		 ContractService.deleteCAMS(ids, {
		    		 callback: function (a) {
		    			 alert (a);
		    			 window.document.location.reload();
		    			 
		    		 }
		    	 }); 
	    	 }
	    	 
	     }
	     </script>
	    </tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_accountForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
	</tiles:put>
</tiles:insert>
