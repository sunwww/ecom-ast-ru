<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ attribute name="name" description="название" required="true"%>
<%@ attribute name="property" description="свойство, куда записывать данные"  required="true" %>
<%@ attribute name="patient" description="свойство пациента"  required="true" %>
<%@ attribute name="dateFinish" description="свойство даты выписки из стационара СЛС" %>
<%@ attribute name="dateStart" description="свойство даты поступления СЛС"  required="true"%>

<style type="text/css">
    #${name}EpicrisisDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}EpicrisisDialog' class='dialog'>
    <h2>Выбор параметров</h2>
    <div class='rootPane'>
		<form>
		    <msh:panel>
		    	<msh:ifInRole roles="/Policy/Mis/MedCase/Document/External/Medservice/View">
		            <msh:row>
			        	<msh:checkBox property="${name}ExtLabs" label="Внешние лабораторные исследования"/>
			        </msh:row>
		    	</msh:ifInRole>
			        
		    	<msh:ifInRole roles="/Policy/Mis/MisLpu/IsNuzMsch">
		            <msh:row>
			        	<msh:checkBox property="${name}Labs" label="Лабораторные исследования (DTM)"/>
			        </msh:row>
		            <msh:row>
			        	<msh:checkBox property="${name}Fisio" label="Физио (DTM)"/>
			        </msh:row>
		            <msh:row>
			        	<msh:checkBox property="${name}Func" label="Функцион.диагностика (DTM)"/>
			        </msh:row>
		            <msh:row>
			        	<msh:checkBox property="${name}Cons" label="Консультации (DTM)"/>
			        </msh:row>
		            <msh:row>
			        	<msh:checkBox property="${name}Luch" label="Лучевая диагностика (DTM)"/>
			        </msh:row>
		        </msh:ifInRole>
		        <msh:row>
		            <msh:checkBox property="${name}Operations" label="Операции"/>
		        </msh:row>
		    </msh:panel>
		        <msh:row>
		            <td colspan="6">
		                <input type="button" name="${name}EpicrisisOk" id='${name}EpicrisisOk' value='OK' onclick='save${name}Epicrisis()'/>
		                <input type="button" value='Отменить' onclick='cancel${name}Epicrisis()'/>
		            </td>
		        </msh:row>
		</form>
		
		</div>
</div>
<%--<script type='text/javascript' src='./dwr/interface/EpicrisisService.js'></script> --%>
<script type="text/javascript">
     var theIs${name}EpicrisisDialogInitialized = false ;
     var the${name}EpicrisisDialog = new msh.widget.Dialog($('${name}EpicrisisDialog')) ;

     // Показать
     function show${name}Epicrisis() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}EpicrisisDialogInitialized) {
         	init${name}Epicrisis() ;

         } 
         
         the${name}EpicrisisDialog.show() ;
		if ($('${name}Labs')) {
			$("${name}Labs").focus() ;
			$("${name}Labs").select() ;
		} else {
			$("${name}Operations").focus() ;
			$("${name}Operations").select() ;
		}
         //setFocusOnField('${name}tempProtCategoryName' );

     }

     // Отмена
     function cancel${name}Epicrisis() {
         the${name}EpicrisisDialog.hide() ;
     }

     // Сохранение данных
     function save${name}Epicrisis() {
		get${name}LabsInfo() ;
     }
     function get${name}LabsInfo() {
		if ($('${name}Labs')&& ($('${name}Labs').checked)||$('${name}Fisio')&& ($('${name}Fisio').checked)
			||$('${name}Func')&& ($('${name}Func').checked) ||$('${name}Cons')&& ($('${name}Cons').checked)||$('${name}Luch')&& ($('${name}Luch').checked)) {
			HospitalMedCaseService.getLabInvestigations($('${patient}').value,$('${dateStart}').value
				,$('${dateFinish}').value, $('${name}Labs').checked,
				$('${name}Fisio').checked,$('${name}Func').checked,
				$('${name}Cons').checked, $('${name}Luch').checked
				 , {
					callback: function(aString) {
						$('${property}').value += "\n" ;
						$('${property}').value += aString ;
						get${name}OperationsInfo() ;
					}
			} ) ;
		} else {
			get${name}OperationsInfo() ;
     	}
     }
     function get${name}OperationsInfo() {
     	if ($('${name}Operations').checked) {
			HospitalMedCaseService.getOperations($('${patient}').value,$('${dateStart}').value
				,$('${dateFinish}').value, {
					callback: function(aString) {
						$('${property}').value += "\n" ;
						$('${property}').value += aString ;
						get${name}ExpMedserviceInfo() ;
					}
			} ) ;
		} else {
			get${name}ExpMedserviceInfo() ;
		}
     }

     function get${name}ExpMedserviceInfo() {
      	if ($('${name}ExtLabs').checked) {
 			HospitalMedCaseService.getExpMedservices($('${patient}').value,$('${dateStart}').value
 				,$('${dateFinish}').value, {
 					callback: function(aString) {
 						$('${property}').value += "\n" ;
 						$('${property}').value += aString ;
 						the${name}EpicrisisDialog.hide() ;
 						$('${property}').focus() ;
 						
 					}
 			} ) ;
 		} else {
 			the${name}EpicrisisDialog.hide() ;
 			$('${property}').focus() ;
 		}
      }

     // инициализация диалогового окна выбора шаблона протокола
     function init${name}Epicrisis() {
             <%--
             $('${name}textTemplProtocol').readOnly=true ;
             ${name}tempProtCategoryAutocomplete.addOnChangeCallback(function() {
             	 ${name}tempProtocolAutocomplete.setVocId("");
             	 $('${name}textTemplProtocol').value = "" ;
             }) ;
             ${name}tempProtocolAutocomplete.addOnChangeCallback(function() {
                 EpicrisisService.getText($('${name}tempProtocol').value, {
                    callback: function(aString) {
                        $('${name}textTemplProtocol').value = aString ;
                     }
                 } ) ;
             }) ;

             eventutil.addEnterSupport('${name}tempProtocolName', 'buttonTempProtOk') ;
             --%>
             theIs${name}EpicrisisDialogInitialized = true ;
             
             
     }
</script>
