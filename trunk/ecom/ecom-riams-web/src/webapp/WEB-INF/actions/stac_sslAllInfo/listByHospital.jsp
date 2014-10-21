<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Журнал обращений по стационару"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_journalByHospital"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%  	
  String typeAge=ActionUtil.updateParameter("Report14","typeAge","8", request) ;
 
 %>
   <ecom:webQuery name="sex_woman_sql" nativeSql="select id,name from VocSex where omccode='2'"/>
 
    <msh:form action="/stac_journalByHospital.do" defaultField="pigeonHoleName" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
      <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"><label for="typeAgeName" id="typeAgeLabel">Возрастная группа:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="1">  взрослые
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeAge" value="2"  >  взрослые старше труд. возраста
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="3"  >  дети
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="4">  до 1 года
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="5"  >  0-14 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="6"  >  15-17 лет
        </td>
        </msh:row>
        <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
         <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeAge" value="7"  >  взрослые труд. возраста с 16 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeAge" value="8"  >  взрослые труд. возраста с 18 лет
        </td>
       </msh:row>
        <msh:row>
        <td class="label" title="Возрастная группа (typeAge)" colspan="1"></td>
         <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeAge" value="9"  >  взрослые старше 60 лет (вкл)
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="10"  >  все
        </td>
       </msh:row>
      
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="1" >  поступления
        </td>
        <td onclick="if (!document.forms[0].typeView[2].checked) {this.childNodes[1].checked='checked';}" colspan="2">
        	<input type="radio" name="typeDate" value="2" onchange="if (document.forms[0].typeView[2].checked) document.forms[0].typeDate[0].checked='checked'">  выписки
        </td>
        <td onclick="if (!document.forms[0].typeView[2].checked) {this.childNodes[1].checked='checked';}" colspan="3">
        	<input type="radio" name="typeDate" value="3" onchange="if (document.forms[0].typeView[2].checked) document.forms[0].typeDate[0].checked='checked'">  состоящие
        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="1">  свод по дням
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="2"  >  общий свод госпитализаций
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkFieldUpdate('typeDate',1,1);"  colspan="2">
	        	<input type="radio" name="typeView" value="3"  >  общий свод отказов от госпитализаций
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="4">  свод по часам заболевания при экст. госпит.  
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
    checkFieldUpdate('typeAge','${typeAge}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
  
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
    	frm.action='stac_journalByHospital.do' ;
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
    	ActionUtil.getValueByList("sex_woman_sql", "sex_woman", request) ;
      	String sexWoman = (String)request.getAttribute("sex_woman") ;
      	String dateAge="dateStart" ;
      	String typeDate=ActionUtil.updateParameter("Report14","typeDate","2", request) ;
    	if (typeDate!=null && typeDate.equals("2")) {
    		dateAge="dateFinish" ;
    	}
    	if (typeAge!=null &&typeAge.equals("3")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
      				.append(" -cast(to_char(p.birthday,'yyyy') as int)")
      				.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
      				.append(" -cast(to_char(p.birthday, 'mm') as int)")
      				.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
      				.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
      				.append(" <0)")
      				.append(" then -1 else 0 end) < 18 ") ;
      		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("age_info", "Дети") ;
      	} else if (typeAge!=null &&typeAge.equals("2")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
    			.append(" then -1 else 0 end) >= case when p.sex_id='").append(sexWoman).append("' then 55 else 60 end ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "Взрослые старше трудоспособного возраста") ;
      	} else if (typeAge!=null &&typeAge.equals("1")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
    			.append(" then -1 else 0 end) >= 18 ") ;
      		//.append(" then -1 else 0 end) between 18 and case when p.sex_id='").append(sexWoman).append("' then 55 else 60 end ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "Взрослые") ;
      	} else if (typeAge!=null &&typeAge.equals("7")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
      			.append(" then -1 else 0 end) between 16 and case when p.sex_id='")
      			.append(sexWoman).append("' then 54 else 59 end ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "Взрослые трудоспособного возраста c 16 лет") ;
      	} else if (typeAge!=null &&typeAge.equals("8")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
      			.append(" then -1 else 0 end) between 18 and case when p.sex_id='")
      			.append(sexWoman).append("' then 54 else 59 end ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "Взрослые трудоспособного возраста с 18 лет") ;
      	} else if (typeAge!=null &&typeAge.equals("9")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
      			.append(" then -1 else 0 end) >= 60 ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "Взрослые старше 60 лет (вкл)") ;
      	} else if (typeAge!=null &&typeAge.equals("4")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
    			.append(" then -1 else 0 end) < 1 ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "до года") ;
      	} else if (typeAge!=null &&typeAge.equals("5")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
    			.append(" then -1 else 0 end) between 0 and 14 ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "0-14") ;
      	} else if (typeAge!=null &&typeAge.equals("6")) {
      		StringBuilder age = new StringBuilder() ;
      		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
    			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
    			.append(" -cast(to_char(p.birthday, 'mm') as int)")
    			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
    			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
    			.append(" <0)")
    			.append(" then -1 else 0 end) between 15 and 17 ") ;
    		request.setAttribute("age_sql", age.toString()) ;
      		request.setAttribute("reportInfo", "15-17") ;
      	}
    	
    	
    	String view = (String)request.getAttribute("typeView") ;
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
    		department= " and ml.id="+dep+"" ;
    	}
    	request.setAttribute("department", department) ;
    	%>
    	<%if (view!=null && (view.equals("1"))) {%>
    
    <msh:section>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="
    
    select  
    '${typeEmergency}:${param.pigeonHole}:${department}:${typeDate}:${dateI}:'
    ||to_char(m.${dateI},'dd.mm.yyyy'),
    m.${dateI} 
,count(distinct case when (m.emergency is null or m.emergency='0') and m.deniedHospitalizating_id is null then m.id else null end) as pl
,count(distinct case when m.emergency='1' and m.deniedHospitalizating_id is null then m.id else null end) as em
,count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as obr
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsVillage='1'
then m.id else null end) as obrVil
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsCity='1'
then m.id else null end) as obrCity
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and a.addressid is not null and substring(a.kladr,1,2)!='30'
then m.id else null end) as obrInog
,count(distinct case when m.deniedHospitalizating_id is null 
and oo.voc_code!='643'  then m.id else null end) as obrInost
,count(distinct case when m.deniedHospitalizating_id is null 
and (oo.voc_code='643' or oo.id is null) and (a.addressid is null or a.domen<3)  then m.id else null end) as obrOther

, count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as denied 
,count(distinct case when m.deniedHospitalizating_id is not null 
and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsVillage='1'
then m.id else null end) as deniedVil
,count(distinct case when m.deniedHospitalizating_id is not null 
and (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsCity='1'
then m.id else null end) as deniedCity
,count(distinct case when m.deniedHospitalizating_id is not null 
and (oo.voc_code='643' or oo.id is null) and a.addressid is not null and substring(a.kladr,1,2)!='30'
then m.id else null end) as deniedInog
,count(distinct case when m.deniedHospitalizating_id is not null 
and oo.voc_code!='643' then m.id else null  end) as deniedInost
,count(distinct case when m.deniedHospitalizating_id is not null 
and (oo.voc_code='643' or oo.id is null) and (a.addressid is null or a.domen<3) then m.id else null  end) as deniedOther
, count(distinct m.id) as all1

from medcase m 
left join MisLpu as ml on ml.id=m.department_id
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id 
where m.dtype='HospitalMedCase' and m.${dateI} 
between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${age_sql}
group by m.${dateI}
order by m.${dateI}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    <form action="print-stac_journalByHospital_1.do" method="post" target="_blank">
        Результаты поиска обращений за период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}
        <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_sql}"> 
        <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
        <input type='hidden' name="s" id="s" value="PrintService">
        <input type='hidden' name="m" id="m" value="printNativeQuery">
        <input type="submit" value="Печать"> 
                                </form>
    </msh:sectionTitle>                             
    <msh:sectionContent>
    <msh:table name="journal_priem" 
    viewUrl="js-stac_sslAllInfo-findByDate.do?short=Short"
    action="js-stac_sslAllInfo-findByDate.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th />
                <th />
                <th colspan=8>Госпитализаций</th>
                <th colspan=6>Отказ</th>
                <th/>
              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Дата" property="2"/>
      <msh:tableColumn isCalcAmount="true" columnName="Плановые" property="3"/>
      <msh:tableColumn isCalcAmount="true" columnName="Экстренные" property="4"/>
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="5"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего с.ж" property="6"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего гор." property="7"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего иног." property="8"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего иностр." property="9"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего другое" property="10"/>
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="11"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них с.ж" property="12"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="13"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иног." property="14"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иностр." property="15"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них другое" property="16"/>
      <msh:tableColumn isCalcAmount="true" columnName="Обращений" property="17"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    	<%} 
    	if (view!=null && (view.equals("2"))) {%>
    <msh:section>
    <msh:sectionTitle>
    <ecom:webQuery name="journal_priem_otd" nameFldSql="journal_priem_otd_sql" nativeSql="
    
    select '${typeEmergency}:${param.pigeonHole}:${department}:${typeDate}:${dateI}:${param.dateBegin}:${dateEnd}:'
    ||m.department_id 
,dep.name
,count(distinct case when (m.emergency is null or m.emergency='0') then m.id else null end) as pl
,count(distinct case when (m.emergency is null or m.emergency='0') and of1.voc_code='К' then m.id else null end) as plK
,count(distinct case when (m.emergency is null or m.emergency='0')  and of1.voc_code='О' then m.id else null end) as plO
,count(distinct case when m.emergency='1' then m.id else null end) as em
,count(distinct case when m.emergency='1' and of1.voc_code='К' then m.id else null end) as emK
,count(distinct case when m.emergency='1'  and of1.voc_code='О' then m.id else null end) as emO
,count(distinct m.id) as obr
,count(distinct case when (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsVillage='1'
then m.id else null end) as obrVil
,count(distinct case when (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsCity='1'
then m.id else null end) as obrCity
,count(distinct case when (oo.voc_code='643' or oo.id is null) and a.addressid is not null and substring(a.kladr,1,2)!='30'
then m.id else null end) as obrInog
,count(distinct case when oo.voc_code!='643'  then m.id else null end) as obrInost
,count(distinct case when (oo.voc_code='643' or oo.id is null) and (a.addressid is null or a.domen<3)  then m.id else null end) as obrOther
from medcase m 
left join mislpu dep on dep.id=m.department_id
left join Omc_Frm of1 on of1.id=m.orderType_id
left join MisLpu as ml on ml.id=m.department_id 
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id 
where m.dtype='HospitalMedCase' 
and m.${dateI} between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is null
${period}
${emerIs} ${pigeonHole} ${department} ${age_sql}
group by m.department_id,dep.name
order by dep.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <form action="print-stac_journalByHospital_2_3.do" method="post" target="_blank">
        Госпитализации за период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}
        <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_otd_sql}"> 
        <input type='hidden' name="sqlColumn" id="sqlColumn" value="Отделение">
        <input type='hidden' name="s" id="s" value="PrintService">
        <input type='hidden' name="m" id="m" value="printNativeQuery">
        <input type="submit" value="Печать"> 
                                </form>
    </msh:sectionTitle>
    <msh:sectionContent >
    <msh:table name="journal_priem_otd" 
    viewUrl="js-stac_sslAllInfo-findHospByPeriod.do?short=Short"
    action="js-stac_sslAllInfo-findHospByPeriod.do"
     idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan=1></th>
                <th colspan=1></th>
                <th colspan=3>Плановые</th>
                <th colspan=3>Экстренные</th>
                <th colspan=6>Общее кол-во</th>

              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Отделение" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="3" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn isCalcAmount="true" columnName="КСП" property="4" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn isCalcAmount="true" columnName="самообращение" property="5" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="6" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn isCalcAmount="true" columnName="КСП" property="7" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn isCalcAmount="true" columnName="самообращение" property="8" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn isCalcAmount="true" columnName="Всего" identificator="false" property="9" guid="7f973955-a5cb-4497-bd0b-f4d05848f049" />
      <msh:tableColumn isCalcAmount="true" columnName="из них с.ж" property="10"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="11"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иног." property="12"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иностр." property="13"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них другое" property="14"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    	<%} 
    	if (view!=null && (view.equals("3"))) {%>
    <msh:section>
    <ecom:webQuery name="journal_priem_denied" nameFldSql="journal_priem_denied_sql" nativeSql="
    
    select '${typeEmergency}:${param.pigeonHole}:${department}:${typeDate}:${dateI}:${param.dateBegin}:${dateEnd}:'
    ||m.deniedHospitalizating_id 
,vdh.name
,count(distinct case when (m.emergency is null or m.emergency='0') then m.id else null end) as pl
,count(distinct case when (m.emergency is null or m.emergency='0') and of1.voc_code='К' then m.id else null end) as plK
,count(distinct case when (m.emergency is null or m.emergency='0')  and of1.voc_code='О' then m.id else null end) as plO
,count(distinct case when m.emergency='1' then m.id else null end) as em
,count(distinct case when m.emergency='1' and of1.voc_code='К' then m.id else null end) as emK
,count(distinct case when m.emergency='1'  and of1.voc_code='О' then m.id else null end) as emO

,count(distinct m.id) as obr

,count(distinct case when (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsVillage='1'
then m.id else null end) as obrVil
,count(distinct case when (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsCity='1'
then m.id else null end) as obrCity
,count(distinct case when (oo.voc_code='643' or oo.id is null) and a.addressid is not null and substring(a.kladr,1,2)!='30'
then m.id else null end) as obrInog
,count(distinct case when oo.voc_code!='643'  then m.id else null end) as obrInost
,count(distinct case when (oo.voc_code='643' or oo.id is null) and (a.addressid is null or a.domen<3)  then m.id else null end) as obrOther

from medcase m 
left join vocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
left join Omc_Frm of1 on of1.id=m.orderType_id
left join MisLpu as ml on ml.id=m.department_id 
left join SecUser su on su.login=m.username
left join WorkFunction wf on wf.secUser_id=su.id
left join Worker w on w.id=wf.worker_id
left join MisLpu ml1 on ml1.id=w.lpu_id
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id 
 
where m.dtype='HospitalMedCase' 
and m.${dateI} between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${period}
${emerIs} ${pigeonHole1} ${department} ${age_sql}
group by m.deniedHospitalizating_id,vdh.name
order by vdh.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
        <form action="print-stac_journalByHospital_2_3.do" method="post" target="_blank">
        Отказы от госпитализации за период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}
        <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_denied_sql}"> 
        <input type='hidden' name="sqlInfo" id="sqlInfo" value="Причина отказа">
        <input type='hidden' name="sqlColumn" id="sqlColumn" value="Причина отказа">
        
        <input type='hidden' name="s" id="s" value="PrintService">
        <input type='hidden' name="m" id="m" value="printNativeQuery">
        <input type="submit" value="Печать"> 
                                </form>
</msh:sectionTitle>
    <msh:sectionContent >
    <msh:table name="journal_priem_denied" 
    viewUrl="js-stac_sslAllInfo-findDeniedByPeriod.do?short=Short"
    action="js-stac_sslAllInfo-findDeniedByPeriod.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th />
                <th />
                <th colspan=3>Плановые</th>
                <th colspan=3>Экстренные</th>
                <th colspan=6>Общее кол-во</th>
              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Причина отказа" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Всего" property="3" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="КСП" property="4" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="самообращение" property="5" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Всего" property="6" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="КСП" property="7" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="самообращение" property="8" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Всего" identificator="false" property="9" guid="7f973955-a5cb-4497-bd0b-f4d05848f049" />
      <msh:tableColumn columnName="из них с.ж" property="10"/>
      <msh:tableColumn columnName="из них гор." property="11"/>
      <msh:tableColumn columnName="из них иног." property="12"/>
      <msh:tableColumn columnName="из них иностр." property="13"/>
      <msh:tableColumn columnName="из них другое" property="14"/>
    </msh:table>
    </msh:sectionContent>
    
    </msh:section>
    <% }
    	%>
   	    	<%if (view!=null && (view.equals("4"))) {%>
    <msh:section>
    <msh:sectionTitle>
    <ecom:webQuery name="journal_priem_otd" nameFldSql="journal_priem_otd_sql" nativeSql="
    
    select '${typeEmergency}:${param.pigeonHole}:${department}:${typeDate}:${dateI}:${param.dateBegin}:${dateEnd}:'
    ||m.department_id 
,dep.name
,count(distinct case when (m.emergency is null or m.emergency='0') then m.id else null end) as pl
,count(distinct case when (m.emergency is null or m.emergency='0') and of1.voc_code='К' then m.id else null end) as plK
,count(distinct case when (m.emergency is null or m.emergency='0')  and of1.voc_code='О' then m.id else null end) as plO
,count(distinct case when m.emergency='1' then m.id else null end) as em
,count(distinct case when m.emergency='1' and vpat.code='1' then m.id else null end) as em1
,count(distinct case when m.emergency='1' and vpat.code='2' then m.id else null end) as em2
,count(distinct case when m.emergency='1' and vpat.code='3' then m.id else null end) as em3
from medcase m 
left join VocPreAdmissionTime vpat on vpat.id=m.preAdmissionTime_id
left join mislpu dep on dep.id=m.department_id
left join Omc_Frm of1 on of1.id=m.orderType_id
left join MisLpu as ml on ml.id=m.department_id 
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id 
where m.dtype='HospitalMedCase' 
and m.${dateI} between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is null
${period}
${emerIs} ${pigeonHole} ${department} ${age_sql}
group by m.department_id,dep.name
order by dep.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <form action="print-stac_journalByHospital_4.do" method="post" target="_blank">
        Госпитализации за период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}
        <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_otd_sql}"> 
        <input type='hidden' name="sqlColumn" id="sqlColumn" value="Отделение">
        <input type='hidden' name="s" id="s" value="PrintService">
        <input type='hidden' name="m" id="m" value="printNativeQuery">
        <input type="submit" value="Печать"> 
                                </form>
    </msh:sectionTitle>
    <msh:sectionContent >
    <msh:table name="journal_priem_otd" 
    viewUrl="js-stac_sslAllInfo-findHospByPeriod.do?short=Short"
    action="js-stac_sslAllInfo-findHospByPeriod.do"
     idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan=1></th>
                <th colspan=1></th>
                <th colspan=3>Плановые</th>
                <th colspan=4>Экстренные</th>

              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Отделение" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Всего" property="3" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="КСП" property="4" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="самообращение" property="5" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Всего" property="6" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Доставленных в первые 6 часов" property="7" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Доставленных в первые 7-24 часов" property="8" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Доставленных позднее 24 часов" identificator="false" property="9" guid="7f973955-a5cb-4497-bd0b-f4d05848f049" />

    </msh:table>
    </msh:sectionContent>
    </msh:section>
    	<%} %>
    	<%
    } else {%>
    	<i>Нет данных </i>
    	<% }   %>

    
  </tiles:put>
</tiles:insert>

