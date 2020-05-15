<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient"  beginForm="mis_medCaseForm" title="Список протоколов"/>
    </tiles:put>

    <tiles:put name='side' type='string' >
        <msh:sideMenu>
            <msh:sideLink key='ALT+1' action="/entitySubclassView-mis_medCase.do?"
                          name="Текущий случай медицинского обслуживания" params="id,medcase"/>
            <msh:sideLink key="ALT+2" styleId="${param.stAll}" params="id,medcase" action="/printProtocolsBySLO.do?stAll=selected" name="Полный список" />
            <msh:sideLink key="ALT+3" styleId="${param.stNoPrint}" params="id,medcase" action="/printProtocolsBySLO.do?stNoPrint=selected" name="Список нераспечатанных протоколов"/>
            <msh:sideLink key="ALT+4" styleId="${param.stPrint}" params="id,medcase" action="/printProtocolsBySLO.do?stPrint=selected" name="Список распечатанных протоколов"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
    if (RolesHelper.checkRoles("/Policy/Mis/MisLpu/Psychiatry", request)) {
    	String stNoPrint = request.getParameter("stNoPrint") ;
    	String stPrint = request.getParameter("stPrint") ;
    	if (stNoPrint!=null &&!stNoPrint.equals("")) {
    		request.setAttribute("dop"," and d.printDate is null");
    	} else if (stPrint!=null &&!stPrint.equals("")) {
    		request.setAttribute("dop"," and d.printDate is not null");
    	}	
    	request.setAttribute("whereSQL","slo.id='"+request.getParameter("id")+"'") ;
    	%>
        <msh:section>
            <msh:sectionTitle>Протоколы по случаю медицинского обслуживания</msh:sectionTitle>
            <msh:sectionContent>
            	<ecom:webQuery name="protocols" nativeSql="
            	select to_char(d.dateRegistration,'yyyymmdd')||'!'||cast(d.timeRegistration as varchar(5))||'!'||d.id as id , d.dateRegistration, d.timeRegistration, d.record, d.printDate , vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doctor 
from Diary as d
left join MedCase slo  on slo.id=d.medCase_id 
left join WorkFunction wf on wf.id=d.specialist_id 
left join Worker w on w.id=wf.worker_id 
left join Patient pw on pw.id=w.person_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
where (slo.id='${param.id}' or slo.parent_id='${param.id}') and upper(d.DTYPE)='PROTOCOL'
				${dop}  
order by d.dateRegistration,d.timeRegistration
"/>
                <msh:table selection="multiply" name="protocols" action="js-smo_visitProtocol-viewProtocol.do" idField="1" noDataMessage="Нет протоколов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='8'>
                                <msh:toolbar>
                                    <a href='javascript:printProtocols("protocols")'>Печать протоколов</a>
                                    <a href='javascript:printProtocols("protocols1")'>Печать протоколов (шаблон 2)</a>
                                    <a href='javascript:printProtocols("protocols_no_sign")'>Печать протоколов (без подписи)</a>
                                    <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'>Добавить протокол</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Специалист" property="6"/>
                    <msh:tableColumn columnName="Протокол" property="4" cssClass="preCell"/>
                    <msh:tableColumn columnName="Дата печати" property="5"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

<%
    } else {
    	String stNoPrint = request.getParameter("stNoPrint") ;
    	String stPrint = request.getParameter("stPrint") ;
    	String type = request.getParameter("type");
    	if (stNoPrint!=null &&!stNoPrint.equals("")) {
    		request.setAttribute("dop"," and d.printDate is null");
    	} else if (stPrint!=null &&!stPrint.equals("")) {
    		request.setAttribute("dop"," and d.printDate is not null");
    	}
    	
    	List l = ActionUtil.getListObjFromNativeQuery("select sls.dtype,sls.patient_id,to_char(sls.datestart,'dd.mm.yyyy') as dat1,to_char(coalesce(sls.datefinish,current_date),'dd.mm.yyyy') as dat2 from medcase slo left join medcase sls on sls.id=slo.parent_id where slo.id="+request.getParameter("id")+" and slo.dtype='DepartmentMedCase'", request) ;
		if (!l.isEmpty()) {
			if (type!=null && !type.equals("") && !type.equals("0"))  {
				if (type.equals("1")) {
					request.setAttribute("whereSQL", "slo.id='"+request.getParameter("id")+"'");
					request.setAttribute("title",": Дневники") ;
				} else if (type.equals("2")) { //lab
					request.setAttribute("title",": Лабораторные исследования") ;
					request.setAttribute("whereSQL","slo.id='"+request.getParameter("id")+"' and aslo.dtype='Visit' and vst.code='LABSURVEY'") ;
				} else if (type.equals("3")) {
		    			Object[] obj = (Object[])l.get(0) ;
		    			request.setAttribute("whereSQL","slo.patient_id='"+obj[1]+"' and aslo.datestart between to_date('"+obj[2]+"','dd.mm.yyyy') and to_date('"+obj[3]+"','dd.mm.yyyy') and aslo.dtype='Visit' and (vst.code='DIAGNOSTIC' or vst.code='SERVICE')") ;
		    		
		    		request.setAttribute("title",": Диагностические исследования") ;
				}
			} else {
				Object[] obj = (Object[])l.get(0) ;
				request.setAttribute("whereSQL"," (slo.id='"+request.getParameter("id")+"' or (slo.patient_id='"
		    		+obj[1]+"' and aslo.datestart between to_date('"+obj[2]+"','dd.MM.yyyy') and to_date('"+obj[3]+"','dd.MM.yyyy') and aslo.dtype='Visit' and (vst.code='DIAGNOSTIC' or vst.code='SERVICE')))") ;
			}
			
		} else {
			request.setAttribute("whereSQL", "slo.id='"+request.getParameter("id")+"'");
			request.setAttribute("title",": Диагностические исследования") ;
		}

		
%>
        <msh:section>
            <msh:sectionTitle>Протоколы по случаю медицинского обслуживания${title}</msh:sectionTitle>
            <msh:sectionContent>
            	<ecom:webQuery name="protocols" nameFldSql="protocols_sql" nativeSql="
            	select to_char(d.dateRegistration,'yyyymmdd')||'!'||cast(d.timeRegistration as varchar(5))||'!'||d.id as id
            	, d.dateRegistration, d.timeRegistration, case when count(mc2.id)>0 then list(mc2.code||' '||mc2.name)||'<br>' when list(aslo.dtype)='Visit' and count (mc.id)>0 then list(mc.code||' '||mc.name)||'<br>' else '' end ||' '|| d.record, d.printDate
            	, vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doctor
            	from MedCase slo
      left join MedCase aslo on aslo.parent_id=slo.parent_id
      left join medcase servmc on servmc.parent_id=aslo.id
      left join medservice mc on mc.id=servmc.medservice_id
            left join VocServiceType vst on vst.id=mc.serviceType_id
      left join Diary as d on aslo.id=d.medCase_id
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      left join medcase smc2 on smc2.id=d.servicemedcase_id
      left join medservice mc2 on mc2.id=smc2.medservice_id
      
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            	where ${whereSQL} and d.DTYPE='Protocol'
				${dop} 
				group by d.dateRegistration,d.timeRegistration,d.id,d.record,d.printDate,vwf.name, pw.lastname, pw.firstname, pw.middlename
				order by d.dateRegistration,d.timeRegistration"/>
                <msh:table selection="multiply" name="protocols" action="js-smo_visitProtocol-viewProtocol.do" idField="1" noDataMessage="Нет протоколов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='8'>
                                <msh:toolbar>
                                    <a href='javascript:printProtocols("protocols")'>Печать протоколов</a>
                                    <a href='javascript:printProtocols("protocols1")'>Печать протоколов (шаблон 2)</a>
                                    <a href='javascript:printProtocols("protocols_no_sign")'>Печать протоколов (без подписи)</a>
                                    <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'>Добавить протокол</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Специалист" property="6"/>
                    <msh:tableColumn columnName="Протокол" property="4" cssClass="preCell"/>
                    <msh:tableColumn columnName="Дата печати" property="5"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

<%    	
    	
    }
    %>

<tags:stac_selectPrinter  name="Select" roles="/Policy/Config/SelectPrinter" />
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function printProtocols(aFile) {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","protocols") ;
            	if(ids) {
            		var p = 'print-'+aFile+'.do?multy=1&m=printProtocols&s=HospitalPrintService1&'+ids ;
            		initSelectPrinter(p,0);
            		
            		
            	} else {
            		alert("Нет выделенных протоколов");
            	}
            	
            }

        </script>
    </tiles:put>


</tiles:insert>