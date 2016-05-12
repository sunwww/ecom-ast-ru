<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSaveGoParentView-dis_medSocCommission.do" defaultField="assignmentDate" guid="52773e21-f0db-45e5-a580-67110e518c29">
      <msh:hidden property="id" guid="88f2ffe0-359b-4a4a-a14c-eba156a5a40c" />
      <msh:hidden property="saveType" guid="8db35c02-7a0b-46a2-90b0-e7a1cb9e2788" />
      <msh:hidden property="disabilityDocument" guid="e49c27a2-d06a-482a-aa6a-d51d268bde0e" />
      <msh:panel guid="ccca9c35-4c94-4093-948c-f08edbe29dc4">
        <msh:row guid="54c16e21-e64d-44ed-bd77-3b0a63d8ed13">
          <msh:textField property="assignmentDate" label="Дата направления" guid="8af81e3e-a0f0-4dc0-b07b-6daa0deb9ee5" />
          <msh:textField property="registrationDate" label="Дата регистрации документов" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:textField property="examinationDate" label="Дата освидетельствования" guid="4e2fcb5c-5011-4a38-b6d2-3f48aa6c95bf" />
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocDisabilityDegree" property="disabilityDergee" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" label="Степень ограничения трудоспособности" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="invalidityRegistration" fieldColSpan="3" horizontalFill="true" label="Установлена/изменена группа инвалидности"/>
        </msh:row>
          <msh:row>
            <msh:autoComplete vocName="vocInvalidity" property="invalidity" label="Инвалидность" guid="84add1e7-0a81-4762-9864-fbffc69b51de" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textArea rows="2" property="comments" label="Комментарий" guid="94ec307a-3517-4790-877b-e9e3cf1ccd29" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_medSocCommissionForm" guid="9285007a-f9c1-44a2-bf85-0066c21b6ed2" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_medSocCommissionForm" guid="4e7f4c66-f820-434d-97e7-590d2d1cdb5c">
      <msh:sideMenu guid="1ff33750-b954-4b95-ac65-91c363e8bf45" title="МСЭК">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_medSocCommission" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Edit" guid="4aa9eacf-bd60-46c7-813e-9a8c543419ce" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_medSocCommission" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Delete" guid="6549f746-6a37-481c-9d15-2471d6281c4f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

