<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
        <msh:ifFormTypeIsNotView formName="pres_servicePrescriptionForm">
            <script type="text/javascript">

                var currentDate = new Date;
                var textDay = currentDate.getDate() < 10 ? '0' + currentDate.getDate() : currentDate.getDate();
                var textMonth = currentDate.getMonth() + 1;
                textMonth = textMonth < 10 ? '0' + textMonth : textMonth;
                var textYear = currentDate.getFullYear();
                var textDate = textDay + '.' + textMonth + '.' + textYear;
                var canChangeDate = false;

                function fillServiceListVocId() {
                    if ($('medcaseType').value === 'POLYCLINIC') {
                        labServiciesAutocomplete.setUrl('simpleVocAutocomplete/labMedServiceByServiceStreamPol');
                    }
                }

                fillServiceListVocId();


                function changeDate(days) {
                    if (canChangeDate) {
                        var l = $('labDate') ? $('labDate').value : $('planStartDate').value;
                        l = l.substr(6, 4) + '-' + l.substr(3, 2) + '-' + l.substr(0, 2);
                        currentDate.setTime(Date.parse(l));
                        currentDate.setDate(currentDate.getDate() + days);
                        var newTextDay = currentDate.getDate() < 10 ? '0' + currentDate.getDate() : currentDate.getDate();
                        var newTextMonth = currentDate.getMonth() + 1;
                        newTextMonth = newTextMonth < 10 ? '0' + newTextMonth : newTextMonth;
                        var newTextYear = currentDate.getFullYear();
                        textDate = newTextDay + '.' + newTextMonth + '.' + newTextYear;
                        if ($('labDate')) $('labDate').value = textDate;
                        if ($('planStartDate')) $('planStartDate').value = textDate;
                        for (var i = 1; i <= labNum; i++) {
                            if ($('labDate' + i)) {
                                $('labDate' + i).value = textDate;
                            }
                        }
                    }
                }

                //Заполняем ЛН данными из шаблона (не удаляя существующие назначения).
                function fillFormFromTemplate(aData) {
                    var aRow = aData.split("#");
                    if (aRow.length > 0) {
                        for (var i = 0; i < aRow.length; i++) {
                            var research = aRow[i].split("@");
                            if (research[0] != null && research[0] != "") {
                                var prescType = research[0];
                                if (prescType == "SERVICE") {
                                    addRows(research[1], 1);
                                }
                            }
                        }
                    }

                }

                function prepare1Row(aId, aName) {

                    $('labServicies').value = aId;
                    $('labServiciesName').value = aName;
                    if (canChangeDate) {
                        show2EnterDate();
                    } else {
                        prepare1RowByDate($('labDate').value);
                    }
                }

                function prepare1RowByDate(aDate) {
                    $('labDate').value = aDate;
                    addRow('lab');
                    $('labServicies').value = '';
                    $('labServiciesName').value = '';
                }

                var oldaction = document.forms['pres_servicePrescriptionForm'].action;
                document.forms['pres_servicePrescriptionForm'].action = "javascript:checkDoubles()";

                var num = 0;
                var labNum = 0;
                var funcNum = 0;
                var labList = "";

                function showChangeDepartment() {
                    if (confirm("Место забора необходимо указывать, если оно отличается от отделения, где лежит пациент!!!")) {
                        $('labDepartmentName').style.visibility = 'visible';
                        $('labDepartmentLabel').style.visibility = 'visible';
                        $('btnChangeDepartment').style.visibility = 'hidden';
                    }

                }

                function preShowDir() {
                    $('1IsViewButton').value = $('prescriptType').value;
                    var list = +$('labServicies').value;
                    clear1DirMedServiceDialog();
                    var typeNum = 0;
                    type = 'lab';
                    typeNum = labNum;


                    while (typeNum > 0) {
                        if (document.getElementById(type + "Element" + typeNum)) {
                            var ar = $(type + "Service" + typeNum).value;
                            list += ",";
                            list += ar;
                        }
                        typeNum -= 1;
                    }

                    $('1ListIds').value = list;
                }

                onload =
                    function isInDepartment() {
                        PrescriptionService.isMedcaseIsDepartment($('medcaseId').value, {
                            callback: function (aResults) {
                                if (aResults === true) {
                                    $('labDepartmentName').style.visibility = 'hidden';
                                    $('labDepartmentLabel').style.visibility = 'hidden';
                                    $('btnChangeDepartment').style.visibility = 'visible';

                                } else {
                                    $('labDepartmentName').style.visibility = 'visible';
                                    $('labDepartmentLabel').style.visibility = 'visible';
                                    $('btnChangeDepartment').style.visibility = 'hidden';
                                }

                            }
                        });
                        startLoad();
                    }

                function startLoad() {
                    var date = new Date();
                    var month = date.getMonth() + 1;
                    if (month < 10) {
                        month = "0" + month;
                    }
                    var day = date.getDate();
                    if (day < 10) {
                        day = "0" + day;
                    }
                    var year = date.getFullYear();
                    if ($('labDate')) $('labDate').value = day + "." + month + "." + year;
                    if ($('funcDate')) $('funcDate').value = day + "." + month + "." + year;
                    if ($('prescriptType').value == "" || $('prescriptType').value == null) {
                        showcheckPrescTypes();
                    }
                }

                function checkDoubles() {
                    labList = "";
                    if ($('labServicies')) {
                        writeServicesToList('lab');
                    }

                    var str = "";
                    var aList = labList;
                    aList = aList.substring(0, aList.length - 1);
                    var aListArr = aList.split("#");
                    if (aListArr.length > 0) {
                        for (var i = 0; i < aListArr.length; i++) {
                            var id = aListArr[i].split(":");
                            str += id[0] + ":";
                        }
                    }
                    str = str.substring(0, str.length - 1);
                    PrescriptionService.getDuplicatePrescriptions($('medcaseId').value, str, {
                        callback: function (aResult) {
                            if (aResult.length > 0) {
                                var aText = "Данные назначения\n " + aResult + "\nуже назначены пациенту в этой истории болезни, все равно назначить?";
                                if (!confirm(aText)) {
                                    document.getElementById('submitButton').disabled = false;
                                    document.getElementById('submitButton').value = 'Создать';
                                    return;
                                }
                            }
                            checkLabs();


                        }
                    });
                }

                //Запускаем при создании/изменении типа назначения
                function changePrescriptionType() {
                    var prescriptionType = $('prescriptType').value;
                    if ($('allowOnlyPaid').value == "true") { //начинаем...
                        showToastMessage("Создание назначения возможно только из списка оплаченных назначений!");
                        jQuery('[name="subm"]').hide();
                        //jQuery('#btnAddCharged').show();
                        PrescriptionService.getAllowedLabServiciesByPatient($('patient').value, prescriptionType, {
                            callback: function (servList) {
                                showChargedServiceList(JSON.parse(servList));
                            }
                        });

                    } else {
                        jQuery('#btnAddCharged').hide();
                        jQuery('#btnAddCharged').hide();
                        labServiciesAutocomplete.setParentId(prescriptionType);
                        writeServicesToList('lab');
                        $('labServicies').value = "";
                        $('labServiciesName').value = "";
                        if (labList.length > 0) {
                            removeRows('lab');
                            PrescriptionService.getPresLabTypes(labList, prescriptionType, {
                                callback: function (aResult) {
                                    if (aResult) {
                                        var resultList = aResult.split('#');
                                        if (resultList.length > 0) {
                                            for (var i = 0; i < resultList.length; i++) {
                                                var resultRow = resultList[i].split(':');
                                                if (resultRow[0] != null && resultRow[0] != "") {
                                                    addRows(resultList[i], 0);
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                }

                function removeRows(type) {
                    var rType;
                    if (type == 'lab') {
                        rType = labNum;
                        labNum = 0;
                    } else if (type == 'func') {
                        rType = funcNum;
                        funcNum = 0;
                    } else if (type == 'drug') {
                        rType = drugNum;
                        drugNum = 0;
                    } else return;

                    if (rType > 0) {
                        for (var i = 1; i <= rType; i++) {
                            if (document.getElementById(type + 'Element' + i)) {
                                var node = document.getElementById(type + 'Element' + i);
                                node.parentNode.removeChild(node);
                            }
                        }
                    }

                }

                function checkLabs() {
                    if ($('funcServicies')) {
                        writeServicesToList('func');
                    }

                    $('labList').value = labList;
                    document.forms['pres_servicePrescriptionForm'].action = oldaction;
                    document.forms['pres_servicePrescriptionForm'].submit();
                }

                function writeServicesToList(type) {
                    var typeNum = 0;
                    if (type == 'lab') {
                        typeNum = labNum;
                    } else if (type == 'func') {
                        typeNum = funcNum;
                    }
                    var isDoubble = 0;
                    while (typeNum > 0) {
                        if (document.getElementById(type + "Element" + typeNum)) {
                            var curService = document.getElementById(type + 'Service' + typeNum);
                            var curDate = document.getElementById(type + 'Date' + typeNum);
                            var curCabinet = document.getElementById(type + 'Cabinet' + typeNum);
                            var curDepartment = document.getElementById(type + 'Department' + typeNum);

                            if (curService.value != "" & curDate.value != "") {
                                labList += curService.value + ":";
                                labList += curDate.value + ":";
                                labList += curCabinet.value + ":";
                                labList += curDepartment.value;
                                if ($(type + 'CaosService' + typeNum)) labList += ":::" + $(type + 'CaosService' + typeNum).value;
                                labList += "#";
                                // Проверка на дубли
                                if ($(type + 'Servicies').value == curService.value) {
                                    isDoubble = 1;
                                }
                            }

                        }
                        typeNum -= 1;
                    }
                    if (isDoubble == 0) {

                        if ($(type + 'Servicies').value != "" & $(type + 'Date').value != "") {
                            labList += $(type + 'Servicies').value;
                            labList += ":";
                            labList += $(type + 'Date').value;
                            labList += ":";
                            labList += $(type + 'Cabinet').value;
                            labList += ":";
                            labList += $(type + 'Department').value;
                            labList += "#";
                        }

                    }
                }

                function isChecked(num) {
                    if (num == 1) {
                        $('prescriptTypeLabel').style.display = "none";
                        $('prescriptTypeName').style.display = "none";
                        $('prescriptType').value = 5;
                    } else {
                        $('prescriptTypeLabel').style.display = "block";
                        $('prescriptTypeName').style.display = "block";
                    }
                }

                function addRows(aResult, aFocus) {
                    var resultRow = aResult.split(":");
                    /*
                    0 - ms.type
                    1 - ms.ID 2 - ms. code+name
                    3 - date
                    4 - cabinetID           5 - cabinetName
                    6 - departmentIntakeID  7 - departmentIntakeName (for lab)
                    8 - timeID              9 - timeName (for func)
                    */
                    var type = resultRow[0];
                    var id = resultRow[1];
                    var name = resultRow[2];
                    var date = resultRow[3] != "" ? resultRow[3] : textDate;
                    var cabinet = resultRow[4] ? resultRow[4] : "";
                    var cabinetName = resultRow[5] ? resultRow[5] : "";
                    var department = resultRow[6] ? resultRow[6] : "";
                    var departmentName = resultRow[7] ? resultRow[7] : "";
                    if (type == null || type == '') return;
                    if (type == 'LABSURVEY' || type == 'lab') {
                        if (department == '') {
                            department = $('labDepartment').value;
                            departmentName = $('labDepartmentName').value;
                        }
                        type = 'lab';
                        num = labNum;
                    } else if (type == 'DIAGNOSTIC' || type == 'func') {
                        type = 'func';
                        num = funcNum;
                    } else if (type == 'surg') {
                        num = surgNum;
                    } else if (type == 'hosp') {
                        num = hospNum;
                    } else if (type.substring(0,8) == 'COMMENT@') {
                        return;
                    }
                    else {
                        alert('Неизвестный тип: ' + type);
                    }
                    num += 1;

                    var tbody = document.getElementById('add' + type + 'Elements');
                    var row = document.createElement("TR");
                    row.id = type + "Element" + num;
                    tbody.appendChild(row);

                    // Создаем ячейки в вышесозданной строке и добавляем тх
                    var td1 = document.createElement("TD");
                    td1.colSpan = "2";
                    td1.align = "right";
                    var td2 = document.createElement("TD");
                    td2.colSpan = "2";
                    var td3 = document.createElement("TD");
                    row.appendChild(td1);
                    row.appendChild(td2);
                    row.appendChild(td3);

                    // Наполняем ячейки
                    //var dt2="<input id='"+type+"Cabinet"+num+"' name='"+type+"Cabinet"+num+"' value='"+cabinet+"' type='hidden'  />";

                    td1.innerHTML = textInput("Дата", type, "Date", num, date, date, 10);
                    td2.innerHTML = hiddenInput(type, "Service", num, id, "") + spanTag("Исследование", name, "");
                    if (type == "lab") {
                        td2.innerHTML += hiddenInput(type, "Department", num, department, "") + spanTag("Место забора", departmentName, "");
                        td2.innerHTML += hiddenInput(type, "Cabinet", num, cabinet, "");
                        labNum = num;
                    } else if (type == "func") {
                        td2.innerHTML += hiddenInput(type, "Cabinet", num, cabinet, "") + spanTag("Кабинет", cabinetName, "");
                        td2.innerHTML += hiddenInput(type, "CalTime", num, resultRow[8], "") + spanTag("Время", resultRow[9], "");
                        funcNum = num;
                        $(type + 'Cabinet').value = '';
                        $(type + 'CabinetName').value = '';
                    } else if (type == 'surg') {
                        surgNum = num;
                    }
                    td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
                    new dateutil.DateField($(type + 'Date' + num));

                    $(type + 'Servicies').value = '';
                    $(type + 'ServiciesName').value = '';
                    if (aFocus) $(type + 'ServiciesName').focus();
                }

                function hiddenInput(aType, aFld, aNum, aValue, aDefaultValue) {
                    return "<input id='" + aType + aFld + aNum + "' name='" + aType + aFld + aNum + "' value='" + (aValue == null || aValue == "" ? aDefaultValue : aValue) + "' type='hidden' />"
                }

                function textInput(aLabel, aType, aFld, aNum, aValue, aDefaultValue, aSize) {
                    return "<span>" + aLabel + "</span><input " + (+aSize > 0 ? "size=" + aSize : "") + " id='" + aType + aFld + aNum + "' name='" + aType + aFld + aNum + "' value='" + (aValue == null || aValue == "" ? aDefaultValue : aValue) + "' type='text'" + (aFld == "Date" && !canChangeDate ? "disabled" : "") + " />"
                }

                function spanTag(aText, aValue, aDefaultValue) {
                    return "<span>" + aText + ": <b>" + (aValue != null && aValue != "" ? aValue.trim() : aDefaultValue.trim()) + "</b></span>. ";
                }

                function addRow(type) {
                    if (type == 'lab') {
                        num = labNum;
                    } else if (type == 'func1') {
                        num = funcNum;
                    }
                    if ($(type + 'Servicies').value == "") {
                        alert("Выбирите услугу!");
                        return;
                    }

                    // Проверим на дубли
                    var checkNum = 1;
                    if (num > 0) {
                        while (checkNum <= num) {
                            if ($(type + 'Service' + checkNum)) {
                                if ($(type + 'Servicies').value == $(type + 'Service' + checkNum).value) {
                                    alert("Уже существует такое исследование!!!");
                                    return;
                                }
                            }
                            checkNum += 1;
                        }
                    }
                    //Проверяем - если платное назнаничение - должно быть оплачено.

                    num += 1;
                    // Считываем значения с формы

                    //  var nameId = document.getElementById(type+'Servicies').value;
                    var tbody = document.getElementById('add' + type + 'Elements');
                    var row = document.createElement("TR");
                    row.id = type + "Element" + num;
                    tbody.appendChild(row);

                    // Создаем ячейки в вышесозданной строке
                    // и добавляем тх
                    var td1 = document.createElement("TD");
                    td1.colSpan = "2";
                    td1.align = "right";
                    var td2 = document.createElement("TD");
                    td2.colSpan = "2";
                    var td3 = document.createElement("TD");
                    var td4 = document.createElement("TD");

                    row.appendChild(td1);
                    row.appendChild(td2);
                    row.appendChild(td3);
                    row.appendChild(td4);
                    // Наполняем ячейки
                    var dt = "<input id='" + type + "Service" + num + "' value='" + $(type + 'Servicies').value + "' type='hidden' name='" + type + "Service" + num + "' horizontalFill='true' size='90' readonly='true' />";
                    dt += "<input id='" + type + "CaosService" + num + "' value='" + $(type + 'CaosService').value + "' type='hidden'>";
                    var dt2 = "<input id='" + type + "Cabinet" + num + "' value='" + $(type + 'Cabinet').value + "' type='hidden' name='" + type + "Cabinet" + num + "' horizontalFill='true' size='1' readonly='true' />";
                    var dt4 = "<input id='" + type + "Department" + num + "' value='" + $(type + 'Department').value + "' type='hidden' name='" + type + "Department" + num + "' horizontalFill='true' size='1' readonly='true' />";
                    td2.innerHTML = dt + "<span>" + $(type + 'ServiciesName').value + "</span>";
                    td1.innerHTML = "<span>Дата: </span><input id='" + type + "Date" + num + "' name='" + type + "Date" + num + "' label='Дата' value='" + $(type + 'Date').value + "' size = '10'" + (!canChangeDate ? "disabled" : "") + " />";
                    //td2.innerHTML += dt2+"<span>. Кабинет: "+$(type+'CabinetName').value+"</span>" ;
                    td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='-' />";
                    new dateutil.DateField($(type + 'Date' + num));
                    td4.innerHTML = dt2 + dt4;

                    if (type == 'lab') {
                        labNum = num;
                    } else if (type == 'func') {
                        funcNum = num;
                    }

                }

                function checkError(aErrorList) {
                    for (var i = 0; i < aErrorList.length; i++) {
                        if (aErrorList[i][3] == "isEmptyUnit") {
                            if ($(aErrorList[i][0]).value == "") {
                                alert("Обязательное поле: " + aErrorList[i][2]);
                                //alert(aErrorList[i][0]+aErrorList[i][1]);
                                $(aErrorList[i][0] + aErrorList[i][1]).focus();
                                return true;
                            }
                        }
                    }
                    return false;
                }

                function prepareLabRow(type) {
                    var fldList, reqList = [];
                    if (type == 'func') {
                        var error = [
                            [type + 'Servicies', 'Name', 'Услуга!', 'isEmptyUnit']
                            , [type + 'CalTime', '', 'Дата и время услуги!', 'isEmptyUnit']

                        ];
                        num = funcNum;
                        fldList = [['Servicies', 1], ['ServiciesName', 1], ['CalDateName', 1], ['Cabinet', 1]
                            , ['CabinetName', 1], ['', 1], ['', 1], ['CalTime', 1], ['CalTimeName', 1], ['', 1]
                        ];

                    }
                    if (checkError(error)) return;
                    // Проверим на дубли
                    var checkNum = 1;
                    while (checkNum <= num) {
                        if (document.getElementById(type + 'Service' + checkNum)) {
                            if ($(type + 'CalTime')) {
                                if ($(type + 'CalTime').value == $(type + 'CalTime' + checkNum).value) {
                                    alert("На это время уже назначена запись!");
                                    return;
                                }
                            }
                            if ($(type + 'Servicies').value == document.getElementById(type + 'Service' + checkNum).value) {
                                if (confirm("В этом листе назначений уже назначено это исследование, ВЫ УВЕРЕНЫ, что хотите назначить его еще раз?")) {
                                    break;
                                } else {
                                    return;
                                }
                            }

                        }
                        checkNum += 1;
                    }
                    var ar = getArrayByFld(type, "", fldList, reqList, "", -1);
                    addPrescription($(type + 'Servicies').value, '', $(type + 'Cabinet').value, $(type + 'CalDateName').value, $(type + 'CalTime').value, $('comments').value);
                    addRows(type + ":" + ar[0], 1);
                }

                function getArrayByFld(aType, aTypeNum, aFldList, aReqFldId, aCheckFld, aCheckId) {
                    var next = true;
                    var l = "", lAll = "", isDoubble = 0;
                    for (var i = 0; i < aReqFldId.length; i++) {
                        if ($(aType + aFldList[aReqFldId[i]][0] + aTypeNum).value == "") {
                            next = false;
                            break;
                        }
                    }
                    if (next) {
                        //Формат строки - name:date:method:freq:freqU:amount:amountU:duration:durationU#
                        for (var i = 0; i < aFldList.length; i++) {
                            var val = aFldList[i][0] == "" ? "" : $(aType + aFldList[i][0] + aTypeNum).value;
                            val = val.replace(":", "-");
                            if (i != 0) {
                                l += ":";
                                lAll += ":";
                            }
                            if (aCheckId == i) {
                                if ($(aCheckFld).value == val) {
                                    isDoubble = 1;
                                }
                            }
                            if (aFldList[i][1] == 1) {
                                l += val;
                            }
                            lAll += val;
                            if (i == (aFldList.length - 1)) {
                                l += "#";
                                lAll += "#";
                            }
                        }
                    }
                    return [l, lAll, isDoubble];
                }

                /* Формируем блок с информаций об оплаченных услугах*/
                function showChargedServiceList(serviceList) {
                    var labSerName = jQuery('#labServiciesName');
                    if (serviceList.length === 0) {
                        alert('У пациента нет оплаченных услуг, назначать нечего!');
                        labSerName.val('НЕТ ОПЛАЧЕННЫХ ЛАБОРАТОРНЫХ УСЛУГ, НАЗНАЧЕНИЕ НЕВОЗМОЖНО');
                        labSerName.prop('disabled', true);
                        jQuery('#btnAddCharged').hide();
                        return;
                    }
                    var d = jQuery('#chargedDiv');
                    var dd = jQuery('#chargedDataDiv');
                    var txt = "";
                    for (var i = 0; i < serviceList.length; i++) {
                        var s = serviceList[i];
                        txt += "<label><input type='checkbox' id=" + s.serviceId + ":" + s.caosId + " name='chkChargedService'"
                            + (s.isAllow ? "" : " disabled ") + "/>" + s.serviceName + "</label><br>";
                    }
                    dd.html(txt);

                    d.dialog({
                        modal: true,
                        position: 'top',
                        width: '70%',
                        buttons: [
                            {
                                text: "Назначить",
                                click: function () {
                                    jQuery('[name="chkChargedService"]').each(function (ind, el) {
                                        if (el.checked) {
                                            jQuery('#labServicies').val(el.id.split(':')[0]);
                                            labSerName.val(jQuery(el).parent().text());
                                            jQuery('#labCaosService').val(el.id.split(':')[1]);
                                            addRow('lab');
                                        }
                                    });
                                    //Сохраняем все
                                    labSerName.val('ВЫБОР ТОЛЬКО ИЗ СПИСКА ОПЛАЧЕННЫХ УСЛУГ');
                                    labSerName.prop('disabled', true);
                                    jQuery(this).dialog("close");
                                }
                            },
                            {
                                text: "Отмена",
                                click: function () {
                                    labSerName.val('ВЫБОР ТОЛЬКО ИЗ СПИСКА ОПЛАЧЕННЫХ УСЛУГ');
                                    labSerName.prop('disabled', true);
                                    jQuery(this).dialog("close");
                                }
                            }
                        ]
                    });
                }

                /*Делаем возможность назначить любую услугу без информации об оплате*/
                function prescriptWithoutPayment() {
                    if (confirm('Информация о назначении будет передана в договорной отдел. Продолжить?')) {
                        jQuery('#labServiciesName').prop('disabled', false);
                        jQuery('[name="subm"]').show();
                        jQuery('#unpaidConfirmation').val("true");
                    }
                }

            </script>
        </msh:ifFormTypeIsNotView>

        <msh:ifFormTypeIsView formName="pres_servicePrescriptionForm">
            <script type="text/javascript">
                function cancelService() {
                    var reason = '' + prompt('Введите причину отмены');
                    PrescriptionService.cancelPrescription($('id').value, reason, {
                        callback: function (a) {
                            alert(a);
                        }
                    });
                }
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>

    <tiles:put name="body" type="string">
        <div id="chargedDiv" title="Список оплаченных услуг">
            <div id="chargedDataDiv"></div>
        </div>
        <!--
        - Назначение медицинской услуги
        -->
        <msh:form action="/entityParentSaveGoView-pres_servicePrescription.do" defaultField="id"
                  title="Назначение медицинской услуги">
            <msh:hidden property="id"/>
            <msh:hidden property="prescriptionList"/>
            <msh:hidden property="serviceStream"/>
            <msh:hidden property="medcaseType"/>
            <msh:hidden property="medcaseId"/>
            <msh:hidden property="patient"/>
            <msh:hidden property="allowOnlyPaid"/>
            <msh:hidden property="unpaidConfirmation"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="labList"/>
            <input type="hidden" id="labCaosService" value="">
            <input type="hidden" id="funcDepartment" value="">
            <input type="hidden" id="labCabinet" value="">
            <msh:panel colsWidth="3">


                <msh:ifFormTypeIsNotView formName="pres_servicePrescriptionForm">
                    <msh:row>
                        <input type='button' name='btnChangePrescriptionType' onclick='showcheckPrescTypes();'
                               value='Изменить тип назначения'/>
                        <input type='button' style="visibility:hidden" id="btnChangeDepartment"
                               name='btnChangeDepartment' onclick='showChangeDepartment();'
                               value='Изменить место забора'/>
                        <input type='button' id="btnMakeAnyPrescription" name='btnMakeAnyPrescription'
                               onclick='prescriptWithoutPayment();' value='Назначить без оплаты'/>
                        <msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения"
                                          horizontalFill="true"
                                          fieldColSpan="3" size="100"/>
                    </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:row>
                    <msh:autoComplete property="prescriptSpecial" label="Назначил" size="100" vocName="workFunction"
                                      fieldColSpan="3" viewOnlyField="true"
                                      horizontalFill="true"/>
                </msh:row>
                <msh:ifFormTypeIsView formName="pres_servicePrescriptionForm">
                    <msh:row>
                        <msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения"
                                          horizontalFill="true"
                                          fieldColSpan="1" size="30"/>
                    </msh:row>
                </msh:ifFormTypeIsView>

                <msh:ifFormTypeIsCreate formName="pres_servicePrescriptionForm">

                    <msh:row>

                        <table id="labTable">
                            <msh:row>
                                <msh:separator label="Лабораторные исследования" colSpan="10"/>
                            </msh:row>
                            <tbody id="addlabElements">
                            <tr>
                                <td style="text-align: center;" colspan="6">
                                    <input type="button" name="subm" onclick="addRow('lab');" value="+" tabindex="4"/>
                                    <input type="button" name="subm" onclick="preShowDir() ;show1DirMedService();"
                                           value="++" tabindex="4"/>
                                    <input type="button" name="btnAddCharged" id="btnAddCharged"
                                           onclick="jQuery( '#chargedDiv' ).dialog('open')"
                                           value="Назначить из списка оплаченных">

                                </td>
                            </tr>
                            <tr>
                                <msh:textField property="labDate" label="Дата " size="10"/>
                                <msh:autoComplete parentId="pres_servicePrescriptionForm.prescriptType"
                                                  property="labServicies" label="Лабораторный анализ"
                                                  vocName="labMedServiceByServiceStreamStac" horizontalFill="true"
                                                  size="90"/>

                            </tr>
                            <tr>
                                <td>
                                    <input type='button' value='-1' title="Уменьшить дату на 1 день"
                                           onclick='changeDate(-1)'>
                                    <input type='button' value='+1' title="Увеличить дату на 1 день"
                                           onclick='changeDate(1)'>

                                </td>
                            </tr>
                            <tr>
                                <msh:autoComplete property="labDepartment" label="Место забора"
                                                  vocName="departmentIntake" size='20' fieldColSpan="3"
                                                  horizontalFill="true"/>
                            </tr>
                            </tbody>
                        </table>


                    </msh:row>


                </msh:ifFormTypeIsCreate>
                <msh:ifFormTypeAreViewOrEdit formName="pres_servicePrescriptionForm">
                    <msh:row>
                        <msh:separator label="Наименование исследования" colSpan="10"/>
                    </msh:row>
                    <msh:autoComplete property="medService" vocName="labMedService" label="Наименование исследования"
                                      horizontalFill="true" size="90"/>
                    <msh:textField property="planStartDate" label="Дата " size="10"/>
                    <msh:ifFormTypeIsNotView formName="pres_servicePrescriptionForm">
                        <td>
                            <input type='button' value='-1' title="Уменьшить дату на 1 день" onclick='changeDate(-1)'>
                            <input type='button' value='+1' title="Увеличить дату на 1 день" onclick='changeDate(1)'>

                        </td>
                    </msh:ifFormTypeIsNotView>
                    <msh:row>
                        <msh:autoComplete property="prescriptCabinet" vocName="funcMedServiceRoom"
                                          parentAutocomplete="medService" label="Кабинет" size='20'
                                          horizontalFill="true"/>
                    </msh:row>
                    <tr>
                        <msh:autoComplete property="department" label="Место забора" vocName="departmentIntake"
                                          size='20' fieldColSpan="3" horizontalFill="true"/>
                    </tr>
                </msh:ifFormTypeAreViewOrEdit>
            </msh:panel>

            <msh:panel>
                <!-- --------------------------------------------------Конец блока "Функциональная диагностика" ------ -->

                <msh:row>
                    <msh:separator label="Дополнительная информация" colSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:label property="createDate" label="Дата создания"/>
                    <msh:label property="createTime" label="время"/>
                </msh:row>
                <msh:row>
                    <msh:label property="createUsername" label="пользователь"/>
                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редактирования"/>
                    <msh:label property="editTime" label="время"/>
                </msh:row>
                <msh:row>
                    <msh:label property="editUsername" label="пользователь"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
        <tags:dir_medService name="1" table="MEDSERVICE" title="Услуги" functionAdd="prepare1Row" addParam="id"/>
        <tags:enter_date name="2" functionSave="prepare1RowByDate"/>
        <msh:ifFormTypeIsView formName="pres_servicePrescriptionForm">
            <msh:section title="Исполнения">
                <ecom:parentEntityListAll formName="pres_prescriptionFulfilmentForm"
                                          attribute="fulfilments"/>
                <msh:table name="fulfilments"
                           action="entityParentView-pres_prescriptionFulfilment.do" idField="id">
                    <msh:tableColumn columnName="Дата исполнения" property="fulfilDate" />
                    <msh:tableColumn columnName="Время исполнения" property="fulfilTime" />
                    <msh:tableColumn columnName="Исполнитель" property="executorInfo" />
                </msh:table>
            </msh:section>
        </msh:ifFormTypeIsView>

    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="StacJournal" beginForm="pres_servicePrescriptionForm"/>
        <tags:pres_vocPrescTypes title="Выбор типа назначения" name="check" parentType="prescriptionList"
                                 parentID="${param.id}"/>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="pres_servicePrescriptionForm">
            <msh:sideMenu title="Назначения">
                <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="id"
                              action="/javascript:cancelService()" name="Отменить" key="ALT+2"/>
                <msh:sideLink confirm="Удалить?" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" params="id"
                              action="/entityParentDelete-pres_servicePrescription" name="Удалить" key="ALT+DEL"/>
            </msh:sideMenu>

            <msh:sideMenu title="Добавить">
                <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id"
                              action="/entityParentPrepareCreate-pres_prescriptionFulfilment"
                              name="Исполнение назначения" key="ALT+3"/>
            </msh:sideMenu>

            <msh:sideMenu title="Показать">
                <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id"
                              action="/entityParentListRedirect-pres_servicePrescription"
                              name="К списку назначений на услугу" key="ALT+4"/>
                <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id"
                              action="/entityParentListRedirect-pres_prescription" name="К списку назначений"
                              key="ALT+4"/>
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeIsCreate formName="pres_servicePrescriptionForm">
            <tags:templatePrescription record="2" parentId="${param.prescriptionList}" name="add"/>
            <msh:sideMenu title="Шаблоны">
                <msh:sideLink action=" javascript:showaddTemplatePrescription()" name="Назначения из шаблона"
                              title="Добавить назначения из шаблона"/>
            </msh:sideMenu>
        </msh:ifFormTypeIsCreate>
    </tiles:put>
</tiles:insert>