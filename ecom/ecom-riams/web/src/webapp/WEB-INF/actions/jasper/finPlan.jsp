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
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по типу  (typeGroup2)" colspan="1"><label for="typeGroup2Name" id="ttypeGroupLabel2">Выберите:</label></td>
                    <td id="vmtd1" colspan="1">
                        <input type="radio" name="typeGroup2" value="1" checked> Стационар
                    </td>
                    <td id="vmtd2" colspan="2">
                        <input type="radio" name="typeGroup2" value="2"> ВМП
                    </td>
                    <td id="vmtd3" colspan="3">
                        <input type="radio" name="typeGroup2" value="3"> Поликлиника
                    </td>
                </msh:row>
                <msh:row>
                    <td  id="gtd0" class="label" title="Поиск по промежутку  (typeGroup)" colspan="1"><label for="typeGroupName" id="ttypeGroupLabel">Группировка:</label></td>
                    <td id="gtd1" onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1" checked> по профилям
                    </td>
                    <td  id="gtd2" onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> по отделениям
                    </td>
                    <td  id="gtd3" onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="3"> не группировать
                    </td>
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
                <msh:row>
                    <td  id="mbd0" class="label" title="Поиск по  (typeGroup3)" colspan="1"><label for="typeGroup3Name" id="ttypeGroup3Label">Мобильная поликлиника:</label></td>
                    <td id="mbd1" onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup3" value="1" checked> всё
                    </td>
                    <td  id="mbd2" onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup3" value="2"> только мобильная
                    </td>
                    <td  id="mbd3" onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup3" value="3"> только не мобильная
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
            eval('var chk =  document.forms[0].typeGroup2') ;
            chk[0].checked='checked' ;
            eval('var chk =  document.forms[0].typeGroup2') ;
            chk[0].checked='checked' ;
            eval('var chk =  document.forms[0].typeGroup3') ;
            chk[0].checked='checked' ;
            function report() {
                if ($('dateBegin').value!=null && $('dateBegin').value!="" && $('dateEnd').value!=null && $('dateEnd').value!="") {
                    HospitalMedCaseService.getSettingsKeyValueByKey("jasperServerUrl", {
                        callback: function (res) {
                            var resMas = res.split("#");
                            var url;
                            if (res != "") {
                                var profilek = ($('filterAdd').value != "") ? "&prname=" + $('filterAdd').value : "";

                                if (getValue('typeGroup2')==3) {
                                    url="http://" + resMas[0] + "/jasperserver/flow.html?_flowIddepartment=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F"+"polyclinicFinPlan"+"&standAlone=true&decorate=no"
                                        + "&j_username=" + resMas[1] + "&j_password=" + resMas[2] + "&dstart=" + $('dateBegin').value + "&dfin=" + $('dateEnd').value + "&user=" + document.getElementById('current_username_li').innerHTML + profilek + "&ismobile="+getValue('typeGroup3');
                                }
                                else {
                                    var bdt = ($('hospType').value != "") ? "&bedtype=" + $('hospType').value : "";
                                    var lpu = ($('department').value != "") ? "&lpu=" + $('department').value : "";
                                    var type = getValue('typeGroup');
                                    var type2 = getText('typeGroup2');
                                    url = "http://" + resMas[0] + "/jasperserver/flow.html?_flowIddepartment=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F" + "NAMEREPORT" + "&standAlone=true&decorate=no"
                                        + "&j_username=" + resMas[1] + "&j_password=" + resMas[2] + "&dstart=" + $('dateBegin').value + "&dfin=" + $('dateEnd').value + "&user=" + document.getElementById('current_username_li').innerHTML
                                        + "&type=" + "TYPEREPORT" + bdt + "&fpplan=" + type2;
                                    url = url.replace("TYPEREPORT", type);
                                    if (type == '1')
                                        url = url.replace("NAMEREPORT", "generalFinPlan") + profilek;
                                    else if (type == '2')
                                        url = url.replace("NAMEREPORT", "generalFinPlan") + lpu;
                                    else if (type == '3')
                                        url = url.replace("NAMEREPORT", "totalFinPlan").replace("&type=3", "") + lpu;
                                }
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
            function getText(aField) {
                eval('var chk =  document.forms[0].'+aField) ;
                var val='';
                if (chk[0].checked) val='HospitalFinancePlan'; else if (chk[1].checked) val='VmpFinancePlan'; else if (chk[2].checked) val='PolyclinicFinancePlan';
                return val;
            }
            document.getElementById("vmtd1").onclick=document.getElementById("vmtd2").onclick=function() {
                this.childNodes[1].checked='checked';
                showStac();
            };
            document.getElementById("vmtd3").onclick=function() {
                this.childNodes[1].checked='checked';
                showPolyclinic();
            };
            function showStac() {
                $('hospTypeLabel').removeAttribute("hidden");
                $('hospTypeName').removeAttribute("hidden");
                $('departmentLabel').removeAttribute("hidden");
                $('departmentName').removeAttribute("hidden");
                $('gtd0').removeAttribute("hidden");
                $('gtd1').removeAttribute("hidden");
                $('gtd2').removeAttribute("hidden");
                $('gtd3').removeAttribute("hidden");
                $('mbd0').setAttribute("hidden",true);
                $('mbd1').setAttribute("hidden",true);
                $('mbd2').setAttribute("hidden",true);
                $('mbd3').setAttribute("hidden",true);
            }
            function showPolyclinic() {
                $('hospTypeLabel').setAttribute("hidden",true);
                $('hospTypeName').setAttribute("hidden",true);
                $('departmentLabel').setAttribute("hidden",true);
                $('departmentName').setAttribute("hidden",true);
                $('gtd0').setAttribute("hidden",true);
                $('gtd1').setAttribute("hidden",true);
                $('gtd2').setAttribute("hidden",true);
                $('gtd3').setAttribute("hidden",true);
                $('mbd0').removeAttribute("hidden");
                $('mbd1').removeAttribute("hidden");
                $('mbd2').removeAttribute("hidden");
                $('mbd3').removeAttribute("hidden");
            }
            if (getValue('typeGroup2')==1 || getValue('typeGroup2')==2) showStac();
            else if (getValue('typeGroup2')==3) showPolyclinic();
        </script>
    </tiles:put>
</tiles:insert>