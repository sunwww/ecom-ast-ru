
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="pharmnet_complect" beginForm="pharmnet_complectRowForm"/>
    </tiles:put>
    <!-- Sider -->
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="pharmnet_complectRowForm">
            <msh:sideMenu title="Управление">
                <msh:sideLink key="ALT+1" params="id"
                              action="/entityEdit-pharmnet_complectRow" name="Изменить"
                              roles="/Policy/Mis/Pharmacy/Administration/Edit" />

                <msh:sideLink key="ALT+DEL" confirm="Удалить?"
                              params="id"
                              action="/entityParentDeleteGoParentView-pharmnet_complectRow" name="Удалить"
                              roles="/Policy/Mis/Pharmacy/Administration/Edit" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>


    <!-- Body -->
    <tiles:put name="body" type="string">
        <%--<msh:form action="/entitySaveGoView-pharmnet_complectRow.do" defaultField="name">--%>
        <msh:form action="/entityParentSaveGoParentView-pharmnet_complectRow.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="complectid" />
            <msh:hidden property="saveType" />
            <msh:row>
                <msh:textField  label="Количество" property="count" fieldColSpan="3" horizontalFill="true"/>
            </msh:row>
            <msh:row>
                <msh:autoComplete  property="regid" label="Наименование" vocName="vocRegids" fieldColSpan="3" horizontalFill="true" />
            </msh:row>
            <msh:submitCancelButtonsRow colSpan="4" />
           <%-- <input id="cancelButton" onclick="this.disabled=true; window.history.back()" title="Отменить изменения [SHIFT+ESC]" value="Отменить" type="button">
            <input id="submitButton" class="default" value="Создать" onclick="createComplectRow()" autocomplete="off" type="button">--%>
        </msh:form>

    </tiles:put>
    <!-- Scripts -->

    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/PharmnetService.js"></script>
        <script type="text/javascript">
            document.getElementById('complectid').value = '${param.id}';
            var complectId = '${param.id}';

          /*  function goBack(){
                location.href = "entityParentView-pharmnet_complect.do?id=${param.id}";
            }

            function createComplectRow() {
                var regid =  document.getElementById('regid').value;
                var count =   document.getElementById('count').value;
                PharmnetService.createComplectRow(regid,complectId,count, {
                    callback : function(aResult) {
                        goBack();
                    }
                });
            }*/
        </script>
    </tiles:put>
</tiles:insert>

