<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_entryListForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:ifFormTypeIsView formName="e2_entryListForm">
        <select id="replaceSelect">
            <option value="SERVICESTREAM">Поток обслуживания</option>
            <option value="SNILS_DOCTOR">СНИЛС лечащего врача</option>
        </select>
        <input type="text" name="replaceFrom" id="replaceFrom" placeholder="Заменить с">
        <input type="text" name="replaceTo" id="replaceTo" placeholder="Заменить на">
        <input type="button" id="replaceClick" value="Заменить" onclick="replaceValue(this)">
        <tags:E2Bill name="E2"/>
        <tags:E2ImportFile name="ImportFile"/>
            <tags:E2UnionListEntry name="Union"/>
        </msh:ifFormTypeIsView>
        <msh:form action="/entitySaveGoView-e2_entryList.do" defaultField="name" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="isClosed"/>
            <msh:hidden property="isDeleted"/>
            <msh:hidden property="lpuOmcCode"/>
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
               <msh:row>
                   <msh:textField property="name" size="100"/>
                   <msh:checkBox property="isDraft"/>
                </msh:row><msh:row>
                   <msh:textField property="startDate"/>
                   <msh:textField property="finishDate"/>
            </msh:row>
                <msh:row>
                    <msh:textField property="checkDate" viewOnlyField="true"/>
                    <msh:textField property="checkTime" viewOnlyField="true"/>
                </msh:row>
                <msh:row>
                   <msh:autoComplete property="entryType" vocName="vocE2ListEntryType" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="createEmptyEntryList" />
                </msh:row>
                <msh:row>
                    <msh:textArea property="historyNumbers" fieldColSpan="5"/>
                </msh:row>

                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
                <msh:ifFormTypeIsView formName="e2_entryListForm">

                <ecom:webQuery name="entries" nameFldSql="entries_sql" nativeSql="select '${param.id}&entryType='||e.entryType||'&billDate='||
                    coalesce(''||to_char(e.billDate,'dd.MM.yyyy'),'')||'&billNumber='||coalesce(e.billNumber,'') ||'&serviceStream='||e.serviceStream||'&isForeign='||case when e.isForeign='1' then '1' else '0' end as id
                ,e.entryType as f2
                ,e.billDate as f3
                ,e.billNumber||max(case when vocbill.id is not null then ' ('||vocbill.name||')' else '' end ) as f4
                ,count(*) as f5_cnt
                ,count(case when e.isDefect='1' then e.id else null end) as f6_cntDefect
                ,e.serviceStream as f7
                ,case when e.isForeign='1' then 'ИНОГ' else '' end as f8_isFor
                 from e2entry e
                 left join e2bill bill on bill.id=e.bill_id
                 left join voce2billstatus vocbill on vocbill.id=bill.status_id
                where e.listentry_id =${param.id} and (e.isDeleted is null or e.isDeleted='0')
                group by e.entryType, e.billDate, e.billNumber ,e.serviceStream, e.isForeign
                 order by e.entryType, e.serviceStream, e.billDate, e.billNumber  "/>

                <msh:table idField="1" name="entries" action="entityParentList-e2_entry.do"  noDataMessage="Нет записей по заполнению" >
                    <msh:tableColumn columnName="Тип записи" property="2"/>
                    <msh:tableColumn columnName="иногородние" property="8"/>
                    <msh:tableColumn columnName="Источник финансирования" property="7"/>
                    <msh:tableColumn columnName="Дата счета" property="3"/>
                    <msh:tableColumn columnName="Номер счета" property="4"/>
                    <msh:tableColumn columnName="Количество записей" property="5"/>
                    <msh:tableColumn columnName="Количество дефектов" property="6" addParam="&defect=1"/>
                    <msh:tableButton property="1" buttonShortName="Присвоить счет" buttonFunction="showE2BillDialog" addParam="this" role="/Policy/E2/Admin"  />
                    <msh:tableButton property="1" buttonShortName="Сформировать пакет" buttonFunction="createMPFile" addParam="this" role="/Policy/E2/Admin" />
                    <msh:tableButton property="1" buttonShortName="Проверить случаи по записи" buttonFunction="makeCheck" addParam="this" role="/Policy/E2/Admin" />

                </msh:table>
                </msh:ifFormTypeIsView>

                <div id ='myGrid'></div>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_entryListForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
        <ecom:webQuery name="isClosedList" nativeSql="select id from e2listentry where id=${param.id} and (isClosed is null or isClosed='0')"/>
            <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_entryList" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentList-e2_entry" name="Записи" roles="/Policy/E2/View" />
                <msh:sideLink params="id" action="/e2_errorsByList" name="Список ошибок" roles="/Policy/E2/View" />
                <msh:sideLink action="/javascript:showExportPaketHistory" name="Просмотреть выгруженные пакеты" roles="/Policy/E2/View" />
                <msh:tableNotEmpty name="isClosedList" guid="8fbc57e0-234a-426d-be88-632a2f5e1f69">
                    <msh:sideLink action="/javascript:makeCheck(null,this)" name="Проверить все случаи" roles="/Policy/E2/View" />
                    <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-e2_entryList" name="Удалить" roles="/Policy/E2/Delete" />
                    <msh:sideLink action="/javascript:showImportFileBillDialog()" name="Импортировать ответ фонда" roles="/Policy/E2/Edit" />
                    <msh:sideLink action="/javascript:exportDefectNewListEntry()" name="Выгрузить дефекты в новое заполнение" roles="/Policy/E2/Edit" />
                    <msh:sideLink action="/javascript:markAsReSend(false)" name="Пометить как первую подачу" roles="/Policy/E2/Edit" />
                    <msh:sideLink action="/javascript:markAsReSend(true)" name="Пометить как повторную подачу" roles="/Policy/E2/Edit" />
                    <msh:sideLink action="/javascript:closeListEntry(true)" name="Закрыть заполнение" roles="/Policy/E2/Admin" />
                    <msh:sideLink action="/javascript:addHistoryNumberToList()" name="Добавить в заполнение госпитализацию" roles="/Policy/E2/Edit" />
                    <msh:sideLink action="/javascript:showUnionUnionDialog()" name="Объединить с другим заполнением" roles="/Policy/E2/Admin" />
                    <msh:sideLink action="/javascript:showUnionExportHistory()" name="Журнал пакетов по счетам" roles="/Policy/E2/Edit" />
                    <msh:sideLink action="/javascript:refillListEntry()" name="Переформировать заполнение" roles="/Policy/E2/Admin" />
                    <msh:sideLink action="/javascript:setDirectAndPlanHospDate()" name="Заполнить пустые даты направления и даты пред. госпитализации" roles="/Policy/E2/Admin" />
                    <msh:sideLink action="/javascript:showSplitForeignOtherBill()" name="Выделить 08,05" roles="/Policy/E2/Admin" />
                </msh:tableNotEmpty>
                <msh:sideLink action="/javascript:closeListEntry(false)" name="Открыть заполнение" roles="/Policy/E2/Admin" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_entryListForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">

                function testNewTable() {
                    var colunms = [
                        {headerName:"id", field:"id"},
                        {headerName:"name", field:"name"},

                    ];
                    var data = JSON.parse('${entries_sql_json}');
                    var eGridDiv = document.querySelector('#myGrid');
                    new agGrid.Grid(eGridDiv, {colunmDef:colunms,rowData:data});
                }





                    var monitor = {};
                    function showSplitForeignOtherBill(){
                        jQuery('#E2Save').click(function(){splitForeignOtherBill();});
                        showE2BillDialog('');

                    }
                    function splitForeignOtherBill() {
                        var billDate=jQuery('#E2BillDate').val();
                        var billNumber=jQuery('#E2BillNumber').val();

                        //String aBillNumber, String aBillDate, String aTerritories
                        Expert2Service.splitForeignOtherBill(${param.id},billNumber, billDate,null,{
                            callback: function(ret){
                                ret = JSON.parse(ret);
                                showToastMessage("Изменено "+ret.count+" записей");
                                window.document.location.reload();
                            }
                        });
                    }
                function refillListEntry() {
                    if (confirm('Вы действительно хотите пересчитать заполнение?')) {
                        Expert2Service.refillListEntry($('id').value, {
                            callback: function () {
                                jQuery.toast({text:'Заполнение пересчитано!',icon:'Info', hideAfter: false });
                            }
                        });
                        jQuery.toast('Пересчет заполнения запущен!');
                    }
                }
                function addHistoryNumberToList() {
                    var list = prompt("Введите номера ИБ");
                    if (list) {
                        Expert2Service.addHospitalMedCaseToList(list,${param.id}, {
                           callback: function() {
                               alert('Добавлено!');
                           }
                        });
                    }
                }
                function markAsReSend(aReSend) {
                    Expert2Service.markAsReSend(${param.id}, aReSend ,{
                       callback: function() {alert('Выполнено!');}
                    });
                }

                function exportDefectNewListEntry() {
                    Expert2Service.exportDefectNewListEntry(${param.id},{
                        callback: function (a) {alert(a);}
                    });
                }

                function replaceValue(btn) {
                    btn.disabled=true;
                    var fld = $('replaceSelect').value;
                    if (fld &&$('replaceTo').value) {
                        Expert2Service.replaceFieldByError(${param.id},null,fld, $('replaceFrom').value, $('replaceTo').value, {
                            callback: function (a) {
                                alert(a);
                                window.document.location.reload();
                            }
                        });
                    } else {
                        alert ('Выберите поле для замены и на что менять');
                    }
                }
                function closeListEntry(aClose) {
                    Expert2Service.closeListEntry(${param.id}, aClose,{callback: function(){alert(aClose?"Закрыто!":"Открыто");window.document.location.reload()}});
                }
                function makeCheck(params, button){
                    if (isRun) {
                        alert('Проверка уже запущена, подождите!');
                        return;
                    }
                 //   var oldVal =button.value;
                    button.value="Подождите...";
                    button.disabled=true;
                    isRun=true;
                    var recalcKsg=false;
                    if (confirm('Пересчитать КСГ для случаев с уже найденным КСГ?')) { recalcKsg=true;}
                 //   if (button) {el.parentNode.removeChild(el);} //удалим элемент чтоб 2 раза не нажимали
                    Expert2Service.checkListEntry(${param.id},recalcKsg,params, {
                        callback: function(monitorId) {
                            monitor.id=monitorId;
                            jQuery.toast("Проверка запущена");
                            //isRun=false;
                            //button.disabled=false;
                            //button.value=oldVal;
                            updateStatus();
                        }
                    });
                }

                var isRun=false;
                function createMPFile(data, button) {
                    if (isRun) {
                        alert('Формирование уже идет, подождите!');
                        return;
                    }
                    isRun=true;
                    button.disabled=true;


                    var a = data.split("&");
                    var type=a[1].split("=")[1];
                    var billNumber=a[3].split("=")[1];
                    var billDate=a[2].split("=")[1];
                    var useAllListEntry = confirm("Формировать файл по счету по всем заполнениям?");
                    var ver = "3.1.1";
               //     if (confirm("Формировать в версии 3.1.1 ?")) {ver="3.1.1";}
                    Expert2Service.makeMPFIle(${param.id},type,billNumber,billDate, null,useAllListEntry,ver,{
                        callback: function(monitorId) {
                            monitor.id=monitorId;
                            jQuery.toast("Формирование файла запущено");
                            updateStatus();
                        }
                    });

                }
                    var statusToast;
                //останавливаем проверку
                function stopCheckAndMonitor() {
                    if (monitor.id) {
                        if (confirm("Желаете прервать проверку?")) {
                            RemoteMonitorService.cancel(monitor.id, {
                                callback: function () {
                                   if (!statusToast) {
                                           statusToast =jQuery.toast({
                                               heading:""
                                               ,text:""
                                               ,icon:"info"
                                               ,hideAfter:false
                                           });
                                       }
                                    statusToast.update({text:"Формирование чето-то прервано!"});
                                   isRun=false;
                                }
                            });
                        }
                    }
                }
                function updateStatus() {
                    var id=monitor.id;
                    if (id){ //Если есть действующий монитор
                        if (statusToast) {
                        } else {
                            statusToast =jQuery.toast({
                                heading:"Формирование"
                                ,text:"Идет расчет..."
                                ,icon:"info"
                                ,hideAfter:false
                            });
                        }
                        RemoteMonitorService.getMonitorStatus(id, {
                            callback: function(aStatus) {
                                var txt;
                                if (aStatus.finish) {
                                 txt="Завеpшено!";
                                 if (aStatus.finishedParameters) {
                                     txt+=" <a href='"+aStatus.finishedParameters+"'>ПЕРЕЙТИ</a>";
                                 }
                                 monitor = {};
                                    isRun=false;
                                } else {
                                    txt=aStatus.text;
                                    txt+="<a href='javascript:stopCheckAndMonitor()'>ПРЕРВАТЬ ФОРМИРОВАНИЕ</a>";
                                    setTimeout(updateStatus,4000) ;
                                }
                                statusToast.update({
                                    text:txt
                                });
                            },
                            errorHandler:function(message) {
                                setTimeout(updateStatus,4000) ;
                            },
                            warningHandler:function(message) {
                                setTimeout(updateStatus,4000) ;
                            }
                        });
                    }

                }

                function setDirectAndPlanHospDate() {
                    Expert2Service.fillDirectDatePlanHosp(${param.id}, {
                       callback: function(){
                           jQuery.toast("Проставление даты направления и даты пред. госпитализации закончено");
                       }
                    });
                    jQuery.toast("Подождите...");
                }
            </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

