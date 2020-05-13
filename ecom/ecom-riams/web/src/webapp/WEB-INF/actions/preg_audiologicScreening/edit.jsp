<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_audiologicScreeningForm">
      <msh:sideMenu title="Аудиологический скрининг">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_audiologicScreening" name="Изменить" roles="/Policy/Mis/Inspection/AudiologicScreening/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_audiologicScreening" name="Удалить" roles="/Policy/Mis/Inspection/AudiologicScreening/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Аудиологический скрининг
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_audiologicScreening.do" defaultField="inspectionDate">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:textField property="inspectionDate" label="Дата осмотра" />
          <msh:textField property="inspectionTime" label="Время осмотра" />
        </msh:row>
        <msh:row>
          <msh:textArea property="notes" label="Описание" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Специалист" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_audiologicScreeningForm" />
  </tiles:put>
</tiles:insert>

