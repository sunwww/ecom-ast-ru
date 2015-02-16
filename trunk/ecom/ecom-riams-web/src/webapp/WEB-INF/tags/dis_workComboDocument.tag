<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="key" required="true" description="Горячие клавиши" %>
<%@ attribute name="confirm" required="false" description="Сообщение" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}WorkComboDocument('.do') " 
	key="${key}" />

<style type="text/css">
    #${name}WorkComboDisDocumentDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    #${name}Job {
	    text-transform: uppercase;
    }
</style>

<div id='${name}WorkComboDisDocumentDialog' class='dialog'>
    <h2>Создание бланка по совместительству</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:separator label="Новый бланк" colSpan="4"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Seria" label="Серия"/>
    		<msh:textField property="${name}Number" label="Номер" size="20"/>
    	</msh:row>
        <msh:row>
        	<msh:autoComplete label="Тип совместит.:" property="${name}WorkComboType" vocName="vocCombo" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Место раб. по совмест:" property="${name}Job"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}WorkComboDocument()"/>
                <input type="button" value='Отменить' onclick='cancel${name}WorkComboDocument()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}WorkComboDisDocumentDialogInitialized = false ;
     var the${name}WorkComboDisDocumentDialog = new msh.widget.Dialog($('${name}WorkComboDisDocumentDialog')) ;
     // Показать
     function show${name}WorkComboDocument() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}WorkComboDisDocumentDialogInitialized) {
         	init${name}WorkComboDisDocumentDialog() ;
          }
         the${name}WorkComboDisDocumentDialog.show() ;
         $("${name}Number").focus() ;
     }

     // Отмена
     function cancel${name}WorkComboDocument() {
         the${name}WorkComboDisDocumentDialog.hide() ;
     }

     // Сохранение данных
     function save${name}WorkComboDocument() {
     	if ($('${name}Job').value=="") {
     		alert("Поле места работы является обязательным") ;
     		$("${name}Job").focus() ;
     	//} else if ($('${name}Seria').value=="") {
     	//	alert("Поле серия является обязательным") ;
     	//	$("${name}Seria").focus() ;
     	} else if ($('${name}Number').value=="") {
     		alert("Поле номер является обязательным") ;
     		$("${name}Number").focus() ;
     	} else {
	     	DisabilityService.createWorkComboDocument(
	     		'${param.id}',$('${name}Job').value, $('${name}Seria').value,$('${name}Number').value
	     		 ,{
	                   callback: function(aString) {
	        				alert('Создан бланк по совместительству!');
	         				window.location.href='entityParentView-dis_document.do?id='+aString ;
	                    }
	                }
	         ) ;
         }
     }

     // инициализация диалогового окна
     function init${name}WorkComboDisDocumentDialog() {
     	theIs${name}WorkComboDisDocumentDialogInitialized = true ;
     }
</script>