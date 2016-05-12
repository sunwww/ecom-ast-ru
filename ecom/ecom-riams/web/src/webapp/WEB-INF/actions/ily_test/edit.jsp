<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-ily_test.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:panel guid="panel">
        <msh:row guid="f50f40f3-2cae-4795-93e8-92f398ceccd1">
          <msh:textArea rows="2" property="hello" label="Название" guid="476b37e4-f8c4-4ed4-b3bd-35066f63c23d" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:textArea guid="textFieldHello" property="hello" label="Нозологическая форма" fieldColSpan="3" horizontalFill="true" rows="3" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entityView-ily_test.do" vocName="vocHello" property="link" guid="3a3e4d1b-8802-467d-b205-715fb379b018" label="Возрастная категория" />
          <msh:autoComplete vocName="vocHello" property="link" label="Соц. статус" guid="5dda7fb6-b2c3-44be-9d10-d8d428637b32" />
        </msh:row>
        <msh:row guid="8c3c658c-f605-42cc-accb-6c930524156b">
          <msh:textArea property="hello" label="Интервал МКБ" guid="49a00211-7ba8-4c4b-8587-dd7d8796ac36" horizontalFill="true" fieldColSpan="3" rows="2" />
        </msh:row>
        <msh:row guid="a4e3c3f8-68ed-40fb-8dc2-0088cd61fe89">
          <msh:autoComplete vocName="vocHello" property="link" label="Фаза заболевания" guid="a96b388e-3158-4b9a-ab8c-f62454a364e9" horizontalFill="on" />
          <msh:autoComplete vocName="vocHello" property="link" label="Стадия заболевания" guid="b2e2b376-b29c-4e23-8d4a-3a3e72fd21c6" />
        </msh:row>
        <msh:row guid="07fbe7a5-4caf-4e14-aa70-287cf4ca610d">
          <msh:autoComplete vocName="vocHello" property="link" label="Осложнение" guid="4dd68795-4fac-4424-837e-5aa23fc67121" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="cde448eb-e2e3-4fd8-8de1-07be31c0fea1">
          <msh:autoComplete vocName="vocHello" property="link" label="Условия оказания мед.помощи" guid="433c4c06-92ee-44d6-9ab3-87d1ffb002d3" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="28a8a1de-6ac5-4ae4-adf3-1e75b284a065">
          <msh:autoComplete vocName="vocHello" property="link" label="Уровень оказания мед.помощи" guid="791e0ff5-f978-4148-b509-e5f19544d197" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="855de982-5baf-46f1-9f8b-f0ee55002148" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="ily_testForm">
      <msh:section guid="sectionChilds" title="Диагностика">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="ily_diagnosticForm" attribute="childs" />
        <msh:table guid="table123" name="childs" action="entityParentView-ily_diagnostic.do" idField="id">
          <msh:tableColumn columnName="Код" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Услуга" property="hello" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Количество" property="parent" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
          <msh:tableColumn columnName="Ср. кол-во" property="parent" guid="c66f6291-70ed-4d88-bb3f-bc63133a70c2" />
          <msh:tableColumn columnName="Мода" property="parent" guid="efbeb0a2-f202-40d3-8d45-017e94a233f6" />
          <msh:tableColumn columnName="Мин. кол-во" property="parent" guid="8c7783a6-d6fa-4d30-a4e9-930c57dd561b" />
          <msh:tableColumn columnName="Макс. кол-во" property="parent" guid="e2635fe0-9661-4fe7-9f47-2ffc65bf5253" />
          <msh:tableColumn columnName="Опт. кол-во" property="parent" guid="1d9ce129-186a-4b45-ab5a-372b70e275f3" />
          <msh:tableColumn columnName="Вероятность" property="parent" guid="334dccd6-a37d-4526-9260-28a67a86478d" />
          <msh:tableColumn columnName="Комментарий" property="parent" guid="5e76467f-0bf8-4085-acd9-b5690178afbf" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="ily_testForm" guid="7c5feee4-1a3d-4185-8d9d-9cc454873f8d">
      <msh:section title="Лечение" guid="01d84b83-63fd-4ad4-8cc5-c170d4980426">
        <ecom:parentEntityListAll guid="parentEntityListCures" formName="ily_cureForm" attribute="cures" />
        <msh:table name="cures" action="entityView-ily_cure.do" guid="a2ba16c9-4a00-4aff-99ed-d66e9ca2eecf" idField="id">
          <msh:tableColumn columnName="Код" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca12c" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="ily_testForm" title="МЭС" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-ily_diagnostic" name="Изменить услугу" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink guid="sideLinkNew" key="ALT+N" params="id" action="/entityParentPrepareCreate-ily_diagnostic" name="Добавить услугу" roles="/Policy/IdeMode/Hello/Create" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-ily_diagnostic" name="Удалить услугу" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

