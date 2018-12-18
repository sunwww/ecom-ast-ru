<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Отчёт по онкологическим случаям</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/journal_onco.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                    <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                </msh:row>
                <msh:row>
                    <msh:textField property="filterAdd1" label="Номер стат. карты" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
                    <td id="td1">
                        <input type="radio" name="isCreated" value="1">  Случаи созданы
                    </td>
                    <td id="td2">
                        <input type="radio" name="isCreated" value="0">  Случаи НЕ созданы
                    </td>
                    <td id="td3">
                        <input type="radio" name="isCreated" value="-1">  Все
                    </td>
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="button" onclick="getList(0,1);" value="Найти" />
                    </td>
                </msh:row>
                <table hidden id="patientTable" border="1px">
                <th width="3%">Случай создан</th>
                <th width="15%">Пациент</th>
                <th width="15%">Отделение</th>
                <th width="7%">Стат. карта</th>
                <th width="10%">Период госпитализации</th>
                <th width="15%">Лечащий врач</th>
                <th width="35%">Основной выписной диагноз</th>
                <tbody id="patientTableBody" name="patientTableBody"></tbody>
                </table>
                <div hidden id="directionBtns">
                    <input type="button" value="<|" onclick="getList(0,1);"/>
                    <input type="button" value="<" onclick="getList(null,-1);"/>
                    <input type="button" value=">" onclick="getList(null,1);"/>
                    <input type="button" value=">|" onclick="getList(null,2);"/>
                </div>
                <div id="textMsg">
                    Выберите параметры и нажмите Найти
                </div>
            </msh:panel>
        </msh:form>

    </tiles:put>
</tiles:insert>
<script type="text/javascript">
    var id=0;
    var id2=0;
    document.getElementsByName("isCreated")[2].checked='checked';
    var tbl =jQuery('#patientTableBody');
    function getList(ID,next) {
        document.getElementById("patientTable").removeAttribute("hidden");
        document.getElementById("directionBtns").removeAttribute("hidden");
        document.getElementById("textMsg").setAttribute("hidden",true);
        tbl.html("Подождите...");
        //что это единичный шаг - чтоб не уходить вникуда
        var flag1StepLeft=(ID==null && next==-1);
        var flag1StepRight=(ID==null && next==1);
        if (typeof next=='undefined') next=1;
        if (ID==null) ID=(next>0)? id2:id;
        jQuery.ajax({
            url:"api/onco/list"
            ,data:{
                id:ID,
                next:next,
                department:$('department').value,
                dateBegin:$('dateBegin').value,
                dateEnd:$('dateEnd').value,
                isCreated:jQuery('input:radio[name=isCreated]:checked').val(),
                stat:$('filterAdd1').value
            }
            ,error: function(jqXHR,ex){console.log(ex);setTimeout(getList,60000);}
            ,success: function(array) {
                document.getElementById("patientTable").removeAttribute("hidden");
                if (!array||array.length==0)  {
                    if (!flag1StepLeft && !flag1StepRight) {
                        tbl.html("Нет СЛС");
                        document.getElementById("directionBtns").setAttribute("hidden",true);
                    }
                    else {
                        if (flag1StepLeft) getList(0,1);
                        if (flag1StepRight) getList(null,2);
                    }
                } else {
                    tbl.html("");
                    for (var i=0;i<array.length;i++) {
                        var el = array[i];
                        if (i==0) id=el.id;
                        tbl.append("<tr onclick=\"goToPageNewWindow('entityView-stac_ssl.do','"+el.id+"');\"><td align='center'><b>"+el.done+"</b></td><td align='center'>"+el.patinfo+"</td><td align='center'>"+el.depname+"</td><td align='center'>"+el.sccode+"</td><td align='center'>"+el.period+"</td><td align='center'>"+el.worker+"</td><td align='center'>"+el.d+"</td></tr>");
                        if (i==array.length-1) id2=el.id;
                    }
                    document.getElementById("directionBtns").removeAttribute("hidden");
                }
            }
        });
    }
    function clear() {
        document.getElementById("patientTable").setAttribute("hidden",true);
        document.getElementById("directionBtns").setAttribute("hidden",true);
        document.getElementById("textMsg").removeAttribute("hidden");
    }
    departmentAutocomplete.addOnChangeCallback(function() {clear();});
    $('dateBegin').oninput = function() {clear();};
    $('dateEnd').oninput = function() {clear();};
    $('dateBegin').onfocus = function() {clear();};
    $('dateEnd').onfocus = function() {clear();};
    $('filterAdd1').oninput = function() {clear();};
    document.getElementById("td1").onclick=function() { clear();this.childNodes[1].checked='checked';};
    document.getElementById("td2").onclick=function() { clear();this.childNodes[1].checked='checked';};
    document.getElementById("td3").onclick=function() { clear();this.childNodes[1].checked='checked';};
</script>