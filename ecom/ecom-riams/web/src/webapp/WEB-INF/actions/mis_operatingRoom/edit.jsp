<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-mis_operatingRoom.do" defaultField="code">
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
    <msh:ifFormTypeIsView formName="mis_operatingRoomForm">
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
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_operatingRoomForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_operatingRoomForm">
      <msh:sideMenu title="Рабочая функция">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_operatingRoom" name="Изменить" roles="/Policy/Mis/MisLpu/OperatingRoom/Create" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_operatingRoom" name="Удалить" roles="/Policy/Mis/MisLpu/OperatingRoom/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_workCalendar" name="Календарь" title="Добавить календарь" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

