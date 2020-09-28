<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="status" required="true" description="tttt" %>
<%@ attribute name="type" required="false" description="Тип заявки" %>


<msh:ifInRole roles="${roles}">

<style type="text/css">
    #${name}ClaimStartDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ClaimStartDialog' class='dialog'>
    <h2>Дата</h2>
    <input type='hidden' id='${name}ExecutorVoc' name='${name}ExecutorVoc' >
    <input type='hidden' id='${name}ClaimType' name='${name}ClaimType'>
    <input type='hidden' id='${name}ClaimId' name='${name}ClaimId'>
    <input type='hidden' id='${name}ClaimStatus' name='${name}ClaimStatus'>
     <div class='rootPane'>
    
<form action="javascript:void(0)">
    <msh:panel>
    	<msh:row>
    		<msh:textField property="${name}Date" label="Дата"/>
    		<msh:textField property="${name}Time" label="Время"/>
    		<msh:autoComplete vocName="executorByClaimType" parentId="${name}ClaimType" property="${name}Executor" label="Исполнитель"/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" name="${name}butOK" id="${name}butOK" value='OK'  onclick="javascript:set${name}Status()"/>
                <input type="button" value='Отменить' onclick='javascript:cancel${name}ClaimStart()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript">
     var theIs${name}ClaimStartDialogInitialized = false ;
     var claimId =0;
    
     //executorsByCurrentUserName 
     var the${name}ClaimStartDialog = new msh.widget.Dialog($('${name}ClaimStartDialog')) ;
     // Показать
     function show${name}ClaimStart(aSelectName) {
    	 if (aSelectName==0) {
    		 $('${name}Executor').value="";
    		 $('${name}ExecutorName').value="";
    		 $('${name}ExecutorName').disabled=true;
    	 } else {
    		 $('${name}ExecutorName').disabled=false;
    	 }
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}ClaimStartDialogInitialized) {
         	init${name}ClaimStartDialog() ;
          }
         var claimType = ($('${type}')!=null && $('${type}').value!='')?$('${type}').value : $('${name}ClaimType').value;
       
         $('${name}ClaimType').value=claimType;
         //${name}ExecutorAutocomplete.setVocName($('${name}ExecutorVoc').value);
         ${name}ExecutorAutocomplete.setParentId(+$('${name}ClaimType').value);
         the${name}ClaimStartDialog.show() ;
         $("${name}Date").focus() ;

     }

     // Отмена
     function cancel${name}ClaimStart() {
         the${name}ClaimStartDialog.hide() ;
     }

     // Сохранение данных
     function save${name}ClaimStart() {
    	 set${name}Started ();
     }
     function set${name}Status() {
    	 var comment='';
    	 var username = $('${name}Executor').value;
     	ClaimService.setStatusClaim($('${name}ClaimStatus').value, $('${name}ClaimId').value, $('${name}Date').value, $('${name}Time').value, username, comment, {
     		callback: function (a) {
     			$('${name}ClaimId').value='';
     			if ($('${name}ClaimType')) $('${name}ClaimType').value='';
     			
     			window.location.reload();
     		}
     	});
     }
    
     
         // инициализация диалогового окна
     function init${name}ClaimStartDialog() {
     	new dateutil.DateField($('${name}Date')) ;
     	new timeutil.TimeField($('${name}Time')) ;
     	var currentDate = new Date;
		var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
		var textMonth = currentDate.getMonth()+1;
		var textMonth = textMonth<10?'0'+textMonth:textMonth;
		var textYear =currentDate.getFullYear();
		$('${name}Date').value=textDay+'.'+textMonth+'.'+textYear;
		var textMinute = currentDate.getMinutes() ;
		var textHour = currentDate.getHours() ;
		$('${name}Time').value=(textHour<10?'0'+textHour:textHour)+':'+(textMinute<10?'0'+textMinute:textMinute);
     	theIs${name}ClaimStartDialogInitialized = true ;
     	 eventutil.addEnterSupport('${name}Date', '${name}Time') ;
     	 eventutil.addEnterSupport( '${name}Time','${name}butOK') ;
     	 

     }
</script>
</msh:ifInRole>