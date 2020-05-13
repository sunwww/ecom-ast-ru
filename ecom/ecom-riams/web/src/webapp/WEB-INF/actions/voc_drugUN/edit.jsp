<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник Лек.Ср
    	  -->
    <msh:form  action="/entitySaveGoView-voc_drugUN.do" defaultField="code">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="5%,60%,5%,1%">
        <msh:row>
        	<msh:textField  label="Код" property="code" />
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="1"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_drugUNForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Лек. средства">
      <msh:sideLink roles="/Policy/Voc/VocDrugUnlicensedName/Edit" key="ALT+2" params="id" action="/entityEdit-voc_drugUN" name="Изменить" title="Изменить данные по Лек.Ср" />
      <msh:sideLink roles="/Policy/Voc/VocDrugUnlicensedName/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-voc_drugUN" name="Удалить" title="Удалить данные по Лек.Ср" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="drugUN"/>
  </tiles:put>
    <tiles:put type="string" name="javascript">
  	<msh:ifFormTypeIsCreate formName="voc_drugUNForm">
  	<script type="text/javascript">
  		$('name').value='${param.name}' ;
  	</script>
  	</msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>

