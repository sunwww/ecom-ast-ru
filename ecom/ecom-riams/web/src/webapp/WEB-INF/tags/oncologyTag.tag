<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="name" required="true" description="название" %>
<%@ attribute name="parentId" required="true" description="id" %>
<div id='${name}Oncology' class='dialog'>
    <iframe id="iframe" src="/riams/entityParentPrepareCreate-oncology_case.do?id=${parentId}&short=ShortCreate" height="600" width="600"></iframe>
    <br>
    <input id="savebtn" value="Сохранить" onclick="save${name}Oncology()" type="button">
    <input id="cancel" value="Закрыть" onclick="cancel${name}Oncology()" type="button">
</div>

<script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
<script type="text/javascript">
    iframe.onload = function(){
        var d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#cancelButton');
        d.parentNode.removeChild(d);
        d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#submitButton');
        d.parentNode.removeChild(d);
    };

    var theIs${name}Initialized = false ;
    var the${name}OncologyDialog = new msh.widget.Dialog($('${name}Oncology')) ;

    function cancel${name}Oncology() {
        the${name}OncologyDialog.hide();
    }
    // Показать
    function show${name}Oncology() {
        // устанавливается инициализация для диалогового окна
        if (!theIs${name}Initialized) {
            init${name}Oncology() ;
        }
        the${name}OncologyDialog.show() ;
    }
    // Сохранение данных
    function save${name}Oncology() {
        document.getElementById('iframe').contentDocument.forms["mainForm"].submit();
        the${name}OncologyDialog.hide();
        var message ="Успешно создано!";
        showToastMessage(message,null,true);
    }
    // инициализация диалогового окна
    function init${name}Oncology() {
        theIs${name}Initialized = true ;
    }
</script>
