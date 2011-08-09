<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник баллов критериев оценки качества
    	  -->
    <msh:form  action="/entityParentSaveGoView-exp_vocMark.do" defaultField="name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenCriterion" property="criterion" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        <msh:row>
        	<msh:textField property="code" label="Код"/>
        	<msh:textField property="mark" label="Оценочный балл" />
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textArea label="Полное название" property="fullname" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Voc" beginForm="exp_vocMarkForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Балл критерия оценки качества">
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationMark/Edit" key="ALT+2" params="id" action="/entityEdit-exp_vocMark" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationMark/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-exp_vocMark" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="exp_voc_kind"/>
  </tiles:put>
</tiles:insert>