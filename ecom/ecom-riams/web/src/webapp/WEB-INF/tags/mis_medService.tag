<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="role" required="true" description="Роль" %>

<msh:ifInRole roles="${role}">

<style type="text/css">
    #${name}ProtChangeInfoDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ProtChangeInfoDialog' class='dialog'>
    <h2 id='${name}ProtChangeInfoTitle'>ВЫБОР ШАБЛОНА ПО УСЛУГАМ</h2>
    <input type='hidden' name='${name}MedService' id='${name}MedService'/>
    <div id="${name}ProtChangeRootPane" class='rootPane'>
    	
	</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}ProtChangeInfoDialogInitialized = false ;
     var the${name}ProtChangeInfoDialog = new msh.widget.Dialog($('${name}ProtChangeInfoDialog')) ;
     // Показать
     function show${name}ProtChangeShowTemplate(aMedService) {
         ..$('${name}ProtChangeInfoTitle').innerHTML = "ВЫБОР ДЕФЕКТА" ;
         $('${name}List').value=aPrescipt;
         PrescriptionService.getListTemplateBySmo($('${name}Smo').value, { 
             callback: function(aResult) {
             	$('${name}ProtChangeRootPane').innerHTML = aResult +"<br><input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}ProtChangeInfo()\">";
             	the${name}ProtChangeInfoDialog.show() ;
             }
 		}); 
         
     }
     function show${name}ProtChangeCabinet(aListPrescript,aDepartment,aPrescriptType) {
    	 
     }

     // Отмена
     function cancel${name}ProtChangeInfo() {
         the${name}ProtChangeInfoDialog.hide() ;
     }

     // Сохранение данных
     function save${name}ProtChangeInfo() {
     	
     }
     
     function save${name}ProtChange() {
    	
 		
    	 
     }
     // инициализация диалогового окна
     function init${name}ProtChangeInfoDialog() {
     	
     	theIs${name}ProtChangeInfoDialogInitialized = true ;
     	
     }
</script>
</msh:ifInRole>