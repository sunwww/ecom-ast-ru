<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="name" required="true" description="название" %>
<%@ attribute name="parentId" required="true" description="id" %>
<div id='${name}Oncology' class='dialog'>
    <iframe id="iframe" src="" height="600" width="1000"></iframe>
    <br>
    <div align="center"><input id="cancel" value="Закрыть" onclick="cancel${name}Oncology()" type="button" style="font-size:25px"></div>
</div>

<script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
<script type="text/javascript">
    iframe.onload = function(){
        var d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#cancelButton');
        if (d!=null && d.parentNode!=null)
            d.parentNode.removeChild(d);
    };

    var theIs${name}Initialized = false ;
    var the${name}OncologyDialog = new msh.widget.Dialog($('${name}Oncology')) ;

    function cancel${name}Oncology() {
        the${name}OncologyDialog.hide();
    }
    // Показать
    function show${name}Oncology(src) {
        // устанавливается инициализация для диалогового окна
        if (!theIs${name}Initialized) {
            init${name}Oncology() ;
        }
        document.getElementById('iframe').src=src;
        the${name}OncologyDialog.show() ;
    }
    // Сохранение данных
    function save${name}Oncology() {
        the${name}OncologyDialog.hide();
        showToastMessage("Успешно создано!",null,true);
    }
    // инициализация диалогового окна
    function init${name}Oncology() {
        theIs${name}Initialized = true ;
    }
</script>
