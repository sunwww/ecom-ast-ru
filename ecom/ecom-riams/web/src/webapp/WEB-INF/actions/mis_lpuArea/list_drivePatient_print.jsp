<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >

    
  <tiles:put name="body" type="string">
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска ${infoTypePat}.
     Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch} ${dateInfo} ${dateFInfo} ${typeInvInfo}
     ${suicideInfo} ${sexInfo} ${ageInfo}
     </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="
   select distinct pcc.id as pccid
   ,pcc.cardNumber as pcccardNumber
   ,coalesce(p.lastname,'-')||' '||coalesce(p.firstname,'-')|| ' '||coalesce(p.middlename,'-') as lfm
   ,area.startDate as areastartdate,area.finishDate as areafinishdate,p.birthday as pbirthday 
   ,$$ByPatient^ZAddressLib(p.id)
   ,$$getDiagnosis^ZPsychUtil(p.id,isnull(area.finishDate,CURRENT_DATE))
   ,vpor.name as vporname,vptr.name as vptrname,vpsor.name as vpsorname
   from PsychiatricCareCard pcc 
   left join Patient p on p.id=pcc.patient_id 
   left join LpuAreaPsychCareCard area on pcc.id=area.careCard_id 
   left join CompulsoryTreatment ct on pcc.id=ct.careCard_id
   left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id=area.id
   left join PsychiaticObservation po1 on po1.careCard_id=pcc.id and po1.lpuAreaPsychCareCard_id is null
   left join Suicide sui on sui.careCard_id=pcc.id
   left join VocPsychObservationReason vpor on vpor.id=area.observationReason_id
   left join VocPsychTransferReason vptr on vptr.id=area.transferReason_id
   left join VocPsychStrikeOffReason vpsor on vpsor.id=area.stikeOffReason_id
   left join Invalidity inv on inv.patient_id=p.id

   where  area.lpuArea_id=${lpuArea} 
   ${dateT} ${typeI} ${compTreat}
   ${suicide} 
   ${group} ${sexT} ${ageFrom} ${ageTo}
   group by area.id 
   order by p.lastname,p.firstname,p.middlename" />
        <msh:table viewUrl="entityShortView-psych_careCard.do" editUrl="entityParentEdit-psych_careCard.do" deleteUrl="entityParentDeleteGoParentView-psych_careCard.do" name="journal_ticket" action="entityView-psych_careCard.do" idField="1" noDataMessage="Не найдено">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn columnName="№карты" property="2"/>
			<msh:tableColumn columnName="ФИО пациента" property="3"/>
			<msh:tableColumn columnName="Год рождения" property="6"/>
			<msh:tableColumn columnName="Дата взятия" property="4"/>
			<msh:tableColumn columnName="Причина взятия" property="9"/>
			<msh:tableColumn columnName="Дата снятия (перевода)" property="5"/>
			<msh:tableColumn columnName="Причина перевода" property="10"/>
			<msh:tableColumn columnName="Причина снятия" property="11"/>
			<msh:tableColumn columnName="Адрес" property="7"/>
			<msh:tableColumn columnName="Диагноз" property="8"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
 
  </tiles:put>
</tiles:insert>

