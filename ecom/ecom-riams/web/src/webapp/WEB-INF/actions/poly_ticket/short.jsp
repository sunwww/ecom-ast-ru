<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_ticketForm" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form title="<a href='entityParentView-poly_ticket.do?id=${param.id}'>Талон</a>" action="entityParentSaveGoView-poly_ticket.do" defaultField="date">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medcard" />
      <msh:panel colsWidth="15%,15%,15%,55%">
        <msh:row>
          <msh:textField label="Запись на прием: Дата" property="date" fieldColSpan="1" />
          <msh:textField label="Время" property="time" fieldColSpan="1" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="poly_ticketForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
		        <msh:row>
		          <msh:autoComplete parentId="poly_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" />
		        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="vocPaymentType" label="Вид оплаты" horizontalFill="true" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="vocServicePlace" label="Место обслуживания" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocReason" property="vocReason" label="Цель посещения" horizontalFill="true" />
          <msh:autoComplete vocName="vocVisitResult" property="vocVisitResult" label="Результат обращения" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Диагноз код МКБ" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocAcuityDiagnosis" property="vocIllnesType" label="Характер заболевания" horizontalFill="true" />
          <msh:autoComplete vocName="vocPrimaryDiagnosis" property="primary" label="Первичность" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" />
          <msh:autoComplete vocName="vocTrauma" property="vocTrauma" label="Травма" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="directHospital" label="Направлен на стационарное лечение" fieldColSpan="3"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
	        <msh:row>
	        	<msh:checkBox label="Беседа с родств." property="talk"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:row>
	        	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medServices" vocName="medServiceForSpec" colSpan="3"/>
	    </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Сопутствующие заболевания" property="concomitantDiseases" colSpan="4" />
        </msh:row>
        <msh:row>
        	<msh:textField property="uet" label="Усл.един.трудоем."/>
          <msh:autoComplete vocName="vocSpecLabel" property="specialLabel" label="Пометка" horizontalFill="true" />
<%--           <msh:autoComplete vocName="omcRoadTrafficInjury" property="roadTrafficInjury" label="Справочник ДТП" horizontalFill="true" /> --%>
        </msh:row>
        <msh:row>
          <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
            <msh:label property="statusName" label="Статус талона" />
          </msh:ifFormTypeAreViewOrEdit>
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="poly_ticketForm">
        <msh:separator label="Выдан талон" colSpan="4" />
        <msh:row>
        	<msh:textField label="Дата" property="dateCreate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время" property="timeCreate" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
	    <msh:row>
        	<msh:textField label="Пользователь" property="usernameCreate" viewOnlyField="true" />
        </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
      </msh:panel>
    </msh:form>

  </tiles:put>
</tiles:insert>