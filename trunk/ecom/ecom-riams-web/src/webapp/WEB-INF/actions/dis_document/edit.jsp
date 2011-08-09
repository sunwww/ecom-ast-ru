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
    <msh:form guid="formHello" action="/entityParentSaveGoView-dis_document.do" defaultField="documentTypeName">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="disabilityCase" />
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



        
        <msh:ifFormTypeIsCreate formName="dis_documentForm">
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
        </msh:ifFormTypeIsCreate>



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
        	<msh:textField property="sanatoriumOgrn" label="ОГРН санатория или клиники НИИ" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>

        <msh:row guid="ef70bf08-f8aa-4283-ae1d-fcc5fa8692de">
          <msh:separator label="Закрытие" colSpan="4" guid="c03fc1ea-c2ab-472e-8d52-3f70b7efbd08" />
        </msh:row>
        <msh:row guid="e46a3-e51c-46ef-9a21-6bvb60831">
    	      <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" guid="c425f-265a-40ab-9581-a8ff" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:checkBox hideLabel="false" property="noActuality" viewOnlyField="false" guid="6deca67a-3fcb-472f-aadd-3e6cf3139c83" horizontalFill="false" label="Испорчен" />

         	<msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Reopen"> 
        		<msh:ifFormTypeIsNotView formName="dis_documentForm">
        			<msh:checkBox property="isClose" label="Документ закрыт" guid="c425f-265a-40ab-9581-a8ff"  />
        		</msh:ifFormTypeIsNotView>
         	</msh:ifInRole> 
        	<msh:ifFormTypeIsView formName="dis_documentForm">
        		<msh:checkBox property="isClose" label="Документ закрыт"/>
        	</msh:ifFormTypeIsView>
        </msh:row>
        <msh:row guid="685ad8f8-f93c-4bd6-98b5-a1618944cb07">
          <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="dis_documentForm">
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Record/View" guid="7c589f25-6e30-4d30-bb2f-d86a68f4f0cd">
        <msh:section guid="sectionChilds" title="Продление">
          <ecom:parentEntityListAll guid="parentEntityListChilds" formName="dis_recordForm" attribute="disabilityRecord" />
          <msh:table guid="tableChilds" name="disabilityRecord" action="entityParentView-dis_record.do" idField="id">
            <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
            <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="a744754f-5212-4807-910f-e4b252aec108" />
            <msh:tableColumn columnName="Специалист" identificator="false" property="workFunctionInfo" guid="14e830-496c-aa2240937d" />
            <msh:tableColumn columnName="Режим" identificator="false" property="regimeText" guid="14e8c4f9-f430-496c-ae75-ae2a2240937d" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/View" guid="1e5e59a5-8acd-4c54-a10a-fafd5ddcc685">
        <msh:section createRoles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create" 
        createUrl="entityParentPrepareCreate-dis_regimeViolation.do?id=${param.id}"
        shortList="entityParentShortList-dis_regimeViolation.do?id=${param.id}"
         title="Нарушение режима" guid="11e46cd9-93cd-44c0-a16d-2470243a0a65">
          <ecom:parentEntityListAll formName="dis_regimeViolationForm" attribute="violation" guid="16363824-17a0-4ba7-9022-720dcb016bad" />
          <msh:table idField="id" name="violation" action="entityParentView-dis_regimeViolation.do" guid="cac74c69-de47-4874-aa5f-a0466d479750">
            <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="cc1f4517-871f-44b9-b614-3dee5bddd607" />
            <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="7a12d9ed-fa3b-49c6-83be-a46de701aded" />
            <msh:tableColumn columnName="Комментарий" property="comment" guid="82b358c5-9acf-4936-90ff-1823d8c2046e" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/View" guid="08b29fe0-78b3-4ade-8a29-5962b37102d2">
        <msh:section title="Медико-социальная экспертная комиссия" guid="15cd2fda-ef53-4af1-b71d-bb9d2b767158">
          <ecom:parentEntityListAll formName="dis_medSocCommissionForm" attribute="medSoc" guid="56302751-44dd-4b75-8d7f-8f66bf0fe577" />
          <msh:table idField="id" name="medSoc" action="entityParentView-dis_medSocCommission.do" guid="99c062d6-25c4-4609-9181-bfa155a7d704">
            <msh:tableColumn columnName="Дата направления" property="assignmentDate" guid="e956f01b-fd81-41f7-ac94-5bc1cc81443a" />
            <msh:tableColumn columnName="Дата регистрации" property="registrationDate" guid="88739c92-a1e1-4c74-b5ef-81f137685a4f" />
            <msh:tableColumn columnName="Дата освидетельствования" property="examinationDate" guid="fb4d52ce-97b4-4b7a-92cd-92d2aa7efee1" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_documentForm" guid="116eb2b5-9e8e-45d6-91a4-328b6922bee6" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="dis_documentForm">
    <script type="text/javascript">
     if (+$('isClose').checked==true) {
     	$('ALT_3').style.display = 'none' ;
     }
     </script>
     </msh:ifFormTypeIsView>
     <msh:ifFormTypeIsNotView formName="dis_documentForm">
    <script type="text/javascript">
		prevDocumentAutocomplete.setParentId($('disabilityCase').value) ;
     </script>
     </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_documentForm" guid="70347895-a57d-49ea-a6d5-e634d280f5e7">
      <msh:sideMenu title="Документ нетрудоспобности" guid="c21230e7-e6fa-462b-b0cd-b1305ecd0ade">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_document" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Edit" guid="d8ee3597-d55e-4f08-a868-c58d8dfc57c4" />
        <tags:closeDisDocument reason="closeReason" roles="/Policy/Mis/Disability/Case/Document/Edit" key="ALT+3" name="doc" title="Закрыть" confirm="Вы действительно хотите закрыть текущий документ нетрудоспособности?" seria='series' number='number'/>
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_document" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Delete" guid="4565603e-337e-48eb-82eb-79bd40cd5108" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink params="id" name="документа нетрудоспособности" action="/print-disability.do?s=DisabilityService&amp;m=printDocument"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="c79769a2-8a1c-4c21-ab9c-b7ed71ceb99d">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_record" roles="/Policy/Mis/Disability/Case/Document/Record/Create" name="Продление" guid="0634b894-60e2-4b73-acee-7bf7316a77fc" title="Продлить листок нетрудоспособности" key="CTRL+1" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_regimeViolation" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create" name="Нарушение режима" guid="d9a0ba4a-a68a-4f48-8492-767e911bce80" title="Добавить запись о нарушении режима" key="CTRL+2" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_medSocCommission" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Create" name="МСЭК" title="Добавить решение медико-социальной экспертной комиссии" guid="4e09fb92-851a-4547-a12d-c384f63e31cd" key="CTRL+3" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

