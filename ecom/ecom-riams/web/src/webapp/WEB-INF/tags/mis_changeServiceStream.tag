<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>

<msh:ifInRole roles="${roles}">
<msh:sideLink name="${title}" action=" javascript:show${name}ChangeServiceStream('.do') " 
	 />

<style type="text/css">
    #${name}ChangeServiceStreamDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ChangeServiceStreamDialog' class='dialog'>
    <h2>Изменить поток обслуживания у СМО</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:autoComplete property="${name}ChangeServiceStream" label="Поток обслуживания" size="100" vocName="vocServiceStream"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}ChangeServiceStream()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}ChangeServiceStream()'/>
            </td>
        </msh:row>
</form>

</div>
</div>
    <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
<script type="text/javascript">
     var theIs${name}ChangeServiceStreamDialogInitialized = false ;
     var the${name}ChangeServiceStreamDialog = new msh.widget.Dialog($('${name}ChangeServiceStreamDialog')) ;
     // Показать
     function show${name}ChangeServiceStream() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}ChangeServiceStreamDialogInitialized) {
         	init${name}ChangeServiceStreamDialog() ;
          }
         the${name}ChangeServiceStreamDialog.show() ;
         $("${name}ChangeServiceStreamName").focus() ;

     }

     // Отмена
     function cancel${name}ChangeServiceStream() {
         the${name}ChangeServiceStreamDialog.hide() ;
         msh.effect.FadeEffect.pushFadeAll();
     }

     // Сохранение данных
     function save${name}ChangeServiceStream() {
     	if (+$('${name}ChangeServiceStream').value==0) {
     		alert("Поле поток обслуживания является обязательным") ;
     		$("${name}ChangeServiceStreamName").focus() ;
     	}  else {
         	TicketService.changeServiceStreamBySmo(
             		'${param.id}', $('${name}ChangeServiceStream').value, {
             			callback: function(aString) {
             				alert(aString) ;
             				window.location.reload() ;
             			}
             		}
             	);
         }
     }

     // инициализация диалогового окна
     function init${name}ChangeServiceStreamDialog() {
     	theIs${name}ChangeServiceStreamDialogInitialized = true ;
     }
</script>
</msh:ifInRole>