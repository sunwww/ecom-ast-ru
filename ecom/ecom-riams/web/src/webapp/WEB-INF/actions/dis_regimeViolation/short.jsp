<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSaveGoView-dis_regimeViolation.do" defaultField="dateFrom">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="disabilityDocument" />
      <msh:panel>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата начала нарушения" />
          <msh:textField property="dateTo" label="Дата окончания нарушения" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocRegimeViolationType" property="regimeViolationType" horizontalFill="true" fieldColSpan="3" label="Тип нарушения" />
        </msh:row>
        <msh:row>
          <msh:textArea rows="2" property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:submitCancelButtonsRow colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_regimeViolationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_regimeViolationForm">
      <msh:sideMenu title="Нарушение режима">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_regimeViolation" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_regimeViolation" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

