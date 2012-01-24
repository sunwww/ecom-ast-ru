<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    <%
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String emer= request.getParameter("emergancyIs") ;
    	if (emer!=null && emer.equals("on")) {
    		request.setAttribute("emerIs","cast(m.emergency as integer)=1") ;
    		request.setAttribute("emerInfo","экстренно") ;
    	} else {
    		request.setAttribute("emerIs","(m.emergency is null or cast(m.emergency as integer) = 0)") ;
    		request.setAttribute("emerInfo","планово") ;
    	}
    	String disc = request.getParameter("dischargeIs") ;
    	String dateI = "dateFinish" ;
    	String timeI = "dischargeTime" ;
    	if (disc!=null && disc.equals("on")) {
    		request.setAttribute("dateI","dateFinish") ;
    		request.setAttribute("timeI","dischargeTime") ;
    		request.setAttribute("dischInfo","выбывшим ") ;
    	} else {
        	dateI = "dateStart" ;
        	timeI = "entranceTime" ;
    		request.setAttribute("dateI","dateStart") ;
    		request.setAttribute("timeI","entranceTime") ;
    		request.setAttribute("dischInfo","поступившим ") ;
    	}
    	String hour8= request.getParameter("hour8") ;
    	String period = "m."+dateI+"= to_date('"+date+"','dd.mm.yyyy')" ;
    	if (hour8!=null && hour8.equals("on")) {
    		Date dat = DateFormat.parseDate(date) ;
    	    Calendar cal = Calendar.getInstance() ;
    	    cal.setTime(dat) ;
    	    cal.add(Calendar.DAY_OF_MONTH, 1) ;
    	    SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy") ;
    	    String date1=format.format(cal.getTime()) ;
    		//request.setAttribute("hour8Is","cast('08:00' as time)") ;
    		request.setAttribute("hour8IsInfo"," (8 часов)") ;
    		period = "((m."+dateI+"= to_date('"+date+"','dd.mm.yyyy') and m."+timeI+">= cast('08:00' as time) )"
    				+" or (m."+dateI+"= to_date('"+date1+"','dd.mm.yyyy') and m."+timeI+"< cast('08:00' as time) )"
    		+")" ;
    	} else {
    		request.setAttribute("hour8IsInfo","") ;
    	}
    	request.setAttribute("period", period) ;
    	String pigeonHole="" ;
    	String pHole = request.getParameter("pigeonHole") ;
    	if (pHole!=null && pHole.equals("") && pHole.equals("0")) {
    		pigeonHole= " and dep.pigeonHole_id='"+pHole+"'" ;
    	}
    	request.setAttribute("pigeonHole", pigeonHole) ;
    	
    	%>

    

    <p>Реестр поступившим за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo}</p>

    <ecom:webQuery name="journal_priem" nativeSql="select 
    m.id as mid
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) as mdateFinish
    ,cast(m.dischargeTime as varchar(5)) as mdischargeTime
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday,sc.code as sccode,m.emergency as memergency
    ,ml.name as mlname,cast(m.entranceTime as varchar(5)) as entranceTime
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join MisLpu as ml on ml.id=m.department_id 
     where m.DTYPE='HospitalMedCase' and ${period}
     and m.deniedHospitalizating_id is null
      and  ${emerIs} ${pigeonHole} order by m.${dateI},ml.name,pat.lastname,pat.firstname,pat.middlename
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Отделение" property="9" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
    <p>Реестр отказов от госпитализаций за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo}</p>
    <ecom:webQuery name="journal_priem" nativeSql="select 
    m.id as mid
    ,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as mdateStart
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
    ,sc.code as sccode,m.emergency as memergency
    ,vdh.name as vdhname
     from MedCase as m  
     left join StatisticStub as sc on sc.medCase_id=m.id 
     left outer join Patient pat on m.patient_id = pat.id 
     left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id  
     where m.DTYPE='HospitalMedCase' and m.${dateI} = to_date('${param.dateBegin}','dd.mm.yyyy')
      and m.deniedHospitalizating_id is not null
      and  ${emerIs} order by m.${dateI},pat.lastname,pat.firstname,pat.middlename
      " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="ФИО пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата обращения" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Экстрено" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Причина отказа" property="6" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
    </msh:table>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    	</tiles:put>
</tiles:insert>
