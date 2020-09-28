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
    <msh:form  action="/entityParentSaveGoParentView-exp_vocMark.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="criterion" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="code" label="Код"/>
        	<msh:textField property="mark" label="Оценочный балл" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isIgnore" label="Не учитывать при суммирование" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isNeedComment" label="При выборе заполнять примечание" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textArea label="Полное название" property="fullname" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="exp_vocMarkForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Балл критерия оценки качества">
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationMark/Edit" key="ALT+2" params="id" action="/entityParentEdit-exp_vocMark" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationMark/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-exp_vocMark" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="exp_voc_kind"/>
  </tiles:put>
</tiles:insert>