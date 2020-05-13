<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_confInspectionForm">
      <msh:sideMenu title="Осмотр родильницы">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_confInspection" name="Изменить" roles="/Policy/Mis/Inspection/Confined/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_confInspection" name="Удалить" roles="/Policy/Mis/Inspection/Confined/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Осмотр родильницы
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_confInspection.do" defaultField="inspectionDate">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="2%,2%,2%,40%">
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:textField property="inspectionDate" label="Дата осмотра" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="inspectionTime" viewOnlyField="false" label="Время" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocInspectionCondition" property="condition" label="Общее состояние" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="mammariesCondition" label="Состояние молочных желез" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="uterusHeight" label="Высота матки" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="lochia" label="Лохии" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="urinaryBladderFunctions" label="Функции мочевого пузыря" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="intestinesFunctions" label="Функции кишечника" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="workFunction" label="Специалист" fieldColSpan="3" horizontalFill="true" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_confInspectionForm" />
  </tiles:put>
</tiles:insert>

