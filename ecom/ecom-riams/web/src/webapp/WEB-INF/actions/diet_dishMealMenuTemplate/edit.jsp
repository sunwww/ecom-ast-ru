<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-diet_dishMealMenuTemplate.do" defaultField="menu">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="menu" />
      <msh:panel>
        <msh:autoComplete vocName="Dish" property="dish" label="Блюдо" size="50" />
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <!--    title="Шаблон блюда меню">-->
      <msh:sideLink params="id" action="/entityParentListRedirect-diet_mealMenuTemplate" name="Список меню-шаблонов" />
      <msh:ifFormTypeIsView formName="diet_dishMealMenuTemplateForm">
        <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Edit" action="/entityEdit-diet_dishMealMenuTemplate" name="Изменить" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="diet_dishMealMenuTemplateForm">
        <!--        <msh:sideLink roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Edit" params="id" action="/entityEdit-diet_dishMealMenuTemplate" name="Изменить" />-->
        <msh:sideLink roles="/Policy/Mis/InvalidFood/DishMealMenuTemplate/Delete" params="id" action="/entityParentDelete-diet_dishMealMenuTemplate" name="Удалить" confirm="Удалить шаблон блюда?" key="ALT+DEL" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_dishMealMenuTemplateForm" />
  </tiles:put>
</tiles:insert>

