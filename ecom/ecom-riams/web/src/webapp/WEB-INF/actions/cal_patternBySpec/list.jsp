<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="lpu" beginForm="mis_lpuForm" title="Список шаблонов"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Create" key="ALT+N" action="/entityParentPrepareCreate-cal_patternBySpec" name="Шаблон календаря" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" nativeSql="select wcp.id,wcp.name as wcpname,vwb.name as vwbname,vwct.name as vwctname from WorkCalendarPattern wcp 
  	left join VocWorkBusy vwb on vwb.id=wcp.workBusy_id
  	left join VocWorkCalendarType vwct on vwct.id=wcp.calendarType_id
  	where wcp.lpu_id='${param.id}' and wcp.dtype='WorkCalenPatternBySpec'
  	"/>
    <msh:table name="list" action="entitySubclassView-cal_pattern.do" idField="1">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Название" property="2" />
			<msh:tableColumn columnName="Тип календаря" property="4" />
			<msh:tableColumn columnName="Тип занятости" property="3" />
    </msh:table>
  </tiles:put>
</tiles:insert>