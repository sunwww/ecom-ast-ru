<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2>Вес и рост меняются. ИМТ будет рассчитан.</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4">
                <tr>
                    <td align="right" width="100">Рост:</td>
                    <td><input type="text" id="height${name}" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td align="right" width="100">Вес:</td>
                    <td><input type="text" id="weight${name}" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td align="right" width="100">ИМТ:</td>
                    <td><input readonly type="text" id="imt${name}" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td><input type="button" value='Сохранить и закрыть' id="${name}Cancel" onclick='javascript:savecancel${name}CloseDocument()'/></td>
                    <td><input type="button" value='Закрыть без сохранения' id="${name}Cancel" onclick='javascript:the${name}CloseDocumentDialog.hide()'/></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    eventutil.addEventListener($('weight${name}'), "change",function(){
        var w = parseInt($('weight${name}').value);
        var h = parseInt($('height${name}').value);
        var imt${name}=(w / (0.0001 * h * h)).toFixed(2);
        $('weight${name}').value=w;
        $('imt${name}').value=imt${name};
        if ($('weight${name}').value=="NaN") $('weight${name}').value="";
    }) ;
    eventutil.addEventListener($('height${name}'), "change",function(){
        var w = parseInt($('weight${name}').value);
        var h = parseInt($('height${name}').value);
        var imt${name}=(w / (0.0001 * h * h)).toFixed(2);
        $('height${name}').value=h;
        $('imt${name}').value=imt${name};
        if ($('height${name}').value=="NaN") $('height${name}').value="";
    }) ;
var theIs${name}CloseDocumentDialogInitialized = false ;
var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog')) ;

function show${name}CloseDocument() {
    HospitalMedCaseService.getHWeightIMT(
        '${param.id}', {
            callback: function(res) {
                the${name}CloseDocumentDialog.show() ;
                if (res!=null) {
                    var aResult = JSON.parse(res) ;
                    $('height${name}').value=(typeof aResult.height==='undefined')? '': aResult.height;
                    $('weight${name}').value=(typeof aResult.weight==='undefined')? '': aResult.weight;
                    $('imt${name}').value=(typeof aResult.imt==='undefined')? '': aResult.imt;
                }
            }
        }
    );
}

function savecancel${name}CloseDocument() {
    HospitalMedCaseService.setHWeightIMT(
        '${param.id}',$('height${name}').value,$('weight${name}').value,$('imt${name}').value, {
            callback: function (res) {
                alert("Значения сохранены!");
                location.reload();
                the${name}CloseDocumentDialog.hide() ;
            }
        }
    );
}
</script>