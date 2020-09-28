<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.poly.web.action.ticket.SearchForm"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Поиск талонов по специалисту</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketspec"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
        	String login = LoginInfo.find(request.getSession(true)).getUsername() ;
        	request.setAttribute("login", login) ;
        	boolean isNotViewPD = RolesHelper.checkRoles("/Policy/Jaas/SecUser/PersonalData/NotView", request) ;
        	if (isNotViewPD) {
        		request.setAttribute("lastnameSql", "p.patientSync||' '||substring(p.lastname,1,1)") ;
        	} else {
        		request.setAttribute("lastnameSql", "p.lastname") ;
        	}
        %>
        <ecom:webQuery name="infoByLogin"
        maxResult="1" nativeSql="
        select wf.id,w.lpu_id,case when wf.isAdministrator='1' then '1' else null end as isAdmin
        from SecUser su
        left join WorkFunction wf on su.id=wf.secUSer_id
        left join Worker w on wf.worker_id=w.id
        where su.login='${login}'
        "
        />
        
            <msh:form action="/smo_ticketspec.do" defaultField="doctorName" method="GET">
                <msh:panel colsWidth="10%,10%,80%">
                	<msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
		            <msh:ifNotInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
	                    <msh:row>
	                        <msh:autoComplete property="doctor" label="Специалист" size='50' horizontalFill="true"
	                                          vocName="workFunction" fieldColSpan="3"/>
	                    </msh:row>
	    	        </msh:ifNotInRole>
		            <msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
		            	<msh:hidden property="doctor"/>
		            </msh:ifInRole>
                    </msh:ifNotInRole>
                    <msh:row>                      
                        <msh:textField property="dateFilter" label="Дата обращения" size='10' />
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            <msh:ifInRole roles="/Policy/Poly/Ticket/IsDoctorEdit">
            </msh:ifInRole>
            <% 
            boolean isRoleDoc = RolesHelper.checkRoles("/Policy/Poly/Ticket/IsDoctorEdit", request) ;
            SearchForm form = session.getAttribute("mis_searchForm")!=null ? (SearchForm) session.getAttribute("mis_searchForm") : null ;
            if (form!=null ) {
				String workFunc ;
            	request.setAttribute("dateFilter", form.getDateFilter()) ;
                if (isRoleDoc) {
                	List list= (List)request.getAttribute("infoByLogin");
        	    	WebQueryResult wqr = list.isEmpty() ? null : (WebQueryResult)list.get(0) ;
        	    	workFunc = wqr!=null ? wqr.get1().toString() : "0" ;
                } else {
					workFunc = form.getDoctor()!=null && form.getDoctor() > 0L ? "" +form.getDoctor() : null;
                }
                if (workFunc!=null) {
                	request.setAttribute("workFunctionSql", " and smc.workFunctionExecute_id='"+workFunc+"'") ;
                }
            %>
            
            <msh:section title="Направленные пациенты">
            <ecom:webQuery name="listDirect" 
            nativeSql="select smc.id as smcid, smc.dateFinish as smcorderDate
            ,${lastnameSql}||' '||p.firstname||' '||p.middlename as patientName
            ,vwf.name||' '||wp.lastname
             from MedCase smc 
             left join Patient p on p.id=smc.patient_id
             left join WorkFunction wf on wf.id=smc.workFunctionExecute_id
             left join Worker w on w.id=wf.worker_id
             left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
             left join Patient wp on wp.id=w.person_id
             where smc.dtype='ShortMedCase' ${workFunctionSql}
             and smc.dateFinish=to_date('${dateFilter}','dd.mm.yyyy') and smc.dateStart is null"/>
	        <msh:table viewUrl="entityShortView-smo_ticket.do" 
	        editUrl="entityParentEdit-smo_ticket.do" 
	        deleteUrl="entityParentDeleteGoParentView-smo_ticket.do" 
	        name="listDirect" action="entityParentEdit-smo_ticket.do" 
	        idField="1" noDataMessage="Не найдено">
	            <msh:tableColumn columnName="Номер" property="1"/>
	            <msh:tableColumn columnName="Дата приема" property="2"/>
	            <msh:tableColumn columnName="Пациент" property="3" />
	            <msh:tableColumn columnName="Специалист" property="4" />
	        </msh:table>
            </msh:section>

            <msh:section title="Принятые пациенты">
            <ecom:webQuery name="listAccepted" 
	            nativeSql="select smc.id as smcid, smc.dateStart as smcorderDate
	            ,cast(smc.timeExecute as varchar(5))
	            ,${lastnameSql}||' '||p.firstname||' '||p.middlename as patientName
	            ,vwf.name||' '||wp.lastname
	             from MedCase smc 
	             left join Patient p on p.id=smc.patient_id
	             left join WorkFunction wf on wf.id=smc.workFunctionExecute_id
	             left join Worker w on w.id=wf.worker_id
	             left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
	             left join Patient wp on wp.id=w.person_id
	             where smc.dtype='ShortMedCase' 
	              and smc.dateStart=to_date('${dateFilter}','dd.mm.yyyy')
	             ${workFunctionSql}
	             "/>
	        <msh:table viewUrl="entityShortView-smo_ticket.do" 
	        editUrl="entityParentEdit-smo_ticket.do" 
	        deleteUrl="entityParentDeleteGoParentView-smo_ticket.do"
	         name="listAccepted" action="entityView-smo_ticket.do" idField="1" noDataMessage="Не найдено">
	            <msh:tableColumn columnName="Номер" property="1"/>
	            <msh:tableColumn columnName="Дата приема" property="2"/>
	            <msh:tableColumn columnName="Время приема" property="3"/>
	            <msh:tableColumn columnName="Пациент" property="4" />
	            <msh:tableColumn columnName="Специалист" property="5" />
	        </msh:table>
        </msh:section>
        <%} else { %>
        <i>Нет данных</i>
        <% } %>
    </tiles:put>

</tiles:insert>