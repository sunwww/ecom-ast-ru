<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    
    <msh:form method="post" guid="formHello" action="/entitySaveGoView-smo_short_spo_stream.do"  defaultField="dateStart" > 
    
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
            <msh:hidden property="otherTicketDates"/>
            <msh:ifFormTypeIsView formName="smo_short_spo_streamForm">
            <script type="text/javascript">
            
            window.document.location = 'entityPrepareCreate-smo_short_spo_stream.do?';
            </script>
            <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:row guid="e6d5b1ac-787d-4a77-b571-81f9ce83c8b8">
            <msh:checkBox property="noActuality" label="Недействительность" guid="816475aa-6ff1-4743-93b7-a6399addd548" />
          </msh:row>
          <msh:textField guid="textFieldHello" property="dateStart" label="Дата начала" />
          <msh:textField property="dateFinish" label="Дата окончания" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="2ff2ea54-5a8f-4338-92ec-ca877c4a7d34">
          <msh:label property="duration" label="Длительность" guid="eeedee49-a83e-4498-9d44-10be53474861" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="МКБ-10" guid="b7d15793-841e-4779-b7ee-b44ac6eb7f08" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="startFunction" label="Кто начал" guid="7b8a49e5-653a-4490-bfcf-ff42801611f7" fieldColSpan="3" horizontalFill="true" />
        <msh:row guid="b7488cc8-933c-406b-8589-8b852f78765e">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="finishFunction" label="Кто завершил" guid="d30a17e6-b833-47e6-9699-417dce4cd008" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="976b032e-82b6-45c9-88ca-935b07a6b482">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="ownerFunction" label="Владелец" guid="1d81b4df-9cdc-40f1-b496-a10a3a5080e0" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
       
       
      </msh:panel>
      </msh:ifFormTypeIsView>
      <msh:panel guid="panel">



        <msh:ifFormTypeIsNotView formName="smo_short_spo_streamForm">
        
        
        <msh:row>
          <msh:autoComplete  vocName="patient" property="patient" label="Пациент" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        
        <msh:row>
          <msh:autoComplete parentAutocomplete="patient"  vocName="medcardByPatient" property="medcard" label="Мед.карта" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        
        
         <msh:row>
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>

     

        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="smo_short_spo_streamForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
	        
		        <msh:row>
		          <msh:autoComplete parentAutocomplete="medcard" vocName="workFunctionByTicket" property="ownerFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
		        </msh:row>
	        
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" guid="e5ac1267-bc69-44b2-8aba-b7455ac064c4" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="workPlaceType" label="Место обслуживания" horizontalFill="true" guid="18063077-15e8-4e4a-8679-ff79de589a72" />
        </msh:row>

        
        <msh:row guid="6d8642e8-756a-482f-a561-a9b474ef6c50">
          <msh:autoComplete vocName="vocReason" property="visitReason" label="Цель посещения" horizontalFill="true"  />
          <msh:autoComplete vocName="vocVisitResult" property="visitResult" label="Результат обращения" horizontalFill="true" />
        </msh:row>
        <msh:row guid="16f1e99-4017-4385-87c1-bf5895e2">
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" guid="ddc10e76-8ee913984f" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        	<msh:ifFormTypeIsCreate formName="smo_short_spo_streamForm">
	        <msh:row>
	        	<msh:textField property="mkb" label="Код МКБ" />
	        </msh:row>
	        </msh:ifFormTypeIsCreate>
        </msh:ifInRole>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true" guid="9818fb43-33d1-4fe9-a0b4-2b04a9eee955" />
        </msh:row>
        <msh:row guid="0489132a-531c-47bc-abfc-1528e774bbfe">
          <msh:textField property="concludingDiagnos" label="Диагноз" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="4bd126b5-2316-42c4-bcb7-ccf5108b2c27">
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" guid="bf850705-5557-438e-b56e-33d59b1618e4" />
          <msh:autoComplete vocName="vocTraumaType" property="concludingTrauma" label="Травма" horizontalFill="true" guid="eedb1042-1861-426e-a0ec-6151c3933dd1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Ambulance">
	        <msh:row>
	        	<msh:autoComplete vocName="vocAmbulance" property="ambulance" label="Бригада СП" horizontalFill="true" />
	        	<msh:autoComplete vocName="vocVisitOutcome" property="visitOutcome" label="Исход СП" horizontalFill="true" />
	        </msh:row>
        </msh:ifInRole>
        <msh:row guid="7dfb3b2c-407d-48f1-9e70-76cb3328f5f5">
        	<msh:autoComplete property="mkbAdc" vocName="vocMkbAdc" parentAutocomplete="idc10" label="Доп.код"/>
        	<msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        <msh:row>
	   	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medServices" vocName="medServiceForSpec" colSpan="6"/>
	    </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Соп. заболевания" property="concomitantDiseases" colSpan="6" guid="1204d6c4-a3ff-44aa-a698-b99816d10337" />
        </msh:row>
        <msh:row>
          <msh:textField label="Дата начала" property="dateStart" fieldColSpan="1" /><%-- 
          <msh:textField label="Дата окончания" property="dateFinish" fieldColSpan="1"/> --%>
        
        	<td><input type="button" value="Добавить дату" onclick="checkIsHoliday()"/></td>
        </msh:row>
        <msh:row>
        <table id="otherDates">
       
        </table>
        </msh:row>
        
     <!--   <input id="cancelButton" type="button" value="Отменить" title="Отменить изменения [SHIFT+ESC]" onclick="this.disabled=true; window.history.back()">
       <input id="submitButton" class="default" type="button" title="Создать [CTRL+ENTER]" onclick="this.value='Создание ...'; this.disabled=true; this.form.submit(); return true ;" value="Создать" autocomplete="off">  -->
      
      <input id="submitButton" class="default" type="button" title="Создать [CTRL+ENTER]" onclick="this.form.submit(); sclear();" value="Создать" autocomplete="off">
      	
      	
       </msh:ifFormTypeIsNotView>
	    
        
      </msh:panel>
     </msh:form> 
  <!--  </form> -->

  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_short_spo_streamForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_short_spo_streamForm" guid="625411c7-8b7e-4d5b-acae-4fe679e24094">
      <msh:sideMenu guid="sideMenu-123" title="СПО">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-smo_short_spo" name="Изменить" roles="/Policy/Mis/MedCase/Spo/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-smo_short_spo" name="Удалить" roles="/Policy/Mis/MedCase/Spo/Delete" />

		<msh:sideLink params="id" action="/js-smo_spo-reopenSpo" name="Открыть СПО" title="Открыть СПО" confirm="Открыть СПО?" key="ALT+4" roles="/Policy/Mis/MedCase/Spo/Reopen" />
		<msh:sideLink params="id" action="/js-smo_spo-closeSpo" name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" guid="d84659f7-7ea9-4400-a11c-c83e7d5c578d" key="ALT+5" roles="/Policy/Mis/MedCase/Spo/Close" />
      	<tags:mis_choiceSpo hiddenNewSpo="1" method="unionSpos" methodGetPatientByPatient="getOpenSpoBySmo" service="TicketService" name="moveVisit"  roles="/Policy/Mis/MedCase/Visit/MoveVisitOtherSpo" title="Объединить с другим СПО" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="fbdebbf4-8006-4417-b7df-f23dcf298f62">
        <%-- <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_case" name="Нетрудоспособность" title="Добавить случай нетрудоспособности" guid="ae605283-4519-488c-9d9e-715d1978def2" /> --%>
      </msh:sideMenu>
      <msh:sideMenu title="Администрирование">
	   	<tags:mis_changeServiceStream name="CSS" title="Изменить поток обслуживания" roles="/Policy/Mis/MedCase/Visit/ChangeServiceStream" />
      	
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
 <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
 <msh:ifFormTypeIsCreate formName="smo_short_spo_streamForm">
 <script type="text/javascript">
 //medcardAutomplete.addOnChangeCallback(function (){updateMedcard();});
 //alert ('hello');
 document.forms[0].target="_blank";
 self.close();
 
 function updateMedcard () {
	 ownerFunctionAutomplete.setParentId($('medcard').value);
 }
  	if (+$('ownerFunction').value<1) {
	  	TicketService.getSessionData( {
		   		callback: function(aResult) {
		   			//alert(aResult) ;
		   			if (aResult!=null&&aResult!="") {
		   				var val = aResult.split("@") ;
		   	   			if (val[0]!="") $('dateStart').value = val[0] ;
		   	   			if (val[1]!="") $('ownerFunction').value=val[1] ;
		   	   			if (val[2]!="") $('ownerFunctionName').value=val[2];
		   	   			if (val[4]!=""&&(+val[4]>0)) $('emergency').checked=true;
		   	   			
		   	   		}
	   			}
	   		
	  	}) ;
  	}
  	</script>
 
<script type="text/javascript"> 
 function sclear(){
	 
	  alert("Сохранено!");
	
	  while(otherDates.firstChild) {
		  otherDates.removeChild(otherDates.firstChild);
		}
	  
	  ///$('patient').autocomplete('close').val('');
	 // patient.value = '';
	 // patientName.value = '';
	  
 }
 </script>
 </msh:ifFormTypeIsCreate>
 	<msh:ifFormTypeIsNotView formName="smo_short_spo_streamForm">
      	<script type="text/javascript"> 
        onload=function(){
        	if ($('otherTicketDates').value!=null&&$('otherTicketDates').value!='') {
        		var arr = $('otherTicketDates').value.split(":");
        		for (var i=0;i<arr.length;i++) {
        			addRow(arr[i]);
        		}
        	}
        }
        
        function checkIsHoliday () {
        	  var aDate = $('dateStart').value;
        	TicketService.isHoliday(aDate, {
        		callback: function (a) {
        			if (+a==1) {
        				alert ("У вас стоит запрет на создание визита в воскресенье.");
        				return;
        			} else {
        				addOtherDate(aDate);
        			}
        		}
        	});
        }
        
        function addOtherDate(otherDate) {
          
            if (otherDate!='') {
            TicketService.getCrossSPO(otherDate,$('patient').value,$('ownerFunction').value, {
            callback: function(aResult) {
            if (aResult!=null&&aResult!='') {
            var arr = aResult.split(':');
            if ($('parent')!=null&&$('parent').value!=arr[0]) {
          if (confirm('Выбранная дата('+otherDate+') пересекается с закрытым СПО (Период с '+arr[1]+' по '+arr[2]+'). Продолжить?')) {
          } else {
            return;
          }
          }
            }
            var dates = document.getElementById('otherDates').childNodes;
                  var l = dates.length;
                  for (var i=1; i<l;i++) {
                 	 if (otherDate==dates[i].childNodes[0].innerHTML) {
                 		 alert ("Такая дата ("+otherDate+") уже есть в обращении");
                  	return;
                    }                 
                 }
                  addRow (otherDate);
            }
            });      
            }      
         }

      	function createOtherDates() {
      		var dates = document.getElementById('otherDates').childNodes;
      		var str = ""; $('otherTicketDates').value='';
      		for (var i=1;i<dates.length;i++) {
      			str+=dates[i].childNodes[0].innerHTML+":";
      		}
      		str=str.length>0?str.trim().substring(0,str.length-1):"";
      		$('otherTicketDates').value=str;
      		TicketService.saveSession($('dateStart').value,$('ownerFunction').value
    	   			,$('ownerFunctionName').value,$('medServices').value,$('emergency').checked, {
    	   		callback: function(aResult) {
    	   			
    	   		}
    	   	});
      	}
      	function addRow (aValue) {
      		var table = document.getElementById('otherDates');
      		var row = document.createElement('TR');
      		var td = document.createElement('TD');
      		var tdDel = document.createElement('TD');
      		table.appendChild(row);
      		row.appendChild(td);
      		td.innerHTML=""+aValue;
      		row.appendChild(tdDel);
      		tdDel.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);createOtherDates()' value='Удалить' />";
      		createOtherDates();
      	}
      	function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				$(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
      	
      	idc10Autocomplete.addOnChangeCallback(function() {
    		setDiagnosisText('idc10','concludingDiagnos') ;
    		if (($('idc10Name').value!='') &&($('idc10Name').value.substring(0,1)=='Z')) {
	      	 	TicketService.findProvReason($('visitReason').value,{
	      	 		callback: function(aResult) {
	      	 			var ind = aResult.indexOf('#') ;
	      	 			if (ind!=-1) {
	      	 				$('visitReason').value=aResult.substring(0,ind) ;
	      	 				$('visitReasonName').value=aResult.substring(ind+1) ;
	      	 			}
	      	 		}
	      	 	}) ;
	      	 }
	    });
    </script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

