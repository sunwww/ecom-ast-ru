<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Medcard" beginForm="poly_medcardForm" title="Талоны"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="tickets"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            
            <ecom:webQuery name="list"
            nativeSql="select smo.id,to_char(dateFinish,'dd.mm.yyyy') as dateFinish,to_char(dateStart,'dd.mm.yyyy') as dateStart,vwf.name||' '||wp.lastname,smo.isTalk from MedCase smo
left join MedCard mc on smo.patient_id=mc.person_id
            left join WorkFunction wf on wf.id=smo.workFunctionExecute_id
            left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            left join Worker w on w.id=wf.worker_id
            left join Patient wp on wp.id=w.person_id 
            where mc.id='${param.id}'
            and smo.dtype='ShortMedCase' and mc.id=smo.medcard_id
            order by smo.dateStart"/>
            <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="list" 
        deleteUrl="entityParentDeleteGoParentView-smo_ticket.do" editUrl="entityParentEdit-smo_ticket.do" viewUrl="entityParentView-smo_ticket.do?short=Short"
        action="entityParentView-smo_ticket.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="1"/>
            <msh:tableColumn columnName="Дата направления" property="2"/>
            <msh:tableColumn columnName="Дата приема" property="3"/>
            <msh:tableColumn columnName="Специалист" property="4" />
	        <msh:tableColumn columnName="Беседа с родст." property="5"/>
        </msh:table>
            </msh:ifInRole>
            <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="list" 
        deleteUrl="entityParentDeleteGoParentView-smo_ticket.do" editUrl="entityParentEdit-smo_ticket.do" viewUrl="entityParentView-smo_ticket.do?short=Short"
        action="entityParentView-smo_ticket.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="1"/>
            <msh:tableColumn columnName="Дата направления" property="2"/>
            <msh:tableColumn columnName="Дата приема" property="3"/>
            <msh:tableColumn columnName="Специалист" property="4" />
         </msh:table>
            </msh:ifNotInRole>
    </tiles:put>

</tiles:insert>