<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-work_workBookRecord.do" defaultField="recordDate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="workBook" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="recordDate" label="Дата записи" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="staffUnit" property="staffUnit" guid="3a3e4d1b-8802-467d-b205-715fb379b018" label="Штатная единица" size="50" />
        </msh:row>
        <msh:row guid="4ec4e0df-983b-4ec6-8ee9-e973e17db647">
          <msh:textField passwordEnabled="false" property="staffUnitAmount" viewOnlyField="false" label="Количество единиц" guid="9ccb0c29-ab48-4ba3-af64-50ae01fab745" horizontalFill="false" />
          <msh:row guid="2ad229e1-b913-4fcf-91ec-6d9fcd00f3b6">
            <msh:autoComplete showId="false" vocName="vocPostBusyType" hideLabel="false" property="postBusyType" viewOnlyField="false" label="Тип занятия должности" guid="f1812d23-1c71-49c6-839c-840ae92fbec6" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
          <msh:autoComplete showId="false" vocName="vocWorkRecordType" hideLabel="false" property="workRecordType" viewOnlyField="false" label="Тип записи" guid="0b2254ad-75df-490e-8709-92d02052ec8e" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b5efcd9f-1e37-43c3-8991-2aadd1108429">
          <msh:autoComplete showId="false" vocName="vocWorkRecordBase" property="workRecordBase" viewOnlyField="false" label="Основание для увольнения" guid="ff653286-a1ea-4eee-9b9c-1188c56e2da5" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="work_workBookRecordForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_workBookRecordForm" insideJavascript="false" guid="04039522-5124-46b8-ab1b-274c8a9bf6b8">
      <msh:sideMenu guid="sideMenu-123" title="Проба">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-work_workBookRecord" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-work_workBookRecord" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="work_workBookRecordForm" insideJavascript="false" guid="583edbd9-2331-4cbc-9097-5eb2c1700a3f">
      <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8" />
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

