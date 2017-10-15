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

<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Вес и рост меняются. ИМТ будет рассчитан.</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4">
                <tr>
                    <td align="right" width="100">Рост:</td>
                    <td><input type="text" id="heightTxt" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td align="right" width="100">Вес:</td>
                    <td><input type="text" id="weightTxt" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td align="right" width="100">ИМТ:</td>
                    <td><input readonly type="text" id="imt" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td><input type="button" value='Сохранить и закрыть' id="${name}Cancel" onclick='javascript:savecancel${name}CloseDocument()'/></td>
                    <td><input type="button" value='Закрыть без сохранения' id="${name}Cancel" onclick='javascript:cancel${name}CloseDocument()'/></td>
                </tr>
            </table>
        </form>

    </div>
</div>

<script type="text/javascript">
    eventutil.addEventListener($('weightTxt'), "change",function(){
        var w = parseInt($('weightTxt').value);
        var h = parseInt($('heightTxt').value);
        var imt=(w / (0.0001 * h * h)).toFixed(2);
        $('weightTxt').value=w;
        $('imt').value=imt;
        if ($('weightTxt').value=="NaN") $('weightTxt').value="";
    }) ;
    eventutil.addEventListener($('heightTxt'), "change",function(){
        var w = parseInt($('weightTxt').value);
        var h = parseInt($('heightTxt').value);
        var imt=(w / (0.0001 * h * h)).toFixed(2);
        $('heightTxt').value=h;
        $('imt').value=imt;
        if ($('heightTxt').value=="NaN") $('heightTxt').value="";
    }) ;
var theIs${name}CloseDisDocumentDialogInitialized = false ;
var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
// Показать

function show${name}CloseDocument() {
    IMT();
    theTableArrow = null ;
}
function IMT() {
    HospitalMedCaseService.getHWeightIMT(
        '${param.id}', {
            callback: function(res) {
                the${name}CloseDisDocumentDialog.show() ;
                if (res!="##") {
                    var aResult = res.split('#') ;
                    if (aResult[0]!="null") $('heightTxt').value=aResult[0];
                    if (aResult[1]!="null") $('weightTxt').value=aResult[1];
                    if (aResult[2]!="null") $('imt').value=aResult[2];
                }
            }
        }
    );
}
// Отмена
function cancel${name}CloseDocument() {
    the${name}CloseDisDocumentDialog.hide() ;
}
//Сохранение
function savecancel${name}CloseDocument() {
    HospitalMedCaseService.setHWeightIMT(
        '${param.id}',$('heightTxt').value,$('weightTxt').value,$('imt').value, {
            callback: function (res) {
                alert("Значения сохранены!");
                location.reload();
                the${name}CloseDisDocumentDialog.hide() ;
            }
        }
    );
}
</script>