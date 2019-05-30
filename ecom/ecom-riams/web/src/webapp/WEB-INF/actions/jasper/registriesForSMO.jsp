<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Реестры для СМО</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/registriesForSMO.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                    <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                </msh:row>

                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                    <td  id="gtd0" class="label" title="Поиск по промежутку  (typeGroup)" colspan="1"><label for="typeGroupName" id="ttypeGroupLabel">Вид группировки:</label></td>
                    <td id="gtd1" onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="1" checked> По ФИО
                    </td>
                    <td  id="gtd2" onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> По виду МП
                    </td>
                </msh:row>
                <msh:row>
                    <msh:textArea rows="15" property="filterAdd1" label="Номера карт" fieldColSpan="3"></msh:textArea>
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
            $('dateEnd').className += " required";
            $('filterAdd1').className = "";

            function report() {
                if ($('dateBegin').value!=null && $('dateBegin').value!="" && $('dateEnd').value!=null && $('dateEnd').value!=""
                    && $('filterAdd1').value!=null && $('filterAdd1').value!="") {
                    formatCards();
                    HospitalMedCaseService.getSettingsKeyValueByKey("jasperServerUrl", {
                        callback: function (res) {
                            var resMas = res.split("#");
                            var url;
                            if (res != "") {
                                url="http://" + resMas[0] + "/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FNAMEREPORT&standAlone=true&decorate=no"
                                    + "&j_username=" + resMas[1] + "&j_password=" + resMas[2] + "&cards="+$('filterAdd1').value
                                    + "&dstart=" + $('dateBegin').value + "&dfin=" + $('dateEnd').value + "&user=" + document.getElementById('current_username_li').innerHTML;
                                var type = getValue('typeGroup');
                                url= (type == '1')? url.replace("NAMEREPORT","Reestr2_for_SMO_v1") : url.replace("NAMEREPORT","Reestr_for_SMO_v3");
                                window.open(url);
                            }
                            else
                                showToastMessage("Нет настройки адреса сервиса!",null,true);
                        }
                    });
                }
                else
                    showToastMessage("Необходимо заполнить все поля!",null,true);
            }
            //получить значение группы радиобт
            function getValue(aField) {
                eval('var chk =  document.forms[0].'+aField) ;
                var val=2;
                if (chk[0].checked) val=1; else if (chk[1].checked) val=2; else if (chk[2].checked) val=3;
                return val;
            }
            //привести список кард в нужный формат: номер карт через 1 пробел
            function formatCards() {
                var str=$('filterAdd1').value.replace(new RegExp('\n', "g"), " ");
                str=str.replace(new RegExp("  ","g")," ");
                for (var i=0; i<str.length; i++) {
                    var s=str[i];
                    if (s!=' ' && isNaN(+s) && !isLetter(s)) {
                        if (i==0 || i==str.length-1)  //любой первый/последний неподходящий символ - удалить
                            str = str.replace(new RegExp(s, "g"), "");
                        else {
                            if (str[i-1]==' ' || str[i+1]==' ') //если это просто левые символы между цифрами
                                str = str.replace(new RegExp(s, "g"), " "); //чтобы 2 номера не схлопнулись
                            else
                                str = str.replace(new RegExp(s, "g"), "");
                        }
                        i=0;
                    }
                }
                while (str.indexOf('  ')!=-1) {
                    str=str.replace(new RegExp("  ","g")," ");
                }
                if (!str.startsWith(' ')) str=' '+str;
                if (!str.endsWith(' ')) str=str+' ';
               $('filterAdd1').value=str;
            }
            //провера не если буква. Оригинальная альтернатива регулярке
            function isLetter(c) {
                return c.toLowerCase() != c.toUpperCase();
            }
        </script>
    </tiles:put>
</tiles:insert>