<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-smo_spo.do" defaultField="dateStart">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="patient" guid="bb02c92e-e6b5-4e05-9375-356e412bb394" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:row guid="e6d5b1ac-787d-4a77-b571-81f9ce83c8b8">
            <msh:checkBox property="noActuality" label="Недействительность" guid="816475aa-6ff1-4743-93b7-a6399addd548" />
          </msh:row>
          <msh:textField guid="textFieldHello" property="dateStart" label="Дата начала" />
          <msh:textField property="dateFinish" label="Дата окончания" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="2ff2ea54-5a8f-4338-92ec-ca877c4a7d34">
          <msh:label property="duration" label="Длительность" guid="eeedee49-a83e-4498-9d44-10be53474861" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="МКБ-10" guid="b7d15793-841e-4779-b7ee-b44ac6eb7f08" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:autoComplete viewAction="entityView-mis_worker.do" vocName="vocWorker" property="startWorker" label="Кто начал" guid="7b8a49e5-653a-4490-bfcf-ff42801611f7" fieldColSpan="3" horizontalFill="true" />
        <msh:row guid="b7488cc8-933c-406b-8589-8b852f78765e">
          <msh:autoComplete viewAction="entityView-mis_Worker" vocName="vocWorker" property="finishWorker" label="Кто завершил" guid="d30a17e6-b833-47e6-9699-417dce4cd008" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="976b032e-82b6-45c9-88ca-935b07a6b482">
          <msh:autoComplete viewAction="entityView-mis_worker.do" vocName="vocWorker" property="owner" label="Владелец" guid="1d81b4df-9cdc-40f1-b496-a10a3a5080e0" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_spoForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Direction">
      <msh:section guid="sectionChilds" title="Направленные">
        <ecom:webQuery name="directions" nativeSql="select MedCase.id&#xA;&#x9;, WorkCalendarDay.calendarDate&#xA;&#x9;, WorkCalendarTime.timeFrom&#xA;&#x9;, Patient.lastname || ' ' ||  Patient.firstname || ' ' ||  Patient.middlename&#xA;        , VocReason.name&#xA;  from MedCase&#xA;  left outer join WorkCalendarDay  on MedCase.datePlan_id    = WorkCalendarDay.id&#xA;  left outer join WorkCalendarTime on MedCase.timePlan_id    = WorkCalendarTime.id&#xA;  left outer join Worker           on MedCase.orderWorker_id = Worker.id&#xA;  left outer join Patient          on Worker.person_id       = Patient.id&#xA;  left outer join VocReason        on MedCase.visitReason_id = VocReason.id&#xA; where MedCase.parent_id=${param.id} &#xA;   and MedCase.DTYPE='Visit'&#xA;   and MedCase.dateStart is null" guid="624771b1-fdf1-449e-b49e-5fcc34e03fb5" />
        <msh:table guid="tableChilds" name="directions" action="entityParentView-smo_visit.do" idField="1">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" guid="709291f1-be97-4cd5-87c3-04a112a96639" />
          <msh:tableColumn columnName="Дата" property="2" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Время" property="3" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Кто направил" property="4" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
          <msh:tableColumn columnName="Цель визита" property="5" guid="470b21ea-45ac-43b3-b592-349baaad13a8" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/View">
      <msh:section title="Исполненные визиты" guid="b5483338-a80a-45a8-881e-0f17c8e752d7">
        <ecom:webQuery name="visits" nativeSql="select MedCase.id&#xA;        , MedCase.dateStart&#xA;&#x9;, Patient.lastname || ' ' ||  Patient.firstname || ' ' ||  Patient.middlename&#xA;  from MedCase&#xA;  left outer join WorkFunction     on MedCase.workFunctionExecute_id = WorkFunction.id&#xA;  left outer join Worker           on WorkFunction.worker_id = Worker.id&#xA;  left outer join Patient          on Worker.person_id       = Patient.id&#xA; where MedCase.parent_id=${param.id} &#xA;   and MedCase.DTYPE='Visit'&#xA;   and MedCase.dateStart is not null&#xA;" guid="e0eb2cf2-270c-43ac-9bae-f59ec0866558" />
        <msh:table idField="1" name="visits" action="entityParentView-smo_visit.do" guid="29d25014-7b5a-4030-846b-52223513c331">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" guid="b054f045-3162-4186-83a9-6b5cabdebc17" />
          <msh:tableColumn columnName="Дата" property="2" guid="9a7bc699-7753-48ea-a2b5-82f2d4abb019" />
          <msh:tableColumn columnName="Исполнитель" property="3" guid="691dbb0c-f2e5-44ca-8746-417c3a585646" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <%-- 
      <msh:ifInRole roles="/Policy/Mis/Disability/DisabilityCase/View">
      <msh:section title="Нетрудоспособность" guid="7dfcdd6c-f655-488c-902b-4b6486fffd58">
        <ecom:parentEntityListAll formName="dis_caseForm" attribute="dis" guid="97f7fd1f-97c2-4b57-a01e-56a5d168b8cc" />
        <msh:table idField="id" name="dis" action="entityParentView-dis_case.do" guid="a4c778de-c3ed-4995-8215-2ad17e57fece">
          <msh:tableColumn columnName="Дата начала" property="dateFrom" guid="2edb485d-971d-499e-8abd-c90ecaf7d480" />
          <msh:tableColumn columnName="Дата окончания" property="dateTo" guid="90c597f9-86a6-4948-be0c-05615685203d" />
          <msh:tableColumn columnName="Продолжительность" property="duration" guid="c54c2899-a6f3-41a5-8473-8fc47ea722e6" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      --%>
      <msh:ifInRole roles="/Policy/Mis/Prescription/Prescript/View">
      <msh:section title="Листы назначений" guid="7dfcdgjt4d6c-f655-488c-902b-4b6486fffd58">
        <ecom:parentEntityListAll formName="pres_prescriptListForm" attribute="prescriptList" guid="97-97c2-4b57-a01e-565cc" />
        <msh:table idField="id" name="prescriptList" action="entityParentView-pres_prescriptList.do" guid="a4ce-c3ed-4995-8215-27fece">
          <msh:tableColumn columnName="Дата заведения" property="date" guid="2e85d-971d-499e-8abd-c90480" />
          <msh:tableColumn columnName="Наименование" property="name" guid="97f9-86a6-4948-be0c-053d" />
          <msh:tableColumn columnName="Владелец" property="OwnerInfo" guid="c54-a6f3-41a5-8473-8f2e6" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_spoForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_spoForm" guid="625411c7-8b7e-4d5b-acae-4fe679e24094">
      <msh:sideMenu guid="sideMenu-123" title="СПО">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_spo" name="Изменить" roles="/Policy/Mis/MedCase/Spo/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-smo_spo" name="Удалить" roles="/Policy/Mis/MedCase/Spo/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="fbdebbf4-8006-4417-b7df-f23dcf298f62">
        <%-- <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_case" name="Нетрудоспособность" title="Добавить случай нетрудоспособности" guid="ae605283-4519-488c-9d9e-715d1978def2" /> --%>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

