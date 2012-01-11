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

			<form action="/riams/contract_reports.do" method="GET">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>

				<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
			</msh:panel>
			</form>

			<msh:section title="Финасовый отчет за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT 
MC.id,
CA.balancesum,
getKontragent(CA.id,' '),
getDateNum(CA.id,' '),
getSumKOplate(CA.id),
getsaldoEnd(CA.id, ' ')
FROM 
  contractaccount as CA
LEFT JOIN servedPerson SP ON CA.servedperson_id=SP.id
LEFT JOIN medcontract MC ON SP.contract_id=MC.id 
WHERE
	CA.dateFrom ${dFrom} AND CA.dateFrom ${dTo} 
			"/>

				<msh:table name="finansReport" action="entityParentView-contract_medContract.do" idField="1">
				    <msh:tableColumn columnName="Номер договроа" property="1" />
					<msh:tableColumn columnName="Наименование контрагента" property="3" />
					<msh:tableColumn columnName="Входящее сальдо" property="2" />
					<msh:tableColumn columnName="Номер и дата счета" property="4" />
					<msh:tableColumn columnName="Сумма к оплате" property="5" />
					<msh:tableColumn columnName="Исходящее сальдо" property="6" />
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
