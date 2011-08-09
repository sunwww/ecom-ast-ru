<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregInspectionForm" guid="e20545-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Осмотр беременной">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregInspection" name="Изменить" roles="/Policy/Mis/Inspection/Pregnancy/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_pregInspection" name="Удалить" roles="/Policy/Mis/Inspection/Pregnancy/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Осмотр беременной
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_pregInspection.do" defaultField="transferDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="pregnancy" guid="9d908-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="medCase" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="5%,20%,5%,20%">
        <msh:row guid="afb83c59-1b86-4b0c-8fbe-1418bf4ffdae">
          <msh:textField label="Дата осмотра" property="inspectionDate" guid="b668b8a9-884a-4bfb-87e3-64b2d269715d" />
          <msh:textField label="Время осмотра" property="inspectionTime" guid="7020454c-4705-4189-9239-0e3c3af59ddb" />
        </msh:row>
        <msh:row guid="e7711953-38c7-4985-9f21-8e0f711121c8">
          <msh:separator label="Осмотр таза" colSpan="4" guid="81f3f857-49c0-4315-9fcb-64c11dfc5007" />
        </msh:row>
        <msh:row guid="084895aa-0eff-4dc8-8735-494de2b892bb">
          <msh:textField label="Таз: D.Sp" property="pelvisDSp" size="20" horizontalFill="true" guid="935a59b0-4e1b-4738-95a3-89b65c0e1e21" />
          <msh:textField label="D.Cr." property="pelvisDCr" guid="12242089-ac79-43ae-8453-2f4bfbb50409" horizontalFill="true" />
        </msh:row>
        <msh:row guid="1d36a52e-c194-4e78-bc52-acaf717fba88">
          <msh:textField label="D.Tr." property="pelvisDTr" guid="ced6c755-4635-4d5a-bed6-3170f69f50fd" horizontalFill="true" />
          <msh:textField label="C.ext." property="pelvisCExt" guid="b68774ff-3872-4d28-b5ca-bd60976cede8" horizontalFill="true" />
        </msh:row>
        <msh:row guid="d093e688-37e2-4f80-96f2-4ba3a62aff39">
          <msh:textField label="C.diag." property="pelvisCDiag" guid="c12001ef-6211-41b9-aebe-ff0ab212e585" horizontalFill="true" />
          <msh:textField label="C.later" property="pelvisCLater" guid="6de07d97-eca7-4be9-85b3-fd8838f21465" horizontalFill="true" />
        </msh:row>
        <msh:row guid="c7fcfb7a-f0bf-4b08-8d58-e522bc0951ae">
          <msh:textField label="Ромб Михаэлиса прод." property="rhombLongitudinal" guid="c6f21244-36de-4dfe-a659-fcd7aa209f5d" horizontalFill="true" />
          <msh:textField label="попер" property="rhombTransversal" guid="dfd01175-6d32-434d-a0fc-c53926b81b71" horizontalFill="true" />
        </msh:row>
        <msh:row guid="541b8dd7-55e8-41d7-a990-2028934a1a9a">
          <msh:separator label="Наружное акушерское исследование" colSpan="4" guid="f93908f0-d924-47a5-8096-d93abcf02f9a" />
        </msh:row>
        <msh:row guid="435878a5-d830-4066-bb60-d49600890c02">
          <msh:textField label="Индекс Соловьева" property="solovievIndex" guid="91f63c6a-b2c4-4721-8089-29899ada5a9e" horizontalFill="true" />
          <msh:textField label="Окружн. живота" property="abdomenCircle" guid="bbad1c82-45d9-4b7d-988e-adf492c323a8" horizontalFill="true" />
        </msh:row>
        <msh:row guid="eb24688d-9555-4153-91d3-f7bf599c0572">
          <msh:textField label="Высота дна матки над лоном" property="uterusHeight" guid="1933d5fc-ce2a-49eb-ac6a-b806d1ce89cd" horizontalFill="true" />
        </msh:row>
        <msh:row guid="3e368910-3a53-43f4-b81a-064483972f1f">
          <msh:autoComplete vocName="vocFetusLocation" label="Положение плода" property="fetusLocation" horizontalFill="true" fieldColSpan="3" guid="50908b23-8f34-4bf4-89ac-059d4145bc98" />
        </msh:row>
        <msh:row guid="aa431485-2d78-410d-8866-c7d6ef91cd34">
          <msh:autoComplete vocName="vocPreviusPart" label="Предлежащая часть" property="previusPart" horizontalFill="true" fieldColSpan="3" guid="ea0aab8b-3c30-4de0-bfdb-f4cf2312994a" />
        </msh:row>
        <msh:row guid="3af2abf7-3def-4e13-961e-94b16c473ba6">
          <msh:textField label="Высота ее стояния" property="previusPartHeight" guid="8699559d-5fdb-4f93-bd43-2f2cc37525f4" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b3f86e15-79a2-42d6-8f32-c7cf7b96696d">
          <msh:autoComplete vocName="vocFetusPalpitationPlace" label="Сердцебиение плода место" property="fetusPalpitationPlace" guid="2ec63a85-c6cb-4466-973b-858d8c5ee315" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="bge15-79a2-42d6-8f32-cd6696d">
          <msh:autoComplete vocName="vocFetusPalpitationNature" label="характер" property="fetusPalpitationNature" guid="22372d67-0d1f-41e6-9e08-6b09af36de73" horizontalFill="true" />
          <msh:textField label="число уд." property="fetusPalpitationRate" guid="3df7c98f-b736-4119-99a7-6e6b6660947d" horizontalFill="true" />
        </msh:row>
        <msh:row guid="1da67e20-9859-4c82-a18d-5016a897133d">
          <msh:separator label="Влагалищное исследование" colSpan="4" guid="918e2923-aacc-46b6-8abb-9766f019a262" />
        </msh:row>
        <msh:row guid="65cc90a7-acdc-49c8-bc3e-0ec22428a4ba">
          <msh:autoComplete vocName="vocPregnancyActivity" label="Родовая деятельность" property="pregnancyActivity" horizontalFill="true" fieldColSpan="3" guid="07a0e9f9-a8e9-421d-91c9-266d91c02651" />
        </msh:row>
        <msh:row guid="0203b7f2-9f47-4d70-868b-501e009103a2">
          <msh:autoComplete vocName="vocVaginalDischarge" label="Влагалищные выделения" property="vaginalDischarge" horizontalFill="true" fieldColSpan="3" guid="33046a67-02b2-4bf1-ae6f-d603b7126a1f" />
        </msh:row>
        <msh:row guid="1da67e20-9859-4c82016a897133d">
          <msh:separator label="Осмотр производил" colSpan="4" guid="918e2923-aacc-46b6-8abb-9766f019a262" />
        </msh:row>
        <msh:row guid="8ad01d61-a34a-437a-9136-816536bac876">
          <msh:autoComplete vocName="workFunction" property="workFunction" label="Специалист" guid="e1e74739-c36a-4b85-93f5-5b835900aa85" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregInspectionForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>

