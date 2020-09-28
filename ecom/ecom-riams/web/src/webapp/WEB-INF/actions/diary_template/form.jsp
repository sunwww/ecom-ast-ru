<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ page import="ru.ecom.diary.ejb.service.protocol.tree.CheckNode"%>
<%@ page import="ru.ecom.diary.web.action.protocol.template.TemplateEditAction"%>
<%@ page import="ru.ecom.diary.ejb.form.protocol.TemplateProtocolForm" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <form action="diary_templateFormSave">
    	

    </form>
    



  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction="mis_medService" />
    <msh:sideMenu title="Заключение">
      <msh:ifFormTypeIsView formName="diary_templateForm">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-diary_template" name="Изменить" roles="/Policy/Diary/Template/Edit" />
        <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-diary_template" name="Удалить" confirm="Удалить шаблон заключения?" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <%-- 
    <msh:sideMenu title="Работа с заключением">
        <msh:sideLink key="ALT+3" params="id" action="/diary_templateParamsEdit" name="Параметры" roles="/Policy/Diary/Template/Edit" />
        <msh:sideLink key="ALT+4" params="id" action="/diary_templateForm" name="Форма ввода" roles="/Policy/Diary/Template/Edit" />
        <msh:sideLink key="ALT+5" params="id" action="/diary_templatePrint" name="Форма печати" roles="/Policy/Diary/Template/Edit" />
    </msh:sideMenu>--%>
    <msh:sideMenu title="Дополнительно">
                  <tags:voc_menu currentAction="mis_medService" />

    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_templateForm" />
  </tiles:put>
    <tiles:put name="javascript" type="string">

    </tiles:put>

</tiles:insert>

