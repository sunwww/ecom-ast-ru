<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_contractAccount.do" defaultField="dateFrom">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="servedPerson" />
			<msh:panel colsWidth="1%,1%,1%">
				<msh:row>
					<msh:textField property="dateFrom" label="Дата открытия"/>
					<msh:textField property="dateTo" label="Дата закрытия"/>
				</msh:row>
				<msh:row>
					<msh:textField property="balanceSum" label="Сумма баланса"/>
					<msh:textField property="reservationSum" label="Резервированная сумма"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="block" label="Блокирован"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_contractAccountForm">
			<msh:section title="Операции по счету">
			<ecom:parentEntityListAll formName="contract_contractAccountOperationForm" attribute="account" />
				<msh:table deleteUrl="entityParentDeleteGoParentView-contract_contractAccountOperation.do" viewUrl="entityShortView-contract_contractAccountOperation.do" editUrl="entityParentEdit-contract_contractAccountOperation.do" name="account" action="entityParentView-contract_contractAccountOperation.do" idField="id">
					<msh:tableColumn columnName="Тип операции" property="typeInfo" />
					<msh:tableColumn columnName="Дата" property="operationDate" />
					<msh:tableColumn columnName="Время операции" property="operationTime" />
					<msh:tableColumn columnName="Стоимость" property="cost" />
					<msh:tableColumn columnName="Случай медицинского обслуживания" property="medcaseInfo" />
					<msh:tableColumn columnName="Отменившая операция" property="repealOperationInfo" />
					<msh:tableColumn columnName="Оператор" property="workFunction" />
					<msh:tableColumn columnName="Скидка" property="discount" />
				</msh:table>
			</msh:section>
			<msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService/View">
			<msh:section title="Медицинские услуги">
			<ecom:webQuery name="medicalService" nativeSql="
							select 
							  CAMS.id as camsid
							  ,'('||pp.code ||') '||pp.name as ppname ,'('||ms.code||') '||ms.name as msname
							  ,CAMS.cost as camscost,CAMS.countMedService as camscountMedService
							  ,CAMS.cost*CAMS.countMedService as totalValue
							   from ContractAccountMedService CAMS
							left join PriceMedService PMS ON PMS.pricePosition_id = CAMS.medservice_id
							left join MedService MS ON MS.id = PMS.medService_id
							left join PricePosition pp on pp.id=pms.pricePosition_id
							where CAMS.account_id = '${param.id}'
			"/>
				<div id="divAllCount1"><h1>Сумма: 0 руб</h1></div>
				<msh:table name="medicalService" 
				deleteUrl="entityParentDeleteGoParentView-contract_contractAccountMedService.do"
				editUrl="entityParentEdit-contract_contractAccountMedService.do"
				viewUrl="entityShortView-contract_contractAccountMedService.do"
				action="entityParentView-contract_contractAccountMedService.do" idField="1">
					<msh:tableColumn columnName="Наим. услуги по прейскуранту" property="2" />
					<msh:tableColumn columnName="Наим. услуги внутр." property="3" />
					<msh:tableColumn columnName="Тариф" property="4" />
					<msh:tableColumn columnName="Кол-во" property="5" />
					<msh:tableColumn columnName="Стоимость" property="6" />
				</msh:table>
				<div id="divAllCount2"><h1>Сумма: 0 руб</h1></div>
			</msh:section>
			</msh:ifInRole>
		</msh:ifFormTypeIsView>
		<%
			List list= (List)request.getAttribute("medicalService");
			out.println("<script>");
			out.println("var sum = 0;");
			for (int i=0 ; i<list.size();i++) {
				WebQueryResult res = (WebQueryResult)list.get(i) ;
				out.println("sum = sum*1 + ("+res.get6()+")*1;" ); 
			}	
			out.println("$('divAllCount1').innerHTML = '<h1>Сумма: '+sum+' руб.</h1>'"); 
			out.println("$('divAllCount2').innerHTML = '<h1>Сумма: '+sum+' руб.</h1>'");
			out.println("</script>");
		%>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_contractAccountForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractAccountForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAccount" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAccount" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-contract_contractAccountOperation" name="Операции по счету" title="Операции по счету" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Create"/>
				<msh:sideLink params="id" action="/contract_contractAccountMedServiciesView" name="Медицинские услуги" title="Медицинские услуги" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService/Create"/>
				<msh:sideLink params="id" action="/entityParentPrepareCreate-contract_contractAccount20MedService" name="Медицинские услуги (до 9 услуг)" title="Медицинские услуги (до 9 услуг)" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService/Create"/>
			</msh:sideMenu>
			<msh:sideMenu title="Печать">
      	      <msh:sideLink params="id" name="Акт выполненных работ" action="/print-contract.do?s=CertificatePersonPrintService&m=PrinCertificate"/>
        	</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
