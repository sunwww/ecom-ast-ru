<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ attribute name="name" required="true" description="название" %>
<%@ attribute name="medCaseId" required="true" description="id" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<msh:ifInRole roles="${roles}">

<div id='${name}Calc' class='dialog'>

</div>

<script type="text/javascript">
    var theIs${name}Initialized = false ;
    var the${name}CalcDialog = new msh.widget.Dialog($('${name}Calc')) ;

    function cancel${name}Calc() {
        the${name}CalcDialog.hide();
    }
    // Показать
    function show${name}Calc() {
        // устанавливается инициализация для диалогового окна
        if (!theIs${name}Initialized) {
            init${name}Calc() ;
        }
        document.getElementById('${name}Calc').innerHTML='<iframe id="iframe" src="/riams/entityParentPrepareCreate-calc_calculationsResult.do?id=${medCaseId}&short=ShortCreate" height="700" width="1300"></iframe>\n' +
            '    <br>';
        the${name}CalcDialog.show() ;
    }
    // Сохранение данных
    function save${name}Calc() {
        document.getElementById('iframe').contentDocument.forms["mainForm"].submit();
        the${name}CalcDialog.hide();
        var message ="Вычисление успешно создано!";
        showToastMessage(message,null,true);
    }
    // инициализация диалогового окна
    function init${name}Calc() {
        theIs${name}Initialized = true ;
    }
</script>
</msh:ifInRole>