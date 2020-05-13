<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Общественно-опасное действие
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_publicDangerousEffect.do" defaultField="effectDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="careCard" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="effectNumber" label="Номер"/>
        	<msh:textField property="effectDate" label="Дата действия"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete showId="false" horizontalFill="true" property="criminalCodeArtical" label="Статья уголовного кодекса" vocName="vocCriminalCodeArticle" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="effectType" label="Тип ООД" vocName="vocPublicDangerousEffect" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="notes" label="Примечание" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_publicDangerousEffectForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeAreViewOrEdit formName="psych_publicDangerousEffectForm">
    <msh:sideMenu title="Общественно опасное действие">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_publicDangerousEffect" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Edit" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-psych_publicDangerousEffect" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/Delete"  />
    </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
</tiles:insert>