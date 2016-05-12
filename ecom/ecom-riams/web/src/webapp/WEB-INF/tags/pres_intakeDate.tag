<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>

<msh:ifInRole roles="${roles}">

<style type="text/css">
    #${name}IntakeInfoDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}IntakeInfoDialog' class='dialog'>
    <h2>Дата</h2>
    <div class='rootPane'>
    
<form action="javascript:void(0)">
    <msh:panel>
    	<msh:row>
    		<input type="hidden" id="${name}List" name="${name}List" />
    		<msh:textField property="${name}Date" label="Дата"/>
    		<msh:textField property="${name}Time" label="Время"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" name="${name}butOK" id="${name}butOK" value='OK'  onclick="javascript:save${name}IntakeInfo()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}IntakeInfo()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}IntakeInfoDialogInitialized = false ;
     var the${name}IntakeInfoDialog = new msh.widget.Dialog($('${name}IntakeInfoDialog')) ;
     // Показать
     function show${name}IntakeInfo(aListPrescript) {
         // устанавливается инициализация для диалогового окна
         $('${name}List').value=aListPrescript;
         if (!theIs${name}IntakeInfoDialogInitialized) {
         	init${name}IntakeInfoDialog() ;
          }
         the${name}IntakeInfoDialog.show() ;
         $("${name}Date").focus() ;

     }

     // Отмена
     function cancel${name}IntakeInfo() {
         the${name}IntakeInfoDialog.hide() ;
     }

     // Сохранение данных
     function save${name}IntakeInfo() {
     	if ($('${name}Date').value=="") {
     		alert("Поле дата является обязательным") ;
     		$("${name}Date").focus() ;
     	} else if ($('${name}Time').value=="") {
     		alert("Поле время является обязательным") ;
     		$("${name}Time").focus() ;
     	}  else {
     		PrescriptionService.intakeService($('${name}List').value,$('${name}Date').value, $('${name}Time').value, { 
	            callback: function(aResult) {
	            	window.document.location.reload();
	            }
			}); 
	     	

         }
     }
     
     function save${name}IntakeCurrent(aListPrescript) {
    	 $('${name}List').value=aListPrescript;
    	 var currentDate = new Date;
 		var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
 		var textMonth = currentDate.getMonth()+1;
 		var textMonth = textMonth<10?'0'+textMonth:textMonth;
 		var textYear =currentDate.getFullYear();
 		var textMinute = currentDate.getMinutes() ;
 		var textHour = currentDate.getHours() ;
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
     	new dateutil.DateField($('${name}Date')) ;
     	new timeutil.TimeField($('${name}Time')) ;
     	var currentDate = new Date;
		var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
		var textMonth = currentDate.getMonth()+1;
		var textMonth = textMonth<10?'0'+textMonth:textMonth;
		var textYear =currentDate.getFullYear();
		$('${name}Date').value=textDay+'.'+textMonth+'.'+textYear;
		var textMinute = currentDate.getMinutes() ;
		var textHour = currentDate.getHours() ;
		$('${name}Time').value=(textHour<10?'0'+textHour:textHour)+':'+(textMinute<10?'0'+textMinute:textMinute);
     	theIs${name}IntakeInfoDialogInitialized = true ;
     	 eventutil.addEnterSupport('${name}Date', '${name}Time') ;
     	 eventutil.addEnterSupport( '${name}Time','${name}butOK') ;

     }
</script>
</msh:ifInRole>