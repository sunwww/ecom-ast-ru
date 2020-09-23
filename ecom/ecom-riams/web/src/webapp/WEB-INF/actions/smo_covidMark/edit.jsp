<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="style" type="string">
        <style>
            .borderedDiv {
                display: inline-block;
                border: 1px solid;
                width: 70%;
                border-color: rgb(59,92,105) ;
                padding-top:5px;
                padding-bottom:10px;
                padding-left:10px;
            }
        </style>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail title="Форма оценки тяжести COVID-19" mainMenu="MedCase" beginForm="smo_covidMarkForm" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="entityParentSaveGoParentView-smo_covidMark.do" defaultField="id">
            <msh:title>Выберите <u><i>максимальное значение</i></u>, которое наблюдалось за время лечения,
                или пункт "Ничего из этого"
            </msh:title>
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="medCase" />
            <msh:hidden property="changeLungs" />
            <msh:hidden property="chdd" />
            <msh:hidden property="puls" />
            <msh:hidden property="temp" />
            <msh:hidden property="badSostString" />
            <msh:hidden property="isBadSozn" />
            <msh:hidden property="sost" />
            <msh:ifFormTypeIsNotView formName="smo_covidMarkForm">
                <msh:hidden property="createDate" />
                <msh:hidden property="createTime" />
                <msh:hidden property="createUsername" />
                <msh:hidden property="editDate" />
                <msh:hidden property="editTime" />
                <msh:hidden property="editUsername" />
            </msh:ifFormTypeIsNotView>
            <table class="viewOnly">
                <tbody>
                    <tr>
                        <td colspan="1" title="Тяжесть&nbsp;заболевания (sostEl)" class="label">
                            <span style="font-size:20px;" id="sostElLabel" class="viewOnlyLabel">Тяжесть&nbsp;заболевания:</span></td>
                        <td colspan="3" class="sostEl">
                            <span style="font-size:20px;">
                                <input class="viewOnly horizontalFill maxHorizontalSize" id="sostElReadOnly" name="sostElReadOnly" size="100" readonly="readonly">
                                <input id="sostEl" value="3" name="sostEl" type="hidden">
                            </span></td>
                    </tr>
                </tbody>
            </table>
            <br>
            <label><b>Пульсоксиметрия:</b></label><br>
            <div class="borderedDiv" id="pulsDiv"></div>
            <br>
            <label><b>Частота дыхательных движений:</b></label><br>
            <div class="borderedDiv" id="chddDiv"></div>
            <br>

            <label><b>Распространенность изменений в обоих легких по результатам компьютерной томографии лёгких:</b></label><br>
            <div class="borderedDiv" id="changeLurgeDiv"></div>
            <br>

            <label><b>Температура тела:</b></label><br>
            <div class="borderedDiv" id="tempDiv"></div>
            <br>
            
            <label><b>Признаки тяжёлого состояния:</b></label><br>
            <div class="borderedDiv" id="badSostDiv"></div>
            <br>

            <label><b>Нарушение сознания:</b></label><br>
            <div class="borderedDiv" id="badSostDiv" onclick = 'calc();'>
                <input id="isBadSoznChb" name="isBadSoznChb" type="checkbox" autocomplete="off">
                <label id="isBadSoznChbLabel" for="isBadSoznChb">Нарушение&nbsp;сознания</label>
            </div>
            <br>
            <msh:panel>
                <msh:ifFormTypeIsView formName="smo_covidMarkForm">
                    <msh:row>
                        <msh:separator label="Дополнительно" colSpan="4"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="createDate" label="Дата создания"/>
                        <msh:label property="createTime" label="время"/>
                        <msh:label property="createUsername" label="пользователь" />
                    </msh:row>
                    <msh:row>
                        <msh:label property="editDate" label="Дата редак."/>
                        <msh:label property="editTime" label="время"/>
                        <msh:label property="editUsername" label="пользователь" />
                    </msh:row>
                </msh:ifFormTypeIsView>
            </msh:panel>
            <msh:ifFormTypeIsNotView formName="smo_covidMarkForm">
                <msh:submitCancelButtonsRow colSpan="4" functionSubmit="this.disabled=true; save(this) ;"/>
            </msh:ifFormTypeIsNotView>
        </msh:form>
            <tiles:put name="javascript" type="string">
                <script type="text/javascript" src="./dwr/interface/CovidService.js"></script>
                <script type="text/javascript" >
                    //вывести существующий при наличии
                    function showOrCreate() {
                        CovidService.getIdIfAlreadyExists(${param.id}, {
                            callback: function (aResult) {
                                if (aResult != '')
                                    window.location.href = 'entityView-smo_covidMark.do?id=' +aResult;
                            }
                        });
                    }
                    jQuery(document).ready(function() {
                        showOrCreate();
                    });
                    /**
                     * загрузка в div из voc.
                     *
                     * @param voc Справочник-таблица
                     * @param div html элемент
                     * @param type radio/checkbox
                     */
                    function loadDivFromVoc(voc,div,type) {
                        var txt = "";
                        CovidService.getVocInJson(voc, {
                            callback: function (aResult) {
                                var vocRes = JSON.parse(aResult);
                                txt += "<table><tbody>";
                                for (var ind1 = 0; ind1 < vocRes.length; ind1++) {
                                    txt + "<tr>";
                                    var vocVal = vocRes[ind1];
                                    txt += "<td id='td" + voc + vocVal.id + "' colspan=\"1\">";
                                    txt += "<label onclick='checkNothingChb(this); calc(); '><input type='" + type + "' name='" + voc + "' id='" + voc + vocVal.id;
                                    txt += "' value='" + vocVal.code + "'";
                                    txt += ">";
                                    txt += vocVal.name;
                                    txt += "</label></td><tr>";
                                }
                                txt += "</tbody><table>";
                                div.innerHTML += txt;
                                transform();
                                <msh:ifFormTypeAreViewOrEdit formName="smo_covidMarkForm">
                                loadValues();
                                </msh:ifFormTypeAreViewOrEdit>
                            }
                        });
                    }

                    //загрузка всего
                    function loadAll() {
                        jQuery('#pulsDiv').html('');
                        jQuery('#chddDiv').html('');
                        jQuery('#changeLurgeDiv').html('');
                        jQuery('#tempDiv').html('');
                        jQuery('#badSostDiv').html('');
                        loadDivFromVoc('vocPuls', jQuery('#pulsDiv')[0], 'radio');
                        loadDivFromVoc('vocChdd', jQuery('#chddDiv')[0], 'radio');
                        loadDivFromVoc('vocChangeLungs', jQuery('#changeLurgeDiv')[0], 'radio');
                        loadDivFromVoc('vocTemp', jQuery('#tempDiv')[0], 'radio');
                        loadDivFromVoc('vocBadSost', jQuery('#badSostDiv')[0], 'checkbox');
                        //jQuery("#isBadSoznChb").click(function(){ calc(); });
                        <msh:ifFormTypeIsView formName="smo_covidMarkForm">
                        disableAll('#mainForm');
                        </msh:ifFormTypeIsView>
                    }
                    loadAll();

                    <msh:ifFormTypeAreViewOrEdit formName="smo_covidMarkForm">
                    //загрузка значений
                    function loadValues() {
                        if ($('vocPuls'+$('puls').value))
                            $('vocPuls'+$('puls').value).checked=true;
                        if ($('vocChdd'+$('chdd').value))
                            $('vocChdd'+$('chdd').value).checked=true;
                        if ($('vocChangeLungs'+$('changeLungs').value))
                            $('vocChangeLungs'+$('changeLungs').value).checked=true;
                        if ($('vocTemp'+$('temp').value))
                            $('vocTemp'+$('temp').value).checked=true;
                        jQuery('#isBadSoznChb').prop('checked',$('isBadSozn').value=="true");
                        $('sostEl').value = $('sost').value;
                        setSostName();
                        CovidService.getBadSosts($('id').value, {
                            callback: function (res) {
                                if (res != '') {
                                    var mas = res.replace(new RegExp(' ','g'),'').split(',');
                                    for (var i=0; i<mas.length; i++) {
                                        if ($('vocBadSost'+mas[i]))
                                            $('vocBadSost'+mas[i]).checked=true;
                                    }
                                }
                            }
                        });
                    }
                    </msh:ifFormTypeAreViewOrEdit>
                    /**
                     * Сохранение
                     *
                     * @param btn Кнопка сохранения
                     */
                    function save(btn) {
                        btn.value='Создание...';
                        var puls = getValueVocRadiooncoT('vocPuls', 'vocPuls');
                        var chdd = getValueVocRadiooncoT('vocChdd', 'vocChdd');
                        var changeLungs = getValueVocRadiooncoT('vocChangeLungs', 'vocChangeLungs');
                        var temp = getValueVocRadiooncoT('vocTemp', 'vocTemp');
                        var vocBadSost=getValueVocChboncoT('vocBadSost');
                        if (puls == -1) {
                            alert("Заполните пульоксиметрию!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (chdd == -1) {
                            alert("Заполните частоту дыхательных движений!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (changeLungs == -1) {
                            alert("Заполните изменения в лёгких!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (temp == -1) {
                            alert("Заполните температуру тела!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (vocBadSost.length==0) {
                            alert("Заполните признаки тяжёлого состояния!");
                            btn.removeAttribute("disabled");
                            btn.value='Создать';
                        }
                        else {
                            $('puls').value = puls;
                            $('chdd').value = chdd;
                            $('changeLungs').value = changeLungs;
                            $('temp').value = temp;
                            $('badSostString').value = vocBadSost;
                            $('isBadSozn').value = $('isBadSoznChb').checked;
                            $('sost').value = $('sostEl').value;
                            calc();
                             document.forms[0].submit();
                        }
                    }

                    //Если Ничего из этого - снять остальное, и наоборот
                    function checkNothingChb(btn) {
                        if (btn.childNodes[0].name=='vocBadSost') {
                            if (btn.childNodes[0].value=='0') {
                                var vocBadSost = getValueVocChboncoT('vocBadSost');
                                for (var i=0; i<vocBadSost.length; i++) {
                                    if (vocBadSost[i]!=1)
                                        jQuery('#vocBadSost'+(i+1)).prop('checked',false)
                                }
                            }
                            else if (btn.childNodes[0].value!='0')
                                jQuery('#vocBadSost1').prop('checked',false)
                        }
                    }

                    //проставить степень
                    function setEl(cnt) {
                        $('sostEl').value=++cnt;//т.к. код с 0 начинается
                        setSostName();
                    }

                    //проверка, является ли 3 уровнем
                    function getCnt3(inputs) {
                        var cnt3 = 0;
                        for (var i = 0; i < inputs.length; i++)
                            if ((inputs[i].type == 'checkbox' || inputs[i].type == 'radio')
                                && inputs[i].name.indexOf('voc') != -1 && inputs[i].name.indexOf('vocBadSost')==-1
                                && inputs[i].checked && inputs[i].value == 3)
                                    cnt3++;
                        //если хоть 1 3его уровня и чекбокс проставлен
                        if (cnt3>0  && jQuery('#isBadSoznChb').prop('checked') || cnt3>=2) {
                            setEl(3);
                            cnt3=2;
                        }
                        return cnt3;
                    }

                    //Вернуть кол-во критериев 2го уровня
                    function getCnt2(inputs) {
                        var cnt2 = 0;
                        for (var i = 0; i < inputs.length; i++)
                            if ((inputs[i].type == 'checkbox' || inputs[i].type == 'radio')
                                && inputs[i].name.indexOf('voc') != -1 && inputs[i].name.indexOf('vocBadSost') == -1
                                && inputs[i].checked && (inputs[i].value == 3 || inputs[i].value == 2))
                                cnt2++;
                        var vocBadSost = getValueVocChboncoT('vocBadSost');
                        //если чекбокс или признак тяжёлого состояния (не "Ничего из этого")
                        if (jQuery('#isBadSoznChb').prop('checked') || (vocBadSost.length > 0 && !jQuery('#vocBadSost1').prop('checked')))
                            cnt2++;
                        if (cnt2 >= 2) {
                            setEl(2);
                            cnt2=2;
                        }
                        return cnt2;
                    }

                    //Проверка 1го уровня
                    function getCnt1(inputs,cnt2) {
                        var cnt1 = cnt2;
                        for (var i = 0; i < inputs.length; i++)
                            if ((inputs[i].type == 'checkbox' || inputs[i].type == 'radio')
                                && inputs[i].name.indexOf('voc') != -1 && inputs[i].name.indexOf('vocBadSost') == -1
                                && inputs[i].checked && inputs[i].value == 1)
                                cnt1++;
                        if (cnt1 >= 2)
                            setEl(1);
                        return cnt1;
                    }


                    //Рассчитать степень
                    function calc() {
                        $('sostEl').value = 0;
                        $('sostElReadOnly').value = '';
                        var inputs = document.getElementsByTagName('input');
                        var cnt2 = 0, cnt1 = 0, cnt3 = getCnt3(inputs);
                        if (cnt3<2) {
                            cnt2 = getCnt2(inputs);
                            if (cnt2<2)
                                cnt1 = getCnt1(inputs,cnt2);
                            if ($('sostEl').value==0) {
                                $('sostEl').value=1;
                                setSostName();
                            }
                        }
                    }

                    //проставить текст состояния
                    function setSostName() {
                        CovidService.getSostById($('sostEl').value, {
                            callback: function (aResult) {
                                if (aResult != '')
                                    $('sostElReadOnly').value = aResult;
                            }
                        });
                    }

                    //перейти к выписке
                    function goToDischarge() {
                        window.location.href = 'entityParentEdit-stac_sslDischarge.do?id=' + $('medCase').value;
                    }
                </script>
            </tiles:put>
    </tiles:put>

            <tiles:put name="side" type="string">
            <msh:ifFormTypeIsView formName="smo_covidMarkForm">
                <msh:sideMenu title="Действия">
                    <msh:sideLink key="ALT+2" params="id" action="/entityEdit-smo_covidMark" name="Изменить" />
                    <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_covidMark" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin" />
                    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/Show,/Policy/Mis/MedCase/Stac/Ssl/Discharge/Edit"
                                  name="Выписка &larr;"   params=""  action='/javascript:goToDischarge();'
                                  key='Alt+9' title='Выписка' styleId="stac_sslDischarge" />
                </msh:sideMenu>
            </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>