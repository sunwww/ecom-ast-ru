<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-work_groupWorkFunction.do" defaultField="code">
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
          <msh:checkBox property="hasServiceStuff" label="Имеет вспомогательный персонал" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="isDefaultLabCabinet" />
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
        	<msh:checkBox property="emergency" label="Экстр. пункт"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="archival" label="Недействительность"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isNoDirectSelf" label="Запрет на создание направление к самому себе" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="isNoViewRemoteUser" label="Запрет на запись удаленными пользователями" fieldColSpan="3" horizontalFill="true"/>
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
        <msh:table name="functions" viewUrl="entityShortView-work_personalWorkFunction.do" action="entityParentView-work_personalWorkFunction.do" idField="id">
          <msh:tableColumn property="workerInfo" columnName="Сотрудник" />
        </msh:table>
      </msh:section>
      <msh:section>
      <ecom:webQuery name="claimTypeList" nativeSql="
      select wfct.id, vct.name from workfunctionclaimtype wfct
	left join vocclaimtype vct on vct.id=wfct.claimtype
	where workfunction=${param.id}"/>
	<msh:table name="claimTypeList" action="entityParentView-mis_workFunctionClaimType.do" idField="1">
          <msh:tableColumn property="2" columnName="Тип заявки" />
        </msh:table>
        </msh:section>
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
        <msh:sideLink params="id" action="/entityParentPrepareCreate-mis_workFunctionClaimType" name="Привязать к типу заявки" roles="/Policy/Mis/Worker/WorkFunction" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_workCalendar" name="Календарь" title="Добавить календарь" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

