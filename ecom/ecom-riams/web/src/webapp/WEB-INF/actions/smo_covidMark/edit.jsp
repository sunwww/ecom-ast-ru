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
        <ecom:titleTrail title="Форма оценки тяжести коронавируса" mainMenu="MedCase" beginForm="smo_covidMarkForm" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="entitySaveGoView-smo_covidMark.do" defaultField="id">
            <msh:title>Отметьте <u><i>максимальные по тяжести</i></u> критерии,
                которые наблюдались во время лечения пациента
            </msh:title>
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="medCase" />
            <msh:hidden property="changeLungs" />
            <msh:hidden property="chdd" />
            <msh:hidden property="puls" />
            <msh:hidden property="temp" />
            <msh:hidden property="soznString" />
            <msh:ifFormTypeIsNotView formName="smo_covidMarkForm">
                <msh:hidden property="createDate" />
                <msh:hidden property="createTime" />
                <msh:hidden property="createUsername" />
                <msh:hidden property="editDate" />
                <msh:hidden property="editTime" />
                <msh:hidden property="editUsername" />
            </msh:ifFormTypeIsNotView>
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
            <label><b>Нарушение сознания:</b></label><br>
            <div class="borderedDiv" id="soznDiv"></div>
            <br>
            <msh:panel>
                <msh:row>
                    <msh:autoComplete vocName="vocSost" property="sost" label="Тяжесть заболевания" fieldColSpan="3" size="100" horizontalFill="true" viewOnlyField="true"/>
                </msh:row>
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
                                    txt += "<label><input type='" + type + "' name='" + voc + "' id='" + voc + vocVal.id;
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
                        jQuery('#soznDiv').html('');
                        loadDivFromVoc('vocPuls', jQuery('#pulsDiv')[0], 'radio');
                        loadDivFromVoc('vocChdd', jQuery('#chddDiv')[0], 'radio');
                        loadDivFromVoc('vocChangeLungs', jQuery('#changeLurgeDiv')[0], 'radio');
                        loadDivFromVoc('vocTemp', jQuery('#tempDiv')[0], 'radio');
                        loadDivFromVoc('vocSozn', jQuery('#soznDiv')[0], 'checkbox');
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
                        CovidService.getSozns($('id').value, {
                            callback: function (res) {
                                if (res != '') {
                                    var mas = res.replace(new RegExp(' ','g'),'').split(',');
                                    for (var i=0; i<mas.length; i++) {
                                        if ($('vocSozn'+mas[i]))
                                            $('vocSozn'+mas[i]).checked=true;
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
                        var vocSozn=getValueVocChboncoT('vocSozn');
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
                        else if (vocSozn.length==0) {
                            alert("Заполните нарушение сознания!");
                            btn.removeAttribute("disabled");
                            btn.value='Создать';
                        }
                        else {
                            $('puls').value = puls;
                            $('chdd').value = chdd;
                            $('changeLungs').value = changeLungs;
                            $('temp').value = temp;
                            $('soznString').value = vocSozn;
                            calc();
                             document.forms[0].submit();
                        }
                    }

                    //Рассчитать степень
                    function calc() {
                        var inputs = document.getElementsByTagName('input');
                        var cnt3=0,cnt2=0,cnt1=0;
                        for (var i = 0; i < inputs.length; i++) {
                            if ((inputs[i].type == 'checkbox' || inputs[i].type == 'radio')
                                && inputs[i].name.indexOf('voc') != -1 && inputs[i].checked)
                                if (inputs[i].value == 3) {
                                    cnt3++;
                                    if (cnt3>=2) {
                                        $('sost').value=3;
                                    }
                                }
                                else if (inputs[i].value == 2) {
                                    cnt2++;
                                    $('sost').value=2;
                                }
                                else if (inputs[i].value == 1) {
                                    cnt1++;
                                    $('sost').value=1;
                                }
                        }
                        if (cnt3<2 && cnt2<2 && cnt1<2)
                            $('sost').value=0;
                        $('sost').value++;//т.к. код с 0 начинается
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