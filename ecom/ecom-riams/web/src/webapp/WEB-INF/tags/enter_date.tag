<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="functionSave" required="true" description="" %>



<style type="text/css">
    #${name}EnterDateDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}EnterDateDialog' class='dialog'>
    <h2>Дата</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}Date" label="Дата"/>
    		
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='OK'  onclick="javascript:save${name}EnterDate()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}EnterDate()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript">
     var theIs${name}EnterDateDialogInitialized = false ;
     var the${name}EnterDateDialog = new msh.widget.Dialog($('${name}EnterDateDialog')) ;
     var someId = 0;
     // Показать
     function show${name}EnterDate(aSomeId) {
         if (aSomeId) someId=aSomeId;
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}EnterDateDialogInitialized) {
         	init${name}EnterDateDialog() ;
          }
         the${name}EnterDateDialog.show() ;
         $("${name}Date").focus() ;

     }

     // Отмена
     function cancel${name}EnterDate() {
         the${name}EnterDateDialog.hide() ;
     }

     // Сохранение данных
     function save${name}EnterDate() {
     	if ($('${name}Date').value==0) {
     		alert("Поле дата является обязательным") ;
     		$("${name}Date").focus() ;
     	}  else {
     	    if (+someId>0) {
                ${functionSave}(someId,$('${name}Date').value) ;
            } else {
                ${functionSave}($('${name}Date').value) ;
            }

	     	cancel${name}EnterDate() ;
         }
     }

     // инициализация диалогового окна
     function init${name}EnterDateDialog() {
     	
     	new dateutil.DateField($('${name}Date')) ;
     	
     	theIs${name}EnterDateDialogInitialized = true ;
     }
</script>
