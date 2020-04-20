<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <%@page import="ru.ecom.web.login.LoginInfo"%>

  <tiles:put name="body" type="string">
    <%
      String username = LoginInfo.find(request.getSession(true)).getUsername() ;
      request.setAttribute("username",username) ;
    %>
    <msh:form action="/entityParentSaveGoView-pres_drugShortPrescription.do" defaultField="id" title="Назначение наркотического средства">
      <msh:hidden property="id" />
      <msh:hidden property="prescriptionList"/>
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="3">
        <msh:row>
          <msh:autoComplete vocName="vocDrug" property="vocDrug" label="Лекарственный препарат" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField label="Количество (на один прием)" property="amount" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:separator colSpan="6" label="График приема"  />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата назначения" property="planStartDate" horizontalFill="true" />
          <msh:textField label="Время" property="planStartTime" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата выполнения" property="fulfilmentForm.fulfilDate" horizontalFill="true" />
          <msh:textField label="Время выполнения" property="fulfilmentForm.fulfilTime" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
          <msh:label property="createDate" label="Дата создания"/>
          <msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
          <msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:submitCancelButtonsRow  colSpan="4" />
      </msh:panel>
    </msh:form>
    <mis:canShowPrescriptionFulfilments formName="pres_drugShortPrescriptionForm" >
      <msh:ifFormTypeIsView  formName="pres_drugShortPrescriptionForm" >
        <msh:section  title="Исполнения">
          <ecom:parentEntityListAll  formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
          <msh:table  name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
            <msh:tableColumn columnName="Дата исполнения" property="fulfilDate"  />
            <msh:tableColumn columnName="Время исполнения" property="fulfilTime"  />
            <msh:tableColumn columnName="Исполнитель" property="executorInfo"  />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </mis:canShowPrescriptionFulfilments>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail  mainMenu="StacJournal" beginForm="pres_drugShortPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_drugShortPrescriptionForm" >
      <msh:sideMenu title="Лекарственное назначение" >
      </msh:sideMenu>

      <msh:sideMenu title="Добавить" >
        <mis:canShowPrescriptionFulfilments formName="pres_drugShortPrescriptionForm" >
          <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения"  key="ALT+3"/>
        </mis:canShowPrescriptionFulfilments>
      </msh:sideMenu>
      <msh:sideMenu title="Перейти"  >
        <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/View" params="id" action="/entityParentListRedirect-pres_drugShortPrescription" name="К списку лекарственных назначений"   key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений"  key="ALT+5"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>

  </tiles:put>

  <tiles:put name="javascript" type="string">

  <script type="text/javascript" src="./dwr/interface/PharmacyService.js"></script>
  <script type="text/javascript">


      function goBack(){
          window.document.location.href = "entityParentView-pres_prescriptList.do?id="+$('prescriptionList').value;
      }
  </script>
  </tiles:put>
</tiles:insert>

