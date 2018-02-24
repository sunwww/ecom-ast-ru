<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!--
- Критерий по 203 приказу
-->
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="side" type="string">
    <msh:ifFormTypeAreViewOrEdit formName="expert_vocQualityEstimationCrit203Form" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
        <msh:sideMenu title="Действия" guid="c65476c8-6c6a-43c4-a70a-84f40bda76e1">
            <tags:AddEditDiagnoseCriteria203 name="AddDs"/>
            <msh:sideLink action="/javascript:showAddDs(${param.id})" name='Диагнозы' title="Просмотр диагнозов" params="" roles="/Policy/Mis/Order203/EditVocs"/>
            <tags:AddEditMedServCriteria203 name="AddEditMedServCriteria203"/>
            <msh:sideLink action="/javascript:showAddEditMedServCriteria203(${param.id})" name='Мед. услуги' title="Просмотр услуг" params="" roles="/Policy/Mis/Order203/EditVocs"/>
            <msh:sideLink action="/javascript:setMedServEmptyString(${param.id})" name='Обнулить услуги' title="Обнулить услуги" params="" roles="/Policy/Mis/Order203/EditVocs"/>
       </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
    <tiles:put name="body" type="string">

        <msh:form  action="/entitySaveGoView-expert_vocQualityEstimationCrit203.do" defaultField="name">
            <msh:hidden guid="hiddenId" property="id" />
            <msh:hidden guid="hiddenSaveType" property="saveType" />
            <msh:hidden guid="hiddenKind" property="kind" />
            <msh:hidden property="parent" />
            <msh:hidden property="type" />
            <msh:hidden property="kind" />
            <msh:hidden property="code" />
            <msh:hidden property="shortName" />
            <msh:hidden property="medServiceCodes" />
            <msh:hidden property="isBoolean" />
            <msh:hidden property="medServiceCodes" />
            <msh:panel guid="panel">

                <msh:row>
                    <msh:textArea label="Наименование" property="name" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox label="Взрослые" property="isGrownup" horizontalFill="true"/>
                    <msh:checkBox label="Дети" property="isChild" horizontalFill="true"/>
                </msh:row>
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="title" type="string">
        <ecom:titleTrail guid="titleTrail-123" mainMenu="Voc" beginForm="expert_vocQualityEstimationCrit203Form" />
    </tiles:put>
    <tiles:put name="javascript" type="string">

        <msh:ifFormTypeIsView formName="expert_vocQualityEstimationCrit203Form">
            <script type="text/javascript">
                    window.location="listCrits203.do";
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <script type="text/javascript">
        function setMedServEmptyString() {
            QualityEstimationService.setMedServEmptyString(${param.id}, {
                callback: function () {
                    $('medServiceCodes').value='';
                    alert('Список услуг обнулён.');
                }

            });
        }
    </script>
</tiles:insert>