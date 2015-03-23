<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-smo_direction" 
    defaultField="orderLpuName" >
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:hidden property="infoByPolicy" />
      <msh:panel guid="panel" colsWidth="10%, 10%, 10%">
            	<msh:row>
      		<td colspan="4"><div id='medPolicyInformation' style="display: none;" class="errorMessage"/></td>
      	</msh:row>
        <msh:row guid="fa7ff4e9-4b3d-4402-b046-86283cf7938e">
          <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="orderLpu" label="Внешний направитель" guid="cbab0829-c896-4b74-9a68-c9f95676cc3b" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <tr>
          <td class="separator" colspan="4">
            <div class="h3">
              <h3>
                К специалисту:
                <msh:ifFormTypeIsNotView formName="smo_directionForm" guid="71ddfd0b-09a1-4cfe-bd83-3dc3738cb9d2">
                  <a href=" javascript:showTimeWorkCalendar('.do') ">расписание специалистов</a>
                </msh:ifFormTypeIsNotView>
              </h3>
            </div>
          </td>
        </tr>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunctionByDirect" property="workFunctionPlan" label="Куда" fieldColSpan="3" horizontalFill="true"  />
        </msh:row>
        <msh:row guid="row1">
          <msh:autoComplete vocName="vocWorkCalendarDayByWorkFunction" property="datePlan" label="Направлен на дату" guid="d7f4bef5-0f84-4d3c-b7d9-b7c7c5d51907" horizontalFill="true" parentAutocomplete="workFunctionPlan" />
          <msh:autoComplete vocName="vocWorkCalendarTimeWorkCalendarDay" property="timePlan" label="Время" guid="1d6b9712-62cc-4c67-a2d8-77bfef298ff3" parentAutocomplete="datePlan" 
          />
        </msh:row>
        <msh:row>
        	<ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Мед. услуги" 
	   		property="medServices" vocName="medServiceForSpec" colSpan="6" />
	 
	    </msh:row>
        <msh:ifFormTypeIsNotView formName="smo_directionForm">
        <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/PreRecord">
        <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
          <msh:separator label="Предварительная запись" colSpan="4" guid="314f5445-a630-411f-88cb-16813fefa0d9" />
        </msh:row>
        <msh:row>
        	<td colspan="4" id="tdPreRecord"></td>
        </msh:row>
        </msh:ifInRole>
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
        <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
          <msh:separator label="Дополнительные параметры (необходимы для справки о стоимости МО)" colSpan="4" guid="314f5445-a630-411f-88cb-16813fefa0d9" />
        </msh:row>
        <msh:row guid="de2d6415-7834-4d4a-934b-c4740cb28b6c">
          <msh:autoComplete showId="false" vocName="vocServiceStream" property="serviceStream" viewOnlyField="false" label="Поток обслуживания" guid="58d43ea6-3555-4eaf-978e-f259920d179c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="a970fa32-6038-4e0b-a0a8-c57b5ebd3a04">
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" viewOnlyField="false" label="Рабочее место" guid="c61023b1-bf59-432f-8764-8a571b1eddf8" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="16db3ed2-4536-4a64-9cac-b73390bf65d6">
          <msh:autoComplete showId="false" vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" guid="5f53c276-d7de-423a-86f5-b523ed37e75d" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель" 
        	parentId="smo_directionForm.patient" viewAction="entityParentView-mis_kinsman.do" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="69487e8f-d832-47f8-b486-dd2cd97d11ca">
          <msh:autoComplete parentId="smo_directionForm.patient" viewAction="entitySubclassView-mis_medCase.do" vocName="vocOpenedSpoByPatient" property="parent"  label="СПО" guid="5c46703a-e901-4c07-9426-10bc2ca3f5df" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
            <msh:label property="username" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_directionForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/MedService/Create">
      	<msh:section title="Услуги" 
      	createUrl="entityParentPrepareCreate-smo_direction_medservice.do?id=${param.id}" 
      	createRoles="/Policy/Mis/MedCase/MedService/Create">
      		<ecom:webQuery name="services" 
      		nativeSql="select mc.id,ms.name,mc.medServiceAmount
      		from MedCase mc 
      		left join MedService ms on mc.medService_id=ms.id
      		where mc.parent_id='${param.id}' and mc.dtype='ServiceMedCase'
      		"/>
      		<msh:table name="services" action="entityParentView-smo_direction_medservice.do"
      		deleteUrl="entityParentDeleteGoParentView-smo_direction_medservice.do" 
      	 	 viewUrl="entityParentView-smo_direction_medservice.do?short=Short" idField="1" >
      			<msh:tableColumn columnName="Название услуги" property="2"/>
      			<msh:tableColumn columnName="Кол-во" property="3"/>
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
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_directionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_directionForm" guid="22796a9c-c0f8-46e4-b0de-02e2f7d3472b">
      <msh:sideMenu guid="sideMenu-123" title="Направление">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-smo_direction" name="Изменить" roles="/Policy/Mis/MedCase/Direction/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_direction" name="Удалить" roles="/Policy/Mis/MedCase/Direction/Delete" />
        
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Create"  key="CTRL+1"
    	name="Направление &larr;"   action="/javascript:goNewDirection('.do')"  
    	 title='Направление'  />
         <msh:sideLink roles="/Policy/Mis/MedCase/Direction/MedService/Create" key="ALT+8" name="Услугу" params="id" 
         	action="/entityParentPrepareCreate-smo_direction_medservice" title="Добавить услугу" guid="df23d" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="SHIFT+8" params="id" action="/print-pat.do?s=PatientPrintService&amp;m=printInfoByMedcase" name="Сведений о пациенте" title="Печать сведений о пациенте" guid="783bad66-e5a6-44a8-9046-23921d00121e" roles="/Policy/Mis/Patient/PrintInfoPatient" />
      	
      	<msh:sideLink params="id" action="/print-vis_ticket.do?s=VisitPrintService&amp;m=printTalon1" name="Талона" title="Печать талона"  roles="/Policy/Mis/MedCase/Direction/Print" />
      	<msh:sideLink params="id" action="/print-vis_ticket2.do?s=VisitPrintService&amp;m=printTalon1" name="Талона (верх. часть)" title="Печать талона (верх.часть)"  roles="/Policy/Mis/MedCase/Direction/Print" />
      	<msh:sideLink params="id" action="/print-vis_ticket1.do?s=VisitPrintService&amp;m=printTalon1" name="Повторного талона" title="Печать повторного талона"  roles="/Policy/Mis/MedCase/Direction/Print1" />
      	<msh:sideLink 
    		name="Справка о стоимости в формате A4" action='.javascript:printReference("",".do")' title='Печать справки о стоимости в формате A4'
    	/>
      	<msh:sideLink 
    		name="Cправка о стоимости в формате A5" action='.javascript:printReference("_f5",".do")' title='Печать справки о стоимости в формате A4'
    	/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsView formName="smo_directionForm">
  	<script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
        <script type="text/javascript">//var theBedFund = $('bedFund').value;
        function printReference(aFormat) {
    		TicketService.getDataByReference(
    			'${param.id}','PREVISIT',{
    				callback: function(aResult) {
    					if (aResult!=null) {
    						window.location.href = "print-doc_reference"+aFormat+".do?medCase=${param.id}&m=refenceSMO&s=VisitPrintService"+aResult;
    						
    					}
    				}, errorHandler: function(aMessage) {
    					if (aMessage!=null) {
    						alert(aMessage);
    					} else {
    				    	alert("Ошибка в обработке данных!!!") ;
    					}
    				}
    			
    			}
    		);
    		
    	}
      	function goNewDirection() {
      		window.location = 'entityParentPrepareCreate-smo_direction.do?id='+$('patient').value+"&orderLpu="+$('orderLpu').value+"&tmp="+Math.random() ;
      	}
      	</script>
    </msh:ifFormTypeIsView>
    <script type="text/javascript">//var theBedFund = $('bedFund').value;
		if ($('infoByPolicy').value.length>0) {
			$('medPolicyInformation').innerHTML = $('infoByPolicy').value + " <u>Направление к врачу по потоку обслуживания ОМС создаваться не будет!!!</u>";
			$('medPolicyInformation').style.display = 'block' ;
		} else {
			$('medPolicyInformation').style.display = 'none' ;
		}
      	</script>
    <msh:ifFormTypeIsCreate formName="smo_directionForm">
    	<script type="text/javascript">
    		if ((+'${param.orderLpu}'>0) && (+$('orderLpu').value==0)) {
    			orderLpuAutocomplete.setVocId('${param.orderLpu}') ;
    		}
    	</script>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsNotView formName="smo_directionForm" guid="0cfa71af-92f6-432b-b592-483a2c92429d">
      <script type="text/javascript">
      //new dateutil.DateField($('datePlanName'));
      //new timeutil.TimeField($('timePlanName'));
      var oldaction = document.forms[0].action ;
      document.forms[0].action = 'javascript:isExistTicket()';
      if (theOtmoa_medServices) theOtmoa_medServices.setParentId($("workFunctionPlan").value+"#"+$("datePlanName").value) ;
      function isExistTicket() {
 		 if (!$('emergency').checked) {
 			 WorkCalendarService.checkPolicyByPatient($('patient').value,
 					 $('datePlanName').value,$('serviceStream').value
 					 ,
 			 {
 				 callback: function(aResult) {
 					 if (+aResult<1) {
 							checkDouble();
 				  	  } else {
 				  		  alert('У Вас стоит запрет на запись пациентов по ОМС без полиса!!!');
 				  		document.forms[0].submitButton.disabled = false ;
 				  		  
 				  	  } 
 					 }
 				 });
 		  } else {
 			 checkDouble();
 		  }
 		 
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
  		updateDefaultDate() ;
  		
  		}) ;
      datePlanAutocomplete.addOnChangeCallback(function(){
    	  getPreRecord() ;
    	  if (theOtmoa_medServices) theOtmoa_medServices.setParentId($("workFunctionPlan").value+"#"+$("datePlanName").value) ;
  		}) ;
    function checkRecord(aId,aValue) {
    	$('timePlan').value = aId; 
    	$('timePlanName').value = aValue ;
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
  					if (theOtmoa_medServices) theOtmoa_medServices.setParentId($("workFunctionPlan").value+"#"+$("datePlanName").value) ;
  					if (theOtmoa_medServices) theOtmoa_medServices.clearData() ; ;
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
  	  			  			}
  	  		  			}
  	  		  			) ;
  	  			} else {
  	  				$('tdPreRecord').innerHTML="";
  	  			}
  		}
	}
  		</script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

