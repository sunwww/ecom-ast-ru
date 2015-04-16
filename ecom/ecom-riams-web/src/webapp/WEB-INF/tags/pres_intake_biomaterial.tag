<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="role" required="true" description="Роль" %>

<msh:ifInRole roles="${role}">

<style type="text/css">
    #${name}IntakeInfoDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}IntakeInfoDialog' class='dialog'>
    <h2 id='${name}IntakeInfoTitle'>ПРИЕМ БИОМАТЕРИЛА</h2>
    <input type='hidden' name='${name}List' id='${name}List'/>
    <div id="${name}IntakeRootPane" class='rootPane'>
    	
	</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}IntakeInfoDialogInitialized = false ;
     var the${name}IntakeInfoDialog = new msh.widget.Dialog($('${name}IntakeInfoDialog')) ;
     // Показать
     function show${name}IntakeCancel(aPrescipt,aBiomatType) {
         $('${name}IntakeInfoTitle').innerHTML = "ВЫБОР ДЕФЕКТА" ;
         $('${name}List').value=aPrescipt;
         PrescriptionService.getDefectByBiomaterial(aPrescipt, aBiomatType, { 
             callback: function(aResult) {
             	$('${name}IntakeRootPane').innerHTML = aResult +"<br><input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}IntakeInfo()\">";
             	the${name}IntakeInfoDialog.show() ;
             }
 		}); 
         
     }
     function show${name}IntakeCabinet(aListPrescript,aDepartment,aPrescriptType) {
    	 $('${name}IntakeInfoTitle').innerHTML = "ВЫБОР КАБИНЕТА" ;
         // устанавливается инициализация для диалогового окна
         $('${name}List').value=aListPrescript;
         PrescriptionService.getLabCabinetByPres(aListPrescript, { 
             callback: function(aResult) {
             	$('${name}IntakeRootPane').innerHTML = aResult ;
             	the${name}IntakeInfoDialog.show() ;
             }
 		}); 
     }

     // Отмена
     function cancel${name}IntakeInfo() {
         the${name}IntakeInfoDialog.hide() ;
     }

     // Сохранение данных
     function save${name}IntakeInfo() {
     	PrescriptionService.intakeService($('${name}List').value,$('${name}Date').value, $('${name}Time').value, { 
            callback: function(aResult) {
            	window.document.location.reload();
            }
		}); 
     }
     
     function save${name}Intake() {
    	
 		PrescriptionService.intakeService($('${name}List').value, textDay+'.'+textMonth+'.'+textYear
      			,(textHour<10?'0'+textHour:textHour)+':'+(textMinute<10?'0'+textMinute:textMinute)
  				, { 
	            callback: function(aResult) {
	            	window.document.location.reload();
	            }
			});
    	 
     }
     // инициализация диалогового окна
     function init${name}IntakeInfoDialog() {
     	
     	theIs${name}IntakeInfoDialogInitialized = true ;
     	
     }
</script>
</msh:ifInRole>