<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
    	String type=request.getParameter("type") ;
    	if (type!=null&&type.equals("1")) {
    		request.setAttribute("filterAdd","slo.id='"+request.getParameter("id")+"' and aslo.dtype='DepartmentMedCase'") ;
    		request.setAttribute("title","дневники") ;
    	} else if (type!=null&&type.equals("2")) {
    		request.setAttribute("title","лабораторные исследования") ;
    		request.setAttribute("filterAdd","slo.id='"+request.getParameter("id")+"' and aslo.dtype='Visit' and vst.code='LABSURVEY'") ;
    	} else if (type!=null&&type.equals("3")) {
    		List l = ActionUtil.getListObjFromNativeQuery("select sls.dtype,sls.patient_id,to_char(sls.datestart,'dd.mm.yyyy') as dat1,to_char(coalesce(sls.datefinish,current_date),'dd.mm.yyyy') as dat2 from medcase slo left join medcase sls on sls.id=slo.parent_id where slo.id="+request.getParameter("id")+" and slo.dtype='DepartmentMedCase'", request) ;
    		if (l.size()>0) {
    			Object[] obj = (Object[])l.get(0) ;
    			request.setAttribute("filterAdd","slo.patient_id='"+obj[1]+"' and aslo.datestart between to_date('"+obj[2]+"','dd.mm.yyyy') and to_date('"+obj[3]+"','dd.mm.yyyy') and aslo.dtype='Visit' and (vst.code='DIAGNOSTIC' or vst.code='SERVICE')") ;
    		} else {
        		request.setAttribute("filterAdd","slo.id='"+request.getParameter("id")+"' and aslo.dtype='Visit' ") ;
    		}
    		request.setAttribute("title","диагностические исследования") ;
    	} else {
    		List l = ActionUtil.getListObjFromNativeQuery("select sls.dtype,sls.patient_id,to_char(sls.datestart,'dd.mm.yyyy') as dat1,to_char(coalesce(sls.datefinish,current_date),'dd.mm.yyyy') as dat2 from medcase slo left join medcase sls on sls.id=slo.parent_id where slo.id="+request.getParameter("id")+" and slo.dtype='DepartmentMedCase'", request) ;
    		if (l.size()>0) {
    			Object[] obj = (Object[])l.get(0) ;
    			request.setAttribute("filterAdd","(slo.id='"+request.getParameter("id")+"' or (slo.patient_id='"
    		    		+obj[1]+"' and aslo.datestart between to_date('"+obj[2]+"','dd.MM.yyyy') and to_date('"+obj[3]+"','dd.MM.yyyy') and aslo.dtype='Visit' and (vst.code='DIAGNOSTIC' or vst.code='SERVICE')))") ;
    		} else {
    			request.setAttribute("filterAdd","slo.id='"+request.getParameter("id")+"'");
    		}
    		
    	}
    	if (request.getParameter("service")!=null) {
    		request.setAttribute("filterAdd", "slo.patient_id='"+request.getParameter("patient")+"' and aslo.dtype='Visit' and servmc.medservice_id in ("+request.getParameter("service")+")") ;
    	}
		List l = ActionUtil.getListObjFromNativeQuery("select slo.department_id,sls.patient_id from medcase slo left join medcase sls on sls.id=slo.parent_id where slo.id="+request.getParameter("id")+" and slo.dtype='DepartmentMedCase'", request) ;
		if (l.size()>0) {
			Object[] obj = (Object[])l.get(0) ;
			request.setAttribute("department",obj[0]);
		} else {
			request.setAttribute("department","0");
		}
    	
    %>
            	<ecom:webQuery nameFldSql="protocols_sql" name="protocols"  nativeSql="select d.id as did, to_char(d.dateRegistration,'dd.mm.yyyy') ||' '|| cast(d.timeRegistration as varchar(5)) as dtimeRegistration
            	,case when count (mc.id)>0 then list(mc.code||' '||mc.name) ||'<'||'br'||'/>' else '' end || d.record 
      ||'<'||'br'||'/>'|| vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doctor
      ,case when aslo.dtype='Visit' then 'background:#F6D8CE;' 
      when aslo.dtype='DepartmentMedCase' and '${department}'!=aslo.department_id then 'background:#E0F8EC;'
      else '' end as record
      ,case when count (mc.id)>0 then list(mc.code||' '||mc.name) else vtp.name end as service
      ,case when '${param.service}' = '' and count (mc.id)>0 then 'js-stac_slo-list_protocols.do?id=${param.id}&short=Short&patient='||slo.patient_id||'&service='||replace(list(''||mc.id),' ','') else null end servjs
      from Diary as d
      left join MedCase aslo on aslo.id=d.medCase_id
      left join MedCase slo on aslo.parent_id=slo.parent_id
      left join medcase servmc on servmc.parent_id=aslo.id
      left join medservice mc on mc.id=servmc.medservice_id
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      left join VocServiceType vst on vst.id=mc.serviceType_id
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
      left join voctypeprotocol vtp on vtp.id=d.type_id
            	where ${filterAdd} and d.DTYPE='Protocol'
            	group by d.dateregistration,d.timeregistration
            	,d.id ,aslo.dtype , d.record 
      ,vwf.name,pw.lastname,pw.firstname,pw.middlename
      ,aslo.dtype='Visit',vtp.name
      ,aslo.department_id,slo.patient_id 
            	order by  d.dateRegistration desc,  d.timeRegistration desc
            	"/>${protocols_sql}
            	
                <msh:table hideTitle="false" styleRow="4" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do">
                    <msh:tableButton property="6" hideIfEmpty="true" buttonFunction="getDefinition" buttonName="Динамика исследования" buttonShortName="Дин."/>
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата и время" property="2"/>
                    <msh:tableColumn columnName="Услуга" property="5" />
                    <msh:tableColumn columnName="Протокол" property="3" cssClass="preCell"/>
                </msh:table>
 


    </tiles:put>

</tiles:insert>