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
        </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	//Журнал для врача стационара для наблюдения за ходов выполнения лаб. анализов
  	String typeResult =ActionUtil.updateParameter("PrescriptJournalDoc","typeResult","1", request) ;
  	String typeDate =ActionUtil.updateParameter("PrescriptJournalDoc","typeDate","3", request) ;
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	ActionUtil.getValueBySql("select wf.id ,case when wf.isAdministrator='1' then '1' else '0' end as isAdmin from workfunction wf left join worker w on w.id=wf.worker_id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "workFunctionId","isAdministrator",request) ;
  	ActionUtil.getValueBySql("select coalesce(wf.lpu_id, w.lpu_id) ,case when wf.isAdministrator='1' then '1' else '0' end as isAdmin from workfunction wf left join worker w on w.id=wf.worker_id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "departmentId","isAdministrator",request) ;
  	String workfunctionId = request.getAttribute("workFunctionId").toString();
  	String departmentId = request.getAttribute("departmentId").toString();
  	String isAdministrator = request.getAttribute("isAdministrator").toString();
  	%>
  	  <msh:form action="/pres_doctor_lab_journal.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
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
        <td class="label" title="Этап исследования (typeResult)"><label for="typeResultName" id="typeResultLabel">Этап выполнения:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeResult" value="1"> Выполнен забор биоматериала
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeResult" value="2"> Передан в лабораторию
        </td>
		  <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
			  <input type="radio" name="typeResult" value="3"> подтвержден врачом КДЛ
		  </td>
     </msh:row>
      <msh:row>
      	<td></td>        
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeResult" value="4"> дефектные биоматериалы
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeResult" value="5"> все
        </td>
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
    <script type='text/javascript'>
    checkFieldUpdate('typeDate','${typeDate}',3) ;
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
    if ("1".equals(typeDate)) {
    	request.setAttribute("dateSql", "p.planStartDate") ;
    } else if ("2".equals(typeDate)) {
    	request.setAttribute("dateSql", "p.intakeDate") ;
    } else {
    	request.setAttribute("dateSql", "p.transferDate") ;
    }
    switch (typeResult) {
		case "4": //Дефект
			sqlAdd.append(" and p.cancelDate is not null ") ;
			break;
		case "3": //Выполнен
			sqlAdd.append(" and mc.dateStart is not null and p.cancelDate is null and mc.workFunctionExecute_id is not null") ;
			break;
		case "2": //Переданы
			sqlAdd.append(" and p.cancelDate is null and p.transferDate is not null and mc.workFunctionExecute_id is null") ;
			break;
		case "1" : //Выполнен забор биоматериала
			sqlAdd.append(" and p.intakeDate is not null and p.transferDate is null and p.cancelDate is null ") ;
			break;
	}
	sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
			, "тип назначения","prescriptType","vpt.id", request)) ;

	 if ("1".equals(isAdministrator)) { //Заведующий
	 	sqlAdd.append(" and slo.department_id=").append(departmentId);

	 } else { //врач отделения
	 	sqlAdd.append(" and p.prescriptSpecial_id=").append(workfunctionId);

	 }
	
    request.setAttribute("sqlAdd", sqlAdd.toString()) ;
    %>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select 
    p.id as pid,
    case 
    when p.cancelDate is not null then 'ОТМЕНЕНО ' || list(coalesce(vpcr.name,'') ||' '||coalesce(p.cancelreasontext,''))
    when p.transferDate is not null and mc.workFunctionExecute_id is null then 'Передан биоматериал в лабораторию'
    when mc.dateStart is not null and p.cancelDate is null and mc.workFunctionExecute_id is not null then 'Выполнено'
    when p.intakeDate is not null and p.transferDate is null then 'Произведен забор биоматериала'
    else null
    end as f2_comment
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , p.materialId||' ('||coalesce(vsst.code,'---')||')' as f4material
    ,pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as f5birthday
   , ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name as f6medServicies
   ,to_char(p.planstartDate,'dd.MM.yyyy')||' '|| wp.lastname as f7fioworker
      ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5))||' '||iwp.lastname as f8dtintake
       ,to_char(p.transferDate,'dd.mm.yyyy')||' '||cast(p.transferTime as varchar(5))||' '||p.transferusername as f9dtransferintake
  ,case
    when p.cancelDate is not null then 'background:red' 
    when mc.dateStart is not null then 'background: green; color:black'
    else ''
  end as f10_colorcomment
  ,to_char(mc.dateStart,'dd.MM.yyyy')||' '||mc.timeexecute||' '||mcPat.lastname as f11_executor
    from prescription p
    left join VocPrescriptCancelReason vpcr on vpcr.id=p.cancelreason_id
    left join VocPrescriptType vpt on vpt.id=p.prescriptType_id
    left join MedCase mc on mc.id=p.medcase_id
    left join WorkFunction mcWf on mcWf.id=mc.workfunctionexecute_id
    left join worker mcW on mcW.id=mcWf.worker_id
    left join patient mcPat on mcPat.id=mcW.person_id
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
    and ${dateSql} between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    and vst.code='LABSURVEY'
    ${sqlAdd}
    group by p.id,pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    ,p.intakedate,pat.birthday,iwp.lastname,iwp.firstname,iwp.middlename,p.intakeTime
    ,p.transferDate,p.transferTime,ms.parent_id,ms.id,ms.code,ms.name,ms.additionCode
    ,p.medCase_id,mc.workFunctionExecute_id,mc.dateStart,vsst.code
    ,wp.lastname,wp.middlename,wp.firstname,vwf.name,mc.id,ml.name
    ,p.canceldate,p.materialid,p.planstartdate
    ,vsst.biomaterial, mcPat.lastname
    order by pat.lastname,pat.firstname,pat.middlename
    
    "/>
    <msh:sectionContent>
		<msh:table name="list" action="javascript:void(0)" idField="1" styleRow="10" >
			<msh:tableColumn columnName="#" property="sn"  />
	      	<msh:tableColumn columnName="Код назн." property="4"/>
	      	<msh:tableColumn columnName="ИБ" property="3"  />
	      	<msh:tableColumn columnName="ФИО пациента" property="5"  />
			<msh:tableColumn columnName="Исследование" property="6"/>
			<msh:tableColumn columnName="Статус" property="2"/>
			<msh:tableColumn columnName ="Назначил" property="7"/>
	      	<msh:tableColumn columnName="Забор" property="8"/>
	      	<msh:tableColumn columnName="Прием в лабораторию" property="9"/>
	      	<msh:tableColumn columnName="Выполнил" property="11"/>
	    </msh:table>
	    <script type="text/javascript">
	    
	    </script>
    </msh:sectionContent>
    </msh:section>

  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
  	<script type="text/javascript">
    	  serviceSubTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
      	  departmentAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
      	  prescriptTypeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;

  	</script>
  </tiles:put>
</tiles:insert>
