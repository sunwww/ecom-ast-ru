<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
       
    </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-stac_omcCriterion.do" defaultField="criterion">
      <msh:panel>
        <msh:hidden property="id" />
        <msh:hidden property="saveType" />
        <msh:hidden property="medCase" />
        <msh:row>
        	<msh:autoComplete vocName="vocClassificationCriterion" property="criterion" label="Класс. критерий" horizontalFill="true" size="100"/>
        </msh:row>
                <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
      
    </msh:form>

</tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_omcCriterionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_omcCriterionForm">
      <msh:sideMenu title="КК">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_omcCriterion" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcCriterion/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-stac_omcCriterion" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/OmcCriterion/Delete" />
      </msh:sideMenu>

    </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsNotView formName="stac_omcCriterionForm">
  	<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>

  	<script type="text/javascript">

      		</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

