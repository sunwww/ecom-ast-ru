<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-smo_direction" defaultField="orderLpuName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:hidden property="infoByPolicy" />
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Direction/CreateDirectionOnCourseTreatment">
        <msh:hidden property="countDays"/>
      </msh:ifNotInRole>
      <msh:panel colsWidth="10%, 10%, 10%">
            	<msh:row>
      		<td colspan="4"><div id='medPolicyInformation' style="display: none;" class="errorMessage"/></td>
      	</msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" horizontalFill="true" fieldColSpan="3"/>
          </msh:row><msh:row>
            <msh:textField property="orderDate" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocServiceStream" property="serviceStream" viewOnlyField="false" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
       
        <msh:row>
          <msh:autoComplete  vocName="guaranteeByPatient" parentId="smo_directionForm.patient" property="guarantee" label="Гарантийное письмо" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        
        <tr>
          <td class="separator" colspan="4">
            <div class="h3">
              <h3>
                К специалисту:
                <msh:ifFormTypeIsNotView formName="smo_directionForm">
                  <a href=" javascript:showTimeWorkCalendar('.do') ">расписание специалистов</a>
                </msh:ifFormTypeIsNotView>
              </h3>
            </div> 
          </td>
        </tr>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" fieldColSpan="3" horizontalFill="true"  />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" horizontalFill="true" parentAutocomplete="workFunctionPlan" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" parentAutocomplete="datePlan" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/CreateDirectionOnCourseTreatment">
         <msh:row> 
        <msh:textField property="countDays"  label="Кол-во дней записи" />
        </msh:row>
        </msh:ifInRole>
        <msh:row>
        	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги"
	   		property="medServices" vocName="medServiceForSpec" colSpan="6"  />
	 
	    </msh:row>
        <msh:ifFormTypeIsNotView formName="smo_directionForm">
        <tr><td colspan="10"><table><tr><td valign="top"><table>
        <msh:row>
          <msh:separator label="Резервы" colSpan="4" />
        </msh:row>
        <msh:row>
        	<td colspan="4">
        	<div id="divReserve"></div>
        	</td>
        </msh:row></table>
        </td><td valign="top"><table>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/PreRecord">
        <msh:row>
          <msh:separator label="Предварительная запись" colSpan="4" />
        </msh:row>
        <msh:row>
        	<td colspan="4" id="tdPreRecord"></td>
        </msh:row>
        </msh:ifInRole></table>
        </td></tr></table></td></tr>
        
        <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/CreateNewTime">
		        <msh:row>
		        	<msh:separator label="<a href='javascript:getWorkFunctionByUsername()'>Создание дополнительного времени</a>" colSpan="4"/>
		        </msh:row>
		        <msh:row>
		        	<td colspan="4" id="tdCreateNewTime">
		        		
		        		<div id="workFunctionByUsername">
		        			
		        		</div>
		        	</td>
		        </msh:row>
        </msh:ifInRole>
        </msh:ifFormTypeIsNotView>
        <msh:row>
          <msh:separator label="Дополнительные параметры" colSpan="4" />
        </msh:row>

        <msh:row>
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" viewOnlyField="false" label="Рабочее место" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" 
        	parentId="smo_directionForm.patient" viewAction="entityParentView-mis_kinsman.do" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete parentId="smo_directionForm.patient" viewAction="entitySubclassView-mis_medCase.do" vocName="vocOpenedSpoByPatient" property="parent"  label="СПО" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        <msh:checkBox property="isPaid"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
            <msh:label property="username" label="пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь" />
        </msh:row>
        
        <msh:submitCancelButtonsRow colSpan="3" functionSubmit="save();"/>
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_directionForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/MedService/Create">
      	<msh:section title="Услуги" 
      	createUrl="entityParentPrepareCreate-smo_direction_medservice.do?id=${param.id}" 
      	createRoles="/Policy/Mis/MedCase/MedService/Create">
      		<ecom:webQuery name="services"
      		nativeSql="select mc.id,ms.name,mc.medServiceAmount,mc.serviceComment
      		from MedCase mc 
      		left join MedService ms on mc.medService_id=ms.id
      		where mc.parent_id='${param.id}' and mc.dtype='ServiceMedCase'
      		"/>
      		<msh:table name="services" action="entityParentView-smo_direction_medservice.do"
      		deleteUrl="entityParentDeleteGoParentView-smo_direction_medservice.do" 
      	 	 viewUrl="entityParentView-smo_direction_medservice.do?short=Short" idField="1" >
      			<msh:tableColumn columnName="Название услуги" property="2"/>
      			<msh:tableColumn columnName="Кол-во" property="3"/>
                <msh:tableColumn columnName="Примечание" property="4"/>
      		</msh:table>
      	</msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    
    <msh:ifFormTypeIsNotView formName="smo_directionForm">
    	<tags:smo_direction_time name="Time" workFuncId="workFunctionPlan" calenDayId="datePlan" calenTimeId="timePlan" />
    	<tags:mis_double name='Ticket' title='Существующие направления в базе:' cmdAdd="document.forms[0].submitButton.disabled = false "/>
    	
    </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_directionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_directionForm">
      <msh:sideMenu title="Направление">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_direction" name="Изменить" roles="/Policy/Mis/MedCase/Direction/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_direction" name="Удалить" roles="/Policy/Mis/MedCase/Direction/Delete" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create"  key="CTRL+1"
    	name="Направление &larr;"   action="/javascript:goNewDirection('.do')"  
    	 title='Направление'  />
         <msh:sideLink roles="/Policy/Mis/MedCase/Direction/MedService/Create" key="ALT+8" name="Услугу" params="id" 
         	action="/entityParentPrepareCreate-smo_direction_medservice" title="Добавить услугу" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="SHIFT+8" params="id" action="/print-pat.do?s=PatientPrintService&amp;m=printInfoByMedcase" name="Сведений о пациенте" title="Печать сведений о пациенте" roles="/Policy/Mis/Patient/PrintInfoPatient" />
      	
      	<msh:sideLink params="id" action="/print-vis_ticket.do?s=VisitPrintService&amp;m=printTalon1" name="Талона" title="Печать талона"  roles="/Policy/Mis/MedCase/Direction/Print" />
      	<msh:sideLink params="id" action="/print-vis_ticket2.do?s=VisitPrintService&amp;m=printTalon1" name="Талона (верх. часть)" title="Печать талона (верх.часть)"  roles="/Policy/Mis/MedCase/Direction/Print" />
      	<msh:sideLink params="id" action="/print-vis_ticket1.do?s=VisitPrintService&amp;m=printTalon1" name="Повторного талона" title="Печать повторного талона"  roles="/Policy/Mis/MedCase/Direction/Print1" />

      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
      <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
  	<msh:ifFormTypeIsView formName="smo_directionForm">
  	
        <script type="text/javascript">//var theBedFund = $('bedFund').value;
        
      	function goNewDirection() {
      		window.location = 'entityParentPrepareCreate-smo_direction.do?id='+$('patient').value+"&orderLpu="+$('orderLpu').value+"&tmp="+Math.random() ;
      	}
      	</script>
    </msh:ifFormTypeIsView>
    <script type="text/javascript">//var theBedFund = $('bedFund').value;
		if ($('infoByPolicy').value.length>0) {
			WorkCalendarService.getChargedServiceStream({
				callback: function(a) {
					a = JSON.parse(a);
					$('serviceStream').value=a.id;
					$('serviceStreamName').value=a.name;
				}
			});
			$('medPolicyInformation').innerHTML = $('infoByPolicy').value + " <u>Направление к врачу по потоку обслуживания ОМС создаваться не будет!!!</u>";
			$('medPolicyInformation').style.display = 'block' ;
		} else {
			$('medPolicyInformation').style.display = 'none' ;
		}
      	</script>
    <msh:ifFormTypeIsCreate formName="smo_directionForm">
    	<script type="text/javascript">
            function testMe(el) {
                console.log("test me "+el);
                alert("test me "+el);
            }
    		if ((+'${param.orderLpu}'>0) && (+$('orderLpu').value==0)) {
    			orderLpuAutocomplete.setVocId('${param.orderLpu}') ;
    		}
    	</script>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsNotView formName="smo_directionForm">
        <msh:ifFormTypeAreViewOrEdit formName="smo_directionForm">
            <script type="text/javascript">
                theOtmoa_medServices.setParentId($("workFunctionPlan").value+"#"+$("datePlanName").value+"#"+$('serviceStream').value) ;
            </script>
        </msh:ifFormTypeAreViewOrEdit>
    <script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
      <script type="text/javascript">
      
   	 function updateTime() {
   		if (+$('datePlan').value>0 && +$('serviceStream').value>0) {
   			WorkCalendarService.getReserveByDateAndService($('datePlan').value,$('serviceStream').value,$('patient').value, {
                 callback: function(aResult) {
                     //alert(aResult) ;
                     $('divReserve').innerHTML = aResult ;
                 }
		    });
        }
     }

   	 serviceStreamAutocomplete.addOnChangeCallback(function() {
	 	updateTime() ;
	 	checkIfDogovorNeeded() ;
        setMedserviceAutocompleteParent();


     });

   	 //TODO Доделать выбор справочника исходя от источника финансирования
   	 function setMedserviceAutocompleteParent() {
   	     if (theOtmoa_medServices) {
   	         theOtmoa_medServices.setParentId($("workFunctionPlan").value+"#"+$("datePlanName").value+"#"+$('serviceStream').value) ;
             theOtmoa_medServices.clearData() ;
         }
     }

      var oldaction = document.forms[0].action ;
      document.forms[0].action = 'javascript:isExistTicket()';
      function isExistTicket() {
 		 if (!$('emergency').checked) {
 			 WorkCalendarService.checkPolicyByPatient($('patient').value,
 					 $('datePlanName').value,$('serviceStream').value
 					 ,
 			 {
 				 callback: function(aResult) {
 					 if (+aResult<1) {
 						checkServiceStreamInHospital();
 				  	  } else {
 				  		  alert('У Вас стоит запрет на запись пациентов по ОМС без полиса!!!');
 				  		document.forms[0].submitButton.disabled = false ;
 				  		  
 				  	  } 
 					 }
 				 });
 		  } else {
 			 checkServiceStreamInHospital();
 		  }
 		 
 		 }
      function checkServiceStreamInHospital () {
    	//  WorkCalendarService.getIsServiceStreamEnabled ($('patient').value, $('serviceStream').value, {
    	//	  callback: function (a) {
    	//		  if (a!=null&&a=='1') {
    	//			  alert ('Невозможно создать направление с указанным потоком обслуживания, т.к пациент лежит в стационаре. Измените поток обслуживания, либо создайте направление из листа назначений');
    	//			  document.forms[0].submitButton.disabled = false ;
    	//		  } else {
    				  checkDouble();
    	//		  }
    	//	  }
    	//  });
      }
      function checkDouble() {
    	  WorkCalendarService.findDoubleBySpecAndDate($('id').value,$('patient').value
	    			  ,$('workFunctionPlan').value, $('datePlan').value
	  		, {
	                 callback: function(aResult) {
	                 
	                    if (aResult) {
					    		showTicketDouble(aResult) ;
					    		//('Уже сделано направление на '+$('date').value+' к специалисту данному пациенту') ;
					    			 ;
	                     } else {
	                    	 document.forms[0].action = oldaction ;
					    	 document.forms[0].submit() ;
	                     }
	                 }
		        	}
		        	); 
      }
      if ((+'${param.time}')>0 && ((+$('timePlan').value)==0)) {
    	  WorkCalendarService.getDataByTime('${param.time}', {
    		  callback:function(aResult) {
    			  if (aResult!='') {
        			  var res=aResult.split('#') ;
        			  $('workFunctionPlan').value=res[0] ;
        			  $('workFunctionPlanName').value=res[1] ;
        			  $('datePlan').value=res[2] ;
        			  $('datePlanName').value=res[3] ;
        			  $('timePlan').value=res[4] ;
        			  $('timePlanName').value=res[5] ;
    			  }
    		  }
    	  }) ;
      } else if ((+'${param.spo}')>0 && ((+$('parent').value)==0)) {
    	  if ((+'${param.workFunction}')>0 && ((+$('workFunctionPlan').value)==0)) {
    		  WorkCalendarService.getInfoSpoAndWorkFunction('${param.spo}','${param.workFunction}',+'${param.serviceStream}',+'${param.visitReason}', {
        		  callback:function(aResult) {
        			  if (aResult!='') {
            			  var res=aResult.split('#') ;
            			  $('parent').value=res[0] ;
            			  $('parentName').value=res[1] ;
            			  $('workFunctionPlan').value=res[2] ;
            			  $('workFunctionPlanName').value=res[3] ;
            			  $('serviceStream').value=res[4] ;
            			  $('serviceStreamName').value=res[5] ;
            			  $('visitReason').value=res[6] ;
            			  $('visitReasonName').value=res[7] ;
            			  updateDefaultDate() ;
        			  }
        		  }
        	  }) ;
    	  } 
      }
	  	eventutil.addEventListener($('datePlanName'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
		  			if ($('datePlanName').value.length==2) $('datePlanName').value=$('datePlanName').value+"." ; 
		  			if ($('datePlanName').value.length==5) $('datePlanName').value=$('datePlanName').value+"." ; 
	  		  	}
	  	) ;
	  	eventutil.addEventListener($('timePlanName'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
		  		if ($('timePlanName').value.length==2) $('timePlanName').value=$('timePlanName').value+":" ; 
	  		  	}
	  	) ;
      
      workFunctionPlanAutocomplete.addOnChangeCallback(function(){
     //     WorkCalendarService.checkIsCanRecordAnyDate($('workFunctionPlan').value, {
     //         callback: function(ret) {
     //             canRecordAnyDate =ret.canRecordAnyDate;
     //             if (canRecordAnyDate) { //Спрячем раздел с выбором времени и даты из справочников
     //                 //Можно направлять на раб. функцию на любую дату. Прячем дату и время, показываем текстовое поле.
      //                jQuery('#')

      //            } else { //Всё как было
                      updateDefaultDate() ;
       //           }
       //       }
       //   });

  		
  		}) ;
      datePlanAutocomplete.addOnChangeCallback(function(){
    	  
    	  getPreRecord() ;
    	  if ($('timePlan')) {
    		  $('timePlan').value='';
    		  }
    	  
    	  if ($('timePlanName')) {
    		  $('timePlanName').value='';
    		  }
          setMedserviceAutocompleteParent();
  		}) ;

    function checkRecord(aId,aValue,aIdService,aService) {
    	$('timePlan').value = aId; 
    	$('timePlanName').value = aValue ;
    	if (+aIdService>0) {
    		$('serviceStream').value=aIdService;
    		$('serviceStreamName').value=aService;
    		
    	}
    }
    
    function getWorkFunctionByUsername() {
    	WorkCalendarService.getWorkFunctionByUsername($('workFunctionPlan').value,
      			{
      				callback:function(aDateDefault) {
      					try {
    	$("workFunctionByUsername").innerHTML="<a href='javascript:hideNewTime()'>Скрыть</a><br/>"+aDateDefault ;
      					} catch (e) {}
    				}
      			}) ;
    }

    function hideNewTime() {
    	try {$("workFunctionByUsername").innerHTML="" ;} catch (e) {}
    }
    function get10DaysByWorkFunction(aWorkFunction) {
    	WorkCalendarService.get10DaysByWorkFunction(aWorkFunction,
    			{callback:function(aResult){
    				$('divDayByWorkFunction').innerHTML = aResult ;
    			}}) ;
    }
    function getTimeByDayAndWorkFunction(aWorkFunction,aWorkCalendarDay) {
    	WorkCalendarService.getTimeByDayAndWorkFunction(aWorkFunction,aWorkCalendarDay,{
    		callback:function(aResult) {
    			$('divTimeByDayAndWorkFunction').innerHTML = aResult ;
    		}
    	}) ;
    }
    function addNewTimeBySpecialist(aWorkCalendarDay,aReserve,aDate,aWorkFunction,aTime1,aTime2,aWorkFunctionInfo) {
    	WorkCalendarService.addNewTimeBySpecialist(aDate,aReserve,aWorkFunction,aTime1,aTime2,{
    		callback:function(aResult) {
    			$('divTimeByDayAndWorkFunction').innerHTML = aResult ;
    			//getTimeByDayAndWorkFunction(aWorkFunction,aWorkCalendarDay) ;
    			hideNewTime() ;
    			$('workFunctionPlan').value=aWorkFunction ;
  			    $('workFunctionPlanName').value=aWorkFunctionInfo ;
  			    $('datePlan').value=aWorkCalendarDay ;
  			    $('datePlanName').value=aDate ;
  			    if (aResult!=null) {
					$('timePlanName').value =  aResult.substring(aResult.indexOf("#")+1) ; ;
					$('timePlan').value = aResult.substring(0,aResult.indexOf("#")) ;
  			    }
  			  	getPreRecord() ;
    			//saveTimeWorkCalendar("4","105","08:00","ТКАЧЕВА СВЕТЛАНА ПЕТРОВНА","26.03.2012","1");
    		}
    	}) ;
    }
    
  	function updateDefaultDate() {
  			WorkCalendarService.getDefaultDate($('workFunctionPlan').value,
  			{
  				callback:function(aDateDefault) {
  					if (aDateDefault!=null) {
  						//alert(aDateDefault) ;
	  					var calDayId, calDayInfo,ind1 ;
	  					ind1 = aDateDefault.indexOf("#") ;
  						calDayInfo = aDateDefault.substring(0,ind1) ;
  						calDayId = aDateDefault.substring(ind1+1) ;
  						
	  					$('datePlan').value=calDayId ;
				        $('datePlanName').value = calDayInfo;
				        getPreRecord();
  					} else {
	  					$('datePlan').value=0 ;
				        $('datePlanName').value = "";
				        getPreRecord();
	  				}
                    setMedserviceAutocompleteParent();

  				    getWorkFunctionByUsername() ;
	  			}
  			}
  			) ;
  			$('timePlan').value="0" ;
			$('timePlanName').value = "";
			 
  		}
  	function getPreRecord() {
  		
  		if ($('tdPreRecord')) {
  			
  			if (+$('datePlan').value>0) {
  	  			WorkCalendarService.getPreRecord($('datePlan').value,
  	  		  			{
  	  		  				callback:function(aResult) {
  	  		  					if (aResult!=null) {
  	  		  						$('tdPreRecord').innerHTML=aResult;
  	  		  					}
  	  			  				else {
  	  			  					$('tdPreRecord').innerHTML="";
  	  			  				}
  	  		  				
  	  		  					updateTime() ;
  	  		  					
  	  			  			}
  	  		  			}
  	  		  			) ;
  	  			} else {
  	  				$('tdPreRecord').innerHTML="";
  	  			}
  		} else {
  			updateTime() ;
  		}
	}
  	function hideGuaranteeDiv(hide) {
  		
  	}
  	hideGuaranteeDiv(true);

     function checkIfDogovorNeeded() {
         if (+$('serviceStream').value>0 && $('guaranteeName')) {
             ContractService.checkIfDogovorIsNeeded($('patient').value, $('serviceStream').value, null,$('datePlan').value,'POLYCLINIC', {
                 callback: function (letter) {
                     letter = JSON.parse(letter);
                     if (letter.status=="ok") {
                         console.log("2"+letter.guaranteeInfo);
                         if (letter.guaranteeInfo) { //нашли г. письмо
                             $('guarantee').value = letter.id;
                             $('guaranteeName').value = letter.guaranteeInfo;
                         }
                     } else {
                         showToastMessage(letter.errorName);
                     }
                     $('guaranteeName').disabled=true;
                 }
             });
         }

     }
      </script>
    </msh:ifFormTypeIsNotView>
      <script type="text/javascript">
          //вывести текстовые поля для ввода примечания
          function otmoaOnCnahge() {
              for (var i=0; i<100; i++) { //id при удалении будут идти не по порядку, но вряд ли больше 100
                  if(document.getElementById('otma_input_'+(i+1))) {
                      var tableRow=document.getElementById('otma_input_'+(i+1)).parentNode.parentNode;
                      if (tableRow.childNodes.length<4) {
                          var td = document.createElement('td');
                          <msh:ifFormTypeIsNotView formName="smo_directionForm">
                          td.innerHTML="<input type='text' id='otma_input_cmnt"+(i+1)+"' size='40' placeholder='Введите примечание'/>";
                          </msh:ifFormTypeIsNotView>
                          <msh:ifFormTypeIsView formName="smo_directionForm">
                          td.innerHTML="<input type='text' disabled id='otma_input_cmnt"+(i+1)+"'  />";
                          </msh:ifFormTypeIsView>
                          tableRow.appendChild(td);
                      }
                  }
              }
          }

          //добавить событие в каждый autocomplete услуг
          function otmoaMasLink() {
              var otmoaMas = document.getElementsByClassName('manyToManyActionLink');
              for (var i=0; i<otmoaMas.length; i++)
                  otmoaMas[i].href = "javascript:otmoaOnCnahge();";
              otmoaMas = document.getElementsByClassName('autocomplete maxHorizontalSize');
              for (var i=0; i<otmoaMas.length; i++) {
                  if (otmoaMas[i].id.indexOf('otma_input_')!=-1) {
                      otmoaMas[i].onclick = function () {
                          otmoaOnCnahge();
                      };
                  }
              }
              setTimeout(otmoaMasLink,500);
          }
          //получить комментарий по названию услуги (увы, id не хранится)
          function getCmtByName(id) {
              var cmt='';
              var otmoaMas = document.getElementsByClassName('autocomplete maxHorizontalSize');
              for (var i=0; i<otmoaMas.length; i++) {
                  if (otmoaMas[i].id.indexOf('otma_input_')!=-1 && i==id) {
                      cmt=document.getElementById('otma_input_cmnt'+otmoaMas[i].id.replace('otma_input_','')).value;
                  }
              }
              return cmt;
          }
          //сохарнить комментарии в поле
          function save() {
              var list = JSON.parse($('medServices').value);
              for (var i = 0; i < list["childs"].length; i++) {
                  list["childs"][i].cmnt=getCmtByName(i);
              }
              $('medServices').value = JSON.stringify(list);
              document.forms[0].submit();
          }

          //загрузка комментариев
          function loadComments() {
              TicketService.getServiceComments(
                  '${param.id}', {
                      callback: function (res) {
                          if (res!=null && res!='[]') {
                              <msh:ifFormTypeIsNotView formName="smo_directionForm">
                              var otmoaMas = document.getElementsByClassName('autocomplete maxHorizontalSize');
                              </msh:ifFormTypeIsNotView>
                              <msh:ifFormTypeIsView formName="smo_directionForm">
                              var otmoaMas = document.getElementsByTagName('a');
                              </msh:ifFormTypeIsView>

                              var aResult = JSON.parse(res);
                              for (var j=0; j<otmoaMas.length; j++) {
                                      var flag=true;
                                      <msh:ifFormTypeIsView formName="smo_directionForm">
                                      flag=otmoaMas[j].href.indexOf('entityView-mis_medService.do')!=-1;
                                      </msh:ifFormTypeIsView>
                                      if (flag) {
                                          for (var i = 0; i < aResult.length; i++) {
                                              if (typeof aResult[i].cmnt!=='undefined') {
                                                  <msh:ifFormTypeIsNotView formName="smo_directionForm">
                                                  var val = otmoaMas[j].value;
                                                  </msh:ifFormTypeIsNotView>
                                                  <msh:ifFormTypeIsView formName="smo_directionForm">
                                                  var val = otmoaMas[j].text;
                                                  </msh:ifFormTypeIsView>
                                                  if (otmoaMas[j].id.indexOf('otma_input_') != -1 && val == aResult[i].name) {
                                                      document.getElementById('otma_input_cmnt' + otmoaMas[j].id.replace('otma_input_', '')).value = aResult[i].cmnt;
                                                  }
                                              }
                                          }
                                      }
                              }
                          }
                      }
                  }
              );
          }
          otmoaOnCnahge();
          otmoaMasLink();
          loadComments();
      </script>
  </tiles:put>
</tiles:insert>