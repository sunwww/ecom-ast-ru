<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_entryListForm" />

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:ifFormTypeIsView formName="e2_entryListForm">
        <select id="replaceSelect">
            <option value="SERVICESTREAM">Поток обслуживания</option>
            <option value="SNILS_DOCTOR">СНИЛС лечащего врача</option>
            <option value="SNILS_REPLACE_STRING">СНИЛСы из настроек</option>
        </select>
        <input type="text" name="replaceFrom" id="replaceFrom" placeholder="Заменить с">
        <input type="text" name="replaceTo" id="replaceTo" placeholder="Заменить на">
        <input type="button" id="replaceClick" value="Заменить" onclick="replaceValue(this)">
        <tags:E2Bill name="E2"/>
        <tags:E2DefaultCancerl name="Cancer" listId="${param.id}"/>
        <tags:E2ImportFile name="ImportFile"/>
            <tags:E2UnionListEntry name="Union"/>
        </msh:ifFormTypeIsView>
        <msh:form action="/entitySaveGoView-e2_entryList.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="isClosed"/>
            <msh:hidden property="isDeleted"/>
            <msh:hidden property="lpuOmcCode"/>
            <msh:hidden property="monitorId"/>
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
                <msh:ifFormTypeIsCreate formName="e2_entryListForm">
                <msh:row>
                    <msh:checkBox property="createEmptyEntryList" />
                </msh:row>
                <msh:row>
                    <msh:textArea property="historyNumbers" fieldColSpan="5"/>
                </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:submitCancelButtonsRow colSpan="4" />
                <msh:ifFormTypeIsView formName="e2_entryListForm">

                <ecom:webQuery name="entries" nameFldSql="entries_sql" nativeSql="select '${param.id}&entryType='||e.entryType||'&billDate='||
                    coalesce(''||to_char(e.billDate,'dd.MM.yyyy'),'')||'&billNumber='||coalesce(e.billNumber,'') ||'&serviceStream='||e.serviceStream
                    ||'&isForeign='||case when e.isForeign='1' then '1' else '0' end||'&billComment='||coalesce(bill.comment,'')||'&fileType='||coalesce(e.fileType,'') as id
                ,e.entryType as f2
                ,e.billDate as f3
                ,e.billNumber||max(case when vocbill.id is not null then ' ('||vocbill.name||')' else '' end ) as f4
                ,count(*) as f5_cnt
                ,count(case when e.isDefect='1' then e.id else null end) as f6_cntDefect
                ,e.serviceStream as f7
                ,case when e.isForeign='1' then 'ИНОГ' else '' end as f8_isFor
                ,bill.comment as f9_billComment
                ,coalesce(e.fileType,'') as f10_fileType
                ,e.bill_Id||''','''||to_char(le.startDate,'dd.MM.yyyy')||' - '||to_char(le.finishDate,'dd.MM.yyyy')||''','''||split_part(bill.billnumber,'/',2)||'' as f11_printBill
                 from e2listEntry le
                 left join e2entry e on e.listentry_id=le.id
                 left join e2bill bill on bill.id=e.bill_id
                 left join voce2billstatus vocbill on vocbill.id=bill.status_id
                where le.id =${param.id} and (e.isDeleted is null or e.isDeleted='0')
                group by e.entryType, e.billDate, e.billNumber ,e.serviceStream, e.isForeign,e.bill_id,bill.comment, e.fileType, le.startDate , le.finishDate, bill.billNumber,bill.sum
                 order by e.entryType, e.serviceStream, e.billDate, e.billNumber  "/>

                <msh:table idField="1" name="entries" action="entityParentList-e2_entry.do"  noDataMessage="Нет записей по заполнению" >
                    <msh:tableColumn columnName="Тип записи" property="2"/>
                    <msh:tableColumn columnName="Тип файла" property="10"/>
                    <msh:tableColumn columnName="иногородние" property="8"/>
                    <msh:tableColumn columnName="Источник финансирования" property="7"/>
                    <msh:tableColumn columnName="Дата счета" property="3"/>
                    <msh:tableColumn columnName="Номер счета" property="4"/>
                    <msh:tableButton property="11" hideIfEmpty="true" buttonShortName="©" buttonFunction="printBill"/>
                    <msh:tableButton property="11" hideIfEmpty="true" buttonShortName="©_" buttonFunction="printReestr"/>
                    <msh:tableColumn columnName="Примечание" property="9"/>
                    <msh:tableColumn columnName="записей" property="5"/>
                    <msh:tableColumn columnName="дефектов" property="6" addParam="&defect=1"/>
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
        <msh:ifFormTypeIsView formName="e2_entryListForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_entryList" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentList-e2_entry" name="Записи" roles="/Policy/E2/View" />
                <msh:sideLink params="id" action="/e2_errorsByList" name="Список ошибок" roles="/Policy/E2/View" />
                <msh:sideLink action="/javascript:showExportPaketHistory" name="Просмотреть выгруженные пакеты" roles="/Policy/E2/View" />
                <msh:ifPropertyIsTrue formName="e2_entryListForm" propertyName="isClosed" invert="true">
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
                    <msh:sideLink action="/javascript:showSplitForeignOtherBill()" name="Выделить иногородних отдельным счетом" roles="/Policy/E2/Admin" />
                    <msh:sideLink action="/javascript:exportToCentralSegment()" name="Сделать запрос в ЦС" roles="/Policy/E2/Edit" />
                    <msh:sideLink action="/javascript:showCancerCancerDialog()" name="Сделать онкослучай" roles="/Policy/E2/Admin" />
                </msh:ifPropertyIsTrue>
                <msh:sideLink action="/javascript:closeListEntry(false)" name="Открыть заполнение" roles="/Policy/E2/Admin" />
                <msh:sideLink action="/javascript:deleteAllDeletedEntries()" name="Удалить удаленные случаи" roles="/Policy/E2/Admin" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_entryListForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                var monitor = {};
                checkIsRunning();

                function printBill(id,period,insCode) {
                    window.location.href = "print-omc_bill_"+insCode+".do?billId="+id+"&s=OmcPrintService&m=printOmcBill"+
                    "&billPeriod="+period;
                }
                function printReestr(id,period,insCode) {
                    window.location.href = "print-omc_reestr_"+insCode+".do?billId="+id+"&s=OmcPrintService&m=printOmcBill"+
                    "&entry=1&billPeriod="+period;
                }
                function deleteAllDeletedEntries() {
                    Expert2Service.deleteAllDeletedEntries();
                }
                function checkIsRunning () {
                    if (+$('monitorId').value>0) {
                        monitor.id = +$('monitorId').value;
                        updateStatus();
                    }
                }

                function exportToCentralSegment() {
                    var histories = prompt("Ведите номера историй");
                        Expert2Service.exportToCentralSegment(${param.id},histories, {
                            callback: function (fileName) {
                                showToastMessage("<a href='/rtf/expert2xml/"+fileName+"'>Скачать файл </a>");
                            }
                        });
                }

                function testNewTable() {
                    var colunms = [
                        {headerName:"id", field:"id"},
                        {headerName:"name", field:"name"},

                    ];
                    var data = JSON.parse('${entries_sql_json}');
                    var eGridDiv = document.querySelector('#myGrid');
                    new agGrid.Grid(eGridDiv, {colunmDef:colunms,rowData:data});
                }


                    function showSplitForeignOtherBill(){
                        jQuery('#E2Save').click(function(){splitForeignOtherBill();});
                        showE2BillDialog('');

                    }
                    function splitForeignOtherBill() {
                        var billDate=jQuery('#E2BillDate').val();
                        var billNumber=jQuery('#E2BillNumber').val();

                        //String aBillNumber, String aBillDate, String aTerritories
                        var terr = prompt("Введите территории (например 05,08)","08");
                        if (terr) {
                        Expert2Service.splitForeignOtherBill(${param.id},billNumber, billDate,terr,{
                            callback: function(ret){
                                ret = JSON.parse(ret);
                                showToastMessage("Изменено "+ret.count+" записей");
                                window.document.location.reload();
                            }
                        });
                        }
                    }
                function refillListEntry() {
                    if (confirm('Вы действительно хотите пересчитать заполнение?')) {
                        Expert2Service.refillListEntry($('id').value, {
                            callback: function (monitorId) {
                                monitor.id=monitorId;
                                updateStatus();
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
                    if (fld && $('replaceTo').value) {
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
                    var ver = "3.2";
                    var fileType=a[7].split("=")[1];
               //     if (confirm("2020?")) ver = "3.2";
                    Expert2Service.makeMPFIle(${param.id},type,billNumber,billDate, null,useAllListEntry,ver,
                        fileType,{
                        callback: function(monitorId) {
                            monitor ={};
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

        <msh:ifFormTypeIsCreate formName="e2_entryListForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                Expert2Service.getDefaultLpuOmcCode({
                    callback: function (code) {
                        $('lpuOmcCode').value=code;
                    }
                });
            </script>

        </msh:ifFormTypeIsCreate>
    </tiles:put>
</tiles:insert>

