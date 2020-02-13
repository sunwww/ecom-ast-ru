<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocEntrySubType.do" defaultField="code">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
                <msh:row>
                    <msh:textField property="code" size="100"/>
                    </msh:row><msh:row>
                    <msh:textField property="name" size="100"/>
            </msh:row><msh:row>
                    <msh:textField property="tariffCode" size="100"/>
            </msh:row><msh:row>
                </msh:row>
               <msh:row>
                   <msh:autoComplete  property="uslOk" vocName="vocE2FondV006" size="100"/>
                </msh:row>
               <msh:row>
                   <msh:autoComplete  property="vidSluch" vocName="vocE2VidSluch" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete  property="visitPurpose" vocName="vocE2FondV025" size="100" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete  property="idsp" vocName="vocE2FondV010Actual" size="100" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete  property="extDispType" vocName="vocE2FondV016" size="100" />
                </msh:row>
               <msh:row>
                   <msh:checkBox  property="isConsultation" />
                   <msh:checkBox  property="isArchival" />
                </msh:row>
                <msh:row>
                   <msh:textField property="fileType" />
                </msh:row><msh:row>
                   <msh:textField property="billProperty" fieldColSpan="3" size="100"/>
                </msh:row>


                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocEntrySubTypeForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocEntrySubTypeForm">

        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocEntrySubTypeForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocEntrySubType" name="Изменить" roles="/Policy/E2/Edit" />
            </msh:sideMenu>
            <tags:expertvoc_menu currentAction="main"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

