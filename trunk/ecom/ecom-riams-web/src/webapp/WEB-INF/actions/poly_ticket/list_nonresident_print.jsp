<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    
  <%
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {

    	%>
    	    <msh:section>
    <msh:sectionTitle>Реестр пациентов. ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql=" select p.id as pid,m.number as mnumber
    , p.lastname||' '|| p.firstname||' '||p.middlename as fiopat,p.birthday
    ,  t.id as tid,t.date as tdate
    ,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo,mkb.code as mkbcode ,vr.name as vrname  
    from Ticket t left join medcard m on m.id=t.medcard_id     
    left join Patient p on p.id=m.person_id     
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join workfunction wf on wf.id=t.workFunction_id    
    left join vocworkfunction vwf on vwf.id=wf.workFunction_id    
    left join worker  w on w.id=wf.worker_id    
    left join patient wp on wp.id=w.person_id    
    left join vocIdc10 mkb on mkb.id=t.idc10_id    
    left join vocreason vr on vr.id=t.vocreason_id    
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where t.date  between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${param.dateEnd}','dd.mm.yyyy')  
    and t.status='2' ${add}     order by p.lastname,p.firstname,p.middlename" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="entityParentView-poly_ticket.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="№мед.карты" property="2" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="ФИО пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />      
      <msh:tableColumn columnName="№талона" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата приема" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Специалист" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Диагноз" property="8" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Цель посещения" property="9" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    	</tiles:put>
</tiles:insert>
