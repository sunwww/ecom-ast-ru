<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<%@ attribute name="title" required="true" description="Заголовок" %>

<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="field" required="true" description="Название поля" %>

<msh:ifInRole roles="${roles}">


    <style type="text/css">
        #${name}NewCalculation {
            visibility: hidden ;
            display: none ;
            position: absolute ;
        }
        .input-block label {
            display: block;
            margin: 0.1rem 0;
        }
        .form-section {
            border: none;
            background-color: rgba(214, 222, 230, 0.42);
            margin-bottom: 1px;
            padding: 1px;
            border-radius: 1px;
        }
    </style>
    <div id='${name}NewCalculationDialog' class='dialog'>
        <h2>Шкала CAPRINI</h2>

        <div id="formula${name}" class="formula${name}"> </div>
        <msh:panel>
                <div class="form-section">
                    <h4>Возраст:</h4>
                    <div class="input-block">
                        <label><input disabled type="radio" name="age${name}" id="age1${name}" value="0" checked="">Младше 41
                            года</label>
                        <label><input disabled type="radio" name="age${name}" id="age2${name}" value="1">41-60</label>
                        <label><input disabled type="radio" name="age${name}" id="age3${name}" value="2">61-74</label>
                        <label><input disabled type="radio" name="age${name}" id="age4${name}" value="3">Старше 75 лет</label>
                    </div>
                </div>

                <div class="form-section">
                    <h4>Пол:</h4>
                    <div>
                        <label><input disabled id="male${name}" type="radio" name="sex${name}" value="0">мужской</label>
                        <label><input disabled id="female${name}" type="radio" name="sex${name}"  value="0">женский</label>

                        <div id="female-section${name}" class="input-block">
                            <label><input  type="checkbox" name="oral-contraceptive-use${name}" value="1">
                                Прием оральных контрацептивов</label>
                            <label><input  type="checkbox" name="pregnancy-and-puerperium${name}" value="1">
                                Беременность и послеродовый период (до 1 мес.)</label>
                            <label><input  type="checkbox" name="miscarriages${name}" value="1">
                                В анамнезе: необъяснимые мертворождения, выкидыши (≥3), преждевременные роды с токсикозом или задержка
                                внутриутробного развития</label>
                        </div>
                    </div>
                </div>
                <div class="form-section input-block">
                    <label><input  disabled type="checkbox" name="body-mass-index-more-than-25${name}" value="1">
                        Избыточный вес и ожирение (ИМТ &gt; 25 кг/м²)</label>
                    <div id="imt-calc${name}">
                        <h4>Расчет ИМТ (Индекс Массы Тела):</h4>
                        <label for="imt-calc-height${name}">рост, см</label>
                        <input type="number" name="height${name}" id="imt-calc-height${name}">
                        <label for="imt-calc-weight${name}">вес, кг</label>
                        <input type="number" name="weight${name}" id="imt-calc-weight${name}">
                        <p id="imt-calc-result${name}"></p>
                    </div>
                </div>

                <div class="form-section input-block">
                    <label><input  type="checkbox" name="edema-of-the-lower-limbs${name}" value="1">
                        Отек нижних конечностей</label>
                    <label><input  type="checkbox" name="varicose-veins${name}" value="1">
                        Варикозные вены</label>
                </div>

                <div class="form-section input-block">
                    <h4>Плановое хирургическое вмешательство:</h4>
                    <label><input  type="radio" name="planned-surgery${name}" value="0" checked="">Нет
                    </label>
                    <label><input  type="radio" name="planned-surgery${name}" value="1">
                        Малое хирургическое вмешательство (менее 60 мин.)</label>
                    <label><input  type="radio" name="planned-surgery${name}" value="2">
                        Большое хирургическое вмешательство (более 60 мин.)</label>
                </div>

                <div class="form-section input-block">
                    <h4>Болезни сердца:</h4>
                    <label><input  type="checkbox" name="acute-myocardial-infarction${name}" value="1">
                        Острый инфаркт миокарда</label>
                    <label><input  type="checkbox" name="chronic-heart-failure${name}" value="1">
                        Хроническая сердечная недостаточность (давностью до 1 мес.)</label>
                </div>

                <div class="form-section input-block">
                    <h4>Венозные тромбоэмболические осложнения:</h4>
                    <label><input  type="checkbox" name="personal-history-of-VTE${name}" value="3">
                        Личный анамнез ВТЭО</label>
                    <label><input  type="checkbox" name="family-history-of-VTE${name}" value="3">
                        Семейный анамнез ВТЭО</label>
                </div>
            <div class="form-section input-block">
                <h3>Болезни лёгких:</h3>
                <label><input class="calc-computing" type="checkbox" name="serious-lung-disease${name}" value="1">
                    Серьезное заболевание лёгких (в т.ч. пневмония давностью до 1 мес.)</label>
                <label><input class="calc-computing" type="checkbox" name="obstructive-lung-disease${name}" value="1">
                    Хроническая обструктивная болезнь лёгких</label>
            </div>

            <div class="form-section input-block">
                <h3>Мутации:</h3>
                <label><input class="calc-computing" type="checkbox" name="mutation-of-the-Leiden-type${name}" value="3">
                    Мутация типа Лейден</label>
                <label><input class="calc-computing" type="checkbox" name="prothrombin-20210A-mutation${name}" value="3">
                    Мутация протромбина 20210А</label>
            </div>

            <div class="form-section input-block">
                <h3>Травмы:</h3>
                <label><input class="calc-computing" type="checkbox" name="multiple-injury${name}" value="5">
                    Множественная травма (давностью до 1 мес.)</label>
                <label><input class="calc-computing" type="checkbox" name="fracture-of-hip-and-shin-bones${name}" value="5">
                    Перелом костей бедра и голени (давностью до 1 мес.)</label>
                <label><input class="calc-computing" type="checkbox" name="Trauma-of-the-spinal-cord${name}" value="5">
                    Травма спинного мозга/паралич (давностью до 1 мес.)</label>
            </div>

            <div class="form-section input-block">
                <label><input class="calc-computing" type="checkbox" name="sepsis${name}" value="1">
                    Сепсис (давностью до 1 мес.)</label>
                <label><input class="calc-computing" type="checkbox" name="hormone-replacement-therapy${name}" value="1">
                    Гормонозаместительная терапия</label>
                <label><input class="calc-computing" type="checkbox" name="bed-rest-in-a-non-surgical-patient${name}" value="1">
                    Постельный режим у нехирургического пациента</label>
                <label><input class="calc-computing" type="checkbox" name="bed-rest-in-the-patient-for-more-than-72-hours${name}" value="2">
                    Постельный режим более 72 часов</label>
                <label><input class="calc-computing" type="checkbox" name="immobilization-of-the-limb${name}" value="2">
                    Иммобилизация конечности (давностью до 1 мес.)</label>
                <label><input class="calc-computing" type="checkbox" name="inflammatory-diseases-of-the-colon-in-anamnesis${name}" value="1">
                    Воспалительные заболевания толстой кишки в анамнезе</label>
                <label><input class="calc-computing" type="checkbox" name="large-surgical-intervention-prescription-up-to-1-month${name}" value="1">
                    Большое хирургическое вмешательство давностью до 1 мес. в анамнезе</label>
                <label><input class="calc-computing" type="checkbox" name="arthroscopic-surgery${name}" value="2">
                    Артроскопическая хирургия</label>
                <label><input class="calc-computing" type="checkbox" name="laparoscopic-intervention${name}" value="2">
                    Лапороскопическое вмешательство (более 60 мин.)</label>
                <label><input class="calc-computing" type="checkbox" name="central-venous-catheterization${name}" value="2">
                    Катетеризация центральных вен</label>
                <label><input class="calc-computing" type="checkbox" name="malignant-neoplasm${name}" value="2">
                    Злокачественное новообразование</label>
                <label><input class="calc-computing" type="checkbox" name="hyperhomocysteinemia${name}" value="3">
                    Гипергомоцистеинемия</label>
                <label><input class="calc-computing" type="checkbox" name="heparin-induced-thrombocytopenia${name}" value="3">
                    Гепарин-индуцированная тромбоцитопения</label>
                <label><input class="calc-computing" type="checkbox" name="elevated-levels-of-antibodies-to-cardiolipin${name}" value="3">
                    Повышенный уровень антител к кардиолипину</label>
                <label><input class="calc-computing" type="checkbox" name="lupus-anticoagulant${name}" value="3">
                    Волчаночный антикоагулянт</label>
                <label><input class="calc-computing" type="checkbox" name="stroke${name}" value="5">
                    Инсульт (давностью до 1 мес.)</label>
                <label><input class="calc-computing" type="checkbox" name="endoprosthetics-of-large-joints${name}" value="5">
                    Эндопротезирование крупных суставов</label>
            </div>
        </msh:panel>
        <tr>
            <td>
                <input class="cancel" id="cancel" value="Отмена" onclick="cancel${name}NewCalculation()" type="button">
            </td>
            <td>
                <input value="Рассчитать" onclick="calculate${name}();" type="button">
            </td>
            <td>
                <input value="Далее" onclick="save${name}NewCalculation();" type="button">
            </td>
        </tr>
    </div>

    <script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
    <script type="text/javascript">
        var theIs${name}NewCalculationDialogInitialized = false ;
        var the${name}NewCalculationDialog = new msh.widget.Dialog($('${name}NewCalculationDialog')) ;
        var departmentId${name}=0;
        var calcId${name}=0;

        // инициализация диалогового окна
        function init${name}NewCalculationDialog() {
            theIs${name}NewCalculationDialogInitialized = true ;
        }

        // Показать
        function show${name}NewCalculation(id,calcId,create) {
            if (!theIs${name}NewCalculationDialogInitialized) {
                init${name}NewCalculationDialog() ;
            }
            the${name}NewCalculationDialog.show() ;
            getAge${name}(id);
            getGender${name}(id);
            getIMT${name}(id);
            departmentId${name}=id;
            calcId${name}=calcId;
            document.getElementById("cancel").style.display ="";
        }

        // Отмена
        function cancel${name}NewCalculation() {
            the${name}NewCalculationDialog.hide() ;

        }

        // Сохранение данных
        function save${name}NewCalculation() {
            calculate${name}();
            var formString=formToString${name}();
            var riskPoints=document.getElementById('formula${name}').innerHTML;
            var prop ;
            if ("${property}"=="") prop = "record" ;


            //18122018 - отметка назначений и противопоказаний
            CalculateService.getRisk(calcId${name},riskPoints,{
                callback: function(aResult) {
                        var ar = aResult.split('#');
                        var riskId=ar[0];
                        var risk=ar[1];
                        formString+=(window.parent.document.getElementById(prop)==null)? "\nНазначения:\n":"\nРезультат: "+riskPoints+"\nРиск: " + risk + "\nНазначения:\n";
                        var calcRes="Результат: "+riskPoints+". Риск: " + risk;
                        if (riskId!=null) {
                            showcalculation_extraNewCalculation('presccalc',departmentId${name},'prescvalue',calcId${name},riskId,'Назначения и противопоказания',formString,prop,risk,calcRes);
                        }
                    }
                });
            the${name}NewCalculationDialog.hide();
        }

        //вычисение результата
        function calculate${name}(){
            var res=0;
            var inputs = document.getElementsByTagName('input');
            for (var i = 0; i < inputs.length; i++) {
                if ((inputs[i].type == 'radio' || inputs[i].type == 'checkbox') && inputs[i].checked==true && inputs[i].name.indexOf('${name}')!=-1) {
                    if (inputs[i].type == 'checkbox') res+=+inputs[i].value;
                    else res+= +getValueVocRadiooncoT${name}(inputs[i].name);
                }
            }
            document.getElementById('formula${name}').innerHTML = res;

        }
        //Форма в строку
        function formToString${name}() {
            var inputs = document.getElementsByTagName('input');
            var formToStirng='Параметр\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tБаллы\n';
            for (var i = 0; i < inputs.length; i++) {
                if ((inputs[i].type == 'radio' || inputs[i].type == 'checkbox') && inputs[i].name.indexOf('${name}')!=-1) {
                    if (inputs[i].name.indexOf('age${name}')!=-1 && formToStirng.indexOf('Возраст: ')==-1) {
                        formToStirng+='Возраст: ';
                        formToStirng+=getTextVocRadiooncoTJustChecked${name}('age${name}');
                        formToStirng+="\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t("+getValueVocRadiooncoT${name}('age${name}')+")";
                        formToStirng+="\n";
                    }
                    if (inputs[i].name.indexOf('sex${name}')!=-1 && formToStirng.indexOf('Пол: ')==-1) {
                        formToStirng+='Пол: ';
                        formToStirng+=getTextVocRadiooncoTJustChecked${name}('sex${name}');
                        formToStirng+="\n";
                    }
                    if (inputs[i].name.indexOf('oral-contraceptive')!=-1 && document.getElementById('female${name}').checked) {
                        formToStirng+=getTextForInputChb${name}('oral-contraceptive-use${name}')+"\n";
                        formToStirng+=getTextForInputChb${name}('pregnancy-and-puerperium${name}')+"\n";
                        formToStirng+=getTextForInputChb${name}('miscarriages${name}')+"\n";
                    }
                    if (inputs[i].name.indexOf('body-mass-index-more-than-25')!=-1)
                        formToStirng+='Индекс Массы Тела:\n'+getTextForInputChb${name}('body-mass-index-more-than-25${name}')+"\n";
                    if (inputs[i].name.indexOf('edema-of-the-lower-limbs')!=-1)
                        formToStirng+=getTextForInputChb${name}('edema-of-the-lower-limbs${name}')+"\n";
                    if (inputs[i].name.indexOf('varicose-veins')!=-1)
                        formToStirng+=getTextForInputChb${name}('varicose-veins${name}')+"\n";
                    if (inputs[i].name.indexOf('planned-surgery')!=-1 && formToStirng.indexOf('Плановое хирургическое вмешательство:')==-1) {
                        formToStirng+='Плановое хирургическое вмешательство:';
                        formToStirng+=getTextVocRadiooncoTJustChecked${name}('planned-surgery${name}');
                        formToStirng+="\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t("+getValueVocRadiooncoT${name}('planned-surgery${name}')+")";
                        formToStirng+="\n";
                    }
                    if (inputs[i].name.indexOf('acute-myocardial-infarction')!=-1)
                        formToStirng+='Болезни сердца:\n'+getTextForInputChb${name}('acute-myocardial-infarction${name}')+"\n";
                    if (inputs[i].name.indexOf('chronic-heart-failure')!=-1)
                        formToStirng+=getTextForInputChb${name}('chronic-heart-failure${name}')+"\n";

                    if (inputs[i].name.indexOf('personal-history-of-VTE')!=-1)
                        formToStirng+='Венозные тромбоэмболические осложнения:\n'+getTextForInputChb${name}('personal-history-of-VTE${name}')+"\n";
                    if (inputs[i].name.indexOf('chronic-heart-failure')!=-1)
                        formToStirng+=getTextForInputChb${name}('family-history-of-VTE${name}')+"\n";

                    if (inputs[i].name.indexOf('serious-lung-disease')!=-1)
                        formToStirng+='Болезни лёгких:\n'+getTextForInputChb${name}('serious-lung-disease${name}')+"\n";
                    if (inputs[i].name.indexOf('obstructive-lung-disease')!=-1)
                        formToStirng+=getTextForInputChb${name}('obstructive-lung-disease${name}')+"\n";

                    if (inputs[i].name.indexOf('mutation-of-the-Leiden-type')!=-1)
                        formToStirng+='Мутации:\n'+getTextForInputChb${name}('mutation-of-the-Leiden-type${name}')+"\n";
                    if (inputs[i].name.indexOf('prothrombin-20210A-mutation')!=-1)
                        formToStirng+=getTextForInputChb${name}('prothrombin-20210A-mutation${name}')+"\n";

                    if (inputs[i].name.indexOf('multiple-injury')!=-1)
                        formToStirng+='Травмы:\n'+getTextForInputChb${name}('multiple-injury${name}')+"\n";
                    if (inputs[i].name.indexOf('fracture-of-hip-and-shin-bones')!=-1)
                        formToStirng+=getTextForInputChb${name}('fracture-of-hip-and-shin-bones${name}')+"\n";
                    if (inputs[i].name.indexOf('fracture-of-hip-and-shin-bones')!=-1)
                        formToStirng+=getTextForInputChb${name}('fracture-of-hip-and-shin-bones${name}')+"\n";

                    if (inputs[i].name.indexOf('sepsis')!=-1)
                        formToStirng+=getTextForInputChb${name}('sepsis${name}')+"\n";
                    if (inputs[i].name.indexOf('hormone-replacement-therapy')!=-1)
                        formToStirng+=getTextForInputChb${name}('hormone-replacement-therapy${name}')+"\n";
                    if (inputs[i].name.indexOf('bed-rest-in-a-non-surgical-patient')!=-1)
                        formToStirng+=getTextForInputChb${name}('bed-rest-in-a-non-surgical-patient${name}')+"\n";
                    if (inputs[i].name.indexOf('bed-rest-in-the-patient-for-more-than-72-hours')!=-1)
                        formToStirng+=getTextForInputChb${name}('bed-rest-in-the-patient-for-more-than-72-hours${name}')+"\n";
                    if (inputs[i].name.indexOf('immobilization-of-the-limb')!=-1)
                        formToStirng+=getTextForInputChb${name}('immobilization-of-the-limb${name}')+"\n";
                    if (inputs[i].name.indexOf('inflammatory-diseases-of-the-colon-in-anamnesis')!=-1)
                        formToStirng+=getTextForInputChb${name}('inflammatory-diseases-of-the-colon-in-anamnesis${name}')+"\n";
                    if (inputs[i].name.indexOf('large-surgical-intervention-prescription-up-to-1-month')!=-1)
                        formToStirng+=getTextForInputChb${name}('large-surgical-intervention-prescription-up-to-1-month${name}')+"\n";
                    if (inputs[i].name.indexOf('arthroscopic-surgery')!=-1)
                        formToStirng+=getTextForInputChb${name}('arthroscopic-surgery${name}')+"\n";
                    if (inputs[i].name.indexOf('laparoscopic-intervention')!=-1)
                        formToStirng+=getTextForInputChb${name}('laparoscopic-intervention${name}')+"\n";
                    if (inputs[i].name.indexOf('central-venous-catheterization')!=-1)
                        formToStirng+=getTextForInputChb${name}('central-venous-catheterization${name}')+"\n";
                    if (inputs[i].name.indexOf('malignant-neoplasm')!=-1)
                        formToStirng+=getTextForInputChb${name}('malignant-neoplasm${name}')+"\n";
                    if (inputs[i].name.indexOf('hyperhomocysteinemia')!=-1)
                        formToStirng+=getTextForInputChb${name}('hyperhomocysteinemia${name}')+"\n";
                    if (inputs[i].name.indexOf('heparin-induced-thrombocytopenia')!=-1)
                        formToStirng+=getTextForInputChb${name}('heparin-induced-thrombocytopenia${name}')+"\n";
                    if (inputs[i].name.indexOf('elevated-levels-of-antibodies-to-cardiolipin')!=-1)
                        formToStirng+=getTextForInputChb${name}('elevated-levels-of-antibodies-to-cardiolipin${name}')+"\n";
                    if (inputs[i].name.indexOf('lupus-anticoagulant')!=-1)
                        formToStirng+=getTextForInputChb${name}('lupus-anticoagulant${name}')+"\n";
                    if (inputs[i].name.indexOf('stroke')!=-1)
                        formToStirng+=getTextForInputChb${name}('strokes${name}')+"\n";
                    if (inputs[i].name.indexOf('endoprosthetics-of-large-joints')!=-1)
                        formToStirng+=getTextForInputChb${name}('endoprosthetics-of-large-joints${name}')+"\n";
                }
            }
            return formToStirng;
        }
        //Получить пол
        function getGender${name}(medcaseId) {
            CalculateService.getGender(medcaseId, {
                callback : function(aResult) {
                    var radio1 = document.querySelector('#male${name}');
                    var radio2 = document.querySelector('#female${name}');
                    if (aResult == 2) radio1.checked=true;
                    if (aResult == 1) radio2.checked=true;
                    /* Всплывающая секция для женщин */
                    if (jQuery('#female${name}').is(':checked')) {
                        jQuery('#female-section${name}').css('display', 'block')
                    } else {
                        jQuery('#female-section${name}').css('display', 'none');
                        jQuery('input:checkbox[name="oral-contraceptive-use${name}"]').prop( "checked", false );
                        jQuery('input:checkbox[name="pregnancy-and-puerperium${name}"]').prop( "checked", false );
                        jQuery('input:checkbox[name="miscarriages${name}"]').prop( "checked", false );
                    }
                }
            });
        }

        //Получить возраст полных лет
        function getAge${name}(medcaseId) {
            CalculateService.getAge(medcaseId, {
                callback : function(aResult) {
                    if (aResult<41) document.querySelector('#age1${name}').checked=true;
                    if (aResult>=41 && aResult<=60) document.querySelector('#age2${name}').checked=true;
                    if (aResult>=61 && aResult<=74) document.querySelector('#age3${name}').checked=true;
                    if (aResult>=75) document.querySelector('#age4${name}').checked=true;
                }
            });
        }
        //Получить ИМТ (если есть)
        function getIMT${name}(medcaseId) {
            CalculateService.getIMT(medcaseId, {
                callback : function(aResult) {
                    if (aResult!='') {
                        var mas=aResult.split('#');
                        jQuery('#imt-calc-height${name}').prop( "value", mas[0]);
                        jQuery('#imt-calc-weight${name}').prop( "value", mas[1]);
                        imt${name}();
                    }
                }
            });
        }
        /* Расчет ИМТ */
        jQuery('#imt-calc-height${name}, #imt-calc-weight${name}').on('change paste keyup', function () {
            imt${name}();
        });
        function imt${name}() {
            var imtHeight = parseFloat(jQuery('#imt-calc-height${name}').val());
            var imtWeight = parseFloat(jQuery('#imt-calc-weight${name}').val());
            if (imtHeight > 0 && imtWeight > 0) {
                var imt = imtWeight / ((imtHeight*imtHeight) / 10000);
                jQuery('#imt-calc-result${name}').html(imt.toFixed(2) + ' кг/м<sup>2</sup>');
                if (imt > 25) {
                    jQuery('input:checkbox[name="body-mass-index-more-than-25${name}"]').prop( "checked", true );
                } else {
                    jQuery('input:checkbox[name="body-mass-index-more-than-25${name}"]').prop( "checked", false );
                }
            }
        }
        //получить значения группы рб
        function getValueVocRadiooncoT${name}(name) {
            var chk = document.getElementsByName(name)
            var res=0;
            for (var i=0; i<chk.length; i++) {
                if (chk[i].checked) res=chk[i].value;
            }
            return res;
        }
        //получить текст группы рб
        function getTextVocRadiooncoT${name}(name) {
            var chk = document.getElementsByName(name);
            var res=[];
            for (var i=0; i<chk.length; i++)
                res.push(jQuery(chk[i]).parent().text());
            return res;
        }
        //текст для чекбокса
        function getTextForInputChb${name}(name) {
            var res='';
            var isExtra=getTextVocRadiooncoT${name}(name);
            if (isExtra[0]==' ') isExtra=isExtra.substring(1);
            if (isExtra!='') {
                var bal=(getValueVocRadiooncoT${name}(name)==0)? 'Нет':'Да';
                res+=isExtra+': '+bal+"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t("+getValueVocRadiooncoT${name}(name)+")\n";
            }
            return res;
        }
        //получить текст группы рб
        function getTextVocRadiooncoTJustChecked${name}(name) {
            var chk = document.getElementsByName(name);
            var res=0;
            for (var i=0; i<chk.length; i++) {
                if (chk[i].checked)
                    res=jQuery(chk[i]).parent().text();
            }
            return res;
        }
    </script>
</msh:ifInRole>
