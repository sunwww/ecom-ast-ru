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
		document.forms['pres_prescriptListForm'].action="javascript:checkLabs()";
		var num=0;
		var labNum=0;
		var funcNum=0;
		var labList="";

		onload=function () {
			PrescriptionService.checkMedCaseEmergency($('medCase').value, "medCase", { 
	            callback: function(aResult) { 
	            	if(!aResult) { 
	            	$('presType1').disabled = "true";
	            	$('presType2').checked = "true";
	            	$('ifEmergencyDisabled').innerHTML="<p style=color:red>В данном случае запрещено создавать экстренные назначения!</p>"
	            	$('tdPresType1').style.display = "none";

	            	} else {
	            		$('presType1').checked = "true";
	            		isChecked(1);
	            		}
	           } 
			} 
	      	); 
			
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
		
		function checkLabs() {
			labList="";
			if ($('presType1').checked) {
				$('prescriptType').value=5; 
			} 
			if ($('labServicies')) {
				writeServicesToList('lab');
			}
			if ($('funcServicies')) {
				writeServicesToList('func');
			}
			$('labList').value=labList ;
		//	alert (labList);
			document.forms['pres_prescriptListForm'].action=oldaction ;
			document.forms['pres_prescriptListForm'].submit();
		}
		
		
		function addRow(type) {
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
	  	td1.innerHTML = "<span>Дата: </span><input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+$(type+'Date').value+"   ' size = '10' />";
	   	td2.innerHTML += dt2+"<span>. Кабинет: "+$(type+'CabinetName').value+"</span>" ;
	   	td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='-' />";
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
      <msh:panel colsWidth="1%,1%,1%,97%">
      <msh:ifFormTypeIsNotView formName="pres_prescriptListForm">
       <msh:row> 
		<td id="ifEmergencyDisabled">
        <label>Тип назначения: </label>
        </td>
        <td id="tdPresType1">
        <input type="radio" id = "presType1" name="presType" value="1" onclick="isChecked(1)">Экстренное
        </td>
        <td id="tdPresType2">
        <input type="radio" id = "presType2" name="presType" value="2" onclick="isChecked(2)" >Плановое
        </td>
		</msh:row>
      <msh:row>
       	<msh:autoComplete vocName="prescriptTypeNotEmergency" property="prescriptType" label="Тип планового назначения" guid="3a3eg4d1b-8802-467d-b205-711tre18" horizontalFill="true" fieldColSpan="3" size="30" />
      </msh:row>
 </msh:ifFormTypeIsNotView>
      <msh:ifFormTypeIsView formName="pres_prescriptListForm">
     	<msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения" guid="3a3eg4d1b-8802-467d-b205-711tre18" horizontalFill="true" fieldColSpan="1" size="30" viewOnlyField="true"/>
        </msh:row>
     </msh:ifFormTypeIsView>
        <msh:row guid="154fb2a0-a3ac-4034-9cbb-087444dbe299">
          <msh:textArea rows="2" property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" guid="f5338dbf-03ae-4c9c-a2ee-e6a3cc240dff" />
        </msh:row>
        <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="workFunction" label="Назначил" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
<%--         <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="prescriptType" label="Тип назначений" vocName="vocPrescriptType" fieldColSpan="3" horizontalFill="true" />
        </msh:row> --%>
        
        <msh:row>
        	<msh:separator label="Режим" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="modeForm.planStartDate" label="Дата начала" size="7"/>
          <msh:autoComplete vocName="vocModePrescription" property="modeForm.modePrescription" label="Режим" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Диета" colSpan="10"/>
        </msh:row>
        <msh:row guid="b556ehb-b971-441e-9a90-53217">
        	<msh:textField property="dietForm.planStartDate" label="Дата начала" size="7"/>
          <msh:autoComplete viewAction="entityView-diet_diet.do" vocName="Diet" 
          property="dietForm.diet" label="Диета" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        </msh:panel>
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
	        </msh:row>
	        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
	        	<msh:textField property="drugForm2.planStartDate" label="Дата начала" size="7" hideLabel="true"/>
	          <msh:autoComplete  vocName="vocDrugClassify" property="drugForm2.drug" label="Лек. препарат" guid="3a3eg4d1b-8802-467d-b205-715fb379b018" size="40" fieldColSpan="1" hideLabel="true"/>
	          <msh:textField property="drugForm2.frequency" label="Частота" size="7" hideLabel="true" />
	          <msh:textField label="Прием" property="drugForm2.amount"   size="3" hideLabel="true"/>
	          <msh:textField property="drugForm2.duration" label="Продолжительность"  size="3" hideLabel="true"/>
	        </msh:row>
	        <msh:row guid="b556ehb-b971-441e-9a90-5194a8019c07">
	        
	          <msh:autoComplete vocName="vocDrugMethod" label="Способ введ." property="drugForm2.method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true"  hideLabel="true"/>
	          <msh:autoComplete hideLabel="true" vocName="vocFrequencyUnit" label="раза в " property="drugForm2.frequencyUnit" size="10"/>
	          <msh:autoComplete hideLabel="true" vocName="vocDrugAmountUnit" label="ед." property="drugForm2.amountUnit" size="10" />
	          <msh:autoComplete hideLabel="true" vocName="vocDurationUnit" label="ед." property="drugForm2.durationUnit" guid="32568-8802-467d-b205-715fb379b018" size="10" />
	        </msh:row>
	        
        </msh:panel>
        
        <!-- --------------------------------------------------Начало блока "Лабораторные анализы" ------ -->
       <msh:ifFormTypeIsCreate formName="pres_prescriptListForm"> 
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
			<msh:ifFormTypeIsNotView formName="pres_prescriptListForm">
			<td>        	
            <input type="button" name="subm" onclick="addRow('lab');" value="+" tabindex="4" />
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
         <msh:ifFormTypeIsCreate formName="pres_prescriptListForm"> 
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
	            <input type="button" name="subm" onclick="addRow('func');" value="+" tabindex="4" />
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
        <!-- --------------------------------------------------Конец блока "Функциональная диагностика" ------ -->
         
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
  			<msh:sideLink action=" javascript:showaddTemplatePrescription(1,&quot;.do&quot;)" name="Назначения из шаблона" guid="a2f380f2-f499-49bf-b205-cdeba65f4e12" title="Добавить назначения из шаблона" />
  		</msh:sideMenu>
  		<%--<tags:templatePrescription record="1" parentId="${param.id}" name="add" /> --%>
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

