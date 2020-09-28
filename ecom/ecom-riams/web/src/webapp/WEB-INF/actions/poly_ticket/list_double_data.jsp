<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard">Список дублей талонов</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:ticket_finds currentAction="ticketdouble" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql=" select t.id as tid,m.number as mnumber, p.lastname||' '|| p.firstname||' '||p.middlename ||' г.р.'||to_char(p.birthday,'DD.MM.YYYY'),t.dateCreate,t.date as tdate,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo,mkb.code as mkbcode ,vr.name as vrname  from Ticket t left join medcard m on m.id=t.medcard_id     left join patient p on p.id=m.person_id     left join workfunction wf on wf.id=t.workFunction_id    left join vocworkfunction vwf on vwf.id=wf.workFunction_id    left join worker  w on w.id=wf.worker_id    left join patient wp on wp.id=w.person_id    left join vocIdc10 mkb on mkb.id=t.idc10_id    left join vocreason vr on vr.id=t.vocreason_id    where t.date  =cast('${date}' as date) and t.workfunction_id='${workfunction}' and t.medcard_id='${medcard}' order by t.id" />
    <msh:table name="list" action="entityParentView-poly_ticket.do" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№талона" property="1" />
      <msh:tableColumn columnName="№мед.карты" property="2" />
      <msh:tableColumn columnName="Пациент" property="3" />
      <msh:tableColumn columnName="Дата создания" property="4" />
      <msh:tableColumn columnName="Дата приема" property="5" />
      <msh:tableColumn columnName="Специалист" property="6" />
      <msh:tableColumn columnName="Диагноз" property="7" />
      <msh:tableColumn columnName="Цель посещения" property="8" />
    </msh:table>
  </tiles:put>
</tiles:insert>

