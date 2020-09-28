<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-ily_test.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:textArea rows="2" property="hello" label="Название" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea property="hello" label="Нозологическая форма" fieldColSpan="3" horizontalFill="true" rows="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityView-ily_test.do" vocName="vocHello" property="link" label="Возрастная категория" />
          <msh:autoComplete vocName="vocHello" property="link" label="Соц. статус" />
        </msh:row>
        <msh:row>
          <msh:textArea property="hello" label="Интервал МКБ" horizontalFill="true" fieldColSpan="3" rows="2" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHello" property="link" label="Фаза заболевания" horizontalFill="on" />
          <msh:autoComplete vocName="vocHello" property="link" label="Стадия заболевания" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHello" property="link" label="Осложнение" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHello" property="link" label="Условия оказания мед.помощи" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHello" property="link" label="Уровень оказания мед.помощи" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row />
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="ily_testForm">
      <msh:section title="Диагностика">
        <ecom:parentEntityListAll formName="ily_diagnosticForm" attribute="childs" />
        <msh:table name="childs" action="entityParentView-ily_diagnostic.do" idField="id">
          <msh:tableColumn columnName="Код" property="id" />
          <msh:tableColumn columnName="Услуга" property="hello" />
          <msh:tableColumn columnName="Количество" property="parent" />
          <msh:tableColumn columnName="Ср. кол-во" property="parent" />
          <msh:tableColumn columnName="Мода" property="parent" />
          <msh:tableColumn columnName="Мин. кол-во" property="parent" />
          <msh:tableColumn columnName="Макс. кол-во" property="parent" />
          <msh:tableColumn columnName="Опт. кол-во" property="parent" />
          <msh:tableColumn columnName="Вероятность" property="parent" />
          <msh:tableColumn columnName="Комментарий" property="parent" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="ily_testForm">
      <msh:section title="Лечение">
        <ecom:parentEntityListAll formName="ily_cureForm" attribute="cures" />
        <msh:table name="cures" action="entityView-ily_cure.do" idField="id">
          <msh:tableColumn columnName="Код" property="id" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="ily_testForm" title="МЭС" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-ily_diagnostic" name="Изменить услугу" roles="/Policy/IdeMode/Hello/Edit" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-ily_diagnostic" name="Добавить услугу" roles="/Policy/IdeMode/Hello/Create" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-ily_diagnostic" name="Удалить услугу" roles="/Policy/IdeMode/Hello/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

