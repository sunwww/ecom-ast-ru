<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Продление
    	  -->
    <msh:form action="/entityParentSaveGoParentView-dis_record.do" defaultField="dateFrom">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="disabilityDocument" />
      <msh:panel>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата начала" />
          <msh:textField property="dateTo" label="Дата окончания" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityRegime" property="regime" label="Режим" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Леч.врач" fieldColSpan="3" horizontalFill="true" size="150" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" hideLabel="false" property="workFunctionAdd" viewOnlyField="false" label="Председ.ВК" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_recordForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_recordForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_record" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Record/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_record" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Record/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

