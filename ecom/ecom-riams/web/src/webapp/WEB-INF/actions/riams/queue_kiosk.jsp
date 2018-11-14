<%@ page import="ru.ecom.web.login.LoginInfo" %><%--
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
String queueCode = request.getParameter("queue");
String kioskMode = request.getParameter("mode");
if (queueCode==null) {queueCode="CHARGED";}
    queueCode = queueCode.toUpperCase();
request.setAttribute("queueCode",queueCode);


request.setAttribute("username", LoginInfo.find(request.getSession(true)).getUsername());
if (kioskMode==null || kioskMode.equals("")) kioskMode="TV";
request.setAttribute("kioskMode",kioskMode);

    /**
     * Экран показа движения очереди. Получаем на вход код очереди - (ОМС,платно,..)
     * */
    String title ;
    switch (queueCode) {
        case "CHARGED":
            title="ПЛАТНЫЕ УСЛУГИ";
            break;
        case "OMC":
            title="OMC";
            break;
        case "JAVA":
            title="ПОМОЩЬ ПРОГРАММИСТОВ";
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
    <style type="text/css">
        #patientWaitingTable {
            text-align: center;
            font-size: 30px;
            margin-bottom: 10px;
            width:100%;
        }
    </style>
    <input type="hidden" id="current_username_li" value="${username}">
<%
if (kioskMode.equals("TV")) {
%>

<table id="patientWaitingTable" border="1">
    <th width="50%">Талон</th>
    <th width="50%">Окно</th>
<tbody id="patientWaitingTableBody" name="patientWaitingTableBody"></tbody>
</table>

<%
}  else if (kioskMode.equals("QUEUE")) { //Делаем таблицу на всё окно. В таблице каждая ячейка - очередь. При щелчке на очереди выходит талончик
%>
<div id="ticketDiv" style="display: none; width: 100px; height: 200px">
</div>
<table id="queueTable" border="1" style="width: 100%; height: 100%">
</table>
<%}%>

<script type="text/javascript" src="./dwr/interface/VocService.js"></script>
<script type="text/javascript" src="/skin/ac.js"></script>
<script type="text/javascript" src="/skin/engine.js"></script>
<script type="text/javascript"> //Общие скрипты
var ws_socketServerStorageName="ws_server_name";
window.onload = function(){${kioskMode}ONLOAD();};

function QUEUEONLOAD() {
    connectWebSocketKiosk();
    getQueueList();
}

function TVONLOAD(){
    connectWebSocketKiosk();
    setTimeout(getAllTickets,4000);
}

/**Подключаемся с сокет-серверу*/
function connectWebSocketKiosk() {
    console.log("connectWebSocketKiosk");
    if (window.ws_socketServerStorageName+'${username}') {
        //Если находим в localStorage информация о сервере WS - работаем. Иначе - запрашиваем у *ServiveJs и заносим результат в localStorage
        var wsServer =ws_getFromLocalStorage(ws_socketServerStorageName+'${username}');
        if (wsServer==null) {
            VocService.getWebSocketServer({callback:function(s) {console.log(s);wsServer=s;ws_saveToLocalStorage(ws_socketServerStorageName+'${username}',s);}});
        }
        console.log("prewsServer="+wsServer);
        wsServer = JSON.parse(wsServer);
        if (wsServer.server!=null) {
            var url = wsServer.server+"/"+wsServer.username;
            console.log("Create new WebSocket, url for ws = "+url);
            ws_socket = new WebSocket(url);
            ws_socket.onopen=function(){console.log('opened');};
            ws_socket.onmessage=function(event) {console.log("on message "+event.data);
                showTheMovie(event.data);
            }
            ws_socket.onerror = function(event) {
                console.log("ws_onError:"+JSON.stringify(event));
                showToastMessage(event.type+" Ошибка " + error.code+" "+event.reason);
                setTimeout(connectWebSocketKiosk,5000); //Переподключимся при разрыве соединения
            };
            ws_socket.onclose = function(event) {
                console.log('Код: ' + event.code + ' причина: ' + event.reason);
            };
        }
    } else {
        console.log("no webSocketRoles");
    }
}
</script>
<script type="text/javascript"> //Скрипты для инфомата записи на прием
    var tbl =jQuery('#queueTable');
    //Список очередей

    function getQueueList(){
        jQuery.ajax({
            url:"api/queue/ticket/getQueues"
            ,data:{terminal:null}
        }).done (function(array) {
            if (array.length==0)  {
                tbl.html("Нет доступных очередей");
            } else {
                tbl.html("");
                for (var i=0;i<array.length;i++) {
                    //разбиваем по 2 элемента на строке
                    var el = array[i];
                    var qId= el.queueId;
                    var qName= el.queueName;
                    var color="";

                    tbl.append("<tr style='"+color+"'><td align='center' onclick='createNewTicket("+qId+")'>"+qName+"</td></tr>");
                }
            }
            setTimeout(getQueueList,1800000); //DEBUG //TODO Убрать перед релизом
        });
    }
    //Получаем билетик
    function createNewTicket(queueId) {
        jQuery.ajax({
            url:"api/queue/ticket/getNewTicket"
            ,data:{queue:queueId}
        }).done (function(json) {
            if (json!=null)  {
                ws_sendMessage({method:"createNewTicket", ticket:json});
                printNewTicket(json);
            }
        });
    }
    function printNewTicket(json) {
      try {
          var html = "<p align='center'>Очередь: " + json.queueName + "</p>";
          html += "<p align='center'>" + json.queueDate + "</p>";
          html += "<p align='center'>Номер " + json.fullNumber + "</p>";
          html += "<p align='center'>" + json.ticketDate + "</p>";
          var mywindow = window.open('', 'my div', 'height=200,width=200');
          mywindow.document.write('<html><head><title>my div</title>');
          mywindow.document.write('</head><body >');
          mywindow.document.write(html);
          mywindow.document.write('</body></html>');
          mywindow.document.close();
          mywindow.focus();
          mywindow.print();
          mywindow.close();
          return true;
      } catch (e) {console.log("Не удалось напечатать талон: "+e);}
    }
</script>

<script type="text/javascript"> //Скрипты для телевизора

/* Получаем все талоны в очереди */
function getAllTickets(){
       console.log("getAllTickets");
        ws_sendMessage({method:"showAllTickets"});
    }

    function showTheMovie(msg) {
        msg = JSON.parse(msg);

        var tbl = jQuery('#patientWaitingTableBody');
        if (msg.status=="error") {
            if (msg.errorCode=="NO_ACTIVE_QUEUE") {
                console.log("TV, no queue, ok...")
            } else {
                alert("Ошибка: "+msg.errorCode+" "+msg.errorName);
            }
            return;
        }
        console.log("showTheMovie = "+msg.function);
        switch (msg.function) {
            case "getNextTicket":
                console.log("getNextTicket= "+JSON.stringify(msg));
                break;
            case "createNewTicket":
                console.log("Талон с номером "+msg.ticket.fullNumber+" отправлен к оператору (или нет)");
                return;
            case "showAllTickets": //Разово заполняем всю таблицу
                if (msg.tickets) {
                    console.log("Количество активных талонов = "+msg.tickets.length);
                    for (i=0;i<msg.tickets.length;i++) {
                        var t = msg.tickets[i];
                        tbl.append("<tr id='tr_"+t.ticketId+"'><td>"+t.ticketNumber+"</td><td>"+t.window+"</td></tr>");
                    }
                }
                break;
            case "showTicket": //Показываем новый талон
                var t = msg.ticket;
                tbl.append("<tr id='tr_"+t.ticketId+"'><td>"+t.ticketNumber+"</td><td>"+t.window+"</td></tr>");

                break;
            case "hideTicket": //Помечаем талон как выполненный
                var t = msg.ticket;
                tbl.children("#tr_"+t.ticketId).remove();
                break;
            default:
                alert("UNKNOWN FUNCTION"+msg.function);
        }
    }

</script>

</body>
</html>
