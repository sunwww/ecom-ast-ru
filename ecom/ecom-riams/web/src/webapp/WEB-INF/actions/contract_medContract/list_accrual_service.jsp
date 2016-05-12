<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService/View">
			<msh:section >
			<msh:sectionTitle>Медицинские услуги</msh:sectionTitle>
			<msh:sectionContent>
			<ecom:webQuery name="medicalService" nativeSql="
select cams.id, pp.code,pp.name,cams.cost,cams.countMedService 
	, cams.countMedService*cams.cost as sumNoAccraulMedService 
	, cao.discount,round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2)
			from ContractAccountMedService cams
			left join ServedPerson sp on cams.servedPerson_id = sp.id
			left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id
			left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'
			left join ContractAccount ca on ca.id=cao.account_id
			left join ContractPerson cp on cp.id=sp.person_id 
			left join patient p on p.id=cp.patient_id
						
			left join PriceMedService pms on pms.id=cams.medService_id
			left join PricePosition pp on pp.id=pms.pricePosition_id
			where ca.contract_id='${param.id}'
			and cao.id is not null
			"/>
				
				<msh:table selection="multy"  name="medicalService" 
				deleteUrl="entityParentDeleteGoParentView-contract_accountMedService.do"
				editUrl="entityParentEdit-contract_accountMedService.do"
				viewUrl="entityShortView-contract_accountMedService.do"
				action="entityParentView-contract_accountMedService.do"
				
				 idField="1">
					<msh:tableColumn columnName="Код" property="2" />
					<msh:tableColumn columnName="Наименование" property="3" />
					<msh:tableColumn columnName="Тариф" property="4" />
					<msh:tableColumn columnName="Общ. кол-во" property="5" />
					<msh:tableColumn columnName="Стоимость" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Скидка" property="7" />
					<msh:tableColumn columnName="Оплачено" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Возрат, кол-во" property="9" />
					<msh:tableColumn columnName="Возрат, руб" property="10" isCalcAmount="true" />
					<msh:tableColumn columnName="Итог" isCalcAmount="true" property="11" />
					
				</msh:table>
				</msh:sectionContent>
			</msh:section>
			</msh:ifInRole>
			

	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_medContractForm" title="Список оплаченных услуг"/>
	</tiles:put>
	<tiles:put name="side" type="string">
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
</tiles:insert>
