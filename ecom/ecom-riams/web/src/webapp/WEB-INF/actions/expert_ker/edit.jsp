<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - КЭР
    	  -->
    <msh:form action="/entityParentSaveGoView-expert_ker.do" defaultField="expertDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:hidden property="patient" />
      <msh:panel colsWidth="1%,1%,1%,97%">
      	<msh:row>
      		<msh:separator colSpan="4" label="Направление на ВК"/>
      	</msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocExpertType" property="type" label="Тип ВК" 
          	horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocExpertPatientStatus" property="patientStatus" label="Статус пациента" 
          	horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:textField property="profession" label="Профессия" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="orderDate" label="Дата напр."/>
        	<msh:textField property="disabilityDate" label="Дата  выхода на нетруд."/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderFunction" fieldColSpan="3" label="Специалист"
        		vocName="workFunction" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="reasonDirect" parentAutocomplete="type" fieldColSpan="5" label="Причина подачи"
        		vocName="vocExpertReason" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="reasonAdd" fieldColSpan="3" label="Описание причины" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="mainDiagnosis" fieldColSpan="3" label="Код осн. диаг." horizontalFill="true" vocName="vocIdc10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="treatmentCurrent" label="Лечение на момент подачи" horizontalFill="true" fieldColSpan="5"/>
        </msh:row>        
        <msh:row>
        	<msh:autoComplete property="orderConclusion" parentAutocomplete="type" fieldColSpan="3" label="Обоснование напр." horizontalFill="true" vocName="vocExpertOrderConclusion"/>
        </msh:row>
          <msh:row>
              <msh:textField property="patientHealthInfo" label="Описание состояния здоровью пациента" fieldColSpan="5" size="50" />
          </msh:row>
        <msh:row>
        	<msh:textField property="delayReason" label="Обоснов. задержки подачи на ВК" horizontalFill="true" labelColSpan="2" fieldColSpan="2"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="preFinishDate" label="Срок предполагаемого лечения" labelColSpan="3"/>
        	<td style="text-align: left; font-weight: bold" id="infoReadPreOnly" />
        </msh:row>        
        </msh:panel>
        <msh:panel colsWidth="1%,1%,1%,97%">
        <msh:row>
        	<msh:separator label="ОПИСАНИЕ" colSpan="4"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/ClinicExpertCard/NumberInJournal">
        <msh:row>
                	<msh:textField property="numberInJournal" labelColSpan="3" label="Порядковый номер в журнале" viewOnlyField="true"/>
        </msh:row>
        </msh:ifInRole>
        <msh:row>
        	<msh:textField property="expertDate" label="Дата экспертизы"/>
        	<msh:autoComplete property="patternCase" fieldColSpan="1" label="Характеристика" vocName="vocExpertPatternCase" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="disabilityDocument" fieldColSpan="3" label="Лист нетруд." parentId="expert_kerForm.patient"
        		vocName="disabilityDocumentByPatient" horizontalFill="true"/>
        </msh:row>
        <msh:row>
            <msh:textField property="anotherDisabilityNumber" label="Номер ЛН выданного другим ЛПУ"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="modeCase" fieldColSpan="3" label="Вид экспертизы" horizontalFill="true" vocName="vocExpertModeCase"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="subjectCase" fieldColSpan="3" label="Предмет" horizontalFill="true" vocName="vocExpertSubject"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="deviationStandards" label="Отклонения от стандарта" horizontalFill="true" vocName="vocExpertDeviationStandards"/>
        	<msh:textField property="deviationStandardsText" label="отклонение" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="defects" label="Дефекты" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="resultStep" label="Достижения ЛПМ" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="conclusionDate" label="Продлен до"/>
        	<msh:autoComplete property="conclusion" fieldColSpan="3" label="Обоснование" horizontalFill="true" vocName="vocExpertConclusion"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentAutocomplete="conclusion" property="conclusionSent" fieldColSpan="3" label="Заключение напр." horizontalFill="true" vocName="vocExpertConclusionSent"/> 
        </msh:row>	
        <msh:row>
        	<msh:textField property="additionInfo" label="Заключение доп.инф." fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="notes" horizontalFill="true" label="Замечания" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="expComposition" fieldColSpan="3" label="Состав экспертов" horizontalFill="true" vocName="vocExpertComposition"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="МСЭ" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="orderHADate" label="Дата напр." />
        	<msh:textField property="conclusionHA" label="Заключ." horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="receiveHADate" label="Дата получ. рез."/>
        	<msh:textField property="additionInfoHA" label="Доп.инфор."  horizontalFill="true"/>
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
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <tags:stac_selectPrinter  name="Select" roles="/Policy/Config/SelectPrinter" />
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="expert_kerForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeIsView formName="expert_kerForm">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-expert_ker" name="Изменить" roles="/Policy/Mis/MedCase/ClinicExpertCard/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-expert_ker" name="Удалить" roles="/Policy/Mis/MedCase/ClinicExpertCard/Delete" />
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:sideMenu title="Просмотр">
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-expert_ker-infoBySmo.do?id='+$('medCase').value+'&short=Short')" 
        name='ВК по госпитализации' title="Просмотр ВК по госпитализации" params="" roles="/Policy/Mis/MedCase/ClinicExpertCard/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:getDefinition('js-expert_ker-infoByPatient.do?id='+$('patient').value+'&short=Short')" 
        name='ВК по пациенту' title="Просмотр ВК по пациенту" params="" roles="/Policy/Mis/MedCase/ClinicExpertCard/View" />
    
    </msh:sideMenu>
    <msh:sideMenu title="Печать" >
        <msh:sideLink action='/javascript:initSelectPrinter("print-directVK.do?s=HospitalPrintService&amp;m=printDirectVK&id=${param.id}",1)'
        	name="Направления" title="Печать направления на ВК" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/PrintDirect" />
        <msh:sideLink
        action='/javascript:initSelectPrinter("print-directVKresult.do?s=HospitalPrintService&amp;m=printDirectVK&id=${param.id}",1)'  
        	name="Протокола" title="Печать протокола направления на ВК" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/PrintDirect" />
    </msh:sideMenu>    
	<msh:sideMenu title="Журналы по КЭР">
	      <msh:sideLink name="Направления на ВК" action="/expert_journal_ker.do"
	      	roles="/Policy/Mis/MedCase/ClinicExpertCard/JournalByPeriod" styleId="journalKERByPeriod"
	      />
	      <msh:sideLink name="Открытые направления на ВК" action="/expert_kersopen.do"
	      	roles="/Policy/Mis/MedCase/ClinicExpertCard/JournalOpenCase" styleId="journalOpenKER"
	      />
	
	</msh:sideMenu>    
    <msh:ifFormTypeIsNotView formName="expert_kerForm">
    	<msh:ifFormTypeAreViewOrEdit formName="expert_kerForm">
		    <msh:sideMenu>
		      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-expert_ker" name="Удалить" roles="/Policy/Mis/MedCase/ClinicExpertCard/Delete" />
		    </msh:sideMenu>
            <tiles:put name="javascript" type="string">
                <script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
                <script language="javascript" type="text/javascript">
                    if ($('expComposition').value=='') {
                        DisabilityService.getDefaultKer({
                            callback : function(aResult) {
                                if (aResult!='') {
                                    var res=aResult.split('#');
                                    if (res[0]!='' && res[1]!='') {
                                        $('expComposition').value=res[0];$('expCompositionName').value=res[1];
                                    }
                                }
                            }
                        });
                    }
                </script>
            </tiles:put>
    	</msh:ifFormTypeAreViewOrEdit>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>