<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-vac_vaccinationEstop.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="vaccination" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:row guid="3ef027c3-abec-4e5d-9f9c-8304165eca10">
            <msh:checkBox property="noActuality" label="Недействительность" guid="155c017f-7d4e-451a-ae3f-bcb8990019d2" />
          </msh:row>
          <msh:textField guid="textFieldHello" property="estopDate" label="Дата" />
          <msh:textField property="dateFinish" label="Дата окончания" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entityView-vac_vaccinationEstop.do" vocName="vocVaccinationEstopType" property="estopType" guid="3a3e4d1b-8802-467d-b205-715fb379b018" fieldColSpan="3" label="Тип" horizontalFill="true" />
        </msh:row>
        <msh:row guid="260e362b-75a2-4800-8482-5b7480593ee8">
          <msh:textArea hideLabel="false" property="comments" viewOnlyField="false" label="Комментарий" guid="37ba4ecc-30a0-4624-8c8a-3c4597c1518b" fieldColSpan="3" rows="5" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="vac_vaccinationEstopForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="vac_vaccinationEstopForm" guid="957b4f83-bcab-4b6a-a328-1667ca474b58">
      <msh:sideMenu guid="sideMenu-123">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-vac_vaccinationEstop" name="Изменить" roles="/Policy/Mis/Vaccination/VaccinationEstop/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-vac_vaccinationEstop" name="Удалить" roles="/Policy/Mis/Vaccination/VaccinationEstop/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

