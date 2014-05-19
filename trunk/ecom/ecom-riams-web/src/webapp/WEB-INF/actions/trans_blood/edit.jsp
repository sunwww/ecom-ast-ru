<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="trans_bloodForm" guid="e20545-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Переливание крови">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-trans_blood" name="Изменить" roles="/Policy/Mis/MedCase/Transfusion/Blood/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-trans_blood" name="Удалить" roles="/Policy/Mis/MedCase/Transfusion/Blood/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Переливание крови
    	  -->
    <msh:form action="/entityParentSave-trans_blood.do" defaultField="journalNumber" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:textField property="journalNumber" label="Номер в журнале" />
          <msh:textField property="startDate" label="Дата" />
        </msh:row>
        <msh:row>
        	<msh:textField property="timeFrom" label="Время переливания с"/>
        	<msh:textField property="timeTo" label="по"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Данные о реципиенте" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="patientBloodGroup" label="Группа крови" vocName="vocBloodGroup" horizontalFill="true" />
          <msh:autoComplete property="patientRhesusFactor" label="Резус-фактор" vocName="vocRhesusFactor" horizontalFill="true" />
        </msh:row>
        </msh:panel>
        <msh:panel colsWidth="1%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%,1%">
        <msh:row>
        	<msh:label property="phenotype" label="Фенотип"/>
        	<msh:checkBox property="phenotypeC" label="C"/>
        	<msh:checkBox property="phenotypec" label="c"/>
        	<msh:checkBox property="phenotypeD" label="D"/>
        	<msh:checkBox property="phenotypeE" label="E"/>
        	<msh:checkBox property="phenotypee" label="e"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:autoComplete property="definitionRhesus" vocName="vocTransfusionDefinitionRhesus" label="Определение резус-принадлежности рециента производилось" labelColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="researchAntibodies" label="Исследование антител" vocName="vocTransfusionResearchAntibodies"/>
        	<msh:textField property="antibodiesList" label="Выявленные антитела"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="transfusionHistory" label="Были переливания" vocName="vocYesNo"/>
        	<msh:autoComplete property="personalTransfusionHistory" label="Были трансфизии по инд.подбору" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="transfusionReactionLast" label="Были реакции осложнения на переливания" vocName="vocYesNo" labelColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="countPregnancy" label="Кол-во беременностей"/>
        	<msh:autoComplete property="pregnancyHang" label="Особенности течения" vocName="vocTransfusionPregnancyHang"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="macroBall" label="Макроскопическая оценка: пригодна к переливанию" vocName="vocYesNo" labelColSpan="3"/>
        </msh:row>

        <msh:row>
          <msh:separator label="Данные с этикетки контейнера" colSpan="6" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="bloodPreparation" label="Препарат крови (компонентов)" horizontalFill="true" vocName="vocBloodPreparation" fieldColSpan="5" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPreparatorBlood" property="preparator" label="Изготовитель" horizontalFill="true" fieldColSpan="1" size="35"/>
          <msh:textField property="preparationDate" label="Дата заготовки" />
          <msh:textField property="expirationDate" label="Срок годности" />
        </msh:row>
        <msh:row>
          <msh:textField property="containerNumber" label="№ контейнера" size="35"/>
          <msh:textField property="doze" label="Объем"/>
        </msh:row>
        <msh:row>
        </msh:row>
        <msh:row>
          <msh:textField property="donor" label="Код донор" horizontalFill="true" size="35"/> 
          <msh:autoComplete property="preparationBloodGroup" label="Група крови" vocName="vocBloodGroup" guid="c4581345-c5cd-4934-b99d-a4ba4571f4f8" horizontalFill="true" />
          <msh:autoComplete property="preparationRhesusFactor" label="Резус-фактор" vocName="vocRhesusFactor" guid="f11b7586-7f17-4dd0-b638-c9ed408cc089" horizontalFill="true" />
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
          <msh:separator label="Информация о контрольных проверках" colSpan="4" guid="43c6f5c7-a52f-4da5-89ac-02b5a5c35986" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="patBloodGroupCheck" label="Група крови реципиента" vocName="vocBloodGroup" horizontalFill="true" />
          <msh:autoComplete property="prepBloodGroupCheck" label="Група крови донора" vocName="vocBloodGroup" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Информация о переливании" colSpan="4" guid="43c6f5c7-a52f-4da5-89ac-02b5a5c35986" />
        </msh:row>
        <msh:row>
          <msh:textField property="reason" label="Показания к применению" guid="2d972bf4-8bd1-4417-8631-6f794deb3fca" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="474922ed-c1b5-4181-8f51-b023cafa4ab0">
          <msh:checkBox property="primaryCase" label="Первичное" guid="fc28910a-a203-44f5-bc4c-689d7e2e5627" />
        </msh:row>
        <msh:row guid="b5c84770-189d-4dca-83ee-0f4c83a5ed52">
          <msh:autoComplete property="transfusionMethod" label="Способ переливания" vocName="vocTransfusionMethod" guid="00d18127-4616-4339-846c-6d38a1df71c5" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="4cbe96c9-d023-4ed7-9d9b-2d5960458199">
          <msh:autoComplete property="transfusionReaction" label="Трансфузионная реакция" vocName="vocTransfusionReaction" guid="e2c027c4-2b6c-451a-a02d-4a7efc593dbb" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="complications" label="Осложнения после переливания" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Информация о реактивах, использовавшихся при определении показателей" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reagentForm1.reagent" label="Реактив 1" vocName="vocTransfusionReagent" fieldColSpan="3" horizontalFill="true"/>
        	<msh:textField property="reagentForm1.series" label="Серия"/>
        	<msh:textField property="reagentForm1.expirationDate" label="Срок годности"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reagentForm2.reagent" label="Реактив 1" vocName="vocTransfusionReagent" fieldColSpan="3" horizontalFill="true"/>
        	<msh:textField property="reagentForm2.series" label="Серия"/>
        	<msh:textField property="reagentForm2.expirationDate" label="Срок годности"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Проведенные пробы на индивидуальную совместимость" colSpan="4"/>
        </msh:row>
        <msh:row>
          <msh:separator label="Наблюдение за состоянием реципиента" colSpan="4"/>
        </msh:row>
        <msh:row>
          <msh:separator label="сразу после переливания" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="monitorForm0.bloodPressureLower" label="АД ниж"/>
        	<msh:textField property="monitorForm0.bloodPressureTop" label="АД вер"/>
        	<msh:textField property="monitorForm0.pulseRate" label="ЧДД"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="monitorForm0.temperature" label="t"/>
        	<msh:autoComplete property="monitorForm0.urineColor" vocName="vocUrineColor" label="Цвет мочи"/>
        </msh:row>
        <msh:row>
          <msh:separator label="через 1 час после переливания" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="monitorForm1.bloodPressureLower" label="АД ниж"/>
        	<msh:textField property="monitorForm1.bloodPressureTop" label="АД вер"/>
        	<msh:textField property="monitorForm1.pulseRate" label="ЧДД"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="monitorForm1.temperature" label="t"/>
        	<msh:autoComplete property="monitorForm1.urineColor" vocName="vocUrineColor" label="Цвет мочи"/>
        </msh:row>
        <msh:row>
          <msh:separator label="через 2 часа после переливания" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="monitorForm2.bloodPressureLower" label="АД ниж"/>
        	<msh:textField property="monitorForm2.bloodPressureTop" label="АД вер"/>
        	<msh:textField property="monitorForm2.pulseRate" label="ЧДД"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="monitorForm2.temperature" label="t"/>
        	<msh:autoComplete property="monitorForm2.urineColor" vocName="vocUrineColor" label="Цвет мочи"/>
        </msh:row>

        <msh:row guid="8cf0863d-991d-4e6c-bb0a-ead66299a21c">
          <msh:autoComplete property="executor" label="Исполнитель" vocName="workFunction" guid="56067cb3-f8bd-4c07-9330-ad6ffee3e83a" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="trans_bloodForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>

