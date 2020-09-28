<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник типов оборудования
    	  -->
    <msh:form  action="/entitySaveGoView-voc_typeEquip.do" defaultField="code">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
      	<msh:row>
      		<msh:textField label="Код" property="code"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" size="40"/>
        	</msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_typeEquipForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Справочник типов оборудования">
      <msh:sideLink roles="/Policy/Voc/VocTypeEquip/Edit" key="ALT+2" params="id" action="/entityEdit-voc_typeEquip" name="Изменить" title="Изменить " />
      <msh:sideLink roles="/Policy/Voc/VocTypeEquip/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-voc_typeEquip" name="Удалить" title="Удалить" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="post"/>
  </tiles:put>
</tiles:insert>