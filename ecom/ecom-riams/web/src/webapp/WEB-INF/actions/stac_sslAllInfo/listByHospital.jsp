<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Журнал обращений по стационару"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_journalByHospital"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%  	
  String typeAge=ActionUtil.updateParameter("Report_journalByHosp","typeAge","10", request) ;
  String view=ActionUtil.updateParameter("Report_journalByHosp","typeView","1", request) ;
 %>
   <ecom:webQuery name="sex_woman_sql" nativeSql="select id,name from VocSex where omccode='2'"/>
 
    <msh:form action="/stac_journalByHospital.do" defaultField="pigeonHoleName" disableFormDataConfirm="true" method="GET">
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
      
      <msh:row>
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
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" fieldColSpan="7" horizontalFill="true" label="Профиль коек" vocName="vocBedType"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
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
    		+$('department').value+":"
            +$('bedType').value;
    }
    </script>
    <%
    String date = request.getParameter("dateBegin") ;
    String date1 = request.getParameter("dateEnd") ;
    
    if (date!=null && !date.equals(""))  {
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("dateEnd", date);
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	ActionUtil.getValueByList("sex_woman_sql", "sex_woman", request) ;
      	String sexWoman = (String)request.getAttribute("sex_woman") ;

      	String typeDate=ActionUtil.updateParameter("Report14","typeDate","2", request) ;
        String dateAge = "2".equals(typeDate) ? "dateFinish" : "dateStart" ;
        String ageSql = " and cast(to_char(m." + dateAge + ",'yyyy') as int)" +
                " -cast(to_char(p.birthday,'yyyy') as int)" +
                " +(case when (cast(to_char(m." + dateAge + ", 'mm') as int)" +
                " -cast(to_char(p.birthday, 'mm') as int)" +
                " +(case when (cast(to_char(m." + dateAge + ",'dd') as int)" +
                " - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)" +
                " <0)" +
                " then -1 else 0 end)";
        if ("3".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+" < 18 ");
            request.setAttribute("age_info", "Дети") ;
        } else if ("2".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+" >= case when p.sex_id='" + sexWoman + "' then 55 else 60 end ");
            request.setAttribute("reportInfo", "Взрослые старше трудоспособного возраста") ;
        } else if ("1".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+" >= 18 ");
            request.setAttribute("reportInfo", "Взрослые") ;
        } else if ("7".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+ " between 16 and case when p.sex_id='" +
                    sexWoman + "' then 54 else 59 end ");
            request.setAttribute("reportInfo", "Взрослые трудоспособного возраста c 16 лет") ;
        } else if ("8".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+" between 18 and case when p.sex_id='" +
                    sexWoman + "' then 54 else 59 end ");
            request.setAttribute("reportInfo", "Взрослые трудоспособного возраста с 18 лет") ;
        } else if ("9".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+" >= 60 ");
            request.setAttribute("reportInfo", "Взрослые старше 60 лет (вкл)") ;
        } else if ("4".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+ " < 1 ");
            request.setAttribute("reportInfo", "до года") ;
        } else if ("5".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+" between 0 and 14 ");
            request.setAttribute("reportInfo", "0-14") ;
        } else if ("6".equals(typeAge)) {
            request.setAttribute("age_sql", ageSql+" between 15 and 17 ");
            request.setAttribute("reportInfo", "15-17") ;
        }

    	String pigeonHole1 ;
    	String pigeonHole ;
    	String pHole = request.getParameter("pigeonHole") ;
    	if (pHole!=null && !pHole.equals("") && !pHole.equals("0")) {
    		pigeonHole1= " and (ml.pigeonHole_id='"+pHole+"' or ml1.pigeonHole_id='"+pHole+"')" ;
    		pigeonHole= " and ml.pigeonHole_id='"+pHole+"'" ;
    	} else {
            pigeonHole1="";
            pigeonHole="";
        }
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	request.setAttribute("pigeonHole1", pigeonHole1) ;
    	
    	String department;
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		department= " and ml.id="+dep ;
    	} else {
    	    department="";
    	}

    	String bedType = request.getParameter("bedType");
    	String bedTypeSql ;
    	if (bedType!=null && !bedType.equals("")) {
    	    bedTypeSql=" and vbt.id="+bedType;
        } else {
    	    bedTypeSql="";
        }

    	request.setAttribute("bedType", bedTypeSql) ;
    	request.setAttribute("department", department) ;
    	if ("1".equals(view)) { //свод по дням
    %>
    
    <msh:section>
    <ecom:webQuery isReportBase="true" name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="
    
    select  
    '&pigeonHole=${param.pigeonHole}&department=${param.department}&bedType=${param.bedType}&typeAge=${typeAge}&typeDate=${typeDate}&dateI=${dateI}&dateBegin='
    ||to_char(m.${dateI},'dd.mm.yyyy')||'&dateEnd='||to_char(m.${dateI},'dd.mm.yyyy'),
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
left join medcase slo on slo.parent_id = m.id and slo.dtype='DepartmentMedCase' and slo.prevmedcase_id is null
left join bedFund bf on bf.id=slo.bedFund_id
left join vocbedtype vbt on vbt.id= bf.bedType_id
left join MisLpu as ml on ml.id=m.department_id
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id 
where m.dtype='HospitalMedCase' and m.${dateI} 
between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${bedType} ${age_sql}
group by m.${dateI}
order by m.${dateI}
    " />
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
    <msh:table name="journal_priem" cellFunction="true"
    viewUrl="js-stac_sslAllInfo-findByDate.do?short=Short"
    action="js-stac_sslAllInfo-findByDate.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th />
                <th />
                <th colspan=8>Госпитализаций</th>
                <th colspan=6>Отказ</th>
                <th/>
              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Дата" property="2"/>
      <msh:tableColumn isCalcAmount="true" columnName="Плановые" property="3" addParam="&typeHosp=1&typeEmergency=2"/>
      <msh:tableColumn isCalcAmount="true" columnName="Экстренные" property="4" addParam="&typeHosp=1&typeEmergency=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="5" addParam="&typeEmergency=${typeEmergency}&typeHosp=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего с.ж" property="6" addParam="&typeEmergency=${typeEmergency}&typeHosp=1&typePatient=vil"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего гор." property="7" addParam="&typeEmergency=${typeEmergency}&typeHosp=1&typePatient=city"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего иног." property="8" addParam="&typeEmergency=${typeEmergency}&typeHosp=1&typePatient=inog"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего иностр." property="9" addParam="&typeEmergency=${typeEmergency}&typeHosp=1&typePatient=inostr"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего другое" property="10" addParam="&typeEmergency=${typeEmergency}&typeHosp=1&typePatient=other"/>
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="11" addParam="&typeEmergency=${typeEmergency}&typeHosp=2"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них с.ж" property="12" addParam="&typeEmergency=${typeEmergency}&typeHosp=2&typePatient=vil"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="13" addParam="&typeEmergency=${typeEmergency}&typeHosp=2&typePatient=city"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иног." property="14" addParam="&typeEmergency=${typeEmergency}&typeHosp=2&typePatient=inog"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иностр." property="15" addParam="&typeEmergency=${typeEmergency}&typeHosp=2&typePatient=inostr"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них другое" property="16" addParam="&typeEmergency=${typeEmergency}&typeHosp=2&typePatient=other"/>
      <msh:tableColumn isCalcAmount="true" columnName="Обращений" property="17"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>

      <msh:section>
          <ecom:webQuery isReportBase="true" name="journal_priem_general" nameFldSql="journal_priem_general_sql" nativeSql="

    select
    '&pigeonHole=${param.pigeonHole}&department=${param.department}&bedType=${param.bedType}&typeDate=${typeDate}&dateI=${dateI}&dateBegin='
    ||to_char(m.${dateI},'dd.mm.yyyy')||'&dateEnd='||to_char(m.${dateI},'dd.mm.yyyy'),
    m.${dateI}
, count(distinct m.id) as all1
,count(distinct case when m.deniedHospitalizating_id is null then m.id else null end) as obr
, count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as denied

from medcase m
left join medcase slo on slo.parent_id = m.id and slo.dtype='DepartmentMedCase' and slo.prevmedcase_id is null
left join bedFund bf on bf.id=slo.bedFund_id
left join vocbedtype vbt on vbt.id= bf.bedType_id
left join MisLpu as ml on ml.id=m.department_id
left join Patient p on p.id=m.patient_id
left join Address2 a on p.address_addressid=a.addressid
left join Omc_Oksm oo on oo.id=p.nationality_id
where m.dtype='HospitalMedCase' and m.${dateI}
between to_date('${param.dateBegin}','dd.mm.yyyy')
and to_date('${dateEnd}','dd.mm.yyyy')
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department} ${bedType} ${age_sql}
group by m.${dateI}
order by m.${dateI}
    " />
          <msh:sectionTitle>
              <form action="print-stac_journalByHospital_1_short.do" method="post" target="_blank">
                  Общий результат поиска обращений за период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}
                  <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_general_sql}">
                  <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
                  <input type='hidden' name="s" id="s" value="PrintService">
                  <input type='hidden' name="m" id="m" value="printNativeQuery">
                  <input type="submit" value="Печать">
              </form>
          </msh:sectionTitle>
          <msh:sectionContent>
              <msh:table name="journal_priem_general" cellFunction="true"
                         viewUrl="js-stac_sslAllInfo-findByDate.do?short=Short"
                         action="js-stac_sslAllInfo-findByDate.do" idField="1">
                  <msh:tableColumn columnName="Дата" property="2"/>
                  <msh:tableColumn isCalcAmount="true" columnName="Всего обращений" property="3"/>
                  <msh:tableColumn isCalcAmount="true" columnName="Всего госпитализированы" property="4" addParam="&typeEmergency=${typeEmergency}&typeHosp=1"/>
                  <msh:tableColumn isCalcAmount="true" columnName="Всего отказано в госпитализации" property="5" addParam="&typeEmergency=${typeEmergency}&typeHosp=2"/>
              </msh:table>
          </msh:sectionContent>
      </msh:section>

    	<%
    	} else if ("2".equals(view)) { //общий свод госпитализаций
        %>
    <msh:section>
    <msh:sectionTitle>
    <ecom:webQuery isReportBase="true" name="journal_priem_otd" nameFldSql="journal_priem_otd_sql" nativeSql="
    select '&pigeonHole=${param.pigeonHole}&typeDate=${typeDate}&dateI=${dateI}&bedType=${param.bedType}&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}&department='
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
left join medcase slo on slo.parent_id = m.id and slo.dtype='DepartmentMedCase' and slo.prevmedcase_id is null
left join bedFund bf on bf.id=slo.bedFund_id
left join vocbedtype vbt on vbt.id= bf.bedType_id
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
${emerIs} ${pigeonHole} ${department} ${bedType} ${age_sql}
group by m.department_id,dep.name
order by dep.name
    " />
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
    <msh:table name="journal_priem_otd" cellFunction="true" 
    viewUrl="js-stac_sslAllInfo-findHospByPeriod.do?typeHosp=1&short=Short"
    action="js-stac_sslAllInfo-findHospByPeriod.do?typeHosp=1"
     idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th colspan=1></th>
                <th colspan=1></th>
                <th colspan=3>Плановые</th>
                <th colspan=3>Экстренные</th>
                <th colspan=6>Общее кол-во</th>

              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="3" addParam="&typeEmergency=2" />
      <msh:tableColumn isCalcAmount="true" columnName="КСП" property="4" addParam="&typeEmergency=2&typeFrm=kcp" />
      <msh:tableColumn isCalcAmount="true" columnName="самообращение" property="5" addParam="&typeEmergency=2&typeFrm=sam" />
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="6" addParam="&typeEmergency=1" />
      <msh:tableColumn isCalcAmount="true" columnName="КСП" property="7" addParam="&typeEmergency=1&typeFrm=kcp" />
      <msh:tableColumn isCalcAmount="true" columnName="самообращение" property="8" addParam="&typeEmergency=1&typeFrm=sam" />
      <msh:tableColumn isCalcAmount="true" columnName="Всего" identificator="false" property="9" addParam="&typeEmergency=${param.typeEmergency}"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них с.ж" property="10"  addParam="&typeEmergency=${param.typeEmergency}&typePatient=vil"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="11" addParam="&typeEmergency=${param.typeEmergency}&typePatient=city"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иног." property="12" addParam="&typeEmergency=${param.typeEmergency}&typePatient=inog"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них иностр." property="13" addParam="&typeEmergency=${param.typeEmergency}&typePatient=inostr"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них другое" property="14" addParam="&typeEmergency=${param.typeEmergency}&typePatient=other"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    	<%
    	} else if ("3".equals(view)) { // общий свод отказов от госпитализаций
        %>
    <msh:section>
    <ecom:webQuery isReportBase="true" name="journal_priem_denied" nameFldSql="journal_priem_denied_sql" nativeSql="
    
    select '&pigeonHole=${param.pigeonHole}&department=${param.department}&typeDate=${typeDate}&dateI=${dateI}&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}&deniedHospitalizating='
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
and (m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${period}
${emerIs} ${pigeonHole1} ${department} ${age_sql}
group by m.deniedHospitalizating_id,vdh.name
order by vdh.name
    " />
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
    <msh:sectionContent>
    <msh:table name="journal_priem_denied" cellFunction="true"  
    viewUrl="js-stac_sslAllInfo-findDeniedByPeriod.do?short=Short&typeHosp=2"
    action="js-stac_sslAllInfo-findDeniedByPeriod.do?typeHosp=2" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th />
                <th />
                <th colspan=3>Плановые</th>
                <th colspan=3>Экстренные</th>
                <th colspan=6>Общее кол-во</th>
              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Причина отказа" property="2" />
      <msh:tableColumn columnName="Всего" property="3" addParam="&typeEmergency=2"/>
      <msh:tableColumn columnName="КСП" property="4" addParam="&typeEmergency=2&typeFrm=kcp" />
      <msh:tableColumn columnName="самообращение" property="5" addParam="&typeEmergency=2&typeFrm=sam" />
      <msh:tableColumn columnName="Всего" property="6" addParam="&typeEmergency=1" />
      <msh:tableColumn columnName="КСП" property="7" addParam="&typeEmergency=1&typeFrm=kcp" />
      <msh:tableColumn columnName="самообращение" property="8" addParam="&typeEmergency=1&typeFrm=sam" />
      <msh:tableColumn columnName="Всего" identificator="false" property="9" addParam="&typeEmergency=${typeEmergency}" />
      <msh:tableColumn columnName="из них с.ж" property="10" addParam="&typeEmergency=${typeEmergency}&typePatient=vil"/>
      <msh:tableColumn columnName="из них гор." property="11" addParam="&typeEmergency=${typeEmergency}&typePatient=city"/>
      <msh:tableColumn columnName="из них иног." property="12" addParam="&typeEmergency=${typeEmergency}&typePatient=inog"/>
      <msh:tableColumn columnName="из них иностр." property="13" addParam="&typeEmergency=${typeEmergency}&typePatient=inostr"/>
      <msh:tableColumn columnName="из них другое" property="14" addParam="&typeEmergency=${typeEmergency}&typePatient=other"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
      <msh:section>
          <ecom:webQuery isReportBase="true" name="journal_priem_otcaz" nameFldSql="journal_priem_otcaz_sql" nativeSql="

    select '&pigeonHole=${param.pigeonHole}&typeDate=${typeDate}&dateI=${dateI}&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}&department='
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
and m.deniedHospitalizating_id is not null
${period}
${emerIs} ${pigeonHole} ${department} ${age_sql}
group by m.department_id,dep.name
order by dep.name
    " />
          <msh:sectionTitle>
              <form action="print-stac_journalByHospital_2_3.do" method="post" target="_blank">
                  Отказы от госпитализации за период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}
                  <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_otcaz_sql}">
                  <input type='hidden' name="sqlColumn" id="sqlColumn" value="Отделение">
                  <input type='hidden' name="s" id="s" value="PrintService">
                  <input type='hidden' name="m" id="m" value="printNativeQuery">
                  <input type="submit" value="Печать">
              </form>
          </msh:sectionTitle>
      <msh:sectionContent >
          <msh:table name="journal_priem_otcaz" cellFunction="true"
                     viewUrl="js-stac_sslAllInfo-findHospByPeriod.do?typeHosp=1&short=Short&otkaz=1"
                     action="js-stac_sslAllInfo-findHospByPeriod.do?typeHosp=1&otkaz=1"
                     idField="1">
              <msh:tableNotEmpty>
                  <tr>
                      <th colspan=1></th>
                      <th colspan=1></th>
                      <th colspan=3>Плановые</th>
                      <th colspan=3>Экстренные</th>
                      <th colspan=6>Общее кол-во</th>

                  </tr>
              </msh:tableNotEmpty>
              <msh:tableColumn columnName="Отделение" property="2" />
              <msh:tableColumn isCalcAmount="true" columnName="Всего" property="3" addParam="&typeEmergency=2" />
              <msh:tableColumn isCalcAmount="true" columnName="КСП" property="4" addParam="&typeEmergency=2&typeFrm=kcp" />
              <msh:tableColumn isCalcAmount="true" columnName="самообращение" property="5" addParam="&typeEmergency=2&typeFrm=sam" />
              <msh:tableColumn isCalcAmount="true" columnName="Всего" property="6" addParam="&typeEmergency=1" />
              <msh:tableColumn isCalcAmount="true" columnName="КСП" property="7" addParam="&typeEmergency=1&typeFrm=kcp" />
              <msh:tableColumn isCalcAmount="true" columnName="самообращение" property="8" addParam="&typeEmergency=1&typeFrm=sam" />
              <msh:tableColumn isCalcAmount="true" columnName="Всего" identificator="false" property="9" addParam="&typeEmergency=${param.typeEmergency}"/>
              <msh:tableColumn isCalcAmount="true" columnName="из них с.ж" property="10"  addParam="&typeEmergency=${param.typeEmergency}&typePatient=vil"/>
              <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="11" addParam="&typeEmergency=${param.typeEmergency}&typePatient=city"/>
              <msh:tableColumn isCalcAmount="true" columnName="из них иног." property="12" addParam="&typeEmergency=${param.typeEmergency}&typePatient=inog"/>
              <msh:tableColumn isCalcAmount="true" columnName="из них иностр." property="13" addParam="&typeEmergency=${param.typeEmergency}&typePatient=inostr"/>
              <msh:tableColumn isCalcAmount="true" columnName="из них другое" property="14" addParam="&typeEmergency=${param.typeEmergency}&typePatient=other"/>
          </msh:table>
      </msh:sectionContent>
      </msh:section>
    <%
    	} else if ("4".equals(view)) { //свод по часам заболевания при экст. госпит
    %>
    <msh:section>
    <msh:sectionTitle>
    <ecom:webQuery isReportBase="true"  name="journal_priem_otd" nameFldSql="journal_priem_otd_sql" nativeSql="
    
    select '&typeEmergency=${typeEmergency}&pigeonHole=${param.pigeonHole}&bedType=${param.bedType}&typeDate=${typeDate}&dateI=${dateI}&dateBegin=${param.dateBegin}&dateEnd=${dateEnd}&department='
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
left join medcase slo on slo.parent_id = m.id and slo.dtype='DepartmentMedCase' and slo.prevmedcase_id is null
left join bedFund bf on bf.id=slo.bedFund_id
left join vocbedtype vbt on vbt.id= bf.bedType_id
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
${emerIs} ${pigeonHole} ${department} ${bedType} ${age_sql}
group by m.department_id,dep.name
order by dep.name
    " />
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
    <msh:table name="journal_priem_otd"  cellFunction="true"
    viewUrl="js-stac_sslAllInfo-findHospByPeriod.do?short=Short&typeHosp=1"
    action="js-stac_sslAllInfo-findHospByPeriod.do?typeHosp=1"
     idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th colspan=1></th>
                <th colspan=1></th>
                <th colspan=3>Плановые</th>
                <th colspan=4>Экстренные</th>

              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Отделение" property="2"  addParam="&typeEmergency=1" />
      <msh:tableColumn columnName="Всего" property="3" addParam="&typeEmergency=2" />
      <msh:tableColumn columnName="КСП" property="4" addParam="&typeEmergency=2&typeFrm=kcp" />
      <msh:tableColumn columnName="самообращение" property="5" addParam="&typeEmergency=2&typeFrm=sam" />
      <msh:tableColumn columnName="Всего" property="6" addParam="&typeEmergency=1" />
      <msh:tableColumn columnName="Доставленных в первые 6 часов" property="7" addParam="&typeEmergency=1&typeDost=1"/>
      <msh:tableColumn columnName="Доставленных в первые 7-24 часов" property="8" addParam="&typeEmergency=1&typeDost=2" />
      <msh:tableColumn columnName="Доставленных позднее 24 часов" identificator="false" property="9" addParam="&typeEmergency=1&typeDost=3" />

    </msh:table>
    </msh:sectionContent>
    </msh:section>
    	<%
    	}
    } else {%>
    	<i>Нет данных </i>
    	<% }   %>
  </tiles:put>
</tiles:insert>

