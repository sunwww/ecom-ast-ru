<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="action" required="true" description="Ссылка" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="document" required="true" description="Заголовок" %>
<%@ attribute name="vocName" required="true" description="Заголовок" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}CreateType('.do') " />

<style type="text/css">
    #createParameter {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CreateParameterDialog' class='dialog'>
    <h2>Выберите тип создаваемого ${document}</h2>
    <div class='rootPane'>
    
<form>
    <msh:panel>
        <msh:row>
        	<msh:autoComplete label="Тип ${document}" property="${name}Type"
        	     vocName="${vocName}"    horizontalFill="true" size="50"/>
        </msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK' onclick='save${name}CreateType()'/>
                <input type="button" value='Отменить' onclick='cancel${name}CreateType()'/>
            </td>
        </msh:row>
</form>

</div>
</div>
<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>

<script type="text/javascript"><!--
     var theIs${name}CreateParameterDialogInitialized = false ;
     var the${name}CreateParameterDialog = new msh.widget.Dialog($('${name}CreateParameterDialog')) ;
     var the${name}IdChangeTypeParameter = "" ;

     // Показать
     function show${name}CreateType(aId) {
    	 the${name}IdChangeTypeParameter = aId?aId:"" ;
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}CreateParameterDialogInitialized) {
         	init${name}CreateParameterDialog() ;
          }
         the${name}CreateParameterDialog.show() ;
         $("${name}Type").focus() ;

     }

     // Отмена
     function cancel${name}CreateType() {
         the${name}CreateParameterDialog.hide() ;
     }

     // Сохранение данных
     function save${name}CreateType() {
     	if ($('${name}Type').value==0) {
     		alert("Не выбран тип параметра") ;
     	} else {
     		if (the${name}IdChangeTypeParameter>0) {
     			TemplateProtocolService.changeTypeByParameter(the${name}IdChangeTypeParameter,$('${name}Type').value, {
   	    		 callback: function() {
   	    			 alert("Тип изменен") ;
   	    			cancel${name}CreateType() ;
   	    			window.location.reload() ;
   	    		 }
   	    	 }) ;
     		} else {
     			window.location.href='${action}&${name}='+$('${name}Type').value+"&typeChange="+the${name}IdChangeTypeParameter ;
     		}
         }
     }

     // инициализация диалогового окна
     function init${name}CreateParameterDialog() {
     	theIs${name}CreateParameterDialogInitialized = true ;
     }
</script>
