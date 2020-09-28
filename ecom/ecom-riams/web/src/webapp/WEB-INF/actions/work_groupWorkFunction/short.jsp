<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-work_groupWorkFunction.do" defaultField="code"
    	title="<a href='entityView-work_groupWorkFunction.do?id=${param.id}'>Групповая рабочая функция</a>">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu" />
      <msh:panel>
        <msh:row>
          <msh:textField property="code" fieldColSpan="3" label="Код" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField property="groupName" fieldColSpan="3" label="Название группы" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityView-voc_workFunction.do" vocName="vocWorkFunction" property="workFunction" label="Функция" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="work_groupWorkFunctionForm">
      <msh:section title="Календарь">
        <ecom:parentEntityListAll formName="cal_workCalendarForm" attribute="childs" />
        <msh:table viewUrl="entityShortView-cal_workCalendar.do" name="childs" action="entityParentView-cal_workCalendar.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" />
        </msh:table>
      </msh:section>
      <msh:section title="Список сотрудников">
        <ecom:parentEntityListAll attribute="functions" formName="work_personalWorkFunctionByGroupForm" />
        <msh:table name="functions" action="entityParentView-work_personalWorkFunction.do" idField="id">
          <msh:tableColumn property="workerInfo" columnName="Сотрудник" />
        </msh:table>
      </msh:section>
      
            <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/View">
        <msh:section title="Журналы шаблонов">
          <ecom:webQuery name="journals" nativeSql="select 
          jpc.id,wcp.name,jpc.noActive,jpc.dateFrom,jpc.dateTo 
          from JournalPatternCalendar jpc
          left join WorkCalendarPattern wcp on wcp.id=jpc.pattern_id
          where jpc.workCalendar_id='${param.id}'
          " />
          <msh:table deleteUrl="entityParentDeleteGoParentView-work_journalPatternCalendar.do" 
          editUrl="entityParentEdit-work_journalPatternCalendar.do" 
          viewUrl="entityShortView-work_journalPatternCalendar.do" 
			name="journals" 
			action="entityParentView-work_journalPatternCalendar.do" idField="1">
            <msh:tableColumn columnName="ИД" property="1" />
            <msh:tableColumn columnName="Информация о шаблоне" property="2" />
            <msh:tableColumn columnName="Отключен" property="3" />
            <msh:tableColumn columnName="Действует с" property="4" />
            <msh:tableColumn columnName="Действует по" property="5" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="work_groupWorkFunctionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_groupWorkFunctionForm">
      <msh:sideMenu title="Рабочая функция">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-work_groupWorkFunction" name="Изменить" roles="/Policy/Mis/Worker/WorkFunction/Create" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-work_groupWorkFunction" name="Удалить" roles="/Policy/Mis/Worker/WorkFunction/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_workCalendar" name="Календарь" title="Добавить календарь" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

