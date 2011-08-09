<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-mis_workBook.do" defaultField="dateStart" title="Трудовая книжка">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="person" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="dateStart" label="Дата заведения" />
			</msh:row>
			<msh:row guid="d324-8c44-4c1f-935b-a2652a6b">
          <msh:textField property="seriesBook" label="Серия трудовой книжки" guid="d318864-8c44-4c1f-935b-ae7652a6b" horizontalFill="false" />
          <msh:textField property="numberBook" label="Номер трудовой книжки" guid="d31a1864-8c44-4c1f-935b-ae76a1f92a6b" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_workBookForm" guid="e4c62deb-7fd7-43f2-a2a1-ba3e365b1">
      <msh:ifInRole roles="/Policy/Mis/Worker/WorkBookRecord/View" guid="a941bb7a-b84d-4152-99ca-835f3430b85b">
        <msh:section title="Записи" guid="fefb4ff0-0639-4962-9a9f-1e2f2">
          <ecom:parentEntityListAll formName="work_workBookRecordForm" attribute="records" guid="99d1c735-3984-4535-8458-a10457bb873f" />
          <msh:table hideTitle="false" disableKeySupport="false" idField="id" name="records" action="entityParentView-work_workBookRecord.do" disabledGoFirst="false" disabledGoLast="false" guid="88956c56-68c5-4785-9a47-02cc52d81180">
            <msh:tableColumn columnName="Дата записи" identificator="false" property="recordDate" guid="c59126ba-e3bb-4a81-804d-ea8684d28256" />
            <msh:tableColumn columnName="Штатная единица" identificator="false" property="staffUnitText" guid="4ace0708-d07c-40e8-ab41-c823a4658eb6" />
            <msh:tableColumn columnName="Тип записи" identificator="false" property="workRecordTypeText" guid="b2b2d54c-0dfc-40ca-9adc-c31ef399eed7" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="mis_workBookForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_workBookForm" insideJavascript="false" guid="7256a11d-e7bd-487e-acdd-a9cc6efd98a5">
      <msh:sideMenu guid="sideMenu-123" title="Проба">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_workBook" name="Изменить" roles="/Policy/Mis/Worker/WorkBook/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_workBook" name="Удалить" roles="/Policy/Mis/Worker/WorkBook/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="mis_workBookForm" insideJavascript="false" guid="bba54d1d-85d0-4b3c-a3df-990dfb130c8e">
      <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-work_workBookRecord" name="Запись" guid="018b92c4-69c1-4eac-85fc-3843cd5c1ea0" roles="/Policy/Mis/Worker/WorkBookRecord/Create" title="Добавить запись" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

