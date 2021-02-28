<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<%@ attribute name="title" required="true" description="Заголовок" %>

<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="field" required="true" description="Название поля" %>

<msh:ifInRole roles="${roles}">
    <div id='${name}NewCalculationDialog' class='dialog'>
        <h2>Шкала GRACE</h2>
        <div class="formula" id="formula${name}"></div>
        <msh:panel>
            <tr>
                <td colspan="1" class="label"><label>Возраст:</label></td>
                <td colspan="3"><input disabled id="age${name}" size="10" type="text"></td>
            </tr>

            <tr>
                <td colspan="1" class="label"><label>ЧСС, уд/мин:</label></td>
                <td colspan="3"><input id="chss${name}" size="10" type="text"></td>
            </tr>

            <tr>
                <td colspan="1" class="label"><label>Систолическое АД, мм. рт. ст.:</label></td>
                <td colspan="3"><input id="ad${name}" size="10" type="text"></td>
            </tr>
            <tr>
                <td colspan="1" class="label"><label>Креатинин:</label></td>
                <td colspan="3"><input id="createnin${name}" size="10" type="text"></td>
            </tr>
            <tr>
                <td colspan="1" class="label"><label>Остановка сердца (на момент поступления):</label></td>
                <td colspan="3"><input id="heartstop${name}" type="checkbox"></td>
            </tr>
            <tr>
                <td colspan="1" class="label"><label>Отклонение сегмента ST:</label></td>
                <td colspan="3"><input id="st${name}" type="checkbox"></td>
            </tr>
            <tr>
                <td colspan="1" class="label"><label>Высокий уровень сердечных ферментов:</label></td>
                <td colspan="3"><input id="ferm${name}" type="checkbox"></td>
            </tr>
        </msh:panel>
        <select id="vocHeartFailureClassByKillip">
            <option>Отсутствие признаков застойной сердечной недостаточности (Класс I)</option>
            <option>Наличие хрипов в легких и/или повышенного давления в югулярных венах (Класс II)</option>
            <option>Наличие отека легких (Класс III)</option>
            <option>Наличие кардиогенного шока (Класс IV)</option>
        </select>
        <br>
        <tr>
            <td>
                <input class="cancel" id="cancel" value="Отмена" onclick="cancel${name}NewCalculation()" type="button">
            </td>
            <td>
                <input value="Рассчитать" onclick="calculate${name}();" type="button">
            </td>
            <td>
                <input value="Сохранить" onclick="save${name}NewCalculation();" type="button">
            </td>
        </tr>
    </div>
    <style type="text/css">
        #${name}NewCalculation {
            visibility: hidden;
            display: none;
            position: absolute;
        }
    </style>
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
            $("age${name}").focus();
            getAge${name}(id);
            departmentId${name} = id;
            calcId${name} = calcId;
            document.getElementById("cancel").style.display = "";

        }

        // Отмена
        function cancel${name}NewCalculation() {
            the${name}NewCalculationDialog.hide();

        }

        var Age${name};
        var Createnin${name} = document.querySelector('#createnin${name}');
        var chss${name} = document.querySelector('#chss${name}');
        var ad${name} = document.querySelector('#ad${name}');
        var heartstop${name} = document.querySelector('#heartstop${name}');
        var st${name} = document.querySelector('#st${name}');
        var ferm${name} = document.querySelector('#ferm${name}');
        var formula${name} = document.querySelector('#formula${name}');

        // Сохранение данных
        function save${name}NewCalculation() {
            var formString = calculate${name}();
            if (formula${name}.innerHTML != '') {
                var prop;
                if ("${property}" == "") prop = "record";

                var record = window.parent.document.getElementById(prop);

                var temp = formula${name}.innerHTML;
                var res = '';
                res += "Шкала GRACE: " + temp + ".\n";
                res += "Острый коронарный синдром с подъемом сегмента ST:\n";
                if (temp < 126) res += '- риск внутригоспитальной  летальности: низкий\t< 2%';
                if (temp < 100) res += '\n-в течение 6 месяцев: низкий\t< 4.5%';
                if (temp >= 126 && temp <= 154) res += '- риск внутригоспитальной  летальности: cредний\t< 2 - 5%';
                if (temp >= 100 && temp <= 127) res += '\n-в течение 6 месяцев: cредний\t< 4.5 - 11%';
                if (temp > 154) res += '- риск внутригоспитальной  летальности: высокий\t> 5%';
                if (temp > 127) res += '\n-в течение 6 месяцев: высокий\t> 11%';

                res += "\nОстрый коронарный синдром без подъема ST:\n";
                if (temp < 109) res += '- риск внутригоспитальной  летальности: низкий\t< 1%';
                if (temp < 89) res += '\n-в течение 6 месяцев: низкий\t< 3%';
                if (temp >= 109 && temp <= 140) res += '- риск внутригоспитальной  летальности: cредний\t< 1 - 3%';
                if (temp >= 89 && temp <= 118) res += '\n-в течение 6 месяцев: cредний\t< 3 - 8%';
                if (temp > 140) res += temp + '- риск внутригоспитальной  летальности: высокий\t> 3%';
                if (temp > 118) res += '\n-в течение 6 месяцев: высокий\t> 8%';

                res += "\n";
                for (var i = 0; i < 100; i++)
                    if (window.parent.document.getElementById('fadeEffect') != null) window.parent.document.getElementById('fadeEffect').hide();
                if (record != null) {
                    record.value += res;
                    showToastMessage("Добавлено к дневнику!", null, true);
                } else CalculateService.SetCalculateResultCreate(departmentId${name},
                    res, calcId${name}, formString, {
                        callback: function () {
                            showToastMessage("Вычисление успешно создано!", null, true);
                        }
                    });
                the${name}NewCalculationDialog.hide();
            }
        }

        //вычисение результата
        function calculate${name}() {
            var formToStirng = '';
            if (Createnin${name}.value == "" || chss${name}.value == "" || ad${name}.value == "" || heartstop${name}.value == "" || st${name}.value == "" ||
                ferm${name}.value == "" || document.getElementById('vocHeartFailureClassByKillip').selectedIndex == -1
                || isNaN(Createnin${name}.value) || isNaN(chss${name}.value == "") || isNaN(ad${name}.value == "") || isNaN(heartstop${name}.value == "")
                || isNaN(st${name}.value == "") || isNaN(ferm${name}.value == "")) {
                formula${name}.innerHTML = '<font color="red"><b>Правильно заполните все поля !</b></font>';
            } else {
                var t = 0, tmp = 0;
                formToStirng = 'Параметр\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tБаллы\n';
                //Подсчёт общего кол-ва баллов
                if (age${name}.value >= 30 && age${name}.value <= 39) t += 8;
                if (age${name}.value >= 40 && age${name}.value <= 49) t += 25;
                if (age${name}.value >= 50 && age${name}.value <= 59) t += 41;
                if (age${name}.value >= 60 && age${name}.value <= 69) t += 58;
                if (age${name}.value >= 70 && age${name}.value <= 79) t += 75;
                if (age${name}.value >= 80 && age${name}.value <= 89) t += 91;
                if (age${name}.value >= 90) t += 100;
                formToStirng += 'Возраст:';
                formToStirng += age${name}.value;
                formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                formToStirng += "\n";
                tmp = t;

                if (chss${name}.value >= 50 && chss${name}.value <= 69) t += 3;
                if (chss${name}.value >= 70 && chss${name}.value <= 89) t += 9;
                if (chss${name}.value >= 90 && chss${name}.value <= 109) t += 15;
                if (chss${name}.value >= 110 && chss${name}.value <= 149) t += 24;
                if (chss${name}.value >= 150 && chss${name}.value <= 199) t += 38;
                if (chss${name}.value >= 200) t += 46;
                formToStirng += 'ЧСС, уд/мин:';
                formToStirng += chss${name}.value;
                formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                formToStirng += "\n";
                tmp = t;

                if (ad${name}.value < 80) t += 58;
                if (ad${name}.value >= 80 && ad${name}.value <= 99) t += 53;
                if (ad${name}.value >= 100 && ad${name}.value <= 119) t += 43;
                if (ad${name}.value >= 120 && ad${name}.value <= 139) t += 34;
                if (ad${name}.value >= 140 && ad${name}.value <= 159) t += 24;
                if (ad${name}.value >= 160 && ad${name}.value <= 199) t += 10;
                formToStirng += 'Систолическое АД, мм. рт. ст.:';
                formToStirng += ad${name}.value;
                formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                formToStirng += "\n";
                tmp = t;

                if (Createnin${name}.value < 35.36) t += 1;
                if (Createnin${name}.value >= 35.36 && Createnin${name}.value <= 70.71) t += 4;
                if (Createnin${name}.value >= 70.72 && Createnin${name}.value <= 106.07) t += 7;
                if (Createnin${name}.value >= 106.08 && Createnin${name}.value <= 141.43) t += 10;
                if (Createnin${name}.value >= 141.43 && Createnin${name}.value <= 176.7) t += 13;
                if (Createnin${name}.value >= 176.8 && Createnin${name}.value <= 353) t += 21;
                if (Createnin${name}.value >= 354) t += 28;
                formToStirng += 'Креатинин:';
                formToStirng += Createnin${name}.value;
                formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                formToStirng += "\n";
                tmp = t;

                if (document.getElementById('vocHeartFailureClassByKillip').selectedIndex == 1) t += 20;
                if (document.getElementById('vocHeartFailureClassByKillip').selectedIndex == 2) t += 39;
                if (document.getElementById('vocHeartFailureClassByKillip').selectedIndex == 3) t += 59;
                if (document.getElementById('vocHeartFailureClassByKillip').selectedIndex != -1) {
                    formToStirng +=
                        document.getElementById('vocHeartFailureClassByKillip').options[document.getElementById('vocHeartFailureClassByKillip').selectedIndex].innerHTML;
                    formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                    formToStirng += "\n";
                    tmp = t;
                }

                if (heartstop${name}.checked) t += 39;
                formToStirng += 'Остановка сердца (на момент поступления) - ';
                formToStirng += (heartstop${name}.checked) ? 'Да' : 'Нет';
                formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                formToStirng += "\n";
                tmp = t;
                if (st${name}.checked) t += 28;
                formToStirng += 'Отклонение сегмента ST - ';
                formToStirng += (st${name}.checked) ? 'Да' : 'Нет';
                formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                formToStirng += "\n";
                tmp = t;
                if (ferm${name}.checked) t += 14;
                formToStirng += 'Высокий уровень сердечных ферментов - ';
                formToStirng += (ferm${name}.checked) ? 'Да' : 'Нет';
                formToStirng += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(" + (t - tmp) + ")";
                formToStirng += "\n";
                tmp = t;

                formula${name}.innerHTML = t.toFixed(2);
            }
            return formToStirng;
        }

        //Получить возраст полных лет
        function getAge${name}(medcaseId) {
            CalculateService.getAge(medcaseId, {
                callback: function (aResult) {
                    var age = document.querySelector('#age${name}');
                    age.value = aResult;
                    Age = aResult;
                }
            });
        }

    </script>
</msh:ifInRole>