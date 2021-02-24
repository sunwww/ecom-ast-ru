<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>

<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2>Период, на который скопировать день</h2>
    <div>
        <form id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:textField property="${name}date" label="Период с" fieldColSpan="3"/>
                    <msh:textField property="${name}date2" label="по" fieldColSpan="3"/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Копировать' id="${name}Add"
                                          onclick='javascript:copy${name}()'/></td>
            </tr>
            <tr>
                <td></td>
            </tr>
            <tr>
                <td align="right"><input type="button" style="font-weight:bold" id="${name}203" value='Закрыть'
                                         id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
            </tr>
        </table>
    </div>
</div>
<script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
<script type="text/javascript">
    var ID;
    new dateutil.DateField($('${name}date'));
    new dateutil.DateField($('${name}date2'));
    $('${name}date').className += " required";
    var theIs${name}CloseDocumentDialogInitialized = false;
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog'));

    // Показать
    function show${name}(id) {
        ID = id;
        theTableArrow = null;
        the${name}CloseDocumentDialog.show();
    }

    // Закрыть
    function cancel${name}() {
        the${name}CloseDocumentDialog.hide();
    }

    // Копировать
    function copy${name}() {
        if ($('${name}date').value != '') {
            var dateParts = $('${name}date').value.split(".");
            var oDate = new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);
            if ($('${name}date2').value == '') $('${name}date2').value = $('${name}date').value;
            var dateParts2 = $('${name}date2').value.split(".");
            var oDate2 = new Date(dateParts2[2], (dateParts2[1] - 1), dateParts2[0]);
            if (oDate2.getTime() >= oDate.getTime()) {
                WorkCalendarService.copyDay(ID, $('${name}date').value, $('${name}date2').value, {
                    callback: function (aResult) {
                        the${name}CloseDocumentDialog.hide();
                        $('${name}date').value = '';
                        $('${name}date2').value = '';
                        showToastMessage(aResult, null, true);
                        updateTable();
                    },
                    errorHandler: function (aMessage) {
                        alert("Не удалось скопировать! " + aMessage);
                    }
                });
            } else alert('Последняя дата меньше первой!');
        } else alert('Ввыедите период!');
    }
</script>