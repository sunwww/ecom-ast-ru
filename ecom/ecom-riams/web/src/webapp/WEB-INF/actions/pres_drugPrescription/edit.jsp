<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <%@page import="ru.ecom.web.login.LoginInfo"%>

  <tiles:put name="body" type="string">
    <%
      request.setAttribute("username",LoginInfo.find(request.getSession(true)).getUsername()) ;
    %>
    <msh:form action="/entityParentSaveGoView-pres_drugPrescription.do" defaultField="id" title="Назначение лекарственного средства">
      <msh:hidden property="id" />
      <msh:hidden property="prescriptionList"/>
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="3">
        <msh:row>
          <msh:autoComplete vocName="vocDrug" property="vocDrug" label="Лекарственный препарат" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDrugMethod" label="Способ введения" property="method" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField property="frequency" label="Частота (раз в день)" horizontalFill="true" />
          <msh:textField label="Количество (на один прием)" property="amount" horizontalFill="true"/>
        </msh:row>
        <msh:row/>
        <msh:row>
          <msh:separator colSpan="6" label="График приема" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата начала" property="planStartDate" horizontalFill="true" />
          <msh:textField label="Время" property="planStartTime" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата окончания" property="planEndDate" horizontalFill="true" />
          <msh:textField label="Время" property="planEndTime" horizontalFill="true" />
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
        <msh:row>
          <msh:label property="cancelDate" label="Дата завершения"/>
          <msh:label property="cancelTime" label="время"/>
        </msh:row>
        <msh:row>
          <msh:label property="cancelUsername" label="пользователь"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <mis:canShowPrescriptionFulfilments formName="pres_drugPrescriptionForm">
      <msh:ifFormTypeIsView formName="pres_drugPrescriptionForm" >
        <msh:section title="Исполнения">
          <ecom:parentEntityListAll formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
          <msh:table name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
            <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" />
            <msh:tableColumn columnName="Время исполнения" property="fulfilTime" />
            <msh:tableColumn columnName="Исполнитель" property="executorInfo" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </mis:canShowPrescriptionFulfilments>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="pres_drugPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_drugPrescriptionForm">
      <msh:sideMenu title="Лекарственное назначение">
        <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/Edit" params="id" action="/javascript:cancelPrescription()" name="Отменить" key="ALT+2"/>
      </msh:sideMenu>

      <msh:sideMenu title="Добавить">
        <mis:canShowPrescriptionFulfilments formName="pres_drugPrescriptionForm">
          <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" key="ALT+3"/>
        </mis:canShowPrescriptionFulfilments>
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" >
        <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/View" params="id" action="/entityParentListRedirect-pres_drugPrescription" name="К списку лекарственных назначений"  key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" key="ALT+5"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>

  </tiles:put>

  <tiles:put name="javascript" type="string">

  <script type="text/javascript" src="./dwr/interface/PharmacyService.js"></script>
  <script type="text/javascript">

      function cancelPrescription() {
          PharmacyService.endPrescription(${param.id},"${username}", {
              callback : function(aResult) {
                  if(aResult=="1"){
                      alert("Назначение уже закрыто!");
                  }else {
                      alert("Назначение закрыто!");
                      goBack();
                  }
              }
          });
      }

      function goBack(){
          location.href = "entityParentView-pres_prescriptList.do?id="+$('prescriptionList').value;
      }
  </script>
  </tiles:put>
</tiles:insert>

