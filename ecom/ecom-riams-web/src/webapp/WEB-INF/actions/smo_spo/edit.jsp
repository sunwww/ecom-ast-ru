<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-smo_spo.do" defaultField="dateStart">
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
        <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="startFunction" label="Кто начал" guid="7b8a49e5-653a-4490-bfcf-ff42801611f7" fieldColSpan="3" horizontalFill="true" />
        <msh:row guid="b7488cc8-933c-406b-8589-8b852f78765e">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="finishFunction" label="Кто завершил" guid="d30a17e6-b833-47e6-9699-417dce4cd008" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="976b032e-82b6-45c9-88ca-935b07a6b482">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="ownerFunction" label="Владелец" guid="1d81b4df-9cdc-40f1-b496-a10a3a5080e0" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_spoForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Direction">
      <msh:section guid="sectionChilds" title="Направленные">
        <ecom:webQuery name="directions" nativeSql="
         select v.id , wcd.calendarDate , wct.timeFrom 
 ,ovwf.name as ovwfname
, owp.lastname || ' ' || owp.firstname || ' ' || owp.middlename  as fiodoc
, vr.name as vrname
, vss.name as vssname
,case when v.noActuality='1' then 'Неактуален' else '' end as actual
from MedCase v
left join WorkCalendarDay wcd on v.datePlan_id = wcd.id 
left join WorkCalendarTime wct on v.timePlan_id = wct.id 
left join WorkFunction owf on owf.id=v.orderWorkFunction_id
left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
left join Worker ow on owf.id = ow.id 
left join Patient owp on ow.person_id = owp.id 
left join VocReason vr on v.visitReason_id = vr.id 
left join VocServiceStream vss on vss.id=v.serviceStream_id
where v.parent_id='${param.id}' 
and v.DTYPE='Visit' 
and v.dateStart is null"/>
        <msh:table guid="tableChilds" name="directions" action="entityParentView-smo_visit.do" 
        idField="1">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" guid="709291f1-be97-4cd5-87c3-04a112a96639" />
          <msh:tableColumn columnName="Дата" property="2" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
          <msh:tableColumn columnName="Время" property="3" guid="a744754f-5212-4807-910f-e4b252aec108" />
          <msh:tableColumn columnName="Раб.функция направителя" property="4" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
          <msh:tableColumn columnName="Кто направил" property="5" guid="bf4cb2b2-eb35-4e8f-b8cb-4ccccb06d5ac" />
          <msh:tableColumn columnName="Цель визита" property="6" guid="470b21ea-45ac-43b3-b592-349baaad13a8" />
          <msh:tableColumn columnName="Поток обслуживания" property="7" guid="470b21ea-45ac-43b3-b592-349baaad13a8" />
          <msh:tableColumn columnName="Актуальность" property="8" guid="470b21ea-45ac-43b3-b592-349baaad13a8" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/View">
      <msh:section title="Исполненные визиты" guid="b5483338-a80a-45a8-881e-0f17c8e752d7">
        <ecom:webQuery name="visits" nativeSql="
 select vis.id as visid, vis.dateStart as visdatestart
 ,vwf.name as vwfname 
, pat.lastname || ' ' ||  pat.firstname || ' ' ||  pat.middlename as fio
,vr.name as vrname,vvr.name as vvrname,vss.name as vssname
,list(distinct mkb.code||' '||vpd.name)
from MedCase vis
    left join WorkFunction wf on vis.workFunctionExecute_id = wf.id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id   
    left join Worker w on wf.worker_id = w.id   
    left join Patient pat on w.person_id = pat.id  
    left join VocReason vr on vis.visitReason_id = vr.id 
    left join VocServiceStream vss on vss.id=vis.serviceStream_id
    left join Diagnosis diag on diag.medcase_id=vis.id
    left join VocIdc10 mkb on mkb.id=diag.idc10_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    left join VocVisitResult vvr on vvr.id=vis.visitResult_id

where vis.parent_id='${param.id}'
and vis.DTYPE='Visit'    
and vis.dateStart is not null 
group by vis.id, vis.dateStart,vwf.name, pat.lastname,  pat.firstname,  pat.middlename
,vr.name ,vss.name,vvr.name
order by vis.dateStart
        " guid="e0eb2cf2-270c-43ac-9bae-f59ec0866558" />
        <msh:table idField="1" name="visits" viewUrl="entityShortView-smo_visit.do" action="entityParentView-smo_visit.do" guid="29d25014-7b5a-4030-846b-52223513c331">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" guid="b054f045-3162-4186-83a9-6b5cabdebc17" />
          <msh:tableColumn columnName="Дата" property="2" guid="9a7bc699-7753-48ea-a2b5-82f2d4abb019" />
          <msh:tableColumn columnName="Раб. функция врача" property="3" guid="691dbb0c-f2e5-44ca-8746-417c3a585646" />
          <msh:tableColumn columnName="ФИО врача" property="4" guid="691dbb0c-f2e5-44ca-8746-417c3a585646" />
          <msh:tableColumn columnName="Цель визита" property="5" guid="691dbb0c-f2e5-44ca-8746-417c3a585646" />
          <msh:tableColumn columnName="Результат" property="6" guid="691dbb0c-f2e5-44ca-8746-417c3a585646" />
          <msh:tableColumn columnName="Поток" property="7" guid="691dbb0c-f2e5-44ca-8746-417c3a585646" />
          <msh:tableColumn columnName="Диагноз" property="8" guid="691dbb0c-f2e5-44ca-8746-417c3a585646" />
          
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
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-smo_spo" name="Удалить" roles="/Policy/Mis/MedCase/Spo/Delete" />
        <msh:sideLink key="ALT+0" action="/js-smo_visit-findPolyAdmissions" name="Рабочий календарь"
        	roles="/Policy/Mis/MedCase/Visit/View" styleId="selected_menu"
        />
		<msh:sideLink params="id" action="/js-smo_spo-reopenSpo" name="Открыть СПО" title="Открыть СПО" confirm="Открыть СПО?" key="ALT+4" roles="/Policy/Mis/MedCase/Spo/Reopen" />
		<msh:sideLink params="id" action="/js-smo_spo-closeSpo" name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" guid="d84659f7-7ea9-4400-a11c-c83e7d5c578d" key="ALT+5" roles="/Policy/Mis/MedCase/Spo/Close" />
      	<tags:mis_choiceSpo hiddenNewSpo="1" method="unionSpos" methodGetPatientByPatient="getOpenSpoBySmo" service="TicketService" name="moveVisit"  roles="/Policy/Mis/MedCase/Visit/MoveVisitOtherSpo" title="Объединить с другим СПО" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="fbdebbf4-8006-4417-b7df-f23dcf298f62">
        <%-- <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_case" name="Нетрудоспособность" title="Добавить случай нетрудоспособности" guid="ae605283-4519-488c-9d9e-715d1978def2" /> --%>
      </msh:sideMenu>
      <msh:sideMenu title="Администрирование">
	   	<tags:mis_changeServiceStream name="CSS" service="TicketService" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Visit/ChangeServiceStream" />
      	
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
 <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
  </tiles:put>
</tiles:insert>

