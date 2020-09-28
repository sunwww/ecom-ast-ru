<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Contract" title="Анализ услуг"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	
    	<tags:contractMenu currentAction="controlReportNoAccrual"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/contact_analisis_no_accrual.do" defaultField="dateFrom">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
     <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
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

        <tags:service_change name="VMS"/>    
    <%
    String date1 = request.getParameter("dateFrom") ;
    String date2 = request.getParameter("dateTo") ;
    
    if (date1!=null && !date1.equals("") )  {
    	if (date2==null || date2.equals("")) {request.setAttribute("dateTo", date1) ;} else {request.setAttribute("dateTo", date2) ;}
    	%>
    
    <msh:section >
    <ecom:webQuery nameFldSql="serverPerson_sql" name="serverPerson" nativeSql="
select ca.id,
	CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			,sp.dateFrom,sp.dateTo
			, count(distinct case when cao.id is null then cams.id else null end) as cntMedService 
			, sum(case when cao.id is null then cams.countMedService*cams.cost else 0 end) as sumNoAccraulMedService 
			, round(sum((case when cao.id is null then cams.countMedService*cams.cost else 0 end)*(100-ca.discountDefault)/100),2) as sumNoAccraulMedServiceWithDiscount 
			from ContractAccount ca
			left join ServedPerson sp on ca.id = sp.account_id
			left join ContractAccountMedService cams on cams.account_id=ca.id
			left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id
			left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'
			left join ContractPerson cp on cp.id=sp.person_id left join patient p on p.id=cp.patient_id
			where ca.dateFrom between to_date('${param.dateFrom}','dd.mm.yyyy') and to_date('${dateTo}','dd.mm.yyyy') 
			and cao.id is null  and caos.id is null
			group by  sp.id,cp.dtype,p.lastname,p.firstname,p.middlename,p.birthday,cp.name
			,sp.dateFrom,sp.dateTo,ca.id,ca.balanceSum, ca.reservationSum,ca.discountdefault
    " />
    <msh:sectionTitle>
    
    <form action="print-contact_analisis_no_accrual.do" method="post" target="_blank">
    Реестр счетов на оплату за период с ${param.dateFrom} по ${dateTo}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр счетов на оплату за период с ${param.dateFrom} по ${dateTo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
				<msh:table name="serverPerson" viewUrl="entityParentView-contract_account.do?short=Short" 
				action="entityParentPrepareCreate-contract_accountOperationAccrual.do"
				idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Информация" property="2"/>
					<msh:tableColumn columnName="Дата начала обсл." property="3"/>
					<msh:tableColumn columnName="Дата окончания" property="4"/>
					<msh:tableColumn columnName="кол-во неопл. услуг" property="5"/>
					<msh:tableColumn columnName="сумма к оплате" property="6"/>
					<msh:tableColumn columnName="сумма к оплате с учетом скидки" property="7"/>
				</msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>