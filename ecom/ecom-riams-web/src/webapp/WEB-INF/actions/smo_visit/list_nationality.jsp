<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="reportNationality"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/journal_nationality_smo_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="categoryForeignNationals"/>
    <input type="hidden" name="s" id="s" value="VisitPrintService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel colsWidth="1%,1%,1%">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" fieldColSpan="3" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        <td colspan="3" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;journal_nationality_smo_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
			<input type="button" title="Печать" onclick="this.value=&quot;Печать&quot;; getId(0);this.form.action=&quot;print-report_categoryForeignNationals.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать" class="default" id="submitButton" autocomplete="off">
<%--			<input type="button" title="Печать bis" onclick="this.value=&quot;Печать bis&quot;; getId(1);this.form.action=&quot;print-f039add.do&quot;;this.form.target=&quot;_blank&quot; ; this.form.submit(); return true ;" value="Печать bis" class="default" id="submitButton" autocomplete="off">
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

    		%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${param.beginDate} по ${param.finishDate}.</msh:sectionTitle>
    <msh:sectionContent>
<ecom:webQuery name="journal_swod" nativeSql="
select coalesce(p.nationality_id,0)||':${param.beginDate}:${param.finishDate}',vn.name as vnname,count(*) as cntAll
,count(distinct case when m.dtype='Visit' then m.id else null end) as polic
,count(distinct case when m.dtype='Visit' and vss.code='CHARGED' then m.id else null end) as policCh
,count(distinct case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is null and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then m.id else null end) as hospitAll
,count(distinct case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is null and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then m.id else null end) as hospitAllCh
,count(distinct case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is null and vht.code='DAYTIMEHOSP' then m.id else null end) as hospitDn
,count(distinct case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is null and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then m.id else null end) as hospitDnCh
from medcase m
left join patient p on p.id=m.patient_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join VocHospType vht on vht.id=m.hospType_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
where (m.dtype='Visit' or m.dtype='HospitalMedCase') 
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.noActuality is null or m.noActuality='0')
group by p.nationality_id,vn.name

" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
        <msh:table
         name="journal_swod" action="journal_nationality_smo_data.do" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="2" class="rightBold">Амбулаторно-поликлиническая помощь</th>
                <th colspan="2" class="rightBold">Стационарная медицинская помощь</th>
                <th colspan="2" class="rightBold">Стационарно-замещающая медицинская помощь</th>
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Гражданство" property="2"/>            
            <msh:tableColumn columnName="Общее кол-во" property="3"/>
            <msh:tableColumn columnName="всего" property="4"/>
            <msh:tableColumn columnName="в т.ч. платно" property="5"/>
            <msh:tableColumn columnName="всего" property="6"/>
            <msh:tableColumn columnName="в т.ч. платно" property="7"/>
            <msh:tableColumn columnName="всего" property="8"/>
            <msh:tableColumn columnName="в т.ч. платно" property="9"/>
        </msh:table>
    </msh:sectionContent>
    <msh:sectionTitle>Данные по талонной версии (посещения)</msh:sectionTitle>
<msh:sectionContent>
<ecom:webQuery name="journal_swod1" nativeSql="
select coalesce(p.nationality_id,0)||':${param.beginDate}:${param.finishDate}',vn.name as vnname
,count(distinct t.id) as polic
,count(distinct case when vss.code='CHARGED' then t.id else null end) as policCh
from Ticket t
left join MedCard m on m.id=t.medCard_id
left join patient p on p.id=m.person_id
left join Omc_Oksm vn on vn.id=p.nationality_id
left join VocServiceStream vss on vss.id=t.vocPaymentType_id
where t.date between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
group by p.nationality_id,vn.name

" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
        <msh:table
         name="journal_swod1" action="journal_nationality_smo_data.do" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="2" class="rightBold">Амбулаторно-поликлиническая помощь</th>
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Гражданство" property="2"/>            
            <msh:tableColumn columnName="всего" property="3"/>
            <msh:tableColumn columnName="в т.ч. платно" property="4"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    	} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	//checkFieldUpdate('typeView','${typeView}',3) ;
  	//checkFieldUpdate('typeGroup','${typeGroup}',9) ;
  	//checkFieldUpdate('typeDate','${typeDate}',2) ;
  	//checkFieldUpdate('typeUser','${typeUser}',3) ;

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