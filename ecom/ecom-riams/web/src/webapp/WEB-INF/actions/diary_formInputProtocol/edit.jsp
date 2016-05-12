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
      <msh:ifFormTypeAreViewOrEdit formName="diary_formInputProtocolForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_formInputProtocol" name="Удалить" roles="/Policy/Diary/Template/FormInputProtocol/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно" guid="9e0388c8-2666-4d66-b865-419c53ef9f89">
            <tags:voc_menu currentAction="mis_medService" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Форма ввода
    	  -->
    <msh:form action="/entityParentSaveGoParentView-diary_formInputProtocol.do" defaultField="name" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="protocol" guid="bd32944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:row>
        	<msh:textField property="name" size="100" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_formInputProtocolForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>