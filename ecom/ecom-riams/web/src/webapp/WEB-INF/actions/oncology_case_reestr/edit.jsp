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
        </style>
    </tiles:put>

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="MedCase" beginForm="oncology_case_reestrForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-oncology_case_reestr.do" defaultField="hello">
            <msh:title>ВЫПИСКА ОБ ОКАЗАНИИ МЕДИЦИНСКОЙ ПОМОЩИ</msh:title>
            <msh:title>ПАЦИЕНТАМ С ОНКОЛОГИЧЕСКИМИ ЗАБОЛЕВАНИЯМИ</msh:title>
            <h2><label id="fio"/></h2>
            <h2><label id="ds"/></h2>
            <msh:checkBox property="suspicionOncologist" label="Подозрение на ЗНО:"/><br>
            <div class="borderedDiv" id="oncologyCase">
                <msh:hidden property="medCase" />
                <msh:hidden property="id" />
                <msh:hidden property="saveType" />
                <msh:hidden property="vocOncologyReasonTreat" />
                <msh:hidden property="histString" />
                <msh:hidden property="surgTreatment" />
                <msh:hidden property="lineDrugTherapy" />
                <msh:hidden property="cycleDrugTherapy" />
                <msh:hidden property="typeTreatment" />
                <msh:hidden property="contraString" />
                <msh:hidden property="typeTreatment" />
                <msh:hidden property="contraString" />
                <msh:hidden property="isFirst" />
                <msh:hidden property="isNauseaAndGagReflexPrev" />
                <msh:hidden property="MKB" />
                <msh:hidden property="allMeds" />
                <div id="vocOncologyReasonTreatDiv">
                    <label><b>Повод обращения (4):</b></label><br>
                </div>
                <msh:row>
                    <msh:autoComplete property="stad" label="Стадия заболевания (2)" vocName="vocOncologyN002parent" parentId="C16" size="25"/>
                </msh:row>
                <msh:separator colSpan="1" label="Стадия заболевания по TNM (3)"/>
                    <msh:autoComplete property="tumor" label="Значение Tumor" vocName="vocOncologyN003parent" parentId="C16" horizontalFill="true" />
                    <msh:autoComplete property="nodus" label="Значение Nodus" vocName="vocOncologyN004parent" parentId="C16" horizontalFill="true" />
                    <msh:autoComplete property="metastasis" label="Значение Metastasis" vocName="vocOncologyN005parent" parentId="C16" horizontalFill="true" />
                <msh:separator colSpan="10" label="." />
                <br><msh:checkBox property="distantMetastasis" label="Наличие отдалённых метастазов (при прогрессировании/рецидиве)"/><br>
                <div id="firstDiv">
                    <label><b>Заболевание выявлено:</b></label>
                    <table><tbody><tr>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeFirstOrNot" value="1"> впервые
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeFirstOrNot" value="2"> ранее
                    </td></tr>
                    </tbody></table>
                </div>
                <label><b>Гистология (5):</b></label>
                <msh:textField property="dateBiops" label="Дата взятия биопсийного материала" fieldColSpan="3"/><br>
                <div class="borderedDiv" id="histologyDiv"> </div>
                <br>
                <label><b>Иммуногистохимия/маркёры (6):</b></label><br>
                <div class="borderedDiv" id="immunoGistMarkDiv"></div>
                <div class="borderedDiv" id="vocOncologyConsiliumDiv">
                    <msh:autoComplete  property="consilium" label="Проведение консилиума (7)" vocName="vocOncologyConsiliumCode" horizontalFill="true" />
                    <br><msh:textField property="dateCons" label="Дата проведения консилиума" fieldColSpan="3"/><br>
                </div>
                <br>
                <label><b>Проведённое лечение (8):</b></label><br>
                <div class="borderedDiv" id="treatmentDiv"></div>
                <br>
                <label><b>Противопоказания и отказы:</b></label><br>
                <div class="borderedDiv" id="contraDiv">
                    <label>Медицинские противопоказания к оказанию медицинской помощи и дата регистрации (11):</label><br>
                    <label><input type="checkBox" name="c1" id="c1" >Противопоказания к проведению хирургического лечения</label>
                    <msh:textField property="date1" label="" /><br>
                    <label><input type="checkBox" name="c2" id="c2" >Противопоказания к проведению химиотерапевтического лечения</label>
                    <msh:textField property="date2" label="" /><br>
                    <label><input type="checkBox" name="c3" id="c3" >Противопоказания к проведению лучевой терапии</label>
                    <msh:textField property="date3" label="" /><br>
                    <label>Отказ от проведения лечения и дата регистрации (12):</label><br>
                    <label><input type="checkBox" name="c4" id="c4" >Отказ от проведения хирургического лечения</label>
                    <msh:textField property="date4" label="" /><br>
                    <label><input type="checkBox" name="c5" id="c5" >Отказ от проведения химиотерапевтического лечения</label>
                    <msh:textField property="date5" label="" /><br>
                    <label><input type="checkBox" name="c6" id="c6" >Отказ от проведения лучевой терапии</label>
                    <msh:textField property="date6" label="" /><br>
                </div>
                <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                    <div>
                        <a id='noteH' href="#bottom" onclick="showNote();" >Показать примечание</a><br>
                        <div id="note" style="display:none;">
                            <b>1</b> Раздел «Направление с целью уточнения диагноза» заполняется при подозрении на злокачественное
                            новообразование.<br>
                            <b>2,3,4</b> Разделы «Стадия заболевания», «Стадия заболевания по ТNМ»,«Повод обращения» заполняются при
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
                            Если повод обращения выбран {0,1,2,3,4}, то поле "Стадия" обязательно для заполнения, в остальных случаях не заполняется.<br>
                            Если повод обращения выбран {0}, то поля "Tumor, Nodus, Metastasis" обязательны для заполнения, в остальных случаях  не заполняются.<br>
                            Атрибут "Наличие отдалённых метастазов (при прогрессировании/рецидиве)" обязателен при Повод обращения = {1,2}, в остальных случаях не заполняется.<br>
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
                <msh:autoComplete property="methodDiagTreat" label="Метод исследования" vocName="vocOncologyMethodDiagTreat" horizontalFill="true"/>
                <msh:autoComplete property="medService" label="Медицинская услуга" vocName="vocMedService" horizontalFill="true" />
            </div>
            <msh:submitCancelButtonsRow colSpan="4" functionSubmit="this.disabled=true; if (document.getElementById('suspicionOncologist').checked) saveDirectionReestr(this); else saveCase(this) ;"/>
            <%
                String mkb=request.getParameter("mkb");
                request.setAttribute("mkb", mkb);
                String actualMsg=request.getParameter("actualMsg");
                request.setAttribute("actualMsg", actualMsg);
                String wasDeleted=request.getParameter("wasDeleted");
                request.setAttribute("wasDeleted", wasDeleted);
            %>
        </msh:form>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
        <script type="text/javascript" >
            //Глобальные массивы
            var requiredStad = [0,1,2,3,4];  //коды поводов обращения, для которых обязательно указание стадии заболевания
            var requiredTNM = [0];  //коды поводов обращения, для которых обязательно указание T N M
            var requiredDistantMts = [1,2];  //коды поводов обращения, для которых обязательно указание Наличие отдалённых метастазов (при прогрессировании/рецидиве)
            var furtherTypeDir=3;  //id типа направления, для которого обязательно заполнение доп. полей
            //нужные дивы
            var suspicionOncologist = document.getElementById("suspicionOncologist");
            var oncologyDirection = document.getElementById("oncologyDirection");
            var oncologyCase = document.getElementById("oncologyCase");
            var histologyDiv = document.getElementById("histologyDiv");
            var immunoGistMarkDiv = document.getElementById("immunoGistMarkDiv");
            var treatmentDiv = document.getElementById("treatmentDiv");
            var disabled=false;
            checkCheckbox();
            checkFurther();
            //стили для красоты
            document.getElementById("date1").style="margin-left:51px";
            document.getElementById("date2").style="";
            document.getElementById("date3").style="margin-left:94px";
            document.getElementById("date4").style="margin-left:122px";
            document.getElementById("date5").style="margin-left:71px";
            document.getElementById("date6").style="margin-left:165px";
            //для экономии времени нужные массивы для лечения
            var mas1=[], mas2=[], mas3=[];
            for(var i=1; i<=6; i++) checkDateContra(document.getElementById("c"+i));
            document.getElementById("c1").onclick=document.getElementById("c2").onclick=
                document.getElementById("c3").onclick=document.getElementById("c4").onclick=
                    document.getElementById("c5").onclick=document.getElementById("c6").onclick=function() {
                        checkDateContra(this);
                    };
            //проставить доступность даты отказа/противопоказания в зависимости от чекбокса
            function checkDateContra(chb) {
                var id=chb.id.replace("c","");
                if (chb.checked) {
                    document.getElementById("date"+id).removeAttribute("disabled");
                    document.getElementById("date"+id).className += " required";
                }
                else {
                    document.getElementById("date"+id).setAttribute("disabled","true");
                    document.getElementById("date"+id).value="";
                    document.getElementById("date"+id).className=document.getElementById("date"+id).className.replace(new RegExp("required","g"),"");
                }
            }
            //показать/скрыть примечание
            function showNote() {
                var note = document.getElementById("note");
                var href = document.getElementById("noteH");
                if (note.style.display=="none") {
                    note.style.display="block";
                    jQuery(href).text("Скрыть примечание");
                }
                else {
                    note.style.display="none";
                    jQuery(href).text("Показать примечание");
                }
            }
            //удалить ненужные элементы, если мы во фрейме
            function deleteIfFrame() {
                //фрейм ли это
                var isFramed = false;
                try {
                    isFramed = window != window.top || document != top.document || self.location != top.location;
                } catch (e) {
                    isFramed = true;
                }
                //удаление ненужных элементов на фрейме
                function deleteChilds(name) {
                    var d = document.getElementById(name);
                    if (d!=null && d.parentNode!=null)
                        d.parentNode.removeChild(d);
                }
                if (isFramed) {
                    deleteChilds('user');
                    deleteChilds('mainmenu');
                    deleteChilds('header');
                    deleteChilds('side');
                    deleteChilds('ideModeMainMenu');
                    deleteChilds('ideModeMainMenuClose');
                    deleteChilds('footer');
                    if (document.getElementsByClassName('titleTrail').length>0) {
                        var d = document.getElementsByClassName('titleTrail')[0];
                        if (d!=null && d.parentNode!=null)
                            d.parentNode.removeChild(d);
                    }
                }
                //не закрываю сразу, т.к. фрейм можно закрыть только из родителя.
            }
            //GLOBALS
            var histString="";
            <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                disabled=true;
                deleteIfFrame();
            </msh:ifFormTypeIsView>
            <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
            var isFramed = false;
            try {
                isFramed = window != window.top || document != top.document || self.location != top.location;
            } catch (e) {
                isFramed = true;
            }
            if (isFramed) //чтобы кнопка была большая, как кнопка Закрыть в тэге
                document.getElementById('submitButton').style.fontSize='25px';
            </msh:ifFormTypeIsNotView>
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
                        setRowsToContaineroncoT('checkbox','vocOncologyTypeDirection','vocOncologyTypeDirectionDiv','',res.split("#"),true,true);
                    }
                }});
            //получить метод исследования и услугу
            OncologyService.getMethodAndService(${param.id}, {
                callback : function(res) {
                    if (res!="") {
                        var mas=res.split('#');
                        if (mas[0]!=null && mas[1]!=null && mas[3]!=null && mas[4]!=null && mas[2]!=null &&
                            mas[0]!='' && mas[1]!='' && mas[3]!='' && mas[4]!='' && mas[2]!='' &&
                            mas[0]!='null' && mas[1]!='null' && mas[3]!='null' && mas[4]!='null' && mas[2]!='null') {
                            $('methodDiagTreat').value =mas[0];
                            $('medService').value =mas[1];
                            if ($('methodDiagTreatName')) $('methodDiagTreatName').value =mas[2];
                            if ($('medServiceName')) $('medServiceName').value =mas[3];
                            if ($('methodDiagTreatReadOnly')) $('methodDiagTreatReadOnly').value =mas[2];
                            if ($('medServiceReadOnly')) $('medServiceReadOnly').value =mas[3];
                        }
                    }
                }});
            </msh:ifFormTypeAreViewOrEdit>
            //проставить видимость дивов в зависимости от чекбоксов
           suspicionOncologist.onclick = function() {
                checkCheckbox();
            };
            function load1() {
                <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
                if (document.getElementById('vocOncologyTypeDirection1')==null) {
                    setRowsToContaineroncoT('checkbox', 'vocOncologyTypeDirection', 'vocOncologyTypeDirectionDiv', '', null, true, true, false);
                    $('date').value = getCurrentDate();
                }
                </msh:ifFormTypeIsCreate>
                }
            function load2() {
                document.getElementById('vocOncologyReasonTreatDiv').innerHTML='<label><b>Повод обращения (4):</b></label><br>';
                histologyDiv.innerHTML='';
                immunoGistMarkDiv.innerHTML='';
                treatmentDiv.innerHTML='';
                if (document.getElementById("vocOncologyReasonTreat8")==null) {
                    setRowsToContaineroncoT('radio', 'vocOncologyReasonTreat', 'vocOncologyReasonTreatDiv', funcSetStadReauired, null, true, true, true);
                    setHistologyoncoT('vocOncologyN008', 'histologyDiv', null, histologyDiv.innerHTML);

                    OncologyService.getMarkersAndMarks({
                        callback: function (res) {
                            if (res != "##") {
                                setRowsToConteinerMarkersoncoT(res, 'immunoGistMarkDiv', 'vocOncologyN010_11', null);
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
                } else {
                    oncologyDirection.style.display  = "none";
                    oncologyCase.style.display  = "block";
                    load2();

                }
            }
            <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
            $('dateCons').disabled=true;
            $('dateBiops').disabled=true;
            document.getElementsByName("typeFirstOrNot")[1].checked=true;
            document.getElementById("stadName").disabled=true;
            document.getElementById("tumorName").disabled=true;
            document.getElementById("nodusName").disabled=true;
            document.getElementById("metastasisName").disabled=true;
            document.getElementById("distantMetastasis").disabled=true;
            </msh:ifFormTypeIsCreate>
            <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
             consiliumAutocomplete.addOnChangeCallback(function() {
                 checkDateCons();
            });
            </msh:ifFormTypeIsNotView>
            //консилиум
            function checkDateCons() {
                if ($('consiliumName')!=null) {
                    if ($('consiliumName').value.length > 0 && $('consiliumName').value[0] != '0') {
                        $('dateCons').disabled = false;
                        document.getElementById("dateCons").className += " required";
                    }
                    else {
                        $('dateCons').disabled = true;
                        $('dateCons').value = "";
                        document.getElementById("dateCons").className = document.getElementById("dateCons").className.replace(new RegExp("required", "g"), "");
                    }
                }
            }
            //проставить диагноз и ФИО пациента
            function setDs(medCaseId) {
                <%
                String mkb=request.getParameter("mkb");
                if (mkb!=null && mkb.length()>5) {
                    String[] masMkb = mkb.split(" ");
                    if (masMkb.length>0)
                        request.setAttribute("mkb", masMkb[0]);
                }
                %>
                //Если онкологическая форма создаётся по кнопке - '${mkb}' будет пустым, иначе - заполнен пока ещё не созданным осн. вып.
                OncologyService.getFIODsPatient(medCaseId,'${mkb}', {
                    callback : function(res) {
                        var mas=res.split("#");
                        if (mas[0]!='' && mas[1]!=''  && mas[2]!='') {
                            document.getElementById("fio").innerHTML="Ф.И.О. пациента " + mas[0];
                            document.getElementById("ds").innerHTML="Диагноз (по МКБ-10): " + mas[1] + mas[2].replace(mas[1],'');
                            $('MKB').value=mas[1];
                            suspicionOncologist.checked= !($('MKB').value.indexOf('C')!=-1);
                            if (suspicionOncologist.checked) checkCheckbox();
                            suspicionOncologist.disabled=true;
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
                        else {
                            alert('Нет основного диагноза в случае! Создание онкологической формы невозможно.');
                            window.location.href='entityParentView-stac_ssl.do?id='+${param.id};
                        }
                    }});
            }
            <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
            //если меняли диагноз с С на не С
            if ('${wasDeleted}'!='' && '${mkb}'[0]!='C') {
                showToastMessage('Созданная и неактуальная онкологическая форма была удалена. Если необходимо, можно создать подозрение на ЗНО. Если нет - просто закрыть вкладку.', null, true);
                document.getElementById('cancelButton').onclick=function () {window.location.href='entityParentView-stac_ssl.do?id='+$('medCase').value;} //чтобы назад в СЛС
                deleteIfFrame();
            }
            else if ('${wasDeleted}'!='' && '${mkb}'[0]=='C') {
                showToastMessage('Подозрение на ЗНО, связанное с неактуальным диагнозом, было удалено. Необходимо создать случай онкологического лечения.', null, true);
                deleteIfFrame();
            }
            setDs(${param.id});
            </msh:ifFormTypeIsCreate>
            <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
            //if ('${mkb}'=='')- простое редактирование формы
            ////если нет - то актуализация формы, в случае, когда форма создана с другим диагнозом, а в выписке указан новый
            //Варианты
            //- * на C - удалить направление (и связанное с ним) и открыть создание полного варианта формы
            //- C на * - удалить форму (и связанное) и открыть создание направления
            //- * на * - открыть направление на редактирование
            //- C на C - актуализация формы - того, что зависит от диагноза
            //- обычное редактирование и вариант с актуализацией формы при смене C на другой C

            OncologyService.getDsWithName(${param.id},'${mkb}', {
                callback : function(res) {
                    if (res!=null && res!='{}') {
                        var aResult = JSON.parse(res);
                        if (typeof aResult.pat!=='undefined' && typeof aResult.oldmkb!=='undefined') {
                            document.getElementById("fio").innerHTML = "Ф.И.О. пациента " + aResult.pat;
                            var isActual = (typeof aResult.newmkb!=='undefined' && aResult.newmkb!=aResult.oldmkb && aResult.newmkb!='');
                            var mkb = ((typeof aResult.newmkb!=='undefined' && aResult.newmkb!=aResult.oldmkb || aResult.newmkb!=''))? aResult.oldmkb: aResult.newmkb;
                            document.getElementById("ds").innerHTML = "Диагноз (по МКБ-10): " + mkb;
                            <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                            var ind = mkb.indexOf(' ');
                            if (ind != -1) {
                                stadAutocomplete.setParentId(mkb.substring(0, ind));
                                tumorAutocomplete.setParentId(mkb.substring(0, ind));
                                nodusAutocomplete.setParentId(mkb.substring(0, ind));
                                metastasisAutocomplete.setParentId(mkb.substring(0, ind));
                            }
                            else {
                                stadAutocomplete.setParentId(mkb);
                                tumorAutocomplete.setParentId(mkb);
                                nodusAutocomplete.setParentId(mkb);
                                metastasisAutocomplete.setParentId(mkb);
                            }
                            if (isActual) actualForm(aResult.oldmkb,aResult.newmkb);
                            else if (!isActual && '${actualMsg}'!='') { //актуализация в случае перехода после href=
                                HospitalMedCaseService.getMedcaseDtypeById($('medCase').value, {
                                    callback: function (resType) {
                                        if (resType == '0') {
                                            showToastMessage('${actualMsg}', null, true);
                                            $('MKB').value='${mkb}';
                                            setDs($('medCase').value);
                                            deleteIfFrame();
                                        }
                                    }});
                            }
                            else {  //актуализация в случае изменения диагноза через Диагноз - Редактировать
                                HospitalMedCaseService.getMedcaseDtypeById($('medCase').value, {
                                    callback: function (resType) {
                                        if (resType == '0') {
                                            OncologyService.checkDiagnosisOnkoForm($('medCase').value, mkb, {  //получить текуший основной выписной или последний диагноз СЛО
                                                callback: function (res2) {
                                                    if (res2 != '' && res2 != '0') {
                                                        var mas = res2.split("#");
                                                        OncologyService.getFIODsPatient($('medCase').value, '', {
                                                            callback: function (res3) {
                                                                var mas3 = res3.split("#");
                                                                if (mas3[0] != '' && mas3[1] != '' && mas3[2] != '') {
                                                                    window.location.href = "entityEdit-oncology_case_reestr.do?id=" + $('id').value + "&actualMsg=" + mas[0];
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }});
                            }
                            </msh:ifFormTypeIsNotView>
                            suspicionOncologist.disabled=true;
                        }
                        else {
                            alert('Нет диагноза, с которым была создана форма!');
                            window.location.href = 'entityParentView-stac_ssl.do?id=' + $('medCase').value
                        }
                    }
                    else {
                        alert('Не удалось найти онкологическую форму! Добавьте основной выписной диагноз (через Диагно - Добавить) и создайте онкологическую форму через Создать случай!');
                        window.location.href = 'entityParentView-stac_ssl.do?id=' + $('medCase').value
                    }
                }});
            //актуализация формы
            function actualForm(oldmkb,newmkb) {
                HospitalMedCaseService.getMedcaseDtypeById($('medCase').value, {
                    callback: function (resType) {
                        if (resType == '0') {
                            var msg='Диагноз меняется с ' + oldmkb + ' на ' + newmkb;
                            if (oldmkb[0]=='C' && newmkb[0]=='C') {
                                $('stad').value=$('stadName').value='';
                                $('tumor').value=$('tumorName').value='';
                                $('nodus').value=$('nodusName').value='';
                                $('metastasis').value=$('metastasisName').value='';
                                $('MKB').value='${mkb}';
                                msg+=". Заполните заново стадию и TNM, которые зависят от диагноза.";
                            }
                            else if (oldmkb[0]!='C' && newmkb[0]!='C'){
                                suspicionOncologist.checked=true;  //редактирование направления
                                msg+=". Отредактируйте направление, если необходимо. Сохраните его.";
                            }
                            else if (oldmkb[0]!='C' && newmkb[0]=='C'){
                                OncologyService.deleteDirectionsByCase($('id').value,{
                                    callback: function (res) {
                                        msg+=". Созданные направления в подозрении на ЗНО удалены. Создайте онкологическую форму.";
                                        window.location.href = "entityParentPrepareCreate-oncology_case_reestr.do?id=" + $('medCase').value + "&wasDeleted=1" + "&mkb="+newmkb;
                                    }
                                });
                            }
                            else if (oldmkb[0]=='C' && newmkb[0]!='C'){
                                OncologyService.deleteAllByCase($('id').value,{
                                    callback: function () {
                                        window.location.href = "entityParentPrepareCreate-oncology_case_reestr.do?id=" + $('medCase').value + "&wasDeleted=1" + "&mkb="+newmkb;
                                    }
                                });
                            }
                            showToastMessage(msg, null, true);
                            $('MKB').value='${mkb}';
                            setDs($('medCase').value);
                            deleteIfFrame();
                        }
                    }
                });
            }
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
                else if($('vocOncologyTypeDirection'+furtherTypeDir) && $('vocOncologyTypeDirection'+furtherTypeDir).checked
                    && $('methodDiagTreat').value=='') {
                    alert("Заполните метод исследования!");
                    btn.removeAttribute("disabled");
                    btn.value='Создать';
                }
                else if($('vocOncologyTypeDirection'+furtherTypeDir) && $('vocOncologyTypeDirection'+furtherTypeDir).checked
                    && $('medService').value=='') {
                    alert("Заполните медицинскую услугу!");
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
                            methodDiagTreat: $('methodDiagTreat').value,
                            medService: $('medService').value,
                            medCase:getValue("medCase"),
                            date:$('date').value,
                            id:${param.id}
                        };
                        mas.list.push(obj);
                    }
                    var json = JSON.stringify(mas);
                    <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                    <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                    OncologyService.editDirectionsByCase(${param.id},json,$('MKB').value,{
                            callback : function(retId) {
                                location.href = "entityView-oncology_case_reestr.do?id="+retId;
                            }
                        });
                    </msh:ifFormTypeIsNotView>
                    </msh:ifFormTypeAreViewOrEdit>
                    <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
                        OncologyService.insertDirection(json,"", $('MKB').value,{
                            callback : function(retId) {
                                location.href = "entityView-oncology_case_reestr.do?id="+retId;
                            }
                        });
                    </msh:ifFormTypeIsCreate>
                }
            }
            //сохранить случай
            function saveCase(btn) {
                //тип лечения обязателен только для стационара
                HospitalMedCaseService.getMedcaseDtypeById($('medCase').value, {
                    callback: function (resType) {
                        var histologyChb1 = document.getElementById("histologyChb1");
                        var histologyChb2 = document.getElementById("histologyChb2");
                        var histologyChb3 = document.getElementById("histologyChb3");
                        var vocOncologyN013_1 = document.getElementById("vocOncologyN013_1");
                        var vocOncologyN013_2 = document.getElementById("vocOncologyN013_2");
                        var vocOncologyN013_4 = document.getElementById("vocOncologyN013_4");
                        var ds = document.getElementById("ds").innerHTML.replace("Диагноз (по МКБ-10): ", "");
                        ds = ds.substring(0, ds.indexOf(" "));
                        btn.value = 'Создание...';
                        var patientCategory = getValueVocRadiooncoT('vocOncologyReasonTreat', 'vocOncologyReasonTreat');
                        if (patientCategory == -1) {
                            alert("Заполните категорию пациента!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (requiredStad.include(patientCategory) && ($('stad').value == null || $('stad').value == '')) {
                            alert("Заполните стадию заболевания! Для данного повода обращения она обязательна!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (requiredTNM.include(patientCategory) && ($('tumor').value == null || $('tumor').value == '')) {
                            alert("Заполните Tumor! Для данного повода обращения обязательно!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (requiredTNM.include(patientCategory) && ($('nodus').value == null || $('nodus').value == '')) {
                            alert("Заполните Nodus! Для данного повода обращения обязательно!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (requiredTNM.include(patientCategory) && ($('metastasis').value == null || $('metastasis').value == '')) {
                            alert("Заполните Metastasis! Для данного повода обращения обязательно!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (!document.getElementsByName("typeFirstOrNot")[0].checked && !document.getElementsByName("typeFirstOrNot")[1].checked) {
                            alert("Заполните, когда было выявлено заболевание!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if ((histologyChb1.checked || histologyChb2.checked || histologyChb3.checked) && $('dateBiops').value == '') {
                            alert("Отмечена гистология, введите дату взятия биопсийного материала!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if ($('dateBiops').value != '' && !histologyChb1.checked && !histologyChb2.checked && !histologyChb3.checked) {
                            alert("Введена дата гистологии, выберите гистологический тип/степени дифференцированности!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        /*else if((ds.indexOf("C15")!=-1 || ds.indexOf("C16")!=-1 || ds.indexOf("C18")!=-1 || ds.indexOf("C19")!=-1 ||
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
                            alert("Для диагноза С16 (эпителиальная опухоль) указывается уровень экспрессии белка НЕR2.");
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
                        }*/
                        else if ($('consilium').value == '') {
                            alert("Введите данные о консилиуме!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if ($('consiliumName').value.length > 0 && $('consiliumName').value[0] != '0' && $('dateCons').value == '') {
                            alert("Отмечено, что было выполнено на консилиуме. Заполните дату проведения консилиума.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (vocOncologyN013_4.checked && getValueVocRadiooncoT('vocOncologyN015') == -1) {
                            alert("Отмечена лекарственная противоопухолевая терапия, выберите линию терапии.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (vocOncologyN013_4.checked && getValueVocRadiooncoT('vocOncologyN016') == -1) {
                            alert("Отмечена лекарственная противоопухолевая терапия, выберите цикл линии.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (vocOncologyN013_4.checked && getValueVocRadiooncoT('vocOncologyN016') == -1) {
                            alert("Отмечена лекарственная противоопухолевая терапия, выберите цикл линии.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (vocOncologyN013_4.checked && !checkMeds()) {
                            alert("Отмечена лекарственная противоопухолевая терапия. Заполните минимум один медикамент (перпарат и дата являются обязательными)!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (document.getElementById("c1").checked && $('date1').value == '') {
                            alert("Отмечено противопоказание к проведению хирургического лечения, введите дату.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (document.getElementById("c2").checked && $('date2').value == '') {
                            alert("Отмечено противопоказание к проведению химиотерапевтического лечения, введите дату.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (document.getElementById("c3").checked && $('date3').value == '') {
                            alert("Отмечено противопоказание к проведению лучевой терапии, введите дату.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (document.getElementById("c4").checked && $('date4').value == '') {
                            alert("Отмечен отказ от проведения хирургического лечения, введите дату.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (document.getElementById("c5").checked && $('date5').value == '') {
                            alert("Отмечен отказ от проведения химиотерапевтического лечения, введите дату.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (document.getElementById("c6").checked && $('date6').value == '') {
                            alert("Отмечен отказ от проведения лучевой терапии, введите дату.");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (!vocOncologyN013_1.checked && !vocOncologyN013_2.checked && !document.getElementById('vocOncologyN013_3').checked && !document.getElementById('vocOncologyN013_4').checked && resType=='0') {
                            alert("Должен быть выбран тип лечения!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else if (vocOncologyN013_1.checked && getValueVocRadiooncoT('vocOncologyN014','vocOncologyN014')==-1) {
                            alert("Выбрано хирургическое лечение, выберите уточнение!");
                            btn.removeAttribute("disabled");
                            btn.value = 'Создать';
                        }
                        else {
                            $('isFirst').value = document.getElementsByName("typeFirstOrNot")[0].checked;
                            $('vocOncologyReasonTreat').value = patientCategory;
                            //гистология hm=true is hist hm=false id marker
                            histString = "";
                            saveHistString(1, "epit", 1, "vocOncologyN008");
                            saveHistString(1, "pochech", 1, "vocOncologyN008");
                            saveHistString(1, "rad5", 1, "vocOncologyN008");
                            saveHistString(1, "endom", 1, "vocOncologyN008");
                            saveHistString(1, "carc", 1, "vocOncologyN008");
                            saveHistString(1, "svetloklet", 2, "vocOncologyN008");
                            saveHistString(1, "melkoklet", 2, "vocOncologyN008");
                            saveHistString(1, "bazalklet", 2, "vocOncologyN008");
                            saveHistString(1, "ploskoklet", 2, "vocOncologyN008");
                            saveHistString(1, "diff", 3, "vocOncologyN008");
                            //маркёры
                            for (var i = 1; i <= 12; i++) saveHistString(2, "vocOncologyN010_11" + i, i, "vocOncologyN010_11");
                            $('histString').value = histString;
                            //лечение
                            if (getValueVocRadiooncoT('vocOncologyN014') != -1) {
                                $('surgTreatment').value = getValueVocRadiooncoT('vocOncologyN014', 'vocOncologyN014');
                                $('typeTreatment').value = document.getElementById("vocOncologyN013_1").value;
                            }
                            else $('surgTreatment').value = null;
                            if (getValueVocRadiooncoT('vocOncologyN015') != -1) {
                                $('lineDrugTherapy').value = getValueVocRadiooncoT('vocOncologyN015', 'vocOncologyN015');
                                $('typeTreatment').value = document.getElementById("vocOncologyN013_4").value;
                            }//если не выбрана, затираем подпункты
                            else $('lineDrugTherapy').value = null;
                            if (getValueVocRadiooncoT('vocOncologyN016') != -1) {
                                $('cycleDrugTherapy').value = getValueVocRadiooncoT('vocOncologyN016', 'vocOncologyN016');
                                $('typeTreatment').value = document.getElementById("vocOncologyN013_4").value;
                                $('isNauseaAndGagReflexPrev').value = document.getElementsByName("typeNauseaOrNot")[0].checked;
                            }
                            else {
                                $('cycleDrugTherapy').value = null;
                                $('isNauseaAndGagReflexPrev').value = null;
                            }
                            if (document.getElementById("vocOncologyN013_2").checked) $('typeTreatment').value = document.getElementById("vocOncologyN013_2").value;
                            if (document.getElementById("vocOncologyN013_3").checked) $('typeTreatment').value = document.getElementById("vocOncologyN013_3").value;
                            //отказы и пр-я
                            $('contraString').value = "";
                            for (var i = 1; i <= 6; i++) {
                                if (document.getElementById("c" + i).checked) {
                                    $('contraString').value += i;
                                    $('contraString').value += "#";
                                    $('contraString').value += $('date' + i).value;
                                    $('contraString').value += "!";
                                }
                            }
                            //медикаменты
                            if (document.getElementById("vocOncologyN013_4").checked) {  //проверяем на клиенте, теперь не надо очищать и проверять на сервере
                                var table = document.getElementById('allMedTble');
                                $('allMeds').value = "";
                                var mas = {
                                    list: []
                                };
                                var voc = 'vocOncologyN020';
                                for (var i = 0; i < table.rows.length; i++) {
                                    var ii = table.rows[i].id.replace('row', '');
                                    if (typeof $('' + voc + ii) !== 'undefined') {
                                        var obj = {
                                            med: $('' + voc + ii).value,
                                            date: $('dateSt' + ii).value
                                        };
                                        mas.list.push(obj);
                                    }
                                }
                                $('allMeds').value = JSON.stringify(mas);
                            }
                            document.forms[0].submit();
                        }
                    }
                });
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
            //Если повод обращения выбран {0,1,2,3,4}, то поле "Стадия" обязательно для заполнения, в остальных случаях  не заполняется.
            function funcSetStadReauired(rb) {
                var checkVal=(rb.id.substring(0,2)!='td')? rb.value:rb.id.replace('tdvocOncologyReasonTreat','');
                if (rb.id.substring(0,2)=='td')
                if (document.getElementById("stadName")) {
                    if (requiredStad.include(checkVal)) {
                        document.getElementById("stadName").className += " required";
                        document.getElementById("stadName").disabled = false;
                    }
                    else {
                        document.getElementById("stadName").className=document.getElementById("stadName").className.replace(new RegExp("required","g"),"");
                        $('stadName').value='';
                        document.getElementById("stadName").disabled=true;
                    }
                }
                funcSetTNMReauired(checkVal);
                funcSetOtdMtsEnabled(checkVal);
            }
            //Если повод обращения выбран {0}, то поля "Tumor, Nodus, Metastasis" обязательны для заполнения, в остальных случаях  не заполняются.
            function funcSetTNMReauired(checkVal) {
                if (document.getElementById("tumorName")) {
                    if (requiredTNM.include(checkVal)) {
                        document.getElementById("tumorName").className += " required";
                        document.getElementById("tumorName").disabled = false;
                    }
                    else {
                        document.getElementById("tumorName").className=document.getElementById("tumorName").className.replace(new RegExp("required","g"),"");
                        $('tumorName').value='';
                        document.getElementById("tumorName").disabled=true;
                    }
                }
                if (document.getElementById("nodusName")) {
                    if (requiredTNM.include(checkVal)) {
                        document.getElementById("nodusName").className += " required";
                        document.getElementById("nodusName").disabled = false;
                    }
                    else {
                        document.getElementById("nodusName").className = document.getElementById("nodusName").className.replace(new RegExp("required", "g"), "");
                        $('nodusName').value='';
                        document.getElementById("nodusName").disabled=true;
                    }
                }
                if (document.getElementById("metastasisName")) {
                    if (requiredTNM.include(checkVal)) {
                        document.getElementById("metastasisName").className += " required";
                        document.getElementById("metastasisName").disabled = false;
                    }
                    else {
                        document.getElementById("metastasisName").className=document.getElementById("metastasisName").className.replace(new RegExp("required","g"),"");
                        $('metastasisName').value='';
                        document.getElementById("metastasisName").disabled=true;
                    }
                }
            }
            document.getElementById("distantMetastasis").setAttribute("disabled",true);
            if (document.getElementById("consiliumName")) document.getElementById("consiliumName").className += " required";
            //Атрибут "Наличие отдалённых метастазов (при прогрессировании/рецидиве)" доступен при Повод обращения = {1,2},  в остальных случаях не заполняется.
            function funcSetOtdMtsEnabled(checkVal) {
                if (document.getElementById("distantMetastasis")) {
                    if (requiredDistantMts.include(checkVal)) {
                        document.getElementById("distantMetastasis").removeAttribute("disabled");
                        document.getElementById("distantMetastasis").disabled = false;
                    }
                    else {
                        document.getElementById("distantMetastasis").setAttribute("disabled", true);
                        document.getElementById("distantMetastasis").checked=false;
                        document.getElementById("distantMetastasis").disabled=true;
                    }
                }
                funcSetTNMReauired(checkVal);
            }
            //Milamesher 02102018
            //type - radio/checkbox; vocname - откуда брать данные; div - название контейнера; ids - массив с id checked, disabled, iscode = code or id from voc
            function setRowsToContaineroncoT(type,voc,divId,func,ids,isCodeorId,groupByCode) {
                var txt="";
                OncologyService.getVocInJson(voc,isCodeorId,groupByCode,{
                    callback: function(aResult) {
                        var vocRes=JSON.parse(aResult);
                        txt+="<table><tbody>";
                        for (var ind1 = 0; ind1 < vocRes.length; ind1++) {
                            txt+"<tr>";
                            var vocVal = vocRes[ind1];
                            txt+="<td id='td" + voc + vocVal.id + "' colspan=\"1\">";
                            txt+="<input type='"+type+"' name='" + voc + "' id='" + voc + vocVal.id;
                            txt +="' value='" + vocVal.id + "'";
                            if (ids!=null) {
                                for (var i=0; i<ids.length; i++) {
                                    if (ids[i]==vocVal.id) txt +=" checked ";
                                }
                            }
                            txt += ">";
                            txt+=(voc=='vocOncologyTypeDirection')?
                                "<label onclick=\"if (document.getElementById('"+voc+vocVal.id+"')) " +
                                "document.getElementById('"+voc+vocVal.id +"').click(); checkFurther();\" id='lbl"+voc+vocVal.id +"'>" + vocVal.name + "</label>"
                                : vocVal.name;
                            txt+="</td><tr>";
                        }
                        txt+="</tbody><table>";
                        document.getElementById(divId).innerHTML+=txt;
                        if (voc=='vocOncologyTypeDirection') {
                            <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                            var inputs = document.getElementsByTagName('input');
                            for (i = 0; i < inputs.length; i++) {
                                if (inputs[i].type == 'checkbox' && inputs[i].name=='vocOncologyTypeDirection')
                                    inputs[i].disabled = true;
                            }
                            </msh:ifFormTypeIsView>
                            document.getElementById(voc+furtherTypeDir).onclick= function() {
                                 checkFurther();
                             }
                        }
                        if (func!='') {
                            for (var ind1 = 0; ind1 < vocRes.length; ind1++) {
                                var id = vocRes[ind1].id;
                                if (document.getElementById(voc+id)) document.getElementById(voc+id).onclick =  function () {
                                    func(this);
                                };
                                if (document.getElementById('td'+voc+id)) document.getElementById('td'+voc+id).onclick =  function () {
                                    this.childNodes[0].checked='checked';
                                    func(this);
                                };
                            }
                        }
                        transform();
                        checkFurther();
                        <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                            loadCaseAll(voc);
                        </msh:ifFormTypeAreViewOrEdit>
                    }});
            }
            //проверка на доп. поля направления
            function checkFurther() {
                <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                if ($('vocOncologyTypeDirection'+furtherTypeDir) && $('vocOncologyTypeDirection'+furtherTypeDir).checked) {
                    $('methodDiagTreat').hidden=false;
                    $('medService').hidden=false;
                    $('methodDiagTreatName').hidden=false;
                    $('medServiceName').hidden=false;
                    $('methodDiagTreatLabel').hidden=false;
                    $('medServiceLabel').hidden=false;
                    $('methodDiagTreatName').className += " required";
                    $('medServiceName').className += " required";
                }
                else {
                    $('methodDiagTreat').value='';
                    $('medService').value='';
                    $('methodDiagTreatName').value='';
                    $('medServiceName').value='';
                    $('methodDiagTreat').hidden=true;
                    $('medService').hidden=true;
                    $('methodDiagTreatName').hidden=true;
                    $('medServiceName').hidden=true;
                    $('methodDiagTreatLabel').hidden=true;
                    $('medServiceLabel').hidden=true;
                    $('methodDiagTreatName').className = document.getElementById("methodDiagTreatName").className.replace(new RegExp("required", "g"), "");
                    $('medServiceName').className = document.getElementById("medServiceName").className.replace(new RegExp("required", "g"), "");
                }
                </msh:ifFormTypeIsNotView>
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
            function loadvocOncologyReasonTreat() {
                if ($('isFirst').value == 'true') document.getElementsByName("typeFirstOrNot")[0].checked = 'checked';
                else document.getElementsByName("typeFirstOrNot")[1].checked = 'checked';
                //vocOncologyReasonTreat - по id получить code
                if ($('vocOncologyReasonTreat').value != '') {
                    OncologyService.getCodeOncology($('vocOncologyReasonTreat').value, 'vocOncologyReasonTreat', {
                        callback: function (res) {
                            if (res != '') {
                                $('vocOncologyReasonTreat').value = res.split('#')[0];
                                if (document.getElementById("vocOncologyReasonTreat" + $('vocOncologyReasonTreat').value)) {
                                    document.getElementById("vocOncologyReasonTreat" + $('vocOncologyReasonTreat').value).checked = 'checked';
                                    funcSetStadReauired(document.getElementById("vocOncologyReasonTreat" + $('vocOncologyReasonTreat').value));
                                    document.getElementById("vocOncologyReasonTreat" + $('vocOncologyReasonTreat').value).click();
                                }
                            }
                        }
                    });
                }
            }
            function loadConsilium() {
                //voconcologyconsilium
                if ($('consilium').value!='') {
                    OncologyService.getCodeOncology($('consilium').value, 'voconcologyconsilium', {
                        callback: function (res) {
                            if (res != '')
                                $('consilium').value = res.split('#')[0];
                            var name=res.split('#')[0]+' '+res.split('#')[1];
                            <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                            var indexSpace = name.indexOf(' ');
                            if (indexSpace != -1) name=name.substring(indexSpace+1);
                            </msh:ifFormTypeIsView>
                            if ($('consiliumName')!=null) {
                                $('consiliumName').value = name;
                                $('dateCons').disabled=false;
                                document.getElementById("dateCons").className += " required";
                            }
                            else if (!$('consiliumReadOnly')) {
                                <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                                    document.getElementById('vocOncologyConsiliumDiv').innerHTML = document.getElementById('vocOncologyConsiliumDiv').innerHTML
                                        .replace(document.getElementById('consiliumLabel').innerHTML + '</span>', document.getElementById('consiliumLabel').innerHTML + '</span>' +
                                            '<input title="' + name + ' " class="viewOnly" id="consiliumReadOnly" name="consiliumReadOnly" size="75" readonly="readonly" value="' + name + '">');
                                    var dateConstmp = $('dateCons').value.split("-");
                                    if ($('dateConsReadOnly') && dateConstmp!='') {
                                        var d1 = new Date(dateConstmp[0], dateConstmp[1] - 1, dateConstmp[2]);
                                        $('dateConsReadOnly').value=d1.toLocaleDateString();
                                }
                                </msh:ifFormTypeIsView>
                            }
                            else if ($('consiliumReadOnly')!=null) {
                                <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                                $('consiliumReadOnly').value=name;
                                </msh:ifFormTypeIsView>
                            }
                            checkDateCons();
                        }
                    });
                }
            }
            function loadContras() {
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
                        <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                        disableAll();
                        </msh:ifFormTypeIsView>
                    }});
            }
            function loadHist() {
                OncologyService.getHistology($('id').value,{
                    callback : function(res) {
                        if (res!="##") {
                            var row = res.split("!");
                            for (var i = 0; i < row.length-1; i++) {
                                var vals = row[i].split("#");
                                var voc = (vals[0]=="1")? 'vocOncologyN008' : 'vocOncologyN010_11';
                                if (document.getElementById(voc+vals[1])) document.getElementById(voc+vals[1]).checked='checked';
                            }
                            //checked для групп
                            if (getValueVocRadiooncoT("epit","vocOncologyN008")!=-1 || getValueVocRadiooncoT("carc","vocOncologyN008")!=-1 ||
                                getValueVocRadiooncoT("pochech","vocOncologyN008")!=-1 || getValueVocRadiooncoT("endom","vocOncologyN008")!=-1 ||
                                getValueVocRadiooncoT("rad5","vocOncologyN008")!=-1) {
                                document.getElementById('histologyChb1').checked='checked';
                                setEnabledH(1);
                            }
                            if (getValueVocRadiooncoT("svetloklet","vocOncologyN008")!=-1 || getValueVocRadiooncoT("melkoklet","vocOncologyN008")!=-1 ||
                                getValueVocRadiooncoT("bazalklet","vocOncologyN008")!=-1 || getValueVocRadiooncoT("ploskoklet","vocOncologyN008")!=-1) {
                                document.getElementById('histologyChb2').checked = 'checked';
                                setEnabledH(2);
                            }

                            if (getValueVocRadiooncoT("diff","vocOncologyN008")!=-1) {
                                document.getElementById('histologyChb3').checked = 'checked';
                                setEnabledH(3);
                            }
                        }
                        transform();
                        setDateBiopsRequired();
                        <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
                        disableAll();
                        </msh:ifFormTypeIsView>
                    }
                });
            }
            function loadTreatment() {
                if ($('typeTreatment').value!=null && $('typeTreatment').value!='' && $('typeTreatment').value!='0') {
                    var val=findAndSetTypeOfTreatment();
                    if (val!=-1) {
                        if (val == '1' && $('surgTreatment').value != null && $('surgTreatment').value != '' && $('surgTreatment').value != '0') {
                            if (document.getElementById("vocOncologyN014" + $('surgTreatment').value))
                                document.getElementById("vocOncologyN014" + $('surgTreatment').value).checked = 'checked';
                            for (var i = 0; i < document.getElementsByName("vocOncologyN014").length; i++) (document.getElementsByName("vocOncologyN014")[i]).removeAttribute("disabled");
                        }
                        if (val == '4' && $('lineDrugTherapy').value != null && $('lineDrugTherapy').value != '' && $('lineDrugTherapy').value != '0') {
                            document.getElementById("vocOncologyN015" + $('lineDrugTherapy').value).checked = 'checked';
                            if (document.getElementById("vocOncologyN016" + $('cycleDrugTherapy').value))
                                document.getElementById("vocOncologyN016" + $('cycleDrugTherapy').value).checked = 'checked';
                            for (var i = 0; i < document.getElementsByName("vocOncologyN015").length; i++) (document.getElementsByName("vocOncologyN015")[i]).removeAttribute("disabled");
                            for (var i = 0; i < document.getElementsByName("vocOncologyN016").length; i++) (document.getElementsByName("vocOncologyN016")[i]).removeAttribute("disabled");
                            if (document.getElementById('nauseaDiv')) document.getElementById('nauseaDiv').removeAttribute('hidden');
                            <msh:ifFormTypeIsNotView formName="oncology_case_reestrForm">
                            for (var i = 0; i < document.getElementsByName("typeNauseaOrNot").length; i++) (document.getElementsByName("typeNauseaOrNot")[i]).removeAttribute("disabled");
                            </msh:ifFormTypeIsNotView>
                            if ($('isNauseaAndGagReflexPrev').value=="true") document.getElementsByName("typeNauseaOrNot")[0].checked = 'checked';
                            else document.getElementsByName("typeNauseaOrNot")[1].checked = 'checked';
                            //медикаменты
                            <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                            OncologyService.getMedsJson(${param.id}, {
                                callback: function (aResult) {
                                    var res=JSON.parse(aResult);
                                    for (var ind1 = 0; ind1 < res.length; ind1++) {
                                        var med = res[ind1];
                                        createRowMed(ind1-1);
                                        var voc='vocOncologyN020';
                                        $(voc+ind1).value=med.vdid;
                                        $(voc+ind1+'Name').value=med.name;
                                        $('dateSt'+ind1).value=med.date;
                                        checkEnableAdd(ind1);
                                    }
                                }
                            });
                             </msh:ifFormTypeAreViewOrEdit>
                        }
                    }
                }
                else  //создание
                    createRowMed(-1);
            }
            //загрузка всех чекбоксов и рб
            function loadCaseAll(voc) {
                if (voc=='vocOncologyReasonTreat') {
                    loadvocOncologyReasonTreat();
                    loadConsilium();
                    loadContras();
                }
                else if (voc=='vocOncologyN008') loadHist();
                else if (voc=='vocOncologyN013') loadTreatment();
            }
            //найти правильный чекбокс в лечении (id - это value)
            function findAndSetTypeOfTreatment() {
                var ind=-1;
                var mas=document.getElementsByName("vocOncologyN013");
                if (mas[0].value==$('typeTreatment').value) ind=1;
                if (mas[1].value==$('typeTreatment').value) ind=4;
                if (mas[2].value==$('typeTreatment').value) ind=2;
                if (mas[3].value==$('typeTreatment').value) ind=3;
                if (ind!=-1 &&  document.getElementById("vocOncologyN013_"+ind)) document.getElementById("vocOncologyN013_"+ind).checked='checked';
                return ind;
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
                OncologyService.getVocInJson(voc,true,true, {
                    callback: function (aResult) {
                        setHistologyTypeoncoT(voc,JSON.parse(aResult),divId,ids,1,false,false,false,txt);
                        <msh:ifFormTypeAreViewOrEdit formName="oncology_case_reestrForm">
                            loadCaseAll(voc);
                        </msh:ifFormTypeAreViewOrEdit>
                    }
                });
            }
            //гистология - контейнер
            function setHistologyTypeoncoT(voc,vocRes,divId,ids,type,type1,type2,type3,txt) {
                for (var ind1 = 0; ind1 < vocRes.length; ind1++) {
                    var vocVal = vocRes[ind1];
                    //проверка кодов для типов
                    if (type==1) { //гистологический тип опухоли
                        if (!type1) {
                            txt+="<td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">" +
                                "                    <input type=\"checkBox\" name=\"histologyChb1\" id=\"histologyChb1\"> " +
                                "<label onclick=\"document.getElementById('histologyChb1').click();\"><b>Гистологический тип опухоли:</b></label>\n" +
                                "                </td></tr>";
                            txt+="<table><tbody><tr>";
                            type1=true;
                        }
                        var tmpFlag=false; //флаг, что заполнили строку значениями
                        txt += "#<td onclick=\"if (document.getElementById('histologyChb1').checked) this.childNodes[0].checked='checked';\" colspan=\"1\">#";
                        if (vocVal.id == '1' || vocVal.id == '2') {//Эпит. и не эпит - в одну группу радиобаттонов
                            txt += "<input type='" + 'radio' + "' name='" + 'epit' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '2') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (vocVal.id == '24' || vocVal.id == '25') {//аденокарценома/неаденокарценома
                            txt += "<input type='" + 'radio' + "' name='" + 'carc' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '25') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (vocVal.id == '11' || vocVal.id == '12') {//почечно/непочечно
                            txt += "<input type='" + 'radio' + "' name='" + 'pochech' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '12') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (vocVal.id == '22' || vocVal.id == '23') {//эндометриод/неэндометриод
                            txt += "<input type='" + 'radio' + "' name='" + 'endom' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '23') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (vocVal.id == '13' || vocVal.id == '14' || vocVal.id == '15' || vocVal.id == '16' || vocVal.id == '17') {//5 чекбоксов
                            txt += "<input type='" + 'radio' + "' name='" + 'rad5' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (tmpFlag)
                            txt=txt.replace("#<td onclick","<td onclick").replace("colspan=\"1\">#","colspan=\"1\">");
                        else
                            txt=txt.replace("#<td onclick=\"if (document.getElementById('histologyChb1').checked) this.childNodes[0].checked='checked';\" colspan=\"1\">#","");
                    } else if (type==2) { //гистологический тип клеток
                        if (!type2) {
                            txt+="</tbody></table>";
                            txt+="<td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">\n" +
                                "                    <input type=\"checkBox\" name=\"histologyChb2\" id=\"histologyChb2\"> " +
                                "<label onclick=\"document.getElementById('histologyChb2').click();\"><b>Гистологический тип клеток:</b></label>\n" +
                                "                </td></tr>";
                            txt+="<table><tbody><tr>";
                            type2=true;
                        }
                        var tmpFlag=false;
                        txt += "#<td onclick=\"if (document.getElementById('histologyChb2').checked) this.childNodes[0].checked='checked';\" colspan=\"1\">#";
                        if (vocVal.id == '3' || vocVal.id == '4') {//светлоклет/несветлоклет
                            txt += "<input type='" + 'radio' + "' name='" + 'svetloklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '4') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (vocVal.id == '9' || vocVal.id == '10') {//мелкоклет/немелкоклет
                            txt += "<input type='" + 'radio' + "' name='" + 'melkoklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '10') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (vocVal.id == '18' || vocVal.id == '19') {//базальноклет/небазальноклет
                            txt += "<input type='" + 'radio' + "' name='" + 'bazalklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '19') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (vocVal.id == '20' || vocVal.id == '21') {//плоскоклет/неплоскоклет
                            txt += "<input type='" + 'radio' + "' name='" + 'ploskoklet' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            if (vocVal.id == '21') txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (tmpFlag)
                            txt=txt.replace("#<td onclick","<td onclick").replace("colspan=\"1\">#","colspan=\"1\">");
                        else
                            txt=txt.replace("#<td onclick=\"if (document.getElementById('histologyChb2').checked) this.childNodes[0].checked='checked';\" colspan=\"1\">#","");
                    } else if (type==3) { //степень дифференцированности ткани опухоли
                        if (!type3) {
                            txt+="</tbody></table>";
                            txt+="<tr><td onclick=\"this.childNodes[1].checked='checked';\" colspan=\"1\">\n" +
                                "                    <input type=\"checkBox\" name=\"histologyChb3\" id=\"histologyChb3\">" +
                                "<label onclick=\"document.getElementById('histologyChb3').click();\"> <b>Степень дифференцированности ткани опухоли:</b></label>\n" +
                                "                </td></tr>";
                            txt+="<table><tbody><tr>";
                            type3=true;
                        }
                        var tmpFlag=false;
                        txt += "#<td onclick=\"if (document.getElementById('histologyChb3').checked) this.childNodes[0].checked='checked';\" colspan=\"1\">#";
                        if (vocVal.id == '5' || vocVal.id == '6' || vocVal.id == '7' || vocVal.id == '8') {//низко/умеренно/высоко/не опр
                            txt += "<input type='" + 'radio' + "' name='" + 'diff' + "' id='" + voc + vocVal.id;
                            txt += "' value='" + vocVal.id + "' style=\"margin:6px;margin-left:12px;\" disabled=true" + "'>" + vocVal.name + "</td>";
                            txt +="</tr>";
                            tmpFlag=true;
                        }
                        if (tmpFlag)
                            txt=txt.replace("#<td onclick","<td onclick").replace("colspan=\"1\">#","colspan=\"1\">");
                        else
                            txt=txt.replace("#<td onclick=\"if (document.getElementById('histologyChb3').checked) this.childNodes[0].checked='checked';\" colspan=\"1\">#","");
                    }
                }
                type++;
                if (type>3) {
                    if (ids!=null) {
                        for (var i=0; i<ids.length; i++) {
                            if (ids[i]==vocVal.id) txt +=" checked ";
                        }
                    }
                    txt+="</td><tr>";
                    document.getElementById(divId).innerHTML+=txt;
                    transform();
                    document.getElementById("histologyChb1").onclick= function() {
                        checkHoncoT(voc,1,"histologyChb1");setDateBiopsRequired();
                    };
                    document.getElementById("histologyChb2").onclick= function() {
                        checkHoncoT(voc,2,"histologyChb2");setDateBiopsRequired();
                    };
                    document.getElementById("histologyChb3").onclick= function() {
                        checkHoncoT(voc,3,"histologyChb3");setDateBiopsRequired();
                    };
                }
                else setHistologyTypeoncoT(voc,vocRes,divId,ids,type,type1,type2,type3,txt);
            }
            //Дата биопсии обязательна при выбранной биопсии
            function setDateBiopsRequired() {
                if (document.getElementById('histologyChb1').checked || document.getElementById('histologyChb2').checked
                    || document.getElementById('histologyChb3').checked) {
                    $('dateBiops').disabled = false;
                    document.getElementById("dateBiops").className += " required";
                }
                else {
                    $('dateBiops').disabled = true;
                    $('dateBiops').value = "";
                    document.getElementById("dateBiops").className = document.getElementById("dateBiops").className.replace(new RegExp("required", "g"), "");
                }
            }
            //проставить доступность/недоступность группы в контейнере гистологии
            function  checkHoncoT(voc,type,chbid) {
                var disable = (!document.getElementById(chbid).checked);
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
                } else if (type == 2) {
                    setDisableEnableChb(voc,"3",disable);
                    setDisableEnableChb(voc,"4",disable);
                    setDisableEnableChb(voc,"9",disable);
                    setDisableEnableChb(voc,"10",disable);
                    setDisableEnableChb(voc,"18",disable);
                    setDisableEnableChb(voc,"19",disable);
                    setDisableEnableChb(voc,"20",disable);
                    setDisableEnableChb(voc,"21",disable);
                } else if (type == 3) {
                    setDisableEnableChb(voc,"5",disable);
                    setDisableEnableChb(voc,"6",disable);
                    setDisableEnableChb(voc,"7",disable);
                    setDisableEnableChb(voc,"8",disable);
                }
            }
            //enabled/disabled
            function setDisableEnableChb(voc,id,disable) {
                if (document.getElementById(voc + id)) {
                    if (disable) {
                        document.getElementById(voc + id).setAttribute("disabled", true);
                        document.getElementById(voc + id).checked = false;
                    }
                    else document.getElementById(voc + id).removeAttribute("disabled");
                }
            }
            //контейнер маркеров
            function setRowsToConteinerMarkersoncoT(res,divId,name,ids) {
                var txt="<table><tbody>";
                var row = res.split("!");
                for(var i=0; i<row.length-1; i++) {
                    txt+="<tr>";
                    var vals = row[i].split("#");
                    var markercode=vals[0]; var markername=vals[1];
                    var markscode=vals[2].split(','); var marksval = vals[3].split(',');
                    txt+="<td><label>"+markername+":</label></td>";
                    for (var j=0; j<markscode.length; j++) {
                        var mcode=markscode[j].replace(" ",""); var mval = marksval[j];
                        txt+="<td align='left' onclick=\"this.childNodes[0].checked='checked'\">" +
                            "<input type='"+'radio'+"' style=\"margin:4px\" name='" + name + markercode + "' id='" + name + mcode + "'";
                        txt +=  " value='" + mcode + "'>"+ mval;// +"</td>";
                        if (ids!=null) {
                            for (var i=0; i<ids.length; i++) {
                                if (ids[i]==mcode) txt +=" checked ";
                            }
                        }
                        txt+="</td>";
                    }
                    txt+="</tr>";
                }
                txt+="</tbody></table><br><input type='button' value='Очистить' onclick='cleanI();'/>";
                document.getElementById(divId).innerHTML=txt;
            }
            //контейнер проведённого лечения
            //назначенные лекарственные препараты не нужно вообще
            //не нужны лучевая и химиолучевая терапии
            //хирургическое лечение
            function setSurgicalTreatmentoncoT(divId,ids,ids2,ids3,disabled) {
                var txt="<table><tbody>";
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
                                var outerId='';
                                if (i==0) {
                                    txt+="<td id='td"+voc+"' onclick=\"this.childNodes[0].checked='checked'; ch1(); ch2();\" colspan=\"1\">";
                                    code=vals[0]; name=vals[1]; style="\"margin:3px\"";
                                    txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc.replace('_1','') + "' id='" + voc + "'";
                                    if (disabled)   txt += " disabled="+disabled;
                                    txt +=  " value='" + code + "'><b>"+ name+"</b></td></tr>";
                                    code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                } else {
                                    code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                }
                                txt+="<tr><td onclick=\"if (document.getElementById('vocOncologyN013_1') && document.getElementById('vocOncologyN013_1').checked) this.childNodes[0].checked='checked';\"><input type='"+'radio'+"' style=" + style + " name='" + voc2 + "' id='" + voc2 + code + "'";
                                txt += " disabled="+true;
                                txt +=  " value='" + code + "'>"+ name +"</td></tr>";
                            }
                            txt+="</tbody></table>";
                            document.getElementById(divId).innerHTML+=txt;
                            drugAgainstTumoroncoT(divId,ids2,ids3,disabled);
                        }
                    }
                    });
            }
            //диагностика и неспец. лечение
            function justAnotheroncoT(divId,ids2,ids3,disabled) {
                //неспец и диагностика
                var txt="<br><tbody><table>";
                var voc="vocOncologyN013_2";
                OncologyService.getTreatment("n13.id as n13c,n13.name as n13n","VocOncologyN013 n13",
                    "n13.code in ('5' ,'6')","n13.code",{
                        callback : function(res) {
                            if (res!="##") {
                                var row=res.split("!");
                                for(var i=0; i<row.length-1; i++) {
                                    txt+="<td onclick=\"this.childNodes[0].checked='checked'; ch1(); ch2();\" colspan=\"1\">";
                                    voc=(i==0)? "vocOncologyN013_2":"vocOncologyN013_3";
                                    var style="\"margin:3px\"";
                                    var code=res.split("!")[i].split("#")[0], name=res.split("!")[i].split("#")[1];
                                    txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc.replace('_3','').replace('_2','') + "' id='" + voc + "'";
                                    if (disabled)   txt += " disabled="+disabled;
                                    txt +=  " value='" + code + "'><b>"+ name +"</b></td><tr>";
                                }
                            }
                            txt+="</tbody></table>";
                            document.getElementById(divId).innerHTML+=txt;
                            transform();
                            loadCaseAll("vocOncologyN013");
                            setOnInput();
                        }});
            }
            //лекарственная противоопухолевая терапия
            function drugAgainstTumoroncoT(divId,ids2,ids3,disabled) {
                var txt="<table><tbody>";
                var voc="vocOncologyN013_4",voc2='vocOncologyN015';
                OncologyService.getTreatment("n13.id as n13c,n13.name as n13n,n15.id as n15c,n15.name as n15n",
                    "VocOncologyN013 n13,VocOncologyN015 n15","n13.code='2' and (n13.finishdate is null or n13.finishdate < current_date)  and (n15.finishdate is null or n15.finishdate < current_date) ","n15.id",{
                        callback : function(res) {
                            if (res!="##") {
                                var row=res.split("!");
                                mas2=row;
                                for(var i=0; i<row.length-1; i++) {
                                    var vals = row[i].split("#");
                                    var code,name,id,style;
                                    if (i==0) {
                                        txt+="<td id='td"+voc+"' onclick=\"this.childNodes[0].checked='checked'; ch1(); ch2();\" colspan=\"1\">";
                                        code=vals[0]; name=vals[1]; style="\"margin:3px\"";
                                        txt+="<input type='"+'radio'+"' style=" + style + " name='" + voc.replace('_4','') + "' id='" + voc + "'";
                                        if (disabled)   txt += " disabled="+disabled;
                                        txt +=  " value='" + code + "'><b>"+ name+"</b></td></tr>";
                                        code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                        txt+="<tr><td><label style=\"\"margin:6px;margin-left:12px;\"><u>Линия лекарственной терапии:</u><br></label></td></tr>";
                                    } else {
                                        code=vals[2]; name=vals[3]; style="\"margin:6px;margin-left:12px;\""
                                    }
                                    txt+="<tr><td onclick=\"if (document.getElementById('vocOncologyN013_4') && document.getElementById('vocOncologyN013_4').checked) this.childNodes[0].checked='checked';\"><input type='"+'radio'+"' style=" + style + " name='" + voc2 + "' id='" + voc2 + code + "'";
                                    txt += " disabled="+true;
                                    txt +=  " value='" + code + "'>"+ name +"</td></tr>";
                                }
                                txt+="</tbody></table><br>";
                                document.getElementById(divId).innerHTML+=txt;
                                drugCycles(divId,ids2,ids3,disabled);
                            }
                        }
                    });
            }
            //циклы лекарств. терапии
            function drugCycles(divId,ids2,ids3,disabled) {
                var txt="<table><tbody>";
                var voc="vocOncologyN013_4",voc2='vocOncologyN016';
                OncologyService.getTreatment("n16.id as n16c,n16.name as n16n",
                    "VocOncologyN013 n13,VocOncologyN016 n16","n13.code='2' and (n16.finishdate is null or n16.finishdate < current_date) ","n16.id",{
                        callback : function(res) {
                            if (res!="##") {
                                var row=res.split("!");
                                mas3=row;
                                txt+="<tr><td><label style=\"\"margin:6px;margin-left:12px;\"><u>Цикл лекарственной терапии:</u></label></td></tr>";
                                for(var i=0; i<row.length-1; i++) {
                                    var vals = row[i].split("#");
                                    var code,name,id,style;
                                    code=vals[0]; name=vals[1]; style="\"margin:6px;margin-left:12px;\""
                                    txt+="<tr><td onclick=\"if (document.getElementById('vocOncologyN013_4') && document.getElementById('vocOncologyN013_4').checked) this.childNodes[0].checked='checked';\"><input type='"+'radio'+"' style=" + style + " name='" + voc2 + "' id='" + voc2 + code + "'";
                                    txt += " disabled="+true;
                                    txt +=  " value='" + code + "'>"+ name +"</td></tr>";
                                }
                                txt+="</tbody></table>";
                                txt+="<div hidden id=\"nauseaDiv\">" +
                                    "<label style=\"margin:3px\"><u>Признак проведения профилактики тошноты и рвотного рефлекса:</u></label>" +
                                    "<table><tbody><tr>" +
                                    "<td onclick=\"this.childNodes[0].checked='checked';\" colspan=\"1\">" +
                                    "<input  style=\"margin:6px;margin-left:12px;\" type=\"radio\" name=\"typeNauseaOrNot\" value=\"1\"> Да" +
                                    "</td>" +
                                    "<td onclick=\"this.childNodes[0].checked='checked';\" colspan=\"2\">" +
                                    "<input  style=\"margin:6px;margin-left:12px;\" type=\"radio\" name=\"typeNauseaOrNot\" value=\"2\"> Нет" +
                                    "</td></tr>" +
                                    "</tbody></table><br><label><u>Введённые препараты:<u></label><br>" +
                                    "<table id=\"allMedTble\"></table>"+
                                    "</div>";
                                document.getElementById(divId).innerHTML+=txt;
                                <msh:ifFormTypeIsCreate formName="oncology_case_reestrForm">
                                document.getElementsByName("typeNauseaOrNot")[1].checked=true;
                                </msh:ifFormTypeIsCreate>
                                justAnotheroncoT(divId,ids2,ids3,disabled);
                            }
                        }
                    });

            }
            //очистить иммуног. и маркёры
            function cleanI() {
                var radios = document.getElementsByTagName('input');
                for (var i = 0; i < radios.length; i++)
                    if (radios[i].type == 'radio' && radios[i].id.indexOf('vocOncologyN010_11')!=-1)
                        radios[i].checked=false;
            }
            //set onclick
            function setOnInput() {
                document.getElementById('vocOncologyN013_1').onclick = function () {
                    ch1(); ch2();
                };
                document.getElementById("vocOncologyN013_2").onclick = function () {
                    ch1(); ch2();
                };
                document.getElementById("vocOncologyN013_3").onclick = function () {
                    ch1(); ch2();
                };
                document.getElementById("vocOncologyN013_4").onclick = function () {
                    ch1(); ch2();
                };
            }
            function ch1() {
                var disable = (!document.getElementById('vocOncologyN013_1').checked);
                for (var i=0; i<mas1.length-1; i++) {
                    var vals = mas1[i].split("#");
                    setDisableEnableChb('vocOncologyN014',vals[2],disable);
                }
                if (!document.getElementById('vocOncologyN013_4').checked) {
                    document.getElementsByName("typeNauseaOrNot")[1].checked = 'checked';
                    $('isNauseaAndGagReflexPrev').value=false;
                }
            }
            function ch2() {
                var disable = (document.getElementById("vocOncologyN013_4").checked) ? false : true;
                for (var i=0; i<mas2.length-1; i++) {
                    var vals = mas2[i].split("#");
                    setDisableEnableChb('vocOncologyN015',vals[2],disable);
                }
                for (var i=0; i<mas3.length-1; i++) {
                    var vals = mas3[i].split("#");
                    setDisableEnableChb('vocOncologyN016',vals[0],disable);
                }
                if (!disable) document.getElementById('nauseaDiv').removeAttribute('hidden');
                else document.getElementById('nauseaDiv').setAttribute('hidden',true);
                if ($('isNauseaAndGagReflexPrev').value=="true") document.getElementsByName("typeNauseaOrNot")[0].checked = 'checked';
                else document.getElementsByName("typeNauseaOrNot")[1].checked = 'checked';
                if (document.getElementById("vocOncologyN013_4").checked && $('vocOncologyN0200')==null)
                    createRowMed(-1);
            }
            //Скрыть див для редактирования
            function disableAll() {
                var divToDisable = (suspicionOncologist.checked)? '#oncologyDirection' : '#oncologyCase';
                jQuery(divToDisable).fadeTo('slow',.6);
                jQuery(divToDisable).append('<div style="position: absolute;bottom:0;left:0;width: 100%;height:98%;z-index:2;opacity:0.4;filter: alpha(opacity = 50)"></div>');
            }
            //Добавление строки с медикаментом
            function createRowMed(ii) {
                var rowFromCopy=ii; //по умолчанию - ii-1
                ii=+ii;
                ii=getNextId(ii);
                var voc='vocOncologyN020';
                var table = document.getElementById('allMedTble');
                var tr = document.createElement('tr');
                tr.id='row'+ii;
                var td1 = document.createElement('td');
                var td2 = document.createElement('td');
                var td3 = document.createElement('td');
                var td4 = document.createElement('td');
                var td5 = document.createElement('td');
                td1.innerHTML="<div><input type=\"hidden\" size=\"1\" name=\""+voc+"\" id=\"" + voc +
                    +ii+"\" value=\"\"><input title=\""+voc+ii+"\" type=\"text\" name=\"" + voc +
                    +ii+"Name\" id=\""+voc+ii+"Name\" size=\"70\" class=\"autocomplete horizontalFill required\" " +
                    "autocomplete=\"off\"><div id=\""+voc+ii+"Div\" style=\"visibility: hidden; display: none;\" " +
                    "class=\"autocomplete\"></div></div>";
                td2.innerHTML="<label id=\"dateSt"+ii+"Label\" for=\"dateSt"+ii+"\">Дата&nbsp;введения:</label>" +
                    "<input title=\"Дата&nbsp;введенияNoneField\" class=\" required\" id=\"dateSt"+ii+"\" name=\"dateSt"+ii+"\" size=\"10\" value=\"\" type=\"text\" autocomplete=\"off\">";
                if (ii!=0) td5.innerHTML="<input id=\"btnDel"+ii+"\" type=\"button\" value=\"-\" onclick=\"delRow(this);\">";
                td5.setAttribute("width","25px");
                td5.setAttribute("align","right");
                td3.innerHTML="<input  disabled=\"true\" id=\"btnAdd"+ii+"\" type=\"button\" value=\"+\" onclick=\"addRowAfter(this);\">";
                td4.innerHTML="<input  disabled=\"true\" id=\"btnAddDbl"+ii+"\" type=\"button\" value=\"++\" onclick=\"addRowDblAfter(this);\">";
                tr.appendChild(td1); tr.appendChild(td2);
                tr.appendChild(td3); tr.appendChild(td4); tr.appendChild(td5);
                table.appendChild(tr);
                eval("var "+voc+ii+"Autocomplete = new msh_autocomplete.Autocomplete()") ;
                eval(voc+ii+"Autocomplete.setUrl('simpleVocAutocomplete/"+voc+"') ");
                eval(voc+ii+"Autocomplete.setIdFieldId('"+voc+ii+"') ");
                eval(voc+ii+"Autocomplete.setNameFieldId('"+voc+ii+"Name') ");
                eval(voc+ii+"Autocomplete.setDivId('"+voc+ii+"Div') ");
                eval(voc+ii+"Autocomplete.setVocKey('"+voc+"') ");
                eval(voc+ii+"Autocomplete.setVocTitle('Медикамент')") ;
                eval(voc+ii+"Autocomplete.build() ");
                eval(voc+ii+"Autocomplete.addOnChangeCallback(function() { checkEnableAdd("+ii+"); })") ;
                if ($('dateSt'+ii)) {
                    new dateutil.DateField($('dateSt'+ii)) ;
                    eventutil.addEventListener($('dateSt'+ii), "keyup", function(){checkEnableAdd(this.id);}) ;
                    eventutil.addEventListener($('dateSt'+ii), "input", function(){checkEnableAdd(this.id);}) ;
                    eventutil.addEventListener($('dateSt'+ii), "blur", function(){checkEnableAdd(this.id);}) ;
                    eventutil.addEventListener($('dateSt'+ii), "paste", function(){checkEnableAdd(this.id);});
                    eventutil.addEventListener($('dateSt'+ii), "dblclick", function(){checkEnableAdd(this.id);});
                    eventutil.addEventListener($('dateSt'+ii), "focus", function(){checkEnableAdd(this.id);});
                }
                return ii;
            }
            //получение следующего id для вставк:
            //+1 - если не удаляли и +/++ с последнего элемента
            //rowcount - если +/++ с любого элемента, но не удаляли
            //первый свободный после rowcount - если +/++ с любого элемента и удаляли
            //можно просто max(id)+1
            function getNextId(ii) {
                var max=ii;
                //if (ii<document.getElementById('allMedTble').rows.length) ii=document.getElementById('allMedTble').rows.length;
                for (var i=0; i<document.getElementById('allMedTble').rows.length; i++) {
                    var id=document.getElementById('allMedTble').rows[i].id.replace('row','');
                    if (id>max) max=id;
                }
                return ++max;
            }
            function delRow(btn) {
               document.getElementById('allMedTble').deleteRow(document.getElementById('row'+btn.id.replace("btnDel","")).rowIndex);
            }
            function addRowDblAfter(btn) {
                var oldII=+btn.id.replace("btnAddDbl","");
                var ii=createRowMed(oldII);
                $('vocOncologyN020'+ii).value=$('vocOncologyN020'+(+btn.id.replace("btnAddDbl",""))).value;
                $('dateSt'+ii).value=getDateAfterOrBeforeCurrent($('dateSt'+oldII).value,'.',1);  //проставить следующую дату по умолчанию
                $('btnAddDbl'+ii).removeAttribute('disabled');
                $('btnAdd'+ii).removeAttribute('disabled');
                $('vocOncologyN020'+ii+'Name').value=$('vocOncologyN020'+(+btn.id.replace("btnAddDbl",""))+'Name').value;
            }
            function addRowAfter(btn) {
                createRowMed(+btn.id.replace("btnAdd",""));
            }
            function checkEnableAdd(id) {
                var ii=(''+id).replace('dateSt','');
                var voc='vocOncologyN020';
                if ($(''+voc+ii) && $('dateSt'+ii) && $(''+voc+ii).value!='' && $('dateSt'+ii).value!=''
                    && $('dateSt'+ii).title.indexOf('Неправильно')==-1) {
                    document.getElementById('btnAdd' + ii).removeAttribute('disabled');
                    document.getElementById('btnAddDbl' + ii).removeAttribute('disabled');
                }
                else {
                    document.getElementById('btnAdd' + ii).setAttribute('disabled', true);
                    document.getElementById('btnAddDbl' + ii).setAttribute('disabled', true);
                }
            }
            //проверка медикаментов
            function checkMeds() {
                var flag=true;
                var voc='vocOncologyN020';
                var table = document.getElementById('allMedTble');
                //удаляю пустые
                for (var i=0; i<table.rows.length; i++) {
                    var ii=table.rows[i].id.replace('row','');
                    if ($(''+voc+ii) && $('dateSt'+ii) &&  $(''+voc+ii).value==''
                    && ($('dateSt'+ii).title.indexOf('Неправильно')!=-1 || $('dateSt'+ii).value=='') && ii!=0) {
                        table.deleteRow(i);
                        i=0;
                    }

                }
                //проверяю заполнение
                for (var i=0; i<table.rows.length; i++) {
                    var ii=table.rows[i].id.replace('row','');
                    if ($(''+voc+ii) && $('dateSt'+ii) &&  ($(''+voc+ii).value==''
                        || $('dateSt'+ii).title.indexOf('Неправильно')!=-1 || $('dateSt'+ii).value==''))
                        flag=false;
                }
                //проверяю хотя бы один
                if (flag) {
                    var cnt=0;
                    for (var i=0; i<table.rows.length; i++) {
                        var ii=table.rows[i].id.replace('row','');
                        if ($(''+voc+ii) && $('dateSt'+ii) && $(''+voc+ii).value!='' && $('dateSt'+ii).value!=''
                            && $('dateSt'+ii).title.indexOf('Неправильно')==-1) cnt++;
                    }
                    flag=(cnt!=0);
                }
                return flag;
            }
        </script>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="oncology_case_reestrForm">
            <msh:sideMenu title="Действия">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-oncology_case_reestr" name="Изменить" roles="/Policy/Mis/Oncology/Case/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-oncology_case_reestr" name="Удалить" roles="/Policy/Mis/Oncology/Case/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>