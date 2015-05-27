<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_userDomain" />
    <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Пользовательский справочник">
      <msh:ifFormTypeIsView formName="diary_userDomainForm" guid="e2054544-85-a21c-3bb9b4569efc">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-diary_userDomain" name="Изменить" roles="/Policy/Diary/User/Domain/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_userDomainForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_userDomain" name="Удалить" roles="/Policy/Diary/User/Domain/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="diary_userDomainForm" guid="8db06246-c49c-496a-bb1f-2de391e40631">
      <msh:sideMenu title="Добавить" guid="adding">
        <msh:sideLink action="/entityParentPrepareCreate-diary_userValue" name="Значение справочника" params="id" roles="/Policy/Diary/User/Domain/Value/Create" guid="a05cafca-1220-455a-b608-b00ed338fdc3" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="9e0388c8-2666-4d66-b865-419c53ef9f89">
                    <tags:voc_menu currentAction="diary_user_voc" />

      </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Медицинских услуг
    	  -->
    <msh:form action="/entityParentSave-diary_userDomain.do" defaultField="name" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="20% 30%">
        <msh:row guid="bb6f7393-5e65-498c-8279-b849d7e9f6b4">
          <msh:textField property="name" label="Название" size="50" guid="b87e9cee-cf5d-43bc-b50d-1911d5e87e40" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
    <msh:ifInRole roles="/Policy/Mis/MedService/View" guid="5e3d7e52-5747-4b60-aab3-f99027a64117">
      <msh:ifFormTypeIsView formName="diary_userDomainForm" guid="9f1dd6a4-a7b7-43e7-b205-85730bba6968">
        <msh:section title="Значения справочника" createUrl="entityParentPrepareCreate-diary_userValue.do?id=${param.id}">
          <ecom:parentEntityListAll attribute="values" formName="diary_userValueForm" guid="values" />
          <msh:table name="values" action="entityParentView-diary_userValue.do" idField="id" guid="16cdff9b-c2ac-4629-8997-eebc80ecc49c">
            <msh:tableColumn property="name" columnName="Значение" guid="2fd022ea-59b0-4cc9-a8ce-0ed4a3ddc91f" />
            <msh:tableColumn property="cntBall" columnName="Балл" guid="2fd022ea-59b0-4cc9-a8ce-0ed4a3ddc91f" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_userDomainForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>

