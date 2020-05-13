<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник дефектов оценки качества
    	  -->
    <msh:form  action="/entityParentSaveGoView-exp_vocCritDefect.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="kind" />
      <msh:hidden property="criterion" />
      <msh:panel>
      	<msh:row>
        	<msh:textField  label="Код" property="code" />
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="exp_vocCritDefectForm" />
  </tiles:put>
  
	<tiles:put name="javascript" type="string">
	<msh:ifFormTypeIsCreate formName="exp_vocCritDefectForm">
		<script type="text/javascript">
			
		</script>
		</msh:ifFormTypeIsCreate>
	</tiles:put>  
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Дефект">
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationCritDefect/Edit" key="ALT+2" params="id" action="/entityParentEdit-exp_vocCritDefect" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationCritDefect/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-exp_vocCritDefect" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

