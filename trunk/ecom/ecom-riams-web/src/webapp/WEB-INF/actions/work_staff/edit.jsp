<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSave-work_staff.do" defaultField="fullRate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="lpu" />
      <msh:panel guid="panel">
        <msh:ifFormTypeIsView formName="work_staffForm" insideJavascript="false" guid="7a134f03-3609-4009-a08b-1459553ebe2d">
          <msh:separator colSpan="6" label="Всего ставок" guid="a7a522f5-8373-4f5e-b0a9-f0be4215b7c1" />
          <msh:row guid="row1">
            <msh:textField guid="textFieldHello" property="fullRate" label="Всего" viewOnlyField="true" />
            <msh:textField passwordEnabled="false" property="budjetRate" viewOnlyField="true" label="Бюджетных" guid="a9da296b-ea68-4b6c-a4bc-ac58faffb3a0" horizontalFill="false" />
            <msh:textField passwordEnabled="false" property="offBudjetRate" viewOnlyField="true" label="Внебюджетных" guid="2444c523-dfc5-495b-8124-9c87260b2916" horizontalFill="false" />
          </msh:row>
          <msh:separator colSpan="6" label="Свободно" guid="3ad69479-e9f4-4874-95d0-c95ba8eb2aa6" />
          <msh:row guid="f579ed96-0df6-413a-b436-740b9c2eb74c">
            <msh:textField passwordEnabled="false" property="freeFullRate" viewOnlyField="true" label="Всего" guid="4a6c9420-10f2-4de3-9c32-6648b613f7aa" horizontalFill="false" />
            <msh:textField passwordEnabled="false" property="freeBudjetRate" viewOnlyField="true" label="Бюджетных" guid="f61cbe19-d2fd-4b65-a902-1eb1421232a7" horizontalFill="false" />
            <msh:textField passwordEnabled="false" property="freeOffBudjetRate" viewOnlyField="false" label="Внебюджетных" guid="cc5ae0fd-ff65-4c51-86b7-26f6811814e2" horizontalFill="false" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="work_staffForm" insideJavascript="true">
      <msh:ifFormTypeIsView formName="work_staffForm" insideJavascript="false" guid="720fcad0-0883-4a35-a79b-d1cc2ac83970">
        <msh:ifInRole roles="/Policy/Mis/Worker/StaffUnit/View" guid="54cd01fb-3477-4fdf-9184-39fe5e46df71">
          <msh:section guid="sectionChilds" title="Штатные единицы">
            <ecom:parentEntityListAll guid="parentEntityListChilds" formName="work_staffUnitForm" attribute="childs" />
            <msh:table guid="tableChilds" name="childs" action="entityParentView-work_staffUnit.do" idField="id">
              <msh:tableColumn columnName="Должность" property="info" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
              <msh:tableColumn columnName="Всего" property="totalAmount" guid="a744754f-5212-4807-910f-e4b252aec108" />
              <msh:tableColumn columnName="Занято" property="busyAmount" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
              <msh:tableColumn columnName="Бюджетная" identificator="false" property="budjet" guid="bc23774c-4484-44a2-8cc5-743e4ed9c944" />
            </msh:table>
          </msh:section>
        </msh:ifInRole>
      </msh:ifFormTypeIsView>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="work_staffForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_staffForm" insideJavascript="false" guid="8464cfe9-ff6b-4aca-8dd4-5ffd211730aa">
      <msh:sideMenu guid="sideMenu-123" title="Штатное расписание">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-work_staff" name="Изменить" roles="/Policy/Mis/Worker/Staff/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-work_staff" name="Удалить" roles="/Policy/Mis/Worker/Staff/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="work_staffForm" insideJavascript="false" guid="e14c404b-4ede-4d34-ba8c-95014423b4ce">
      <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-work_staffUnit" name="Штатную единицу" title="Добавить штатную единицу" guid="a32b81fe-abd1-4fd8-8c09-8e05c7ae4790" roles="/Policy/Mis/Worker/StaffUnit/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

