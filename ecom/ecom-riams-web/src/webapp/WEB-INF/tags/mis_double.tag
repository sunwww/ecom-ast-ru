<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="cmdAdd" required="false" description="кнопка сохранения" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="rolesBan" required="false" description="Роль ограничивающая, создание при обнаружении дубля"%>

<style type="text/css">
    #${name}DoubleDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}DoubleDialog' class='dialog'>
    <h2>Внимаение возможны дубли!!!</h2>
    <div class='rootPane'>
    <h3>${title}</h3>
<form action="javascript:">
    <msh:panel>
    
    	<msh:row>
    		<span id='${name}DoubleText'/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" name='${name}DoubleSave' style="display: none;" id='${name}DoubleSave' value='Продолжить сохранение' onclick='javascript:next${name}Double()'/>
                <input type="button" name='${name}DoubleCancel' id='${name}DoubleCancel' value='Вернуться к редактированию' onclick='javascript:cancel${name}Double()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}DoubleDialogInitialized = false ;
     var the${name}DoubleDialog = new msh.widget.Dialog($('${name}DoubleDialog')) ;
     // Показать
     function show${name}Double(aText) {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}DoubleDialogInitialized) {
         	init${name}DoubleDialog() ;
          }
         $('${name}DoubleText').innerHTML = aText ;
         the${name}DoubleDialog.show() ;

     }

     // Отмена
     function next${name}Double() {
        document.forms[0].action = oldaction ;
		document.forms[0].submit() ;
		$('submitButton').style.display='true' ;
		$('${name}DoubleSave').readOnly='true' ;
		$('${name}DoubleSave').style.display = 'none' ;
		$('${name}DoubleCancel').style.display = 'none' ;
		$('${name}DoubleText').innerHTML='<i>Идет сохранение</i>' ;
		document.forms[0].action = 'alert("Идет обработка данных")' ;
     }
     // Отмена
     function cancel${name}Double() {
    	 if ('${cmdAdd}'!='')try{ eval(${cmdAdd})} catch(e){} ;
        the${name}DoubleDialog.hide() ;
     }

     

     // инициализация диалогового окна
     function init${name}DoubleDialog() {
    	 var rolesBan='${rolesBan}' ;
    	 if (rolesBan=='') {
    		 $('${name}DoubleSave').style.display = 'block' ;
    		 theIs${name}DoubleDialogInitialized = true ;
    	 } else {
    		 if ($('saveType').value=='1') {
    		 PatientService.checkPolicy(rolesBan,
    				 {
    			 		callback: function(aResult) {
    			 			
    			 			if (+aResult==0) {
    			 	    		 $('${name}DoubleSave').style.display = 'block' ;
    			 	    		 theIs${name}DoubleDialogInitialized = true ;
    			 			} 
    			 		}
    				 }) ;
    	 	} else {
    	 		 $('${name}DoubleSave').style.display = 'block' ;
 	    		 theIs${name}DoubleDialogInitialized = true ;
    	 	}
    	 }
    	 
     	
     }
</script>