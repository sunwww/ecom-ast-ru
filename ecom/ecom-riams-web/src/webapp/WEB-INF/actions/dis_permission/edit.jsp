<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-dis_permission.do" defaultField="permission">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="disabilityDocument" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:row guid="db3f3d41-18de-4e0b-ba89-03fbf9bafdab">
            <msh:checkBox property="permission" label="Разрешить" guid="d8753682-c0cd-4af6-8773-48bed28389a0" />
          </msh:row>
          <msh:textField property="dateFrom" label="Дата начала" />
          <msh:textField property="dateTo" label="Дата окончания" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textArea property="comment" label="Комментарий" guid="317c897b-27b9-4dc0-b65d-6e2654995960" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_permissionForm" title="Разрешение МСЭК" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-dis_permission" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Permission/Edit" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-dis_permission" name="Добавить потомка" roles="/Policy/Mis/Disability/Case/Document/Permission/Create" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-dis_permission" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Permission/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>