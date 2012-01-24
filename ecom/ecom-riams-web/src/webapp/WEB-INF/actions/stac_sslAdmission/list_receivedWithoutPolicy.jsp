<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по пациентам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_receivedWithoutPolicy"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_ticketsByNonredidentPatientList.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  все
        </td>
        </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
<%--         <td class="label" title="Длительность (period)" colspan="1"><label for="periodName" id="peroidLabel">Длительность:</label></td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="1"> Неделя
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="2"> Месяц
        </td> --%>
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_hosp" nativeSql="
    
    select 
    hosp.id as hospid
    , dep.name as depname
    , vss.name as vssname 
    , hosp.dateStart as hospdateStart
    , ss.code as statcard
    , vas.name as vasname
    , p.id as pid
    , p.lastname||' '||p.firstname||' '||p.middlename as fio
    , p.birthday as pbirthday
from Medcase hosp 
left join StatisticStub ss on ss.id=hosp.statisticStub_id 
left join MisLpu dep on dep.id=hosp.department_id 
left join vocservicestream vss on vss.id=hosp.servicestream_id 
left join patient p on p.id=hosp.patient_id 
left join vocAdditionStatus vas on vas.id=p.additionStatus_id 
 where hosp.DTYPE='HospitalMedCase' and hosp.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')
      and to_date('${param.dateEnd}','dd.mm.yyyy')
 and (vss.code = 'OBLIGATORYINSURANCE' or vss.code='PRIVATEINSURANCE') 
and (select count(*) from medcase_medpolicy pol where pol.medCase_id=hosp.id)=0
and hosp.deniedHospitalizating_id is null
order by dep.name,vss.name,p.lastname,p.firstname,p.middlename


        "  />
        <msh:table name="journal_hosp" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Отделение" property="2"/>
            <msh:tableColumn columnName="Вид оплаты" property="3"/>
            <msh:tableColumn columnName="Дата поступления" property="4"/>
            <msh:tableColumn columnName="№стат.карты" property="5"/>
            <msh:tableColumn columnName="Доп.статус" property="6"/>
            <msh:tableColumn columnName="ФИО пациента" property="8"/>
            <msh:tableColumn columnName="Год рождения" property="9"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>

    <script type='text/javascript'>
    var typePatient = document.forms[0].typePatient ;
    
    if ((+'${typePatient}')==1) {
    	typePatient[0].checked='checked' ;
    } else if ((+'${typePatient}')==2) {
    	typePatient[1].checked='checked' ;
    } else {
    	typePatient[2].checked='checked' ;
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_receivedWithoutPolicy_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_receivedWithoutPolicy_print.do' ;
    }
    
    </script>
  </tiles:put>
</tiles:insert>

