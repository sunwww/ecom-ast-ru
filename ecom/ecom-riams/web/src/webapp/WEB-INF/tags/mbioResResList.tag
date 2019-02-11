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
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Список микробиологических исследований пациента с положительным результатом</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <div id="info"></div>
            <table width="100%" cellspacing="0" cellpadding="4" id="table1" border="1">
                <tr>
                    <th align="center" width="150">Код услуги</th>
                    <th align="center" width="400">Название</th>
                    <th align="center" width="300">Короткое название</th>
                    <th align="center" width="120">Дата</th>
                </tr>
            </table>
            <table width="100%" cellspacing="10" cellpadding="10" id="table2">
            </table>
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
        showMBioResResList();
        theTableArrow = null ;
    }
    function showMBioResResList() {
        var table2 = document.getElementById('table2');
        table2.innerHTML="<tr><td></td></tr><tr><td align=\"center\"><input type=\"button\" value=\'Закрыть\' id=\"${name}Cancel\" onclick=\'javascript:cancel${name}CloseDocument()\'/></td></tr><tr><td></td></tr>";
        HospitalMedCaseService.getPatientFIOStat(
            ID, {
                callback: function(res) {
                    if (res!=null) document.getElementById("info").innerText=res;
                }
            }
        );
        HospitalMedCaseService.showMBioResResList(
            ID, {
                callback: function(res) {
                    //alert(res);
                    if (res!="##") {
                        var all = res.split('!') ;
                        var table = document.getElementById('table1');
                        table.innerHTML="<tr>\n" +
                            "<th align=\"center\" width=\"100\">Код услуги</th>\n" +
                            "<th align=\"center\" width=\"300\">Название</th>\n" +
                            "<th align=\"center\" width=\"300\">Короткое название</th>\n" +
                            "<th align=\"center\" width=\"100\">Дата</th>\n" +
                            "</tr>";
                        for (var i=0; i<all.length-1; i++) {
                            var aResult = all[i].split('#');
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');var td2 = document.createElement('td');
                            var td3 = document.createElement('td');var td4 = document.createElement('td');
                            td1.innerHTML = aResult[0];td2.innerHTML = aResult[1];td3.innerHTML = aResult[2];td4.innerHTML = aResult[3];
                            td1.align = "center";td2.align = "center";td3.align = "center";td4.align = "center";
                            tr.appendChild(td1);tr.appendChild(td2);tr.appendChild(td3);tr.appendChild(td4);
                            table.appendChild(tr);
                        }
                        the${name}CloseDisDocumentDialog.show() ;
                    }
                    else alert("Микробиологические исследования пациента с положительным результатом не найдены!");
                }
            }
        );
    }
    // Отмена
    function cancel${name}CloseDocument() {
        the${name}CloseDisDocumentDialog.hide() ;
    }
</script>