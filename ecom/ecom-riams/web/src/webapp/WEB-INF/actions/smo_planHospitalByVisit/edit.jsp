<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

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

    <msh:form action="/entityParentSaveGoView-smo_planHospitalByVisit.do" defaultField="phone" guid="137f576d-2283-4edd-9978-74290e04b873" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Create">
      <msh:panel guid="80209fa0-fbd4-45d0-be90-26ca4219af2e" colsWidth="15px,250px,15px">
        <msh:hidden property="id" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
        <msh:hidden property="visit" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
        <msh:hidden property="patient" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
        <msh:hidden property="saveType" guid="c409dfd8-f4e7-469f-9322-1982b666a087" />
        <msh:row>
        	<msh:textField property="phone" label="Телефон" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Параметры госпитализации" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderLpu" label="Направлен из ЛПУ" fieldColSpan="3" horizontalFill="true" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" horizontalFill="true"/>
        	
         </msh:row>
        
          
       
        <msh:row>
        	<msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentAutocomplete="department" property="bedType" label="Профиль коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedTypeByDep"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" label="Тип коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedSubTypeByDep"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="hospitalRoom" parentAutocomplete="department" label="Забронировано место в палате:" vocName="hospitalRoomByLpu" fieldColSpan="2" labelColSpan="2" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="dateFrom" label="с"/>
        	<msh:textField property="dateTo" label="по"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="cntDays" label="дней"/>
        </msh:row>

        <msh:row>
        	<msh:autoComplete property="idc10" vocName="vocIdc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField horizontalFill="true" property="diagnosis" fieldColSpan="3" label="Диагноз"/>
        </msh:row>
        <msh:ifFormTypeIsCreate formName="smo_planHospitalByVisitForm">
        <msh:row>
        	<td colspan="3" align="center">
        	<input type="button" onclick="getTextDiaryByMedCase(this);return false;" value="Вставить данные дневниковой записи"/>
        	</td>
        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row>
        	<msh:textArea property="comment" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        <msh:checkBox property="isOperation" label="Назначить операцию?"/>
        </msh:row>
        </msh:panel>
         <msh:panel styleId='createOperationDiv'> 
          <msh:row>
        	<msh:separator label="Назначение на операцию" colSpan="4"/>
        </msh:row>
		<msh:row>
    			<msh:autoComplete property="surgService" label="Исследование" vocName="surgicalOperations" horizontalFill="true" size="90" fieldColSpan="4" />
   		</msh:row>
   		<msh:row>
				 <msh:autoComplete property="surgCabinet" label="Операционная"  fieldColSpan="4" parentAutocomplete="surgService" vocName="operatingRoomsByMedService" size='20' horizontalFill="true" />
			</msh:row>
		<msh:row>
				 <msh:autoComplete property="surgCalDate" parentAutocomplete="surgCabinet" vocName="vocWorkCalendarDayByWorkFunction" label="Дата" size="10" fieldColSpan="1" />
			</msh:row>
			<msh:row>
    			 <msh:autoComplete property="surgCalTime" parentAutocomplete="surgCalDate" label="Время" vocName="vocWorkCalendarTimeWorkCalendarDay" fieldColSpan="1" />
    		</msh:row> 
    		 <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
         </msh:row>
         <tr><td colspan="10"><table><tr><td valign="top"><table>
        <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
          <msh:separator label="Резервы" colSpan="4" guid="314f5445-a630-411f-88cb-16813fefa0d9" />
        </msh:row>
        <msh:row>
        	<td colspan="4">
        	<div id="divReserve"></div>
        	</td>
        </msh:row></table>
        </td><td valign="top"><table>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/PreRecord">
        <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
          <msh:separator label="Предварительная запись" colSpan="4" guid="314f5445-a630-411f-88cb-16813fefa0d9" />
        </msh:row>
        <msh:row>
        	<td colspan="4" id="tdPreRecord"></td>
        </msh:row>
        </msh:ifInRole></table>
        </td></tr></table></td></tr>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:separator label="Фактическая госпитализация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="medCase" parentId="smo_planHospitalByVisitForm.patient" fieldColSpan="3" label="Госпитализация" vocName="slsByPatient" horizontalFill="true"
        	/>
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunction" label="Функция" guid="010e3a75-ba7e-45da-a82a-9c618a0ffcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
        
      </msh:panel>
     
        <msh:panel> 
		<msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />   
        </msh:panel>
    </msh:form>

</tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_planHospitalByVisitForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_planHospitalByVisitForm" guid="c7cae1b4-31ca-4b76-ab51-7f75b52d11b6">
      <msh:sideMenu title="Планирование госпитализаций" guid="edd9bfa6-e6e7-4998-b4c2-08754057b0aa">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_planHospitalByVisit" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_planHospitalByVisit" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-documentDirection1.do?m=printPlanHospital&s=VisitPrintService" name="Предварительной госпитализации"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="smo_planHospitalByVisitForm">
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection1.do?next=entityParentView-smo_visit.do__id="+$('visit').value+"&s=VisitPrintService&m=printPlanHospital&id=${param.id}" ;
      	}
  }
    printDocument();
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection1.do?next=entityParentView-smo_visit.do__id="+$('visit').value+"&s=VisitPrintService&m=printPlanHospital&id=${param.id}" ;
      	}
      }
    printDocument();
    </script>
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  <msh:ifFormTypeIsNotView formName="smo_planHospitalByVisitForm">
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
  	
  	<script type="text/javascript">
	
	surgCalDateAutocomplete.addOnChangeCallback(function(){
		 $('surgCalTime').value="" ;
		  $('surgCalTimeName').value="" ;
	  	  getPreRecord() ;
 	 });
	surgCabinetAutocomplete.addOnChangeCallback(function(){
		updateDefaultDate() ;
	}) ;
	function checkRecord(aId,aValue,aIdService,aService) {
    	$('surgCalTime').value = aId; 
    	$('surgCalTimeName').value = aValue ;
    
    }
  	function getPreRecord() {
  	  		
  	  		if ($('tdPreRecord')) {
  	  			
  	  			if ($('surgCalDate') && +$('surgCalDate').value>0) {
  	  	  			WorkCalendarService.getPreRecord($('surgCalDate').value,
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
  		
  		function updateTime() {
  	   		if (+$('surgCalDate').value>0 ) {
  	   			WorkCalendarService.getReserveByDateAndService($('surgCalDate').value,$('serviceStream').value,$('patient').value
  		    			  
  		  		, {
  		                 callback: function(aResult) {
  		                	 //alert(aResult) ;
  		                	 $('divReserve').innerHTML = aResult ;
  		                 }
  			        	}
  			        	); 
  	    }
  	   	}
  	
  	function updateDefaultDate() {
		WorkCalendarService.getDefaultDate($('surgCabinet').value,
		{
			callback:function(aDateDefault) {
				if (aDateDefault!=null) {
					//alert(aDateDefault) ;
					var calDayId, calDayInfo,ind1 ;
					ind1 = aDateDefault.indexOf("#") ;
					calDayInfo = aDateDefault.substring(0,ind1) ;
					calDayId = aDateDefault.substring(ind1+1) ;
					
					$('surgCalDate').value=calDayId ;
		        $('surgCalDateName').value = calDayInfo;
		        getPreRecord();
				} else {
					$('surgCalDate').value=0 ;
		        $('surgCalDateName').value = "";
		        getPreRecord();
				}
				
			    
			}
		}
		) ;
		$('surgCalTime').value="0" ;
	$('surgCalTimeName').value = "";
	 
	}
  	
  	eventutil.addEventListener($('isOperation'), 'click', function () {showTable('createOperationDiv', 'isOperation');}) ;
  	showTable('createOperationDiv', 'isOperation');
  	function showTable(aTableId, aCheckFld) {
    	//alert(aTableId+"--" + aCheckFld) ;
    	var aCanShow = $(aCheckFld).checked ;
		//try {
			//alert( aCanShow ? 'table-row' : 'none') ;
			$(aTableId).style.display = aCanShow ? 'block' : 'none' ;
		//} catch (e) {
			// for IE
			//alert(aCanShow ? 'block' : 'none') ;
		//	try{
		//	$(aTableId).style.display = aCanShow ? 'block' : 'none' ;
		//	}catch(e) {}
		//}	
	}
  	
  		//initPersonPatientDialog();
  		function getTextDiaryByMedCase(aElement) {
  			HospitalMedCaseService.getTextDiaryByMedCase(
					 $('visit').value,{
    			callback: function(aResult) {
    				$('comment').value=$('comment').value+"\n"+aResult ;
    				
    			}
    		}) ;
  			aElement.style.display="none" ;
  		} 
  		departmentAutocomplete.addOnChangeCallback(function() {
			HospitalMedCaseService.getDefaultBedTypeByDepartment(
					 $('department').value, $('serviceStream').value
      				, $('dateFrom').value,{
      			callback: function(aResult) {
      				var res = aResult.split('#') ;

      				if (+res[0]!=0) {
      					$('bedType').value = res[0] ; 
      					$('bedTypeName').value = res[1] ; 
      					$('bedSubType').value = res[2] ; 
      					$('bedSubTypeName').value = res[3] ; 
      				} else {
      		      	 	$('bedType').value='0';
      		      	 	$('bedTypeName').value='';
      		      	 	$('bedSubType').value='0';
      		      	 	$('bedSubTypeName').value='';
      				}
      				bedSubTypeAutocomplete.setParentId($('department').value+'#'+$('bedType').value) ;
      				
      			}
      		}) ;  
  		});
  		bedTypeAutocomplete.addOnChangeCallback(function() {
  			HospitalMedCaseService.getDefaultBedSubTypeByDepartment(
					 $('department').value, $('serviceStream').value
					 ,$('bedType').value
     				, $('dateFrom').value,{
     			callback: function(aResult) {
     				var res = aResult.split('#') ;

     				if (+res[0]!=0) {
     					$('bedSubType').value = res[0] ; 
     					$('bedSubTypeName').value = res[1] ; 
     				} else {
     		      	 	$('bedSubType').value='0';
     		      	 	$('bedSubTypeName').value='';
     				}
     				bedSubTypeAutocomplete.setParentId($('department').value+'#'+$('bedType').value) ;
     				
     			}
     		}) ;
      	 });

      		</script>  
  </msh:ifFormTypeIsNotView>
  </tiles:put>

</tiles:insert>

