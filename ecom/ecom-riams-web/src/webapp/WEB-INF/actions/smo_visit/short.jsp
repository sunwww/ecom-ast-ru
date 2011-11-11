<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form title="<a href='entityParentView-smo_visit.do?id=${param.id}'>Визит</a>" action="/entitySaveGoView-smo_visit.do" defaultField="dateStart">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:hidden property="isDiaryCreate"/>
      <msh:hidden property="isPrint"/>
      <msh:hidden property="isDiagnosisCreate"/>
            <msh:hidden property="patient" guid="ef57d35d-e9a0-48ba-a00c-b77676505ab2" />
      <msh:panel guid="panel" colsWidth="20%, 10%, 10%">
        <msh:separator colSpan="4" label="Направлен" guid="86dbd4c5-cfa1-4af1-b250-fabe97b77971" />
        <msh:row guid="fa7ff4e9-4b3d-4402-b046-86283cf7938e">
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" guid="377c0e96-6812-477d-b44f-d640d659b7b6" fieldColSpan="3" size="50" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" guid="d7f4bef5-0f84-4d3c-b7d9-b7c7c5d51907" horizontalFill="true" parentAutocomplete="workFunctionPlan" viewOnlyField="true" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" guid="1d6b9712-62cc-4c67-a2d8-77bfef298ff3" parentAutocomplete="datePlan" viewOnlyField="true" />
        </msh:row>
        <msh:row guid="1f82-f4e4-4051-ae36-9bfded6">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="orderWorkFunction" label="Функция направителя" guid="01d5-ba7e-45da-a82a-9cdcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" size="50" />
        </msh:row>
        <msh:row guid="213e017e-0fd0-4027-a84b-96d11818c390">
          <msh:autoComplete viewAction="entityParentView-smo_spo.do" property="parent" label="СПО" guid="1daf90d8-052b-4776-809c-aedeeac116be" fieldColSpan="3" horizontalFill="true" vocName="vocOpenedSpoByPatient" parentId="smo_visitForm.patient" />
        </msh:row>
        <msh:row guid="8171a822-87ac-4a70-9c65-dd2234890dad">
          <msh:autoComplete showId="false" vocName="vocServiceStream" property="serviceStream" label="Поток" guid="d23cec11-2213-41d7-8877-73235062a656" horizontalFill="true" />
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" label="Место" guid="7f2d4e25-c863-439c-99eb-4f82a6159067" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:separator colSpan="4" label="Прием" guid="86dbd4c5-cfa1-4af1-b250-fabe97b77971y" />
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="smo_visitForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="bb138544-81d9-4339-b3a7-cab980708336">
          <msh:textField property="dateStart" label="Дата приема" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
          <msh:textField property="timeExecute" label="Время" guid="8d583c3f-dda1-43a9-8417-5a2d43a6cd40" />
        </msh:row>
        <msh:row guid="2c4a87f8-7053-4539-b172-c551603d828e">
          <msh:autoComplete vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" guid="7250a2c7-21c3-4633-95ed-5f9fd37d05a3" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="84300380-adbd-4c7f-b3ba-65d5fe0fbfe6">
          <msh:autoComplete showId="false" vocName="vocVisitResult" property="visitResult" viewOnlyField="false" label="Результат визита" guid="77a0dc57-91e5-45a8-b12e-0cdebc6475bb" fieldColSpan="1" horizontalFill="true" />
          <msh:textField property="nextVisitDate" label="След.дата посещен. по плану"/>
        </msh:row>
        <msh:row guid="8g80-adbd-4c7f-b3ba-6ffe6">
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" guid="77g57-91e5-45a8-b12e-0r6475bb" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="18e3d5aa-abff-40e7-ad50-bcc01ee998c1">
	        <msh:textField property="uet" label="Усл.един.трудоем."/>
          <msh:textField property="privilegeRecipeAmount" label="Количество выписанных льготных рецептов" guid="d677be2b-3347-4379-a77e-04d06580ba29" />
        </msh:row>
        <msh:row guid="3738a973-a1d3-460e-ab4a-c9ef551a9951">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunctionExecute" label="Функция" guid="010e3a75-ba7e-45da-a82a-9c618a0ffcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isPrintInfo" label="Распечатан?" viewOnlyField="true"/>
			<msh:checkBox property="noActuality" viewOnlyField="false" label="Недействительность визита" guid="6573be39-9a16-4a7c-bdef-5ca915d669c2" horizontalFill="false" fieldColSpan="1" labelColSpan="1" />        
		</msh:row>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_visitForm" />
  </tiles:put>  
</tiles:insert>

