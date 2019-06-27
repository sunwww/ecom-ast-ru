<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>


<script type="text/javascript" src="./dwr/interface/ContractService.js">/**/</script>
<div id='${name}ChangePrntMsDialog' class='dialog'>
    <h2>Выберите группу, в которую перенести услугу</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="grTable${name}" border="1" >
    </table>
    <div>
        <form id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="msGr${name}" fieldColSpan="7" horizontalFill="true" label="Группа мед. услуг" vocName="medServiceGroupAll" size="100"/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <br>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Изменить группу' onclick='javascript:changeParent${name}()'/></td>
                <td align="center"><input type="button"  style="font-weight:bold" id="${name}203" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
            </tr>
        </table>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
            <tr>

            <tr><td></td></tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var ID;
    var theIs${name}ChangePrntMsDialogInitialized = false ;
    var the${name}ChangePrntMsDialog = new msh.widget.Dialog($('${name}ChangePrntMsDialog')) ;
    // Показать

    function show${name}(id) {
        ID=id;
        theTableArrow = null ;
        the${name}ChangePrntMsDialog.show() ;
    }
    // Отмена
    function cancel${name}() {
        the${name}ChangePrntMsDialog.hide() ;
    }
    //Перенос услуги
    function changeParent${name}() {
        ContractService.changeParentMedService(ID, $('msGr${name}').value, {
            callback: function (res) {
                showToastMessage(res,null,true);
                window.location.reload();
                the${name}ChangePrntMsDialog.hide();
            }});
    }
</script>