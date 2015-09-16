<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-smo_short_spo.do" defaultField="dateStart">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="patient" />
      <msh:hidden property="medcard" />
            <msh:hidden property="otherTicketDates"/>
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
      <msh:panel guid="panel">



        <msh:ifFormTypeIsNotView formName="smo_short_spoForm">
        
        
        
         <msh:row>
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>

     

        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="smo_short_spoForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
	        
		        <msh:row>
		          <msh:autoComplete parentId="smo_short_spoForm.medcard" vocName="workFunctionByTicket" property="ownerFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
		        </msh:row>
	        
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" guid="e5ac1267-bc69-44b2-8aba-b7455ac064c4" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="workPlaceType" label="Место обслуживания" horizontalFill="true" guid="18063077-15e8-4e4a-8679-ff79de589a72" />
        </msh:row>

        
        <msh:row guid="6d8642e8-756a-482f-a561-a9b474ef6c50">
          <msh:autoComplete vocName="vocReason" property="visitReason" label="Цель посещения" horizontalFill="true"  />
          <msh:autoComplete vocName="vocVisitResult" property="visitResult" label="Результат обращения" horizontalFill="true" />
        </msh:row>
        <msh:row guid="16f1e99-4017-4385-87c1-bf5895e2">
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" guid="ddc10e76-8ee913984f" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        	<msh:ifFormTypeIsCreate formName="smo_short_spoForm">
	        <msh:row>
	        	<msh:textField property="mkb" label="Код МКБ" />
	        </msh:row>
	        </msh:ifFormTypeIsCreate>
        </msh:ifInRole>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true" guid="9818fb43-33d1-4fe9-a0b4-2b04a9eee955" />
        </msh:row>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:textField property="concludingDiagnos" label="Диагноз" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="4bd126b5-2316-42c4-bcb7-ccf5108b2c27">
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" guid="bf850705-5557-438e-b56e-33d59b1618e4" />
          <msh:autoComplete vocName="vocTraumaType" property="concludingTrauma" label="Травма" horizontalFill="true" guid="eedb1042-1861-426e-a0ec-6151c3933dd1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Ambulance">
	        <msh:row>
	        	<msh:autoComplete vocName="vocAmbulance" property="ambulance" label="Бригада СП" horizontalFill="true" />
	        	<msh:autoComplete vocName="vocVisitOutcome" property="visitOutcome" label="Исход СП" horizontalFill="true" />
	        </msh:row>
        </msh:ifInRole>
        <msh:row guid="7dfb3b2c-407d-48f1-9e70-76cb3328f5f5">
        	<msh:autoComplete property="mkbAdc" vocName="vocMkbAdc" parentAutocomplete="idc10" label="Доп.код"/>
        	<msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        <msh:row>
	   	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medServices" vocName="medServiceForSpec" colSpan="6"/>
	    </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Соп. заболевания" property="concomitantDiseases" colSpan="6" guid="1204d6c4-a3ff-44aa-a698-b99816d10337" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата начала" property="dateStart" fieldColSpan="1" /><%-- 
          <msh:textField label="Дата окончания" property="dateFinish" fieldColSpan="1"/> --%>
        
        	<td><input type="button" value="Добавить дату" onclick="addOtherDate()"/></td>
        </msh:row>
        <msh:row>
        <table id="otherDates">
        	
        </table>
        </msh:row>
        
       </msh:ifFormTypeIsNotView>
	    
        
	    
        <msh:row>
         <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
        </msh:row>
        
       
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_short_spoForm">
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
left join Worker ow on owf.worker_id = ow.id 
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
      
            <msh:ifInRole roles="/Policy/Poly/Ticket/View">
      <msh:section title="Талоны" guid="b5483338-a80a-45a8-881e-0f17c8e752d7">
        <ecom:webQuery name="tickets" nativeSql="
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
and vis.DTYPE='ShortMedCase'    
and vis.dateStart is not null 
group by vis.id, vis.dateStart,vwf.name, pat.lastname,  pat.firstname,  pat.middlename
,vr.name ,vss.name,vvr.name
order by vis.dateStart
        " guid="e0eb2cf2-270c-43ac-9bae-f59ec0866558" />
        <msh:table idField="1" name="tickets" viewUrl="entityEntityView-smo_ticket.do?short=Short" action="entityParentView-smo_ticket.do" guid="29d25014-7b5a-4030-846b-52223513c331">
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
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_short_spoForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_short_spoForm" guid="625411c7-8b7e-4d5b-acae-4fe679e24094">
      <msh:sideMenu guid="sideMenu-123" title="СПО">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_short_spo" name="Изменить" roles="/Policy/Mis/MedCase/Spo/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-smo_short_spo" name="Удалить" roles="/Policy/Mis/MedCase/Spo/Delete" />

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
 	<msh:ifFormTypeIsNotView formName="smo_short_spoForm">
      	<script type="text/javascript"> 
        onload=function(){
        	if ($('otherTicketDates').value!=null&&$('otherTicketDates').value!='') {
        		var arr = $('otherTicketDates').value.split(":");
        		for (var i=0;i<arr.length;i++) {
        			addRow(arr[i]);
        		}
        	}
        }
        function addOtherDate() {
            var otherDate = $('dateStart').value;
            if (otherDate!='') {
            TicketService.getCrossSPO(otherDate,$('patient').value,$('ownerFunction').value, {
            callback: function(aResult) {
            if (aResult!=null&&aResult!='') {
            var arr = aResult.split(':');
            if ($('parent')!=null&&$('parent').value!=arr[0]) {
          if (confirm('Выбранная дата('+otherDate+') пересекается с закрытым СПО (Период с '+arr[1]+' по '+arr[2]+'). Продолжить?')) {
          } else {
            return;
          }
          }
            }
            var dates = document.getElementById('otherDates').childNodes;
                  var l = dates.length;
                  for (var i=1; i<l;i++) {
                 	 if (otherDate==dates[i].childNodes[0].innerHTML) {
                 		 alert ("Такая дата ("+otherDate+") уже есть в обращении");
                  	return;
                    }                 
                 }
                  addRow (otherDate);
            }
            });      
            }      
         }
      	function createOtherDates() {
      		var dates = document.getElementById('otherDates').childNodes;
      		var str = ""; $('otherTicketDates').value='';
      		for (var i=1;i<dates.length;i++) {
      			str+=dates[i].childNodes[0].innerHTML+":";
      		}
      		str=str.length>0?str.trim().substring(0,str.length-1):"";
      		$('otherTicketDates').value=str;
      	}
      	function addRow (aValue) {
      		var table = document.getElementById('otherDates');
      		var row = document.createElement('TR');
      		var td = document.createElement('TD');
      		var tdDel = document.createElement('TD');
      		table.appendChild(row);
      		row.appendChild(td);
      		td.innerHTML=""+aValue;
      		row.appendChild(tdDel);
      		tdDel.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);createOtherDates()' value='Удалить' />";
      		createOtherDates();
      	}
      	
    </script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

