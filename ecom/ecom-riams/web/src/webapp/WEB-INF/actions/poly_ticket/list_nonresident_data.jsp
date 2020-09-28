<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard">Список талонов ....</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:ticket_finds currentAction="ticketsByResident" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="journal_priem" nativeSql=" select p.id as pid,m.number as mnumber
    , p.lastname||' '|| p.firstname||' '||p.middlename as fiopat,p.birthday
    ,  t.id as tid,t.date as tdate
    ,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo
    ,mkb.code as mkbcode ,vr.name as vrname, case when t.talk=1 then 'Да' else '' end
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
    where t.date  =  to_date('${date}','dd.mm.yyyy')
    and t.status='2' ${add}     order by p.lastname,p.firstname,p.middlename" />
    
    <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
	    <msh:table viewUrl="entityShortView-poly_ticket.do" editUrl="entityParentEdit-poly_ticket.do" 
	    deleteUrl="entityParentDeleteGoParentView-poly_ticket.do" 
	    name="journal_priem" action="entityParentView-poly_ticket.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="№мед.карты" property="2" />
	      <msh:tableColumn columnName="ФИО пациента" property="3" />
	      <msh:tableColumn columnName="Год рождения" property="4" />
	      <msh:tableColumn columnName="№талона" property="5" />
	      <msh:tableColumn columnName="Дата приема" property="6" />
	      <msh:tableColumn columnName="Специалист" property="7" />
	      <msh:tableColumn columnName="Диагноз" property="8" />
	      <msh:tableColumn columnName="Цель посещения" property="9" />
	      <msh:tableColumn columnName="Беседа с родст." property="10" />
	    </msh:table>
    
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
	    <msh:table viewUrl="entityShortView-poly_ticket.do" editUrl="entityParentEdit-poly_ticket.do" 
	    deleteUrl="entityParentDeleteGoParentView-poly_ticket.do" 
	    name="journal_priem" action="entityParentView-poly_ticket.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="№мед.карты" property="2" />
	      <msh:tableColumn columnName="ФИО пациента" property="3" />
	      <msh:tableColumn columnName="Год рождения" property="4" />
	      <msh:tableColumn columnName="№талона" property="5" />
	      <msh:tableColumn columnName="Дата приема" property="6" />
	      <msh:tableColumn columnName="Специалист" property="7" />
	      <msh:tableColumn columnName="Диагноз" property="8" />
	      <msh:tableColumn columnName="Цель посещения" property="9" />
	    </msh:table>
    
    </msh:ifNotInRole>
  </tiles:put>
</tiles:insert>

