<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

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
        	<msh:separator label="Выбор шаблона" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete label="Диета" property="${name}templateDiet"                  vocName="Diet"    horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
	        <msh:autoComplete label="Шаблон"    property="${name}templateMenu" vocName="vocMenuByDiet" horizontalFill="true" parentAutocomplete="${name}templateDiet" fieldColSpan="3"/>
        <msh:row>
            <msh:textArea label='Описание назначения' property="${name}descriptionMenu"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Параметры нового шаблона" colSpan="4"/>
        </msh:row>
        <msh:row >
          <msh:autoComplete label="Диета" showId="false" hideLabel="false" property="${name}newDiet" viewOnlyField="false" horizontalFill="true" fieldColSpan="3" vocName="Diet" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocServiceStream" hideLabel="false" property="${name}newServiceStream" viewOnlyField="false" label="Поток обслуживания" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocWeekDay" property="${name}newWeekDay" label="День недели" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="${name}newDateFrom" label="Дата начала действия" />
          <msh:textField property="${name}newDateTo" label="Дата окончания действия" />
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
     new dateutil.DateField($('${name}newDateTo')) ;
     new dateutil.DateField($('${name}newDateFrom')) ;
     
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
     		error =error+" Не выбрана шаблон !!!" ;
     		saveIs=0 ;
        } 
        if ($('${name}newServiceStream').value==0) {
   			error=error+" Не выбран поток обслуживания !!!" ;
   			saveIs=0 ;
     	}
     	if ($('${name}newWeekDay').value==0) {
   			error=error+" Не выбран день недели !!!" ;
   			saveIs=0 ;
     	}
		if ($('${name}newDateFrom').value=="") {
			error=error+" Не указана дата начала действия!!!";
			saveIs=0;
		}
		if ($('${name}newDateTo').value=="") {
			error=error+" Не указана дата окончания действия !!!";
			saveIs=0 ;
		} 
     	if (saveIs==1) {
		    DietService.saveMenuInNewTemplate(
	     		$('${name}templateMenu').value,$('${name}newDiet').value
	     		,$('${name}newServiceStream').value,$('${name}newWeekDay').value
                  ,$('${name}newDateFrom').value, $('${name}newDateTo').value 
	     		 ,{
	                   callback: function(aString) {
	                       alert(aString) ;
	                       window.document.location.reload()  ;
	                    }
	                }
	         ) ;
	         //Long aIdTemplateMenu, Long aIdDiet, Long aIdServiceStream
			//,Long aIdWeekDay, String aDateFrom, String aDateTo
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
