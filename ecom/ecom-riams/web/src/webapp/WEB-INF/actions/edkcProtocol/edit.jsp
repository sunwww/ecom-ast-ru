<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name="style" type="string"></tiles:put>
    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoSubclassView-edkcProtocol.do" defaultField="dateRegistration" guid="b55hb-b971-441e-9a90-5155c07"
                  fileTransferSupports="true">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="username" />
            <msh:hidden property="date" />
            <msh:hidden property="time" />
            <msh:hidden property="specialist" />
            <msh:hidden property="obsSheet" />
        </msh:form>
        <msh:panel colsWidth="1%,1%,1%,1%,1%,1%,65%">
            <msh:row>
                <msh:autoComplete property="type" fieldColSpan="3"
                                  label="Тип протокола" horizontalFill="true"
                                  vocName="vocTypeProtocol" />
            </msh:row>
            <msh:row>
                <msh:textArea property="record" label="Текст:" size="100" rows="25"
                              fieldColSpan="8" guid="b6ehb-b971-441e-9a90-519c07"/>

            </msh:row>
            <msh:row>
                <msh:submitCancelButtonsRow colSpan="3"  functionSubmit="" />
            </msh:row>
            <msh:ifFormTypeIsView formName="edkcProtocolForm">

                <msh:row>
                    <msh:textField property="date" label="Дата создания"
                                   viewOnlyField="true" />
                    <msh:textField property="time" label="Время" viewOnlyField="true" />
                    <msh:textField property="username" label="Пользователь"
                                   viewOnlyField="true" />
                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редак." />
                    <msh:label property="editTime" label="Время редак." />
                    <msh:label property="editUsername" label="Пользователь" />
                </msh:row>
                <msh:row>
                    <msh:textField property="printDate" label="Дата печати"
                                   viewOnlyField="true" />
                    <msh:textField property="printTime" label="Время"
                                   viewOnlyField="true" />
                </msh:row>
            </msh:ifFormTypeIsView>
                </msh:panel>
    </tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="edkcProtocolForm"/>
    </tiles:put>
    <tiles:put name='javascript' type='string'>
    <script type="text/javascript" src="./dwr/interface/PatientService.js">/**/</script>
    <script type="text/javascript">
    </script>
    </tiles:put>
</tiles:insert>