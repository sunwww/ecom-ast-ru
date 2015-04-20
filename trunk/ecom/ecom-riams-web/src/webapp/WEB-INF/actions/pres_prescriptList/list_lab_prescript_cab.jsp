<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.Date"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="LaboratoryJournal" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Забор биоматериала для лабораторных исследований" />
   
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	
  	String typeResult =ActionUtil.updateParameter("PrescriptJournalDoc","typeResult","1", request) ;
  	String typeCabinet =ActionUtil.updateParameter("PrescriptJournalDoc","typeCabinet","1", request) ;
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	ActionUtil.getValueBySql("select gwf.id,gwf.groupname from workfunction gwf left join workfunction wf on wf.group_id=gwf.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "group_id","group_name",request) ;
  	Object groupId = request.getAttribute("group_id") ;
  	if (groupId!=null && !groupId.equals("")) {
  	%>
  	  <msh:form action="/pres_journal_prescript_cab_lab.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
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
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
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
    
    <script type='text/javascript'>
    //checkFieldUpdate('typeIntake','${typeIntake}',1) ;
    //checkFieldUpdate('typeMaterial','${typeMaterial}',1) ;
    //checkFieldUpdate('typeTransfer','${typeTransfer}',1) ;
    checkFieldUpdate('typeCabinet','${typeCabinet}',1) ;
    checkFieldUpdate('typeResult','${typeResult}',1) ;
    function checkfrm() {
    	document.forms[0].submit() ;
    }
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
    if ($('beginDate').value=="") {
    	$('beginDate').value=getCurrentDate() ;
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
	    		//alert(123) ;
	        	PrescriptionService.cancelService( aId,aReasonId,aReason, { 
		            callback: function(aResult) {
		            	window.document.location.reload();
		            }
				});
	    	} else {
	    		//alert(321) ;
	    		cancelBioIntakeInfo();
	    	}	
		}
			 
    </script>
    <%
    String beginDate = request.getParameter("beginDate") ;
  	//if (department!=null && !department.equals("")) {
  		
  		if (beginDate==null || beginDate.equals("")) {
  			beginDate=DateFormat.formatToDate(new Date()) ;
  		}
  		String endDate = request.getParameter("endDate") ;
  	  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
    StringBuilder sqlAdd = new StringBuilder() ;
    
    if (typeResult!=null && typeResult.equals("1")) {
    	sqlAdd.append(" and p.medcase_id is null and p.cancelDate is null") ;
    } else if (typeResult!=null && typeResult.equals("2")) {
    	sqlAdd.append(" and mc.dateStart is null and p.cancelDate is null and p.medcase_id is not null and mc.workFunctionExecute_id is null") ;
    } else if (typeResult!=null && typeResult.equals("3")) {
    	sqlAdd.append(" and mc.dateStart is null and p.cancelDate is null and mc.workFunctionExecute_id is not null") ;
    } else if (typeResult!=null && typeResult.equals("4")) {
    	sqlAdd.append("  and mc.dateStart is not null and p.cancelDate is null and mc.workFunctionExecute_id is not null") ;
    } else if (typeResult!=null && typeResult.equals("5")) {
    	sqlAdd.append("  and p.cancelDate is not null ") ;
    }
    if (typeCabinet!=null && typeCabinet.equals("1")) {
    	sqlAdd.append(" and p.prescriptCabinet_id = '"+groupId+"'") ;
    } else {
    	sqlAdd.append(" and p.prescriptCabinet_id in (select g2wf.id from workfunction gwf left join workfunctionservice gwfs on gwfs.workfunction_id=gwf.id left join workfunctionservice g2wfs on g2wfs.medservice_id=gwfs.medservice_id left join workfunction g2wf on g2wf.id=g2wfs.workfunction_id where gwf.id='"+groupId+"') and (select count(*) from WorkFunctionService wfs left join MedService pms on pms.id=wfs.medservice_id where wfs.workfunction_id='"+groupId+"' and pms.id=ms.parent_id)>0") ;
    }
	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
			, "отделение","deparment","ml.id", request)) ;
	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
			, "тип назначения","prescriptType","vpt.id", request)) ;
	sqlAdd.append(ActionUtil.getValueInfoById("select id, code||' '||name from medservice where id=:id"
			, "исследование","service","ms.id", request)) ;
	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocServiceSubType where id=:id"
			, "биоматериал","serviceSubType","ms.serviceSubType_id", request)) ;
	 StringBuilder title = new StringBuilder() ;
	title.append(request.getAttribute("departmentInfo"))
		.append(" ").append(request.getAttribute("serviceSubTypeInfo")) 
		.append(" ").append(request.getAttribute("prescriptTypeInfo")) 
		.append(" ").append(request.getAttribute("serviceInfo")) ;
	
    request.setAttribute("sqlAdd", sqlAdd.toString()) ;
    
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
    end as comment
    
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , p.materialId||' ('||coalesce(vsst.code,'---')||')' as f4material
    ,pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as f5birthday
   , ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name as f6medServicies
   ,wp.lastname ||' ('||ml.name||')' as f7fioworker
      ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5))||' '||iwp.lastname as f8dtintake
       ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f9dtintake
       ,to_char(p.planStartDate,'dd.mm.yyyy') as f10planStartDate
   ,mc.id as f11mcid
   ,'js-pres_prescriptList-pres_by_6_month_patient.do?id='||pat.id as f12patid
   ,'entitySubclassShortView-mis_medCase.do?id='||pl.medCase_id as f13sls
  ,  case when p.medCase_id is null and p.cancelDate is null and p.medcase_id is null then replace(list(''||p.id),' ','')||''','''||coalesce(vsst.biomaterial,'-') else null end as j14scanc
  ,  case when mc.dateStart is null and mc.workFunctionExecute_id is null and p.medcase_id is null and p.cancelDate is null and p.medcase_id is not null then p.id else null end as j15sanaliz
  ,  case when mc.dateStart is null and p.cancelDate is null and p.medcase_id is not null and mc.workFunctionExecute_id is null then p.id else null end as j16enter
  ,  case when mc.dateStart is not null and p.cancelDate is null and mc.workFunctionExecute_id is not null then p.id else null end as j16enter
  
    from prescription p
    left join MedCase mc on mc.id=p.medcase_id
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
    where p.dtype='ServicePrescription'
    and p.planStartDate between to_date('${beginDate}','dd.mm.yyyy') 
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
    ,vsst.biomaterial
    order by pat.lastname,pat.firstname,pat.middlename
    
    "/>
    
    <msh:sectionTitle>

    </msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="javascript:void(0)" idField="1" >
	     <msh:tableButton property="14" hideIfEmpty="true" buttonFunction="showBioIntakeCancel" buttonName="Брак" buttonShortName="Брак"/>
	     <msh:tableButton property="14" hideIfEmpty="true" buttonFunction="checkLabAnalyzed" buttonName="Выполнен анализ" buttonShortName="Анализ"/>
	     <msh:tableButton property="15" hideIfEmpty="true" buttonFunction="goService" buttonName="Заполнен резальтат" buttonShortName="Рез."/>
	     <msh:tableButton property="16" hideIfEmpty="true" buttonFunction="checkControl" buttonName="Результат заведен правильно" buttonShortName="Подт."/>
	      <msh:tableColumn columnName="#" property="sn"  />
	      <msh:tableColumn columnName="Код назн." property="4"/>
	      <msh:tableButton property="13" buttonFunction="getDefinition" buttonName="Просмотр данных о госпитализации" buttonShortName="ИБ" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableButton property="12" buttonFunction="getDefinition" buttonName="Просмотр данных о лабораторных назначениях" buttonShortName="ЛН" hideIfEmpty="true" role="/Policy/Mis/Patient/View"/>
	      <msh:tableColumn columnName="ИБ" property="3"  />
	      <msh:tableColumn columnName="ФИО пациента" property="5"  />
	      <msh:tableColumn columnName="Забор" property="8"/>
	      <msh:tableColumn columnName="Прием в лабораторию" property="9"/>
	      <msh:tableColumn columnName="Исследование" property="6"/>
	      <msh:tableColumn columnName ="Назначил" property="7"/>
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
  		/*function getMaterialId(aMaterialId) {
			aMaterialId=prompt('Введите наименование пробирки:', aMaterialId) ;
			if (aMaterialId==null || aMaterialId=="") {
				if (confirm('Неопределено наименование пробирки. Хотите ввести еще раз?')) {
					return getMaterialId(aMaterialId) ;
				}
			} else {
				return aMaterialId ;
			}
			return null ;
  		}*/
  		
  	    function transferInLab() {
  	    	var ids = theTableArrow.getInsertedIdsAsParams("","list") ;
  	        if (ids) {
  	        	PrescriptionService.checkTransferService( ids, { 
  		            callback: function(aResult) {
  		            	window.document.location.reload();
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
  		function checkLabAnalyzed(aId) {
  			PrescriptionService.checkLabAnalyzed( aId, { 
		            callback: function(aResult) {
		            	window.document.location.reload();
		            }
				});
  		}
  	    function cancelInLab(aId,aReasonId,aReason) {
  	    	var reason = getReason(aReason) ;
  	    	if (reason!=null) {
  	    		//alert(123) ;
  	        	PrescriptionService.cancelService( aId,aReasonId,aReason, { 
  		            callback: function(aResult) {
  		            	window.document.location.reload();
  		            }
  				});
  	    	} else {
  	    		//alert(321) ;
  	    		cancelBioIntakeInfo();
  	    	}	
  		}
    	  serviceSubTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
      	  departmentAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
      	  prescriptTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;

  	</script>
  </tiles:put>
</tiles:insert>
