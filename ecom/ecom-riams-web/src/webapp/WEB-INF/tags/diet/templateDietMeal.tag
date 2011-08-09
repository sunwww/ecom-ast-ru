<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags/diet" prefix="tags" %>

<%@ attribute name="parentId" required="false" description="ИД родителя: при record=1-ИД шаблона меню, а 2,3 - ИД диеты берется из шаблона" %>
<%@ attribute name="name" required="true" description="название" %>

<style type="text/css">
    #textProtocol {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}templateDietDialog' class='dialog'>
    <h2>Выбор шаблона меню</h2>
    <div class='rootPane'>
    
<form>
    <msh:panel>
        <msh:row>
        	<msh:autoComplete label="Диета" property="${name}templateDiet"                  vocName="Diet"    horizontalFill="true"/>
        </msh:row>
	        <msh:autoComplete label="Шаблон"    property="${name}templateMenu" vocName="vocMenuByDiet" horizontalFill="true" parentAutocomplete="${name}templateDiet" />
        <msh:row>
            <msh:textArea label='Описание назначения' property="${name}descriptionMenu"  horizontalFill="true"/>
        </msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK' onclick='save${name}TemplateDiet()'/>
                <input type="button" value='Отменить' onclick='cancel${name}TemplateDiet()'/>
            </td>
        </msh:row>
</form>

</div>
</div>
<script type='text/javascript' src='./dwr/interface/DietService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}TempDietDialogInitialized = false ;
     var the${name}TempDietDialog = new msh.widget.Dialog($('${name}templateDietDialog')) ;



     // Показать
     function show${name}TemplateDiet() {

         // устанавливается инициализация для диалогового окна
         if (!theIs${name}TempDietDialogInitialized) {
         	init${name}TemplateDiet() ;
          }
         the${name}TempDietDialog.show() ;
         $("${name}templateDiet").focus() ;

     }



     // Отмена
     function cancel${name}TemplateDiet() {
         the${name}TempDietDialog.hide() ;
     }

     // Сохранение данных
     function save${name}TemplateDiet() {
     	if ($('${name}templateMenu').value==0) {
     		alert("Не выбран шаблон назначения") ;
     	} else {
     		
		    DietService.saveExistsTemplateMenu(
	     		'${parentId}',$('${name}templateMenu').value
	     		 ,{
	                   callback: function(aString) {
	                       alert(aString) ;
	                       window.document.location.reload()  ;
	                    }
	                }
	         ) ;
         }
         //window.document.location.reload()  ;
         //theTempProtDialog.hide() ;
     }

     // инициализация диалогового окна
     function init${name}TemplateDiet() {
             $('${name}descriptionMenu').readOnly=true ;
             $('${name}descriptionMenu').value = "" ;
             
             ${name}templateMenuAutocomplete.addOnChangeCallback(function() {
                 DietService.getDescriptionMenu($('${name}templateMenu').value, {
                    callback: function(aString) {
                        $('${name}descriptionMenu').value = aString ;
                     }
                 } ) ;
             }) ;
             
             ${name}templateDietAutocomplete.addOnChangeCallback(function() {
                 ${name}templateMenuAutocomplete.setVocId('');
                 $('${name}descriptionMenu').value = "" ;
             }) ;
             if ("${record}"=="1" && $('diet')) {
             	${name}templateDietAutocomplete.setVocId($('diet').value);
             }
             theIs${name}TempDietDialogInitialized = true ;
     }
</script>
