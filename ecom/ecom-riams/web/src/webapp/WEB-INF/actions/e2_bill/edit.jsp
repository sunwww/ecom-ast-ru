<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <tags:E2ServiceAdd name="Diagnosis"/>
        <msh:form action="/entitySaveGoView-e2_bill.do" defaultField="billNumber">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="4" label="Общие"/>
                <msh:row>
                    <msh:textField property="billNumber" viewOnlyField="true"/>
                    <msh:textField property="billDate" viewOnlyField="true"/>
                </msh:row>
               <msh:row>
                <msh:autoComplete vocName="vocE2BillStatus" property="status"  size="50"/>
                </msh:row><msh:row>
                <msh:autoComplete vocName="vocInsuranceCompany" property="company"  size="50"/>
            </msh:row>
        <msh:row>
                <msh:textField property="sum" size="50" viewOnlyField="true"/>
            </msh:row>
        <msh:row>
                <msh:textField property="comment" size="100" />
            </msh:row>
        <msh:row>
                <msh:textField property="billProperty" size="100" />
            </msh:row>


                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>


    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_billForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_billForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">


            </script>

        </msh:ifFormTypeAreViewOrEdit>

    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_billForm">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityEdit-e2_bill" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-e2_bill" name="Удалить" roles="/Policy/E2/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

