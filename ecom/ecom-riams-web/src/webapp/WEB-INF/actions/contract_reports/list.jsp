<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='side' type='string'>
	<msh:sideMenu title="Печать">
    	<msh:sideLink params="dateFrom,dateTo" name="Финасовый отчет" action="/print-finreport.do?s=Contractreport&m=FinRep"/>
    </msh:sideMenu>
	<tags:contractMenu currentAction="financeReport1"/>
	</tiles:put>
	<tiles:put name='body' type='string' >

		

			<form action="/riams/contract_reports.do" method="GET">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>

				<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
			</msh:panel>
			</form>
<%
		String dateFrom = request.getParameter("dateFrom") ;
		String dFrom = "" ;
		if (dateFrom==null ||dateFrom.equals("") ) {
			dFrom=" is null " ;
		} else {
			dFrom = ">=to_date('"+dateFrom+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dFrom",dFrom) ;
		
		String dateTo = request.getParameter("dateTo") ;
		String dTo = "" ;
		if (dateTo==null ||dateTo.equals("") ) {
			dTo=" is null " ;
		} else {
			dTo = "<=to_date('"+dateTo+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dTo",dTo) ;
		
		String FromTo = "";
		if  (dateTo==null ||dateTo.equals("") ) {}
		else if (dateFrom==null ||dateFrom.equals("") ) {}
		else FromTo="C "+dFrom+" По "+dTo;
		%>
			<msh:section title="Финасовый отчет за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT cao.id
,MC.contractnumber || ' '||to_char(mc.dateFrom,'dd.mm.yyyy') as dateNum
,coalesce(CCP.lastname||' '||CCP.firstname||' '||CCP.middlename||' г.р. '||to_char(CCP.birthday,'dd.mm.yyyy')
,CCO.name) as kontragent
,round(sum(case when cao.dtype='OperationAccrual' then cao.cost*(100-coalesce(cao.discount,0))/100 else 0 end),2) as accrualSum
,round(sum(case when cao.dtype='OperationReturn' then cao.cost*(100-coalesce(cao.discount,0))/100 else 0 end),2) as returnSum
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id 
LEFT JOIN contractPerson CC ON CC.id=MC.customer_id
LEFT JOIN patient CCP ON CCP.id=CC.patient_id
LEFT JOIN VocOrg CCO ON CCO.id=CC.organization_id
left join ContractAccountOperation CAO on CAO.account_id=CA.id 
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy') 
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')
group by cao.id,mc.id,CCP.lastname,CCP.firstname,CCP.middlename,CCP.birthday,CCO.name,MC.contractnumber,mc.dateFrom
			"/>

				<msh:table name="finansReport" 
				action="entitySubclassView-contract_accountOperation.do" 
				idField="1">
					<msh:tableColumn columnName="Договор" property="2" />
					<msh:tableColumn columnName="Наименование контрагента" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="Оплачено" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Возврат" isCalcAmount="true" property="5" />
				</msh:table>

			</msh:section>

	</tiles:put>
	<tiles:put  name='javascript' type='string'>
	<script type="text/javascript">
	eventutil.addEnterSupport('dateFrom', 'dateTo') ;
	eventutil.addEnterSupport('dateTo', 'subok');
	 new dateutil.DateField($('dateTo'));
	 new dateutil.DateField($('dateFrom'));
	</script>
	</tiles:put>
</tiles:insert>