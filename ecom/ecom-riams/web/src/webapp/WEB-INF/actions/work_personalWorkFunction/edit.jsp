<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoView-work_personalWorkFunction.do" defaultField="workFunctionName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpuRegister"/>
            <msh:hidden property="worker"/>
            <msh:hidden property="allGroups"/>
            <msh:panel>
                <msh:row>
                    <msh:textField property="code" horizontalFill="true" fieldColSpan="3" label="Код"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete viewAction="entityView-voc_workFunction.do" vocName="vocWorkFunction"
                                      property="workFunction" fieldColSpan="3" label="Название функции" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocAcademicDegree" property="degrees" fieldColSpan="1"
                                      label="Учёная степень" size="20"/>
                    <msh:autoComplete vocName="vocCategory" property="category" fieldColSpan="1" label="Категория"
                                      size="20"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="groupWorkFunction" property="group" fieldColSpan="3"
                                      label="Групповая рабочая функция" size="50"
                                      viewAction="entityParentView-work_groupWorkFunction.do"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete viewAction="userView.do" vocName="secUser" property="secUser"
                                      label="Вход в систему" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="registrationInterval" label="Интервал регистр." horizontalFill="true"/>
                    <msh:checkBox property="archival" label="Архив"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocAttorney" property="attorney" label="Использует доверенность"
                                      fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isSurgical" label="Оперирующий"/>
                    <msh:checkBox property="isInstrumentNurse" label="Опер.сестра"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isAdministrator" label="Начальник"/>
                    <msh:checkBox property="emergency" label="Экстр. пункт"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isRotation" label="Совместитель"/>
                    <msh:checkBox property="isNoP7Sync" label="Не синхронизировать с \"Парус-7\""/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isNoDirectSelf" label="Запрет на создание направление к самому себе"
                                  fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isDirectionNoTime"
                                  label="Разрешить создавать направления без указания времени" fieldColSpan="3"
                                  horizontalFill="true"/>
                </msh:row>
                <msh:ifFormTypeIsCreate formName="work_personalWorkFunctionForm">
                    <msh:row>
                        <msh:checkBox property="isCalendarCreate" label="Создавать календарь"/>
                    </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:row>
                    <msh:checkBox property="isNoViewRemoteUser"/>
                    <msh:autoComplete property="copyingEquipmentDefault" vocName="copyingEquipment"
                                      horizontalFill="true" label="Принтер"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="kkmEquipmentDefault" vocName="kkmEquipment"
                                      horizontalFill="true" label="ККМ"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="cabinet" label="Кабинет" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textArea property="comment" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:separator label="Электронная очередь" colSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="queue" vocName="vocTicketQueue" fieldColSpan="2" size="100"
                                      label="Тип очереди"/>
                    <msh:textField property="windowNumber" label="Номер окна"/>
                </msh:row>
                <msh:row>
                    <msh:separator label="Дополнительная информация" colSpan="4"/>
                </msh:row>
                <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/View">
                    <msh:row>
                        <msh:label property="promedCodeWorkstaff" label="promedCodeWorkstaff"/>
                        <msh:label property="promedCodeLpuSection" label="promedCodeLpuSection"/>
                    </msh:row>
                </msh:ifInRole>
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
                <msh:submitCancelButtonsRow colSpan="2" functionSubmit="save();"/>
            </msh:panel>
        </msh:form>

        <msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
            <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/View">
                <msh:section title="Календарь">
                    <ecom:parentEntityListAll formName="cal_workCalendarForm" attribute="childs"/>
                    <msh:table viewUrl="entityShortView-cal_workCalendar.do" name="childs"
                               action="entityParentView-cal_workCalendar.do" idField="id">
                        <msh:tableColumn columnName="ИД" property="id"/>
                    </msh:table>
                </msh:section>
            </msh:ifInRole>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Lpu" beginForm="work_personalWorkFunctionForm"/>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
            <msh:sideMenu title="Рабочая функция">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-work_personalWorkFunction" name="Изменить"
                              roles="/Policy/Mis/Worker/WorkFunction/Create"/>
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id"
                              action="/entityParentDelete-work_personalWorkFunction" name="Удалить"
                              roles="/Policy/Mis/Worker/WorkFunction/Delete"/>
                <msh:sideLink action="/javascript:generationCalendar('.do')" name="Генерировать"
                              roles="/Policy/Mis/Worker/WorkCalendar/View"/>
                <msh:sideLink action="/javascript:updatePromedCodes('.do')" name="Обновить коды Промеда"
                              roles="/Policy/Mis/Worker/WorkFunction/UpdatePromedCodes"/>
                <msh:sideLink action="/javascript:createSecUser('.do')" name="Добавить пользователя"
                              roles="/Policy/Jaas/SecUser/Create" styleId="createSecUser"/>
            </msh:sideMenu>
            <msh:sideMenu title="Добавить">
                <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Create" key="ALT+3" params="id"
                              action="/entityParentPrepareCreate-cal_workCalendar" name="Календарь"
                              title="Добавить календарь"/>
            </msh:sideMenu>
            <msh:sideMenu title="Показать"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
        <msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
            <script type="text/javascript">
                function generationCalendar() {
                    WorkCalendarService.generateBySpecialist(
                        ${param.id},
                        {
                            callback: function (aTime) {
                                alert(aTime);
                            }
                            , errorHandler: function (aMessage) {
                                alert(aMessage);
                            }
                        });
                }

                function updatePromedCodes() {
                    WorkCalendarService.updatePromedCodes(
                        ${param.id},
                        {
                            callback: function (res) {
                                alert(res);
                            }
                        });
                }

                function createSecUser() {
                    window.location = 'entityPrepareCreate-secuser.do?workFunction=' + $('id').value + "&tmp=" + Math.random();
                }

                if (+$('secUser').value > 0) $('createSecUser').style.display = 'none';
            </script>
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeIsNotView formName="work_personalWorkFunctionForm">
            <script type="text/javascript">
                var groupII = 0;  //кол-во групповых рабочих функций
                groupAutocomplete.setParentId(getWorkerAndFunction());
                workFunctionAutocomplete.addOnChangeCallback(function () {
                    updateGroup();
                });

                function updateGroup() {
                    groupAutocomplete.setParentId(getWorkerAndFunction());
                    groupAutocomplete.setVocId('');
                }

                function getWorkerAndFunction() {
                    return $('workFunction').value + "#" + $('worker').value;
                }

                <msh:ifFormTypeIsCreate formName="work_personalWorkFunctionForm">
                //кнопка для добавления нескольких групповых фнукций
                jQuery(document).ready(function () {
                    var groupDiv = jQuery('#groupDiv');
                    groupDiv.detach();
                    jQuery('#groupName').parent().append("<input style='margin-left:10px' type='button' onclick='addAnotherGroup();' value='+'>");
                    jQuery('#groupName').parent().append(groupDiv);
                });

                //возможность добавить несколько групповых рабочих функций
                function addAnotherGroup() {
                    var voc = 'groupWorkFunction';
                    var html = "<div><input type=\"hidden\" size=\"1\" name=\"" + voc + "\" id=\"" + voc +
                        +groupII + "\" value=\"\"><input title=\"" + voc + groupII + "\" type=\"text\" name=\"" + voc +
                        +groupII + "Name\" id=\"nnn" + voc + groupII + "Name\" size=\"70\" class=\"autocomplete horizontalFill\" " +
                        "autocomplete=\"off\"><div id=\"" + voc + groupII + "Div\" style=\"visibility: hidden; display: none;\" " +
                        "class=\"autocomplete\"></div></div>";
                    jQuery('#groupName').parent().parent().append(html);
                    eval("var " + voc + groupII + "Autocomplete = new msh_autocomplete.Autocomplete()");
                    eval(voc + groupII + "Autocomplete.setUrl('simpleVocAutocomplete/" + voc + "') ");
                    eval(voc + groupII + "Autocomplete.setIdFieldId('" + voc + groupII + "') ");
                    eval(voc + groupII + "Autocomplete.setNameFieldId('nnn" + voc + groupII + "Name') ");
                    eval(voc + groupII + "Autocomplete.setDivId('" + voc + groupII + "Div') ");
                    eval(voc + groupII + "Autocomplete.setVocKey('" + voc + "') ");
                    eval(voc + groupII + "Autocomplete.setVocTitle('Групповая рабочая функция')");
                    eval(voc + groupII + "Autocomplete.build() ");
                    eval(voc + groupII + "Autocomplete.setParentId(getWorkerAndFunction()) ");
                    groupII++;
                }

                </msh:ifFormTypeIsCreate>
                //сохранение
                function save() {
                    <msh:ifFormTypeIsCreate formName="work_personalWorkFunctionForm">
                    $('allGroups').value = "";
                    var mas = {
                        list: []
                    };
                    var voc = 'groupWorkFunction';

                    var totalTd = jQuery('#groupName').parent().parent();
                    jQuery('#groupName').parent().parent().find('input[id*="nnn' + voc + '"]').each(function (ind, el) {
                        if ($('' + voc + el.id.replace('nnn' + voc, '').replace('Name', '')).value != '') {
                            var obj = {
                                group: $('' + voc + el.id.replace('nnn' + voc, '').replace('Name', '')).value
                            };
                            mas.list.push(obj);
                        }
                    });
                    $('allGroups').value = JSON.stringify(mas);
                    </msh:ifFormTypeIsCreate>
                    document.forms[0].submit();
                }
            </script>
        </msh:ifFormTypeIsNotView>
        <script type="text/javascript">
            function getWfCabinet() {
                WorkCalendarService.getWfCabinet(${param.id}, {
                    callback: function (res) {
                        if ($('cabinet')) $('cabinet').value = res;
                        if ($('cabinetReadOnly')) $('cabinetReadOnly').value = res;
                    }
                });
            }

            getWfCabinet();
        </script>
    </tiles:put>
</tiles:insert>