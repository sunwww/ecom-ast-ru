<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_cancerDiagnostic.do" defaultField="type" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="cancerEntry" />
            <msh:panel>

                <msh:separator label="Диагностика" colSpan="4"/>
                <msh:row>
                <msh:autoComplete property="type" vocName="vocOncologyDiagTypeCode" size="50"/>
                <msh:autoComplete property="code" vocName="vocOncologyN007Code" size="50"/>
                </msh:row> <msh:row>
                <msh:autoComplete property="result" vocName="vocOncologyN008Code" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="biopsyDate"/>
                </msh:row>
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="1" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_cancerDiagnosticForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsNotView formName="e2_cancerDiagnosticForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>

                <script type="text/javascript">
                    function setParentTypeAutocomplete(isClean) {
                        if (isClean) {
                            $('code').value="";
                            $('result').value="";
                            $('codeName').value="";
                            $('resultName').value="";
                        }
                        if ($('type') && $('type').value==1) {
                            codeAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN007Code');
                            resultAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN008Code');
                        } else if ($('type') && $('type').value==2) {
                            codeAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN010Code');
                            resultAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN011Code');
                        } else {

                        }

                    }
                    typeAutocomplete.addOnChangeCallback(function() {
                        setParentTypeAutocomplete(true);
                    });
                    setParentTypeAutocomplete(false);
                    //if diagnosticType==1 DiagnosticCode = vocOncologyN007Code , DiagnosticResult = VocOncologyN008Code
                    //if diagnosticType==2 DiagnosticCode = VocOncologyN010Code , DiagnosticResult = VocOncologyN011Code
                </script>

          </msh:ifFormTypeIsNotView>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_cancerDiagnosticForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
            <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
                <msh:sideLink params="id" action="/entityParentEdit-e2_cancerDiagnostic" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentDelete-e2_cancerDiagnostic" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
</tiles:insert>

