<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select p.id,t.date,$$CheckPatientOMC^ZMedPolicy(m.person_id,t.date),p.lastname||' '||p.firstname||' '||p.middlename,count(*),m.number from Ticket t left join medcard as m on m.id=t.medcard_id left join patient p on p.id=m.person_id where t.date  between '${param.dateBegin}'  and '${param.dateEnd}' ${add} and $$CheckPatientOMC^ZMedPolicy(m.person_id,t.date) is not null group by m.id" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_ticket" action="entityView-mis_patient.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="ФИО пациента" property="4"/>
            <msh:tableColumn columnName="№медкарты" property="6"/>
            <msh:tableColumn columnName="Полис" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="5"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
    	</tiles:put>
</tiles:insert>
