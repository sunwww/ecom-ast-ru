<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Expert" beginForm="mis_medCaseForm" guid="a79e22af-e87a-45dd-9743-59a1f8f3d66a" title="Экспертные карты"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="0d13c843-c26a-4ae2-ae97-d61b44618bae" title="Добавить по СМО">
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-expert_card" name="Экспертную карту" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Mis/MedCase/QualityEstimationCard/Create" />
    </msh:sideMenu>
    <msh:sideMenu>
    	<msh:sideLink  action="/entityParentList-expert_card.do" params="id" name="Список экспертных карт по СМО" roles="/Policy/Mis/MedCase/QualityEstimationCard/View" title="Список экспертных карт" styleId="selected"/>
    </msh:sideMenu>
        <tags:expert_menu currentAction="expert_card_smo"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section title="Список экспертных карт" createRoles="/Policy/Mis/MedCase/QualityEstimationCard/Create" createUrl="entityParentPrepareCreate-expert_card.do?id=${param.id}">
  			<ecom:webQuery  name="Expert" nativeSql="
  			
select card.id,vek.name,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename,d.name,p.lastname||' '||p.firstname||' '||p.middlename
 ,case when md.DTYPE='HospitalMedCase' then 'СЛС №'||ss1.code  when md.DTYPE='DepartmentMedCase' then 'СЛО '||md.id||' СЛС №'||ss2.code  when md.DTYPE='PolyclinicMedCase' then 'СПО №'||md.id else 'СМО №'||md.id end 
 ,mkb.code,card.createDate,card.createUsername 
 ,(select avg(vqem.mark) from QualityEstimationCrit qecB left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeB.id)
 , (select avg(vqem.mark) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeE.id)
 , (select avg(vqem.mark) from QualityEstimationCrit qecB  left join vocqualityEstimationMark vqem on vqem.id=qecB.mark_id where qecB.estimation_id=qeC.id)
 from QualityEstimationCard card
 left join VocQualityEstimationKind vek on vek.id=card.kind_id
 left join WorkFunction wf on wf.id=card.doctorCase_id
 left join Worker w on w.id=wf.worker_id
 left join Patient wp on wp.id=w.person_id
 left join Patient p on p.id=card.patient_id
 left join VocIdc10 mkb on mkb.id=card.idc10_id
 left join MisLpu d on d.id=card.department_id
 left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
 left join MedCase md on md.id=card.medcase_id
 left join MedCase mh on mh.id=md.parent_id
 left join StatisticStub ss1 on ss1.id=md.statisticStub_id
 left join StatisticStub ss2 on ss2.id=mh.statisticStub_id
 left join qualityestimation qeB on card.id=qeB.card_id and qeB.expertType='BranchManager'  left join qualityestimation qeE on card.id=qeE.card_id and qeE.expertType='Expert'  left join qualityestimation qeC on card.id=qeC.card_id and qeC.expertType='Coeur'
where card.medcase_id='${param.id}'"/>
  			<msh:table name="Expert"  action="entityParentView-expert_card.do" idField="1">
      <msh:tableColumn columnName="Тип экспертизы" property="2" guid="69-8a75-e825fd37e296" />
      <msh:tableColumn columnName="Леч.врач" property="3" guid="69-8a75-e825fd37e296" />
      <msh:tableColumn columnName="Отделение" property="4" guid="81b717f5-f9db-4033-aa22-c680b21" />
      <msh:tableColumn columnName="Диагноз" property="7" />
      <msh:tableColumn columnName="Пациент" property="5" guid="69-8a75-e825fd37e296" />
      <msh:tableColumn columnName="№мед.карты" property="6" guid="698c-d4e3-4be5-8a75-e825fd37e296" />
      <msh:tableColumn columnName="Зав.отд." property="10" guid="6c34a7-4512-90aa-75e4e5ae6d63" />
      <msh:tableColumn columnName="Эксперт" property="11" guid="6c3be340-b4a7-4512-90aa-75e4e5ae6d63" />
      <msh:tableColumn columnName="КЭР" property="12" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />
      <msh:tableColumn columnName="Пользователь" property="9" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />
      <msh:tableColumn columnName="Дата создания" property="8" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />

  			</msh:table>
	  	</msh:section>
  </tiles:put>
</tiles:insert>

