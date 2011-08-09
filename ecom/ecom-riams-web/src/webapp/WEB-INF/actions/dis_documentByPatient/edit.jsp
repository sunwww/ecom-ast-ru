<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Документ нетрудоспособности
    	  -->
  <msh:ifFormTypeIsView formName="dis_documentByPatientForm">
  <script type="text/javascript">
  	window.location.href='entityParentView-dis_document.do?id='+${param.id} ;
  </script>
  </msh:ifFormTypeIsView>
    <msh:form guid="formHello" action="/entityParentSaveGoView-dis_documentByPatient.do" defaultField="documentTypeName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="patient" />
      <msh:panel guid="panel">
        <msh:row guid="e1b2e51c-46ef-21-6b6c19b60831">
          <msh:autoComplete vocName="mainLpu" property="anotherLpu" label="Другое лечебное учреждение" guid="c431085f-265a-4c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="e1b2ffa3-e51c-46ef-21-6b6c19b60831">
          <msh:autoComplete vocName="vocDisabilityDocumentType" property="documentType" label="Документ" guid="c431085f-265a-40ab-958a1c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="382fee95-4537-4242-abd4-fa4e40cda49e">
          <msh:textField passwordEnabled="false" hideLabel="false" property="issueDate" viewOnlyField="false" guid="7a444864-9b79-4e21-b218-11989c5d4c98" horizontalFill="false" />
          <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" guid="2e7aa7a4-336c-4831-b3d9-97d6f64d2ef1" horizontalFill="true" size="20" />
        </msh:row>
        <msh:row guid="b91f60e2-b0b6-4f21-8b0e-29e11ca9178c">
          <msh:textField property="series" label="Серия" guid="b9d0f37f-bd93-4e91-be9c-703c363ca9a8" />
          <msh:textField property="number" label="Номер" guid="48e71893-5dc9-4cca-8b36-6e6fe8000365" fieldColSpan="20" />
        </msh:row>
        <msh:row guid="e1b2ffa3-e51c-46ef-9a21-6b6c19b60831">
          <msh:autoComplete vocName="disabilityDocumentByCase" property="prevDocument" label="Предыдущий документ" guid="c431085f-265a-40ab-9581-a1c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox fieldColSpan="3" property="placementService" label="Состоит на учете в службе занятости"/>
        </msh:row>
        <msh:row guid="3972e779-80b6-45cb-8004-df6bcb22da38">
          <msh:separator label="Период нетрудоспособности" colSpan="4" guid="819b1c93-689a-404c-bd28-c18025b03fe4" />
        </msh:row>
        <msh:row guid="84f03e51-91e3-4985-b48c-e65cd27fe438">
          <msh:textField property="dateFrom" label="Дата начала" guid="71bb6108-4449-460b-aaca-0c7419683133" />
          <msh:textField property="dateTo" label="Дата окончания" guid="31e70e41-3526-4a9e-b746-263d6e81e657" />
        </msh:row>
        <msh:row guid="3a4d2f16-324a-48d1-b9ee-cef55e075ff7">
          <msh:autoComplete vocName="vocDisabilityRegime" property="regime" label="Режим" guid="a0252f86-792b-4992-a278-5cb0d1a1bc27" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="010a9b09-d905-4a1b-9be0-ef400444b947">
          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Специалист" guid="baeb2ab7-b6c6-4b61-b05b-a939fa32af9a" fieldColSpan="3" horizontalFill="true" />
        </msh:row>

        <msh:row guid="ab64beea-d8a0-4e8e-85b4-1911bb34c492">
          <msh:separator label="Совместительство" colSpan="4" guid="3ff9bb0c-9272-467c-9623-a30b175721fd" />
        </msh:row>
        <msh:row guid="382f295-4537-4242-abd4-fa4e8e">
          <msh:autoComplete vocName="vocCombo" property="workComboType" label="Тип совместительства" guid="227a4-336c-4831-b3d9-9f12ef1" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="58c7dec9-1488-4c39-a0e1-b8b0f7def7fd">
          <msh:textField guid="textFieldHello" property="mainWorkDocumentSeries" label="Серия по основному месту работы" />
          <msh:textField property="mainWorkDocumentNumber" label="номер" guid="0cdf1a41-0d4d-40d6-81a4-4e61b1dd3095" />
        </msh:row>
        <msh:row guid="78e547a9-eb93-4f32-b406-c90227deb286">
          <msh:separator label="Причина нетрудоспособности и больной по уходу" colSpan="4" guid="7079359c-4652-4f5b-8e43-bbd120ce2270" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityReason" property="disabilityReason" label="Причина нетруд." guid="c431085f-265a-40ab-9581-a1c8b5babeff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocDisabilityReason2" property="disabilityReason2" label="Доп. причина нетруд." fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocDisabilityReason" property="disabilityReasonChange" label="Изм. причины нетруд." fieldColSpan="3" horizontalFill="true"/>
        </msh:row>        
        <msh:row guid="e25a3-e51c-46ef-9a21-63260831">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ-10" guid="c431085f-265a-40ab-9581-a121beff" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="6b069356-39d7-44d4-bf5b-111b87f531a2">
          <msh:textField passwordEnabled="false" hideLabel="false" property="diagnosis" viewOnlyField="false" label="Диагноз" guid="37c0ea12-7093-47e8-99da-12d257e57fd7" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="9e7a70d7-cb8f-41bd-b7d2-3165215fe4c2">
          <msh:autoComplete property="nursedPatient" vocName="patient" guid="c9fe8723-d54b-46ce-b40d-1227e5fa5639" fieldColSpan="3" horizontalFill="true" label="Больной по уходу" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Госпитацизация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="hospitalizedFrom" label="Дата начала госпит."/>
        	<msh:textField property="hospitalizedTo" label="Дата окончания"/>
        </msh:row>
        <msh:row guid="88a82dd9-cbfc-40a6-a689-e764dc5977ee">
          <msh:separator label="Санаторное лечение" colSpan="4" guid="df2feaa7-d518-475a-afc5-fc424b3441e4" />
        </msh:row>
        <msh:row guid="184d86d4-851c-4a2a-a2be-850a6b1b2ae9">
          <msh:textField property="sanatoriumDateFrom" label="Дата начала" guid="c78d8c08-23f6-4825-9910-050d0d4c41bb" />
          <msh:textField property="sanatoriumDateTo" label="Дата окончания" guid="daa5ef7d-f6fa-474b-8d67-f56e7922c417" />
        </msh:row>
        <msh:row guid="67f2fb08-bc13-487d-910c-ebb7d4437344">
          <msh:textField property="sanatoriumTicketNumber" label="Номер путевки" guid="719c215c-d614-4c67-ba5d-8d1af83257ec" />
        </msh:row>
        <msh:row guid="903f0864-d284-4112-b549-1799f62bfe3f">
          <msh:textField property="sanatoriumPlace" label="Место нахождения санатория" guid="1934315c-53e9-4b96-9ce2-6e7bc7a59a2e" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="sanatoriumOgrn" label="ОГРН санатория или клиники НИИ" labelColSpan="2" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="969eb16b-136b-4938-af07-b1cfd7dd7fb2">
          <msh:separator label="Беременность" colSpan="4" guid="1fdcd012-6a6b-4320-ba3d-251149e1dc95" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="earlyPregnancyRegistration"  label="Поставлена на учет в ранние сроки беременности (до 12 недель)" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="ef70bf08-f8aa-4283-ae1d-fcc5fa8692de">
          <msh:separator label="Закрытие" colSpan="4" guid="c03fc1ea-c2ab-472e-8d52-3f70b7efbd08" />
        </msh:row>
        <%-- 
        <msh:row guid="e46a3-e51c-46ef-9a21-6bvb60831">
          <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" guid="c425f-265a-40ab-9581-a8ff" horizontalFill="true" size="40" />
        </msh:row> 
        --%>
        <msh:row guid="685ad8f8-f93c-4bd6-98b5-a1618944cb07">
          <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsNotView formName="dis_documentByPatientForm">
    <script type="text/javascript">
		prevDocumentAutocomplete.setParentId($('disabilityCase').value) ;
     </script>
     </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_documentByPatientForm" guid="116eb2b5-9e8e-45d6-91a4-328b6922bee6" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
  <tiles:put name="side" type="string" />
</tiles:insert>

