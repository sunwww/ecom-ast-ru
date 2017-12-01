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
    <h2>Информация о критериях</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4" id="table1" border="1">
                <tr>
                    <th align="center" width="450">Критерий</th>
                    <th align="center" width="150">Выполнен?*</th>
                </tr>
            </table>
            <table width="100%" cellspacing="10" cellpadding="10" id="table2">
            </table>
            <div>*Информация рассчитана автоматически.</div>
        </form>
    </div>
</div>

<script type="text/javascript">
    var ID;
    var theIs${name}CloseDisDocumentDialogInitialized = false ;
    var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
    // Показать

    function show${name}CloseDocument(id) {
        ID=id;
        showCriteriasByDiagnosis();
        theTableArrow = null ;
    }
    function showCriteriasByDiagnosis() {
        var table2 = document.getElementById('table2');
        table2.innerHTML="<tr><td></td></tr><tr><td align=\"center\"><input type=\"button\" value=\'Закрыть\' id=\"${name}Cancel\" onclick=\'javascript:cancel${name}CloseDocument()\'/></td></tr><tr><td></td></tr>";
        QualityEstimationService.showCriteriasByDiagnosis(
            ID, {
                callback: function(res) {
                    //alert(res);
                    if (res!="##") {
                        the${name}CloseDisDocumentDialog.show() ;
                        var all = res.split('!') ;
                        var table = document.getElementById('table1');
                        table.innerHTML="<tr><th align=\"center\" width=\"150\">Критерий</th><th align=\"center\" width=\"150\">Выполнен?*</th></tr>";
                        for (var i=0; i<all.length-1; i++) {
                            var aResult = all[i].split('#');
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            var td2 = document.createElement('td');
                            td1.innerHTML = aResult[1];td2.innerHTML = aResult[2];
                            td1.align = "center";td2.align = "center";
                            tr.appendChild(td1);tr.appendChild(td2);
                            table.appendChild(tr);
                        }
                    }
                    else alert("Для основного клинического диагноза госпитализации не найдено данных по 203 приказу!");
                }
            }
        );
    }
    // Отмена
    function cancel${name}CloseDocument() {
        the${name}CloseDisDocumentDialog.hide() ;
    }
</script>