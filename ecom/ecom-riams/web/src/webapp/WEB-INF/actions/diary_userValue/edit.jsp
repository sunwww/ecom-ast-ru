<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_userDomain" />
    <msh:sideMenu title="Значение">
      <msh:ifFormTypeIsView formName="diary_userValueForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-diary_userValue" name="Изменить" roles="/Policy/Diary/User/Domain/Value/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_userValueForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_userValue" name="Удалить" roles="/Policy/Diary/User/Domain/Value/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="diary_userValueForm">
      <msh:sideMenu title="Дополнительно">
                    <tags:voc_menu currentAction="diary_user_voc" />

      </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Медицинских услуг
    	  -->
    <msh:form action="/entityParentSaveGoParentView-diary_userValue.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="domain" />
      <msh:panel>
        <msh:row>
          <msh:textField property="name" label="Название" size="150" />
        </msh:row>
        <msh:row>
          <msh:textField property="cntBall" label="Кол-во баллов" size="50"  />
        </msh:row>
        <msh:row>
          <msh:textField property="comment" label="Сообщение при выборе значения из справочника" size="50"  />
        </msh:row>
        <msh:row>
          <msh:checkBox property="useByDefault" label="Использовать по умолчанию"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_userValueForm" />
  </tiles:put>
</tiles:insert>

