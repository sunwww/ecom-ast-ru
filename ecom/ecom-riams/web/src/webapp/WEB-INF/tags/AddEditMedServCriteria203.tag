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
    <h2>Медицинские услуги критерия по 203 приказу</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="medServTable" border="1" >
    </table>
    <div>
        <form  id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocMedServ' vocName="medServiceAll" label='Услуга:'/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Добавить' id="${name}Add" onclick='javascript:addMedServ${name}()'/></td>
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
        var table = document.getElementById('medServTable');
        table.innerHTML=" <tr><th align=\"center\" width=\"450\">Услуга</th><th align=\"center\" width=\"300\">Удалить?</th></tr><tr>";
        QualityEstimationService.selectMedServOfCrit203ById(
            ID, {
                callback: function(res) {
                    if (res!="##") {
                        res=res.replace(new RegExp("'", 'g'), "");
                        var all = res.split(',') ;
                        for (var i=0; i<all.length; i++) {
                            var aResult=all[i];
                            if (aResult!=null && aResult!="") {
                                var tr = document.createElement('tr');
                                var td1 = document.createElement('td');
                                td1.innerHTML = aResult;
                                var td2 = document.createElement('td');
                                td2.innerHTML = "<input type=\"button\" value='Удалить' id="+aResult+" onclick='javascript:deleteMedServ${name}(\""+aResult+"\")'/>";
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
    function deleteMedServ${name}(medServId) {
        QualityEstimationService.deleteMedServOfCrit203ById(
            ID,medServId, {
                callback: function(res) {
                    if (res!="0") {
                        $('medServiceCodes').value=res;
                        the${name}CloseDisDocumentDialog.hide() ;
                        reload${name}();
                    }
                }
            }
        );
    }
    //Добавление связи
    function addMedServ${name}() {
        var medServId=$(${name}vocMedServ).value;
        if (medServId!=null && medServId!="") {
            QualityEstimationService.addMedServOfCrit203ById(
                ID,medServId, {
                    callback: function(res) {
                        if (res!="0") {
                            $('medServiceCodes').value=res;
                            the${name}CloseDisDocumentDialog.hide() ;
                            reload${name}();
                        }
                        else alert("Эта услуга уже связана с текущим критерием!");
                        $('${name}vocMedServ').value="";
                        $('${name}vocMedServName').value="";
                    }
                }
            );
        }
    }
</script>