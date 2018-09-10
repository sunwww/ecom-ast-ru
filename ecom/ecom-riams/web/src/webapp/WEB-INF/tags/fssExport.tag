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

    .ok {
         border: 1px dashed #634f36;
         background: #ebff95;
         font-family: "Courier New", Courier, monospace;
         padding: 7px;
         margin: 0 0 1em;
         white-space: pre-wrap;
     }
    .error {
        border: 1px dashed #634f36;
        background: #ffcbd5;
        font-family: "Courier New", Courier, monospace;
        padding: 7px;
        margin: 0 0 1em;
        white-space: pre-wrap;
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
        DisabilityService.exportDisabilityDoc('${documentId}', {

            callback: function(json) {
                json  = JSON.parse(json);
                if(json.error!=null && json.code!=null){
                    $('${name}fssExportResultDiv').innerHTML=json.error+"<br> Обновите страницу и попробуйте еще раз";
                }else {
                var resultHTML="<p class='#res'>" +
                    "<span style='font-size: medium; color: #2d2d2b; '>" +
                    "#TEXT" +
                    "#ЭЛН:"+json.lncode +"<br>"+
                    "Ответ:"+json.message +"<br>"+
                    "Номер запроса:"+json.requestId +"<br><br>";

                if(json.status==0){
                    resultHTML = resultHTML.replace("#res","error");
                    resultHTML = resultHTML.replace("#TEXT","Были найдены ошибки:");
                    json.errors.forEach(
                        function(entry) {
                            resultHTML+="Ошибка: "+entry.errmess+"<br>";
                        });
                }else {
                    resultHTML = resultHTML.replace("#TEXT","ЭЛН успешно выгружен");
                    resultHTML = resultHTML.replace("#res","ok");
                }
                $('${name}fssExportResultDiv').innerHTML=resultHTML;
                }
            }
        });
    }
        function cancel${name}fssExport() {
            the${name}fssExport.hide() ;
            msh.effect.FadeEffect.pushFadeAll();
        }
</script>