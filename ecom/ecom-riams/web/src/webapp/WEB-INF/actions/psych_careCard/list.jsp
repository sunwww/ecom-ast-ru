<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm" title="Список карт по пациенту"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  		<msh:sideMenu title="Добавить">
  			<msh:sideLink action="/entityParentPrepareCreate-psych_careCard" name="Карту, обратившегося за психиатрической помощью" params="id" key="ALT+2" roles="/Policy/Mis/Psychiatry/CareCard/Create" />
  		</msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="46a542d6-da04-4268-bb9a-5ebddf3baea2">
        <msh:sideLink styleId="selected_menu" action="/entityParentList-psych_careCard" name="Карты обратившегося за психиатрической помощью" params="id" roles="/Policy/Mis/Psychiatry/CareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_compulsoryTreatment.do?ispat=1" name="Список принудительных лечений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/View"/>
        <msh:sideLink action="/entityParentList-psych_examination.do?ispat=1" name="Список судебно-психиатрических экспертиз по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/View"/>
        <msh:sideLink action="/entityParentList-psych_healthState.do?ispat=1" name="Список состояний здоровья пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/HealthState/View"/>
        <msh:sideLink action="/entityParentList-psych_lpuAreaPsychCareCard.do?ispat=1" name="Движение по участкам пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_observation.do?ispat=1" name="Список психиатрических наблюдений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/View"/>
        <msh:sideLink action="/entityParentList-psych_publicDangerousEffect.do?ispat=1" name="Список общественно-опасных событий по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/View"/>
        <msh:sideLink action="/entityParentList-psych_suicide.do?ispat=1" name="Список суицидов пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/Suicide/View"/>
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/View" guid="a7036440-353f-4667-a18e-a0da4885cdaa">
        <msh:section>
        	<msh:sectionTitle>Карты, обратившегося за психиатрической помощью. <a href='entityParentPrepareCreate-psych_careCard.do?id=${param.id}'>Добавить</a></msh:sectionTitle>
        	<msh:sectionContent>
		        <ecom:webQuery name="psychCard" hql="select id,cardNumber from PsychiatricCareCard where patient_id='${param.id}'"/>
		        <msh:table hideTitle="false" disableKeySupport="false" idField="1" name="psychCard" action="entityParentView-psych_careCard.do" disabledGoFirst="false" disabledGoLast="false" guid="c60c25643d41-d947-476f-8b04-88dc333e4">
		            <msh:tableColumn columnName="Номер карты" identificator="false" property="2" guid="6d12646caf2-c76d-4e99-a8d2-afc6ef8bcdf6" />
		        </msh:table>
        	</msh:sectionContent>
        </msh:section>
      </msh:ifInRole>
  </tiles:put>
</tiles:insert>

