<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>

<script type="text/javascript" src="./dwr/interface/PatientService.js">/**/</script>
<div id='${name}Dialog' class='dialog'>
    <h2>Листы наблюдения пациента</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="sheetTable${name}" border="1" >
    </table>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="right"><input type="button"  style="font-weight:bold" value='Закрыть' id="${name}Cancel" onclick='the${name}Dialog.hide() ;'/></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var aPatId;
    var the${name}Dialog = new msh.widget.Dialog($('${name}Dialog')) ;
    // Показать

    function show${name}(id) {
        aPatId=id;
        var table = document.getElementById('sheetTable${name}');
        table.innerHTML=" <tr><th align='center' width='85'>Дата установки</th><th align='center' width='350'>Открыл лист наблюдения</th>" +
            "<th align='center' width='85'>Дата снятия</th><th align='center' width='350'>Закрыл лист наблюдения</th>" +
            "<th align='center' width='35'>Длительность</th><th align='center' width='55'>Исход наблюдения</th></tr><tr>";
        PatientService.selectObservSheetPatient(
            aPatId, {
                callback: function(res) {
                    if (res!=null && res!='[]') {
                        var aResult = JSON.parse(res);
                        for (var i=0; i<aResult.length; i++) {
                            var tr = document.createElement('tr');
                            var td = document.createElement('td');
                            var td2 = document.createElement('td');
                            var td3 = document.createElement('td');
                            var td4 = document.createElement('td');
                            var td5 = document.createElement('td');
                            var td6 = document.createElement('td');
                            td.innerHTML=typeof aResult[i].startDate == 'undefined'? ' - ' : aResult[i].startDate;
                            td2.innerHTML=typeof aResult[i].wpStart == 'undefined'? ' - ' : aResult[i].wpStart;
                            td3.innerHTML=typeof aResult[i].finishDate == 'undefined'? ' - ' : aResult[i].finishDate;
                            td4.innerHTML=typeof aResult[i].wpFin == 'undefined'? ' - ' : aResult[i].wpFin;
                            td5.innerHTML=typeof aResult[i].dlit == 'undefined'? ' - ' : aResult[i].dlit;
                            td6.innerHTML=typeof aResult[i].res == 'undefined'? ' - ' : aResult[i].res;
                            td.setAttribute("align","center");
                            td2.setAttribute("align","center");
                            td3.setAttribute("align","center");
                            td4.setAttribute("align","center");
                            td5.setAttribute("align","center");
                            td6.setAttribute("align","center");
                            tr.appendChild(td);
                            tr.appendChild(td2);
                            tr.appendChild(td3);
                            tr.appendChild(td4);
                            tr.appendChild(td5);
                            tr.appendChild(td6);
                            tr.id=aResult[i].id;
                            tr.onclick=function() {
                                window.location.href='/riams/entityView-edkcObsSheet.do?id='+this.id;
                            };
                            table.appendChild(tr);
                        }
                    }
                    the${name}Dialog.show();
                }
            }
        );
    }
</script>