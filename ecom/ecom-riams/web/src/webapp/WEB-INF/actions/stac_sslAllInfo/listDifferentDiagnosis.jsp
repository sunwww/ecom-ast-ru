<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Список всех ССЛ с расхождением диагнозов</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/MedCase" key="ALT+N" action="/" name="По отделению" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
     
 <ecom:webQuery name="diag_typeReg_vip_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='3'"/>
 <ecom:webQuery name="diag_typeReg_klin_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='4'"/>
 <ecom:webQuery name="diag_mainPriority_sql" nativeSql="select id,name from VocPrimaryDiagnosis where code='1'"/>
 
  <%
  String department = (String) request.getParameter("department");
  String typeDate=ActionUtil.updateParameter("Report14","typeDate","2", request) ;
	String startDate = (String) request.getParameter("dateBegin");
	String finishDate = (String) request.getParameter("dateEnd");
  String clinicalMkbName = "ml.name || '<br> '|| mkb2.code||' ' ||mkb2.name";
	String addSql = "";
  if (startDate!=null&&!startDate.equals("")) {
	  if (typeDate!=null&&!typeDate.equals("")&&typeDate.equals("1")) {
		  typeDate = "sls.datestart";	  
	  } else {
		  typeDate = "sls.datefinish";	
	  }
	  if (finishDate!=null&&!finishDate.equals("")) {
		  addSql+=" and "+typeDate+" between to_date('"+startDate+"','dd.MM.yyyy') and to_date('"+finishDate+"','dd.MM.yyyy')";
	  } else {
		  addSql += " and "+typeDate+">=to_date('"+startDate+"','dd.MM.yyyy')";
	  }
  } else {
	  addSql +=" and sls.datefinish=current_date";
  }
  if (department!=null&&!department.equals("")) {
	  addSql+=" and slo.department_id="+department;
	  clinicalMkbName ="mkb2.code||' ' ||mkb2.name";
  }
	request.setAttribute("addSql", addSql);
	request.setAttribute("clinicalMkbName", clinicalMkbName);
	ActionUtil.getValueByList("diag_typeReg_klin_sql", "clinicalTypeId", request) ;
	String clinicalTypeId = (String)request.getAttribute("clinicalTypeId") ;
	
  	ActionUtil.getValueByList("diag_typeReg_vip_sql", "dischargeTypeId", request) ;
	//String dischargeTypeId = (String)request.getAttribute("dischargeTypeId") ;
	
  	ActionUtil.getValueByList("diag_mainPriority_sql", "mainPriorityId", request) ;
	//String mainPriorityId = (String)request.getAttribute("mainPriorityId") ;
	
  	
 %>
 
 <msh:form action="/stac_diff_diagnosis.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
     <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
               <msh:row>
        <td class="label" title="Период поиска (typeDate)" colspan="1"></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  Дата начала госпитализации
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="2">  Дата окончания госпитализации
        </td>
       
        </msh:row>
         
         <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" /></msh:row><msh:row>
        <msh:autoComplete property="department" vocName="vocLpuHospOtdAll" label="Отделение" fieldColSpan="6" size="50"/>
           </msh:row><msh:row><td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
      </msh:row>
    <ecom:webQuery isReportBase="true" name="datelist" nameFldSql="datelist_sql" nativeSql="
select sls.id, ss.code
,pat.patientinfo
, mkb1.code||' ' ||mkb1.name as dischargedMkb, ${clinicalMkbName} as clinicalMkb 
from medcase sls
left join statisticstub ss on ss.medcase_id = sls.id
left join patient pat on pat.id=sls.patient_id  
left join medcase slo on slo.parent_id=sls.id
left join diagnosis d1 on d1.medcase_id=sls.id
left join vocidc10 mkb1 on mkb1.id=d1.idc10_id
left join mislpu ml on ml.id=slo.department_id
left join diagnosis d2 on d2.medcase_id=slo.id
left join vocidc10 mkb2 on mkb2.id=d2.idc10_id

where sls.dtype='HospitalMedCase'
and sls.datefinish is not null
and slo.datefinish is not null
and d1.registrationtype_id=${dischargeTypeId}
and d2.registrationtype_id=${clinicalTypeId}
and d1.priority_id=${mainPriorityId}
and d2.priority_id=${mainPriorityId}
and mkb1.id!=mkb2.id
${addSql}
" />
    <msh:section>
    <msh:sectionContent>
    <msh:table viewUrl="entityShortView-stac_ssl.do" name="datelist" idField="1" action="entityView-stac_ssl.do">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="2" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
      <msh:tableColumn columnName="Клинический диагноз" property="5" />
      <msh:tableColumn columnName="Выписной диагноз" property="4" />
    </msh:table>

    </msh:sectionContent>
    </msh:section>
    </msh:panel>

</msh:form>
  <script type='text/javascript'>
  checkFieldUpdate('typeDate','${typeDate}',2) ;

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
  </tiles:put>
  

  
</tiles:insert>

