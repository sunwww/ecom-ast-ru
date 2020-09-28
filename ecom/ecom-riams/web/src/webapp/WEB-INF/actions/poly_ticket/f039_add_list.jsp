<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Просмотр данных по Форме 039/у-02 bis </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:ticket_finds currentAction="form039add"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_f039_add_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="m" id="m" value="f039add"/>
    <input type="hidden" name="s" id="s" value="TicketService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel colsWidth="1%,1%,1%">
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" />
        	<msh:textField property="finishDate" label="по" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunction" 
        		horizontalFill="true" fieldColSpan="3" size="70"/>
        </msh:row>
        <msh:row>
        
        <td colspan="3" class="buttons">
			<input type="button" title="Найти [CTRL+ENTER]" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;poly_f039_add_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать [CTRL+ENTER]" onclick="this.value=&quot;Печать&quot;; getId(0);this.form.action=&quot;print-f039add.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать bis [CTRL+ENTER]" onclick="this.value=&quot;Печать&quot;; getId(1);this.form.action=&quot;print-f039add.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}. ${specInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="SELECT '${beginDate}:${finishDate}:'||w.lpu_id as id
,to_char(t.date,'dd.mm.yyyy') as tdate
,count(*) as cntAll
,count(case when (to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-17),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntAll17
,count(case when (to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntAll60
,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllness
,count(case when vr.code='ILLNESS' and (to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy')>p.birthday) then 1 else null end) as cntIllnes17
,count(case when vr.code='ILLNESS' and (p.birthday>=to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy')) then 1 else null end) as cntIllnes60
,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProf
,count(case when (vr.code='HOME' or vr.code='HOMEACTIVE') then 1 else null end) as cntHome
,count(case when vr.code='ILLNESS' and (vr.code='HOME' or vr.code='HOMEACTIVE') then 1 else null end) as cntIllnesHome
,count(case when vr.code='ILLNESS' and (vr.code='HOME' or vr.code='HOMEACTIVE') and (to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy')>p.birthday) then 1 else null end) as cntIllnesHome17
,count(case when vr.code='ILLNESS' and (vr.code='HOME' or vr.code='HOMEACTIVE') and (to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-1),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntIllnesHome1
,count(case when vr.code='ILLNESS' and (vr.code='HOME' or vr.code='HOMEACTIVE') and (p.birthday>=to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-60),'dd.mm.yyyy')) then 1 else null end) as cntIllnesHome60
,count(case when vr.code='PROFYLACTIC' and (vr.code='HOME' or vr.code='HOMEACTIVE') and (to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-18),'dd.mm.yyyy')>p.birthday) then 1 else null end) as cntProfHome17
,count(case when vr.code='PROFYLACTIC' and (vr.code='HOME' or vr.code='HOMEACTIVE') and (to_date(to_char(t.date,'dd.mm')||'.'||(cast(to_char(t.date,'yyyy') as int)-1),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntProfHome1
,count(case when (vss.code='OBLIGATORYINSURANCE') then 1 else null end) as cntOMC
,count(case when (vss.code='BUDGET') then 1 else null end) as cntBudget
,count(case when (vss.code='CHARGED') then 1 else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then 1 else null end) as cntPrivateIns
,count(case when (ad1.domen=5 or ad2.domen=5) then 1 else null end) as cntVil
FROM TICKET t 
LEFT JOIN Medcard m on m.id=t.medcard_id
LEFT JOIN Patient p ON p.id=m.person_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId
LEFT JOIN Address2 ad2 on ad2.addressId=ad1.parent_addressId 
LEFT JOIN VocReason vr on vr.id=t.vocReason_id
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=t.vocServicePlace_id
LEFT JOIN VocServiceStream vss on vss.id=t.vocPaymentType_id
LEFT JOIN WorkFunction wf on wf.id=t.workFunction_id
LEFT JOIN Worker w on w.id=wf.worker_id
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id
WHERE t.date BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') and t.status='2' ${specAdd}
GROUP BY t.date 
" />
        <msh:table
         name="journal_ticket" action="entityView-poly_ticket.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Дата" property="2"/>            
            <msh:tableColumn columnName="Кол-во посещ" property="3"/>
            <msh:tableColumn columnName="из них сельс.жител." property="21"/>
            <msh:tableColumn columnName="в том числе до 17 лет" property="4"/>
            <msh:tableColumn columnName="в том числе старше 60 лет" property="5"/>
            <msh:tableColumn columnName="По поводу забол" property="6"/>
            <msh:tableColumn columnName="в том числе до 17 лет" property="7"/>
            <msh:tableColumn columnName="в том числе старше 60 лет" property="8"/>
            <msh:tableColumn columnName="Профилак." property="9"/>
            <msh:tableColumn columnName="На дому" property="10"/>
            <msh:tableColumn columnName="На дому по забол." property="11"/>
            <msh:tableColumn columnName="в том числе до 17 лет" property="12"/>
            <msh:tableColumn columnName="0-1(вкл) лет" property="13"/>
            <msh:tableColumn columnName="в том числе старше 60 лет" property="14"/>
            <msh:tableColumn columnName="На дому проф до 17 лет" property="15"/>
            <msh:tableColumn columnName="0-1(вкл) лет" property="16"/>
            <msh:tableColumn columnName="ОМС" property="17"/>
            <msh:tableColumn columnName="бюджет" property="18"/>
            <msh:tableColumn columnName="платные" property="19"/>
            <msh:tableColumn columnName="ДМС" property="20"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
		function getId(aBis) {
  			var args=$('beginDate').value+":"+$('finishDate').value
  			+":"+$('specialist').value;
  			
			$('id').value =args ;
			if (+aBis>0) {
				$('m').value='f039add' ;
			} else {
				$('m').value='f039' ;
			}
  		}
  		
  	</script>
  </tiles:put>

</tiles:insert>