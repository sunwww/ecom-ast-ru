<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #${name}UnionDialog {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}UnionDialog' class='dialog'>
    <h2>Выберите новое заполнение</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4">
                <tr>
                    <msh:autoComplete property="${name}MainListEntry" vocName="vocOpenListEntry"
                                      label="Главное заполнение" fieldColSpan="2" size="100"/>
                </tr>
                <tr>
                    <td><input type="button" value='Объединить' id="${name}Save"
                               onclick='javascript:save${name}UnionDialog()'/></td>
                    <td><input type="button" value='Отмена' id="${name}Cancel"
                               onclick='javascript:cancel${name}UnionDialog()'/></td>
                </tr>
            </table>
        </form>

    </div>
</div>

<div id="${name}ExportHistoryDialog" class="dialog">
    <h2>Журнал выгрузки документов</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId2">
            <table width="100%" cellspacing="0" cellpadding="4" id="${name}ExportHistoryTable">
            </table>
            <input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}ExportHistory()'/>
        </form>

    </div>
</div>

<script type="text/javascript">

    var theIs${name}UnionDialogInitialized = false;
    var the${name}UnionDialog = new msh.widget.Dialog($('${name}UnionDialog'));
    var the${name}ExportHistory = new msh.widget.Dialog($('${name}ExportHistoryDialog'));
    var the${name}someData;

    function show${name}ExportHistory() {
        find${name}ExportHistory();
        the${name}ExportHistory.show();
    }

    function find${name}ExportHistory() {
        Expert2Service.getPacketJournalByBillNumber(null, null, {
            callback: function (ret) {
                var json = JSON.parse(ret);
                var str = "";
                for (var i in json) {
                    var el = json[i];
                    if (el.billNumber) str += "<tr><td>Счет № " + el.billNumber + "</td><td>Дата счета " + el.billDate + "</td><td>Время формирования " + el.createDate + "</td><td><a href='" + el.filename + "'>СКАЧАТЬ ПАКЕТ</a></td></tr>";
                }
                $('${name}ExportHistoryTable').innerHTML = str;
            }
        });
    }

    function show${name}UnionDialog() {
        the${name}UnionDialog.show();
    }

    // Отмена
    function cancel${name}ExportHistory() {
        the${name}ExportHistory.hide();
    }

    function cancel${name}UnionDialog() {
        the${name}UnionDialog.hide();
    }

    //Сохранение
    function save${name}UnionDialog() {
        var newListId = $('${name}MainListEntry').value;
        if (newListId > 0) {
            Expert2Service.unionListEntries(${param.id}, newListId, {
                callback: function () {
                    alert('Объединено!')
                    window.location = "entityList-e2_entryList.do";
                }
            });
            return;
        } else {
            alert('Выберите главное заполнение');
        }

    }
</script>