<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%
    String username = LoginInfo.find(request.getSession()).getUsername();
    request.setAttribute("username", username);
%>



<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">


    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <tags:pharmInventoryTag name="My" roles="/Policy/Mis/Prescription/ViewInformation"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:row>
            <msh:autoComplete property="vocStorage" label="Выбирите кабинет:" vocName="vocPharmnetStorage" fieldColSpan="3" horizontalFill="true" />
        </msh:row>

        <br>
        <div id="inventar"></div>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/PharmnetService.js"></script>
        <script type="text/javascript">
            vocStorageAutocomplete.addOnChangeCallback(function(){

                var div = document.getElementById('inventar');
                var vocStorage = document.getElementById('vocStorage');
                PharmnetService.getInventTable(vocStorage.value, {
                    callback: function (aResult) {
                        div.innerHTML = aResult;
                    }});
            });
        </script>
    </tiles:put>
</tiles:insert>
