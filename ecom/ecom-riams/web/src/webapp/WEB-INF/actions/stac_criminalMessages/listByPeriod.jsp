<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал сообщений в полицию"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="stac_criminalMessages" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String typeAge=ActionUtil.updateParameter("Report_criminal","typeAge","3", request) ;
  String view=ActionUtil.updateParameter("Report_criminal","typeView1","1", request) ;
 // request.setAttribute(arg0, arg1)
  String typeDuration=ActionUtil.updateParameter("Report_criminal","typeDuration","4", request) ;
  if (request.getParameter("short")==null) {
  %>
    <msh:form action="/journal_militiaMessage.do" defaultField="pigeonHoleName" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    <input type="hidden" name="s" id="s" value="HospitalPrintService" />
    <input type="hidden" name="m" id="m" value="printReestrByDay" />
    <input type="hidden" name="id" id="id" value=""/>
    
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="pigeonHole" fieldColSpan="7" 
      		horizontalFill="true" label="Приемник"
      		vocName="vocPigeonHole"
      		/>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="1">  экстренные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="2" >  плановые
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="3">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по длительности госпитализации (typeDuration)" colspan="1"><label for="typeDurationName" id="typeDurationLabel">Длительность госп.:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDuration" value="1">  0-24 часа
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDuration" value="2" >  0-7 суток
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDuration" value="3">  0-30
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDuration" value="4">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по возрастным категориям (typeAge)" colspan="1"><label for="typeAgeName" id="typeAgeLabel">Возраст. категория:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAge" value="1">  0-17 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAge" value="2" >  трудосп. возраст от 18 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAge" value="3">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по дате  (typeDate1)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate1" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate1" value="2">  регистрации
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate1" value="3">  когда произошло событие
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate1" value="4">  выписки
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="2">  реестр по приказу минзрава 03.02.2010 №42-Пр/13 
	        </td>
        </msh:row>
        <msh:row>
            <td></td>
 	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="3"  >  свод по дням 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="4"  >  общий свод по госпитализациям
	        </td>
        </msh:row>
        <msh:row>
            <td></td>
            <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="5"  >  общий свод отказов от госпитализаций
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="6"  >  общий свод по обращениям
	        </td>
        </msh:row>
        <msh:row>
            <td></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="7">  реестр МДГП-ЦП 
	        </td>            
	        
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="8">  свод по возрастам 
	        </td>            
	        
        </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocCriminalPhoneMessageType" property="phoneMessageType" label="Тип" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocPhoneMessageSubType" property="phoneMessageSubType" parentAutocomplete="phoneMessageType" label="Описание" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
            <msh:autoComplete property="filterAdd" fieldColSpan="8" horizontalFill="true" label="Район" vocName="vocRayon"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
            <input type="submit" onclick="print()" value="Печать реестра" />
<%--        <input type="submit" onclick="printNew()" value="Печать (по отделениям)" />
            <input type="submit" onclick="printNew1()" value="Печать (сопроводительного листа) 1" />
            <input type="submit" onclick="printNew2()" value="Печать (сопроводительного листа) 2" />
             --%>
          </td>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    checkFieldUpdate('typeDate1','${typeDate1}',1) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView1','${typeView1}',1) ;
    checkFieldUpdate('typeAge','${typeAge}',3) ;
    checkFieldUpdate('typeDuration','${typeDuration}',4) ;
  
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
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='journal_militiaMessage.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printCriminalMessage" ;
    	frm.s.value="HospitalPrintReport" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_criminalMessage.do' ;
    	
    }
    </script>

    
    <%}
    String date = request.getParameter("dateBegin") ;
    String date1 = request.getParameter("dateEnd") ;

    if (date!=null && !date.equals(""))  {
    	ActionUtil.getValueBySql("select id,omccode from VocSex where omccode='2'"
    			,"sex_id","sex_code",request);  		
    	StringBuilder age = new StringBuilder() ;
  		age.append("  cast(to_char(m.dateStart,'yyyy') as int)")
		.append(" -cast(to_char(p.birthday,'yyyy') as int)")
		.append(" +(case when (cast(to_char(m.dateStart, 'mm') as int)")
		.append(" -cast(to_char(p.birthday, 'mm') as int)")
		.append(" +(case when (cast(to_char(m.dateStart,'dd') as int)") 
		.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
		.append(" <0)")
			.append(" then -1 else 0 end) between 18 and case when p.sex_id='")
			.append(request.getAttribute("sex_id")).append("' then 54 else 59 end ") ;
    	request.setAttribute("age_works_pat", age.toString()) ;
    	age=new StringBuilder() ;
  		age.append(" cast(to_char(m.dateStart,'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(m.dateStart, 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(m.dateStart,'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 0 and 17 ") ;
    	request.setAttribute("age_0_17", age.toString()) ;
    	if (typeAge!=null &&typeAge.equals("1")) {
    		request.setAttribute("ageSql", " and "+request.getAttribute("age_0_17")) ;
    	} else if (typeAge!=null &&typeAge.equals("2")) {
    		request.setAttribute("ageSql", " and "+request.getAttribute("age_works_pat")) ;
    	}
    	
    	if (typeDuration!=null &&typeDuration.equals("1")) {
    		request.setAttribute("durationSql", " and (m.dateStart=m.dateFinish or (m.dateFinish-m.dateStart=1 and m.dischargetime<m.entrancetime))") ;
    	} else if (typeDuration!=null &&typeDuration.equals("2")) {
    		request.setAttribute("durationSql", " and m.dateFinish-m.dateStart<=7") ;
    	} else if (typeDuration!=null &&typeDuration.equals("3")) {
    		request.setAttribute("durationSql", " and m.dateFinish-m.dateStart<=31") ;
    	}
    	
    	ActionUtil.getValueBySql("select id,code from VocHospitalizationResult where code='11'"
    			,"death_id","death_code",request);
    	String typeDeath = request.getParameter("typeDeath") ;
    	if (typeDeath!=null &&typeDeath.equals("1")) {
    		request.setAttribute("deathSql", " and m.result_id="+request.getAttribute("death_id")) ;
    	}
    	
    	String typeHosp = request.getParameter("typeHosp") ;
    	if (typeHosp!=null &&typeHosp.equals("1")) {
    		request.setAttribute("hospSql", " and m.deniedHospitalizating_id is null") ;
    	} else if (typeHosp!=null &&typeHosp.equals("2")) {
    		request.setAttribute("hospSql", " and m.deniedHospitalizating_id is not null") ;
    	}

    	if (date1==null ||date1.equals("")) {date1=date;}
    		request.setAttribute("dateEnd", date1) ;
        request.setAttribute("isReportBase", ActionUtil.isReportBase(date,date1,request));
    	//String view = (String)request.getParameter("typeView1") ;
    	//out.print("view="+view);
    	String pigeonHole1="" ;
    	String pigeonHole="" ;
    	String pHole = request.getParameter("pigeonHole") ;
    	if (pHole!=null && !pHole.equals("") && !pHole.equals("0")) {
    		pigeonHole1= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    		pigeonHole= " and ml.pigeonHole_id='"+pHole+"'" ;
    	}
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	request.setAttribute("pigeonHole1", pigeonHole1) ;
    	
    	String phoneMessageType = request.getParameter("phoneMessageType") ;
    	if (phoneMessageType!=null && !phoneMessageType.equals("") && !phoneMessageType.equals("0")) {
    		request.setAttribute("phoneMessageType", " and pm.phoneMessageType_id='"+phoneMessageType+"'") ;
    	}
    	String phoneMessageSubType = request.getParameter("phoneMessageSubType") ;
    	if (phoneMessageSubType!=null && !phoneMessageSubType.equals("") && !phoneMessageSubType.equals("0")) {
    		request.setAttribute("phoneMessageSubType", " and pm.phoneMessageSubType_id='"+phoneMessageSubType+"'") ;
    	}
    	
    	String department="" ;
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		department= " and ml.id='"+dep+"'" ;
    	}
    	request.setAttribute("department", department) ;
    	
    	String typeDate1 = (String)request.getAttribute("typeDate1") ;
    	if (typeDate1!=null && typeDate1.equals("1")) {
    		request.setAttribute("paramDate","m.dateStart") ;
    		request.setAttribute("paramDateInfo","Дата поступления") ;
    	} else if (typeDate1!=null && typeDate1.equals("4")) {
    		request.setAttribute("paramDate","m.dateFinish") ;
    		request.setAttribute("paramDateInfo","Дата выписки") ;
    	} else if (typeDate1!=null && typeDate1.equals("3")) {
    		request.setAttribute("paramDate", "pm.whenDateEventOccurred") ;
    		request.setAttribute("paramDateInfo", "Дата, когда произошло событие") ;
    	} else {
    		request.setAttribute("paramDate","pm.phoneDate") ;
    		request.setAttribute("paramDateInfo","Дата регистрации сообщения") ;
    	}
        String rayon="" ;
        String r = request.getParameter("filterAdd") ;
        if (r!=null && !r.equals("") && !r.equals("0")) {
            rayon=" and rayon.id='"+r+"'";
        }
        request.setAttribute("rayonSql", rayon) ;
    	%>
    	<%if (view!=null && (view.equals("1"))) {
    	String typeGroup = request.getParameter("typeGroup") ;
    	if (typeGroup!=null && typeGroup.equals("patient")) {
        	%>
            
            <msh:section>
            <msh:sectionTitle>
            <ecom:webQuery isReportBase="${isReportBase}" name="journal_militia" nameFldSql="journal_militia_sql" nativeSql="
            select m.id, list(to_char(pm.phoneDate,'dd.mm.yyyy')) as pmphone
            ,list(vpht.name||coalesce(' '||vpmst.name,'')) as vphtnamevpmstname
            ,list(to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5))) as whenevent
            ,list(pm.place) as pmplace
            ,list(coalesce(vpme.name,pm.recieverFio)) as reciever
            ,list(vpmo.name) as vphoname,list(wp.lastname) as wplastname
            ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat
            ,list(coalesce(vpmorg.name,pm.phone,pm.recieverOrganization)) as organization
            ,list(pm.diagnosis) as pmdiagnosis
            ,coalesce(vdh.name,'СК №'||ss.code||' от '||to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5))) as sscode
            ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as discharge
            ,rayon.name as rname
            from PhoneMessage pm 
            left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
            left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
            left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
            left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
            left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
            left join WorkFunction wf on wf.id=pm.workFunction_id
            left join Worker w on w.id=wf.worker_id
            left join Patient wp on wp.id=w.person_id
            left join medcase m on m.id=pm.medCase_id
            left join StatisticStub ss on ss.id=m.statisticstub_id
            left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
            left join Patient p on p.id=m.patient_id
        	left join MisLpu as ml on ml.id=m.department_id 
        	left join SecUser su on su.login=m.username
        	left join WorkFunction wf1 on wf1.secUser_id=su.id
        	left join Worker w1 on w1.id=wf1.worker_id
        	left join MisLpu ml1 on ml1.id=w1.lpu_id
        	left join Vocrayon rayon on rayon.id=pm.rayon_id
            where pm.dtype='CriminalPhoneMessage'
            and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
        and ( m.noActuality is null or m.noActuality='0')
        ${period}
        ${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${durationSql} ${ageSql} ${rayonSql}
            group by m.id,p.lastname,p.firstname,p.middlename,p.birthday,ss.code,vdh.name
            ,m.dateFinish,m.dischargeTime,m.dateStart,m.entranceTime,rayon.name
            order by p.lastname
            " />
            
            
            <form action="print-stac_criminalMessage_1_pat.do" method="post" target="_blank">
    Реестр с ${param.dateBegin} по ${param.dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
            </msh:sectionTitle>
            <msh:sectionContent>
            <msh:table name="journal_militia"
            viewUrl="entityShortView-stac_ssl.do" 
             action="entityParentView-stac_ssl.do" idField="1" >
              <msh:tableColumn columnName="#" property="sn" />
              <msh:tableColumn columnName="Дата" property="2" />
              <msh:tableColumn property="12" columnName="Госпит."/>
              <msh:tableColumn property="9" columnName="Пациент"/>
              <msh:tableColumn columnName="Тип" property="3" />
              <msh:tableColumn columnName="Когда" property="4" />
              <msh:tableColumn columnName="Место" property="5" />
              <msh:tableColumn columnName="Фамилия принявшего" property="6" />
              <msh:tableColumn columnName="Фамилия передавшего" property="8" />
              <msh:tableColumn columnName="Диагноз" property="11" />
              <msh:tableColumn columnName="Исход" property="7" />
              <msh:tableColumn columnName="Дата выписки" property="13" />
              <msh:tableColumn columnName="Район" property="14" />
            </msh:table>
            </msh:sectionContent>
            </msh:section>
            <% 
    		
    	} else {
    	%>
    
    <msh:section>
    <msh:sectionTitle>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_militia" nameFldSql="journal_militia_sql" nativeSql="
    select pm.id, pm.phoneDate
    ,vpht.name||coalesce(' '||vpmst.name,'') as vphtnamevpmstname
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5)) as whenevent
    ,pm.place as pmplace
    ,coalesce(vpme.name,pm.recieverFio) as reciever
    ,vpmo.name as vphoname,wp.lastname as wplastname
    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat
    ,coalesce(vpmorg.name,pm.phone,pm.recieverOrganization) as organization
    ,pm.diagnosis as pmdiagnosis
    ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as datedischarge
    ,rayon.name as rname
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join medcase m on m.id=pm.medCase_id
    left join Patient p on p.id=m.patient_id
	left join MisLpu as ml on ml.id=m.department_id 
	left join SecUser su on su.login=m.username
	left join WorkFunction wf1 on wf1.secUser_id=su.id
	left join Worker w1 on w1.id=wf1.worker_id
	left join MisLpu ml1 on ml1.id=w1.lpu_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${durationSql} ${ageSql} ${rayonSql}
    order by ${paramDate}
    " />
    <form action="print-stac_criminalMessage_1.do" method="post" target="_blank">
    Реестр с ${param.dateBegin} по ${param.dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn property="9" columnName="Пациент"/>
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Когда" property="4" />
      <msh:tableColumn columnName="Место" property="5" />
      <msh:tableColumn columnName="Фамилия принявшего" property="6" />
      <msh:tableColumn columnName="Фамилия передавшего" property="8" />
      <msh:tableColumn columnName="Диагноз" property="11" />
      <msh:tableColumn columnName="Исход" property="7" />
      <msh:tableColumn property="12" columnName="Дата выписки"/>
      <msh:tableColumn columnName="Район" property="13" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    	}
    	}  
    if (view!=null && (view.equals("2"))) {%>
    
    <msh:section>
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_militia_sql" name="journal_militia" nativeSql="
    select pm.id, 
    p.lastname||' '||p.firstname||' '||p.middlename as fiopat
    ,pol.series||' '||polNumber as seriesPolicy
    ,ri.name as rename
    ,p.passportSeries||' '||p.passportNumber as passportInfo
    ,to_char(p.birthday,'dd.mm.yyyy') as pbirthday
    ,vs.name as vsname
    , case when p.address_addressId is not null 
          then coalesce(a.fullname,a.name) 
               ||case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end
               ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end
	       ||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end
       when p.territoryRegistrationNonresident_id is not null
	  then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident
	       ||' '||ost.name||' '||p.StreetNonresident
               ||case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end
	       ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end
	       ||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end
       else  p.foreignRegistrationAddress end as address
    ,to_char(m.dateStart,'dd.mm.yyyy') as mdateStart
    ,to_char(m.dateFinish,'dd.mm.yyyy') as mdateFinish
    ,ml.name as mlname
    
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5))
    ||' '||vpht.name||coalesce(' '||vpmst.name,'') 
    ||' '||pm.place as pmplace
    ,pm.diagnosis as pmdiagnosis
    ,vpmo.name||case when vho.id is null then '' else ' - '||vho.name end as vphoname
    ,case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is null then 'Стационар'
          when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is not null then 'Помощь в приемном отделении'
     else m.dtype end as typeHelp
     , case when m.emergency='1' then 'ДА' else 'НЕТ' end as emergency
     ,vss.name as vssname
     ,pol.commonNumber as commonNumber
     ,case when m.dateFinish is not null then (select list(distinct mkb.code)
      from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=diag.medcase_id and vdrt.code='3' and vpd.code='1' ) else '' end as mkbAfter
     ,case when m.dateFinish is not null then (select list(distinct mkb.name) from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=diag.medcase_id and vdrt.code='3' and vpd.code='1' ) else '' end as mkbAftername
,rayon.name as rname
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    
    left join medcase m on m.id=pm.medCase_id
    left join medcase_medpolicy mcmp on mcmp.medcase_id=m.id
    left join medpolicy pol on pol.id=mcmp.policies_id
    left join reg_ic ri on ri.id=pol.company_id
    left join VocHospitalizationOutcome vho on vho.id=m.outcome_id
    left join Patient p on p.id=m.patient_id
    left join VocSex vs on vs.id=p.sex_id
    left join Address2 a on a.addressId=p.address_addressId
    left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id
    left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id
    left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id
    left join VocServiceStream vss on vss.id=m.serviceStream_id
    left join MisLpu as ml on ml.id=m.department_id 
    left join SecUser su on su.login=m.username
    left join WorkFunction wf1 on wf1.secUser_id=su.id
    left join Worker w1 on w1.id=wf1.worker_id
    left join MisLpu ml1 on ml1.id=w1.lpu_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${rayonSql}
    order by ${paramDate}
    " />
    <msh:sectionTitle>
    
    <form action="print-stac_criminalMessage_pr42.do" method="post" target="_blank">
    Реестр с ${param.dateBegin} по ${param.dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form><form action="print-stac_criminalMessage_pr42_1.do" method="post" target="_blank">
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="${param.dateBegin} - ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать доп. реестра"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="ФИО пациента" property="2" />
      <msh:tableColumn columnName="Серия и номер полиса" property="3" />
      <msh:tableColumn columnName="Наименование СМО" property="4" />
      <msh:tableColumn columnName="Серия и номер паспорта" property="5" />
      <msh:tableColumn columnName="Дата рождения" property="6" />
      <msh:tableColumn columnName="пол" property="7" />
      <msh:tableColumn columnName="Домашний адрес, место работы" property="8" />
      <msh:tableColumn property="9" columnName="Дата начала лечения"/>
      <msh:tableColumn columnName="Дата окончания лечения" property="10" />
      <msh:tableColumn columnName="Название отделения" property="11" />
      <msh:tableColumn columnName="Краткая информация об обстоятельствах получения травмы" property="12" />
      <msh:tableColumn columnName="Диагноз" property="13" />
      <msh:tableColumn columnName="Исход лечения" property="14" />
      <msh:tableColumn columnName="Вид м/помощи" property="15" />
      <msh:tableColumn columnName="Район" property="21" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }  
    	if (view!=null && (view.equals("3"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по дням с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_militia" nativeSql="
    select '&dateBegin='||to_char(${paramDate},'dd.mm.yyyy')||'&dateEnd='||to_char(${paramDate},'dd.mm.yyyy') as id
    ,to_char(${paramDate},'dd.mm.yyyy') as dateSearch
    , count(pm.id) as cntMessages
    , count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as cntHosp
    , count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as cntDenied
    ,count(distinct m.id) as cntObr
    from PhoneMessage pm 
    left join medcase m on m.id=pm.medCase_id
    left join MisLpu as ml on ml.id=m.department_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${rayonSql}
    group by ${paramDate}
    order by ${paramDate}
    " />
    <msh:table name="journal_militia"
    cellFunction="true"
     action="journal_militiaMessage.do?typeView1=1&typeDate1=${typeDate1}&department=${param.department}&pigeonHole=${param.pigeonHole}&phoneMessageType=${param.phoneMessageType}&phoneMessageSubType=${param.phoneMessageSubType}&typeEmergency=${typeEmergency}&typeAge=${typeAge}&typeDate1=${typeDate1}" idField="1">
      <msh:tableColumn columnName="${paramDateInfo}" property="2"/>
      <msh:tableColumn columnName="Кол-во сообщений" property="3"/>
      <msh:tableColumn columnName="Кол-во госпит." property="4" addParam="&typeGroup=patient&typeHosp=1" />
      <msh:tableColumn columnName="Кол-во отказов от госпит." property="5" addParam="&typeGroup=patient&typeHosp=2"/>
      <msh:tableColumn columnName="Кол-во обращений" property="6" addParam="&typeGroup=patient"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    	}
    	
    if (view!=null && (view.equals("4"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по госпитализациям с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_militia" nativeSql="
    select '&department='||coalesce(m.department_id,0)||'&phoneMessageType='||coalesce(vpmt.id,0) as id,ml.name as mlname,vpmt.name as vpmtname, count(pm.id) as cntPm
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is null
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${rayonSql}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " />
    <msh:table name="journal_militia" cellFunction="true"
    action="journal_militiaMessage.do?dateBegin=${param.dateBegin}&dateEnd=${dateEnd}&typeHosp=1&typeView1=1&typeDate1=${typeDate1}&pigeonHole=${param.pigeonHole}&phoneMessageSubType=${param.phoneMessageSubType}&typeEmergency=${typeEmergency}&typeAge=${typeAge}" idField="1">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Кол-во сообщений" property="4" addParam=""/>
      <msh:tableColumn columnName="Кол-во госпитализаций" property="5" addParam="&typeGroup=patient"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
	if (view!=null && (view.equals("5"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по отказам с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_militia" nativeSql="
    select '&department='||coalesce(m.department_id,0)||'&phoneMessageType='||coalesce(vpmt.id,0) as id
    ,ml.name as mlname,vpmt.name as vpmtname
    ,count(pm.id) as cntPm
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${rayonSql}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " />
    <msh:table name="journal_militia"
    cellFunction="true"
    action="journal_militiaMessage.do?dateBegin=${param.dateBegin}&dateEnd=${dateEnd}&typeHosp=2&typeView1=1&typeDate1=${typeDate1}&pigeonHole=${param.pigeonHole}&phoneMessageSubType=${param.phoneMessageSubType}&typeEmergency=${typeEmergency}&typeAge=${typeAge}&typeDate1=${typeDate1}" idField="1">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3"/>
      <msh:tableColumn columnName="Кол-во сообщений" property="4" />
      <msh:tableColumn columnName="Кол-во отказов" property="5" addParam="typeGroup=patient"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
	if (view!=null && (view.equals("6"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по обращениям с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_militia" nativeSql="
    select '&department='||coalesce(m.department_id,0)||'&phoneMessageType='||coalesce(vpmt.id,0) as id,ml.name as mlname,vpmt.name as vpmtname, count(pm.id) as cntPm
    , count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as cntHosp
    , count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as cntDenied
    ,count(distinct m.id) as cntObr
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${rayonSql}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " />
    <msh:table name="journal_militia"
        cellFunction="true"
    action="journal_militiaMessage.do?dateBegin=${param.dateBegin}&dateEnd=${dateEnd}&typeView1=1&typeDate1=${typeDate1}&department=${param.department}&pigeonHole=${param.pigeonHole}&phoneMessageType=${param.phoneMessageType}&phoneMessageSubType=${param.phoneMessageSubType}&typeEmergency=${typeEmergency}&typeAge=${typeAge}&typeDate1=${typeDate1}" 
	idField="1">
      <msh:tableColumn columnName="Отделение" property="2" addParam=""/>
      <msh:tableColumn columnName="Тип" property="3" addParam=""/>
      <msh:tableColumn columnName="Кол-во сообщений" property="4" addParam=""/>
      <msh:tableColumn columnName="Кол-во госпит." property="5" addParam="&typeGroup=patient&typeHosp=1"/>
      <msh:tableColumn columnName="Кол-во отказов от госпит." property="6" addParam="&typeGroup=patient&typeHosp=2"/>
      <msh:tableColumn columnName="Кол-во обращений" property="7" addParam="&typeGroup=patient"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
    if (view!=null && (view.equals("7"))) {%>
    
    <msh:section >
    <ecom:webQuery isReportBase="${isReportBase}" nameFldSql="journal_militia_sql" name="journal_militia" nativeSql="
    select pm.id, ss.code as sscode,
    p.lastname||' '||p.firstname||' '||p.middlename ||' '|| to_char(p.birthday,'dd.mm.yyyy') as pbirthday
    ,to_char(m.dateStart,'dd.mm.yyyy') ||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,pm.diagnosis as pmdiagnosis
    ,case when m.dateFinish is null then (select list(distinct mkb.code) from Diagnosis diag
    left join medcase slo on slo.id=diag.medCase_id
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=slo.parent_id and vdrt.code='4' and vpd.code='1' ) else (select list(distinct mkb.code) 
from Diagnosis diag

		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=diag.medcase_id and vdrt.code='4' and vpd.code='1' ) end as mkbBefore
    ,case when m.dateFinish is not null then (select list(distinct mkb.code) from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
where m.id=diag.medcase_id and vdrt.code='3' and vpd.code='1' ) else '' end as mkbAfter
    ,vpms.name as vpmsname
    ,case when m.dateFinish is null then 'На лечении' 
    else coalesce(to_char(m.dateFinish,'dd.mm.yyyy'),'')||' '||
    case when vhr.code!='11' then vho.name else 'смерть' end end as vphoname
    ,rayon.name as rname
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join VocPhoneMessageState vpms on vpms.id=pm.state_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    
    left join medcase m on m.id=pm.medCase_id
    left join StatisticStub ss on ss.id=m.statisticStub_id
    left join VocHospitalizationOutcome vho on vho.id=m.outcome_id
    left join VocHospitalizationResult vhr on vhr.id=m.result_id
    left join Patient p on p.id=m.patient_id
    left join VocSex vs on vs.id=p.sex_id
    left join MisLpu as ml on ml.id=m.department_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and m.deniedHospitalizating_id is null and ( m.noActuality is null or m.noActuality='0')
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${rayonSql}
    order by ${paramDate}
    " />
    <msh:sectionTitle>
    
    <form action="print-stac_criminalMessage_pr_mdgp.do" method="post" target="_blank">
    Информация о пострадавших в результате ДТП, получивших медицинскую помощь в учреждении здравоохранения с ${param.dateBegin} по ${param.dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="№стат.карты" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Дата и время поступления" property="4" />
      <msh:tableColumn columnName="Диагноз" property="5" />
      <msh:tableColumn columnName="Код диагноза по МКБ при поступлении" property="6" />
      <msh:tableColumn columnName="Код диагноза по МКБ при выписке (переводе, смерти)" property="7" />
      <msh:tableColumn columnName="Тяжесть состояния" property="8" />
      <msh:tableColumn columnName="Исход" property="9" />
      <msh:tableColumn columnName="Район" property="10" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
     <%} 
    if (view!=null && (view.equals("8"))) {
    StringBuilder sls24h = new StringBuilder() ;
    sls24h.append(" and (m.datestart=m.datefinish or (m.datefinish-m.datestart=1 and m.dischargetime<m.entrancetime))") ;
    request.setAttribute("sls24h", sls24h.toString()) ;
    %>
    <msh:section>
    <msh:sectionTitle>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_militia" nameFldSql="journal_militia_sql" nativeSql="
    select '&typeEmergency=${typeEmergency}&paramDate=${typeDate1}'
    	||'&pigeonHole=${param.pigeonHole}'||'&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}'
    	||'&department=${param.department}'||'&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}'
    	||'&phoneMessageType='||coalesce(vpmt.id,0)||'&phoneMessageSubType=${param.phoneMessageSubType}' as id
    ,vpmt.name as f2vpmtname
    , count(distinct case when m.result_id='${death_id}' then m.id else null end) as f3cntDeathHosp
    , count(distinct case when m.result_id='${death_id}' and ${age_0_17} then m.id else null end) as f4cntDeathHosp
    , count(distinct case when m.result_id='${death_id}' and ${age_works_pat} then m.id else null end) as f5cntDeathHosp
    , count(distinct case when m.result_id='${death_id}' ${sls24h} then m.id else null end) as f6cntDeath24h
    , count(distinct case when m.result_id='${death_id}' ${sls24h} and ${age_0_17} then m.id else null end) as f7cntDeath24h
    , count(distinct case when m.result_id='${death_id}' ${sls24h} and ${age_works_pat} then m.id else null end) as f8cntDeath24h
    , count(distinct case when m.result_id='${death_id}' and m.dateFinish-m.dateStart<=7 then m.id else null end) as f9cntDeath7d
    , count(distinct case when m.result_id='${death_id}' and m.dateFinish-m.dateStart<=7 and ${age_0_17}  then m.id else null end) as f10cntDeath7d
    , count(distinct case when m.result_id='${death_id}' and m.dateFinish-m.dateStart<=7 and ${age_works_pat} then m.id else null end) as f11cntDeath7d
    , count(distinct case when m.result_id='${death_id}' and m.dateFinish-m.dateStart<=30 then m.id else null end) as f12cntDeath30d
    , count(distinct case when m.result_id='${death_id}' and m.dateFinish-m.dateStart<=30 and ${age_0_17} then m.id else null end) as f13cntDeath30d
    , count(distinct case when m.result_id='${death_id}' and m.dateFinish-m.dateStart<=30 and ${age_works_pat} then m.id else null end) as f14cntDeath30d
    ,count(distinct m.id) as f15cntHosp
    ,count(distinct case when ${age_0_17} then m.id else null end) as f16cntHosp
    ,count(distinct case when ${age_works_pat}  then m.id else null end) as f17cntHosp
    , count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as f18cntHosp
    , count(distinct case when m.deniedHospitalizating_id is null and ${age_0_17} then m.id else null end) as f19cntHosp
    , count(distinct case when m.deniedHospitalizating_id is null and ${age_works_pat} then m.id else null end) as f20cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    left join Patient p on p.id=m.patient_id
    left join Vocrayon rayon on rayon.id=pm.rayon_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${hospSql} ${emerIs} ${pigeonHole} ${department} ${phoneMessageType} ${phoneMessageSubType} ${durationSql} ${ageSql} ${deathSql} ${rayonSql}
    group by vpmt.id,vpmt.name
    order by vpmt.name
    " />
                <form action="print-stac_criminalMessage_8.do" method="post" target="_blank">
    Свод по возрастам с ${param.dateBegin} по ${param.dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_militia_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_militia"
      
    action="journal_militiaMessage.do?short=Short&typeView1=1&typeGroup=patient" cellFunction="true" 
	idField="1">
	<msh:tableNotEmpty>
		<tr>
                <th colspan="1" />
                <th colspan="3" class="rightBold">Умершие в стационаре</th>
                <th colspan="3" class="rightBold">Умершие в стационаре в первые 24 часа</th>
                <th colspan="3" class="rightBold">Умершие в течение 0-7 суток </th>
                <th colspan="3" class="rightBold">Умершие в течение 0-30 суток </th>
                <th colspan="3" class="rightBold">Общее кол-во пострадавших</th>
                <th colspan="3" class="rightBold">Кол-во госпитализированных</th>
        </tr>
	</msh:tableNotEmpty>
      <msh:tableColumn columnName="Тип" property="2" />
      <msh:tableColumn columnName="общ. кол-во" property="3"  addParam="&typeDeath=1&typeAge=${typeAge}&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="0-17" property="4"  addParam="&typeDeath=1&typeAge=2&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="труд. нас." property="5"  addParam="&typeDeath=1&typeAge=2&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="общ. кол-во" property="6" addParam="&typeDeath=1&typeDuration=1&typeAge=${typeAge}"/>
      <msh:tableColumn columnName="0-17" property="7"  addParam="&typeDeath=1&typeDuration=1&typeAge=1"/>
      <msh:tableColumn columnName="труд. нас." property="8"  addParam="&typeDeath=1&typeDuration=1&typeAge=2"/>
      <msh:tableColumn columnName="общ. кол-во" property="9"  addParam="&typeDeath=1&typeDuration=2"/>
      <msh:tableColumn columnName="0-17" property="10"  addParam="&typeDeath=1&typeDuration=2&typeAge=1"/>
      <msh:tableColumn columnName="труд. нас." property="11"  addParam="&typeDeath=1&typeDuration=2&typeAge=2"/>
      <msh:tableColumn columnName="общ. кол-во" property="12"  addParam="&typeDeath=1&typeDuration=3&typeAge=${typeAge}"/>
      <msh:tableColumn columnName="0-17" property="13"  addParam="&typeDeath=1&typeDuration=3"/>
      <msh:tableColumn columnName="труд. нас." property="14"  addParam="&typeDeath=1&typeDuration=3"/>
      <msh:tableColumn columnName="общ. кол-во" property="15" addParam="&typeAge=${typeAge}&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="0-17" property="16"  addParam="&typeAge=1&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="труд. нас." property="17"  addParam="&typeAge=2&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="общ. кол-во" property="18" addParam="&typeHosp=1&typeAge=${typeAge}&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="0-17" property="19"  addParam="&typeAge=1&typeHosp=1&typeDuration=${typeDuration}"/>
      <msh:tableColumn columnName="труд. нас." property="20"  addParam="&typeAge=2&typeHosp=1&typeDuration=${typeDuration}"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
     %>
    <% 
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>
<!--lastrelease milamesher 06062018 rayon-->