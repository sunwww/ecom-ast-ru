<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="action" required="true" description="Сссылка" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}CreateType('.do') " />

<style type="text/css">
    #createParameter {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CreateParameterDialog' class='dialog'>
    <h2>Выберите тип создаваемого параметра</h2>
    <div class='rootPane'>
    
<form>
    <msh:panel>
        <msh:row>
        	<msh:autoComplete label="Тип параметра" property="${name}Type"                  vocName="parameterType"    horizontalFill="true" size="50"/>
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
<script type='text/javascript' src='./dwr/interface/DietService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}CreateParameterDialogInitialized = false ;
     var the${name}CreateParameterDialog = new msh.widget.Dialog($('${name}CreateParameterDialog')) ;

     // Показать
     function show${name}CreateType() {
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
     		//TODO доделать
     		window.location.href='${action}&${name}='+$('${name}Type').value ;
         }
     }

     // инициализация диалогового окна
     function init${name}CreateParameterDialog() {
     	theIs${name}CreateParameterDialogInitialized = true ;
     }
</script>
