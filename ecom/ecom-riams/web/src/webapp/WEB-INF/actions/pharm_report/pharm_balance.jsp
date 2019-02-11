<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name="body" type="string">


        <msh:row>
            <msh:autoComplete property="vocStorage" label="Кабинет" vocName="vocPharmnetStorage" fieldColSpan="3" horizontalFill="true" />
        </msh:row>

        <br>
        <div id="balance"></div>

    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/PharmnetService.js"></script>
        <script type="text/javascript">
            vocStorageAutocomplete.addOnChangeCallback(function(){

            var div = document.getElementById('balance');
                var vocStorage = document.getElementById('vocStorage');
                PharmnetService.getBalance(vocStorage.value, {
                    callback: function (aResult) {
                        div.innerHTML = aResult;
                    }});
            });
        </script>
    </tiles:put>
</tiles:insert>
