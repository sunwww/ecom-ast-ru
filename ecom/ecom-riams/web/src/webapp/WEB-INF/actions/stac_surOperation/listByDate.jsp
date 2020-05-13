<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал операций" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="stac_surOperation" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String[] ids = request.getParameter("id").split(":") ;
  	request.setAttribute("addParamSql", ids[0]) ;
  	request.setAttribute("beginDate", ids[1]) ;
  	request.setAttribute("endDate", ids[2]) ;
  	if (ids.length>=4 && ids[3]!=null && !ids[3].equals("") && !ids[3].equals("0")) {
  	  	request.setAttribute("spec", " and so.surgeon_id='"+ids[3]+"'") ;
  	}
  	if (ids.length>=5 && ids[4]!=null && !ids[4].equals("") && !ids[4].equals("0")) {
	  	request.setAttribute("medService", " and so.medService_id='"+ids[4]+"'") ;
  	}
  	if (ids.length>=6 && ids[5]!=null && !ids[5].equals("") && !ids[5].equals("0")) {
	  	request.setAttribute("department", " and so.department_id='"+ids[5]+"'") ;
  	}
  	String typeDate=ActionUtil.updateParameter("SurgicalOperation","typeDate","1", request) ;
  	
  	String typeEmergency=ActionUtil.updateParameter("SurgicalOperation","typeEmergency","3", request) ;
  	
    String typeEndoscopyUse = ActionUtil.updateParameter("SurgicalOperation","typeEndoscopyUse","3", request) ;
    String typeAnaesthesUse = ActionUtil.updateParameter("SurgicalOperation","typeAnaesthesUse","3", request) ;

    String typeDateSql ;
	if ("2".equals(typeDate)) {
		typeDateSql = "sls.dateFinish" ;
	} else if ("3".equals(typeDate)) {
		typeDateSql = "slsHosp.dateFinish" ;
	}  else {
		typeDateSql = "so.operationDate";
	}
	request.setAttribute("typeDateSql", typeDateSql);
	
    String typeEndoscopyUseSql;
    if ("1".equals(typeEndoscopyUse)) {
    	typeEndoscopyUseSql=" and so.endoscopyUse='1'" ;
    } else if ("2".equals(typeEndoscopyUse)) {
    	typeEndoscopyUseSql= "and (so.endoscopyUse='0' or so.endoscopyUse is null)" ;
    } else {
		typeEndoscopyUseSql ="";
	}
    request.setAttribute("typeEndoscopyUseSql", typeEndoscopyUseSql) ;
    String typeAnaesthesUseSql;
    if ("1".equals(typeAnaesthesUse)) {
    	typeAnaesthesUseSql=" and an.id is not null " ;
    } else if ("2".equals(typeAnaesthesUse)) {
    	typeAnaesthesUseSql= " and an.id is null " ;
    } else {
		typeAnaesthesUseSql = "";
	}
    request.setAttribute("typeAnaesthesUseSql", typeAnaesthesUseSql) ;

	if (typeEmergency.equals("1")) {
		if ("2".equals(typeDate)) {
			request.setAttribute("typeEmergencySql", " and sls.emergency='1'") ;
    	} else if ("3".equals(typeDate)) {
    		request.setAttribute("typeEmergencySql", " and slsHosp.emergency='1')") ;
    	} else {
    		request.setAttribute("typeEmergencySql", " and (slo.datestart is not null and sls.emergency='1' or slo.datestart is null and slsHosp.emergency='1')") ;
    	}
	} else if (typeEmergency.equals("2")) {
		if ("2".equals(typeDate)) {
			request.setAttribute("typeEmergencySql", " and (sls.emergency='0' or sls.emergency is null)") ;
    	} else if ("3".equals(typeDate)) {
    		request.setAttribute("typeEmergencySql", " and (slsHosp.emergency='0' or slsHosp.emergency is null)") ;
    	} else {
    		request.setAttribute("typeEmergencySql", " and (slo.datestart is not null and (sls.emergency='0' or sls.emergency is null) or slo.datestart is null and (slsHosp.emergency='0' or slsHosp.emergency is null))") ;
    	}
	}
	
	ActionUtil.setParameterFilterSql("serviceStream", "so.serviceStream_id", request) ;
	ActionUtil.setParameterFilterSql("additionStatus", "sls.result_id", request) ;

  %>
    <msh:section>
      <msh:sectionTitle>
		<ecom:webQuery name="journal_surOperation1" nameFldSql="journal_surOperation1_sql" nativeSql="select so.id as id
	    ,coalesce(to_char(so.operationDate,'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')||' - '||to_char(so.operationDateTo,'DD.MM.YYYY')||' '||to_char(so.operationTimeTo,'HH24:MI'),to_char(so.operationDate,'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')) as operDate
	    , vo.code||' '||vo.name as voname
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
	     ,svwf.name||' '||swp.lastname||' '||swp.firstname||' '||swp.middlename as surinfo,

	     cast('Класс раны: ' as varchar(12))||vcw.name||cast(', препарат: ' as varchar(12))
	    ||vab.name||' '||so.dose||' '||vmd.name||cast(' в 1). ' as varchar(7))||so.firstdosetime||cast(' 2). ' as varchar(5))
	    ||case when so.seconddosetime is not null then cast(so.seconddosetime as varchar) else '-' end as ant
	     from SurgicalOperation so

	    left join anesthesia an on an.surgicaloperation_id=so.id
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
	        between to_date('${beginDate}','dd.mm.yyyy')
	          and to_date('${endDate}','dd.mm.yyyy') 
	          ${department} ${spec} ${medService}
	           ${addParamSql} ${serviceStreamSql} ${typeEmergencySql} ${typeEndoscopyUseSql} ${typeAnaesthesUseSql} ${additionStatusSql}
	          order by p.lastname,p.firstname,p.middlename
	        " />
	        
	            	    <form action="print-journal_surOperationByDate_r.do" method="post" target="_blank">
        Результаты поиска за дату с ${beginDate} по ${endDate}.
        <msh:link action="journal_surOperation.do">Выбрать другие параметры</msh:link>
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_surOperation1_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Результаты поиска за дату с ${beginDate} по ${endDate}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
	        
      </msh:sectionTitle>
      <msh:sectionContent>
	    <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation1"
	    action="entityView-stac_surOperation.do" idField="1" 
	    viewUrl="entityShortView-stac_surOperation.do"
	    >
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="Статус пациента" property="7"/>
	      <msh:tableColumn columnName="Пациент" property="5"/>
	      <msh:tableColumn columnName="Период операции" property="2" />
	      <msh:tableColumn columnName="Хирург" property="8"/>
	      <msh:tableColumn columnName="Ассистенты" property="4"/>
	      <msh:tableColumn columnName="Операции" property="3"/>
	      <msh:tableColumn columnName="Анестезия" property="6"/>
			<msh:tableColumn columnName="Антибиотикопрофилактика" property="9"/>
	    </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>