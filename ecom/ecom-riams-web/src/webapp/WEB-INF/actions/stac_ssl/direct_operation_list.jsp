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
    <msh:form action="/direct_operation_list.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    <input type="hidden" value="printDeathList" name="m">
    <input type="hidden" value="HospitalReportService" name="s">
    <input type="hidden" value="" name="param">

        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="6" horizontalFill="true" size="50" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
	        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
		<td colspan="3">
            <input type="submit" onclick="find()" value="Найти" />
<!--            <input type="submit" onclick="print()" value="Печать" />-->
          </td>
         </msh:row>
        
    </msh:panel>
    </msh:form>
    
    <%
    
    String date = (String)request.getParameter("dateBegin") ;
    //String view = (String)request.getAttribute("typeView") ;
    if (date!=null && !date.equals("") )  {
    	
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd=date ;
    	}
		request.setAttribute("dateEnd", dateEnd) ;
		
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	String dep = (String) request.getParameter("department") ; 
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("dep", " and wN.lpu_id='"+dep+"'");
    		request.setAttribute("depOper", " and wN.lpu_id='"+dep+"'");
    	} else{
    		request.setAttribute("dep", "") ;
    	}
    	   	 
    %>    
    <msh:section title="Журнал направлений на хир. операции. Период с ${param.dateBegin} по ${dateEnd}.">
    <msh:sectionContent>
    <ecom:webQuery name="journal_list_suroper" nameFldSql="journal_list_suroper_sql" nativeSql=" 
select mc.id as medcaseid
,pat.patientinfo
,wf.id
,ms.code||' '||ms.name as oper_name
,wf.groupname
,to_char(wcd.calendardate,'dd.MM.yyyy') as oper_date
,cast(wct.timefrom as varchar(5)) as oper_time
,mlN.name||' (' ||wp.lastname||')' as naprInfo
,ss.code
,p.comments
from prescription p 
left join workcalendartime wct on wct.prescription=p.id
left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
left join prescriptionlist pl on pl.id=p.prescriptionlist_id
left join medcase mc on mc.id=pl.medcase_id
left join medcase mcP on mcP.id=mc.parent_id
left join patient pat on pat.id=mc.patient_id
left join medservice ms on ms.id=p.medservice_id
left join vocservicetype vst on vst.id=ms.servicetype_id
left join workfunction wf on wf.id=p.prescriptcabinet_id
left join workfunction wfN on wfN.id=p.prescriptspecial_id
left join worker wN on wN.id=wfN.worker_id
left join patient wp on wp.id=wN.person_id
left join mislpu mlN on mlN.id=wN.lpu_id
left join statisticstub ss on ss.id=coalesce(mc.statisticstub_id,mcP.statisticstub_id)
where wcd.calendardate between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
and vst.code='OPERATION'  
${dep}
order by wcd.calendardate, wct.timefrom
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_list_suroper"
         action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Пациент" property="2"/>
            <msh:tableColumn columnName="ИБ" property="9"/>
            <msh:tableColumn columnName="Направитель" property="8"/>
            <msh:tableColumn columnName="Операция" property="4"/>
            <msh:tableColumn columnName="Операционная" property="5"/>
            <msh:tableColumn columnName="Дата назначения" property="6"/>
            <msh:tableColumn columnName="Время назначения" property="7"/>
            <msh:tableColumn columnName="Примечание" property="10"/>
            
            
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    <script type='text/javascript'>
    
   /*   checkFieldUpdate('typeEmergency','${typeEmergency}',3) ; */
     
   
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

    </script>
  </tiles:put>
</tiles:insert>

