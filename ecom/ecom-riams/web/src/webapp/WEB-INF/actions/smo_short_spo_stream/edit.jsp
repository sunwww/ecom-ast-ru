<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    
    <msh:form method="post" action="/entitySaveGoView-smo_short_spo_stream.do"  defaultField="dateStart" >
    
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
            <msh:hidden property="otherTicketDates"/>
            <msh:ifFormTypeIsView formName="smo_short_spo_streamForm">
            <script type="text/javascript">
            
            window.document.location = 'entityPrepareCreate-smo_short_spo_stream.do?';
            </script>
            <msh:panel>
        <msh:row>
          <msh:row>
            <msh:checkBox property="noActuality" label="Недействительность" />
          </msh:row>
          <msh:textField property="dateStart" label="Дата начала" />
          <msh:textField property="dateFinish" label="Дата окончания" />
        </msh:row>
        <msh:row>
          <msh:label property="duration" label="Длительность" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="МКБ-10" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="startFunction" label="Кто начал" fieldColSpan="3" horizontalFill="true" />
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="finishFunction" label="Кто завершил" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="ownerFunction" label="Владелец" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
       
       
      </msh:panel>
      </msh:ifFormTypeIsView>
      <msh:panel>



        <msh:ifFormTypeIsNotView formName="smo_short_spo_streamForm">
        
        
        <msh:row>
          <msh:autoComplete  vocName="patient" property="patient" label="Пациент" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        
        <msh:row>
          <msh:autoComplete parentAutocomplete="patient"  vocName="medcardByPatient" property="medcard" label="Мед.карта" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        
        
         <msh:row>
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" horizontalFill="true" fieldColSpan="3" />
        </msh:row>

     

        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="smo_short_spo_streamForm.medcard" vocName="kinsmanByTicket" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
	        
		        <msh:row>
		          <msh:autoComplete parentAutocomplete="medcard" vocName="workFunctionByTicket" property="ownerFunction" label="Специалист" fieldColSpan="3"  horizontalFill="true" />
		        </msh:row>
	        
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="workPlaceType" label="Место обслуживания" horizontalFill="true" />
        </msh:row>

        
        <msh:row>
          <msh:autoComplete vocName="vocReason" property="visitReason" label="Цель посещения" horizontalFill="true"  />
          <msh:autoComplete vocName="vocVisitResult" property="visitResult" label="Результат обращения" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Посещение в данном году по данному заболевания" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        	<msh:ifFormTypeIsCreate formName="smo_short_spo_streamForm">
	        <msh:row>
	        	<msh:textField property="mkb" label="Код МКБ" />
	        </msh:row>
	        </msh:ifFormTypeIsCreate>
        </msh:ifInRole>
        <msh:row>
          <msh:autoComplete vocName="vocIdc10" property="idc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="concludingDiagnos" label="Диагноз" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDispanseryRegistration" property="dispRegistration" label="Диспансерный учет" horizontalFill="true" />
          <msh:autoComplete vocName="vocTraumaType" property="concludingTrauma" label="Травма" horizontalFill="true" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/Ambulance">
	        <msh:row>
	        	<msh:autoComplete vocName="vocAmbulance" property="ambulance" label="Бригада СП" horizontalFill="true" />
	        	<msh:autoComplete vocName="vocVisitOutcome" property="visitOutcome" label="Исход СП" horizontalFill="true" />
	        </msh:row>
        </msh:ifInRole>
        <msh:row>
        	<msh:autoComplete property="mkbAdc" vocName="vocMkbAdc" parentAutocomplete="idc10" label="Доп.код"/>
        	<msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        <msh:row>
	   	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medServices" vocName="medServiceForSpec" colSpan="6"/>
	    </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vocIdc10" label="Соп. заболевания" property="concomitantDiseases" colSpan="6" />
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
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_short_spo_streamForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_short_spo_streamForm">
      <msh:sideMenu title="СПО">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-smo_short_spo" name="Изменить" roles="/Policy/Mis/MedCase/Spo/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-smo_short_spo" name="Удалить" roles="/Policy/Mis/MedCase/Spo/Delete" />

		<msh:sideLink params="id" action="/js-smo_spo-reopenSpo" name="Открыть СПО" title="Открыть СПО" confirm="Открыть СПО?" key="ALT+4" roles="/Policy/Mis/MedCase/Spo/Reopen" />
		<msh:sideLink params="id" action="/js-smo_spo-closeSpo" name="Закрыть СПО" title="Закрыть СПО" confirm="Закрыть СПО?" key="ALT+5" roles="/Policy/Mis/MedCase/Spo/Close" />
      	<tags:mis_choiceSpo hiddenNewSpo="1" method="unionSpos" methodGetPatientByPatient="getOpenSpoBySmo" service="TicketService" name="moveVisit"  roles="/Policy/Mis/MedCase/Visit/MoveVisitOtherSpo" title="Объединить с другим СПО" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <%-- <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_case" name="Нетрудоспособность" title="Добавить случай нетрудоспособности" /> --%>
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

