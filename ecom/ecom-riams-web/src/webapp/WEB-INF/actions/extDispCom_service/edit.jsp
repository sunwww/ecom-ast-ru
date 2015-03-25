<%@page import="java.util.List"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.Collection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<form action="javascript:void(0)"  id="mainForm" method="get" >
		<input type="hidden" value="${param.id}" name="card" id="card">
	<ecom:webQuery name="getServiceExam" nativeSql="
	select 
	
 max(case when eds.card_id=edc.id then eds.id else null end) as serviceid
, veds.id as vedsid,veds.code as vedscode,veds.name as vedsname 
,case when veds.isVisit='1' then 'ExtDispVisit' else 'ExtDispExam' end as edsdtype
, to_char(max(case when eds.card_id=edc.id then eds.serviceDate else edc.startdate end),'dd.mm.yyyy') as servicedate
, case when count(case when eds.card_id=edc.id and eds.isPathology='1' then '1' else null end)>0 then 'checked' else null end
as servcheck
 from ExtDispCard edc
left join Patient pat on pat.id=edc.patient_id
left join ExtDispPlan edp on edp.dispType_id=edc.dispType_id
left join ExtDispPlanService edps on edps.plan_id=edp.id
left join extdispservice eds on eds.dtype='ExtDispExam' and eds.card_id=edc.id
left join VocExtDispService veds on veds.id=edps.servicetype_id
where edc.id='${param.id}' 
and (eds.serviceType_id=edps.serviceType_id or eds.id is null)
and (edps.sex_id=pat.sex_id or edps.sex_id is null)
and veds.id is not null
and (veds.isVisit='0' or veds.isVisit is null)
group by veds.id,veds.code,veds.name,veds.isVisit
order by veds.id,veds.name"/>
	<ecom:webQuery name="getServiceVisit" nativeSql="
	select 
 max(eds.id) as serviceid
, veds.id as vedsid,veds.code as vedscode,veds.name as vedsname 
,case when veds.isVisit='1' then 'ExtDispVisit' else 'ExtDispExam' end as edsdtype
, to_char(max(case when eds.card_id=edc.id then eds.serviceDate else edc.startdate end),'dd.mm.yyyy') as servicedate
,list(eds.recommendation) as edsRecommendation
, case when count(case when eds.isEtdccSuspicion='1' then '1' else null end)>0 then 'checked' else null end
,veds.workfunctioncode 
,case when max(eds.workfunction_id) is not null then max(cast(eds.workfunction_id as varchar)) else 
	case when (max(eds.id)is null and count(wf1.id)='1') then max(cast(wf1.id as varchar)) else '' end end as edsWF

,case when max(eds.workfunction_id)is not null then max(vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename || ' '||ml.name) else 
	case when (max(eds.id)is null and count(wf1.id)='1') then max(vwf1.name||' '||wp1.lastname||' '||wp1.firstname||' '||wp1.middlename || ' '||ml1.name) else '' end end as wfname
,max(case when (eds.idc10_id is not null) then cast(mkb.id as varchar) else '' end) as mkb10
,max(case when (eds.idc10_id is not null) then mkb.code ||' '||mkb.name else '' end) as mkb10Name
from ExtDispCard edc
left join Patient pat on pat.id=edc.patient_id
left join ExtDispPlan edp on edp.dispType_id=edc.dispType_id
left join ExtDispPlanService edps on edps.plan_id=edp.id
left join extdispservice eds on eds.dtype='ExtDispVisit' and eds.card_id=edc.id
left join VocExtDispService veds on veds.id=edps.servicetype_id
left join WorkFunction wf on wf.id=eds.workfunction_id
left join VocWorkFunction vwf on vwf.id= wf.workfunction_id
left join Worker w on w.id=wf.worker_id
left join mislpu ml on ml.id=w.lpu_id
left join Patient wp on wp.id=w.person_id
left join Vocidc10 mkb on mkb.id=eds.idc10_id
left join VocWorkFunction vwf1 on vwf1.code = veds.workfunctioncode
left join WorkFunction wf1 on wf1.workfunction_id=vwf1.id and (wf1.archival is null or wf1.archival='0')
left join Worker w1 on w1.id=wf1.worker_id
left join mislpu ml1 on ml1.id=w1.lpu_id
left join Patient wp1 on wp1.id=w1.person_id
where edc.id='${param.id}' 
and (eds.serviceType_id=edps.serviceType_id or eds.id is null)
and (edps.sex_id=pat.sex_id or edps.sex_id is null)
and (veds.isVisit='1')
and veds.id is not null

group by veds.id,veds.code,veds.name
,veds.isVisit,veds.workfunctioncode
order by veds.id,veds.name

	"/>
	<ecom:webQuery maxResult="1" name="getCardWorkfunction" nativeSql="select vwf.code, wf.id, vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename || ' '||ml.name
	from extdispcard edc
	left join workfunction wf on wf.id=edc.workfunction_id 
	left join vocworkfunction vwf on vwf.id=wf.workfunction_id
	left join worker w on w.id=wf.worker_id
	left join mislpu ml on ml.id=w.lpu_id
	left join patient wp on wp.id=w.person_id
	where edc.id='${param.id}'
	" /> 
	<%List listExam = (List)request.getAttribute("getServiceExam") ;
	List listVisit = (List)request.getAttribute("getServiceVisit") ;
	List cardWorkfunction = (List) request.getAttribute("getCardWorkfunction");
	if (listExam!=null && !listExam.isEmpty()) {
	} else {
		%>
<ecom:webQuery name="servicePlanExam"
nativeSql="
select case when 0=1 then '1' else null end
,veds.id as vedsid
,veds.code as vedscode
,veds.name as vedsname 
,case when veds.isVisit='1' then 'ExtDispVisit' else 'ExtDispExam' end as dtype

 from ExtDispCard edc
 left join Patient pat on pat.id=edc.patient_id
left join ExtDispPlan edp on edp.dispType_id=edc.dispType_id
left join ExtDispPlanService edps on edps.plan_id=edp.id
left join VocExtDispService veds on veds.id=edps.servicetype_id
where edc.id='${param.id}' and (edps.sex_id=pat.sex_id or edps.sex_id is null)
and edc.ageGroup_id=edps.ageGroup_id
and (veds.isVisit='0' or veds.isVisit is null)

group by veds.id,veds.code,veds.name,veds.isVisit
order by veds.id,veds.name"
/>		
		<%
		listExam = (List)request.getAttribute("servicePlanExam") ;
	}
	if (listVisit!=null && !listVisit.isEmpty()) {
	} else {
		%>
<ecom:webQuery name="servicePlanVisit" 
nativeSql="
select case when 0=1 then '1' else null end
,veds.id as vedsid
,veds.code as vedscode
,veds.name as vedsname 
,case when veds.isVisit='1' then 'ExtDispVisit' else 'ExtDispExam' end as dtype
,cast('' as varchar(1)) as servicedate
,cast('' as varchar(1)) as recommend
,cast('' as varchar(1)) as suspicion
,veds.workfunctioncode as vedsworkfunction
,cast('' as varchar(1)) as wfID
,cast('' as varchar(1)) as wfName
,cast('' as varchar(1)) as Idc10Id
,cast('' as varchar(1)) as Idc10Name

 from ExtDispCard edc
 left join Patient pat on pat.id=edc.patient_id
left join ExtDispPlan edp on edp.dispType_id=edc.dispType_id
left join ExtDispPlanService edps on edps.plan_id=edp.id
left join VocExtDispService veds on veds.id=edps.servicetype_id
where edc.id='${param.id}' and (edps.sex_id=pat.sex_id or edps.sex_id is null)
and veds.isVisit='1'
and edc.ageGroup_id=edps.ageGroup_id
group by veds.id,veds.code,veds.name,edps.isVisit,veds.isVisit,veds.workfunctioncode
order by veds.id,veds.name"
/>		
		<%
		listVisit = (List)request.getAttribute("servicePlanVisit") ;
	}
	int sizeExam = listExam.size() ;
	int sizeVisit = listVisit.size() ;
	String[] fldExam = {"examServiceDate","examIsPathology"} ;
	String[] fldExamDate = {"examServiceDate"} ;
	String[] fldVisit = {"visitServiceDate","visitRecommendation","visitIsEtdccSuspicion","workFunctionName", "Idc10Name"} ;
	String[] fldVisitDate = {"visitServiceDate"} ;
	String[] fldVisitAutocomlete = {"workFunction", "Idc10"} ;
	out.println("<input type='hidden' id='cntExam' name='cntExam' value='"+listExam.size()+"'>") ;
	out.println("<input type='hidden' id='cntVisit' name='cntVisit' value='"+listVisit.size()+"'>") ;
	if (sizeVisit>0||sizeExam>0) {
%>
<table border=0>
<%
		out.println("") ;
		
		
		if (listExam.size()>0) {
	%>
		<tr><td><h2>Обследования</h2></td></tr>
	<%
	out.println("<tr><td>") ;
	out.println("<table border=1>") ;
	out.println("<tr>") ;
	out.print("<th>№</th>") ;
	out.print("<th>Код</th>") ;
	out.print("<th>Услуга</th>") ;
	out.print("<th>Дата оказания</th>") ;
	out.print("<th>Выявлена патология</th>") ;
	out.println("</tr>") ;
	for (int i=0; i<sizeExam; i++) {
		WebQueryResult wqr = (WebQueryResult) listExam.get(i) ;
		out.println("<tr>") ;
		out.print("<td>") ;
		out.println("<input type='hidden' name='examId"+i+"' id='examId"+i+"' value='");out.println(wqr.get1()!=null?wqr.get1():"0") ;out.print("'/>") ;
		out.println(i+1) ;
		out.print("</td>") ;
		out.print("<td>") ;
		out.println("<input type='hidden' name='examServiceType"+i+"' id='examServiceType"+i+"' value='");out.print(wqr.get2()) ;out.print("'/>") ;
		out.println("<b>"+wqr.get3()+"</b>") ;out.print("</td>") ;
		out.print("<td>") ;out.println(wqr.get4()) ;out.print("</td>") ;
		out.print("<td>") ;out.println("<input type='text' size='10' name='examServiceDate"+i+"' id='examServiceDate"+i+"' value='");
		out.print(wqr.get6()!=null?wqr.get6():"");
		out.print("'>") ;out.print("</td>") ;
		out.print("<td>") ;out.println("<input type='checkbox' name='examIsPathology"+i+"' id='examIsPathology"+i+"' ");
		out.print(wqr.get7()!=null?wqr.get7():"");out.print(">") ;out.print("</td>") ;
		out.println("</tr>") ;
	}
	out.println("</table>") ;
	out.println("</td></tr>") ;
		}

		
		
		
		
		if (sizeVisit>0) {			
			WebQueryResult cardWQR = (WebQueryResult) cardWorkfunction.get(0);
	%>
		<tr><td><h2>Посещения</h2></td></tr>
	<%
	out.println("<tr><td>") ;
	out.println("<table border=1>") ;
	out.println("<tr>") ;
	out.print("<th>№</th>") ;
	out.print("<th>Код</th>") ;
	out.print("<th>Услуга</th>") ;
	out.print("<th>Дата оказания</th>") ;
	out.print("<th>Рекомендации</th>") ;
	out.print("<th colspan='3'>Рабочая функция</th>") ;
	out.print("<th colspan='2'>Диагноз</th>") ;
	out.println("</tr>") ;
	for (int i=0; i<sizeVisit; i++) {
		WebQueryResult wqr = (WebQueryResult) listVisit.get(i) ;
		out.println("<tr>") ;
		out.print("<td>") ;
		out.println("<input type='hidden' name='visitId"+i+"' id='visitId"+i+"' value='");out.println(wqr.get1()!=null?wqr.get1():"0") ;out.print("'/>") ;
		out.println(i+1) ;
		out.print("</td>") ;
		out.print("<td>") ;
		out.print("<input type='hidden' name='visitServiceType"+i+"' id='visitServiceType"+i+"' value='");out.print(wqr.get2()) ;out.print("'/>") ;
		out.print("<input type='hidden' name='visitServiceTypeName"+i+"' id='visitServiceTypeName"+i+"' value='");out.print(wqr.get3()) ;out.print("'/>") ;
		out.print("<b>"+wqr.get3()+"</b>") ;out.print("</td>") ;
		out.print("<td>") ;out.println(wqr.get4()) ;out.print("</td>") ;
		out.print("<td>") ;out.println("<input type='text' size='10' name='visitServiceDate"+i+"' id='visitServiceDate"+i+"' value='");
		out.print(wqr.get6()!=null?wqr.get6():"");
		out.print("'>") ;out.print("</td>") ;
		out.print("<td>") ;out.println("<input type='text' size='10' name='visitRecommendation"+i+"' id='visitRecommendation"+i+"' value='");
		out.print(wqr.get7()!=null?wqr.get7():"");
		out.print("'>") ;out.println("<input type='hidden' name='visitIsEtdccSuspicion"+i+"' id='visitIsEtdccSuspicion"+i+"' ");
		out.print(wqr.get8()!=null?wqr.get8():"");out.print(">") ;out.print("</td>") ;
		/* Добавляем рабочую функцию врача */
		if (wqr.get9().equals(cardWQR.get1()) && (wqr.get1()==null || wqr.get1().equals(""))) {
			out.print("<td title='Врач' class='label' colspan='1' size='10'><label id='lpuLabel' for='workFunctionName'>Врач:</label></td>"+
					"<td colspan='2' class='workFunction'><div><input size='1' name='workFunction"+i+"' value='"+(cardWQR.get2()!=null?cardWQR.get2():"")+"' "+
			"id='workFunction"+i+"' type='hidden'><input autocomplete='off' title='workFunction' name='workFunction"+i+"Name' value='"+cardWQR.get3()+"' id='workFunction"+i+"Name'"+
			" size='40' class='autocomplete horizontalFill' type='text'><input size='1' name='workFunctionCode"+i+"' value='"+wqr.get9()+"' "+
			"id='workFunctionCode"+i+"' type='hidden'><div style='visibility: hidden; display: none;'"+
			" id='workFunction"+i+"Div'></div></div></td>");
		} else {
		out.print("<td title='Врач' class='label' colspan='1' size='10'><label id='lpuLabel' for='workFunctionName'>Врач:</label></td>"+
				"<td colspan='2' class='workFunction'><div><input size='1' name='workFunction"+i+"' value='"+(wqr.get10()!=null?wqr.get10():"")+"' "+
		"id='workFunction"+i+"' type='hidden'><input autocomplete='off' title='workFunction' name='workFunction"+i+"Name' value='"+wqr.get11()+"' id='workFunction"+i+"Name'"+
		" size='40' class='autocomplete horizontalFill' type='text'><input size='1' name='workFunctionCode"+i+"' value='"+wqr.get9()+"' "+
		"id='workFunctionCode"+i+"' type='hidden'><div style='visibility: hidden; display: none;'"+
		" id='workFunction"+i+"Div'></div></div></td>");
		
		/* ---Добавляем рабочую функцию врача */
		}
		/* Диагноз  */
		out.print("<td title='Диагноз' class='label' colspan='1' size='10'><label id='IdcLabel' for='IdcName'>Диагноз:</label></td>"+
				"<td colspan='1' class='Idc10'><div><input size='1' name='Idc10"+i+"' value='"+wqr.get12()+"' "+
		"id='Idc10"+i+"' type='hidden'><input autocomplete='off' title='Idc10' name='Idc10"+i+"Name' value='"+wqr.get13()+"' id='Idc10"+i+"Name'"+
		" size='10' class='autocomplete horizontalFill' type='text'><div style='visibility: hidden; display: none;'"+
		" id='Idc10"+i+"Div'></div></div></td>");
		/* ---Диагноз  */
		out.println("</tr>") ;
	}
	out.println("</table>") ;
	out.println("</td></tr>") ;
	
	
	%>


	<% 	
		}
		%>
		
		<tr><td class="buttons"><input type="button" value="Отменить" title="Отменить изменения" onclick="this.disabled=true; window.location.href='entityParentView-extDispCom_card.do?id=${param.id}';  return true ;" id="cancelButton">
		<input type="button" title="Сохранить изменения "	onclick="this.disabled=true; this.value=&quot;Сохранение изменений ...&quot;; this.form.action='js-extDispCom_service-save.do'; this.form.submit(); return true ;" value="Сохранить изменения " class="default" id="submitButton" autocomplete="off"></td></tr>
		</table>
		<%
		
		out.println("<script type='text/javascript'>") ;
		out.println("") ;
		out.println("// script exam") ;
		out.println("") ;
		for (int i=0;i<sizeExam; i++) {
			//if (i>0) out.println(" eventutil.addEnterSupport('examIsPathology"+(i-1)+"','examServiceDate"+i+"');") ;
			//out.println(" eventutil.addEnterSupport('examServiceDate"+i+"','examIsPathology"+i+"');") ;
			
			if (i>0) out.println(" eventutil.addEnterSupport('"
					+fldExam[fldExam.length-1]+(i-1)+"','"+fldExam[0]+i+"');") ;
			for (int ii=0;ii<fldExam.length-1;ii++) {
				out.println(" eventutil.addEnterSupport('"+fldExam[ii]+i+"','"+fldExam[ii+1]+i+"');") ;
			}
			for (int ii=0;ii<fldExamDate.length;ii++) {
				out.println(" new dateutil.DateField($('"+fldExamDate[ii]+i+"')) ;") ;
			}
		}
		out.println("") ;
		out.println("// script visit") ;
		out.println("") ;
		for (int i=0;i<sizeVisit; i++) {
			//workFunction Autocomplete
			String wf="workFunction"+i+"Autocomplete";
			out.print("var "+wf+" = new msh_autocomplete.Autocomplete() ;\n");
			out.print(wf+".setUrl('simpleVocAutocomplete/workFunctionByCode') ;\n");
			out.print(wf+".setIdFieldId('workFunction"+i+"');\n");
			out.print(wf+".setNameFieldId('workFunction"+i+"Name') ;\n");
			out.print(wf+".setDivId('workFunction"+i+"Div') ;\n");
			out.print(wf+".build() ;\n");
//			out.print(wf+".setParentId($('workFunctionCode"+i+"').value); \n");
			out.print(wf+".setParentId($('visitServiceTypeName"+i+"').value); \n");

			//Diagnosis Autocomplete
			String mkb="Idc10"+i+"Autocomplete";
			out.print("var "+wf+" = new msh_autocomplete.Autocomplete() ;\n");
			out.print(wf+".setUrl('simpleVocAutocomplete/vocIdc10') ;\n");
			out.print(wf+".setIdFieldId('Idc10"+i+"');\n");
			out.print(wf+".setNameFieldId('Idc10"+i+"Name') ;\n");
			out.print(wf+".setDivId('Idc10"+i+"Div') ;\n");
			out.print(wf+".build() ;\n");
			
			if (i>0) out.println(" eventutil.addEnterSupport('"
					+fldVisit[fldVisit.length-1]+(i-1)+"','"+fldVisit[0]+i+"');") ;
			for (int ii=0;ii<fldVisit.length-1;ii++) {
				out.println(" eventutil.addEnterSupport('"+fldVisit[ii]+i+"','"+fldVisit[ii+1]+i+"');") ;
			}
			for (int ii=0;ii<fldVisitDate.length;ii++) {
				out.println(" new dateutil.DateField($('"+fldVisitDate[ii]+i+"')) ;") ;
			}
		}
		
		if (sizeExam==0) {
			out.println("   setFocusOnField('"+fldVisit[0]+"0') ;") ;
		} else {
			out.println("   setFocusOnField('"+fldExam[0]+"0') ;") ;
			if (sizeVisit>0) {
				out.println(" eventutil.addEnterSupport('"+fldExam[fldExam.length-1]+(sizeExam-1)+"','"
					+fldVisit[0]+0+"');") ;
			}
		}
		if (sizeVisit==0) {
			out.println(" eventutil.addEnterSupport('"+fldExam[fldExam.length-1]+(sizeExam-1)+"', 'submitButton') ;") ;
		} else {
			out.println(" eventutil.addEnterSupport('"+fldVisit[fldVisit.length-1]+(sizeVisit-1)+"', 'submitButton') ;") ;
		}
		//out.println("Event.observe(window, 'load', _formInit_938, true);function _formInit_938() {msh.util.FormData.getInstance().init($('mainForm')) ;}") ;
		out.println("</script>") ;
		
	} else {%>
<B>НЕТ УСЛУГ ПО ЗАДАННЫМ ПАРАМЕТРАМ КАРТЫ ДОП. ДИСПАНСЕРИЗАЦИИ</B>
<% } %>			
		</form>

	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="extDispCom_cardForm" title="Услуги" />
	</tiles:put>

</tiles:insert>
