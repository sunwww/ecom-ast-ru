<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_deathReasonForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Причина смерти">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_deathReason" name="Изменить" roles="/Policy/Mis/MedCase/DeathCase/Reason/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_deathReason" name="Удалить" roles="/Policy/Mis/MedCase/DeathCase/Reason/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай смерти
    	  -->
    <msh:form action="/entityParentSaveGoParentView-stac_deathReason.do" defaultField="reasonType" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="5344b5d6-fefa-45e5-9221-3e6060a89d25" />
      <msh:hidden property="deathCase" guid="710eb92b-fc3f-4b47280" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:separator label="Основные сведения" colSpan="" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
        <msh:row guid="f244ab-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="vocDeathReasonType" property="reasonType" label="Тип" fieldColSpan="3" horizontalFill="true" guid="109f7264-23b2-42cd90747816c" />
        </msh:row>
        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
          <msh:textField property="reason" label="Причина" guid="a7996448-21ee-4647-b7bb-0d8501b9c2c5" horizontalFill="true" size="100" />
        </msh:row>
        <msh:row guid="f2-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true" guid="109f7264-23b216c" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_deathReasonForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
</tiles:insert>

