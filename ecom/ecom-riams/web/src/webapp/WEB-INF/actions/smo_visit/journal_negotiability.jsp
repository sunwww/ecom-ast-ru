<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по обращаемости пациентов в поликлинику</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="reportDirect"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/visit_journal_direction.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="m" id="m" value="f039"/>
    <input type="hidden" name="s" id="s" value="TicketService"/>
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
    <msh:panel colsWidth="1%,1%,1%">
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" />
        	<msh:textField property="finishDate" fieldColSpan="3" label="по" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" vocName="vocWorkFunction" 
        		horizontalFill="true" fieldColSpan="10" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunctionByDirect" 
        		horizontalFill="true" fieldColSpan="10" size="70"/>
        </msh:row>
    
        <msh:row>
        	<msh:autoComplete property="serviceStream" vocName="vocServiceStream"
        		horizontalFill="true" fieldColSpan="10" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workPlaceType" vocName="vocWorkPlaceType"
        		horizontalFill="true" fieldColSpan="10" size="70"/>
        </msh:row>    
        <msh:row>
        	<msh:autoComplete property="lpu" vocName="lpu"
        		horizontalFill="true" fieldColSpan="10" size="70"/>
        </msh:row>    
        <msh:row>
        	<msh:autoComplete property="orderLpu" vocName="mainLpu"
        		horizontalFill="true" fieldColSpan="10" size="70"/>
        </msh:row>
        <tr><td colspan="12"><table>    
        <msh:row>
	        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1">  создания
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2"> направления
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeGroup" value="1">  датам
	        </td>
	        
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2">ЛПУ
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3">  врачам
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4">  специальностям
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="5">Поток обслуж.  
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';" style="text-align: left">
	        	<input type="radio" name="typeGroup" value="6">Место обслуж.  
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="7"> ЛПУ направления  
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="8"> типу ЛПУ направления
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="1"> обращаемость
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2">  дефекты направления
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
	        	<input type="radio" name="typeView" value="3"  > сроки обследования 
	        </td>
        </msh:row>
        </table></td></tr>
        <msh:row>
        <td colspan="3" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;visit_journal_direction.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
<%--			<input type="button" title="Печать" onclick="this.value=&quot;Печать&quot;; getId(0);this.form.action=&quot;print-f039.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать bis" onclick="this.value=&quot;Печать bis&quot;; getId(1);this.form.action=&quot;print-f039add.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать bis" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать cons" onclick="this.value=&quot;Печать cons&quot;; getId(0);this.form.action=&quot;print-f039cons.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать cons" class="default" id="submitButton" autocomplete="off">
 --%>		
 		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null
    	 || request.getParameter("id")!=null && !request.getParameter("id").equals("")
    	) {
    	    String view = (String)request.getAttribute("typeView") ;
        	if (view!=null && (view.equals("1"))) {

    		%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}</msh:sectionTitle>
    <msh:sectionContent>
<ecom:webQuery name="journal_reestr" nativeSql="
select t.id as tid
, lpu.name as lpuname
, vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as wfinfo
,vss.name as vssname,vwpt.name as vwptname,olpu.name as olpuname
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename as owfinfo
, p.lastname||' '||p.firstname||' '||coalesce(p.middlename) as fiopat
,p.birthday as pbirthday,pvr.name as vrname
,to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate
,to_char(t.dateStart,'dd.mm.yyyy') as datepriem
,to_char(t.createDate,'dd.mm.yyyy') as createDate
 ,case when vss.code = 'OBLIGATORYINSURANCE' and (select count(*) from medpolicy mp where
     mp.patient_id=p.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=wcd.calendarDate and
    (mp.actualDateTo is null or mp.actualDateTo>=wcd.calendarDate)
    )=0 then 'Да' else '' end as withoutPolicy
,case when vss.code = 'OBLIGATORYINSURANCE' and (select count(*) from medpolicy mp where
     mp.patient_id=p.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=wcd.calendarDate and
    (mp.actualDateTo is null or mp.actualDateTo>=wcd.calendarDate)
    )>1 then 'Да' else '' end as more1Policy
 
 
${queryTextEnd}


" />
        <msh:table
         name="journal_reestr" viewUrl="entityShortView-smo_visit.do" action="entityView-smo_visit.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Направлен ЛПУ" property="6"/>
            <msh:tableColumn columnName="ФИО пациента" property="8"/>
            <msh:tableColumn columnName="Дата рождения" property="9"/>
            <msh:tableColumn columnName="Район" property="10"/>
            <msh:tableColumn columnName="Дата создания напр." property="13"/>
            <msh:tableColumn columnName="Внут. направитель" property="7"/>
            <msh:tableColumn columnName="Дата направления" property="11"/>
            <msh:tableColumn columnName="Дата приема" property="12"/>
            <msh:tableColumn columnName="ЛПУ" property="2"/>
            <msh:tableColumn columnName="Направлен к врачу" property="3"/>
            <msh:tableColumn columnName="Поток обслуживания" property="4"/>
            <msh:tableColumn columnName="Место обслуживания" property="5"/>
            <msh:tableColumn columnName="Без полиса" property="14"/>
            <msh:tableColumn columnName="Более 1 полиса " property="15"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <%
    } 
    	if (view==null || view.equals("2")) { 
    %>
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}. ${groupByTitle} ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}</msh:sectionTitle>
    <msh:sectionContent>
<ecom:webQuery name="journal_swod" nativeSql="
${queryTextBegin}
, count(distinct wct.medcase_id)+count(distinct case when t.noActuality ='1' then t.id else null end) as cntNapr
, count(distinct case when t.visitResult_id is not null and (t.noActuality='0' or t.noActuality is null) then t.id else null end)  as cntPriem
, count(distinct case when t.noActuality ='1' then t.id else null end)  as cntNeiavka
, count(distinct wct.medcase_id)+count(distinct case when t.noActuality ='1' then t.id else null end)-count(distinct case when t.visitResult_id is not null then t.id else null end)  as cntNopriem
, round((count(distinct case when t.visitResult_id is not null then t.id else null end)-count(distinct case when t.noActuality ='1' then t.id else null end)) *100/cast(count(distinct wct.medcase_id) as numeric),0) as procPriem
, round((count(distinct wct.medcase_id)+count(distinct case when t.noActuality ='1' then t.id else null end)-count(distinct case when t.visitResult_id is not null then t.id else null end)) *100/cast(count(distinct wct.medcase_id) as numeric),0) as procNoPriem
,count(distinct case when vss.code = 'OBLIGATORYINSURANCE' and (select count(*) from medpolicy mp where
     mp.patient_id=p.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=wcd.calendarDate and
    (mp.actualDateTo is null or mp.actualDateTo>=wcd.calendarDate)
    )=0 then wct.medCase_id else null end) as withoutPolicy
,count(distinct case when vss.code = 'OBLIGATORYINSURANCE' and (select count(*) from medpolicy mp where
     mp.patient_id=p.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=wcd.calendarDate and
    (mp.actualDateTo is null or mp.actualDateTo>=wcd.calendarDate)
    )>1 then wct.medCase_id else null end) as more1Policy

,count(distinct case when adr.kladr like '30000001%' then wct.medcase_id else null end) as cntAstrakhan
,count(distinct case when adr.kladr like '30000002%' then wct.medcase_id else null end) as cntZnamenkc
,count(distinct case when adr.kladr like '30%' and adr.kladr not like '30000%' then wct.medcase_id else null end) as cntRayon
,count(distinct case when adr.kladr not like '30%' and ok.voc_code='643' then wct.medcase_id else null end) as cntInog
,count(distinct case when ok.voc_code!='643' then wct.medcase_id else null end) as cntInost

${queryTextEnd}
${groupBy}

" />
        <msh:table
         name="journal_swod" action="visit_journal_direction.do" idField="1" noDataMessage="Не найдено">
             <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${groupByTitle}" property="2"/>            
            <msh:tableColumn columnName="Кол-во напр. пац." property="3"/>
            <msh:tableColumn columnName="Кол-во прин. пац." property="4"/>
            <msh:tableColumn columnName="Кол-во, не явившихся на прием" property="5"/>
            <msh:tableColumn columnName="Кол-во неприн. пац." property="6"/>
            <msh:tableColumn columnName="% принятых пациентов" property="7"/>
            <msh:tableColumn columnName="Напр. без полиса" property="9"/>
            <msh:tableColumn columnName="Напр. с 2 и более полисов" property="10"/>
            <msh:tableColumn columnName="г. Астрахань" property="11"/>
            <msh:tableColumn columnName="г. Знаменск" property="12"/>
            <msh:tableColumn columnName="Область" property="13"/>
            <msh:tableColumn columnName="Иного- родние" property="14"/>
            <msh:tableColumn columnName="Иност- ранцы" property="15"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <%
    } 
    	if (view==null || view.equals("3")) { 
    %>
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}</msh:sectionTitle>
    <msh:sectionContent>
<ecom:webQuery name="journal_swod_rayon" nativeSql="
${queryTextBegin}
, count(distinct wct.medcase_id) as cntNapr
,count(distinct case when vss.code = 'OBLIGATORYINSURANCE' and (select count(*) from medpolicy mp where
     mp.patient_id=p.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=wcd.calendarDate and
    (mp.actualDateTo is null or mp.actualDateTo>=wcd.calendarDate)
    )=0 then wct.medCase_id else null end) as withoutPolicy
,count(distinct case when vss.code = 'OBLIGATORYINSURANCE' and (select count(*) from medpolicy mp where
     mp.patient_id=p.id and
    mp.dtype like 'MedPolicyOmc%' and mp.actualDateFrom<=wcd.calendarDate and
    (mp.actualDateTo is null or mp.actualDateTo>=wcd.calendarDate)
    )>1 then wct.medCase_id else null end) as more1Policy

,count(distinct case when adr.kladr like '30000001%' then wct.medcase_id else null end) as cntAstrakhan
,count(distinct case when adr.kladr like '30000002%' then wct.medcase_id else null end) as cntZnamenkc
,count(distinct case when adr.kladr like '30%' and adr.kladr not like '30000%' then wct.medcase_id else null end) as cntRayon
,count(distinct case when pvr.code='А' then wct.medcase_id else null end) as cntAhtub
,count(distinct case when pvr.code='В' then wct.medcase_id else null end) as cntVolodar
,count(distinct case when pvr.code='Е' then wct.medcase_id else null end) as cntEnotaev
,count(distinct case when pvr.code='ИК' then wct.medcase_id else null end) as cntIkrian
,count(distinct case when pvr.code='КА' then wct.medcase_id else null end) as cntKamiz
,count(distinct case when pvr.code='КР' then wct.medcase_id else null end) as cntKrasnoiar
,count(distinct case when pvr.code='ЛМ' then wct.medcase_id else null end) as cntLiman
,count(distinct case when pvr.code='НР' then wct.medcase_id else null end) as cntNarim
,count(distinct case when pvr.code='ПР' then wct.medcase_id else null end) as cntPrivol
,count(distinct case when pvr.code='ХР' then wct.medcase_id else null end) as cntHarab
,count(distinct case when pvr.code='Ч' then wct.medcase_id else null end) as cntChernoiar
,count(distinct case when adr.kladr not like '30%' and ok.voc_code='643' then wct.medcase_id else null end) as cntInog
,count(distinct case when ok.voc_code!='643' then wct.medcase_id else null end) as cntInost

${queryTextEnd}
${groupBy}

" />
        <msh:table
         name="journal_swod_rayon" action="visit_journal_direction.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${groupByTitle}" property="2"/>            
            <msh:tableColumn columnName="Кол-во напр. пац." property="3"/>
            <msh:tableColumn columnName="Напр. без полиса" property="4"/>
            <msh:tableColumn columnName="Напр. с 2 и более полисов" property="5"/>
            <msh:tableColumn columnName="г. Астра- хань" property="6"/>
            <msh:tableColumn columnName="г. Зна- менск" property="7"/>
            <msh:tableColumn columnName="Всего" property="8"/>
            <msh:tableColumn columnName="Ахту- бин- ский" property="9"/>
            <msh:tableColumn columnName="Воло- дар- ский " property="10"/>
            <msh:tableColumn columnName="Ено- таевский" property="11"/>
            <msh:tableColumn columnName="Икря- нинский" property="12"/>
            <msh:tableColumn columnName="Камы- зякский" property="13"/>
            <msh:tableColumn columnName="Красно- ярский" property="14"/>
            <msh:tableColumn columnName="Лиман- ский" property="15"/>
            <msh:tableColumn columnName="Нари- манов- ский" property="16"/>
            <msh:tableColumn columnName="Привол- жский" property="17"/>
            <msh:tableColumn columnName="Хара- балин- ский" property="18"/>
            <msh:tableColumn columnName="Черно- ярский" property="19"/>
            <msh:tableColumn columnName="Ино- город- ние" property="20"/>
            <msh:tableColumn columnName="Иност- ранцы" property="21"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% }} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	var typeGroup = document.forms[0].typeGroup ;
  	var typeGroupId = +'${typeGroup}' ; 
  	if(typeGroupId==0) typeGroupId=1 ; 
  	typeGroup[typeGroupId-1].checked='checked' ;
  	checkFieldUpdate('typeView','${typeView}',3) ;
  	checkFieldUpdate('typeGroup','${typeGroup}',9) ;
  	checkFieldUpdate('typeDate','${typeDate}',2) ;
  	checkFieldUpdate('typeUser','${typeUser}',3) ;

  	function checkFieldUpdate(aField,aValue,aDefault) {
  		aValue=+aValue ;
    	eval('var chk =  document.forms[0].'+aField) ;
    	max = chk.length ;
    	if (aValue<1) aValue=+aDefault ;
    	if (aValue>max) {
    		if (aDefault>max) {
    			chk[max-1].checked='checked' ;
    		} else {
    			chk[aDefault-1].checked='checked' ;
    		}
    	} else {
    		chk[aValue-1].checked='checked' ;
    	}
    }
    
  	function getId(aBis) {
		var args=$('beginDate').value+":"+$('finishDate').value
 			+":"+getCheckedValue(typeGroup)
 			+":"+$('specialist').value
 			+":"+$('workFunction').value
 			+":"+$('lpu').value
 			+":"+$('orderFunction').value
 			+":0";
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