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
    <ecom:titleTrail mainMenu="Patient" beginForm="${form}" title="Список психиатрических экспертиз по пациенту"/>
  </tiles:put>
  <tiles:put name="side" type="string">
      <msh:sideMenu title="Перейти">
        <msh:sideLink action="/entityParentList${dopCareCard}-psych_careCard${addParam}" name="Карты, обратившегося за психиатрической помощью" params="id" roles="/Policy/Mis/Psychiatry/CareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_compulsoryTreatment${addParam}" name="Список принудительных лечений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/View"/>
        <msh:sideLink styleId="selected_menu" action="/entityParentList-psych_examination${addParam}" name="Список судебно-психиатрических экспертиз по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/View"/>
        <msh:sideLink action="/entityParentList-psych_healthState${addParam}" name="Список состояний здоровья пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/HealthState/View"/>
        <msh:sideLink action="/entityParentList-psych_lpuAreaPsychCareCard${addParam}" name="Движение по участкам пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/View"/>
        <msh:sideLink action="/entityParentList-psych_observation${addParam}" name="Список психиатрических наблюдений по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/View"/>
        <msh:sideLink action="/entityParentList-psych_publicDangerousEffect${addParam}" name="Список общественно-опасных событий по пациенту" params="id" roles="/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect/View"/>
        <msh:sideLink action="/entityParentList-psych_suicide${addParam}" name="Список суицидов пациента" params="id" roles="/Policy/Mis/Psychiatry/CareCard/Suicide/View"/>
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listd" nativeSql="select pe.id,pcc.cardNumber,pe.actNumber,pe.examinationDate
  	,vcc.name as vccname,vcca.name as vccaname,vpe.name as vpename,vpep.name as vpepname,pe.reporter,pe.expertDecision,pe.actNotes
  	
  	from PsychiatricExamination pe
  		left join VocCriminalCase vcc on vcc.id=pe.criminalCase_id
  		left join VocCriminalCodeArticle vcca on vcca.id=pe.criminalCodeArtical_id
  		left join VocPsychExamination vpe on vpe.id=pe.kind_id
  		left join VocPsychExamPaticipation vpep on vpep.id = pe.paticipation_id
  		left join PsychiatricCareCard pcc on pcc.id=pe.careCard_id   	where ${queryDop}	"/>
        <msh:sideMenu>
    </msh:sideMenu>
    <msh:table name="listd" action="entityParentView-psych_examination.do" idField="1">
              <msh:tableColumn property="sn" columnName="#"/>
              <msh:tableColumn property="1" columnName="ИД"/>
              <msh:tableColumn property="2" columnName="№карты"/>
              <msh:tableColumn property="3" columnName="Номер акта"/>
              <msh:tableColumn property="4" columnName="Дата экспертизы"/>
              <msh:tableColumn property="5" columnName="Вид уголовного дела"/>
              <msh:tableColumn property="6" columnName="Статья уголовного кодекса"/>
              <msh:tableColumn property="7" columnName="Вид экспертизы"/>
              <msh:tableColumn property="8" columnName="Вид участия в экспертизе"/>
              <msh:tableColumn property="9" columnName="Докладчик"/>
              <msh:tableColumn property="10" columnName="Экспертное заключение"/>
              <msh:tableColumn property="11" columnName="Описание акта"/>
    </msh:table>
  </tiles:put>
</tiles:insert>

