<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name="style" type="string">
	
	</tiles:put>

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр назначений на операции
        </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_analysis_department"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/direct_operation_list.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    <input type="hidden" value="printDeathList" name="m">
    <input type="hidden" value="HospitalReportService" name="s">
    <input type="hidden" value="" name="param">

        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="6" horizontalFill="true" size="50" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="roomType" fieldColSpan="6" horizontalFill="true" size="50" label="Операционная" vocName="operationRoom"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
		<td colspan="3">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
         </msh:row>
        
    </msh:panel>
    </msh:form>
    
    <%
    
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals("") )  {
    	request.setAttribute("startDate", date);
    	String dateEnd = request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd=date ;
    	}
		request.setAttribute("finishDate", dateEnd) ;
		
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	String dep = request.getParameter("department") ;
    	String room = request.getParameter("roomType") ;
    	String sqlAdd="";
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    	    sqlAdd +=" and wN.lpu_id="+dep;
    	}
    	if (room!=null && !"".equals(room)) {
            sqlAdd +=" and p.prescriptCabinet_id="+room;
            request.setAttribute("cabinetId",room);
            request.setAttribute("cabinetName",request.getParameter("roomTypeName"));
        }
        request.setAttribute("dep", sqlAdd);

    %>    
    <msh:section title="Журнал направлений на хир. операции. Период с ${startDate} по ${finishDate}.">
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_list_suroper" nameFldSql="journal_list_suroper_sql" nativeSql="
select 
 mc.id as f1_medcaseid
,pat.lastname||' '|| substring(pat.firstname,1,1)||' '||substring(coalesce(pat.middlename,' '),1,1) ||
 ', '||to_char(pat.birthday,'yyyy')||'г.р. №' || coalesce(ss.code,'') as f2_patInfo
,wf.id as f3
,ms.code||' '||ms.name as oper_name
,wf.groupname as f5_groupname
,to_char(p.planStartDate,'dd.MM.yyyy') as f6_oper_date
,cast(p.planStartTime as varchar(5))||'-'||cast(p.planEndTime as varchar(5)) as f7_oper_time
,mlN.name||' (' ||wp.lastname||')' as f8_naprInfo
,p.comments as f9_comments
,cast('background:#F6D8CE;color:black;' as varchar) as f10_colorStyle
, p.id as f11_presId
, mkb.code||' '||mkb.name as f12_diagnosis
, va.name as f13_anastesia
, vbg.name||' '||vrf.name as f14_bloodInfo
, surgPat.lastname as f15_surgeonInfo
from prescription p
left join medservice ms on ms.id=p.medservice_id
left join vocservicetype vst on vst.id=ms.servicetype_id
left join workfunction wf on wf.id=p.prescriptCabinet_id
left join workfunction surgWf on surgWf.id=p.intakeSpecial_id
left join worker surgW on surgW.id=surgWf.worker_id
left join patient surgPat on surgPat.id=surgW.person_id
left join VocRhesusFactor vrf on vrf.id=p.rhesusFactor_id
left join VocBloodGroup vbg on vbg.id=p.bloodGroup_id
left join vocanesthesia va on va.id=p.anesthesiaType_id
left join prescriptionlist pl on pl.id=p.prescriptionlist_id
left join medcase mc on mc.id=pl.medcase_id
left join diagnosis d on d.medcase_id=mc.id and d.priority_id=1 and d.registrationType_id=4
left join vocidc10 mkb on mkb.id=d.idc10_id
left join medcase mcP on mcP.id=mc.parent_id
left join patient pat on pat.id=mc.patient_id
left join workfunction wfN on wfN.id=p.prescriptspecial_id
left join worker wN on wN.id=wfN.worker_id
left join patient wp on wp.id=wN.person_id
left join mislpu mlN on mlN.id=wN.lpu_id
left join statisticstub ss on ss.id=coalesce(mc.statisticstub_id,mcP.statisticstub_id)
where p.planStartDate between to_date('${startDate}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
and wf.dtype='OperatingRoom'
and vst.code='OPERATION'  
${dep}
order by p.planStartDate, p.planStartTime" />

        <msh:table printToExcelButton="сохранить в excel" styleRow='10' name="journal_list_suroper"
         action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Пациент" property="2"/>
            <msh:tableColumn columnName="Диагноз" property="12"/>
            <msh:tableColumn columnName="Операция" property="4"/>
            <msh:tableColumn columnName="Вид наркоза" property="13"/>
            <msh:tableColumn columnName="Время проведения" property="7"/>
            <msh:tableColumn columnName="Хирург" property="15"/>
            <msh:tableColumn columnName="Группа крови" property="14"/>
            <msh:tableColumn columnName="Направитель" property="8"/>
            <msh:tableColumn columnName="Операционная" property="5"/>
            <msh:tableColumn columnName="Дата назначения" property="6"/>
            <msh:tableColumn columnName="Примечание" property="9"/>
            <msh:tableButton buttonFunction="printCheckList" buttonShortName="Печать чек-листа" buttonName="Печать чек-листа" property="12"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
      <form action="print-reestOperationsByCabinet.do" method="post" target="_blank">
          <input type="hidden" name="id" id="id" value="${cabinetId}">
          <input type="hidden" name="date" id="date" value="${startDate}">
          <input type="hidden" name="sqlInfo" id="sqlInfo" value="${journal_list_suroper_sql}"/>
          <input type='hidden' name="s" id="s" value="HospitalPrintService">
          <input type='hidden' name="m" id="m" value="printOperationsByCabinet">
          <input type="submit" value="Печать реестра">
      </form>
    
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    <script type='text/javascript'>

    function checkFieldUpdate(aField,aValue,aDefault) {

    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    function getCheckedValue(aField) {
    	eval('var radioGrp =  document.forms[0].'+aField) ;
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++){
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		} 
  		return radioValue ;
  	}

    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='direct_operation_list.do' ;
    }
    function printCheckList(id) {
    	window.location='print-checkList.do?s=HospitalPrintService&m=printCheckList&id='+id;
    }

    </script>
  </tiles:put>
</tiles:insert>

