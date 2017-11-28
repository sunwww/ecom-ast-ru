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
<script type="text/javascript" src="./dwr/interface/QualityEstimationService.js">/**/</script>
<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Информация о критериях по диагнозу</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <label>По 203 приказу при постановке этого диагноза (основной клинический) необходимо выполнить следующие критерии:</label>
            <table width="100%" cellspacing="0" cellpadding="4" id="table1" border="1">
                <tr>
                    <th align="center" width="450">Критерий</th>
                </tr>
            </table>
            <table width="100%" cellspacing="10" cellpadding="10" id="table2">
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    var ID;
    var REG;
    var PRIOR;
    var FORM;
    var theIs${name}CloseDisDocumentDialogInitialized = false ;
    var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
    // Показать

    function show${name}CloseDocument(id,reg,prior,form) {
        ID=id;
        REG=reg;
        PRIOR=prior;
        FORM=form;
        showCriteriasWhenDiagnoseCreating();
        theTableArrow = null ;
    }
    function showCriteriasWhenDiagnoseCreating() {
        var table2 = document.getElementById('table2');
        table2.innerHTML="<tr><td></td></tr><tr><td align=\"center\"><input type=\"button\" value=\'Принято к сведению.\' id=\"${name}Cancel\" onclick=\'javascript:cancel${name}CloseDocument()\'/></td></tr><tr><td></td></tr>";
        QualityEstimationService.showJustCriterias(
            ID,REG,PRIOR, {
                callback: function(res) {
                    if (res!="##") {
                        the${name}CloseDisDocumentDialog.show() ;
                        var table = document.getElementById('table1');
                        table.innerHTML="<tr><th align=\"center\" width=\"150\">Критерий</th>";
                        var aResult = res.split('#');
                        for (var i=0; i<aResult.length-1; i++) {
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            td1.innerHTML = aResult[i];
                            td1.align = "center";
                            tr.appendChild(td1);
                            table.appendChild(tr);
                        }
                    }
                    else FORM.submit();
                }
            }
        );
    }
       // Отмена
    function cancel${name}CloseDocument() {
        the${name}CloseDisDocumentDialog.hide() ;
        FORM.submit();
    }
</script>