<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-mis_bedFund.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="lpu" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" guid="a5-4caf-4e14-aa70-287c" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="f53-2cae-4795-93e8-9cd1">
          <msh:textField property="amount" label="Количество коек" size="20" fieldColSpan="3" guid="e4d1b-8802-467d-b205-70" />
        </msh:row>
        <msh:row guid="f53-2cae-4795-93e8-9cd1">
          <msh:checkBox property="forChild" label="Для детей" guid="1b-8802-467d-b205-70" />
          <msh:checkBox property="noFood" label="Без питания" guid="1b-8802-467d-b205-70" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="isRehab" label="Реабилитационные койки" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        
        	<msh:checkBox property="addCaseDuration" fieldColSpan="3" label="День выписки и день поступления считать разными днями"/>
        </msh:row>
        <msh:row guid="b6eb-b971-44-9a90-5107">
          <msh:autoComplete vocName="vocBedSubType" property="bedSubType" label="Тип коек" guid="3d-88-467d-b205-715fb3b018" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="b6eb-b971-441e-9a90-5107">
          <msh:autoComplete vocName="vocBedReductionType" property="reductionType" label="" guid="3d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="b6gteb-b971-441e-9a90-5107">
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" guid="5dfb6-b2c3-44be-9d10-d8d4" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="8c3c-f605-42cc-accb-6c6b">
          <msh:autoComplete vocName="vocHospitalServiceType" property="serviceType" label="Тип госпитального обслуживания" guid="88e-3158-4b9a-ab8c-f364e9" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="07fbe7a5-4caf-4e14-aa70-287cf4ca610d">
          <msh:textField property="dateStart" label="Дата начала актуальности" guid="e4d1b-8802-467d-b205-70jgh5" />
          <msh:textField property="timeStart" label="Время начала актуальности" guid="e4d1b-8802-467d-b205-75765" />
        </msh:row>
        <msh:row guid="07fbe7a5-4caf-4e14-aa70-287246">
          <msh:textField property="dateFinish" label="Дата окончания актуальности" guid="e1b-8802-467d-b205-75765" />
          <msh:textField property="timeFinish" label="Время окончания актуальности" guid="e4d1b-8802-467d-b205-757876" />
        </msh:row>
        <msh:row guid="855de982-5baf-46f1-9f8b-f48" />
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="mis_bedFundForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeIsView formName="mis_bedFundForm">
    <msh:sideMenu guid="sideMenu-123">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_bedFund" name="Изменить" roles="/Policy/Mis/BedFund/Edit" />
      <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_bedFund" name="Удалить" roles="/Policy/Mis/BedFund/Delete" />
    </msh:sideMenu>
    
    <msh:sideMenu title="Перейти" guid="sideMenu-123">
      <msh:sideLink guid="7a5-4caf-4e14-aa70-287" action="/entityParentListRedirect-mis_bedFund" name="К списку" params="id" />
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

