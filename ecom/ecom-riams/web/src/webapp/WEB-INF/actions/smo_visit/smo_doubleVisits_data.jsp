<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Poly" property="worker">Список повторных посещений <a href="smo_doubleVisits_list.do">Изменить дату</a></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:visit_finds currentAction="journalDouble"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql=" select t.id as tid,t.dateStart, p.lastname||' '|| p.firstname||' '||p.middlename ||' г.р.'||to_char(p.birthday,'DD.MM.YYYY'),plDay.calendarDate as planDate,t.dateStart as tdate,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo ,vr.name as vrname  
    	from Medcase t    
    	 left join patient p on p.id=t.patient_id  
    	 left join workcalendarday plDay on plDay.id=t.datePlan_id  
    	 left join workfunction wf on wf.id=t.workFunctionExecute_id    
    	 left join vocworkfunction vwf on vwf.id=wf.workFunction_id    
    	 left join worker  w on w.id=wf.worker_id   
    	  left join patient wp on wp.id=w.person_id   
    	   left join vocIdc10 mkb on mkb.id=t.idc10_id   
    	    left join vocreason vr on vr.id=t.visitreason_id    
    	    where t.patient_id='${medcard}' and t.dateStart  =to_date('${date}','dd.mm.yyyy')  
    	    and t.DTYPE='Visit' and t.workfunctionExecute_id='${workfunction}'  
    	    order by p.lastname" />
    <msh:table name="list" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№визита" property="1" />
      <msh:tableColumn columnName="Пациент" property="3" />
      <msh:tableColumn columnName="Дата направления" property="4" />
      <msh:tableColumn columnName="Дата приема" property="5" />
      <msh:tableColumn columnName="Специалист" property="6" />
      <msh:tableColumn columnName="Цель посещения" property="7" />
    </msh:table>
  </tiles:put>
</tiles:insert>