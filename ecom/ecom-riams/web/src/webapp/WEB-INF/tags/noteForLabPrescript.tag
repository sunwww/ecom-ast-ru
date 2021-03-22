<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="name" required="true" description="Название поля Address" %>
<%@ attribute name="functionSave" required="true" description="Функция для сохр-я" %>


<style type="text/css">
    #${name}NoteDialog {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}NoteDialog' class='dialog'>
    <h2>Примечание для лаборатории</h2>
    <div class='rootPane'>
        <form>
            <msh:panel>
                <msh:row>
                    <msh:autoComplete vocName="vocPatientStatus" property="patientStatus" label="Статус пациентки"
                                      fieldColSpan="5"
                                      horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="lastMensDate" label="Дата последней менструации"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="otherStatus" label="Иное"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocTypeBiomat" property="typeBiomat" label="Вид биомат." fieldColSpan="5"
                                      horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="otherBiomat" label="Иное (биомат.)"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="height" label="Рост"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="weight" label="Вес"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="age" label="Возраст"/>
                </msh:row>
            </msh:panel>
            <msh:row>
                <td colspan="6">
                    <input type="button" id='${name}buttonLabNoteOk' value='Сохранить' onclick='save${name}LabNote(0)'/>
                    <input type="button" value='Отменить' onclick='cancel${name}LabNote()'/>
                </td>
            </msh:row>
        </form>

    </div>
</div>

<script type="text/javascript">
    var theIs${name}NoteDialogInitialized = false;
    new dateutil.DateField($('lastMensDate'));
    var the${name}NoteDialog = new msh.widget.Dialog($('${name}NoteDialog'));

    var labs${name} = "";


    function check${name}LabNote() {
        var msg = '';
        if (labs${name}.indexOf('1') != -1) {
            if (!$('patientStatus').value)
                msg += 'статус пациентки ; ';
            else if ($('patientStatus').value == 4 && !$('lastMensDate').value)
                msg += 'дата последней менструации ; ';
            else if ($('patientStatus').value == 5 && !$('otherStatus').value)
                msg += 'иное ; ';
        }
        if (labs${name}.indexOf('2') != -1 && !$('typeBiomat').value)
            msg += 'вид биоматериала ; ';
        else if ($('typeBiomat').value == 4 && !$('otherBiomat').value)
            msg += 'иное (биомат.) ; ';
        if (labs${name}.indexOf('3') != -1) {
            if (!$('height').value)
                msg += 'рост ; ';
            if (!$('weight').value)
                msg += 'вес ; ';
            if (!$('age').value)
                msg += 'возраст ; ';
        }
        if (labs${name}.indexOf('3') != -1
            && (isNaN($('age').value) || isNaN($('height').value) || isNaN($('weight').value)))
            msg += ' Поля рост, вес и возраст - только числовые!';
        return msg;
    }

    function save${name}LabNote() {
        var msg = check${name}LabNote();
        if (msg)
            showToastMessage("Обязательно заполнить: " + msg, null, true, true, 5000);
        else {
            var note1 = '', note2 = '', note3 = '';
            if ($('patientStatusName').value)
                note1 += 'Статус: ' + $('patientStatusName').value;
            if ($('lastMensDate').value)
                note1 += ' Дата посл. менс.: ' + $('lastMensDate').value + ' ';
            if ($('otherStatus').value)
                note1 += ' (' + $('otherStatus').value + ')';
            if ($('typeBiomatName').value)
                note2 += ' Вид биомат.: ' + $('typeBiomatName').value;
            if ($('otherBiomat').value)
                note2 += ' (' + $('otherBiomat').value + ')';
            if ($('height').value)
                note3 += 'Рост: ' + $('height').value;
            if ($('weight').value)
                note3 += ' Вес: ' + $('weight').value + '. ';
            if ($('age').value)
                note3 += ' Возраст: ' + $('age').value + ' ';
            $('noteForLab1').value = note1;
            $('noteForLab2').value = note2;
            $('noteForLab3').value = note3;
            ${functionSave}(1);
        }
    }

    function init() {
        patientStatusAutocomplete.addOnChangeCallback(function () {
            if ($('patientStatus').value == 5)
                $('otherStatus').className += " required";
            else if ($('patientStatus').value == 4)
                $('lastMensDate').className += " required";
            else {
                removeCssClass($('otherStatus'), 'required');
                removeCssClass($('lastMensDate'), 'required');
            }
        });

        typeBiomatAutocomplete.addOnChangeCallback(function () {
            if ($('typeBiomat').value == 4)
                $('otherBiomat').className += " required";
            else
                removeCssClass($('otherBiomat'), 'required');
        });
    }

    //удалить css класс
    function removeCssClass(el, className) {
        el.className = el.className.replace(new RegExp(className, "g"), "");
    }

    function show${name}LabNote(labs) {
        labs${name} = labs;
        if (labs${name}.indexOf('1') != -1) {
            $('patientStatusName').className += ' required';

            removeCssClass($('lastMensDate'), 'required');
            removeCssClass($('otherStatus'), 'required');
            if (labs${name}.indexOf('2') == -1)
                removeCssClass($('typeBiomatName'), 'required');
            removeCssClass($('otherBiomat'), 'required');
            if (labs${name}.indexOf('3') != -1) {
                removeCssClass($('height'), 'required');
                removeCssClass($('weight'), 'required');
                removeCssClass($('age'), 'required');
            }
        }
        if (labs${name}.indexOf('2') != -1) {
            $('typeBiomatName').className += ' required';
            removeCssClass($('lastMensDate'), 'required');
            removeCssClass($('otherStatus'), 'required');
            if (labs${name}.indexOf('1') == -1)
                removeCssClass($('patientStatusName'), 'required');
            removeCssClass($('otherBiomat'), 'required');
            if (labs${name}.indexOf('3') == -1) {
                removeCssClass($('height'), 'required');
                removeCssClass($('weight'), 'required');
                removeCssClass($('age'), 'required');
            }
        }
        if (labs${name}.indexOf('3') != -1) {
            $('height').className += ' required';
            $('weight').className += ' required';
            $('age').className += ' required';

            removeCssClass($('lastMensDate'), 'required');
            removeCssClass($('otherStatus'), 'required');
            if (labs${name}.indexOf('1') == -1)
                removeCssClass($('patientStatusName'), 'required');
            if (labs${name}.indexOf('2') == -1)
                removeCssClass($('typeBiomatName'), 'required');
            removeCssClass($('otherBiomat'), 'required');
        }
        the${name}NoteDialog.show();
        init();
        getPatientAge();
    }

    function cancel${name}LabNote() {
        $('submitButton').disabled = false;
        $('submitButton').value = 'Создать';
        the${name}NoteDialog.hide();
    }

    //получить рассчитанный возраст
    function getPatientAge() {
        PrescriptionService.getPatientAge(${param.id}, {
            callback: function (aResult) {
                if (aResult)
                    $('age').value = aResult;
            }
        });
    }
</script>