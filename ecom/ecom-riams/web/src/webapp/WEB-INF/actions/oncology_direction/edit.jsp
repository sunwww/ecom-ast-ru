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
        <ecom:titleTrail mainMenu="Patient" beginForm="oncology_directionForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-oncology_direction.do" defaultField="hello" title="Онкологическое направление">
            <div class="borderedDiv" id="oncologyCase">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="oncologyCase" />
            <msh:textField property="date" label="Дата направления"/><br>
            <msh:autoComplete  property="typeDirection" label="Вид направления" vocName="vocOncologyTypeDirection" fieldColSpan="3" horizontalFill="true" />
            <msh:autoComplete  property="methodDiagTreat" label="Метод диагностического лечения" vocName="vocOncologyMethodDiagTreat" fieldColSpan="3" horizontalFill="true" />
            <msh:autoComplete  property="medService" label="Мед услуга" vocName="vocMedService" fieldColSpan="3" horizontalFill="true" />
            </div>
            <br>
            <msh:submitCancelButtonsRow colSpan="4" />
        </msh:form>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/OncologyService.js"></script>
        <script type="text/javascript" >

            var typeDirection = document.getElementById("typeDirection");
            var methodDiagTreat = document.getElementById("methodDiagTreat");
            var medService = document.getElementById("medService");

            disableAutocomplete("methodDiagTreat");
            typeDirectionAutocomplete.addOnChangeCallback(function(){
                if(typeDirection.value!=null && typeDirection.value!=""){
                    OncologyService.getCode(typeDirection.value,"voconcologytypedirection", {
                        callback : function(code) {
                            if(code=="3"){
                                enableAutocomplete("methodDiagTreat");
                            }else{
                                disableAutocomplete("methodDiagTreat");
                            }
                        }
                    });
                }
            });


            function disableAutocomplete(name){
                var field = document.getElementById(name);
                var fieldName = document.getElementById(name+"Name")
                var fieldLabel = document.getElementById(name+"Label")

                fieldName.style.display  = "none";
                fieldLabel.style.display  = "none";
                fieldName.value = null;
                field.value = null;
            }

            function enableAutocomplete(name){
                var field = document.getElementById(name);
                var fieldName = document.getElementById(name+"Name")
                var fieldLabel = document.getElementById(name+"Label")

                fieldName.style.display  = "block";
                fieldLabel.style.display  = "block";
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

