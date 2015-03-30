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
    <msh:title mainMenu="Journals" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Забор биоматериала для лабораторных исследований" />
   
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	ActionUtil.getValueBySql("select lpu.id,lpu.name from mislpu lpu left join worker w on w.lpu_id=lpu.id left join workfunction wf on wf.worker_id=w.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "lpu_id","lpu_name",request) ;
  	Object lpu = request.getAttribute("lpu_id") ;
  	String typeIntake =ActionUtil.updateParameter("PrescriptJournal","typeIntake","2", request) ;
  	String typeMaterial =ActionUtil.updateParameter("PrescriptJournal","typeMaterial","1", request) ;
    String typeTransfer =ActionUtil.updateParameter("PrescriptJournal","typeTransfer","2", request) ;

  	if (lpu!=null && !lpu.equals("")) {
  		String beginDate = request.getParameter("beginDate") ;
  		if (beginDate==null || beginDate.equals("")) {
  			beginDate=DateFormat.formatToDate(new Date()) ;
  		}
  		String endDate = request.getParameter("endDate") ;
  	  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
  	%>
  	<tags:pres_intakeDate name="Biomat"/>
  	  <msh:form action="/pres_journal_intake.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Забор материала (typeIntake)" colspan="1"><label for="typeIntakeame" id="typeIntakeLabel">Забор:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeIntake" value="1"> был
        </td>
        <td onclick="this.childNodes[1].checked='checked';" >
        	<input type="radio" name="typeIntake" value="2"> не был
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeIntake" value="3"> отобразить все данные
        </td>
     </msh:row>
      
      <msh:row>
        <td class="label" title="Передача в лабораторию (typeTransfer)"><label for="typeTransferName" id="typeTransferLabel">Передача в лабораторию:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeTransfer" value="1"> осуществлена
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeTransfer" value="2"> не была произведена
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeTransfer" value="3"> все
        </td>

       </msh:row>
     <%--
      <msh:row>
        <td class="label" title="Наименование пробирки (typeMaterial)" colspan="2"><label for="typeMaterial" id="typeMaterialLabel">Пробирка:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMaterial" value="1"> №стат.карты, Фамилия пациента
        </td>
        <td onclick="this.childNodes[1].checked='checked';" >
        	<input type="radio" name="typeMaterial" value="2"> штрих-код
        </td>

       </msh:row>
       --%>
        
      <msh:row>
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <script type='text/javascript'>
    checkFieldUpdate('typeIntake','${typeIntake}',1) ;
    //checkFieldUpdate('typeMaterial','${typeMaterial}',1) ;
    checkFieldUpdate('typeTransfer','${typeTransfer}',1) ;

   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[1].'+aField) ;
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

			 
    </script>
    <%
    StringBuilder sqlAdd = new StringBuilder() ;
    if (typeIntake!=null && typeIntake.equals("1")) {
		sqlAdd.append(" and p.intakeDate is not null ") ;
	} else if (typeIntake!=null && typeIntake.equals("2")) {
		sqlAdd.append(" and p.intakeDate is null ") ;
	}
    if (typeTransfer!=null && typeTransfer.equals("1")) {
		sqlAdd.append(" and p.transferDate is not null ") ;
	} else if (typeTransfer!=null && typeTransfer.equals("2")) {
		sqlAdd.append(" and p.transferDate is null ") ;
	}
    request.setAttribute("sqlAdd", sqlAdd.toString()) ;
    %>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select 
    case when p.intakeDate is null then list(''||p.id) else null end as js1
    ,case when p.intakeDate is null then null else list(''||p.id) end as js2
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , coalesce(case when list(p.materialId)='' then coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    
    ||'#'||pat.lastname else list(p.materialId) end) as f4material
    ,coalesce(vsst.name,'---') as f5vsstname
    ,pat.lastname as f6lastname,pat.firstname as f7firstname,pat.middlename as f8middlename
    ,to_char(pat.birthday,'dd.mm.yyyy') as f9birthday
   ,list(case when vst.code='LABSURVEY' then ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name else null end) as f10medServicies
   ,list(wp.lastname||' '||wp.firstname||' '||wp.middlename) as f11fioworker
   ,list(distinct iwp.lastname||' '||iwp.firstname||' '||iwp.middlename) as f12intakefioworker
       ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f13dtintake
   
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
    and coalesce(p.department_id,w.lpu_id)='${lpu_id}' 
    and p.cancelDate is null ${sqlAdd}
    group by pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    ,p.intakedate,pat.birthday,iwp.lastname,iwp.firstname,iwp.middlename,p.intakeTime
    order by pat.lastname,pat.firstname,pat.middlename
    "/>
    
    <msh:sectionTitle>
    
    <form action="print-pres_lab_prescript_by_department.do" method="post" target="_blank">
	    Список пациентов за ${beginDate}-${endDate} по отделению ${lpu_name}
	    <input type='hidden' name="sqlText" id="sqlText" value="select 
	    pat.id as f1pat
    ,case when p.intakeDate is null then '' else '' end as f2js
    , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , coalesce(case when list(p.materialId)='' then coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    ||'#'||pat.lastname else list(p.materialId) end) as f4material
    ,coalesce(vsst.name,'---') as f5vsstname
    ,pat.lastname as f6last,pat.firstname as f7first,pat.middlename as f8middlename 
    ,to_char(pat.birthday,'dd.mm.yyyy') as f9birthday
    ,list(case when vst.code='LABSURVEY' then ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name else null end) as f10medServicies
    ,list(distinct wp.lastname||' '||wp.firstname||' '||wp.middlename) as f11fioworker
    ,list(distinct iwp.lastname||' '||iwp.firstname||' '||iwp.middlename) as f12intakefioworker
    ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f13dtintake
    ,list(ms.additionCode || (select to_char(pmc.datestart,'dd.mm.yyyy') 
    	from medcase mc 
    	left join MedCase pmc on pmc.id=mc.parent_id and pmc.dtype='Visit'
    	where mc.patient_id=slo.patient_id 
    		and mc.medService_id=p.medService_id
    		and pmc.datestart between current_date-1 and current_date-8
    		
    )) as mc
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
    and coalesce(p.department_id,w.lpu_id)='${lpu_id}' 
    and p.cancelDate is null ${sqlAdd}
    group by pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id,ms.additionCode
    ,p.intakedate,pat.birthday,iwp.lastname,iwp.firstname,iwp.middlename,p.intakeTime
    order by vsst.name,pat.lastname,pat.firstname,pat.middlename"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value='Список пациентов за ${beginDate}-${endDate} по отделению ${lpu_name}'>
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printGroupNativeQuery">
	    <input type='hidden' name="groupField" id="groupField" value="4">
	    <input type="submit" value="Печать"> 
	    </form>    
    <form action="print-pres_lab_prescript_by_department.do" method="post" target="_blank">
	    
	    <input type='hidden' name="sqlText" id="sqlText" value="select 
	    pat.id as f1pat
    ,case when p.intakeDate is null then '' else '' end as f2js
    , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , coalesce(case when list(p.materialId)='' then coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    ||'#'||pat.lastname else list(p.materialId) end) as f4material
    ,coalesce(vsst.name,'---') as f5vsstname
    ,pat.lastname as f6last,pat.firstname as f7first,pat.middlename as f8middlename 
    ,to_char(pat.birthday,'dd.mm.yyyy') as f9birthday
    ,list(case when vst.code='LABSURVEY' then ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name else null end) as f10medServicies
    ,list(distinct wp.lastname||' '||wp.firstname||' '||wp.middlename) as f11fioworker
    ,list(distinct iwp.lastname||' '||iwp.firstname||' '||iwp.middlename) as f12intakefioworker
    ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f13dtintake
    ,list(ms.additionCode || (select to_char(pmc.datestart,'dd.mm.yyyy') 
    	from medcase mc 
    	left join MedCase pmc on pmc.id=mc.parent_id and pmc.dtype='Visit'
    	where mc.patient_id=slo.patient_id 
    		and mc.medService_id=p.medService_id
    		and pmc.datestart between current_date-1 and current_date-8
    		
    )) as mc
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
    and coalesce(p.department_id,w.lpu_id)='${lpu_id}' 
    and p.cancelDate is null ${sqlAdd}
    group by pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id,ms.additionCode
    ,p.intakedate,pat.birthday,iwp.lastname,iwp.firstname,iwp.middlename,p.intakeTime
    order by vsst.name,pat.lastname,pat.firstname,pat.middlename"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value='Список пациентов за ${beginDate}-${endDate} по отделению ${lpu_name}'>
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printGroupColumnNativeQuery">
	    <input type='hidden' name="groupField" id="groupField" value="4">
	    <input type='hidden' name="cntColumn" id="cntColumn" value="2">
	    <input type="submit" value="Печать" onclick="this.form.action='print-pres_lab_prescript_by_department.do'"> 
	    <input type="submit" value="Печать" onclick="this.form.action='print-pres_lab_prescript_by_department_birok.do'"> 
	    </form>    
    </msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="javascript:void(0)" idField="1">
	      <msh:tableButton property="1" buttonFunction="showBiomatIntakeInfo" buttonName="Прием биоматериала осуществлен" buttonShortName="Прием" hideIfEmpty="true"/>
	      <msh:tableButton property="2" buttonFunction="removeService" buttonName="Очистить данные о приеме биоматериала" buttonShortName="Очистить данные о приеме" hideIfEmpty="true" role="/Policy/Mis/Journal/Prescription/LabSurvey/IsRemoveIntake"/>
	      <msh:tableColumn columnName="Стат.карта" property="3"  />
	      <msh:tableColumn columnName="Код биоматериала" property="4"/>
	      <msh:tableColumn columnName="Метка биоматериала" property="5"/>
	      <msh:tableColumn columnName="Фамилия пациента" property="6"  />
	      <msh:tableColumn columnName="Имя" property="7" />
	      <msh:tableColumn columnName="Отчетство" property="8"/>
	      <msh:tableColumn columnName="Дата рождения" property="9"/>
	      <msh:tableColumn columnName="Список услуг" property="10"/>

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
  		
  		function removeService(aListPrescript, aMaterialId) {
  				PrescriptionService.intakeServiceRemove(aListPrescript, { 
		            callback: function(aResult) {
		            	window.document.location.reload();
		            }
				}); 
  		}
  	</script>
  </tiles:put>
</tiles:insert>

