<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals">Экстренная хирургическая помощь и злокачественные новообразования</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_report016"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  
  <% 
  boolean sh = true ;
  //String shS =  ;
  if (request.getParameter("short")!=null) {
	  sh=false ;
  }
  String typeView=ActionUtil.updateParameter("ReportSurgicalHelp","typeView","4", request) ;
  
  if (sh) {
	 
  %>
    <msh:form action="/stac_journal_surgical_help.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport016" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeView" value="1">  экстренную хирургическую помощь
        </td>
      </msh:row>
      <msh:row>
      	<td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="5">
        	<input type="radio" name="typeView" value="2">  экстренную хирургическую помощь (разбивка по срокам доставки)
        </td>
      </msh:row>
      <msh:row>
      	<td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeView" value="3"  >  операции по поводу злокачественных новообразований
        </td>
       </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Дата начала" />
        <msh:textField property="dateEnd" label="Дата окончания" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
  ${isReportBase}
		<ecom:webQuery isReportBase="${isReportBase}"  name="result_death_sql" nativeSql="select id,name from VocHospitalizationResult where code='11'"/>
  ${isReportBase}
		<ecom:webQuery isReportBase="${isReportBase}"  name="diag_typeReg_cl_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='3'"/>
  ${isReportBase}
		<ecom:webQuery isReportBase="${isReportBase}"  name="diag_priority_m_sql" nativeSql="select id,name from VocPriorityDiagnosis where code='1'"/>
      ${isReportBase}
      <ecom:webQuery isReportBase="${isReportBase}" name="diag_typeReg_pat_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='5'"/>
    
       <script type='text/javascript'>
    
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
    	frm.action='stac_journal_surgical_help.do' ;
    }
			 
    </script>
     <%
  }
     String date = request.getParameter("dateBegin") ;
     String dep1 = request.getParameter("department1") ;
     String dep = request.getParameter("department") ;
     //if (dep!=null && dep.equals("undefined")) dep=null ;
     String id = request.getParameter("id") ;
     String view = (String)request.getAttribute("typeView") ;
    
if (date!=null && !date.equals("")) {
	String dateEnd = (String)request.getParameter("dateEnd") ;
	if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
  	ActionUtil.getValueByList("diag_typeReg_pat_sql", "diag_typeReg_pat", request) ;

	ActionUtil.getValueByList("diag_priority_m_sql", "diag_priority_m", request) ;
	ActionUtil.getValueByList("diag_typeReg_cl_sql", "diag_typeReg_cl", request) ;
	ActionUtil.getValueByList("result_death_sql", "result_death", request) ;
	//ActionUtil.setParameterFilterSql("department", "sloa.department_id", request) ;
	ActionUtil.setParameterFilterSql("department", "case when mlLast.isnoomc='1' then mlPreLast.id else mlLast.id end ", request) ;
    if (typeView.equals("1")||typeView.equals("2")) {
    	if (typeView.equals("2")) {
    		request.setAttribute("tableGroup", ",vpat.id,vpat.name") ;
    		request.setAttribute("tableOrder", ",vpat.id") ;
    		request.setAttribute("tableField", "vpat.name") ;
    		request.setAttribute("tableIdAdd", "||'&vpatid='||vpat.id") ;
    	} else {
    		request.setAttribute("tableField", "vrspt.strCode") ;
    		request.setAttribute("tableStyle", "td.spokDost,th.spokDost{display:none;}") ;
    	}
        %>
        <style type="text/css">
        ${tableStyle}
        </style>
	<msh:section>
		<msh:sectionTitle>
		
		${isReportBase}
		<ecom:webQuery isReportBase="${isReportBase}"  name="list_surgical" nameFldSql="list_surgical_sql"
		nativeSql="select vrspt.id||'&strcode='||vrspt.id ${tableIdAdd}
		,vrspt.name as vrsotname,vrspt.code as vrsptcode
		
		,${tableField} as vrsptstrcode 
,count(distinct sls.id) as cntAll
,count(distinct case when sls.result_id='${result_death}' then sls.id else null end) as cntDeath

,count(distinct sls.id)-count(distinct case when so.id is not null then sls.id else null end) as cntNoOperAll
,count(distinct case when sls.result_id='${result_death}' then sls.id else null end)
-count(distinct case when so.id is not null and sls.result_id='${result_death}' then sls.id else null end) as cntNoOperDeath

,count(distinct case when so.id is not null then sls.id else null end) as cntOperAll
,count(distinct case when so.id is not null and sls.result_id='${result_death}' then sls.id else null end) as cntOperDeath
 from medcase sls
left join diagnosis diag on sls.id=diag.medcase_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join MedCase sloa on sloa.parent_id=sls.id  and sloa.dateFinish is not null
left join MedCase sloaPre on sloaPre.id=sloa.prevmedcase_id
left join mislpu mlLast on mlLast.id=sloa.department_id
left join mislpu mlPreLast on mlPreLast.id=sloaPre.department_id
left join MedCase sloall on sloall.parent_id=sls.id
left join SurgicalOperation so on so.medCase_id=sloall.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join VocReportSetParameterType vrspt on vrspt.classname='30_3600'
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
left join Patient p on p.id=sls.patient_id
left join VocPreAdmissionTime vpat on vpat.id=sls.preAdmissionTime_id
where 
sls.dtype='HospitalMedCase' and sls.dateFinish 
between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and coalesce(
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_cl}'
    and vpd.id='${diag_priority_m}'
    )
    ) between rspt.codefrom and rspt.codeto 

and vdrt.id='${diag_typeReg_cl}' and vpd.id='${diag_priority_m}'
${departmentSql}
group by vrspt.id,vrspt.name,vrspt.strCode,vrspt.code ${tableGroup}
order by vrspt.strCode ${tableOrder}"
		/>
		  <form action="print-stac_journal_surgical_help_${typeView}.do" method="post" target="_blank">
	    Экстренная хирургическая помощь
	    <input type='hidden' name="sqlText" id="sqlText" value="${list_surgical_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Экстренная хирургическая помощь за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>  
			
		</msh:sectionTitle>
		<msh:sectionContent>
		
		<msh:table name="list_surgical" action="stac_journal_surgical_help.do?typeView=1&typeReestr=1&department=${param.department}&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}" idField="1">
			<msh:tableColumn property="2" columnName="Диагноз"/>
			<msh:tableColumn property="3" columnName="Код по МКБ"/>
			<msh:tableColumn property="4" cssClass="spokDost" columnName="Сроки доставки"/>
			<msh:tableColumn property="5" columnName="Всего"/>
			<msh:tableColumn property="6" columnName="из них умерло"/>
			<msh:tableColumn property="7" columnName="не опер. всего"/>
			<msh:tableColumn property="8" columnName="не опер. умерло"/>
			<msh:tableColumn property="9" columnName="опер. всего"/>
			<msh:tableColumn property="10" columnName="опер. умерло"/>
		</msh:table>
		</msh:sectionContent><pre>${list_surgical_sql}</pre>
	</msh:section>
        <%
    	
    } else if (typeView.equals("3")) {
        %>
	<msh:section>
		<msh:sectionTitle>
		${isReportBase}
		<ecom:webQuery isReportBase="${isReportBase}"  name="list_surgical" nameFldSql="list_surgical_sql"
		nativeSql="select mkb.id as mkbid,mkb.code as mkbcode,
		mkb.name as mkbname,count(distinct sls.id) as cntAll
,count(distinct case when sls.result_id='${result_death}' then sls.id else null end) as cntDeath

,count(distinct sls.id)-count(distinct case when so.id is not null then sls.id else null end) as cntNoOperAll
,count(distinct case when sls.result_id='${result_death}' then sls.id else null end)
-count(distinct case when so.id is not null and sls.result_id='${result_death}' then sls.id else null end) as cntNoOperDeath

,count(distinct case when so.id is not null then sls.id else null end) as cntOperAll
,count(distinct case when so.id is not null and sls.result_id='${result_death}' then sls.id else null end) as cntOperDeath
 from medcase sls
left join diagnosis diag on sls.id=diag.medcase_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join MedCase sloa on sloa.parent_id=sls.id
left join MedCase sloall on sloall.parent_id=sls.id
left join SurgicalOperation so on so.medCase_id=sloall.id
left join BedFund bf on bf.id=sloa.bedFund_id
left join Patient p on p.id=sls.patient_id
where 
sls.dtype='HospitalMedCase' and sls.dateFinish 
between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
    and sls.dateFinish is not null

and coalesce(
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_pat}'
    and vpd.id='${diag_priority_m}'
    )
    ,
    (select max(mkb.code) from Diagnosis diag 
    left join vocidc10 mkb on mkb.id=diag.idc10_id
    left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    where sls.id=diag.medcase_id  
    and vdrt.id='${diag_typeReg_cl}'
    and vpd.id='${diag_priority_m}'
    )
    ) between 'C00' and 'C97.999'
and vdrt.id='${diag_typeReg_cl}' and vpd.id='${diag_priority_m}'
${departmentSql}
group by mkb.id,mkb.code,mkb.name
order by mkb.code"
		/>
		  <form action="print-stac_journal_surgical_help_${typeView}.do" method="post" target="_blank">
	    Экстренная хирургическая помощь
	    <input type='hidden' name="sqlText" id="sqlText" value="${list_surgical_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Выписанные с диагнозом злокачественным новообразования за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>  
			
		</msh:sectionTitle>
		<msh:sectionContent>
		
		<msh:table name="list_surgical" action="stac_journal_surgical_help.do?typeView=1&typeReestr=1&department=${param.department}&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}" idField="1">
			<msh:tableColumn property="2" columnName="Код по МКБ"/>
			<msh:tableColumn property="3" columnName="Диагноз"/>
			<msh:tableColumn property="4" isCalcAmount="true" columnName="Всего"/>
			<msh:tableColumn property="5" isCalcAmount="true" columnName="из них умерло"/>
			<msh:tableColumn property="6" isCalcAmount="true" columnName="не опер. всего"/>
			<msh:tableColumn property="7" isCalcAmount="true" columnName="не опер. умерло"/>
			<msh:tableColumn property="8" isCalcAmount="true" columnName="опер. всего"/>
			<msh:tableColumn property="9" isCalcAmount="true" columnName="опер. умерло"/>
		</msh:table>
		</msh:sectionContent>
	</msh:section>
        
        
        <%
    	
    }
    %>
    <%
	
} else {
	  out.println("<i>Выберите параметры и нажмите кнопку \"Найти\"</i>");
} %>
  
    

  </tiles:put>
</tiles:insert>