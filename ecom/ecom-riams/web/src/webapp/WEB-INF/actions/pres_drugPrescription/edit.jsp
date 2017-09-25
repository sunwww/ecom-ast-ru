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
    <msh:form guid="formHello" action="/entityParentSaveGoView-pres_drugPrescription.do" defaultField="id" title="Назначение лекарственного средства">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="prescriptionList"/>
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel" colsWidth="3">
        <msh:row>
          <msh:autoComplete  parentId="id" vocName="vocPharmDrugOnStorage" property="drug" label="Лекарственный препарат" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDrugMethod" label="Способ введения" property="method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField property="frequency" label="Частота (раз в день)" horizontalFill="true" />
          <msh:textField label="Количество (на один прием)" property="amount" horizontalFill="true"/>
        </msh:row>
        <msh:row/>
        <msh:row>
          <msh:separator colSpan="6" label="График приема" guid="52167v-7fee-45e5-96ec-7b0ggf19182cfcb" />
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
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <mis:canShowPrescriptionFulfilments formName="pres_drugPrescriptionForm" guid="6eb-b971-441e-9a90-51d2">
      <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_drugPrescriptionForm" >
        <msh:section guid="sectionChilds" title="Исполнения">
          <ecom:parentEntityListAll guid="parentEntityListChilds" formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
          <msh:table guid="tableChilds3434" name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
            <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
            <msh:tableColumn columnName="Время исполнения" property="fulfilTime" guid="d61b9d49-a94d-4cf2-af1b-05020213901f" />
            <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="bfe44281-0967-45f2-a6af-f5b368cca8ae" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </mis:canShowPrescriptionFulfilments>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Prescription" beginForm="pres_drugPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_drugPrescriptionForm" guid="998yr7aa692-c1d3-4d79-bc37-cfa204fa846c">
      <msh:sideMenu title="Лекарственное назначение" guid="eb2154-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/Edit" params="id" action="/javascript:CancelPrescription()" name="Отменить" key="ALT+2"/>
      </msh:sideMenu>

      <msh:sideMenu title="Добавить" guid="0e25aac7-5361-434d-a8a7-1237aabb506f">
        <mis:canShowPrescriptionFulfilments formName="pres_drugPrescriptionForm" guid="aa692-c1d3-4d79-bc37-cfa20">
          <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" guid="ca3519-9239-47e3-aec4-9a847144" key="ALT+3"/>
        </mis:canShowPrescriptionFulfilments>
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="67f8ha758-3ad2-4e6f-a791-482e3cc20955" >
        <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/View" params="id" action="/entityParentListRedirect-pres_drugPrescription" name="К списку лекарственных назначений" guid="e98f5d94-fe74-4c43-85b1-2e4847ce3eff"  key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" guid="e98f5d94-fe74-4c43-85b1-2e4847ce3eff" key="ALT+5"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>

  </tiles:put>

  <tiles:put name="javascript" type="string">

  <script type="text/javascript" src="./dwr/interface/PharmacyService.js"></script>
  <script language="javascript" type="text/javascript">

      function CancelPrescription() {
          var name = "${username}";
          PharmacyService.endPrescription(${param.id},name, {
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
          var PrescriptionListId = document.querySelector('#prescriptionList').value;
          location.href = "entityParentView-pres_prescriptList.do?id="+PrescriptionListId;
      }
  </script>
  </tiles:put>
</tiles:insert>

