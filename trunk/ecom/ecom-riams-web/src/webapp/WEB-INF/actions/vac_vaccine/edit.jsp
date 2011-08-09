<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-vac_vaccine.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        
        
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="abbrevation" label="Аббревиатура" />
        </msh:row>
        <msh:row guid="c4643a8b-47b4-4771-bad8-7c54564d9864">
          <msh:textField property="name" label="Название" guid="d95b5f6c-1c03-4ef6-a40e-a7cc53337fa6" />
        </msh:row>

        <msh:row guid="ba0e6be0-3960-4f90-bb0a-1a65dd2bae14">
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocNosology" label="Вакцинируемые нозологии" property="nosologyList" />
        </msh:row>
        
        <msh:row guid="ba0e6be0-3960-4f90-bb0a-1a65dd2bae0d">
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocCommonReaction" label="Общие реакции" property="commonReactionList" />
        </msh:row>
        <msh:row guid="ba0e6be0-3960-4f90-bb0a-1a65dd2bae12">
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocLocalReaction" label="Местные реакции" property="localReactionList" />
        </msh:row>
        <msh:row guid="ba0e6be0-3960-4f90-bb0a-1a65dd2bae13">
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocMethod" label="Методы вакцинации" property="methodList" />
        </msh:row>
        
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="vac_vaccineForm" />
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="vac_vaccineForm" title="Вакцина" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-vac_vaccine" name="Изменить" roles="/Policy/Mis/Vaccination/Vaccine/Edit" />
      <msh:sideLink guid="sideLinkNew" key="ALT+N" params="id" action="/entityParentPrepareCreate-vac_vaccine" name="Добавить вакцину" roles="/Policy/Mis/Vaccination/Vaccine/Create" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-vac_vaccine" name="Удалить" roles="/Policy/Mis/Vaccination/Vaccine/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

