<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}BillDialog' class='dialog'>
    <h2>Введите реквизиты счета</h2>
    <div class='rootPane'>

        <msh:form action="e2-ImportFile.do" defaultField="file" fileTransferSupports="true">
            <input type="hidden" name="objectId" id="objectId" value="${param.id}">
            <input type="hidden" name="dirName" id="dirName" value="">

            <div id="dirNameName" name="dirNameName"></div>

            <table width="100%" cellspacing="0" cellpadding="4">
                <tr>
                    <td colspan="2">
                        <input type="button" value="Создать заполнение из файла" onclick="setAction('createEntry','СОЗДАЕМ НОВОЕ ЗАПОЛНЕНИЕ')">
                        <input type="button" value="Импорт дефектов" onclick="setAction('importDefect','ИМПОРТ ДЕФЕКТОВ')">
                    </td>
                </tr>
                <tr><td colspan="2">
                        <input type="button" value="Импорт ошибок ФЛК" onclick="setAction('importFlk','ИМПОРТ ОШИБОК ФЛК')">
                    </td>
                </tr>
                <tr>
                    <td align="right" width="100">Выберите файл:</td>
                    <td><input type="file" id="file" name="file" ></td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr>
                    <td><input type="submit" value='Импортировать' id="${name}Save" /></td>
                    <td><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}BillNumber()'/></td>
                </tr>
            </table>
        </msh:form>

    </div>
</div>

<script type="text/javascript">

var theIs${name}BillDialogInitialized = false ;
var the${name}BillDialog = new msh.widget.Dialog($('${name}BillDialog')) ;
function setAction(val, label) {
    $('dirName').value=val;
    $('dirNameName').innerHTML=label;
}

// Показать
function show${name}BillDialog() {
    the${name}BillDialog.show() ;

}

// Отмена
function cancel${name}BillNumber() {
    the${name}BillDialog.hide() ;
}

</script>