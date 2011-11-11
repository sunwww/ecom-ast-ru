<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-dis_regimeViolation.do" defaultField="dateFrom">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="disabilityDocument" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField property="dateFrom" label="Дата начала нарушения" />
          <msh:textField property="dateTo" label="Дата окончания нарушения" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocRegimeViolationType" property="regimeViolationType" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" label="Тип нарушения" />
        </msh:row>
        <msh:row guid="3b4b6ca6-aec4-4589-af82-57e4f632b717">
          <msh:textArea rows="2" property="comment" label="Комментарий" guid="94aab4c5-059b-40ae-9256-0c31e72a1ec7" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="a2a8b981-5f0c-4039-9edf-428abf128072">
          <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Disability" beginForm="dis_regimeViolationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_regimeViolationForm" guid="ec8617ac-7f6e-464f-94b0-7490a485f6f0">
      <msh:sideMenu guid="1b569ae3-ebb8-44fc-92f5-168d7a42e84f" title="Нарушение режима">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_regimeViolation" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Edit" guid="99be4728-b613-4814-8439-ee161d1b714f" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_regimeViolation" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Delete" guid="cf8047c5-0819-4655-82cc-2181a049c3c5" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

