<%@ tag pageEncoding="utf8" %>


<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="parentID" required="true" description="ИД родителя" %>

<style type="text/css">
    #${name}PrescTypesDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}PrescriptListDialog' class='dialog'>
    <h2>Выбирите услугу для создания</h2>
    <div class='rootPane'>
    <h3>Выбор вида назначения:</h3>
<form action="javascript:">
    <table id = 'tablePrescriptList'>
    <tr>
    	<td>
    		<input name="add" type='button' onclick="javascript:goTo('service')" value='Добавить лабораторные назначение'> 
    	</td>
    </tr>
    <tr>
    	<td>
    		<input name="add" type='button' onclick="javascript:goTo('func')" value='Добавить диагностическое исследование'> 
    	</td>
    </tr>
    <tr>
    	<td>
    		<input name="add" type='button' onclick="javascript:goTo('operation')" value='Добавить назначение на операцию'> 
    	</td>
    </tr>
    <tr>
    	<td>
    		<input name="add" type='button' onclick="javascript:goTo('diet')" value='Добавить диету'> 
    	</td>
    </tr>
    <tr>
    	<td>
    		<input name="view" type='button' onclick="javascript:goTo('view')" value='Просмотреть назначения'> 
    	</td>
    </tr>
   <tr>
    	<td>
    		<input name="cancel" type='button' onclick="javascript:cancel${name}PrescriptList()" value='Ничего не надо, я передумал'> 
    	</td>
    </tr>
	</table>

</form>

</div>
</div>
<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
<script type="text/javascript"><!--
var plId ;
var isSLSClosed = true;
     var theIs${name}PrescriptListDialogInitialized = false ;
     var the${name}PolicyDialog = new msh.widget.Dialog($('${name}PrescriptListDialog')) ;
     var the${name}PrescriptListDialog = new msh.widget.Dialog($('${name}PrescriptListDialog')) ;
     // Показать 
         
     function goTo(aValue) {
    	if (aValue=='service') window.location='entityParentPrepareCreate-pres_servicePrescription.do?id='+plId;
    	else if (aValue=='operation') window.location='entityParentPrepareCreate-pres_operationPrescription.do?id='+plId;
    	else if (aValue=='diet') window.location='entityParentPrepareCreate-pres_dietPrescription.do?id='+plId;
    	else if (aValue=='func') window.location='entityParentPrepareCreate-pres_diagnosticPrescription.do?id='+plId;
    	else if (aValue=='view') window.location='entityParentView-pres_prescriptList.do?id='+plId;
     }
     
   
     
     // Отмена 
     function cancel${name}PrescriptList() {
        the${name}PrescriptListDialog.hide() ;
     }
     
	
     function disableButtons() {
    	 var b = document.getElementsByName('add');
    	
		 for (var i=0; i<b.length;i++) {
			  
			 b[i].disabled=true;
		 }
     }
     // инициализация диалогового окна 
     function show${name}PrescriptList() {
		//alert ("In TAG, $id = ="+'${parentID}'); 
		 PrescriptionService.isPrescriptListExists('${parentID}', {
						 callback: function (aPresID) {
							 if (aPresID==null||aPresID=='null') {
								 alert ('Пациент выписан, назначения отсутствуют, добавление назначений невоможно!');
								 cancel${name}PrescriptList();
								 		return;			 
							 } else {
								 var isMedcaseClosed = aPresID.substring(0,1);
								 if (isMedcaseClosed=='0') {
									disableButtons();
									 alert ('Пациент выписан, добавление назначений невоможно!');
								 }
								 plId = aPresID.substring(1);
								 the${name}PrescriptListDialog.show() ;
							 }
							 
							 
						 }
					 });
		  
		 theIs${name}PrescriptListDialogInitialized=true;
     }
</script>