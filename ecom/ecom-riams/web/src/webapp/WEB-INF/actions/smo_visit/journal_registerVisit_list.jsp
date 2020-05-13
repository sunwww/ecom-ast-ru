<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly" title="Учет посещений" property="worker">Просмотр данных по пациентам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="journalRegisterVisit"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%	
  String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", request) ;
  String typeIsTalk =ActionUtil.updateParameter("Form039Action","typeIsTalk","3", request) ;
  String typeAge =ActionUtil.updateParameter("Form039Action","typeAge","2", request) ;
  String typeRep =ActionUtil.updateParameter("Form039Action","typeRep","1", request) ;
  JournalBySpecialistForm form = (JournalBySpecialistForm)request.getSession().getAttribute("poly_journalBySpecForm");
	//String args =form.getBeginDate()+":"+form.getFinishDate()
	//+":"+form.getSpecialist()+":"+form.getRayon()
	//+":"+form.getPrimaryInYear() +":" +form.getNumberInJournal() +":"
	//;
	String startDate=form.getBeginDate() ;
	String finishDate=form.getFinishDate() ;
	request.setAttribute("numberInJournal", form.getNumberInJournal()) ;
	/*request.setAttribute("finishDate", finishDate) ;
	request.setAttribute("specialist", form.getSpecialist()) ;
	request.setAttribute("rayon", form.getRayon()) ;
	request.setAttribute("primaryInYear", form.getPrimaryInYear()) ;*/
	//String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", aRequest) ;
	//System.out.println("args="+args) ;
	String order = "" ;
	if (form.getOrderBySpecialist()!=null && form.getOrderBySpecialist().booleanValue()) {
		order="workFunction_id" ;
		request.setAttribute("order", "Сортировка по специалисту") ;
	}  else {
		request.setAttribute("order", "Сортировка по времени приема") ;
		order="dateStart,timeExecute" ;
	}
	
	String dtypeSql = " (t.dtype='Visit' or t.dtype='ShortMedCase') " ;
	if (typeDtype.equals("1")) {
		dtypeSql="t.dtype='Visit'" ;
	} else if (typeDtype.equals("2")) {
		dtypeSql="t.dtype='ShortMedCase'" ;
	}
	String medcard = form.getMedcard() ;
	String selectAdd = "";
	String sql = "where "+dtypeSql;
	if (medcard!=null&&!medcard.equals("")&&medcard.length()>1) {
		
		String[] fs1=medcard.split(",") ;
		StringBuilder filtOr = new StringBuilder() ;
		
		for (int i=0;i<fs1.length;i++) {
			String filt1 = fs1[i].trim() ;
			
			if (filt1.length()>0) {
				if (filtOr.length()>0) filtOr.append(",") ;
	    		filtOr.append("'"+filt1+"'") ;

			}
		}
		request.setAttribute("medcardAddJoin", "left join  medcard medc on p.id=medc.person_id") ;
		sql=sql+" and ( upper(medc.number) in ("
				+filtOr.toString()+") or upper(p.patientSync) in ("+filtOr.toString()+"))" ;
	}
	sql=sql+" and t.patient_id is not null and t.dateStart between to_date('"+startDate+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy')" ;
	if (form.getLpu()!=null && (form.getLpu().intValue()>0)) {
		sql = sql + " and w.lpu_id='"+form.getLpu()+"'" ;
	}
	if (form.getWorkFunction()!=null && (form.getWorkFunction().intValue()>0)) {
		sql = sql + " and wf.workFunction_id='"+form.getWorkFunction()+"'" ;
	}
	if (form.getSpecialist()!=null && (form.getSpecialist().intValue()>0)) {
		sql = sql + " and t.workFunctionExecute_id='"+form.getSpecialist()+"'" ;
	}
		
	if (form.getServiceStream()!=null && (form.getServiceStream().intValue()>0)) {
		sql = sql+" and t.serviceStream_id='"+form.getServiceStream()+"'" ;
	}
	if (form.getAdditionStatus()!=null && (form.getAdditionStatus().intValue()>0)) {
		sql = sql+" and p.additionStatus_id='"+form.getAdditionStatus()+"'" ;
	}
	if (form.getPrimaryInYear()!=null && (form.getPrimaryInYear().intValue()>0)) {
		sql = sql+" and t.hospitalization_id='"+form.getPrimaryInYear()+"'" ;
	}
	if (form.getRayon()!=null && (form.getRayon().intValue()>0)) {
		sql = sql+" and p.rayon_id='"+form.getRayon()+"'" ;
	}
	if (form.getVisitReason()!=null && (form.getVisitReason().intValue()>0)) {
		sql = sql+" and t.visitReason_id='"+form.getVisitReason()+"'" ;
	}
	if (form.getWorkPlaceType()!=null&&form.getWorkPlaceType().intValue()>0) {
		sql+=" and t.workPlaceType_id='"+form.getWorkPlaceType()+"'";
		selectAdd +=" ,vic.name as f18_lpassportType, p.passportseries, p.passportnumber,  p.snils ,to_char(t.dateStart,'dd.MM.yyyy') as dateStart_1, coalesce(cast(t.timeExecute as varchar(5)),'') as visitTime ";
	}
	String isTalkSql = "" ;
	if (typeIsTalk.equals("1")) {
		isTalkSql = " and t.isTalk='1'" ;
	} else if (typeIsTalk.equals("2")) {
		isTalkSql = " and (t.isTalk='0' or t.isTalk is null)" ;
	}
	StringBuilder ageSql = new StringBuilder() ;
	if (typeAge.equals("1")) {
  		ageSql.append(" and cast(to_char(t.dateStart,'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(t.dateStart, 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(t.dateStart,'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 0 and 14 ") ;
	} 
	sql = sql+isTalkSql ;
	
	sql = sql+ageSql.toString() ;
	request.setAttribute("sql", sql) ;
	request.setAttribute("order", order) ;
	request.setAttribute("selectAdd", selectAdd);
  %>
    <msh:form action="/smo_journalRegisterVisit_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    <input type="hidden" name="m" id="m" value="journalRegisterVisitByFrm"/>
    <input type="hidden" name="s" id="s" value="SmoVisitService"/>
    <input type="hidden" name="id" id="id"/>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row>
        	<msh:textField property="numberInJournal" labelColSpan="3" size="25"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="beginDate" label="Период с" />
        	<msh:textField property="finishDate" label="по" />
        </msh:row>
        <msh:row>
        	<msh:textField property="medcard" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lpu" vocName="lpu"
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" vocName="vocWorkFunction" 
        		horizontalFill="true" fieldColSpan="6"/>
        	
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunction" 
        		horizontalFill="true" fieldColSpan="6"/>
        	
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" vocName="vocServiceStream"
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="additionStatus" vocName="vocAdditionStatus" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>        
        <msh:row>
        	<msh:autoComplete property="workPlaceType" vocName="vocWorkPlaceType" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>        
        <msh:row>
        	<msh:autoComplete labelColSpan="6" property="primaryInYear" vocName="vocHospitalization" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="rayon" vocName="vocRayon" fieldColSpan="2" horizontalFill="true" />
        	<msh:autoComplete property="visitReason" label="Цель визита" vocName="vocReason" fieldColSpan="2" horizontalFill="true"/>
        </msh:row>
         <msh:row>
	        <td class="label" title="База (typeDtype)" colspan="1">
	        <label for="typeDtypeName" id="typeDtypeLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="1">  Визит.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDtype" value="2" >  Талон.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="3">  Все
	        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="1"  >  0-14 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="2"  >  все
        </td>
       </msh:row>

		<msh:row>
			<td class="label" title="Тип отчета (typeRep)" colspan="1">
				<label for="typeRepName" id="typeRepLabel">Тип отчета:</label>
			</td>
			<td onclick="this.childNodes[1].checked='checked';">
				<input type="radio" name="typeRep" value="1"  >  учет посещений
			</td>
			<td onclick="this.childNodes[1].checked='checked';">
				<input type="radio" name="typeRep" value="2"  >  хронометраж
			</td>
		</msh:row>

       <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
         <msh:row>
	        <td class="label" title="База (typeIsTalk)" colspan="1">
	        <label for="typeDtypeName" id="typeIsTalkLabel">Беседа с родс.:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeIsTalk" value="1">  только беседы
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeIsTalk" value="2" >  основные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeIsTalk" value="3">  Все
	        </td>
        </msh:row>
	   </msh:ifInRole>
        <msh:row>
        <td colspan="4" class="buttons">
			<input type="button" value="Отменить" title="Отменить изменения [SHIFT+ESC]" onclick="this.disabled=true; window.history.back()" id="cancelButton">
			<input type="button" title="Найти [CTRL+ENTER]" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;smo_journalRegisterVisit_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
		</td>
		</msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null) {
    	%>


	  <% if (typeRep.equals("2")) {%>
	  <input id="getExcel" class="button" name="submit" value="Сохранить в excel" onclick="mshSaveTableToExcelById()" role="button" type="submit">
	  <div id="myTemp">
	  <ecom:webQuery name="chronometr" nativeSql="
		select
		p.patientsync as numcard,
		p.lastname||' '||p.firstname||' '||p.middlename as fio,
		vwf.name as workfunction,
		to_char(t.dateStart,'dd.MM.yyyy') as dateofvisit,
		vs.code as sex,
		case when date_part('year',age(current_date, p.birthday))=0 then cast('-'||date_part('month',age(current_date, p.birthday)) as text) else cast(date_part('year',age(current_date, p.birthday)) as text) end as years,
		vh.code as vhcode,
		vpt.omccode as workplace,
		1 as typemed,
		case when (select list (mkb.code) from diagnosis d left join vocidc10 mkb on mkb.id=d.idc10_id where d.medCase_id=t.id) like '%Z%' then '2,1' else '1,1' end,
		(select list (mkb.code) from diagnosis d left join vocidc10 mkb on mkb.id=d.idc10_id where d.medCase_id=t.id) as diag,
		to_char(t.dateStart,'dd.MM.yyyy') as datecreate
	 	from MedCase t
    	left join patient p on p.id=t.patient_id
    	${medcardAddJoin}
		left join vocworkplacetype   vpt on vpt.id = t.workplacetype_id
		left join workfunction wf on wf.id=t.workFunctionExecute_id left join worker w on w.id=wf.worker_id
		left join vocworkfunction vwf on vwf.id=wf.workfunction_id
		left join patient wp on wp.id=w.person_id
		left join vocsex vs on vs.id=p.sex_id
		left join vochospitalization vh on vh.id=t.hospitalization_id left join VocServiceStream vss on vss.id=t.serviceStream_id left join MisLpu oml on oml.id=t.orderLpu_id left join VocReason vvr on t.visitReason_id=vvr.id left join vocIdentityCard vic on vic.id=p.passporttype_id
		${sql}
		and (t.noActuality is null or t.noActuality='0')
        "/>
	  <msh:tableNotEmpty name="chronometr">
		  <msh:table name="chronometr" action="javascript:void(0)" idField="1">
			  <msh:tableColumn columnName="Номер карты пациента" property="1"/>
			  <msh:tableColumn columnName="ФИО пациента" property="2"/>
			  <msh:tableColumn columnName="Должность врача" property="3"/>
			  <msh:tableColumn columnName="Дата обращения" property="4"/>
			  <msh:tableColumn columnName="Пол" property="5"/>
			  <msh:tableColumn columnName="Возраст" property="6"/>
			  <msh:tableColumn columnName="Посещение" property="7"/>
			  <msh:tableColumn columnName="Место посещения" property="8"/>
			  <msh:tableColumn columnName="Вид оказываемой мед. помощи" property="9"/>
			  <msh:tableColumn columnName="Цель посещения" property="10"/>
			  <msh:tableColumn columnName="Код диагноза" property="11"/>
			  <msh:tableColumn columnName="Дата заполнения" property="12"/>
		  </msh:table>
		  </div>
	  </msh:tableNotEmpty>
	  <%} else {%>
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}</msh:sectionTitle>
    <msh:sectionContent>
    	<ecom:webQuery name="registger_visit"
    	 nameFldSql="registger_visit_sql"
    	nativeSql="select t.id ,to_char(t.dateStart,'dd.MM.yyyy')||' '||coalesce(cast(timeExecute as varchar(5)),'') as dateexecute
    	,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as spec
    	,p.lastname||' '||p.firstname||' '||p.middlename as fio,vs.name as vsname,to_char(p.birthday,'dd.mm.yyyy') as birthday
    	, case 
    	when p.address_addressId is not null           then coalesce(a.fullname,a.name) ||                case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end       
    	when p.territoryRegistrationNonresident_id is not null          then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident               ||' '||ost.name||' '||p.StreetNonresident||               case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end   else ''   
    	end as address,vr.name as vrname
    	,(select list(mp.series||' '||mp.polNumber||' '||ri.name||' ('||to_char(mp.actualDateFrom,'dd.MM.yyyy')||coalesce('-'||to_char(mp.actualDateTo,'dd.MM.yyyy')||')','-нет даты окончания')) from medpolicy mp left join reg_ic ri on ri.id=mp.company_id where mp.patient_id=p.id  and t.dateStart between mp.actualDateFrom and COALESCE(mp.actualDateTo,CURRENT_DATE)) as policy
    	, vh.code as vhcode 
    	, (select list (mkb.code) from diagnosis d left join vocidc10 mkb on mkb.id=d.idc10_id where d.medCase_id=t.id) as diag
    	, vss.name as vssname, (select list (ms.name) from medcase ser left join medservice ms on ms.id=ser.medService_id where ser.parent_id=t.id and ser.dtype='ServiceMedCase') as usl
    	,case when t.isDirectHospital='1' then 'Да' when t.orderDate is not null then 'Да' else '' end as directHosp
    	, oml.name as omlname 
    	,vvr.name as vvrname
    	,t.externalId as texternalId
    	${selectAdd}
    	from Medcase t  
    	left join patient p on p.id=t.patient_id  left join vocrayon vr on vr.id=p.rayon_id
    	${medcardAddJoin}  
    	left join workfunction wf on wf.id=t.workFunctionExecute_id  left join worker w on w.id=wf.worker_id 
    	left join vocworkfunction vwf on vwf.id=wf.workfunction_id left join patient wp on wp.id=w.person_id 
    	left join vocsex vs on vs.id=p.sex_id 
    	left join Address2 a on a.addressId=p.address_addressId 
    	left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id 
    	left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id 
    	left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id 
    	left join vochospitalization vh on vh.id=t.hospitalization_id  
    	left join VocServiceStream vss on vss.id=t.serviceStream_id
    	left join MisLpu oml on oml.id=t.orderLpu_id
    	left join VocReason vvr on t.visitReason_id=vvr.id
    	left join vocIdentityCard vic on vic.id=p.passporttype_id
    	${sql} 
		and (t.noActuality is null or t.noActuality='0') order by ${order}
    	"/>
    	
    	    <form action="print-journalRegistration.do" method="post" target="_blank">
    
    <input type='hidden' name="sqlText" id="sqlText" value="${registger_visit_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}">
    
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type='hidden' name="cntBegin" id="cntBegin" value="${numberInJournal}">
    <input type="submit"  onclick="this.form.action='print-journalRegistration.do'" value="Печать шаблон 1">
     <input type="submit" onclick="this.form.action='print-journalRegistration_1.do'" value="Печать шаблон 2">
     <input type="submit" onclick="this.form.action='print-journalRegistration_2.do'" value="Печать шаблон 3">
     <input type="submit" onclick="this.form.action='print-journalRegistration_2.do'" value="Печать шаблон 4(скор. помощь)">
    </form>
        <msh:table viewUrl="entitySubclassShortView-mis_medCase.do" 
        name="registger_visit" 
        action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="№визита" property="1"/>            
            <msh:tableColumn columnName="Дата приема" property="2"/>            
            <msh:tableColumn columnName="Специалист" property="3"/>
            <msh:tableColumn columnName="ФИО" property="4"/>
            <msh:tableColumn columnName="Пол" property="5"/>
            <msh:tableColumn columnName="Дата рождения" property="6"/>
            <msh:tableColumn columnName="Адрес" property="7"/>
            <msh:tableColumn columnName="Район проживания" property="8"/>
            <msh:tableColumn columnName="Серия номер полиса" property="9"/>
            <msh:tableColumn columnName="Диагноз. Код МКБ" property="10"/>
            <msh:tableColumn columnName="Код посещения" property="11"/>
            <msh:tableColumn columnName="Поток обслуживания" property="12"/>
            <msh:tableColumn columnName="Услуги" property="13"/>
            <msh:tableColumn columnName="Напр. на стац. лечение" property="14"/>
            <msh:tableColumn columnName="ЛПУ направитель" property="15"/>
            <msh:tableColumn columnName="Цель визита" property="16"/>
        </msh:table>
        
        <ecom:webQuery name="listCount" nativeSql="
	select case when t.isDirectHospital='1' then 'Да' when t.orderDate is not null then 'Да' else 'Нет' end as direct,count(t.id) as cnt
	 from MedCase t 
    	left join patient p on p.id=t.patient_id  left join vocrayon vr on vr.id=p.rayon_id  
    	${medcardAddJoin}
    	left join workfunction wf on wf.id=t.workFunctionExecute_id  left join worker w on w.id=wf.worker_id 
    	left join vocworkfunction vwf on vwf.id=wf.workfunction_id left join patient wp on wp.id=w.person_id 
    	left join vocsex vs on vs.id=p.sex_id
    	left join vochospitalization vh on vh.id=t.hospitalization_id  
    	left join VocServiceStream vss on vss.id=t.serviceStream_id
	${sql}
	and (t.noActuality is null or t.noActuality='0')
	 group by case when t.isDirectHospital='1' then 'Да' when t.orderDate is not null then 'Да' else 'Нет' end       
        "/>
        <msh:tableNotEmpty name="listCount">
         <msh:table name="listCount" action="javascript:void(0)" idField="1">
        	<msh:tableColumn columnName="Напр. на стац.лечение" property="1"/>
        	<msh:tableColumn columnName="Кол-во" property="2"/>
        </msh:table>
       </msh:tableNotEmpty>
    </msh:sectionContent>
    </msh:section>
    <%}} %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
  	<script type="text/javascript">
    checkFieldUpdate('typeIsTalk','${typeIsTalk}',3) ;
  	</script>
  	</msh:ifInRole>
  	<script type="text/javascript">
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeAge','${typeAge}',2) ;
    checkFieldUpdate('typeRep','${typeRep}',1) ;

    function checkFieldUpdate(aField,aValue,aDefault) {
    	eval('var chk =  document.forms[0].'+aField) ;
    	eval('var aMax =  chk.length') ;
    	if (aMax>aDefault) {aDefault=aMax}
    	if ((+aValue)>aMax) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
  		function getId() {
  			var args=$('beginDate').value+":"+$('finishDate').value
  			+":"+$('specialist').value+":"+$('rayon').value+":"+$('primaryInYear').value 
  			+":" +$('numberInJournal').value +":";
  			
  			if ($('orderBySpecialist') && $('orderBySpecialist').checked) {
  				args=args+"workFunction_id" ;
  			}  else {
  				args=args+"dateStart,timeExecute" ;
  			}
  			args=args+":"+$('workFunction').value+":"+$('lpu').value+":"+$('serviceStream').value ;
  			args=args+":" +getCheckedRadio(document.forms[0],"typeDtype");
  			$('id').value =args ; 
  		}

    function mshPrintTextToExcelTable (html) {
        window.location.href='data:application/vnd.ms-excel,'+'\uFEFF'+encodeURIComponent(html);
    }

    function mshSaveTableToExcelById() {
        mshPrintTextToExcelTable(document.getElementById("myTemp").outerHTML);
    }
  	</script>
  </tiles:put>
</tiles:insert>