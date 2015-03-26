<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="key" required="key" description="Горячие клавиши" %>
<%@ attribute name="confirm" required="false" description="Сообщение" %>
<%@ attribute name="date" required="true" description="Дата" %>
<%@ attribute name="number" required="true" description="Номер" %>

<msh:ifInRole roles="${roles}">

<style type="text/css">
    #${name}IntakeDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}IntakeDialog' class='dialog'>
    <h2>Дата</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}Date" label="Дата"/>
    		<msh:textField property="${name}Time" label="Номер"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}Intake()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}Intake()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}IntakeDialogInitialized = false ;
     var the${name}IntakeDialog = new msh.widget.Dialog($('${name}IntakeDialog')) ;
     // Показать
     function show${name}Intake() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}IntakeDialogInitialized) {
         	init${name}IntakeDialog() ;
          }
         the${name}IntakeDialog.show() ;
         $("${name}Date").focus() ;

     }

     // Отмена
     function cancel${name}Intake() {
         the${name}IntakeDialog.hide() ;
     }

     // Сохранение данных
     function save${name}Intake() {
     	if ($('${name}Date').value=="") {
     		alert("Поле дата является обязательным") ;
     		$("${name}Date").focus() ;
     	} else if ($('${name}Time').value=="") {
     		alert("Поле время является обязательным") ;
     		$("${name}Time").focus() ;
     	}  else {
     		PrescriptionService.intakeService(aListPrescript, $('${name}Date').value
     				,$('${name}Time').value
     				, { 
	            callback: function(aResult) {
	            	window.document.location.reload();
	            }
			});
         }
     }

     // инициализация диалогового окна
     function init${name}IntakeDialog() {
     	
		var d = new Date();
     	var dd = date.getDate();if ( dd < 10 ) dd = '0' + dd;
		var hh = date.getHours();if ( hh < 10 ) hh = '0' + hh;
		var min = date.getMinutes();if ( min < 10 ) min = '0' + min;
		var yyyy = date.getFullYear() ;if ( yyyy < 10 ) yyyy = yyyy;
     	$('${name}Date').value = dd+'.'+mm+'.'+yy;
     	$('${name}Time').value = hh+":"+min;
     	new dateutil.DateField($('${name}Date')) ;
     	new timeutil.TimeField($('${name}Time')) ;
		theIs${name}IntakeDialogInitialized = true ;
     }
</script>
</msh:ifInRole>