package ru.ecom.websocket;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.queue.domain.QueueTicket;
import ru.ecom.queue.service.IQueueService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/ws_queue/{username}", configurator =HttpSessionConfigurator.class )
public class QueueWebSocket {
    private static final Logger LOG = Logger.getLogger(QueueWebSocket.class);
    private static final String TVROLE="TV";
    private static final String OPERATORROLE="OPERATOR";
    private static final String ADMINROLE="ADMIN";
    public QueueWebSocket() {}

    private static final HashMap<String, OperatorSession> sessions= new HashMap<>();

    /** имя пользователя */
    public String getUsername() {return theUsername;}
    public void setUsername(String aUsername) {theUsername = aUsername;}
    /** имя пользователя */
    private String theUsername ;

    /** Injection */
    public Injection getInjection() {return theInjection;}
    public void setInjection(Injection aInjection) {theInjection = aInjection;}
    /** Injection */
    private Injection theInjection ;

    public Session getSession(){return session;}
    private Session session;

    @OnOpen
    public void onOpen(@PathParam("username") String aUsername
            ,  Session aSession, EndpointConfig aConfig) throws NamingException {
        session=aSession;
        setUsername(aUsername);

        OperatorSession os;
        if (getInjection()==null) {
            HttpSession s = (HttpSession) aConfig.getUserProperties().get(HttpSession.class.getName());
            setInjection((Injection) s.getAttribute(Injection.class.getName()));
        }
        if (!sessions.containsKey(aUsername)) { //Выполняется разово при входе пользователя в систему.
            os = new OperatorSession(this);
            String role=aUsername.startsWith("TV_")?TVROLE:OPERATORROLE;
            os.setRole(role);
            WorkFunction wf = getInjection().getService(IQueueService.class).getWorkFunctionByUsername(aUsername);
            if (wf==null || wf.getQueue()==null) {
                LOG.error("В этой рабочей функции у пользователя "+aUsername+" нет очереди");
                sendErrorMessage("NO_ACTIVE_QUEUE","В этой рабочей функции у пользователя нет очереди",os);
                return;
            }
            os.setQueueCode(wf.getQueue().getCode());
            os.setWorkFunction(wf);
            os.setWindow(wf.getWindowNumber());
            LOG.error("set window = "+os.getWindow());
            sessions.put(aUsername, os);
            sendBroadCastMessage(getOKJson("sendMessage").put("message"," Пришел новый пользователь "+aUsername),ADMINROLE);
        } else {
            os = sessions.get(aUsername);
            os.setSession(aSession);
            os.setIsOffline(false);
            sendBroadCastMessage(getOKJson("sendMessage").put("message","client "+aUsername+" is ONLINE"),ADMINROLE);
        }
        JSONObject ret = getOKJson("getNextTicket", os).put("ticket",getCurrentTicket(os));
        sendMessage(ret,os);
    }

    @OnClose
    /**При выходе пользователя*/
    public void onClose(@PathParam("username") String aUsername){
        OperatorSession os = sessions.get(aUsername);
        if (os!=null)os.setIsOffline(true);
    }

    @OnMessage
    public void onMessage(@PathParam("username") String aUsername,String aMessage) {
        OperatorSession operatorSession;
        try {
            operatorSession=sessions.get(aUsername);

            JSONObject msg = new JSONObject(aMessage) ;
            String method =msg.getString("method");
            IQueueService queueService = getInjection().getService(IQueueService.class);
            switch (method) {
                case "changeWindowNumber":
                    setWindowNumber(method, operatorSession,msg.getString("windowNumber"));
                    break;
                case "createNewTicket": //Создан новый талон, попробуем найти ему свободного оператора
                    findFreeOperatorByTicket(msg.getJSONObject("ticket"));
                    break;
                case "exit":
                    onLogout(operatorSession);
                    break;
                case "showAllTickets":
                   if (operatorSession!=null) sendMessage(getOKJson(method).put("tickets",getAllActiveTickets(operatorSession.getQueueCode())),operatorSession);
                    break;
                case "getNextTicket": //Получаем запрос на взятие в работу нового талона
                    operatorSession.setIsOffline(false);
                    QueueTicket ticket = operatorSession.getTicket();
                    if (ticket!=null) {
                        LOG.info("MARK TICKET AS EXECUTED ID = "+ticket.getId());
                        markTicketExecuted(ticket, queueService,operatorSession); //Заканчиваем работу с талоном, убираем его с экрана
                    }
                    operatorSession.setTicket(null);
                    ticket = queueService.getFirstTicketInQueue(aUsername);
                    operatorSession.setTicket(ticket);
                    if (ticket!=null) { //если есть талоны в очереди
                        startTicketExecute(operatorSession,ticket);
                        LOG.info("new ticket = "+ticket.getId());
                        showTicketOnTheScreen(ticket);
                    } else {
                        operatorSession.setIsBusy(false);
                        LOG.info("no ticket in queue");
                    }
                    JSONObject ret = getOKJson(method, operatorSession).put("ticket", getCurrentTicket(operatorSession,ticket));
                    sendMessage(ret,operatorSession);
                    break;
                case "stopWork":
                    if (operatorSession.getTicket()!=null) {
                        markTicketExecuted(operatorSession.getTicket(),queueService,operatorSession);
                    }
                    operatorSession.setIsOffline(true);
                    operatorSession.setIsBusy(true);
                    sendMessage(getOKJson(method, operatorSession),operatorSession);
                    break;
                case "startWork":
                    operatorSession.setIsOffline(false);
                    operatorSession.setIsBusy(false);
                    sendMessage(getOKJson(method, operatorSession),operatorSession);
                    break;
                case "sendMessage":
                    sendBroadCastMessage(getOKJson(method).put("message",msg.getString("message")),null);
                    break;
                case "countAllSessions":
                    sendBroadCastMessage(getOKJson(method).put("message","").put("cntAll","1"),ADMINROLE);
                    break;
                case "markTicketExecuted":
                    JSONObject t = msg.getJSONObject("ticket");
                    markTicketExecuted(null,queueService,operatorSession,t.getLong("ticketId"));
                    break;
                default:
                    LOG.error("Неизвестный тип сообщение!"+method);
                    break;
            }
        } catch (Exception e) {
            LOG.error("error",e);
        }
    }

    @OnError
    public void onError(Throwable err) {
        err.printStackTrace();
        LOG.error("onError "+err);
    }
private boolean isNotNull (String o) { return o!=null && !o.trim().equals("");}

    /** При прекращении работы пользователя*/
    private void onLogout(OperatorSession aSession) {
        if (aSession.getTicket()!=null) {markTicketExecuted(aSession.getTicket(),null,aSession);}
        sessions.remove(session);
    }


    /** Изменяем номер окна для рабочей функции*/
    private void setWindowNumber(String aMethod, OperatorSession aSession, String aWindowNumber) {
        if (isNotNull(aWindowNumber)) {
            WorkFunction wf = aSession.getWorkFunction();
            wf.setWindowNumber(aWindowNumber);
            aSession.setWindow(aWindowNumber);
            persistObject(wf);
            LOG.info(wf.getId()+" window number = "+wf.getWindowNumber());
                sendMessage(getOKJson(aMethod, aSession).put("windowNumber",aWindowNumber),aSession);
        } else {
            LOG.error("Window number is null");
        }
    }

    /**Получаем талон по его ИД*/
private QueueTicket getTicketById(Long aTicketId) {
    try {
        return getInjection().getService(IQueueService.class).findTicketById(aTicketId);
    } catch (NamingException e) {
        e.printStackTrace();
        return null;
    }
}
    /**Находим свободного оператора по коду очереди */
private OperatorSession getFreeOperator (String aQueueCode) {
    OperatorSession os = null;
    try {
        os = sessions.entrySet().stream().filter(
                e-> !e.getValue().getIsOffline() && !e.getValue().getIsBusy()
                        && aQueueCode.equals(e.getValue().getQueueCode())
                        && OPERATORROLE.equals(e.getValue().getRole())
        ).findAny().get().getValue();
    } catch (Exception e) {
        LOG.warn("can't find free operator");
    }
        return os;
}

    /** При получении талона пациентов находим для него свободного оператора*/
    private void findFreeOperatorByTicket(JSONObject aTicket) {
            String qCode=aTicket.getString("queueCode");
            OperatorSession os = getFreeOperator(qCode);
            LOG.info("Find free operator = "+os);
            if (os!=null) {//Находим свободного оператора, отдаем ему талон на обработку
                QueueTicket ticket = getTicketById(aTicket.getLong("ticketId"));
                startTicketExecute(os, ticket);
                JSONObject ret = getOKJson("getNextTicket", os).put("ticket", getCurrentTicket(os,ticket));
                sendMessage(ret,os);
                showTicketOnTheScreen(ticket);
            }
    }

    /** Отмечаем: оператор начал обрабатывать талон*/
    private void startTicketExecute(OperatorSession aSession, QueueTicket aTicket) {
        if (aSession==null || aTicket==null) {
            LOG.error("cant Start Ticket Execute"+aSession +""+ aTicket);
            return;
        }
        aSession.setIsBusy(true);
        aSession.setTicket(aTicket);
        aTicket.setExecutor(aSession.getWorkFunction());
        aTicket.setStartExecuteDate(new Date());
        persistObject(aTicket);
    }

    /** Отправляем сообщение об ошибке */
    private void sendErrorMessage(String aErrorCode, String aErrorname, OperatorSession aSession) {
        try {
            if (aSession.getSession().isOpen()) {
                aSession.getSession().getBasicRemote().sendText("{\"status\":\"error\",\"errorCode\":\""+aErrorCode+"\",\"errorName\":\"aErrorName\"}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**Отправляем сообщение пользователю*/
    private void sendMessage(JSONObject aMessage,OperatorSession aSession) {
        try {
            Session operatorSession = aSession.getSession();
            if (operatorSession.isOpen()){
                operatorSession.getBasicRemote().sendText(aMessage.toString());
            }
        } catch (IOException e) {
            sessions.remove(aSession.getUsername());
            LOG.error("Error sending message ="+e);
        }
    }
    /**Собираем всю информацию о талоне
     * Либо о талоне по самому талону
     * Либо по оператору, если он обрабатывает талон
     * */
    private JSONObject getCurrentTicket(QueueTicket aTicket) {return getCurrentTicket(null,aTicket);}
    private JSONObject getCurrentTicket(OperatorSession aSession) {return getCurrentTicket(aSession,null);}
    private JSONObject getCurrentTicket(OperatorSession aSession, QueueTicket aTicket) {
        JSONObject ticketJson = new JSONObject();
        if (aSession!=null && aSession.getTicket()!=null) {
            aTicket = aSession.getTicket();
        }
            if (aTicket!=null) {
                ticketJson = new JSONObject()
                        .put("ticketId",aTicket.getId())
                        .put("ticketNumber",aTicket.getFullNumber())
                        .put("window",aSession!=null?(aSession.getWindow()!=null?aSession.getWindow():"+0"):aTicket.getWindowNumber());
            }
        return ticketJson;
    }

    /**Отмечаем талон как выполненный.
     * Указываем дату окончания, убираем талон с экрана, отмечаем оператора как свободного
     * */
    private void markTicketExecuted(QueueTicket aTicket, IQueueService aQueueService, OperatorSession aSession) {
        markTicketExecuted(aTicket,aQueueService,aSession,null);
    }
    private void markTicketExecuted(QueueTicket aTicket, IQueueService aQueueService, OperatorSession aSession, Long aTicketId) {
        try {
            if (aQueueService==null) {
                aQueueService=getInjection().getService(IQueueService.class);
            }
        } catch (Exception e ){
            e.printStackTrace();
            LOG.error(aTicket.getId()+" No QueueService");
        }
        if (aTicket==null) {
            LOG.info("ticket = neulll!!");
            aTicket=aQueueService.findTicketById(aTicketId);
        }
        if (aTicket!=null && aTicket.getFinishExecutor()==null) {
            aTicket.setFinishExecuteDate(new Date());
            aTicket.setFinishExecutor(aSession.getWorkFunction());
            aQueueService.persistTicket(aTicket);
            if(ADMINROLE.equals(aSession.getRole())) { //При пометке администратором талона как выполненный отправляем пользователю информацию о выполнении талона
                final Long tId = aTicket.getId();
                sessions.forEach((k,s)->{
                    if(s.getTicket()!=null && s.getTicket().getId()==tId) {
                        s.setTicket(null);s.setIsBusy(false);
                        sendMessage(getOKJson("getNextTicket",s).put("ticket","{}"),s);
                        LOG.info("send to session null ticket");
                    }
                });
            } else {
                aSession.setIsBusy(false);
            }
            hideTicketOnTheScreen(aTicket); //удаляем талон с экрана
        }
    }

    /** Отображаем талон на экране*/
    private void showTicketOnTheScreen(QueueTicket aTicket) {showHideTicketOnTheScreen(aTicket,false);}
    /** Убираем талон с экрана*/
    private void hideTicketOnTheScreen(QueueTicket aTicket) {showHideTicketOnTheScreen(aTicket,true);}
    /** Отображаем или прячем талон на экране*/
    private void showHideTicketOnTheScreen(QueueTicket aTicket, boolean hideTicket) {
            JSONObject ticket  =getOKJson(hideTicket ? "hideTicket" : "showTicket").put("ticket",getCurrentTicket(aTicket));
            String qCode = aTicket.getQueue().getType().getCode();
            sessions.forEach((k,v)->{
                Session s = v.getSession();
                if (s.isOpen() && TVROLE.equals(v.getRole()) && qCode.equals(v.getQueueCode())) {
                        try {
                            s.getBasicRemote().sendText(ticket.toString());
                            LOG.info("===TO TV = "+ticket.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            });

    }

    /** Отображаем все активные талоны на экране по коду очереди
     * upd: отображаем талоны только к онлайн - операторам
     * */
    private JSONArray getAllActiveTickets(String aQueueCode) {
        JSONArray tickets = new JSONArray();
        sessions.forEach((k,v)->{
            if (!v.getIsOffline()) {
                QueueTicket ticket = v.getTicket();
                if (ticket!=null && aQueueCode.equals(ticket.getQueue().getType().getCode())) {
                    tickets.put(getCurrentTicket(ticket));
                }
            }
        });
        return tickets;
    }
    /**Отправляем сообщение всем пользователям по роли*/
    private void sendBroadCastMessage(JSONObject aMessage, String aRole) {
     //   LOG.info("send broadcastmesage "+aMessage.toString());
        int activeSessions=0;
        try{
            for (Map.Entry<String, OperatorSession> e: sessions.entrySet() ) {
                OperatorSession s = e.getValue();
                Session operatorSession = s.getSession();
                if (operatorSession.isOpen()) {
                    if (!s.getIsOffline()) {activeSessions++;}
                    if (aRole==null || aRole.equals(s.getRole())){
                        operatorSession.getBasicRemote().sendText(aMessage.toString());
                    }
                } else {
                    s.setIsOffline(true);
                }
            }
            if (aMessage.has("cntAll")) {
                LOG.info("ALL active sessions = "+activeSessions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject getOKJson(String aFunction) {return getOKJson(aFunction,null) ;}
    private JSONObject getOKJson(String aFunction, OperatorSession os) {
        JSONObject ret = new JSONObject();
           ret.put("status","ok").put("function",aFunction);
            if (os!=null) {
                ret.put("isBusy",os.getIsBusy()).put("isOffline",os.getIsOffline());
                if (isNotNull(os.getWindow())) ret.put("windowNumber", os.getWindow());
            }
        return ret;
    }

    private void persistObject(Object aEntity) {
        if (aEntity!=null) {
            try {
                getInjection().getService(IQueueService.class).persistEntity(aEntity);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

}
