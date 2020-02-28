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
                    <th align="center" width="150">Оценка в карте?**</th>
                </tr>
            </table>
            <table width="100%" cellspacing="10" cellpadding="10" id="table2">
            </table>
            <div>*Информация рассчитана автоматически.</div>
            <div>**Информация оценки из черновика/экспертной карты.</div>
            <div><input type="button" value='Черновик экспертной карты заведующего' id="${name}Cancel" onclick='javascript:draft${name}CloseDocument()'/></div>
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
                callback: function(aResult) {
                    if (aResult!=null && aResult!='[]') {
                        var res = JSON.parse(aResult);
                        var table = document.getElementById('table1');
                        table.innerHTML="<tr><th align=\"center\" width=\"850\">Критерий</th><th align=\"center\" width=\"150\">Выполнен?*</th><th align=\"center\" width=\"150\">Оценка в карте?**</th></tr>";
                        for (var i=0; i<res.length; i++) {
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            var td2 = document.createElement('td');
                            var td3 = document.createElement('td');
                            td1.innerHTML = res[i].crit;td2.innerHTML = res[i].automark;td3.innerHTML = res[i].mark;
                            td1.setAttribute("align","center");
                            td2.setAttribute("align","center");
                            td3.setAttribute("align","center");
                            tr.appendChild(td1);tr.appendChild(td2);tr.appendChild(td3);
                            table.appendChild(tr);
                        }
                        the${name}CloseDisDocumentDialog.show() ;
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
    //Создание черновика ЭК
    function draft${name}CloseDocument() {
        QualityEstimationService.getIfCanCreateNow(ID,null, {
            callback: function (res2) {
                if (res2) { //Выписан позднее дней в настройке/Ещё лежит как обычно
                    QualityEstimationService.createDraftEK(
                        ID, 'PR203', {
                            callback: function (res) {
                                if (res != null) window.location = 'entityEdit-expert_qualityEstimationDraft.do?id=' + res + '&type=BranchManager';
                                else {
                                    alert("Заведующий отделением уже заполнил эту карту, больше редактировать черновик нельзя!");
                                }
                            }
                        }
                    );
                }
                else
                    alert("Истёк срок с момента выписки пациента, во время которого можно создавать и редактировать экспертные карты (и черновики) по 203 приказу!");
            }
        });
    }
</script>