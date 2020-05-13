<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-vac_vaccine.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        
        
        <msh:row>
          <msh:textField property="abbrevation" label="Аббревиатура" />
        </msh:row>
        <msh:row>
          <msh:textField property="name" label="Название" />
        </msh:row>

        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocNosology" label="Вакцинируемые нозологии" property="nosologyList" />
        </msh:row>
        
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocCommonReaction" label="Общие реакции" property="commonReactionList" />
        </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocLocalReaction" label="Местные реакции" property="localReactionList" />
        </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vaccineVocMethod" label="Методы вакцинации" property="methodList" />
        </msh:row>
        
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="vac_vaccineForm" />
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="vac_vaccineForm" title="Вакцина" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-vac_vaccine" name="Изменить" roles="/Policy/Mis/Vaccination/Vaccine/Edit" />
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-vac_vaccine" name="Добавить вакцину" roles="/Policy/Mis/Vaccination/Vaccine/Create" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-vac_vaccine" name="Удалить" roles="/Policy/Mis/Vaccination/Vaccine/Delete" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

