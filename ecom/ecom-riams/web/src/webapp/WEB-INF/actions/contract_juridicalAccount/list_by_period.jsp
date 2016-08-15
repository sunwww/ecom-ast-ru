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
		<tags:contractMenu currentAction="contract_account"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<%
	String typeContractPerson =ActionUtil.updateParameter("ReportContractFind","typeContractPerson","1", request) ;
	String typeContractAccount =ActionUtil.updateParameter("ReportContractFind","typeContractAccount","3", request) ;
	%>
	<msh:form action="contract_find_by_account_number.do" defaultField="contractNumber" method="get">
			<msh:panel>
				<msh:row>
					<msh:textField property="accountNumber" label="№ счета" fieldColSpan="10" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="contractNumber" label="№ контракта или контрагент" fieldColSpan="10" horizontalFill="true"/>
				</msh:row>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
        <msh:row>
	        <td class="label" title="Договорная персона (typeContractPerson)" colspan="1"><label for="typeContractPersonName" id="typeContractPersonLabel">Договорная персона:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" title="поиск по всем юридическим лицам">
	        	<input type="radio" name="typeContractPerson" value="1"> юрид. лицо (все)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по юридическим лицам: страховая компания">
	        	<input type="radio" name="typeContractPerson" value="2"> страх. компания
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по юридическим лицам: силовые структуры">
	        	<input type="radio" name="typeContractPerson" value="3"> силовые структуры
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по юридическим лицам: ЛПУ">
	        	<input type="radio" name="typeContractPerson" value="4"> ЛПУ
	        </td>	        
        </msh:row>
        <msh:row>
	        <td class="label" title="Статус счета" colspan="1"><label for="typeContractAccountName" id="typeContractAccountLabel">Статус счета:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" title="">
	        	<input type="radio" name="typeContractAccount" value="1"> выставлен
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="">
	        	<input type="radio" name="typeContractAccount" value="2"> на оплату
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="" colspan="3">
	        	<input type="radio" name="typeContractAccount" value="3"> не учитывать параметр
	        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="contractLabel" fieldColSpan="10" label="Метка" 
        		vocName="vocContractLabel" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Найти" doNotDisableButtons="cancel" labelSaving="Поиск..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
		<script type='text/javascript'>
    checkFieldUpdate('typeContractPerson','${typeContractPerson}',1) ;
    checkFieldUpdate('typeContractAccount','${typeContractAccount}',3) ;
    
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
	<% 
	String dateFrom = request.getParameter("dateFrom") ;
	String contractNumber  = request.getParameter("contractNumber")!=null?request.getParameter("contractNumber").toUpperCase():null ;
	String accountNumber  = request.getParameter("accountNumber")!=null?request.getParameter("accountNumber").toUpperCase() :null;
	  	if (dateFrom !=null ||(contractNumber!=null&&!contractNumber.equals("")) || (accountNumber!=null&&!accountNumber.equals("")) ) {
			StringBuilder fio = new StringBuilder() ;
			String dateTo = request.getParameter("dateTo")!=null?request.getParameter("dateTo"):dateFrom ;
			String prefix = "juridical" ;
			StringBuilder paramSql= new StringBuilder() ;
		  	StringBuilder paramHref= new StringBuilder() ;
			
			String orderBy = "mc.id" ;
			if (dateFrom!=null&&!dateFrom.equals("")){
				paramSql.append("ca.dateFrom between to_date('").append(dateFrom).append("','dd.mm.yyyy') and to_date('").append(dateTo).append("','dd.mm.yyyy')") ;
			}
			
			if (typeContractPerson.equals("1")) {
				paramSql.append("  cp.dtype='JuridicalPerson'") ;
				fio.append("cp.name like '%").append(contractNumber).append("%'") ;
			} else if (typeContractPerson.equals("2")) {
				paramSql.append( "  cp.dtype='JuridicalPerson'") ;
				paramSql.append( "  and reg.id is not null") ;
				fio.append("(cp.name like '%").append(contractNumber).append("%' or reg.name like '%").append(contractNumber).append("%')") ;
			} else if (typeContractPerson.equals("3")) {
				paramSql.append("  cp.dtype='JuridicalPerson'") ;
				paramSql.append("  and vjp.code='SILOVIK'") ;
				fio.append("(cp.name like '%").append(contractNumber).append("%' )") ;
			} else if (typeContractPerson.equals("4")) {
				orderBy = "vcl.code,lpu.name,contractNumber" ;
				paramSql.append("  cp.dtype='JuridicalPerson'") ;
				paramSql.append( "  and lpu.id is not null") ;
				fio.append("(cp.name like '%").append(contractNumber).append("%' or lpu.name like '%").append(contractNumber).append("%')") ;
			}
			paramSql.append(" and (").append(fio.toString()).append(" or mc.contractNumber='").append(contractNumber).append("')") ;
			if (typeContractAccount!=null && typeContractAccount.equals("1")) {
				paramSql.append(" and ca.isfinished='1'") ;
			} else if (typeContractAccount!=null && typeContractAccount.equals("1")) {
				paramSql.append(" and (ca.isfinished is null or ca.isfinished='0')") ;
			}
			if (accountNumber!=null && !accountNumber.equals("")) paramSql.append(" and  ca.accountNumber='").append(accountNumber).append("'") ;
		  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("juridicalPersonType", "cp.juridicalPersonType_id", request)) ;
		  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("contractLabel", "vcl.id", request)) ;
		  	
		request.setAttribute("paramSql", paramSql.toString()) ;
	  	request.setAttribute("paramHref", paramHref.toString()) ;
		request.setAttribute("fiocp", fio.toString()) ;
		request.setAttribute("prefix", prefix) ;
		request.setAttribute("orderBy", orderBy) ;
	%>
			<ecom:webQuery name="childContract" nameFldSql="childContract_sql" nativeSql="
			select mc.id,mc.contractNumber as mccontractNumber
			,coalesce(to_char(mc.dateFrom,'dd.mm.yyyy'),'')||'-'||coalesce(to_char(mc.dateTo,'dd.mm.yyyy'),'') as mcdateTo
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: ' 
				when cp.dtype='JuridicalPerson' 
				  then case   
					when reg.id is not null then 'Страховая компания '
					when lpu.id is not null then 'ЛПУ '
					when vjp.code='SILOVIK' then 'Силовые структуры'
					else 'Юрид.лицо: '||coalesce(vjp.name||' ','') end 
				END as persname
			,coalesce(p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY'),cp.name) as cpname
			, coalesce(lpu.name,reg.name) as vjpname 
			,ml.name as mlname
			
			,vcrp.name as vcrpname,pl.name as plname
			,mc.limitMoney ||case when mc.isRequiredGuaratee='1'
				then ' (обязательно гарант.письмо)' else '' end as limitMoney
			,vcl.name as vclname
			,ca.accountNumber
			, case when ca.isFinished='1' then 'Закрыт' else null end as isfinished
			, case when mc.isFinished='1' then 'background:#EDE;' end|| 
				case when ca.isFinished='1' then 'color:red;' else null end as isfinishedstyle
			
			from MedContract mc
			left join ContractAccount ca on ca.contract_id=mc.id
			left join VocContractLabel vcl on vcl.id=mc.contractLabel_id
			left join ContractPerson cp on cp.id=mc.customer_id
			left join MisLpu lpu on lpu.id=cp.lpu_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join Patient p on p.id=cp.patient_id
			left join MisLpu ml on ml.id=mc.lpu_id
			left join VocContractRulesProcessing vcrp on vcrp.id=mc.rulesProcessing_id
			left join PriceList pl on pl.id=mc.priceList_id
			left join VocJuridicalPerson vjp on vjp.id=cp.juridicalPersonType_id
			where  
			${paramSql}
			order by ${orderBy}
			"/>
				<msh:table styleRow="14" name="childContract" action="entityParentView-contract_juridicalContract.do" idField="1" disableKeySupport="true">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn property="11" columnName="Метка"/>
					<msh:tableColumn columnName="№ счета" property="12" />
					<msh:tableColumn columnName="Статус" property="13" />
					<msh:tableColumn columnName="№ договора" property="2" />
					<msh:tableColumn columnName="Период действия" property="3" />
					<msh:tableColumn columnName="Заказчик тип" property="4" />
					<msh:tableColumn columnName="Наименование" property="5" />
					
					<msh:tableColumn columnName="Обработка правил" property="8" />
					<msh:tableColumn columnName="Прейскурант" property="9" />
					<msh:tableColumn columnName="Лимит" property="10" />
				</msh:table>
				
				<%} %>	
	</tiles:put>
</tiles:insert>