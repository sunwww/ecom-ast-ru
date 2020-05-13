<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <msh:ifInRole roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet">
        <tiles:put name='title' type='string'>
            <msh:title mainMenu="Expert2">ЕДКЦ</msh:title>
        </tiles:put>

        <tiles:put name='body' type='string'>
            <h2>Работа с пациентами</h2>
            <div>
                <form action="/javascript:void()">
                    <msh:panel>
                        <msh:row>
                            <msh:autoComplete showId="false" vocName="patient" property="patient" viewOnlyField="false"  label="Персона"
                                              size="50" viewAction="entityView-mis_patient.do" fieldColSpan="2"/>
                            <td align="right" width="1px"><div id="personButton" style="margin-left:5px"></div></td>
                        </msh:row>
                    </msh:panel>
                    <tags:mis_patient_new divForButton="personButton" name='Person' title='Создание новой персоны' autoComplitePatient="patient"/>
                    <br>
                    <h2>Работа с листами наблюдения</h2>
                        <tags:observSheet name="observSheet"/>
                        <tags:vocObservRes name="vocObservRes"/>
                        <msh:sideLink action="/javascript:openObservSheetJs()" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                                      name="Открыть ЛН" title="Открыть ЛН"
                        />
                        <msh:sideLink action="/javascript:showobservSheetJs()" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                                      name="Просмотреть ЛН" title="Просмотреть все ЛН"
                        />
                        <msh:sideLink action="/javascript:showvocObservResJs()" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                                      name="Закрыть ЛН" title="Закрыть текущий ЛН"
                        />
                        <msh:sideLink action="/javascript:consultProtocolJs('edkc_1')" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                                      name="Протокол консультации" title="Протокол консультации"
                        />
                        <msh:sideLink action="/javascript:consultProtocolJs('edkc_ev')" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                                      name="Протокол ежесуточного наблюдения" title="Протокол ежесуточного наблюдения"
                        />
                    <h2>Отчёты</h2>
                    <msh:sideLink action="/riams_edkc_patientList.do" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet"
                                  name="Отчёт по пациентам" title="Отчёт по пациентам"
                    />
                </form>
            </div>
        </tiles:put>
        <tiles:put name="javascript" type="string">
            <script type="text/javascript">
                $('patient').value='${param.patient}';
                $('patientName').value='${param.patientName}';
                if ('${param.close}'!='')
                    showToastMessage('Лист наблюдения закрыт.',null,true);
                if ('${param.open}'!='')
                    showToastMessage('Лист наблюдения открыт.',null,true);
                initPersonPatientDialog();
                function openObservSheetJs() {
                    if ($('patient').value!='')
                        openObservSheet($('patient').value);
                    else
                        showToastMessage('Необходимо выбрать пациента!',null,true);
                }
                function showobservSheetJs() {
                    if ($('patient').value!='')
                        showobservSheet($('patient').value);
                    else
                        showToastMessage('Необходимо выбрать пациента!',null,true);
                }
                function showvocObservResJs() {
                    if ($('patient').value!='')
                        showvocObservRes($('patient').value,'/riams/riams_edkc.do?close=1');
                    else
                        showToastMessage('Необходимо выбрать пациента!',null,true);
                }
                function consultProtocolJs(code) {
                    if ($('patient').value!='') {
                        PatientService.getObservationSheetOpenedId($('patient').value, {
                            callback: function(id) {
                                if (id!=0)
                                    window.location.href='entityParentPrepareCreate-edkcProtocol.do?id='+id+'&type='+code;
                                else
                                    showToastMessage('Не удалось найти открытый лист наблюдения!',null,true);
                            }
                        });
                    }
                    else
                        showToastMessage('Необходимо выбрать пациента!',null,true);
                }
                //в завимисости от наличия/отсутствия и статуса листка наблюдения скрывает/показывает кнопки
                function updateObservButtons(status) {
                    //0 - нет никаких, можно открыть
                    //1 - есть открытый: можно просмотреть, закрыть, добавить всё
                    //2 - есть закрытый: можно просмотреть, открыть
                    var аMas = document.getElementsByTagName('a');
                    for (var i=0; i<аMas.length; i++) {
                        if (аMas[i].innerHTML=='Открыть ЛН') {
                            if (status==0 || status==2)
                                аMas[i].parentNode.removeAttribute('hidden');
                            else
                                аMas[i].parentNode.setAttribute('hidden',true);
                        }
                        else if (аMas[i].innerHTML=='Просмотреть ЛН') {
                            if (status==1 || status==2)
                                аMas[i].parentNode.removeAttribute('hidden');
                            else
                                аMas[i].parentNode.setAttribute('hidden',true);
                        }
                        else if (аMas[i].innerHTML=='Закрыть ЛН' || аMas[i].innerHTML=='Протокол ежесуточного наблюдения' || аMas[i].innerHTML=='Протокол консультации') {
                            if (status==1)
                                аMas[i].parentNode.removeAttribute('hidden');
                            else
                                аMas[i].parentNode.setAttribute('hidden',true);
                        }
                    }
                }
                //получает статус листка наблюдения
                function checkObservStatus() {
                    if ($('patient').value!='')
                        PatientService.getObservationSheetStatus($('patient').value, {
                            callback: function(status) {
                                updateObservButtons(status);
                            }
                        });
                    else
                        updateObservButtons(4);
                }
                checkObservStatus();
                //открыть лист наблюдения
                function openObservSheet(aPatId) {
                    PatientService.openObservSheet(aPatId, {
                        callback: function(res) {
                            if (res=='1')
                                window.location.href='/riams/riams_edkc.do?patient='+aPatId+'&patientName='+$('patientName').value+'&open=1';
                            else
                                showToastMessage('Уже есть открытый лист наблюдения, нельзя открыть ещё один!',null,true);
                        }
                    });
                }
                patientAutocomplete.addOnChangeCallback(function() {
                    checkObservStatus();
                });
            </script>
        </tiles:put>
    </msh:ifInRole>
</tiles:insert>
