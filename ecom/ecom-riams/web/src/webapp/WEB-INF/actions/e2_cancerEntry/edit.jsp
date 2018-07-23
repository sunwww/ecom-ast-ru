<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <tags:E2ServiceAdd name="Diagnosis"/>
        <msh:form action="/entityParentSaveGoParentView-e2_cancerEntry.do" defaultField="lastname" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="entry" />
            <msh:panel>
     <msh:separator colSpan="4" label="Общие"/>
                <msh:row>
                <msh:checkBox property="maybeCancer"/>
                  </msh:row>
                <msh:row>
                  </msh:row>
                <msh:row>
                    <msh:autoComplete property="occasion" vocName="vocOncologyReasonTreatCode" size="50"/>
                    <msh:autoComplete property="stage" vocName="vocOncologyN012Code" size="50"/>
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
                    <msh:autoComplete property="consiliumResult" vocName="vocOncologyConsiliumCode"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceType" vocName="vocOncologyN013Code"/>
                    <msh:autoComplete property="surgicalType" vocName="vocOncologyN014Code"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="drugLine" vocName="vocOncologyN015Code"/>
                    <msh:autoComplete property="drugCycle" vocName="vocOncologyN016Code"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="radiationTherapy" vocName="vocOncologyN017Code"/>
                </msh:row>

                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="1" />
            </msh:panel>
        </msh:form>
        <ecom:webQuery name="directions" nativeSql="select link.id, vd.code||' '||vd.name as name, coalesce(link.value, vd.value) as value
from E2CoefficientPatientDifficultyEntryLink link
left join VocE2CoefficientPatientDifficulty vd on vd.id=link.difficulty_id
where link.entry_id=${param.id}"/>
        Направления
        <msh:table  idField="1" name="directions" action="/javascript:void()" noDataMessage="Нет уровней сложности">
            <msh:tableColumn columnName="ИД" property="1"/>
            <msh:tableColumn columnName="Уровень сложности" property="2"/>
            <msh:tableColumn columnName="Коэффициент" property="3"/>
        </msh:table>


    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_cancerEntryForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_cancerEntryForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>

                <script type="text/javascript">


            function gotoMedcase() {
                window.open('entitySubclassView-mis_medCase.do?id='+$('externalId').value);
            }

        </msh:ifFormTypeIsView>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_cancerEntryForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
            <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
                <msh:sideLink action="/javascript:gotoMedcase()" name="Перейти к СМО" roles="/Policy/E2" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

