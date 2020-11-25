<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="LaboratoryJournal" title="Забор биоматериала для лабораторных исследований" />
   
  </tiles:put>
  <tiles:put name="side" type="string">
 <msh:sideMenu>
	<tags:laboratory_menu currentAction="pres_cabinet"/>
</msh:sideMenu>
</tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	
  	String typeResult =ActionUtil.updateParameter("PrescriptJournalDoc","typeResult","1", request) ;
  	String typeCabinet =ActionUtil.updateParameter("PrescriptJournalDoc","typeCabinet","1", request) ;
  	String typeDate =ActionUtil.updateParameter("PrescriptJournalDoc","typeDate","3", request) ;
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	ActionUtil.getValueBySql("select gwf.id,gwf.groupname from workfunction gwf left join workfunction wf on wf.group_id=gwf.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "group_id","group_name",request) ;
  	Object groupId = request.getAttribute("group_id") ;

  	if (groupId!=null && !groupId.equals("")) {
  	%>
  	  <msh:form action="/pres_journal_prescript_cab_lab.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Кабинет (typeCabinet)" colspan="1"><label for="typeCabinetName" id="typeCabinetLabel">Кабинет:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();">
        	<input type="radio" name="typeCabinet" value="1"> свой кабинет
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" >
        	<input type="radio" name="typeCabinet" value="2"> отдел
        </td>
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
        <td class="label" title="Этап исследования (typeResult)"><label for="typeResultName" id="typeResultLabel">Этап выполнения:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeResult" value="1"> передан в лабораторию
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeResult" value="2"> выполнено исследование
        </td>
      	<td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeResult" value="3"> введен результат
        </td>
     </msh:row>
      <msh:row>
      	<td></td>        
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeResult" value="4"> подтвержден врачом КДЛ
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeResult" value="5"> дефектные биоматериалы
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeResult" value="6"> все
        </td>
       </msh:row>

        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" fieldColSpan="4" horizontalFill="true" label="Тип назначения" vocName="vocPrescriptType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentId="LABSURVEY" property="serviceSubType" fieldColSpan="4" horizontalFill="true" label="Тип биоматериала" vocName="vocServiceSubTypeByCode"/>
        </msh:row>        
      <msh:row>
        <msh:textField property="beginDate" label="Дата" />
           <td>
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
      	<tags:pres_intake_biomaterial name="Bio" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory"/>
      	<tags:pres_labDoctorPrescription name="LabDoctor" />
    
    <script type='text/javascript'>
    checkFieldUpdate('typeDate','${typeDate}',3) ;
    checkFieldUpdate('typeCabinet','${typeCabinet}',1) ;
    checkFieldUpdate('typeResult','${typeResult}',1) ;
    
    function checkfrm() {
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
    if ($('beginDate').value=="") {
    	$('beginDate').value=getCurrentDate() ;
    }
			 
    </script>
    <%
    String beginDate = request.getParameter("beginDate") ;
  		
  		if (beginDate==null || beginDate.equals("")) {
  			beginDate=DateFormat.formatToDate(new Date()) ;
  		}
  		String endDate = request.getParameter("endDate") ;
  	  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
    StringBuilder sqlAdd = new StringBuilder() ;
    if ("1".equals(typeDate)) {
    	request.setAttribute("dateSql", "p.planStartDate") ;
    } else if ("2".equals(typeDate)) {
    	request.setAttribute("dateSql", "p.intakeDate") ;
    } else {
    	request.setAttribute("dateSql", "p.transferDate") ;
    }
    if ("1".equals(typeResult)) {
    	sqlAdd.append(" and p.medcase_id is null and p.cancelDate is null") ;
    } else if ("2".equals(typeResult)) {
    	sqlAdd.append(" and mc.dateStart is null and p.cancelDate is null and p.medcase_id is not null and mc.workFunctionExecute_id is null") ;
    } else if ("3".equals(typeResult)) {
    	sqlAdd.append(" and mc.dateStart is null and p.cancelDate is null and mc.workFunctionExecute_id is not null") ;
    } else if ("4".equals(typeResult)) {
    	sqlAdd.append("  and mc.dateStart is not null and p.cancelDate is null and mc.workFunctionExecute_id is not null") ;
    } else if ("5".equals(typeResult)) {
    	sqlAdd.append("  and p.cancelDate is not null ") ;
    }
    if ("1".equals(typeCabinet)) {
    	sqlAdd.append(" and p.prescriptCabinet_id = '").append(groupId).append("'");
    } else {
    	sqlAdd.append(" and p.prescriptCabinet_id in (select g2wf.id from workfunction gwf left join workfunctionservice gwfs on gwfs.workfunction_id=gwf.id left join workfunctionservice g2wfs on g2wfs.medservice_id=gwfs.medservice_id left join workfunction g2wf on g2wf.id=g2wfs.workfunction_id where gwf.id='").append(groupId).append("') and (select count(*) from WorkFunctionService wfs left join MedService pms on pms.id=wfs.medservice_id where wfs.workfunction_id='").append(groupId).append("' and pms.id=ms.parent_id)>0");
    }
	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
			, "отделение","department","ml.id", request)) ;
	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
			, "тип назначения","prescriptType","vpt.id", request)) ;
	sqlAdd.append(ActionUtil.getValueInfoById("select id, code||' '||name from medservice where id=:id"
			, "исследование","service","ms.id", request)) ;
	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocServiceSubType where id=:id"
			, "биоматериал","serviceSubType","ms.serviceSubType_id", request)) ;
		String serviceSubType = "";
		if (request.getParameter("serviceSubType")!=null)
			serviceSubType =  request.getParameter("serviceSubType");
		request.setAttribute("serviceSubType", serviceSubType) ;
		if (!serviceSubType.equals("24"))
			sqlAdd.append(" and vsst.code<>'COVID'");
		request.setAttribute("sqlAdd", sqlAdd.toString()) ;
		String title = request.getAttribute("departmentInfo") +
				" " + request.getAttribute("serviceSubTypeInfo") +
				" " + request.getAttribute("prescriptTypeInfo") +
				" " + request.getAttribute("serviceInfo");
		request.setAttribute("titleInfo", title) ;
    %>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select 
    p.id as pid,
    case 
    when p.cancelDate is not null then 'ОТМЕНЕНО' 
   
    when p.medcase_id is null then 'Передан биоматериал в лабораторию'
    when mc.dateStart is null and p.cancelDate is null and p.medcase_id is not null and mc.workFunctionExecute_id is null then 'В обработке'
    when mc.dateStart is null and p.cancelDate is null and p.medcase_id is not null and mc.workFunctionExecute_id is not null then 'Ожидается подтверждение врача КДЛ'
    when mc.dateStart is not null and p.cancelDate is null and p.medcase_id is not null and mc.workFunctionExecute_id is not null then 'Выполнено'
    else null
    end as f2_comment
    
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , p.materialId||' ('||coalesce(vsst.code,'---')||')' as f4material
    ,case when ht.id is not null then '<div id=\"circle\"></div> '||pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy')
    else pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy')  end as f5birthday
   , ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name as f6medServicies
   ,wp.lastname ||' ('||ml.name||')' as f7fioworker
      ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5))||' '||iwp.lastname as f8dtintake
       ,to_char(p.transferDate,'dd.mm.yyyy')||' '||cast(p.transferTime as varchar(5)) as f9dtransferintake
       ,to_char(p.planStartDate,'dd.mm.yyyy') as f10planStartDate
   ,mc.id as f11mcid
   ,'js-pres_prescriptList-pres_by_6_month_patient.do?id='||pat.id as f12patid
   ,'entitySubclassShortView-mis_medCase.do?id='||pl.medCase_id as f13sls
  ,  case when p.medCase_id is null and p.cancelDate is null and p.transferdate is not null  then replace(list(''||p.id),' ','')||''','''||coalesce(vsst.biomaterial,'-') else null end as j14scanc
  ,  case when mc.dateStart is null and p.medcase_id is not null and p.cancelDate is null then mc.id||''','''||p.id||''','''||ms.id||''',''saveBioResult' else null end as j15sanaliz
  ,  case when mc.dateStart is null and p.cancelDate is null and mc.workFunctionExecute_id is not null then mc.id||''','''||d.id||''','''||p.id else null end as j16enter
  , case when p.canceldate is not null then list(coalesce(vpcr.name,'') ||' '||coalesce(p.cancelreasontext,'')) else d.record end as d17record 
  ,  case when p.medCase_id is null and p.cancelDate is null and p.medcase_id is null and p.transferdate is not null then '0'','''||p.id||''','''||ms.id||''',''saveBioResult' else null end as j18scanc
  ,
    case 
    when p.cancelDate is not null then 'background:red' 
    when mc.workFunctionExecute_id is not null and mc.dateStart is null  then 'background:#6CC4F0;color:black'
    when mc.workFunctionExecute_id is null then 'background:#7AE673;color:black'
    else ''
    end as f19_colorcomment
    ,case when p.canceldate is null and (p.medcase_id is null or mc.datestart is null) then p.id else null end as f20_prescriptionLabDoctorButton
    ,case when mc.datestart is null and p.cancelDate is null then replace(list(''||p.id),' ','')||''','''||coalesce(vsst.biomaterial,'-') else null end as j21cancelAllTime
    , case when p.medcase_id is not null then 'Выполнил: '||suLab.fullName ||' '|| to_char(mc.createdate,'dd.MM.yyyy')||' '||cast(mc.createTime as varchar(5))
      || case when mc.datestart is not null then ' Подтвердил: '||suLabDoc.fullName||' '||to_char(mc.editdate,'dd.MM.yyyy')||' '||cast(mc.edittime as varchar(5)) else '' end else '' end as f22_executeinfo
      ,'js-stac_slo-list_protocols.do?short=Short&id='||pl.medCase_id||'&patient='||pat.id||'&service='||p.medService_id as f23presHistory
  ,p.materialPCRid as f24pcr
    from prescription p
    left join VocPrescriptCancelReason vpcr on vpcr.id=p.cancelreason_id
    left join VocPrescriptType vpt on vpt.id=p.prescriptType_id
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
    left join hitechMedicalCase ht on ht.medcase_id=slo.id or ht.medcase_id=ANY(select id from medcase where parent_id=sls.id and dtype='DepartmentMedCase')
    left join secuser suLab on suLab.login=mc.username
    left join secuser suLabDoc on suLabDoc.login=mc.editUsername
    where p.dtype='ServicePrescription'
    and ${dateSql} between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    and vst.code='LABSURVEY' 
    and p.transferdate is not null 
    
    ${sqlAdd}
    group by p.id,pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    ,p.intakedate,pat.birthday,iwp.lastname,iwp.firstname,iwp.middlename,p.intakeTime
    ,p.transferDate,p.transferTime,ms.parent_id,ms.id,ms.code,ms.name,ms.additionCode
    ,p.medCase_id,mc.workFunctionExecute_id,mc.dateStart,vsst.code
    ,wp.lastname,wp.middlename,wp.firstname,vwf.name,mc.id,ml.name
    ,p.canceldate,p.materialid,p.planstartdate
    ,vsst.biomaterial,d.record,d.id, ht.id, suLab.fullName, suLabDoc.fullName,p.materialPCRid
    order by pat.lastname,pat.firstname,pat.middlename
    
    "/>
    
    <msh:sectionTitle>${titleInfo}</msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="javascript:void(0)" idField="1" styleRow="19" >
	     <msh:tableButton property="21" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" buttonFunction="hideRow(this); showBioIntakeCancel" buttonName="Брак" buttonShortName="Брак" />
	     <msh:tableButton property="14" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" buttonFunction="hideRow(this); checkLabAnalyzed" buttonName="Анализ" buttonShortName="Анализ" />
	     <msh:tableButton property="18" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" buttonFunction="hideRow(this); goBioService" buttonName="Подтвердить выполнение результата и ввести результат" buttonShortName="Ан.+Рез." />
         <msh:tableButton property="18" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratoryPCR" buttonFunction="hideRow(this); goBioService" buttonName="Подтвердить выполнение результата и ввести результат" buttonShortName="Рез. ПЦР" />
	     <msh:tableButton property="15" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" buttonFunction="hideRow(this); goBioService" buttonName="Ввести результат" buttonShortName="Рез." />
	     <msh:tableButton property="16" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" buttonFunction="hideRow(this); checkLabControl" buttonName="Результат заведен правильно" buttonShortName="Подт." />
	      <msh:tableColumn columnName="#" property="sn"  />
	      <msh:tableColumn columnName="Код назн." property="4"/>
			<msh:tableColumn columnName="Номер ПЦР" property="24" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratoryPCR"/>
	      <msh:tableButton property="13" buttonFunction="getDefinition" buttonName="Просмотр данных о госпитализации" buttonShortName="ИБ" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableButton property="12" buttonFunction="getDefinition" buttonName="Просмотр данных о лабораторных назначениях" buttonShortName="ЛН" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory"/>
	      <msh:tableButton property="23" buttonFunction="getDefinition" buttonName="Просмотр динамики анализа" buttonShortName="Дин" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory"/>
	      <msh:tableColumn columnName="ИБ" property="3"  />
	      <msh:tableColumn columnName="ФИО пациента" property="5"  />
	      <msh:tableColumn columnName ="Результат" property="17" cssClass="preCell"/>
	      <msh:tableColumn columnName="Забор" property="8"/>
	      <msh:tableColumn columnName="Прием в лабораторию" property="9"/>
	      <msh:tableColumn columnName="Исследование" property="6"/>
	      <msh:tableColumn columnName ="Назначил" property="7"/>
	      <msh:tableColumn columnName ="Выполнил" property="22"/>
	      <msh:tableButton property="20" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" buttonFunction="showLabDoctorDirMedService" buttonName="Добавить анализ" buttonShortName="Доб.А"/>
		  <msh:tableButton property="21" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/DoctorLaboratory" buttonFunction="makePatology" buttonName="Критическая патология" buttonShortName="Критическая патология" />

		</msh:table>
	    <script type="text/javascript">
	    
	    </script>
    </msh:sectionContent>
    </msh:section>
  	
  	<%	
  	} else {
  	  	%>
<H1>НЕПРАВИЛЬНО НАСТРОЕН ПРОФИЛЬ ПОЛЬЗОВАТЕЛЯ!!! ОБРАТИТЕСЬ К АДМИНИСТРАТОРУ!!!!</H1>  	  	
  	  	<%	
  	}
  	
	%>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
  	<script type="text/javascript">
  	var fldJson = null ;
  		function hideRow(btn) {
            jQuery(btn).parent().parent().fadeTo(0,0.2);
        }

  		function checkLabControl(aSmoId,aProtocolId,aPrescriptId) {
  			PrescriptionService.checkLabControl(aSmoId,aProtocolId,aPrescriptId, {
  				callback: function () {
  					//window.document.location.reload();
  				}
  			}) ;
  		}
  		
  		function checkLabAnalyzed(aId) {
  			PrescriptionService.checkLabAnalyzed( aId, { 
		            callback: function() {
		            //	window.document.location.reload();
		            }
				});
  		}
  		
    	  serviceSubTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
      	  departmentAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
      	  prescriptTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;

      	  function makePatology(prId) {
      	      if (confirm("Установить патологию этому назначению?")) {
                  PrescriptionService.patologyService(prId, {
                      callback: function (res) {
                          showToastMessage(res, null, true, false, 3000);
                      }
                  });
              }
		  }

  	</script>
  </tiles:put>
</tiles:insert>
