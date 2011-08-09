<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Policies" beginForm="secpolicyForm" guid="074f7104-f6f8-4849-b2db-31fda5dc8ddf" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu134">
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-secpolicy" name="Создать новую политику" guid="b7a3f010-fdc0-4746-87fb-23af352dc433" />
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-secpolicy" name="Изменить" guid="d8409e8c-ea15-4d0f-bdec-6b986ff4c7ad" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-secpolicy" name="Удалить" guid="df5fd3d8-2915-414d-a394-6e1789e632c8" confirm="Удалить политику?" />
	      	<tags:sec_policyFull roles="/Policy/Jaas/SecPolicy/Edit"
	      	name='addPol' title='Добавить/Перейти к политике' key="ALT+9"/>
    </msh:sideMenu>
        <msh:sideMenu title="Администрирование">
        	<msh:sideLink action="/servicePolicy-exportEdit.do" roles="/Policy/Jaas/SecPolicy/Export" name="Экспорт списка политик"/>
			<msh:sideLink action="/servicePolicy-importEdit.do" roles="/Policy/Jaas/SecPolicy/Import" name="Импорт списка политик"/>
        </msh:sideMenu>
        <tags:menuJaas currentAction="policies"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-secpolicy.do" idField="id" guid="a8aa4143-f5be-49a7-86d7-47a8dcccc08b">
      <msh:tableColumn columnName="Ключ" property="key" guid="cfd8f03d-cc59-4975-b8b4-9432cf1c233b" />
      <msh:tableColumn columnName="Название" property="name" guid="91f81ba3-08dc-4be4-b141-cbed24b78fbc" />
      <msh:tableColumn columnName="Комментарий" property="comment" guid="053f94e5-3dc4-4d9c-ac8c-e139eaaf8ecf" />
    </msh:table>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">Element.addClassName($('mainMenuPolicies'), "selected") ;</script>
  </tiles:put>
</tiles:insert>

