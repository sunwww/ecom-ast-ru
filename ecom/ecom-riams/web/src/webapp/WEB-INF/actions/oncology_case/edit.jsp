<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="style" type="string">
        <style>
            .borderedDiv {
                display: inline-block;
                border: 1px solid;
                width: 40%;
                border-color: rgb(59,92,105) ;
                padding-top:5px;
                padding-bottom:10px;
                padding-left:10px;
            }
        </style>
    </tiles:put>

    <tiles:put name="title" type="string">
        <ecom:titleTrail guid="titleTrail-123" mainMenu="MedCase" beginForm="oncology_caseForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-oncology_case.do" defaultField="hello" title="Случай онкологического лечения">

            <msh:checkBox property="suspicionOncologist" label="Подозрение на ЗНО:"/><br>
            <div class="borderedDiv" id="oncologyCase">

                <msh:hidden guid="hiddenParent" property="medCase" />
                <msh:hidden guid="hiddenId" property="id" />
                <msh:hidden guid="hiddenSaveType" property="saveType" />

                <msh:autoComplete  property="vocOncologyReasonTreat" label="Повод обращения" vocName="vocOncologyReasonTreat" fieldColSpan="3" horizontalFill="true" />
                <br><msh:checkBox property="distantMetastasis" label="Удаленные метастазы:"/><br>
                <msh:autoComplete  property="stad" label="Стадия заболевания" vocName="vocOncologyN002" horizontalFill="true"/>
                <msh:autoComplete  property="tumor" label="Значение Tumor" vocName="vocOncologyN003" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="nodus" label="Значение Nodus" vocName="vocOncologyN004" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="metastasis" label="Значение Metastasis" vocName="vocOncologyN005" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="consilium" label="Сведения о проведении консилиума" vocName="vocOncologyConsilium" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="typeTreatment" label="Тип услуги" vocName="vocOncologyN013" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="surgTreatment" label="Тип хирургического лечения" vocName="vocOncologyN014" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="lineDrugTherapy" label="Линия лекарственной терапии" vocName="vocOncologyN015" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="cycleDrugTherapy" label="Цикл лекарственной терапии" vocName="vocOncologyN016" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="typeRadTherapy" label="Тип лучевой терапии" vocName="vocOncologyN017" fieldColSpan="3" horizontalFill="true" />
                <msh:textField property="sumDose" label="Суммарная очаговая доза" fieldColSpan="3" horizontalFill="true" />
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
            </div>

            <div class="borderedDiv" id="oncologyDirection">
                <msh:textField property="date" label="Дата направления" fieldColSpan="3" horizontalFill="true"/><br>
                <msh:autoComplete  property="typeDirection" label="Вид направления" vocName="vocOncologyTypeDirection" horizontalFill="true"/>
                <msh:autoComplete  property="methodDiagTreat" label="Метод диагностического лечения" vocName="vocOncologyMethodDiagTreat" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="medService" label="Мед услуга" vocName="vocMedService" fieldColSpan="3" horizontalFill="true" />
                <br>
                <input type="button" value="Отменить" onclick="test()">
                <input type="button" value="Создать" onclick="saveDirection()">
            </div>

            <%-- VIEW ONLY --%>
            <msh:ifFormTypeIsView formName="oncology_caseForm">
                <msh:section createRoles="/Policy/Mis/Oncology/Direction/Create"
                             createUrl="entityParentPrepareCreate-oncology_direction.do?id=${param.id}"
                             title="Список направлений">
                    <ecom:webQuery name="list" nativeSql="
                    select od.id,otd.name as type ,omd.name as method,ms.name as service
                    from oncologydirection od
                    left join vocOncologyTypeDirection otd on otd.id = od.typedirection_id
                    left join vocOncologyMethodDiagTreat omd on omd.id = od.methoddiagtreat_id
                    left join vocmedservice ms on ms.id = od.medservice_id
                    where od.oncologycase_id = ${param.id}"/>
                    <msh:table viewUrl="entityView-oncology_direction.do?short=Short" name="list"
                               action="entityParentView-oncology_direction.do" idField="1" >
                        <msh:tableColumn columnName="#" property="sn"/>
                        <msh:tableColumn columnName="Вид направления" property="2"/>
                        <msh:tableColumn columnName="Метод диагност. лечения" property="3"/>
                        <msh:tableColumn columnName="Мед. услуга" property="4"/>
                    </msh:table>
                </msh:section>

                <msh:section createRoles="/Policy/Mis/Oncology/Contra/Create"
                             createUrl="entityParentPrepareCreate-oncology_contra.do?id=${param.id}"
                             title="Список противопоказаний и отказов">
                    <ecom:webQuery name="list" nativeSql="select oc.id,oc.date,n001.name
                    from oncologycontra oc
                    left join voconcologyn001 n001 on n001.id = oc.contraindicationandrejection_id
                    where oncologycase_id = ${param.id}"/>
                    <msh:table viewUrl="entityView-dis_case.do?short=Short" name="list"
                               action="entityParentView-dis_case.do" idField="1" >
                        <msh:tableColumn columnName="#" property="sn"/>
                        <msh:tableColumn columnName="Дата регистрации" property="2"/>
                        <msh:tableColumn columnName="Противопоказание" property="3"/>
                    </msh:table>
                </msh:section>

                <msh:section createRoles="/Policy/Mis/Oncology/Diagnostic/Create"
                             createUrl="entityParentPrepareCreate-oncology_diagnostic.do?id=${param.id}"
                             title="Диагностический блок">
                    <ecom:webQuery name="list" nativeSql="
                    select od.id,
                    case when od.histiology_id is null then n010.name else n007.name end as diag1,
                    case when od.resulthistiology_id is null then n011.name else n008.name end as diag2
                    from oncologydiagnostic od
                    left join voconcologyn007 n007 on n007.id = od.histiology_id
                    left join voconcologyn008 n008 on n008.id = od.resulthistiology_id
                    left join voconcologyn010 n010 on n010.id = od.markers_id
                    left join voconcologyn011 n011 on n011.id = od.valuemarkers_id
                    where oncologycase_id = ${param.id}"/>
                    <msh:table viewUrl="entityView-oncology_diagnostic.do?short=Short" name="list"
                               action="entityParentView-oncology_diagnostic.do" idField="1" >
                        <msh:tableColumn columnName="#" property="sn"/>
                        <msh:tableColumn columnName="Код диагностического показателя" property="2"/>
                        <msh:tableColumn columnName="Код результата диагностики" property="3"/>
                    </msh:table>
                </msh:section>
            </msh:ifFormTypeIsView>
        </msh:form>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
        <script type="text/javascript" >

            var typeTreatment = document.getElementById("typeTreatment");
            var oncologyDirection = document.getElementById("oncologyDirection");
            var oncologyCase = document.getElementById("oncologyCase");
            var suspicionOncologist = document.getElementById("suspicionOncologist");
            var distantMetastasis = document.getElementById("distantMetastasis");
            var vocOncologyReasonTreat = document.getElementById("vocOncologyReasonTreat");


            <msh:ifFormTypeIsView formName="oncology_caseForm">
                if(suspicionOncologist.checked){
                    oncologyCase.style.display  = "none";
                }
                oncologyDirection.style.display  = "none";
            </msh:ifFormTypeIsView>

            init();
            suspicionOncologist.oninput = function() {
                checkCheckbox();
            };


            vocOncologyReasonTreatAutocomplete.addOnChangeCallback(function(){
                if( vocOncologyReasonTreat.value==""){
                    distantMetastasis.checked = false;
                    distantMetastasis.disabled = true;
                }else {
                    distantMetastasis.disabled = false;
                }
            });

            typeTreatmentAutocomplete.addOnChangeCallback(function(){
                if(typeTreatment.value!=null && typeTreatment.value!=""){
                    OncologyService.getCode(typeTreatment.value,"vocOncologyN013", {
                        callback : function(code) {
                            disableAllElements();
                            if(code=='1') {
                                enableAutocomplete("surgTreatment");
                            }
                            if(code=='2'){
                                enableAutocomplete("lineDrugTherapy");
                                enableAutocomplete("cycleDrugTherapy");
                                enableElement("sumDose");
                            }
                            if(code=='3') {
                                enableElement("sumDose");
                            }
                            if(code=='3' || code=='4'){
                                enableAutocomplete("typeRadTherapy");
                            }
                        }
                    });
                }
            });

            function saveDirection() {
                var obj = {
                    typeDirection: getValue("typeDirection"),
                    methodDiagTreat: getValue("methodDiagTreat"),
                    medService: getValue("medService"),
                    medCase:getValue("medCase"),
                    id:${param.id}
                };
                var json = JSON.stringify(obj, "", 4);
                OncologyService.insertDirection(json, {
                    callback : function(retId) {
                        goOnView(retId);
                    }
                });
            }
            function getValue(name) {
                var temp = document.getElementById(name);
                return temp.value;
            }

            function goOnView(id){
                location.href = "entityView-oncology_case.do?id="+id;
            }

            function enableAutocomplete(name){
                enableElement(name);
                var fieldName = document.getElementById(name+"Name");
                fieldName.style.display  = "block";
            }
            function enableElement(name) {
                var field = document.getElementById(name);
                var fieldLabel = document.getElementById(name+"Label");
                field.style.display  = "block";
                fieldLabel.style.display  = "block";
            }

            function disableAutocomplete(name){
                disableElement(name);
                var fieldName = document.getElementById(name+"Name");
                fieldName.style.display  = "none";
            }

            function disableElement(name) {
                var field = document.getElementById(name);
                var fieldLabel = document.getElementById(name+"Label");
                var fieldLabel = document.getElementById(name+"Label");
                field.style.display  = "none";
                fieldLabel.style.display  = "none";
            }


            function disableAllElements() {
                disableAutocomplete("surgTreatment");
                disableAutocomplete("lineDrugTherapy");
                disableAutocomplete("cycleDrugTherapy");
                disableAutocomplete("typeRadTherapy");
                disableElement("sumDose");
            }

            function init() {
                disableAllElements();
                oncologyDirection.style.display  = "none";
                distantMetastasis.disabled = true;
            }

            function checkCheckbox() {
                if(suspicionOncologist.checked){
                    oncologyDirection.style.display  = "block";
                    oncologyCase.style.display  = "none";
                }else {
                    oncologyDirection.style.display  = "none";
                    oncologyCase.style.display  = "block";
                }
            }
            document.getElementById("date").className += " required";
            document.getElementById("stadName").className += " required";
            document.getElementById("tumorName").className += " required";
            document.getElementById("nodusName").className += " required";
            document.getElementById("metastasisName").className += " required";
            document.getElementById("typeTreatmentName").className += " required";

            document.getElementById("surgTreatmentName").className += " required";
            document.getElementById("lineDrugTherapyName").className += " required";
            document.getElementById("cycleDrugTherapyName").className += " required";
            document.getElementById("sumDose").className += " required";
            document.getElementById("typeRadTherapyName").className += " required";


        </script>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="oncology_caseForm">
            <msh:sideMenu guid="sideMenu-123" title="Действия">
                <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-oncology_case" name="Изменить" roles="/Policy/Mis/Oncology/Case/Edit" />
                <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="deleteOncologyCase(${param.id})" name="Удалить" roles="/Policy/Mis/Oncology/Case/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

