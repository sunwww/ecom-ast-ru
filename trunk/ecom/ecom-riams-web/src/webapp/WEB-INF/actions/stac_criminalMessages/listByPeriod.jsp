<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал сообщений в милицию"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:style_currentMenu currentAction="stac_criminalMessages" />
    	<tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/journal_militiaMessage.do" defaultField="pigeonHoleName" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    <input type="hidden" name="s" id="s" value="HospitalPrintService" />
    <input type="hidden" name="m" id="m" value="printReestrByDay" />
    <input type="hidden" name="id" id="id" value=""/>
    
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="2"  >  свод по дням 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="3"  >  общий свод по госпитализациям
	        </td>
        </msh:row>
        <msh:row>
                <td></td>
                <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView1" value="4"  >  общий свод отказов от госпитализаций
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView1" value="5"  >  общий свод по обращениям
	        </td>
        </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
<%--            <input type="submit" onclick="print()" value="Печать" />
            <input type="submit" onclick="printNew()" value="Печать (по отделениям)" />
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
    	frm.m.value="printHospitalByPeriod" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_hospitalByPeriod.do' ;
    	$('id').value = getCheckedRadio(frm,"typeEmergency")+":"
    		+getCheckedRadio(frm,"typeHour")+":"
    		+getCheckedRadio(frm,"typeDate")+":"
    		+$('dateBegin').value+":"
    		+$('pigeonHole').value+":"
    		+$('department').value;
    }
    </script>

    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String date1 = (String)request.getParameter("dateEnd") ;
    
    if (date!=null && !date.equals(""))  {
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("dateEnd", date);
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	String view = (String)request.getAttribute("typeView1") ;
    	String pigeonHole1="" ;
    	String pigeonHole="" ;
    	String pHole = request.getParameter("pigeonHole") ;
    	if (pHole!=null && !pHole.equals("") && !pHole.equals("0")) {
    		pigeonHole1= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    		pigeonHole= " and ml.pigeonHole_id='"+pHole+"'" ;
    	}
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	request.setAttribute("pigeonHole1", pigeonHole1) ;
    	
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
    	} else if (typeDate1!=null && typeDate1.equals("3")) {
    		request.setAttribute("paramDate", "pm.whenDateEventOccurred") ;
    		request.setAttribute("paramDateInfo", "Дата, когда произошло событие") ;
    	} else {
    		request.setAttribute("paramDate","pm.phoneDate") ;
    		request.setAttribute("paramDateInfo","Дата регистрации сообщения") ;
    	}
    	%>
    	<%if (view!=null && (view.equals("1"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Реестр с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select pm.id, pm.phoneDate
    ,vpht.name||coalesce(' '||vpmst.name,'')
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5)) as whenevent
    ,pm.place as pmplace
    ,coalesce(vpme.name,pm.recieverFio) as reciever
    ,vpmo.name as vphoname,wp.lastname as wplastname
    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat
    ,coalesce(vpmorg.name,pm.phone,pm.recieverOrganization) as organization
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
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department}
    order by ${paramDate}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn property="9" columnName="Пациент"/>
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Когда" property="4" />
      <msh:tableColumn columnName="Место" property="5" />
      <msh:tableColumn columnName="Фамилия принявшего" property="6" />
      <msh:tableColumn columnName="Фамилия передавшего" property="8" />
      <msh:tableColumn columnName="Исход" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }  
    	if (view!=null && (view.equals("2"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по дням с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:'||to_char(${paramDate},'dd.mm.yyyy') as id,to_char(${paramDate},'dd.mm.yyyy') as dateSearch, count(pm.id) as cntMessages
    , count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as cntHosp
    , count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as cntDenied
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join medcase m on m.id=pm.medCase_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department}
    group by ${paramDate}
    order by ${paramDate}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="js-stac_criminalMessages-listByDate.do?short=Short" 
     action="js-stac_criminalMessages-listByDate.do" idField="1">
      <msh:tableColumn columnName="${paramDateInfo}" property="2"/>
      <msh:tableColumn columnName="Кол-во сообщений" property="3"/>
      <msh:tableColumn columnName="Кол-во госпит." property="4" />
      <msh:tableColumn columnName="Кол-во отказов от госпит." property="5" />
      <msh:tableColumn columnName="Кол-во обращений" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    	}
    	
    if (view!=null && (view.equals("3"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по госпитализациям с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:${dateStart}:${dateEnd}:'||coalesce(m.department_id,0)||':'||coalesce(vpmt.id,0) as id,ml.name as mlname,vpmt.name as vpmtname, count(pm.id) as cntPm
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is null
${period}
${emerIs} ${pigeonHole} ${department}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia" 
    viewUrl="js-stac_criminalMessages-listByHospital.do?short=Short"
    action="js-stac_criminalMessages-listByHospital.do?dateSearch=${dateSearch}" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Кол-во сообщений" property="4" />
      <msh:tableColumn columnName="Кол-во госпитализаций" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
	if (view!=null && (view.equals("4"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по отказам с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:${dateStart}:${dateEnd}:'||coalesce(m.department_id,0)||':'||coalesce(vpmt.id,0) as id
    ,ml.name as mlname,vpmt.name as vpmtname
    ,count(pm.id) as cntPm
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${period}
${emerIs} ${pigeonHole} ${department}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="js-stac_criminalMessages-listByDenied.do?short=Short" 
    action="js-stac_criminalMessages-listByDenied.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Кол-во сообщений" property="4" />
      <msh:tableColumn columnName="Кол-во отказов" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%
    } 
	if (view!=null && (view.equals("5"))) {%>
    <msh:section>
    <msh:sectionTitle>Свод по обращениям с ${param.dateBegin} по ${param.dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    select '${typeEmergency}:${param.pigeonHole}:${department}:${paramDate}:${dateStart}:${dateEnd}:'||coalesce(m.department_id,0)||':'||coalesce(vpmt.id,0) as id,ml.name as mlname,vpmt.name as vpmtname, count(pm.id) as cntPm
    , count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as cntHosp
    , count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as cntDenied
    ,count(distinct m.id) as cntHosp
    from PhoneMessage pm 
    left join VocPhoneMessageType vpmt on vpmt.id=pm.phoneMessageType_id
    left join medcase m on m.id=pm.medCase_id
    left join mislpu ml on ml.id=m.department_id
    where pm.dtype='CriminalPhoneMessage'
    and ${paramDate} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department}
    group by m.department_id,ml.name,vpmt.id,vpmt.name
    order by ml.name,vpmt.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
        viewUrl="js-stac_criminalMessages-listByObr.do?short=Short" 
    action="js-stac_criminalMessages-listByObr.do" 
	idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Кол-во сообщений" property="4" />
      <msh:tableColumn columnName="Кол-во госпит." property="5" />
      <msh:tableColumn columnName="Кол-во отказов от госпит." property="6" />
      <msh:tableColumn columnName="Кол-во обращений" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} %>
    <% 
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>