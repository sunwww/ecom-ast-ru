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
	<tags:contractMenu currentAction="financeReport"/>
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
		
		%>

			<form action="/riams/contract_reports.do" method="GET">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				</msh:row>
				<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisplayCansel="true" labelSaving="Формирование..." colSpan="4"/>
			</msh:panel>
			</form>

			<msh:section title="Финасовый отчет за период ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
SELECT 
-- Имя
P.lastname||' '||P.firstname||' '||P.middlename AS name,
-- Поступление
CAO.cost AS Postuplenie,
-- Выбраем возвраты
(
	SELECT cost 
	FROM contractAccountOperation AS CAO1
	WHERE repealoperation_id = CAO.id
)
AS Vosvrat

FROM
contractAccount AS CA

--Получаем тип и стоимость операции
LEFT JOIN contractAccountOperation AS CAO ON CAO.account_id = CA.id
LEFT JOIN vocAccountOperation AS VO ON CAO.type_id = VO.id
--Получаем Имя персоны
LEFT JOIN servedPerson AS SP ON CA.servedPerson_id = SP.id
LEFT JOIN medContract AS MC ON SP.contract_id = MC.id
LEFT JOIN contractPerson AS CP ON MC.customer_id = CP.id
LEFT JOIN patient AS P ON CP.patient_id = P.id

WHERE 
-- Тип операции начисление
VO.code = '4'
-- Определяем физических 
AND 
CP.patient_id is not null
-- Определяем дату
-- AND 
-- CA.dateFrom = TO_DATE('01.01.10','dd.mm.yy') 
			"/>

				<msh:table name="finansReport" action="entityParentView-contract_contractAccount.do" idField="#">
				    <msh:tableColumn columnName="Контрагент" property="1" />
					<msh:tableColumn columnName="Начисление" property="2" />
					<msh:tableColumn columnName="Возврат" property="3" />
				</msh:table>
			</msh:section>
	</tiles:put>
	<tiles:put  name='javascript' type='string'>
	<script type="text/javascript">
	eventutil.addEnterSupport('dateFrom', 'subok') ;
	 new dateutil.DateField($('dateFrom'));
	</script>
	</tiles:put>
</tiles:insert>
