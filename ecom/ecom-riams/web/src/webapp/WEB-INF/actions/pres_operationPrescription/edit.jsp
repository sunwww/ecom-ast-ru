<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
	<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
	<msh:ifFormTypeIsNotView formName="pres_operationPrescriptionForm">
	<script type="text/javascript">
	
	var currentDate = new Date;
	var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
	var textMonth = currentDate.getMonth()+1;
	var textMonth = textMonth<10?'0'+textMonth:textMonth;
	var textYear =currentDate.getFullYear();
	var textDate = textDay+'.'+textMonth+'.'+textYear;

	//Заполняем ЛН данными из шаблона (не удаляя существующие назначения). 
	function fillFormFromTemplate(aData) {
		
		var aRow = aData.split("#");
		if (aRow.length>0) {
			for (var i=0;i<aRow.length;i++) {
				var research = aRow[i].split("@");
				if (research[0]!=null && research[0]!="" ){
					var prescType = research[0];
					if (prescType=="SERVICE") {
						addRows(research[1],1);
					}
				}
			}
		}
		
	}

	var oldaction = document.forms['pres_operationPrescriptionForm'].action ;
	document.forms['pres_operationPrescriptionForm'].action="javascript:cancell()";
	
	var num=0;
	var labNum=0;
	var funcNum=0;
	var surgNum=0;
	var labList="";
	function cancell () {
		window.location='entityParentView-pres_prescriptList.do?id='+$('prescriptionList').value;	
	} 
	function preShowDir() {
		 $('1IsViewButton').value=$('prescriptType').value ;
		 var list = +$('labServicies').value;
		 clear1DirMedServiceDialog() ;
		 var typeNum = 0;
			type='lab';
				typeNum = labNum;
				
			
			while (typeNum>0) {
				if (document.getElementById(type+"Element"+typeNum)) {
					var ar = $(type+"Service"+typeNum).value ;
					list+="," ;
					list+=ar ;
				}
	       		typeNum-=1;
		 	}
			
		 $('1ListIds').value=list;
	 }
 	onload = showShowPrescriptList($('prescriptionList').value);
	function startLoad () {
		var date = new Date();
		var month = date.getMonth()+1; if (month<10) {month="0"+month;}
		var day = date.getDate(); if (day<10) {day="0"+day;}
		var year = date.getFullYear();
	}	

	function checkDoubles () {
		labList="";
		var i=0;
		// Id from aList 
		var str="";
		var aList =labList;
		aList = aList.substring(0,aList.length-1);
		var aListArr = aList.split("#");
		if (aListArr.length>0) {
			for (var i=0; i<aListArr.length;i++) {
				var id = aListArr[i].split(":");
			str+=id[0]+":";
			}
		}
		str=str.substring(0,str.length-1);
		PrescriptionService.getMedcaseByPrescriptionList($('prescriptionList').value, {
			callback: function (aMedCase) {
				PrescriptionService.getDuplicatePrescriptions(""+aMedCase, str,{
					callback: function(aResult) {
						if (aResult.length>0){
							var aText = "Данные назначения\n "+aResult+"\nуже назначены пациенту в этой истории болезни, все равно назначить?";
								if (!confirm (aText)) {							
									document.getElementById('submitButton').disabled=false;
									document.getElementById('submitButton').value='Создать';
									return;
									}
						}
						checkLabs();
						
						
					}
				});		
			}
		});
		 
	

	}

	function removeRows(type) {
		var rType;
		if (type=='lab') {rType=labNum; labNum=0;}
		else if (type=='func') {rType=funcNum;funcNum=0;}
		else if (type=='drug') {rType=drugNum;drugNum=0;}
		else return;
		 
		if (rType>0) {
			for (var i=1; i<=rType;i++) {
				if (document.getElementById(type+'Element'+i)){
					var node = document.getElementById(type+'Element'+i);
					node.parentNode.removeChild(node);
				}				
			}
		}
		
	}
	function checkError(aErrorList) {
		for (var i=0;i<aErrorList.length;i++) {
			if (aErrorList[i][3]=="isEmptyUnit") {
				if ($(aErrorList[i][0]).value==""){
					alert("Обязательное поле: "+aErrorList[i][2]) ;
					//alert(aErrorList[i][0]+aErrorList[i][1]);
					$(aErrorList[i][0]+aErrorList[i][1]).focus() ;
					return true;
				}
			}
		}
		return false ;
	}
	function prepareLabRow(type) {
	var fldList,reqList =[];
	
	if (type=='surg') {
		var error = [
						[type+'Servicies','Name', 'Операция!','isEmptyUnit']
				      	,[type+'CalTime','', 'Дата и время операции!','isEmptyUnit']
					  
					];
		num = surgNum;
		fldList = [['surgServicies',1],['surgServiciesName',1],['surgCalDateName',1],['surgCabinet',1]
		,['surgCabinetName',1],['',1],['',1],['surgCalTime',1],['surgCalTimeName',1],['comments',1]
	] ;
	
	}
	var typeDate = new Date();
	var l = $(type+'CalDateName').value;
	l=l.substr(6,4)+'-'+l.substr(3,2)+'-'+l.substr(0,2);
	typeDate.setTime (Date.parse(l));
	
	if (typeDate<currentDate) {
		if (!confirm ('Дата назначения меньше текущей даты. Создать назначение прошедшим днем?')) {
			return;
		}
	} 
	
	
	if (checkError(error)) return ;
	// Проверим на дубли 
	var checkNum = 1;
	while (checkNum<=num) {
		if (document.getElementById(type+'Service'+checkNum)) {
			if ($(type+'CalTime')) {
				if ($(type+'CalTime').value == $(type+'CalTime'+checkNum).value) {
					alert ("На это время уже назначена запись!");
					return;
				}
			}
			if ($(type+'Servicies').value==document.getElementById(type+'Service'+checkNum).value){
				if (confirm("В этом листе назначений уже назначено это исследование, ВЫ УВЕРЕНЫ, что хотите назначить его еще раз?")) {
					break; 
				} else {
					return;
				}
			}
			
		}
		checkNum+=1;
	}
	var ar = getArrayByFld(type,"", fldList, reqList, "", -1) ;
	addRows(type+":"+ar[0],1);
}
    function checkRecord(aId,aValue,aIdService,aService) {
    	$('surgCalTime').value = aId; 
    	$('surgCalTimeName').value = aValue ;
    	
    }	
function addPrescription(aLabID, aLabDepartment, aLabCabinet, aDateStart, aWCT, comments) {
	PrescriptionService.addPrescriptionToListWCT($('prescriptionList').value, aLabID, aLabDepartment, aLabCabinet,"ServicePrescription",aDateStart, aWCT, comments);
}
function deletePrescription(aMedService, aWCT) {
	PrescriptionService.removePrescriptionFromListWCT($('prescriptionList').value,aMedService,aWCT);
}

		function getArrayByFld(aType, aTypeNum, aFldList, aReqFldId, aCheckFld, aCheckId) {
			var next = true ;
			var l="",lAll="",isDoubble=0 ;
		for (var i=0;i<aReqFldId.length;i++) {
			if ($(aType+aFldList[aReqFldId[i]][0]+aTypeNum).value=="") {next=false ; break;}  
		}
		if (next) {
			for (var i=0;i<aFldList.length;i++) {
				var val = aFldList[i][0]==""?"":$(aFldList[i][0]+aTypeNum).value ;
				val = val.replace(":","-");
				if (i!=0) {
					l += ":" ;lAll+=":";
				} 
				if (aCheckId==i) {
					if ($(aCheckFld).value==val) {
		            	isDoubble=1;	
		            }						
				}
				if (aFldList[i][1]==1) { l += val ; }
				lAll+=val ;
				//if (i==(aFldList.length-1)) {l += "#" ;lAll+="#" ;}
			}
		}
		return [l,lAll,isDoubble] ;
		}
		

		function addRows(aResult,aFocus) {
			var resultRow = aResult.split(":");
		/*
		0 - ms.type
		1 - ms.ID 2 - ms. code+name
		3 - date
		4 - cabinetID	        	5 - cabinetname
		6 - departmentintakecode	7 - departmentintakename (for lab)
		8 - timeID	                9 - timename (for func)
		*/
		var type = resultRow[0];
		var id = resultRow[1]; 
		var name = resultRow[2];
		var date = resultRow[3]!=""?resultRow[3]:textDate;
		var cabinet = resultRow[4]?resultRow[4]:"";
		var cabinetName = resultRow[5]?resultRow[5]:"";
		var calTime = resultRow[8];
		var calDateName = resultRow[9];
		var comment = resultRow[10]?resultRow[10]:"";
		
		if (type=='LABSURVEY' || type=='lab') {
			type='lab'; num = labNum;
		} else if (type=='DIAGNOSTIC' || type=='func') {
			type='func'; num = funcNum; 
		} else if (type=='surg') {
			num = surgNum;
		} else if (type=='hosp') {
			num = hospNum;
		} else {
			alert ("unknown type " + type);
			return;
		}
		addPrescription(id,'',cabinet,date,calTime,comment);
		num+=1;
	    
 		var tbody = document.getElementById('add'+type+'Elements');
	    var row = document.createElement("TR");
		row.id = type+"Element"+num;
	    tbody.appendChild(row);
	
	    // Создаем ячейки в вышесозданной строке и добавляем тх 
	    var td1 = document.createElement("TD"); td1.colSpan="1"; td1.align="right";
	    var td2 = document.createElement("TD"); td2.colSpan="3";
	    var td3 = document.createElement("TD");
	    row.appendChild(td1); row.appendChild(td2); row.appendChild(td3);
	   
	    // Наполняем ячейки 
	    //var dt2="<input id='"+type+"Cabinet"+num+"' name='"+type+"Cabinet"+num+"' value='"+cabinet+"' type='hidden'  />";
	    
	  	td1.innerHTML = "";//textInput("Дата",type,"Date",num,resultRow[3],date,10) ;
	    td2.innerHTML = hiddenInput(type,"Service",num,resultRow[1],"")+spanTag("Исследование",resultRow[2],"");
	   	if (type=="lab") {
	   		td2.innerHTML += hiddenInput(type,"Department",num,resultRow[6],"")+spanTag("Место забора",resultRow[7],"") ;
	   		td2.innerHTML += hiddenInput(type,"Cabinet",num,cabinet,"");
	   		labNum = num;
	   	} else if (type=="func"){
		   	td2.innerHTML += hiddenInput(type,"Cabinet",num,resultRow[4],"")+spanTag("Кабинет",resultRow[5],"");
	   		td2.innerHTML += hiddenInput(type,"CalTime",num,resultRow[8],"")+spanTag("Время",resultRow[9],"") ;
	   		funcNum = num;
	   		$(type+'Cabinet').value='';
			$(type+'CabinetName').value='';
	   	} else if (type=='surg') {
	   		surgNum=num;
	   		td1.innerHTML = spanTag("Дата",resultRow[3]+" "+resultRow[9],"") ;
	   		td1.colSpan="1";
	   		td2.innerHTML += hiddenInput(type,"Cabinet",num,resultRow[4],"")+spanTag("Операционная",resultRow[5],"");
	   		td2.innerHTML += hiddenInput(type,"CalTime",num,resultRow[8],"");
	   		$(type+'CalTime').value='';
	   		$(type+'CalTimeName').value='';
	   
	   	}
	   	td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);deletePrescription("+resultRow[1]+","+resultRow[8]+")' value='Удалить' />";
	   	//new dateutil.DateField($(type+'Date'+num));
		
		$(type+'Servicies').value='';
		$(type+'ServiciesName').value='';
		if ($('tdPreRecord')) { $('tdPreRecord').innerHTML=""; }
		if ($('divReserve')) { $('divReserve').innerHTML=""; }
		getPreRecord() 
		if (aFocus) $(type+'ServiciesName').focus() ;
}
	function hiddenInput(aType,aFld,aNum,aValue,aDefaultValue) {
		return "<input id='"+aType+aFld+aNum+"' name='"+aType+aFld+aNum+"' value='"+(aValue==null||aValue==""?aDefaultValue:aValue)+"' type='hidden' />"
	}
	function textInput(aLabel,aType,aFld,aNum,aValue,aDefaultValue,aSize) {
		return "<span>"+aLabel+"</span><input "+(+aSize>0?"size="+aSize:"")+" id='"+aType+aFld+aNum+"' name='"+aType+aFld+aNum+"' value='"+(aValue==null||aValue==""?aDefaultValue:aValue)+"' type='text' />"
	}
	function spanTag(aText,aValue,aDefaultValue) {
		return "<span>"+aText+": <b>"+(aValue!=null&&aValue!=""?aValue.trim():aDefaultValue.trim())+"</b></span>. " ;
	}
	surgCalDateAutocomplete.addOnChangeCallback(function(){
	  	  getPreRecord() ;
	 });
	surgCabinetAutocomplete.addOnChangeCallback(function(){
		updateDefaultDate() ;
	}) ;
	
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
 			WorkCalendarService.getReserveByDateAndServiceByPrescriptionList($('surgCalDate').value,$('prescriptionList').value
	    			  
	  		, {
	                 callback: function(aResult) {
	                	 //alert(aResult) ;
	                	 if ($('divReserve')) { $('divReserve').innerHTML = aResult ; }
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
			</script>
			</msh:ifFormTypeIsNotView>
			</tiles:put>

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение на операцию
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-pres_operationPrescription.do" defaultField="id" title="Назначение операции">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="prescriptionList" guid="8b852c-d5aa-40f0-a9f5-21dfgd6" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="labList" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:panel guid="panel" colsWidth="3">  
         <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="prescriptSpecial" label="Назначил" size="100" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
        </msh:row>
         <msh:ifFormTypeIsView formName="pres_operationPrescriptionForm">
      <msh:row>
    			<msh:autoComplete property="medService" label="Исследование" vocName="surgicalOperations" horizontalFill="true" size="90" viewOnlyField="true" />
    		 </msh:row>
			 <msh:row>
				 <msh:autoComplete property="prescriptCabinet" label="Операционная"  viewOnlyField="true" vocName="operationRoom" size='20' horizontalFill="true" />
			</msh:row>
			<msh:row>
				 <msh:textField property="planStartDate" size="10" fieldColSpan="1" />
    		</msh:row>
			<msh:row>
				 <msh:textArea property="comments" label="Примечание" size="50" fieldColSpan="4" />
			</msh:row>
      
    </msh:ifFormTypeIsView>
 <%-- --------------------------------------------------Начало блока "Операции" ------ --%>
         <msh:ifFormTypeIsCreate formName="pres_operationPrescriptionForm"> 
        <msh:panel styleId="tblSurgOperation">
       	 <msh:row>
        	<msh:separator label="Операции" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="surgTable">
        <tbody id="addsurgElements">
        <msh:row>
    			<msh:autoComplete property="surgServicies" label="Операция" vocName="surgicalOperations" horizontalFill="true" size="90" fieldColSpan="4" />
    		 </msh:row>
    		<msh:row>
				 <msh:autoComplete property="surgCabinet" parentAutocomplete="surgServicies" label="Операционная"  fieldColSpan="4" vocName="operatingRoomsByMedService" size='20' horizontalFill="true" />
			</msh:row>
			
			 
			<msh:row>
				 <msh:autoComplete property="surgCalDate" parentAutocomplete="surgCabinet" vocName="vocWorkCalendarDayByWorkFunction" label="Дата" size="10" fieldColSpan="1" />
			</msh:row>
			<msh:row>
    			 <msh:autoComplete property="surgCalTime" parentAutocomplete="surgCalDate" label="Время" vocName="vocWorkCalendarTimeWorkCalendarDay" fieldColSpan="1" />
    		</msh:row>
			<msh:row>
				 <msh:textArea property="comments" label="Примечание" size="50" fieldColSpan="4" />
			</msh:row>
			<msh:row>
				<td colspan="4" align="center">        	
	            	<input type="button" name="subm" onclick="prepareLabRow('surg');" value="Создать назначение" tabindex="4" />
	            </td>
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
           </tbody>
    		</table>
    		</td></tr></msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
        <%-- -- --------------------------------------------------Конец блока "Операции" ------ --%>
        <msh:ifFormTypeAreViewOrEdit formName="pres_operationPrescriptionForm">
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
        </msh:ifFormTypeAreViewOrEdit>
        <msh:row><td>
        <input type='button' onclick='cancell()' value='Закрыть'>
        </td></msh:row>              
        <%--  <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" /> --%> 
      </msh:panel>
    </msh:form>
    <tags:enter_date name="2" functionSave="prepare1RowByDate"/>
   
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="StacJournal" beginForm="pres_operationPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_operationPrescriptionForm" guid="99ca692-c1d3-4d79-bc37-c6726c">
      <msh:sideMenu title="Назначения" guid="eb3f54-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="id" action="/entityParentEdit-pres_operationPrescription" name="Изменить" guid="ca5sui7r-9239-47e3-aec4-995462584" key="ALT+2"/>
        <msh:sideLink confirm="Удалить?" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" params="id" action="/entityParentDelete-pres_operationPrescription" name="Удалить" guid="ca5sui7r-9239-47e3-aec4-995462584" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="0e2ac7-5361-434d-a8a7-1284796f">
    
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" guid="ca3s9-9239-47e3-aec4-9a846547144" key="ALT+3"/>
      
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="67aa758-3ad2-4e6f-a791-4839460955" >
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentListRedirect-pres_operationPrescription" name="К списку назначений на операцию" guid="e9d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" guid="e9d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsCreate formName="pres_operationPrescriptionForm">
    <tags:templatePrescription record="2" parentId="${param.prescriptionList}" name="add" />
    <tags:pres_addFromDirections parentID='${param.prescriptionList}' name='Show'/>
<%--     <msh:sideMenu title="Шаблоны">
  			<msh:sideLink action=" javascript:showaddTemplatePrescription()" name="Назначения из шаблона" guid="a2f380f2-f499-49bf-b205-cdeba65f8888" title="Добавить назначения из шаблона" />
  		</msh:sideMenu> --%>
  		
    </msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>

