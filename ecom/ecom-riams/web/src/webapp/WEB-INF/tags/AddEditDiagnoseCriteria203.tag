<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/QualityEstimationService.js">/**/</script>
<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Диагнозы критерия по 203 приказу</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="diagnTable" border="1" >
    </table>
    <div>
<form id="${name}">
<msh:panel>
    <msh:row>
        <msh:comboBox size='300' horizontalFill="true" property='${name}vocidc10' vocName="vocIdc10" label='Диагноз:'/>
    </msh:row>
</msh:panel>
</form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Добавить' id="${name}Add" onclick='javascript:addDiagnose${name}()'/></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td align="right"><input type="button"  style="font-weight:bold" id="${name}203" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
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
    var theIs${name}CloseDisDocumentDialogInitialized = false ;
    var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
    // Показать

    function show${name}(id) {
        ID=id;
        theTableArrow = null ;
        reload${name}();
    }
    // Отмена
    function cancel${name}() {
        the${name}CloseDisDocumentDialog.hide() ;
    }
    //Перезагрузка
    function reload${name}() {
        var table = document.getElementById('diagnTable');
        table.innerHTML=" <tr><th align=\"center\" width=\"450\">МКБ</th><th align=\"center\" width=\"300\">Удалить?</th></tr><tr>";
        QualityEstimationService.selectDiagnoseOfCrit203ById(
            ID, {
                callback: function(res) {
                    if (res!="##") {
                        var all = res.split('!') ;
                        for (var i=0; i<all.length-1; i++) {
                            var aResult=all[i].split('#');
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            if (aResult[1]!="null" && aResult[0]!="null") {
                                td1.innerHTML = aResult[1];
                                var td2 = document.createElement('td');
                                td2.innerHTML = "<input type=\"button\" value='Удалить' id="+aResult[0]+" onclick='javascript:deleteDiagnose${name}("+aResult[0]+")'/>";
                                td1.align = "center";
                                td2.align = "center";
                                tr.appendChild(td1);
                                tr.appendChild(td2);
                                table.appendChild(tr);
                            }
                        }
                    }
                    the${name}CloseDisDocumentDialog.show();
                }
            }
        );
    }
    //Удаление связи
    function deleteDiagnose${name}(idc10Id) {
        QualityEstimationService.deleteDiagnoseOfCrit203ById(
            ID,idc10Id, {
                callback: function() {
                    the${name}CloseDisDocumentDialog.hide() ;
                    reload${name}();
                }
            }
        );
    }
    //Добавление связи
    function addDiagnose${name}() {
        var idc10Id=$(${name}vocidc10).value;
        if (idc10Id!=null && idc10Id!="") {
            QualityEstimationService.addDiagnoseOfCrit203ById(
                ID,idc10Id, {
                    callback: function(res) {
                        if (res==true) {
                        the${name}CloseDisDocumentDialog.hide() ;
                        reload${name}();
                        }
                        else alert("Этот диагноз уже связан с текущим критерием!");
                        $('${name}vocidc10').value="";
                        $('${name}vocidc10Name').value="";
                    }
                }
            );
        }
    }
</script>