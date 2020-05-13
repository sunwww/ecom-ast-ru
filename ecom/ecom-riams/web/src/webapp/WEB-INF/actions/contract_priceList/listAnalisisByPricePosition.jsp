<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Contract" title="Анализ услуг прейскуранта"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	
    	<tags:contractMenu currentAction="analisisPriceServices"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
    String typeView =ActionUtil.updateParameter("Contract_analisis","typeView","3", request) ;
	String typeFindMed =ActionUtil.updateParameter("Contract_analisis","typeFindMed","5", request) ;
	
	
  %>
  
    <msh:form action="/contact_analisis_by_priceServices.do" defaultField="priceListName" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7"/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по услуг прейскуранта с медицинскими услугами (typeFindMed)" colspan="1"><label for="typeFindMedName" id="typeFindMedLabel">Нахождение услуг в спр.услуг по коду:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" >
        	<input type="radio" name="typeFindMed" value="1"> совпадают коды  
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  >
        	<input type="radio" name="typeFindMed" value="2" >  совпадают названия
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  >
        	<input type="radio" name="typeFindMed" value="3">  совпадают коды и названия
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  >
        	<input type="radio" name="typeFindMed" value="4">  нет совпадений
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  >
        	<input type="radio" name="typeFindMed" value="5">  отображать все
        </td>
      </msh:row>

        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="1">  соответствие проставлено
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="2">  нет соответствия 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="3">  отображать все 
	        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="priceList" fieldColSpan="7" horizontalFill="true" label="Прейскурант" vocName="priceList"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="filterByCode" fieldColSpan="7" horizontalFill="true" label="Фильтр по коду"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="filterByName" fieldColSpan="7" horizontalFill="true" label="Фильтр по наименованию"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    checkFieldUpdate('typeFindMed','${typeFindMed}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
  
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
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='contact_analisis_by_priceServices.do' ;
    }
    function print() {

    	
    }
    </script>

        <tags:service_change name="VMS"/>    
    
    <%
    String priceList = (String)request.getParameter("priceList") ;
    String date1 = (String)request.getParameter("dateEnd") ;
    
    if (priceList!=null && !priceList.equals("") && !priceList.equals("0"))  {

    	if (typeFindMed!=null && typeFindMed.equals("1")) {
    		request.setAttribute("findMedSql", " pp.code=ms.code") ;
    		request.setAttribute("findMedAddSql", " and ms.id is not null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("2")) {
    		request.setAttribute("findMedSql", " trim(upper(ms.name))=trim(upper(pp.name))") ;
    		request.setAttribute("findMedAddSql", " and ms.id is not null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("3")) {
    		request.setAttribute("findMedSql", " pp.code=ms.code and trim(upper(ms.name))=trim(upper(pp.name))") ;
    		request.setAttribute("findMedAddSql", " and ms.id is not null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("4")) {
    		request.setAttribute("findMedSql", " (pp.code=ms.code or trim(upper(ms.name))=trim(upper(pp.name)))") ;
    		request.setAttribute("findMedAddSql", " and ms.id is null") ;
    	} else if (typeFindMed!=null && typeFindMed.equals("5")) {
    		request.setAttribute("findMedSql", "") ;
    	} 
    	if (typeView!=null && typeView.equals("1")) {
    		request.setAttribute("viewSql", " and pms.id is not null and ms1.id is not null") ;
    	} else if (typeView!=null && typeView.equals("2")) {
    		request.setAttribute("viewSql", " and ms1.id is null") ;
    	} else if (typeView!=null && typeView.equals("3")) {
    		request.setAttribute("viewSql", "") ;
    	} 
    	String filterByCode = request.getParameter("filterByCode") ;
    	String filterByName = request.getParameter("filterByName") ;
    	ActionUtil.setUpperLikeSql("filterByCode", "pp.code", request) ;
    	ActionUtil.setUpperLikeSql("filterByName", "pp.name", request) ;
    	ActionUtil.setParameterFilterSql("priceList","pp.priceList_id", request) ;
    	if (typeFindMed!=null && (typeFindMed.equals("1") ||typeFindMed.equals("2")||typeFindMed.equals("3")||typeFindMed.equals("4"))) {
    	%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select pp.id||':'||coalesce(ms.id,'0') as ppid,pp.code as ppcode,pp.name as ppname
,ms1.code as ms1code,ms1.name as ms1name
,ms.code as mscode,ms.name as msname,pp.cost
from PricePosition pp
left join PriceMedService pms on pms.pricePosition_id=pp.id
left join MedService ms1 on ms1.id=pms.medService_id
left join MedService ms on ${findMedSql}
where pp.priceList_id = '${param.priceList}' and pp.dtype='PricePosition' ${viewSql} ${findMedAddSql}
${filterByCodeSql} ${filterByNameSql}
order by pp.code
    " />
    <msh:sectionTitle>
    
    <form action="print-contract_analisis_PMservices.do" method="post" target="_blank">
    Реестр услуг.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_expert"
    viewUrl="entityParentView-contract_pricePosition.do?short=Short" 
     action="entityParentView-contract_pricePosition.do" idField="1" >
     	<msh:tableNotEmpty>
     		                  
     		<tr>
     			<th></th>
     			<th></th>
     			<th colspan="3">По прейскуранту</th>
     			<th colspan="3">Соответсвие</th>
     			<th colspan="2">Подобранное соответствие</th>
     		</tr>
     	</msh:tableNotEmpty>
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="код" property="2" />
      <msh:tableColumn columnName="наименование" property="3" />
      <msh:tableColumn columnName="цена" property="8" />
      <msh:tableColumn columnName="код" property="4" />
      <msh:tableColumn columnName="наименование" property="5" />
	  	<msh:tableButton property="1" buttonFunction="showVMSServiceFind" addParam="'MedService','PricePosition'" buttonName="Прикрепление к услуге" buttonShortName="П"/>
      <msh:tableColumn columnName="код" property="6" />
      <msh:tableColumn columnName="наименование" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} else { %>
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select pp.id as ppid,pp.code as ppcode,pp.name as ppname
,ms1.code as ms1code,ms1.name as ms1name,pp.cost,pp.id
from PricePosition pp
left join PriceMedService pms on pms.pricePosition_id=pp.id
left join MedService ms1 on ms1.id=pms.medService_id
where pp.priceList_id = '${param.priceList}' and pp.dtype='PricePosition' ${viewSql} 
${filterByCodeSql} ${filterByNameSql}
order by pp.code
    " />
    <msh:sectionTitle>
    
    <form action="print-contract_analisis_PMservices.do" method="post" target="_blank">
    Реестр услуг.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_expert"
    viewUrl="entityParentView-contract_pricePosition.do?short=Short" 
     action="entityParentView-contract_pricePosition.do" idField="1" >
     	<msh:tableNotEmpty>
     		<tr>
     			<th></th>
     			<th></th>
     			<th colspan="4">По прейскуранту</th>
     			<th colspan="3">Соответсвие</th>
     		</tr>
     	</msh:tableNotEmpty>
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="код" property="2" />
      <msh:tableColumn columnName="наименование" property="3" />
      <msh:tableColumn columnName="цена" property="6" />
      <msh:tableColumn columnName="id" property="7" />
	  	<msh:tableButton property="1" buttonFunction="showVMSServiceFind" addParam="'MedService','PricePosition'" buttonName="Прикрепление к услуге" buttonShortName="П"/>
      <msh:tableColumn columnName="код" property="4" />
      <msh:tableColumn columnName="наименование" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    }
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
  <tiles:put type="string" name="javascript">
  <script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
  	<script type="text/javascript">
  	function updatePriceMedServices() {
  		var ids = theTableArrow.getInsertedIdsAsParams("","journal_expert") ;
  		ContractService.settingAppropriateService(
					ids,
				
		     {
					callback: function(aResult) {
						alert(aResult) ;
					}
			});
  	}
  	</script>
  </tiles:put>
</tiles:insert>