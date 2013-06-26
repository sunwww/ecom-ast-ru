<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Открытые направления на ВК</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="journalOpenKER"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
    	<%
    	String date = request.getParameter("orderDate") ;
    	if (date==null) {
    	%>
    	<ecom:webQuery name="list"
    	nativeSql="select 
			 '&orderDate='||to_char(cec.orderDate,'dd.mm.yyyy') as id,to_char(cec.orderDate,'dd.mm.yyyy') as orderDate, count(*) as cnt 
			    	from ClinicExpertCard cec 
			where expertdate is null
			group by cec.orderDate
			order by cec.orderDate"
    	/>
        <msh:table name="list" viewUrl="expert_kersopen.do?short=Short" action="expert_kersopen.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата направления" property="2"/>
            <msh:tableColumn columnName="Кол-во" property="3"/>
        </msh:table>
        <% } else { %>
	       	<ecom:webQuery name="list"
		    	nativeSql="select 
cec.id,cec.orderDate,cec.disabilitydate
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename as workfunction
,p.lastname||' '||p.firstname||' '||p.middlename as fio

from ClinicExpertCard cec
left join WorkFunction owf on owf.id=cec.orderFunction_id
left join Worker ow on ow.id=owf.worker_id
left join Patient owp on owp.id=ow.person_id
left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
left join Patient p on p.id=cec.patient_id
where cec.orderDate=to_date('${param.orderDate}','dd.mm.yyyy') 
		    	and cec.expertDate is null 
"
		    	/>
            <msh:table name="list" action="entityParentEdit-expert_ker.do" idField="1" noDataMessage="Не найдено">
		      <msh:tableColumn columnName="Номер" property="1"/>
		      <msh:tableColumn columnName="Дата направления" property="2"/>
		      <msh:tableColumn columnName="Дата выхода на нетрудоспособность" property="3"/>
		      <msh:tableColumn columnName="Специалист" property="4"/>
		      <msh:tableColumn columnName="Пациент" property="5"/>
		    </msh:table>
        <% }%>
    </tiles:put>

</tiles:insert>