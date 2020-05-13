<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Направление на ВК
    	  -->
    <msh:form action="/entityParentSaveGoView-expert_ker_direct.do" defaultField="patientStatusName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:hidden property="patient" />
      <msh:panel colsWidth="1%,1%,1%,1%,96%">
        <msh:row>
        	<msh:autoComplete vocName="vocExpertType" property="type" label="Тип ВК" 
          	horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocExpertPatientStatus" property="patientStatus" label="Статус пациента" 
          	horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row>
        	<msh:textField property="profession" label="Профессия" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="disabilityDate" label="Дата  выхода на нетруд."/>
        	<msh:textField property="orderDate" label="Дата напр."/>
        	<td style="text-align: left; font-weight: bold" id="infoReadOnly" />
        </msh:row>
        <msh:row>

        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderLpu" fieldColSpan="5" label="Отделение"
        		vocName="lpu" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderFunction" fieldColSpan="5" label="Специалист"
        		vocName="workFunctionByLpu" parentAutocomplete="orderLpu" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reasonDirect" parentAutocomplete="type" fieldColSpan="5" label="Причина подачи"
        		vocName="vocExpertReason" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="reasonAdd" fieldColSpan="3" label="Описание причины" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="disabilityDocument" fieldColSpan="5" label="Лист нетруд." parentId="expert_ker_directForm.patient"
        		vocName="disabilityDocumentByPatient" horizontalFill="true"/>
        </msh:row>
          <msh:row>
              <msh:textField property="anotherDisabilityNumber" label="Номер ЛН выданного другим ЛПУ"/>
          </msh:row>
        <msh:row>
        	<msh:autoComplete property="mainDiagnosis" fieldColSpan="5" label="Код осн. диаг." horizontalFill="true" vocName="vocIdc10"/>
        </msh:row>      
        <msh:row>
        	<msh:textField property="treatmentCurrent" label="Лечение на момент подачи" horizontalFill="true" fieldColSpan="5"/>
        </msh:row>                
        <msh:row>
        	<msh:autoComplete property="orderConclusion" parentAutocomplete="type" fieldColSpan="5" label="Обоснование напр." horizontalFill="true" vocName="vocExpertOrderConclusion"/>
        </msh:row>
          <msh:row>
              <msh:textField property="patientHealthInfo" label="Описание состояния здоровью пациента" fieldColSpan="5" size="50" />
          </msh:row>
        <msh:row>
        	<msh:textField property="delayReason" label="Обоснов. задержки подачи на ВК" horizontalFill="true" labelColSpan="4" fieldColSpan="2"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="preFinishDate" label="Срок предполагаемого лечения" labelColSpan="3"/>
        	<td style="text-align: left; font-weight: bold" id="infoReadPreOnly" />
        </msh:row>        
        </msh:panel>
        <msh:panel colsWidth="5%,5%,5%,5%,5%">
	        <msh:row>
	        	<msh:separator label="Дополнительная информация" colSpan="6"/>
	        </msh:row>
	        <msh:row>
	        	<msh:label property="createDate" label="Дата создания"/>
	        	<msh:label property="createTime" label="время"/>
	        	<msh:label property="createUsername" label="пользователь"/>
	        </msh:row>
	        <msh:row>
	        	<msh:label property="editDate" label="Дата редактирования"/>
	        	<msh:label property="editTime" label="время"/>
	        	<msh:label property="editUsername" label="пользователь"/>
	        </msh:row>  
	        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put type="string" name="javascript">
  	<script type="text/javascript">
  	function setPeriod(aBeginFld,aFinishFld,aFldInfo,aAddCnt) {
  		try {
  			if ($(aBeginFld).value.length==10 &&  $(aFinishFld).value.length==10) {
	  			var dateTo = new Date($(aFinishFld).value.replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1')+" 12:12:12 GMT +0300") ;
	  			var dateFrom = new Date($(aBeginFld).value.replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1')+" 12:12:12 GMT +0300") ;
	  			var one_day=1000*60*60*24 ;
	  			$(aFldInfo).innerHTML=" &nbsp;кол-во дней: "+(aAddCnt+((dateTo.getTime()-dateFrom.getTime())/one_day)) +"";
  			}
  		} catch(e) {
  			alert(e) ;
  		}
  	}
  	
  	var fnPeriod = function() {setPeriod("disabilityDate","orderDate",'infoReadOnly',1) ;} ; 
  	var fnPeriod1 = function() {setPeriod("orderDate","preFinishDate",'infoReadPreOnly',0) ;} ; 
  	
    function updateFldPeriod(aFld,aFnPeriod) {
    	eventutil.addEventListener($(aFld), eventutil.EVENT_KEY_UP,	aFnPeriod) ;   	
    	eventutil.addEventListener($(aFld), eventutil.EVENT_KEY_DOWN, aFnPeriod) ;
    	eventutil.addEventListener($(aFld), "blur", aFnPeriod) ;
    	eventutil.addEventListener($(aFld), "change", aFnPeriod) ;
    }
  	updateFldPeriod("disabilityDate",fnPeriod) ; 
  	updateFldPeriod("orderDate",fnPeriod) ;
  	updateFldPeriod("preFinishDate",fnPeriod1) ; 
  	updateFldPeriod("orderDate",fnPeriod1) ;
  	setPeriod("disabilityDate","orderDate",'infoReadOnly',1) ;
  	setPeriod("orderDate","preFinishDate",'infoReadPreOnly',0) ;
  	</script>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="expert_ker_directForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeIsView formName="expert_ker_directForm">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-expert_ker_direct" name="Изменить" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-expert_ker_direct" name="Удалить" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Delete" />
    </msh:sideMenu>
    
    <msh:sideMenu title="Печать" >
        <msh:sideLink params="id" action="/print-directVK.do?s=HospitalPrintService&amp;m=printDirectVK"  
        	name="Направления" title="Печать направления на ВК" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/PrintDirect" />
        <msh:sideLink params="id" action="/print-directVKresult.do?s=HospitalPrintService&amp;m=printDirectVK"  
        	name="Протокол" title="Печать протокола направления на ВК" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/PrintDirect" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink action="/entityParentListRedirect-expert_ker_direct" name="К списку" params="id" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/View"/>
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>