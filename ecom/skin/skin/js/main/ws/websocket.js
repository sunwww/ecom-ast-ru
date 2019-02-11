

var ws_socket;
function ws_exit(){
    try{ws_sendMessage({method:"exit"});ws_removeFromLocalStorage(ws_socketServerStorageName+"_"+jQuery('#current_username_li').html())} catch (e) {console.log("cant exit websocket "+e);}
}
function connectWebSocket() {
    console.log("connectWebSocket");
    if (window.ws_socketServerStorageName) {
        var username= jQuery('#current_username_li').html();
        //Если находим в localStorage информация о сервере WS - работаем. Иначе - запрашиваем у *ServiveJs и заносим результат в localStorage
        var wsServer =ws_getFromLocalStorage(ws_socketServerStorageName+"_"+username);
        if (wsServer==null) {
            VocService.getWebSocketServer({callback:function(s) {
                ws_saveToLocalStorage(ws_socketServerStorageName+"_"+username,s);
                connectWebSocket();
            }
            });
            return;
        }
        console.log("prewsServer="+wsServer);
        wsServer = JSON.parse(wsServer);
        if (wsServer.server!=null) {
            var url = wsServer.server+"/"+wsServer.username;
            ws_socket = new WebSocket(url);
            ws_socket.onopen=function(){console.log('opened');};
            ws_socket.onmessage=function(event) {ws_onMessage(event.data);}
            ws_socket.onerror = function(event) {ws_onError(event);};
            ws_socket.onclose = function(event) {ws_onClose(event);};
        }
    } else {
        console.log("no webSocketRoles");
    }
}
function ws_onClose(event) {
    console.log('Код: ' + event.code + ' причина: ' + event.reason);
    setTimeout(connectWebSocket,10000);
}

function ws_onError(event) {
    jQuery('#ws_finishWorkDiv').html("ОШИБКА СОЕДИНЕНИЯ С СЕРВЕРОМ")
    jQuery('#ws_windowWorkDiv').html("ОШИБКА");
    jQuery('#ws_ticketNumberDiv').html("ОШИБКА");
    jQuery('#ws_nextTicketDiv').unbind("click");
    jQuery('#ws_nextTicketDiv').html("Переподключиться");
    jQuery('#ws_nextTicketDiv').on('click',function(){connectWebSocket();});
    showToastMessage(event.type+" Неизвестная Ошибка",null,null,true);
    console.log("reconnect in 10 sec");
    ws_onClose(event);
}
//Заканчиваем работу с очередью или начинаем работу
function ws_setStartWorking(isStart) {
    if (confirm(isStart?"Приступить к работе?":"Прекратить работу?")){
        ws_sendMessage({method:true==isStart?"startWork":"stopWork"});
    }
}
function ws_getStartWorking(isStart) {
    var fwd =jQuery('#ws_finishWorkDiv');
    fwd.unbind("click");
    if (true==isStart) {
        fwd.html("СТАТУС - РАБОТАЕМ");
        fwd.on('click',function(){ws_setStartWorking(false);});
    } else {
        fwd.html("СТАТУС - ПЕРЕРЫВ");
        fwd.on('click',function(){ws_setStartWorking(true);});
    }
}
function ws_setNewWindowNumber() {
    var num = prompt("Введите номер окошка");
    if (num) {
        ws_sendMessage({method:"changeWindowNumber",windowNumber:num});
    }
}

function ws_getNewWindowNumber(msg) {
    if (msg.windowNumber) {
        jQuery('#ws_windowWorkDiv').html("ОКНО №"+msg.windowNumber);
    }
}
//Отмечаем текущий талон как выполненный и запрашиваем новый талон
function ws_setNewTicket() {
    console.log("ws_setNewTicket===========");
    var msg = {method:"getNextTicket"};
    ws_sendMessage(msg);
}

function ws_sendCountAllMessage() {
    var msg = {method:"countAllSessions"};
    ws_sendMessage(msg);
}
function ws_getNewTicket(msg) {
    var ticket = msg.ticket;
    var number ;
    if (ticket && ticket.ticketNumber) {
        number="ТАЛОН №"+ticket.ticketNumber;
    }  else {
        number="---";
    }
    jQuery('#ws_ticketNumberDiv').html(number);
    if (msg.windowNumber) {ws_getNewWindowNumber(msg);}
    try{ws_getStartWorking(false==msg.isOffline);}catch (e) {console.log("e=="+e);}
}

//Вызываемся при получении сообщения от ws сервера
function ws_onMessage(msg) {
    console.log("ws_onMessage. Получили мессадж = "+msg);
    msg = JSON.parse(msg);
    if (msg.status=='ok') {
        switch(msg.function) {
            case "changeWindowNumber": //Сменили номер окна
                console.log("Новый номер окна: "+msg.windowNumber);
                ws_getNewWindowNumber(msg);
                break;
            case "showAllTickets": //Отобразим все талоны на экране
                console.log("ws_onMessage_"+msg.function);
                showTheMovie(msg);
                break;
            case "showTicket": //Отобразим новый талон
                console.log("ws_onMessage_"+msg.function);
                showTheMovie(msg);
                break;
            case "hideTicket": //Спрячем старый талон
                console.log("ws_onMessage_"+msg.function);
                showTheMovie(msg);
                break;
            case "getNextTicket" :
                ws_getNewTicket(msg);
                break;
            case "startWork": //Устанавливаем режим - работает/не работает
               ws_getStartWorking(true);
               break;
            case "stopWork": //Устанавливаем режим - работает/не работает
                ws_getStartWorking(false);
                break;
            case "sendMessage":
                showToastMessage(msg.message);
                break;
            default:
                showToastMessage(JSON.stringify(msg));
                break;
        }
    } else if (msg.status=='error') {
        if (msg.errorCode=="NO_ACTIVE_QUEUE") {
            jQuery('#ws_ticketNumberDiv').html("Н/О");
        } else {
            showToastMessage(msg.errorCode);
        }

    }
}
function ws_sendMessage(json) {
    console.log("ws_sendMessage="+JSON.stringify(json));
    ws_socket.send(JSON.stringify(json));
}



function ws_sendTextMessage(aFldName) {
    va = jQuery('#'+aFldName).val();
    var json = {method:'sendMessage',message:va?va:"Я - слон!"};
    ws_sendMessage(json);
}


function ws_getFromLocalStorage(aFld) {
    try{return localStorage.getItem(aFld);} catch (e) { console.log("get_local_storage_err="+e);return null;}
}

function ws_saveToLocalStorage(aFld, aValue) {
    try {localStorage.setItem(aFld,aValue);}catch(e) {}
}
function ws_removeFromLocalStorage(aFld) {
    try {localStorage.removeItem(aFld);}catch (e) {console.log("remove ex"+e);}
}