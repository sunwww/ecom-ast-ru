<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDocument {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2>Информация о предварительных госпитализациях</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table id="table${name}" width="100%" cellspacing="0" cellpadding="4" border="1">
                <tr>
                    <td align="center" width="150"><b>Предварительная дата</b></td>
                    <td align="center" width="350"><b>Диагноз</b></td>
                    <td align="center" width="250"><b>Отделение</b></td>
                    <td align="center" width="180"><b>Врач</b></td>
                    <td align="center" width="180"></td>
                </tr>
            </table>
            <table>
                <tr height="15">
                    <td></td>
                    <td>
                </tr>
            </table>
            <div align="center"><input type="button" value='Закрыть' id="${name}Cancel"
                                       onclick='javascript:the${name}CloseDocumentDialog.hide() ;'/></div>
        </form>
    </div>
</div>

<script type="text/javascript">
    var theIs${name}CloseDocumentDialogInitialized = false;
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog'));

    function editPre(id) {
        window.location = 'entityEdit-smo_planHospitalByVisit.do?id=' + id;
    }

    function show${name}CloseDocument() {
        HospitalMedCaseService.prevPlanHospital($('patient').value, {
                callback: function (res) {
                    var table = document.getElementById("table${name}");
                    for (var i = table.rows.length - 1; i >= 1; i--) table.deleteRow(i);
                    var array = JSON.parse(res);
                    if (res != null) {
                        for (var i = 0; i < array.length; i++) {
                            var obj = array[i];
                            var row = document.createElement('tr');
                            var td1 = document.createElement('td');
                            td1.appendChild(document.createTextNode(obj.date));
                            var td2 = document.createElement('td');
                            td2.appendChild(document.createTextNode(obj.diagnosis));
                            var td3 = document.createElement('td');
                            td3.appendChild(document.createTextNode(obj.lpu));
                            var td4 = document.createElement('td');
                            td4.appendChild(document.createTextNode(obj.fiopost));
                            var td5 = document.createElement('td');
                            td5.innerHTML = "<input type='button' onclick='editPre(" + obj.id + ")' value='ИЗМЕНИТЬ'>";
                            row.appendChild(td1);
                            row.appendChild(td2);
                            row.appendChild(td3);
                            row.appendChild(td4);
                            row.appendChild(td5);
                            table.appendChild(row);
                        }
                        the${name}CloseDocumentDialog.show();
                    } else alert('Актуальных предварительных госпитализаций нет!');
                }
            }
        );
    }
</script>