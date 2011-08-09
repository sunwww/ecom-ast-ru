<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Продление
    	  -->
    <msh:form action="/entityParentSaveGoParentView-dis_record.do" defaultField="dateFrom" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
      <msh:hidden property="id" guid="5c0cd11d-8c40-4669-8374-1c599b406756" />
      <msh:hidden property="saveType" guid="61393d25-2fb3-45c3-9d30-43b04835a031" />
      <msh:hidden property="disabilityDocument" guid="c97264df-430c-4caf-a3b7-7612d3ca4068" />
      <msh:panel guid="161079ef-2bd0-4429-ad35-9e205bdbe1ce">
        <msh:row guid="84f03e51-91e3-4985-b48c-e65cd27fe438">
          <msh:textField property="dateFrom" label="Дата начала" guid="71bb6108-4449-460b-aaca-0c7419683133" />
          <msh:textField property="dateTo" label="Дата окончания" guid="31e70e41-3526-4a9e-b746-263d6e81e657" />
        </msh:row>
        <msh:row guid="3a4d2f16-324a-48d1-b9ee-cef55e075ff7">
          <msh:autoComplete vocName="vocDisabilityRegime" property="regime" label="Режим" guid="a0252f86-792b-4992-a278-5cb0d1a1bc27" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="010a9b09-d905-4a1b-9be0-ef400444b947">
          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Специалист" guid="baeb2ab7-b6c6-4b61-b05b-a939fa32af9a" fieldColSpan="3" horizontalFill="true" size="150" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_recordForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_recordForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
      <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_record" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Record/Edit" guid="609c81cf-05e5-4e07-90b7-87b38863114c" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_record" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Record/Delete" guid="1a3265b4-cebb-4536-a471-c79003ccf548" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

