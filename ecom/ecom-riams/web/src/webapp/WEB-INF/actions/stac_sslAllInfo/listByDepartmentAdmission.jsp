<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Журнал направленных пациентов в отделение ${departmentInfo} (неоформленных) </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_journalByDepartmentAdmission"/>
  </tiles:put>
  <tiles:put name="body" type="string">
		    <msh:form action="/stac_journalByDepartmentAdmission.do" defaultField="department" disableFormDataConfirm="true" method="GET">
		    <msh:panel  colsWidth="10%,90%">
		      <msh:row >
		        <msh:separator label="Параметры поиска" colSpan="4" />
		      </msh:row>
    <msh:row>
    <td colspan="1" class='label'>
	<input type="hidden" name="onlyMonthH" id="onlyMonthH">
    <input type='checkbox' name='onlyMonth' id='onlyMonth' onClick='javascript:document.location.href="stac_journalByDepartmentAdmission.do?department=${param.department}&onlyMonth="+this.checked+"&onlyMonthH="+(this.checked?"1":"0")'>
    </td>
    <td colspan=3 class='onlyMonth'>
	    <label id='onlyMonthLabel' for="onlyMonth"> Отображать направленных за последний месяц</label>
    
    </td>
    </msh:row>
		    </msh:panel>
		    </msh:form>
        <%
		    Long department = (Long)request.getAttribute("department") ;
		    if (department!=null && department.intValue()>0 )  {
    	%>
    <msh:section>
    <msh:sectionTitle>Журнал направленных пациентов в отделение (из приемного отделения)
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
    <a href='stac_journalByDepartmentAdmission.do'>Выбрать другое отделение</a>
    </msh:ifInRole>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_admission1" nativeSql="
select m.id,to_char(m.dateStart,'dd.mm.yyyy')||' '||cast(m.entranceTime as varchar(5)) as datestart,m.username,stat.code,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,vas.name as vasname  ,list(diag.name) as diagname
,vss.name as vssname
    from MedCase  m 
    left outer join Patient pat on m.patient_id = pat.id  
    left outer join StatisticStub stat on m.statisticstub_id=stat.id 
    left join VocAdditionStatus vas on vas.id=pat.additionStatus_id
    left join MedCase d on d.parent_id=m.id and d.dtype='DepartmentMedCase'
    left join Diagnosis diag on diag.medCase_id=m.id
    left join VocServiceStream vss on vss.id=m.serviceStream_id
	where m.DTYPE='HospitalMedCase' and m.department_id='${department}'
	and d.id is null and (m.noActuality is null or m.noActuality='0') 
	and (m.ambulanceTreatment is null or m.ambulanceTreatment='0')
	 and m.dateFinish is null
	${onlyMonthH}
	 and m.deniedHospitalizating_id is null
	group by m.id,m.dateStart,m.entranceTime,m.username,stat.code,pat.lastname,pat.firstname,pat.middlename,pat.birthday
,vas.name ,vss.name
	 
	order by pat.lastname,pat.firstname,pat.middlename
     "/>
    <msh:table name="journal_admission1" viewUrl="entityShortView-stac_ssl.do" action="entityParentPrepareCreate-stac_slo.do" idField="1">
    	<msh:tableColumn columnName="#" property="sn"/>
      <msh:tableColumn columnName="Стат.карта" property="4" />
      <msh:tableColumn columnName="Статус пациента" property="7" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" />
      <msh:tableColumn columnName="Год рождения" property="6" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Диагноз" property="8" />
      <msh:tableColumn columnName="Кем открыт" property="3" />
      <msh:tableColumn property="9" columnName="Поток обслуживания"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <msh:section>
    <msh:sectionTitle>Журнал переведенных пациентов из других отделений в отделение 
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
    <a href='stac_journalByDepartmentAdmission.do'>Выбрать другое отделение</a>
    </msh:ifInRole>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_admission" nativeSql="
select sls.id,sls.dateStart,sls.username,stat.code,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,lput.name as lputname   
,to_char(d.transferDate,'dd.mm.yyyy') || ' ' || cast(d.transferTime as varchar(5)) as dtransferDate,vas.name as vasname
  ,vss.name as vssname
from MedCase d
left join MedCase n on n.prevMedCase_id=d.id
left join MedCase sls on sls.id=d.parent_id
left join Patient pat on pat.id=sls.patient_id
left join StatisticStub stat on stat.id=sls.statisticStub_id
left join mislpu lput on lput.id=d.department_id
left join VocServiceStream vss on vss.id=d.serviceStream_id
    left join VocAdditionStatus vas on vas.id=pat.additionStatus_id
where d.DTYPE='DepartmentMedCase' 
 and d.transferDepartment_id='${department}' 
 and n.dateStart is null  ${onlyMonthD} and sls.dateFinish is null
 and sls.deniedHospitalizating_id is null
    "/>
    <msh:table viewUrl="entityShortView-stac_ssl.do" name="journal_admission" action="entityParentPrepareCreate-stac_slo.do" idField="1">
    	<msh:tableColumn columnName="#" property="sn"/>
      <msh:tableColumn columnName="Перевод из отделения" property="7" />
      <msh:tableColumn property="10" columnName="Поток обслуживания"/>
      <msh:tableColumn columnName="Дата перевода" property="8" />
      <msh:tableColumn columnName="Стат.карта" property="4" />
      <msh:tableColumn columnName="Статус пациента" property="9" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" />
      <msh:tableColumn columnName="Год рождения" property="6" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} else {%>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
        <msh:section>
	    <msh:sectionTitle>Свод направленных пациентов в отделение (из приемного отделения) ${onlyMonthInfo}</msh:sectionTitle>
	    <msh:sectionContent>
	    <ecom:webQuery name="journal_admission1" nativeSql="
select ml.id,ml.name,count(distinct m.id) as cntAll
,count(distinct case when m.dateFinish is null and d.id is not null then m.id else null end) as cntCurrent 
,count(distinct case when d.id is null then m.id else null end) as cntNoOf 
 from MedCase  m 
 left join MisLpu ml on ml.id=m.department_id
    left join MedCase d on d.parent_id=m.id and d.dtype='DepartmentMedCase'
	where m.DTYPE='HospitalMedCase'
	and (m.noActuality is null or m.noActuality='0') 
	and (m.ambulanceTreatment is null or m.ambulanceTreatment='0') and m.dateFinish is null
	${onlyMonthH}
	 and m.deniedHospitalizating_id is null
	group by ml.id,ml.name
	 
	order by ml.name
	     "/>
	     <%-- --%>
	    <msh:table name="journal_admission1" viewUrl="stac_journalByDepartmentAdmission.do?s=Short&" 
	     action="stac_journalByDepartmentAdmission.do" idField="1">
	    	<msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Отделение" property="2"/>
	      <msh:tableColumn columnName="Кол-во поступивших" property="3"/>
	      <msh:tableColumn columnName="Кол-во офорленных и состоящих " property="4"/>
	      <msh:tableColumn columnName="Кол-во неофорленных" property="5"/>
	    </msh:table>
	    </msh:sectionContent>
	    </msh:section>
	<msh:section>
    <msh:sectionTitle>Свод переведенных пациентов из других отделений в отделение </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_admission" nativeSql="
select ml.id,ml.name,count(distinct sls.id) as cntAll
from MedCase d
left join MedCase n on n.prevMedCase_id=d.id
left join MedCase sls on sls.id=d.parent_id
left join Mislpu ml on ml.id=d.transferDepartment_id
where d.DTYPE='DepartmentMedCase' 
 and n.dateStart is null  ${onlyMonthD} and sls.dateFinish is null
 and sls.deniedHospitalizating_id is null
 and d.transferDepartment_id is not null
 group by ml.id,ml.name
    "/>
    <msh:table 
    viewUrl="stac_journalByDepartmentAdmission.do?s=Short&"
    name="journal_admission" action="entityParentPrepareCreate-stac_slo.do" idField="1">
    	  <msh:tableColumn columnName="#" property="sn"/>
	      <msh:tableColumn columnName="Отделение" property="2"/>
	      <msh:tableColumn columnName="Кол-во переведенных" property="3"/> 
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    </msh:ifInRole>
    <%} %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" >
  		if ((+'${onlyMonth}')==1) {
    		$('onlyMonth').checked='checked' ;
    	} else {
    		$('onlyMonth').checked='' ;
    	}
  	</script>
  </tiles:put>
</tiles:insert>

