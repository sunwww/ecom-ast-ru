<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient"  beginForm="mis_medCaseForm" title="Список протоколов"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
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
    	String stNoPrint = request.getParameter("stNoPrint") ;
    	String stPrint = request.getParameter("stPrint") ;
    	if (stNoPrint!=null &&!stNoPrint.equals("")) {
    		request.setAttribute("dop"," and d.printDate is null");
    	} else if (stPrint!=null &&!stPrint.equals("")) {
    		request.setAttribute("dop"," and d.printDate is not null");
    	}
    %>
        <msh:section>
            <msh:sectionTitle>Протоколы по случаю медицинского обслуживания</msh:sectionTitle>
            <msh:sectionContent>
            	<ecom:webQuery name="protocols" nativeSql="
            	select to_char(d.dateRegistration,'dd.mm.yyyy')||'!'||cast(d.timeRegistration as varchar(5))||'!'||d.id as id
            	, d.dateRegistration, d.timeRegistration, d.record, d.printDate 
            	, vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doctor
            	from MedCase slo
      left join MedCase aslo on aslo.parent_id=slo.parent_id
      left join Diary as d on aslo.id=d.medCase_id
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            	where slo.id='${param.id}' and d.DTYPE='Protocol'
				${dop}
				order by d.dateRegistration,d.timeRegistration"/>
                <msh:table selection="multiply" name="protocols" action="js-smo_visitProtocol-viewProtocol.do" idField="1" noDataMessage="Нет протоколов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='8'>
                                <msh:toolbar>
                                    <a href='javascript:printProtocols("protocols")'>Печать протоколов</a>
                                    <a href='javascript:printProtocols("protocols1")'>Печать протоколов (шаблон 2)</a>
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


    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function printProtocols(aFile) {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","protocols") ;
            	if(ids) {
            		//alert(ids) ;
            		window.location = 'print-'+aFile+'.do?multy=1&m=printProtocols&s=HospitalPrintService1&'+ids ;
            		
            	} else {
            		alert("Нет выделенных протоколов");
            	}
            	
            }

        </script>
    </tiles:put>


</tiles:insert>