<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #Document {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>
<script type="text/javascript" src="./dwr/interface/PregnancyService.js">/**/</script>
<div id='${name}DocumentDialog' class='dialog'>
    <h2>Скрининги новорождённых на выявление критического врождённого порока сердца</h2>
    <div class='rootPane'>
        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4" id="table${name}" border="1">
                <tr>
                    <th align="center" width="400">Этап</th>
                </tr>
            </table>
            <div><input type="button" align="center" value='Закрыть' id="${name}Cancel"
                        onclick='javascript:the${name}DocumentDialog.hide()'/></div>
        </form>
    </div>
</div>

<script type="text/javascript">
    var theIs${name}DocumentDialogInitialized = false;
    var the${name}DocumentDialog = new msh.widget.Dialog($('${name}DocumentDialog'));

    function show${name}(aSloId) {
        var table = document.getElementById('table${name}');
        table.innerHTML = "<tr><th align=\"center\" width=\"400\">Этап</th></tr>";
        PregnancyService.getAllCardiacScreenings(aSloId, {
                callback: function (res) {
                    if (res != null && res != '[]') {
                        var aResult = JSON.parse(res);
                        for (var i = 0; i < aResult.length; i++) {
                            var tr = document.createElement('tr');
                            var td = document.createElement('td');
                            td.innerHTML = aResult[i].type + ' этап ' + aResult[i].date;
                            td.id = JSON.stringify(aResult[i]);
                            td.onclick = function () {
                                var obj = JSON.parse(this.id);
                                var type = obj.type;
                                var id = obj.id;
                                var way = (type == 'I') ? 'First' : 'Second';
                                window.open('entityParentView-stac_screeningCardiac' + way + '.do?id=' + id);
                            };
                            td.setAttribute("align", "center");
                            tr.appendChild(td);
                            table.appendChild(tr);
                        }
                        the${name}DocumentDialog.show();
                    } else {
                        showToastMessage('Ни одного скрининга не найдено.', null, true);
                        the${name}DocumentDialog.hide();
                    }
                }
            }
        );
    }
</script>