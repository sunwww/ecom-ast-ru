<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_inspectionForm" >
      <msh:sideMenu  title="">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-preg_inspection" name="Изменить" roles="/Policy/Mis/Inspection/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_inspection" name="Удалить" roles="/Policy/Mis/Inspection/Delete"  />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentView-preg_inspection.do" defaultField="inspectionDate" >
      <msh:hidden property="id"  />
      <msh:hidden property="medCase"  />
      <msh:hidden property="saveType" />
      <msh:panel >
        <msh:row >
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row >
          <msh:textField property="inspectionDate" label="Дата"  />
          <msh:textField property="inspectionTime" label="Время"  />
        </msh:row>
        <msh:row >
          <msh:textArea property="notes" label="Описание"  rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row >
          <msh:textField property="inspector" label="Кто проводил осмотр"  fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4"  />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_inspectionForm"  />
  </tiles:put>
</tiles:insert>

