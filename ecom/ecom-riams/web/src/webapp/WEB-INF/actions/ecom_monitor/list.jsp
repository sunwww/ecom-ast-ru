<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


<tiles:put name='body' type='string'>
   <table border="1">
       <tr>
           <th>ИД</th>
           <th>Название</th>
           <th>Статус</th>
           <th>Последнее сообщение</th>
           <th>Финишный мессадж</th>
       </tr>
       <tbody id="monitorList">

       </tbody>

   </table>

</tiles:put>

<tiles:put name='side' type='string'>
</tiles:put>

<tiles:put name="javascript" type="string">
    <script type="text/javascript">

        function update() {
            RemoteMonitorService.getAllMonitors({
                callback: function (list) {
                    list = JSON.parse(list);
                    var txt="";
                    if (list.length===0) {
                        console.log("no monitors");
                        txt = "<tr><td colspan='4'>Нет активных мониторов</td></tr>";
                    } else {
                        for (var i = 0; i < list.size(); i++) {
                            var el = list[i];
                            txt += "<tr>";
                            txt += "<td>" + el.id + "</td><td>" + el.name + "</td><td>" + el.status + "</td><td>" + el.text + "</td><td>" + el.finishText + "</td>";
                            txt += "</tr>";
                        }
                    }
                    jQuery('#monitorList').html(txt);
                }
            });
        }
         update() ;

    </script>
</tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">

    </style>
</tiles:put>


</tiles:insert>