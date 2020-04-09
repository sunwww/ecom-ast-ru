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
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2>Список микробиологических исследований пациента с положительным результатом</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <div id="info"></div>
            <table width="100%" cellspacing="0" cellpadding="4" id="table1${name}" border="1">
                <tr>
                    <th align="center" width="150">Код услуги</th>
                    <th align="center" width="400">Название</th>
                    <th align="center" width="300">Короткое название</th>
                    <th align="center" width="120">Дата</th>
                </tr>
            </table>
            <table width="100%" cellspacing="10" cellpadding="10" id="table2${name}">
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var theIs${name}CloseDocumentDialogInitialized = false ;
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog')) ;

    function show${name}CloseDocument(id) {
        var table2${name} = document.getElementById('table2${name}');
        table2${name}.innerHTML="<tr><td></td></tr><tr><td align=\"center\"><input type=\"button\" value=\'Закрыть\' id=\"${name}Cancel\" onclick=\'javascript:the${name}CloseDocumentDialog.hide()\'/></td></tr><tr><td></td></tr>";
        HospitalMedCaseService.getPatientFIOStat(id, {
                callback: function(res) {
                    jQuery("#info").text(res)
                }
            }
        );
        HospitalMedCaseService.showMBioResResList(id, {
                callback: function(res) {
                    if (res!=null) {
                        var array = JSON.parse(res);
                        var table = document.getElementById('table1${name}');
                        table.innerHTML="<tr>\n" +
                            "<th align=\"center\" width=\"100\">Код услуги</th>\n" +
                            "<th align=\"center\" width=\"300\">Название</th>\n" +
                            "<th align=\"center\" width=\"300\">Короткое название</th>\n" +
                            "<th align=\"center\" width=\"100\">Дата</th>\n" +
                            "</tr>";
                        for (var i = 0; i < array.length; i++) {
                            var obj = array[i];
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');var td2 = document.createElement('td');
                            var td3 = document.createElement('td');var td4 = document.createElement('td');
                            td1.innerHTML = obj.name1;td2.innerHTML = obj.name3;td3.innerHTML = obj.shname;td4.innerHTML = obj.dt;
                            td1.setAttribute('align','center');td2.setAttribute('align','center');
                            td3.setAttribute('align','center');td4.setAttribute('align','center');
                            tr.appendChild(td1);tr.appendChild(td2);tr.appendChild(td3);tr.appendChild(td4);
                            table.appendChild(tr);
                        }
                        the${name}CloseDocumentDialog.show() ;
                    }
                    else alert("Микробиологические исследования пациента с положительным результатом не найдены!");
                }
            }
        );
    }
</script>