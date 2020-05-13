<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="trans_otherForm">
      <msh:sideMenu title="Переливание крови">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-trans_other" name="Изменить" roles="/Policy/Mis/MedCase/Transfusion/Other/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-trans_other" name="Удалить" roles="/Policy/Mis/MedCase/Transfusion/Other/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Переливание крови
    	  -->
    <msh:form action="/entityParentSave-trans_other.do" defaultField="preparationDate">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="5%,20%,5%,20%">
        <msh:row>
          <msh:textField property="journalNumber" label="Номер в журнале" horizontalFill="true" />
          <msh:textField property="startDate" label="Дата" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="otherPreparation" label="Трансфузионная жидкость" horizontalFill="true" fieldColSpan="3" vocName="vocOtherTransfusPreparation" />
        </msh:row>
        <msh:row>
          <msh:textField property="preparationDate" label="Дата приготовления" />
          <msh:textField property="series" label="Серия" />
        </msh:row>
        <msh:row>
          <msh:textField property="preparator" label="Изготовитель" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="reason" label="Показания к применению" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="doze" label="Доза (мл)" horizontalFill="true" />
          <msh:checkBox property="primaryCase" label="Первичное" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="transfusionMethod" label="Способ переливания" vocName="vocTransfusionMethod" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="transfusionReaction" label="Трансфузионная реакция" vocName="vocTransfusionReaction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="complications" label="Осложнения после переливания" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="executor" label="Исполнитель" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="trans_otherForm" />
  </tiles:put>
</tiles:insert>

