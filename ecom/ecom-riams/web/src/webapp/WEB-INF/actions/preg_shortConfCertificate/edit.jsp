<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="preg_shortConfCertificateForm">
            <msh:sideMenu title="Родовый сертификат">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_shortConfCertificate" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit"/>
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-preg_shortConfCertificate" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete"/>
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="preg_shortConfCertificateForm"/>
    </tiles:put>


    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-preg_shortConfCertificate.do" defaultField="transferDate">
            <msh:hidden property="id"/>
            <msh:hidden property="medCase"/>
            <msh:hidden property="saveType"/>
            <msh:panel>
                <msh:row>
                    <msh:textField property="number" label="Номер сертификата"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan=""/>
            </msh:panel>
        </msh:form>
    </tiles:put>

</tiles:insert>

