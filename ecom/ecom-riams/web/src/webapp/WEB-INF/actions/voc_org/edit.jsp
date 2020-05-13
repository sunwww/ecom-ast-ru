<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник организаций
    	  -->
    <msh:form  action="/entitySaveGoView-voc_org.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Краткое наим." property="code" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Старый код ФОНДА" property="oldFondNumber" />
        	<msh:textField  label="Новый код ФОНДА" property="fondNumber" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_orgForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Организация">
      <msh:sideLink roles="/Policy/Voc/VocOrg/Edit" key="ALT+2" params="id" action="/entityEdit-voc_org" name="Изменить" title="Изменить данные по организации" />
      <msh:sideLink roles="/Policy/Voc/VocOrg/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-voc_org" name="Удалить" title="Удалить данные из организации" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="org"/>
  </tiles:put>
</tiles:insert>

