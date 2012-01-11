<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" guid="14568ace-1821-4897-841f-b9b42d93026d" title="Список рабочих функций по персоне" />
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" nativeSql="
  		select wf.id as wfid,vwf.name as vwfname,su.login as sulogin,ml.name as mlname,wf.code
  		from WorkFunction wf
  		left join Worker w on w.id=wf.worker_id
  		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
  		left join MisLpu ml on ml.id=w.lpu_id
  		left join SecUser su on su.id=wf.secUser_id
  		where w.person_id=${param.id}
  	"/>
    <msh:table name="list" action="entityView-work_personalWorkFunction.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="ИД" property="1" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Код специалиста" property="5" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Функция" property="2" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Подразделение" property="4" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Вход в систему" property="3" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>

