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
        <h2>Шкала NIHSS</h2>

        <div id="formula${name}" class="formula${name}"></div>
        <h3>Уровень сознания</h3>
        <select id="ursozn${name}">
            <option>Не изменено</option>
            <option>Оглушение</option>
            <option>Сопор</option>
            <option>Кома</option>
        </select>
        <br>
        <h3>Ответы на вопросы</h3>
        <select id="otv${name}">
            <option>Правильно отвечает на два вопроса</option>
            <option>Правильно отвечает на один вопрос</option>
            <option>Не отвечает</option>
        </select>
        <br>
        <h3>Реакция на команды</h3>
        <select id="reakcoms${name}">
            <option>Правильно выполняет 2 команды</option>
            <option>Правильно выполняет 1 команду</option>
            <option>Не выполняет ни одной команды</option>
        </select>
        <br>
        <h3>Парез взгляда</h3>
        <select id="parez${name}">
            <option>Взгляд нормальный</option>
            <option>Частичный парез взгляда</option>
            <option>Полный парез взгляда</option>
        </select>
        <br>
        <h3>Поля зрения</h3>
        <select id="polzr${name}">
            <option>Сохранены</option>
            <option>Частичная гемианопсия</option>
            <option>Полная гемианопсия</option>
            <option>Билатеральная гемианопсия</option>
        </select>
        <br>
        <h3>Парез мимической мускулатуры</h3>
        <select id="pzrezmimich${name}">
            <option>Отсутствует</option>
            <option>Легкий</option>
            <option>Частичный</option>
            <option>Полный</option>
        </select>
        <br>
        <h3>Двигательные функции <i>верхней левой</i> конечности</h3>
        <select id="leftup${name}">
            <option>Пареза нет</option>
            <option>Опускается медленно, за 5 секунд</option>
            <option>Быстро падает, менее чем за 5 сек</option>
            <option>Не может преодолеть силу притяжения</option>
            <option>Движений в руке нет</option>
        </select>
        <br>
        <h3>Двигательные функции <i>верхней правой</i> конечности</h3>
        <select id="rightup${name}">
            <option>Пареза нет</option>
            <option>Опускается медленно, за 5 секунд</option>
            <option>Быстро падает, менее чем за 5 сек</option>
            <option>Не может преодолеть силу притяжения</option>
            <option>Движений в руке нет</option>
        </select>
        <br>
        <h3>Двигательные функции <i>нижней</i> конечности</h3>
        <select id="down${name}">
            <option>Пареза нет</option>
            <option>Опускается медленно, за 5 секунд</option>
            <option>Быстро падает, менее чем за 5 сек</option>
            <option>Не может преодолеть силу притяжения</option>
            <option>Движений в ноге нет</option>
        </select>
        <br>
        <h3>Чувствительность</h3>
        <select id="sense${name}">
            <option>Не нарушена</option>
            <option>Гипестезия</option>
            <option>Анестезия</option>
        </select>
        <br>
        <h3>Атаксия</h3>
        <select id="at${name}">
            <option>Нет</option>
            <option>В руке или ноге</option>
            <option>В руке и ноге</option>
        </select>
        <br>
        <h3>Речь</h3>
        <select id="speech${name}">
            <option>Нормальная</option>
            <option>Легкая афазия</option>
            <option>Выраженная афазия</option>
            <option>Тотальная афазия</option>
        </select>
        <br>
        <h3>Дизартрия</h3>
        <select id="dizart${name}">
            <option>Нет</option>
            <option>Умеренная</option>
            <option>Выраженная</option>
        </select>
        <br>
        <h3>Невнимательность</h3>
        <select id="nevnim${name}">
            <option>Нет</option>
            <option>Легкая степень</option>
            <option>Тяжелая степень</option>
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
            document.getElementById("cancel").style.display = "";
        }

        // Отмена
        function cancel${name}NewCalculation() {
            the${name}NewCalculationDialog.hide();
        }

        // Сохранение данных
        function save${name}NewCalculation() {
            calculate${name}();
            var formString = formToString${name}();
            var res = document.getElementById('formula${name}').innerHTML;

            var resStr = 'Шкала инсульта NIHSS. Результат: ';
            resStr += res + ' - ';
            if (res == 0 || res < 3) resStr += 'состояние удовлетворительное';
            if (res >= 3 && res <= 8) resStr += 'неврологические нарушения легкой степени';
            if (res >= 9 && res <= 12) resStr += 'неврологические нарушения средней степени';
            if (res >= 13 && res <= 15) resStr += 'тяжелые неврологические нарушения';
            if (res >= 16 && res <= 34) resStr += 'неврологические нарушения крайней степени тяжести';
            if (res > 34) resStr += 'кома';
            if (res < 10) resStr += '. Прогнозируется благоприятный исход';
            if (res > 20) resStr += '. Прогнозируется неблагоприятный исход';
            resStr += '\n';

            var prop;
            if ("${property}" == "") prop = "record";

            var record = window.parent.document.getElementById(prop);
            if (record != null) {
                record.value += resStr;
                showToastMessage("Добавлено к дневнику!", null, true)
            } else CalculateService.SetCalculateResultCreate(departmentId${name},
                resStr, calcId${name}, formString, {
                    callback: function () {
                        showToastMessage("Вычисление успешно создано!", null, true);
                    }
                });
            for (var i = 0; i < 100; i++)
                if (window.parent.document.getElementById('fadeEffect') != null) window.parent.document.getElementById('fadeEffect').hide();

            the${name}NewCalculationDialog.hide();
        }

        //вычисение результата
        function calculate${name}() {
            var res = 0;
            res += document.getElementById('ursozn${name}').selectedIndex;
            res += document.getElementById('otv${name}').selectedIndex;
            res += document.getElementById('reakcoms${name}').selectedIndex;
            res += document.getElementById('parez${name}').selectedIndex;
            res += document.getElementById('polzr${name}').selectedIndex;
            res += document.getElementById('pzrezmimich${name}').selectedIndex;
            res += document.getElementById('leftup${name}').selectedIndex;
            res += document.getElementById('rightup${name}').selectedIndex;
            res += document.getElementById('down${name}').selectedIndex;
            res += document.getElementById('sense${name}').selectedIndex;
            res += document.getElementById('at${name}').selectedIndex;
            res += document.getElementById('speech${name}').selectedIndex;
            res += document.getElementById('dizart${name}').selectedIndex;
            res += document.getElementById('nevnim${name}').selectedIndex;
            document.getElementById('formula${name}').innerHTML = res;
        }

        //Форма в строку
        function formToString${name}() {
            var formToStirng = 'Параметр\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tБаллы\n';
            formToStirng += 'Уровень сознания:\t' + selectToString('ursozn${name}');
            formToStirng += 'Ответы на вопросы:\t' + selectToString('otv${name}');
            formToStirng += 'Реакция на команды:\t' + selectToString('reakcoms${name}');
            formToStirng += 'Парез взгляда:\t' + selectToString('parez${name}');
            formToStirng += 'Поля зрения:\t' + selectToString('polzr${name}');
            formToStirng += 'Парез мимической мускулатуры:\t' + selectToString('pzrezmimich${name}');
            formToStirng += 'Двигательные функции левой верхней конечности:\t' + selectToString('leftup${name}');
            formToStirng += 'Двигательные функции правой верхней конечности:\t' + selectToString('rightup${name}');
            formToStirng += 'Двигательные функции нижней конечности:\t' + selectToString('down${name}');
            formToStirng += 'Чувствительность:\t' + selectToString('sense${name}');
            formToStirng += 'Атаксия:\t' + selectToString('at${name}');
            formToStirng += 'Речь:\t' + selectToString('speech${name}');
            formToStirng += 'Дизартрия:\t' + selectToString('dizart${name}');
            formToStirng += 'Невнимательность:\t' + selectToString('nevnim${name}');
            return formToStirng;
        }

        //Список в строку
        function selectToString(name) {
            return document.getElementById(name).options[document.getElementById(name).selectedIndex].innerHTML
                + '\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t' + (document.getElementById(name).selectedIndex) + '\n';
        }
    </script>
</msh:ifInRole>
