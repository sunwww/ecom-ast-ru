<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="record" required="true" description="Тип записи: 1-в существующее меню, 2-в новое меню-заказ, 3-в новый шаблон меню" %>
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
        	<msh:autoComplete label="Диета" property="${name}templateDiet"                  vocName="Diet"    horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete label="Шаблон меню-раскладки"    property="${name}templateMenu" vocName="vocMenuByDiet" horizontalFill="true" parentAutocomplete="${name}templateDiet" fieldColSpan="3" />
	    </msh:row>
	    <msh:row>
	    	<msh:autoComplete label="Шаблон приема пищи" property="${name}templateChildMenu" vocName="vocChildMenu" horizontalFill="true" parentAutocomplete="${name}templateMenu" fieldColSpan="3" />
	    </msh:row>
        <msh:row>
            <msh:textArea label='Описание назначения' property="${name}descriptionMenu"  horizontalFill="true" fieldColSpan="3"/>
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
     
     //var ${name}Date



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
        var saveIs = 1, error="";
     	if ($('${name}templateChildMenu').value==0) {
     		error ="Не выбран шаблон приема пищи!!!" ;
     	} 
     	if (saveIs==1) {
		    DietService.saveMenuExist(
	     		'${parentId}',$('${name}templateChildMenu').value
	     		 ,{
	                   callback: function(aString) {
	                       alert(aString) ;
	                       window.document.location.reload()  ;
	                    }
	                }
	         ) ;     		

         } else {
         	alert(error);
         }
         //window.document.location.reload()  ;
         //theTempProtDialog.hide() ;
     }

     // инициализация диалогового окна
     function init${name}TemplateDiet() {
             $('${name}descriptionMenu').readOnly=true ;
             $('${name}descriptionMenu').value = "" ;
             
             ${name}templateChildMenuAutocomplete.addOnChangeCallback(function() {
                 DietService.getDescriptionChildMenu($('${name}templateChildMenu').value, {
                    callback: function(aString) {
                        $('${name}descriptionMenu').value = aString ;
                     }
                 } ) ;
             }) ;
             
             ${name}templateDietAutocomplete.addOnChangeCallback(function() {
                 ${name}templateMenuAutocomplete.setVocId('');
                 ${name}templateChildMenuAutocomplete.setVocId('');
                 $('${name}descriptionMenu').value = "" ;
             }) ;
             ${name}templateMenuAutocomplete.addOnChangeCallback(function() {
                 ${name}templateChildMenuAutocomplete.setVocId('');
                 $('${name}descriptionMenu').value = "" ;
             }) ;
             
             if ("${record}"=="1" && $('diet')) {
             	${name}templateDietAutocomplete.setVocId($('diet').value);
             }
             theIs${name}TempDietDialogInitialized = true ;
     }
</script>
