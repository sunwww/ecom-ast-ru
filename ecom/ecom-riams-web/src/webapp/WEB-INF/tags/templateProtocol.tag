<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ attribute name="name" description="название" %>
<%@ attribute name="property" description="свойство, куда записывать данные" %>

<style type="text/css">
    #textProtocol {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}templateProtocolDialog' class='dialog'>
    <h2>Выбор шаблона протокола</h2>
    <div class='rootPane'>
		<form>
		    <msh:panel>
		            <msh:row>
		        	<msh:autoComplete label="Категория" property="${name}tempProtCategory"                  vocName="vocTemplateCategory"   fieldColSpan="5" horizontalFill="true"/>
		        </msh:row>
		        <msh:row>
		            <msh:autoComplete horizontalFill="true" property='${name}tempProtocol' vocName="vocTemplateProtocolByCategory" label='Шаблоны:' fieldColSpan="5" parentAutocomplete="${name}tempProtCategory"/>
		        </msh:row>
		        <msh:row>
		            <msh:textArea isNotGoEnter="true"  property="${name}textTemplProtocol" label='Текст шаблона:' size="50" fieldColSpan="5" hideLabel="true"/>
		        </msh:row>
		    </msh:panel>
		        <msh:row>
		            <td colspan="6">
		                <input type="button" name="buttonTempProtOk" id='buttonTempProtOk' value='OK' onclick='save${name}TemplateProtocol()'/>
		                <input type="button" value='Отменить' onclick='cancel${name}TemplateProtocol()'/>
		            </td>
		        </msh:row>
		</form>
		
		</div>
</div>
<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
<script type="text/javascript">
     var theIs${name}TempProtDialogInitialized = false ;
     var the${name}TempProtDialog = new msh.widget.Dialog($('${name}templateProtocolDialog')) ;

     // Показать
     function show${name}TemplateProtocol() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}TempProtDialogInitialized) {
			init${name}TemplateProtocol() ;
			$("${name}tempProtCategoryName").focus() ;
			$("${name}tempProtCategoryName").select() ;
         } else {
			$("${name}tempProtCategoryName").focus() ;
			$("${name}tempProtCategoryName").select() ;
         }
         the${name}TempProtDialog.show() ;
         //setFocusOnField('${name}tempProtCategoryName' );

     }

     // Отмена
     function cancel${name}TemplateProtocol() {
         the${name}TempProtDialog.hide() ;
     }

     // Сохранение данных
     function save${name}TemplateProtocol() {
			var prop ;
     		if ("${property}"=="") {
     			prop = "record" ;
     		} else {
     			prop = "${property}" ;
     		}
   			$(prop).value += $('${name}textTemplProtocol').value ;
	         the${name}TempProtDialog.hide() ;
	         $(prop).focus() ;
	         //$(prop).select() ;
     }

     // инициализация диалогового окна выбора шаблона протокола
     function init${name}TemplateProtocol() {
             $('${name}textTemplProtocol').readOnly=true ;
             ${name}tempProtCategoryAutocomplete.addOnChangeCallback(function() {
             	 ${name}tempProtocolAutocomplete.setVocId("");
             	 $('${name}textTemplProtocol').value = "" ;
             }) ;
             ${name}tempProtocolAutocomplete.addOnChangeCallback(function() {
                 TemplateProtocolService.getText($('${name}tempProtocol').value, {
                    callback: function(aString) {
                        $('${name}textTemplProtocol').value = aString ;
                     }
                 } ) ;
             }) ;

             theIs${name}TempProtDialogInitialized = true ;
             eventutil.addEnterSupport('${name}tempProtocolName', 'buttonTempProtOk') ;
             
             
     }
</script>
