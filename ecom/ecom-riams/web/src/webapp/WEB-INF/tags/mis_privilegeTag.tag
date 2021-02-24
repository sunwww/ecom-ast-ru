<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="name" required="true" description="название" %>
<div id='${name}Privilege' class='dialog'>
    <iframe id="iframe" src="/riams/entityParentPrepareCreate-mis_privilege.do?id=0&short=ShortCreate" height="200"
            width="600"></iframe>
    <br>
    <input value="Сохранить" onclick="save${name}Privilege()" type="button">
    <input id="cancel" value="Закрыть" onclick="cancel${name}Privilege()" type="button">
</div>

<script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
<script type="text/javascript">
    var PersonId;
    iframe.onload = function () {
        console.info('iframe загружен');
        var d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#cancelButton');
        d.parentNode.removeChild(d);
        d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#submitButton');
        d.parentNode.removeChild(d);
        var d = document.getElementById('servedPerson');

        PatientService.getPatientFromContractPerson(d.value, {
            callback: function (aResult) {
                PersonId = aResult;
            }
        });
    };

    var theIs${name}Initialized = false;
    var the${name}PrivilegeDialog = new msh.widget.Dialog($('${name}Privilege'));

    function cancel${name}Privilege() {
        the${name}PrivilegeDialog.hide();
    }

    // Показать
    function show${name}Privilege() {
        // устанавливается инициализация для диалогового окна
        if (!theIs${name}Initialized) {
            init${name}Privilege();
        }
        the${name}PrivilegeDialog.show();
    }

    // Сохранение данных
    function save${name}Privilege() {
        var beginDate = document.getElementById('iframe').contentDocument.documentElement.querySelector('#beginDate');
        var endDate = document.getElementById('iframe').contentDocument.documentElement.querySelector('#endDate');
        var category = document.getElementById('iframe').contentDocument.documentElement.querySelector('#category');
        var numberDoc = document.getElementById('iframe').contentDocument.documentElement.querySelector('#numberDoc');
        var serialDoc = document.getElementById('iframe').contentDocument.documentElement.querySelector('#serialDoc');
        PatientService.savePrivilege(PersonId, numberDoc.value, serialDoc.value, beginDate.value, endDate.value, category.value, {
            callback: function () {
            }
        });

        the${name}PrivilegeDialog.hide();
    }

    // инициализация диалогового окна
    function init${name}Privilege() {
        theIs${name}Initialized = true;
    }
</script>
