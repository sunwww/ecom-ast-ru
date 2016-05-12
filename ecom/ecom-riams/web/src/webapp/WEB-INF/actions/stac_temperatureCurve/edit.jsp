<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

 <tiles:put name="title" type="string">
    <ecom:titleTrail guid="c0mec29-09d7-49de-adbb-51b5c251c1" mainMenu="Patient" beginForm="stac_temperatureCurveForm" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Температурная кривая
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-stac_temperatureCurve.do" defaultField="takingDate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="medCase" />
      <msh:panel guid="panel">
        <msh:row guid="b76eb-b971-441e-9a90-51707">
          <msh:textField property="takingDate" label="Дата" guid="50t19c-de41-4b70-b68f-0ft3a" />
        </msh:row>
        <msh:row guid="bffdcf-151a-46b2-ae9b-8s47d2">
          <msh:textField property="illnessDayNumber" label="День болезни " guid="034f8-dcd5-4574-9dc6-d8fef" />
          <msh:textField property="hospDayNumber" label="День пребывания в стационаре " guid="0f34f8-dcd5-4574-9dc6-dd8fef" />
        </msh:row>
        <msh:row guid="bgffdcf-151a-46b2-ae9b-8sg47d2">
          <msh:autoComplete vocName="vocDayTime" property="dayTime" guid="3td1b-8802-467d-b205-71t18" label="Время суток" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="bfcf-151a-46b2-ae9b-f8s47d2">
          <msh:textField property="bloodPressureUp" label="Артериальное давление, систолическое " guid="0f8-dcd5-4574-9dc6-d8fef" />
          <msh:textField property="bloodPressureDown" label="диастолическое " guid="0ff8-dcd5-4574-9dc6-ddef" />
          <msh:textField property="pulse" label="Пульс " guid="0ff8-dcd5-4574-9dc6-dd8fef" />
        </msh:row>
        <msh:row guid="bff4fdcf-151a-46b2-ae9b-f8s47d2">
          <msh:textField property="degree" label="Температура, в градусах" guid="0e34f8-dcd5-4574-9dc6-d8fef" />
          <msh:textField property="respirationRate" label="ЧДД" guid="0f34rf8-dcd5-4574-9dc6-dd8fef" />
          <msh:textField property="weight" label="Вес " guid="0f3r4f8-dcd5-4574-9dc6-drd8fef" />
        </msh:row>
        <msh:row guid="bgffdcf5-151a-46b2-ae9b-8sg47d2">
          <msh:autoComplete vocName="vocStoolType" property="stool" guid="3btd1b-8802-467d-b205-71t18" horizontalFill="true" label="Стул" fieldColSpan="5" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
 
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_temperatureCurveForm" guid="d47e9-6ddb-4cf2-9375-4c83fb8">
      <msh:sideMenu guid="c0c29-09d7-49de-adbb-5151c1">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-stac_temperatureCurve" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_temperatureCurve" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

