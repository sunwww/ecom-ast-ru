<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - МИАЦ. Вакцинации
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-vac_vaccination.do" defaultField="executeDate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="medCase" />
      <msh:hidden property="patient" guid="7fa5a00f-93fd-4d7a-a25b-9bb702c96f82" />
      <msh:panel guid="panel" colsWidth="5% 20% 10%">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="executeDate" label="Дата исполнения" guid="b765f6f6-abdd-4244-909e-b2d7899059e5" />
          <msh:textField property="executeTime" label="Время исполнения" guid="63874968-1e2c-4797-b5c0-12d899eb3549" />
        </msh:row>
        <msh:row guid="5b33447e-2d3b-4e3d-8329-2030782492c8">
          <msh:autoComplete vocName="vaccine" property="vaccine" label="Вакцина" horizontalFill="true" fieldColSpan="3" guid="af259891-2bfc263c09b304" />
        </msh:row>
        <msh:row guid="29fee7f1-fcec-4615-9baf-ec67954145cc">
          <msh:autoComplete vocName="vocVaccinationMaterial" property="material" parentAutocomplete="vaccine" label="Вакцинный материал" horizontalFill="true" fieldColSpan="3" guid="af259891-2b21-4c83-a5d9-fc263c09b304" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocVaccinationReaction" property="vaccinationReaction" label="Реакция вакцинация" horizontalFill="true" fieldColSpan="3" guid="af259891-2b21-4c83-a5d9-fc263c09b304" />
        </msh:row>
        <msh:row guid="7284598c-0f92-4136-a9ee-a418a5e845fa">
          <msh:textField property="dose" label="Доза" guid="26df1de7-4d5b-4ef3-a1d0-8521a33b8078" />
          <msh:textField property="series" label="Серия" guid="7c96b972-8de8-4349-bad5-c6d347eef3bd" />
        </msh:row>
        <msh:textField property="controlNumber" label="Контрольный номер" guid="bd065f79-c627-40f3-b240-48c17d7fdf69" />
        <msh:textField property="expirationDate" label="Дата годности" guid="98105a65-d9ef-4e24-881c-1e290cd602b4" />
        <msh:row guid="817b980b-4955-4bd8-8ca3-44a21140dd4b">
          <msh:autoComplete vocName="vocVaccinationPhase" property="phase" label="Фаза вакцинации" guid="cc3a4d16-c290-4e14-848a-1e0cca040ce6" />
          <msh:textField property="nextDate" label="Дата следующей вакцинации" guid="f4af12ae-590b-45cc-b4c1-b2fde8ee9dbd" />
        </msh:row>
        <msh:row guid="817b9hn880b-4955-4bd8-8ca3-44a21140dd4b">
          <msh:autoComplete vocName="vocVaccinationMethod" property="method" label="Метод вакцинации" horizontalFill="true" fieldColSpan="3" guid="394da423-e4da-45f7-b090-0e5aabbca2a1" />
        </msh:row>
        <msh:row guid="d79b8a97-7f02-4bac-81ba-9cb3d2826512">
          <msh:autoComplete vocName="vocVaccinationReasonType" property="reasonType" label="Причина вакцинации" horizontalFill="true" fieldColSpan="3" guid="513ee73c-c5a6-46d7-b18b-7c6f8e0e65b5" />
        </msh:row>
        <msh:row guid="92b82974-0424-4656-b2af-cb2410fe6802">
          <msh:autoComplete property="permitWorker" label="Разрешил" vocName="workFunction" guid="81eb5e69-952c-4f33-a1c4-6a9b85655076" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="92b8h82974-0424-4656-b2af-cb2410fe6802">
          <msh:autoComplete property="executeWorker" label="Исполнитель" vocName="workFunction" guid="c77e7d32-4d54-452c-92f7-aa720c80b035" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="vac_vaccinationForm" guid="8c94mn403e-3dda-408e-85f8-78081f">
      <msh:ifInRole roles="/Policy/Mis/Vaccination/VaccinationEstop/View" guid="c8f889eb-4694-4951-9550-570cd73a61c6">
        <msh:section guid="b67c5be3-5330-4589-8da8-788n8453a" title="Медотводы">
          <ecom:parentEntityListAll formName="vac_vaccinationEstopForm" attribute="estop" guid="31a2f8c507-4d9b-4ddc-99f5-5e3a07b48447" />
          <msh:table hideTitle="false" idField="id" name="estop" action="entityParentView-vac_vaccinationEstop.do" guid="6ca52aa435-03a5-43de-8754-3fa4d54ede17">
            <msh:tableColumn columnName="Дата медотвода" property="estopDate" guid="da45lo8036-c475-4178-bdc6-7a6056818c04" />
            <msh:tableColumn columnName="Дата окончания медотвода" identificator="false" property="dateFinish" guid="90b0nf05fc-2d25-46d1-b850-460c0bc6b0e0" />
            <msh:tableColumn columnName="Комментарий" identificator="false" property="comments" guid="691b39df-29f8-46e0-8c53-8b733b61kj843e" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Vaccination/VaccinationAssesment/View" guid="9713e169-4355-4bcf-ae7f-9e5b7a6b204b">
        <msh:section title="Оценка" guid="581465b8-f83a-4098-bb59-b74d9e82c1f2">
          <ecom:parentEntityListAll formName="vac_vaccinationAssesmentForm" attribute="assesment" guid="31a25gf8c507-4d9b-4ddc-99f5-5e3a07b48447" />
          <msh:table hideTitle="false" idField="id" name="assesment" action="entityParentView-vac_vaccinationAssesment.do" guid="6ca52a6ha435-03a5-43de-8754-3fa4d54ede17">
            <msh:tableColumn columnName="Дата оценки" identificator="false" property="assesmentDate" guid="f6а591b39df-29f8-46e0-8c53-8b733b6c1kj843e" />
            <msh:tableColumn columnName="Общие реакции" identificator="false" property="commonReactionComment" guid="2b44e0cd-34d3-4e62-8da3-e7cb35e57a9b" />
            <msh:tableColumn columnName="Локальные реакции" identificator="false" property="localReactionComment" guid="c2beffbb-0c91-41d6-aa96-bc12920fb120" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="vac_vaccinationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="vac_vaccinationForm" guid="02eb8e83-5fd0-4620-8f18-b5076ac63095">
      <msh:sideMenu guid="sideMenu-123">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-vac_vaccination" name="Изменить" roles="/Policy/Mis/Vaccination/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-vac_vaccination" name="Удалить" roles="/Policy/Mis/Vaccination/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="vac_vaccinationForm" guid="bc41cdf4-6b80-4c5c-a913-0f1925285a66">
      <msh:sideMenu title="Добавить" guid="c78e9b18-4526-4849-aac5-1c0aa1d34139">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-vac_vaccinationEstop" key="CTRL+1" name="Медотвод" title="Добавить медотвод" roles="/Policy/Mis/Vaccination/VaccinationEstop/Create" guid="bd73fd88-b9a5-4a83-b518-03e59c99d8c1" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-vac_vaccinationAssesment" key="CTRL+2" name="Оценка" title="Добавить оценку" roles="/Policy/Mis/Vaccination/VaccinationAssesment/Create" guid="9128ac4b-931a-45ba-8123-a797ffd63002" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

