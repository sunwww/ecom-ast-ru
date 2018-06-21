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
            #schedule-table{
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
            .rt5{
                color:white;
                background: #4134eb
            }
            .r0{
                background: #a6ffea;
            }

            td:hover {
                cursor: pointer;
                user-select: none;

            }
            th{
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
                    <msh:textField property="timeFrom" label="Начальное время"/>
                    <msh:textField property="timeTo" label="Конечное время"/>
                </msh:row>

                <msh:row>
                    <td onclick="this.childNodes[1].checked='checked';chkchange(1)"> <input class="radio" name="rdMode" id="rdMode" value="1" type="radio" >длительность визита (минут)</td>
                    <td onclick="this.childNodes[1].checked='checked';chkchange(2)"> <input class="radio" name="rdMode" id="rdMode" value="2" type="radio" >кол-во визитов</td>
                    <br>
                    <msh:row>
                        <msh:textField property="countVisits" label="Кол-во"/>
                    </msh:row>

                </msh:row>

            </msh:panel>
        </msh:form>
        <input type="button" onclick="createDateTimes()" value="Создать" />
        <div id="schedule">

        </div>
        <div id="newTab"> </div>
    </tiles:put>

    <tiles:put type="string" name="javascript">
        <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
        <script type='text/javascript'>

            var weekplus=0;
            document.forms[0].rdMode[0].checked=true ;
            var checkedRadio=1;

            function chkchange(value) {

                checkedRadio = value;
                if(document.forms[0].rdMode[0].checked)
                {
                    checkedRadio=1;
                }else {
                    checkedRadio=2;
                }

                //alert(checkedRadio);
            }

            function createDateTimes() {
                WorkCalendarService.createDateTimes($('dateFrom').value,$('dateTo').value,
                    $('specialist').value,$('timeFrom').value,$('timeTo').value,
                    $('countVisits').value,checkedRadio,{
                        callback: function(aResult) {
                            alert(aResult) ;
                            updateTable();
                        },
                        errorHandler: function(aMessage) {
                            alert("Не удалось создать! "+aMessage) ;
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
                        //alert(aResult) ;
                    },
                    errorHandler: function(aMessage) {
                        alert("Не удалось отобразить! "+aMessage) ;
                    }
                });
            }

            specialistAutocomplete.addOnChangeCallback(function(){
                updateTable();

                //tranetable();
            });


            /*function tranetable() {

                var myTable = document.getElementById('schedule-table');
                var newTable = document.createElement('table');
                var maxColumns = 0;
                for(var r = 0; r < myTable.rows.length; r++) {
                    if(myTable.rows[r].cells.length > maxColumns) {
                        maxColumns = myTable.rows[r].cells.length;
                    }
                }
                for(var c = 0; c < maxColumns; c++) {
                    newTable.insertRow(c);
                    for(var r = 0; r < myTable.rows.length; r++) {
                        if(myTable.rows[r].length <= c) {
                            newTable.rows[c].insertCell(r);
                            newTable.rows[c].cells[r] = '-';
                        }
                        else {
                            newTable.rows[c].insertCell(r);
                            newTable.rows[c].cells[r].innerHTML = myTable.rows[r].cells[c].innerHTML;
                        }
                    }
                }
                var div = document.getElementById('newTab');
                div.appendChild(newTable);
            }*/
        </script>
    </tiles:put>
</tiles:insert>