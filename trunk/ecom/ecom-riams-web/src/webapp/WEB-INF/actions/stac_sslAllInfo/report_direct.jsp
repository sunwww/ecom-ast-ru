<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Отчет по направленным"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String noViewForm = request.getParameter("noViewForm") ;

  	String typeEmergency=ActionUtil.updateParameter("ReportDirectByHospital","typeEmergency","3", request) ;
  	String typeDepartment=ActionUtil.updateParameter("ReportDirectByHospital","typeDepartment","2", request) ;
  	String typeDate=ActionUtil.updateParameter("ReportDirectByHospital","typeDate","2", request) ;
  	String typeGroup=ActionUtil.updateParameter("ReportDirectByHospital","typeGroup","2", request) ;
  	
  	
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/stac_report_direct_in_hospital.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport13" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="1"> поступления  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Искать по отделению (typeDepartment)" colspan="1"><label for="typeDepartmentName" id="typeDepartmentLabel">Отделение:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDepartment" value="1"> поступления  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDepartment" value="2">  выписки
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Группировать (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировать:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="1"> отделению  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeGroup" value="2">  кем направлен
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Доставлен по показаниям (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания к поступлению:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="1">  экстренно
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="2"  >  планово
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="3"  >  все
        </td>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4" horizontalFill="true" label="Поток обслуживания" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lpuDirect" fieldColSpan="4" horizontalFill="true" label="ЛПУ направителя" vocName="mainLpu"/>
        </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%} %>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
           checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
           checkFieldUpdate('typeGroup','${typeGroup}',2) ;
           checkFieldUpdate('typeDate','${typeDate}',2) ;
           checkFieldUpdate('typeDepartment','${typeDepartment}',2) ;

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
    	frm.action='journal_doc_externalMedService.do' ;
    }
   
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printHistology" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_histology.do' ;
    	$('id').value = $('dateBegin').value+":"
    		+$('dateBegin').value+":"
    		+$('department').value;
    }/*
    function printJournal() {
    	var frm = document.forms[0] ;
    	frm.m.value="printJournalByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_journal001.do' ;
    	$('id').value = 
    		$('dateBegin').value+":"
    		
    		+$('department').value;
    }
    */
    /**/
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;
    //String id = (String)request.getParameter("id") ;
    String period = request.getParameter("period") ;
    String strcode =request.getParameter("strcode") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
  	String dep = request.getParameter("department") ; 
  	if (dep!=null&&!dep.equals("")&&!dep.equals("0")) {
  		if (typeDepartment!=null && typeDepartment.equals("1")) {
  			request.setAttribute("department", " and sls.department_id='"+dep+"'") ;
  		} else {
  			request.setAttribute("department", " and slo.department_id='"+dep+"'") ;
  		}
  	}
  	String lpuDirect = request.getParameter("lpuDirect") ; 
  	if (lpuDirect!=null&&!lpuDirect.equals("")&&!lpuDirect.equals("0")) {
		request.setAttribute("lpuDirectSql", " and sls.orderLpu_id='"+lpuDirect+"'") ;
  	}
  	if (typeGroup!=null && typeGroup.equals("1")) {
  		request.setAttribute("typeGroupId", "dep.id") ;
  		request.setAttribute("typeGroupName", "dep.name") ;
  		request.setAttribute("typeGroupColumn", "department") ;
  	} else {
  		request.setAttribute("typeGroupId", "ml.id") ;
  		request.setAttribute("typeGroupName", "ml.name") ;
  		request.setAttribute("typeGroupColumn", "lpuDirect") ;
  	}
  	String serviceStream = request.getParameter("serviceStream") ; 
  	if (serviceStream!=null&&!serviceStream.equals("")&&!serviceStream.equals("0")) {
		request.setAttribute("serviceStreamSql", " and sls.serviceStream_id='"+serviceStream+"'") ;
  	}
    if (typeDate!=null && typeDate.equals("1")) {
    	request.setAttribute("dateSql", "sls.dateStart") ;
    } else {
    	request.setAttribute("dateSql", "slo.dateFinish") ;
    }
    if (typeEmergency!=null && typeEmergency.equals("1")) {
    	request.setAttribute("emergencySql", " and sls.emergency='1'") ;
    } else if (typeEmergency!=null && typeEmergency.equals("2")) {
    	request.setAttribute("emergencySql", " and (sls.emergency is null or sls.emergency='0')") ;
    } else {
    	
    }
    String view = (String)request.getAttribute("typeView") ;
    
    if (date!=null && !date.equals("")) {
        
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод ${reportInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="report_direct_swod" nativeSql="
select '&department=${param.department}&serviceStream=${param.serviceStream}&lpuDirect='||${typeGroupId},coalesce(${typeGroupName},'Без направления'),
count(*) 
,count(case when sls.emergency='1' then sls.id else null end) as cntEmergency
,count(case when sls.emergency='1' and of.voc_code='А' then sls.id else null end) as cntEmerVrAmb
,count(case when sls.emergency='1' and of.voc_code='В' then sls.id else null end) as cntEmerRVK
,count(case when sls.emergency='1' and of.voc_code='К' then sls.id else null end) as cntEmerAmb
,count(case when sls.emergency='1' and of.voc_code='О' then sls.id else null end) as cntEmerSam 
,count(case when sls.emergency='1' and of.voc_code='П' then sls.id else null end) as cntEmerPoly
,count(case when sls.emergency='1' and of.voc_code='С' then sls.id else null end) as cntEmerStac
,count(case when (sls.emergency is null or sls.emergency='0') then sls.id else null end) as cntPlan
,count(case when (sls.emergency is null or sls.emergency='0') and vht.code='POLYCLINIC' then sls.id else null end) as cntPlanPoly

from medcase sls
left join medcase slo on slo.parent_id=sls.id
left join misLpu ml on ml.id=sls.orderLpu_id
left join misLpu dep on dep.id=sls.department_id
left join omc_frm of on of.id=sls.orderType_id
left join vocServiceStream vss on vss.id=sls.serviceStream_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
where slo.dtype='DepartmentMedCase'
and ${dateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
${department} ${emergencySql} ${lpuDirectSql} ${serviceStreamSql}
group by ${typeGroupId},${typeGroupName}
order by ${typeGroupName}
" />
    <msh:table name="report_direct_swod" 
    viewUrl="stac_report_direct_in_hospital.do?typeDate=${typeDate}&typeEmergency=${typeEmergency}&typeDepartment=${typeDepartment}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_direct_in_hospital.do?typeDate=${typeDate}&typeEmergency=${typeEmergency}&typeDepartment=${typeDepartment}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
     <msh:tableNotEmpty>
     	<tr>
     		<th></th>
     		<th></th>
     		<th></th>
     		<th></th>
     		<th colspan="6" class="rigthBold">из них доставлено</th>
     		<th></th>
     		<th class="rigthBold">из них</th>
     	</tr>
     </msh:tableNotEmpty>
      <msh:tableColumn columnName="Наименование ЛПУ" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" isCalcAmount="true" />
      <msh:tableColumn property="4" columnName="Кол-во экстренных" isCalcAmount="true"/>
      <msh:tableColumn columnName="врач. амбулаторий" property="5" isCalcAmount="true"/>
      <msh:tableColumn columnName="коммисий РВК" property="6" isCalcAmount="true"/>
      <msh:tableColumn columnName="карета скорой помощи" property="7" isCalcAmount="true"/>
      <msh:tableColumn columnName="самообращение" property="8" isCalcAmount="true"/>
      <msh:tableColumn columnName="пол-ка" property="9" isCalcAmount="true"/>
      <msh:tableColumn columnName="стационар" property="10" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во плановых" property="11" isCalcAmount="true"/>
      <msh:tableColumn columnName="пол-ка" property="12" isCalcAmount="true"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%} else if (period!=null && !period.equals("")) {
    	
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
    select 
sls.id as slsid
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename
,cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end)
 as age,sls.dateStart,sls.dateFinish
, ml.name as mlname, dep.name as depname
 ,of.name as ofname, vss.name as vssname
from MedCase sls
left join patient p on p.id=sls.patient_id
left join MedCase slo on sls.id=slo.parent_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join misLpu ml on ml.id=sls.orderLpu_id
left join misLpu dep on dep.id=sls.department_id
left join omc_frm of on of.id=sls.orderType_id
left join vocServiceStream vss on vss.id=sls.serviceStream_id
where slo.dtype='DepartmentMedCase'
and ${dateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
${department} ${emergencySql} ${lpuDirectSql} ${serviceStreamSql}
order by p.lastname,p.firstname,p.middlename " />
    <msh:table name="journal_surOperation" 
    viewUrl="entityShortView-stac_ssl.do" 
     action="entityView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="№стат. карт" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Возраст" property="4" />
      <msh:tableColumn columnName="Дата поступления" property="5"/>
      <msh:tableColumn columnName="Дата выписки" property="6"/>
      <msh:tableColumn columnName="Кем направлен" property="7"/>
      <msh:tableColumn columnName="Отделение" property="8"/>
      <msh:tableColumn columnName="Кем доставлен" property="9"/>
      <msh:tableColumn columnName="Поток обслуживания" property="10"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    		<%
    	} else {%>
    	<i>Нет данных </i>
    	<% } %>
    
  </tiles:put>
</tiles:insert>