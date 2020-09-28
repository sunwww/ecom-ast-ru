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
    <form>
    	<div id='viewParameter'>
    	</div>
    </form>
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction="mis_medService" />
    <msh:sideMenu title="Заключение">
      <msh:ifFormTypeIsView formName="diary_templateForm">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-diary_template" name="Изменить" roles="/Policy/Diary/Template/Edit" />
        <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-diary_template" name="Удалить" confirm="Удалить шаблон заключения?" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <tags:voc_menu currentAction="medService" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_templateForm" />
  </tiles:put>
    <tiles:put name="javascript" type="string">
    	
    	<script type="text/javascript" src="./dwr/interface/TemplateProtocolService.js"></script>
    	<script type="text/javascript">
    	
    	</script>
    </tiles:put>
</tiles:insert>

