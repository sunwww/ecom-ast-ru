<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form guid="formHello" action="entityParentSaveGoView-diet_dishMealMenuTemplate.do" defaultField="menu">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="menu" guid="982a287b-40fe-4c1e-9130-82562df9df25" />
      <msh:panel guid="panel">
        <msh:autoComplete vocName="Dish" property="dish" label="Блюдо" guid="84ah3g9dgd5dd1e-0a81-4762-9864-fbdfg" size="50" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="63ff46d1-6536-413a-8e27-92438cccd35c">
      <!--    title="Шаблон блюда меню" guid="442c9ce4-f154-40d7-9a36-2096ae6d06ca">-->
      <msh:sideLink params="id" action="/entityParentListRedirect-diet_mealMenuTemplate" name="Список меню-шаблонов" guid="c247af03-9a20-4bc2-9a07-befed8cb6d38" />
      <msh:ifFormTypeIsView formName="diet_dishMealMenuTemplateForm" guid="dd22af18-3d65-4f8e-bda7-3561165dd3b6">
        <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Edit" action="/entityEdit-diet_dishMealMenuTemplate" name="Изменить" guid="36921d28-bb88-4243-9562-8f7b42583eb8" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="diet_dishMealMenuTemplateForm" guid="cdb0a6fa-1b6a-450d-a731-dc2311237858">
        <!--        <msh:sideLink roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Edit" params="id" action="/entityEdit-diet_dishMealMenuTemplate" name="Изменить" guid="5e442b65-1729-444f-bd8e-da9022a24228" />-->
        <msh:sideLink roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Delete" params="id" action="/entityParentDelete-diet_dishMealMenuTemplate" name="Удалить" confirm="Удалить шаблон блюда?" guid="6936d709-4d3b-4192-89f6-f1b7c0446169" key="ALT+DEL" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_dishMealMenuTemplateForm" guid="22c9bd9e-d3c1-4320-929f-e9e4a9497c32" />
  </tiles:put>
</tiles:insert>

