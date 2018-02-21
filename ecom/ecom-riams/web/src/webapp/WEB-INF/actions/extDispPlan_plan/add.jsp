<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="extDispPlan_planForm" />
    </tiles:put>
<%
    String typeSort =ActionUtil.updateParameter("ExtDispPlanView","typeSort","1", request) ;
%>
    <tiles:put name="body" type="string">
        <msh:form  action="/extDispPlanAdd.do" defaultField="count" disableFormDataConfirm="true">
        <msh:hidden property="id" />
            <label id="cntLabelValue">Количество записей в плане: <input  type="text" id="cntRecords" name="cntRecords"></label>
        <msh:panel>
            <msh:row>
        <msh:autoComplete property="lpu" label="Отделение" vocName="lpu" size="80"/>
            </msh:row><msh:row>
        <msh:autoComplete property="area" vocName="lpuAreaWithParent" parentAutocomplete="lpu" label="Участок" size="50"/>
            </msh:row><msh:row>
        <msh:autoComplete property="sex" vocName="vocSex" label="Пол"/>
            </msh:row>
            <msh:row>
                <msh:textField property="years" label="Год рождения (через запятую)" />
            </msh:row>
            <msh:row>
                <msh:textField property="count" label="Количество значений"/>
            </msh:row>
        <msh:row>
            <msh:textField property="lastname" label="Поиск по фамилии"/>
        </msh:row>
            <msh:row>
                <td class="label" title="Отображать" colspan="1"><label for="typeSortName" id="typeSortLabel">Отображать:</label></td>
                <td onclick="this.childNodes[1].checked='checked';">
                    <input type="radio" name="typeSort" value="1">  Сортировка по ФИО
                </td>
                <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                    <input type="radio" name="typeSort" value="2">  Произвольная сортировка
                </td>
            </msh:row>
        </msh:panel>
        <input type="button" onclick="findPersons(false)" value="Поиск">
        <input type="button" onclick="findPersons(true)" value="Найти и добавить всех в список">
        <%
         //   ActionUtil.setParameterFilterSql("lpu","area.lpu_id", request) ;
          //  ActionUtil.setParameterFilterSql("area","att.area_id", request) ;
          //  ActionUtil.setParameterFilterSql("sex","pat.sex_id", request) ;
          //  ActionUtil.setLikeSql("lastname","pat.pastname", request);

        %>
        </msh:form>
        <msh:separator colSpan="8" label="Записи в плане"/>
        <div id="resultDiv" name="resultDiv">

        </div>


    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
        <script type="text/javascript" src="./dwr/interface/ExtDispService.js"></script>

            <script type="text/javascript">

                checkFieldUpdate('typeSort','${typeSort}','1');
                function checkFieldUpdate(aField,aValue,aDefaultValue) {
                    eval('var chk =  document.forms[0].'+aField) ;
                    var aMax=chk.length ;
                    //alert(aField+" "+aValue+" "+aMax+" "+chk) ;
                    if ((+aValue)==0 || (+aValue)>(+aMax)) {
                        chk[+aDefaultValue-1].checked='checked' ;
                    } else {
                        chk[+aValue-1].checked='checked' ;
                    }
                }


                function findPersons(needAdd) { //Поиск подходящих пациентов по условиям
                    lpu = $('lpu').value;
                    area=$('area').value;
                    sex=jQuery('#sex').val();
                    lastname =jQuery('#lastname').val();
                    year = jQuery('#years').val();
                    count=+jQuery('#count').val();
                    typeSort = jQuery('input:radio[name=typeSort]:checked').val();
                    //ExtDispService.findPerson(Long aLpuId, Long aAreaId, Long aSexId, String aPatientInfo, Long aYear, Long aLimit,
                    ExtDispService.findPerson(lpu, area, sex, lastname, year,count>0?count:null, typeSort, {
                        callback: function (ret) {
                            if (ret!=null&&ret!="") {
                                if (needAdd) { //Сразу добавляем найденных пациентов в план
                                    addPersonsToPlan(JSON.parse(ret));
                                } else {
                                    arr = JSON.parse(ret);
                                    var table = "<table border='1'>";
                                    table+="<tr id='row_'><td colspan='2'>Фамилия пациента</td><td>Участок прикрепления</td></tr>";
                                    for  (var i in arr) {
                                        if (arr[i].id) {
                                            table += "<tr id='pat_" + arr[i].id + "'>";
                                            table += "<td><input type='button' value='Добавить' onclick='javascript:addPersonToPlan(" + arr[i].id + ")'></td>";
                                            table += "<td>" + arr[i].name + "</td>";
                                            table += "<td>" + arr[i].area + "</td>";
                                            table += "</tr>";
                                        }
                                    }
                                    table+="</table>";
                                    $('resultDiv').innerHTML=table;
                                }

                            } else {
                                alert('Записей не найдено!');
                                $('resultDiv').innerHTML="";
                            }
                        }
                    });
                }
                function addPersonToPlan(aId) {
                    var a={id:aId}; var arr = new Array();arr.push(a);addPersonsToPlan(arr);
                }

                    // На входе получаем массив jsonObject
                function addPersonsToPlan(aPersonJson) { //Добавляем пациента/ов в план
                    if (aPersonJson) {
                        str = JSON.stringify(aPersonJson);

                        ExtDispService.fillDispPlanByPersons(str, ${param.id}, {
                            callback: function (cnt) {
                                alert('В план добавлено '+cnt+' записей');
                                countRecordsInPlan();
                            }
                        });
                    }

                }
                function countRecordsInPlan() {
                    ExtDispService.countRecordsInPlan(${param.id}, {
                        callback: function (ret) {
                            $('cntRecords').value=ret;
                        }
                    });
                }
                countRecordsInPlan();

                function removePersonFromList() { //

                }
            </script>


    </tiles:put>


    <tiles:put name="side" type="string">
            <msh:sideMenu>
                <msh:sideLink params="id" action="/extDispPlanView" name="Поиск населения в плане" title="Поиск населения в плане" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
                <msh:sideLink action="/mis_attachment" name="Выгрузить план в СК" title="Выгрузить план в СК" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
            </msh:sideMenu>
    </tiles:put>
</tiles:insert>
