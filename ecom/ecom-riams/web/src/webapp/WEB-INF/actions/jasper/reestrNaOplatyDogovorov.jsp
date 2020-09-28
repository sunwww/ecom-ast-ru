<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Реестр на оплату для договоров</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/reestrNaOplatyDogovorov.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
        <msh:panel>
            <msh:row>
                <msh:textField property="dateBegin" label="Период с" />
                <msh:textField property="dateEnd" label="по" />
            </msh:row>
            <msh:row>
                <msh:autoComplete property="serviceStream" fieldColSpan="4" horizontalFill="true" label="Поток обслуживания" vocName="vocSstreamE2Entry"/>
            </msh:row>
            <msh:row>
                <msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип помощи" vocName="vocTypeHelpJReport"/>
            </msh:row>
            <msh:row>
                <msh:textField property="pigeonHole" fieldColSpan="4" horizontalFill="true" label="Номер счёта"/>
            </msh:row>
            <msh:row>
                <td colspan="3">
                    <input type="button" onclick="report()" value="Отчёт" />
                </td>
            </msh:row>
        </msh:panel>
    </msh:form>
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type="text/javascript">
            $('serviceStreamName').className += " required";
            $('hospTypeName').className += " required";
            $('dateEnd').className += " required";
            function report() {
                if (document.getElementById("serviceStreamName").value!=null && document.getElementById("serviceStreamName").value!=""
                && $('dateBegin').value!=null && $('dateBegin').value!="" && $('dateEnd').value!=null && $('dateEnd').value!="") {
                    HospitalMedCaseService.getSettingsKeyValueByKey("jasperServerUrl", {
                        callback: function (res) {
                            var resMas = res.split("#");
                            if (res != "") {
                                var sstream=document.getElementById("serviceStreamName").value;
                                if (sstream[sstream.length-1]==' ') sstream=sstream.substring(0,sstream.length-1);
                                var billnumtext=($('pigeonHole').value=='')? '':'&billnumtext='+$('pigeonHole').value;
                                HospitalMedCaseService. getVocServiceStreamCodeByName(sstream, {
                                    callback: function (res) {
                                        if (res != "##") {
                                            window.open("http://" + resMas[0] + "/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FRegistryForPaymentForContracts&standAlone=true&decorate=no"
                                                + "&j_username=" + resMas[1] + "&j_password=" + resMas[2] + "&sstream="+ res + "&dstart=" + $('dateBegin').value + "&dfin=" + $('dateEnd').value + "&user=" + document.getElementById('current_username_li').innerHTML
                                            + "&type=" + $('hospType').value + billnumtext);
                                        }
                                    }
                                });
                            }
                            else
                                alert("Нет настройки адреса сервиса!");
                        }
                    });
                }
                else
                    alert("Необходимо заполнить поток обслуживания, тип помощи и период!");
            }
        </script>
    </tiles:put>
</tiles:insert>