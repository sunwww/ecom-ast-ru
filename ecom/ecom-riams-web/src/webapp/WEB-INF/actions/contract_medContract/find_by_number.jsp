<%@page import="java.util.StringTokenizer"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Поиск договоров</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<%--
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/Create" params="" action="/entityPrepareCreate-contract_medContract" title="Добавить медицинский договор" name="Медицинский договор" />
		</msh:sideMenu>
		 --%>
		 <msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/Create" params="" action="/entityPrepareCreate-contract_medContract" title="Добавить медицинский договор" name="Медицинский договор" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<%
	String typeContractPerson =ActionUtil.updateParameter("ReportContractFind","typeContractPerson","1", request) ;
	%>
	<msh:form action="contract_find_by_number.do" defaultField="contractNumber" >
			<msh:panel>
				<msh:row>
					<msh:textField property="contractNumber" label="№ контракта или контрагент" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				
        <msh:row>
	        <td class="label" title="Договорная персона (typeContractPerson)" colspan="1"><label for="typeContractPersonName" id="typeContractPersonLabel">Договорная персона:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeContractPerson" value="1"> физическое лицо
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeContractPerson" value="2"> юридическое лицо
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeContractPerson" value="3"> страховая компания
	        </td>
        </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Найти" doNotDisableButtons="cancel" labelSaving="Поиск..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
		<script type='text/javascript'>
    checkFieldUpdate('typeContractPerson','${typeContractPerson}',1) ;
    
   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
	    
			 
    </script>
	<% if (request.getParameter("contractNumber")!=null) { 
		StringBuilder fio = new StringBuilder() ;
		String contractNumber  = request.getParameter("contractNumber").toUpperCase() ;
		
		if (typeContractPerson.equals("1"))	{
			request.setAttribute("cpdtypeSql", " and cp.dtype='NaturalPerson'") ;
			StringTokenizer st = new StringTokenizer(contractNumber, " \t;,.");
			if (st.hasMoreTokens()) {
				fio.append(" p.lastname like '").append(st.nextToken()).append("%'");
			}
			if (st.hasMoreTokens()) {
				fio.append(" and p.firstname like '").append(st.nextToken()).append("%'");
			}
			if (st.hasMoreTokens()) {
				fio.append(" and p.middlename like '").append(st.nextToken()).append("%'");
			}
		} else if (typeContractPerson.equals("2")) {
			request.setAttribute("cpdtypeSql", " and cp.dtype='JuridicalPerson'") ;
			fio.append("cp.name like '%").append(contractNumber).append("%'") ;
		} else if (typeContractPerson.equals("3")) {
			request.setAttribute("cpdtypeSql", " and cp.dtype='JuridicalPerson'") ;
			fio.append("(cp.name like '%").append(contractNumber).append("%' or reg.name like '%").append(contractNumber).append("%')") ;
		}
		
		request.setAttribute("fiocp", fio.toString()) ;
	%>
			<ecom:webQuery name="childContract" nativeSql="
			select mc.id
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') 
			when cp.dtype='JuridicalPerson' then 'Юрид.лицо: '||cp.name else 'Страховая компания'||reg.name END
			,ml.name as mlname,mc.contractNumber as mccontractNumber
			,mc.dateFrom as mcdateFrom,mc.dateTo as mcdateTo
			,vcrp.name as vcrpname,pl.name as plname
			from MedContract mc
			left join ContractPerson cp on cp.id=mc.customer_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join Patient p on p.id=cp.patient_id
			left join MisLpu ml on ml.id=mc.lpu_id
			left join VocContractRulesProcessing vcrp on vcrp.id=mc.rulesProcessing_id
			left join PriceList pl on pl.id=mc.priceList_id
			where  (mc.contractNumber='${param.contractNumber}' 
			or 
			 ${fiocp}) 
			${cpdtypeSql}
			"/>
				<msh:table name="childContract" action="entityParentView-contract_medContract.do" idField="1" disableKeySupport="true">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn columnName="№ договора" property="4" />
					<msh:tableColumn columnName="Заказчик" property="2" />
					<msh:tableColumn columnName="Дата начала" property="5" />
					<msh:tableColumn columnName="Дата окончания" property="6" />
					<msh:tableColumn columnName="Обработка правил" property="7" />
					<msh:tableColumn columnName="Прейскурант" property="8" />
				</msh:table>	
				<%} %>	
	</tiles:put>
</tiles:insert>
