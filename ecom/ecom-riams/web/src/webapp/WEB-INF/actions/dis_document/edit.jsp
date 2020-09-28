<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%
    String username = LoginInfo.find(request.getSession()).getUsername();
    request.setAttribute("username", username);
%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="style" type="string">
        <style type="text/css">

            #prolongation td._9, #prolongation td._8, #prolongation td._7,
            #prolongation td._11 input, #prolongation td._10 input,
            #closeTable td._3, #closeTable td._4, #closeTable td._2 input {
                font: bold 30px/1.5 cursive;
                text-align: center;
            }
        </style>
    </tiles:put>
    <tiles:put name="body" type="string">
        <!--
        - Документ нетрудоспособности
        -->
        <msh:form action="/entityParentSaveGoView-dis_document.do" defaultField="documentTypeName">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="disabilityCase" />
            <msh:panel>
                <msh:label property="exportStatus" fieldColSpan="4"/>
                <msh:row>
                    <msh:autoComplete vocName="mainLpu" property="anotherLpu" label="Другое лечебное учреждение" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocDisabilityDocumentType" property="documentType" label="Документ" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="status" fieldColSpan="3" horizontalFill="true" label="Статус документа" vocName="vocDisabilityStatus" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete viewAction="entityView-dis_document.do"
                                      shortViewAction="entityShortView-dis_document.do" viewOnlyField="true"
                                      vocName="disabilityDocumentByCase" property="duplicate"
                                      label="Заменен на документ" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:textField passwordEnabled="false" hideLabel="false" property="issueDate" viewOnlyField="false" horizontalFill="false" />
                    <msh:autoComplete vocName="vocDisabilityDocumentPrimarity" property="primarity" label="Первичность" horizontalFill="true" size="20" />
                </msh:row>
                <msh:row>
                    <msh:textField property="beginWorkDate" viewOnlyField="true" horizontalFill="false" />
                </msh:row>
                <msh:row>
                    <msh:textField property="series" label="Серия" />
                    <msh:textField property="number" label="Номер"  size="20" fieldColSpan="30" />
                    <msh:ifFormTypeIsCreate formName="dis_documentForm">
                        <td><input id="getFreeNumberButton" type="button" onclick="getFreeNumber('number',this)" value="Получить номер"></td>
                        <msh:checkBox property="ELN" label="Электронный"/>
                    </msh:ifFormTypeIsCreate>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="disabilityDocumentByCase" property="prevDocument" label="Предыдущий документ" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="job" fieldColSpan="3" label="Место работы" horizontalFill="true"/>
                </msh:row>
                <msh:ifFormTypeIsNotView formName="dis_documentForm">
                    <msh:row>
                        <msh:textField property="otherNumber" fieldColSpan="3" horizontalFill="true"
                                       label="Место рабо (29 сим.)" viewOnlyField="true"/>

                    </msh:row>
                    <msh:row>
                        <msh:checkBox property="isUpdateWork" fieldColSpan="3" horizontalFill="Обновить сокращенное название организации"/>
                    </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:ifFormTypeIsCreate formName="dis_documentForm">
                    <msh:row>
                        <msh:separator label="Период нетрудоспособности" colSpan="4" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="dateFrom" label="Дата начала" />
                        <msh:textField property="dateTo" label="Дата окончания" />
                    </msh:row>
                    <msh:ifFormTypeIsNotView formName="dis_documentForm">
                        <msh:row>
                            <msh:textField property="info" labelColSpan="2" fieldColSpan="2" horizontalFill="true"
                                           label="Количество дней нетрудоспособности" viewOnlyField="true"/>

                        </msh:row>
                    </msh:ifFormTypeIsNotView>

                    <msh:row>
                        <msh:autoComplete vocName="vocDisabilityRegime" property="regime" label="Режим" fieldColSpan="3" horizontalFill="true" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Леч.врач" fieldColSpan="3" horizontalFill="true" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunctionAdd" viewOnlyField="false" label="Председ. ВК" fieldColSpan="3" horizontalFill="true" />
                    </msh:row>
                </msh:ifFormTypeIsCreate>



                <msh:row>
                    <msh:separator label="Совместительство" colSpan="4" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocCombo" property="workComboType" label="Тип совместительства" horizontalFill="true" fieldColSpan="3" />
                </msh:row>
                <msh:row>
                    <msh:textField property="mainWorkDocumentSeries" label="Серия по основному месту работы" />
                    <msh:textField property="mainWorkDocumentNumber" label="номер" />
                </msh:row>
                <msh:row>
                    <msh:separator label="Причина нетрудоспособности" colSpan="4" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocDisabilityReason" property="disabilityReason" label="Причина нетруд." fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocDisabilityReason2" property="disabilityReason2" label="Доп. причина нетруд." fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocDisabilityReason" property="disabilityReasonChange" label="Изм. причины нетруд." fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocIdc10" property="idc10" label="Диагноз первич." fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocIdc10" property="idc10Final" label="Диагноз заключ." fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:separator label="Госпитацизация" colSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="hospitalizedFrom" label="Дата начала госпит."/>
                    <msh:textField property="hospitalizedTo" label="Дата окончания"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="hospitalizedNumber" label="№ истории болезни" fieldColSpan="3" />
                </msh:row>
                <msh:row>
                    <msh:separator label="Санаторное лечение" colSpan="4" />
                </msh:row>
                <msh:row>
                    <msh:textField property="sanatoriumDateFrom" label="Дата начала (пред.родов)" />
                    <msh:textField property="sanatoriumDateTo" label="Дата окончания" />
                </msh:row>
                <msh:row>
                    <msh:textField property="sanatoriumTicketNumber" label="Номер путевки" />
                </msh:row>
                <msh:row>
                    <msh:textField property="sanatoriumPlace" label="Место нахождения санатория" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:textField property="sanatoriumOgrn" label="ОГРН санатория или клиники НИИ" fieldColSpan="2" horizontalFill="true"/>
                </msh:row>

                <msh:row>
                    <msh:separator label="Закрытие" colSpan="4" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocDisabilityDocumentCloseReason" property="closeReason" label="Причина закрытия" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="otherCloseDate" label="Иная дата закрытия для причин 32, 33, 34, 36" labelColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox hideLabel="false" property="noActuality" viewOnlyField="true" horizontalFill="false" label="Испорчен" />

                    <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Reopen">
                        <msh:ifFormTypeIsNotView formName="dis_documentForm">
                            <msh:checkBox property="isClose" label="Документ закрыт"  />
                        </msh:ifFormTypeIsNotView>
                    </msh:ifInRole>
                    <%--<msh:ifFormTypeIsView formName="dis_documentForm">
                        <msh:checkBox property="isClose" label="Документ закрыт"/>
                    </msh:ifFormTypeIsView>--%>
                    <msh:checkBox property="isClose" label="Документ закрыт"/>

                    <msh:ifFormTypeIsView formName="dis_documentForm">
                        <ecom:webQuery name="list" nativeSql="select
            dd.id
            ,case when dd.isclose=true and dd.closeexport=false then dd.id else null end as signadd
            ,case when ds.id is not null then '&#9989;' else '&#x2716;' end as sig
            ,case when dd.closeexport=false then '&#x2716;' else '&#9989;' end as export
            ,case when ds.id is not null then 'close' else null end as delclose
            from disabilitydocument dd
            left join disabilitysign ds on dd.id = ds.disabilitydocumentid_id and ds.noactual = false and ds.code = 'close'
            where dd.id =  ${param.id}"/>
                        <msh:table guid="closeTable" name="list" action="entityParentView-dis_record.do" idField="1">
                            <msh:tableButton role="/Policy/Mis/Disability/ElectronicDisability/Doc" hideIfEmpty="true" property="2" addParam="'close'" buttonFunction="javascript:showJournalfssSign" buttonName="Подписать" buttonShortName="&#9997;"/>
                            <msh:tableColumn role="/Policy/Mis/Disability/ElectronicDisability/Doc" columnName="Подпись" property="3"/>
                            <msh:tableColumn columnName="Выгружен" property="4"/>
                            <msh:tableButton role="/Policy/Mis/Disability/ElectronicDisability/Doc" hideIfEmpty="true" property="5" buttonName="Удалить подпись закрытия" buttonFunction="javascript:deleteSign" buttonShortName="close"/>
                        </msh:table>
                    </msh:ifFormTypeIsView>

                </msh:row>
                <msh:row>
                    <msh:separator label="Дополнительная информация" colSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
                    <msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="editDate" label="Дата редактирования" viewOnlyField="true"/>
                    <msh:textField property="editUsername" label="Пользователь" viewOnlyField="true"/>
                </msh:row>
                <msh:row>
                    <msh:submitCancelButtonsRow colSpan="3" />
                </msh:row>
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="dis_documentForm">
            <ecom:webQuery name="duplicateDocument" nativeSql="select dd.id,dd.series|| ' '||dd.number from disabilitydocument dd where dd.duplicate_id=${param.id }"/>
            <msh:section title="Данный документ заменил документ">
                <msh:table name="duplicateDocument"
                           action="entityView-dis_document.do"
                           viewUrl="entityShortView-dis_document.do"
                           hideTitle="true" idField="1">
                    <msh:tableColumn property="2" columnName="номер"/>
                </msh:table>
            </msh:section>
            <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/Record/View">
                <div id="block">

                    <msh:section title="Продление"
                                 createRoles="/Policy/Mis/Disability/Case/Document/Record/Create" createUrl="entityParentPrepareCreate-dis_record.do?id=${param.id}">
                        <ecom:webQuery name="list" nativeSql="select distinct(dr.id),dr.datefrom,dr.dateto,case when dr.workfunction_id is not null then p.lastname||' '||p.firstname||' '||p.middlename||'<br>'||vwf.name else dr.docname||'<br>'||dr.docrole end as doc,
                    case when dr.workfunctionadd_id is not null then pvk.lastname||' '||pvk.firstname||' '||pvk.middlename||'<br>'|| vwfvk.name else dr.vkname||'<br>'||dr.vkrole end as vk
                    ,vr.name as regime
                    ,case when dr.isexport is null or dr.isexport='0' then '&#x2716;' else '&#9989;' end as export
                    ,case when dsdoc.id is not null then '&#9989;' else '&#x2716;' end as docsig
                    ,case when pvk.id is not null then case when dsvk.id is not null then '&#9989;' else '&#x2716;' end else '' end as vksig
                    ,case when dr.isexport is null or dr.isexport='0' then case when dr.workfunctionadd_id is not null then dr.id else null end else null end as vksignadd
                    ,case when dr.isexport is null or dr.isexport='0' then case when dr.workfunction_id is not null then dr.id else null end else null end as docsignadd
                    ,case when dsdoc.id is not null then 'doc'||'#'||dr.id else null end as deldoc
                    ,case when dsvk.id is not null then 'vk'||'#'||dr.id else null end as delvk
                    from disabilityrecord dr
                    left join workfunction wfdoc on wfdoc.id =  dr.workfunction_id
                    left join vocworkfunction  vwf on vwf.id = wfdoc.workfunction_id
                    left join worker w on w.id = wfdoc.worker_id
                    left join patient p on p.id = w.person_id
                    left join workfunction wfvk on wfvk.id =  dr.workfunctionadd_id
                    left join vocworkfunction  vwfvk on vwfvk.id = wfvk.workfunction_id
                    left join worker wvk on wvk.id = wfvk.worker_id
                    left join patient pvk on pvk.id = wvk.person_id
                    left join VocDisabilityRegime vr on vr.id = dr.regime_id
                    left join disabilitysign dsdoc on dsdoc.disabilitydocumentid_id = ${param.id} and dsdoc.externalid = dr.id and dsdoc.code = 'doc'
                    left join disabilitysign dsvk on dsvk.disabilitydocumentid_id = ${param.id} and dsvk.externalid = dr.id and dsvk.code = 'vk'
                    where disabilitydocument_id = ${param.id} order by dr.datefrom"/>
                        <msh:table guid="prolongation" name="list" action="entityParentView-dis_record.do" idField="1">
                            <msh:tableColumn columnName="Дата начала" property="2"/>
                            <msh:tableColumn columnName="Дата окончания" property="3"/>
                            <msh:tableColumn columnName="Леч.врач" property="4"/>
                            <msh:tableButton role="/Policy/Mis/Disability/ElectronicDisability/Doc" hideIfEmpty="true" property="11" addParam="'doc'" buttonFunction="javascript:showJournalfssSign" buttonName="Подписать врачом" buttonShortName="&#9997;"/>
                            <msh:tableColumn role="/Policy/Mis/Disability/ElectronicDisability/Doc" columnName="Подпись" property="8"/>
                            <msh:tableColumn columnName="Председ. ВК" property="5"/>
                            <msh:tableButton role="/Policy/Mis/Disability/ElectronicDisability/Vk" hideIfEmpty="true" property="10" addParam="'vk'" buttonFunction="javascript:showJournalfssSign" buttonName="Подписать ВК" buttonShortName="&#9997;"/>
                            <msh:tableColumn role="/Policy/Mis/Disability/ElectronicDisability/Vk" columnName="Подпись" property="9"/>
                            <msh:tableColumn columnName="Режим" property="6"/>
                            <msh:tableColumn columnName="Выгружен" property="7"/>
                            <msh:tableButton role="/Policy/Mis/Disability/ElectronicDisability/Doc" hideIfEmpty="true" property="12" buttonName="Удалить подпись врача" buttonFunction="javascript:deleteSign" buttonShortName="doc"/>
                            <msh:tableButton role="/Policy/Mis/Disability/ElectronicDisability/Vk" hideIfEmpty="true" property="13" buttonName="Удалить подпись ВК" buttonFunction="javascript:deleteSign"  buttonShortName="vk"/>
                        </msh:table>
                    </msh:section>
                    <!-- score data here -->
                </div>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/View">
                <msh:section createRoles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create"
                             createUrl="entityParentPrepareCreate-dis_regimeViolation.do?id=${param.id}"
                             shortList="entityParentShortList-dis_regimeViolation.do?id=${param.id}"
                             title="Нарушение режима">
                    <ecom:parentEntityListAll formName="dis_regimeViolationForm" attribute="violation" />
                    <msh:table viewUrl="entityShortView-dis_regimeViolation.do" idField="id" name="violation" action="entityParentView-dis_regimeViolation.do">
                        <msh:tableColumn columnName="Дата начала" property="dateFrom" />
                        <msh:tableColumn columnName="Дата окончания" property="dateTo" />
                        <msh:tableColumn columnName="Комментарий" property="comment" />
                    </msh:table>
                </msh:section>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/View">
                <msh:section title="Медико-социальная экспертная комиссия">
                    <ecom:parentEntityListAll formName="dis_medSocCommissionForm" attribute="medSoc" />
                    <msh:table viewUrl="entityShortView-dis_medSocCommission.do" idField="id" name="medSoc" action="entityParentView-dis_medSocCommission.do">
                        <msh:tableColumn columnName="Дата направления" property="assignmentDate" />
                        <msh:tableColumn columnName="Дата регистрации" property="registrationDate" />
                        <msh:tableColumn columnName="Дата освидетельствования" property="examinationDate" />
                    </msh:table>
                </msh:section>
            </msh:ifInRole>
            <tags:fssJournal name="Journal" documentId="${param.id}"/>
            <tags:fssExport name="Export" documentId="${param.id}"/>
            <tags:fssSign name="Journal" username="${username}"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Disability" beginForm="dis_documentForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
        <script type="text/javascript">

            function unattachEln() {
                DisabilityService.unattachEln(${param.id},{
                    callback: function(aString) {
                        showToastMessage(aString,null,true);
                        jQuery("#mainForm").load("entityParentView-dis_document.do?id=${param.id} #mainForm");
                    }});
            }

            function updateEln() {
                DisabilityService.updateEln(${param.id},{
                    callback: function(res) {
                        if (res==true) {
                            alert("ЭЛН был обновлён");
                            window.location.reload();
                        }
                        else
                            showToastMessage('Не удалось обновить ЭЛН!',null,true);
                    }});
            }

            function printDoc(aTemplate) {
                DisabilityService.getPrefixForLN({
                    callback: function(aResult) {
                        document.location.href = "print-disability_"+aTemplate+aResult+".do?s=DisabilityService&m=printDocument&id=${param.id}";
                    }
                }) ;
            }

            //Milamesher 13112018 удаление подписей
            function deleteSign(code) {
                var drId=null;
                if (code.indexOf('close')==-1) {
                    var mas = code.split('#');
                    code=mas[0];
                    drId=mas[1];
                }
                DisabilityService.deleteSign(drId,${param.id},code,{
                    callback: function(aString) {
                        showToastMessage(aString,null,true);
                        if (aString.length<=8) window.location.reload();
                    }});
            }
        </script>
        <msh:ifFormTypeIsView formName="dis_documentForm">
            <script type="text/javascript">
                if (+$('isClose').checked==true) {
                    $('ALT_3').style.display = 'none' ;
                    $('ALT_7').style.display = 'none' ;
                } else {
                    $('ALT_6').style.display = 'none' ;
                }
            </script>
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeIsView formName="dis_documentForm">
            <script type="text/javascript">
                function getFreeNumber (aField, aButton){
                    if (""+aField=="") {
                        aField="number";
                    }
                    if ($(aField).value!="") {
                        alert ("Поле \"Номер\" уже заполнено");
                        return;
                    }
                    aButton.value="Подождите...";
                    aButton.disabled=true;
                    DisabilityService.getFreeNumberForDisabilityDocument({
                        callback: function (num) {
                            if (num!=null&&num!="") {
                                $(aField).value=num;
                                $(aField).className="viewOnly";
                                $(aField).disabled=true;
                                aButton.style.display="none";
                                var a = document.getElementById('ELN');
                                a.checked=true;
                            } else {
                                alert ("Не удалось получить номер больничного листа");
                            }

                        }
                    });
                }
            </script>
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeIsCreate formName="dis_documentForm">
            <script type="text/javascript">
                function getFreeNumber (aField, aButton){
                    if (""+aField=="") {
                        aField="number";
                    }
                    if ($(aField).value!="") {
                        alert ("Поле \"Номер\" уже заполнено");
                        return;
                    }
                    DisabilityService.getFreeNumberForDisabilityDocument({
                        callback: function (num) {
                            if (num!=null&&num!="") {
                                $(aField).value=num;
                                $(aField).className="viewOnly";
                                aButton.style.display="none";
                                var a = document.getElementById('ELN');
                                a.checked=true;
                            } else {
                                alert ("Не удалось получить номер больничного листа");
                            }

                        }
                    });
                }
            </script>
        </msh:ifFormTypeIsCreate>
        <msh:ifFormTypeIsNotView formName="dis_documentForm">
            <script type="text/javascript">

                prevDocumentAutocomplete.setParentId($('disabilityCase').value) ;
                closeReasonAutocomplete.addOnChangeCallback(function() {
                    DisabilityService.getCodeByReasonClose($('closeReason').value,{
                        callback: function(aString) {
                            if (aString!=null&&aString!=""&&(aString=="32" || aString=="33"||aString=="34"||aString=="36")) {
                                DisabilityService.getMaxDateToByDisDocument($('id').value,{
                                    callback: function(aString1) {
                                        if (aString1!=null&&aString1!=""&&aString1!="null") {
                                            $('otherCloseDate').value=aString1 ;
                                        } else {
                                            $('otherCloseDate').value=$('hospitalizedTo').value ;;
                                        }
                                    }
                                });
                                $('otherCloseDate').className="required";
                            } else {
                                // alert('null');
                                $('otherCloseDate').className="";
                                $('otherCloseDate').value="";
                                document.getElementById('isClose').checked = '';
                                document.getElementById('beginWorkDateReadOnly').value = '';
                            }
                        }
                    })
                });
                idc10Autocomplete.addOnChangeCallback(function() {
                    if ($('idc10Final').value==""){
                        $('idc10Final').value = $('idc10').value ;
                        $('idc10FinalName').value = $('idc10Name').value ;
                    }
                });
                eventutil.addEventListener($('job'), eventutil.EVENT_KEY_DOWN,
                    function() {
                        setJob() ;
                    }) ;
                eventutil.addEventListener($('job'), eventutil.EVENT_KEY_UP,
                    function() {
                        setJob() ;
                    }) ;
                eventutil.addEventListener($('job'), "change",
                    function() {
                        setJob() ;
                    }) ;

                function setJob() {
                    $('otherNumberReadOnly').value=$('job').value.substring(0,29).toUpperCase() ;
                }
                setJob() ;


                eventutil.addEventListener($('dateFrom'), eventutil.EVENT_KEY_DOWN,
                    function() {
                        setPeriod() ;
                    }) ;
                eventutil.addEventListener($('dateFrom'), eventutil.EVENT_KEY_UP,
                    function() {
                        setPeriod() ;
                    }) ;
                eventutil.addEventListener($('dateFrom'), "change",
                    function() {
                        setPeriod() ;
                    }) ;
                eventutil.addEventListener($('dateFrom'), "blur",
                    function() {
                        setPeriod() ;
                    }) ;
                eventutil.addEventListener($('dateTo'), eventutil.EVENT_KEY_DOWN,
                    function() {
                        setPeriod() ;
                    }) ;
                eventutil.addEventListener($('dateTo'), eventutil.EVENT_KEY_UP,
                    function() {
                        setPeriod() ;
                    }) ;
                eventutil.addEventListener($('dateTo'), "change",
                    function() {
                        setPeriod() ;
                    }) ;
                eventutil.addEventListener($('dateTo'), "blur",
                    function() {
                        setPeriod() ;
                    }) ;
                function setPeriod() {
                    try {
                        if ($('dateFrom').value.length==10 &&  $('dateTo').value.length==10) {
                            var dateTo = new Date($('dateTo').value.replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1')+" 12:12:12 GMT +0300") ;
                            var dateFrom = new Date($('dateFrom').value.replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1')+" 12:12:12 GMT +0300") ;
                            var one_day=1000*60*60*24 ;
                            $('infoReadOnly').value=1+((dateTo.getTime()-dateFrom.getTime())/one_day) ;
                        }
                    } catch(e) {
                        //alert('222') ;
                    }
                }
                setPeriod();
            </script>
        </msh:ifFormTypeIsNotView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="dis_documentForm">
            <msh:sideMenu title="Документ нетрудоспобности">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_document" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_document" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Delete" />
                <msh:sideLink confirm="Действительно?" params="id" action="/javascript:unattachEln()" name="Отвязать ЭЛН" roles="/Policy/Mis/Disability/ElectronicDisability/UnattachEln"/>
                <msh:sideLink key="ALT+9" action="/javascript:showtoAnotherDisCase()" name="Перенести в другой СНТ" roles="/Policy/Mis/Disability/Case/Document/Edit" />
                <tags:closeDisDocument reason="closeReason"
                                       roles="/Policy/Mis/Disability/Case/Document/Edit" key="ALT+3"
                                       name="doc" title="Закрыть" otherCloseDate="otherCloseDate"
                                       confirm="Вы действительно хотите закрыть текущий документ нетрудоспособности?"
                                       seria='series' number='number' />
                <tags:dis_duplicateDocument roles="/Policy/Mis/Disability/Case/Document/Create" key="ALT+4"
                                            name="duplicate" title="Дубликат (испорчен)" confirm="Вы действительно хотите создать дубликат текущего документа нетрудоспособности?" />
                <tags:dis_workComboDocument roles="/Policy/Mis/Disability/Case/Document/Create" key="ALT+5"
                                            name="workCombo" title="Бланк по совместительству" confirm="Вы действительно хотите создать документ по совместительству на основе текущего документа нетрудоспособности?" />
                <msh:sideLink confirm="Действительно?" params="id" action="/javascript:updateEln()" name="Обновить ЭЛН" roles="/Policy/Mis/Disability/ElectronicDisability/UpdateEln"/>
                <tags:toAnotherDisCase name="toAnotherDisCase"  documentId="${param.id}"/>
            </msh:sideMenu>
            <msh:sideMenu title="Печать">
                <msh:sideLink  name="шаблон 1" key="ALT+6" action="/javascript:printDoc(1,'.do')"/>
                <msh:sideLink  name="НЕЗАКРЫТЫЙ шаблон 1" key="ALT+7" action="/javascript:printDoc(1,'.do')"/>
                <msh:sideLink  name="шаблон 2" key="ALT+8"  action="/javascript:printDoc(2,'.do')"/>
                <msh:sideLink  name="шаблон 3" action="/javascript:printDoc(3,'.do')"/>
                <msh:sideLink  name="шаблон 4" action="/javascript:printDoc(4,'.do')"/>
                <msh:sideLink  name="шаблон МВД" action="/javascript:printDoc(5,'.do')"/>
                <msh:sideLink  name="шаблон справки 095" action="/javascript:printDoc('reference','.do')"/>
            </msh:sideMenu>
            <msh:sideMenu title="Добавить">
                <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_record" roles="/Policy/Mis/Disability/Case/Document/Record/Create" name="Продление" title="Продлить листок нетрудоспособности" key="CTRL+1" />
                <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_regimeViolation" roles="/Policy/Mis/Disability/Case/Document/RegimeViolationRecord/Create" name="Нарушение режима" title="Добавить запись о нарушении режима" key="CTRL+2" />
                <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_medSocCommission" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Create" name="МСЭК" title="Добавить решение медико-социальной экспертной комиссии" key="CTRL+3" />
            </msh:sideMenu>
            <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/ExportDocument">
                <msh:sideMenu  title="Экспорт в ФСС">
                    <msh:sideLink  name="Экспортировать документ" action="/javascript:showJournalFSSProgress()"/>
                    <msh:sideLink  name="Просмотреть журнал экспорта" action="/javascript:showJournalFSSJournal()"/>
                    <msh:sideLink roles="/Policy/Mis/Disability/ElectronicDisability/Doc" name="Экспорт документа" action="/javascript:showExportfssExport()"/>
                </msh:sideMenu>
            </msh:ifInRole>
            <msh:ifInRole roles="/Policy/Mis/Disability/Case/Document/AnnulSheet">
                <tags:annulDisSheetReason name="annulDisSheetReason" />
                <msh:sideMenu  title="Аннулирование ЛН">
                    <msh:sideLink  name="Аннулировать документ" action="/javascript:showannulDisSheetReasonCloseElectronicDocument()"/>
                </msh:sideMenu>
            </msh:ifInRole>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>