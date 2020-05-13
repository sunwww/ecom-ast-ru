<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник диапазонов
    	  -->
    <msh:form  action="/entityParentSaveGoParentView-rep_parameterVariety.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parameterType" />
      <msh:panel>
        <msh:row>
        	<msh:textField label="Код с" property="codeFrom" />
        	<msh:textField label="по" property="codeTo" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="rep_parameterVarietyForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Диапазоны МКБ">
      <msh:sideLink roles="/Policy/Voc/ReportConfig/Edit" key="ALT+2" params="id" action="/entityParentEdit-rep_parameterVariety" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Voc/ReportConfig/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-rep_parameterVariety" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="repconfig"/>
  </tiles:put>
</tiles:insert>

