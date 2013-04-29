<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Открытые талоны по датам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="ticketopen"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
    	<%
    	String date = request.getParameter("dateFinish") ;
    	if (date==null) {
    	%>
    	<ecom:webQuery name="list"
    	nativeSql="select 
    	'&dateFinish='||to_char(smc.dateFinish,'dd.mm.yyyy') as dateFinish, count(*) as cnt 
    	from MedCase smc 
    	where smc.dtype='ShortMedCase' and smc.dateStart is null 
    	group by smc.dateFinish 
    	order by smc.dateFinish"
    	/>
        <msh:table name="list" action="smo_ticketOpenByDate.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата приема" property="1"/>
            <msh:tableColumn columnName="Кол-во" property="2"/>
        </msh:table>
        <% } else { %>
	       	<ecom:webQuery name="list"
		    	nativeSql="select 
		    	smc.id as smcid,to_char(smc.createDate,'dd.mm.yyyy') as createdate
		    	,cast(smc.createTime as varchar(5)) as createtime
		    	,vwf.name||' '||wp.lastname as workFunction
		    	,p.lastname||p.firstname||p.middlename as patient
		    	from MedCase smc 
		    	where smc.dtype='ShortMedCase' 
		    	and smc.dateFinish=to_date('${param.dateFinish}','dd.mm.yyyy') 
		    	and smc.dateStart is null 
"
		    	/>
            <msh:table name="list" action="entityParentEdit-poly_ticket.do" idField="1" noDataMessage="Не найдено">
		      <msh:tableColumn columnName="Номер" property="1"/>
		      <msh:tableColumn columnName="Дата выдачи" property="2"/>
		      <msh:tableColumn columnName="Время выдачи" property="3"/>
		      <msh:tableColumn columnName="Специалист" property="4"/>
		      <msh:tableColumn columnName="Пациент" property="5"/>
		    </msh:table>
        <% }%>
    </tiles:put>

</tiles:insert>