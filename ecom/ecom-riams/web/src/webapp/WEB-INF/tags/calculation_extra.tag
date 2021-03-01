<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<msh:ifInRole roles="${roles}">


    <style type="text/css">
        #${name}NewCalculation {
            visibility: hidden;
            display: none;
            position: absolute;
        }
    </style>
    <div id='${name}NewCalculationDialog' class='dialog'>
        <h2 id="${name}title"></h2>

        <div class="formula"></div>
        <msh:panel>
            <div id="${name}div">
                <b><label id="risk${name}">Риск: </label></b><br>
                <div width="100%" id="${name}Table">
                    <label>Назначения</label><br>
                </div>
                <br><label>Другое:</label><br>
                <textarea rows="8" cols="55" class="area" required id="${name}text"></textarea><br>
                <div width="100%" id="${name}Table2">
                    <label>Противопоказания</label><br>
                </div>
                <br><label>Другое:</label><br>
                <textarea rows="8" cols="55" class="area" required id="${name}text2"></textarea><br>
                <tr>
                    <td>
                        <input class="cancel" id="cancel" value="Отмена" onclick="cancel${name}NewCalculation()"
                               type="button">
                    </td>
                    <td>
                        <input value="Далее" onclick="save${name}NewCalculation();" type="button">
                    </td>
                </tr>
            </div>
        </msh:panel>
    </div>


    <script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
    <script type="text/javascript">
        var theIs${name}NewCalculationDialogInitialized = false;
        var the${name}NewCalculationDialog = new msh.widget.Dialog($('${name}NewCalculationDialog'));
        var departmentId${name} = 0;
        var calcId${name} = 0;
        var formString${name} = '';
        var riskId${name} = '';
        var prop${name} = '';
        var resFormCalculation${name} = '';

        // инициализация диалогового окна
        function init${name}NewCalculationDialog() {
            theIs${name}NewCalculationDialogInitialized = true;
        }

        // Показать
        function show${name}NewCalculation(voc, depid, name, calcId, riskId, title, formString, prop, risk, res) {
            if (!theIs${name}NewCalculationDialogInitialized) {
                init${name}NewCalculationDialog();
            }
            departmentId${name} = depid;
            calcId${name} = calcId;
            riskId${name} = riskId;
            formString${name} = formString;
            prop${name} = prop;
            resFormCalculation${name} = res;
            document.getElementById('${name}title').innerHTML = title;
            document.getElementById('risk${name}').innerHTML = risk;
            loadPresc(voc, depid, name, calcId, riskId);
            the${name}NewCalculationDialog.show();
            document.getElementById("cancel").style.display = "";
        }

        //Загрузка назначений
        function loadPresc(voc, depid, name, calcId, riskId) {
            var n = 2, m = 2;
            var mas = new Array(n);
            for (var i = 0; i < n; i++) mas[i] = new Array(m);
            mas[0][0] = 'calculator_id';
            mas[0][1] = calcId;
            mas[1][0] = 'calcrisk_id';
            mas[1][1] = riskId;
            document.getElementById('${name}Table').innerHTML = "";
            CalculateService.getInJson(voc, name, mas, {
                callback: function (aResult) {
                    var vocRes = JSON.parse(aResult);
                    for (var ind1 = 0; ind1 < vocRes.length; ind1++) {
                        var vocVal = vocRes[ind1];
                        document.getElementById('${name}Table').innerHTML += "<label><input type=\"checkbox\" name='${name}" + vocVal.prescvalue + "' id=" + vocVal.id + ">" + vocVal.prescvalue + '<\label>';
                        document.getElementById('${name}Table').innerHTML += "<br>";
                        if ((ind1 + 1) % 3 == 0)
                            document.getElementById('${name}Table').innerHTML += "<br>";
                    }
                    loadContras('contracalc', depid, 'contravalue', calcId);
                }
            });
        }

        //Загрузка противопоказаний
        function loadContras(voc, depid, name, calcId) {
            var n = 1, m = 1;
            var mas = new Array(n);
            for (var i = 0; i < n; i++) mas[i] = new Array(m);
            mas[0][0] = 'calculator_id';
            mas[0][1] = calcId;
            document.getElementById('${name}Table2').innerHTML = "";
            CalculateService.getInJson(voc, name, mas, {
                callback: function (aResult) {
                    var vocRes = JSON.parse(aResult);
                    for (var ind1 = 0; ind1 < vocRes.length; ind1++) {
                        var vocVal = vocRes[ind1];
                        document.getElementById('${name}Table2').innerHTML += "<label><input type=\"checkbox\"  name='${name}" + vocVal.contravalue + "' id=" + vocVal.id + ">" + vocVal.contravalue + '<\label>';
                        document.getElementById('${name}Table2').innerHTML += "<br>";
                    }
                }
            });
        }

        // Отмена
        function cancel${name}NewCalculation() {
            the${name}NewCalculationDialog.hide();
        }

        // Сохранение данных
        function save${name}NewCalculation() {
            var inputs = document.getElementById('${name}Table').childNodes;
            var count1 = 0, count2 = 0;
            for (var i = 0; i < inputs.length; i++) {
                if (inputs[i].childNodes.length > 0 && inputs[i].childNodes[0].type == 'checkbox' && inputs[i].childNodes[0].checked == true) {
                    formString${name} += inputs[i].childNodes[0].name.replace('${name}', '') + "\n";
                    count1++;
                }
            }
            if (document.getElementById('${name}text').value != '') formString${name} += document.getElementById('${name}text').value + "\n";
            if (count1 == 0 && document.getElementById('${name}text').value == '') formString${name} += "-\n";

            formString${name} += "Противопоказания:\n";
            inputs = document.getElementById('${name}Table2').childNodes;
            for (var i = 0; i < inputs.length; i++) {
                if (inputs[i].childNodes.length > 0 && inputs[i].childNodes[0].type == 'checkbox' && inputs[i].childNodes[0].checked == true) {
                    formString${name} += inputs[i].childNodes[0].name.replace('${name}', '') + "\n";
                    count2++;
                }
            }
            if (document.getElementById('${name}text2').value != '') formString${name} += document.getElementById('${name}text2').value + "\n";
            if (count2 == 0 && document.getElementById('${name}text2').value == '') formString${name} += "-\n";

            var record = window.parent.document.getElementById(prop${name});

            for (var i = 0; i < 100; i++)
                if (window.parent.document.getElementById('fadeEffect') != null) window.parent.document.getElementById('fadeEffect').hide();
            if (record != null) {
                record.value += formString${name};
                showToastMessage("Добавлено к дневнику!", null, true);
            } else
                CalculateService.SetCalculateResultCreate(departmentId${name},
                    resFormCalculation${name}, calcId${name}, formString${name}, {
                        callback: function () {
                            showToastMessage("Вычисление успешно создано!", null, true);
                            location.href = "entityParentView-stac_slo.do?id=" + departmentId${name};
                        }
                    });
            the${name}NewCalculationDialog.hide();
        }
    </script>
</msh:ifInRole>
