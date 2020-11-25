<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="LaboratoryJournal" title="Передача биоматериала для лабораторных исследований в лабораторию" />
   
  </tiles:put>
  <tiles:put name="side" type="string">
        <msh:sideMenu>
                <tags:laboratory_menu currentAction="pres_transfer"/>
        </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	
  	String typeIntake =ActionUtil.updateParameter("PrescriptJournalTransfer","typeIntake","1", request) ;
  	String typeTransfer =ActionUtil.updateParameter("PrescriptJournalTransfer","typeTransfer","2", request) ;
  	String typeDate =ActionUtil.updateParameter("PrescriptJournalTransfer","typeDate","1", request) ;

    String depSql ="";
    String department = request.getParameter("department");
    if (department!=null && !department.equals(""))
        depSql = " and coalesce(p.department_id,w.lpu_id)='" + department + "' ";
    request.setAttribute("depSql", depSql) ;

    String serviceSubType = "", subTSql = "";
    if (request.getParameter("serviceSubType")!=null)
        serviceSubType =  request.getParameter("serviceSubType");
    if (serviceSubType!=null && !serviceSubType.equals(""))
        subTSql = " and vsst.id=" + serviceSubType;
    request.setAttribute("subTSql", subTSql) ;
  	%>
  	  <msh:form  action="/pres_journal_intake_transfer.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
            <msh:row>
        <td class="label" title="Искать по дате (typeDate)"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeDate" value="1"> направления
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeDate" value="2"> забора
        </td>
      	<td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeDate" value="3"> передачи в лабораторию
        </td>
     </msh:row>
      
      <msh:row>
        <td class="label" title="Забор материала (typeIntake)" colspan="1"><label for="typeIntakeame" id="typeIntakeLabel">Забор:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();">
        	<input type="radio" name="typeIntake" value="1"> был
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" >
        	<input type="radio" name="typeIntake" value="2"> не был
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeIntake" value="3"> отобразить все данные
        </td>
     </msh:row>
      <msh:row>
        <td class="label" title="Передача в лабораторию (typeTransfer)"><label for="typeTransferName" id="typeTransferLabel">Передача в лабораторию:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeTransfer" value="1"> осуществлена
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeTransfer" value="2"> не была произведена (с группировкой по типу биоматериала)
        </td>
       </msh:row>
        <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" >
        	<input type="radio" name="typeTransfer" value="3"> отбракованные
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeTransfer" value="4"> не была произведена (без группировки по типу биоматериала)
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeTransfer" value="5"> все
        </td>

       </msh:row>

        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" fieldColSpan="4" horizontalFill="true" label="Тип назначения" vocName="vocPrescriptType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceSubType"  parentId="LABSURVEY" fieldColSpan="4" horizontalFill="true" label="Тип биоматериала" vocName="vocServiceSubTypeByCode"/>
        </msh:row>
        <msh:row>
            <msh:autoComplete property="cancelReason" fieldColSpan="4" horizontalFill="true" label="Причина брака" vocName="vocPrescriptCancelReason"/>
        </msh:row>
      <msh:row>
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" value="Отобразить данные"/>
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
      	<tags:pres_intake_biomaterial name="Bio" role="/Policy/Mis/Journal/Prescription/LabSurvey/IsCheckTransfer"/>
      	<tags:pres_transferByBarcode name="Barcode" />

    <script type='text/javascript'>
    checkFieldUpdate('typeIntake','${typeIntake}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',3) ;
    checkFieldUpdate('typeTransfer','${typeTransfer}',1) ;
    function checkfrm(type) {
        //если выбрана причина брака и не проставлено отбракованные, проставить отбракованные
        //если выбирается не отбракованные, то убрать причину брака
        var brak = document.getElementsByName('typeTransfer')[2];
        if (($('cancelReason').value) && !brak.checked) {
            if (type)
                brak.checked = true;
            else {
                $('cancelReason').value='';
                $('cancelReasonName').value='';
            }
        }

    	document.forms[0].submit() ;
    }

   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }

    </script>
    <%
    String beginDate = request.getParameter("beginDate") ;
  	if (department!=null && !department.equals("") || serviceSubType.equals("24")) {

  		if (beginDate==null || beginDate.equals("")) {
  			beginDate=DateFormat.formatToDate(new java.util.Date()) ;
  		}
  		String endDate = request.getParameter("endDate") ;
  	  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
    StringBuilder sqlAdd = new StringBuilder() ;
    if (typeIntake!=null && typeIntake.equals("1")) {
		sqlAdd.append(" and p.intakeDate is not null ") ;
	} else if (typeIntake!=null && typeIntake.equals("2")) {
		sqlAdd.append(" and p.intakeDate is null ") ;
	}
    if (typeTransfer!=null && typeTransfer.equals("1")) {
		sqlAdd.append(" and p.transferDate is not null ") ;
		//sqlAdd.append(" and p.transferDate is not null and p.cancelDate is null") ;
	} else if (typeTransfer!=null && (typeTransfer.equals("2")||typeTransfer.equals("4"))) {
		sqlAdd.append(" and p.transferDate is null and p.cancelDate is null") ;
	} else if (typeTransfer!=null && typeTransfer.equals("3")) {
		sqlAdd.append(" and p.cancelDate is not null ") ;
		//sqlAdd.append(" and p.transferDate is null and p.cancelDate is not null ") ; //Пока будем выводить все забракованные (лаборатория от 11-05-2018
	}
    if (typeDate!=null&&typeDate.equals("1")) {
    	request.setAttribute("dateSql", "p.planStartDate") ;
    } else if (typeDate!=null&&typeDate.equals("2")) {
    	request.setAttribute("dateSql", "p.intakeDate") ;
    } else {
    	request.setAttribute("dateSql", "p.transferDate") ;
    }

	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
				, "отделение","deparment","ml.id", request)) ;
		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
				, "тип назначения","prescriptType","vpt.id", request)) ;
		sqlAdd.append(ActionUtil.getValueInfoById("select id, code||' '||name from medservice where id=:id"
				, "исследование","service","ms.id", request)) ;
		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocServiceSubType where id=:id"
				, "биоматериал","serviceSubType","ms.serviceSubType_id", request)) ;
        sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptCancelReason where id=:id"
                , "причина брака","cancelReason","vctr.id", request)) ;
		 StringBuilder title = new StringBuilder() ;
		title.append(request.getAttribute("departmentInfo"))
			.append(" ").append(request.getAttribute("serviceSubTypeInfo")) 
			.append(" ").append(request.getAttribute("prescriptTypeInfo")) 
			.append(" ").append(request.getAttribute("serviceInfo"))
            .append(" ").append(request.getAttribute("cancelReasonInfo")) ;
    request.setAttribute("sqlAdd", sqlAdd.toString()) ;
    request.setAttribute("titleInfo", title.toString()) ;
    %>
    
    <% 
    if (typeTransfer.equals("1")) {
%>

    <msh:section title="${titleInfo}">
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
            	select 
    p.id as pid,
    case 
    when p.cancelDate is not null then 'ОТМЕНЕНО/БРАК'
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
    ,pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as f6birthday
   , ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name||' <b>'||coalesce(vpt.shortname,vpt.name)||'</b>' as f7medServicies
   ,wp.lastname||' '||wp.firstname||' '||wp.middlename as f8fioworker
   ,iwp.lastname||' '||iwp.firstname||' '||iwp.middlename as f9intakefioworker
       ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f10dtintake
       ,to_char(p.planStartDate,'dd.mm.yyyy') as f11planStartDate
   ,mc.id as f12mcid
   ,'entityShortView-mis_patient.do?id='||pat.id as f13patid
   ,'entitySubclassShortView-mis_medCase.do?id='||pl.medCase_id as f14sls
   ,coalesce(mlIntake.name,ml.name) as m15lname
  ,  case when mc.dateStart is null and p.cancelDate is null then coalesce(mc.id,0)||''','''||p.id||''','''||ms.id||''',''saveBioResult' else null end as j16sanaliz
  ,  case when p.medCase_id is null and p.cancelDate is null and p.medcase_id is null then ''||p.id||''','''||coalesce(vsst.biomaterial,'-') else null end as j17scanc
   , case when mc.datestart is null then 'НЕ ПОДТВЕРЖДЕННЫЙ РЕЗУЛЬТАТ!!!<br>' else '' end || d.record as drecord
   ,
    case 
    when p.cancelDate is not null then 'background:red' 
    when mc.workFunctionExecute_id is not null and mc.dateStart is null  then 'background:blue;color:white'
    when mc.workFunctionExecute_id is not null and mc.dateStart is not null  then 'background:green;color:white'
    else ''
    end as f19comment
  ,p.materialPCRid as f20pcr
    from prescription p
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
    left join WorkFunction wfCab on wfCab.id=p.prescriptCabinet_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    left join WorkFunction iwf on iwf.id=p.intakeSpecial_id
    left join Worker iw on iw.id=iwf.worker_id
    left join Patient iwp on iwp.id=iw.person_id
    left join MisLpu ml on ml.id=w.lpu_id
    left join MisLpu mlIntake on mlIntake.id=p.department_id
    left join vocprescripttype vpt on vpt.id=p.prescripttype_id
    left join VocPrescriptCancelReason vctr on vctr.id=p.cancelreason_id
    where p.dtype='ServicePrescription'
    and ${dateSql} between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    ${depSql}
    ${subTSql}
    and vst.code='LABSURVEY'
    ${sqlAdd}
     and ml.id not in (1,391)
    order by pat.lastname,pat.firstname,pat.middlename
    "/>
    <msh:sectionTitle>${titleInfo}</msh:sectionTitle>
        <msh:sectionContent>
	    <msh:table name="list" action="javascript:void(0)" idField="1" styleRow="19" escapeSymbols="false" printToExcelButton="Сохранить в excel">
        <msh:tableButton property="16" buttonFunction="goBioService" role="/Policy/Mis/Journal/Prescription/LabSurvey/LaborantRegistrator" buttonName="Результат" buttonShortName="Ввод результата" hideIfEmpty="true"/>
	      <msh:tableButton property="17" buttonFunction="showBioIntakeCancel" role="/Policy/Mis/Journal/Prescription/LabSurvey/LaborantRegistrator" buttonName="Брак биоматериала" buttonShortName="Брак" hideIfEmpty="true"/>
          <msh:tableColumn columnName="#" property="sn"  />
	      <msh:tableColumn columnName="Ход работ" property="2"  />
	      <msh:tableButton property="14" buttonFunction="getDefinition" buttonName="Просмотр данных о госпитализации" buttonShortName="П" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableColumn columnName="ИБ" property="3"  />
	      <msh:tableColumn columnName="Дата напр." property="11"/>
	      <msh:tableColumn columnName="Отделение" property="15"  />
	      <msh:tableColumn columnName="Забор" property="10"/>
	      <msh:tableColumn columnName="Код" property="4"/>
          <msh:tableColumn columnName="Номер ПЦР" property="20" role="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratoryPCR"/>
	      <msh:tableButton property="13" buttonFunction="getDefinition" buttonName="Просмотр данных о пациенте" buttonShortName="П" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableColumn columnName="ФИО пациента" property="6"  />
	      <msh:tableColumn columnName="Услуга" property="7"/>
	      <msh:tableColumn columnName="Результат" property="18" cssClass="preCell"/>
	      

	    </msh:table>
	    <script type="text/javascript">
	    
	    </script>
    </msh:sectionContent>
    </msh:section>


<%    	
    } else {
    	if (typeTransfer.equals("4")) {request.setAttribute("addByGroup", "p.id,");}
    %>
    <msh:section title="${titleInfo}">
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select 
  replace(list(''||p.id),' ','') as f1pid
  ,  case when p.transferDate is null and p.cancelDate is null then replace(list(''||p.id),' ','')||''','''||coalesce(vsst.biomaterial,'-') else null end as js2
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , coalesce(case when list(p.materialId)='' then coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    
    ||'#'||pat.lastname else list(p.materialId) end) as f4material
    ,coalesce(vsst.name,'---') as f5vsstname
    ,case when ht.id is not null then '<div id=\"circle\"></div> '||pat.lastname else pat.lastname end as f6lastname,pat.firstname as f7firstname,pat.middlename as f8middlename
    ,to_char(pat.birthday,'dd.mm.yyyy') as f9birthday
   ,list(case when vst.code='LABSURVEY' then ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name||' <b>'||coalesce(vpt.shortname,'')||'</b>' else null end) as f10medServicies
   ,list(wp.lastname||' '||wp.firstname||' '||wp.middlename) as f11fioworker
   ,list(distinct iwp.lastname||' '||iwp.firstname||' '||iwp.middlename) as f12intakefioworker
       ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f13dtintake
       ,coalesce(wfCab.groupName,'')||' '||to_char(p.transferDate,'dd.mm.yyyy')||' '||cast(p.transferTime as varchar(5)) as f14dttransfer
       ,to_char(p.cancelDate,'dd.mm.yyyy')||' '||cast(p.cancelTime as varchar(5)) as f15dtcancel
  , case when p.barcodeNumber is not null then 'ШК №'||p.barcodeNumber end as f16_barcode
  ,coalesce(mlIntake.name,ml.name) as m17lname
  ,  case when p.cancelDate is not null then replace(list(''||p.id),' ','') else null end as js18
  ,p.materialPCRid as f19pcr
    from prescription p
    
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
    left join WorkFunction wfCab on wfCab.id=p.prescriptCabinet_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    left join WorkFunction iwf on iwf.id=p.intakeSpecial_id
    left join Worker iw on iw.id=iwf.worker_id
    left join Patient iwp on iwp.id=iw.person_id
    left join MisLpu ml on ml.id=w.lpu_id
    left join MisLpu mlIntake on mlIntake.id=p.department_id
    left join VocPrescriptType vpt on vpt.id=p.prescriptType_id
    left join VocPrescriptCancelReason vctr on vctr.id=p.cancelreason_id
    left join hitechMedicalCase ht on ht.medcase_id=slo.id or ht.medcase_id=ANY(select id from medcase where parent_id=sls.id and dtype='DepartmentMedCase')
    where p.dtype='ServicePrescription'
    and ${dateSql} between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    ${depSql}
    ${subTSql}
    and vst.code='LABSURVEY' 
    
    ${sqlAdd}
     and ml.id not in (1,391)
    group by ${addByGroup}pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    ,p.intakedate,pat.birthday,iwp.lastname,iwp.firstname,iwp.middlename,p.intakeTime
    ,p.transferDate,p.transferTime,vsst.biomaterial,p.cancelDAte,p.cancelTime,wfCab.groupName, p.barcodeNumber, ht.id,mlintake.name,ml.name ,p.materialPCRid
    order by pat.lastname,pat.firstname,pat.middlename
    "/>
    
    <msh:sectionTitle>

        <msh:ifInRole roles="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratoryPCR/Reestr">
            <a id='noteH' href="#bottom" onclick="showNote();" >Показать примечание</a><br>
            <div id="note" style="display:none;">
                <u>Свод</u>
                <p>Фильтр по отделению недоступен</p>
                <u>Печать реестра</u>
                <p>указать только <i>дату</i></p>
                <p>поиск по <i>дате направления</i> (не период, просто первая дата)</p>
                <p>передача в лабораторию <i>не была произведена</i></p>
                <p>передача в лабораторию <i>не была произведена</i></p>
                <p>вывод только <i>не отбракованных</i></p>
                <p>вывод только тех, где <i>осуществлён забор</i></p>
            </div>
            <form  id="printForm" name="printForm" action="print-print-pres_lab_pcrCovid_by_department.do" method="post" target="_blank">
                <input type='hidden' name="sqlText" id="sqlText" value="">
                <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
                <input type='hidden' name="s" id="s" value="PrintService">
                <input type='hidden' name="m" id="m" value="printGroupColumnNativeQuery">
                <input type='hidden' name="groupField" id="groupField" value="5">
                <input type='hidden' name="cntColumn" id="cntColumn" value="1">
                <input type='hidden' name="planStartDate" id="planStartDate" value="${beginDate}">
                <input type="button" value="Реестр приёма-передачи SARS-COV2" onclick="printPresLabCovidReestr(1)">
                <input type="button" value="Реестр направленных SARS-COV2" onclick="printPresLabCovidReestr()">
                <script type="text/javascript">
                    function printPresLabCovidReestr(withoutIntake) {
                        $('sqlText').value=" select p.materialPCRId as f1material" +
                            " ,pat.lastname as f2last,pat.firstname as f3first,pat.middlename as f4middlenam" +
                            " ,to_char(p.intakeDate,'dd.mm.yyyy') as f5dtintake" +
                            " ,dep.name as f6depName" +
                            " ,p.materialPCRId as f7countTable" +
                            " from prescription p" +
                            " left join PrescriptionList pl on pl.id=p.prescriptionList_id" +
                            " left join MedCase slo on slo.id=pl.medCase_id" +
                            " left join Patient pat on pat.id=slo.patient_id" +
                            " left join MedService ms on ms.id=p.medService_id" +
                            " left join VocServiceType vst on vst.id=ms.serviceType_id" +
                            " left join MisLpu dep on dep.id=slo.department_id" +
                            " left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id" +
                            " where p.dtype='ServicePrescription'  " +
                            " and p.planStartDate=to_date('"+$('beginDate').value + "','dd.mm.yyyy')  "+
                            " and vst.code='LABSURVEY'  "+
                            " and p.cancelDate is null ${sqlAdd}";
                        $('sqlText').value += withoutIntake? " and p.intakeDate is not null" : "";
                        $('sqlText').value += " and vsst.code='COVID' " +
                            " group by dep.name,p.materialPCRId,pat.id,p.intakeDate" +
                            " order by dep.name,pat.lastname,pat.firstname,pat.middlename";
                        document.getElementById('printForm').action='print-pres_lab_pcrCovid_by_department.do';
                        document.getElementById('printForm').submit();
                    }
                </script>
            </form>
        </msh:ifInRole>
    </msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="javascript:void(0)" idField="1" selection="multiply" escapeSymbols="false" printToExcelButton="Сохранить в excel">
	     <msh:tableNotEmpty>
	     <msh:ifInRole roles="/Policy/Mis/Journal/Prescription/LabSurvey/IsCheckTransfer">
            <tr>
              <th colspan="15">
                <msh:toolbar>
<input type='button' onclick='javascript: showBarcodeIntakeInfo()' value='Приём биоматериала по штрих-коду'>
                  <a href="javascript:transferInLab()">Переданы в лабораторию</a>
                </msh:toolbar>
              </th>
            </tr>
            </msh:ifInRole>
          </msh:tableNotEmpty>
          <msh:tableButton hideIfEmpty="true" property="2" buttonFunction="showBioIntakeCancel" buttonName="Осуществлен брак" buttonShortName="Брак" role="/Policy/Mis/Journal/Prescription/LabSurvey/IsCheckTransfer"/>
          <msh:tableButton property="18" buttonFunction="unCancel" role="/Policy/Mis/Journal/Prescription/LabSurvey/UnCancelBeforeTransfer" buttonName="Отмена брака" buttonShortName="Отмена брака" hideIfEmpty="true"/>
          <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="Код назн." property="4"/>
	      <msh:tableColumn columnName="ИБ" property="3"  />
	      <msh:tableColumn columnName="Метка биоматериала" property="5"/>
          <msh:tableColumn columnName="Номер ПЦР" property="19" role="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratoryPCR"/>
	      <msh:tableColumn columnName="Фамилия пациента" property="6"  />
	      <msh:tableColumn columnName="Имя" property="7" />
	      <msh:tableColumn columnName="Отчетство" property="8"/>
	      <msh:tableColumn columnName="Дата рождения" property="9"/>
	      <msh:tableColumn columnName="Дата и время забора" property="13"/>
	      <msh:tableColumn columnName="Дата и время приема в лабораторию" property="14"/>
	      <msh:tableColumn columnName="Дата и время отбраковки" property="15"/>
          <msh:tableColumn columnName="Отделение" property="17" role="/Policy/Mis/Journal/Prescription/LabSurvey/TransferToLaboratoryPCR"/>
	      <msh:tableColumn columnName="Список услуг" property="10"/>
	    </msh:table>
	    <script type="text/javascript">
	    
	    </script>
    </msh:sectionContent>
    </msh:section>
  	
  	<%	
    }
  	} else {
  	  	%>
      <H1>Выберите параметры и нажмите <u>Отобразить данные</u> </H1>
  	  	<%	
  	}
  	
	%>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
  	<script type="text/javascript">
        <msh:ifInRole roles="/Policy/Mis/Journal/Prescription/LabSurvey/UnCancelBeforeTransfer">
        //отмена брака
        function unCancel(aPrescriptId) {
            if (confirm('Вы уверены?')) {
                PrescriptionService.unCancelPrescript(aPrescriptId, {
                    callback: function () {
                        window.document.location.reload();
                    }
                });
            }
        }
        </msh:ifInRole>
  		function transferByBarcode() {alert("");
  			
  		}

  	    function transferInLab(aPrescript) {
  	    	if (typeof aPrescript=="undefined") {
  	    		var ids = theTableArrow.getInsertedIdsAsParams("","list") ;
	  	        if (ids) {
                    showBioIntakeCabinet(ids) ;
	  	        } else {
	  	            alert("Нет выделенных пациентов");
	  	        }
  	    	} else {
  	    		showBioIntakeCabinet(aPrescript) ;
  	    	}
  		}
  	    function transferInLabCheck(aCabinet) {
  	    	var cabinet = aCabinet.split(",") ;
  	    	var list = "" ;
  	    	for (var i=0;i<cabinet.length;i++) {
  	    		var cabI = cabinet[i] ;
  	    		var val = getCheckedRadio(document.forms["frmCabinet"],"typeCabinet"+cabI) ;
  	    		
  	    		if (val=="") {
  	    			alert("Выберите кабинеты для всех исследований") ;
  	    			list == null ;
  	    			return;  //fix - если не выбран какой-то кабинет по типу исследований, вернуть на выбор, а не идти дальше
  	    		}
  	    		if (list!="") list += ":" ;
  	    		list += val ;
  	    	}
  	        if (list!=null) {
  	        	
  	        	PrescriptionService.checkTransferService( list, {
  		            callback: function() {
  		           	var tmpList = list.split(":");
  		           	var presList = "";
  		           	var serviceList = "";
  		           	for (var i=0;i<tmpList.length;i++) {
  		           		if (presList!="") {presList+=",";}

  		           		var ttmp =tmpList[i].split("#");
  		           		presList+=ttmp[2];
  		           		if (ttmp.length>3) {
                            if (serviceList!="") {serviceList+=",";}
                            serviceList +=ttmp[3];
                        }

  		           	}
  		            PrescriptionService.setDefaultDiary(presList,serviceList, {
  		            	callback: function (a) {
  		            		window.document.location.reload();
  		            	}
  		            });
  		            	
  		            }
  				});
  	        	
  	        } else {
  	            alert("Нет выделенных пациентов");
  	        }
  	        
  				
  		}
  		function getReason(aReason) {
  			if (+aReason>0) {
				reason=prompt('Введите причину брака:', '') ;
				if (reason==null) {
					return null ;
				} else if (reason.trim()=="") {
					return getReason(aReason) ;
				} else {
					return reason ;
				}
				
  			} else {
  				return "" ;
  			}
  		}
  	    function cancelInLab(aId,aReasonId,aReason) {
  	    	var reason = getReason(aReason) ;
  	    	if (reason!=null) {
  	        	PrescriptionService.cancelService( aId,aReasonId,aReason, { 
  		            callback: function() {
  		            	window.document.location.reload();
  		            }
  				});
  	    	} else {
  	    		cancelBioIntakeInfo();
  	    	}	
  		}

        //показать/скрыть примечание
        function showNote() {
            var note = document.getElementById("note");
            var href = document.getElementById("noteH");
            if (note.style.display=="none") {
                note.style.display="block";
                jQuery(href).text("Скрыть примечание");
            }
            else {
                note.style.display="none";
                jQuery(href).text("Показать примечание");
            }
        }
        if ($('beginDate').value=="") {
            $('beginDate').value=getCurrentDate() ;
        }
  	  serviceSubTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
  	  departmentAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
  	  prescriptTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
      cancelReasonAutocomplete.addOnChangeCallback(function() {checkfrm(1)}) ;
  	</script>
  </tiles:put>
</tiles:insert>

