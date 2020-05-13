 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="sec_userPermissionForm">
      <msh:sideMenu title="Разрешения пользователя">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-sec_userPermission" name="Изменить" roles="/Policy/Jaas/Permission/User/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-sec_userPermission" name="Удалить" roles="/Policy/Jaas/Permission/User/Delete" />
        <msh:sideLink key="ALT+3" action="/js-sec_userPermission-listNext.do" name="⇧К списку" roles="/Policy/Jaas/Permission/User/View" />
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
          <msh:textField property="dateFrom" label="Дата когда можно делать изменения с" size="25" />
          <msh:textField property="dateTo" label="по" size="40" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="object" vocName="vocObjectPermission" fieldColSpan="3" horizontalFill="true" label="Объект" />
        </msh:row>
        <msh:row>
          <msh:autoComplete parentAutocomplete="object" property="permission" vocName="vocPermission" fieldColSpan="3" horizontalFill="true" label="Разрешение" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="userView.do" vocName="secUser" property="username" label="Вход в систему" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="idObject" fieldColSpan="3" label="ИД объекта" />
        </msh:row>
        <msh:row>
        	<msh:textField property="editPeriodFrom" label="Период, который можно редак., с"/>
        	<msh:textField property="editPeriodTo" label="по"/>
        </msh:row>
        <msh:ifFormTypeIsView formName="sec_userPermissionForm">
          <msh:row>
            <msh:textField property="createDate" label="Дата создания" />
            <msh:textField property="createTime" label="Время создания" />
          </msh:row>
          <msh:row>
            <msh:textField property="createUsername" fieldColSpan="3" label="Пользователь, давший разрешение" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="sec_userPermissionForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
  <script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
  <msh:ifFormTypeIsCreate formName="sec_userPermissionForm">
   <script>
  window.onload=function (){
      $('idObject').value='${param.ido}';
	  $('object').value='${param.type}';
	  if ($('object').value=="1") {
	    $('objectName').value="Протокол";
      } else if ($('object').value=="2") {
             $('objectName').value="Выписка";
	  }
      $('dateFrom').value=$('dateTo').value=getCurrentDate();
	  if (+'${param.type}'>0) {
        TemplateProtocolService.getDefaultValueForPermission($('object').value, {
          callback: function (aResult) {
            if (aResult != '{}') {
              var perm = JSON.parse(aResult);
              $('permission').value = perm.id;
              $('permissionName').value = perm.name;
            }
          }
        });
        TemplateProtocolService.getPeriodForPermission($('object').value, $('idObject').value, {
          callback: function (aResult) {
            if (aResult != '{}') {
              var res = JSON.parse(aResult);
              $('editPeriodFrom').value = res.dateStart;
              $('editPeriodTo').value = res.dateFinish;
              $('username').value = res.suId;
              $('usernameName').value = res.suLogin;
            }
          }
        });
      }
      if ('${type}'=="1") $('usernameName').disabled=true;
  }
  </script>
  </msh:ifFormTypeIsCreate>
</tiles:insert>