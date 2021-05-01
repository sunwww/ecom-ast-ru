<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="name" required="true" description="Название поля Address" %>


<style type="text/css">
    #${name}CurveDialog {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}CurveDialog' class='dialog'>
    <h2>Создание показателя температурного листа</h2>
    <div class='rootPane'>
        <form>
            <msh:panel>
                <msh:row>
                    <span id="resultText"> </span>
                </msh:row>
                <msh:row>
                    <msh:textField property="takingDate" label="Дата" viewOnlyField="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocDayTime" property="dayTime" label="Время суток" fieldColSpan="5"
                                      horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="degree" label="Температура, в градусах"/>
                </msh:row>
            </msh:panel>
            <msh:row>
                <td colspan="6">
                    <input type="button" id='${name}buttonCurveOk' value='Сохранить' onclick='save${name}Curve(0)'/>
                    <input type="button" id='${name}buttonCurveOk' value='Сохранить и создать новый'
                           onclick='save${name}Curve(1)'/>
                    <input type="button" value='Отменить' onclick='cancel${name}Curve()'/>
                </td>
            </msh:row>
        </form>

    </div>
</div>

<script type="text/javascript">
    var theIs${name}CurveDialogInitialized = false;
    new dateutil.DateField($('takingDate'));
    var the${name}CurveDialog = new msh.widget.Dialog($('${name}CurveDialog'));
    var sloId${name};

    function checkFields(aFields) {

        for (var i = 0; i < aFields.length; i++) {
            if ($(aFields[i][0]).value == '') {
                alert('Поле \"' + aFields[i][1] + '\" обязательно к заполнению');
                return false;
            }
        }
        return true;

    }

    function save${name}Curve(next) {
        var requiredFields = [['takingDate', 'Дата'], ['dayTime', 'Время суток'], ['degree', 'Температура']];
        if (checkFields(requiredFields)) {
            var obj = {
                takingDate: $('takingDate').value,
                dayTime: $('dayTime').value,
                degree: $('degree').value
            };
            $('resultText').innerHTML = "...";
            var data = JSON.stringify(obj);
            HospitalMedCaseService.createTemperatureCurve(sloId${name}, data, {
                callback: function (a) {
                    if (a == '1') {
                        $('resultText').innerHTML = "Успешно создано";
                    } else {
                        $('resultText').innerHTML = "Ошибка: " + a;
                    }
                    if (next == 1) {
                        $('dayTime').value = '';
                        $('dayTimeName').value = '';
                    } else {
                        cancel${name}Curve();
                    }
                }
            });
        }
    }

    function show${name}Curve(sloId) {
        the${name}CurveDialog.show();
        $('takingDate').value = getCurrentDate();
        $('takingDateReadOnly').value = getCurrentDate();
        sloId${name} = sloId;
    }

    function cancel${name}Curve() {
        the${name}CurveDialog.hide();
    }
</script>