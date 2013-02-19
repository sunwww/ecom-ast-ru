<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="title" required="true" description="Название поля waitingMessage" %>
<%@ attribute name="name" required="true" description="Название поля waitingMessage" %>




<style type="text/css">
    #${name}waitingMessageDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='${name}waitingMessageDialog' class='dialog'>
    <h2>${title}</h2>
    <div class='rootPane'>
    	       <i>Идёт проверка введенных данных</i>
    	<%--
        <msh:row>
            <td colspan="6">
                <input type="button" id='${name}buttonwaitingMessageOk' value='OK' onclick='save${name}waitingMessage()'/>
                <input type="button" value='Отменить' onclick='cancel${name}waitingMessage()'/>
            </td>
        </msh:row>
         --%>
</div>
</div>

<script type="text/javascript">


    

    var the${name}waitingMessageDialog = new msh.widget.Dialog($('${name}waitingMessageDialog')) ;
    var the${name}Fields ;
    var theIs${name}waitingMessageDialogInitialized = false ;

    function show${name}waitingMessage() {
        if(!theIs${name}waitingMessageDialogInitialized) {
        	init${name}waitingMessage() ;
        }
        the${name}waitingMessageDialog.show() ;
        
    }

    function cancel${name}waitingMessage() {
        the${name}waitingMessageDialog.hide() ;
    }



    function init${name}waitingMessage() {}

    

</script>



