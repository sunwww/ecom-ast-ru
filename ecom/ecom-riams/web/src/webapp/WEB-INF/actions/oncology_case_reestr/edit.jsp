<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
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
            .input[type=radio] {
                transform: scale(1.5);
            }
            .input[type=checkbox] {
                transform: scale(1.5);
            }
        </style>
    </tiles:put>

    <tiles:put name="title" type="string">
        <ecom:titleTrail guid="titleTrail-123" mainMenu="MedCase" beginForm="oncology_case_reestrForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-oncology_case_reestr.do" defaultField="hello">
            <msh:title>ВЫПИСКА ОБ ОКАЗАНИИ МЕДИЦИНСКОЙ ПОМОЩИ</msh:title>
            <msh:title>ПАЦИЕНТАМ С ОНКОЛОГИЧЕСКИМИ ЗАБОЛЕВАНИЯМИ</msh:title>
            <h2><label id="fio"/></h2>
            <h2><label id="ds"/></h2>
            <msh:checkBox property="suspicionOncologist" label="Подозрение на ЗНО:"/><br>
            <div class="borderedDiv" id="oncologyCase">

                <msh:hidden guid="hiddenParent" property="medCase" />
                <msh:hidden guid="hiddenId" property="id" />
                <msh:hidden guid="hiddenSaveType" property="saveType" />
                <msh:hidden guid="vocOncologyReasonTreat" property="vocOncologyReasonTreat" />
                <msh:hidden guid="histString" property="histString" />
                <msh:hidden guid="consilium" property="consilium" />
                <msh:hidden guid="surgTreatment" property="surgTreatment" />
                <msh:hidden guid="lineDrugTherapy" property="lineDrugTherapy" />
                <msh:hidden guid="typeTreatment" property="typeTreatment" />
                <msh:hidden guid="contraString" property="contraString" />
                <msh:hidden property="isFirst" />
                <msh:row>
                    <msh:autoComplete  property="stad" label="Стадия заболевания (2)" vocName="vocOncologyN002parent" parentId="C16" size="25"/>
                </msh:row>
                <msh:separator colSpan="1" label="Стадия заболевания по TNM (3)"/>
                    <msh:autoComplete  property="tumor" label="Значение Tumor" vocName="vocOncologyN003parent" parentId="C16" horizontalFill="true" />
                    <msh:autoComplete  property="nodus" label="Значение Nodus" vocName="vocOncologyN004parent" parentId="C16" horizontalFill="true" />
                    <msh:autoComplete  property="metastasis" label="Значение Metastasis" vocName="vocOncologyN005parent" parentId="C16" horizontalFill="true" />
                <msh:separator colSpan="10" label="." />
                <br><msh:checkBox property="distantMetastasis" label="Наличие отдалённых метастазов (при прогрессировании/рецидиве)"/><br>
                <div id="firstDiv">
                    <label><b>Заболевание выявлено:</b></label>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeFirstOrNot" value="1"> впервые
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeFirstOrNot" value="2"> ранее
                    </td>
                </div>
                <div id="vocOncologyReasonTreatDiv">
                    <label><b>Категория пациента (4):</b></label><br>
                </div>
                <br>
                <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                    <input type="checkBox" name="histologyChb" id="histologyChb"> <b>Гистология (5):</b>
                    <msh:textField property="dateBiops" label="Дата взятия биопсийного материала" fieldColSpan="3"/><br>
                </td><br>
                <div class="borderedDiv" id="histologyDiv"> </div>
                <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                    <input type="checkBox" name="immunoGistMarkChb" id="immunoGistMarkChb"> <b>Иммуногистохимия/маркёры (6):</b><br>
                </td><br>
                <div class="borderedDiv" id="immunoGistMarkDiv"></div>
                <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                    <input type="checkBox" name="consiliumChb" id="consiliumChb"> <b>Проведение консилиума (7):</b>
                    <msh:textField property="dateCons" label="Дата проведения консилиума" fieldColSpan="3"/><br>
                </td><br>
                <div class="borderedDiv" id="vocOncologyConsiliumDiv">
                </div>
                <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                    <input type="checkBox" name="treatmentChb" id="treatmentChb"> <b>Проведённое лечение (8):</b><br>
                </td><br>
                <div class="borderedDiv" id="treatmentDiv"></div>
                <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                    <input type="checkBox" name="contraChb" id="contraChb"> <b>Противопоказания и отказы:</b><br>
                </td><br>
                <div class="borderedDiv" id="contraDiv">
                    <label><b>Медицинские противопоказания к оказанию медицинской помощи и дата регистрации (11):</b></label><br>
                    <input type="checkBox" name="c1" id="c1" > Противопоказания к проведению хирургического лечения
                    <msh:textField property="date1" label="" /><br>
                    <input type="checkBox" name="c2" id="c2" > Противопоказания к проведению химиотерапевтического лечения
                    <msh:textField property="date2" label="" /><br>
                    <input type="checkBox" name="c3" id="c3" > Противопоказания к проведению лучевой терапии
                    <msh:textField property="date3" label="" /><br>
                    <label><b>Отказ от проведения лечения и дата регистрации (12):</b></label><br>
                    <input type="checkBox" name="c4" id="c4" > Отказ от проведения хирургического лечения
                    <msh:textField property="date4" label="" /><br>
                    <input type="checkBox" name="c5" id="c5" > Отказ от проведения химиотерапевтического лечения
                    <msh:textField property="date5" label="" /><br>
                    <input type="checkBox" name="c6" id="c6" > Отказ от проведения лучевой терапии
                    <msh:textField property="date6" label="" /><br>
                </div>
                <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                    <div>
                        <a id='noteH' href="#bottom" onclick="showNote();" >Показать примечание</a><br>
                        <div id="note" style="display:none;">
                            <b>1</b> Раздел «Направление с целью уточнения диагноза» заполняется при подозрении на злокачественное
                            новообразование.<br>
                            <b>2,3,4</b> Разделы «Стадия заболевания», «Стадия заболевания по ТNМ»,«Категория пациента» заполняются при
                            установленном диагнозе злокачественного новообразования.<br>
                            <b>5</b> Раздел «Гистология» заполняется при установленном диагнозе злокачественного новообразования.<br>
                            Для диагнозов <i>С15, С16, С18, С19, С20, С25, С32, С34, С50, С53, С56, С61, С67</i> указывается, является ли опухоль
                            эпителиальной.<br>
                            Для диагнозов <i>С15, С16</i> (эпителиальная опухоль) указывается, является ли опухоль аденокарциномой.<br>
                            Для диагноза <i>С34</i> (эпителиальная опухоль) указывается, является ли опухоль мелкоклеточной.<br>
                            Для диагноза <i>С44</i> (эпителиальная опухоль) указывается, является ли опухоль базальноклеточной или
                            плоскоклеточной.<br>
                            Для диагноза <i>С54</i> (любой тип опухоли) указывается, является ли опухоль эндометриоидной, а также, для
                            эндометриоидной опухоли, указывается степень дифференцированности опухоли.<br>
                            Для диагноза <i>С56</i> (эпителиальная опухоль) указывается степень дифференцированности опухоли.<br>
                            Для диагноза <i>С64</i> (любой тип опухоли) указывается, является ли опухоль почечноклеточной, а также, для
                            почечноклеточной опухоли, является ли она светлоклеточной.<br>
                            Для диагноза <i>С73</i> (любой тип опухоли) указывается, является ли опухоль папиллярной, фолликулярной,
                            гюртклеточной, медуллярной или анапластической.<br>
                            <b>6</b> Раздел «Иммуногистохимия / маркеры» заполняется каждый раз при наличии сведений о результатах исследований.
                            <br>Для диагноза <i>С16</i> (эпителиальная опухоль) указывается уровень экспрессии белка <i>НЕR2</i>.<br>
                            Для диагнозов <i>С18, С19, С20</i> (эпителиальная опухоль) указывается наличие мутаций в генах <i>RAS</i> и <i>BRAF</i>.<br>
                            Для диагноза <i>С34</i> (эпителиальная опухоль) указываются наличие мутаций в гене <i>EGFR</i>, наличие транслокации в генах
                            <i>АLK</i> или <i>ROS1</i>, уровень экспрессии белка <i>PD-L1</i>.<br>
                            Для диагноза <i>С43</i> указываются наличие мутаций в гене <i>BRAF</i>, наличие мутаций в гене <i>c-Kit</i>.<br>
                            Для диагноза <i>С50</i> (эпителиальная опухоль) указываются наличие рецепторов к эстрогенам, наличие рецепторов к
                            прогестерону, индекс пролиферативной активности экспрессии <i>Ki-67</i>, уровень экспрессии белка <i>HER2</i>, наличие
                            мутаций в генах <i>BRCA</i>.<br>
                            <b>7</b> Раздел «Проведение консилиума» заполняется каждый раз при наличии сведений о результатах проведенного
                            консилиума.<br>
                            <b>8</b> Раздел «Проведенное лечение» заполняется при оказании соответствующей медицинской помощи.<br>
                            <b>9,10</b> Указывается либо номер схемы лекарственной терапии, либо МНН и режим дозирования лекарственного
                            препарата (на данный момент не заполняются).<br>
                            <b>11</b> Раздел «Медицинские противопоказания к оказанию медицинской помощи» заполняется при регистрации медицинских
                            противопоказаний.<br>
                            <b>12</b> Указывается в случае оформления отказа от медицинского вмешательства в соответствии со статьёй 20
                            Федерального закона от 21.11.2011 № 323-ФЗ «Об основах охраны здоровья граждан в Российской Федерации».<br>
                        </div>
                    </div>
                </msh:ifFormTypeIsNotView>
                </div>

            <div class="borderedDiv" id="oncologyDirection">
                <msh:textField property="date" label="Дата направления" fieldColSpan="3"/><br>
                <div id="vocOncologyTypeDirectionDiv">
                    <label>Направление с целью уточнения диагноза (1):</label><br>
                </div>
                <br>
            </div>
            <msh:submitCancelButtonsRow colSpan="4" functionSubmit="this.disabled=true; if (document.getElementById('suspicionOncologist').checked) saveDirectionReestr(this); else saveCase(this) ;"/>
        </msh:form>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
        <script type="text/javascript" >
            //нужные дивы
            var suspicionOncologist = document.getElementById("suspicionOncologist");
            var oncologyDirection = document.getElementById("oncologyDirection");
            var histologyChb = document.getElementById("histologyChb");
            var immunoGistMarkChb = document.getElementById("immunoGistMarkChb");
            var consiliumChb = document.getElementById("consiliumChb");
            var treatmentChb = document.getElementById("treatmentChb");
            var histologyDiv = document.getElementById("histologyDiv");
            var immunoGistMarkDiv = document.getElementById("immunoGistMarkDiv");
            var vocOncologyConsiliumDiv = document.getElementById("vocOncologyConsiliumDiv");
            var treatmentDiv = document.getElementById("treatmentDiv");
            var contraChb = document.getElementById("contraChb");
            var contraDiv = document.getElementById("contraDiv");
            var disabled=false;
            checkCheckbox();
            checkCheckboxH();
            checkCheckboxI();
            checkCheckboxC();
            checkCheckboxT();
            checkCheckboxContra();
            //стили для красоты
            document.getElementById("date1").style="margin-left:51px";
            document.getElementById("date2").style="";
            document.getElementById("date3").style="margin-left:94px";
            document.getElementById("date4").style="margin-left:122px";
            document.getElementById("date5").style="margin-left:71px";
            document.getElementById("date6").style="margin-left:165px";
            //для экономии времени нужные массивы для лечения
            var mas1=[], mas2=[];
            for(var i=1; i<=6; i++) checkDateContra(document.getElementById("c"+i));
            document.getElementById("c1").oninput=document.getElementById("c2").oninput=
                document.getElementById("c3").oninput=document.getElementById("c4").oninput=
                    document.getElementById("c5").oninput=document.getElementById("c6").oninput=function() {
                        checkDateContra(this);
                    };
            //проставить доступность даты отказа/противопоказания в зависимости от чекбокса
            function checkDateContra(chb) {
                var id=chb.id.replace("c","");
                if (chb.checked) document.getElementById("date"+id).removeAttribute("disabled");
                else {
                    document.getElementById("date"+id).setAttribute("disabled","true");
                    document.getElementById("date"+id).value="";
                }
            }
            //показать/скрыть примечание
            function showNote() {
                var note = document.getElementById("note");
                var href = document.getElementById("noteH");
                if (note.style.display=="none") {
                    note.style.display="block";
                    href.innerText="Скрыть примечание";
                }
                else {
                    note.style.display="none";
                    href.innerText="Показать примечание";
                }
            }
            //GLOBALS
            var histString="";
            <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                disabled=true;
            </msh:ifFormTypeIsView>
            <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
            suspicionOncologist.disabled=true;
            //проставить дату направления
            OncologyService.getDateDirection(${param.id}, {
                callback : function(res) {
                    if (document.getElementById("date")) document.getElementById("date").value=res;
                    if (document.getElementById("dateReadOnly")) document.getElementById("dateReadOnly").value=res;
                }});
            //получить и проставить прочеканные типы направлений
            OncologyService.getAllDirectionCode(${param.id}, {
                callback : function(res) {
                    if (res!="##") {
                        setRowsToContaineroncoT('checkbox','vocOncologyTypeDirection','vocOncologyTypeDirectionDiv','',res.split("#"),disabled,true);
                    }
                }});
            </msh:ifFormTypeAreViewOrEdit>
            //проставить видимость дивов в зависимости от чекбоксов
           suspicionOncologist.oninput = function() {
                checkCheckbox();
            };
            histologyChb.oninput = function() {
                checkCheckboxH();
            };
            immunoGistMarkChb.oninput = function() {
                checkCheckboxI();
            };
            consiliumChb.oninput = function() {
                checkCheckboxC();
            };
            treatmentChb.oninput = function() {
                checkCheckboxT();
            };
            contraChb.oninput = function() {
                checkCheckboxContra();
            };
            function load1() {
                <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
                if (document.getElementById('vocOncologyTypeDirection1')==null) {
                    setRowsToContaineroncoT('checkbox', 'vocOncologyTypeDirection', 'vocOncologyTypeDirectionDiv', '', null, false, false);
                    $('date').value = getCurrentDate();
                }
                </msh:ifFormTypeIsCreate>
                }
            function load2() {
                if (document.getElementById("vocOncologyReasonTreat8")==null) {
                    setRowsToContaineroncoT('radio', 'vocOncologyReasonTreat', 'vocOncologyReasonTreatDiv', '', null, false, false);
                    setHistologyoncoT('vocOncologyN008', 'histologyDiv', null, histologyDiv.innerHTML);
                    setRowsToContaineroncoT('radio', 'vocOncologyConsilium', 'vocOncologyConsiliumDiv', '', null, false, false);
                    OncologyService.getMarkersAndMarks({
                        callback: function (res) {
                            if (res != "##") {
                                setRowsToConteinerMarkersoncoT(res, 'immunoGistMarkDiv', false, 'vocOncologyN010_11', null);
                            }
                        }
                    });
                    setSurgicalTreatmentoncoT('treatmentDiv', null, false);
                }
            }
            //случай/подозрение
            function checkCheckbox() {
                if(suspicionOncologist.checked){
                    oncologyDirection.style.display  = "block";
                    oncologyCase.style.display  = "none";
                    load1();
                }else {
                    oncologyDirection.style.display  = "none";
                    oncologyCase.style.display  = "block";
                    load2();

                }
            }
            //гистология
            function checkCheckboxH() {
                if(histologyChb.checked){
                    histologyDiv.style.display  = "block";
                    $('dateBiops').disabled=false;
                }else {
                    histologyDiv.style.display  = "none";
                    $('dateBiops').disabled=true;
                    $('dateBiops').value="";
                    cleanHistology();
                }
            }
            //иммуногистихимия
            function checkCheckboxI() {
                if(immunoGistMarkChb.checked){
                    immunoGistMarkDiv.style.display  = "block";
                }else {
                    immunoGistMarkDiv.style.display  = "none";
                    cleanI();
                }
            }
            //консилиум
            function checkCheckboxC() {
                if(consiliumChb.checked){
                    vocOncologyConsiliumDiv.style.display  = "block";
                    $('dateCons').disabled=false;
                }else {
                    vocOncologyConsiliumDiv.style.display  = "none";
                    $('dateCons').disabled=true;
                    $('dateCons').value="";
                    cleanC();
                }
            }
            //лечение
            function checkCheckboxT() {
                if(treatmentChb.checked){
                    treatmentDiv.style.display  = "block";
                }else {
                    treatmentDiv.style.display  = "none";
                    cleanTreatment();
                }
            }
            //отказы/пр-я
            function checkCheckboxContra() {
                if(contraChb.checked){
                    contraDiv.style.display  = "block";
                }else {
                    contraDiv.style.display  = "none";
                    cleanContra();
                }
                for(var i=1; i<=6; i++) checkDateContra(document.getElementById("c"+i));
            }
            //проставить диагноз и ФИО пациента
            <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
                OncologyService.getFIODsPatient(${param.id}, {
                callback : function(res) {
                    if (res!="#") {
                        var mas=res.split("#");
                        document.getElementById("fio").innerHTML="Ф.И.О. пациента " + mas[0];
                        document.getElementById("ds").innerHTML="Диагноз (по МКБ-10): " + mas[1];
                        if (mas[1]!=null && mas[1]!='' && typeof stadAutocomplete != 'undefined') {
                            var ind=mas[1].indexOf(' ');
                            if (ind!=-1) {
                                stadAutocomplete.setParentId(mas[1].substring(0, ind));
                                tumorAutocomplete.setParentId(mas[1].substring(0, ind));
                                nodusAutocomplete.setParentId(mas[1].substring(0, ind));
                                metastasisAutocomplete.setParentId(mas[1].substring(0, ind));
                            }
                            else {
                                stadAutocomplete.setParentId(mas[1]) ;
                                tumorAutocomplete.setParentId(mas[1]) ;
                                nodusAutocomplete.setParentId(mas[1]) ;
                                metastasisAutocomplete.setParentId(mas[1]) ;
                            }
                        }
                    }
                }});
            </msh:ifFormTypeIsCreate>
            <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
            OncologyService.getFIODsPatient($('medCase').value, {
                callback : function(res) {
                    if (res!="#") {
                        var mas=res.split("#");
                        document.getElementById("fio").innerHTML="Ф.И.О. пациента " + mas[0];
                        document.getElementById("ds").innerHTML="Диагноз (по МКБ-10): " + mas[1];
                        if (mas[1]!=null && mas[1]!='' && typeof stadAutocomplete != 'undefined') {
                            var ind=mas[1].indexOf(' ');
                            if (ind!=-1) {
                                stadAutocomplete.setParentId(mas[1].substring(0,ind)) ;
                                tumorAutocomplete.setParentId(mas[1].substring(0,ind)) ;
                                nodusAutocomplete.setParentId(mas[1].substring(0,ind)) ;
                                metastasisAutocomplete.setParentId(mas[1].substring(0,ind)) ;
                            }
                            else {
                                stadAutocomplete.setParentId(mas[1]) ;
                                tumorAutocomplete.setParentId(mas[1]) ;
                                nodusAutocomplete.setParentId(mas[1]) ;
                                metastasisAutocomplete.setParentId(mas[1]) ;
                            }
                        }
                    }
                }});
            OncologyService.getDates($('id').value, {
                callback : function(res) {
                    if (res!="##") {
                        var mas=res.split("#");
                        if (mas[0]!=null && mas[0]!='null') $('dateBiops').value=mas[0];
                        if (mas[1]!=null && mas[1]!='null') $('dateCons').value=mas[1];
                        <msh:ifFormTypeAreViewOrEdit  formName="oncology_case_reestrForm">
                        <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                            if (mas[2]!='null') $('dateBiopsReadOnly').value=mas[2];
                            if (mas[3]!='null') $('dateConsReadOnly').value=mas[3];
                        </msh:ifFormTypeIsView>
                        <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                            if (mas[0]!='null') $('dateBiops').value=mas[2];
                            if (mas[1]!='null') $('dateCons').value=mas[3];
                        </msh:ifFormTypeIsNotView>
                        </msh:ifFormTypeAreViewOrEdit>
                    }
                }});
            </msh:ifFormTypeAreViewOrEdit>
            //сохранить направления при подозрении на ЗНО
            function saveDirectionReestr(btn) {
                btn.value='Создание...';
                var typeDir=getValueVocChboncoT('vocOncologyTypeDirection');
                if ($('date').value=="") {
                    alert("Заполните дату направления!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (typeDir.length==0) {
                    alert("Заполните тип направления!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else {
                    var mas={
                        list:[]
                    };
                    for (var i=0; i<typeDir.length; i++) {
                        var obj = {
                            typeDirection: typeDir[i],
                            methodDiagTreat: "",
                            medService: "",
                            medCase:getValue("medCase"),
                            date:$('date').value,
                            id:${param.id}
                        };
                        mas.list.push(obj);
                    }
                    var json = JSON.stringify(mas);
                    <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                    <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                    OncologyService.editDirectionsByCase(${param.id},json,{
                            callback : function(retId) {
                                location.href = "entityView-oncology_case_reestr.do?id="+retId;
                            }
                        });
                    </msh:ifFormTypeIsNotView>
                    </msh:ifFormTypeAreViewOrEdit>
                    <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
                        OncologyService.insertDirection(json,"", {
                            callback : function(retId) {
                                location.href = "entityView-oncology_case_reestr.do?id="+retId;
                            }
                        });
                    </msh:ifFormTypeIsCreate>
                }
            }
            //сохранить случай
            function saveCase(btn) {
                var histologyChb1=document.getElementById("histologyChb1");
                var histologyChb2=document.getElementById("histologyChb2");
                var histologyChb3=document.getElementById("histologyChb3");
                var vocOncologyN013_1=document.getElementById("vocOncologyN013_1");
                var vocOncologyN013_2=document.getElementById("vocOncologyN013_2");
                var ds=document.getElementById("ds").innerHTML.replace("Диагноз (по МКБ-10): ","");
                ds=ds.substring(0,ds.indexOf(" "));
                btn.value='Создание...';
                var patientCategory=getValueVocRadiooncoT('vocOncologyReasonTreat','vocOncologyReasonTreat');
                if ($('stad').value==null || $('stad').value=='') {
                    alert("Заполните стадию заболевания!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if ($('tumor').value==null || $('tumor').value=='') {
                    alert("Заполните Tumor!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if ($('nodus').value==null || $('nodus').value=='') {
                    alert("Заполните Nodus!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if ($('metastasis').value==null || $('metastasis').value=='') {
                    alert("Заполните Metastasis!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (!document.getElementsByName("typeFirstOrNot")[0].checked && !document.getElementsByName("typeFirstOrNot")[1].checked) {
                    alert("Заполните, когда было выявлено заболевание!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (patientCategory==-1) {
                    alert("Заполните категорию пациента!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (histologyChb.checked && $('dateBiops').value=='') {
                    alert("Отмечена гистология, введите дату взятия биопсийного материала!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (histologyChb.checked && !histologyChb1.checked && !histologyChb2.checked && !histologyChb3.checked) {
                    alert("Отмечена гистология, выберите гистологический тип/степени дифференцированности!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if((ds.indexOf("C15")!=-1 || ds.indexOf("C16")!=-1 || ds.indexOf("C18")!=-1 || ds.indexOf("C19")!=-1 ||
                    ds.indexOf("C20")!=-1 || ds.indexOf("C25")!=-1 || ds.indexOf("C32")!=-1 || ds.indexOf("C34")!=-1 ||
                    ds.indexOf("C50")!=-1 || ds.indexOf("C53")!=-1 || ds.indexOf("C56")!=-1 || ds.indexOf("C61")!=-1 || ds.indexOf("C67")!=-1)
                    && getValueVocRadiooncoT("epit","vocOncologyN008")==-1) {
                    alert("Для диагнозов С15, С16, С18, С19, С20, С25, С32, С34, С50, С53, С56, С61, С67 указывается, является ли опухоль эпителиальной.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (((ds.indexOf("C15")!=-1 || ds.indexOf("C16")!=-1) && getValueVocRadiooncoT("carc","vocOncologyN008")==-1)) {
                    alert("Для диагнозов С15, С16 (эпителиальная опухоль) указывается, является ли опухоль аденокарциномой.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C34")!=-1 && getValueVocRadiooncoT("melkoklet","vocOncologyN008")==-1) {
                    alert("Для диагноза С34 (эпителиальная опухоль) указывается, является ли опухоль мелкоклеточной.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C44")!=-1 && getValueVocRadiooncoT("bazalklet","vocOncologyN008")==-1) {
                    alert("Для диагноза С44 (эпителиальная опухоль) указывается, является ли опухоль базальноклеточной или плоскоклеточной.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C44")!=-1 && getValueVocRadiooncoT("ploskoklet","vocOncologyN008")==-1) {
                    alert("Для диагноза С44 (эпителиальная опухоль) указывается, является ли опухоль базальноклеточной или плоскоклеточной.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C54")!=-1 && getValueVocRadiooncoT("endom","vocOncologyN008")==-1) {
                    alert("Для диагноза С54 (любой тип опухоли) указывается, является ли опухоль эндометриоидной.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (getValueVocRadiooncoT("endom","vocOncologyN008")==22 && getValueVocRadiooncoT("diff","vocOncologyN008")==-1) {
                    alert("Для эндометриоидной опухоли указывается степень дифференцированности опухоли.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C56")!=-1 && getValueVocRadiooncoT("diff","vocOncologyN008")==-1) {
                    alert("Для диагноза С56 (эпителиальная опухоль) указывается степень дифференцированности опухоли.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C64")!=-1 && getValueVocRadiooncoT("pochech","vocOncologyN008")==-1) {
                    alert("Для диагноза С64 (любой тип опухоли) указывается, является ли опухоль почечноклеточной.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (getValueVocRadiooncoT("pochech","vocOncologyN008")==11 && getValueVocRadiooncoT("svetloklet","vocOncologyN008")==-1) {
                    alert("Для почечноклеточной опухоли указывается, является ли она светлоклеточной.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C73")!=-1 && getValueVocRadiooncoT("rad5","vocOncologyN008")==-1) {
                    alert("Для диагноза С73 (любой тип опухоли) указывается, является ли опухоль папиллярной, фолликулярной, гюртклеточной, медуллярной или анапластической.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C16")!=-1 && getValueVocRadiooncoT("vocOncologyN010_111","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С16 (эпителиальная опухоhistologyChbль) указывается уровень экспрессии белка НЕR2.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if ((ds.indexOf("C18")!=-1 || ds.indexOf("C19")!=-1 || ds.indexOf("C20")!=-1) && getValueVocRadiooncoT("vocOncologyN010_114","vocOncologyN010_11")==-1) {
                    alert("Для диагнозов С18, С19, С20 (эпителиальная опухоль) указывается наличие мутаций в генах RAS и BRAF.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if ((ds.indexOf("C18")!=-1 || ds.indexOf("C19")!=-1 || ds.indexOf("C20")!=-1) && getValueVocRadiooncoT("vocOncologyN010_112","vocOncologyN010_11")==-1) {
                    alert("Для диагнозов С18, С19, С20 (эпителиальная опухоль) указывается наличие мутаций в генах RAS и BRAF.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C34")!=-1 && getValueVocRadiooncoT("vocOncologyN010_115","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С34 (эпителиальная опухоль) указываются наличие мутаций в гене EGFR, наличие транслокации в генах АLK или ROSI, уровень экспрессии белка PD-L1.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C34")!=-1 && getValueVocRadiooncoT("vocOncologyN010_116","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С34 (эпителиальная опухоль) указываются наличие мутаций в гене EGFR, наличие транслокации в генах АLK или ROSI, уровень экспрессии белка PD-L1.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C34")!=-1 && getValueVocRadiooncoT("vocOncologyN010_117","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С34 (эпителиальная опухоль) указываются наличие мутаций в гене EGFR, наличие транслокации в генах АLK или ROSI, уровень экспрессии белка PD-L1.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C43")!=-1 && getValueVocRadiooncoT("vocOncologyN010_112","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С43 указываются наличие мутаций в гене BRAF, наличие мутаций в гене c-Kit.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C43")!=-1 && getValueVocRadiooncoT("vocOncologyN010_113","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С43 указываются наличие мутаций в гене BRAF, наличие мутаций в гене c-Kit.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C50")!=-1 && getValueVocRadiooncoT("vocOncologyN010_118","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С50 (эпителиальная опухоль) указываются наличие рецепторов к эстрогенам, наличие рецепторов к прогестерону, индекс пролиферативной активности экспрессии Ki-67, уровень экспрессии белка HER2, наличие мутаций в генах BRCA.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C50")!=-1 && getValueVocRadiooncoT("vocOncologyN010_119","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С50 (эпителиальная опухоль) указываются наличие рецепторов к эстрогенам, наличие рецепторов к прогестерону, индекс пролиферативной активности экспрессии Ki-67, уровень экспрессии белка HER2, наличие мутаций в генах BRCA.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C50")!=-1 && getValueVocRadiooncoT("vocOncologyN010_1110","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С50 (эпителиальная опухоль) указываются наличие рецепторов к эстрогенам, наличие рецепторов к прогестерону, индекс пролиферативной активности экспрессии Ki-67, уровень экспрессии белка HER2, наличие мутаций в генах BRCA.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C50")!=-1 && getValueVocRadiooncoT("vocOncologyN010_111","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С50 (эпителиальная опухоль) указываются наличие рецепторов к эстрогенам, наличие рецепторов к прогестерону, индекс пролиферативной активности экспрессии Ki-67, уровень экспрессии белка HER2, наличие мутаций в генах BRCA.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (ds.indexOf("C50")!=-1 && getValueVocRadiooncoT("vocOncologyN010_1112","vocOncologyN010_11")==-1) {
                    alert("Для диагноза С50 (эпителиальная опухоль) указываются наличие рецепторов к эстрогенам, наличие рецепторов к прогестерону, индекс пролиферативной активности экспрессии Ki-67, уровень экспрессии белка HER2, наличие мутаций в генах BRCA.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if ($('dateCons').value!='' && getValueVocRadiooncoT('vocOncologyConsilium')==-1) {
                    alert("Заполнена дата проведения консилиума, выберите, что было выполнено на консилиуме.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (getValueVocRadiooncoT('vocOncologyConsilium')!=-1 && $('dateCons').value=='') {
                    alert("Отмечено, что было выполнено на консилиуме. Заполните дату проведения консилиума.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (vocOncologyN013_1.checked && getValueVocRadiooncoT('vocOncologyN014')==-1) {
                    alert("Отмечено хирургическое лечение, выберите подпункт.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (vocOncologyN013_2.checked && getValueVocRadiooncoT('vocOncologyN015')==-1) {
                    alert("Отмечена лекарственная противоопухолевая терапия, выберите подпункт.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (document.getElementById("c1").checked && $('date1').value=='') {
                    alert("Отмечено противопоказание к проведению хирургического лечения, введите дату.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (document.getElementById("c2").checked && $('date2').value=='') {
                    alert("Отмечено противопоказание к проведению химиотерапевтического лечения, введите дату.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (document.getElementById("c3").checked && $('date3').value=='') {
                    alert("Отмечено противопоказание к проведению лучевой терапии, введите дату.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (document.getElementById("c4").checked && $('date4').value=='') {
                    alert("Отмечен отказ от проведения хирургического лечения, введите дату.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (document.getElementById("c5").checked && $('date5').value=='') {
                    alert("Отмечен отказ от проведения химиотерапевтического лечения, введите дату.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (document.getElementById("c6").checked && $('date6').value=='') {
                    alert("Отмечен отказ от проведения лучевой терапии, введите дату.");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if (!vocOncologyN013_1.checked && !vocOncologyN013_2.checked && !document.getElementById('vocOncologyN013_3').checked) {
                    alert("Должен быть выбран тип лечения!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else {
                    $('isFirst').value=document.getElementsByName("typeFirstOrNot")[0].checked;
                    $('vocOncologyReasonTreat').value=patientCategory;
                    //гистология hm=true is hist hm=false id marker
                    histString="";
                    saveHistString(1,"epit",1,"vocOncologyN008");
                    saveHistString(1,"pochech",1,"vocOncologyN008");
                    saveHistString(1,"rad5",1,"vocOncologyN008");
                    saveHistString(1,"endom",1,"vocOncologyN008");
                    saveHistString(1,"carc",1,"vocOncologyN008");
                    saveHistString(1,"svetloklet",2,"vocOncologyN008");
                    saveHistString(1,"melkoklet",2,"vocOncologyN008");
                    saveHistString(1,"bazalklet",2,"vocOncologyN008");
                    saveHistString(1,"ploskoklet",2,"vocOncologyN008");
                    saveHistString(1,"diff",3,"vocOncologyN008");
                    //маркёры
                    for (var i=1; i<=12; i++) saveHistString(2,"vocOncologyN010_11"+i,i,"vocOncologyN010_11");
                    $('histString').value=histString;
                    //консилиум
                    if (getValueVocRadiooncoT('vocOncologyConsilium')!=-1) $('consilium').value=getValueVocRadiooncoT('vocOncologyConsilium','vocOncologyConsilium');
                    //лечение
                    if (getValueVocRadiooncoT('vocOncologyN014')!=-1) {
                        $('surgTreatment').value=getValueVocRadiooncoT('vocOncologyN014','vocOncologyN014');
                        $('typeTreatment').value=document.getElementById("vocOncologyN013_1").value;
                    }
                    if (getValueVocRadiooncoT('vocOncologyN015')!=-1) {
                        $('lineDrugTherapy').value=getValueVocRadiooncoT('vocOncologyN015','vocOncologyN015');
                        $('typeTreatment').value=document.getElementById("vocOncologyN013_2").value;
                    }
                    if (document.getElementById("vocOncologyN013_3").checked) $('typeTreatment').value=document.getElementById("vocOncologyN013_3").value;
                    //отказы и пр-я
                    $('contraString').value="";
                    for (var i=1; i<=6; i++) {
                        if (document.getElementById("c"+i).checked) {
                            $('contraString').value += i;
                            $('contraString').value += "#";
                            $('contraString').value += $('date' + i).value;
                            $('contraString').value += "!";
                        }
                    }
                    document.forms[0].submit();
                }
            }
            //сохранить значение элементов в строку формата атр1#атр2!
            function saveHistString(ht,name,type,voc) {
                var res=getValueVocRadiooncoT(name,voc);
                if (res!=-1) {
                    histString+=ht;
                    histString+="#";
                    histString+=type;
                    histString+="#";
                    histString+=res;
                    histString+="!";
                }
            }
            function getValue(name) {
                var temp = document.getElementById(name);
                return temp.value;
            }
            //обязательные поля - проставить стиль
            if (document.getElementById("stadName")) document.getElementById("stadName").className += " required";
            if (document.getElementById("tumorName")) document.getElementById("tumorName").className += " required";
            if (document.getElementById("nodusName")) document.getElementById("nodusName").className += " required";
            if (document.getElementById("metastasisName")) document.getElementById("metastasisName").className += " required";

            //Milamesher 02102018
            //type - radio/checkbox; vocname - откуда брать данные; div - название контейнера; ids - массив с id checked, disabled, iscode = code or id from voc
            function setRowsToContaineroncoT(type,voc,divId,func,ids,disabled,isCodeorId) {
                var txt="";
                VocService.getAllValueByVocs(voc,isCodeorId,{
                    callback: function(aResult) {
                        var vocRes=JSON.parse(aResult).vocs[0];
                        for (var ind1 = 0; ind1 < vocRes.values.length; ind1++) {
                            var vocVal = vocRes.values[ind1];
                            txt+="<td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">";
                            txt+="<input type='"+type+"' name='" + voc + "' id='" + voc + vocVal.id;
                            if (disabled)   txt += "' disabled="+disabled +  " value='" + vocVal.id + "'";
                            else txt +="' value='" + vocVal.id + "'";
                            if (ids!=null) {
                                for (var i=0; i<ids.length; i++) {
                                    if (ids[i]==vocVal.id) txt +=" checked ";
                                }
                                //if(ids.length==1 && vocVal.id==ids[0])  txt +=" checked ";
                            }
                            if (func!="") txt += " onclick="+func +"(this)>" + vocVal.name; else txt += ">" + vocVal.name;
                            txt+="</td><br>";
                        }
                        document.getElementById(divId).innerHTML+=txt;
                        transform();
                        <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                            loadCaseAll(voc);
                        </msh:ifFormTypeAreViewOrEdit>
                    }});
            }
            //увеличение размеров рб
            function transform() {
                var radios = document.getElementsByTagName('input');
                for (i = 0; i < radios.length; i++) {
                    if (radios[i].type == 'radio'  || radios[i].type == 'checkbox') {
                        if (radios[i].style.margin=='') radios[i].style.margin='5px'; if (radios[i].style.transform=='') radios[i].style.transform = "scale(1.5)";
                    }
                }
            }
            //загрузка всех чекбоксов и рб
            function loadCaseAll(voc) {
                if (voc=='vocOncologyReasonTreat') {
                    if ($('isFirst').value=='true') document.getElementsByName("typeFirstOrNot")[0].checked='checked';
                    else document.getElementsByName("typeFirstOrNot")[1].checked='checked';
                    document.getElementById("vocOncologyReasonTreat"+$('vocOncologyReasonTreat').value).checked='checked';
                    contraChb.checked='checked';
                    checkCheckboxContra();
                    OncologyService.getContras($('id').value, {
                        callback: function (res) {
                            if (res != "##") {
                                var row = res.split("!");
                                for (var i = 0; i < row.length; i++) {
                                    var vals = row[i].split("#");
                                    if (vals[0] != null && vals[0] != '' && vals[1] != null && vals[1] != '' && vals[2] != null && vals[2] != '') {
                                        document.getElementById("c" + vals[0]).checked = 'checked';
                                        <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                                        <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                                            document.getElementById("date" + vals[0]).value = vals[1];
                                            document.getElementById("date" + vals[0] + "ReadOnly").value = vals[2];
                                        </msh:ifFormTypeIsView>
                                        <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                                            document.getElementById("date" + vals[0]).value = vals[2];
                                        </msh:ifFormTypeIsNotView>
                                        </msh:ifFormTypeAreViewOrEdit>

                                    }
                                }
                            }
                            for(var i=1; i<=6; i++) checkDateContra(document.getElementById("c"+i));
                            transform();
                        }});
                    <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                    document.getElementsByName("typeFirstOrNot")[0].setAttribute("disabled",true);
                    document.getElementsByName("typeFirstOrNot")[1].setAttribute("disabled",true);
                    for (var i=0; i<document.getElementById('vocOncologyReasonTreatDiv').childNodes.length; i++)
                        document.getElementById('vocOncologyReasonTreatDiv').childNodes[i].disabled=true;
                    for (var i=0; i<document.getElementById('contraDiv').childNodes.length; i++)
                        document.getElementById('contraDiv').childNodes[i].disabled=true;
                    document.getElementById('contraChb').setAttribute("disabled",true);
                    </msh:ifFormTypeIsView>
                }
                else if (voc=='vocOncologyN008') {
                    histologyChb.checked='checked';
                    immunoGistMarkChb.checked='checked';
                    consiliumChb.checked='checked';
                    checkCheckboxH();
                    checkCheckboxI();
                    checkCheckboxC();
                    <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                    document.getElementById('histologyChb').setAttribute("disabled",true);
                    document.getElementById('immunoGistMarkChb').setAttribute("disabled",true);
                    document.getElementById('consiliumChb').setAttribute("disabled",true);
                    document.getElementById('dateBiops').setAttribute("disabled",true);
                    document.getElementById('histologyChb1').setAttribute("disabled",true);
                    document.getElementById('histologyChb2').setAttribute("disabled",true);
                    document.getElementById('histologyChb3').setAttribute("disabled",true);
                    </msh:ifFormTypeIsView>
                    OncologyService.getHistology($('id').value,{
                        callback : function(res) {
                            if (res!="##") {
                                var row = res.split("!");
                                for (var i = 0; i < row.length-1; i++) {
                                    var vals = row[i].split("#");
                                    var voc = (vals[0]=="1")? 'vocOncologyN008' : 'vocOncologyN010_11';
                                    document.getElementById(voc+vals[1]).checked='checked';
                                }
                                //checked для групп
                                if (getValueVocRadiooncoT("epit","vocOncologyN008")!=-1 || getValueVocRadiooncoT("carc","vocOncologyN008")!=-1 ||
                                    getValueVocRadiooncoT("pochech","vocOncologyN008")!=-1 || getValueVocRadiooncoT("endom","vocOncologyN008")!=-1 ||
                                    getValueVocRadiooncoT("rad5","vocOncologyN008")!=-1) {
                                    document.getElementById('histologyChb1').checked='checked';
                                    <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                                    <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                                    document.getElementById('histologyChb1').removeAttribute("disabled");
                                    setEnabledH(1);
                                    </msh:ifFormTypeIsNotView>
                                    </msh:ifFormTypeAreViewOrEdit>
                                }
                                if (getValueVocRadiooncoT("svetloklet","vocOncologyN008")!=-1 || getValueVocRadiooncoT("melkoklet","vocOncologyN008")!=-1 ||
                                    getValueVocRadiooncoT("bazalklet","vocOncologyN008")!=-1 || getValueVocRadiooncoT("ploskoklet","vocOncologyN008")!=-1) {
                                    document.getElementById('histologyChb2').checked = 'checked';
                                    <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                                    <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                                    document.getElementById('histologyChb2').removeAttribute("disabled");
                                    setEnabledH(2);
                                    </msh:ifFormTypeIsNotView>
                                    </msh:ifFormTypeAreViewOrEdit>
                                }
                                if (getValueVocRadiooncoT("diff","vocOncologyN008")!=-1) {
                                    document.getElementById('histologyChb3').checked = 'checked';
                                    <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                                    <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                                    document.getElementById('histologyChb3').removeAttribute("disabled");
                                    setEnabledH(3);
                                    </msh:ifFormTypeIsNotView>
                                    </msh:ifFormTypeAreViewOrEdit>
                                }
                            }
                            <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                            <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                            for (var i=0; i<document.getElementById('immunoGistMarkDiv').childNodes.length; i++)
                                document.getElementById('immunoGistMarkDiv').childNodes[i].disabled=true;
                            //setEnabledH(2);
                            </msh:ifFormTypeIsView>
                            </msh:ifFormTypeAreViewOrEdit>
                            transform();
                            hideUnChecked();
                        }
                    });
                }
                else if (voc=='vocOncologyConsilium') {
                    if ($('consilium').value!=null && $('consilium').value!=''  && $('consilium').value!='0') document.getElementById("vocOncologyConsilium"+$('consilium').value).checked='checked';
                    <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                    for (var i=0; i<document.getElementById('vocOncologyConsiliumDiv').childNodes.length; i++)
                        document.getElementById('vocOncologyConsiliumDiv').childNodes[i].disabled=true;
                    </msh:ifFormTypeIsView>
                }
                else if (voc=='vocOncologyN013') {
                    <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                    treatmentChb.checked='checked';
                    </msh:ifFormTypeAreViewOrEdit>
                    checkCheckboxT();
                    if ($('typeTreatment').value!=null && $('typeTreatment').value!='' && $('typeTreatment').value!='0') {
                        var val=findAndSetTypeOfTreatment();
                        if (val!=-1) {
                            if (val == '1' && $('surgTreatment').value != null && $('surgTreatment').value != '' && $('surgTreatment').value != '0') {
                                document.getElementById("vocOncologyN014" + $('surgTreatment').value).checked = 'checked';
                                for (var i = 0; i < document.getElementsByName("vocOncologyN014").length; i++) (document.getElementsByName("vocOncologyN014")[i]).removeAttribute("disabled");
                            }
                            if (val == '2' && $('lineDrugTherapy').value != null && $('lineDrugTherapy').value != '' && $('lineDrugTherapy').value != '0') {
                                document.getElementById("vocOncologyN015" + $('lineDrugTherapy').value).checked = 'checked';
                                for (var i = 0; i < document.getElementsByName("vocOncologyN015").length; i++) (document.getElementsByName("vocOncologyN015")[i]).removeAttribute("disabled");
                            }
                        }
                    }
                    <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                    for (var i=0; i<document.getElementById('treatmentDiv').childNodes.length; i++)
                        document.getElementById('treatmentDiv').childNodes[i].disabled=true;
                    document.getElementById('treatmentChb').setAttribute("disabled",true);
                    </msh:ifFormTypeIsView>
                }
            }
            //скрыть ненужное при ред-ии/просмотре
            function hideUnChecked() {
                //если ред-е/просмотр и нет контейнеров - снимаем галочки
                <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                //нет биопсии
                if (getValueVocRadiooncoT("epit","vocOncologyN008")==-1 && getValueVocRadiooncoT("carc","vocOncologyN008")==-1 &&
                    getValueVocRadiooncoT("pochech","vocOncologyN008")==-1 && getValueVocRadiooncoT("endom","vocOncologyN008")==-1 &&
                    getValueVocRadiooncoT("rad5","vocOncologyN008")==-1 && getValueVocRadiooncoT("svetloklet","vocOncologyN008")==-1 &&
                    getValueVocRadiooncoT("melkoklet","vocOncologyN008")==-1 && getValueVocRadiooncoT("bazalklet","vocOncologyN008")==-1 &&
                    getValueVocRadiooncoT("ploskoklet","vocOncologyN008")==-1 && getValueVocRadiooncoT("diff","vocOncologyN008")==-1) {
                    document.getElementById('histologyChb').checked=false;
                    checkCheckboxH();
                }
                //нет маркёров
                if (getValueVocRadiooncoT("vocOncologyN010_111","vocOncologyN010_11")==-1 && getValueVocRadiooncoT("vocOncologyN010_1112","vocOncologyN010_11")==-1 &&
                    getValueVocRadiooncoT("vocOncologyN010_119","vocOncologyN010_11")==-1 && getValueVocRadiooncoT("vocOncologyN010_1110","vocOncologyN010_11")==-1 &&
                    getValueVocRadiooncoT("vocOncologyN010_118","vocOncologyN010_11")==-1 && getValueVocRadiooncoT("vocOncologyN010_114","vocOncologyN010_11")==-1 &&
                    getValueVocRadiooncoT("vocOncologyN010_115","vocOncologyN010_11")==-1 && getValueVocRadiooncoT("vocOncologyN010_116","vocOncologyN010_11")==-1 &&
                    getValueVocRadiooncoT("vocOncologyN010_112","vocOncologyN010_11")==-1 && getValueVocRadiooncoT("vocOncologyN010_113","vocOncologyN010_11")==-1 &&
                    getValueVocRadiooncoT("vocOncologyN010_117","vocOncologyN010_11")==-1) {
                    document.getElementById('immunoGistMarkChb').checked = false;
                    checkCheckboxI();
                }
                //нет консилиума
                if (getValueVocRadiooncoT('vocOncologyConsilium')==-1) {
                    document.getElementById('consiliumChb').checked = false;
                    checkCheckboxC();
                }
                //нет противопоказаний/отказов=
                if (!document.getElementById("c1").checked && !document.getElementById("c2").checked && !document.getElementById("c3").checked &&
                    !document.getElementById("c4").checked && !document.getElementById("c5").checked && !document.getElementById("c6").checked) {
                    document.getElementById('contraChb').checked = false;
                    checkCheckboxContra();
                }
                    </msh:ifFormTypeAreViewOrEdit>
            }
            //проставить доступность у разделов гистологии
            function setEnabledH(t) {
                if (t=='1') {
                    for (var i = 0; i < document.getElementsByName("epit").length; i++) (document.getElementsByName("epit")[i]).removeAttribute("disabled");
                    for (var i = 0; i < document.getElementsByName("carc").length; i++) (document.getElementsByName("carc")[i]).removeAttribute("disabled");
                    for (var i = 0; i < document.getElementsByName("pochech").length; i++) (document.getElementsByName("pochech")[i]).removeAttribute("disabled");
                    for (var i = 0; i < document.getElementsByName("endom").length; i++) (document.getElementsByName("endom")[i]).removeAttribute("disabled");
                    for (var i = 0; i < document.getElementsByName("rad5").length; i++) (document.getElementsByName("rad5")[i]).removeAttribute("disabled");
                }
                else if (t=='2') {
                    for (var i = 0; i < document.getElementsByName("svetloklet").length; i++) (document.getElementsByName("svetloklet")[i]).removeAttribute("disabled");
                    for (var i = 0; i < document.getElementsByName("melkoklet").length; i++) (document.getElementsByName("melkoklet")[i]).removeAttribute("disabled");
                    for (var i = 0; i < document.getElementsByName("bazalklet").length; i++) (document.getElementsByName("bazalklet")[i]).removeAttribute("disabled");
                    for (var i = 0; i < document.getElementsByName("ploskoklet").length; i++) (document.getElementsByName("ploskoklet")[i]).removeAttribute("disabled");
                }
                else if (t=='3') for (var i = 0; i < document.getElementsByName("diff").length; i++) (document.getElementsByName("diff")[i]).removeAttribute("disabled");
            }
            //найти правильный чекбокс в лечении (id - это value)
            function findAndSetTypeOfTreatment() {
                var ind=-1;
                var mas=document.getElementsByName("vocOncologyN013");
                if (mas[0].value==$('typeTreatment').value) ind=1;
                if (mas[1].value==$('typeTreatment').value) ind=2;
                if (mas[2].value==$('typeTreatment').value) ind=3;
                if (ind!=-1) document.getElementById("vocOncologyN013_"+ind).checked='checked';
                return ind;
            }
            //получить значения группы радиобаттонов
            function getValueVocRadiooncoT(name,voc) {
                eval('var chk =  document.forms[0].'+name) ;
                var res=-1;
                for (var i=0; i<chk.length; i++) {
                    if (chk[i].checked) res=chk[i].id.replace(voc,'');
                }
                return res;
            }
            //получить значения группы чекбоксов
            function getValueVocChboncoT(voc) {
                eval('var chk =  document.forms[0].'+voc) ;
                var res=[];
                for (var i=0; i<chk.length; i++) {
                    if (chk[i].checked) res.push(chk[i].id.replace(voc,''));
                }
                return res;
            }
            /*
            * Зашитое в код ввиду нецелесообразности остальных вариантов
            * Получаю voc008 (code,name), innerHtml меняю у divId, ids - значения (checked/не checked), disabled - недоступность, type - тип гистологии (группа на форме)
            * type1,type2,type3 - отметки, что добавили заголовок к типу
            * txt - innerHtml
            * */
            function setHistologyoncoT(voc,divId,ids,txt) {
                VocService.getAllValueByVocs(voc,false, {
                    callback: function (aResult) {
                        setHistologyTypeoncoT(voc,JSON.parse(aResult).vocs[0],divId,ids,1,false,false,false,txt);
                        <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                            loadCaseAll(voc);
                        </msh:ifFormTypeAreViewOrEdit>
                    }
                });
            }
            //гистология - контейнер
            function setHistologyTypeoncoT(voc,vocRes,divId,ids,type,type1,type2,type3,txt) {
                for (var ind1 = 0; ind1 < vocRes.values.length; ind1++) {
                    var vocVal = vocRes.values[ind1];
                    //проверка кодов для типов
                    if (type==1) { //гистологический тип опухоли
                        if (!type1) {
                            txt+="<br><td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">" +
                                "                    <input type=\"checkBox\" name=\"histologyChb1\" id=\"histologyChb1\"> <b>Гистологический тип опухоли:</b>\n" +
                                "                </td><br>";
                            type1=true;
                        }
                        txt += "<td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">";
                        if (vocVal.id == '1' || vocVal.id == '2') {//Эпит. и не эпит - в одну группу радиобаттонов
                            txt += "<input type='" + 'radio' + "' name='" + 'epit' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '2') txt +="<br>";
                        }
                        if (vocVal.id == '24' || vocVal.id == '25') {//аденокарценома/неаденокарценома
                            txt += "<input type='" + 'radio' + "' name='" + 'carc' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '25') txt +="<br>";
                        }
                        if (vocVal.id == '11' || vocVal.id == '12') {//почечно/непочечно
                            txt += "<input type='" + 'radio' + "' name='" + 'pochech' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '12') txt +="<br>";
                        }
                        if (vocVal.id == '22' || vocVal.id == '23') {//эндометриод/неэндометриод
                            txt += "<input type='" + 'radio' + "' name='" + 'endom' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '23') txt +="<br>";
                        }
                        if (vocVal.id == '13' || vocVal.id == '14' || vocVal.id == '15' || vocVal.id == '16' || vocVal.id == '17') {//5 чекбоксов
                            txt += "<input type='" + 'radio' + "' name='" + 'rad5' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            txt +="<br>";
                        }
                    }
                    if (type==2) { //гистологический тип клеток
                        if (!type2) {
                            txt+="<br><td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">\n" +
                                "                    <input type=\"checkBox\" name=\"histologyChb2\" id=\"histologyChb2\"> <b>Гистологический тип клеток:</b>\n" +
                                "                </td><br>";
                            type2=true;
                        }
                        txt += "<td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">";
                        if (vocVal.id == '3' || vocVal.id == '4') {//светлоклет/несветлоклет
                            txt += "<input type='" + 'radio' + "' name='" + 'svetloklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '4') txt +="<br>";
                        }
                        if (vocVal.id == '9' || vocVal.id == '10') {//мелкоклет/немелкоклет
                            txt += "<input type='" + 'radio' + "' name='" + 'melkoklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '10') txt +="<br>";
                        }
                        if (vocVal.id == '18' || vocVal.id == '19') {//базальноклет/небазальноклет
                            txt += "<input type='" + 'radio' + "' name='" + 'bazalklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '19') txt +="<br>";
                        }
                        if (vocVal.id == '20' || vocVal.id == '21') {//плоскоклет/неплоскоклет
                            txt += "<input type='" + 'radio' + "' name='" + 'ploskoklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            if (vocVal.id == '21') txt +="<br>";
                        }
                    }
                    if (type==3) { //степень дифференцированности ткани опухоли
                        if (!type3) {
                            txt+="<br><td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">\n" +
                                "                    <input type=\"checkBox\" name=\"histologyChb3\" id=\"histologyChb3\"> <b>Степень дифференцированности ткани опухоли:</b>\n" +
                                "                </td><br>";
                            type3=true;
                        }
                        txt += "<td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">";
                        if (vocVal.id == '5' || vocVal.id == '6' || vocVal.id == '7' || vocVal.id == '8') {//низко/умеренно/высоко/не опр
                            txt += "<input type='" + 'radio' + "' name='" + 'diff' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name;
                            txt +="<br>";
                        }
                    }
                }
                type++;
                if (type>3) {
                    if (ids!=null) {
                        for (var i=0; i<ids.length; i++) {
                            if (ids[i]==vocVal.id) txt +=" checked ";
                        }
                    }
                    txt+="</td><br>";
                    document.getElementById(divId).innerHTML+=txt;
                    transform();
                    document.getElementById("histologyChb1").oninput= function() {
                        checkHoncoT(voc,1,"histologyChb1");
                    };
                    document.getElementById("histologyChb2").oninput= function() {
                        checkHoncoT(voc,2,"histologyChb2");
                    };
                    document.getElementById("histologyChb3").oninput= function() {
                        checkHoncoT(voc,3,"histologyChb3");
                    };
                    return;
                }
                else setHistologyTypeoncoT(voc,vocRes,divId,ids,type,type1,type2,type3,txt);
            }
            //проставить доступность/недоступность группы в контейнере гистологии
            function  checkHoncoT(voc,type,chbid) {
                var disable = (document.getElementById(chbid).checked) ? false : true;
                if (type == 1) {
                    setDisableEnableChb(voc,"1",disable);
                    setDisableEnableChb(voc,"2",disable);
                    setDisableEnableChb(voc,"24",disable);
                    setDisableEnableChb(voc,"25",disable);
                    setDisableEnableChb(voc,"11",disable);
                    setDisableEnableChb(voc,"12",disable);
                    setDisableEnableChb(voc,"22",disable);
                    setDisableEnableChb(voc,"23",disable);
                    setDisableEnableChb(voc,"13",disable);
                    setDisableEnableChb(voc,"14",disable);
                    setDisableEnableChb(voc,"15",disable);
                    setDisableEnableChb(voc,"16",disable);
                    setDisableEnableChb(voc,"17",disable);
                }
                if (type == 2) {
                    setDisableEnableChb(voc,"3",disable);
                    setDisableEnableChb(voc,"4",disable);
                    setDisableEnableChb(voc,"9",disable);
                    setDisableEnableChb(voc,"10",disable);
                    setDisableEnableChb(voc,"18",disable);
                    setDisableEnableChb(voc,"19",disable);
                    setDisableEnableChb(voc,"20",disable);
                    setDisableEnableChb(voc,"21",disable);
                }
                if (type == 3) {
                    setDisableEnableChb(voc,"5",disable);
                    setDisableEnableChb(voc,"6",disable);
                    setDisableEnableChb(voc,"7",disable);
                    setDisableEnableChb(voc,"8",disable);
                }
            }
            //enabled/disabled
            function setDisableEnableChb(voc,id,disable) {
                if (disable) {
                    document.getElementById(voc + id).setAttribute("disabled", true);
                    document.getElementById(voc + id).checked = false;
                }
                else document.getElementById(voc + id).removeAttribute("disabled");
            }
            //enabled/disabled by name
            /*function setDisableEnableName(name,disable) {
                if (disable) {
                    var mas = document.getElementsByName(name);
                    for (var i=0; i<mas.length; i++) {
                        document.getElementsByName(name)[i].setAttribute("disabled", true);
                        document.getElementsByName(name)[i].checked = false;
                    }
                }
                else {
                    for (var i=0; i<mas.length; i++)
                        document.getElementsByName(name)[i].removeAttribute("disabled");
                }
            }*/
            //контейнер маркеров
            function setRowsToConteinerMarkersoncoT(res,divId,disabled,name,ids) {
                var txt="";
                var row = res.split("!");
                //alert(row+ ' ; '+row.length);
                for(var i=0; i<row.length-1; i++) {
                    var vals = row[i].split("#");
                    //alert(vals[0] + " , " + vals[1] + " , " + vals[2] + " , " + vals[3]);
                    var markercode=vals[0]; var markername=vals[1];
                    var markscode=vals[2].split(','); var marksval = vals[3].split(',');
                    txt+="<label>"+markername+":</label>";
                    txt+="<td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">";
                    for (var j=0; j<markscode.length; j++) {
                        var mcode=markscode[j].replace(" ",""); var mval = marksval[j];
                        txt+="<input type='"+'radio'+"' style=\"margin:4px\" name='" + name + markercode + "' id='" + name + mcode + "'";
                        if (disabled)   txt += " disabled="+disabled;
                        txt +=  " value='" + mcode + "'>"+ mval +"</td>";
                        if (ids!=null) {
                            for (var i=0; i<ids.length; i++) {
                                if (ids[i]==mcode) txt +=" checked ";
                            }
                        }
                    }
                    txt+="<br>";
                }
                document.getElementById(divId).innerHTML+=txt;
            }
            //контейнер проведённого лечения
            //назначенные лекарственные препараты не нужно вообще
            //не нужны лучевая и химиолучевая терапии
            //хирургическое лечение
            function setSurgicalTreatmentoncoT(divId,ids,ids2,ids3,disabled) {
                var txt="";
                var voc='vocOncologyN013_1',voc2='vocOncologyN014';
                OncologyService.getTreatment("n13.id as n13c,n13.name as n13n,n14.id as n14c,n14.name as n14n",
                    "VocOncologyN013 n13,VocOncologyN014 n14", "n13.code='1'", "n14.id",{
                    callback : function(res) {
                        if (res!="##") {
                            var row=res.split("!");
                            mas1=row;
                            for(var i=0; i<row.length-1; i++) {
                                var vals = row[i].split("#");
                                var code,name,id,style;
                                if (i==0) {
                                    code=vals[0]; name=vals[1]; style="\"margin:3px\"";
                                    txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc.replace('_1','') + "' id='" + voc + "'";
                                    if (disabled)   txt += " disabled="+disabled;
                                    txt +=  " value='" + code + "'>"+ name +"</td><br>";
                                    code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                }
                                else {
                                    code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                }
                                txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc2 + "' id='" + voc2 + code + "'";
                                txt += " disabled="+true;
                                txt +=  " value='" + code + "'>"+ name +"</td><br>";
                            }
                            document.getElementById(divId).innerHTML+=txt;
                            drugAgainstTumoroncoT(divId,ids2,ids3,disabled)
                        }
                    }
                });
            }
            //лекарственная противоопухолевая терапия
            function drugAgainstTumoroncoT(divId,ids2,ids3,disabled) {
                var voc="vocOncologyN013_2",voc2='vocOncologyN015';
                OncologyService.getTreatment("n13.id as n13c,n13.name as n13n,n15.id as n15c,n15.name as n15n",
                    "VocOncologyN013 n13,VocOncologyN015 n15","n13.code='2'","n15.id",{
                    callback : function(res) {
                        if (res!="##") {
                            var row=res.split("!");
                            mas2=row;
                            var txt="";
                            for(var i=0; i<row.length-1; i++) {
                                var vals = row[i].split("#");
                                var code,name,id,style;
                                if (i==0) {
                                    code=vals[0]; name=vals[1]; style="\"margin:3px\"";
                                    txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc.replace('_2','') + "' id='" + voc + "'";
                                    if (disabled)   txt += " disabled="+disabled;
                                    txt +=  " value='" + code + "'>"+ name +"</td><br>";
                                    code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                }
                                else {
                                    code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                }
                                txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc2 + "' id='" + voc2 + code + "'";
                                txt += " disabled="+true;
                                txt +=  " value='" + code + "'>"+ name +"</td><br>";
                            }
                            document.getElementById(divId).innerHTML+=txt;
                           //неспец
                            voc="vocOncologyN013_3";
                            var txt="";
                            OncologyService.getTreatment("n13.id as n13c,n13.name as n13n","VocOncologyN013 n13",
                                "n13.code='5'","n13.code='5'",{
                                callback : function(res) {
                                    if (res!="##") {
                                        style="\"margin:3px\"";
                                        var code=res.split("!")[0].split("#")[0], name=res.split("!")[0].split("#")[1];
                                        txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc.replace('_3','') + "' id='" + voc + "'";
                                        if (disabled)   txt += " disabled="+disabled;
                                        txt +=  " value='" + code + "'>"+ name +"</td><br>";
                                        document.getElementById(divId).innerHTML+=txt;
                                    }
                                    transform();
                                    loadCaseAll("vocOncologyN013");
                                    setOnInput();
                                    document.getElementById('treatmentChb').checked=true;
                                    checkCheckboxT();
                                }});
                        }
                    }
                });
            }
            //clean
            //очистить гистологию
            function cleanHistology() {
                if (document.getElementById("histologyChb1")) {
                    document.getElementById("histologyChb1").checked = false;
                    document.getElementById('histologyChb2').checked = false;
                    document.getElementById('histologyChb3').checked = false;
                    checkHoncoT('vocOncologyN008', 1, "histologyChb1");
                    checkHoncoT('vocOncologyN008', 2, "histologyChb2");
                    checkHoncoT('vocOncologyN008', 3, "histologyChb3");
                }
            }
            //очистить иммуног. и маркёры
            function cleanI() {
                for (var i=0; i<immunoGistMarkDiv.childNodes.length; i++) immunoGistMarkDiv.childNodes[i].checked=false;
            }
            //очистить консилиум
            function cleanC() {
                $('dateCons').value='';
                for (var i=0; i<vocOncologyConsiliumDiv.childNodes.length; i++) vocOncologyConsiliumDiv.childNodes[i].checked=false;
            }
            //очистить лечение
            function cleanTreatment() {
                if (document.getElementById('vocOncologyN013_1')) {
                    document.getElementById('vocOncologyN013_1').checked = false;
                    document.getElementById('vocOncologyN013_2').checked = false;
                    document.getElementById('vocOncologyN013_3').checked = false;
                    ch1();
                    ch2();
                }
            }
            //очистить противопоказания/отказы
            function cleanContra() {
                for (var i=1; i<6; i++) {
                    document.getElementById("date"+i).value="";
                    document.getElementById("c"+i).checked=false;
                }
            }
            //set oninput
            function setOnInput() {
                document.getElementById('vocOncologyN013_1').oninput = function () {
                    ch1();ch2();
                };
                document.getElementById("vocOncologyN013_2").oninput = function () {
                    ch1();ch2();
                };
                document.getElementById("vocOncologyN013_3").oninput = function () {
                    ch1();ch2();
                };
            }
            function ch1() {
                var disable = (document.getElementById('vocOncologyN013_1').checked) ? false : true;
                for (var i=0; i<mas1.length-1; i++) {
                    var vals = mas1[i].split("#");
                    setDisableEnableChb('vocOncologyN014',vals[2],disable);
                }
            }
            function ch2() {
                var disable = (document.getElementById("vocOncologyN013_2").checked) ? false : true;
                for (var i=0; i<mas2.length-1; i++) {
                    var vals = mas2[i].split("#");
                    setDisableEnableChb('vocOncologyN015',vals[2],disable);
                }
            }
        </script>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
            <msh:sideMenu guid="sideMenu-123" title="Действия">
                <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-oncology_case_reestr" name="Изменить" roles="/Policy/Mis/Oncology/Case/Edit" />
                <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-oncology_case_reestr" name="Удалить" roles="/Policy/Mis/Oncology/Case/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>