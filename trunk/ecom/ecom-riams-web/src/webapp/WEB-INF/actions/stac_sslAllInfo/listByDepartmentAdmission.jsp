<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал направленных пациентов в отделение ${departmentInfo} (неоформленных) </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_journalByDepartmentAdmission"/>
  </tiles:put>
  <tiles:put name="body" type="string">
		    <msh:form action="/stac_journalByDepartmentAdmission.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
		    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff"  colsWidth="10%,90%">
		      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9" >
		        <msh:separator label="Параметры поиска" colSpan="4" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
		      </msh:row>
    <msh:row>
    <td colspan="1" class='label'>
    <input type='checkbox' name='onlyMonth' id='onlyMonth' onClick='javascript:document.location.href="stac_journalByDepartmentAdmission.do?department=${param.department}&onlyMonth="+this.checked'>
    </td>
    <td colspan=3 class='onlyMonth'>
	    <label id='onlyMonthLabel' for="onlyMonth"> Отображать направленных за последний месяц</label>
    
    </td>
    </msh:row>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
			      <msh:row>
			      	<msh:autoComplete size="100" property="department" vocName="lpu" label="Отделение" fieldColSpan="2" horizontalFill="true"/>
		           <td>
		           
		           
		            <input type="submit" value="Найти" />
		          </td>
		      </msh:row>
      </msh:ifInRole>
		    </msh:panel>
		    </msh:form>
        <%
		    Long department = (Long)request.getAttribute("department") ;
		    if (department!=null && department.intValue()>0 )  {
    	%>
    <msh:section>
    <msh:sectionTitle>Журнал направленных пациентов в отделение (из приемного отделения)
    </msh:sectionTitle>
    <msh:sectionContent>
    <%-- 
    <ecom:webQuery name="journal_admission1" nativeSql="
    select m.id,m.dateStart,m.username,stat.code,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday  
    from MedCase  m 
    left outer join Patient pat on m.patient_id = pat.id  
    left outer join StatisticStub stat on m.statisticstub_id=stat.id 
    where m.DTYPE='HospitalMedCase' 
    and (select count(*) from MedCase as mcH where mcH.DTYPE='DepartmentMedCase' and mcH.parent_id=m.id )=0 and department_id='${department}' and (m.noActuality is null or cast(m.noActuality as integer)=0)" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <%-- 
    <%-- --%>
    <ecom:webQuery name="journal_admission1" nativeSql="
select m.id,m.dateStart,m.username,stat.code,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,vas.name as vasname  ,list(diag.name) as diagname
    from MedCase  m 
    left outer join Patient pat on m.patient_id = pat.id  
    left outer join StatisticStub stat on m.statisticstub_id=stat.id 
    left join VocAdditionStatus vas on vas.id=pat.additionStatus_id
    left join MedCase d on d.parent_id=m.id and d.dtype='DepartmentMedCase'
    left join Diagnosis diag on diag.medCase_id=m.id
	where m.DTYPE='HospitalMedCase' and m.department_id='${department}'
	and d.id is null and (m.noActuality is null or cast(m.noActuality as integer)=0) 
	and (cast(m.ambulanceTreatment as integer)=0 or m.ambulanceTreatment is null) and m.dateFinish is null
	${onlyMonthH}
	group by m.id,m.dateStart,m.username,stat.code,pat.lastname,pat.firstname,pat.middlename,pat.birthday
,vas.name 
	 
	order by pat.lastname,pat.firstname,pat.middlename
     "/>
     <%-- --%>
    <msh:table name="journal_admission1" viewUrl="entityShortView-stac_ssl.do" action="entityParentPrepareCreate-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
    	<msh:tableColumn columnName="#" property="sn"/>
      <msh:tableColumn columnName="Стат.карта" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Статус пациента" property="7" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Фамилия имя отчетсво пациента" property="5" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Год рождения" property="6" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Диагноз" property="8" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Кем открыт" property="3" guid="37-b552-4154-a82a-ee484a1714ad" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <msh:section>
    <msh:sectionTitle>Журнал переведенных пациентов из других отделений в отделение </msh:sectionTitle>
    <msh:sectionContent>
<%--     <ecom:webQuery name="journal_admission" nativeSql="
    select m.id,m.dateStart,m.username,stat.code,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday  
    from MedCase  m 
    left outer join Patient pat on m.patient_id = pat.id  
    left outer join StatisticStub stat on m.statisticstub_id=stat.id  
    where m.DTYPE='HospitalMedCase'  
    and ( select count(*) from MedCase  t where t.parent_id=m.id and t.DTYPE='DepartmentMedCase' and t.transferDate is null)=0  
    and (select count(*) from MedCase as t1 where t1.parent_id=m.id and t1.DTYPE='DepartmentMedCase' and t1.transferDepartment_id='${department}' and (select count(*) from MedCase as mm where mm.parent_id=m.id and mm.DTYPE='DepartmentMedCase' and mm.prevMedCase_id=t1.id)=0 )=1 
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    --%>
    <ecom:webQuery name="journal_admission" nativeSql="
select sls.id,sls.dateStart,sls.username,stat.code,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,lput.name as lputname   
,d.transferDate as dtransferDate,vas.name  
from MedCase d
left join MedCase n on n.prevMedCase_id=d.id
left join MedCase sls on sls.id=d.parent_id
left join Patient pat on pat.id=sls.patient_id
left join StatisticStub stat on stat.id=sls.statisticStub_id
left join mislpu lput on lput.id=d.department_id
    left join VocAdditionStatus vas on vas.id=pat.additionStatus_id
where d.DTYPE='DepartmentMedCase' 
 and d.transferDepartment_id='${department}' and n.dateStart is null  ${onlyMonthD} and sls.dateFinish is null
    "/>
    <msh:table viewUrl="entityShortView-stac_ssl.do" name="journal_admission" action="entityParentPrepareCreate-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
    	<msh:tableColumn columnName="#" property="sn"/>
      <msh:tableColumn columnName="Перевод из отделения" property="7" guid="fc26523a-eb9c-4ca9ac5b" />
      <msh:tableColumn columnName="Дата перевода" property="8" guid="fc26523a-eb9c-44bc-b12e-42cb7" />
      <msh:tableColumn columnName="Стат.карта" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Статус пациента" property="9" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Фамилия имя отчетсво пациента" property="5" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Год рождения" property="6" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
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

