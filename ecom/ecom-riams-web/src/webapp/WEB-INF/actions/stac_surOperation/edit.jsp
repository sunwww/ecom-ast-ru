<%@page import="java.util.Collection"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.awt.print.Printable"%>
<%@page import="ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            
            .protocols {
				left:0px;  width:60em; 
				top:0px;  height:55em;
				overflow: auto;
			}
			.operationText {
			width:100%;
			}
			.histologicalStudy {
			width:100%;
			}
        </style>

    </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Хирургическая операция
    	  -->
    	  <%
        	SurgicalOperationForm frm = (SurgicalOperationForm)request.getAttribute("stac_surOperationForm") ;
        	request.setAttribute("medcase", frm.getMedCase()) ;
        	
        %>
    <msh:form action="/entityParentSaveGoSubclassView-stac_surOperation.do" defaultField="" guid="137f576d-2283-4edd-9978-74290e04b873" editRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
      <msh:panel guid="80209fa0-fbd4-45d0-be90-26ca4219af2e" colsWidth="15px,250px,15px">
        <msh:hidden property="id" />
        <msh:hidden property="patient" />
        <msh:hidden property="saveType" />
        <msh:hidden property="medCase" />
        <msh:hidden property="lpu" />
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:row guid="132b1-2e6b-425a-a14e-1c330959">
          <msh:textField property="numberInJournal" label="Номер протокола"  labelColSpan="2" fieldColSpan="2"/>
        </msh:row>
        <msh:separator label="Сведения до операции" colSpan="" guid="a7a51c30-4065-4ab85b4ade6f66" />
        <msh:row guid="132b1-2e6b-425a-a14e-1c330959">
          <msh:autoComplete property="idc10Before" label="МКБ до операции" guid="e3939-a6a1-303f14f" fieldColSpan="3" horizontalFill="true" vocName="vocIdc10" />
        </msh:row>
        </msh:ifNotInRole>
        <msh:separator label="Сведения об операции" colSpan="" guid="a7a51c304-335b4ade6f66" />
 	        <msh:row guid="f7540b-4474-46c6-b162-828">
	          <msh:textField property="operationDate" label="Начало дата" guid="e8636a99-31e6-4c99-a6f5-825da2a35caf" />
	          <msh:textField property="operationTime" label="время" guid="b5bc7756-2fa4-496b-8a35-f54f44be9732" />
	        </msh:row>
	        <msh:row guid="f7b4a40b-4474-46c6-b162-80be1590e1a8">
	          <msh:textField property="operationDateTo" label="Окончание дата" guid="e8599-31e6-4c99-a6f5-885caf" />
	          <msh:textField property="operationTimeTo" label="время" guid="496b-8a35-f89732" />
	        </msh:row>
        <msh:row guid="a03a1e02-5a44-4403-bb71-fb8e5afcec43">
          <msh:autoComplete property="department" label="Отделение" guid="cfc50051-15f6-4b6f-a382-9c5387482c60" fieldColSpan="3" horizontalFill="true" vocName="vocLpuOtd" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="serviceStream" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" vocName="vocServiceStream" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete label="Хирург" property="surgeon" horizontalFill="true" fieldColSpan="3" vocName="workFunctionIsSurgical" />
        </msh:row>     
        <msh:ifFormTypeIsView formName="stac_surOperationForm">   
        <msh:row guid="1221-2e6b-425a-a14e-1c02959">
          <msh:autoComplete property="operation" label="Операция" size="60" guid="e22-9d6f-4c39-a6a1-302f14f" fieldColSpan="3" horizontalFill="true" vocName="vocOperation" />
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row guid="1221-2e6b-425a-a14e-1c02959">
          <msh:autoComplete property="medService" label="Операция (услуга)" size="60" fieldColSpan="3" horizontalFill="true" vocName="medServiceOperation" />
        </msh:row>
        <mis:ifPatientIsWoman classByObject="MedCase" idObject="${medcase}">
	        <msh:row>
	        	<msh:autoComplete property="abortion" vocName="vocAbortion" fieldColSpan="3" horizontalFill="true" label="Тип аборта"/>
	        </msh:row>
        </mis:ifPatientIsWoman>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        	<msh:hidden property="surgeonFunctions"/>
        	<msh:hidden property="complications"/>
        	<msh:hidden property="profile"/>
        	<msh:hidden property="operatingNurse"/>
        	<msh:hidden property="method"/>
        	<msh:hidden property="operationText"/>
        	<msh:hidden property="aspect"/>
        	<msh:hidden property="technology"/>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        <msh:row guid="1221-2e6b-425a-a14e-1c02959">
          <msh:autoComplete property="profile" label="Профиль" guid="e22-9d6f-4c39-a6a1-302f14f" horizontalFill="true" vocName="vocSurgicalProfile" />
          <msh:autoComplete property="method" label="Метод" guid="e22-9d6a1-302f14f" horizontalFill="true" vocName="vocOperationMethod" />
        </msh:row>
        <msh:row guid="ca8a7727-42ac-4c64-8e52-23d4f84dfe43">
          <msh:textArea rows="6" hideLabel="false" property="operationText" viewOnlyField="false" guid="e-5833-4bc3-80df-52fdd237fce9" fieldColSpan="3" label="Протокол операции" />
        </msh:row>
        <msh:row>
          <msh:ifFormTypeIsNotView formName="stac_surOperationForm" guid="1c1ec646-5-b9d5-177a7324aa7f">
            <td colspan="4" align="center">
              <input type="button" value="Шаблон" onClick="showOperationTextTempTemplateProtocol()" />
              <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
            </td>
          </msh:ifFormTypeIsNotView>
        </msh:row>
        </msh:ifNotInRole>
        <msh:row guid="203625bc-d215-4f48-8ee3-44f48785755d">
          <msh:autoComplete horizontalFill="true" property="aspect" label="Показания" vocName="vocHospitalAspect" />
          <msh:autoComplete horizontalFill="true" vocName="vocOperationTechnology" property="technology" label="С испол. ВМТ"/>
        </msh:row>
        
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/HideCheckBox">
	       <msh:row>
	          <msh:checkBox property="base" label="Основная" guid="35bdec3d-2c23-47df-b8c7-4fb706224994" fieldColSpan="1" />
	          <msh:checkBox property="minor" label="Малая операция" guid="a8774c57-1b50-4358-916d-ba51249357e7" />
	       </msh:row>
        </msh:ifNotInRole>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">          
        
        <msh:row guid="f0851bc5-6ac2-4e6f-bfab-90593e637799">
          <ecom:oneToManyOneAutocomplete label="Ассистенты" property="surgeonFunctions" vocName="workFunctionIsSurgical" guid="e68271bf-c384-4022-9fb6-6ba7eeedb6fe" />
        </msh:row>
        <msh:row guid="12721-2e6b-425a-a14e-1c0298959">
          <msh:autoComplete property="operatingNurse" label="Опер. медсестра" guid="e282-9d6f-4c39-a6a1-30g2f14f" fieldColSpan="3" horizontalFill="true" vocName="workFunctionIsInstrumentNurse" />
        </msh:row>
        </msh:ifNotInRole>

        <msh:ifFormTypeIsCreate formName="stac_surOperationForm">
        	<msh:row>
        		<msh:separator label="Анестезия" colSpan="4"/>
        	</msh:row>
	        <msh:row>
	          <msh:autoComplete property="isAnesthesia" label="Анестезия проводилась?" horizontalFill="true" vocName="vocYesNo" fieldColSpan="3" />
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="anesthesia" label="Метод" horizontalFill="true" vocName="vocAnesthesiaMethod" fieldColSpan="3" />
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="anesthesiaService" label="Услуга" horizontalFill="true" vocName="medServiceAnesthesia" fieldColSpan="3" />
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="anesthesiaType" label="Вид" horizontalFill="true" vocName="vocAnesthesia" fieldColSpan="3" />
	        </msh:row>
	        <msh:row >
	          <msh:autoComplete property="anaesthetist" label="Анестезист" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
	        </msh:row>
        </msh:ifFormTypeIsCreate>
         <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/HideCheckBox">
        <msh:separator label="Использование специальной аппаратуры" colSpan="" guid="78d9-74f0-401c-ab10-01bdf544acd8" />
	        <msh:row guid="c065ec29-09d7-49de-adbb-516655c251c1">
	          <msh:checkBox property="endoscopyUse" label="Эндоскопия" guid="35f35cdf-4b7e-45f7-af9f-252d656e10d7" />
	          <msh:checkBox property="laserUse" label="Лазерная аппаратура" guid="8a4372de-f324-4206-9369-adaa25ffa23e" />
	        </msh:row>
	        <msh:row guid="738f9c05-0052-41af-ba25-28e72a055b1e">
	          <msh:checkBox property="cryogenicUse" label="Криогенная аппаратура" guid="99f18eb4-ad41-4d24-8e21-5a5df64e0d07" />
	        </msh:row>
        <msh:separator label="Сведения после операции" colSpan="" guid="a7a51c30-4065-4ab8-ac94-335b4ade6f66" />
        
        <msh:row guid="ad5ec-5754-4cbd-bcb5-a592">
          <ecom:oneToManyOneAutocomplete vocName="vocComplication" colSpan="3" label="Осложнения" property="complications" guid="652c9b95-2724-4086-87f5-aefd67b01e8c" />
        </msh:row>
        <msh:row guid="1aaf301e-9bae-462b-9e03-e9c5f3aa5162">
          <msh:textArea hideLabel="false" property="histologicalStudy" viewOnlyField="false" label="Гистол. исследование" guid="535c0b96-277f-4f60-bd03-19421447bcd0" fieldColSpan="3" horizontalFill="true" rows="6" />
        </msh:row>
        <msh:row>
          <msh:ifFormTypeIsNotView formName="stac_surOperationForm" guid="1c1ec646-5-b9d5-177a7324aa7f">
            <td colspan="4" align="center">
              <input type="button" value="Шаблон" onClick="showHistologicalStudyTempTemplateProtocol()" />
              <input type="button" id="changeSizeHistButton" value="Увеличить" onclick="changeSizeHist()">
            </td>
          </msh:ifFormTypeIsNotView>
        </msh:row>

        <msh:row guid="13-2e6b-425a-a14e-1359">
          <msh:autoComplete property="idc10After" label="МКБ после операции" guid="ef192-9d6f-4c39-a6a1-304f" fieldColSpan="3" horizontalFill="true" vocName="vocIdc10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="outcome" label="Исход операции" fieldColSpan="3" horizontalFill="true" vocName="vocOperationOutcome" />
        </msh:row>
        </msh:ifNotInRole>
                <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="createUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="printDate" label="Дата печати"/>
        	<msh:hidden property="printTime"/>
        	<msh:label property="printUsername" label="пользователь"/>
        </msh:row>
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm" guid="6ea7dcbb-d32c-4230-b6b0-a662dcc9f568">
      <tags:templateProtocol property="histologicalStudy" name="HistologicalStudyTemp" 
      idSmo="stac_surOperationForm.medCase" version="Visit" voc="protocolVisitByPatient"
      />
      <tags:templateProtocol property="operationText" name="OperationTextTemp" 
      idSmo="stac_surOperationForm.medCase" version="Visit" voc="protocolVisitByPatient"
      />
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="stac_surOperationForm" guid="e71c21cc-2a77-4d16-9ee0-ba293d19a42b">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View" guid="9a06820c-3f3b-4744-880d-06aa1745888d">
        <ecom:parentEntityListAll attribute="anesthesies" formName="stac_anesthesiaForm" guid="af9854a8-f5a7-45b6-859b-ecbaec1ef3b9" />
        <msh:tableNotEmpty name="anesthesies" guid="cc93a3d4-7e22-4b2f-a16c-46b56c963753">
          <msh:section title="Анестезия" guid="8ac85b8f-45f3-439b-979d-f7ce88a54dbd">
            <msh:table name="anesthesies" action="entityParentView-stac_anesthesia.do" idField="id" guid="d89b3a7c-2689-48fb-be75-3da899826749">
              <msh:tableColumn columnName="Анестезиолог" property="anesthesistInfo" guid="8e832f90-6905-44cf-952e-76495689c35b" />
              <msh:tableColumn columnName="Длительность (мин)" property="duration" guid="8e832f90-6905-44cf-952e-76495689c35b" />
              <msh:tableColumn columnName="Метод" property="methodInfo" guid="8e832f90-6905-44cf-952e-76495689c35b" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
    	<tags:mis_double name='MedService' title='Данная операция оказана:' cmdAdd="document.forms[0].submitButton.disabled = false "/>
    </msh:ifFormTypeIsNotView>  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_surOperationForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_surOperationForm" guid="c7cae1b4-31ca-4b76-ab51-7f75b52d11b6">
      <msh:sideMenu title="Хир. операция" guid="edd9bfa6-e6e7-4998-b4c2-08754057b0aa">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_surOperation" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_surOperation" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_surOperation" name="⇧Cписок операций" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" guid="a604af1f-cdbe-447b-f6ef8209698f" title="Перейти к списку операций" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="a28f403f-54ac-4982-8133-2b10d91ea770">
        <msh:sideLink key="CTRT+1" params="id" action="/entityParentPrepareCreate-stac_anesthesia" name="Анестезию" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View" guid="a604af1f-cdbe-418a-947b-f6ef8209698f" title="Добавить анестезию" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-surgicalOperation.do?m=printSurOperation&s=HospitalPrintService" name="Протокола операции"/>
      	<msh:sideLink key="CTRL+3" params="id" action="/print-surgicalOperation2.do?m=printSurOperation&s=HospitalPrintService" name="Протокола операции 2 копии"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type="text/javascript">// <![CDATA[//
    
	var isChangeSizeEpicrisis=1 ;
	var isChangeSizeHist=1 ;
	function changeSizeEpicrisis() {
		if (isChangeSizeEpicrisis==1) {
			Element.addClassName($('operationText'), "protocols") ;
			if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
			isChangeSizeEpicrisis=0 ;
		} else {
			Element.removeClassName($('operationText'), "protocols") ;
			if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
			isChangeSizeEpicrisis=1;
		}
	}
	eventutil.addEventListener($('operationText'), "dblclick", 
  		  	function() {
				changeSizeEpicrisis() ;
  		  	}) ;
	
	function changeSizeHist() {
		if (isChangeSizeHist==1) {
			Element.addClassName($('histologicalStudy'), "protocols") ;
			if ($('changeSizeHistButton')) $('changeSizeHistButton').value='Уменьшить' ;
			isChangeSizeHist=0 ;
		} else {
			Element.removeClassName($('histologicalStudy'), "protocols") ;
			if ($('changeSizeHistButton')) $('changeSizeHistButton').value='Увеличить' ;
			isChangeSizeHist=1;
		}
	}
	eventutil.addEventListener($('histologicalStudy'), "dblclick", 
  		  	function() {
				changeSizeHist() ;
  		  	}) ;
    	
    	var oldaction = document.forms[0].action ;
    	document.forms[0].action = 'javascript:isExistSurOperation()';
    	function isExistSurOperation() {
    		 
    		HospitalMedCaseService.findDoubleOperationByPatient($('id').value,$('medCase').value,$('medService').value, $('operationDate').value
    		, {
                   callback: function(aResult) {
                   		//alert(aResult);
                      if (aResult) {
				    		showMedServiceDouble(aResult) ;
                       } else {
                       		document.forms[0].action = oldaction ;
				    		document.forms[0].submit() ;
                       }
                   }
	        	}
	        	);
    	}
    		
    	//]]>
    	</script>
    	
  </msh:ifFormTypeIsNotView>
  
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm" guid="8b68bb61-7bc0-425e-b09c-7361891144b1">
    	  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
    	  <script type="text/javascript">
    	  	$('numberInJournal').focus() ;
    	  	$('numberInJournal').select() ;
    	  </script>
    	  </msh:ifNotInRole>
    	  <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
    	  <script type="text/javascript">
    	  	$('operationDate').focus() ;
    	  	$('operationDate').select() ;
    	  </script>
    	  </msh:ifInRole>
      <script type="text/javascript">
      departmentAutocomplete.setParentId($('lpu').value) ;
  	var oldValue = $('operationDate').value ;
  	try {
  		if (operationAutocomplete) operationAutocomplete.setParentId($('operationDate').value) ;
	} catch(e) {
		
	}
  	
  	eventutil.addEventListener($('operationDate'), "change", 
  	function() {
  		changeParentMedService() ;
  	}) ;
  	eventutil.addEventListener($('operationDate'),'blur',function(){
  		
  		if (oldValue!=$('operationDate').value) {
  			changeParentMedService() ;
  			if ($('operationDateTo').value=="") {
	  			$('operationDateTo').value=$('operationDate').value ;
	  		}
  		}
  	}) ;
  	/*eventutil.addEventListener($('operationDate'),'blur',function(){
  		if (oldValue!=$('operationDate').value) {
  			changeParentMedService() ;

  		}
  		
  	}) ;*/

  	function changeParentMedService() {
  		
  		
  		try {if (operationAutocomplete) {
  			var oper = $('operation').value ;
  			operationAutocomplete.setParentId($('operationDate').value) ;
  			operationAutocomplete.setVocId(oper) ;
  		}} catch(e) {
  			
  		}
  		var medService = $('medService').value ;
  		medServiceAutocomplete.setParentId($('operationDate').value+'#'+$('surgeon').value+'#'+$('department').value) ;
  		medServiceAutocomplete.setVocId(medService) ;
  		
  		//$('operationName').value='' ;
  		oldValue = $('operationDate').value ;
  	} 
  	 surgeonAutocomplete.addOnChangeCallback(function() {
  		changeParentMedService() ;
  		});
  	 departmentAutocomplete.addOnChangeCallback(function() {
  		changeParentMedService() ;
  		});
  	changeParentMedService() ;
  	</script>
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsCreate formName="stac_surOperationForm">
    <script type="text/javascript">
    isAnesthesiaAutocomplete.addOnChangeCallback(function() {
    	anest() ;
    }) ;
    function anest(){
    	HospitalMedCaseService.getYesNo($('isAnesthesia').value,{
    	callback: function(aResult) {
          	 //alert(aResult);
             if (+aResult==1) {
            		$('anesthesiaName').style.display = "block" ;
              		$('anaesthetistName').style.display = "block" ;
            		$('anesthesiaTypeName').style.display = "block" ;
              } else {
              		$('anesthesiaName').style.display = "none" ;
              		$('anaesthetistName').style.display = "none" ;
              		$('anesthesiaTypeName').style.display = "none" ;
              }
          }
		});
    }
    	anest();
    </script>
            <style type="text/css">
            #anesthesiaLabel,#anaesthetistLabel, #isAnesthesiaLabel
            , #anesthesiaTypeLabel,#anaesthetistLabel, #isAnesthesiaTypeLabel
             {
                color: blue ;
            }
            #isAnesthesiaName,#anaesthetistName, #anesthesiaName
            , #anesthesiaTypeName {
                background-color:#FFFFA0;
            }
        </style>
    </msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>

