<%@ page import="ru.ecom.web.util.ActionUtil" %><%--
  Created by IntelliJ IDEA.
  User: vtsybulin
  Date: 15.06.2018
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<html>
<%
    boolean isDoctor = !ActionUtil.isUserInRole("/Policy/TV",request);
    String kioskType = request.getParameter("mode");
    if (kioskType==null) {kioskType="";}
    kioskType = kioskType.toUpperCase();
/**
 * ADMISSION = Очередь в приемном отделении
 * */
    String title ;
    switch (kioskType) {
        case "ADMISSION":
            title="Очередь в приемном отделении";
            break;
        case "QUEUE":
            title="Электронная очередь";
            break;
            default:
                title="НЕПОНЯТНО";
    }

    request.setAttribute("pageTitle",title);
%>
<head>
    <title>${pageTitle}</title>
    <link rel="stylesheet" type="text/css" href="/skin/css/css/select2/select2.min.css">
</head>
<body>
<%
if (kioskType.equals("ADMISSION")) {
    String pigeonHole = request.getParameter("pigeonHole");
    request.setAttribute("pigeonHole",pigeonHole);
    request.setAttribute("pigeonHoleName",request.getParameter("pigeonHoleName"));
    String isEmergency = request.getParameter("isEmergency");
    request.setAttribute("isEmergency",isEmergency);
    String token = "66405d38-a173-4cb7-a1b6-3ada51c16ac5";
        request.setAttribute("token","token:'"+token+"',");
   //     boolean isDoctor = "1".equals(request.getParameter("isDoctor"));
        request.setAttribute("isDoctor",isDoctor);
    //Показываем информацию об очереди в приемном отделении
    if (isDoctor) {
    %>
<msh:panel>
<msh:row>

    <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
    <td onclick="this.childNodes[1].checked='checked'; reloadPage();">
        <input type="radio" name="isEmergency" value="1" checked="checked">  Экстренные
    </td>
    <td onclick="this.childNodes[1].checked='checked';reloadPage();">
        <input type="radio" name="isEmergency" value="0">  Плановые
    </td>
    <td onclick="this.childNodes[1].checked='checked';reloadPage();">
        <input type="radio" name="isEmergency" value="-1">  Все
    </td>
</msh:row>
<msh:row>

    <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отказные:</label></td>
    <td onclick="this.childNodes[1].checked='checked'; reloadPage();">
        <input type="radio" name="onlyDenied" value="1" checked="checked">  Отказ от госпитализации
    </td>
    <td onclick="this.childNodes[1].checked='checked';reloadPage();">
        <input type="radio" name="onlyDenied" value="0">  Госпитализированные
    </td>
    <td onclick="this.childNodes[1].checked='checked';reloadPage();">
        <input type="radio" name="onlyDenied" value="-1">  Все
    </td>
</msh:row>
    <msh:row>
        <td>
        <label>Период: <input type="text" size="10" id="startDate" name="startDate" value="${param.startDate}">
        <input type="text" size="10" id="finishDate" name="finishDate" value="${param.finishDate}"></label>
        <input type="button" value="Поиск" onclick="reloadPage()">
        <input type="button" value="Excel" onclick="mshSaveTableToExcelById('patientWaitingTable')">
    </td>
    </msh:row>
    <msh:row><td colspan="4">
        <select class="select-pigeonHole" id="select-pigeonHole" style="width: 100%"></select>
    </td>
    </msh:row>

</msh:panel>
<%}%>
<table id="patientWaitingTable">
    <th width="10%">Пациент</th>
    <%if (isDoctor) {%>
    <th width="5%">Время ожидания</th>
    <th width="10%">Время поступления</th>
    <th width="10%">Отделение</th>
    <th width="35%" >Назначенные услуги</th>
    <%}%>
    <th width="30%" >Выполненные услуги</th>
<tbody id="patientWaitingTableBody" name="patientWaitingTableBody"></tbody>
</table>
<script type="text/javascript" src="/skin/ac.js"></script>
<script type="text/javascript">
    <% if (isDoctor) { %>
    jQuery(document).ready(function() {initPigeonHole();});
    var s2;
    function initPigeonHole() {
        var pId=+'${param.pigeonHole}';
         s2 = jQuery("#select-pigeonHole");
        s2.select2({
            ajax:{
                url:'simpleVocAutocomplete/vocPigeonHole?type=json'
                ,datatype:'json'
                ,delay:250
                ,data: function(params) {

                }
            }
            ,placeholder:"Приемник"
        });

        if (pId>0) {
            s2.val("1").trigger("change");
            console.log(+pId+"<<<");
        }
        s2.on('select2:select', function (e) {
            console.log(e);
            reloadPage();
        });
    }
<% } %>
var colors={red:"background-color:red;"
    , orange:"background-color: orange;"
    , yellow:"background-color: yellow;"
    ,blue:"background-color: lightblue;"
    ,green:"background-color: lightgreen;"};
    var tbl =jQuery('#patientWaitingTableBody');
    tbl.html("Подождите...");

    getQueue();
    var isDoctor = ${isDoctor} ;
    new dateutil.DateField($('startDate'));
    new dateutil.DateField($('finishDate'));

    function reloadPage() {
        var onlyDenied = jQuery('input:radio[name=onlyDenied]:checked').val();
        var em = jQuery('input:radio[name=isEmergency]:checked').val();
        var append ="";
        if ($('startDate').value) {
            append="&startDate="+$('startDate').value+"&finishDate="+($('finishDate').value ? $('finishDate').value : $('startDate').value);
        }
        window.location.search="mode=ADMISSION&onlyDenied="+onlyDenied+"&isEmergency="+em+"&pigeonHole="+(jQuery('#select-pigeonHole').val()?jQuery('#select-pigeonHole').val():"")+"&pigeonHoleName=${pigeonHoleName}"+append;
    }
    function getQueue() {
        jQuery.ajax({
            url:"api/queue/hospital/emergencyQueue"
            ,data:{
               ${token}
                emergency:'${isEmergency}',
                pigeonHole:'${param.pigeonHole}',
                isDoctor:${isDoctor},
                startDate:'${param.startDate}',
                finishDate:'${param.finishDate}',
                onlyDenied: '${param.onlyDenied}'
            }
            ,error: function(jqXHR,ex){console.log(ex);alert('Произошла какая-то ошибка: '+ex);setTimeout(getQueue,60000);}
            ,success: function(array) {
                if (!array||array.length===0)  {
                    tbl.html("Нет пациентов в очереди");
                } else {
                    tbl.html("");
                    for (var i=0;i<array.length;i++) {
                        var el = array[i];
                        var minutes = el.minutes;
                        var color="";
                        if (minutes>119){color=colors.red} else if (minutes>89){color=colors.orange} else if (minutes>59) {color=colors.yellow} else if (minutes>29){color=colors.blue} else {color=colors.green;}
                        tbl.append("<tr"+(isDoctor ? " onclick=\"goToPageNewWindow('entityView-stac_ssl.do','"+el.id+"');\" ": "")+" style='"+color+"'>" +
                            "<td>"+el.patientInfo+"</td>" +
                            (isDoctor ? "<td>"+el.waitTime+"</td><td>"+el.startTime+"</td><td>"+el.departmentName+"</td>" +
                                "<td>"+(el.planServices ? el.planServices : "-")+"</td>" :"") +"<td>"+(el.madeServices ? el.madeServices : "-")+"</td>"+
                            "</tr>");
                    }//60-90 - yellow
                }
                if ('${param.startDate}'==='') setTimeout(getQueue,60000);
        }
    });
    }
checkFieldUpdate("isEmergency",'${param.isEmergency}','1');
checkFieldUpdate("onlyDenied",'${param.onlyDenied}','1');
function checkFieldUpdate(aField,aValue,aDefaultValue) {
    if (jQuery(":radio[name="+aField+"][value='"+aValue+"']").val()!=undefined) {
        jQuery(":radio[name="+aField+"][value='"+aValue+"']").prop('checked',true);
    } else {
        jQuery(":radio[name="+aField+"][value='"+aDefaultValue+"']").prop('checked',true);
    }
}

</script>
<%
} else {out.print("Я не знаю что вы от меня хотите");}
%>
</body>
</html>
