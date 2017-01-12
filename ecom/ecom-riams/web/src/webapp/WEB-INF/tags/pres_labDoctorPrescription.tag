<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="addParam" description="Заголовок" %>


<style type="text/css">
    #${name}DirMedServiceDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    
    
</style>

<div id='${name}DirMedServiceDialog' class='dialog'>
    <h2>${title}. <a href='javascript:void(0)' onclick='cancel${name}DirMedService()'>Закрыть</a></h2>
    <div class='rootPane'>
<form action="javascript:void(0)">
    <msh:panel>
    <msh:autoComplete property="${name}NewMedService" vocName="labMedServiceForLabDoctor" label="Название исследования" size="50"/>
    
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Создать'  onclick="javascript:save${name}DirMedService()"/>
                <input type="button" value='Отменить'  onclick="javascript:cancel${name}DirMedService()"/>
            </td>
        </msh:row>
</form>

</div>
</div>


<script type="text/javascript"><!--
     var theIs${name}DirMedServiceDialogInitialized = false ;
     var the${name}DirMedServiceDialog = new msh.widget.Dialog($('${name}DirMedServiceDialog')) ;
     var the${name}OldPrescription = 0;
     // Показать
     function show${name}DirMedService(pid) {
    	 the${name}OldPrescription = pid;
         the${name}DirMedServiceDialog.show() ;
         

     }

     // Отмена
     function cancel${name}DirMedService() {
         the${name}DirMedServiceDialog.hide() ;
         msh.effect.FadeEffect.pushFadeAll();
     }

     // Сохранение данных
     function save${name}DirMedService() {
    	 if ($('${name}NewMedService').value==''||$('${name}NewMedService').value=='0') {
    		 alert ('Не выбрано исследование!');
    	 } else {
	    	 PrescriptionService.duplicatePrescription(the${name}OldPrescription, $('${name}NewMedService').value, {
	    		callback: function (a) {
	    			if (a!=null) {
	    				alert ("Назначение успешно создано");
	    				window.document.location.reload();
	    			} else {
	    				alert ("Произошла обработка исключительной операции. Обратитесь в тех. поддержку");
	    			}
	    			
	    			
	    		} 
	    	 });
	    	 cancel${name}DirMedService() ;
    	 }
     }
     

</script>
