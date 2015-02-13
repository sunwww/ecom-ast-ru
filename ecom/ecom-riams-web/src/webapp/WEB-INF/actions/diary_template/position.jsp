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
    <msh:sideMenu title="Заключение" guid="5db0db09-9993-44cb-8477-a3fee5037b42">
      <msh:ifFormTypeIsView formName="diary_templateForm" guid="dd63e5e4-f81c-43f2-b50a-f12b1d8e026b">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-diary_template" name="Изменить" roles="/Policy/Diary/Template/Edit" guid="05503c33-989a-45dc-ab6f-8d1be735e97e" />
        <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-diary_template" name="Удалить" confirm="Удалить шаблон заключения?" guid="bc31c499-00cd-4cf8-94f0-fcdf1c9915ff" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <tags:diary_additionMenu />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_templateForm" guid="4399c99f-8801-4a73-b168-c25c23f8b0ba" />
  </tiles:put>
    <tiles:put name="javascript" type="string">
    	
    	<script type="text/javascript" src="./dwr/interface/TemplateProtocolService.js"></script>
    	<script type="text/javascript">
    	
    	</script>
    </tiles:put>
</tiles:insert>

