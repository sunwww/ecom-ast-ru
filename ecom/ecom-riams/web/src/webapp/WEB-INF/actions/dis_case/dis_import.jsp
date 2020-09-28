<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="body" type="string">
        <msh:form action="/dis_import.do" defaultField="documentTypeName">
        <msh:panel>
            <msh:row>
                <msh:textField property="elnNumber" label="Номер ЭЛН" horizontalFill="true" size="50"/>
            </msh:row>
        </msh:panel>

        </msh:form>
        <input type="button" value="Импортировать из ФСС" onclick="importDisabilityDocument('import',this)">
        <input type="button" value="Просмотреть ЭЛН (не импортируя)" onclick="importDisabilityDocument('show',this)">
        <div id="responseDiv"/>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
        <script language="javascript" type="text/javascript">
            $('elnNumber').className+=" required";

            function importDisabilityDocument(method,aButton) {
                if (+$('elnNumber').value>0) {
                    var oldValue =aButton.value;
                    aButton.value="Подождите...";
                    aButton.disabled=true;
                    DisabilityService.importDisabilityDocument($('elnNumber').value,+'${param.id}',method, {
                       callback: function (a) {
                         $('responseDiv').innerHTML=a;
                         aButton.value = oldValue;
                           aButton.disabled=false;
                       }
                    });
                } else {
                    alert ('Не заполнен номер ЭЛН!');
                }
            }
        </script>
    </tiles:put>
</tiles:insert>
