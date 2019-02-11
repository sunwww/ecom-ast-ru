<%--
  Created by IntelliJ IDEA.
  User: vtsybulin
  Date: 15.06.2018
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<html>
<%
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
</head>
<body>
<%
if (kioskType.equals("ADMISSION")) {
    System.out.println("URI="+request.getRequestURI());
    System.out.println("URI="+request.getPathInfo());
    System.out.println("URI="+request.getQueryString());
    System.out.println("URI="+request.getContextPath());

    System.out.println("URI="+request.getPathTranslated());
    String pigeonHole = request.getParameter("pigeonHole");
    request.setAttribute("pigeonHole",pigeonHole);
    request.setAttribute("pigeonHoleName",request.getParameter("pigeonHoleName"));
    String isEmergency = request.getParameter("isEmergency");
    request.setAttribute("isEmergency",isEmergency);
    String token = request.getParameter("token");
    token="66405d38-a173-4cb7-a1b6-3ada51c16ac5";
    //System.out.println("URI="+request.getContextPath());
    if (1==1 ||token!=null) {
        request.setAttribute("token","token:'"+token+"',");
    } else {
        request.setAttribute("token","");
    }
    //Показываем информацию об очереди в приемном отделении
    %>
<msh:panel>
    <msh:autoComplete property="pigeonHole" vocName="vocPigeonHole" label="Приемник"/>
<msh:row>

    <td class="label" title="Представление (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
    <td onclick="this.childNodes[1].checked='checked'; reloadPage();">
        <input type="radio" name="isEmergency" value="1">  Экстренные
    </td>
    <td onclick="this.childNodes[1].checked='checked';reloadPage();">
        <input type="radio" name="isEmergency" value="0">  Плановые
    </td>
    <td onclick="this.childNodes[1].checked='checked';reloadPage();">
        <input type="radio" name="isEmergency" value="-1">  Все
    </td>
</msh:row>
</msh:panel>

<table id="patientWaitingTable">
    <th width="20%">Пациент</th>
    <th width="10%">Время ожидания</th>
    <th width="20%">Время поступления</th>
    <th width="50%">Отделение</th>
<tbody id="patientWaitingTableBody" name="patientWaitingTableBody"></tbody>
</table>
<script type="text/javascript" src="/skin/ac.js"></script>
<script type="text/javascript">
var colors={red:"background-color:red;", orange:"background-color: orange;", yellow:"background-color: yellow;",blue:"background-color: lightblue;"}
    var tbl =jQuery('#patientWaitingTableBody');
    tbl.html("Подождите...");
    $('pigeonHole').value='${pigeonHole}';
    $('pigeonHoleName').value='${pigeonHoleName}';

    getQueue();

    pigeonHoleAutocomplete.addOnChangeCallback( function() {reloadPage();});
    function reloadPage() {
        var em = jQuery('input:radio[name=isEmergency]:checked').val();
        window.location.search="mode=ADMISSION&isEmergency="+em+"&pigeonHole="+$('pigeonHole').value+"&pigeonHoleName="+$('pigeonHoleName');
    }
    function getQueue() {
        jQuery.ajax({
            url:"api/queue/hospital/emergencyQueue"
            ,data:{
               ${token}
                emergency:'${isEmergency}',
                pigeonHole:'${pigeonHole}'
            }
            ,error: function(jqXHR,ex){console.log(ex);setTimeout(getQueue,60000);}
            ,success: function(array) {
                if (!array||array.length==0)  {
                    tbl.html("Нет пациентов в очереди");
                } else {
                    tbl.html("");
                    for (var i=0;i<array.length;i++) {
                        var el = array[i];
                        var minutes = el.minutes;
                        var color="";
                        if (minutes>119){color=colors.red} else if (minutes>89){color=colors.orange} else if (minutes>59) {color=colors.yellow} else if (minutes>29){color=colors.blue}
                        tbl.append("<tr onclick=\"goToPageNewWindow('entityView-stac_ssl.do','"+el.id+"');\"style='"+color+"'><td>"+el.patientInfo+"</td><td>"+el.waitTime+"</td><td>"+el.startTime+"</td><td>"+el.departmentName+"</td></tr>");
                    }//60-90 - yellow
                }
                setTimeout(getQueue,60000);
        }
    });
    }
checkFieldUpdate("isEmergency",'${param.isEmergency}','1');
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
