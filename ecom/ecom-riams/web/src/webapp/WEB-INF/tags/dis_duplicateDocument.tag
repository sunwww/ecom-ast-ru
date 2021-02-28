<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="key" required="true" description="Горячие клавиши" %>
<%@ attribute name="confirm" required="false" description="Сообщение" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}DuplicateDocument('.do') "
              key="${key}"/>

<style type="text/css">
    #${name}DuplicateDisDocumentDialog {
        visibility: hidden;
        display: none;
        position: absolute;
    }

    #${name}Job {
        text-transform: uppercase;
    }
</style>

<div id='${name}DuplicateDisDocumentDialog' class='dialog'>
    <h2>Создание дубликата (замена испорченного)</h2>
    <div class='rootPane'>

        <form action="javascript:">
            <msh:panel>
                <msh:row>
                    <td><input id="get${name}FreeNumberButton" type="button"
                               onclick="getFreeNumber('${name}Number',this)" value="Получить номер ЭЛН"></td>
                </msh:row>
                <msh:row>
                    <msh:separator label="Новый бланк" colSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="${name}Seria" label="Серия"/>
                    <msh:textField property="${name}Number" label="Номер" size="20"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete label="Причина выдачи дубликата:" property="${name}Reason"
                                      vocName="vocDisabilityStatusForDuplicate" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:textField label="Место работы:" property="${name}Job" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="${name}UpdateJob" label="Обновить место работы" fieldColSpan="3"
                                  horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete label="Пред.ВК:" property="${name}WorkFunction2" vocName="workFunction"
                                      horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
            </msh:panel>
            <msh:row>
                <td colspan="6">
                    <input type="button" value='OK' onclick="javascript:save${name}DuplicateDocument()"/>
                    <input type="button" value='Отменить' onclick='cancel${name}DuplicateDocument()'/>
                </td>
            </msh:row>
        </form>

    </div>
</div>

<script type="text/javascript">
    var theIs${name}DuplicateDisDocumentDialogInitialized = false;
    var the${name}DuplicateDisDocumentDialog = new msh.widget.Dialog($('${name}DuplicateDisDocumentDialog'));

    // Показать
    function show${name}DuplicateDocument() {
        // устанавливается инициализация для диалогового окна
        if (!theIs${name}DuplicateDisDocumentDialogInitialized) {
            init${name}DuplicateDisDocumentDialog();
        }
        checkIsElectronicDocument();

    }

    // Отмена
    function cancel${name}DuplicateDocument() {
        the${name}DuplicateDisDocumentDialog.hide();
    }

    // Сохранение данных
    function save${name}DuplicateDocument() {
        if ($('${name}Reason').value == 0) {
            alert("Не выбрана причина закрытия");
            $("${name}ReasonName").focus();
        } else if ($('${name}Number').value == "") {
            alert("Поле номер является обязательным");
            $("${name}Number").focus();
        } else {
            DisabilityService.createDuplicateDocument(
                '${param.id}', $('${name}Reason').value, $('${name}Seria').value, $('${name}Number').value
                , $('${name}WorkFunction2').value, $('${name}Job').value, $('${name}UpdateJob').checked
                , {
                    callback: function (aString) {
                        alert('Создан дубликат!');
                        window.location.href = 'entityParentView-dis_document.do?id=' + aString;
                    }
                }
            );

        }
    }

    // инициализация диалогового окна
    function init${name}DuplicateDisDocumentDialog() {
        theIs${name}DuplicateDisDocumentDialogInitialized = true;
        $('${name}Job').value = $('job').value;
    }

    function checkIsElectronicDocument() {
        DisabilityService.getElectronicDisabilityDocumentInfo(${param.id}, null, {
            callback: function (res) {
                {
                    if (res != null) { //электронный ЛВН id#number#createDate#exportDate#annulDate
                        var eln = res.split("#");
                        if ("" + eln[4] == "") { //ЭЛН не закрыт
                            if (confirm("Больничный лист является электронным, для выдачи дубликата необходимо аннулировать этот больничный лист. Перейти к аннулированию?")) {
                                showannulDisSheetReasonCloseElectronicDocument();
                            }
                        } else {
                            the${name}DuplicateDisDocumentDialog.show();
                            $("${name}Number").focus();
                        }
                    } else { //Не электронный, продолжаем
                        the${name}DuplicateDisDocumentDialog.show();
                        $("${name}Number").focus();
                    }
                }
            }
        });
    }
</script>