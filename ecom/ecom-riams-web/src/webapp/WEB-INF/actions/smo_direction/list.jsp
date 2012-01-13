<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
  	<ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm" title="Список направлений, сделанных за текущее число"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create" key="ALT+N" action="/entityPrepareCreate-smo_direction" name="Направление" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:section title="Список направлений">
  	<ecom:webQuery name="list" nativeSql="
  	select d.id,to_char(wcd.calendarDate,'dd.mm.yyyy'),cast(wct.timeFrom as varchar(5))
  		,vwf.name as vwfname,ml.name as mlname
  		,wp.lastname||' '||wp.firstname||' '||wp.middlename
  		,d.username
  	from MedCase d
  	left join WorkCalendarDay wcd on wcd.id=d.datePlan_id
  	left join WorkCalendarTime wct on wct.id=d.timePlan_id
  	left join WorkFunction wf on wf.id=d.workFunctionPlan_id
  	left join Worker w on w.id=wf.worker_id
  	left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
  	left join Patient wp on wp.id=w.person_id
  	left join MisLpu ml on ml.id=d.orderLpu_id
  	 
  	where d.dtype='Visit' and d.createDate=CURRENT_DATE
  	"/>
    <msh:table name="list" action="entityView-smo_direction.do" idField="1"
    viewUrl="entityShortView-smo_direction.do" 
    deleteUrl="entityParentDeleteGoParentView-smo_direction.do" 
    editUrl="entityParentEdit-smo_direction.do"
    >
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn columnName="Время" property="3" />
      <msh:tableColumn columnName="Специалист" property="4" />
      <msh:tableColumn columnName="ФИО специалиста" property="6" />
      <msh:tableColumn columnName="Направитель (внеш.ЛПУ)" property="5" />
      <msh:tableColumn columnName="Создал" property="7" />
    </msh:table>
  </msh:section>
  </tiles:put>
</tiles:insert>

