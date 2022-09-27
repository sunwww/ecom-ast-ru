<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="documentId" required="true" description="Поле Id документа" %>

<style type="text/css">
    #${name}FSSJournal {
        visibility: hidden;
        display: none;
        position: absolute;
    }

    #${name}FSSProgress {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}FSSJournal' class='dialog'>
    <h2>Журнал экспорта документа в фонд</h2>
    <div class='rootPane'>
        <h3>Журнал экспорта документа в фонд</h3>
        <form action="javascript:void(0)" id="frmFond" name="frmFond">
            <msh:panel>

                <msh:row>
                    <span id='${name}FSSJournalText'/>
                </msh:row>
            </msh:panel>
            <msh:row>
                <td colspan="6">
                    <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}FSSJournal()'/>
                </td>
            </msh:row>
        </form>
    </div>
</div>
<div id='${name}FSSProgress' class='dialog'>
    <h2>Процесс отправки больничного листа</h2>
    <div class='rootPane'>
        <div id="${name}FSSProgressResultDiv"/>

    </div>
    <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}FSSProgress()'/>
    <div style="display: none" id="${name}confirmPersonalDataDiv">
        <input type="button" value='Подтверждаю данные пациента (отправить ЭЛН повторно)'
               onclick='javascript:show${name}FSSProgress(true)'/>
    </div>
</div>

<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
<script type="text/javascript">
    var theIs${name}FSSJournalInitialized = false;
    var the${name}FSSJournal = new msh.widget.Dialog($('${name}FSSJournal'));
    var the${name}FSSProgress = new msh.widget.Dialog($('${name}FSSProgress'));

    //Пользователь подтверждает правильность персональных данных пациента
    function show${name}FSSProgress(the${name}confirmPersonalData) {

        if (true !==the${name}confirmPersonalData || confirm("Вы подтверждаете отправку ЭЛН с проверенными данными пациента?")) {
            DisabilityService.getIfDisDocHasVK('${documentId}', {
                callback: function (resultVK) {
                    if ($('anotherLpu').value != '' || resultVK || $('issueDate').value != '' && $('hospitalizedTo').value != ''
                        && $('issueDate').value == $('hospitalizedTo').value != ''
                        || $('issueDate').value == '' || $('hospitalizedTo').value == '') {
                        $('${name}FSSProgressResultDiv').innerHTML = "Подождите, идет отправка больничного листа на сервер";
                        the${name}FSSProgress.show();
                        DisabilityService.exportDisabilityDocument('${documentId}', the${name}confirmPersonalData, {
                            callback: function (a) { //возвращается html страница
                                $('${name}FSSProgressResultDiv').innerHTML = a;
                                if (a.indexOf("Подтверждение данных застрахованного") !== -1) {
                                    $('${name}confirmPersonalDataDiv').style = 'display: block';
                                } else {
                                    $('${name}confirmPersonalDataDiv').style = 'display: none';
                                }
                            }
                        });
                    } else showToastMessage('Проверьте дату выдачи и дату госпитализации! Они непустые, следовательно, должны совпадать!', null, true);
                }
            });
        } else {
            cancel${name}FSSProgress();
        }
    }

    // Показать
    function show${name}FSSJournal() {
        DisabilityService.getExportJournalById('${documentId}', {
            callback: function (res) {
                if (res != null) {
                    $('${name}FSSJournalText').innerHTML = res;
                } else {
                    $('${name}FSSJournalText').innerHTML = "ДАННЫЕ ПО ЭКСПОРТУ НЕ НАЙДЕНЫ";
                }
            }
        });

        the${name}FSSJournal.show();
    }

    // Отмена
    function cancel${name}FSSJournal() {
        the${name}FSSJournal.hide();
        msh.effect.FadeEffect.pushFadeAll();
    }

    function cancel${name}FSSProgress() {
        the${name}FSSProgress.hide();
        msh.effect.FadeEffect.pushFadeAll();
    }
</script>