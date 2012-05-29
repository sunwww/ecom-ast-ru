<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard" guid="4b11dc98-30fc-413e-8bc6-976f292e704f">
   	Список талонов за ${date} по специалисту ${specInfo}
    </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:ticket_finds currentAction="ticketsBySpec" />
  </tiles:put>
  <tiles:put name="body" type="string">
      <ecom:webQuery name="journal_priem" nativeSql=" select t.id as tid,m.number as mnumber
    , p.lastname||' '|| p.firstname||' '||coalesce(p.middlename,'') as fiopat,p.birthday
    ,  t.id as tid,t.date as tdate
    ,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo
    ,mkb.code as mkbcode ,vr.name as vrname
    ,case when cast(t.talk as int)=1 then 1 else null end as talk  
    from Ticket t left join medcard m on m.id=t.medcard_id     
    left join Patient p on p.id=m.person_id     
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join workfunction wf on wf.id=t.workFunction_id    
    left join vocworkfunction vwf on vwf.id=wf.workFunction_id    
    left join worker  w on w.id=wf.worker_id    
    left join patient wp on wp.id=w.person_id    
    left join vocIdc10 mkb on mkb.id=t.idc10_id    
    left join vocreason vr on vr.id=t.vocreason_id    
    where t.date  =to_date('${date}','dd.mm.yyyy')  
    and t.status='2'  and t.workFunction_id='${spec}' ${add} 
    
       order by p.lastname,p.firstname,p.middlename" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
  
	<msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
    <msh:table name="journal_priem" viewUrl="entityShortView-poly_ticket.do" 
    editUrl="entityParentEdit-poly_ticket.do"
     action="entityParentEdit-poly_ticket.do" idField="1" noDataMessage="Не найдено" guid="6600cebc-4548-4f57-a048-5a3a2e67a673">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="№мед.карты" property="2" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="ФИО пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />      
      <msh:tableColumn columnName="№талона" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата приема" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Специалист" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Диагноз" property="8" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Цель посещения" property="9" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Беседа с родс." property="10" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
    </msh:table>
	</msh:ifInRole>
	<msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
    <msh:table name="journal_priem" viewUrl="entityShortView-poly_ticket.do" 
    editUrl="entityParentEdit-poly_ticket.do"
     action="entityParentEdit-poly_ticket.do" idField="1" noDataMessage="Не найдено" guid="6600cebc-4548-4f57-a048-5a3a2e67a673">
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
	</msh:ifNotInRole>
  </tiles:put>
</tiles:insert>

