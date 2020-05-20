<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>

<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2>Отменить консультацию</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="cmbTable" border="1" >
    </table>
    <div>
        <form id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocWfConsultationCancelReason' vocName="vocWfConsultationCancelReason" label='Причина:'/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Отменить назначение' id="${name}Add" onclick='javascript:wfCancel${name}()'/></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td align="right"><input type="button"  style="font-weight:bold" id="${name}203" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
<script type="text/javascript">
    var ID;
    var theIs${name}CloseDocumentDialogInitialized = false ;
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog')) ;
    // Показать
    function show${name}(id) {
        ID=id;
        theTableArrow = null ;
        the${name}CloseDocumentDialog.show();
    }
    // Закрыть
    function cancel${name}() {
        the${name}CloseDocumentDialog.hide() ;
    }
    // Отменить консультацию
    function wfCancel${name}() {
        var reason=document.getElementById('${name}vocWfConsultationCancelReason').value;
        if (reason==null || reason=='-' || reason=='') alert('Выберите причину!'); else {
            reason=reason.replace(/[-0-9 ][ ]/gim,'');
            PrescriptionService.cancelWFPrescription(ID, reason, {
                callback:function (a) {
                    showToastMessage(a,null,true,false,2000);
                    the${name}CloseDocumentDialog.hide() ;
                }
            }) ;
        }
    }
</script>