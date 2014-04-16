<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_ticketForm" guid="5c4f3682-e66b-4e0d-b448-4e6a2961a943" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form title="<a href='entityParentView-poly_ticket.do?id=${param.id}'>Талон</a>" action="entityParentSaveGoView-poly_ticket.do" defaultField="date" guid="77bf3d00-cfc6-49eb-9751-76e82d38751c">
      <msh:hidden property="id" guid="e862851f-7390-4fe6-9a37-3b22306138b4" />
      <msh:hidden property="saveType" guid="3e3fb7b5-258e-4194-9dbe-5093382cf627" />
      <msh:hidden property="medcard" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:panel colsWidth="15%,15%,15%,55%" guid="fecf8cd8-e883-4375-b47a-2954067ec3a7">
        <msh:row guid="59560d9f-0765-4df0-bfb7-9a90b5eed824">
          <msh:textField label="Запись на прием: Дата" property="date" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
          <msh:textField label="Время" property="time" fieldColSpan="1" guid="ed78c5e3-5e2c-4d8c-b64e-75767dcf0775" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="poly_ticketForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
		        <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
		          <msh:autoComplete parentId="poly_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
		        </msh:row>
        <msh:row guid="a4309021-f766-4a2b-aad0-1ddbf0b3c9d9">
          <msh:autoComplete vocName="vocServiceStream" property="vocPaymentType" label="Вид оплаты" horizontalFill="true" guid="e5ac1267-bc69-44b2-8aba-b7455ac064c4" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="vocServicePlace" label="Место обслуживания" horizontalFill="true" guid="18063077-15e8-4e4a-8679-ff79de589a72" />
        </msh:row>
        <msh:row guid="6d8642e8-756a-482f-a561-a9b474ef6c50">
          <msh:autoComplete vocName="vocReason" property="vocReason" label="Цель посещения" horizontalFill="true" guid="3632a2ed-6ecb-446f-8ae3-fe047f091076" />
          <msh:autoComplete vocName="vocVisitResult" property="vocVisitResult" label="Результат обращения" horizontalFill="true" guid="4346bd08-5fe2-4f01-9065-93a66cdfc1dd" />
        </msh:row>
        <msh:row guid="16f1e99-4017-4385-87c1-bf5895e2">
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" guid="ddc10e76-8ee913984f" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Диагноз код МКБ" fieldColSpan="3" horizontalFill="true" guid="9818fb43-33d1-4fe9-a0b4-2b04a9eee955" />
        </msh:row>
        <msh:row guid="7866a1f6-46fd-4df8-81dd-15391801c26d">
          <msh:autoComplete vocName="vocAcuityDiagnosis" property="vocIllnesType" label="Характер заболевания" horizontalFill="true" guid="da756e0d-c6c0-4870-85c6-65973d6183de" />
          <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" horizontalFill="true" guid="dade0d-c6c0-4870-85c6-6d3de" />
        </msh:row>
        <msh:row guid="4bd126b5-2316-42c4-bcb7-ccf5108b2c27">
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" guid="bf850705-5557-438e-b56e-33d59b1618e4" />
          <msh:autoComplete vocName="vocTrauma" property="vocTrauma" label="Травма" horizontalFill="true" guid="eedb1042-1861-426e-a0ec-6151c3933dd1" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="directHospital" label="Направлен на стационарное лечение" fieldColSpan="3"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
	        <msh:row>
	        	<msh:checkBox label="Беседа с родств." property="talk"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:row>
	        	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medServices" vocName="medServiceForSpec" colSpan="3"/>
	    </msh:row>
        <msh:row guid="1283d16a-e417-4add-acf5-5185dbb7737d">
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Сопутствующие заболевания" property="concomitantDiseases" colSpan="4" guid="1204d6c4-a3ff-44aa-a698-b99816d10337" />
        </msh:row>
        <msh:row guid="7dfb3b2c-407d-48f1-9e70-76cb3328f5f5">
        	<msh:textField property="uet" label="Усл.един.трудоем."/>
          <msh:autoComplete vocName="vocSpecLabel" property="specialLabel" label="Пометка" horizontalFill="true" guid="31f7b8de-d9e3-4f53-868a-1b76cf0930da" />
<%--           <msh:autoComplete vocName="omcRoadTrafficInjury" property="roadTrafficInjury" label="Справочник ДТП" horizontalFill="true" guid="317de-d9e3-4f53-868a-1b40da" /> --%>
        </msh:row>
        <msh:row guid="4392dbed-1d23-4f6f-8f78-7c9dc1a22238">
          <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm" guid="e3e0c68a-63b6-47ee-8d1b-cc6eceb2dc23">
            <msh:label property="statusName" label="Статус талона" guid="388bd2d8-3a20-4072-bd11-fa5afadcc7c2" />
          </msh:ifFormTypeAreViewOrEdit>
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
        <msh:separator label="Выдан талон" colSpan="4" guid="d9a7ec35-7893-48b3-aa08-f2e04d9a9400" />
        <msh:row>
        	<msh:textField label="Дата" property="dateCreate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время" property="timeCreate" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
	    <msh:row>
        	<msh:textField label="Пользователь" property="usernameCreate" viewOnlyField="true" />
        </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
      </msh:panel>
    </msh:form>

  </tiles:put>
</tiles:insert>