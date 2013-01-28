<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title>Список хирургических оперций</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Хир.операций">
            <msh:sideLink key="ALT+2" styleId="${param.stAll}" action="/stac_print_surOperation.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stAll=selected" name="Полный список" />
            <msh:sideLink key="ALT+3" styleId="${param.stNoPrint}" action="/stac_print_surOperation.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stNoPrint=selected" name="Список нераспечатанных"/>
            <msh:sideLink key="ALT+4" styleId="${param.stPrint}" action="/stac_print_surOperation.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stPrint=selected" name="Список распечатанных"/>
        </msh:sideMenu>
        <msh:sideMenu title="Протоколы">
            <msh:sideLink key="ALT+5" action="/stac_print_protocol.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stAll=selected" name="Полный список" />
            <msh:sideLink key="ALT+6" action="/stac_print_protocol.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stNoPrint=selected" name="Список нераспечатанных"/>
            <msh:sideLink key="ALT+7" action="/stac_print_protocol.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stPrint=selected" name="Список распечатанных"/>
        </msh:sideMenu>
        <msh:sideMenu title="Выписки">
            <msh:sideLink key="ALT+8" action="/stac_print_discharge.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stAll=selected" name="Полный список" />
            <msh:sideLink key="ALT+9" action="/stac_print_discharge.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stNoPrint=selected" name="Список нераспечатанных"/>
            <msh:sideLink key="ALT+0" action="/stac_print_discharge.do?department=${param.department}&curator=${param.curator}&owner=${param.owner}&stPrint=selected" name="Список распечатанных"/>
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
    		request.setAttribute("dop"," and d.printDate is null");
    	} else if (stPrint!=null &&!stPrint.equals("")) {
    		request.setAttribute("dop"," and d.printDate is not null");
    	}
    	if (curator!=null&&!curator.equals("")) {
    		request.setAttribute("filterAdd"," and mc.ownerFunction_id='"+curator+"'") ;
    		request.setAttribute("title","по лечащему врачу") ;
    	} else if (owner!=null&&!owner.equals("")) {
    		request.setAttribute("filterAdd"," and d.surgeon_id='"+owner+"'") ;
    		request.setAttribute("title","свои операции") ;
    	} else if (department!=null&&!department.equals("")) {
    		request.setAttribute("filterAdd"," and mc.department_id='"+department+"'") ;
    		request.setAttribute("title","по состоящим в отделении") ;
    	} else {
    		request.setAttribute("filterAdd"," and mc.ownerFunction_id='0'") ;
    	}
    	
    %>
        <msh:section>
            <msh:sectionTitle>Хирургические операции ${title}</msh:sectionTitle>
            <msh:sectionContent>
            	<ecom:webQuery name="protocols"  nativeSql="select 
            	coalesce(to_char(d.operationDate,'yyyymmdd'),'')||'!'||coalesce(cast(d.operationTime as varchar(5)),'')||'!'||d.id as idf
            	, to_char(d.operationDate,'dd.mm.yyyy')||' '||cast(d.operationTime as varchar(5)) as datereg
            	, substring(d.operationText,1,100)||'...' as drecord
            	, d.printDate as dpritndate
            	,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as owner
            	,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as patinfo
    			,sc.code as sccode
            	from MedCase mc
            	left join SurgicalOperation d on mc.id=d.medCase_id
            	left join Patient pat on pat.id=mc.patient_id
            	left join MedCase as sls on sls.id = mc.parent_id 
    			left join StatisticStub as sc on sc.medCase_id=sls.id 
            	left join WorkFunction wf on wf.id=d.surgeon_id
            	left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    			left join Worker w on w.id=wf.worker_id
    			left join Patient wp on wp.id=w.person_id
            	where sls.dtype='HospitalMedCase' 
            	and (sls.dateFinish is null or sls.dateFinish>(CURRENT_DATE-2))
            	${filterAdd}
            	and d.id is not null 
            	${dop}
            	
            	order by pat.lastname,pat.id,d.operationDate,d.operationTime"/>
                <msh:table selection="multiply"
                viewUrl="js-stac_surOperation-viewShortOperation.do"
                 name="protocols" action="js-stac_surOperation-viewOperation.do" idField="1" noDataMessage="Нет протоколов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='11'>
                                <msh:toolbar>
                                    <a href='javascript:printProtocols("stac_surOperations")'>Печать хирургических операций</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="№стат. карты" property="7"/>
                    <msh:tableColumn columnName="ФИО пациента" property="6"/>
                    <msh:tableColumn columnName="Дата и время" property="2"/>
                    <msh:tableColumn columnName="Специалист" property="5"/>
                    <msh:tableColumn columnName="Дата печати" property="4"/>
                    <msh:tableColumn columnName="Протокол операции" property="3" cssClass="preCell"/>
                    
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
            		window.location = 'print-'+aFile+'.do?multy=1&m=printSurOperations&s=HospitalPrintService1&'+ids ;
            		
            	} else {
            		alert("Нет выделенных операций");
            	}
            	
            }

        </script>
    </tiles:put>


</tiles:insert>