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
      <msh:panel guid="80209fa0-fbd4-45d0-be90-26ca4219af2e">
        <msh:hidden property="id" />
        <msh:hidden property="saveType" />
        <msh:hidden property="medCase" />
        <msh:row>
        	<msh:autoComplete vocName="vocClassificationCriterion" property="criterion" label="Класс. критерий" horizontalFill="true" size="100"/>
        </msh:row>
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
      
    </msh:form>

</tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_omcCriterionForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_omcCriterionForm" guid="c7cae1b4-31ca-4b76-ab51-7f75b52d11b6">
      <msh:sideMenu title="КК" guid="edd9bfa6-e6e7-4998-b4c2-08754057b0aa">
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

