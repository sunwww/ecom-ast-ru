<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
<msh:form action="entityParentSaveGoView-mis_usl.do" defaultField="kodLpuName">
<msh:hidden property="id"/>
<msh:hidden property="saveType"/>
<msh:hidden property="patient"/>

<msh:panel>
<msh:separator colSpan="4" label="ЛПУ"/>
<msh:row>
    <msh:autoComplete property="kodLpu" vocName="timeOmcLpu" horizontalFill="true" size="50" fieldColSpan="3"/>
</msh:row>
<msh:row>
    <msh:autoComplete property="vidLpu" vocName="timeOmcVidLpu" horizontalFill="true" size='50' fieldColSpan="3"/>
</msh:row>

<msh:separator colSpan="4" label="Заболевание"/>
<msh:row>
    <msh:textField label='История болезни/карта' property="caseHistoryNumber" horizontalFill="true"/>
    <msh:textField label='Койко-дни' property="bedDays"/>
</msh:row>

<msh:row>
    <msh:textField label='Дата поступления' property="admissionDate"/>
    <msh:textField label='Время' property="admissionTime"/>
</msh:row>
<msh:row>
    <msh:textField label='Дата выписки' property="dischargeDate"/>
    <msh:textField label='Дата перевода' property="transferDate"/>
</msh:row>

<msh:row>
    <msh:autoComplete label='Основное диагноз' property="diagnosisMain" vocName="timeOmcMkb10"
                      horizontalFill="true" fieldColSpan="3"/>
</msh:row>
<msh:row>
    <msh:autoComplete label='Сопутствующий' property="diagnosisConcomitant" vocName="timeOmcMkb10"
                      horizontalFill="true" fieldColSpan="3"/>
</msh:row>
<msh:row>
    <msh:autoComplete label='Осложнение' property="osl" vocName="timeOmcOsl" horizontalFill="true" fieldColSpan="3"/>
</msh:row>
<msh:row>
    <msh:autoComplete label='Характер заболевания' property="qz" vocName="timeOmcQz" horizontalFill="true"
                      fieldColSpan="3"/>
</msh:row>

<msh:row>
    <msh:autoComplete property="directionType" vocName="timeOmcAs" horizontalFill="true"/>
    <msh:textField label='Обращение' property="rehospitalization"/>
</msh:row>

<msh:row>
    <msh:autoComplete property="lpuFrom" vocName="timeOmcLpu" horizontalFill="true" fieldColSpan="3"/>
</msh:row>

<msh:row>
    <msh:autoComplete property="lpuFromUnit" vocName="timeOmcFrm" horizontalFill="true" fieldColSpan="3"/>
</msh:row>


<msh:row>
    <msh:autoComplete label='Результат обращения' property="result" vocName="timeOmcResG"
                      horizontalFill="true" fieldColSpan="3"/>
</msh:row>

<msh:separator colSpan="4" label="Врач"/>
<msh:row>
    <msh:autoComplete property="doctorPost" vocName="timeOmcPrvd" horizontalFill="true" fieldColSpan="3"/>
</msh:row>
<msh:row>
    <msh:textField property="doctorSnils" size='20' fieldColSpan="1"/>
    <msh:textField property="doctorCode" size='20' fieldColSpan="1"/>
</msh:row>

<msh:separator colSpan="4" label="Оказанная услуга"/>
<msh:row>
    <msh:autoComplete label='Услуга' property="render" vocName="timeOmcTariff" horizontalFill="true"
                      fieldColSpan="3"/>
</msh:row>
<msh:row>
    <msh:autoComplete property="depType" fieldColSpan="3" vocName="timeOmcDepType" horizontalFill="true"/>
</msh:row>

<msh:row>
    <msh:textField property="registryNumber" horizontalFill="true"/>
</msh:row>

<msh:row>
    <msh:textField property="billDate" horizontalFill="true"/>
    <msh:textField property="billNumber" horizontalFill="true"/>
</msh:row>

<msh:row>
    <msh:autoComplete label='Уровень' property="level" vocName="timeOmcKl" horizontalFill="true" fieldColSpan="3"/>
</msh:row>
<msh:row>
    <msh:textField label='Экспертная оценка' property="expert" horizontalFill="true"/>
    <msh:textField property="casus" horizontalFill="true"/>
</msh:row>


<msh:separator colSpan="4" label="Листок нетрудоспособности"/>

<msh:row>
    <msh:textField label='Открытие' property="disabilityOpen" horizontalFill="true"/>
    <msh:textField label='Закрытие' property="disabilityClose" horizontalFill="true"/>
</msh:row>


<msh:submitCancelButtonsRow colSpan="6"/>
</msh:panel>

</msh:form>

</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>

        <msh:ifFormTypeIsView formName="mis_uslForm">
            <msh:sideLink key="ALT+2" params="id" roles="/Policy/Mis/Usl/Edit" action="/entityEdit-mis_usl"
                          name="Изменить"/>
        </msh:ifFormTypeIsView>

        <hr/>
        <msh:ifFormTypeAreViewOrEdit formName="mis_uslForm">
            <msh:sideLink roles="/Policy/Mis/Usl/Delete" key='ALT+DEL' params="id"
                          action="/entityParentDeleteGoParentView-mis_usl" name="Удалить"
                          confirm="Удалить услугу?"/>
        </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
</tiles:put>

<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_uslForm"/>
</tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">
        form.viewOnly td.bedDays {
            width: 15em;
        }
    </style>
</tiles:put>


</tiles:insert>