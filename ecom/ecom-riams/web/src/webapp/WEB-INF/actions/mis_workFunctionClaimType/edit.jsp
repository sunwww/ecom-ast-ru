<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_workFunctionClaimType.do" defaultField="claimType">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
        <msh:hidden property="workfunction" />
      <msh:panel>
      <msh:autoComplete property="workfunction" vocName="groupWorkFunction" label="Рабочая функция" size="50" />
         <msh:row>
                <msh:autoComplete property="claimType" vocName="vocClaimType" size="50" label="Тип заявки" horizontalFill="true" />
         </msh:row>
         <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_workFunctionClaimTypeForm" />
  </tiles:put>
   <tiles:put name="side" type="string">
  <msh:sideMenu title="Проба">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_workFunctionClaimType" name="Изменить" roles="/Policy/Mis/Claim/Operator" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_workFunctionClaimType" name="Удалить" roles="/Policy/Mis/Claim/Operator" />
    </msh:sideMenu>
    </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">

    </script>
  </tiles:put>
  <tiles:put name="style" type="string">
    <style type="text/css">h3 {
    		margin-top: 1em ;
    		margin-bottom: 0.5em ;
    		color: brown ;
    	}
    </style>
  </tiles:put>
</tiles:insert>

