<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="entityParentSaveGoView-diet_vocFoodStuffTemplate.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row />
        <msh:autoComplete vocName="vocFoodStuff" property="foodStuff" label="Продукт питания" fieldColSpan="5" horizontalFill="true" size="50" />
        <msh:row />
        <msh:textField property="gross" label="Брутто" />
        <msh:row />
        <msh:autoComplete vocName="vocMonth" property="monthStart" label="Месяц начала действия" horizontalFill="true" />
        <msh:autoComplete vocName="vocMonth" property="monthEnd" label="Месяц окончания действия (включительно)" horizontalFill="true" />
        <msh:row />
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Diet" beginForm="diet_vocFoodStuffTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" />
      <msh:sideLink action="/entityList-diet_dish.do" name="Список блюд" />
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diet_vocFoodStuffTemplate" name="Изменить" roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_vocFoodStuff" name="Удалить" roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

