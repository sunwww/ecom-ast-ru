<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_birthCaseForm" guid="9d27df65-9af0-4398-8a7d-57059cd29743">
      <msh:sideMenu title="Случай рождения" guid="58caa1eb-0a6c-4e93-9575-fde48488ebe5">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_birthCase" name="Изменить" roles="/Policy/Mis/MedCase/BirthCase/Edit" guid="c714bd8f-4fef-44dc-9d0b-cbaf455258dc" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_birthCase" name="Удалить" roles="/Policy/Mis/MedCase/BirthCase/Delete" guid="48146ed8-f1f6-431c-8d29-2ba385d21cb7" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="ae718b87-6274-4a66-97b9-6708062f581e">
        <msh:sideLink roles="/Policy/Mis/Certificate/Birth/Create" name="Свидетельство о рождении" params="id" action="/entityParentPrepareCreate-stac_birthCertificate" title="Добавить свидетельство о рождении" guid="c89c87df-4978-46d6-8f7e-ddbdf9fe5fca" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай рождения
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_birthCase.do" defaultField="childbirthDate" guid="63c31f7d-c99c-44f1-9236-aab59a65cc4f">
      <msh:hidden property="id" guid="bd0acac0-10f3-47a8-9b15-0d42a6369dbf" />
      <msh:hidden property="saveType" guid="ff2056d2-edb4-4b48-beda-cfb01a60b964" />
      <msh:hidden property="medCase" guid="24f60be5-6346-4790-924a-0727f665525a" />
      <msh:hidden property="patient" guid="2862908e-c425-420e-8d82-ad86522d7d22" />
      <msh:panel guid="f71a2cac-9676-4672-ac98-0e9e305cd9ea">
        <msh:separator label="Основные сведения" colSpan="" guid="be299385-b3b1-4c0c-80d6-7033ca9a9c5f" />
        <msh:row guid="2822bfda-b649-402e-b21d-23332f6f988b">
          <msh:textField property="childbirthDate" label="Дата родов" guid="623d03fd-f2ea-4da5-85b4-593a65996c1c" />
          <msh:textField property="childbirthTime" label="время" guid="76dd3e83-867c-4361-a103-db3d6f671d69" />
        </msh:row>
        <msh:row guid="c5cc0909-1c67-4e7f-81b7-4b88c1e320f8">
          <msh:autoComplete vocName="vocChildbirthPlace" property="childbirthPlace" label="Место родов" guid="ce9306eb-2a38-4c5e-8637-057a558583cd" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="4d6e91a4-158d-4ace-9f18-9126e2689287">
          <msh:textField property="childbirthNumber" label="Которые по счету роды" guid="66ac01c7-ac08-463b-98d0-e08a10dd1420" />
          <msh:textField property="pregnancyNumber" label="Которая по счету беременность" guid="e068ef0b-6f98-45b2-af26-75972df30974" />
        </msh:row>
        <msh:row guid="3b8d53ed-9f9a-4d50-aa14-5a7e238d1944">
          <msh:textField property="childNumber" label="Который по счету родившийся ребенок у матери" fieldColSpan="3" guid="2ffc8a2f-fa46-4cbb-85ce-4d4476a1669a" />
        </msh:row>
        <msh:row guid="12bbb3ce-b594-409f-82f0-632a66a1f984">
          <msh:autoComplete vocName="vocOrderAndQuantity" property="orderAndQuantity" label="Ребенок родился при:" fieldColSpan="3" horizontalFill="true" guid="a345210d-e673-4387-9c2a-e483551a19d6" />
        </msh:row>
        <msh:row guid="16613427-cf06-4ee9-a143-e902cb4b59b6">
          <msh:textField property="childBirthWeight" label="Масса (вес) при рождении, гр." guid="7d3d7358-0f6d-4316-b9ee-f136a393c6a3" />
          <msh:textField property="childBirthHeight" label="Рост ребенка при рождении, см." guid="0f93c468-c8d2-4ea6-baf7-a17c2c6684a3" />
        </msh:row>
        <msh:row guid="8b4184d6-6e37-4110-adc7-c8e84334c98e">
          <msh:textField label="Оценка по шкале Апгар, 1 мин." property="apgarScaleEstimate" guid="768180f3-3668-491e-bc3d-e96e5c765e3f" />
          <msh:textField label="Оценка по шкале Апгар, 5 мин." property="apgarScaleEstimateFive" guid="5edd7582-e4d0-4545-97c6-dbab95262ecd" />
        </msh:row>
        <msh:row guid="22b1c6e6-cd9d-4408-9b73-07174f0742dd">
          <ecom:oneToManyOneAutocomplete property="livebirthCriterion" vocName="vocLivebirthCriterion" label="Критерии живорождения:" colSpan="4" guid="6d2ab56a-5c41-4df8-9d8d-fbd589498103" />
        </msh:row>
        <msh:row guid="22b1c6e6-cd9d-4408-9b73-07174f0742dd">
          <ecom:oneToManyOneAutocomplete property="pregnancyMedFactors" vocName="vocPregnancyMedFactor" label="Медицинские факторы настоящей беременности" colSpan="4" guid="8e2f1d8d-914c-4c49-8ba0-d66eb11ec476" />
        </msh:row>
        <msh:row guid="22b1c6e6-cd9d-4408-9b73-07174f0742dd">
          <ecom:oneToManyOneAutocomplete property="pregnancyRiskFactors" vocName="vocPregnancyRiskFactor" label="Прочие факторы риска во время беременности" colSpan="4" guid="cf1c234d-38d6-41c4-9b4a-757f743fb22c" />
        </msh:row>
        <msh:row guid="22b1c6e6-cd9d-4408-9b73-07174f0742dd">
          <ecom:oneToManyOneAutocomplete property="childbirthComplications" vocName="vocChildbirthComplication" label="Осложнения родов" colSpan="4" guid="7bf797e2-903d-4fd6-97db-af98122cf296" />
        </msh:row>
        <msh:row guid="22b1c6e6-cd9d-4408-9b73-07174f0742dd">
          <ecom:oneToManyOneAutocomplete property="obstetricProcedures" vocName="vocObstetricProcedure" label="Акушерские процедуры" colSpan="4" guid="7bfde3b4-0929-4538-af87-c8c1d257977a" />
        </msh:row>
        <msh:row guid="22b1c6e6-cd9d-4408-9b73-07174f0742dd">
          <ecom:oneToManyOneAutocomplete property="newbornComplications" vocName="vocNewbornComplication" label="Осложнения новорожденного" colSpan="4" guid="144e1b65-4c92-4b12-a14f-2d301082d428" />
        </msh:row>
        <msh:row guid="22b1c6e6-cd9d-4408-9b73-07174f0742dd">
          <ecom:oneToManyOneAutocomplete property="congenitalAnomalies" vocName="vocCongenitalAnomaly" label="Врожденные аномалии развития" colSpan="4" guid="9c80094b-9a80-43b7-9e01-ab1d213c5bbf" />
        </msh:row>
        <msh:row guid="12bbb3ce-b594-409f-82f0-632a66a1f984">
          <msh:autoComplete vocName="workFunction" property="birthWitness" label="Врач" fieldColSpan="3" horizontalFill="true" guid="d9b80459-4933-406a-a20d-f87cc28dee35" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="8515167d-96cf-48ad-8294-1f3f60a105ec" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="stac_birthCaseForm" guid="63128f29-44b9-495b-bff6-ed66537d2e57">
      <msh:ifInRole roles="/Policy/Mis/Certificate/Birth/View" guid="395ec377-9a3a-4345-8d86-f0e6c47bcfe5">
        <msh:section title="Свидетельства о рождении" guid="3ef4d927-bbe1-4994-9a9c-28f6a28e7744">
          <ecom:parentEntityListAll formName="stac_birthCertificateForm" attribute="birthCertificate" guid="001b8d93-dc3b-4eff-a27f-7809a7835e56" />
          <msh:table name="birthCertificate" action="entityParentView-stac_birthCertificate.do" idField="id" guid="241cc9af-5968-4d4c-8dcc-ca467ae62eb8">
            <msh:tableColumn columnName="Дата выдачи" property="dateIssue" cssClass="preCell" guid="d590de0b-8500-4600-9e7e-9c643934d45d" />
            <msh:tableColumn columnName="Серия" property="series" cssClass="preCell" guid="6406b803-e787-4532-826e-252f5470322f" />
            <msh:tableColumn columnName="Номер" property="number" cssClass="preCell" guid="387a7673-2ca8-4329-a850-b2e78cd188a3" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_birthCaseForm" guid="6602cbc1-6a25-4fe2-a951-b297a9d9b990" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
</tiles:insert>

