<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_confInspectionForm" guid="0f85f363-64d8-4aee-bd43-0310f6e8f253">
      <msh:sideMenu title="Осмотр родильницы" guid="7bda1da9-5f4f-4d4f-904b-3321d3130059">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_confInspection" name="Изменить" roles="/Policy/Mis/Inspection/Confined/Edit" guid="eaefe78a-d186-4a6e-ac10-5c060dee745f" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_confInspection" name="Удалить" roles="/Policy/Mis/Inspection/Confined/Delete" guid="f974dc53-6290-4ce3-906e-3606eead4ace" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Осмотр родильницы
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_confInspection.do" defaultField="inspectionDate" guid="8ff64b92-b3a0-49a8-972f-d7417b8ab3a4">
      <msh:hidden property="id" guid="70a0afb3-9b12-4b06-8501-55ae76c99f61" />
      <msh:hidden property="medCase" guid="38ecd069-35c8-408b-9c99-fb41e57719b0" />
      <msh:hidden property="saveType" guid="67527e0a-b0b3-4b4d-b16a-dbb01d574b4f" />
      <msh:panel guid="2d454298-e86b-460b-a9dd-4b8a2e3f4a47" colsWidth="2%,2%,2%,40%">
        <msh:row guid="0a771a4d-cc9d-4a98-9c7c-15eff109b3a0">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="ee3c7702-5b7e-4c9c-ae4f-d49bd72c4ab4">
          <msh:textField property="inspectionDate" label="Дата осмотра" guid="3a766ec8-cbf6-4b01-9757-2fc1c112d2a4" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="inspectionTime" viewOnlyField="false" label="Время" guid="8f6418f2-6787-4601-9e89-714c291db0d0" horizontalFill="false" />
        </msh:row>
        <msh:row guid="f024f673-da99-4e2a-85d7-c92c8750a28f">
          <msh:autoComplete vocName="vocInspectionCondition" property="condition" label="Общее состояние" guid="92af441f-bcb2-437e-b679-b0b87dff4008" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="3d268b29-f785-48b7-9d7e-c96d3199e088">
          <msh:textField property="mammariesCondition" label="Состояние молочных желез" guid="7ec7f40b-4d9b-4c9d-9a6f-a0758ab05f0f" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="190388d6-ef73-4472-a8f9-16e5b6e52af8">
          <msh:textField property="uterusHeight" label="Высота матки" guid="05d21b7a-7161-451e-99b5-fe46fb0e2792" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="19fd6-ef73-4472-a8f9-16s2af8">
          <msh:textField property="lochia" label="Лохии" guid="12bb2729-792d-4b11-8363-f394463eace0" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="d647736b-4513-473c-a1b9-a2e8dc1d434a">
          <msh:textField property="urinaryBladderFunctions" label="Функции мочевого пузыря" guid="ceef8e67-761d-4013-a5e7-bab0261ab65f" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="31c33ce3-58a4-4309-ad7e-ebfb85309077">
          <msh:textField property="intestinesFunctions" label="Функции кишечника" guid="6aa44efc-9e9a-4aba-9c23-8631ac683de1" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="9eae613a-9bae-482c-b36b-0ee63d6534ea">
          <msh:autoComplete property="workFunction" label="Специалист" fieldColSpan="3" guid="4d9c78e4-5b38-402b-8e3d-764b364faa3e" horizontalFill="true" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="bb9a2ae9-1ac8-4168-a992-c1d0e8f840a8" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_confInspectionForm" guid="4001c664-36f8-4f7b-a3bf-f1f58aa99fd2" />
  </tiles:put>
</tiles:insert>

