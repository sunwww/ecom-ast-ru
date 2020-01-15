<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>

<div id='${name}BillDialog' class='dialog'>
    <h2>Введите реквизиты счета</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4">
                <tr>
                    <td align="right" width="100">Номер счета:</td>
                    <td><input type="text" id="${name}BillNumber" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td align="right" width="100">Дата счета</td>
                    <td><input type="text" id="${name}BillDate" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td align="right" width="100">Примечание</td>
                    <td><input type="text" id="${name}BillComment" maxlength="50" size="100"></td>
                </tr>

                <tr>
                    <td><input type="button" value='Присвоить' id="${name}Save" onclick='javascript:save${name}BillNumber()'/></td>
                    <td><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}BillNumber()'/></td>
                </tr>
            </table>
        </form>

    </div>
</div>

<script type="text/javascript">

var theIs${name}BillDialogInitialized = false ;
var the${name}BillDialog = new msh.widget.Dialog($('${name}BillDialog')) ;
var the${name}someData;
new dateutil.DateField(${name}BillDate) ;
// Показать
function show${name}BillDialog(someStuff) {
    if (someStuff && someStuff!=""){
        the${name}someData=someStuff;
        var dt =the${name}someData.split("&");
        var oldBillDate= dt[2].split("=")[1];
        var oldBillNumber= dt[3].split("=")[1];
        var oldBillComment= dt[6].split("=")[1];
        $('${name}BillNumber').value=oldBillNumber;
        $('${name}BillDate').value=oldBillDate;
        $('${name}BillComment').value=oldBillComment;
    }
    the${name}BillDialog.show() ;
}

// Отмена
function cancel${name}BillNumber() {
    the${name}BillDialog.hide() ;
}
//Сохранение
function save${name}BillNumber() {
    var newBillNumber=$('${name}BillNumber').value;
    var newBillDate=$('${name}BillDate').value;
    var newBillComment=$('${name}BillComment').value;
    if (newBillNumber==''||newBillDate=='') {
        if (!confirm('Вы хотите очистить информацию о счете?')) {
            alert('Укажите номер и дату счета');
            return;
        }
    }
    var dt =the${name}someData.split("&");
    alert(the${name}someData);
var type = dt[1].split("=")[1];
var oldBillDate= dt[2].split("=")[1];
var oldBillNumber= dt[3].split("=")[1];
var serviceStream = dt[4].split("=")[1];
var isForeign=dt[5].split("=")[1];
var fileType=dt[7].split("=")[1];
    Expert2Service.saveBillDateAndNumber(${param.id},type,serviceStream,oldBillNumber, oldBillDate,newBillNumber,newBillDate,isForeign,newBillComment, fileType, {
        callback: function (a) {
            cancel${name}BillNumber();
            window.location.reload();
        }
    })

}
</script>