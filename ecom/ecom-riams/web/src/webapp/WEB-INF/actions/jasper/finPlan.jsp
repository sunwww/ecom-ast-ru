<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Выполнение финансового плана</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/finPlan.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                    <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="hospType" fieldColSpan="4" horizontalFill="true" label="Тип коек" vocName="vocTypeStacJReport"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="filterAdd" fieldColSpan="4" horizontalFill="true" label="Профиль" vocName="vocE2MedHelpProfileJasper"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="5" label="Отделение" horizontalFill="true" vocName="lpu"/>
                </msh:row>
                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                    <td class="label" title="Поиск по промежутку  (typeGroup)" colspan="1"><label for="typeGroupName" id="ttypeGroupLabel">Выберите:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1" checked> группировать по профилям
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> группировать по отделениям
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="3"> общий финансовый план
                    </td>
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
            eval('var chk =  document.forms[0].typeGroup') ;
            chk[0].checked='checked' ;
            function report() {
                if ($('dateBegin').value!=null && $('dateBegin').value!="" && $('dateEnd').value!=null && $('dateEnd').value!="") {
                    HospitalMedCaseService.getSettingsKeyValueByKey("jasperServerUrl", {
                        callback: function (res) {
                            var resMas = res.split("#");
                            if (res != "##") {
                                var bdt=($('hospType').value!="")? "&bedtype=" + $('hospType').value:"";
                                var profilek=($('filterAdd').value!="")? "&prname=" + $('filterAdd').value:"";
                                var lpu=($('department').value!="")? "&lpu=" + $('department').value:"";
                                var type=getValue('typeGroup');
                                url="http://" + resMas[0] + "/jasperserver/flow.html?_flowIddepartment=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F"+"NAMEREPORT"+"&standAlone=true&decorate=no"
                                    + "&j_username=" + resMas[1] + "&j_password=" + resMas[2] + "&dstart=" + $('dateBegin').value + "&dfin=" + $('dateEnd').value + "&user=" + document.getElementById('current_username_li').innerHTML
                                    +"&type="+"TYPEREPORT" + bdt;
                                url=url.replace("TYPEREPORT", type);
                                if (type=='1')
                                    url = url.replace("NAMEREPORT", "generalFinPlan")+ profilek;
                                else if (type=='2')
                                    url = url.replace("NAMEREPORT", "generalFinPlan")+lpu;
                                else if (type=='3')
                                    url = url.replace("NAMEREPORT", "totalFinPlan").replace("&type=3","")+ lpu;
                                window.open(url);
                            }
                            else
                                alert("Нет настройки адреса сервиса!");
                        }
                    });
                }
                else
                    alert("Необходимо заполнить все поля!");
            }
            function getValue(aField) {
                eval('var chk =  document.forms[0].'+aField) ;
                var val=2;
                if (chk[0].checked) val=1; else if (chk[1].checked) val=2; else if (chk[2].checked) val=3;
                return val;
            }
        </script>
    </tiles:put>
</tiles:insert>