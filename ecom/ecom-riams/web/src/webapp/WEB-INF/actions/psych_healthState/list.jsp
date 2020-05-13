<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
  <% if (request.getParameter("ispat")!=null && request.getParameter("ispat").equals("1")) {
	  request.setAttribute("queryDop","pcc.patient_id='"+request.getParameter("id")+"'");
	  request.setAttribute("form","mis_patientForm");
	  request.setAttribute("dopCareCard","") ;
	  request.setAttribute("addParam",".do?ispat=1") ;
  }else {
	  request.setAttribute("queryDop","pcc.patient_id=(select patient_id from PsychiatricCareCard where id='"+request.getParameter("id")+"')");
	  request.setAttribute("form","psych_careCardForm");
	  request.setAttribute("dopCareCard","Redirect") ;
	  request.setAttribute("addParam","") ;
  }
  %>


  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="${form}" title="Список состояний здоровья по пациенту"/>
  </tiles:put>
  <tiles:put name="side" type="string">
      <msh:sideMenu title="Перейти">
        <msh:sideLink action="/entityParentList${dopCareCard}-psych_careCard${addParam}" name="Карты, обратившегося за психиатрической помощью" params="id" roles="/Policy/Mis/Psychiatry/CareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_compulsoryTreatment${addParam}" name="Список принудительных лечений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/View"/>
        <msh:sideLink action="/entityParentList-psych_examination${addParam}" name="Список судебно-психиатрических экспертиз по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/View"/>
        <msh:sideLink styleId="selected_menu" action="/entityParentList-psych_healthState${addParam}" name="Список состояний здоровья пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/HealthState/View"/>
        <msh:sideLink action="/entityParentList-psych_lpuAreaPsychCareCard${addParam}" name="Движение по участкам пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_observation${addParam}" name="Список психиатрических наблюдений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/View"/>
        <msh:sideLink action="/entityParentList-psych_publicDangerousEffect${addParam}" name="Список общественно-опасных событий по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/View"/>
        <msh:sideLink action="/entityParentList-psych_suicide${addParam}" name="Список суицидов пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/Suicide/View"/>
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listd" nativeSql="select phs.id,pcc.cardNumber
  	,phs.startDate,phs.finishDate,vphs.name as vphsname,phs.notes
  	from PsychicHealthState phs
  		left join VocPsyhicHealthState vphs on vphs.id=phs.kind_id
  		left join PsychiatricCareCard pcc on pcc.id=phs.careCard_id   	where ${queryDop}   	"/>
    <msh:table name="listd" action="entityParentView-psych_healthState.do" idField="1">
              <msh:tableColumn property="sn" columnName="#"/>
              <msh:tableColumn property="1" columnName="ИД"/>
              <msh:tableColumn property="2" columnName="№карты"/>
              <msh:tableColumn property="3" columnName="Дата начала"/>
              <msh:tableColumn property="4" columnName="Дата окончания"/>
              <msh:tableColumn property="5" columnName="Вид состояния псих.здоровья"/>
              <msh:tableColumn property="6" columnName="Описание"/>
    </msh:table>
  </tiles:put>
</tiles:insert>

