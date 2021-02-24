<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="sloId" required="true" description="СЛО, по которому искать другие СЛО" %>
<%@ attribute name="func" required="true" description="Функция, которую запустить после выбора СЛО" %>

<div id='${name}CloseDocumentDialog' class='dialog'>
    <table width="100%" cellspacing="10" cellpadding="10" id="table${name}">
    </table>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td></td>
            </tr>
            <tr>
                <td align="right"><input type="button" style="font-weight:bold" id="${name}" value='Отменить'
                                         id="${name}Cancel" onclick='the${name}CloseDocumentDialog.hide()'/></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog'));

    // Показать
    function show${name}() {
        theTableArrow = null;
        var table = document.getElementById('table${name}');
        table.innerHTML = "";
        PregnancyService.getAllSLOFromSLSBySLO(${sloId}, {
            callback: function (res) {
                if (res != null && res != '[]') {
                    var aResult = JSON.parse(res);
                    for (var i = 0; i < aResult.length; i++) {
                        var tr = document.createElement('tr');
                        var td = document.createElement('td');
                        td.innerHTML = "<input type='button' style='width:100%;font-size:30px; margin:5px' value='" + aResult[i].name +
                            "' id='" + aResult[i].id + "' onclick=\" ${func}(this.id); the${name}CloseDocumentDialog.hide() ;\"/>";
                        td.setAttribute("align", "center");
                        tr.appendChild(td);
                        table.appendChild(tr);
                    }
                    the${name}CloseDocumentDialog.show();
                } else {
                    showToastMessage('СЛО не найдено', null, true);
                    the${name}CloseDocumentDialog.hide();
                }
            }
        });
    }
</script>