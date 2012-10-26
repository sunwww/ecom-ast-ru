<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title>Список выписок</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Хир.операций">
            <msh:sideLink key="ALT+2" action="/stac_print_surOperation.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stAll=selected" name="Полный список" />
            <msh:sideLink key="ALT+3" action="/stac_print_surOperation.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stNoPrint=selected" name="Список нераспечатанных"/>
            <msh:sideLink key="ALT+4" action="/stac_print_surOperation.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stPrint=selected" name="Список распечатанных"/>
        </msh:sideMenu>
        <msh:sideMenu title="Протоколы">
            <msh:sideLink key="ALT+5" action="/stac_print_protocol.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stAll=selected" name="Полный список" />
            <msh:sideLink key="ALT+6" action="/stac_print_protocol.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stNoPrint=selected" name="Список нераспечатанных"/>
            <msh:sideLink key="ALT+7" action="/stac_print_protocol.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stPrint=selected" name="Список распечатанных"/>
        </msh:sideMenu>
        <msh:sideMenu title="Выписки">
            <msh:sideLink key="ALT+8" styleId="${param.stAll}" action="/stac_print_discharge.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stAll=selected" name="Полный список" />
            <msh:sideLink key="ALT+9" styleId="${param.stNoPrint}" action="/stac_print_discharge.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stNoPrint=selected" name="Список нераспечатанных"/>
            <msh:sideLink key="ALT+0" styleId="${param.stPrint}" action="/stac_print_discharge.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stPrint=selected" name="Список распечатанных"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
    	String stNoPrint = request.getParameter("stNoPrint") ;
    	String stPrint = request.getParameter("stPrint") ;
    	String department = request.getParameter("department") ;
    	String curator = request.getParameter("curator") ;
    	String owner = request.getParameter("owner") ;
    	if (stNoPrint!=null &&!stNoPrint.equals("")) {
    		request.setAttribute("dop"," and sls.printDate is null");
    	} else if (stPrint!=null &&!stPrint.equals("")) {
    		request.setAttribute("dop"," and sls.printDate is not null");
    	}
    	if (curator!=null&&!curator.equals("")) {
    		request.setAttribute("filterAdd"," and mc.ownerFunction_id='"+curator+"'") ;
    		request.setAttribute("title","по лечащему врачу") ;
    	} else if (owner!=null&&!owner.equals("")) {
    		request.setAttribute("filterAdd"," and mc.ownerFunction_id='"+curator+"'") ;
    		request.setAttribute("title","свои осмотры") ;
    	} else if (department!=null&&!department.equals("")) {
    		request.setAttribute("filterAdd"," and (mc.department_id='"+department+"' and (ml.isNoOmc='0' or ml.isNoOmc is null) or pslo.department_id='"+department+"' and ml.isNoOmc='1')") ;
    		request.setAttribute("title","по выписанным из отделения") ;
    	} else {
    		request.setAttribute("filterAdd"," and mc.ownerFunction_id='0'") ;
    	}
    	
    %>
        <msh:section>
            <msh:sectionTitle>Выписки ${title}</msh:sectionTitle>
            <msh:sectionContent>
            	<ecom:webQuery name="protocols"  nativeSql="select 
            	mc.dateStart||'!'||mc.entranceTime||'!'||sls.id as idf
            	, to_char(mc.dateStart,'dd.mm.yyyy')||' '||cast(mc.entranceTime as varchar(5)) as datereg
            	, substring(sls.dischargeEpicrisis,1,100)||'...' as drecord
            	, sls.printDate as dpritndate
            	,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as owner
            	,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as patinfo
    			,sc.code as sccode
            	from MedCase mc
            	left join MisLpu ml on ml.id=mc.department_id
            	left join MedCase pslo on pslo.id=mc.prevMedCase_id
            	left join Patient pat on pat.id=mc.patient_id
            	left join MedCase as sls on sls.id = mc.parent_id 
    			left join StatisticStub as sc on sc.medCase_id=sls.id 
            	left join WorkFunction wf on wf.id=mc.ownerFunction_id
            	left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    			left join Worker w on w.id=wf.worker_id
    			left join Patient wp on wp.id=w.person_id
            	where mc.dtype='DepartmentMedCase' and mc.dateFinish 
            	between current_date-3 and current_date
            	${filterAdd}
            	
            	${dop}
            	order by pat.lastname,pat.id,sls.dateFinish,sls.dischargetime"/>
                <msh:table selection="multiply"
                viewUrl="js-stac_sslDischarge-viewShortProtocol.do?short=Short"
                 name="protocols" action="js-stac_sslDischarge-viewProtocol.do" idField="1" noDataMessage="Нет протоколов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='11'>
                                <msh:toolbar>
                                    <a href='javascript:printDischarge("stac_discharge")'>Печать выписок</a>
                                    <a href='javascript:printStatCards("stac_statCards")'>Печать стат.карт</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="№стат. карты" property="7"/>
                    <msh:tableColumn columnName="ФИО пациента" property="6"/>
                    <msh:tableColumn columnName="Дата и время" property="2"/>
                    <msh:tableColumn columnName="Лечащий врач" property="5"/>
                    <msh:tableColumn columnName="Дата печати" property="4"/>
                    <msh:tableColumn columnName="Протокол" property="3" cssClass="preCell"/>
                    
                </msh:table>
            </msh:sectionContent>
        </msh:section>


    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function printDischarge(aFile) {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","protocols") ;
            	if(ids) {
            		//alert(ids) ;
            		window.location = 'print-'+aFile+'.do?multy=1&m=printBillings&s=HospitalPrintService1&'+ids ;
            		
            	} else {
            		alert("Нет выделенных случаев");
            	}
            	
            }
            function printStatCards(aFile) {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","protocols") ;
            	if(ids) {
            		//alert(ids) ;
            		window.location = 'print-'+aFile+'.do?multy=1&m=printStatCards&s=HospitalPrintService1&'+ids ;
            		
            	} else {
            		alert("Нет выделенных случаев");
            	}
            	
            }

        </script>
    </tiles:put>


</tiles:insert>