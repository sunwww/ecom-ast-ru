<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<%@ attribute name="title" required="true" description="Заголовок" %>

<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="field" required="true" description="Название поля" %>

<msh:ifInRole roles="${roles}">


    <style type="text/css">
        #${name}NewCalculation {
            visibility: hidden;
            display: none;
            position: absolute;
        }
    </style>
    <div id='${name}NewCalculationDialog' class='dialog'>
        <h2>Расчет скорости клубочковой фильтрации</h2>

        <div class="formula"></div>
        <msh:panel>
            <tr>
                <td colspan="1" class="label"><label>Пол:</label></td>
                <td colspan="2">
                    <label><input disabled name="sex" id="sex2" value="2" type="radio">муж </label>
                    <label><input disabled name="sex" id="sex1" value="1" type="radio">жен</label>
                </td>
            </tr>

            <tr>
                <td colspan="1" class="label"><label>Возраст:</label></td>
                <td colspan="3"><input disabled id="age" size="10" type="text"></td>
            </tr>

            <tr>
                <td colspan="1" class="label"><label>Креатинин:</label></td>
                <td colspan="3"><input id="createnin" size="10" type="text"></td>
            </tr>

            <tr>
                <td colspan="1" class="label"><label>Масса тела(кг):</label></td>
                <td colspan="3"><input id="mass" size="10" type="text"></td>
            </tr>

            <msh:row>
                <msh:textField viewOnlyField="true" property="result" label="Результат" horizontalFill="true"
                               fieldColSpan="3"/>
            </msh:row>
        </msh:panel>
        <tr>
            <td>
                <input class="cancel" id="cancel" value="Отмена" onclick="cancel${name}NewCalculation()" type="button">
            </td>
            <td>
                <input value="Рассчитать" onclick="calculate${name}();" type="button">
            </td>
            <td>
                <input value="Рассчитать и добавить к дневнику" onclick="save${name}NewCalculation();" type="button">
            </td>
        </tr>
    </div>


    <script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
    <script type="text/javascript">
        var theIs${name}NewCalculationDialogInitialized = false;
        var the${name}NewCalculationDialog = new msh.widget.Dialog($('${name}NewCalculationDialog'));
        var departmentId${name} = 0;
        var calcId${name} = 0;

        // инициализация диалогового окна
        function init${name}NewCalculationDialog() {
            theIs${name}NewCalculationDialogInitialized = true;
        }

        // Показать
        function show${name}NewCalculation(id, calcId, create) {
            if (!theIs${name}NewCalculationDialogInitialized) {
                init${name}NewCalculationDialog();
            }
            the${name}NewCalculationDialog.show();
            departmentId${name} = id;
            calcId${name} = calcId;
            $("age").focus();
            getAge${name}(id);
            getGender${name}(id);
            document.getElementById("cancel").style.display = "";

        }

        // Отмена
        function cancel${name}NewCalculation() {
            the${name}NewCalculationDialog.hide();

        }

        var Gender;
        var Age;
        var Createnin = document.querySelector('#createnin');
        var Mass = document.querySelector('#mass');
        var formula = document.querySelector('.formula');

        // Сохранение данных
        function save${name}NewCalculation() {
            calculate${name}();
            var result = document.querySelector('#resultReadOnly');
            if (result.value != '') {
                var prop;
                if ("${property}" == "") prop = "record";

                var record = window.parent.document.getElementById(prop);

                var temp = $('resultReadOnly').value;
                var res = "СКФ:";
                var str = "Требуется коррекция дозы лекарственных средств, которые активно выводятся с мочой. Лечащий врач может принять решение о направлении пациента на консультацию к врачу-клиническому фармакологу";
                if (temp > 89) {
                    res += temp + ' - стадия 1, нормальная СКФ';
                }
                if (temp >= 60 && temp <= 89) {
                    res += temp + ' - стадия 2, признаки нефропатии, легкое снижение СКФ';
                }
                if (temp >= 45 && temp <= 59) {
                    res += temp + ' - стадия 3А, умеренное снижение СКФ';
                }
                if (temp >= 30 && temp <= 44) {
                    res += temp + ' - стадия 3Б, выраженное снижение СКФ';
                    alert(str);
                }
                if (temp >= 15 && temp <= 29) {
                    res += temp + ' - стадия 4, тяжелое снижение СКФ';
                    alert(str);
                }
                if (temp < 15) {
                    res += temp + ' - стадия 5, терминальная хроническая почечная недостаточность';
                    alert(str);
                }
                if (record != null) {
                    record.value += res + "\n";
                    showToastMessage("Добавлено к дневнику!", null, true);
                } else
                    CalculateService.SetCalculateResultCreate(departmentId${name},
                        res, calcId${name}, '', {
                            callback: function () {
                                showToastMessage("Вычисление успешно создано!", null, true);
                            }
                        });
                for (var i = 0; i < 100; i++)
                    if (window.parent.document.getElementById('allCalc') != null) window.parent.document.getElementById('fadeEffect').hide();
                the${name}NewCalculationDialog.hide();
            }
        }

        //вычисение результата
        function calculate${name}() {
            if (Createnin.value == "" || Mass.value == "") {
                formula.innerHTML = '<font color="red"><b>Заполните все поля!</b></font>';
            } else {
                var result = document.querySelector('#resultReadOnly');
                result.className = "horizontalFill";
                if (Gender == "2") {
                    var t = ((1.23 * (140 - Age) * Mass.value) / Createnin.value);
                    result.value = t.toFixed(2);
                    formula.innerHTML = '<b>Формула Кокрофта-Голта для мужчин</b>';
                }

                if (Gender == "1") {
                    var t = ((1.04 * (140 - Age) * Mass.value) / Createnin.value);
                    result.value = t.toFixed(2);
                    formula.innerHTML = '<b>Формула Кокрофта-Голта для мужчин</b>';
                }
                result.className = "viewOnly horizontalFill";
            }
        }

        //Получить пол
        function getGender${name}(medcaseId) {

            CalculateService.getGender(medcaseId, {
                callback: function (aResult) {

                    var radio1 = document.querySelector('#sex1');
                    var radio2 = document.querySelector('#sex2');

                    if (aResult == 2) {
                        radio2.checked = true;
                        Gender = aResult;
                    }

                    if (aResult == 1) {
                        radio1.checked = true;
                        Gender = aResult;
                    }
                }
            });
        }

        //Получить возраст полных лет
        function getAge${name}(medcaseId) {
            CalculateService.getAge(medcaseId, {
                callback: function (aResult) {
                    var age = document.querySelector('#age');
                    age.value = aResult;
                    Age = aResult;
                }
            });
        }


    </script>
</msh:ifInRole>
