<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_confinedCardForm" guid="er5-4285-a21c-3efc">
      <msh:sideMenu guid="9ew53-1f35-4c18-b99d-ew60c9" title="Обменная карта">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_confinedCard" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" guid="5w5-7629-4458-b5a5-et" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_confinedCard" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" guid="7w-c131-47f4-b8a0-2w0f" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="ac479220-7834-48af-a281-3fadc8037f52">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBornInformation" name="Сведения о новорожденном" roles="/Policy/Mis/Pregnancy/NewBornInformation/Create" guid="7w-1-47f4-b8a0-2w0f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Беременность
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_confinedCard.do" defaultField="birthDate" guid="bf54f648-5ec9-4a95-a796-80ff0021fd40">
      <msh:hidden property="id" guid="dk-e434-45a5-90f0-b0kd00ec6" />
      <msh:hidden property="pregnancy" guid="9k88-e051-4d0a-8da6-3k93" />
      <msh:hidden property="saveType" guid="bh07-c944-4587-a963-ah3caf" />
      <msh:panel guid="d0310-bf53-4ce1-9dd5-0hec01">
        <msh:row guid="dfe-1885-4cc3-b3cc-dfc1b">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="d62e-4edf-4797-9626-dafd0">
          <msh:autoComplete property="hospital" label="Наименование" vocName="mainLpu" guid="0eg8-c384-4346-a026-a1fbc7" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row guid="d8s3-4218-47b5-86c1-2d9df">
          <msh:textField property="hospitalizationDate" label="Дата поступления" guid="a9d7d-f795-49fb-b76e-3age5" />
          <msh:textField property="birthDate" label="Дата родов" guid="a9g7d-f795-49fb-b76e-3dge5" />
        </msh:row>
        <msh:row guid="fcee0044-d2d7-4d30-a50c-6d4c56d7561e">
          <msh:textArea property="birthFeatures" label="Особенности течения родов" rows="8" fieldColSpan="4" guid="aacbccd2-b044-4b6c-97d7-fada290cbe7a" />
        </msh:row>
        <msh:row guid="07d7cd2f-75f4-4f7f-a4ff-477cedbe0e12">
          <msh:textArea property="birthOperations" label="Оперативные пособия в родах" rows="5" fieldColSpan="4" guid="b196dbd4-0e16-474f-bfee-a1cc679e9d69" />
        </msh:row>
        <msh:row guid="d3f2638f-efdf-4d96-aff5-2ed1cdfddcbb">
          <msh:textArea property="anesthetization" label="Обезболивание" rows="5" fieldColSpan="4" guid="aa22d96e-db10-41a0-8a3c-fc4ca1c612c1" />
        </msh:row>
        <msh:row guid="a543b3cf-16f6-47dd-b003-359512199ce5">
          <msh:textArea property="postNatalFeatures" label="Течение послеродового периода" rows="2" fieldColSpan="4" guid="6e384f61-10d2-43a2-9e9a-939af532ff22" />
        </msh:row>
        <msh:row guid="c7b75aaa-acf1-43f5-97de-d099044c2a94">
          <msh:textField property="birthDischargeDays" label="Выписана на " guid="a9dd7d-f795-b76e-3age5" labelColSpan="2" />
          <td>день после родов</td>
        </msh:row>
        <msh:row guid="dg7ec-5690-47e4-9201-bdf9b">
          <msh:textArea property="dischargeMotherCondition" label="Состояние матери при выписке" guid="bffhf36-154f-4d02-4bf4s57" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="dgec-5690-9201-bdf9b">
          <msh:checkBox property="patronageNeeded" label="Нуждается в патронаже" guid="51246486-39eb-44ce-af41-dc50fa7b6758" />
        </msh:row>
        <msh:row guid="2f7ec-5690-47e4-9201-bs9b">
          <msh:textArea property="patronageStatement" label="Показания к патронажу" guid="bfghf36-15g4f-4dg02-4b4s57" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="a83b4b06-eb31-4358-b7b6-449a86a5e572">
          <msh:textArea property="notes" label="Особые замечания" rows="5" fieldColSpan="4" guid="7481ba23-a6fc-4f4c-a8ca-db6bd4479936" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="6yec-9b93-4faf-b729-8r54f" />
      </msh:panel>
    </msh:form>
      <msh:ifFormTypeIsView formName="preg_confinedCardForm" guid="8e2a813e-0d2d-42e7-b7ef-9035faabedb1">
	<msh:ifInRole roles="/Policy/Mis/Pregnancy/NewBornInformation/View" guid="60f0fe00-953c-445f-bb5c-00ba80e8a8d9">
        <msh:section title="Сведения о новорожденном" guid="62805b03-cd25-40d5-8b68-faf395c8ab74">
          <ecom:parentEntityListAll formName="preg_newBornInformationForm" attribute="newBorns" guid="988c6488-2beb-4d75-b5c9-6bb2811ba24c" />
          <msh:table hideTitle="false" idField="id" name="newBorns" action="entityParentView-preg_newBornInformation.do" guid="acf55fc4-8beb-4786-9d87-de39d712dcc3">
            <msh:tableColumn columnName="Дата заполнения" property="fillingDate" guid="dad36-c475-4178-bdc6-7s8c04" />
            <msh:tableColumn columnName="Вес при рождении" identificator="false" property="birthWeight" guid="f1c6a51e-86f4-45f5-85c5-0a3742b7d35c" />
            <msh:tableColumn columnName="Рост при рождении" identificator="false" property="birthHeight" guid="e43105dd-b331-476c-ab20-d53b564df230" />
            <msh:tableColumn columnName="Состояние при рождении" identificator="false" property="birthCondition" guid="3438126d-3836-4339-a333-a6c758964295" />
            <msh:tableColumn columnName="Вес при выписке" identificator="false" property="dischargeWeight" guid="d1de6208-fc4a-48ac-ba41-8ce0b77ebdca" />
            <msh:tableColumn columnName="Состояние при выписке" identificator="false" property="dischargeCondition" guid="91cb9ead-59bb-4c78-82af-1ed1b2ff5156" />
          </msh:table>
        </msh:section>
    </msh:ifInRole>
      </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_confinedCardForm" guid="ft1c-1ba9-4e61-8632-ae1c" />
  </tiles:put>
</tiles:insert>

