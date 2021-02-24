<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>

<msh:ifInRole roles="${roles}">

    <style type="text/css">
        #${name}IntakeInfoDialog {
            visibility: hidden;
            display: none;
            position: absolute;
        }
    </style>

    <div id='${name}IntakeInfoDialog' class='dialog'>
        <h2>Считайте штрих-код</h2>
        <div class='rootPane'>

            <form action="javascript:void(0)">
                <msh:panel>
                    <msh:row>
                        <input type="hidden" id="${name}List" name="${name}List"/>
                        <!-- <msh:textField property="${name}Barcode" label="Штрих-код"/>-->
                        <input type="text" id="${name}Barcode" name="${name}Barcode" autofocus="autofocus"/><br>
                        <textarea id="textArea" ROWS=10 COLS=50 readonly></textarea>
                    </msh:row>
                </msh:panel>
                <msh:row>
                    <td colspan="6">
                        <!--  <input type="button" name="${name}butOK" id="${name}butOK" value='OK'  onclick="javascript:save${name}IntakeInfo()"/>  -->
                        <input type="button" value='Закрыть' onclick='javascript:cancel${name}IntakeInfo()'/>
                    </td>
                </msh:row>
            </form>

        </div>
    </div>

    <script type="text/javascript">
        var theIs${name}IntakeInfoDialogInitialized = false;
        var the${name}IntakeInfoDialog = new msh.widget.Dialog($('${name}IntakeInfoDialog'));

        // Показать
        function show${name}IntakeInfo() {
            the${name}IntakeInfoDialog.show();
            ${name}Barcode.focus();

        }

        // Отмена
        function cancel${name}IntakeInfo() {
            the${name}IntakeInfoDialog.hide();
        }

        // Сохранение данных
        function save${name}IntakeInfo(aBarcodeNumber) {

        }


        // инициализация диалогового окна
        function init${name}IntakeInfoDialog() {
            ${name}Barcode.value = '123';
        }

        BarcodeBarcode.onkeydown = handle;

        function handle(aEvent) {
            var keyCode = aEvent.keyCode;
            if (keyCode == 13) {
                if ($('${name}Barcode').value != null && $('${name}Barcode').value != '') {
                    PrescriptionService.checkTransferServiceBarcode($('${name}Barcode').value, {
                        callback: function (a) {
                            textArea.value = a;
                            BarcodeBarcode.value = "";
                        }
                    });
                }
            }
        }

    </script>
</msh:ifInRole>