<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>

<style type="text/css">
    #${name}GetAccountDiaglog {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}GetAccountDiaglog' class='dialog'>
    <h2>Укажите счет.</h2>
    <div class='rootPane'>
        <h3>${title}</h3>
        <form action="javascript:">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="${name}account" size="100" vocName="vocAccountByJuridicalPerson"
                                      label="Счет"/>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="button" value="ОК" onclick="save${name}GetAccount()"/>
                        <input type="button" value="Отмена" onclick="cancel${name}GetAccount()"/>

                    </td>
                </msh:row>
            </msh:panel>
        </form>

    </div>
</div>
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>

<script type="text/javascript">
    var theIs${name}GetAccountDiaglogInitialized = false;
    var the${name}GetAccountDiaglog = new msh.widget.Dialog($('${name}GetAccountDiaglog'));
    the${name}SMOID = 0;
    the${name}Function = "set${name}Account";

    // Показать
    function show${name}GetAccount(aSmo, aFunction) {
        the${name}SMOID = aSmo;
        if (aFunction != null && aFunction != "") {
            the${name}Function = aFunction;
        }
        // устанавливается инициализация для диалогового окна
        if (theIs${name}GetAccountDiaglogInitialized) {
        }

        the${name}GetAccountDiaglog.show();

    }

    // Отмена
    function cancel${name}GetAccount() {
        the${name}GetAccountDiaglog.hide();
    }

    // Отмена
    function save${name}GetAccount() {
        if (+('${name}account').value > 0) {
            alert("Укажите счет!!");
        } else {
            eval(the${name}Function + "(the${name}SMOID,$('${name}account').value)");


            the${name}GetAccountDiaglog.hide();
        }
    }

    function set${name}Account(aSmoId, aAccount) {
        HospitalMedCaseService.setAccountBySmo(aSmoId, aAccount, {
            callback: function (aResult) {
                alert(aResult);
            }
        });
    }

    // инициализация диалогового окна
    function init${name}GetAccountDiaglog() {
        theIs${name}GetAccountDiaglogInitialized = true;
    }
</script>