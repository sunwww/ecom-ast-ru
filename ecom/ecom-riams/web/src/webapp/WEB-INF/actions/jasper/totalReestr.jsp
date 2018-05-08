<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Реестр по платникам</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/totalReestr.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
        <msh:panel>
            <msh:row>
                <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
            </msh:row>
            <msh:row>
                <msh:autoComplete property="serviceStream" fieldColSpan="4" horizontalFill="true" label="Поток обслуживания" vocName="vocSstreamE2Entry"/>
            </msh:row>
            <msh:row>
                <msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип помощи" vocName="vocTypeHelpJReport"/>
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
            function report() {
                if (document.getElementById("serviceStreamName").value!=null && document.getElementById("serviceStreamName").value!=""
                && $('dateBegin').value!=null && $('dateBegin').value!="" && $('dateEnd').value!=null && $('dateEnd').value!="") {
                    HospitalMedCaseService.getSettingsKeyValueByKey("jasperServerUrl", {
                        callback: function (res) {
                            var resMas = res.split("#");
                            window.location.href = "totalReestr.do?dateBegin="+$('dateBegin').value+"&dateEnd="+$('dateEnd').value+"&serviceStream="+$('serviceStream').value+"&hospType="+$('hospType').value;
                            if (res != "##") {
                                var sstream=document.getElementById("serviceStreamName").value;
                                if (sstream[sstream.length-1]==' ') sstream=sstream.substring(0,sstream.length-1);
                                HospitalMedCaseService. getVocServiceStreamCodeByName(sstream, {
                                    callback: function (res) {
                                        if (res != "##") {
                                            window.open("http://" + resMas[0] + "/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F2KSG&standAlone=true&decorate=no"
                                                + "&j_username=" + resMas[1] + "&j_password=" + resMas[2] + "&sstream="+ res + "&dstart=" + $('dateBegin').value + "&dfin=" + $('dateEnd').value + "&user=" + document.getElementById('current_username_li').innerHTML
                                            + "&type=" + $('hospType').value);
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
                    alert("Необходимо заполнить все поля!");
            }
        </script>
    </tiles:put>
</tiles:insert>