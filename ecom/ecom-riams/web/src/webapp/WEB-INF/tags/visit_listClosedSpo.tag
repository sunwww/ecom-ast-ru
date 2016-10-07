<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>



<style type="text/css">
    #${name}ClosedSpoDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ClosedSpoDialog' class='dialog'>
    <h2>Добавить визит в закрытый СПО</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:autoComplete property="${name}ClosedSpo" label="Случай СПО" size="100" vocName="vocClosedSpoByPatient"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}ClosedSpo()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}ClosedSpo()'/>
            </td>
        </msh:row>
</form>

</div>
</div>
<script type="text/javascript" src="./dwr/interface/TicketService.js"></script>

<script type="text/javascript"><!--

    var theIs${name}ClosedSpoDialogInitialized = false ;
     var the${name}ClosedSpoDialog = new msh.widget.Dialog($('${name}ClosedSpoDialog')) ;
     // Показать
     function show${name}ClosedSpo() {
    	 // устанавливается инициализация для диалогового окна
         if (!theIs${name}ClosedSpoDialogInitialized) {
         	init${name}ClosedSpoDialog() ;
          }
         TicketService.getDefaultParameter("CLOSED_SPO_ADD_PERIOD","30",{
        	 callback: function (a){
        		// $('cntDays').value=a;
        		 ${name}ClosedSpoAutocomplete.setParentId($('patient').value+"#"+$('dateStart').value+"#"+a);
        	 }
         });
         the${name}ClosedSpoDialog.show() ;
         $("${name}ClosedSpoName").focus() ;

     }

     // Отмена
     function cancel${name}ClosedSpo() {
         the${name}ClosedSpoDialog.hide() ;
         msh.effect.FadeEffect.pushFadeAll();
     }

     // Сохранение данных
     function save${name}ClosedSpo() {
    	 if ($('parent')) {
    		 $('parent').value=$('${name}ClosedSpo').value;
    		 $('parentName').value=$('${name}ClosedSpoName').value;
    	 }
    	 the${name}ClosedSpoDialog.hide() ;
         msh.effect.FadeEffect.pushFadeAll();
     }

     // инициализация диалогового окна
     function init${name}ClosedSpoDialog() {
     	theIs${name}ClosedSpoDialogInitialized = true ;
     }
</script>
