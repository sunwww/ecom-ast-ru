<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregnanCardForm">
      <msh:sideMenu title="Беременность">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregnanCard" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_pregnanCard" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Беременность
    	  -->
    <msh:form  
    action="/entityParentSaveGoParentView-preg_pregnanCard.do" defaultField="consultationName">
      <msh:hidden property="id" />
      <msh:hidden property="pregnancy" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="consultation" label="Консультация" vocName="mainLpu" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Перенесенные общие и гинекологические заболевания и операции" colSpan="6" />
        </msh:row>
        <msh:row>
          <msh:textArea property="transmitDiseases" hideLabel="true" labelColSpan="1" fieldColSpan="5" rows="2" />
        </msh:row>
        <msh:row>
          <msh:separator label="Особенности течения прежних беременностей, родов, послеродового периода" colSpan="6" />
        </msh:row>
        <msh:row>
          <msh:textArea property="previousPregnancies" hideLabel="true" labelColSpan="1" rows="2" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="lastMensis" label="Последняя менструация" labelColSpan="2" />
          <msh:checkBox property="dontRememberLastMensis" label="не помнит"/>
        </msh:row>
        <msh:row>
          <msh:textField property="shortPregnancyAmount" label="Кол-во коротких беременностей" labelColSpan="2" />
          <msh:textField property="childbirthAmount" label="кол-во родов" />
        </msh:row>
        <msh:row>
          <msh:textField property="firstVisitDate" label="Первое посещение консультации при данной беременности" labelColSpan="3" />
          <msh:checkBox property="dontVisitCons" label="не посещала"/>
        </msh:row>
        <msh:row>
          <msh:textField property="visitsAmount" label="Всего посетила раз" labelColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:separator label="Особенности течения данной беременности, состояние беременной" colSpan="6" />
        </msh:row>
        <msh:row>
          <msh:textArea labelColSpan="1" property="pregnancyFeatures" hideLabel="true" fieldColSpan="4" rows="8" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Моча дата:" passwordEnabled="false" hideLabel="false" property="urineAnalysisDate" viewOnlyField="false" horizontalFill="false" labelColSpan="2" />
          <msh:textField property="urineAnalysis" label="результат" />
        </msh:row>
        <msh:row>
          <msh:textField property="bloodAnalysisDate" label="Кровь дата" labelColSpan="2" />
          <msh:textField passwordEnabled="false" hideLabel="false" property="bloodAnalysis" viewOnlyField="false" horizontalFill="false" label="результат" />
        </msh:row>
        <msh:row>
          <msh:textField property="trainingAmount" label="Кол-во занятий психопрофилактич. подготовки" labelColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="lastTrainingDate" label="Последние занятия" labelColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="supposedBithPeriod" label="Предполагаемый срок родов" labelColSpan="2" />
        </msh:row>
        <msh:row>
          <msh:separator label="Особые замечания" colSpan="6" />
        </msh:row>
        <msh:row>
          <msh:textArea property="notes" hideLabel="true" rows="5" fieldColSpan="4" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregnanCardForm" />
  </tiles:put>
</tiles:insert>

