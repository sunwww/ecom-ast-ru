<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
	<msh:ifFormTypeIsNotView formName="pres_servicePrescriptionForm">
	<script type="text/javascript">
	var oldaction = document.forms['pres_servicePrescriptionForm'].action ;
	document.forms['pres_servicePrescriptionForm'].action="javascript:checkLabs()";
	
	var num=0;
	var labNum=0;
	var funcNum=0;
	var labList="";
	
	function checkLabs() {
		if ($('labServicies')) {
			writeServicesToList('lab');
		}
		if ($('funcServicies')) {
			writeServicesToList('func');
		}
		
		$('labList').value=labList ;
//		alert (labList); 
		document.forms['pres_servicePrescriptionForm'].action=oldaction ;
		document.forms['pres_servicePrescriptionForm'].submit();
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
		
		function isChecked(num) {
			if (num==1) {
				$('prescriptTypeLabel').style.display="none";
				$('prescriptTypeName').style.display="none";
			} else {
				$('prescriptTypeLabel').style.display="block";
				$('prescriptTypeName').style.display="block";
			}
	}
		onload =function test() {
//		alert ("plID = "+$('prescriptionList').value);
		PrescriptionService.checkMedCaseEmergency($('prescriptionList').value, { 
            callback: function(aResult) { 
//           	alert("aResult = "+aResult);
            	if(!aResult) { 
            	$('presType1').disabled = "true";
            	$('presType2').checked = "true";
            	$('ifEmergencyDisabled').innerHTML="<p style=color:red>В данном случае запрещено создавать экстренные назначения!</p>"
            	$('tdPresType1').style.display = "none";

            	} else {
            		$('presType1').checked = "true";
            		isChecked(1);
            		$('prescriptType').value=5; //5 - экстренно 
            	}
           } 
		} 
      	); 
		
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
	    var td2 = document.createElement("TD");
	    var td3 = document.createElement("TD");
	    var td4 = document.createElement("TD");
	    
	    row.appendChild(document.createElement("TD"));
	    row.appendChild(document.createElement("TD"));
	    row.appendChild(td1);
	    row.appendChild(document.createElement("TD"));
	    row.appendChild(document.createElement("TD"));
	    row.appendChild(td2);
	    row.appendChild(document.createElement("TD"));
	    row.appendChild(document.createElement("TD"));
	    row.appendChild(td3);
	    row.appendChild(td4);
	    
	    // Наполняем ячейки 
	    var dt="<input id='"+type+"Service"+num+"' value='"+$(type+'Servicies').value+"' type='hidden' name='"+type+"Service"+num+"' horizontalFill='true' size='90' readonly='true' />";
	    var dt2="<input id='"+type+"Cabinet"+num+"' value='"+$(type+'Cabinet').value+"' type='hidden' name='"+type+"Cabinet"+num+"' horizontalFill='true' size='20' readonly='true' />";
	    td1.innerHTML = dt+"<span>"+$(type+'ServiciesName').value+"</span>" ;
	  	td2.innerHTML = "<input id='"+type+"Date"+num+"' name='"+type+"Date"+num+"' label='Дата' value='"+$(type+'Date').value+"' size = '10' />";
	   	td3.innerHTML = dt2+"<span>"+$(type+'CabinetName').value+"</span>" ;
	   	td4.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='-' />";
	   	new dateutil.DateField($(type+'Date'+num));
					   
		if (type=='lab') {
			labNum = num;
		} else if (type=='func'){
			funcNum = num;
		}
	}
			</script>
			</msh:ifFormTypeIsNotView>
			</tiles:put>

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение медицинской услуги
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-pres_servicePrescription.do" defaultField="id" title="Назначение медицинской услуги">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="prescriptionList" guid="8b852c-d5aa-40f0-a9f5-21dfgd6" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="labList" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:panel guid="panel" colsWidth="3">
        <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="prescriptSpecial" label="Назначил" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="5" horizontalFill="true"  />
        <td></td>
        </msh:row>
        
      	<msh:ifFormTypeIsNotView formName="pres_servicePrescriptionForm">
        <msh:row guid="b5srehb-b971-441e-9a90-53217">
        <td>
        <label>Тип назначения: </label>
        </td>
        <td id="ifEmergencyDisabled">
        </td>
        <td id="tdPresType1">
        <input type="radio" id = "presType1" name="presType" value="1" onclick="isChecked(1)">Экстренное
        </td>
        <td id="tdPresType2">
        <input type="radio" id = "presType2" name="presType" value="2" onclick="isChecked(2)" >Плановое
        </td>
      	<msh:autoComplete vocName="prescriptTypeNotEmergency" property="prescriptType" label="Тип планового назначения" guid="3a3eg4d1b-8802-467d-b205-711tre18" horizontalFill="true" fieldColSpan="1" size="30" />
      </msh:row>
     </msh:ifFormTypeIsNotView>
     <msh:ifFormTypeIsView formName="pres_servicePrescriptionForm">
     	<msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения" guid="3a3eg4d1b-8802-467d-b205-711tre18" horizontalFill="true" fieldColSpan="1" size="30" viewOnlyField="true"/>
        </msh:row>
     </msh:ifFormTypeIsView>

        
        <!-- --------------------------------------------------Начало блока "Лабораторные анализы" ------ -->
        <msh:ifFormTypeIsCreate formName="pres_servicePrescriptionForm">
        <msh:row>
        	<msh:separator label="Лабораторные анализы" colSpan="10"/>
        </msh:row>
        <msh:row>
       
        <table id="labTable">
        <tbody id="addlabElements">

    		<tr>
    		<td>
   		    <msh:autoComplete property="labServicies" label="Лабораторный анализ" vocName="labMedService" horizontalFill="true" size="90"/>
			</td>
			<td colspan='1'>
			<div>
			<msh:textField property="labDate" label="Дата " size="10"/>
			</div>
			</td>
			</tr>
			<tr>
			<td>
			<msh:autoComplete property="labCabinet" label="Кабинет" parentAutocomplete="labServicies" vocName="funcMedServiceRoom" size='20' horizontalFill="true" />
			</td>
			<td>        	
            <input type="button" name="subm" onclick="addRow('lab');" value="+" tabindex="4" />
            </td>
            </tr>
           </tbody>
    		</table>
    		

        </msh:row>
         <msh:panel>
        <msh:row>
        	<msh:separator label="Функциональные исследования" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="funcTable">
        <tbody id="addfuncElements">
    		<tr>
    		<td>
			<msh:autoComplete property="funcServicies" label="Исследование" vocName="funcMedService" horizontalFill="true" size="90" />
			</td>
			<td colspan='1'>
			<div>
			<msh:textField property="funcDate" label="Дата " size="10"/>
			</div>
			</td>
			</tr>
			<tr>
			<td>
			<msh:autoComplete property="funcCabinet" label="Кабинет" parentAutocomplete="funcServicies" vocName="funcMedServiceRoom" size='20' horizontalFill="true" />
			</td>
			
			<td>        	
            <input type="button" name="subm" onclick="addRow('func');" value="+" tabindex="4" />
            </td>
            </tr>
       		</tbody>
    		</table>
    		</td></tr></msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
         <msh:ifFormTypeAreViewOrEdit formName="pres_servicePrescriptionForm">
         <msh:row>
        	<msh:separator label="Наименование исследования" colSpan="10"/>
        </msh:row>
  		<msh:autoComplete property="medService" vocName="labMedService"  label="Наименование исследования" horizontalFill="true" size="90" />
  		<msh:textField property="planStartDate" label="Дата " size="10"/>
		<msh:row>
		<%-- <msh:textArea property="prescriptCabinet" label="Кабинет" /> --%>
		<msh:autoComplete property="prescriptCabinet" vocName="funcMedServiceRoom" parentAutocomplete="medService" label="Кабинет" size='20' horizontalFill="true" />
  		</msh:row>
		</msh:ifFormTypeAreViewOrEdit>
        </msh:panel>

        <msh:panel>
        <!-- --------------------------------------------------Конец блока "Функциональная диагностика" ------ -->
        
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
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="pres_servicePrescriptionForm">
      <msh:section guid="sectionChilds" title="Исполнения">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="pres_prescriptionFulfilmentForm" attribute="fulfilments" />
        <msh:table guid="tableChilds3434" name="fulfilments" action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
          <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" guid="8c2s9b-89d7-46a9-a8c3-c08527e" />
          <msh:tableColumn columnName="Время исполнения" property="fulfilTime" guid="d6dd49-a94d-4cf2-af1b-02581f" />
          <msh:tableColumn columnName="Исполнитель" property="executorInfo" guid="bfv1-0967-45f2-a6af-f654e" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="StacJournal" beginForm="pres_servicePrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_servicePrescriptionForm" guid="99ca692-c1d3-4d79-bc37-c6726c">
      <msh:sideMenu title="Назначения" guid="eb3f54-b971-441e-9a90-51jhf">
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="id" action="/entityParentEdit-pres_servicePrescription" name="Изменить" guid="ca5sui7r-9239-47e3-aec4-995462584" key="ALT+2"/>
        <msh:sideLink confirm="Удалить?" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" params="id" action="/entityParentDelete-pres_servicePrescription" name="Удалить" guid="ca5sui7r-9239-47e3-aec4-995462584" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="0e2ac7-5361-434d-a8a7-1284796f">
    
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" guid="ca3s9-9239-47e3-aec4-9a846547144" key="ALT+3"/>
      
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="67aa758-3ad2-4e6f-a791-4839460955" >
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentListRedirect-pres_servicePrescription" name="К списку назначений на услугу" guid="e9d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" guid="e9d94-fe74-4c43-85b1-267231ff" key="ALT+4"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

