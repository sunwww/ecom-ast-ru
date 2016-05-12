<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_statisticStubNewForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Стат.карты">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-stac_statisticStubNew" 
        	name="Изменить" 
        	roles="/Policy/Config/Hospital/StatisticStubNew/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+2" confirm="Создать следующий номер стат. карты за новый год по ЛПУ?" 
        	params="" 
        	action="/entityPrepareCreate-stac_statisticStubNew" name="Создать" 
        	roles="/Policy/Config/Hospital/StatisticStubNew/Create" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" 
        	action="/entityDelete-stac_statisticStubNew" name="Удалить" 
        	roles="/Policy/Config/Hospital/StatisticStubNew/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="ad80d37d-5a0b-44e3-a4ae-3df85de3d1c3">
        <msh:sideLink key="ALT+3" params="" action="/entityList-stac_statisticStubNew" name="⇧Cписок годов" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" guid="f6a4b395-ccee-4db6-aad7-9bc15aa2f7b8" title="Перейти к списку случаев лечения в отделении" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Следующий номер стат карты по году
    	  -->
    <msh:form action="/entitySave-stac_statisticStubNew.do" defaultField="transferDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="20% 30%">
        <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
          <msh:autoComplete vocName="lpu" property="lpu" label="Лечебное учреждение" fieldColSpan="6" 
          	horizontalFill="true" guid="968469ce-dd95-40f4-ef6cd3e4f" size="30" />
        </msh:row>
        
        <msh:row >
          <msh:autoComplete vocName="vocPigeonHole" property="pigeonHole" label="Приемник" fieldColSpan="6" 
          	horizontalFill="true" guid="968469ce-dd95-40f4-ef6cd3e4f" size="30" />
        </msh:row>
        
        <msh:row guid="a3509d1f-9324-4997-a7c3-6ca8f12a9347">
          <msh:textField property="year" label="Год" guid="f8f5c912-00b8-4fd8-87b9-abe417212d78" />
          <msh:textField property="code" label="Номер" guid="c04ab410-42df-4f5b-b365-b4acf17a2616" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isPlan"/>
        	<msh:checkBox property="isEmergency"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="stac_statisticStubNewForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>

</tiles:insert>

