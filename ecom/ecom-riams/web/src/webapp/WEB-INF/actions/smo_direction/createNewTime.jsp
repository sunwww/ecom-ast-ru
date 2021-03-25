<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Patient">Создание нового времени по специалисту</msh:title>
    </tiles:put>

    <tiles:put name="style" type="string">
        <style type="text/css">
            #schedule {
                margin-top: 10px;
            }

            #head-cont {
                display: inline-block;
                font-size: 18px;
                margin-right: 15px;
                margin-bottom: 4px;
                cursor: default;
                font-family: 'Roboto', sans-serif;
                color: #444;
                background: #b8b9ba;
                border-radius: 3px;
            }

            #alink {
                font-size: 25px;
                font-family: 'Roboto', sans-serif;
                color: #aa2836;
                text-decoration: none;
                padding: 5px 10px;
            }

            #table {
                -moz-user-select: none;
                text-align: center;
                font-family: 'Roboto', sans-serif;
                color: #444;
                font-size: 16px;
                background: #FFF;
                border-collapse: separate;
                display: inline-table;
                min-width: 890px !important;
                max-width: 1024px !important;
            }

            .r0 {
                background: #a6ffea;
            }

            .r3 {
                color: black;
                background: #ff9de0
            }

            .r2 {
                color: black;
                background: #ffcc00
            }

            .r4 {
                color: black;
                background: #668ceb
            }

            .r5 {
                color: white;
                background: #4134eb
            }


            td:hover {
                cursor: pointer;
                user-select: none;

            }

            .th {
                background: #BBCCFF;
            }
        </style>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecord" name="Пред. запись"
                          action="/js-smo_direction-preRecorded.do"/>
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecordMany" name="Пред. запись неск-ко специалистов"
                          action="/js-smo_direction-preRecordedMany.do"/>
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Journal" name="Журнал направленных"
                          action="/visit_journal_direction.do"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/createNewTime.do" defaultField="specialistName" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete size="100" fieldColSpan="7" vocName="workFunctionByDirect" property="specialist"
                                      label="Специалист" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateFrom" label="Начальная дата"/>
                    <msh:textField property="dateTo" label="Конечная дата"/>
                </msh:row>
                <msh:row>
                    <td></td>
                    <td onclick="this.childNodes[1].checked='checked';chkchangeChet(3)"><input class="radio"
                                                                                               name="chetnechet"
                                                                                               id="chetnechet3"
                                                                                               value="3" type="radio">все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';chkchangeChet(0)"><input class="radio"
                                                                                               name="chetnechet"
                                                                                               id="chetnechet0"
                                                                                               value="0" type="radio">четные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';chkchangeChet(1)"><input class="radio"
                                                                                               name="chetnechet"
                                                                                               id="chetnechet1"
                                                                                               value="1" type="radio">нечетные
                    </td>
                </msh:row>
                <msh:row>
                    <msh:textField property="timeFrom" label="Начальное время"/>
                    <msh:textField property="timeTo" label="Конечное время"/>
                </msh:row>

                <msh:row>
                    <td></td>
                    <td onclick="this.childNodes[1].checked='checked';chkchange(1)"><input class="radio" name="rdMode"
                                                                                           id="rdMode1" value="1"
                                                                                           type="radio">длительность
                        визита (минут)
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';chkchange(2)"><input class="radio" name="rdMode"
                                                                                           id="rdMode2" value="2"
                                                                                           type="radio">кол-во визитов
                    </td>
                    <br>
                    <msh:row>
                        <msh:textField property="countVisits" label="Кол-во"/>
                    </msh:row>

                    <msh:row>
                        <msh:autoComplete size="100" fieldColSpan="7" vocName="vocServiceReserveType"
                                          property="reserveType" label="Тип резерва" horizontalFill="true"/>
                    </msh:row>
                </msh:row>
            </msh:panel>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek0" value="0" type="checkbox" style="margin:2px"
                   onchange="chooseAllDaysOfWeek()">
            <label onclick="document.getElementById('dayOfWeek0').click()">все</label><br>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek1" value="1" type="checkbox" style="margin:2px"
                   onchange="checkAll()">
            <label onclick="document.getElementById('dayOfWeek1').click()">Понедельник</label><br>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek2" value="2" type="checkbox" style="margin:2px"
                   onchange="checkAll()">
            <label onclick="document.getElementById('dayOfWeek2').click()">Вторник</label><br>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek3" value="3" type="checkbox" style="margin:2px"
                   onchange="checkAll()">
            <label onclick="document.getElementById('dayOfWeek3').click()">Среда</label><br>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek4" value="4" type="checkbox" style="margin:2px"
                   onchange="checkAll()">
            <label onclick="document.getElementById('dayOfWeek4').click()">Четверг</label><br>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek5" value="5" type="checkbox" style="margin:2px"
                   onchange="checkAll()">
            <label onclick="document.getElementById('dayOfWeek5').click()">Пятница</label><br>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek6" value="6" type="checkbox" style="margin:2px"
                   onchange="checkAll()">
            <label onclick="document.getElementById('dayOfWeek6').click()">Суббота</label><br>
            <input type="checkBox" name="dayOfWeek" id="dayOfWeek7" value="7" type="checkbox" style="margin:2px"
                   onchange="checkAll()">
            <label onclick="document.getElementById('dayOfWeek7').click()">Воскресенье</label><br>
        </msh:form>
        <input type="button" onclick="createDateTimes(this)" value="Создать"/>
        <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/OperatingRoom">
            <input type="button" onclick="changeSpecialist(this)" value="Поменять на опер."/>
        </msh:ifInRole>
        <div id="schedule">
        </div>

        <menu id="menu" class="menu">
            <li class="menu-item submenu">
                <button type="button" class="menu-btn">
                    <i class="fa fa-users"></i>
                    <span class="menu-text">Изменить резерв</span>
                </button>
                <menu class="menu" id="menuSubMenu">
                </menu>
            </li>
            <li class="menu-separator"></li>
            <li class="menu-item">
                <button type="button" class="menu-btn" onclick="deleteTime()">
                    <i class="fa fa-reply"></i>
                    <span class="menu-text">Удалить время</span>
                </button>
            </li>
            <li class="menu-item disabled" disabled>
                <button type="button" class="menu-btn" onclick="editRecord()">
                    <i class="fa fa-reply"></i>
                    <span class="menu-text">Редактировать</span>
                </button>
            </li>
            <li class="menu-item">
                <button type="button" class="menu-btn" onclick="updateTable()">
                    <i class="fa fa-star"></i>
                    <span class="menu-text">Обновить</span>
                </button>
            </li>
            <li class="menu-item">
                <button type="button" class="menu-btn" onclick="addTime()">
                    <i class="fa fa-star"></i>
                    <span class="menu-text">Добавить время после</span>
                </button>
            </li>
        </menu>


        <menu id="menu2" class="menu">
            <li class="menu-item">
                <button type="button" class="menu-btn" onclick="deleteDay()">
                    <i class="fa fa-download"></i>
                    <span class="menu-text">Удалить день</span>
                </button>
            </li>
            <li class="menu-item">
                <tags:chooseCopyPeriod name="chooseCopyPeriod" title="на который скопировать день" action="copy"
                                       actionText="Копировать"/>
                <tags:chooseCopyPeriod name="chooseDeletePeriod" title="в который удалить дни" action="deletePeriod"
                                       actionText="Удалить"/>
                <button type="button" class="menu-btn" onclick="showchooseCopyPeriod(thisCell.getAttribute('id'));">
                    <i class="fa fa-download"></i>
                    <span class="menu-text">Скопировать день</span>
                </button>
            </li>
            <li class="menu-item">
                <button type="button" class="menu-btn" onclick="showchooseDeletePeriod(thisCell.getAttribute('id'));">
                    <i class="fa fa-download"></i>
                    <span class="menu-text">Удалить период</span>
                </button>
            </li>
        </menu>

        <div id='newt'></div>
    </tiles:put>

    <tiles:put type="string" name="javascript">
        <link rel="stylesheet" type="text/css" href="css/contextMenu.css">
        <script type="text/javascript" src="js/contextMenu.js"></script>
        <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>

        <script type='text/javascript'>
            getReserves();

            function getReserves() {
                WorkCalendarService.getActualReserves({
                    callback: function (arr) {
                        if (arr) {
                            arr = JSON.parse(arr);
                            var t = "";
                            for (i = 0; i < arr.length; i++) {
                                r = arr[i];
                                t += "<li class=\"menu-item\">" +
                                    " <button type=\"button\" class=\"menu-btn\" onclick=\"changeReserve('" + r.id + "');\">" +
                                    " <i class=\"fa fa-comment\"></i>" +
                                    " <span class=\"menu-text\">" + r.name + "</span>" +
                                    " </button>" +
                                    " </li>"
                            }
                            t += "<li class=\"menu-item\">" +
                                " <button type=\"button\" class=\"menu-btn\" onclick=\"changeReserve('0');\">" +
                                " <i class=\"fa fa-comment\"></i>" +
                                " <span class=\"menu-text\">Удалить резерв</span>" +
                                " </button>" +
                                " </li>"
                            jQuery('#menuSubMenu').html(t);
                        }
                    }
                });

            }

            var thisCell;
            var weekplus = 0;
            document.forms[0].rdMode[0].checked = true;
            document.forms[0].chetnechet[0].checked = true;
            var checkedRadio = 1;
            var checkedRadioevenodd = 3;

            function chkchange(value) {
                checkedRadio = value;
                if (document.forms[0].rdMode[0].checked) {
                    checkedRadio = 1;
                } else {
                    checkedRadio = 2;
                }
            }

            function chkchangeChet(value) {
                checkedRadioevenodd = value;
            }

            function createDateTimes(ths) {
                //проверка на непустой промежуток/кол-во
                if (document.getElementById("countVisits").value != '' && !isNaN(+document.getElementById("countVisits").value)) {
                    //если ни один не выбран  - по умолчанию - все
                    var flag = false;
                    for (var i = 0; i < 8; i++)
                        if (document.getElementById('dayOfWeek' + i).checked) flag = true;
                    if (!flag) document.getElementById('dayOfWeek0').checked = 'checked';

                    ths.value = "Подождите...";
                    ths.disabled = true;

                    WorkCalendarService.createDateTimes($('dateFrom').value, $('dateTo').value,
                        $('specialist').value, $('timeFrom').value, $('timeTo').value,
                        $('countVisits').value, checkedRadio, $('reserveType').value, checkedRadioevenodd,
                        document.getElementById('dayOfWeek0').checked,
                        document.getElementById('dayOfWeek1').checked,
                        document.getElementById('dayOfWeek2').checked,
                        document.getElementById('dayOfWeek3').checked,
                        document.getElementById('dayOfWeek4').checked,
                        document.getElementById('dayOfWeek5').checked,
                        document.getElementById('dayOfWeek6').checked,
                        document.getElementById('dayOfWeek7').checked, {
                            callback: function () {
                                updateTable();
                                ths.disabled = false;
                                ths.value = "Создать";
                                var message = "Успешно создано!";
                                showToastMessage(message, null, true, false, 3000);
                            },
                            errorHandler: function (aMessage) {
                                showToastMessage("Не удалось создать! " + aMessage, null, true, true, 3000);
                                ths.disabled = false;
                                ths.value = "Создать";
                            }
                        });
                } else showToastMessage('Введите длительность визитов либо их количество!', null, true, true, 3000);
            }

            //выбрать все дни недели
            function chooseAllDaysOfWeek() {
                if (document.getElementById("dayOfWeek0").checked) {
                    document.getElementById("dayOfWeek1").checked = document.getElementById("dayOfWeek2").checked =
                        document.getElementById("dayOfWeek3").checked = document.getElementById("dayOfWeek4").checked =
                            document.getElementById("dayOfWeek5").checked = document.getElementById("dayOfWeek6").checked =
                                document.getElementById("dayOfWeek7").checked = 'checked';
                }
            }

            //снять отметку с все, если не все отмечены
            function checkAll() {
                if (document.getElementById("dayOfWeek0").checked &&
                    !document.getElementById("dayOfWeek1").checked || !document.getElementById("dayOfWeek2").checked ||
                    !document.getElementById("dayOfWeek3").checked || !document.getElementById("dayOfWeek4").checked ||
                    !document.getElementById("dayOfWeek5").checked || !document.getElementById("dayOfWeek6").checked ||
                    !document.getElementById("dayOfWeek7").checked) document.getElementById("dayOfWeek0").checked = false;
                if (!document.getElementById("dayOfWeek0").checked &&
                    document.getElementById("dayOfWeek1").checked && document.getElementById("dayOfWeek2").checked &&
                    document.getElementById("dayOfWeek3").checked && document.getElementById("dayOfWeek4").checked &&
                    document.getElementById("dayOfWeek5").checked && document.getElementById("dayOfWeek6").checked &&
                    document.getElementById("dayOfWeek7").checked) document.getElementById("dayOfWeek0").checked = 'checked';
            }

            function nextWeek() {
                weekplus = weekplus + 7;
                updateTable();
            }

            function prevWeek() {
                weekplus = weekplus - 7;
                updateTable();
            }

            function updateTable() {
                WorkCalendarService.buildSheduleTable($('specialist').value, weekplus, {
                    callback: function (aResult) {
                        var t = document.getElementById("schedule");
                        t.innerHTML = aResult;
                        tranetable();
                    },
                    errorHandler: function (aMessage) {
                        showToastMessage("Не удалось отобразить! " + aMessage, null, true, true, 3000);
                    }
                });
            }


            specialistAutocomplete.addOnChangeCallback(function () {
                updateTable();
            });

            window.document.oncontextmenu = function (e) {
                if (e.target.getAttribute('class') == 'th') {
                    setMenu("menu2");
                    thisCell = e.target;
                    onContextMenu(e);
                }

                if (e.target.getAttribute('class') != 'th' && e.target.nodeName == 'TD') {
                    setMenu("menu");
                    thisCell = e.target;
                    onContextMenu(e);
                }
            };

            //если ничего не выделено - делаем текущий, если что-то выделено - делаю им
            function changeReserve(type) {
                var array = [];
                var checkboxes = document.querySelectorAll('input[type=checkbox]:checked');
                for (var i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i].id.indexOf("dayOfWeek") == -1)
                        array.push(checkboxes[i].id.replace("ch", ""));
                }
                if (array.length == 0) {
                    WorkCalendarService.changeScheduleElementReserve(thisCell.getAttribute('id'), type, {
                        callback: function () {
                            updateTable();

                        },
                        errorHandler: function (aMessage) {
                            showToastMessage("Не удалось отобразить! " + aMessage, null, true, true, 3000);
                        }
                    });
                } else {
                    WorkCalendarService.changeScheduleArrayReserve(array, type, {
                        callback: function () {
                            updateTable();

                        },
                        errorHandler: function (aMessage) {
                            showToastMessage("Не удалось отобразить! " + aMessage, null, true, true, 3000);
                        }
                    });
                }
            }

            //добавить время в существующий день
            function addTime() {
                var mins;
                do {
                    mins = prompt("Введите значение минут:", "00");
                    if (mins != null && mins >= 0 && mins < 60) {
                        if (mins.length < 2) mins = "0" + mins;
                        WorkCalendarService.addTime(thisCell.getAttribute('id'), mins, {
                            callback: function (aResult) {
                                showToastMessage(aResult, null, true, false, 3000);
                                updateTable();
                            },
                            errorHandler: function (aMessage) {
                                showToastMessage("Не удалось создать время! " + aMessage, null, true, true, 3000);
                            }
                        });
                    } else if (mins != null) showToastMessage("Некорректное количество минут!", null, true, true, 3000);
                }
                while (mins != null && (mins < 0 || mins >= 60));
            }

            function deleteTime() {
                WorkCalendarService.setScheduleElementIsDelete(thisCell.getAttribute('id'), {
                    callback: function () {
                        updateTable();
                    },
                    errorHandler: function (aMessage) {
                        showToastMessage("Не удалось отобразить! " + aMessage, null, true, true, 3000);
                    }
                });
            }

            function deleteDay() {
                WorkCalendarService.setScheduleDayIsDelete(thisCell.getAttribute('id'), {
                    callback: function () {
                        updateTable();
                    },
                    errorHandler: function (aMessage) {
                        showToastMessage("Не удалось отобразить! " + aMessage, null, true, true, 3000);
                    }
                });
            }

            function editRecord() {
                var id = thisCell.getAttribute('id');
                var t = document.getElementById(id).innerHTML;
                document.getElementById(id).innerHTML = "<input id='edit' style='display: block;' value='" + t + "'>";
                document.getElementById(id).focus();
            }

            function tranetable() {

                var myTable = document.getElementById('table');
                var newTable = document.createElement('table');
                newTable.id = myTable.id;
                var maxCells = 0;
                var maxRows = (myTable.rows.length);
                for (var r = 0; r < myTable.rows.length; r++) {
                    if (myTable.rows[r].cells.length > maxCells) {
                        maxCells = myTable.rows[r].cells.length;
                    }
                }

                for (var i = 0; i < maxCells; i++) {
                    newTable.insertRow(i);
                    for (var j = 0; j < maxRows; j++) {

                        if (myTable.rows[j].cells[i] == undefined) {
                            newTable.rows[i].insertCell(j);
                            newTable.rows[i].cells[j] = '-';
                        } else {
                            newTable.rows[i].insertCell(j);
                            newTable.rows[i].cells[j].innerHTML = myTable.rows[j].cells[i].innerHTML;
                            newTable.rows[i].cells[j].className = myTable.rows[j].cells[i].className;
                            newTable.rows[i].cells[j].id = myTable.rows[j].cells[i].id;
                        }
                    }
                }

                var div = document.getElementById('body-cont');
                div.innerHTML = "";
                div.appendChild(newTable);
            }

            <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/OperatingRoom">
            //изменить справочник Специалист <-> Операционная
            function changeSpecialist(btn) {
                if (btn.value == 'Поменять на опер.') {
                    specialistAutocomplete.setUrl('simpleVocAutocomplete/operationRoom');
                    specialistAutocomplete.setVocKey('operationRoom');
                    btn.value = 'Поменять на спец.'
                } else {
                    specialistAutocomplete.setUrl('simpleVocAutocomplete/workFunctionByDirect');
                    specialistAutocomplete.setVocKey('workFunctionByDirect');
                    btn.value = 'Поменять на опер.'
                }
                specialistAutocomplete.setVocId('');
                document.getElementById("schedule").innerHTML = '';
            }

            </msh:ifInRole>
        </script>
    </tiles:put>
</tiles:insert>