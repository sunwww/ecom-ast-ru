<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="sec_softConfigForm">
      <msh:sideMenu title="Настройка приложения">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-sec_softConfig" name="Изменить" roles="/Policy/Config/Soft/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-sec_softConfig" name="Удалить" roles="/Policy/Config/Soft/Delete" />
        <msh:sideLink key="ALT+3"  action="/entityList-sec_softConfig" name="⇧К списку настроек" roles="/Policy/Config/Soft/View" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Настройка приложения
    	  -->
    <msh:form action="/entitySaveGoView-sec_softConfig.do" defaultField="key">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:separator label="Основные сведения" colSpan="" />
        <msh:row>
          <msh:textField property="key" label="Ключ" size="25" />
          <msh:textField property="keyValue" label="Значение" size="40" />
        </msh:row>
        <msh:row>
          <msh:textArea property="description" label="Описание" rows="4" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="sec_softConfigForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
</tiles:insert>

