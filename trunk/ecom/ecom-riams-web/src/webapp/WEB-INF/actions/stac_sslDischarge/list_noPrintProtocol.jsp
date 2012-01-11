<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient"  beginForm="mis_medCaseForm" title="Список протоколов"/>
    </tiles:put>

    <tiles:put name='side' type='string'>

        <msh:sideMenu title="Списки протоколов">
            <msh:sideLink key="ALT+2" styleId="${param.stAll}" params="id" action="/printProtocolsBySLS.do?stAll=selected" name="все" />
            <msh:sideLink key="ALT+3" styleId="${param.stNoPrint}" params="id" action="/printProtocolsBySLS.do?stNoPrint=selected" name="нераспечатанные"/>
            <msh:sideLink key="ALT+4" styleId="${param.stPrint}" params="id" action="/printProtocolsBySLS.do?stPrint=selected" name="распечатанные"/>
        </msh:sideMenu>
        <msh:sideMenu>
        </msh:sideMenu>
        <msh:sideMenu title="Текущий случай лечения в стационаре">
            <msh:sideLink key='ALT+1' action="/entitySubclassView-mis_medCase.do?"
                          name="Текущий случай лечения в стационаре" params="id"/>
        </msh:sideMenu>
                <tags:stac_hospitalMenu currentAction="stac_protocol" />
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
    	String stNoPrint = request.getParameter("stNoPrint") ;
    	String stPrint = request.getParameter("stPrint") ;
    	if (stNoPrint!=null &&!stNoPrint.equals("")) {
    		request.setAttribute("dop"," and p.printDate is null");
    	} else if (stPrint!=null &&!stPrint.equals("")) {
    		request.setAttribute("dop"," and p.printDate is not null");
    	}
    %>
        <msh:section>
            <msh:sectionTitle>Протоколы по случаю лечения в стационаре (и отделениях) </msh:sectionTitle>
            <msh:sectionContent>
			<ecom:webQuery name="protocols"  nativeSql="select p.dateRegistration ||'!'||p.timeRegistration ||'!'||p.id, p.dateRegistration, p.timeRegistration, p.record, p.printDate 
				, case when m.DTYPE='HospitalMedCase' then 'Приемное отделение' when m.DTYPE='DepartmentMedCase' then d.name else '' end
				from Diary p
				left join MedCase m on m.id=p.medcase_id 
				left join MisLpu d on d.id=m.department_id
            	where (m.parent_id='${param.id}' and m.dtype='DepartmentMedCase' or m.id='${param.id}' and m.dtype='HospitalMedCase')
            	${dop}
            	order by p.dateRegistration,p.timeRegistration"/>
                <msh:table selection="multiply" name="protocols" action="js-smo_visitProtocol-viewProtocol.do" idField="1" noDataMessage="Нет протоколов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='8'>
                                <msh:toolbar>
                                    <a href='javascript:printProtocols()'>Печать протоколов</a>
                                    <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'>Добавить протокол</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Отделение" property="6"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Протокол" property="4" cssClass="preCell"/>
                    <msh:tableColumn columnName="Дата печати" property="5"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>


    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function printProtocols() {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","protocols") ;
            	if(ids) {
            		//alert(ids) ;
            		window.location = 'print-protocols.do?multy=1&m=printProtocols&s=HospitalPrintService&'+ids ;
            		
            	} else {
            		alert("Нет выделенных протоколов");
            	}
            	
            }

        </script>
    </tiles:put>


</tiles:insert>