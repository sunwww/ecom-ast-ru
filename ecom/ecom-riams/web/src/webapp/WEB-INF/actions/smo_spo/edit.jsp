<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  -->
    <msh:form action="/entityParentSaveGoView-smo_spo.do" defaultField="dateStart">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:panel>
        <msh:row>
          <msh:row>
            <msh:checkBox property="noActuality" label="Недействительность" />
          </msh:row>
          <msh:textField property="dateStart" label="Дата начала" />
          <msh:textField property="dateFinish" label="Дата окончания" />
        </msh:row>
        <msh:row>
          <msh:label property="duration" label="Длительность" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="МКБ-10" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="startFunction" label="Кто начал" fieldColSpan="3" horizontalFill="true" />
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="finishFunction" label="Кто завершил" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="ownerFunction" label="Владелец" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_spoForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Direction">
      <msh:section title="Направленные">
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
        <msh:table name="directions" action="entityParentView-smo_visit.do"
        idField="1">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" />
          <msh:tableColumn columnName="Дата" property="2" />
          <msh:tableColumn columnName="Время" property="3" />
          <msh:tableColumn columnName="Раб.функция направителя" property="4" />
          <msh:tableColumn columnName="Кто направил" property="5" />
          <msh:tableColumn columnName="Цель визита" property="6" />
          <msh:tableColumn columnName="Поток обслуживания" property="7" />
          <msh:tableColumn columnName="Актуальность" property="8" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/View">
      <msh:section title="Доп. услуги">
        <ecom:webQuery name="visits" nativeSql="
 select vis.id as visid, vis.dateStart as visdatestart
 ,vwf.name as vwfname 
, pat.lastname || ' ' ||  pat.firstname || ' ' ||  pat.middlename as fio
, vss.name as vssname
,list(ms.code||' '||ms.name) as service
from MedCase vis
    left join WorkFunction wf on vis.workFunctionExecute_id = wf.id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id   
    left join Worker w on wf.worker_id = w.id   
    left join Patient pat on w.person_id = pat.id
    left join VocServiceStream vss on vss.id=vis.serviceStream_id
    left join Diagnosis diag on diag.medcase_id=vis.id

left join medservice ms on ms.id=vis.medservice_id
where vis.parent_id='${param.id}'
and vis.DTYPE='ServiceMedCase'    
and vis.dateStart is not null 
group by vis.id, vis.dateStart,vwf.name, pat.lastname,  pat.firstname,  pat.middlename, vss.name
order by vis.dateStart
        " />
        <msh:table idField="1" name="visits" viewUrl="entityShortView-smo_medService.do" action="entityParentView-smo_medService.do">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" />
          <msh:tableColumn columnName="Дата" property="2" />
          <msh:tableColumn columnName="Раб. функция врача" property="3" />
          <msh:tableColumn columnName="ФИО врача" property="4" />
          <msh:tableColumn columnName="Поток" property="5" />
          <msh:tableColumn columnName="Услуги" property="6" />
          
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/View">
      <msh:section title="Исполненные визиты">
        <ecom:webQuery name="visits" nativeSql="
 select vis.id as visid, vis.dateStart as visdatestart
 ,vwf.name as vwfname 
, pat.lastname || ' ' ||  pat.firstname || ' ' ||  pat.middlename as fio
,vr.name as vrname,vvr.name as vvrname,vss.name as vssname
,list(distinct mkb.code||' '||vpd.name) as mkb
,list(ms.code||' '||ms.name) as service
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
left join MedCase smc on smc.parent_id=vis.id
left join medservice ms on ms.id=smc.medservice_id
where vis.parent_id='${param.id}'
and vis.DTYPE='Visit'    
and vis.dateStart is not null 
group by vis.id, vis.dateStart,vwf.name, pat.lastname,  pat.firstname,  pat.middlename
,vr.name ,vss.name,vvr.name
order by vis.dateStart
        " />
        <msh:table idField="1" name="visits" viewUrl="entityShortView-smo_visit.do" action="entityParentView-smo_visit.do">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" />
          <msh:tableColumn columnName="Дата" property="2" />
          <msh:tableColumn columnName="Раб. функция врача" property="3" />
          <msh:tableColumn columnName="ФИО врача" property="4" />
          <msh:tableColumn columnName="Цель визита" property="5" />
          <msh:tableColumn columnName="Результат" property="6" />
          <msh:tableColumn columnName="Поток" property="7" />
          <msh:tableColumn columnName="Диагноз" property="8" />
          <msh:tableColumn columnName="Услуги" property="9" />
          
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      
            <msh:ifInRole roles="/Policy/Poly/Ticket/View">
      <msh:section title="Талоны">
        <ecom:webQuery name="tickets" nativeSql="
 select vis.id as visid, vis.dateStart as visdatestart
 ,vwf.name as vwfname 
, pat.lastname || ' ' ||  pat.firstname || ' ' ||  pat.middlename as fio
,vr.name as vrname,vvr.name as vvrname,vss.name as vssname
,list(distinct mkb.code||' '||vpd.name) as mkb
,list(ms.code||' '||ms.name) as service
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
left join MedCase smc on smc.parent_id=vis.id 
left join medservice ms on ms.id=smc.medservice_id

where vis.parent_id='${param.id}'
and vis.DTYPE='ShortMedCase'    
and vis.dateStart is not null 
group by vis.id, vis.dateStart,vwf.name, pat.lastname,  pat.firstname,  pat.middlename
,vr.name ,vss.name,vvr.name
order by vis.dateStart
        " />
        <msh:table idField="1" name="tickets" viewUrl="entityEntityView-smo_ticket.do?short=Short" action="entityParentView-smo_ticket.do">
          <msh:tableColumn columnName="Номер" identificator="false" property="1" />
          <msh:tableColumn columnName="Дата" property="2" />
          <msh:tableColumn columnName="Раб. функция врача" property="3" />
          <msh:tableColumn columnName="ФИО врача" property="4" />
          <msh:tableColumn columnName="Цель визита" property="5" />
          <msh:tableColumn columnName="Результат" property="6" />
          <msh:tableColumn columnName="Поток" property="7" />
          <msh:tableColumn columnName="Диагноз" property="8" />
          <msh:tableColumn columnName="Услуги" property="9" />
          
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Prescription/Prescript/View">
      <msh:section title="Листы назначений">
        <ecom:parentEntityListAll formName="pres_prescriptListForm" attribute="prescriptList" />
        <msh:table idField="id" name="prescriptList" action="entityParentView-pres_prescriptList.do">
          <msh:tableColumn columnName="Дата заведения" property="createDate" />
          <msh:tableColumn columnName="Наименование" property="name" />
          <msh:tableColumn columnName="Владелец" property="OwnerInfo" />
        </msh:table>
      </msh:section>
      </msh:ifInRole>
      <msh:ifInRole roles="/Policy/Mis/Oncology/Case/View">
      <msh:section title="Случай онкологического лечения">
        <ecom:webQuery name="list" nativeSql="
                    select oc.id,
                    case when oc.suspiciononcologist=true then 'Да' else 'Нет' end as sus,
                    ort.name as reason,
                    case when oc.distantmetastasis=true then 'Да' else 'Нет' end as disy,
                    n02.name as stad,
                    case when n03.name is not null and n03.name!='' then n03.name else n03.tumorcode end as tumor,
                    case when n04.name is not null and n04.name!='' then n04.name else n04.noduscode end as nodus,
                    case when n05.name is not null and n05.name!='' then n05.name else n05.metastasiscode end as metastasis,
                    cons.name as consil,
                    n13.name as typetreat,
                    oc.mkb
                    from oncologycase oc
                    left join vocOncologyReasonTreat ort on ort.id = oc.vocOncologyReasonTreat_id
                    left join vocOncologyN002 n02 on n02.id = oc.stad_id
                    left join vocOncologyN003 n03 on n03.id = oc.tumor_id
                    left join vocOncologyN004 n04 on n04.id = oc.nodus_id
                    left join vocOncologyN005 n05 on n05.id = oc.metastasis_id
                    left join voconcologyconsilium cons on cons.id = oc.consilium_id
                    left join vocOncologyN013 n13 on n13.id = oc.typeTreatment_id
                    where medcase_id = ${param.id}"/>
        <msh:table viewUrl="entityView-oncology_case_reestr.do?short=Short" name="list"
                   action="entityParentView-oncology_case_reestr.do" idField="1" >
          <msh:tableColumn columnName="#" property="sn"/>
          <msh:tableColumn columnName="Подозрение на ЗНО" property="2"/>
          <msh:tableColumn columnName="Причина обращения" property="3"/>
          <msh:tableColumn columnName="Удаленные метастазы" property="4"/>
          <msh:tableColumn columnName="Стадия" property="5"/>
          <msh:tableColumn columnName="Tumor" property="6"/>
          <msh:tableColumn columnName="Nodus" property="7"/>
          <msh:tableColumn columnName="Metastasis" property="8"/>
          <msh:tableColumn columnName="Консилиум" property="9"/>
          <msh:tableColumn columnName="Тип лечения" property="10"/>
          <msh:tableColumn columnName="Диагноз" property="11"/>
        </msh:table>
      </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_spoForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_spoForm">
      <msh:sideMenu title="СПО">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-smo_spo" name="Изменить" roles="/Policy/Mis/MedCase/Spo/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-smo_spo" name="Удалить" roles="/Policy/Mis/MedCase/Spo/Delete" />
        <msh:sideLink key="ALT+0" action="/js-smo_visit-findPolyAdmissions" name="Рабочий календарь" roles="/Policy/Mis/MedCase/Visit/View" styleId="selected_menu"/>
        <msh:sideLink params="id" action="/js-smo_spo-reopenSpo" name="Открыть СПО" title="Открыть СПО" confirm="Открыть СПО?" key="ALT+4" roles="/Policy/Mis/MedCase/Spo/Reopen" />
		<msh:sideLink params="id" action="/js-smo_spo-closeSpo" name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" key="ALT+5" roles="/Policy/Mis/MedCase/Spo/Close" />
        <msh:sideLink key="ALT+1" params="id" action="/entityParentPrepareCreate-oncology_case_reestr.do" name="Создать онкологический случай" roles="/Policy/Mis/Oncology/Case/Create"/>
        <tags:mis_choiceSpo hiddenNewSpo="1" method="unionSpos" methodGetPatientByPatient="getOpenSpoBySmo" service="TicketService" name="moveVisit"  roles="/Policy/Mis/MedCase/Visit/MoveVisitOtherSpo" title="Объединить с другим СПО" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-smo_spo-cost_case.do?short=Short&id=${param.id}','.do')" name='Цена' title="Просмотр стоимости услуг"         	roles="/Policy/Mis/Contract/Journals/AnalisisMedServices" />
            <msh:sideLink roles="/Policy/Mis/MedCase/MedService/View" name="Мед.услуги"  
    	styleId="viewShort" action="/javascript:getDefinition('entityParentList-smo_medService.do?short=Short&id=${param.id}','.do')"  title='Мед.услуги'
    	/>
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-contract_juridicalContract-account_view_by_patient.do?short=Short&id=${param.id}','.do')" name='Услуги по счету' title="Просмотр услуг по счету" 
        	roles="/Policy/Mis/Contract/Journals/AnalisisMedServices" />        
         
        <tags:contract_getAccount name="ACCOUNT"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <%-- <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_case" name="Нетрудоспособность" title="Добавить случай нетрудоспособности" /> --%>
      </msh:sideMenu>
      <msh:sideMenu title="Администрирование">
	   	<tags:mis_changeServiceStream name="CSS" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Visit/ChangeServiceStream" />
      	
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
 <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
    <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
    <script type="text/javascript" >
      <msh:ifFormTypeIsView formName="smo_spoForm">
      var btn = document.getElementById("ALT_1");
      btn.style.display  = "none";
        OncologyService.checkSPO(${param.id},{
            callback : function(res) {
               if(res){
                    if(confirm("Требуется создать онкологический случай")){
                        document.location.replace("entityParentPrepareCreate-oncology_case_reestr.do?id=${param.id}");
                    }
                   btn.style.display  = "block";
                }
            }
        });
      </msh:ifFormTypeIsView>
    </script>
  </tiles:put>
</tiles:insert>

