<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-doc_flow.do" defaultField="placeFromAutocomplete">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
        	<msh:autoComplete property="placeFrom" size="100" label="Отправлено из" fieldColSpan="3"  vocName="vocFlowDocmentPlace"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="placeTo" label="Отправлено в" fieldColSpan="3" horizontalFill="true" vocName="vocFlowDocmentPlace"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Описание документа" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="year" label="Год рождения"/>
        	<msh:textField property="lastname" label="Фамилия"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="firstname" label="Имя"/>
        	<msh:textField property="middlename" label="Отчество"/>
        </msh:row>
        <msh:row>
        <td colspan="4"><div id="dvListPatients"/></td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="patient" label="Пациент" fieldColSpan="3" horizontalFill="true" vocName="patient"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="slsByPatient" parentAutocomplete="patient" property="medCase" label="История болезни" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="medcard" parentAutocomplete="patient" label="Мед.карта" fieldColSpan="3" horizontalFill="true" vocName="medcardByPatient"/>
        </msh:row>
        <msh:row>
        	<ecom:checkGroup  label="Вид" tableName="VocFlowDocumentType" tableField="name" tableId="id" property="type"/>
        </msh:row>
        
        
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <div id="divViewResult"></div>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="doc_flowForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
       <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
       <msh:ifFormTypeIsCreate formName="doc_flowForm">
       <script type="text/javascript">
			try {
			  	var old_action = document.forms["mainForm"].action ; 
			  	document.forms["mainForm"].action="javascript:createFlowDocumentByPatient()" ; 
			  	
			} catch(e) {}
		</script>
       </msh:ifFormTypeIsCreate>
<script type="text/javascript">
function deleteFlowDoc(aId) {
		PatientService.deleteDataByFlowDoc(aId,3, { 
            callback: function(aResult) {
            	getFlowDocumentByPatient(1);
            }
		}); 
	}
function getListPatients() {
	PatientService.getPatients($('lastname').value,$('firstname').value,$('middlename').value,$('year').value, { 
        callback: function(aResult) {
        	$('dvListPatients').innerHTML=aResult;
        }
	}); 
}
function checkPatient(val) {
	if (val!=null && val!="") {
		var v = val.split("#") ;
		$('patient').value = v[0] ;
		$('patientName').value = v[1] ;
		$('medCase').value="" ;
		$('medcard').value="" ;
		$('medCaseName').value="" ;
		$('medcardName').value="" ;
		getFlowDocumentByPatient(1);
	}
}

function getFlowDocumentByPatient(aViewType) {
		
		PatientService.getFlowDocumentByPatient($('patient').value,$('medCase').value
				,$('medcard').value,$('type').value, aViewType ,{
            callback: function(aResult) {
            	$('divViewResult').innerHTML=aResult ;
            }
        	});
		
	}
	function createFlowDocumentByPatient() {
		PatientService.createFlowDocumentByPatient($('placeFrom').value, $('placeTo').value
			,$('patient').value,$('medCase').value, $('medcard').value,$('type').value
			, {
            callback: function(aResult) {
            	$('divViewResult').innerHTML=aResult ;
            	$('submitButton').disabled=false ;
            	$('submitButton').value='Создать' ;
            }
        	});
	}
	patientAutocomplete.addOnChangeCallback(function() {
		$('medCase').value="" ;
		$('medcard').value="" ;
		$('medCaseName').value="" ;
		$('medcardName').value="" ;
		getFlowDocumentByPatient(1);
		
    });
	medCaseAutocomplete.addOnChangeCallback(function() {
		getFlowDocumentByPatient(1);
    });
	medcardAutocomplete.addOnChangeCallback(function() {
		getFlowDocumentByPatient(1);
    });
	var l = document.getElementsByName('typeTemp');
	var text = '';
	if (l.length>0) {
		for (var i=0;i<l.length;i++)
			{
			eventutil.addEventListener(l[i],'change',function(){getFlowDocumentByPatient(1)}) ;
    		}
	}
	eventutil.addEventListener($('year'),'change',function(){getListPatients(1)}) 
	eventutil.addEventListener($('lastname'),'change',function(){getListPatients(1)}) 
	eventutil.addEventListener($('firstname'),'change',function(){getListPatients(1)}) 
	eventutil.addEventListener($('middlename'),'change',function(){getListPatients(1)}) 
    </script>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_flowForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" action="/entityPrepareCreate-doc_flow" name="Создать" roles="/Policy/Mis/Document/Flow/Create" />
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-doc_flow" name="Изменить" roles="/Policy/Mis/Document/Flow/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-doc_flow" name="Удалить" roles="/Policy/Mis/Document/Flow/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
      <msh:sideMenu title="Журналы">
      	<msh:sideLink name="Журнал документов для отправления" action="/doc_flow_sending.do"/>
      	<msh:sideLink name="Журнал документов для получения" action="/doc_flow_receit.do"/>
      	<msh:sideLink name="Журнал отслеж. документов" action="/doc_flow_backtrace.do"/>
      </msh:sideMenu>
  </tiles:put>
</tiles:insert>

