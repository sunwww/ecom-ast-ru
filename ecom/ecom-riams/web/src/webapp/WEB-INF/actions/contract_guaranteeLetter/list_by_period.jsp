<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Поиск гарантийных писем</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<tags:contractMenu currentAction="quaranteeReport"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<%
	String typeDate =ActionUtil.updateParameter("ReportContractFind","typeDate","1", request) ;
	request.setAttribute("typeDate", typeDate);
	%>
	<msh:form action="contract_list_quarantee_by_period.do" defaultField="contractNumber" method="get">
			<msh:panel>
				<msh:row>
					<msh:textField property="guaranteeNumber" label="№ гар. письма" fieldColSpan="10" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="contractNumber" label="№ контракта или контрагент" fieldColSpan="10" horizontalFill="true"/>
				</msh:row>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
        <msh:row>
	        <td class="label" title="Поиск по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Поиск по дате:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1"> выдаче гар. письма
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2"> выдаче действия письма
	        </td>
	       
        </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Найти" doNotDisableButtons="cancel" labelSaving="Поиск..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
		<script type='text/javascript'>
    checkFieldUpdate('typeDate','${typeDate}',1) ;

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
//	String contractNumber  = request.getParameter("contractNumber")!=null?request.getParameter("contractNumber").toUpperCase():null ;
//	String accountNumber  = request.getParameter("accountNumber")!=null?request.getParameter("accountNumber").toUpperCase() :null;
	  	if (dateFrom !=null) {
		//	StringBuilder fio = new StringBuilder() ;
			String dateTo = request.getParameter("dateTo")!=null?request.getParameter("dateTo"):dateFrom ;
			String prefix = "juridical" ;
			StringBuilder paramSql= new StringBuilder() ;
		//  	StringBuilder paramHref= new StringBuilder() ;
			
			if ("1".equals(typeDate)){
				paramSql.append(" cg.issueDate between to_date('").append(dateFrom).append("','dd.MM.yyyy') and to_date('").append(dateTo).append("','dd.MM.yyyy')") ;
			} else if ("2".equals(typeDate)) {
				paramSql.append(" cg.actionDate <=to_date('").append(dateFrom).append("','dd.MM.yyyy') and cg.actionDateTo >=to_date('").append(dateTo).append("','dd.MM.yyyy')") ;
			}
		request.setAttribute("paramSql", paramSql.toString()) ;
//	  	request.setAttribute("paramHref", paramHref.toString()) ;
//		request.setAttribute("fiocp", fio.toString()) ;
		request.setAttribute("prefix", prefix) ;
		
	%>
			<ecom:webQuery name="lettersList" nameFldSql="lettersList_sql" nativeSql="
			select cg.id ,cg.numberdoc as f2 , pat.patientinfo as f3, cg.limitmoney as f4 
			,sum(pp.cost*coalesce(smc.medserviceamount,1)) as f5_spent, cg.limitmoney - sum(pp.cost*coalesce(smc.medserviceamount,1)) as f6_ostatok
			, case when sum(pp.cost*coalesce(smc.medserviceamount,1))*100/cg.limitmoney >90 then 'background:red' else '' end as f7_style
			from ContractGuarantee cg 
			left join medcontract mc on mc.id=cg.contract_id
			left join medcase vis on vis.guarantee_id=cg.id
			left join medcase smc on smc.parent_id=vis.id 
			left join pricemedservice pms on pms.medservice_id=smc.medservice_id
			left join priceposition pp on pp.id=pms.priceposition_id and pp.pricelist_id=mc.pricelist_id
			left join contractPerson cp on cp.id = cg.contractperson_id
			left join patient pat on pat.id=cp.patient_id
			where  
			${paramSql}
			group by cg.id ,cg.numberdoc  ,pat.patientinfo ,cg.limitmoney
			order by pat.patientinfo
			"/>
			
				<msh:table styleRow="7" name="lettersList" action="entityParentView-contract_guaranteeLetter.do" idField="1" disableKeySupport="true" >
					<msh:tableColumn columnName="№ гар. письма" property="2" />
					<msh:tableColumn columnName="ФИО персоны" property="3" />
					<msh:tableColumn columnName="Лимит письма" property="4" />
					<msh:tableColumn columnName="Израсховано по письму" property="5" />
					<msh:tableColumn columnName="Остаток" property="6" />
					
				</msh:table>

		<msh:sectionTitle>Расчет остатка по гар. письму по новому алгоритму</msh:sectionTitle>
		<ecom:webQuery name="lettersListNews" nameFldSql="lettersListNewsSql" nativeSql="
			select cg.id ,cg.numberdoc as f2 , pat.patientinfo as f3, cg.limitmoney as f4
			,sum(cams.cost*coalesce(cams.countmedservice,1)) as f5_spent, cg.limitmoney - sum(cams.cost*coalesce(cams.countmedservice,1)) as f6_ostatok
			, case when sum(cams.cost*coalesce(cams.countmedservice,1))*100/cg.limitmoney >90 then 'background:red' else '' end as f7_style
			from ContractGuarantee cg
			left join medcontract mc on mc.id=cg.contract_id
			left join ContractAccountMedService cams on cams.guarantee_id=cg.id
			left join pricemedservice pms on pms.id=cams.medservice_id
			left join contractPerson cp on cp.id = cg.contractperson_id
			left join patient pat on pat.id=cp.patient_id
			where
			${paramSql}
			group by cg.id, cg.numberdoc, pat.patientinfo, cg.limitmoney
			order by pat.patientinfo
			"/>${lettersListNewsSql}

		<msh:table styleRow="7" name="lettersListNews" action="entityParentView-contract_guaranteeLetter.do" idField="1" disableKeySupport="true" >
			<msh:tableColumn columnName="№ гар. письма" property="2" />
			<msh:tableColumn columnName="ФИО персоны" property="3" />
			<msh:tableColumn columnName="Лимит письма" property="4" />
			<msh:tableColumn columnName="Израсховано по письму" property="5" />
			<msh:tableColumn columnName="Остаток" property="6" />
		</msh:table>
				<%} %>
	
	</tiles:put>
	
	
</tiles:insert>