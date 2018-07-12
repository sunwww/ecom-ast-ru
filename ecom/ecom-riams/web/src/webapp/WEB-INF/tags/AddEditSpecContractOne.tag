<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>

<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/ContractService.js">/**/</script>
<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Направление к специалисту</h2>
    <div>
        <form  id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete viewAction="entityParentView-mis_lpu.do" vocName="mainLpu" property="${name}orderLpu" label="Внешний направитель" horizontalFill="true" fieldColSpan="3" size='1000'/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete showId="false" vocName="vocServiceStream" property="${name}serviceStream" viewOnlyField="false" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" size='1000'/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="${name}workFunctionPlan" label="Специалист" vocName="workFunctionByDirect" horizontalFill="true" size='1000' fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="${name}datePlan" parentAutocomplete="${name}workFunctionPlan" vocName="vocWorkCalendarDayByWorkFunction" label="Направлен на дату" fieldColSpan="1" size='30'/>
                    <msh:autoComplete property="${name}timePlan"  label="Время" vocName="vocWorkCalendarTimeWorkCalendarDay" parentAutocomplete="${name}datePlan" fieldColSpan="2" size='30'/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="${name}workPlaceType" viewOnlyField="false" label="Рабочее место" guid="c61023b1-bf59-432f-8764-8a571b1eddf8" fieldColSpan="3" horizontalFill="true" size='1000' />
                </msh:row>
                <msh:row>
                    <msh:checkBox label="Неотложная помощь" property="${name}emergency" fieldColSpan="3"/>
                </msh:row>
                <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
                    <msh:separator label="Резервы" colSpan="4" guid="314f5445-a630-411f-88cb-16813fefa0d9" />
                </msh:row>
                <msh:row>
                    <td colspan="4">
                        <div id="divReserve"></div>
                    </td>
                </msh:row></table>
                </td><td valign="top"><table>
                <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/PreRecord">
                    <msh:row guid="6898ae03-16fe-46dd-9b8f-8cc25e19913b">
                        <msh:separator label="Предварительная запись" colSpan="4" guid="314f5445-a630-411f-88cb-16813fefa0d9" />
                    </msh:row>
                    <msh:row>
                        <td colspan="4" id="tdPreRecord"></td>
                    </msh:row>
                </msh:ifInRole></table>
                </td></tr></table></td></tr>

                <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/CreateNewTime">
                    <msh:row>
                        <msh:separator label="<a href='javascript:getWorkFunctionByUsername()'>Создание дополнительного времени</a>" colSpan="4"/>
                    </msh:row>
                    <msh:row>
                        <td colspan="4" id="tdCreateNewTime">

                            <div id="workFunctionByUsername">

                            </div>
                        </td>
                    </msh:row>
                </msh:ifInRole>
            </msh:panel>
        </form>
    </div>
    <div class="${name}medservs">
        <div class="left">
            <td><input id="${name}addMs" type="submit" value="+ услуга" onclick='javascript:addMedServiceAutoComplete${name}(false)'/></td>
        </div>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td></td>
            </tr>
            <tr>
                <td align="center"><input type="button" value='В ДОГОВОР' id="${name}Add" onclick='javascript:putToContract${name}()'/></td>
                <td align="center"><input type="button"  style="font-weight:bold" value='Отмена' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
            </tr>
        </table>
    </div>
</div>
</div>
<script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
<script type="text/javascript">
    var ${name}prLID;
    var theIs${name}CloseDisDocumentDialogInitialized = false ;
    var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
    var visits = new Array();
    var NUM=1;

    var firstMs;
    // Показать
    function show${name}(id) {
        if (id!=null && id!='') {
            ${name}prLID=id;
            theTableArrow = null ;
            the${name}CloseDisDocumentDialog.show();
            ${name}workFunctionPlanAutocomplete.addOnChangeCallback(function(){
                updateDefaultDate() ;
                deleteAllMs();
            }) ;
            ${name}datePlanAutocomplete.addOnChangeCallback(function(){
                if ($('${name}timePlan')) {
                    $('${name}timePlan').value='';
                }
                if ($('${name}timePlanName')) {
                    $('${name}timePlanName').value='';
                }
                setMsParentId();
                getPreRecord() ;
            }) ;
            ${name}serviceStreamAutocomplete.addOnChangeCallback(function() {
                updateTime() ;
            });
            //обнуление
            ('${name}orderLpu').value=0 ;
            $('${name}orderLpuName').value = "";
            ('${name}workFunctionPlan').value=0 ;
            $('${name}workFunctionPlanName').value = "";
            ('${name}datePlan').value=0 ;
            $('${name}datePlanName').value = "";
            $('${name}timePlan').value=0 ;
            $('${name}timePlanName').value = "";
            $('${name}serviceStream').value=6;
            $('${name}serviceStreamName').value='ПЛАТНЫЙ';
            $('${name}workPlaceType').value=1;
            $('${name}workPlaceTypeName').value='поликлиника';
            document.getElementById("${name}emergency").checked=false;
            deleteAllMs();
            //заливка
            document.getElementById("${name}workFunctionPlanName").style.backgroundColor = '#fcffa7';
            document.getElementById("${name}datePlanName").style.backgroundColor = '#fcffa7';
            document.getElementById("${name}timePlanName").style.backgroundColor = '#fcffa7';
            document.getElementById("${name}workPlaceTypeName").style.backgroundColor = '#fcffa7';
            document.getElementById("${name}serviceStreamName").style.backgroundColor = '#fcffa7';
            addMedServiceAutoComplete${name}(true);
        }
        else alert("Выберите прейскурант!");
    }
    //удаление всех услуг
    function deleteAllMs() {
        for (var i=2; i<NUM; i++)
            if (document.getElementById("${name}div"+i)!=null)
                document.querySelector('#'+"${name}div"+i).remove();
        //первую обнулить
        if (document.getElementById("${name}div1")!=null) {
            document.querySelector('#'+"AddEditSpecContractOnepriceMedServicies1").value='';
            document.querySelector('#'+"AddEditSpecContractOnepriceMedServiciesName1").value='';
        }
    }
    //проставить parentId в первой услуге
    function setMsParentId() {
        if (document.getElementById("${name}workFunctionPlan").value!==null && document.getElementById("${name}workFunctionPlanName").value!==''
            && document.getElementById("${name}datePlan").value!=='' && document.getElementById("${name}datePlanName").value!==''
            && document.getElementById("${name}div1")!=null && firstMs!=null)
            firstMs.setParentId(document.getElementById("${name}workFunctionPlan").value+"#"+document.getElementById("${name}datePlanName").value+"#"+${name}prLID) ;
    }
    // Отмена
    function cancel${name}() {
        the${name}CloseDisDocumentDialog.hide() ;
    }
    //Добавление услуги (если заполнены поля по визиту либо это первая услуга)
    function addMedServiceAutoComplete${name}(flag) {
        if (checkAllVisitFields() || flag) {
            var str='<div id="${name}div'+NUM+'">' +
                '<input onclick="deleteMedServiceAutoComplete${name}(jQuery(this).parent().attr(\'id\'))" id="ms_' + NUM + '" type="submit" class="del" value="-" style="margin:5px"/>'+
                '<input type="hidden" size="1" name="${name}priceMedServicies'+NUM+'" value="" id="${name}priceMedServicies'+NUM+'">' +
                '<input title="medServiceSpecPriceList" type="text" name="${name}priceMedServiciesName'+NUM+'" value="" id="${name}priceMedServiciesName'+NUM+
                '" size="1000" class="autocomplete horizontalFill maxHorizontalSize" autocomplete="off">' +
                'Кол-во: <input type="text" maxlength="10" size="10" id="${name}num'+NUM+'" pattern="/[0-9]+/" value="1"/>'+
                '<div id="${name}priceMedServiciesDiv'+NUM+'">' + '<span></span></div>' +
                '</div>';
            jQuery(str).appendTo('.${name}medservs');
            var t = new msh_autocomplete.Autocomplete();
            t.setUrl('simpleVocAutocomplete/medServiceForSpecPriceList');
            t.setIdFieldId('${name}priceMedServicies' + NUM);
            t.setNameFieldId('${name}priceMedServiciesName' + NUM);
            t.setDivId('${name}priceMedServiciesDiv' + NUM);
            t.build();
            if (!flag)
                t.setParentId(document.getElementById("${name}workFunctionPlan").value+"#"+document.getElementById("${name}datePlanName").value+"#"+${name}prLID) ;
            else {
                t.setParentId("-1#-1#-1"); firstMs=t;
            } //чтобы выводилось '-', а не 500
            NUM++;
        }
        else alert("Перед добавлением услуг заполните поток, специалиста, дату, время, рабочее место!");
    }
    //Удаление блока с услугой
    function deleteMedServiceAutoComplete${name}(id) {
        document.querySelector('#'+id).remove();
    }
    //установка первой подходящей даты
    function updateDefaultDate() {
        WorkCalendarService.getDefaultDate($('${name}workFunctionPlan').value, {
                callback:function(aDateDefault) {
                    if (aDateDefault!=null) {
                        var calDayId, calDayInfo,ind1 ;
                        ind1 = aDateDefault.indexOf("#") ;
                        calDayInfo = aDateDefault.substring(0,ind1) ;
                        calDayId = aDateDefault.substring(ind1+1) ;

                        $('${name}datePlan').value=calDayId ;
                        $('${name}datePlanName').value = calDayInfo;
                        getPreRecord();
                    } else {
                        $('${name}datePlan').value=0 ;
                        $('${name}datePlanName').value = "";
                        getPreRecord();
                    }
                    setMsParentId();
                    getWorkFunctionByUsername() ;
                }
            }
        ) ;
        $('${name}timePlan').value="0" ;
        $('${name}timePlanName').value = "";
    }
    //отправить в договор
    function putToContract${name}() {
        if (checkAllVisitFields()) {
            var visit= {
                wf: document.getElementById("${name}workFunctionPlan").value,
                orderLpuId:document.getElementById("${name}orderLpu").value,
                spec: document.getElementById("${name}workFunctionPlanName").value,
                date: $('${name}datePlan').value, time: $('${name}timePlan').value,
                datetime: document.getElementById("${name}datePlanName").value+" "+document.getElementById("${name}timePlanName").value,
                sstream: document.getElementById("${name}serviceStreamName").value,
                sstreamId: document.getElementById("${name}serviceStream").value,
                wplace: document.getElementById("${name}workPlaceTypeName").value,
                wplaceId: document.getElementById("${name}workPlaceType").value,
                help: (+document.getElementById("${name}emergency").checked),
                servs: new Array(),
                dateddmmyyy:document.getElementById("${name}datePlanName").value
            };
            for (var i=1; i<NUM; i++) {
                if (document.getElementById("${name}priceMedServicies"+i)!=null && document.getElementById("${name}num"+i)!=null
                    && document.getElementById("${name}priceMedServicies"+i).value!==null && document.getElementById("${name}priceMedServiciesName"+i).value!==''
                    && (+$('${name}num'+i).value)>=1) {
                    var ms= {
                        msid: document.getElementById("${name}priceMedServicies"+i).value,
                        name: document.getElementById("${name}priceMedServiciesName"+i).value,
                        num: document.getElementById("${name}num"+i).value
                    };
                    visit.servs.push(ms);
                }
            }
            if (isVisitUnique(visit)) {
                if (visit.servs.length!=0) {
                    visits.push(visit);
                    //берём только этот визит
                    for (var j=0; j<visit.servs.length; j++) //добавляю услуги из визита в договор
                        addRow(visit.servs[j].msid,visit.servs[j].name,visit.servs[j].num);
                    //и бронирую время для визита
                    WorkCalendarService.setPrePatientBeforeContract(visit.time,document.getElementById("servedPerson").value, {
                        callback:function() {}}) ;
                    if (visits.length==0) alert("Список услуг с направлениями пуст!");
                    else {
                        var t={
                            visits:visits
                        };
                        $('referralsInfo').value=JSON.stringify(t);

                    }
                    cancel${name}();
                }
                else alert("Нельзя создавать визиты без услуг!");
            }
            else alert("Вы уже добавили точно такой же визит! Нельзя создавать 2 абсолютно одинаковых визита.");
        }
        else alert("Необходимо выбрать специалиста, дату, время, поток обслуживания и рабочее место!");
    }
    //Проверка на уникальность визита - нужно проверять БЕЗ услуг, ибо и без услуг это дубль, пусть добавляют в тот же визит
    function isVisitUnique(el) {
        for(var i=0; i<visits.length; i++) {
            var vis=visits[i];
            if (vis.wf===el.wf && vis.orderLpuId===el.orderLpuId && vis.date===el.date && vis.datetime===el.datetime
                && vis.sstreamId===el.sstreamId && vis.wplaceId===el.wplaceId && vis.help===el.help) return false;
        }
        return true;
    }
    //проверка на заполнение всех полей по визиту
    function checkAllVisitFields() {
        return (document.getElementById("${name}workFunctionPlan").value!==null && document.getElementById("${name}workFunctionPlanName").value!==''
            && document.getElementById("${name}datePlan").value!==''  && document.getElementById("${name}timePlan").value!==''
            && document.getElementById("${name}datePlanName").value!==''  && document.getElementById("${name}timePlanName").value!==''
            && document.getElementById("${name}workPlaceType").value!=='' && document.getElementById("${name}serviceStream").value!==''
            && document.getElementById("${name}workPlaceTypeName").value!=='' && document.getElementById("${name}serviceStreamName").value!=='');
    }


    function updateTime() {
        if (+$('${name}datePlan').value>0 && +$('${name}serviceStream').value>0) {
            WorkCalendarService.getReserveByDateAndService($('${name}datePlan').value,$('${name}serviceStream').value,0
                , {
                    callback: function(aResult) {
                        //alert(aResult) ;
                        $('divReserve').innerHTML = aResult ;
                    }
                }
            );
        }
    }
    function getPreRecord() {
        if ($('tdPreRecord')) {

            if (+$('${name}datePlan').value>0) {
                WorkCalendarService.getPreRecord($('${name}datePlan').value,
                    {
                        callback:function(aResult) {
                            if (aResult!=null) {
                                $('tdPreRecord').innerHTML=aResult;
                            }
                            else {
                                $('tdPreRecord').innerHTML="";
                            }

                            updateTime() ;

                        }
                    }
                ) ;
            } else {
                $('tdPreRecord').innerHTML="";
            }
        } else {
            updateTime() ;
        }
    }
    function checkRecord(aId,aValue,aIdService,aService) {
        $('${name}timePlan').value = aId;
        $('${name}timePlanName').value = aValue ;
        if (+aIdService>0) {
            $('${name}serviceStream').value=aIdService;
            $('${name}serviceStreamName').value=aService;

        }
    }
    function getWorkFunctionByUsername() {
        WorkCalendarService.getWorkFunctionByUsername($('${name}workFunctionPlan').value,
            {
                callback:function(aDateDefault) {
                    try {
                        $("workFunctionByUsername").innerHTML="<a href='javascript:hideNewTime()'>Скрыть</a><br/>"+aDateDefault ;
                    } catch (e) {}
                }
            }) ;
    }
    function hideNewTime() {
        try {$("workFunctionByUsername").innerHTML="" ;} catch (e) {}
    }
    function get10DaysByWorkFunction(aWorkFunction) {
        WorkCalendarService.get10DaysByWorkFunction(aWorkFunction,
            {callback:function(aResult){
                    $('divDayByWorkFunction').innerHTML = aResult ;
                }}) ;
    }
    function getTimeByDayAndWorkFunction(aWorkFunction,aWorkCalendarDay) {
        WorkCalendarService.getTimeByDayAndWorkFunction(aWorkFunction,aWorkCalendarDay,{
            callback:function(aResult) {
                $('divTimeByDayAndWorkFunction').innerHTML = aResult ;
            }
        }) ;
    }
    function addNewTimeBySpecialist(aWorkCalendarDay,aReserve,aDate,aWorkFunction,aTime1,aTime2,aWorkFunctionInfo) {
        WorkCalendarService.addNewTimeBySpecialist(aDate,aReserve,aWorkFunction,aTime1,aTime2,{
            callback:function(aResult) {
                $('divTimeByDayAndWorkFunction').innerHTML = aResult ;
                //getTimeByDayAndWorkFunction(aWorkFunction,aWorkCalendarDay) ;
                hideNewTime() ;
                $('${name}workFunctionPlan').value=aWorkFunction ;
                $('${name}workFunctionPlanName').value=aWorkFunctionInfo ;
                $('${name}datePlan').value=aWorkCalendarDay ;
                $('${name}datePlanName').value=aDate ;
                if (aResult!=null) {
                    $('${name}timePlanName').value =  aResult.substring(aResult.indexOf("#")+1) ; ;
                    $('${name}timePlan').value = aResult.substring(0,aResult.indexOf("#")) ;
                }
                getPreRecord() ;
            }
        }) ;
    }
</script>
<!--lastrelease milameser 2905 #99-->