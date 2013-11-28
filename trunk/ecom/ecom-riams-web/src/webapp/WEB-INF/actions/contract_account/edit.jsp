
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
		<script type="text/javascript">
		function accrual() {
			var ids = theTableArrow.getInsertedIdsAsParams("camsid","medicalService") ;
			document.location (ids)
			
		}
		</script>
	</tiles:put>
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_account.do" defaultField="dateFrom" title="<a href='entityParentView-contract_account.do?id=${param.id}'>Счет</a>" >
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
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
					<msh:textField property="discountDefault" label="Скидка"/>
					<msh:checkBox property="block" label="Блокирован"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_accountForm">
			<msh:section title="Операции по счету">
			<table border='0'>
				<tr>
					<td>
						<msh:section title="Начисления. Последние 10" >
							<ecom:webQuery name="operationsAccrual" maxResult="10" nativeSql="select cao.id
								, to_char(cao.operationDate,'dd.mm.yyyy')||' '||cast(cao.operationTime as varchar(5))  as operationDate
								, cao.cost, cao.discount,round(cao.cost*(100-cao.discount)/100,2) as oplate
								,vwf.name||' '||wp.lastname
								from ContractAccountOperation cao
								left join WorkFunction wf on wf.id=cao.workFunction_id
								left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
								left join Worker w on w.id=wf.worker_id
								left join Patient wp on wp.id=w.person_id
							where cao.account_id='${param.id}'
							and cao.dtype='OperationAccrual'
							order by cao.operationDate desc,cao.operationTime desc
							"/>
							<msh:table  
							printUrl="print-dogovor572.do?s=CertificatePersonPrintService&m=printContractByAccrual"
							viewUrl="entityParentView-contract_accountOperationAccrual.do?short=Short" 
							 name="operationsAccrual" action="entityParentView-contract_accountOperationAccrual.do" idField="1">
								<msh:tableColumn columnName="Дата и время операции" property="2" />
								<msh:tableColumn columnName="Стоимость" property="3" />
								<msh:tableColumn columnName="Скидка" property="4" />
								<msh:tableColumn columnName="Оплачено" property="5" />
								<msh:tableColumn columnName="Оператор" property="6" />
							</msh:table>
						</msh:section>
					</td>
					<td>
						<msh:ifInRole roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService/View">
						<msh:section >
	<ecom:webQuery name="medicalService" nativeSql="
			select cams.id, pp.code,pp.name,cams.cost,cams.countMedService 
						, cams.countMedService*cams.cost as sumNoAccraulMedService 
						,round(cams.countMedService*cams.cost*(100-ca.discountDefault)/100,2) as oplateWithDiscount
						from ContractAccount ca
						left join ContractAccountMedService cams on cams.account_id=ca.id
						left join PriceMedService pms on pms.id=cams.medService_id
						left join PricePosition pp on pp.id=pms.pricePosition_id
						left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id
						left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'
						where ca.id='${param.id}' and cao.id is null and caos.id is null
						group by  cams.id, pp.code, pp.name , cams.countMedService,cams.cost,ca.discountdefault						
						"/>
							
						<msh:sectionTitle>Медицинские услуги (неоплаченные)
						
						<msh:tableNotEmpty name="medicalService"><a href='entityParentPrepareCreate-contract_accountOperationAccrual.do?id=${param.id}'>Оплатить</a></msh:tableNotEmpty>
						</msh:sectionTitle>
						<msh:sectionContent>
							<msh:table selection="multy" name="medicalService" 
							viewUrl="entityParentView-contract_accountMedService.do?short=Short"
							deleteUrl="entityParentDeleteGoParentView-contract_accountMedService.do"
							editUrl="entityParentEdit-contract_accountMedService.do"
							action="entityParentView-contract_accountMedService.do" idField="1">
								<msh:tableColumn columnName="Код" property="2" />
								<msh:tableColumn columnName="Наим." property="3" />
								<msh:tableColumn columnName="Тариф" property="4" />
								<msh:tableColumn columnName="Кол-во" property="5" />
								<msh:tableColumn columnName="Стоимость" isCalcAmount="true" property="6" />
								<msh:tableColumn columnName="Стоимость с учетом скидки" isCalcAmount="true" property="7" />
							</msh:table>
							</msh:sectionContent>
						</msh:section>
						</msh:ifInRole>
					
					</td>

				</tr>
			</table>
			</msh:section>
			<msh:ifInRole roles="">
<msh:section title="Обслуживаемые персоны (потребители)">
			<ecom:webQuery nativeSql="select sp.id,
			CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			
			from ServedPerson sp
			left join ContractPerson cp on cp.id=sp.person_id 
			left join patient p on p.id=cp.patient_id
			where sp.account_id='${param.id}'
			group by  sp.id,cp.dtype,p.lastname,p.firstname,p.middlename,p.birthday,cp.name
			
			" name="serverPerson"/>
				<msh:table name="serverPerson" action="entityParentView-contract_servedPerson.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Информация" property="2"/>

					
				</msh:table>
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
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_accountForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_accountForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_account" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Edit"/>
				<msh:sideLink key="ALT+DEL" confirm="Вы точно хотите удалить счет?" params="id" action="/entityParentDeleteGoParentView-contract_account" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink name="Счет" action="/entityParentPrepareCreate-contract_servedPerson" params="id" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Create" title="Добавить потребителя (обслуживающую персону)"/>
				<msh:sideLink params="id" action="/entityParentPrepareCreate-contract_accountOperation" name="Операции по счету" title="Операции по счету" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation/Create"/>
				<msh:sideLink params="id" action="/contract_accountMedServiciesView" name="Медицинские услуги" title="Медицинские услуги" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/MedService/Create"/>
			</msh:sideMenu>
			<%--
			<msh:sideMenu title="Печать">
      	      <msh:sideLink params="id" name="Акт выполненных работ" action="/print-contract.do?s=CertificatePersonPrintService&m=PrinCertificate"/>
      	      <msh:sideLink params="id" name="Договора" action="/print-dogovor572.do?s=CertificatePersonPrintService&m=PrinCertificate"/>
        	</msh:sideMenu> --%>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
