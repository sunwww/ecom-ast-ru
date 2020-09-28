<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список рабочих функций по персоне" />
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" nativeSql="
  		select wf.id as wfid,vwf.name as vwfname,su.login as sulogin,ml.name as mlname,wf.code
  		,gwf.groupName as groupName
  		,case when wf.archival='1' then 'color: red' end as f7_styleRow
  		from WorkFunction wf
  		left join workfunction gwf on gwf.id=wf.group_id
  		left join Worker w on w.id=wf.worker_id
  		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
  		left join MisLpu ml on ml.id=w.lpu_id
  		left join SecUser su on su.id=wf.secUser_id
  		where w.person_id=${param.id}
  	"/>
    <msh:table name="list" styleRow="7" action="entityView-work_personalWorkFunction.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="ИД" property="1" />
      <msh:tableColumn columnName="Код специалиста" property="5" />
      <msh:tableColumn columnName="Функция" property="2" />
      <msh:tableColumn columnName="Подразделение" property="4" />
      <msh:tableColumn columnName="Групповая раб. функция" property="6" />
      <msh:tableColumn columnName="Вход в систему" property="3" />
    </msh:table>
  </tiles:put>
</tiles:insert>

