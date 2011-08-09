<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ attribute name="name" description="название" %>
<%@ attribute name="property" description="свойство, куда записывать данные" %>
<%@ attribute name="ticket" description="ticket" %>

<style type="text/css">
    #textProtocol {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}prevProtocolDialog' class='dialog'>
    <h2>Выбор предыдущего протокола</h2>
    <div class='rootPane'>
		<form>
		    <msh:panel>
	            <msh:row>
		        	<msh:autoComplete label="Информация о протоколе" 
		        		property="${name}PrevProtocol" parentId="${ticket}"                 
		        		vocName="protocolByPatient"   
		        		fieldColSpan="5" horizontalFill="true"/>
		        </msh:row>
		        <msh:row>
		            <msh:textArea property="${name}textPrevProtocol" label='Предыдущий протокола:' horizontalFill="true" fieldColSpan="5"/>
		        </msh:row>
		    </msh:panel>
		        <msh:row>
		            <td colspan="6">
		                <input type="button" name="buttonTempProtOk" id='button${name}PrevProtOk' value='OK' 
		                	onclick='save${name}PrevProtocol()'/>
		                <input type="button" value='Отменить' onclick='cancel${name}PrevProtocol()'/>
		            </td>
		        </msh:row>
		</form>
		
		</div>
</div>
<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
<script type="text/javascript">
     var theIs${name}PrevProtDialogInitialized = false ;
     var the${name}PrevProtDialog = new msh.widget.Dialog($('${name}prevProtocolDialog')) ;

     // Показать
     function show${name}PrevProtocol() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}PrevProtDialogInitialized) {
         	init${name}PrevProtocol() ;
         }
         the${name}PrevProtDialog.show() ;
         //$("${name}PrevProtCategoryName").focus() ;
         //$("${name}PrevProtCategoryName").select() ;
         setFocusOnField('${name}PrevProtocolName' );

     }

     // Отмена
     function cancel${name}PrevProtocol() {
         the${name}PrevProtDialog.hide() ;
     }

     // Сохранение данных
     function save${name}PrevProtocol() {
			var prop ;
     		if ("${property}"=="") {
     			prop = "record" ;
     		} else {
     			prop = "${property}" ;
     		}
   			$(prop).value += $('${name}textPrevProtocol').value ;
	         the${name}PrevProtDialog.hide() ;
	         $(prop).focus() ;
	         //$(prop).select() ;
     }

     // инициализация диалогового окна выбора шаблона протокола
     function init${name}PrevProtocol() {
             $('${name}textPrevProtocol').readOnly=true ;
             ${name}PrevProtocolAutocomplete.addOnChangeCallback(function() {
             	 ${name}PrevProtocolAutocomplete.setVocId("");
             	 $('${name}textPrevProtocol').value = "" ;
             }) ;
             ${name}PrevProtocolAutocomplete.addOnChangeCallback(function() {
                 TemplateProtocolService.getPreviousText($('${name}PrevProtocol').value, {
                    callback: function(aString) {
                        $('${name}textPrevProtocol').value = aString ;
                     }
                 } ) ;
             }) ;

             theIs${name}PrevProtDialogInitialized = true ;
             eventutil.addEnterSupport('${name}PrevProtocolName', 'buttonPrevProtOk') ;
             
     }
</script>
