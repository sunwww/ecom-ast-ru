<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="record" required="true" description="Тип записи: 1-в существующий лист назначений, 2-в новый лист назначений" %>
<%@ attribute name="parentId" required="false" description="ИД родителя: при record=1-ИД листа назначений, а 2-ИД СМО" %>
<%@ attribute name="name" required="true" description="название" %>

<style type="text/css">
    #textProtocol {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}templatePrescriptionDialog' class='dialog'>
    <h2>Выбор шаблона листа назначений</h2>
    <div class='rootPane'>
    
<form>
    <msh:panel>
        <msh:row>
        	<msh:autoComplete label="Категория" property="${name}templateCategory"                  vocName="vocTemplateCategory"    horizontalFill="true"/>
        </msh:row>
	        <msh:autoComplete label="Шаблон"    property="${name}templatePrescription" vocName="vocTemplatePrescription" horizontalFill="true" parentAutocomplete="${name}templateCategory" />
        <msh:row>
            <msh:textArea label='Описание назначения' property="${name}decriptionTextPrescription"  horizontalFill="true"/>
        </msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK' onclick='save${name}TemplatePrescription()'/>
                <input type="button" value='Отменить' onclick='cancel${name}TemplatePrescription()'/>
            </td>
            
        </msh:row>
        <msh:row>
        <td valign="top">
			<div id="${name}divListPrescriptions"></div>
		</td>
        </msh:row>
</form>

</div>
</div>
<script type='text/javascript' src='./dwr/interface/PrescriptionService.js'></script>
<script type="text/javascript">
     var theIs${name}TempPrescriptionDialogInitialized = false ;
     var the${name}TempPrescriptionDialog = new msh.widget.Dialog($('${name}templatePrescriptionDialog')) ;



     // Показать
     function show${name}TemplatePrescription() {
//	alert ("showTemplatePrescription started. ");

          // устанавливается инициализация для диалогового окна
         if (!theIs${name}TempPrescriptionDialogInitialized) {
         	init${name}TemplatePrescription() ;
          }
         the${name}TempPrescriptionDialog.show() ;
         $("${name}templateCategory").focus() ;

     } 



     // Отмена
     function cancel${name}TemplatePrescription() {
         the${name}TempPrescriptionDialog.hide() ;
     }

     // Сохранение данных
     function save${name}TemplatePrescription() {
     	if ($('${name}templatePrescription').value==0) {
     		alert("Не выбран шаблон назначения") ;
     	} else {
     		PrescriptionService.getLabListFromTemplate(
     			$('${name}templatePrescription').value ,$('prescriptType').value, {
     				callback: function(aLabList) {
  						the${name}TempPrescriptionDialog.hide() ;
  						if (aLabList!="" && aLabList!=null){
  							var resultList = aLabList.split('#');
	       					if (resultList.length>0) {
	       						for (var i=0; i<resultList.length;i++) {
	       							var resultRow = resultList[i].split(':');
	       							if (resultRow[0]!=null&&resultRow[0]!=""){
	       								addRows(resultList[i],0);
	       							}
	       						}
	       					}
  						}
  						}
     			}
     		);
     	}
     }
     function get${name}PrescriptionById(aId,aIsSave) {
    	 var id=$('${name}templatePrescription').value ;
    	 if (+aId>0) {
    		 id=aId ;
    	 }
    	 if (+aIsSave>0) {
    		 PrescriptionService.getLabListFromTemplate(
  					id ,$('prescriptType').value, {
  						callback: function(aLabList) {
  						the${name}TempPrescriptionDialog.hide() ;
  						if (aLabList!="" && aLabList!=null){
  							var resultList = aLabList.split('#');
	       					if (resultList.length>0) {
	       						for (var i=0; i<resultList.length;i++) {
	       							var resultRow = resultList[i].split(':');
	       							if (resultRow[0]!=null&&resultRow[0]!=""){
	       								addRows(resultList[i],0);
	       							}
	       						}
	       					}
  						}
                        }
                 }
  					); 
    	 } else {
    	 
    	 PrescriptionService.getDescription(id, {
             callback: function(aString) {
                 $('${name}decriptionTextPrescription').value = aString ;
              }
          } ) ;
    	 }
     }

     // инициализация диалогового окна
     function init${name}TemplatePrescription() {
             $('${name}decriptionTextPrescription').readOnly=true ;
             $('${name}decriptionTextPrescription').value = "" ;
             ${name}templatePrescriptionAutocomplete.addOnChangeCallback(function() {
                 get${name}PrescriptionById()
             }) ;
             ${name}templateCategoryAutocomplete.addOnChangeCallback(function() {
                 ${name}templatePrescriptionAutocomplete.setVocId('');
                 $('${name}decriptionTextPrescription').value = "" ;
             if ($('${name}templateCategory').value=="") {
    	         ${name}templatePrescriptionAutocomplete.setUrl('simpleVocAutocomplete/vocTemplatePrescription');
	             ${name}templatePrescriptionAutocomplete.setVocKey('vocTemplatePrescription');
             } else {
    	         ${name}templatePrescriptionAutocomplete.setUrl('simpleVocAutocomplete/vocTemplatePrescriptionParent');
	             ${name}templatePrescriptionAutocomplete.setVocKey('vocTemplatePrescriptionParent');
             }
               //  ${name}templatePrescriptionAutocomplete.setParentId($('${name}templateCategory').value) ;
                 ${name}templatePrescriptionAutocomplete.setVocId('');
             }) ;
             PrescriptionService.listProtocolsByUsername('get${name}PrescriptionById',{
                 callback: function(aString) {
                     $('${name}divListPrescriptions').innerHTML = aString ;
                     
                  }
              } ) ;
             theIs${name}TempPrescriptionDialogInitialized = true ;
     }
</script>
