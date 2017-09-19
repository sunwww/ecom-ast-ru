<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="body" type="string">
        <msh:form guid="formHello" action="/dis_import.do" defaultField="documentTypeName">
        <msh:panel guid="panel">
            <msh:hidden property="patient_id" />
             <msh:hidden property="ogrn"/>
            <msh:row>
                <msh:textField property="snils" label="СНИЛС" viewOnlyField="true"/>
            </msh:row>
            <%--<msh:row>
                <msh:textField property="patient_id" label="ID" horizontalFill="true"/>
            </msh:row>
            <msh:row>
                <msh:textField property="ogrn" label="ОГРН" horizontalFill="true"/>
            </msh:row>--%>
            <msh:row>
                <msh:textField property="elnNumber" label="Номер ЭЛН" horizontalFill="true"/>
            </msh:row>
        </msh:panel>
        </msh:form>
        <input type="button" value="Импорт из ФСС" onclick="dodo()">
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
        <script language="javascript" type="text/javascript">
            function dodo() {
                alert("asd");
            }

            document.getElementById('patient_id').value = ${param.id};
            DisabilityService.getSnils(${param.id},{
                callback: function (snils) {
                    document.getElementById('snilsReadOnly').value = snils;
                }
            });

        </script>
    </tiles:put>
</tiles:insert>
