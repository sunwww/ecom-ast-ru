<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-mis_workBook.do" defaultField="hello" title="Трудовая книжка">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="person" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="dateStart" label="Дата заведения" />
          <msh:textField passwordEnabled="false" property="numberBook" viewOnlyField="false" label="Номер трудовой книжки" guid="d31a1864-8c44-4c1f-935b-ae76a1f92a6b" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_workBookForm" guid="e4c62deb-7fd7-43f2-a2a1-ba3e365b1">
      <msh:section title="Записи" guid="fefb4ff0-0639-4962-9a9f-1e2f2">
        <ecom:parentEntityListAll formName="work_workBookRecordForm" attribute="records" guid="99d1c735-3984-4535-8458-a10457bb873f" />
        <msh:table hideTitle="false" disableKeySupport="false" idField="id" name="records" action="entityParentView-work_workBookRecord.do" disabledGoFirst="false" disabledGoLast="false" guid="88956c56-68c5-4785-9a47-02cc52d81180">
          <msh:tableColumn columnName="Дата записи" identificator="false" property="recordDate" guid="c59126ba-e3bb-4a81-804d-ea8684d28256" />
          <msh:tableColumn columnName="Штатная единица" identificator="false" property="staffUnit" guid="4ace0708-d07c-40e8-ab41-c823a4658eb6" />
          <msh:tableColumn columnName="Тип записи" identificator="false" property="workRecordType" guid="b2b2d54c-0dfc-40ca-9adc-c31ef399eed7" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="mis_workBookForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Проба">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_workBook" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink guid="sideLinkNew" key="ALT+N" params="id" action="/entityParentPrepareCreate-mis_workBook" name="Потомка" roles="/Policy/IdeMode/Hello/Create" title="Добавить потомка" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-mis_workBook" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-work_workBookRecord" name="Добавить запись" title="Добавить запись в трудовую книжку" guid="018b92c4-69c1-4eac-85fc-3843cd5c1ea0" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

