<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Направление на ВК
    	  -->
    <msh:form action="/entityParentSaveGoView-expert_ker_direct.do" defaultField="patientStatusName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="medCase" />
      <msh:hidden guid="hiddenParent" property="patient" />
      <msh:panel colsWidth="1%,1%,1%,97%">
        <msh:row>
          <msh:autoComplete vocName="vocExpertPatientStatus" property="patientStatus" label="Статус пациента" 
          	horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:textField property="profession" label="Профессия" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="orderDate" label="Дата напр."/>
        	<msh:textField property="disabilityDate" label="Дата  выхода на нетруд."/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderFunction" fieldColSpan="3" label="Специалист"
        		vocName="workFunction" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="disabilityDocument" fieldColSpan="3" label="Лист нетруд." parentId="expert_ker_directForm.patient"
        		vocName="disabilityDocumentByPatient" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="mainDiagnosis" fieldColSpan="3" label="Код осн. диаг." horizontalFill="true" vocName="vocIdc10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="concomitantDiagnosis" label="Сопут. диагноз" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="complicationDiagnosis" label="Осложнение" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>        
        <msh:row>
        	<msh:autoComplete property="orderConclusion" fieldColSpan="3" label="Обоснование напр." horizontalFill="true" vocName="vocExpertOrderConclusion"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="delayReason" label="Обоснов. задержки подачи на ВК" horizontalFill="true" labelColSpan="2" fieldColSpan="2"/>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="expert_ker_directForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeIsView formName="expert_ker_directForm">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-expert_ker_direct" name="Изменить" roles="/Policy/Mis/MedCase/ClinicExpertCard/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-expert_ker_direct" name="Удалить" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Delete" />
    </msh:sideMenu>
    
    <msh:sideMenu title="Перейти" guid="sideMenu-123">
      <msh:sideLink guid="7a5-4caf-4e14-aa70-287" action="/entityParentListRedirect-expert_ker_direct" name="К списку" params="id" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/View"/>
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>