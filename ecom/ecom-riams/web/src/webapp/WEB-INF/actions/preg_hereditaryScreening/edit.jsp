<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_hereditaryScreeningForm">
      <msh:sideMenu title="Скрининг на наследственные заболевания">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_hereditaryScreening" name="Изменить" roles="/Policy/Mis/Inspection/HereditaryScreening/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_hereditaryScreening" name="Удалить" roles="/Policy/Mis/Inspection/HereditaryScreening/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Скрининг на наследственные заболевания 
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_hereditaryScreening.do" defaultField="inspectionDate">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:textField property="inspectionDate" label="Дата" />
          <msh:textField property="inspectionTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:textArea property="notes" label="Описание" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="workFunction" label="Специалист" horizontalFill="true" fieldColSpan="3" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_hereditaryScreeningForm" />
  </tiles:put>
</tiles:insert>

