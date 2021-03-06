<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал выписанных по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalDischargeByUserDepartment"/>
  </tiles:put>
  <tiles:put name="body" type="string">    
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments,/Policy/Mis/MedCase/Stac/Journal/ShowInfoByDate">
		    <msh:form action="/stac_journalDischargeByUserDepartment.do" defaultField="dateStart" disableFormDataConfirm="true" method="POST" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
        	java.util.Date date = new java.util.Date() ;
        	Calendar cal = Calendar.getInstance() ;
        	cal.setTime(date) ;
        	cal.add(Calendar.DAY_OF_MONTH, -7) ;
        	SimpleDateFormat format_1 = new SimpleDateFormat("dd.MM.yyyy") ;
        	request.setAttribute("current_5", format_1.format(cal.getTime())) ;
		    Long department = (Long)request.getAttribute("department") ;
		    if (department!=null && department.intValue()>0 )  {
    	%>
    <msh:section>
    <msh:sectionTitle>Журнал выписанных пациентов из отделения  ${departmentInfo} в течение 7х дней
     <a href='stac_print_discharge.do?department=${department}&stNoPrint=selected'>Печать выписок</a>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select m.id,to_char(sls.dateStart,'dd.mm.yyyy')||' - '||to_char(sls.dateFinish,'dd.mm.yyyy'),pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    	,pat.birthday,sc.code as sccode
    	,list((sls.dateFinish-so.operationDate)||' дн. после операции: '||ms.name) as oper 
    	,wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
    ,	  case 
			when (sls.dateFinish-sls.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((sls.dateFinish-sls.dateStart)+1) 
			else (sls.dateFinish-sls.dateStart)
		  end as cnt1
    ,	  case 
			when (sls.dateFinish-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((sls.dateFinish-m.dateStart)+1) 
			else (sls.dateFinish-m.dateStart)
		  end as cnt2
    	
    from medCase m 
    left join MedCase as sls on sls.id = m.parent_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id in (m.id,sls.id)
    left join medservice ms on ms.id=so.medService_id
    left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left outer join Patient pat on m.patient_id = pat.id 
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}' 
    and m.dateFinish between to_date('${current_5}','dd.mm.yyyy') and current_date
    group by  m.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart,sls.dateFinish
    ,bf.addCaseDuration
    order by sls.dateFinish,pat.lastname,pat.firstname,pat.middlename
    "
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Леч.врач" property="7"/>
      <msh:tableColumn columnName="Период лечения" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Кол-во к.дней" property="8"/>
      <msh:tableColumn columnName="Операции" property="6"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
    <msh:section>
    <msh:sectionTitle>Свод выписанных пациентов в отделение  ${departmentInfo} в течение 7 дней
     <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
    <a href='stac_journalDischargeByUserDepartment.do'>Выбрать другое отделение</a>
    </msh:ifInRole>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select ml.id,ml.name, count(distinct sls.id) as cntSls
    ,count(distinct case when sls.emergency='1' then sls.id else null end) as cntEmergency
    ,count(distinct case when so.id is not null then sls.id else null end) as cntOper
    ,count(distinct case when sls.dateFinish=current_date then sls.id else null end) as cntCurrent
    from medCase m 
    left join MedCase as sls on sls.id = m.parent_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id in (m.id,sls.id)
    left join medservice ms on ms.id=so.medService_id
    left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left outer join Patient pat on m.patient_id = pat.id
    left join MisLpu ml on ml.id=m.department_id
    where m.DTYPE='DepartmentMedCase'
    and m.dateFinish between to_date('${current_5}','dd.mm.yyyy') and current_date
    group by ml.id,ml.name
    order by ml.name
    "
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />    
    <msh:table name="datelist" viewUrl="stac_journalDischargeByUserDepartment.do?s=Short&" action="stac_journalDischargeByUserDepartment.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во выписанных" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Кол-во выписанных сегодня" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="кол-во экстренных" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="кол-во опер. пациентов" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
    </msh:table>     
     </msh:sectionContent>
     </msh:section>
     </msh:ifInRole>
    <% } %>
  </tiles:put>
</tiles:insert>