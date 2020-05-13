<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал хирургических операций"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="stac_surOperation" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
  <% 
  
  String typeEndoscopyUse = ActionUtil.updateParameter("SurgicalOperation","typeEndoscopyUse","3", request) ;
  String typeAnaesthesUse = ActionUtil.updateParameter("SurgicalOperation","typeAnaesthesUse","3", request) ;
  String typeEmergency = ActionUtil.updateParameter("SurgicalOperation","typeEmergency","3", request) ;
  if (request.getParameter("short")==null) {
	  //ActionUtil.updateParameter("SurgicalOperation","typeEndoscopyUse","3", request) ;
	  ActionUtil.updateParameter("SurgicalOperation","typeView","1", request) ;
	  
	  ActionUtil.updateParameter("SurgicalOperation","typeOrder","1", request) ;
	  ActionUtil.updateParameter("SurgicalOperation","typeDate","1", request) ;
  }
  %>
    <msh:form action="/journal_surOperation.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  свод по датам
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2">  свод по хирургам и операциям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3">  свод по отделению и операциям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4">  свод по операциям
        </td>
     </msh:row>
     <msh:row>
     	<td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="5">  свод по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="6">  свод по хирургам и ур.сл.опер.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7">  свод по отделению и ур.сл.опер.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="8">  реестр
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Сортировка (typeOrder)" colspan="1"><label for="typeOrderName" id="typeOrderLabel">Сортировка:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeOrder" value="1">  название операция
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeOrder" value="2">  код операция
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeOrder" value="3">  доп. параметры, название операции
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeOrder" value="4">  доп. параметры, код операции
        </td>
      </msh:row>     
      <msh:row>
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  операции
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="2">  выписки (учит. операции в отд.)
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="3">  выписки (учит. операции в прием.отд.)
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Экстренность (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Порядок поступления.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="1">  экстренно
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="2" >  планово
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="3">  не учит.параметр
        </td>
      </msh:row>      	
      <msh:row>
        <td class="label" title="Поиск по эндоскопии (typeEndoscopyUse)" colspan="1"><label for="typeEndoscopyUseName" id="typeEndoscopyUseLabel">Операции с испол.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEndoscopyUse" value="1">  эндоскопии
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEndoscopyUse" value="2" >  без эндоскопии
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEndoscopyUse" value="3">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по анестезии (typeAnaesthesUse)" colspan="1"><label for="typeAnaesthesUseName" id="typeAnaesthesUseLabel">Операции с испол.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAnaesthesUse" value="1"> анестезии
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAnaesthesUse" value="2" > без анестезии
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAnaesthesUse" value="3"> все
        </td>
      </msh:row>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" vocName="lpu"/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="spec" fieldColSpan="7" horizontalFill="true" vocName="workFunctionIsSurgical"/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="serviceStream" fieldColSpan="7" horizontalFill="true" vocName="vocServiceStream"/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="additionStatus" label="Результат госпитализации" fieldColSpan="7" horizontalFill="true" vocName="vocHospitalizationResult"/>
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
           <script type='text/javascript'>

    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeEndoscopyUse','${typeEndoscopyUse}',3) ;
    checkFieldUpdate('typeAnaesthesUse','${typeAnaesthesUse}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeOrder','${typeOrder}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    
  
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
    	frm.action='journal_surOperation.do' ;
    }

    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;

    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    ActionUtil.setParameterFilterSql("serviceStream", "so.serviceStream_id", request) ;
    ActionUtil.setParameterFilterSql("additionStatus", "sls.result_id", request) ;
    String view = (String)request.getAttribute("typeView") ;
    String typeOrder = (String)request.getAttribute("typeOrder") ;
    
    String typeEndoscopyUseSql=""; 
    if ("1".equals(typeEndoscopyUse)) {
    	typeEndoscopyUseSql=" and so.endoscopyUse='1'" ;
    } else if ("2".equals(typeEndoscopyUse)) {
    	typeEndoscopyUseSql= "and (so.endoscopyUse='0' or so.endoscopyUse is null)" ;
    }
    request.setAttribute("typeEndoscopyUseSql", typeEndoscopyUseSql) ;
    
    String typeAnaesthesUseSql="";
    if ("1".equals(typeAnaesthesUse)) {
    	typeAnaesthesUseSql=" and an.id is not null " ;
    } else if ("2".equals(typeAnaesthesUse)) {
    	typeAnaesthesUseSql= " and an.id is null " ;
    }
    request.setAttribute("typeAnaesthesUseSql", typeAnaesthesUseSql) ;

	String typeDate=ActionUtil.updateParameter("SurgicalOperation","typeDate","1", request) ;
	if (typeEmergency.equals("1")) {
		if ("2".equals(typeDate)) {
			request.setAttribute("typeEmergencySql", " and sls.emergency='1'") ;
    	} else if ("3".equals(typeDate)) {
    		request.setAttribute("typeEmergencySql", " and slsHosp.emergency='1')") ;
    	} else {
    		request.setAttribute("typeEmergencySql", " and (slo.datestart is not null and sls.emergency='1' or slo.datestart is null and slsHosp.emergency='1')") ;
    	}
	} else if ("2".equals(typeEmergency)) {
		if ("2".equals(typeDate)) {
			request.setAttribute("typeEmergencySql", " and (sls.emergency='0' or sls.emergency is null)") ;
    	} else if ("3".equals(typeDate)) {
    		request.setAttribute("typeEmergencySql", " and (slsHosp.emergency='0' or slsHosp.emergency is null)") ;
    	} else {
    		request.setAttribute("typeEmergencySql", " and (slo.datestart is not null and (sls.emergency='0' or sls.emergency is null) or slo.datestart is null and (slsHosp.emergency='0' or slsHosp.emergency is null))") ;
    	}
	}

    if (date!=null && !date.equals("")) {
    	String typeDateSql = "so.operationDate" ;
    	if ("2".equals(typeDate)) {
    		typeDateSql = "sls.dateFinish" ;
    	} else if ("3".equals(typeDate)) {
    		typeDateSql = "slsHosp.dateFinish" ;
    	} 
    	request.setAttribute("typeDateSql", typeDateSql);
    	
        AdmissionJournalForm frm = (AdmissionJournalForm) session.getAttribute("stac_admissionJournalForm");
        //String department = (String)request.getAttribute("department") ;
        //String spec = (String)request.getAttribute("spec") ;
    	if (typeOrder!=null) {
    		String group1 = "so.medservice_id,vo.id,vo.name,vo.code,vo.complexity" ;
    		String orderName = "vo.name" ;
    		String orderId = "vo.id" ;
    		if ("6".equals(view) || "7".equals(view)) {
    			orderId="vo.complexity" ;orderName="vo.complexity" ;
    			group1 = "vo.complexity" ;
    		} 
    		if (typeOrder.equals("1")) {
	    		request.setAttribute("order1", orderName+",") ;
	    		request.setAttribute("order2", "") ;
	    		request.setAttribute("group1", group1+",") ;
	    		request.setAttribute("group2", "") ;
	    	} else if (typeOrder.equals("2")) {
	    		request.setAttribute("order1", orderId+",") ;
	    		request.setAttribute("order2", "") ;
	    		request.setAttribute("group1", group1+",") ;
	    		request.setAttribute("group2", "") ;
	    	} else if (typeOrder.equals("3")) {
	    		request.setAttribute("order1", "") ;
	    		request.setAttribute("order2", ","+orderName) ;
	    		request.setAttribute("group1", "") ;
	    		request.setAttribute("group2", ","+group1) ;
	    	} else if (typeOrder.equals("4")) {
	    		request.setAttribute("order1", "") ;
	    		request.setAttribute("order2", ","+orderId) ;
	    		request.setAttribute("group1", "") ;
	    		request.setAttribute("group2", ","+group1) ;
	    	}
    	}
    	if (frm!=null) {
    	if (frm.getDepartment()!=null && !frm.getDepartment().equals(0L)) {
    		request.setAttribute("department", " and so.department_id='"+frm.getDepartment()+"'") ;
    		request.setAttribute("departmentSql", " and so.department_id="+frm.getDepartment()+"") ;
    	}
    	if (frm.getSpec()!=null && !frm.getSpec().equals(0L)) {
    		request.setAttribute("spec", " and so.surgeon_id='"+frm.getSpec()+"'") ;
    		request.setAttribute("specSql", " and so.surgeon_id="+frm.getSpec()+"") ;
    	}
    	
    	}
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
    <% if ("1".equals(view)) { %>
    <msh:section>
    <msh:sectionTitle>Разбивка по дням</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperation" nativeSql="
    select '${departmentSql} ${specSql}:'||to_char(${typeDateSql},'dd.mm.yyyy')||':'||to_char(${typeDateSql},'dd.mm.yyyy')||'&additionStatus=${param.additionStatus}' as id
    ,to_char(${typeDateSql},'dd.mm.yyyy')
    , count(distinct so.id) as cntOper
    , count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    ,count(distinct case when so.firstdosetime is not null then so.id else null end) as cntAntibio
     from SurgicalOperation so 
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
     left join anesthesia an on an.surgicaloperation_id=so.id
     where 
    ${typeDateSql}  between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${typeAnaesthesUseSql} ${additionStatusSql}
    
    group by ${typeDateSql} 
    order by ${typeDateSql}" />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation" viewUrl="journal_surOperationByDate.do?short=Short&dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}"  action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" identificator="false" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="4" />
        <msh:tableColumn isCalcAmount="true" columnName="из них антибиотикотерапия" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else if ("2".equals(view)) { %>
    <msh:section>
    <msh:sectionTitle>Разбивка по хирургам и операциям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||so.surgeon_id||':'||so.medservice_id||'&additionStatus=${param.additionStatus}' as id
    ,vwf.name||' '||p.lastname||' '|| p.firstname||' '|| p.middlename as doctor
    , vo.code as vocode, vo.name as voname,vo.complexity,count(distinct so.id) as cnt 
    , count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    ,count(distinct case when so.firstdosetime is not null then so.id else null end) as cntAntibio
    
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${additionStatusSql}
group by ${group1}so.surgeon_id,vwf.name,p.lastname, p.firstname, p.middlename ${group2}
order by ${order1} p.lastname,p.firstname,p.middlename ${order2}" />
    <msh:table printToExcelButton="Сохранить в Excel" name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Специалист" property="2" />
      <msh:tableColumn columnName="Операция" property="3" />
      <msh:tableColumn columnName="Операция" property="4" />
      <msh:tableColumn columnName="Уровень сложности" property="5" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во операций" property="6" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="7" />
        <msh:tableColumn isCalcAmount="true" columnName="из них антибиотикотерапия" property="8" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <% } else if ("3".equals(view)) { %>
    <msh:section>
    <msh:sectionTitle>Разбивка по отделениям и операциям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec"  nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||''||':'||so.medservice_id||':'||so.department_id||'&additionStatus=${param.additionStatus}' as id
    ,dep.name as depname
    , vo.code as vocode, vo.name as voname,vo.complexity,count(distinct so.id) as cnt
    , count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    ,count(distinct case when so.firstdosetime is not null then so.id else null end) as cntAntibio
     
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join mislpu dep on dep.id=so.department_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${additionStatusSql}
group by  ${group1} so.department_id,dep.name ${group2}
order by ${order1} dep.name ${order2}" />
    <msh:table printToExcelButton="Сохранить в Excel" name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Код" property="3" />
      <msh:tableColumn columnName="Операция" property="4" />
      <msh:tableColumn columnName="Уровень сложности" property="5" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="6" />
      <msh:tableColumn isCalcAmount="true" columnName="из них эндоскопии" property="7"/>
       <msh:tableColumn isCalcAmount="true" columnName="из них антибиотикотерапия" property="8"/>
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <% } else if ("5".equals(view)) { %>
    <msh:section>
    <msh:sectionTitle>Разбивка по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||''||'::'||so.department_id||'&additionStatus=${param.additionStatus}' as id
    ,dep.name as depname
    ,count(distinct so.id) as cnt 
        , count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    ,count(distinct case when so.firstdosetime is not null then so.id else null end) as cntAntibio
    
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join mislpu dep on dep.id=so.department_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${additionStatusSql}
group by so.department_id,dep.name
order by dep.name 
" />
    <msh:table printToExcelButton="Сохранить в Excel" name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="4" />
      <msh:tableColumn isCalcAmount="true" columnName="из них антибиотикотерапия" property="5" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <% } else if ("4".equals(view)) { %>
    <msh:section>
    <msh:sectionTitle>Разбивка по операциям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}::'||so.medservice_id ||'&additionStatus=${param.additionStatus}' as id
    , vo.code as vocode, vo.name as voname,vo.complexity,count(distinct so.id) as cnt 
        , count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    ,count(distinct case when so.firstdosetime is not null then so.id else null end) as cntAntibio
    
    from SurgicalOperation so
     
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${additionStatusSql}
group by so.medservice_id, vo.code,vo.name ,vo.complexity

order by vo.name" />
    <msh:table printToExcelButton="Сохранить в Excel" name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Код" property="2" />
      <msh:tableColumn columnName="Операция" property="3" />
      <msh:tableColumn columnName="Уровень сложности" property="4" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="5" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="6" />
        <msh:tableColumn isCalcAmount="true" columnName="из них выполнена антибиотикопрофилактика" property="7" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <% } else if ("6".equals(view)) { %>
    <msh:section>
    <msh:sectionTitle>Разбивка по хирургам и уровням сложности операций</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec" nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||so.surgeon_id||':'||'&additionStatus=${param.additionStatus}' as id
    ,vwf.name||' '||p.lastname||' '|| p.firstname||' '|| p.middlename as doctor
   ,vo.complexity
   	,count(distinct so.id) as cnt 
       , count(distinct case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    ,count(distinct case when so.firstdosetime is not null then so.id else null end) as cntAntibio
   
    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join workfunction wf on wf.id=so.surgeon_id
left join worker w on w.id=wf.worker_id
left join patient p on p.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${additionStatusSql}
group by ${group1}so.surgeon_id,vwf.name,p.lastname, p.firstname, p.middlename ${group2}
order by ${order1} p.lastname,p.firstname,p.middlename ${order2}" />
    <msh:table printToExcelButton="Сохранить в Excel" name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Специалист" property="2" />
       <msh:tableColumn columnName="Уровень сложности" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="Кол-во операций" property="4" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="5" />
        <msh:tableColumn isCalcAmount="true" columnName="из них антибиотикотерапия" property="6" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <% } else if ("7".equals(view)) { %>
    <msh:section>
    <msh:sectionTitle>Разбивка по отделениям и уровням сложности операций</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_surOperationBySpec"  nativeSql="select 
    '${departmentSql} ${specSql}:'||'${dateBegin}:${dateEnd}:'||''||'::'||so.department_id ||'&additionStatus=${param.additionStatus}' as id
    ,dep.name as depname
    ,vo.complexity,count(*) as cnt 
    ,round(100*count(distinct so.id)/
cast((select count(distinct so1.id) from SurgicalOperation so1
where so1.operationDate between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy')  and so.department_id=so1.department_id
) as numeric) ,2) as srDep
    , count(case when so.endoscopyUse='1' then so.id else null end) as cntEndoscopyUse
    ,count(distinct case when so.firstdosetime is not null then so.id else null end) as cntAntibio

    from SurgicalOperation so
left join medservice vo on vo.id=so.medService_id
left join mislpu dep on dep.id=so.department_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'
where ${typeDateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
and to_date('${dateEnd}','dd.mm.yyyy') ${department} ${spec} ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${additionStatusSql}
group by  ${group1} so.department_id,dep.name ${group2}
order by ${order1} dep.name ${order2}" />
    <msh:table printToExcelButton="Сохранить в Excel" name="journal_surOperationBySpec" viewUrl="journal_surOperationByDate.do?short=Short&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" action="journal_surOperationByDate.do?dateSearch=${dateSearch}&typeDate=${param.typeDate}&typeEndoscopyUse=${param.typeEndoscopyUse}&typeEmergency=${param.typeEmergency}&serviceStream=${param.serviceStream}" idField="1">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Уровень сложности" property="3" />
      <msh:tableColumn isCalcAmount="true" columnName="Количество операций" property="4" />
      <msh:tableColumn columnName="% от общ. числа опер. по отд." property="5"/>            
      <msh:tableColumn isCalcAmount="true" columnName="из них с испол. эндоскопии" property="6" />
        <msh:tableColumn isCalcAmount="true" columnName="из них антибиотикотерапия" property="7" />
    </msh:table>
    </msh:sectionContent>    
    </msh:section>
    <% } else if ("8".equals(view)) { %>
	    <ecom:webQuery name="journal_surOperation1" nameFldSql="journal_surOperation1_sql" nativeSql="select so.id as id
	    ,coalesce(to_char(so.operationDate,'DD.MM.YYYY')||' '||cast(so.operationTime as varchar(5))||' - '||to_char(so.operationDateTo,'DD.MM.YYYY')||' '||cast(so.operationTimeTo as varchar(5)),to_char(${typeDateSql},'DD.MM.YYYY')) as operDate
	    , vo.name as voname
	    ,(select list(' '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) from SurgicalOperation_WorkFunction sowf left join WorkFunction wf on wf.id=sowf.surgeonFunctions_id left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id left join vocworkFunction vwf on vwf.id=wf.workFunction_id where sowf.SurgicalOperation_id=so.id ) as surgOper 
	    ,p.lastname||' '||p.firstname||' '||p.middlename ||' гр '||to_char(p.birthday,'DD.MM.YYYY') as patientInfo,
	    (select list(' '||vam.name|| ' '|| a.duration||' мин '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) as aneth 
	    from Anesthesia a
	    left join VocAnesthesiaMethod vam on vam.id=a.method_id
	    left join WorkFunction wf on wf.id=a.anesthesist_id
	    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
	    left join Worker w on w.id=wf.worker_id
	    left join Patient wp on wp.id=w.person_id
	    
	    where a.surgicalOperation_id=so.id
	    )
	     ,vas.name as vasname
	     ,svwf.name||' '||swp.lastname||' '||swp.firstname||' '||swp.middlename as surinfo
	     ,vo.complexity,
	     cast('Класс раны: ' as varchar(12))||vcw.name||cast(', препарат: ' as varchar(12))
	    ||vab.name||' '||so.dose||' '||vmd.name||cast(' в 1). ' as varchar(7))||so.firstdosetime||cast(' 2). ' as varchar(5))
	    ||case when so.seconddosetime is not null then cast(so.seconddosetime as varchar) else '-' end as ant
	     from SurgicalOperation so

	    left join WorkFunction swf on swf.id=so.surgeon_id
	    left join VocWorkFunction svwf on svwf.id=swf.workFunction_id
	    left join Worker sw on sw.id=swf.worker_id
	    left join Patient swp on swp.id=sw.person_id

	      left join Patient p on p.id=so.patient_id
	      left join VocAdditionStatus vas on vas.id=p.additionStatus_id
	      left join MedService vo on vo.id=so.medService_id
     left join MedCase slo on slo.id=so.medCase_id and slo.dtype='DepartmentMedCase'
     left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
     left join MedCase slsHosp on slsHosp.id=so.medCase_id and slsHosp.dtype='HospitalMedCase'

          left join vocclasswound vcw on vcw.id=so.classwound_id
     left join vocmethodsdrugadm vmd on vmd.id=so.methodsdrugadm_id
     left join vocantibioticdrug vab on vab.id=so.antibioticdrug_id
	       where ${typeDateSql} 
	        between to_date('${dateBegin}','dd.mm.yyyy')
	          and to_date('${dateEnd}','dd.mm.yyyy')   ${department} ${spec} 
	          ${typeEndoscopyUseSql} ${typeEmergencySql} ${serviceStreamSql} ${additionStatusSql}
	          order by ${order1} p.lastname,p.firstname,p.middlename ${order2}
	        " />
    <msh:section>
    <msh:sectionTitle>
    
    <form action="print-stac_journal_surOperationByDate_8.do" method="post" target="_blank">
    Реестр хирургических операций с ${dateBegin} по ${dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_surOperation1_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр хирургических операций с ${dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form></msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table printToExcelButton="Сохранить в Excel" name="journal_surOperation1"
	    action="entityView-stac_surOperation.do" idField="1" 
	    viewUrl="entityShortView-stac_surOperation.do"
	    >
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="Статус пациента" property="7"/>
	      <msh:tableColumn columnName="Пациент" property="5"/>
	      <msh:tableColumn columnName="Период операции" property="2" />
	      <msh:tableColumn columnName="Хирург" property="8"/>
	      <msh:tableColumn columnName="Ассистенты" property="4"/>
	      <msh:tableColumn columnName="Операция" property="3"/>
	      <msh:tableColumn columnName="Уровень сложности" property="9"/>
	      <msh:tableColumn columnName="Анестезия" property="6"/>
            <msh:tableColumn columnName="Антибиотикопрофилактика" property="10"/>
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%}
    } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
  </tiles:put>
</tiles:insert>