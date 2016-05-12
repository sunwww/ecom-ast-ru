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
    	//else if (aValue=='service') window.location='entityParentPrepareCreate-pres_servicePrescription.do?id='+plId;
     }
     
     function show${name}PrescriptList(aId) {
    	 // устанавливается инициализация для диалогового окна 
         
         	init${name}PrescriptListDialog(aId) ;
         	// the${name}PrescriptListDialog.show() ;
          
        	//	  the${name}PrescriptListDialog.show() ;
          
     }
     
     // Отмена 
     function cancel${name}PrescriptList() {
        the${name}PrescriptListDialog.hide() ;
     }
     
	
     // инициализация диалогового окна 
     function init${name}PrescriptListDialog(aId) {
    	 
 		PrescriptionService.getDirections(aId, 'PrescriptionList','','OPERATION', {
   		 callback: function (aResult) {
   			 if (aResult!=null&&aResult!='') {
   				// alert (aResult);
   				 var htm = '';
   				var napr=aResult.split('#');
   				htm+="<p size='15px'>Создать назначение на основе предварительного направления?</p>";
   				htm+= "<table border='1'>";
				htm+="<tr><td>Дата</td>";
				htm+="<td>Время</td>";
				htm+="<td colspan='2'>Услуга</td></tr>";
   				for (var i=0;i<napr.length;i++) {
   					var str = napr[i].split(':');
   					/*
   					0 - ms.type
   					1 - ms.ID 2 - ms. code+name
   					3 - date
   					4 - cabinetcode           5 - cabinetname
   					6 - departmentintakecode  7 - departmentintakename (for lab)
   					8 - timecode              9 - timename (for func)
   					*/
   				htm+='<tr>';
   					htm+='<td>'+str[3]+'</td><td>'+str[9]+'</td><td>'+str[2]+'</td>';
   					htm+="<td><input type='button' onclick='addRows(\""+napr[i]+"\",0);var node=this.parentNode.parentNode;node.parentNode.removeChild(node);cancel${name}PrescriptList();' value = 'Создать назначение'></td>";
   					htm+='</tr>';
   					//htm+=''
   				}
   				htm+="<tr><td colspan='4'><input type='button' onclick='cancel${name}PrescriptList();' value='Закрыть'></td></tr>"
   				htm+='</table>';
   				//alert (htm);
   				$('${name}PrescriptListDialog').innerHTML = htm;
   				the${name}PrescriptListDialog.show();
   			 } else {
   				 
   			 }
   			 
   			 
   		 }
   	 }); 
		  
		 theIs${name}PrescriptListDialogInitialized=true;
     }
</script>