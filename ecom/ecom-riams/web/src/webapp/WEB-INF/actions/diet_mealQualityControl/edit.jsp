<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-diet_mealQualityControl.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="diet" />
      <msh:panel guid="panel" colsWidth="10%,20%,10%,60%">
        <msh:row guid="row1" />
        <msh:textField property="controlDate" label="Дата" guid="54564gj653a-c6c2-4221-bb72-7706" size="20" horizontalFill="true" />
        <msh:autoComplete vocName="vocMealTime" property="mealTime" label="Время приема пищи" guid="84ate52dd1e7-0a81-4762-9264-fbffc6" horizontalFill="true" size="40" />
        <msh:row guid="b5fgh4f45s6eb-b971-441e-9a90-5194a" />
        <msh:separator label="Оценки" colSpan="4" guid="a1682bd0-f2f1-4b08-9bf2-965e95510630" />
        <msh:row guid="bt5fgh4f45s6eb-b971-441e-9a90-519i4a" />
        <msh:textField property="estimateMenu" label="Оценка меню" guid="kjht52iu7-0a81-4762-9864-fbffc6" horizontalFill="true" />
        <msh:textField property="estimateDish" label="Оценка блюда" guid="kjhy6t52iu7-0a81-4762-9864-fbf25fc6" />
        <msh:row guid="bt5fgh645s6eb-b971-441e-9a90-519i10a" />
        <msh:textField property="estimatePreparation" label="Оценка приготовления" guid="kj76ng2iu7-0a81-4762-9864-fbffc6" horizontalFill="true" />
        <msh:textField property="estimateWeight" label="Оценка веса" guid="kj7652yt2iu7-0a81-4762-9864-fbffc6" />
        <msh:row guid="bt5fgh64526eb-b971-441e-9a90-517410a" />
        <msh:textField property="estimateSanitation" label="Оценка санитарии" guid="kj763256u7-0a81-4762-9864-fb2ffc6" horizontalFill="true" />
        <msh:checkBox property="issuePermission" label="Разрешено" fieldColSpan="3" guid="f50f40f3-2cae-4795-93e8-92f39818ceccd1" />
        <msh:row guid="bt5ht2fgh64526eb-b971-441e-9a90-517410a" />
        <msh:textField property="dutyDoctorInfo" label="Дежурный врач" guid="kj763gf256u7-0a81-4762-9864-fb2ffc6" horizontalFill="true" size="50" fieldColSpan="3" />
        <msh:row guid="bt5ht2fgh645v26eb-b971-441e-9a90-5174125mj0a" />
        <msh:textField property="testerInfo" label="Снял пробу" guid="kj7632ft756u7-0a81-4762-9864-fb2ffc6" horizontalFill="true" size="50" fieldColSpan="3" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Diet" beginForm="diet_mealQualityControlForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink action="/entityList-diet_mealQualityControl" name="Список" guid="cfcc38f5-acdc-407d-ab94-f40ac55be477" params="id" />
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-diet_mealQualityControl" name="Изменить" roles="/Policy/Mis/InvalidFood/MealQualityControl/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diet_mealQualityControl" name="Удалить" roles="/Policy/Mis/InvalidFood/MealQualityControl/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="9e7gf30f59-3e55-41c8-add9-fbcd43d0f9be" />
  </tiles:put>
</tiles:insert>

