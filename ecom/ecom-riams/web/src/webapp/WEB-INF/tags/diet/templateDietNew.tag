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
	        <msh:autoComplete label="Шаблон"    property="${name}templateMenu" vocName="vocMenuByDiet" horizontalFill="true" parentAutocomplete="${name}templateDiet" fieldColSpan="3"/>
        <msh:row>
            <msh:textArea label='Описание назначения' property="${name}descriptionMenu"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="${name}Lpu" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="${name}Date" label="Дата" />
	        <msh:textField property="${name}PortionAmount" label="Кол-во порций" horizontalFill="true"/>
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
     new dateutil.DateField($('${name}Date')) ;
     
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
     	if ($('${name}templateMenu').value==0) {
     		error ="Не выбран шаблон назначения !!!" ;
     		saveIs=0 ;
        } else {
        	if ($('${name}Lpu').value==0) {
     			error="Не выбрано отделение !!!" ;
     			saveIs=0 ;
     		} else {
     			if ($('${name}Date').value=="") {
     				error="Не указана дата меню-заказов !!!";
     				saveIs=0 ;
     			} else {
     				if (+$('${name}PortionAmount').value<1) {
     					error="Кол-во порций должно быть больше 0 !!!";
     					saveIs=0;
     				}
     			}
     		}
        
        }
     	if (saveIs==1) {
     		
		    DietService.saveMenu(
	     		'${parentId}',$('${name}templateMenu').value, ${record}

                  ,$('${name}Date').value, $('${name}Lpu').value , $('${name}PortionAmount').value
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
