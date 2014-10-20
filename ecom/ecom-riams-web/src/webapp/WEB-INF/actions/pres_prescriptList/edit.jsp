<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="javascript" type="string">

		<script type="text/javascript">
		$('mainForm').action="javascript:checkLabs()";
		var num=0;
		function checkLabs() 
			{
			var tComment = document.getElementById("comments");
            var allData="";
			while (num>0)
				{
				if (document.getElementById("LabElement"+num))
					{
					
					var curLabService = document.getElementById('labService'+num);
					var curLabDate = document.getElementById('dateLab'+num);
					if (curLabService.value != "" & curLabDate.value != "")
						{
						tComment.value+=curLabService.value;
			            tComment.value+=":";
			            tComment.value+=curLabDate.value;
			            tComment.value+=";";
			            
			            allData+=curLabService.value;
			            allData+=":";
			            allData+=curLabDate.value;
			            allData+=";";
					}
					
				}
	            num-=1;
			 }
			if ($('labServicies').value != "" & $('dateLab').value != "")
        	{
        	allData+=$('labServicies').value;
            allData+=":";
            allData+=$('dateLab').value;
            allData+=";";
        	}
			alert(allData);
			//return;
		}
		function remRow(rId) {
			var tr = "LabElement"+rId;
			var element = document.getElementById(tr);
			element.parentNode.removeChild(element);
		}
		function addRow() {
			 num+=1;
		    // Считываем значения с формы 
		    //td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9'>"; 
		    var nameId = document.getElementById('labServicies').value;
		    	//var cnt=+$('cnt').value+1 ;
	   			//		$('cnt').value = cnt;
	                   //alert(aPosition) ;
					    //var name = document.getElementById('priceMedServiceName').value;
					   // nameId = document.getElementById('priceMedService').value;
					    //cost = document.getElementsByName("cost"+nameId)[0].value;
					    
					    // Находим нужную таблицу
					    var tbody = document.getElementById('addLabElements');
						//if(nameId!="")if(nameId!=null)
						    // Создаем строку таблицы и добавляем ее
						    var row = document.createElement("TR");
							row.id = "LabElement"+num;
						    tbody.appendChild(row);
						
						    // Создаем ячейки в вышесозданной строке
						    // и добавляем тх
						    var td1 = document.createElement("TD");
						    var td2 = document.createElement("TD");
						    var td3 = document.createElement("TD");
						    
						    row.appendChild(document.createElement("TD"));
						    row.appendChild(document.createElement("TD"));
						    row.appendChild(td1);
						    row.appendChild(document.createElement("TD"));
						    row.appendChild(document.createElement("TD"));
						    row.appendChild(td2);
						    row.appendChild(td3);
						    
						    // Наполняем ячейки
						    var dt="<input id='labService"+num+"' value='"+$('labServicies').value+"' type='hidden' name='labService' horizontalFill='true' size='90' readonly='true' />";
						    td1.innerHTML = dt+"<span>"+$('labServiciesName').value+"</span>" ;
						  	td2.innerHTML = "<input id='dateLab"+num+"' name='dateLab' label='Дата' value='"+$('dateLab').value+"' size = '10' />";
						   	td3.innerHTML = "<input type='button' name='subm"+num+"' onclick='remRow("+num+");' value='Удалить строку' />";
						   
		}
		</script>
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
      <msh:panel colsWidth="1%,1%,1%,1%,1%">
        <msh:row guid="154fb2a0-a3ac-4034-9cbb-087444dbe299">
          <msh:textArea rows="2" property="comments" label="Комментарии" fieldColSpan="9" horizontalFill="true" guid="f5338dbf-03ae-4c9c-a2ee-e6a3cc240dff" />
        </msh:row>
        <msh:ifFormTypeIsCreate formName="pres_prescriptListForm">
        <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="workFunction" label="Назначил" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="9" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Режим" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="modeForm.planStartDate" label="Дата начала" size="7"/>
          <msh:autoComplete vocName="vocModePrescription" property="modeForm.modePrescription" label="Режим" horizontalFill="true" fieldColSpan="9" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Диета" colSpan="10"/>
        </msh:row>
        <msh:row guid="b556ehb-b971-441e-9a90-53217">
        	<msh:textField property="dietForm.planStartDate" label="Дата начала" size="7"/>
          <msh:autoComplete viewAction="entityView-diet_diet.do" vocName="Diet" 
          property="dietForm.diet" label="Диета" horizontalFill="true" fieldColSpan="9" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Лекарственные назначения" colSpan="10"/>
        </msh:row>
        
       
        <tr><td colspan="10">
        <msh:panel styleId="border">
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
	        <td></td>
	          <msh:autoComplete vocName="vocDrugMethod" label="Способ введ." property="drugForm2.method" guid="3adk3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true"  hideLabel="true"/>
	          <msh:autoComplete hideLabel="true" vocName="vocFrequencyUnit" label="раза в " property="drugForm2.frequencyUnit" size="10"/>
	          <msh:autoComplete hideLabel="true" vocName="vocDrugAmountUnit" label="ед." property="drugForm2.amountUnit" size="10" />
	          <msh:autoComplete hideLabel="true" vocName="vocDurationUnit" label="ед." property="drugForm2.durationUnit" guid="32568-8802-467d-b205-715fb379b018" size="10" />
	        </msh:row>
	        
        </msh:panel>
        </td></tr>
        
        </msh:ifFormTypeIsCreate>
       <!-- --------------------------------------------------Начало блока "Лабораторные анализы" ------ -->
        <msh:row>
        	<msh:separator label="Лабораторные анализы" colSpan="10"/>
        </msh:row>
        <table id="labTable">
        <tbody id="addLabElements">
    		<tr>
    		<td>
			<msh:autoComplete property="labServicies" label="Лабораторный анализ" vocName="labMedService" horizontalFill="true" size="90"/>
			</td>
			<td colspan='1'>
			<div>
			<msh:textField property="dateLab" label="Дата " size="10"/>
			</div>
			</td>
			<msh:ifFormTypeIsNotView formName="pres_prescriptListForm">
			<td>        	
            <input type="button" name="subm" onclick="addRow();" value="+" tabindex="4" />
            
            <input type="button" name="subm" onclick="checkLabs();" value="123"  />
           <!--  <input type="button" name="subm" onclick="show1DirMedService();" value="++" tabindex="4" />  -->
            </td>
            </msh:ifFormTypeIsNotView>
            </tr>
            

    		</tbody>
    		</table>
        <!-- --------------------------------------------------Конец блока "Лабораторные анализы" ------ -->
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
  		<tags:templatePrescription record="1" parentId="${param.id}" name="add" />
  	</msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsView formName="pres_prescriptListForm" guid="d4c560e9-6ddb-4cf2-9375-4caf7f0d3fb8">
      <msh:sideMenu title="Лист назначений" guid="2742309d-41bf-4fbe-9238-2f895b5f79a9">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-pres_prescriptList" name="Изменить" roles="/Policy/Mis/Prescription/Prescript/Edit" guid="0f0781cd-81dd-4da2-aba5-67eab700b161" />
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

