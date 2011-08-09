<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_newBornInformationForm" guid="e20545-4285-a21c-3bbh69efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Беременность">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_newBornInformation" name="Изменить" roles="/Policy/Mis/Pregnancy/NewBornInformation/Edit" guid="5a11-7629-4458-b5a5-e55114" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_newBornInformation" name="Удалить" roles="/Policy/Mis/Pregnancy/NewBornInformation/Delete" guid="770b6-c131-47f4-b8a0-2600450f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_newBornInformation.do" defaultField="fillingDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="confinedExchangeCard" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:row guid="d5e2c59e-1885-4cc3-b3cc-ddd4ddd06c1b">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="d8s3-428-47b5-86c1-2d9df">
          <msh:textField property="fillingDate" label="Дата" guid="a9fd7d-f795-49fb-b76e-3adge5" />
        </msh:row>
        <msh:row guid="d8s3-42b5-86c1-2d9df">
          <msh:textField property="birthCondition" label="Состояние при рождении" guid="a9fdddd7d-f795-49fb-b76e-3adge5" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row guid="d8s3-5-86c1-2d9df">
          <msh:textField property="dischargeCondition" label="Состояние при выписке" guid="a9fd7d-b76e-3adge5" horizontalFill="true" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="d8s3-5-86c1-2d9df">
          <msh:textField property="birthWeight" label="Вес при рождении" guid="af7d-b76e-3adge5" />
          <msh:textField property="birthHeight" label="Рост при рождении" guid="afff7d-b76e-3adge5" />
        </msh:row>
        <msh:row guid="d8s3-5-86c1-2d9df">
          <msh:textField property="dischargeWeight" label="Вес при выписке" guid="a9fd7d-b76e-3s5" />
        </msh:row>
        <msh:row guid="1kce-43ea-9ce1-0f25">
          <msh:separator label="Мероприятия, проведенные в отношении новорожденного" colSpan="6" guid="03df-4a14-2d88" />
        </msh:row>
        <msh:row guid="dgec-5690-9201-bdf9b">
          <msh:checkBox property="vcgVaccination" label="Противотуберкулезная вакцинация" guid="b3a5088d-74d4-47b0-834d-123fbdda1c1a" />
          <msh:textField property="vcgEstop" label="Причины отказа" guid="a9fhhhd7d-bh76e-3s5" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row guid="dgggg7ec-5690-47e4-9201-bdf9b">
          <msh:textArea property="otherActions" label="Другие мероприятия" guid="bfggfhf36-154f-4d02-4bf4s57" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="dgh8g7ec-5690-47e4-9201-bdf9b">
          <msh:textArea property="postNatalFeatures" label="Особенности течения послеродового периода" guid="bf85558436-154f-4d02-4bf4s57" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row guid="dgg88g7ec-5690-47e4-9201-bdf9b">
          <msh:textArea property="notes" label="Особые замечания" guid="bf85ggfhf36-154f-4d02-4bf4s57" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_newBornInformationForm" guid="fb4g3e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>

