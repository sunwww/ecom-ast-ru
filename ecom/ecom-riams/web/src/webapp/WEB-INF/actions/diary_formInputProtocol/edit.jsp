<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="mis_medService" />
    <msh:sideMenu title="Параметр">
      <msh:ifFormTypeIsView formName="diary_formInputProtocolForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-diary_formInputProtocol" name="Изменить" roles="/Policy/Diary/Template/FormInputProtocol/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_formInputProtocolForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_formInputProtocol" name="Удалить" roles="/Policy/Diary/Template/FormInputProtocol/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
            <tags:voc_menu currentAction="mis_medService" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Форма ввода
    	  -->
    <msh:form action="/entityParentSaveGoParentView-diary_formInputProtocol.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="protocol" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="name" size="100" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_formInputProtocolForm" />
  </tiles:put>
</tiles:insert>