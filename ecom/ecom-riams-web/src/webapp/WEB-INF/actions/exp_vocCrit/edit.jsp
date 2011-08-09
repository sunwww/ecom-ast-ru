<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник критериев оценки качества
    	  -->
    <msh:form  action="/entityParentSaveGoView-exp_vocCrit.do" defaultField="name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenKind" property="kind" />
      <msh:panel guid="panel">
        <msh:row>
        	<msh:textField  label="Код" property="code" />
        	<msh:textField label="Короткое название" property="shortName" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocQualityEstimationCritType" label="Тип" property="type" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="exp_vocCritForm">
    	<msh:ifInRole roles="/Policy/Voc/VocQualityEstimationMark/View">
    		<ecom:webQuery name="crit" nativeSql="select id,code,name,mark from VocQualityEstimationMark where criterion_id='${param.id}' order by code"/>
    		<msh:table name="crit" action="entityParentView-exp_vocMark.do" idField="1">
    			<msh:tableColumn property="2" columnName="Код"/>
    			<msh:tableColumn property="3" columnName="Наименование"/>
    			<msh:tableColumn property="4" columnName="Оценка"/>
    		</msh:table>
    	</msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Voc" beginForm="exp_vocCritForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Критерий качества">
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationCrit/Edit" key="ALT+2" params="id" action="/entityEdit-exp_vocCrit" name="Изменить" title="Изменить данные по организации" />
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationCrit/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-exp_vocCrit" name="Удалить" title="Удалить данные из организации" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить">
    	<msh:sideLink roles="/Policy/Voc/VocQualityEstimationMark/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-exp_vocMark" name="Оценку" title="Добавить оценку"/>
    </msh:sideMenu>
    <tags:voc_menu currentAction="exp_voc_kind"/>
  </tiles:put>
</tiles:insert>

