<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #${name}AutorizationDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='${name}AutorizationDialog' class='dialog'>
    <h2>Введите логин и пароль</h2>
    <div class='rootPane'>
    
<form action="javascript:save${name}Password()" target="_blank">
    <msh:panel styleId="panel${name}" >
    	<msh:row>
    		<msh:textField property="${name}Login" label="Введите логин" fieldColSpan="3" horizontalFill="true" />
    		<msh:textField property="password" label="Введите пароль" fieldColSpan="3" horizontalFill="true" passwordEnabled="true"/>
    	</msh:row>
    	 	
    </msh:panel>
        <div id='div${name}Button'>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Ok' id="${name}Ok" onclick="this.form.${name}Login.name='username';this.form.action='ecom_loginSave.do';this.form.submit();cancel${name}Autorization();this.form.username.name='${name}Login';"/>
                <input type="button" value='Отменить' id="${name}Cancel" onclick="cancel${name}Autorization();"/>
            </td>
        </msh:row>
        <msh:row>
        <td colspan="4">ПОСЛЕ ВВОДА ЛОГИНА И ПАРОЛЯ НЕ ЗАБУДЬТЕ ЗАКРЫТЬ НОВУЮ ВКЛАДКУ И СОХРАНИТЬ ДАННЫЕ!!!!!</td>
        </msh:row>
        </div>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}AutorizationDialogInitialized = false ;
     var the${name}AutorizationDialog = new msh.widget.Dialog($('${name}AutorizationDialog')) ;

     // Показать
     function show${name}Autorization() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}AutorizationDialogInitialized) {
         	init${name}AutorizationDialog() ;
          }
         the${name}AutorizationDialog.show() ;

         $("${name}Login").focus() ;
         $("${name}Login").select() ;
         

     }

     // Отмена
     function cancel${name}Autorization() {
         the${name}AutorizationDialog.hide() ;
     }

     // Сохранение данных
     function save${name}Autorization() {
    	var aObj=$('${name}Password').value;
    	cancel${name}Password();
     	eval("${command}") ;
     }
     function edit${name}Autorization() {
 		$('panel${name}').style.display="" ;
 		$('div${name}Button').style.display="" ;
 		$('${name}errorDiv').innerHTML="" ;
     }
     

     // инициализация диалогового окна
     function init${name}AutorizationDialog() {
     	theIs${name}AutorizationDialogInitialized = true ;
     }
     -->
     </script>
     