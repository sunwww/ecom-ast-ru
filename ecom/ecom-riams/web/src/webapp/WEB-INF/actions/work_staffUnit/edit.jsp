<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-work_staffUnit.do" defaultField="postName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="staff" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:autoComplete showId="false" vocName="vocPost" property="post" viewOnlyField="false" label="Должность" guid="a290a63c-1831-4ab3-bc06-f8db07757bfd" size="50" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="2549173b-bee2-4323-b524-e5e0dba335e6">
          <msh:checkBox property="budjet" viewOnlyField="false" label="Бюджетная" guid="d049fcfe-4a37-4cd5-96fe-d1a6899c1589" horizontalFill="false" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textField passwordEnabled="false" property="totalAmount" label="Общее количество" guid="03bfe285-c75e-4f22-a2f8-375b5cc6db44" />
          <msh:ifFormTypeIsView formName="work_staffUnitForm" insideJavascript="false" guid="5d0e980b-c19a-4712-95f2-48665c9d5db0">
            <msh:textField passwordEnabled="false" property="busyAmount" viewOnlyField="false" label="Занято" guid="4a367633-823f-4f93-bafe-ed000abf9534" />
          </msh:ifFormTypeIsView>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifInRole roles="FIXME" guid="4c09fb20-5bf3-47ba-8fa8-c8c5549a7ceb">
      <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="work_staffUnitForm">
        <msh:section guid="sectionChilds" title="Записи трудовых книжек">
          <ecom:parentEntityListAll guid="parentEntityListChilds" formName="work_workBookRecordForm" attribute="childs" />
          <msh:table guid="tableChilds" name="childs" action="entityParentView-work_staffUnit.do" idField="staffUnit">
            <msh:tableColumn columnName="№ трудовой книжки" property="workBook" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
            <msh:tableColumn columnName="Дата записи" property="recordDate" guid="a744754f-5212-4807-910f-e4b252aec108" />
            <msh:tableColumn columnName="Сотрудник" identificator="false" property="staffUnitText" guid="392894f7-14d3-4367-902a-77a7e7488983" />
            <msh:tableColumn columnName="Количество занимаемых ставок" identificator="false" property="staffUnitAmount" guid="d94fb34a-3cd3-4409-8ba9-a3f5299073b7" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="work_staffUnitForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_staffUnitForm" insideJavascript="false" guid="8c18c12a-0c4d-42d9-bfc7-0f40adb896aa">
      <msh:sideMenu guid="sideMenu-123" title="Штатная единица">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-work_staffUnit" name="Изменить" roles="/Policy/Mis/Worker/StaffUnit/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-work_staffUnit" name="Удалить" roles="/Policy/Mis/Worker/StaffUnit/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

