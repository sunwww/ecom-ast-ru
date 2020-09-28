<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-e2_cancerEntry.do" defaultField="maybeCancer">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="entry" />
            <msh:panel>
                <msh:row>
                    <msh:checkBox property="maybeCancer"/>
                </msh:row>
                <msh:ifFormTypeIsCreate formName="e2_cancerEntryForm">
                <msh:separator label="Направление" colSpan="4"/>
                    <msh:row>
                        <msh:textField property="directionDate"/>
                        <msh:autoComplete property="directionType" vocName="vocOncologyTypeDirectionCode" size="50"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="directionDirectLpu" label="ЛПУ куда направляем"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="directionSurveyMethod" vocName="vocOncologyMethodDiagTreatCode" size="50"/>
                        <msh:autoComplete property="directionMedService" vocName="vocMedServiceCode" size="50"/>
                    </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:separator colSpan="4" label="Общие"/>
                <msh:row>
                  </msh:row>
                <msh:row>
                    <msh:autoComplete property="occasion" vocName="vocOncologyReasonTreatCode" size="50"/>
                    <msh:autoComplete property="stage" vocName="vocOncologyN002Code" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="tumor" vocName="vocOncologyN003Code" size="50"/>
                    <msh:autoComplete property="nodus" vocName="vocOncologyN004Code" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isMetastasisFound" />
                    <msh:autoComplete property="metastasis" vocName="vocOncologyN005Code" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="sod" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="consiliumResult" vocName="vocOncologyConsiliumCode" size="50"/>
                    <msh:textField property="consiliumDate" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceType" vocName="vocOncologyN013Code" size="50"/>
                    <msh:autoComplete property="surgicalType" vocName="vocOncologyN014Code" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="drugLine" vocName="vocOncologyN015Code" size="50"/>
                    <msh:autoComplete property="drugCycle" vocName="vocOncologyN016Code" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="radiationTherapy" vocName="vocOncologyN017Code" size="50"/>
                </msh:row>
                <msh:ifFormTypeIsCreate formName="e2_cancerEntryForm">
                <msh:separator label="Противопоказания" colSpan="4"/>
                <msh:row>
                <msh:textField property="refusalDate"/>
                <msh:autoComplete property="refusalCode" vocName="vocOncologyN001Code" size="50"/>
                </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:ifFormTypeIsCreate formName="e2_cancerEntryForm">
                <msh:separator label="Диагностика" colSpan="4"/>
                <msh:row>
                <msh:autoComplete property="diagnosticType" vocName="vocOncologyDiagTypeCode" size="50"/>
                <msh:autoComplete property="diagnosticCode" vocName="vocOncologyN007Code" size="50"/>
                </msh:row> <msh:row>
                <msh:autoComplete property="diagnosticResult" vocName="vocOncologyN008Code" size="50"/>
                </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
<msh:ifFormTypeIsView formName="e2_cancerEntryForm">
    <msh:section title="Направления" createUrl="entityParentPrepareCreate-e2_cancerDirection.do?id=${param.id}">
        <ecom:webQuery name="directionList" nativeSql="select d.id, d.date, d.medservice
        ,d.surveymethod
        from e2cancerdirection d
  where d.cancerentry_id=${param.id} "/>
        <msh:tableNotEmpty  name="directionList"  >
            <msh:table deleteUrl="entityParentDeleteGoParentView-e2_cancerDirection.do" idField="1" name="directionList" action="entityEdit-e2_cancerDirection.do" >
                <msh:tableColumn columnName="Дата направления" property="2"/>
                <msh:tableColumn columnName="Услуга" property="3"/>
                <msh:tableColumn columnName="Метод" property="4"/>
            </msh:table>
        </msh:tableNotEmpty>
    </msh:section>
    <msh:section title="Противопоказания" createUrl="entityParentPrepareCreate-e2_cancerRefusal.do?id=${param.id}">
        <ecom:webQuery name="refusalList" nativeSql="select d.id, d.date, d.code
        from e2cancerrefusal d
  where d.cancerentry_id=${param.id} "/>
        <msh:tableNotEmpty name="refusalList"  >
            <msh:table deleteUrl="entityParentDeleteGoParentView-e2_cancerRefusal.do" idField="1" name="refusalList" action="entityEdit-e2_cancerRefusal.do" >
                <msh:tableColumn columnName="Дата отказа" property="2"/>
                <msh:tableColumn columnName="Код" property="3"/>
            </msh:table>
        </msh:tableNotEmpty>
    </msh:section>

    <msh:section title="Диагностика" createUrl="entityParentPrepareCreate-e2_cancerDiagnostic.do?id=${param.id}">
    <ecom:webQuery name="diagnosticList" nativeSql="select d.id, d.type, d.code, d.result
        from e2cancerdiagnostic d
  where d.cancerentry_id=${param.id} "/>
    <msh:tableNotEmpty  name="diagnosticList"  >
        <msh:table deleteUrl="entityParentDeleteGoParentView-e2_cancerDiagnostic.do" idField="1" name="diagnosticList" action="entityEdit-e2_cancerDiagnostic.do" >
            <msh:tableColumn columnName="Тип" property="2"/>
            <msh:tableColumn columnName="Код" property="3"/>
            <msh:tableColumn columnName="Результат" property="4"/>
        </msh:table>
    </msh:tableNotEmpty>
</msh:section>

    <msh:section title="Лекарственные препараты" createUrl="entityParentPrepareCreate-e2_cancerDrug.do?id=${param.id}">
        <ecom:webQuery name="drugList" nativeSql="select d.id, v020.name
        from e2cancerdrug d
        left join VocOncologyN020 v020 on v020.id=d.drug_id
  where d.cancerentry_id=${param.id} "/>
        <msh:tableNotEmpty  name="drugList"  >
            <msh:table deleteUrl="entityParentDeleteGoParentView-e2_cancerDrug.do" idField="1" name="drugList" action="entityEdit-e2_cancerDrug.do" ><input type="button" onclick="deleteAllDrugs()" value="Удалить все лекарства">
                <msh:tableColumn columnName="Лекарство" property="2"/>
            </msh:table>
        </msh:tableNotEmpty>
    </msh:section>

</msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_cancerEntryForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <msh:ifFormTypeIsCreate formName="e2_cancerEntryForm">
                <script type="text/javascript">

                    diagnosticTypeAutocomplete.addOnChangeCallback(function() {
                        if ($('diagnosticType') && $('diagnosticType').value==1) {
                            diagnosticCodeAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN007Code');
                            diagnosticResultAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN008Code');
                        } else if ($('diagnosticType') && $('diagnosticType').value==2) {
                            diagnosticCodeAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN010Code');
                            diagnosticResultAutocomplete.setUrl('simpleVocAutocomplete/vocOncologyN011Code');
                        }
                    });
                </script>

          </msh:ifFormTypeIsCreate>
        <script type="text/javascript">
            function deleteAllDrugs() {
                Expert2Service.deleteAllDrugByCancer(${param.id},{
                    callback: function () {
                    alert('Удаолено');
                    }
                });
            }
        </script>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_cancerEntryForm">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityParentEdit-e2_cancerEntry" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityParentDelete-e2_cancerEntry" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
</tiles:insert>

