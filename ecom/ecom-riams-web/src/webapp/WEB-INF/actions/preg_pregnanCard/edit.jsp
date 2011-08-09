<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregnanCardForm" guid="e20545-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Беременность">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregnanCard" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_pregnanCard" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Беременность
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_pregnanCard.do" defaultField="transferDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="pregnancy" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:row guid="d5e2c59e-1885-4cc3-b3cc-ddd4ddd06c1b">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="ef50b62e-4edf-4797-9626-d616e6608fd0">
          <msh:autoComplete property="consultation" label="Консультация" vocName="mainLpu" guid="0ed67568-c384-4346-a026-a1bd68456bc7" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row guid="d36c6491-849e-4985-8b52-e392924d1c57">
          <msh:separator label="Перенесенные общие и гинекологические заболевания и операции" colSpan="6" guid="dcdf4c0a-637c-4d9d-83a1-ffedac316191" />
        </msh:row>
        <msh:row guid="97040ec7-1eb3-4253-b68b-2cfdeddfd896">
          <msh:textArea property="transmitDiseases" hideLabel="true" labelColSpan="1" guid="04a73163-bba6-4f90-9556-b9c5388e64a4" fieldColSpan="5" rows="2" />
        </msh:row>
        <msh:row guid="55581ac1-add4-4ba9-b47e-cfc2c18b9e8e">
          <msh:separator label="Особенности течения прежних беременностей, родов, послеродового периода" colSpan="6" guid="2b3dfbd3-15ee-488d-a00f-dfee5f49a9c0" />
        </msh:row>
        <msh:row guid="2fea97ec-5690-47e4-9201-b13b5c80759b">
          <msh:textArea property="previousPregnancies" hideLabel="true" labelColSpan="1" guid="b0ed6436-154f-4d02-8b58-44f5b9480457" rows="2" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="d0d1a483-4218-47b5-86c1-22a5798099df">
          <msh:textField property="lastMensis" label="Последняя менструация" guid="a984fd7d-f795-49fb-b76e-3a27e42b57e5" labelColSpan="2" />
        </msh:row>
        <msh:row guid="c058c225-6b48-435d-82f6-2d304aeecf2c">
          <msh:textField property="shortPregnancyAmount" label="Кол-во коротких беременностей" guid="ff1a2b10-36aa-4591-91a7-0c715fcb7d2a" labelColSpan="2" />
          <msh:textField property="childbirthAmount" label="кол-во родов" guid="a36b4238-6c48-4be7-b62c-0440fae5131d" />
        </msh:row>
        <msh:row guid="1911f4e7-ac58-48f3-8c16-c23d5ea63512">
          <msh:textField property="firstVisitDate" label="Первое посещение консультации при данной беременности" guid="6992d50a-6444-4b68-80ee-c2a54d23b18e" labelColSpan="3" />
        </msh:row>
        <msh:row guid="f8008ada-30f8-4618-98ae-a8d5b2e4da2c">
          <msh:textField property="visitsAmount" label="Всего посетила раз" labelColSpan="3" guid="c4d36cb7-f54e-4c06-8182-31980747f2fe" />
        </msh:row>
        <msh:row guid="12e64ece-7727-43ea-9ce1-0c7a0d2c9c25">
          <msh:separator label="Особенности течения данной беременности, состояние беременной" colSpan="6" guid="0368ea3f-4a14-4d1b-913a-245493f74d88" />
        </msh:row>
        <msh:row guid="6afa0013-d949-4928-806f-1ec70721af6a">
          <msh:textArea labelColSpan="1" property="pregnancyFeatures" hideLabel="true" guid="dccbda1f-0fcd-4402-b61b-c19429b93600" fieldColSpan="4" rows="8" horizontalFill="true" />
        </msh:row>
        <msh:row guid="5fa1c8ea-d80f-4fb5-9c38-27710200b72e">
          <msh:textField label="Моча дата:" passwordEnabled="false" hideLabel="false" property="urineAnalysisDate" viewOnlyField="false" guid="852a1279-7081-4f4d-9483-6bdb8da7b4cf" horizontalFill="false" labelColSpan="2" />
          <msh:textField property="urineAnalysis" label="результат" guid="f2227aad-c8a9-49b8-853c-2014652fbdb7" />
        </msh:row>
        <msh:row guid="f514ab9c-fd8b-47de-b075-9175c4da0f47">
          <msh:textField property="bloodAnalysisDate" label="Кровь дата" guid="27f35aa9-88a6-4d7a-aef0-dfdd3792231a" labelColSpan="2" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="bloodAnalysis" viewOnlyField="false" guid="faab4826-0a29-4e01-bc5b-f5b329af773f" horizontalFill="false" label="результат" />
        </msh:row>
        <msh:row guid="71d21cac-298b-42ad-ac41-ce1eed86b27e">
          <msh:textField property="trainingAmount" label="Кол-во занятий психопрофилактич. подготовки" guid="eb81c26e-b554-454d-868d-dd7db8b3851e" labelColSpan="3" />
        </msh:row>
        <msh:row guid="696dd38d-c329-43b9-b44b-daef4b1f50d9">
          <msh:textField property="lastTrainingDate" label="Последние занятия" guid="24a85991-6046-47d7-814c-611bef11272d" labelColSpan="3" />
        </msh:row>
        <msh:row guid="80267a9a-6ecd-4137-9e13-6a510007eca1">
          <msh:textField property="supposedBithPeriod" label="Предполагаемый срок родов" guid="de524868-f37c-4cd5-bb5f-e584b3cc661e" labelColSpan="2" />
        </msh:row>
        <msh:row guid="063e513b-a61b-465b-b78f-bb9484b9cdc4">
          <msh:separator label="Особые замечания" colSpan="6" guid="6303b712-cbd1-4e37-912e-34401c2b117f" />
        </msh:row>
        <msh:row guid="80362854-5e0a-4d42-9177-4dd0f8243559">
          <msh:textArea property="notes" hideLabel="true" guid="1489394e-fde0-4d7f-9db0-7aa58e613e52" rows="5" fieldColSpan="4" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregnanCardForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>

