<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="entityParentSaveGoView-diet_vocFoodStuffTemplate.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        <msh:row guid="row1" />
        <msh:autoComplete vocName="vocFoodStuff" property="foodStuff" label="Продукт питания" guid="84de7-0a81-4762-9864-6d9b" fieldColSpan="5" horizontalFill="true" size="50" />
        <msh:row guid="b5df45-b971-441e-9a90-51da8" />
        <msh:textField property="gross" label="Брутто" guid="se73a-c6c2-4221-bdb7" />
        <msh:row guid="b85f4b-b971-441e-9a90-58194" />
        <msh:autoComplete vocName="vocMonth" property="monthStart" label="Месяц начала действия" guid="84gfde7-0a81-4762-9864-6d9b" horizontalFill="true" />
        <msh:autoComplete vocName="vocMonth" property="monthEnd" label="Месяц окончания действия (включительно)" guid="84hswde7-0a81-4762-9864-6d9b" horizontalFill="true" />
        <msh:row guid="bd5dh45-b971-441e-9a90-51dga8" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_vocFoodStuffTemplateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink action="/entityList-diet_diet.do?id=-1" name="Список диет" guid="b5g09-ed7d-4bb5-8d55-e0183bcf4b" />
      <msh:sideLink action="/entityList-diet_dish.do" name="Список блюд" guid="djfbf59-ed7d-4bb5-8d55-e018f4b" />
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_vocFoodStuffTemplate" name="Изменить" roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_vocFoodStuff" name="Удалить" roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

