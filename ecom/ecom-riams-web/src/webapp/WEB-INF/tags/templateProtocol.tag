<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ attribute name="name" description="название" %>
<%@ attribute name="property" description="свойство, куда записывать данные" %>
<%@ attribute name="version" description="версия ПО Ticket,Visit" %>
<%@ attribute name="idSmo" description="индентификатор случая" %>

<style type="text/css">
    #textProtocol {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    li.liTemp {
    	cursor: pointer;
    	list-style:none outside none;
    }
    li.liTemp:HOVER {
    	cursor: pointer;
    	background-color: #06C ;
    	color: white ;
	}
</style>

<div id='${name}templateProtocolDialog' class='dialog'>
    <h2>Выбор шаблона протокола</h2>
    <div class='rootPane'>
    <form>
    <table>
		<tr><td valign="top">
		    <msh:panel>
		            <msh:row>
		        	<msh:autoComplete label="Категория" property="${name}tempProtCategory"                  vocName="vocTemplateCategory"   fieldColSpan="5" horizontalFill="true"/>
		        </msh:row>
		        <msh:row>
		            <msh:autoComplete horizontalFill="true" property='${name}tempProtocol' vocName="vocTemplateProtocolByCategory" label='Шаблоны:' fieldColSpan="5" parentAutocomplete="${name}tempProtCategory"/>
		        </msh:row>

		        <msh:row styleId="tdPrev2">
		        	<msh:autoComplete label="Информация о пред.протоколах" 
		        		property="${name}PrevProtocol"                 
		        		vocName="protocolTicketByPatient"   
		        		fieldColSpan="5" horizontalFill="true" parentId="${idSmo}"/>
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
		</td>
		<td valign="top">
			<div id="${name}divListProtocols"></div>
		</td>
		</tr>
		</table>
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
            	 get${name}TextProtocolById($('${name}tempProtocol').value) ;
             }) ;

             theIs${name}TempProtDialogInitialized = true ;
             eventutil.addEnterSupport('${name}tempProtocolName', 'buttonTempProtOk') ;
             eventutil.addEnterSupport('${name}PrevProtocolName', 'buttonTempProtOk') ;
             
             if ('${version}'=='Ticket') {
            	 ${name}PrevProtocolAutocomplete.setUrl('simpleVocAutocomplete/protocolTicketByPatient') ;
             } else if ('${version}'=='Visit') {
            	 ${name}PrevProtocolAutocomplete.setUrl('simpleVocAutocomplete/protocolVisitByPatient') ;
             } else {
            	 ${name}showRow("tdPrev2",false) ;
             }
             ${name}PrevProtocolAutocomplete.addOnChangeCallback(function() {
            	 get${name}TextDiaryById($('${name}PrevProtocol').value,0) ;
             }) ;
             load${name}ListProtocolsByUsername() ;
             setFocusOnField('${name}tempProtCategoryName') ;
     }
     function ${name}showRow(aRowId, aCanShow, aField ) {
  		//alert(aRowId) ;
 			try {
 				//alert( aCanShow ? 'table-row' : 'none') ;
 				$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
 			} catch (e) {
 				// for IE
 				//alert(aCanShow ? 'block' : 'none') ;
 				$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
 			}	
 		}
     function load${name}ListProtocolsByUsername() {
    	 TemplateProtocolService.listProtocolsByUsername( ${name}PrevProtocolAutocomplete.getParentId(), 'get${name}TextProtocolById','get${name}TextDiaryById','${version}',{
             callback: function(aString) {
                 $('${name}divListProtocols').innerHTML = aString ;
              }
          } ) ;
     }
     function get${name}TextProtocolById(aId,aOk) {
         TemplateProtocolService.getText(aId, {
             callback: function(aString) {
                 $('${name}textTemplProtocol').value = aString ;
                 if (+aOk==1) {
                	 save${name}TemplateProtocol() ;
                	 $('${name}tempProtocol').value='' ;
                	 $('${name}tempProtocolName').value='' ;
                 }
            	 $('${name}PrevProtocol').value='' ;
            	 $('${name}PrevProtocolName').value='' ;
              }
          } ) ;
    	 
     }
     function get${name}TextDiaryById(aId,aOk) {
         TemplateProtocolService.getPreviousText(aId, {
             callback: function(aString) {
                 $('${name}textTemplProtocol').value = aString ;
                 if (+aOk==1) {
                	 save${name}TemplateProtocol() ;
                	 $('${name}PrevProtocol').value='' ;
                	 $('${name}PrevProtocolName').value='' ;
                 } 
            	 $('${name}tempProtocol').value='' ;
            	 $('${name}tempProtocolName').value='' ;
              }
          } ) ;
    	 
     }
     
</script>
