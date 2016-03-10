<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="functionSave" required="true" description="" %>



<style type="text/css">
    #${name}CreationAccountDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CreationAccountDialog' class='dialog'>
    <h2>Дата</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}AccountNumber" label="Номер счета"/>
    		<msh:textField property="${name}DateFrom" label="Дата c"/>
    		<msh:textField property="${name}DateTo" label="по"/>
    		
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}CreationAccount()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}CreationAccount()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}CreationAccountDialogInitialized = false ;
     var the${name}CreationAccountDialog = new msh.widget.Dialog($('${name}CreationAccountDialog')) ;
     // Показать
     function show${name}CreationAccount() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}CreationAccountDialogInitialized) {
         	init${name}CreationAccountDialog() ;
          }
         the${name}CreationAccountDialog.show() ;
         $("${name}AccountNumber").focus() ;

     }

     // Отмена
     function cancel${name}CreationAccount() {
         the${name}CreationAccountDialog.hide() ;
     }

     // Сохранение данных
     function save${name}CreationAccount() {
     	if ($('${name}DateFrom').value.length==0) {
     		alert("Поле дата является обязательным") ;
     		$("${name}DateFrom").focus() ;
     	} else if ($('${name}DateTo').value.length==0) {
     		alert("Поле дата является обязательным") ;
     		$("${name}DateFrom").focus() ;
     	}  else {
	     	${functionSave}($('${name}DateFrom').value,$('${name}DateTo').value,$('${name}AccountNumber').value) ;
	     	cancel${name}CreationAccount() ;
         }
     }

     // инициализация диалогового окна
     function init${name}CreationAccountDialog() {
     	
     	new dateutil.DateField($('${name}DateFrom')) ;
     	new dateutil.DateField($('${name}DateTo')) ;
     	
     	theIs${name}CreationAccountDialogInitialized = true ;
     }
</script>
