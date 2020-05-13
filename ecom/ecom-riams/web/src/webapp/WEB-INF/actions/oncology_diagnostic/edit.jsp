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
        <ecom:titleTrail mainMenu="Patient" beginForm="oncology_diagnosticForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-oncology_diagnostic.do" defaultField="hello" title="Случай онкологического лечения">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="oncologyCase" />
            <div class="borderedDiv" id="oncologyCase">
                <msh:autoComplete  property="vocOncologyDiagType" label="Тип диагностического показателя" vocName="vocOncologyDiagType" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="histiology" label="Код диагностического показателя" vocName="vocOncologyN007" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="markers" label="Код диагностического показателя" vocName="vocOncologyN010" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="resultHistiology" label="Код результата диагностики" vocName="vocOncologyN008" fieldColSpan="3" horizontalFill="true" />
                <msh:autoComplete  property="valueMarkers" label="Код результата диагностики" vocName="vocOncologyN011" fieldColSpan="3" horizontalFill="true" />
            </div>
            <br>
            <msh:submitCancelButtonsRow colSpan="4" />
        </msh:form>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
        <script type="text/javascript" >

            <msh:ifFormTypeIsNotView formName="oncology_diagnosticForm">

            var vocOncologyDiagType = document.getElementById("vocOncologyDiagType");
            disableAutocomplete("markers");
            disableAutocomplete("valueMarkers");
            disableAutocomplete("histiology");
            disableAutocomplete("resultHistiology");
            vocOncologyDiagTypeAutocomplete.addOnChangeCallback(function(){
                if(vocOncologyDiagType.value!=null && vocOncologyDiagType.value!=""){
                    OncologyService.getCode(vocOncologyDiagType.value,"voconcologydiagtype", {
                        callback : function(code) {
                            if(code=="1"){
                                enableAutocomplete("histiology");
                                enableAutocomplete("resultHistiology");
                                disableAutocomplete("markers");
                                disableAutocomplete("valueMarkers");
                            }
                            if(code=="2"){
                                enableAutocomplete("markers");
                                enableAutocomplete("valueMarkers");
                                disableAutocomplete("histiology");
                                disableAutocomplete("resultHistiology");
                            }
                        }
                    });
                }
            });
            </msh:ifFormTypeIsNotView>

            function disableAutocomplete(name){
                try {
                    var field = document.getElementById(name);
                    var fieldName = document.getElementById(name+"Name");
                    var fieldLabel = document.getElementById(name+"Label");

                    fieldName.style.display  = "none";
                    fieldLabel.style.display  = "none";
                    fieldName.value = null;
                    field.value = null;}
                catch (err) {
                    alert(err.toHTML);
                }
            }

            function enableAutocomplete(name){
                try {
                    var fieldName = document.getElementById(name + "Name");
                    var fieldLabel = document.getElementById(name + "Label");
                    fieldName.style.display = "block";
                    fieldLabel.style.display = "block";
                }
                catch (err) {
                    alert(err.toHTML);
                }
            }
        </script>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu title="Действия">
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-oncology_direction" name="Изменить" roles="/Policy/Mis/Oncology/Case/Delete" />
            <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-oncology_direction" name="Удалить" roles="/Policy/Mis/Oncology/Case/Delete" />
        </msh:sideMenu>
    </tiles:put>
</tiles:insert>

