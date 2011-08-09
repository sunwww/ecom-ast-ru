<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="key" required="true" description="Горячие клавиши" %>
<%@ attribute name="confirm" required="false" description="Сообщение" %>
<%@ attribute name="reason" required="true" description="Закрытие" %>
<%@ attribute name="seria" required="true" description="Серия" %>
<%@ attribute name="number" required="true" description="Номер" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}CloseDocument('.do') " 
	key="${key}" />

<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Выберите причину закрытия</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}Seria" label="Серия"/>
    		<msh:textField property="${name}Number" label="Номер"/>
    	</msh:row>
        <msh:row>
        	<msh:autoComplete label="Причина закрытия" property="${name}Reason" vocName="vocDisabilityDocumentCloseReason"    horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}CloseDocument()"/>
                <input type="button" value='Отменить' onclick='cancel${name}CloseDocument()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}CloseDisDocumentDialogInitialized = false ;
     var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
     // Показать
     function show${name}CloseDocument() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}CloseDisDocumentDialogInitialized) {
         	init${name}CloseDisDocumentDialog() ;
          }
         the${name}CloseDisDocumentDialog.show() ;
         $("${name}ReasonName").focus() ;

     }

     // Отмена
     function cancel${name}CloseDocument() {
         the${name}CloseDisDocumentDialog.hide() ;
     }

     // Сохранение данных
     function save${name}CloseDocument() {
     	if ($('${name}Reason').value==0) {
     		alert("Не выбрана причина закрытия") ;
     		$("${name}ReasonName").focus() ;
     	} else if ($('${name}Seria').value=="") {
     		alert("Поле серия является обязательным") ;
     		$("${name}Seria").focus() ;
     	} else if ($('${name}Number').value=="") {
     		alert("Поле номер является обязательным") ;
     		$("${name}Number").focus() ;
     	} else {
	     	DisabilityService.closeDisabilityDocument(
	     		'${param.id}',$('${name}Reason').value, $('${name}Seria').value,$('${name}Number').value
	     		 ,{
	                   callback: function(aString) {
	                       $('${reason}').value=$('${name}Reason').value ;
	                       $('${reason}ReadOnly').value=aString ;
	                       $('${seria}').value=$('${name}Seria').value ;
	                       $('${seria}ReadOnly').value=$('${name}Seria').value ;
	                       $('${number}').value=$('${name}Number').value ;
	                       $('${number}ReadOnly').value=$('${name}Number').value ;
	                       $('isClose').checked=true ;
	                       
	                       cancel${name}CloseDocument() ;
	                       $('ALT_3').style.display = 'none' ;
	                       //window.document.location.reload()  ;
	                    }
	                }
	         ) ;

         }
     }

     // инициализация диалогового окна
     function init${name}CloseDisDocumentDialog() {
     	DisabilityService.getDataByClose (
     		'${param.id}', {
     			callback: function(aString) {
     				
     				//seria
					var cnt_s = aString.indexOf("#") ;
					$('${name}Seria').value = aString.substring(0,cnt_s) ;
					//number
					var cnt_n = aString.indexOf("#",cnt_s+1) ;
     				$('${name}Number').value =  aString.substring(cnt_s+1,cnt_n)  ;
     				//reason
					var cnt_r = aString.indexOf("#",cnt_n+1) ;
					$('${name}Reason').value = aString.substring(cnt_n+1,cnt_r) ;
     				//reason info
					var cnt_ri = aString.indexOf("#",cnt_r+1) ;
					$('${name}ReasonName').value = aString.substring(cnt_r+1) ;
     			}
     		}
     	);
     	theIs${name}CloseDisDocumentDialogInitialized = true ;
     }
</script>