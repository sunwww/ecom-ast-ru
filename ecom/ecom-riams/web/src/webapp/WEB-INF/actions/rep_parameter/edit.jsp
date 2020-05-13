<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник параметров для отчета
    	  -->
    <msh:form  action="/entitySaveGoView-rep_parameter.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="className" label="ОТЧЕТ" size="20"/>
        	<msh:textField label="Код строки" property="strCode"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Код" property="code" size="20"/>
        	<msh:autoComplete label="Пол" property="sex" vocName="vocSex"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="rep_parameterForm">
<ecom:webQuery name="list" nativeSql="
  	select tpt.id, tpt.codeFrom,tpt.codeTo 
  	from ReportSetTypeParameterType tpt
where tpt.parameterType_id='${param.id}'
order by tpt.codeFrom

"/>
  	<msh:table name="list" 
  	deleteUrl="entityParentDeleteGoParentView-rep_parameterVariety.do?short=Short"
  	editUrl="entityParentEdit-rep_parameterVariety.do"
  	
  	 action="entityParentView-rep_parameterVariety.do" idField="1">
  		<msh:tableColumn property="2" columnName="с"/>
  		<msh:tableColumn property="3" columnName="по"/>
  		
  	</msh:table>    
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="rep_parameterForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Параметр">
      <msh:sideLink roles="/Policy/Voc/ReportConfig/Edit" key="ALT+2" params="id" action="/entityEdit-rep_parameter" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Voc/ReportConfig/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-rep_parameter" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Voc/ReportConfig/Create" key="ALT+2" params="id" action="/entityParentPrepareCreate-rep_parameterVariety" name="Диапазон" title="Добавить диапазон" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="repconfig"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsCreate formName="rep_parameterForm">
  	<script type="text/javascript">
  	$('className').value='${param.id}'
  	</script>
  </msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>

