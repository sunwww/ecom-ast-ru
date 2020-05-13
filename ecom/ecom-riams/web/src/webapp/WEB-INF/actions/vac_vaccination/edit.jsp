<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - МИАЦ. Вакцинации
    	  -->
    <msh:form action="/entityParentSaveGoView-vac_vaccination.do" defaultField="executeDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:hidden property="patient" />
      <msh:panel colsWidth="5% 20% 10%">
        <msh:row>
          <msh:textField property="executeDate" label="Дата исполнения" />
          <msh:textField property="executeTime" label="Время исполнения" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vaccine" property="vaccine" label="Вакцина" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocVaccinationMaterial" property="material" parentAutocomplete="vaccine" label="Вакцинный материал" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocVaccinationReaction" property="vaccinationReaction" label="Реакция вакцинация" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="dose" label="Доза" />
          <msh:textField property="series" label="Серия" />
        </msh:row>
        <msh:textField property="controlNumber" label="Контрольный номер" />
        <msh:textField property="expirationDate" label="Дата годности" />
        <msh:row>
          <msh:autoComplete vocName="vocVaccinationPhase" property="phase" label="Фаза вакцинации" />
          <msh:textField property="nextDate" label="Дата следующей вакцинации" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocVaccinationMethod" property="method" label="Метод вакцинации" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocVaccinationReasonType" property="reasonType" label="Причина вакцинации" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="permitWorker" label="Разрешил" vocName="workFunction" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="executeWorker" label="Исполнитель" vocName="workFunction" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="vac_vaccinationForm">
      <msh:ifInRole roles="/Policy/Mis/Vaccination/VaccinationEstop/View">
        <msh:section title="Медотводы">
          <ecom:parentEntityListAll formName="vac_vaccinationEstopForm" attribute="estop" />
          <msh:table hideTitle="false" idField="id" name="estop" action="entityParentView-vac_vaccinationEstop.do">
            <msh:tableColumn columnName="Дата медотвода" property="estopDate" />
            <msh:tableColumn columnName="Дата окончания медотвода" identificator="false" property="dateFinish" />
            <msh:tableColumn columnName="Комментарий" identificator="false" property="comments" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Vaccination/VaccinationAssesment/View">
        <msh:section title="Оценка">
          <ecom:parentEntityListAll formName="vac_vaccinationAssesmentForm" attribute="assesment" />
          <msh:table hideTitle="false" idField="id" name="assesment" action="entityParentView-vac_vaccinationAssesment.do">
            <msh:tableColumn columnName="Дата оценки" identificator="false" property="assesmentDate"/>
            <msh:tableColumn columnName="Общие реакции" identificator="false" property="commonReactionComment" />
            <msh:tableColumn columnName="Локальные реакции" identificator="false" property="localReactionComment" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="vac_vaccinationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="vac_vaccinationForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-vac_vaccination" name="Изменить" roles="/Policy/Mis/Vaccination/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-vac_vaccination" name="Удалить" roles="/Policy/Mis/Vaccination/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="vac_vaccinationForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-vac_vaccinationEstop" key="CTRL+1" name="Медотвод" title="Добавить медотвод" roles="/Policy/Mis/Vaccination/VaccinationEstop/Create" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-vac_vaccinationAssesment" key="CTRL+2" name="Оценка" title="Добавить оценку" roles="/Policy/Mis/Vaccination/VaccinationAssesment/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

