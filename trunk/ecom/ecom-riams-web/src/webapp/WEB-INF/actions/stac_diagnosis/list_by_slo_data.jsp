<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Список CЛО по диагнозам за период. <a href='stac_diagnosis_by_slo_list.do'>Выбрать другие параметры</a>" guid="81085da2-7de9-45ce-9f89-b40c462727b6" />
    
  </tiles:put>
  <tiles:put name="side" type="string" >

  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:sectionTitle guid="88380872-1a21-47b8-8287-dd2e68aeeeb9">${param.departmentInfo}</msh:sectionTitle>
	<ecom:webQuery name="datelist" nativeSql="
	select slo.id,slo.dateStart,slo.dateFinish,slo.transferDate
	,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
	,pat.birthday,sc.code from Diagnosis diag
	left join medCase slo on slo.id=diag.medCase_id 
	left join MedCase as sls on sls.id = slo.parent_id 
	left join StatisticStub as sc on sc.medCase_id=sls.id 
	left outer join Patient pat on slo.patient_id = pat.id 
	left join VocIdc10 mkb on mkb.id=diag.idc10_id
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
	where ${fldDate} between to_date('${dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and slo.dtype='DepartmentMedCase'
			${servStream} ${dep} ${mkbCode} and diag.priority_id='${priority}'
			and diag.registrationType_id='${registrationType}'
			${prioritySql} ${regType} ${bedSubTypeSql}
	order by pat.lastname,pat.firstname,pat.middlename
	" 
	/>
    <msh:table name="datelist" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Дата перевода" property="4" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
  </tiles:put>
</tiles:insert>