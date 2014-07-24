<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="command" required="true" description="Команда" %>


<style type="text/css">
    #${name}PasswordDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='${name}PasswordDialog' class='dialog'>
    <h2>${title}</h2>
    <div class='rootPane'>
    
<form action="javascript:save${name}Password()">
    <msh:panel styleId="panel${name}" >
    	<msh:row>
    		<msh:textField property="${name}Password" label="Введите ПИН1" fieldColSpan="3" horizontalFill="true" passwordEnabled="true"/>
    	</msh:row>
    	 	
    </msh:panel>
        <div id='div${name}Button'>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Сохранить' id="${name}Ok"  onclick="javascript:save${name}Password()"/>
                <input type="button" value='Отменить' id="${name}Cancel" onclick='javascript:cancel${name}Password()'/>
            </td>
        </msh:row>
        </div>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/PasswordService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}PasswordDialogInitialized = false ;
     var the${name}PasswordDialog = new msh.widget.Dialog($('${name}PasswordDialog')) ;

     // Показать
     function show${name}Password() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}PasswordDialogInitialized) {
         	init${name}PasswordDialog() ;
          }
         the${name}PasswordDialog.show() ;

         $("${name}Password").focus() ;
         $("${name}Password").select() ;
         

     }

     // Отмена
     function cancel${name}Password() {
         the${name}PasswordDialog.hide() ;
     }

     // Сохранение данных
     function save${name}Password() {
    	var aObj=$('${name}Password').value;
    	cancel${name}Password();
     	eval("${command}") ;
     }
     function edit${name}Password() {
     
 		$('panel${name}').style.display="" ;
 		$('div${name}Button').style.display="" ;
 		$('${name}errorDiv').innerHTML="" ;
     
     }
     

     // инициализация диалогового окна
     function init${name}PasswordDialog() {
     	
     	theIs${name}PasswordDialogInitialized = true ;
     }
     </script>
     