<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник хир.операций
    	  -->
    <msh:form  action="/entitySaveGoView-voc_operation.do" defaultField="name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        <msh:row>
        	<msh:textField label="Код операции" property="code" />
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        <msh:separator label="Период актуальности хир.операции" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Начало" property="startActualDate" />
        	<msh:textField label="Окончание" property="finishActualDate" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Voc" beginForm="voc_operationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Хир.операция">
      <msh:sideLink roles="/Policy/Voc/VocOperation/Edit" key="ALT+2" params="id" action="/entityEdit-voc_operation" name="Изменить" title="Изменить данные по хир.операции" />
      <msh:sideLink roles="/Policy/Voc/VocOperation/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-voc_operation" name="Удалить" title="Удалить данные по хир.операции" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="operation"/>
  </tiles:put>
</tiles:insert>

