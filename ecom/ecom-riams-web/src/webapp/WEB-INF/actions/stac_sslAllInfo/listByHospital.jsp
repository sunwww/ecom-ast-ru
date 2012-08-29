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
    		department= " and ml.id='"+dep+"'" ;
    	}
    	request.setAttribute("department", department) ;
    	%>
    	<%if (view!=null && (view.equals("1"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений за период с ${param.dateBegin} по ${dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="
    
    select  
    '${typeEmergency}:${param.pigeonHole}:${department}:${typeDate}:${dateI}:'
    ||to_char(m.${dateI},'dd.mm.yyyy'),
    m.${dateI} 
,count(distinct case when (m.emergency is null or m.emergency='0') and m.deniedHospitalizating_id is null then m.id else null end) as pl
,count(distinct case when m.emergency='1' and m.deniedHospitalizating_id is null then m.id else null end) as em

, CEILING(count(distinct m.id)-count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end)) as obr
, count(distinct case when m.deniedHospitalizating_id is not null then m.id else null end) as denied 
, count(distinct m.id) as all from medcase m 
left join MisLpu as ml on ml.id=m.department_id 
where m.dtype='HospitalMedCase' and m.${dateI} 
between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
${period}
${emerIs} ${pigeonHole} ${department}
group by m.${dateI}
order by m.${dateI}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" 
    viewUrl="js-stac_sslAllInfo-findByDate.do?short=Short"
    action="js-stac_sslAllInfo-findByDate.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th />
                <th />
                <th colspan=3>Госпитализаций</th>
                <th/>
                <th/>
              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Дата" property="2"/>
      <msh:tableColumn columnName="Плановые" property="3"/>
      <msh:tableColumn columnName="Экстренные" property="4"/>
      <msh:tableColumn columnName="Итого" property="5"/>
      <msh:tableColumn columnName="Отказ" property="6"/>
      <msh:tableColumn columnName="Обращений" property="7"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    	<%} 
    	if (view!=null && (view.equals("2"))) {%>
    <msh:section>
    <msh:sectionTitle>Госпитализации</msh:sectionTitle>
    <msh:sectionContent >
    <ecom:webQuery name="journal_priem_otd" nativeSql="
    
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
from medcase m 
left join mislpu dep on dep.id=m.department_id
left join Omc_Frm of1 on of1.id=m.orderType_id
left join MisLpu as ml on ml.id=m.department_id 
where m.dtype='HospitalMedCase' 
and m.${dateI} between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is null
${period}
${emerIs} ${pigeonHole} ${department}
group by m.department_id,dep.name
order by dep.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
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
                <th colspan=1></th>

              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Отделение" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Всего" property="3" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="КСП" property="4" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="самообращение" property="5" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Всего" property="6" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="КСП" property="7" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="самообращение" property="8" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Итого" identificator="false" property="9" guid="7f973955-a5cb-4497-bd0b-f4d05848f049" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    	<%} 
    	if (view!=null && (view.equals("3"))) {%>
    <msh:section>
    <msh:sectionTitle>Отказы от госпитализаций</msh:sectionTitle>
    <msh:sectionContent >
    <ecom:webQuery name="journal_priem_denied" nativeSql="
    
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
from medcase m 
left join vocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
left join Omc_Frm of1 on of1.id=m.orderType_id
left join MisLpu as ml on ml.id=m.department_id 
left join SecUser su on su.login=m.username
left join WorkFunction wf on wf.secUser_id=su.id
left join Worker w on w.id=wf.worker_id
left join MisLpu ml1 on ml1.id=w.lpu_id 
where m.dtype='HospitalMedCase' 
and m.${dateI} between to_date('${param.dateBegin}','dd.mm.yyyy')  
and to_date('${dateEnd}','dd.mm.yyyy')  
and ( m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${period}
${emerIs} ${pigeonHole1} ${department}
group by m.deniedHospitalizating_id,vdh.name
order by vdh.name
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem_denied" 
    viewUrl="js-stac_sslAllInfo-findDeniedByPeriod.do?short=Short"
    action="js-stac_sslAllInfo-findDeniedByPeriod.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th />
                <th colspan=3>Плановые</th>
                <th colspan=3>Экстренные</th>
                <th colspan=1></th>
              </tr>
            </msh:tableNotEmpty>    
      <msh:tableColumn columnName="Причина отказа" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
      <msh:tableColumn columnName="Всего" property="3" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="КСП" property="4" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="самообращение" property="5" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Всего" property="6" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="КСП" property="7" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="самообращение" property="8" guid="8410185d-711a-4851-bca4-913a77381989" />
      <msh:tableColumn columnName="Итого" identificator="false" property="9" guid="7f973955-a5cb-4497-bd0b-f4d05848f049" />
    </msh:table>
    </msh:sectionContent>
    
    </msh:section>
    <% }
    	%>
    	
    	<%
    } else {%>
    	<i>Нет данных </i>
    	<% }   %>

    
  </tiles:put>
</tiles:insert>

