
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <!-- Upper -->
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="pharmnet_storage" beginForm="pharmnet_storageForm"/>
    </tiles:put>

    <!-- Sider -->
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="pharmnet_storageForm">
            <msh:sideMenu title="Управление">
                <msh:sideLink key="ALT+1" params="id"
                              action="/entityEdit-pharmnet_storage" name="Изменить"
                              roles="/Policy/Mis/Pharmacy/Administration/Edit" />

                <msh:sideLink key="ALT+2" params="id"
                              action="/entityPrepareCreate-pharmnet_storage" name="Добавить новый склад"
                              roles="/Policy/Mis/Pharmacy/Administration/Create" />

                <msh:sideLink styleId="viewShort" params="id"
                              action="/entityList-pharmnet_storage.do" name="К списку складов"
                              roles="/Policy/Mis/Pharmacy/Administration/Create" />
<%--
                <msh:sideLink key="ALT+DEL" confirm="Удалить?"
                              params="id"
                              action="/entityParentDeleteGoParentView-pharmnet_storage" name="Удалить"
                              roles="/Policy/Mis/Pharmacy/Administration/Edit" />--%>
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>

    <!-- Body -->
    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-pharmnet_storage.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:row>
                <msh:autoComplete  property="branchId" label="Наименование Фармнет" vocName="vocBranch" fieldColSpan="3" horizontalFill="true" />
            </msh:row>
            <msh:row>
                <msh:autoComplete  property="groupWorkfuncId" label="Групповая раб. функция" vocName="funcMedServiceRoom" fieldColSpan="3" horizontalFill="true" />
            </msh:row>
            <msh:submitCancelButtonsRow colSpan="4" />
        </msh:form>
    </tiles:put>
    <!-- Scripts -->
    <tiles:put name="javascript" type="string">
    </tiles:put>
</tiles:insert>

