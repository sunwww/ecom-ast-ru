<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-smo_visitPrescriptionBlank.do" defaultField="series" guid="aee345be-82ca-4aff-9349-92df93b593cc">
      <msh:hidden property="id" guid="af7e3d82-e7b2-439f-96b0-aaa45e5e4ec2" />
      <msh:hidden property="saveType" guid="aeaf3006-9cb4-4bbb-bffc-55f39f409801" />
      <msh:hidden property="medCase" guid="8a2ae298-5156-4fad-801c-dad6af24da28" />
      <msh:panel guid="3b2dc74f-d07e-478c-a34f-809970852ab8">
        <msh:row guid="d772fb99-bd50-4195-abcf-0268ddb1fe1d">
          <msh:textField label="Серия бланка" property="series" fieldColSpan="1" horizontalFill="true" size="5" guid="fb201944-ca8c-4923-bf58-83b696779808" />
          <msh:textField label="Номер бланка" property="number" fieldColSpan="1" horizontalFill="true" size="10" guid="84a01450-d507-46ec-967c-b5af384430ae" />
        </msh:row>
        <msh:row guid="81cb8a12-11ea-479a-85d4-44f0fa0b9536">
          <msh:textField label="Дата выписки" property="writingOutDate" fieldColSpan="2" horizontalFill="true" size="5" guid="b4cf926d-2af4-46ba-9436-43602c8b9775" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="b9f4fbc7-8d87-4602-b6a0-29d1943504a5" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="d35101e3-6394-491d-979f-af7285fbddb8">
      <msh:ifFormTypeIsView formName="smo_visitPrescriptionBlankForm" guid="5a0b1360-622e-435e-b41b-cfc13d18eecd">
        <msh:sideLink roles="/Policy/Mis/MedCase/PrescriptionBlank/Edit" key="ALT+2" params="id" action="/entityParentEdit-smo_visitPrescriptionBlank" name="Редактировать" guid="b88b4ff2-bf47-44f9-bc14-abbfafffa805" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="smo_visitPrescriptionBlankForm" guid="5251465e-e503-4073-a41c-0b71a701d85d">
        <msh:sideLink roles="/Policy/Mis/MedCase/PrescriptionBlank/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-smo_visitPrescriptionBlank" name="Удалить" confirm="Вы действительно хотите удалить?" guid="f7288bb0-6a71-4401-b729-2903aa17e3e4" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="MedCase" beginForm="smo_visitPrescriptionBlankForm" guid="589668eb-5e15-40a3-a7f6-334fb2d8406d" />
  </tiles:put>
</tiles:insert>

