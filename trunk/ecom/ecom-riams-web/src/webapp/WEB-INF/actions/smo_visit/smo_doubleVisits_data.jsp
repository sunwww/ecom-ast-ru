<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Poly" property="worker" guid="4b11dc98-30fc-413e-8bc6-976f292e704f">Список повторных посещений <a href="smo_doubleVisits_list.do">Изменить дату</a></msh:title>
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
    	    where t.patient_id='${medcard}' and t.dateStart  =cast('${date}' as date)  
    	    and t.DTYPE='Visit' and t.workfunctionExecute_id='${workfunction}'  
    	    order by p.lastname" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="list" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено" guid="6600cebc-4548-4f57-a048-5a3a2e67a673">
      <msh:tableColumn columnName="#" property="sn" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
      <msh:tableColumn columnName="№визита" property="1" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
      <msh:tableColumn columnName="Пациент" property="3" guid="d7955208-4c68-42ce-85d6-684a4b9076a9" />
      <msh:tableColumn columnName="Дата направления" property="4" guid="ee9ce01d-4924-4e76-bc93-3ecb73d8b18f" />
      <msh:tableColumn columnName="Дата приема" property="5" guid="ee9ce01d-4924-4e76-bc93-3ecb73d8b18f" />
      <msh:tableColumn columnName="Специалист" property="6" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
      <msh:tableColumn columnName="Цель посещения" property="7" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
    </msh:table>
  </tiles:put>
</tiles:insert>