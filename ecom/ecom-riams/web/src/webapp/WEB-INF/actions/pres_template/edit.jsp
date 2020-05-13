<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>

<msh:ifFormTypeAreViewOrEdit formName="pres_templateForm">
<msh:ifFormTypeIsNotView formName="pres_templateForm">

		<script type="text/javascript">
		var oldaction = document.forms['pres_templateForm'].action ;
		document.forms['pres_templateForm'].action="javascript:checkLabs()";
		var num=0;
		var labNum=0;
		var funcNum=0;
		var drugNum=0;
		var labList="";
		var drugList="";
		
		function addPrescription(aLabID, aLabDepartment, aLabCabinet) {
			var aID = $('id').value;
			PrescriptionService.addPrescriptionToList(aID, aLabID, aLabDepartment, aLabCabinet,"ServicePrescription");
		}
		function deletePrescription(aMedService) {
			PrescriptionService.removePrescriptionFromList($('id').value,aMedService,null);
		}
 		onload=function () {
           	if ($('labList').value.length>0)
          		{
           	//	alert ("labList= "+$('labList').value);
          		PrescriptionService.getPresLabTypes($('labList').value, 0,{
          			callback: function(aResult2) {
          				//alert ("aResult is : " +aResult2);
          				if (aResult2) {	            					
          					var resultList = aResult2.split('#');
          					if (resultList.length>0) {
          						for (var i=0; i<resultList.length;i++) {
          							var resultRowID = resultList[i].split(':');
          							if (resultRowID[0]!="" && resultRowID[0]!=null)
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
           	if ($('id').value!=null&&$('id').value>0) {
           		PrescriptionService.getLabListFromTemplate($('id').value,null,{
           			callback: function(aResult) {
           				fillFormFromTemplate(aResult);
           			}
           		});
           	}
			
		}

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
				var curService = $(type+'Service'+typeNum).value;
			//	var curDate = $(type+'Date'+typeNum).value;
				var curCabinet = $(type+'Cabinet'+typeNum)?$(type+'Cabinet'+typeNum).value:"";
				var curDepartment = $(type+'Department'+typeNum)?$(type+'Department'+typeNum).value:"";
				
				if (curService.value != "") {	
		            // Проверка на дубли 
		            if ($(type+'Servicies').value==curService) {
		            	isDoubble=1;	
		            }
				}
					
			}
       		typeNum-=1;
	 	}
		if (isDoubble==0) {
			if ($(type+'Servicies').value != "") {
			   	labList+=$(type+'Servicies').value;
	            labList+=":";
	            //labList+=$(type+'Date').value;
	            labList+=":";
	            labList+=$(type+'Cabinet')?$(type+'Cabinet').value:"";
	            labList+=":";
	            labList+=$(type+'Department')?$(type+'Department').value:"";
	            labList+="#";
	         }
	     }
		}
		
		function writeDrugsToList() {
			drugList="";
			var type = "drug";
			var typeNum = 0;
		if (type=='lab') {
			typeNum = labNum;
		} else if (type=='func') {
			typeNum = funcNum;
		} else if (type=="drug") {
			typeNum = drugNum;
		}
		var isDoubble=0;
		while (typeNum>0) {
			if (document.getElementById(type+"Element"+typeNum)) {
				var curDrug = $(type+'Drug'+typeNum).value;
			//	var curDate = document.getElementById(type+'Date'+typeNum);
				var curMethod = $(type+'Method'+typeNum);
				var curAmount = $(type+'Amount'+typeNum);
				var curDuration = $(type+'Duration'+typeNum);
				var curFrequency = $(type+'Frequency'+typeNum);
				var curAmountUnit = $(type+'AmountUnit'+typeNum);
				var curDurationUnit = $(type+'DurationUnit'+typeNum);
				var curFrequencyUnit = $(type+'FrequencyUnit'+typeNum);
				if (curDrug.value != "") {	
					//Формат строки - name:date:method:freq:freqU:amount:amountU:duration:durationU# 
		            drugList+=curDrug;
		            drugList+=":";
		      //      drugList+=curDate.value;
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
		            // Проверка на дубли 
		            if ($('drugForm1.drug').value==curDrug.value) {
		            	isDoubble=1;	
		            }
				}
					
			}
       		typeNum-=1;
	 	}
		if (isDoubble==0) {
			if ($('drugForm1.drug').value != "") {
				drugList+=$('drugForm1.drug').value;
	            drugList+=":";
	       //     drugList+=$('drugForm1.planStartDate').value;
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
	         }
	     }
	//	alert("In addRow DrugList = "+drugList);
		}
		
		function checkLabs() {
			labList="";
			if ($('labServicies')) {
				writeServicesToList('lab');
			}
			if ($('funcServicies')) {
				writeServicesToList('func');
			}
			writeDrugsToList();
			$('labList').value=labList ;
			$('drugList').value = drugList;
	//		alert("labList = "+$('labList').value); 
	//		alert("drugList = "+$('drugList').value); 
			document.forms['pres_templateForm'].action=oldaction ;
			document.forms['pres_templateForm'].submit();
		}
		
		
		function prepareLabRow(type) {
			
			if ($(type+'Servicies').value==""){
				alert("Выбирите услугу!");
				return;
			}
			// Проверим на дубли 
			if (isDouble(type, $(type+'Servicies').value)==1) {
				alert("В шаблоне уже назначено такое исследование!");
				return;
			}
			var aLabID = $(type+'Servicies').value;
			var aLabName =$(type+'ServiciesName').value;
		//	var aLabDate = $(type+'Date').value;
			var aLabCabinet = $(type+'Cabinet').value;
			var aLabCabinetName = $(type+'CabinetName')?$(type+'CabinetName').value:"";
			var aLabDepartment = $(type+'Department').value;
			var aLabDepartmentName = $(type+'DepartmentName')?$(type+'DepartmentName').value:"";
			
			var aData = type+":"+aLabID+":"+aLabName+"::"+aLabCabinet+":"+aLabCabinetName+":"+aLabDepartment+":"+aLabDepartmentName; 
			//alert ("prepare - "+aLabID+":"+aLabCabinet+":"+aLabDepartment);
			addPrescription(aLabID, aLabDepartment, aLabCabinet);
			addRow(aData);
		}
		
		function isDouble (aType, aID) {
			if (aType=='lab') {
				num = labNum;
			} else if (aType=='func') {
				num = funcNum;
			} else {
				alert ("isDouble, Тип не указан!");
				return 1;
			}
			var checkNum = 1;
			if (num>0){
				while (checkNum<=num) {
					if ($(aType+'Service'+checkNum)) {
						if (aID==$(aType+'Service'+checkNum).value){
							return 1;
						}
					}
					checkNum+=1;
			}
			}
			return 0;
		}
		function prepare1Row(aID, aName) {
			//Add Lab from ++ 
			if (isDouble('lab', aID)==0) {
				addPrescription(aID, "", "");
				addRow("LABSURVEY:"+aID+":"+aName+":::::")
			} else {
				alert("В шаблоне уже назначено такое исследование!");
			}
			
		}
		function addRow(result) {
			var resultRow = result.split(":");
			/*
			0 - ms.type
			1 - ms.ID
			2 - ms. code+name
			3 - date
			4 - cabinetID
			5 - cabinetname
			6 - departmentID
			7 - departmentName
			*/
			var aLabType = resultRow[0];
			var aLabID = resultRow[1]; 
			var aLabName = resultRow[2];
		//	var aLabDate = resultRow[3];
			var aLabCabinet = resultRow[4]?resultRow[4]:"";
			var aLabCabinetName = resultRow[5]?resultRow[5]:"";
			var aLabDepartment = resultRow[6]?resultRow[6]:"";
			var aLabDepartmentName = resultRow[7]?resultRow[7]:"";
			
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
	    var td2 = document.createElement("TD");
	    td2.colSpan="2";
	    var td3 = document.createElement("TD");
	    
		 row.appendChild(td2);
		 row.appendChild(td3);
	   
	    // Наполняем ячейки 
	  
	    var dt=spanTag("",aLabName,"")+hiddenInput(type, "Service",num,aLabID,"");
	    var dt2=(aLabCabinet!=null&&aLabCabinet!='')?spanTag("Кабинет",aLabCabinetName,"")+hiddenInput(type, "Cabinet",num,aLabCabinet,""):"";
	    var dt3 =(aLabDepartment!=null&&aLabDepartment!='')?spanTag("Место забора", aLabDepartmentName,"") + hiddenInput(type,"Department",num,aLabDepartment,""):"";
	    td2.innerHTML = dt +dt2+dt3;
	   	td3.innerHTML = "<input type='button' name='subm' onclick='javascript:deletePrescription("+aLabID+");var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
	   
					   
		if (type=='lab') {
			labNum = num;
		} else if (type=='func'){
			funcNum = num;
		}
		$(type+'Servicies').value='';
		$(type+'ServiciesName').value='';
	}
		
		function hiddenInput(aType,aFld,aNum,aValue,aDefaultValue) {
			return "<input id='"+aType+aFld+aNum+"' name='"+aType+aFld+aNum+"' value='"+(aValue==null||aValue==""?aDefaultValue:aValue)+"' type='hidden' />"
		}
		function textInput(aLabel,aType,aFld,aNum,aValue,aDefaultValue,aSize) {
			return "<span>"+aLabel+"</span><input "+(+aSize>0?"size="+aSize:"")+" id='"+aType+aFld+aNum+"' name='"+aType+aFld+aNum+"' value='"+(aValue==null||aValue==""?aDefaultValue:aValue)+"' type='text' />"
		}
		function spanTag(aText,aValue,aDefaultValue) {
			var text = aText!=""?aText+": <b>":"<b>";
			return "<span>"+text+(aValue!=null&&aValue!=""?aValue.trim():aDefaultValue.trim())+"</b></span>. " ;
		}
		
		function isChecked(num) {
			if (num==1) {
				$('prescriptTypeLabel').style.display="none";
				$('prescriptTypeName').style.display="none";
				$('prescriptType').value=5;
			} else if (num==2){
				$('prescriptTypeLabel').style.display="block";
				$('prescriptTypeName').style.display="block";
			}
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
		    var aResult = aDrug+":"+aDrugName+"::"+aDrugMethod+":"+aDrugMethodName
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
	    var aDrugDate = aDrugArr[2];
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
	    
	//	 row.appendChild(td1);
		 row.appendChild(td2);
		 tbody.appendChild(row);
		 row.appendChild(td3);
		 row.appendChild(td4);
		 row.appendChild(td5);
		 row.appendChild(td6);
	    
	    // Наполняем ячейки 
	    //drug 
	    var dt2="<input id='"+type+"Drug"+num+"' value='"+aDrugID+"' type='hidden' name='"+type+"Drug"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //method 
	    dt2+="<input id='"+type+"Method"+num+"' value='"+aDrugMethod+"' type='hidden' name='"+type+"Method"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //frequency 
	    var dt3="<input id='"+type+"Frequency"+num+"' value='"+aDrugFrequency+"' type='hidden' name='"+type+"Frequency"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    // frequency unit 
	    dt3+="<input id='"+type+"FrequencyUnit"+num+"' value='"+aDrugFrequencyUnit+"' type='hidden' name='"+type+"FrequencyUnit"+num+"' size='10' readonly='true' />";
	    // Amount 
	    var dt4="<input id='"+type+"Amount"+num+"' value='"+aDrugAmount+"' type='hidden' name='"+type+"Amount"+num+"' horizontalFill='true' size='20' readonly='true' />";
	    //Amount unit 
	    dt4+="<input id='"+type+"AmountUnit"+num+"' value='"+aDrugAmountUnit+"' type='hidden' name='"+type+"AmountUnit"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //Duration 
	    var dt5="<input id='"+type+"Duration"+num+"' value='"+aDrugDuration+"' type='hidden' name='"+type+"Duration"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //Duration unit 
	    dt5+="<input id='"+type+"DurationUnit"+num+"' value='"+aDrugDurationUnit+"' type='hidden' name='"+type+"DurationUnit"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    	   
	 //   td1.innerHTML = "";//"<span>Дата: </span><input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+aDrugDate+"' size = '10' />";	    
	    td2.innerHTML = dt2+"<span> "+aDrugName+"</span> Способ введения: <span>"+aDrugMethodName+"</span>" ;
	  	td3.innerHTML = dt3+"<span>. Частота: "+aDrugFrequency +" "+aDrugFrequencyUnitName+"</span>";
	    td4.innerHTML = dt4+"<span>. Дозировка: "+aDrugAmount+" "+aDrugAmountUnitName+"</span>" ;
	   	td5.innerHTML = dt5+"<span>. Продолжительность: "+aDrugDuration+" "+aDrugDurationUnitName+"</span>";
	    td6.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
	   //	new dateutil.DateField($(type+'Date'+num));
					   
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
		</msh:ifFormTypeAreViewOrEdit>
		</tiles:put>

  <tiles:put name="body" type="string">
    <!-- 
    	  - Шаблон листа назначений
    	  -->
    <msh:form action="/entitySaveGoView-pres_template.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="labList" />
      <msh:hidden property="drugList" />
      <msh:hidden property="labCabinet" />
      <msh:panel>
     	<msh:row>
          <msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения" horizontalFill="true" fieldColSpan="1" size="30" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="name" label="Название"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:textArea rows="2" property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <ecom:oneToManyOneAutocomplete label="Категории шаблона" property="categories" vocName="vocTemplateCategory" colSpan="4" />
        </msh:row>        
        <msh:row>
          <ecom:oneToManyOneAutocomplete  viewAction="entityView-secgroup.do" label="Довер. группы" vocName="secGroup" property="secGroups" colSpan="4" />
        </msh:row>
         </msh:panel>
         <msh:panel>
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
        
        <msh:row>
        	<msh:submitCancelButtonsRow colSpan="3" labelSave="Сохранить данные заголовка"  />
        </msh:row>              
        
      </msh:panel>
        <msh:ifFormTypeAreViewOrEdit formName="pres_templateForm">
         <msh:panel>
       <msh:ifFormTypeIsNotView formName="pres_templateForm">
       <msh:ifInRole roles="/Policy/Mis/Prescription/Template/ModePrescription/Create">
	        <msh:row>
	        	<msh:separator label="Режим" colSpan="10"/>
	        </msh:row>
	        <msh:row>
	          <msh:autoComplete vocName="vocModePrescription" property="modeForm.modePrescription" label="Режим" horizontalFill="true" fieldColSpan="9" />
	        </msh:row>
        </msh:ifInRole>
        <msh:ifInRole roles="/Policy/Mis/Prescription/Template/DietPrescription/Create">
	        <msh:row>
	        	<msh:separator label="Диета" colSpan="10"/>
	        </msh:row>
	        <msh:row>
	        	
	          <msh:autoComplete viewAction="entityView-diet_diet.do" vocName="Diet" 
	          property="dietForm.diet" label="Диета" horizontalFill="true" fieldColSpan="9" size="100"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:ifInRole roles="/Policy/Mis/Prescription/Template/DrugPrescription/Create">

          
        <msh:panel styleId="border">
        <msh:row>
        	<msh:separator label="Лекарственные назначения" colSpan="20"/>
        </msh:row>
        <msh:row>
        		
        		<td>Лек.ср-во и способ введения</td>
        		<td>Частота</td>
        		<td>Дозировка</td>
        		<td>Продолжительность</td>
        </msh:row>
	        <msh:row>
	        <%-- 	<msh:textField property="drugForm1.planStartDate" label="Дата начала" size="7" hideLabel="true"/> --%>
	          <msh:autoComplete  vocName="vocDrugClassify" property="drugForm1.drug" label="Лек. препарат" size="40" fieldColSpan="1" hideLabel="true"/>
	          <msh:textField property="drugForm1.frequency" label="Частота" size="7" hideLabel="true" />
	          <msh:textField label="Прием" property="drugForm1.amount"   size="3" hideLabel="true"/>
	          <msh:textField property="drugForm1.duration" label="Продолжительность"  size="3" hideLabel="true"/>
	        </msh:row>
	        <msh:row>
	        
	          <msh:autoComplete vocName="vocDrugMethod" label="Способ введ." property="drugForm1.method" horizontalFill="true"  hideLabel="true"/>
	          <msh:autoComplete hideLabel="true" vocName="vocFrequencyUnit" label="раза в " property="drugForm1.frequencyUnit" size="10"/>
	          <msh:autoComplete hideLabel="true" vocName="vocDrugAmountUnit" label="ед." property="drugForm1.amountUnit" size="10" />
	          <msh:autoComplete hideLabel="true" vocName="vocDurationUnit" label="ед." property="drugForm1.durationUnit" size="10" />
	          
	         
	          <td>        	
            <input type="button" name="subm" onclick="prepareDrugRow();" value="Добавить" tabindex="4" />
            </td>
	        </msh:row>
	     <table id="drugTable">
        <tbody id="adddrugElements">
        </tbody>
        </table>
	    </msh:panel>
        </msh:ifInRole>
        </msh:ifFormTypeIsNotView>
       <msh:ifFormTypeIsNotView formName="pres_templateForm"> 
        <msh:panel>
        <msh:row>
        	<msh:separator label="Лабораторные исследования" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="labTable">
        <tbody id="addlabElements">
			<tr>
			<msh:autoComplete property="labServicies" label="Исследование" vocName="labMedService" parentId="0" size="90"/> 
			<msh:ifFormTypeIsNotView formName="pres_templateForm">
			<td>        	
            <input type="button" name="subm" onclick="prepareLabRow('lab');" value="Добавить" tabindex="4" />
	            <input type="button" name="subm" onclick="show1DirMedService();" value="++" tabindex="4" />
            </td>
            </msh:ifFormTypeIsNotView>
            </tr>
            <tr>
    			<msh:autoComplete property="labDepartment" vocName="departmentIntake" label="Место забора" size='20' fieldColSpan="3" horizontalFill="true" />
			</tr>
           </tbody>
    		</table>
    		</td></tr>
        </msh:row>
        </msh:panel>
        <msh:panel>
       	 <msh:row>
        	<msh:separator label="Функциональные исследования" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="funcTable">
        <tbody id="addfuncElements">
    		<tr>
    			<msh:autoComplete property="funcServicies" label="Исследование" vocName="funcMedService" horizontalFill="true" size="90" />
    			<td>        	
	            <input type="button" name="subm" onclick="prepareLabRow('func');" value="Добавить" tabindex="4" />
	            </td>
			 </tr>
			 <tr>
			<msh:autoComplete property="funcCabinet" label="Кабинет" parentAutocomplete="funcServicies" fieldColSpan="3" vocName="funcMedServiceRoom" size='20' horizontalFill="true" />
			</tr>
           </tbody>
    		</table>
    		</td></tr></msh:row>
        </msh:panel>
        
        </msh:ifFormTypeIsNotView >
        </msh:panel>
      </msh:ifFormTypeAreViewOrEdit>
    </msh:form>
    <msh:ifFormTypeIsView formName="pres_templateForm">
     
      
      <msh:ifInRole roles="/Policy/Mis/Prescription/Template/DrugPrescription/View">
        <msh:section title="Назначения">
          <ecom:parentEntityListAll formName="pres_prescriptionForm" attribute="prescription" />
          <msh:table name="prescription" action="/javascript:void()" idField="id">
            <msh:tableColumn columnName="#" property="id" />
            <msh:tableColumn columnName="Описание назначения" property="descriptionInfo" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="pres_templateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_templateForm">
      <msh:sideMenu title="Шаблон">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-pres_template" name="Изменить назначения" roles="/Policy/Mis/Prescription/Template/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-pres_template" name="Удалить" roles="/Policy/Mis/Prescription/Template/Delete" />
      </msh:sideMenu>
      <tags:template_menu currentAction="prescriptions"/>
    </msh:ifFormTypeIsView>
    <tags:templatePrescription record="2" name="add" />
    <tags:dir_medService name="1" table="MEDSERVICE" title="Услуги" functionAdd="prepare1Row" addParam="id" />
    <msh:sideLink action=" javascript:showaddTemplatePrescription()" name="Назначения на основе существующего шаблона" title="Создать шаблон лист назначения на основе существующего шаблона" />
  </tiles:put>
  
  <tiles:put name="style" type="string">
  	<style type="text/css">
         #borderTable {
         	border: 2px gray;
         }
         #borderTable td {
         	border: 1px solid gray;
         }
         #borderTable th{
         	border: 1px solid gray;
         }
         
        </style>
  </tiles:put>
</tiles:insert>

