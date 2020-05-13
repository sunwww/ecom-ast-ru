<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал услуг"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="smo_medCase" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/journal_serviceMedCase.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = request.getParameter("dateBegin") ;
    
    if (date!=null && !date.equals("")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <table>
    <tr>
    <td valign="top">
	    <msh:section title="УСЛУГИ: Общие">
		    <ecom:webQuery name="journal_medService" nativeSql="select dateStart, count(id) from MedCase where DTYPE='ServiceMedCase' and dateStart  between '${param.dateBegin}'  and '${param.dateEnd}'  group by dateStart " />
		    <msh:table name="journal_medService" action="js-smo_medService-listByDate.do?dateSearch=${dateSearch}" idField="1">
		      <msh:tableColumn columnName="Дата" property="1" />
		      <msh:tableColumn columnName="Количество услуг" identificator="false" property="2" />
		    </msh:table>
	    </msh:section>
	   </td><td>&nbsp;&nbsp;</td><td valign="top">
	    <msh:section title="УСЛУГИ: Хирургические операции">
		    <ecom:webQuery name="journal_surOperation" nativeSql="select operationDate, count(id) from SurgicalOperation where operationDate  between '${param.dateBegin}'  and '${param.dateEnd}'  group by operationDate " />
		    <msh:table name="journal_surOperation" action="journal_surOperationByDate.do?dateSearch=${dateSearch}" idField="1">
		      <msh:tableColumn columnName="Дата" property="1" />
		      <msh:tableColumn columnName="Количество операций" identificator="false" property="2" />
		    </msh:table>
	    </msh:section>
	    </td></tr></table>
	    <msh:section title="Реестр услуг">
	    <ecom:webQuery name="jour_service" nativeSql="
	    select 
	    smc.id
	    ,smc.dateStart as smcdateStart
	    ,smc.timeExecute as smctimeExecute
	    ,smc.medServiceAmount as smcmedService
	    ,ms.name as msname
	    , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename 
	    ,vss.name as vssname
	    ,p.lastname||' '||p.firstname||' '||p.middlename ||' гр '||to_char(p.birthday,'DD.MM.YYYY')
	    ,vas.name as vasname
	    from MedCase smc 
	    left join VocServiceStream vss on vss.id=smc.serviceStream_id
	    left join MedService ms on ms.id=smc.medService_id 
	    left join Patient p on p.id=smc.patient_id 
	    left join WorkFunction wf on wf.id=smc.workFunctionExecute_id 
	    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
	    left join Worker w on w.id=wf.worker_id 
	    left join Patient wp on wp.id=w.person_id 
	    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
	    where smc.DTYPE='ServiceMedCase' and smc.dateStart  between '${param.dateBegin}'  and '${param.dateEnd}' 
	    "/>
	    <msh:table name="jour_service" action="entityView-smo_medService.do" idField="1" >
	    	<msh:tableColumn columnName="#" property="sn"/>
	    	<msh:tableColumn columnName="Дата" property="2"/>
	    	<msh:tableColumn columnName="Время" property="3"/>
	    	<msh:tableColumn columnName="Статус пациента" property="9"/>
	    	<msh:tableColumn columnName="Пациент" property="8"/>
	    	<msh:tableColumn columnName="Услуга" property="5"/>
	    	<msh:tableColumn columnName="Кол-во" property="4"/>
	    	<msh:tableColumn columnName="Специалист" property="6"/>
	    	<msh:tableColumn columnName="Поток обслуживания" property="7"/>
	    </msh:table>
	    </msh:section>
	    <msh:section title="Реестр хирургических операций">
	    <ecom:webQuery name="journal_surOperation1" nativeSql="select so.id
	    ,to_char(so.operationDate,'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')||' - '||to_char(so.operationDateTo,'DD.MM.YYYY')||' '||to_char(so.operationTimeTo,'HH24:MI'), vo.name as voname,
	    (select list(' '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) from SurgicalOperation_WorkFunction sowf left join WorkFunction wf on wf.id=sowf.surgeonFunctions_id left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id left join vocworkFunction vwf on vwf.id=wf.workFunction_id where sowf.SurgicalOperation_id=so.id )
	    ,p.lastname||' '||p.firstname||' '||p.middlename ||' гр '||to_char(p.birthday,'DD.MM.YYYY'),
	    (select list(' '||vam.name|| ' '|| a.duration||' мин '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) 
	    from Anesthesia a
	    left join VocAnesthesiaMethod vam on vam.id=a.method_id
	    left join WorkFunction wf on wf.id=a.anesthesist_id
	    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
	    left join Worker w on w.id=wf.worker_id
	    left join Patient wp on wp.id=w.person_id
	    
	    where a.surgicalOperation_id=so.id
	    )
	     ,vas.name as vasname
	     from SurgicalOperation so
	      left join Patient p on p.id=so.patient_id
	      left join VocAdditionStatus vas on vas.id=p.additionStatus_id
	      left join MedService vo on vo.id=so.medService_id where operationDate  between '${param.dateBegin}'  and '${param.dateEnd}'  " />
	    <msh:table name="journal_surOperation1" action="entityView-stac_surOperation.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="Статус пациента" property="7"/>
	      <msh:tableColumn columnName="Пациент" property="5"/>
	      <msh:tableColumn columnName="Период операции" property="2" />
	      <msh:tableColumn columnName="Хирург(и)" property="4"/>
	      <msh:tableColumn columnName="Операции" property="3"/>
	      <msh:tableColumn columnName="Анестезия" property="6"/>
	    </msh:table>
	    </msh:section>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    <script type='text/javascript'>
    
			 Calendar.setup({
				 inputField : "dateBegin", // id of the input field
				 ifFormat : "%Y-%m-%d", // format of the input field
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus"
			 });
			 Calendar.setup({
				 inputField : "dateEnd",
				 ifFormat : "%Y-%m-%d",
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus"
 			});
    </script>
    
  </tiles:put>
</tiles:insert>