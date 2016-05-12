<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-mis_invalidity.do" defaultField="isFirstDisclose">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>

            <msh:panel>
                <msh:row>
                	<msh:textField property="dateFrom" label="Дата установления"/>
                	<msh:checkBox property="initial" label="Первичная инв."/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property="registrationPlace" label="Место регистрации" vocName="mainLpu" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox label="Инвалид с детства" property="childhoodInvalid" />
                </msh:row>
                <msh:row>
                	<msh:checkBox property="withoutExam" label="Без переосвидетельства" horizontalFill="true"/>
                	<msh:checkBox property="incapable" label="Недееспособный"  horizontalFill="true"/>
                </msh:row>
                <msh:row>
                	<msh:textField property="revisionDate" label="Дата пересмотра"/>
                	<msh:textField property="dateTo" label="Дата снятия инв."/>
                </msh:row>
                <msh:row>
                	<msh:textField property="lastRevisionDate" label="Дата посл. пересмотра"/>
                	<msh:textField property="nextRevisionDate" label="Дата след. пересмотра"/>
                </msh:row>
                <msh:row>
                	<msh:checkBox property="greatePatrioticWarInvalid" label="Инвалид ВОВ"/>
                </msh:row>
                <msh:row>
                	<msh:checkBox property="isWorking" label="Трудоспособен"/>
                	<msh:autoComplete property="group" label="Группа инвалидности" horizontalFill="true" vocName="vocInvalidity"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property="healthViolation" label="Гл.наруш. сост. здор. при инвал." vocName="vocInvalidityHealthViolation" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property="vitalRestriction" label="Вед. огран. жизнедеят. при инвал." vocName="vocInvalidityVitalRestriction" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property="workPlace" horizontalFill="true" label="Вид места работы" vocName="vocInvalidWorkPlace" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                	<msh:checkBox property="workProfDisutility" fieldColSpan="3" label="Работа в условиях профвредности"/>
                </msh:row>
                <msh:row>
	                <msh:autoComplete property="idc10" label="МКБ10" fieldColSpan="3" horizontalFill="true" vocName="vocIdc10"/>
                </msh:row>
                <msh:row>
                	<msh:textField property="diagnosis" label="Диагноз" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>



        </msh:form>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Инвалидность">

            <msh:ifFormTypeIsView formName="mis_invalidityForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_invalidity" name="Изменить"/>
            </msh:ifFormTypeIsView>

            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_invalidityForm">
                <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_invalidity" name="Удалить"
                              confirm="Удалить инвалидность?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_invalidityForm"/>
    </tiles:put>


</tiles:insert>