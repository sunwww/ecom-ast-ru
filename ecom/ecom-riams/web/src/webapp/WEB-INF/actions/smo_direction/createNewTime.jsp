<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Patient">Создание нового времени по специалисту</msh:title>
    </tiles:put>

    <tiles:put name="style" type="string">
        <style type="text/css">
            #schedule{
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

            #alink{
                font-size: 25px;
                font-family: 'Roboto', sans-serif;
                color: #aa2836;
                text-decoration: none;
                padding: 5px 10px;
            }
            #table{
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

            .r0{
                background: #a6ffea;
            }
            .r3{
                color:black;
                background: #ff9de0
            }
            .r2{
                color:black;
                background:#ffcc00
            }
            .r4{
                color:black;
                background: #668ceb
            }
            .r5{
                color:white;
                background: #4134eb
            }


            td:hover {
                cursor: pointer;
                user-select: none;

            }
            .th{
                background:#BBCCFF;
            }
        </style>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecord" name="Пред. запись" action="/js-smo_direction-preRecorded.do"/>
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecordMany" name="Пред. запись неск-ко специалистов" action="/js-smo_direction-preRecordedMany.do"/>
            <msh:sideLink roles="/Policy/Mis/MedCase/Direction/Journal" name="Журнал направленных" action="/visit_journal_direction.do"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/createNewTime.do" defaultField="specialistName" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete size="100" fieldColSpan="7" vocName="workFunctionByDirect" property="specialist" label="Специалист" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateFrom" label="Начальная дата"/>
                    <msh:textField property="dateTo" label="Конечная дата"/>
                </msh:row>
                <msh:row>
                    <td></td>
                    <td onclick="this.childNodes[1].checked='checked';chkchangeChet(3)"> <input class="radio" name="chetnechet" id="chetnechet" value="3" type="radio" >все</td>
                    <td onclick="this.childNodes[1].checked='checked';chkchangeChet(0)"> <input class="radio" name="chetnechet" id="chetnechet" value="0" type="radio" >четные</td>
                    <td onclick="this.childNodes[1].checked='checked';chkchangeChet(1)"> <input class="radio" name="chetnechet" id="chetnechet" value="1" type="radio" >нечетные</td>
                </msh:row>
                <msh:row>
                    <msh:textField property="timeFrom" label="Начальное время"/>
                    <msh:textField property="timeTo" label="Конечное время"/>
                </msh:row>

                <msh:row>
                    <td></td>
                    <td onclick="this.childNodes[1].checked='checked';chkchange(1)"> <input class="radio" name="rdMode" id="rdMode" value="1" type="radio" >длительность визита (минут)</td>
                    <td onclick="this.childNodes[1].checked='checked';chkchange(2)"> <input class="radio" name="rdMode" id="rdMode" value="2" type="radio" >кол-во визитов</td>
                    <br>
                    <msh:row>
                        <msh:textField property="countVisits" label="Кол-во"/>
                    </msh:row>

                    <msh:row>
                        <msh:autoComplete size="100" fieldColSpan="7" vocName="vocServiceReserveType" property="reserveType" label="Тип резерва" horizontalFill="true"/>
                    </msh:row>
                </msh:row>

            </msh:panel>
        </msh:form>
        <input type="button" onclick="createDateTimes(this)" value="Создать" />
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
        </menu>


        <menu id="menu2" class="menu">
            <li class="menu-item">
                <button type="button" class="menu-btn" onclick="deleteDay()">
                    <i class="fa fa-download"></i>
                    <span class="menu-text" >Удалить день</span>
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
        callback: function(arr) {
            if (arr) {
                arr = JSON.parse(arr);
                var t="";
                alert(arr.length);
                for (i=0;i<arr.length;i++) {
                    r = arr[i];
                    alert (JSON.stringify(r));
                     t +="<li class=\"menu-item\">" +
                        " <button type=\"button\" class=\"menu-btn\" onclick=\"changeReserve('"+r.id+"');\">" +
                        " <i class=\"fa fa-comment\"></i>" +
                        " <span class=\"menu-text\">"+r.name+"</span>" +
                        " </button>" +
                        " </li>"
                }
                t +="<li class=\"menu-item\">" +
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
            var weekplus=0;
            document.forms[0].rdMode[0].checked=true ;
            document.forms[0].chetnechet[0].checked=true ;
            var checkedRadio=1;
            var checkedRadioevenodd=3;

            function chkchange(value) {
                checkedRadio = value;
                if(document.forms[0].rdMode[0].checked){
                    checkedRadio=1;
                }else {
                    checkedRadio=2;
                }
            }
            function chkchangeChet(value) {
                checkedRadioevenodd = value;
            }

            function createDateTimes(ths) {

                ths.value="Подождите...";
                ths.disabled=true;

                WorkCalendarService.createDateTimes($('dateFrom').value,$('dateTo').value,
                    $('specialist').value,$('timeFrom').value,$('timeTo').value,
                    $('countVisits').value,checkedRadio,$('reserveType').value,checkedRadioevenodd,{
                        callback: function(aResult) {
                            //alert(aResult);
                            updateTable();
                            ths.disabled=false;
                            ths.value="Создать";
                            var message ="Успешно создано!";
                            showToastMessage(message,null,true);
                        },
                        errorHandler: function(aMessage) {
                            alert("Не удалось создать! " +aMessage) ;
                            ths.disabled=false;
                            ths.value="Создать";
                        }
                    }) ;
            }
            function nextWeek() {
                weekplus=weekplus+7;
                updateTable();
            }

            function prevWeek() {
                weekplus=weekplus-7;
                updateTable();
            }

            function updateTable() {
                WorkCalendarService.buildSheduleTable($('specialist').value,weekplus,{
                    callback: function(aResult) {
                        var t = document.getElementById("schedule");
                        t.innerHTML=aResult;
                        tranetable();
                    },
                    errorHandler: function(aMessage) {
                        alert("Не удалось отобразить! "+aMessage) ;
                    }
                });
            }


            specialistAutocomplete.addOnChangeCallback(function(){
                updateTable();
            });

           /* window.document.onclick = function (e) {
                if(e.target.getAttribute('class')=='th'){
                    setMenu("menu2");
                    thisCell = e.target;
                    document.addEventListener('contextmenu', onContextMenu, true);
                }

                if(e.target.getAttribute('class')!='th' && e.target.nodeName=='TD'){
                    setMenu("menu");
                  thisCell = e.target;
                  document.addEventListener('contextmenu', onContextMenu, true);
                }
            };*/

            window.document.oncontextmenu = function (e) {
                if(e.target.getAttribute('class')=='th'){
                    setMenu("menu2");
                    thisCell = e.target;
                    onContextMenu(e);
                }

                if(e.target.getAttribute('class')!='th' && e.target.nodeName=='TD'){
                    setMenu("menu");
                    thisCell = e.target;
                    onContextMenu(e);
                }
            };

            function changeReserve(type) {
                WorkCalendarService.changeScheduleElementReserve(thisCell.getAttribute('id'),type,{
                    callback: function(aResult) {
                        updateTable();

                    },
                    errorHandler: function(aMessage) {
                        alert("Не удалось отобразить! "+aMessage) ;
                    }
                });
            }

            function deleteTime() {
                WorkCalendarService.setScheduleElementIsDelete(thisCell.getAttribute('id'),{
                    callback: function(aResult) {
                        updateTable();
                    },
                    errorHandler: function(aMessage) {
                        alert("Не удалось отобразить! "+aMessage) ;
                    }
                });
            }

            function deleteDay() {
                WorkCalendarService.setScheduleDayIsDelete(thisCell.getAttribute('id'),{
                    callback: function(aResult) {
                        updateTable();
                    },
                    errorHandler: function(aMessage) {
                        alert("Не удалось отобразить! "+aMessage) ;
                    }
                });
            }

            function editRecord() {
                var id = thisCell.getAttribute('id');
                var t = document.getElementById(id).innerHTML;
                document.getElementById(id).innerHTML ="<input id='edit' style='display: block;' value='"+t+"'>";
                document.getElementById(id).focus();
            }

            function tranetable() {

                var myTable = document.getElementById('table');
                var newTable = document.createElement('table');
                newTable.id = myTable.id;
                var maxCells = 0;
                var maxRows = (myTable.rows.length);
                for(var r = 0; r < myTable.rows.length; r++) {
                    if(myTable.rows[r].cells.length > maxCells) {
                        maxCells = myTable.rows[r].cells.length;
                    }
                }

                for(var i = 0; i < maxCells; i++) {
                    newTable.insertRow(i);
                    for(var j = 0; j < maxRows; j++) {

                        //alert(i+" "+j);
                        if(myTable.rows[j].cells[i] == undefined){
                            newTable.rows[i].insertCell(j);
                            newTable.rows[i].cells[j] = '-';
                        }else{
                            newTable.rows[i].insertCell(j);
                            newTable.rows[i].cells[j].innerHTML = myTable.rows[j].cells[i].innerHTML;
                            newTable.rows[i].cells[j].className = myTable.rows[j].cells[i].className;
                            newTable.rows[i].cells[j].id = myTable.rows[j].cells[i].id;
                        }
                    }
                }

                var div = document.getElementById('body-cont');
                div.innerHTML="";
                div.appendChild(newTable);
            }
        </script>
    </tiles:put>
</tiles:insert>