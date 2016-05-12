<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-mis_education.do" defaultField="institutName" title="Образование">
      <msh:hidden guid="hiddenId" property="person" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="person" />
      <msh:panel guid="panel" title="Образование">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocInstitut" property="institut" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" label="Институт" />
          <msh:row guid="a1eb7307-a6db-43b2-a097-8c6b2f88bd83">
            <msh:textField passwordEnabled="false" property="faculty" viewOnlyField="false" label="Факультет" guid="202b19a5-97e7-4deb-a26c-39862d7fa3c6" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
          <msh:row guid="ab8491c0-7b9d-4ad8-86d9-7c590f172347">
            <msh:autoComplete vocName="vocSpec" property="spec" label="Специальность" guid="6dbbe759-318c-44b3-9487-8ae1ac9ad02a" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
          <msh:textField passwordEnabled="false" property="qualification" viewOnlyField="false" label="Квалификация" guid="a9e55495-b140-4eec-8373-5141ed574946" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="bf5ce54d-93b3-496d-92af-07c2714fed8b">
          <msh:textField property="numberDiplom" label="Номер диплома" guid="d265775a-25be-456e-a7e2-66e2edd84da4" />
          <msh:autoComplete showId="false" property="type" viewOnlyField="false" label="Тип обучения " guid="15929ac7-d3e4-40ec-9187-694600bcb1a6" horizontalFill="true" vocName="vocEducationType" size="25" />
        </msh:row>
        <msh:row guid="3c930888-ce58-4945-8660-8ed4e4f842ad">
          <msh:textField passwordEnabled="false" property="dateStart" viewOnlyField="false" label="Дата начала обучения" guid="d0a24c32-8241-41d9-9c3a-d2326e4ad773" horizontalFill="true" />
          <msh:textField passwordEnabled="false" property="dateFinish" viewOnlyField="false" label="Дата окончания обучения" guid="d0311571-ae4e-443d-844e-a05a4e30398b" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="mis_educationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_educationForm" insideJavascript="false" guid="6bbc76de-ccbc-44ff-be63-f6e5fa7ff415">
      <msh:sideMenu guid="sideMenu-123">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_education" name="Изменить" roles="/Policy/Mis/Worker/Education/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_education" name="Удалить" roles="/Policy/Mis/Worker/Education/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

