<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>

<msh:ifFormTypeIsNotView formName="pres_prescriptListForm">
		<script type="text/javascript">
		var oldaction = document.forms['pres_prescriptListForm'].action ;
		document.forms['pres_prescriptListForm'].action="javascript:checkDoubles()";
		var num=0;
		var labNum=0;
		var funcNum=0;
		var drugNum=0;
		var labList="";
		var drugList="";
		var allDrugList="";
		var currentDate = new Date;
		var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
		var textMonth = currentDate.getMonth()+1;
		var textMonth = textMonth<10?'0'+textMonth:textMonth;
		var textYear =currentDate.getFullYear();
		var textDate = textDay+'.'+textMonth+'.'+textYear;
		
 		onload=checkPrescriptExists();
 		
 		// Проверяем, есть ли другие ЛН в данном СЛО 
		function checkPrescriptExists() {
			if ($('prescriptType').value=="" || $('prescriptType').value==null){			
 				PrescriptionService.isPrescriptListExists($('medCase').value, {
					callback: function(plID) {
						if (plID!="" && plID!=null) {
						//	alert ("Prescription List in this medcase exists, "+plID);
							createUrl(plID+" В данном СЛО уже заведен лист назначения, хотите в него перейти?",
									"entityParentView-pres_prescriptList.do?id="+plID,
									"В данном СЛО уже заведен лист назначения.");
						//	startLoad(); //Отладка 
						} else {
							startLoad();
						}
					}
				});
			} else {
				startLoad();
			}
		}
 		
 			function startLoad() {
 			if ($('prescriptType').value=="" || $('prescriptType').value==null){
 				showcheckPrescTypes(); 				
 			}
 			$('prescriptTypeName').disabled='true';
 			if ($('labList').value.length>0)
           		{
 			//	alert ("labList.value= "+$('labList').value);
           		PrescriptionService.getPresLabTypes($('labList').value, 0,{
           			callback: function(aResult2) {
           			//	alert ("aResult is : " +aResult2);
           				if (aResult2) {
           					var resultList = aResult2.split('#');
           					if (resultList.length>0) {
           						for (var i=0; i<resultList.length;i++) {
           							var resultRow = resultList[i].split(':');
           							if (resultRow[0]!="" && resultRow[0]!=null)
           								{
           								addRow(resultList[i]);
           								}
           						}
           					}
           				}
           			}
           		}
           				);
           		} 
 			if ($('allDrugList').value.length>0) {
 				var aDrugArr = $('allDrugList').value.split('#');
 				for (var i=0;i<aDrugArr.length;i++) {
 					var resultRow = aDrugArr[i].split(':');
 					if (resultRow[0]!="" && resultRow[0]!=null){
 						addDrugRow(aDrugArr[i]);
 					}
 				}
 			}
 			
 

}  
		
		  function createUrl( aTitleConfirm,aUrlCreate,aMessageNoRight) {
		     	if (confirm(aTitleConfirm)) {
					window.location.href = aUrlCreate  ;
				} else {
					window.history.back();
				}
				
		  }
		//При изменении типа ЛН, удаляем все лаб. исследования, прогоняем через PrescriptionService, 
		//Заполняем только теми исследованиями, у которых соответствующий тип 

		function changePrescriptionType() {
			 writeServicesToList('lab');
				$('labServicies').value="";
				$('labServiciesName').value="";
				if (labList.length>0) {
					 removeRows('lab');
					PrescriptionService.getPresLabTypes(labList, $('prescriptType').value,{
						callback: function(aResult) {
							if (aResult) {
		       					var resultList = aResult.split('#');
		       					if (resultList.length>0) {
		       						for (var i=0; i<resultList.length;i++) {
		       							var resultRow = resultList[i].split(':');
		       							if (resultRow[0]!="" && resultRow[0]!=null)
		       								{
		       								addRow(resultList[i]);
		       								}
		       						}
		       					}
		       				}
						}
					});
				}
		}
		
		
 		function writeServicesToList(type) {
			var typeNum = 0;
		if (type=='lab') {
			typeNum = labNum;
		} else if (type=='func') {
			typeNum = funcNum;
		}
		var isDoubble=0;
		while (typeNum>0) {
			if (document.getElementById(type+"Element"+typeNum)) {
				var curService = document.getElementById(type+'Service'+typeNum);
				var curDate = document.getElementById(type+'Date'+typeNum);
				var curCabinet = document.getElementById(type+'Cabinet'+typeNum);
				if (curService.value != "" & curDate.value != "") {			            
		            labList+=curService.value;
		            labList+=":";
		            labList+=curDate.value;
		            labList+=":";
		            labList+=curCabinet.value;
		            labList+="#";
		            // Проверка на дубли 
		            if ($(type+'Servicies').value==curService.value) {
		            	isDoubble=1;	
		            }
				}
					
			}
       		typeNum-=1;
	 	}
		if (isDoubble==0) {
			if ($(type+'Servicies').value != "" & $(type+'Date').value != "") {
			   	labList+=$(type+'Servicies').value;
	            labList+=":";
	            labList+=$(type+'Date').value;
	            labList+=":";
	            labList+=$(type+'Cabinet').value;
	            labList+="#";
	         }
	     }
		}
		// Добавление всех лекарственных назначений в список 
		function writeDrugsToList() {
			drugList="";
			allDrugList="";
			var type = "drug";
			var typeNum = 0;
			if (type=="drug") {
			typeNum = drugNum;
			}
		var isDoubble=0;
		while (typeNum>0) {
			if (document.getElementById(type+"Element"+typeNum)) {
				var curDrug = $(type+'Drug'+typeNum).value;
				var curDrugName = $(type+'DrugName'+typeNum).value;
				var curDate = document.getElementById(type+'Date'+typeNum);
				var curMethod = document.getElementById(type+'Method'+typeNum);
				var curMethodName = document.getElementById(type+'MethodName'+typeNum);
				var curAmount = document.getElementById(type+'Amount'+typeNum);
				var curDuration = document.getElementById(type+'Duration'+typeNum);
				var curFrequency = document.getElementById(type+'Frequency'+typeNum);
				var curAmountUnit = document.getElementById(type+'AmountUnit'+typeNum);
				var curAmountUnitName = document.getElementById(type+'AmountUnitName'+typeNum);
				var curDurationUnit = document.getElementById(type+'DurationUnit'+typeNum);
				var curDurationUnitName = document.getElementById(type+'DurationUnitName'+typeNum);
				var curFrequencyUnit = document.getElementById(type+'FrequencyUnit'+typeNum);
				var curFrequencyUnitName = document.getElementById(type+'FrequencyUnitName'+typeNum);
				if (curDrug != "" & curDate.value!= "") {	
					//Формат строки - name:date:method:freq:freqU:amount:amountU:duration:durationU# 
		            drugList+=curDrug;
		            drugList+=":";
		            drugList+=curDate.value;
		            drugList+=":";
		            drugList+=curMethod.value;
		            drugList+=":";
		            drugList+=curFrequency.value;
		            drugList+=":";
		            drugList+=curFrequencyUnit.value;
		            drugList+=":";
		            drugList+=curAmount.value;
		            drugList+=":";
		            drugList+=curAmountUnit.value;
		            drugList+=":";
		            drugList+=curDuration.value;
		            drugList+=":";
		            drugList+=curDurationUnit.value;
		            drugList+="#";
		            // Add into allDrugList 
		            allDrugList+=curDrug;
		            allDrugList+=":";
		            allDrugList+=curDrugName;
		            allDrugList+=":";
		            allDrugList+=curDate.value;
		            allDrugList+=":";
		            allDrugList+=curMethod.value;
		            allDrugList+=":";
		            allDrugList+=curMethodName.value;
		            allDrugList+=":";
		            allDrugList+=curFrequency.value;
		            allDrugList+=":";
		            allDrugList+=curFrequencyUnit.value;
		            allDrugList+=":";
		            allDrugList+=curFrequencyUnitName.value;
		            allDrugList+=":";
		            allDrugList+=curAmount.value;
		            allDrugList+=":";
		            allDrugList+=curAmountUnit.value;
		            allDrugList+=":";
		            allDrugList+=curAmountUnitName.value;
		            allDrugList+=":";
		            allDrugList+=curDuration.value;
		            allDrugList+=":";
		            allDrugList+=curDurationUnit.value;
		            allDrugList+=":";
		            allDrugList+=curDurationUnitName.value;
		            allDrugList+="#";
		            // Проверка на дубли 
		            if ($('drugForm1.drug').value==curDrug) {
		            	isDoubble=1;	
		            }
				}
					
			}
       		typeNum-=1;
	 	}
		if (isDoubble==0) {
			if ($('drugForm1.drug').value != "" & $('drugForm1.planStartDate').value != "") {
				drugList+=$('drugForm1.drug').value;
	            drugList+=":";
	            drugList+=$('drugForm1.planStartDate').value;
	            drugList+=":";
	            drugList+=$('drugForm1.method').value;
	            drugList+=":";
	            drugList+=$('drugForm1.frequency').value;
	            drugList+=":";
	            drugList+=$('drugForm1.frequencyUnit').value;
	            drugList+=":";
	            drugList+=$('drugForm1.amount').value;
	            drugList+=":";
	            drugList+=$('drugForm1.amountUnit').value;
	            drugList+=":";
	            drugList+=$('drugForm1.duration').value;
	            drugList+=":";
	            drugList+=$('drugForm1.durationUnit').value;
	            drugList+="#";
	            // allDrugList 
	            allDrugList+=$('drugForm1.drug').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.drugName').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.planStartDate').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.method').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.methodName').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.frequency').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.frequencyUnit').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.frequencyUnitName').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.amount').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.amountUnit').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.amountUnitName').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.duration').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.durationUnit').value;
	            allDrugList+=":";
	            allDrugList+=$('drugForm1.durationUnitName').value;
	            allDrugList+="#";
	         }
	     }
	//	alert("DrugList = "+drugList); 
		}
		
		// Проверка на пустые поля в блоке лек. обеспечение 
		function isEmptyUnit(aField,aFieldText) {
			if ($(aField).value!="" && $(aField+'Unit').value=="") {
			alert ('Заполните поле" '+aFieldText+'"');
			$(aField+'UnitName').focus();
			document.getElementById('submitButton').disabled=false;
			document.getElementById('submitButton').value='Создать';
			return 1;
				}
			return 0;
		}
		
		// Проверяем на наличие назначений в других ЛН этого же СЛО 
		function checkDoubles () {
			labList="";
			if ($('labServicies')) {
				writeServicesToList('lab');
			}
			var i=0;
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
			PrescriptionService.getDuplicatePrescriptions($('medCase').value, str,{
				callback: function(aResult) {
					if (aResult.length>0){
						var aText = "Данные назначения\n "+aResult+"\nуже назначены пациенту в этой истории болезни, назначить всё равно?";
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
		
		function checkLabs() {

			if (isEmptyUnit('drugForm1.frequency', 'Частота')) return;
			if (isEmptyUnit('drugForm1.amount', 'Дозировка')) return;
			if (isEmptyUnit('drugForm1.duration', 'Продолжительность')) return;
			
			
			if ($('funcServicies')) {
				writeServicesToList('func');
			}
			$('labList').value=labList ;
			writeDrugsToList();
			$('drugList').value = drugList;
			$('allDrugList').value = allDrugList;
	//		alert("labList = "+$('labList').value); 
	//		alert("drugList = "+$('drugList').value); 
			document.forms['pres_prescriptListForm'].action=oldaction ;
			document.forms['pres_prescriptListForm'].submit();
		}
		
		function prepareLabRow(type) {
			if (type=='lab') {
				num = labNum;
			} else if (type=='func') {
				num = funcNum;
			}
			if (document.getElementById(type+'Servicies').value==""){
				alert("Выбирите услугу!");
				return;
			}
			// Проверим на дубли 
			var checkNum = 1;
			if (num>0){
				while (checkNum<=num) {
					if (document.getElementById(type+'Service'+checkNum)) {
						if ($(type+'Servicies').value==document.getElementById(type+'Service'+checkNum).value){
						//	if ($(type+'Date').value==document.getElementById(type+'Date'+checkNum).value) {
								if (confirm("В этом листе назначений уже назначено это исследование, ВЫ УВЕРЕНЫ, что хотите назначить его еще раз?")) {
									break; 
								}else {
									return;
								}
								
						//	}
						}
					}
					checkNum+=1;
			}
			}

			var aLabID = document.getElementById(type+'Servicies').value;
			var aLabName =document.getElementById(type+'ServiciesName').value;
			var aLabDate = $(type+'Date').value;
			var aLabCabinet = $(type+'Cabinet').value;
			var aLabCabinetName = $(type+'CabinetName').value;
			
			var aData = type+":"+aLabID+":"+aLabName+":"+aLabDate+":"+aLabCabinet+":"+aLabCabinetName; 
			addRow(aData);
		}

		
		//AddRow для лабораторных и функциональных исследований 
		function addRow(result) {
			var resultRow = result.split(":");
			/*
			0 - ms.type
			1 - ms.ID
			2 - ms. code+name
			3 - date
			4 - cabinetcode
			5 - cabinetname
			*/
			var aLabType = resultRow[0];
			var aLabID = resultRow[1]; 
			var aLabName = resultRow[2];
			var aLabDate = resultRow[3]!=""?resultRow[3]:textDate;
			var aLabCabinet = resultRow[4]?resultRow[4]:"";
			var aLabCabinetName = resultRow[5]?resultRow[5]:"";
			var type="";
			
		if (aLabType=='LABSURVEY' || aLabType=='lab') {
			type='lab';
			num = labNum;
		} else if (aLabType=='DIAGNOSTIC' || aLabType=='func') {
			num = funcNum;
			type='func';
		}
			
		num+=1;
	    
 		var tbody = document.getElementById('add'+type+'Elements');
	    var row = document.createElement("TR");
		row.id = type+"Element"+num;
	    tbody.appendChild(row);
	
	    // Создаем ячейки в вышесозданной строке 
	    // и добавляем тх 
	    var td1 = document.createElement("TD");
	   	td1.colSpan="2";
	   	td1.align="right";
	    var td2 = document.createElement("TD");
	    td2.colSpan="2";
	    var td3 = document.createElement("TD");
	    
		 row.appendChild(td1);
		 row.appendChild(td2);
		 row.appendChild(td3);
	   
	    // Наполняем ячейки 
	    var dt="<input id='"+type+"Service"+num+"' value='"+aLabID+"' type='hidden' name='"+type+"Service"+num+"' horizontalFill='true' size='90' readonly='true' />";
	    var dt2="<input id='"+type+"Cabinet"+num+"' value='"+aLabCabinet+"' type='hidden' name='"+type+"Cabinet"+num+"' horizontalFill='true' size='20' readonly='true' />";
	    
	    td2.innerHTML = dt+"<span>Исследование: <b>"+aLabName+"</b></span>" ;
	  	td1.innerHTML = "<span>Дата: </span><input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+aLabDate+"' size = '10' />";
	   	td2.innerHTML += dt2+"<span>. Кабинет: "+aLabCabinetName+"</span>" ;
	   	td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
	   	new dateutil.DateField($(type+'Date'+num));
					   
		if (type=='lab') {
			labNum = num;
		} else if (type=='func'){
			funcNum = num;
		}
		$(type+'Servicies').value='';
		$(type+'ServiciesName').value='';
	}
		
		function prepareDrugRow(){
			if ($('drugForm1.drug').value=='' ||$('drugForm1.method').value=='' ) {
				alert ('Введите название лекарства и способ введения!');
				return;
			}
			if (isEmptyUnit('drugForm1.frequency', 'Частота')) return;
			if (isEmptyUnit('drugForm1.amount', 'Дозировка')) return;
			if (isEmptyUnit('drugForm1.duration', 'Продолжительность')) return;
			var aDrug=$('drugForm1.drug').value;
			var aDrugName = $('drugForm1.drugName').value;
			var aDrugDate = $('drugForm1.planStartDate').value;
		    var aDrugMethod = $('drugForm1.method').value;
		    var aDrugMethodName = $('drugForm1.methodName').value;
		    var aDrugFrequency=$('drugForm1.frequency').value;
		    var aDrugFrequencyUnit=$('drugForm1.frequencyUnit').value;
		    var aDrugFrequencyUnitName =$('drugForm1.frequencyUnitName').value;
		    var aDrugAmount=$('drugForm1.amount').value;
		    var aDrugAmountUnit=$('drugForm1.amountUnit').value;
		    var aDrugAmountUnitName = $('drugForm1.amountUnitName').value;
		    var aDrugDuration=$('drugForm1.duration').value;
		    var aDrugDurationUnit=$('drugForm1.durationUnit').value;
		    var aDrugDurationUnitName =$('drugForm1.durationUnitName').value;
		    var aResult = aDrug+":"+aDrugName+":"+aDrugDate+":"+aDrugMethod+":"+aDrugMethodName
		    	+":"+aDrugFrequency+":"+aDrugFrequencyUnit+":"+aDrugFrequencyUnitName
		    	+":"+aDrugAmount+":"+aDrugAmountUnit+":"+aDrugAmountUnitName
		    	+":"+aDrugDuration+":"+aDrugDurationUnit+":"+aDrugDurationUnitName;
			addDrugRow(aResult);
		}
		
		// Добавляем лек. обеспечение 
		function addDrugRow(aResult) { 
			var type = "drug";
			num = drugNum;
		
		num+=1;
		var aDrugArr = aResult.split(":");
	    var aDrugID = aDrugArr[0];
	    var aDrugName = aDrugArr[1];
	    var aDrugDate = aDrugArr[2]!=""?aDrugArr[2]:textDate;
	    var aDrugMethod = aDrugArr[3]!=null?aDrugArr[3]:"";
	    var aDrugMethodName = aDrugArr[4]!=null?aDrugArr[4]:"";
	    var aDrugFrequency = aDrugArr[5];
	    var aDrugFrequencyUnit = aDrugArr[6]!=null?aDrugArr[6]:"";
	    var aDrugFrequencyUnitName = aDrugArr[7]!=null?aDrugArr[7]:"";
	    var aDrugAmount = aDrugArr[8];
	    var aDrugAmountUnit = aDrugArr[9]!=null?aDrugArr[9]:"";
	    var aDrugAmountUnitName= aDrugArr[10]!=null?aDrugArr[10]:"";
	    var aDrugDuration = aDrugArr[11];
	    var aDrugDurationUnit = aDrugArr[12]!=null?aDrugArr[12]:"";
	    var aDrugDurationUnitName = aDrugArr[13]!=null?aDrugArr[13]:"";
	    
 		var tbody = document.getElementById('add'+type+'Elements');
	    var row = document.createElement("TR");
		row.id = type+"Element"+num;
	    tbody.appendChild(row);
	
	    // Создаем ячейки в вышесозданной строке 
	    // и добавляем тх 
	    var td1 = document.createElement("TD");
	  // 	td1.colSpan="2";
	  // 	td1.align="right";
	    var td2 = document.createElement("TD");
	  //  td2.colSpan="2";
	    var td3 = document.createElement("TD");
	    var td4 = document.createElement("TD");
	    var td5 = document.createElement("TD");
	    var td6 = document.createElement("TD");
	    
		 row.appendChild(td1);
		 row.appendChild(td2);
		 tbody.appendChild(row);
		 row.appendChild(td3);
		 row.appendChild(td4);
		 row.appendChild(td5);
		 row.appendChild(td6);
	    
	    // Наполняем ячейки 
	    //drug 
	    var dt2="<input id='"+type+"Drug"+num+"' value='"+aDrugID+"' type='hidden' name='"+type+"Drug"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    dt2+="<input id='"+type+"DrugName"+num+"' value='"+aDrugName+"' type='hidden' name='"+type+"DrugName"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //method+(Name) 
	    dt2+="<input id='"+type+"Method"+num+"' value='"+aDrugMethod+"' type='hidden' name='"+type+"Method"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    dt2+="<input id='"+type+"MethodName"+num+"' value='"+aDrugMethodName+"' type='hidden' name='"+type+"MethodName"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //frequency 
	    var dt3="<input id='"+type+"Frequency"+num+"' value='"+aDrugFrequency+"' type='hidden' name='"+type+"Frequency"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    // frequency unit+(Name) 
	    dt3+="<input id='"+type+"FrequencyUnit"+num+"' value='"+aDrugFrequencyUnit+"' type='hidden' name='"+type+"FrequencyUnit"+num+"' size='10' readonly='true' />";
	    dt3+="<input id='"+type+"FrequencyUnitName"+num+"' value='"+aDrugFrequencyUnitName+"' type='hidden' name='"+type+"FrequencyUnitName"+num+"' size='10' readonly='true' />";
	    // Amount 
	    var dt4="<input id='"+type+"Amount"+num+"' value='"+aDrugAmount+"' type='hidden' name='"+type+"Amount"+num+"' horizontalFill='true' size='20' readonly='true' />";
	    //Amount unit+(Name)
	    dt4+="<input id='"+type+"AmountUnit"+num+"' value='"+aDrugAmountUnit+"' type='hidden' name='"+type+"AmountUnit"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    dt4+="<input id='"+type+"AmountUnitName"+num+"' value='"+aDrugAmountUnitName+"' type='hidden' name='"+type+"AmountUnitName"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //Duration 
	    var dt5="<input id='"+type+"Duration"+num+"' value='"+aDrugDuration+"' type='hidden' name='"+type+"Duration"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //Duration unit+(Name) 
	    dt5+="<input id='"+type+"DurationUnit"+num+"' value='"+aDrugDurationUnit+"' type='hidden' name='"+type+"DurationUnit"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    dt5+="<input id='"+type+"DurationUnitName"+num+"' value='"+aDrugDurationUnitName+"' type='hidden' name='"+type+"DurationUnitName"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    	   
	    td1.innerHTML = "<span>Дата: </span><input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+aDrugDate+"' size = '10' />";	    
	    td2.innerHTML = dt2+"<span id='"+type+"DrugName"+num+"'> "+aDrugName+"</span> Способ введения: "+aDrugMethodName+"</span>" ;
	  	td3.innerHTML = dt3+"<span>. Частота: "+aDrugFrequency +" "+aDrugFrequencyUnitName+"</span></span>";
	    td4.innerHTML = dt4+"<span>. Дозировка: "+aDrugAmount+" "+aDrugAmountUnitName+"</span>" ;
	   	td5.innerHTML = dt5+"<span>. Продолжительность: "+aDrugDuration+" "+aDrugDurationUnitName+"</span>";
	    td6.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
	   	new dateutil.DateField($(type+'Date'+num));
					   
	   	drugNum = num;
		$('drugForm1.drug').value='';
		$('drugForm1.drugName').value='';
		$('drugForm1.method').value='';
		$('drugForm1.methodName').value='';
	    $('drugForm1.frequency').value='';
	    $('drugForm1.frequencyUnit').value='';
	    $('drugForm1.frequencyUnitName').value='';
	    $('drugForm1.amount').value='';
	    $('drugForm1.amountUnit').value='';
	    $('drugForm1.amountUnitName').value='';
	    $('drugForm1.duration').value='';
	    $('drugForm1.durationUnit').value='';
	    $('drugForm1.durationUnitName').value='';
	}
		//Удаляем строки назначений (лекарство, лабораторные и функциональные исследования) 
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
		function flushAllFields() {
			$('comments').value="";
			$('labServicies').value="";
			$('labServiciesName').value="";
			$('funcServicies').value="";
			$('funcServiciesName').value="";
			if (labNum>0)  {removeRows('lab'); }
			if (funcNum>0) {removeRows('func');}
			if (drugNum>0) {removeRows('drug');}
		}
		
		//Заполняем ЛН данными из шаблона (не удаляя существующие назначения). 
		function fillFormFromTemplate(aData) {
			$('comments').value="";
			//flushAllFields();
			var aRow = aData.split("#");
			if (aRow.length>0) {
				for (var i=0;i<aRow.length;i++) {
					var research = aRow[i].split("@");
					if (research[0]!=null && research[0]!="" ){
						var prescType = research[0];
						if (prescType=="DRUG") {
							addDrugRow(research[1]);
						} 
						else if (prescType=="SERVICE") {
							addRow(research[1]);
						}
						else if (prescType=="DIET")	{
							var dietData = research[1].split(":");
							$('dietForm.diet').value=dietData[0];
							$('dietForm.dietName').value=dietData[0]+" "+dietData[1];
							
						}
						else if (prescType=="MODE")	{
							var modeData = research[1].split(":");
							$('modeForm.modePrescription').value=modeData[0];
							$('modeForm.modePrescriptionName').value=modeData[0]+" "+modeData[1];
							
						}
						else if (prescType=="COMMENT"){
							$('comments').value=research[1];
						} else {
						 }
					}
				}
			}
			
		}
	
		</script>
		</msh:ifFormTypeIsNotView>
			</tiles:put>
		
  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="StacJournal" guid="29345263-7743-4455-879e-130b73690294" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  -  лист назначений
    	  -->
   <msh:form action="/entityParentSaveGoView-pres_prescriptList.do" defaultField="workFunctionName" guid="ea411ae6-6822-4cbd-a7f3-b8f6cfa1beba">
      <msh:hidden property="id" guid="ba8ca3c4-0044-44ab-bb12-a75e3441fae2" />
      <msh:hidden property="saveType" guid="efb8a9d9-e3c6-4f03-87bc-f0cccb820e89" />
      <msh:hidden property="medCase" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:hidden property="labList" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:hidden property="drugList" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:hidden property="allDrugList" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:panel colsWidth="1%,1%,1%,97%">
      <msh:ifFormTypeIsNotView formName="pres_prescriptListForm">
            <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
           
          <input type='button' name='btnChangePrescriptionType' onclick='showcheckPrescTypes();' value='Изменить тип назначения' />
        </msh:row>
         </msh:ifFormTypeIsNotView>
      <msh:row>
       	<msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения" guid="3a3eg4d1b-8802-467d-b205-711tre18" horizontalFill="true" fieldColSpan="3" size="30" />
      </msh:row>
		<msh:row guid="154fb2a0-a3ac-4034-9cbb-087444dbe299">
          <msh:textArea rows="2" property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" guid="f5338dbf-03ae-4c9c-a2ee-e6a3cc240dff" />
        </msh:row>
        <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="workFunction" label="Назначил" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row><td></td>
        <msh:row>
        	<msh:separator label="Режим" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="modeForm.planStartDate" label="Дата начала" size="10"/>
          <msh:autoComplete vocName="vocModePrescription" property="modeForm.modePrescription" label="Режим" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Диета" colSpan="10"/>
        </msh:row>
        <msh:row guid="b556ehb-b971-441e-9a90-53217">
        	<msh:textField property="dietForm.planStartDate" label="Дата начала" size="10"/>
          <msh:autoComplete viewAction="entityView-diet_diet.do" vocName="Diet" 
          property="dietForm.diet" label="Диета" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        </msh:panel>
        <%-- -------------------------Начало блока "Лекарственное обеспечение" --%>
     <%--    <msh:ifInRole roles="/Policy/Mis/Prescription/Drugs"> --%>
         <div style="display: none">
          <msh:ifFormTypeIsCreate formName="pres_prescriptListForm">  
        <msh:panel styleId="border">
        <msh:row>
        	<msh:separator label="Лекарственные назначения" colSpan="20"/>
        </msh:row>
        <msh:row>
        		<td>Дата начала</td>
        		<td>Лек.ср-во и способ введения</td>
        		<td>Частота</td>
        		<td>Дозировка</td>
        		<td>Продолжительность</td>
        </msh:row>
	        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
	          <msh:textField property="drugForm1.planStartDate" label="Дата начала" size="10" hideLabel="true"/>
	          <msh:autoComplete vocName="vocDrugClassify" property="drugForm1.drug" label="Лек. препарат" guid="3a3eg4d1b-8802-467d-b205-715fb379b018" size="40" fieldColSpan="1" hideLabel="true"/>
	          <msh:textField property="drugForm1.frequency" label="Частота" size="7" hideLabel="true" />
	          <msh:textField label="Прием" property="drugForm1.amount" size="3" hideLabel="true"/>
	          <msh:textField property="drugForm1.duration" label="Продолжительность" size="3" hideLabel="true"/>
	        </msh:row>
	        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
	        <td></td>
	          <msh:autoComplete vocName="vocDrugMethod" label="Способ введ." property="drugForm1.method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true"  hideLabel="true"/>
	          <msh:autoComplete hideLabel="true" vocName="vocFrequencyUnit" label="раза в " property="drugForm1.frequencyUnit" size="10"/>
	          <msh:autoComplete hideLabel="true" vocName="vocDrugAmountUnit" label="ед." property="drugForm1.amountUnit" size="10" />
	          <msh:autoComplete hideLabel="true" vocName="vocDurationUnit" label="ед." property="drugForm1.durationUnit" guid="32568-8802-467d-b205-715fb379b018" size="10" />
	          <td>        	
              <input type="button" name="subm" onclick="prepareDrugRow();" value="Добавить" tabindex="4" />
              </td>
          
	        </msh:row>
	     <table id="drugTable">
        <tbody id="adddrugElements">
        </tbody>
        </table>
	    </msh:panel>
	    </msh:ifFormTypeIsCreate>
	    </div>
	    <%-- </msh:ifInRole> --%>
        <%-- --------------------------------------------------Конец блока "Лекарственное обеспечение" --%>
        <%-- --------------------------------------------------Начало блока "Лабораторные анализы" ------ --%>
       <msh:ifFormTypeIsCreate formName="pres_prescriptListForm"> 
        <msh:panel styleId="border">
        <msh:row>
        	<msh:separator label="Лабораторные исследования" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="labTable">
        <tbody id="addlabElements">
    		
			<tr>
			<msh:textField property="labDate" label="Дата " size="10"/>
			<msh:autoComplete property="labServicies" label="Исследование" vocName="labMedService" horizontalFill="true" size="90"/>
			<msh:ifFormTypeIsNotView formName="pres_prescriptListForm">
			<td>        	
            <input type="button" name="subm" onclick="prepareLabRow('lab');" value="Добавить" tabindex="4" />
            </td>
            </msh:ifFormTypeIsNotView>
            </tr>
            <tr>
				<msh:autoComplete property="labCabinet" label="Место забора" parentAutocomplete="labServicies" vocName="funcMedServiceRoom" size='20' fieldColSpan="3" horizontalFill="true" />
			</tr>
           </tbody>
    		</table>
    		</td></tr>
        </msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
         <%-- --------------------------------------------------Конец блока "Лабораторные анализы" ------ --%>
        <%-- --------------------------------------------------Начало блока "Функциональная диагностика" ------ --%>
         <msh:ifFormTypeIsCreate formName="pres_prescriptListForm"> 
        <msh:panel styleId="border">
       	 <msh:row>
        	<msh:separator label="Функциональные исследования" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="funcTable">
        <tbody id="addfuncElements">
    		<tr>
    			<msh:textField property="funcDate" label="Дата " size="10"/>
    			<msh:autoComplete property="funcServicies" label="Исследование" vocName="funcMedService" horizontalFill="true" size="90" />
    			<td>        	
	            <input type="button" name="subm" onclick="prepareLabRow('func');" value="Добавить" tabindex="4" />
	            </td>
			 </tr>
			 <tr>
			<msh:autoComplete property="funcCabinet" label="Кабинет" parentAutocomplete="funcServicies" fieldColSpan="3" vocName="funcMedServiceRoom" size='20' horizontalFill="true" />
			</tr>
			<msh:ifFormTypeIsNotView formName="pres_prescriptListForm">
			</msh:ifFormTypeIsNotView>
           </tbody>
    		</table>
    		</td></tr></msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
        <msh:panel>
        <%-- -- --------------------------------------------------Конец блока "Функциональная диагностика" ------ --%>
         
          <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        	<msh:label property="createUsername" label="пол-ль"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редакт."/>
        	<msh:label property="editTime" label="время"/>
        	<msh:label property="editUsername" label="пол-ль"/>
        </msh:row>                
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <tags:dir_medService name="1" table="PRICEMEDSERVICE" title="Прейскурант" functionAdd="addRowWithDir" addParam="priceList"/>
    
    <msh:ifFormTypeIsView formName="pres_prescriptListForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <msh:ifInRole roles="/Policy/Mis/Prescription/DrugPrescription/View" guid="bf331972-44d3-4b35-9f3e-627a9be109e8">
    	<tags:pres_prescriptByList field="pl.id='${param.id}'" />
      </msh:ifInRole>
      
    </msh:ifFormTypeIsView> 
  </tiles:put>
  <tiles:put name="side" type="string">
  	<msh:ifFormTypeIsCreate formName="pres_prescriptListForm">
  		<msh:sideMenu title="Шаблоны">
  			<%-- <msh:sideLink action=" javascript:shownewTemplatePrescription()" name="ЛН на основе шаблона" guid="a2f380f2-f499-49bf-b205-cdeba65f4e12" title="ЛН на основе шаблона" /> --%>
  			<msh:sideLink action=" javascript:showaddTemplatePrescription()" name="Назначения из шаблона" guid="a2f380f2-f499-49bf-b205-cdeba65f8888" title="Добавить назначения из шаблона" />
  		</msh:sideMenu>
  		<tags:templatePrescription record="2" parentId="${param.id}" name="add" />
  		<tags:pres_vocPrescTypes title="Выбор типа листа назначения" name="check" parentType = "medCase" parentID="${param.id}"/>
  	</msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsView formName="pres_prescriptListForm" guid="d4c560e9-6ddb-4cf2-9375-4caf7f0d3fb8">
      <msh:sideMenu title="Лист назначений" guid="2742309d-41bf-4fbe-9238-2f895b5f79a9">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pres_prescriptList" name="Удалить" roles="/Policy/Mis/Prescription/Prescript/Delete" guid="99bf20e3-4292-4554-bd60-051aa4338ee1" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="9825ef2b-1d4b-4070-b035-b6707a878e5c">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-pres_drugPrescription" name="Лекарственное средство" roles="/Policy/Mis/Prescription/DrugPrescription/View" guid="f5549341-6246-4cc4-8369-6f7b04931f2a" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-pres_dietPrescription" name="Диету" guid="71dca8ec-ccdf-4f2a-88c7-750cbc00b045" roles="/Policy/Mis/Prescription/DietPrescription/View" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentPrepareCreate-pres_servicePrescription" name="Медицинскую услугу" guid="3bb119f6-39d0-4bf4-9198-48f90e56f944" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ModePrescription/View" params="id" action="/entityParentPrepareCreate-pres_modePrescription" name="Режим" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="4943cb98-adb2-4c2d-9668-e973ee0ed67f">
        <msh:sideLink key="ALT+9" action="/entityParentListRedirect-pres_prescriptList" name="⇧К списку сводных листов назначений" guid="07f2bb72-26b3-4609-a19a-7dffebdd0171" params="id" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="9793b3d9-bf76-4b96-b4c5-300b97c01753">
        <msh:sideLink action="/js-pres_prescriptList-print" name="Листа назначений" params="id" guid="503861b9-a4ed-4098-97aa-4c89b8a977bb" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

