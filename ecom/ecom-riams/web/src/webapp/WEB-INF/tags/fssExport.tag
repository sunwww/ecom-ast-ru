<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="documentId" required="true" description="Поле Id документа" %>

<style type="text/css">
    #${name}fssExport {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<div id='${name}fssExport' class='dialog'>
    <h2>Процесс отправки больничного листа</h2>
    <div class='rootPane'>
        <div id="${name}fssExportResultDiv" />

    </div>
    <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}fssExport()'/>
</div>

<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
<script type="text/javascript">
    var theIs${name}fssExportInitialized = false ;
    var the${name}fssExport= new msh.widget.Dialog($('${name}fssExport')) ;

    function show${name}fssExport() {

        $('${name}fssExportResultDiv').innerHTML="Подождите, идет отправка больничного листа на сервер";
        the${name}fssExport.show();
        DisabilityService.exportDisabilityDocument2('${documentId}', {
            callback: function(a) {

            }
        });
    }
        function cancel${name}fssExport() {
            the${name}fssExport.hide() ;
            msh.effect.FadeEffect.pushFadeAll();
        }
</script>