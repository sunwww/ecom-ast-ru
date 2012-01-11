<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр данных по Форме 039/у-02 </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="report039"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_f039_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="f039"/>
    <input type="hidden" name="s" id="s" value="TicketService"/>
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="1"/>
    <msh:panel colsWidth="1%,1%,1%">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" vocName="vocWorkFunction" 
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunction" 
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lpu" vocName="lpu"
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" vocName="vocServiceStream"
        		horizontalFill="true" fieldColSpan="6" size="70"/>
        </msh:row>
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1">  датам
	        </td>
	        
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2">ЛПУ
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3">  врачам
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4">  специальностям
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td colspan="3" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="5">Поток обслуживания  
	        </td>

        </msh:row>
        <msh:row>
        <td colspan="3" class="buttons">
			<input type="button" title="Найти [CTRL+ENTER]" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;poly_f039_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать [CTRL+ENTER]" onclick="this.value=&quot;Печать&quot;; getId(0);this.form.action=&quot;print-f039.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать bis [CTRL+ENTER]" onclick="this.value=&quot;Печать bis&quot;; getId(1);this.form.action=&quot;print-f039add.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать bis" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать cons [CTRL+ENTER]" onclick="this.value=&quot;Печать cons&quot;; getId(0);this.form.action=&quot;print-f039cons.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать cons" class="default" id="submitButton" autocomplete="off">
		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}</msh:sectionTitle>
    <msh:sectionContent>
<%--     <ecom:webQuery name="journal_ticket" nativeSql="SELECT '${beginDate}:${finishDate}:${typeGroup}:'||${groupByTId} as id
,${groupByTName} as tfield
,count(*) as cntAll
,count(case when (ad1.domen=5 or ad2.domen=5) then 1 else null end) as cntVil
,count(case when 
	(to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) 
	then 1 else null end) as cntAll17
,count(case when 
	(to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) then 1 else null end) as cntAll60
,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllnessAll
,count(case when vr.code='ILLNESS' 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnes17
,count(case when vr.code='ILLNESS' and 
	(to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) then 1 else null end) as cntIllnes60
,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProfAll
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnesHome17
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-2),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnesHome01
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) 
	then 1 else null end) as cntIllnesHome60
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntProfHome17
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-2),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntProfHome01
,count(case when (vss.code='OBLIGATORYINSURANCE') then 1 else null end) as cntOMC
,count(case when (vss.code='BUDGET') then 1 else null end) as cntBudget
,count(case when (vss.code='CHARGED') then 1 else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then 1 else null end) as cntPrivateIns
FROM TICKET t 
LEFT JOIN Medcard m on m.id=t.medcard_id
LEFT JOIN Patient p ON p.id=m.person_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId
LEFT JOIN Address2 ad2 on ad2.addressId=ad1.parent_addressId 
LEFT JOIN VocReason vr on vr.id=t.vocReason_id
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=t.vocServicePlace_id
LEFT JOIN VocServiceStream vss on vss.id=t.vocPaymentType_id
LEFT JOIN WorkFunction wf on wf.id=t.workFunction_id
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id
LEFT JOIN Worker w on w.id=wf.worker_id
LEFT JOIN Patient wp on wp.id=w.person_id
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id
WHERE t.date BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') and t.status='2' 
 ${lpuAdd} ${workFunctionAdd} ${specAdd} ${serviceStreamAddT}

GROUP BY ${groupByT} ORDER BY ${groupByTOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
--%>
<ecom:webQuery name="journal_ticket" nativeSql="
${queryTextBegin}
,count(*) as cntAll
,count(case when (ad1.domen=5 or ad2.domen=5) then 1 else null end) as cntVil
,count(case when 
	(to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) 
	then 1 else null end) as cntAll17
,count(case when 
	(to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) then 1 else null end) as cntAll60
,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllnessAll
,count(case when vr.code='ILLNESS' 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnes17
,count(case when vr.code='ILLNESS' and 
	(to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) then 1 else null end) as cntIllnes60
,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProfAll
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHomeAll
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnesHome17
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-2),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntIllnesHome01
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy') >= p.birthday) 
	then 1 else null end) as cntIllnesHome60
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntProfHome17
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (to_date(to_char(t.date,'dd.mm')||'.'
	||(cast(to_char(t.date,'yyyy') as int)-2),'dd.mm.yyyy') < p.birthday) then 1 else null end) as cntProfHome01
,count(case when (vss.code='OBLIGATORYINSURANCE') then 1 else null end) as cntOMC
,count(case when (vss.code='BUDGET') then 1 else null end) as cntBudget
,count(case when (vss.code='CHARGED') then 1 else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then 1 else null end) as cntPrivateIns
,count(case when vr.code='CONSULTATION' then 1 else null end) as cntConsultationAll

${queryTextEnd}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
        <msh:table
         name="journal_ticket" action="entityView-poly_ticket.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="${groupByTitle}" property="2"/>            
            <msh:tableColumn columnName="Кол-во посещ" property="3"/>
            <msh:tableColumn columnName="из них сельс.жител." property="4"/>
            <msh:tableColumn columnName="в том числе до 17 лет" property="5"/>
            <msh:tableColumn columnName="в том числе старше 60 лет" property="6"/>
            <msh:tableColumn columnName="По поводу конс" property="22"/>            
            <msh:tableColumn columnName="По поводу забол" property="7"/>
            <msh:tableColumn columnName="в том числе до 17 лет" property="8"/>
            <msh:tableColumn columnName="в том числе старше 60 лет" property="9"/>
            <msh:tableColumn columnName="Профилак." property="10"/>
            <msh:tableColumn columnName="На дому" property="11"/>
            <msh:tableColumn columnName="На дому по забол." property="12"/>
            <msh:tableColumn columnName="в том числе до 17 лет" property="13"/>
            <msh:tableColumn columnName="0-1(вкл) лет" property="14"/>
            <msh:tableColumn columnName="в том числе старше 60 лет" property="15"/>
            <msh:tableColumn columnName="На дому проф до 17 лет" property="16"/>
            <msh:tableColumn columnName="0-1(вкл) лет" property="17"/>
            <msh:tableColumn columnName="ОМС" property="18"/>
            <msh:tableColumn columnName="бюджет" property="19"/>
            <msh:tableColumn columnName="платные" property="20"/>
            <msh:tableColumn columnName="ДМС" property="21"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	var typeGroup = document.forms[0].typeGroup ;
  	var typeGroupId = +'${typeGroup}' ; 
  	if(typeGroupId==0) typeGroupId=1 ; 
  	typeGroup[typeGroupId-1].checked='checked' ;
    
  	function getId(aBis) {
		var args=$('beginDate').value+":"+$('finishDate').value
 			+":"+getCheckedValue(typeGroup)
 			+":"+$('specialist').value
 			+":"+$('workFunction').value
 			+":"+$('lpu').value
 			+":"+$('serviceStream').value
 			+":1";
		//aSpecialist, aWorkFunction, aLpu, aServiceStream
		//aGroupBy, aStartDate, aFinishDate
		//, aSpecialist, aWorkFunction, aLpu, aServiceStream 			
		$('id').value =args ; 
		if (+aBis>0) {
			$('m').value='f039add' ;
		} else {
			$('m').value='f039' ;
		}
		
	}
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++){
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		} 
  		return radioValue ;
  	}
  		
  	</script>
  </tiles:put>

</tiles:insert>

