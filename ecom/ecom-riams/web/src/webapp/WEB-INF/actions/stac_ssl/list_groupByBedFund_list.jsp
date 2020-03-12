<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по коечному фонду</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalByBedFund"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_ticketsByNonredidentPatientList.do" defaultField="department" disableFormDataConfirm="true" method="GET" >
    <msh:panel >
      <msh:row >
        <msh:separator label="Параметры поиска" colSpan="7"  />
      </msh:row>
      <msh:row >
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="3">  перевода
        </td>
        </msh:row>
      <msh:row >
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  все
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Доп. статус (typeStatus)" colspan="1"><label for="typePatientName" id="typePatientLabel">Доп.статус:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeStatus" value="1">  отображать
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeStatus" value="2">  не отображать
        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Поток обслуживания (typeSwod)" colspan="1"><label for="typePatientName" id="typePatientLabel">Поток обслуживания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeSwod" value="1">  отображать
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeSwod" value="2">  не отображать
        </td>
        </msh:row>
        <msh:row>
        <td class="label" title="Отображать (typeView)" colspan="1">
        	<label for="typeViewName" id="typeViewLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="1">  реестр по дням
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2">  свод
        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4"
        	label="Отделение" horizontalFill="true" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" fieldColSpan="4"
        	label="Тип коек" horizontalFill="true" vocName="vocBedSubType"/>
        </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с"  />
        <msh:textField property="dateEnd" label="по"  />
		<td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
                  </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	String period = date+":"+dateEnd ;
    	
    	String stat = request.getParameter("typeStatus") ;
    	String swod = request.getParameter("typeSwod") ;
    	String typeView = request.getParameter("typeView") ;
    	boolean isStat = !"2".equals(stat);
    	boolean isSwod = !"2".equals(swod);

    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("dep", " and d.id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    	}
    	String servStream = request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("servStream", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
    	String bedSubType = request.getParameter("bedSubType") ;
    	if (bedSubType!=null && !bedSubType.equals("") && !bedSubType.equals("0")) {
    		request.setAttribute("bedSubTypeSql", " and bf.bedSubType_id="+bedSubType);
    		request.setAttribute("bedSubType", bedSubType) ;
    	} else {
    		request.setAttribute("bedSubType", "0") ;
    	}
    	if (typeView.equals("2")) {
    		request.setAttribute("addGroup", "") ;
    		request.setAttribute("addGroupId", "") ;
    	} else {
    		String dateT=(String)request.getAttribute("dateT") ;
    		request.setAttribute("addGroup", dateT+",") ;
    		request.setAttribute("addGroupId", ",to_char("+dateT+",'dd.mm.yyyy') as dat") ;
    		period="'||to_char("+dateT+",'dd.mm.yyyy')||':'||to_char("+dateT+",'dd.mm.yyyy')||'" ;
    	}
    	request.setAttribute("period", period) ;

		  if (isStat) {
			//
			if (isSwod) {
			%>
			    <ecom:webQuery name="journal_ticket" nativeSql="select  
    	m.bedfund_id||':${period}:${dateT}:${param.typePatient }:'||
    	case when p.additionStatus_id is null then '' else cast(p.additionStatus_id as varchar(10)) end
    	||':${serviceStream}:${department}' 
    	as idparam
    	, d.name as dname,vbt.name as vbtname,vbst.name as vbstname,count(*) as cnt
    	,vss.name as vssname
    	,sum(
    	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end
    	
    	
    	) as sum1
    	,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    	${addGroupId}
    	,count(
    		case when vhr.code='11' then hmc.id else null end
    	) as deathCnt
    	,vas.name as vasname
    	
    from MedCase as m 
    left join MedCase as hmc on hmc.id=m.parent_id
    left join VocHospitalizationResult vhr on vhr.id=hmc.result_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocservicestream as vss on vss.id=bf.servicestream_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient p on p.id=hmc.patient_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where upper(m.DTYPE)=upper('DepartmentMedCase') and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') ${add}
    ${dep} ${servStream} ${bedSubTypeSql}
    group by ${addGroup} m.department_id,m.bedfund_id,vbst.id,p.additionStatus_id,vbt.name,vbst.name,vss.name,vas.name,d.name
    order by ${addGroup} d.name,vss.name,vas.name,vbt.name,vbst.name 
    "  />
    <%} else { %>
			    <ecom:webQuery name="journal_ticket_swod" nativeSql="select  
    	list(''||m.bedFund_id)||':${period}:${dateT}:${param.typePatient }:'||
    	case when p.additionStatus_id is null then '' else cast(p.additionStatus_id as varchar(10)) end
    	||':${serviceStream}:${department}' 
    	as idparam
    	, d.name as dname,vbt.name as vbtname,vbst.name as vbstname,count(*) as cnt
    	,sum(
    	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end
    	
    	
    	) as sum1
    	,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    	${addGroupId}
    	,count(
    		case when vhr.code='11' then hmc.id else null end
    	) as deathCnt
    	,vas.name as vasname
    	    	
    	
    from MedCase as m 
    left join MedCase as hmc on hmc.id=m.parent_id 
    left join VocHospitalizationResult vhr on vhr.id=hmc.result_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocservicestream as vss on vss.id=bf.servicestream_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient p on p.id=hmc.patient_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where upper(m.DTYPE)=upper('DepartmentMedCase') and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') ${add}
    ${dep} ${servStream } ${bedSubTypeSql}
    group by ${addGroup} m.department_id,vbt.id,vbst.id,p.additionStatus_id,vbt.name,vbst.name,vas.name,d.name
    order by ${addGroup} d.name,vas.name,vbt.name,vbst.name
    "  />
			
			<%
    	}
	} else { 
		%> 
		<style type="text/css">
		td.statusPatient,th.statusPatient {
			display: none;
		} 
		</style>
		<%if (isSwod) { %>
    <ecom:webQuery name="journal_ticket" nativeSql="select  
    	m.bedfund_id||':${period}:${dateT}:${param.typePatient }:-'
    	||':${serviceStream}:${department}' 
    	as idparam
    	, d.name as dname,vbt.name as vbtname,vbst.name as vbstname,count(*) as cnt
    	,vss.name as vssname
    	,sum(
    	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end
    	
    	
    	) as sum1
    	,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    	${addGroupId}
    	    	,count(
    		case when vhr.code='11' then hmc.id else null end
    	) as deathCnt
    from MedCase as m 
    left join MedCase as hmc on hmc.id=m.parent_id 
    left join VocHospitalizationResult vhr on vhr.id=hmc.result_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocservicestream as vss on vss.id=bf.servicestream_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient p on p.id=hmc.patient_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where m.DTYPE='DepartmentMedCase' and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') ${add}
    ${dep} ${servStream } ${bedSubTypeSql}
    group by ${addGroup} m.department_id,m.bedfund_id,vbst.id,vbt.name,vbst.name,vss.name,d.name
    order by ${addGroup} d.name,vss.name,vbt.name,vbst.name
    "  />
    <%} else { %>
    <ecom:webQuery name="journal_ticket_swod" nativeSql="select  
    	list(''||m.bedFund_id)||':${period}:${dateT}:${param.typePatient }:-'
    	||':${serviceStream}:${department}' 
    	as idparam
    	, d.name as dname,vbt.name as vbtname,vbst.name as vbstname,count(*) as cnt
    	,sum(
    	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end
    	
    	
    	) as sum1
    	,sum(
    	  case 
			when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1) 
			else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)
		  end
    	
    	) as sum2
    	${addGroupId}
    	    	,count(
    		case when vhr.code='11' then hmc.id else null end
    	) as deathCnt
    from MedCase as m 
    left join MedCase as hmc on hmc.id=m.parent_id 
    left join VocHospitalizationResult vhr on vhr.id=hmc.result_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocservicestream as vss on vss.id=bf.servicestream_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient p on p.id=hmc.patient_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where m.DTYPE='DepartmentMedCase' and ${dateT} between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy') ${add}
    ${dep} ${servStream } ${bedSubTypeSql}
    group by ${addGroup} m.department_id,vbst.id,vbt.id,vbt.name,vbst.name,d.name
    order by ${addGroup} d.name,vbt.name,vbst.name
    "  />
		   
		<%} }%>
<% 
if (typeView.equals("2")) {
	if (isSwod) { %>    
	<msh:section>
    <msh:sectionTitle>Результаты поиска ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${dateInfo}</msh:sectionTitle>
    <msh:sectionContent>
        <msh:table name="journal_ticket" action="stac_groupByBedFundData.do" 
        viewUrl="stac_groupByBedFundData.do?s=Short" 
        idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Статус пациента" property="10" cssClass="statusPatient"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Поток обслужив." property="6"/>
            <msh:tableColumn columnName="Профиль коек" property="3"/>
            <msh:tableColumn columnName="Тип коек" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛО" property="7" cssClass="NotViewInfoStac"/>
            <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛС" property="8" />
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во умерших пациентов" property="9"/>
        </msh:table>
        
    
   		<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByBedFund/NotViewInfoStac">
        <style type="text/css">
		td.NotViewInfoStac,th.NotViewInfoStac {
			display: none;
		} 
		</style>
   		</msh:ifInRole>
   		
    </msh:sectionContent>
    </msh:section>
    <%} else { %>
    <msh:section title="Свод по отделению и профилю коек ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${dateInfo}">
		<msh:table name="journal_ticket_swod" action="stac_groupByBedFundData.do" 
		viewUrl="stac_groupByBedFundData.do?s=Short" 
		idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Статус пациента" property="9" cssClass="statusPatient"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Профиль коек" property="3"/>
            <msh:tableColumn columnName="Тип коек" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛО" property="6" cssClass="NotViewInfoStac"/>
            <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛС" property="7" />
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во умерших пациентов" property="8"/>
            
        </msh:table>    
    </msh:section>
    <% }
	
} else {
    	%>
    	реестр по дням в разработке
    	<%
    	if (isSwod) { %>    
    	<msh:section>
        <msh:sectionTitle>Результаты поиска ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${dateInfo}</msh:sectionTitle>
        <msh:sectionContent>
            <msh:table name="journal_ticket" action="stac_groupByBedFundData.do" 
            viewUrl="stac_groupByBedFundData.do?s=Short" 
            idField="1" noDataMessage="Не найдено">
                <msh:tableColumn columnName="#" property="sn"/>
                <msh:tableColumn columnName="Статус пациента" property="11" cssClass="statusPatient"/>
                <msh:tableColumn columnName="Отделение" property="2"/>
                <msh:tableColumn columnName="Поток обслужив." property="6"/>
                <msh:tableColumn columnName="Профиль коек" property="3"/>
                <msh:tableColumn columnName="Тип коек" property="4"/>
                 <msh:tableColumn columnName="Число" property="9" />
                <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="5"/>
                <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛО" property="7" cssClass="NotViewInfoStac"/>
                <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛС" property="8" />
                <msh:tableColumn isCalcAmount="true" columnName="Кол-во умерших пациентов" property="10"/>
                
            </msh:table>
            
        
       		<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByBedFund/NotViewInfoStac">
            <style type="text/css">
    		td.NotViewInfoStac,th.NotViewInfoStac {
    			display: none;
    		} 
    		</style>
       		</msh:ifInRole>
       		
        </msh:sectionContent>
        </msh:section>
        <%} else { %>
        <msh:section title="Свод по отделению и профилю коек ${infoTypePat}. Период с ${param.dateBegin} по ${dateEnd}. ${infoSearch} ${dateInfo}">
    		<msh:table name="journal_ticket_swod"
    		viewUrl="stac_groupByBedFundData.do?s=Short" 
    		action="stac_groupByBedFundData.do" idField="1" noDataMessage="Не найдено">
                <msh:tableColumn columnName="#" property="sn"/>
                <msh:tableColumn columnName="Статус пациента" property="10" cssClass="statusPatient"/>
                <msh:tableColumn columnName="Отделение" property="2"/>
                <msh:tableColumn columnName="Профиль коек" property="3"/>
                <msh:tableColumn columnName="Тип коек" property="4"/>
                <msh:tableColumn columnName="Число" property="8" />
                <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="5"/>
                <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛО" property="6" cssClass="NotViewInfoStac"/>
                <msh:tableColumn isCalcAmount="true" columnName="Сумма КД по СЛС" property="7" />
                <msh:tableColumn isCalcAmount="true" columnName="Кол-во умерших пациентов" property="9"/>
                
            </msh:table>    
        </msh:section>
        <% }    	
		}
		} else {
		
		%>
    
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
 
     
    <script type='text/javascript'>
    checkFieldUpdate('typeSwod','${typeSwod}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typePatient','${typePatient}',4) ;
    checkFieldUpdate('typeStatus','${typeStatus}',2) ;
    checkFieldUpdate('typeView','${typeView}',2) ;
    
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
       	aValue=+aValue ;
       	var max=chk.length ;
       	if (aValue==0 || (aValue)>(max)) {
       		chk[+aDefaultValue-1].checked='checked' ;
       	} else {
       		chk[+aValue-1].checked='checked' ;
       	}
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_groupByBedFundList.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_groupByBedFundList.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>

