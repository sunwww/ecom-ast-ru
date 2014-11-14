<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>

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

 		onload=function () {			
 	            	if ($('labList').value!="")
	            		{
	            		PrescriptionService.getPresLabTypes($('labList').value, {
	            			callback: function(aResult2) {
	            				alert ("aResult is : " +aResult2);
	            				if (aResult2) {	            					
	            					var resultList = aResult2.split('#');
	            					if (resultList.length>0) {
	            						for (var i=0; i<resultList.length;i++) {
	            							var resultRow = resultList[i].split(':');
	            							if (resultRow[0]!="" && resultRow[0]!=null)
	            								{	            	
	            								/*
	            								0 - ms.ID
	            								1 - ms. code+name
	            								2 - ms.type
	            								3 - date
	            								4 - cabinetcode+name
	            								*/
	            								addRow(resultRow);
	            								
	            								}
	            						}
	            					}
	            				}
	            			}
	            		}
	            				);
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
				var curDate = document.getElementById(type+'Date'+typeNum);
				var curMethod = document.getElementById(type+'Method'+typeNum);
				var curAmount = document.getElementById(type+'Amount'+typeNum);
				var curDuration = document.getElementById(type+'Duration'+typeNum);
				var curFrequency = document.getElementById(type+'Frequency'+typeNum);
				var curAmountUnit = document.getElementById(type+'AmountUnit'+typeNum);
				var curDurationUnit = document.getElementById(type+'DurationUnit'+typeNum);
				var curFrequencyUnit = document.getElementById(type+'FrequencyUnit'+typeNum);
				if (curDrug.value != "" & curDate.value != "") {	
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
	         }
	     }
		alert("In addRow DrugList = "+drugList);
		}
		
		function checkLabs() {
			labList="";
/* 			if ($('presType1').checked) {
				$('prescriptType').value=5; 
			}  */
			if ($('labServicies')) {
				writeServicesToList('lab');
			}
			if ($('funcServicies')) {
				writeServicesToList('func');
			}
			writeDrugsToList();
			$('labList').value=labList ;
			$('drugList').value = drugList;
			alert("labList = "+$('labList').value);
			alert("drugList = "+$('drugList').value);
			document.forms['pres_templateForm'].action=oldaction ;
			document.forms['pres_templateForm'].submit();
		}
		
		function addRowLabs(type) { //Добавление исследования 
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
								alert("Уже существует такое исследование!!!");
								return;
						//	}
						}
					}
					checkNum+=1;
			}
			}
			
			num+=1;
		    // Считываем значения с формы 
		    
		    var nameId = document.getElementById(type+'Servicies').value;
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
		    var dt="<input id='"+type+"Service"+num+"' value='"+$(type+'Servicies').value+"' type='hidden' name='"+type+"Service"+num+"' horizontalFill='true' size='90' readonly='true' />";
		    var dt2="<input id='"+type+"Cabinet"+num+"' value='"+$(type+'Cabinet').value+"' type='hidden' name='"+type+"Cabinet"+num+"' horizontalFill='true' size='20' readonly='true' />";
		    
		    td2.innerHTML = dt+"<span>"+$(type+'ServiciesName').value+"</span>" ;
		  	td1.innerHTML = "<span>Дата: </span><input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+$(type+'Date').value+"' size = '10' />";
		   	td2.innerHTML += dt2+"<span>. Кабинет: "+$(type+'CabinetName').value+"</span>" ;
		   	td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
		   	new dateutil.DateField($(type+'Date'+num));
						   
			if (type=='lab') {
				labNum = num;
			} else if (type=='func'){
				funcNum = num;
			}
		}
		
		
		function addRow(resultRow) {
			/*
			0 - ms.ID
			1 - ms. code+name
			2 - ms.type
			3 - date
			4 - cabinetcode+name
			*/
			//alert ("addRow, "+resultRow);
			var aID = resultRow[0]; 
			var thisServiceName = resultRow[1];
			var type = resultRow[2];
			var thisDate = resultRow[3];
			var thisCabinetName = resultRow[4];
			var thisCabinet = thisCabinetName.split(" ");
			
			
		if (type=='LABSURVEY') {
			type='lab';
			num = labNum;
		} else if (type=='DIAGNOSTIC') {
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
	    var thisDate, thisCabinet;
	    // Наполняем ячейки 
	    var dt="<input id='"+type+"Service"+num+"' value='"+aID+"' type='hidden' name='"+type+"Service"+num+"' horizontalFill='true' size='90' readonly='true' />";
	    var dt2="<input id='"+type+"Cabinet"+num+"' value='"+thisCabinet[0]+"' type='hidden' name='"+type+"Cabinet"+num+"' horizontalFill='true' size='20' readonly='true' />";
	    
	    td2.innerHTML = dt+"<span>"+thisServiceName+"</span>" ;
	  	td1.innerHTML = "<span>Дата: </span><input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+thisDate+"' size = '10' />";
	   	td2.innerHTML += dt2+"<span>. Кабинет: "+thisCabinetName+"</span>" ;
	   	td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
	   	new dateutil.DateField($(type+'Date'+num));
					   
		if (type=='lab') {
			labNum = num;
		} else if (type=='func'){
			funcNum = num;
		}
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
		function addDrugRow() { // Добавляем лек. обеспечение 
			var type = "drug";
			num = drugNum;
		
		num+=1;
	    
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
	    var dt2="<input id='"+type+"Drug"+num+"' value='"+$('drugForm1.drug').value+"' type='hidden' name='"+type+"Drug"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //method 
	    dt2+="<input id='"+type+"Method"+num+"' value='"+$('drugForm1.method').value+"' type='hidden' name='"+type+"Method"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //frequency 
	    var dt3="<input id='"+type+"Frequency"+num+"' value='"+$('drugForm1.frequency').value+"' type='hidden' name='"+type+"Frequency"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    // frequency unit 
	    dt3+="<input id='"+type+"FrequencyUnit"+num+"' value='"+$('drugForm1.frequencyUnit').value+"' type='hidden' name='"+type+"FrequencyUnit"+num+"' size='10' readonly='true' />";
	    // Amount 
	    var dt4="<input id='"+type+"Amount"+num+"' value='"+$('drugForm1.amount').value+"' type='hidden' name='"+type+"Amount"+num+"' horizontalFill='true' size='20' readonly='true' />";
	    //Amount unit 
	    dt4+="<input id='"+type+"AmountUnit"+num+"' value='"+$('drugForm1.amountUnit').value+"' type='hidden' name='"+type+"AmountUnit"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //Duration 
	    var dt5="<input id='"+type+"Duration"+num+"' value='"+$('drugForm1.duration').value+"' type='hidden' name='"+type+"Duration"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    //Duration unit 
	    dt5+="<input id='"+type+"DurationUnit"+num+"' value='"+$('drugForm1.durationUnit').value+"' type='hidden' name='"+type+"DurationUnit"+num+"' horizontalFill='true' size='10' readonly='true' />";
	    	   
	    td1.innerHTML = "<span>Дата: </span><input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+$('drugForm1.planStartDate').value+"' size = '10' />";	    
	    td2.innerHTML = dt2+"<span>. "+$('drugForm1.drugName').value+"</span> Способ введения: <span>"+$('drugForm1.methodName').value+"</span>" ;
	  	td3.innerHTML = dt3+"<span>. Частота: "+$('drugForm1.frequency').value +" "+$('drugForm1.frequencyUnitName').value+"</span>";
	    td4.innerHTML = dt4+"<span>. Дозировка: "+$('drugForm1.amount').value+" "+$('drugForm1.amountUnitName').value+"</span>" ;
	   	td5.innerHTML = dt5+"<span>. Продолжительность: "+$('drugForm1.duration').value+" "+$('drugForm1.durationUnitName').value+"</span>";
	    td6.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
	   	new dateutil.DateField($(type+'Date'+num));
					   
	   	drugNum = num;
	   	writeDrugsToList();
	}
	
		</script>
		</msh:ifFormTypeIsNotView>
		</tiles:put>

  <tiles:put name="body" type="string">
    <!-- 
    	  - Шаблон листа назначений
    	  -->
    <msh:form action="/entitySaveGoView-pres_template.do" defaultField="name" guid="ea411ae6-6822-4cbd-a7f3-b8f6cfa1beba">
      <msh:hidden property="id" guid="ba8ca3c4-0044-44ab-bb12-a75e3441fae2" />
      <msh:hidden property="saveType" guid="efb8a9d9-e3c6-4f03-87bc-f0cccb820e89" />
      <msh:hidden property="labList" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:hidden property="drugList" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:panel>
     	<msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения" guid="3a3eg4d1b-8802-467d-b205-711tre18" horizontalFill="true" fieldColSpan="1" size="30" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="name" label="Название"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="154fb2a0-a3ac-4034-9cbb-087444dbe299">
          <msh:textArea rows="2" property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" guid="f5338dbf-03ae-4c9c-a2ee-e6a3cc240dff" />
        </msh:row>
        <msh:row guid="b28cb409-df33-4406-bef7-9a79f93b49dd">
          <ecom:oneToManyOneAutocomplete label="Категории шаблона" property="categories" vocName="vocTemplateCategory" colSpan="4" />
        </msh:row>        

       <msh:ifFormTypeIsCreate formName="pres_templateForm">
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
	        <msh:row guid="b556ehb-b971-441e-9a90-53217">
	        	
	          <msh:autoComplete viewAction="entityView-diet_diet.do" vocName="Diet" 
	          property="dietForm.diet" label="Диета" horizontalFill="true" fieldColSpan="9" />
	        </msh:row>
        </msh:ifInRole>
        <msh:ifInRole roles="/Policy/Mis/Prescription/Template/DrugPrescription/Create">
  <!-- -------------------------Начало блока "ЛЕкарственное обеспечение" -->
          <msh:ifFormTypeIsNotView formName="pres_templateForm">  
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
	        	<msh:textField property="drugForm1.planStartDate" label="Дата начала" size="7" hideLabel="true"/>
	          <msh:autoComplete  vocName="vocDrugClassify" property="drugForm1.drug" label="Лек. препарат" guid="3a3eg4d1b-8802-467d-b205-715fb379b018" size="40" fieldColSpan="1" hideLabel="true"/>
	          <msh:textField property="drugForm1.frequency" label="Частота" size="7" hideLabel="true" />
	          <msh:textField label="Прием" property="drugForm1.amount"   size="3" hideLabel="true"/>
	          <msh:textField property="drugForm1.duration" label="Продолжительность"  size="3" hideLabel="true"/>
	        </msh:row>
	        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
	        <td></td>
	          <msh:autoComplete vocName="vocDrugMethod" label="Способ введ." property="drugForm1.method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true"  hideLabel="true"/>
	          <msh:autoComplete hideLabel="true" vocName="vocFrequencyUnit" label="раза в " property="drugForm1.frequencyUnit" size="10"/>
	          <msh:autoComplete hideLabel="true" vocName="vocDrugAmountUnit" label="ед." property="drugForm1.amountUnit" size="10" />
	          <msh:autoComplete hideLabel="true" vocName="vocDurationUnit" label="ед." property="drugForm1.durationUnit" guid="32568-8802-467d-b205-715fb379b018" size="10" />
	          
	         
	          <td>        	
            <input type="button" name="subm" onclick="addDrugRow();" value="Добавить" tabindex="4" />
            </td>
          
	        </msh:row>
	     <table id="drugTable">
        <tbody id="adddrugElements">
        </tbody>
        </table>
	    </msh:panel>
	    </msh:ifFormTypeIsNotView>
        <!-- --------------------------------------------------Конец блока "Лекарственное обеспечение" -->

        </msh:ifInRole>
        </msh:ifFormTypeIsCreate>
      <!-- --------------------------------------------------Начало блока "Лабораторные анализы" ------ -->
       <msh:ifFormTypeIsCreate formName="pres_templateForm"> 
        <msh:panel>
        <msh:row>
        	<msh:separator label="Лабораторные исследования" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="labTable">
        <tbody id="addlabElements">
    		
			<tr>
			<msh:textField property="labDate" label="Дата " size="10"/>
			<msh:autoComplete property="labServicies" label="Лабораторный анализ" vocName="labMedService" horizontalFill="true" size="90"/>
			<msh:ifFormTypeIsNotView formName="pres_templateForm">
			<td>        	
            <input type="button" name="subm" onclick="addRowLabs('lab');" value="+" tabindex="4" />
            </td>
            </msh:ifFormTypeIsNotView>
            </tr>
            <tr>
    		
			<msh:autoComplete property="labCabinet" label="Кабинет" parentAutocomplete="labServicies" vocName="funcMedServiceRoom" size='20' fieldColSpan="3" horizontalFill="true" />
			</tr>
           </tbody>
    		</table>
    		</td></tr>
        </msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
         <!-- --------------------------------------------------Конец блока "Лабораторные анализы" ------ -->
        <!-- --------------------------------------------------Начало блока "Функциональная диагностика" ------ -->
         <msh:ifFormTypeIsCreate formName="pres_templateForm"> 
        <msh:panel>
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
	            <input type="button" name="subm" onclick="addRowLabs('func');" value="+" tabindex="4" />
	            </td>
			 </tr>
			 <tr>
			<msh:autoComplete property="funcCabinet" label="Кабинет" parentAutocomplete="funcServicies" fieldColSpan="3" vocName="funcMedServiceRoom" size='20' horizontalFill="true" />
			</tr>
			<msh:ifFormTypeIsNotView formName="pres_templateForm">
			</msh:ifFormTypeIsNotView>
           </tbody>
    		</table>
    		</td></tr></msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
          <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="10"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
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
        	<msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
        </msh:row>              
        
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="pres_templateForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <tags:templatePrescription record="1" parentId="${param.id}" name="new" />
      <msh:ifInRole roles="/Policy/Mis/Prescription/Template/DrugPrescription/View" guid="bf331972-44d3-4b35-9f3e-627a9be109e8">
        <msh:section title="Назначения" guid="c33b9308-477d-4d6b-8d7b-e290e7de2530">
          <ecom:parentEntityListAll formName="pres_prescriptionForm" attribute="prescription" guid="f9491526-b5e6-4a92-ba34-0d9892326de7" />
          <msh:table name="prescription" action="entitySubclassView-pres_template_prescription.do" idField="id" guid="95f63378-5b89-4f15-ad12-ba3f87976c52">
            <msh:tableColumn columnName="#" property="id" guid="24ec4672-8e09-4ec3-80bf-c2e7cb489806" />
            <msh:tableColumn columnName="Описание назначения" property="descriptionInfo" guid="6a4b783f-f665-4f8a-aff0-3e918f97914d" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="pres_templateForm" guid="af3d88cd-60b5-4bba-a85d-ac2334f43161" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_templateForm" guid="d4c560e9-6ddb-4cf2-9375-4caf7f0d3fb8">
      <msh:sideMenu title="Шаблон" guid="2742309d-41bf-4fbe-9238-2f895b5f79a9">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-pres_template" name="Изменить" roles="/Policy/Mis/Prescription/Template/Edit" guid="0f0781cd-81dd-4da2-aba5-67eab700b161" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-pres_template" name="Удалить" roles="/Policy/Mis/Prescription/Template/Delete" guid="99bf20e3-4292-4554-bd60-051aa4338ee1" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="9825ef2b-1d4b-4070-b035-b6707a878e5c">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-pres_template_drugPrescription" name="Лекарственное средство" roles="/Policy/Mis/Prescription/Template/DrugPrescription/View" guid="f5549341-6246-4cc4-8369-6f7b04931f2a" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-pres_template_dietPrescription" name="Диету" guid="71dca8ec-ccdf-4f2a-88c7-750cbc00b045" roles="/Policy/Mis/Prescription/Template/DietPrescription/View" />
        <msh:sideLink roles="/Policy/Mis/Prescription/Template/ModePrescription/View" params="id" action="/entityParentPrepareCreate-pres_template_modePrescription" name="Режим" />
        <msh:sideLink action=" javascript:shownewTemplatePrescription(1,&quot;.do&quot;)" name="Назначения на основе существующего шаблона" title="Создать шаблон лист назначения на основе существующего шаблона" guid="c6e48b9d-d1cf-4731-af04-3f8fe356717e" />
      </msh:sideMenu>
      <tags:template_menu currentAction="prescriptions"/>
    </msh:ifFormTypeIsView>
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

