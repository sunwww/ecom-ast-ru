<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>



<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="LaboratoryJournal">Поиск лабораторных назначений</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    

        <msh:sideMenu>
                <tags:laboratory_menu currentAction="pres_find"/>
                <tags:prescriptionReportTag name="My" roles="/Policy/Mis/Prescription/ViewInformation"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
    
      <msh:form action="/pres_prescription_find.do" defaultField="number" method="GET">
                <msh:panel>
                	<msh:row>
                	
                	</msh:row>
				    <msh:row>
                        <msh:textField property="number" label="№ назначения" />
                        <msh:textField property="dateFrom" label="от" />
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            <%
            if (request.getParameter("number")!=null && request.getParameter("dateFrom")!=null) {
            %>
            <msh:section title='Результат поиска'>
            	<ecom:webQuery name="listPres" nativeSql="
    select 
    p.id as pid,
    case 
    when p.cancelDate is not null then 'ОТМЕНЕНО: ' ||vpcr.name||' '||coalesce(p.cancelreasontext,'')
    when p.intakeDate is null then 'Назначено'
    when p.transferDate is null then 'Взят биоматериал в отделении'
    when p.prescriptCabinet_id is null then 'Передан биоматериал в лабораторию'
    when mc.workFunctionExecute_id is null then 'Передан в кабинет'
    when mc.workFunctionExecute_id is not null and mc.dateStart is null  then 'Ожидается подтверждение врача КДЛ'
    else 'Выполнено'
    end as comment
    
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , p.materialId||' ('||vsst.code||')' as f4material
    ,coalesce(vsst.name,'---') as f5vsstname
    ,(case when ht.id is not null then '<div id=\"circle\"></div> ' else '' end)||pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as f6birthday
   , ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name||' ('||coalesce(vpt.shortname||')',')') as f7medServicies
   ,wp.lastname||' '||wp.firstname||' '||wp.middlename as f8fioworker
   ,iwp.lastname||' '||iwp.firstname||' '||iwp.middlename as f9intakefioworker
       ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f10dtintake
       ,to_char(p.planStartDate,'dd.mm.yyyy') as f11planStartDate
   ,mc.id as f12mcid
   ,'entityShortView-mis_patient.do?id='||pat.id as f13patid
   ,'entitySubclassShortView-mis_medCase.do?id='||pl.medCase_id as f14sls
   ,coalesce(mlIntake.name,ml.name) as m15lname
  ,  case when mc.dateStart is null and p.cancelDate is null and p.transferDate is not null then coalesce(mc.id,0)||''','''||p.id||''','''||ms.id||''',''saveBioResult' else null end as j16sanaliz
  ,  case when p.medCase_id is null and p.cancelDate is null then ''||p.id||''','''||coalesce(vsst.biomaterial,'-') else null end as j17scanc
   , case when mc.datestart is null then 'НЕ ПОДТВЕРЖДЕННЫЙ РЕЗУЛЬТАТ!!!<br>' else '' end || d.record as f18drecord
   ,case when mc.datestart is not null then p.id end as f19btnAnnul
   ,to_char(p.transferDate,'dd.mm.yyyy')||' '||cast(p.transferTime as varchar(5)) as f20dtintake
   ,case when p.canceldate is not null then p.id end as f21btnUncancel
   ,p.barcodenumber as f22
   ,'js-stac_slo-list_protocols.do?short=Short&id='||pl.medCase_id||'&patient='||pat.id||'&service='||p.medService_id as f23presHistory
    from prescription p
    left join vocprescriptcancelreason vpcr on vpcr.id=p.cancelreason_id
    left join MedCase mc on mc.id=p.medcase_id
    left join Diary d on d.medcase_id=mc.id
    left join PrescriptionList pl on pl.id=p.prescriptionList_id
    left join MedCase slo on slo.id=pl.medCase_id
    left join MedCase sls on sls.id=slo.parent_id
    left join StatisticStub ssSls on ssSls.id=sls.statisticstub_id
    left join StatisticStub ssSlo on ssSlo.id=slo.statisticstub_id
    left join Patient pat on pat.id=slo.patient_id
    left join MedService ms on ms.id=p.medService_id
    left join VocServiceType vst on vst.id=ms.serviceType_id
    left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
    left join WorkFunction wf on wf.id=p.prescriptSpecial_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    left join WorkFunction iwf on iwf.id=p.intakeSpecial_id
    left join Worker iw on iw.id=iwf.worker_id
    left join Patient iwp on iwp.id=iw.person_id
    left join MisLpu ml on ml.id=w.lpu_id
    left join MisLpu mlIntake on mlIntake.id=p.department_id
    left join vocPrescriptType vpt on vpt.id=p.prescripttype_id
    left join hitechMedicalCase ht on ht.medcase_id=slo.id or ht.medcase_id=ANY(select id from medcase where parent_id=sls.id and dtype='DepartmentMedCase')
    where p.intakeDate = to_date('${param.dateFrom}','dd.mm.yyyy')
    and p.materialId='${param.number}'
    and vst.code='LABSURVEY' 
    order by pat.lastname,pat.firstname,pat.middlename
    
"/>
         <msh:table name="listPres" action="javascript:void(0)" idField="1">
	      <msh:tableButton property="19" buttonFunction="annulBioResult" role="/Policy/Mis/Journal/Prescription/LabSurvey/AnnulPrescription" buttonName="Аннулирование" buttonShortName="Аннулировать результат" hideIfEmpty="true"/>
	      <msh:tableButton property="16" buttonFunction="goBioService" role="/Policy/Mis/Journal/Prescription/LabSurvey/LaborantRegistrator" buttonName="Результат" buttonShortName="Ввод результата" hideIfEmpty="true"/>
	      <msh:tableButton property="17" buttonFunction="showBioIntakeCancel" role="/Policy/Mis/Journal/Prescription/LabSurvey/LaborantRegistrator" buttonName="Брак биоматериала" buttonShortName="Брак" hideIfEmpty="true"/>
	      <msh:tableButton property="21" buttonFunction="uncancelBioIntake" role="/Policy/Mis/Journal/Prescription/LabSurvey/AnnulPrescription" buttonName="Отмена брака" buttonShortName="Отменить брак" hideIfEmpty="true"/>
	      <msh:tableColumn columnName="#" property="sn"  />
	      <msh:tableColumn columnName="Ход работ" property="2"  />
	      <msh:tableButton property="22" buttonFunction="printBarcode" buttonName="Печать штрих-кода" buttonShortName="Ш" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableButton property="14" buttonFunction="getDefinition" buttonName="Просмотр данных о госпитализации" buttonShortName="П" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableColumn columnName="ИБ" property="3"  />
	      <msh:tableColumn columnName="Дата напр." property="11"/>
	      <msh:tableColumn columnName="Отделение" property="15"  />
	      <msh:tableColumn columnName="Забор" property="10"/>
	      <msh:tableColumn columnName="Передача в лаб." property="20"/>
	      <msh:tableColumn columnName="Код" property="4"/>  <!-- 'showMyPrescriptionReport('||p.id||',1)' -->
	      <msh:tableButton property="13" buttonFunction="getDefinition" buttonName="Просмотр данных о пациенте" buttonShortName="П" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableButton property="1" buttonFunction="showMyPrescriptionReport" buttonName="История назначения" buttonShortName="О" hideIfEmpty="true" role="/Policy/Mis/Prescription/ViewInformation"/>
	      <msh:tableButton property="23" buttonFunction="getDefinition" buttonName="Просмотр динамики анализа" buttonShortName="Дин" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory"/>
	      <msh:tableColumn columnName="ФИО пациента" property="6"  />
	      <msh:tableColumn columnName="Услуга" property="7"/>
	      <msh:tableColumn columnName="Результат" property="18" cssClass="preCell"/>
	      
	    </msh:table>
            </msh:section>
            <%} %>
            <tags:pres_intake_biomaterial name="Bio" role="/Policy/Mis/Journal/Prescription/LabSurvey/LaborantRegistrator"/>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
  	
    <script type="text/javascript">// <![CDATA[//
    	
    	$('number').focus() ;
    	$('number').select() ;
    	if ($('dateFrom').value=="") {
	    	var currentDate = new Date;
			var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
			var textMonth = currentDate.getMonth()+1;
			var textMonth = textMonth<10?'0'+textMonth:textMonth;
			var textYear =currentDate.getFullYear();
			$('dateFrom').value=textDay+'.'+textMonth+'.'+textYear;
    	}
    	
    	function printBarcode(id)
    	{
    		window.location = "print-barcodeList.do?s=PrintService&m=printBarcodeByPrescription&barcode="+id;
    	}
  	</script>
  
     </tiles:put>

</tiles:insert>