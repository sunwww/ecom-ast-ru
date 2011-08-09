<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="sec_userPermissionForm" guid="9d27df65-9af0-4398-8a7d-57059cd29743">
      <msh:sideMenu title="Разрешения пользователя" guid="58caa1eb-0a6c-4e93-9575-fde48488ebe5">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-sec_userPermission" name="Изменить" roles="/Policy/Jaas/Permission/User/Edit" guid="c714bd8f-4fef-44dc-9d0b-cbaf455258dc" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-sec_userPermission" name="Удалить" roles="/Policy/Jaas/Permission/User/Delete" guid="48146ed8-f1f6-431c-8d29-2ba385d21cb7" />
        <msh:sideLink key="ALT+3" action="/entityList-sec_userPermission" name="⇧К списку" roles="/Policy/Jaas/Permission/User/View" guid="48146ed8--431c-8d29-2ba385d21cb7" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Настройка приложения
    	  -->
    <msh:form action="/entitySaveGoView-sec_userPermission.do" defaultField="key">
      <msh:hidden property="id"/>
      <msh:hidden property="createDate"/>
      <msh:hidden property="createTime"/>
      <msh:hidden property="createUsername"/>
      <msh:hidden property="saveType"/>
      <msh:panel>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата когда можно делать изменения с" guid="623d03fd-f2ea-4da5-85b4-593a65996c1c" size="25" />
          <msh:textField property="dateTo" label="по" guid="76dd3e83-867c-4361-a103-db3d6f671d69" size="40" />
        </msh:row>
        <msh:row guid="e5d8409b-f9a2-4670-8a94-48c89de97938">
          <msh:autoComplete property="object" vocName="vocObjectPermission" fieldColSpan="3" horizontalFill="true" label="Объект" guid="586864ac-7794-40b3-a41d-c71ee45d62ec" />
        </msh:row>
        <msh:row>
          <msh:autoComplete parentAutocomplete="object" property="permission" vocName="vocPermission" fieldColSpan="3" horizontalFill="true" label="Разрешение" guid="f4445f44-a3b7-4b21-9293-c439c269474e" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="userView.do" vocName="secUser" property="username" label="Вход в систему" guid="8754e635-11ce-4c73-b398-4479988fd60d" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="idObject" fieldColSpan="3" label="ИД объекта" guid="ddcbdb90-4d5d-41fa-b450-4d621e4b0e0c" />
        </msh:row>
        <msh:row>
        	<msh:textField property="editPeriodFrom" label="Период, которые можно редак. с"/>
        	<msh:textField property="editPeriodTo" label="по"/>
        </msh:row>
        <msh:ifFormTypeIsView formName="sec_userPermissionForm" guid="3cadad1c-f7da-4cbf-9a44-f85da5c9db5c">
          <msh:row guid="49a0973f-032e-4d54-9903-bef5405a534d">
            <msh:textField property="createDate" label="Дата создания" guid="e3314acf-324b-45bb-a8d7-e4460b7fbe1e" />
            <msh:textField property="createTime" label="Время создания" guid="456f5007-ce50-45cf-ac95-a47d00df97bb" />
          </msh:row>
          <msh:row guid="60ec0aae-4193-4d16-9e57-4d12fc8cd83c">
            <msh:textField property="createUsername" fieldColSpan="3" label="Пользователь, давший разрешение" guid="634edc57-cca4-41ca-b4c8-bac7ea6aedb2" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:submitCancelButtonsRow colSpan="4" guid="8515167d-96cf-48ad-8294-1f3f60a105ec" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="sec_userPermissionForm" guid="6602cbc1-6a25-4fe2-a951-b297a9d9b990" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
</tiles:insert>

