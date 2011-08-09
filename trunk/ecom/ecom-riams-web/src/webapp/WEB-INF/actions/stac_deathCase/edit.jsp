<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_deathCaseForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Случай смерти">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_deathCase" name="Изменить" roles="/Policy/Mis/MedCase/DeathCase/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_deathCase" name="Удалить" roles="/Policy/Mis/MedCase/DeathCase/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc9" title="Добавить">
        <msh:sideLink roles="/Policy/Mis/MedCase/DeathCase/Reason/Create" name="Причину смерти" params="id" action="/entityParentPrepareCreate-stac_deathReason" title="Добавить причину смерти" guid="11cc057f-b309-4193-9d22-199373cfd28d" />
        <msh:sideLink roles="/Policy/Mis/Certificate/Death/Create" name="Свидетельство о смерти" params="id" action="/entityParentPrepareCreate-stac_deathCertificate" title="Добавить свидетельство о смерти" guid="11cc0-b309-4193-9d22-199373cfd28d" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай смерти
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_deathCase.do" defaultField="deathDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="5344b5d6-fefa-45e5-9221-3e6060a89d25" />
      <msh:hidden property="medCase" guid="710eb92b-fc3f-4390-b32df6837280" />
      <msh:hidden property="patient" guid="710eb92b-fc3f-4b44-9390-b32df6837280" />
      <msh:hidden property="deathPlaceAddress" guid="710eb3f-4b44-9390-b32df6837280" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:separator label="Основные сведения" colSpan="" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
          <msh:textField property="deathDate" label="Дата смерти" guid="a7996448-21ee-4647-b7bb-0d8501b9c2c5" />
          <msh:textField property="deathTime" label="время" guid="11dd2d9a-3b35-4620-be61-eaede78e8a7a" />
        </msh:row>
        <msh:row guid="f2-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="vocDeathPlace" property="deathPlace" label="Место смерти" fieldColSpan="3" horizontalFill="true" guid="109f7264-23b216c" />
        </msh:row>
        <msh:row guid="1da6d4c4-abb4-4a9b-bee3-a4618fa4ac95">
          <msh:autoComplete showId="false" vocName="vocDeathReason" hideLabel="false" property="deathReason" viewOnlyField="false" label="Смерть произошла от:" guid="9eb66c69-6860-4464-b671-48494ec2dc85" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="e034c8ae-b8b7-40f8-82a7-eac5979cc85e">
          <td colspan="1" title="Адрес (deathPlaceAddressField)" class="label">
            <label id="deathPlaceAddressFieldLabel" for="deathPlaceAddressField">Адрес места смерти:</label>
          </td>
          <td colspan="3" class="deathPlaceAddressField">
            <input title="АдресNoneField" class=" horizontalFill" id="deathPlaceAddressField" name="deathPlaceAddressField" size="10" value="Адрес... " type="text" />
          </td>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/DeathCase/Child">
        <msh:separator label="Для детей, умерших в возрасте до 1 года" colSpan="" guid="d4313623-45ca-43cc-826d-bc1b6" />
        <msh:row guid="d62f29-4e95-42a5-9063-9255a8">
          <msh:textField property="birthPlace" label="Место рождения" guid="80346725-7478-4afa-bc1d-f6e331bbff19" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="d59-4e95-42a5-9063-988">
          <msh:textField property="birthPlaceAdress" label="Адрес места рождения" guid="af4416bc-eff1-438c-b393-371c35ce6c98" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="f244ab-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="patient" property="mother" label="Мать" fieldColSpan="3" horizontalFill="true" guid="109f7264-23b2-42cd90747816c" />
        </msh:row>
        <msh:separator label="Для детей, умерших в возрасте от 6 дней до 1 месяца" colSpan="" guid="d43136ca-43cc-826d-bc1b6" />
        <msh:row guid="f244aba5-68fb-4ccc480cca147">
          <msh:autoComplete vocName="vocIsPrematurity" property="isPrematurity" label="Доношенность" fieldColSpan="3" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" />
        </msh:row>
        <msh:separator label="Для детей, умерших в возрасте от 6 дней до 1 года" colSpan="" guid="d4ca-43cc-826d-bc1b6" />
        <msh:row guid="21b4ac48-1773-410d-b85f-537680420aa4">
          <msh:textField property="babyNumber" label="Какой ребенок по счету у матери" guid="bb7b87a8-c542-47ef-93b6-91106abf9f19" labelColSpan="2" horizontalFill="true" />
        </msh:row>
        <msh:row guid="a3509d1f-9324-4997-a7c3-6ca8f12a9347">
          <msh:textField property="birthWeight" guid="c04ab410-42df-4f5b-b365-b4acf17a2616" labelColSpan="2" label="Масса (вес) при рождении, гр." />
        </msh:row>
        </msh:ifInRole>
        <msh:separator label="В случае смерти от несчастного случая, отравления или травмы" colSpan="" guid="d4313623-45ca-43cc-821b6" />
        <msh:row guid="9b781b-afb9aedfb7a8">
          <msh:textField label="Дата (травмы) отравления" property="accidentDate" guid="fff1dd9f3fad" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="f2aba5-68fb-4ccc-9982-7b4480cmca147">
          <msh:autoComplete vocName="vocAccidentSubType" property="accidentSubType" label="Вид травмы" fieldColSpan="3" horizontalFill="true" guid="1064-23b2-42c0-ba47-65d90747816c" />
        </msh:row>
        <msh:row guid="9b781235-66ad-4f9d-991b-afb9aedfb7a8">
          <msh:textField label="Место" property="accidentPlace" guid="fff1dd1d-b7a5-4fe2-899b-3292ec9f3fad" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="a3509d1f-9324-49ca8f12a9347">
          <msh:textField property="accidentCircumstance" label="Обстоятельства" guid="f8f5c912-00b8-4fd8-87b9-abe417212d78" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Причина смерти установлена" colSpan="" guid="d43132a-43c" />
        <msh:row guid="72adfc17c0-8eb4-a23ee9e84ed8">
          <msh:autoComplete vocName="vocDeathWitnessFunction" property="deathWitnessFunction" label="Кем установлена" fieldColSpan="3" horizontalFill="true" guid="fa-6afe-4c26-82f3-50532049a8bc" />
        </msh:row>
        <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
          <msh:autoComplete vocName="workFunction" property="deathWitness" label="Врач" fieldColSpan="3" horizontalFill="true" guid="968469ce-dd95-40f4-af14-deef6cd3e4f3" viewAction="entitySubclassView-work_workFunction.do" />
        </msh:row>
        <msh:row guid="f221de2e-c742-4c2a-af1c-135daf9d2d7f">
          <ecom:oneToManyOneAutocomplete property="deathEvidence" vocName="vocDeathEvidence" label="На основании" colSpan="4" guid="d9257246-a9f6-4ecc-8d3d-ff10abb2b64c" />
        </msh:row>
        <msh:row guid="f2a5-68fb-4ccc-9982-7b4447">
          <msh:autoComplete vocName="vocAfterPregnance" property="afterPregnance" label="Умерла после окончания беременности" fieldColSpan="2" horizontalFill="true" guid="10964-23b2-42c0-ba47-6547816c" labelColSpan="2" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_deathCaseForm" guid="48eb9700-d07d-4115-a476-a5a5e">
      <msh:ifInRole roles="/Policy/Mis/MedCase/DeathCase/Reason/View" guid="932601e0-0d99-4b63-8f44-2466f6e91c0f">
        <msh:section title="Причины смерти" guid="1f21294-8ea0-4b66-a0f3-62713c1">
          <ecom:parentEntityListAll formName="stac_deathReasonForm" attribute="deathReasons" guid="30260c-7369-4ec7-a67c-882abcf" />
          <msh:table hideTitle="true" idField="id" name="deathReasons" action="entityParentView-stac_deathReason.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
            <msh:tableColumn columnName="Тип" property="reasonTypeInfo" guid="0694f7-ed40-4ebf-a274-1efd6901cfe4" />
            <msh:tableColumn columnName="Причина" property="reason" guid="0694fd40-4ebf-a274-1efd6901cfe4" />
            <msh:tableColumn columnName="Код МКБ10" property="idc10Info" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Certificate/Death/View" guid="b0ceb3e4-a6a2-41fa-be6b-ea222196a33d">
        <msh:section title="Свидетельства о смерти" guid="1f214-8ea0-4b66-a0f3-62713c1">
          <ecom:parentEntityListAll formName="stac_deathCertificateForm" attribute="deathCertificate" guid="302c-7369-4ec7-a67c-882abcf" />
          <msh:table name="deathCertificate" action="entityParentView-stac_deathCertificate.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d916">
            <msh:tableColumn columnName="Дата выдачи" property="dateIssue" guid="b85fe4-b1cb-4320-aa85-014d26" cssClass="preCell" />
            <msh:tableColumn columnName="Серия" property="series" guid="b5fe4-b1cb-4320-aa85-014d26" cssClass="preCell" />
            <msh:tableColumn columnName="Номер" property="number" guid="bfe4-b1cb-4320-aa85-014d26" cssClass="preCell" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <tags:addressCity form="stac_deathCaseForm" name="deathPlaceAddress" addressField="deathPlaceAddressField" />
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_deathCaseForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
</tiles:insert>

