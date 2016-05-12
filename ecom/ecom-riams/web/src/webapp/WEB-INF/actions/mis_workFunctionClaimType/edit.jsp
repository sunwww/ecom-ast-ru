<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_workFunctionClaimType.do" defaultField="claimType" guid="17f720e4-3690-4ae5-961b-6d69348757b6">
      <msh:hidden property="id" guid="df46ed12-bbf3-4f90-9046-dcf1b595541c" />
      <msh:hidden property="saveType" guid="12fee02b-f848-4039-b3f6-35cbf5f3a629" />
        <msh:hidden property="workfunction" guid="289afe10-7420-4b97-b514-d2403eeb73a1" />
      <msh:panel guid="eb62e08a-d34a-4af0-9f13-d23197a33fef">
      <msh:autoComplete property="workfunction" vocName="groupWorkFunction" label="Рабочая функция" size="50" />
         <msh:row guid="a7a62505-2bfe-41b6-a54f-217b970dc0c3">
                <msh:autoComplete property="claimType" vocName="vocClaimType" size="50" label="Тип заявки" guid="67d2a4af-71bc-4a19-8844-4a59b97fabda" horizontalFill="true" />
         </msh:row>
         <msh:submitCancelButtonsRow colSpan="2" guid="fe0172d0-16e6-490d-9bf2-ab6ac96e7402" />
      </msh:panel>
    </msh:form>
  </tiles:put>

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_workFunctionClaimTypeForm" guid="66679190-c7ff-4509-9a19-bd9a724138ac" />
  </tiles:put>
   <tiles:put name="side" type="string">
  <msh:sideMenu guid="sideMenu-123" title="Проба">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_workFunctionClaimType" name="Изменить" roles="/Policy/Mis/Claim/Operator" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_workFunctionClaimType" name="Удалить" roles="/Policy/Mis/Claim/Operator" />
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

