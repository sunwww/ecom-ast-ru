<%@page import="ru.nuzmsh.util.PropertyUtil"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Disability">Журнал внутреннего контроля качества</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="journalOpenKER"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
	String estimationType = request.getParameter("estimationKind");
	String dateEnd = request.getParameter("dateEnd");
	String dateStart = request.getParameter("dateBegin");
    String typeOrder = ActionUtil.updateParameter("QualityEstimationCard","typeOrder","1", request) ;
    String typeMarks = ActionUtil.updateParameter("QualityEstimationCard","typeMarks","3", request) ;
    String typeReport = ActionUtil.updateParameter("QualityEstimationCard","typeReport","1", request) ;
    String typeEstimation = ActionUtil.updateParameter("QualityEstimationCard","typeEstimation","3", request) ;
   %>
    <msh:form action="quality_card_journal.do" defaultField="estimationKindName"  method="get" >
    <msh:panel>
          <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
       <msh:row>
        <msh:autoComplete property="estimationKind" vocName="vocQualityEstimationKind" label="Тип оценки качества" fieldColSpan="30" size="50" />
      </msh:row> 
      <msh:row>
        <msh:textField property="dateBegin" />
        <msh:textField property="dateEnd" />
      </msh:row>
         <msh:row>
      <td><label>Тип отчета:</label></td>
      <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="1">  Журнал внутреннего контроля
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="2" >  Отчет о проведении контроля
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeReport" value="3" >  все
        </td>
      </msh:row> 
      <%-- 
        <msh:row>
      <td><label>Тип оценки качества:</label></td>
      <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeEstimation" value="1">  стационар
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeEstimation" value="2" >  поликлиника
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeEstimation" value="3" >  все
        </td>
      </msh:row>--%>
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
            <input type="submit"  value="Найти" />
          </td>
      </msh:row>
      </msh:panel>
      </msh:form>
          <script type='text/javascript'>
    
    checkFieldUpdate('typeOrder','${typeOrder}',1) ;
  //  checkFieldUpdate('typeEstimation','${typeEstimation}',1) ;
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
      </script>
    	<% 
    	String sqlAdd = "";
    	String orderBySql = "pat.patientinfo";
    	if (dateEnd==null||dateEnd.equals("")) {
    		dateEnd = dateStart;
    	}
    	
    	
    	
    	if (typeOrder!=null&&typeOrder.equals("2")) {
    		orderBySql="qec.createdate";
    	}
    	
    	if (typeMarks!=null&&!typeMarks.equals("")) {
    		if (typeMarks.equals("1")) {
    			sqlAdd+=" and qe.experttype='Expert'";
    		} else {
    			sqlAdd+=" and qe.experttype='BranchManager'";
    		}
    	} 
    	
    	request.setAttribute("finishDate", dateEnd);
    	request.setAttribute("sqlAdd", sqlAdd);
    	request.setAttribute("orderBySql",orderBySql);
    	//String sql = 
    	
    	if (dateStart!=null&&!dateStart.equals("") ) {
    		%>
    	<ecom:webQuery name="critList" nativeSql="
    	select id,code,name from vocqualityestimationcrit where kind_id=${param.estimationKind} and parent_id is null order by code
    	"/>
    		<%
    		List critList=(List)request.getAttribute("critList") ;
    		StringBuilder sql = new StringBuilder() ;
    		for (int i=0;i<critList.size();i++) {
    			WebQueryResult wqr = (WebQueryResult)critList.get(i) ;
    			sql.append(",max(case when vqec.id = '").append(wqr.get1()).append("' then (vqem.mark) else 0 end) as f").append(i+5).append("_def1") ;
    		}
    		request.setAttribute("critSql", sql.toString()) ;
    		request.setAttribute("countCrit", ""+critList.size()) ;
    	%>
    	<ecom:webQuery name="card_list" nameFldSql="card_list_sql"
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
    	
    	<h2>Журнал внутреннего контроля качества оказания медицинской помощи</h2> 
    	<table border="1px solid">
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
    	} else {
    		%>
         	<i>Выберите параметры</i>
         	<%
    	}
    	%>
    
    </tiles:put>

</tiles:insert>