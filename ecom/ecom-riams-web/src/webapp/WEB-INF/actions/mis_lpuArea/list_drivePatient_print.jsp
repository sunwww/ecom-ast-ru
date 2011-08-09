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
    <msh:sectionContent>
      
    <ecom:webQuery name="journal_ticket" nativeSql="
   select distinct pcc.id as pccid
   ,pcc.cardNumber as pcccardNumber
   ,coalesce(p.lastname,'-')||' '||coalesce(p.firstname,'-')|| ' '||coalesce(p.middlename,'-') as lfm
   ,area.startDate as areastartdate,area.finishDate as areafinishdate,p.birthday as pbirthday 
   ,$$ByPatient^ZAddressLib(p.id)
   ,$$getDiagnosis^ZPsychUtil(p.id,isnull(area.finishDate,CURRENT_DATE))
   from PsychiatricCareCard pcc 
   left join Patient p on p.id=pcc.patient_id 
   left join LpuAreaPsychCareCard area on pcc.id=area.careCard_id 
   ${lpo}
   where  area.lpuArea_id=${param.id} 
   ${dateT} ${dateF} ${typeI} ${group} order by p.lastname,p.firstname,p.middlename" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_ticket" action="entityView-psych_careCard.do" idField="1" noDataMessage="Не найдено">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn columnName="№карты" property="2"/>
			<msh:tableColumn columnName="ФИО пациента" property="3"/>
			<msh:tableColumn columnName="Год рождения" property="6"/>
			<msh:tableColumn columnName="Дата прибытия" property="4"/>
			<msh:tableColumn columnName="Дата убытия" property="5"/>
			<msh:tableColumn columnName="Адрес" property="7"/>
			<msh:tableColumn columnName="Диагноз" property="8"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
 
  </tiles:put>
</tiles:insert>

