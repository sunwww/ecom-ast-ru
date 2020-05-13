<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Policies" beginForm="secpolicyForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-secpolicy" name="Создать новую политику" />
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-secpolicy" name="Изменить" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-secpolicy" name="Удалить" confirm="Удалить политику?" />
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
    <msh:table name="list" action="entityParentView-secpolicy.do" idField="id">
      <msh:tableColumn columnName="Ключ" property="key" />
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn columnName="Комментарий" property="comment" />
    </msh:table>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">Element.addClassName($('mainMenuPolicies'), "selected") ;</script>
  </tiles:put>
</tiles:insert>

