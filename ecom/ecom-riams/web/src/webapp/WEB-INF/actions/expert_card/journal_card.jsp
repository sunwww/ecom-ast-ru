<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.util.PropertyUtil"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<style>
h2 {display:none;}
@media print {
h2 {display:inline;}
img,div#copyright,h1,ul#ideModeMainMenu, div#ideModeMainMenuClose {display:none;}
input#beginDate{display:inline;}
div.x-box-mc{display:none;}
div#header{display:none;}
}
</style>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Disability">Журнал внутреннего контроля качества</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="journalOpenKER"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
	String estimationKind = request.getParameter("estimationKind");
	String dateEnd = request.getParameter("dateEnd");
	String dateStart = request.getParameter("dateBegin");
    String typeOrder = ActionUtil.updateParameter("QualityEstimationCard","typeOrder","1", request) ;
    String typeMarks = ActionUtil.updateParameter("QualityEstimationCard","typeMarks","3", request) ;
    String typeReport = ActionUtil.updateParameter("QualityEstimationCard","typeReport","1", request) ;
    String typeEstimation = ActionUtil.updateParameter("QualityEstimationCard","typeEstimation","3", request) ;

   %>
    <msh:form action="quality_card_journal.do" defaultField="estimationKindName"  method="get" >
    <msh:panel>
          <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
       <msh:row>
        <msh:autoComplete property="estimationKind" vocName="vocQualityEstimationKind" label="Тип оценки качества" fieldColSpan="30" size="50" />
      </msh:row> 
       <msh:row>
        <msh:autoComplete property="department" vocName="lpu" label="Отделение" fieldColSpan="30" size="50" />
      </msh:row> 
       <msh:row>
        <msh:autoComplete property="workFunction" vocName="workFunctionByLpu" parentAutocomplete="department" label="Врач" fieldColSpan="30" size="50" />
      </msh:row> 
       <msh:row>
        <msh:autoComplete property="expert" vocName="workFunctionAllForExpert" label="Эксперт" fieldColSpan="30" size="50" />
      </msh:row> 
      <msh:row>
        <msh:textField property="dateBegin" />
        <msh:textField property="dateEnd" />
      </msh:row>
         <msh:row>
      <td><label>Тип отчета:</label></td>
      <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="1">  реестр по пациентам
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="2" >  группировка по леч.врачу
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="3" >  группировка по отделению
        </td>
      </msh:row>
      <msh:row>
      <td></td> 
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="4" > по показателям
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="5" > по эксперту
        </td>
      </msh:row> 
      
      <msh:row>
      <td><label>Сортировать по:</label></td>
      <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeOrder" value="1">  по ФИО пациента
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeOrder" value="2" >  по дате экспертизы
        </td>
      </msh:row>
      <msh:row>
      <td><label>Отображать:</label></td>
      <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeMarks" value="1">оценки эксперта
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeMarks" value="2" >оценки заведующего
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeMarks" value="3" >  все оценки
        </td>
      </msh:row>
       <msh:row>
           <td colspan="11">
            <input type="submit"  value="Найти"  onclick="find()"/>
			<input type="submit" onclick="printQuarterlyReport()" value="Печать ежеквартального отчёта" />
          </td>
      </msh:row>
		<input type="hidden" value="HospitalPrintService" name="s"/>
		<input type="hidden" value="printQuarterlyReport" name="m"/>
      </msh:panel>
      </msh:form>
          <script type='text/javascript'>
    
    checkFieldUpdate('typeOrder','${typeOrder}',1) ;
    checkFieldUpdate('typeMarks','${typeMarks}',1) ;
    checkFieldUpdate('typeReport','${typeReport}',1) ;
    //checkFieldUpdate('typeLpu','${typeLpu}',3) ;
  
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
        frm.action='quality_card_journal.do' ;
    }

    function printQuarterlyReport() {
        var frm = document.forms[0] ;
        frm.target='_blank' ;
        frm.m.value='printQuarterlyReport' ;
        frm.action='print-quarterlyReport.do' ;
    }
      </script>
    	<% 
    	StringBuilder sqlAdd = new StringBuilder();
    	String orderBySql = "pat.patientinfo";
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd = dateStart;
    	}
    	
    	
    	
    	if (typeOrder!=null&&typeOrder.equals("2")) {
    		orderBySql="qec.createdate";
    	}
    	
    	
    	
    	if (dateStart!=null&&!dateStart.equals("") && estimationKind!=null &&
    			!estimationKind.equals("")&&!estimationKind.equals("0")) {
    		if (typeMarks!=null&&!typeMarks.equals("")) {
        		if (typeMarks.equals("1")) {
        			sqlAdd.append(" and qe.experttype='Expert'");
        		} else if (typeMarks.equals("2")) {
        			sqlAdd.append(" and qe.experttype='BranchManager'");
        		}
        	} 
    		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
        			, "отделение","department","wml.id", request));
        	sqlAdd.append(ActionUtil.getValueInfoById("select wf.id, vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename from workfunction wf left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join vocworkfunction vwf on wf.workfunction_id=vwf.id where wf.id=:id"
        			, "врач","workFunction","qec.doctorcase_id", request));
        	sqlAdd.append(ActionUtil.getValueInfoById("select wf.id, wp.lastname||' '||wp.firstname||' '||wp.middlename from workfunction wf left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join vocworkfunction vwf on wf.workfunction_id=vwf.id where wf.id=:id"
        			, "эксперт","expert","qe.expert_id", request));
        	request.setAttribute("finishDate", dateEnd);
        	request.setAttribute("sqlAdd", sqlAdd);
        	request.setAttribute("orderBySql",orderBySql);
        	StringBuilder title = new StringBuilder() ;
        	title.append("Период ").append(dateStart).append("-").append(dateEnd).append(request.getAttribute("departmentInfo"))
        		.append(" ").append(request.getAttribute("workFunctionInfo")) 
        		;
        	
            request.setAttribute("sqlAdd", sqlAdd.toString()) ;
            request.setAttribute("titleInfo", title.toString()) ;
			request.setAttribute("isReportBase", ActionUtil.isReportBase(dateStart,dateEnd,request));
    		%>
    	<ecom:webQuery name="critList" nativeSql="
    	select id,code,name from vocqualityestimationcrit where kind_id=${param.estimationKind} and parent_id is null order by code
    	"/>
    		<%
    		List critList=(List)request.getAttribute("critList") ;
    		request.setAttribute("countCrit", ""+critList.size()) ;
        	//String sql = 
        	if (typeReport!=null&&typeReport.equals("1")) {
        		StringBuilder sql = new StringBuilder() ;
        		for (int i=0;i<critList.size();i++) {
        			WebQueryResult wqr = (WebQueryResult)critList.get(i) ;
        			sql.append(",max(case when vqec.id = '").append(wqr.get1()).append("' then (vqem.mark||' '||(select list(vqecd.name) from qualityestimationcritdefect qecd left join vocqualityestimationcritdefect vqecd on vqecd.id=qecd.defect where qecd.criterion=qecr.id ))  else ''||0 end) as f").append(i+5).append("_def1") ;
        		}
        		request.setAttribute("critSql", sql.toString()) ;
    	%>
    	<ecom:webQuery isReportBase="${isReportBase}" name="card_list" nameFldSql="card_list_sql"
    	nativeSql="select qec.id
,to_char(qec.createdate,'dd.MM.yyyy') as f1_createDate
,vwf.name ||' '||wpat.lastname ||' ' || wpat.firstname||' '||wpat.middlename ||' '|| 	wml.name as f2_dep_doctor
,pat.patientinfo ||' (№'||coalesce(ss.code,pat.patientSync)||')' as f3_patient
,mkb.code as f4_diagnosis
${critSql}
,round(cast(sum (vqem.mark)/count(vqec.id) as numeric),2) as f5_average
from qualityestimationcard qec
left join vocidc10 mkb on mkb.id=qec.idc10_id
left join workfunction wf on wf.id=qec.doctorcase_id
left join worker w on w.id=wf.worker_id
left join mislpu wml on wml.id=w.lpu_id
left join patient wpat on wpat.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join qualityestimation qe on qe.card_id=qec.id
left join qualityestimationcrit qecr on qecr.estimation_id=qe.id
left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id
left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  
left join medcase sls on sls.id=qec.medcase_id
left join medcase sls2 on sls2.id=sls.parent_id
left join patient pat on pat.id=sls.patient_id
left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)

where  qec.createDate between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
and qec.kind_id=${param.estimationKind}
${sqlAdd}
group by qec.id,qec.createdate ,wml.name ,vwf.name,wpat.lastname, wpat.firstname,wpat.middlename ,ss.code,pat.patientinfo ,mkb.code,pat.patientSync 
order by ${orderBySql}
"
    	/>
    	
    	<h2>Журнал внутреннего контроля качества оказания медицинской помощи. ${titleInfo}</h2> 
    	<input type='button' onclick="mshSaveTableToExcelById('expertCardReport')" value='Сохранить в excel'>
    	<table id='expertCardReport' border="1px solid">
           <tr>
         		<th rowspan="2">#</th>
         		<th rowspan="2">Дата проведения контроля</th>
         		<th rowspan="2">ФИО врача/отделение</th>
         		<th rowspan="2">ФИО пациента, номер карты</th>
         		<th rowspan="2">Диагноз по МКБ-10</th>
         		<th colspan="${countCrit}">Дефекты</th>
         		<th rowspan="2">Интегрированная оценка качества оказания мед. помощи</th>
         	</tr>
         	<tr>
         	<%
    		for (int i=0;i<critList.size();i++) {
    			WebQueryResult wqr = (WebQueryResult)critList.get(i) ;
    			out.print("<th>") ;out.print(wqr.get2()) ;out.print(" ") ;out.print(wqr.get3()) ;out.print("</th>") ;
    		}
 	
         	%>
         	</tr>
         	<%
         	List card_list = (List) request.getAttribute("card_list") ;
    		for (int i=0;i<card_list.size();i++) {
    			WebQueryResult wqr = (WebQueryResult)card_list.get(i) ;
    			out.print("<tr>") ;
    			out.print("<td>");
    			out.print(i+1);
    			out.print("</td>") ;
    			
    			for (int j=1;j<critList.size()+6;j++) {
    				out.print("<td>") ;
    				Object value = PropertyUtil.getPropertyValue(wqr, ""+(j+1)) ;
    				out.print(value) ;
    				out.print("</td>") ;
    			}
    			out.print("</tr>") ;
    		}
 	
         	%>
    	</table>
    	<%
    	
        	} else if (typeReport!=null && (typeReport.equals("2")||typeReport.equals("3")||typeReport.equals("5"))) {
        		StringBuilder sql = new StringBuilder() ;
        		for (int i=0;i<critList.size();i++) {
        			WebQueryResult wqr = (WebQueryResult)critList.get(i) ;
        			sql.append(",round(cast(avg(case when vqec.id = '").append(wqr.get1()).append("' then vqem.mark else null end) as numeric),2) as f").append(i+5).append("_def1") ;
        		}
        		request.setAttribute("critSql", sql.toString()) ;
        		if (typeReport.equals("2")) {
        			request.setAttribute("groupBy", "wf.id,wml.name ,vwf.name,wpat.lastname, wpat.firstname,wpat.middlename") ;
        			request.setAttribute("nameFldId", "wf.id") ;
        			request.setAttribute("nameFld", "vwf.name ||' '||wpat.lastname ||' ' || wpat.firstname||' '||wpat.middlename ||' '|| 	wml.name") ;
        			request.setAttribute("nameTitle", "ФИО врача/отделение") ;
        			request.setAttribute("orderBySql", "wpat.lastname, wpat.firstname,wpat.middlename") ;
        		} else if (typeReport.equals("3")) {
        			request.setAttribute("groupBy", "wml.id,wml.name ") ;
        			request.setAttribute("nameFldId", "wml.id") ;
        			request.setAttribute("nameFld", "wml.name") ;
        			request.setAttribute("nameTitle", "ФИО врача/отделение") ;
        			request.setAttribute("orderBySql", "wml.name") ;
        		} else if (typeReport.equals("5")) { //Группировка по эксперту
        			request.setAttribute("groupBy", "wfExp.id, vwfExp.name, patExp.lastname, patExp.firstname, patExp.middlename") ;
        			request.setAttribute("nameFldId", " wfExp.id") ;
        			request.setAttribute("nameFld", "vwfExp.name||' '||patExp.lastname||' '|| patExp.firstname ||' '|| patExp.middlename") ;
        			request.setAttribute("nameTitle", "ФИО эксперта") ;
        			request.setAttribute("orderBySql", "patExp.lastname, patExp.firstname, patExp.middlename") ;
        		}
%>

    	<ecom:webQuery isReportBase="${isReportBase}" name="card_list" nameFldSql="card_list_sql"
    	nativeSql="select ${nameFldId}
,${nameFld} as f2_dep_doctor
,count(distinct qe.id) as f3_cntExp
${critSql}
,round(cast(sum (vqem.mark)/count(vqec.id) as numeric),2) as f5_average
from qualityestimationcard qec
left join vocidc10 mkb on mkb.id=qec.idc10_id
left join workfunction wf on wf.id=qec.doctorcase_id
left join worker w on w.id=wf.worker_id
left join mislpu wml on wml.id=w.lpu_id
left join patient wpat on wpat.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join qualityestimation qe on qe.card_id=qec.id

left join workfunction wfExp on wfExp.id=qe.expert_id
left join worker wExp on wExp.id=wfExp.worker_id
left join vocworkfunction vwfExp on vwfExp.id=wfExp.workfunction_id
left join patient patExp on patExp.id=wExp.person_id
left join qualityestimationcrit qecr on qecr.estimation_id=qe.id
left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id
left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  
left join medcase sls on sls.id=qec.medcase_id
left join medcase sls2 on sls2.id=sls.parent_id
left join patient pat on pat.id=sls.patient_id
left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)

where  qec.createDate between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
and qec.kind_id='${param.estimationKind}'
${sqlAdd}
group by ${groupBy} 
order by ${orderBySql}
"
    	/>
    	<h2>Журнал внутреннего контроля качества оказания медицинской помощи. ${titleInfo}</h2>
		<input type='button' onclick="mshSaveTableToExcelById('journalExpertTable')" value='Сохранить в excel'>
    	<table border="1px solid" id="journalExpertTable">
           <tr>
         		<th rowspan="2">#</th>
         		<th rowspan="2">${nameTitle}</th>
         		<th rowspan="2">Кол-во экспертиз</th>
         		<th colspan="${countCrit}">Критерии оценки качества медицинской помощи (оценка в баллах от 0 до 1,0)</th>
         		<th rowspan="2">Интегрированная оценка качества оказания мед. помощи</th>
         	</tr>
         	<tr>
         	<%
    		for (int i=0;i<critList.size();i++) {
    			WebQueryResult wqr = (WebQueryResult)critList.get(i) ;
    			out.print("<th>") ;out.print(wqr.get2()) ;out.print(" ") ;out.print(wqr.get3()) ;out.print("</th>") ;
    		}
 	
         	%>
         	</tr>
         	<%
         	List card_list = (List) request.getAttribute("card_list") ;
    		for (int i=0;i<card_list.size();i++) {
    			WebQueryResult wqr = (WebQueryResult)card_list.get(i) ;
    			out.print("<tr>") ;
    			out.print("<td>");
    			out.print(i+1);
    			out.print("</td>") ;
    			
    			for (int j=1;j<critList.size()+4;j++) {
    				out.print("<td>") ;
    				Object value = PropertyUtil.getPropertyValue(wqr, ""+(j+1)) ;
    				out.print(value) ;
    				out.print("</td>") ;
    			}
    			out.print("</tr>") ;
    		}
 	
         	%>
    	</table>
    	<%
    	
        	} else if (typeReport!=null && (typeReport.equals("4"))) {
        		%>
            	<ecom:webQuery name="cntCards" nativeSql="
select count(distinct qe.id) as f1_cntExp
, count(distinct qe.id) - count(distinct case when vqem.mark='1' then null else qe.id end) as f4_cntExp

from qualityestimationcard qec
left join vocidc10 mkb on mkb.id=qec.idc10_id
left join workfunction wf on wf.id=qec.doctorcase_id
left join worker w on w.id=wf.worker_id
left join mislpu wml on wml.id=w.lpu_id
left join patient wpat on wpat.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join qualityestimation qe on qe.card_id=qec.id
left join qualityestimationcrit qecr on qecr.estimation_id=qe.id
left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id
left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  
left join medcase sls on sls.id=qec.medcase_id
left join medcase sls2 on sls2.id=sls.parent_id
left join patient pat on pat.id=sls.patient_id
left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)

where  qec.createDate between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
and qec.kind_id='${param.estimationKind}'
${sqlAdd}
            	"/>
            		<%
            		List cntCards=(List)request.getAttribute("cntCards") ;
        		if (cntCards.size()>0) {
        			WebQueryResult wqr = (WebQueryResult)cntCards.get(0) ;
        			request.setAttribute("cntCards_del", wqr.get1()) ;
        			request.setAttribute("cntCards_view", wqr.get1()) ;
        			request.setAttribute("cntCards_view_no_def", wqr.get2()) ;
        		} else {
        			request.setAttribute("cntCards_del", "1") ;
        			request.setAttribute("cntCards_view", "0") ;
        			request.setAttribute("cntCards_view_no_def", "0") ;
        		}
%>

    	<ecom:webQuery isReportBase="${isReportBase}" name="card_list" nameFldSql="card_list_sql"
    	nativeSql="select vqec.id as f1_name_id
    	,vqec.code as f2_cntExp
    	,vqec.name as f3_name_crit
,count(distinct case when vqem.mark='1' then null else qec.id end) as f4_cntExp
,round(cast((100*count(distinct case when vqem.mark='1' then null else qec.id end)/'${cntCards_del}') as numeric),2) as f5_cntExp
,round(cast(avg (vqem.mark) as numeric),2) as f6_average
from qualityestimationcard qec
left join vocidc10 mkb on mkb.id=qec.idc10_id
left join workfunction wf on wf.id=qec.doctorcase_id
left join worker w on w.id=wf.worker_id
left join mislpu wml on wml.id=w.lpu_id
left join patient wpat on wpat.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join qualityestimation qe on qe.card_id=qec.id
left join qualityestimationcrit qecr on qecr.estimation_id=qe.id
left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id
left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  
left join medcase sls on sls.id=qec.medcase_id
left join medcase sls2 on sls2.id=sls.parent_id
left join patient pat on pat.id=sls.patient_id
left join statisticstub ss on ss.medcase_id=coalesce(sls2.id,sls.id)

where  qec.createDate between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
and qec.kind_id='${param.estimationKind}'
${sqlAdd}
group by vqec.id ,vqec.code,vqec.name
order by vqec.code
"
    	/>
    	
    	<h2>Журнал внутреннего контроля качества оказания медицинской помощи. ${titleInfo}</h2>
    	<h3>Кол-во проведенных экспертиз: ${cntCards_view}</h3>
    	<h3>из них без дефектов: ${cntCards_view_no_def}</h3>
		<input type='button' onclick="mshSaveTableToExcelById('journalExpertTable2')" value='Сохранить в excel'>
    	<table border="1px solid" id="journalExpertTable2">
           <tr>
         		<th>Код</th>
         		<th>Наименование дефекта</th>
         		<th>кол-во выявленных дефектов</th>
         		<th>% от кол-ва проведенных экспертиз</th>
         		<th>Показатель качества (в баллах)</th>
         	</tr>
         	<%
         	List card_list = (List) request.getAttribute("card_list") ;
    		for (int i=0;i<card_list.size();i++) {
    			WebQueryResult wqr = (WebQueryResult)card_list.get(i) ;
    			out.print("<tr>") ;
    			out.print("<td>");
    			out.print(i+1);
    			out.print("</td>") ;
    			
    			for (int j=2;j<6;j++) {
    				out.print("<td>") ;
    				Object value = PropertyUtil.getPropertyValue(wqr, ""+(j+1)) ;
    				out.print(value) ;
    				out.print("</td>") ;
    			}
    			out.print("</tr>") ;
    		}
 	
         	%>
    	</table>


<%        		
        	}
    	} else {
    		%>
         	<i>Выберите параметры</i>
         	<%
    	}
    	%>
    
    </tiles:put>

</tiles:insert>