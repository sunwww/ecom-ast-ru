<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-mis_specialization.do" defaultField="id">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="worker"/>

            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="base" label="Основание" vocName="vocBase" fieldColSpan="4" horizontalFill="true" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="institut" label="Учреждение" vocName="vocInstitut" fieldColSpan="4" horizontalFill="true" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateStart" label="Дата обучения с"/>
                    <msh:textField property="dateFinish" label="по"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateGetCertif" label="Дата выдачи сертификата"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="numberCertif" label="Номер сертификата" fieldColSpan="4" horizontalFill="true" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="spec" label="Специализация" vocName="vocSpec" fieldColSpan="4" horizontalFill="true" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="academicStatus" label="Присвоенное ученое звание" vocName="vocAcademicStatus"/>
                    <msh:autoComplete property="academicDegree" label="Присвоенная ученая степень" vocName="vocAcademicDegree"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="category" label="Присвоенная категория" vocName="vocCategory"/>
                    <msh:textField property="dateCategory" label="Дата присвоения категории"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_specializationForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_specialization" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_specializationForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_specialization" name="Удалить" confirm="Удалить?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_specializationForm" />
    </tiles:put>


</tiles:insert>