<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalCurrentByUserDepartment"/>
  </tiles:put>
  <tiles:put name="body" type="string">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments,/Policy/Mis/MedCase/Stac/Journal/ShowInfoByDate">
		    <msh:form action="/stac_journalCurrentByUserDepartment.do" defaultField="dateStart" disableFormDataConfirm="true" method="POST" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
		    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff"  colsWidth="10%,89%">
		      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9" >
		        <msh:separator label="Параметры поиска" colSpan="6" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
		      </msh:row>
			      	<msh:row>
			      		<msh:textField property="dateStart" label="Дата"/>
				           <td>
				            <input type="submit" value="Найти" />
				          </td>
			      	</msh:row>
		    </msh:panel>
		    </msh:form>
      </msh:ifInRole>
        <%
		    Long department = (Long)request.getAttribute("department") ;
		    if (department!=null && department.intValue()>0 )  {
    	%>
    <msh:section>
    <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="

    select m.id
    ,sc.code as sccode
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy')
    ||case when m.datestart!=sls.dateStart then '(госп. с '||to_char(sls.dateStart,'dd.mm.yyyy')||')' else '' end
    ||case when m.dateFinish is not null then ' выписывается '||to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) else '' end as datestart

    	,wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
    ,	  case
			when (CURRENT_DATE-sls.dateStart)=0 then 1
			when bf.addCaseDuration='1' then ((CURRENT_DATE-sls.dateStart)+1)
			else (CURRENT_DATE-sls.dateStart)
		  end as cnt1
    	,list((current_Date-so.operationDate)||' дн. после операции: '||ms.name)||' '||list((current_Date-so1.operationDate)||' дн. после операции: '||ms1.name) as oper
    ,	  case
			when (CURRENT_DATE-m.dateStart)=0 then 1
			when bf.addCaseDuration='1' then ((CURRENT_DATE-m.dateStart)+1)
			else (CURRENT_DATE-m.dateStart)
		  end as cnt2

    ,list(vdrt.name||' '||vpd.name||' '||mkb.code) as diag
    ,coalesce(vic.name,'')||' сер. '||pat.passportSeries||' №'||pat.passportNumber||' выдан '||to_char(pat.passportDateIssued,'dd.mm.yyyy')||' '||pat.passportWhomIssued as passport
    , case when pat.address_addressId is not null
          then coalesce(adr.fullname,adr.name)
               ||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end
               ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end
	       ||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end
       when pat.territoryRegistrationNonresident_id is not null
	  then okt.name||' '||pat.RegionRegistrationNonresident||' '||oq.name||' '||pat.SettlementNonresident
	       ||' '||ost.name||' '||pat.StreetNonresident
               ||case when pat.HouseNonresident is not null and pat.HouseNonresident!='' then ' д.'||pat.HouseNonresident else '' end
	       ||case when pat.BuildingHousesNonresident is not null and pat.BuildingHousesNonresident!='' then ' корп.'|| pat.BuildingHousesNonresident else '' end
	       ||case when pat.ApartmentNonresident is not null and pat.ApartmentNonresident!='' then ' кв. '|| pat.ApartmentNonresident else '' end
       else  pat.foreignRegistrationAddress end as address
    ,pat.passportSeries||' '||pat.passportNumber as passportshort
    from medCase m
    left join Diagnosis diag on diag.medcase_id=m.id
    left join vocidc10 mkb on mkb.id=diag.idc10_id
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id

    left join MedCase as sls on sls.id = m.parent_id
    left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
    left join bedfund as bf on bf.id=m.bedfund_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id =sloAll.id
    left join SurgicalOperation so1 on so1.medCase_id =sls.id
    left join medservice ms on ms.id=so.medService_id
    left join medservice ms1 on ms1.id=so1.medService_id
    left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join Patient pat on m.patient_id = pat.id
    left join Address2 adr on adr.addressid = pat.address_addressid
    left join Omc_KodTer okt on okt.id=pat.territoryRegistrationNonresident_id
    left join Omc_Qnp oq on oq.id=pat.TypeSettlementNonresident_id
    left join Omc_StreetT ost on ost.id=pat.TypeStreetNonresident_id
    left join VocIdentityCard vic on vic.id=pat.passportType_id
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}'
    and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    group by  m.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart
    ,bf.addCaseDuration,m.dateFinish,m.dischargeTime
    ,vic.name,pat.passportSeries,pat.passportNumber,pat.passportDateIssued,pat.passportWhomIssued
   , pat.address_addressId ,adr.fullname,adr.name
               , pat.houseNumber , pat.houseBuilding ,pat.flatNumber
               , pat.territoryRegistrationNonresident_id , okt.name,pat.RegionRegistrationNonresident,oq.name,pat.SettlementNonresident
	       ,ost.name,pat.StreetNonresident
              , pat.HouseNonresident , pat.BuildingHousesNonresident,pat.ApartmentNonresident

       , pat.foreignRegistrationAddress
    order by pat.lastname,pat.firstname,pat.middlename
    "
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
         <ecom:webQuery name="datelist_r" nameFldSql="datelist_r_sql" nativeSql="
    select m.id,to_char(m.dateStart,'dd.mm.yyyy')||case when m.dateFinish is not null then ' выписывается '||to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) else '' end as datestart,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    	,to_char(pat.birthday,'dd.mm.yyyy') as birthday,sc.code as sccode
    	,list((current_Date-so.operationDate)||' дн. после операции: '||ms.name) as oper
    	,wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
    ,	  case
			when (CURRENT_DATE-sls.dateStart)=0 then 1
			when bf.addCaseDuration='1' then ((CURRENT_DATE-sls.dateStart)+1)
			else (CURRENT_DATE-sls.dateStart)
		  end as cnt1

    from medCase m

    left join MedCase as prev1 on prev1.id=m.prevMedCase_id
    left join MedCase as prev2 on prev2.id=prev1.prevMedCase_id
    left join MedCase as prev3 on prev3.id=prev2.prevMedCase_id
    left join MisLpu lp on lp.id=m.department_id
    left join MisLpu lp1 on lp1.id=prev1.department_id
    left join MisLpu lp2 on lp2.id=prev2.department_id
    left join MedCase as sls on sls.id = m.parent_id
    left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
    left join bedfund as bf on bf.id=m.bedfund_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id =sloAll.id
    left join medservice ms on ms.id=so.medService_id
    left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join Patient pat on m.patient_id = pat.id
    where m.DTYPE='DepartmentMedCase'
    and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    and

    	lp.isNoOmc='1' and (prev1.department_id='${department}'
    	or lp1.isNoOmc='1' and
    	(prev2.department_id='${department}' or lp2.isNoOmc='1' and prev3.department_id='${department}')
    	)
    group by  m.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart
    ,bf.addCaseDuration,m.dateFinish,m.dischargeTime
    order by pat.lastname,pat.firstname,pat.middlename
    "
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
     <msh:sectionTitle>
    <form action="print-stac_current_department.do" method="post" target="_blank">
    Журнал состоящих пациентов в отделении  ${departmentInfo} на текущий момент
    <input type='hidden' name="sqlText" id="sqlText" value="${datelist_sql}">
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Журнал состоящих пациентов в отделении  ${departmentInfo} на текущий момент">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать">
    </form>
    <form action="print-stac_current_department_adr.do" method="post" target="_blank">

    <input type='hidden' name="sqlText" id="sqlText" value="${datelist_sql}">
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Журнал состоящих пациентов в отделении  ${departmentInfo} на текущий момент">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать реестра с адресами">
    </form>
     <a href='stac_print_protocol.do?department=${department}&stNoPrint=selected'>Печать дневников</a>
     <a href='stac_print_surOperation.do?department=${department}&stNoPrint=selected'>Печать хир. операций</a>
     <a href='stac_print_discharge.do?department=${department}&stNoPrint=selected'>Печать выписок</a>

    </msh:sectionTitle>
    <msh:sectionContent>

    <msh:table name="datelist" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="2" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Леч.врач" property="6"/>
      <msh:tableColumn columnName="Кол-во к.дней СЛС" property="7"/>
      <msh:tableColumn columnName="Операции" property="8"/>
      <msh:tableColumn columnName="Кол-во к.дней СЛО" property="9"/>
      <msh:tableColumn columnName="Диагноз" property="10"/>
      <msh:tableColumn columnName="Паспортные данные" property="11"/>
      <msh:tableColumn columnName="Адрес" property="12"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <msh:section>
        <msh:sectionTitle>Список пациентов, находящихся в реанимации
    </msh:sectionTitle>
    <msh:sectionContent>

    <msh:table name="datelist_r" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Леч.врач" property="7"/>
      <msh:tableColumn columnName="Кол-во к.дней СЛС" property="8"/>
      <msh:tableColumn columnName="Операции" property="6"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
    <msh:section>
    <msh:sectionTitle>Свод состоящих пациентов в отделении  ${departmentInfo} на текущий момент
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select m.department_id,ml.name, count(distinct sls.id) as cntSls
    ,count(distinct case when sls.emergency='1' then sls.id else null end) as cntEmergency
    ,count(distinct case when so.id is not null or so1.id is not null then sls.id else null end) as cntOper
    from medCase m
    left join MedCase as sls on sls.id = m.parent_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id=m.id
    left join SurgicalOperation so1 on so1.medCase_id =sls.id
    left join MisLpu ml on ml.id=m.department_id
    where m.DTYPE='DepartmentMedCase'
    and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    group by m.department_id,ml.name
    order by ml.name
    "
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" viewUrl="stac_journalCurrentByUserDepartment.do?s=Short&" action="stac_journalCurrentByUserDepartment.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во состоящих" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="кол-во экстренных" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="кол-во опер. пациентов" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
    </msh:table>
     </msh:sectionContent>
     </msh:section>
     </msh:ifInRole>
    <% } %>
  </tiles:put>
</tiles:insert>

