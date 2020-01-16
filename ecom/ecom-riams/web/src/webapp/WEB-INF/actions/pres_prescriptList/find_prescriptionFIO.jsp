<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>



<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="LaboratoryJournal">Поиск лабораторных назначений по ФИО</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    

        <msh:sideMenu>
                <tags:laboratory_menu currentAction="pres_find"/>
                <tags:prescriptionReportTag name="My" roles="/Policy/Mis/Prescription/ViewInformation"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
        String typeDate = ActionUtil.updateParameter("find_prescriptionFIO","typeDate", "1",request);
        if (typeDate!=null) {
            if (typeDate.equals("2")) typeDate = "sls.dateFinish";
            else if (typeDate.equals("1")) typeDate = "sls.datestart";
        }
        String dateBegin = request.getParameter("dateBegin") ;
        if (dateBegin!=null && !dateBegin.equals("")) {
            request.setAttribute("dateBegin",dateBegin);
        }
        String dateEnd = request.getParameter("dateEnd") ;
        if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
        if (dateEnd!=null && !dateEnd.equals("")) {
            request.setAttribute("dateEnd",dateEnd);
        }
        String f = request.getParameter("filterAdd") ;
        if (f!=null && !f.equals("")) {
            request.setAttribute("f",f+"%");
        }
        else
            request.setAttribute("f","%");
        String i = request.getParameter("filterAdd1") ;
        if (i!=null && !i.equals("")) {
            request.setAttribute("i",i+"%");
        }
        else
            request.setAttribute("i","%");
        String o = request.getParameter("filterAdd2") ;
        if (o!=null && !o.equals("")) {
            request.setAttribute("o",o+"%");
        }
        else
            request.setAttribute("o","%");
        request.setAttribute("typeD",typeDate);
    %>
      <msh:form action="/pres_prescription_findFIO.do" defaultField="number" method="GET">
                <msh:panel>
                	<msh:row>
                	
                	</msh:row>
                    <msh:row>
                        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                    </msh:row>
				    <msh:row>
                        <msh:textField property="filterAdd" label="Фамилия" />
                        <msh:textField property="filterAdd1" label="Имя" />
                        <msh:textField property="filterAdd2" label="Отчество" />
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>

                    <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
                        <td onclick="this.childNodes[1].checked='checked';">
                            <input type="radio" name="typeDate" value="1">  поступления
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                            <input type="radio" name="typeDate" value="2">  выписки
                        </td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            <%
                if (request.getParameter("typeDate")!=null && !request.getParameter("typeDate").equals("") &&
                        request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
            %>
            <msh:section title='Результат поиска'>
            	<ecom:webQuery name="listPres" nativeSql="
    select 
    p.id as f1_pid,
    case 
    when p.cancelDate is not null then 'ОТМЕНЕНО: ' ||vpcr.name||' '||coalesce(p.cancelreasontext,'')
    when p.intakeDate is null then 'Назначено'
    when p.transferDate is null then 'Взят биоматериал в отделении'
    when p.prescriptCabinet_id is null then 'Передан биоматериал в лабораторию'
    when mc.workFunctionExecute_id is null then 'Передан в кабинет'
    when mc.workFunctionExecute_id is not null and mc.dateStart is null  then 'Ожидается подтверждение врача КДЛ'
    else 'Выполнено'
    end as f2_comment
    
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3_codemed
    , p.materialId||' ('||vsst.code||')' as f4_material
    ,coalesce(vsst.name,'---') as f5_vsstname
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
   , case when mc.datestart is null then 'НЕ ПОДТВЕРЖДЕННЫЙ РЕЗУЛЬТАТ!!!<br>' else '' end || d.record as f18_drecord
   ,case when mc.datestart is not null then p.id end as f19_btnAnnul
   ,to_char(p.transferDate,'dd.mm.yyyy')||' '||cast(p.transferTime as varchar(5)) as f20dtintake
   ,case when p.canceldate is not null then p.id end as f21_btnUncancel
   ,p.barcodenumber as f22
   , case when p.medcase_id is not null then 'Выполнил: '||suLab.fullName ||' '|| to_char(mc.createdate,'dd.MM.yyyy')||' '||cast(mc.createTime as varchar(5))
      || case when mc.datestart is not null then ' Подтвердил: '||suLabDoc.fullName||' '||to_char(mc.editdate,'dd.MM.yyyy')||' '||cast(mc.edittime as varchar(5)) else '' end else '' end as f23_executeinfo
   ,'js-stac_slo-list_protocols.do?short=Short&id='||pl.medCase_id||'&patient='||pat.id||'&service='||p.medService_id as f24presHistory
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
    left join secuser suLab on suLab.login=mc.username
    left join secuser suLabDoc on suLabDoc.login=mc.editUsername
    where  ${typeD} between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
    and pat.lastname like upper('${f}') and pat.firstname like upper('${i}') and pat.middlename like upper('${o}')
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
	      <msh:tableColumn columnName="Код" property="4"/>
	      <msh:tableButton property="13" buttonFunction="getDefinition" buttonName="Просмотр данных о пациенте" buttonShortName="П" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableButton property="1" buttonFunction="showMyPrescriptionReport" buttonName="История назначения" buttonShortName="О" hideIfEmpty="true" role="/Policy/Mis/Prescription/ViewInformation"/>
	      <msh:tableButton property="24" buttonFunction="getDefinition" buttonName="Просмотр динамики анализа" buttonShortName="Дин" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory"/>
	      <msh:tableColumn columnName="ФИО пациента" property="6"  />
	      <msh:tableColumn columnName="Услуга" property="7"/>
	      <msh:tableColumn columnName="Результат" property="18" cssClass="preCell"/>
	      <msh:tableColumn columnName="Выполнение" property="23" />

	    </msh:table>
            </msh:section>
            <%} %>
            <tags:pres_intake_biomaterial name="Bio" role="/Policy/Mis/Journal/Prescription/LabSurvey/LaborantRegistrator"/>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
  	
    <script type="text/javascript">
        function checkFieldUpdate(aField,aValue,aDefaultValue) {
            eval('var chk =  document.forms[0].'+aField) ;
            var aMax=chk.length ;
            //alert(aField+" "+aValue+" "+aMax+" "+chk) ;
            if ((+aValue)==0 || (+aValue)>(+aMax)) {
                chk[+aDefaultValue-1].checked='checked' ;
            } else {
                chk[+aValue-1].checked='checked' ;
            }
        }
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    	function printBarcode(id)
    	{
    		window.location = "print-barcodeList.do?s=PrintService&m=printBarcodeByPrescription&barcode="+id;
    	}
  	</script>
  
     </tiles:put>

</tiles:insert>