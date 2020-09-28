<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="java.util.StringTokenizer"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Поиск договоров</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		 <msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/Create" params="" action="/entityPrepareCreate-contract_medContract" title="Добавить медицинский договор" name="Медицинский договор" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<%
	String typeContractPerson =ActionUtil.updateParameter("ReportContractFind","typeContractPerson","1", request) ;
	String typeContract =ActionUtil.updateParameter("ReportContractFind","typeContract","3", request) ;
	%>
	<msh:form action="contract_find_by_number.do" defaultField="contractNumber" method="get">
			<msh:panel>
				<msh:row>
					<msh:textField property="contractNumber" label="№ контракта или контрагент" fieldColSpan="10" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="accountNumber" label="№ счета" fieldColSpan="10" horizontalFill="true"/>
				</msh:row>

        <msh:row>
	        <td class="label" title="Договорная персона (typeContractPerson)" colspan="1"><label for="typeContractPersonName" id="typeContractPersonLabel">Договорная персона:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по всем физическим лицам">
	        	<input type="radio" name="typeContractPerson" value="1"> физ. лицо
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" title="поиск по всем юридическим лицам">
	        	<input type="radio" name="typeContractPerson" value="2"> юрид. лицо (все)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по юридическим лицам: страховая компания">
	        	<input type="radio" name="typeContractPerson" value="3"> страх. компания
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по юридическим лицам: силовые структуры">
	        	<input type="radio" name="typeContractPerson" value="4"> силовые структуры
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по юридическим лицам: ЛПУ">
	        	<input type="radio" name="typeContractPerson" value="5"> ЛПУ
	        </td>	        
	        <td onclick="this.childNodes[1].checked='checked';"  title="поиск по юридическим лицам: ЛПУ">
	        	<input type="radio" name="typeContractPerson" value="6"> Обслуживаемое физ. лицо
	        </td>	        
        </msh:row>
         <msh:row>
	        <td class="label" title="Статус договора" colspan="1"><label for="typeContractName" id="typeContractLabel">Статус договора:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" title="">
	        	<input type="radio" name="typeContract" value="1"> закрытые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="">
	        	<input type="radio" name="typeContract" value="2"> открытые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  title="" colspan="3">
	        	<input type="radio" name="typeContract" value="3"> не учитывать параметр
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
    checkFieldUpdate('typeContract','${typeContract}',3) ;
    jQuery('#accountNumber').on('keydown', function(e) {enterSubm(e);});
    jQuery('#contractNumber').on('keydown', function(e) {enterSubm(e);});
    function enterSubm(e) {
		if (e.which==13) {
			document.forms[0].submit();
		}
	}

   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if (+aValue===0 || +aValue>+aMax) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
	    
			 
    </script>
	<%
		String accountNumber = request.getParameter("accountNumber") ;
		if (accountNumber!=null && !accountNumber.equals("")) { //переходим сразу к счету
		%>
		<script type='text/javascript'>
			window.document.location="entityParentPrepareCreate-contract_accountOperationAccrual.do?id="+${param.accountNumber};
		</script>
		<%
		} else if (request.getParameter("contractNumber")!=null ) {
			StringBuilder fio = new StringBuilder() ;
			String prefix = "juridical" ;
			StringBuilder paramSql= new StringBuilder() ;
			String contractNumber  = request.getParameter("contractNumber").toUpperCase() ;
			String orderBy = "mc.id" ;
			if (typeContractPerson.equals("1"))	{
				prefix = "med" ;
				paramSql.append("  cp.dtype='NaturalPerson'") ;
				StringTokenizer st = new StringTokenizer(contractNumber, " \t;,.");
				if (request.getParameter("contractNumber").equals("")) {
					fio.append(" p.lastname=''") ;
				} else {
					if (st.hasMoreTokens()) {
						fio.append(" p.lastname like '").append(st.nextToken()).append("%'");
					}
					if (st.hasMoreTokens()) {
						fio.append(" and p.firstname like '").append(st.nextToken()).append("%'");
					}
					if (st.hasMoreTokens()) {
						fio.append(" and p.middlename like '").append(st.nextToken()).append("%'");
					}
				}
			} else if (typeContractPerson.equals("2")) {
				paramSql.append("  cp.dtype='JuridicalPerson'") ;
				fio.append("cp.name like '%").append(contractNumber).append("%'") ;
			} else if (typeContractPerson.equals("3")) {
				paramSql.append( "  cp.dtype='JuridicalPerson'") ;
				paramSql.append( "  and reg.id is not null") ;
				fio.append("(cp.name like '%").append(contractNumber).append("%' or reg.name like '%").append(contractNumber).append("%')") ;
			} else if (typeContractPerson.equals("4")) {
				paramSql.append(" cp.dtype='JuridicalPerson'") ;
				paramSql.append("  and vjp.code='SILOVIK'") ;
				fio.append("(cp.name like '%").append(contractNumber).append("%' )") ;
			} else if (typeContractPerson.equals("5")) {
				orderBy = "vcl.code,lpu.name,contractNumber" ;
				paramSql.append(" cp.dtype='JuridicalPerson'") ;
				paramSql.append( "  and lpu.id is not null") ;
				fio.append("(cp.name like '%").append(contractNumber).append("%' or lpu.name like '%").append(contractNumber).append("%')") ;
			} else if (typeContractPerson.equals("6")) {
				paramSql.append("  cp.dtype='NaturalPerson'") ;
				StringTokenizer st = new StringTokenizer(contractNumber, " \t;,.");
				if (request.getParameter("contractNumber").equals("")) {
					fio.append(" p1.lastname=''") ;
				} else {
					if (st.hasMoreTokens()) {
						fio.append(" p1.lastname like '").append(st.nextToken()).append("%'");
					}
					if (st.hasMoreTokens()) {
						fio.append(" and p1.firstname like '").append(st.nextToken()).append("%'");
					}
					if (st.hasMoreTokens()) {
						fio.append(" and p1.middlename like '").append(st.nextToken()).append("%'");
					}
				}
				
				String leftJoinSql = " left join servedperson sp on sp.contract_id=mc.id"+
						 " left join ContractPerson cp1 on cp1.id=sp.person_id"+
						 " left join patient p1 on p1.id=cp1.patient_id";	 
				request.setAttribute("leftJoin",leftJoinSql);
			}
			if ("1".equals(typeContract)) {
				paramSql.append(" and mc.isfinished='1'") ;
			} else if ("0".equals(typeContract)) {
				paramSql.append(" and (mc.isfinished is null or mc.isfinished='0')") ;
			}

			paramSql.append(" and (").append(fio.toString()).append(" or mc.contractNumber='").append(contractNumber).append("')") ;
		  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("juridicalPersonType", "cp.juridicalPersonType_id", request)) ;
		  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("contractLabel", "vcl.id", request)) ;
		  	
		request.setAttribute("paramSql", paramSql.toString()) ;
	  	request.setAttribute("paramHref", "") ;
		request.setAttribute("fiocp", fio.toString()) ;
		request.setAttribute("prefix", prefix) ;
		request.setAttribute("orderBy", orderBy) ;
	%>
			<ecom:webQuery name="childContract" nativeSql="
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
			, case when mc.isFinished='1' then 'background:#EDE;' end as isfinishedstyle
			
			from MedContract mc
			left join VocContractLabel vcl on vcl.id=mc.contractLabel_id
			left join ContractPerson cp on cp.id=mc.customer_id
			left join MisLpu lpu on lpu.id=cp.lpu_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join Patient p on p.id=cp.patient_id
			left join MisLpu ml on ml.id=mc.lpu_id
			left join VocContractRulesProcessing vcrp on vcrp.id=mc.rulesProcessing_id
			left join PriceList pl on pl.id=mc.priceList_id
			left join VocJuridicalPerson vjp on vjp.id=cp.juridicalPersonType_id
			${leftJoin}
			where
			${paramSql} and (MC.isDeleted is null or MC.isDeleted='0')
			order by ${orderBy}
			"/>
				<msh:table styleRow="12" name="childContract" action="entityParentView-contract_${prefix}Contract.do" idField="1" disableKeySupport="true">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn property="11" columnName="Метка"/>
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