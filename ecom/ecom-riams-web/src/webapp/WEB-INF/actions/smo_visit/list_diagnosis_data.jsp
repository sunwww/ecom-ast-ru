<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Poly" title="Список визитов по диагнозам за период. <a href='journal_visit_diagnosis.do'>Выбрать другие параметры</a>" guid="81085da2-7de9-45ce-9f89-b40c462727b6" />
    
  </tiles:put>
  <tiles:put name="side" type="string" >
  	<tags:visit_finds currentAction="reportDiagnosis"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:sectionTitle >${param.departmentInfo}</msh:sectionTitle>
	<ecom:webQuery name="datelist" nativeSql="
	select vis.id,vis.dateStart
	,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as fio
	,pat.birthday,mkb.code,vip.name
	,vwf.name||' '||wp.lastname ||' ' ||wp.firstname|| ' ' || wp.middlename as doctor
	 from Diagnosis diag
	left join VocIllnesPrimary vip on vip.id=diag.illnesPrimary_id
	left join VocIdc10 mkb on mkb.id=diag.idc10_id
	left join MedCase vis on vis.id=diag.medCase_id
	left join Patient pat on pat.id=vis.patient_id
	left join VocSex vs on vs.id=pat.sex_id
	left join VocServiceStream vss on vss.id=vis.serviceStream_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
	left join WorkFunction wf on wf.id=vis.workFunctionExecute_id
	left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
	left join Worker w on w.id=wf.worker_id
	left join Patient wp on wp.id=w.person_id
	where vis.dateStart between to_date('${dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and vis.dtype='Visit'
			and (vis.noActuality is null or vis.noActuality='0')
			 and diag.priority_id='${priority}'
			${mkbCode}
			${serviceStreamSql} ${departmentSql} 
			${workFunctionSql}
	order by pat.lastname,pat.firstname,pat.middlename
	" 
	/>
    <msh:table name="datelist" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn property="2" columnName="Дата приема"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="МКБ 10" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Характер заболевания" property="6" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Специалист" property="7" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
  </tiles:put>
</tiles:insert>