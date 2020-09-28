<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

 <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_temperatureCurveForm" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Температурная кривая
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_temperatureCurve.do" defaultField="takingDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:panel>
        <msh:row>
          <msh:textField property="takingDate" label="Дата" />
        </msh:row>
        <msh:row>
          <msh:textField property="illnessDayNumber" label="День болезни " />
          <msh:textField property="hospDayNumber" label="День пребывания в стационаре " />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDayTime" property="dayTime" label="Время суток" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="bloodPressureUp" label="Артериальное давление, систолическое " />
          <msh:textField property="bloodPressureDown" label="диастолическое " />
          <msh:textField property="pulse" label="Пульс " />
        </msh:row>
        <msh:row>
          <msh:textField property="degree" label="Температура, в градусах" />
          <msh:textField property="respirationRate" label="ЧДД" />
          <msh:textField property="weight" label="Вес " />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocStoolType" property="stool" horizontalFill="true" label="Стул" fieldColSpan="5" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
 
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_temperatureCurveForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_temperatureCurve" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_temperatureCurve" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

