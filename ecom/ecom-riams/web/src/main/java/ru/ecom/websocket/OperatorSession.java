package ru.ecom.websocket;

import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.queue.domain.QueueTicket;

import javax.websocket.Session;

public class OperatorSession {

    /** Роль оператора */
    public String getRole() {return theRole;}
    public void setRole(String aRole) {theRole = aRole;}
    /** Роль оператора */
    private String theRole ;

    /** Тип очереди для телевизора */
    public String getQueueCode() {return theQueueCode;}
    public void setQueueCode(String aQueueCode) {theQueueCode = aQueueCode;}
    /** Тип очереди для телевизора */
    private String theQueueCode ;

    /** недоступен */
    public Boolean getIsOffline() {return theOffline;}
    public void setIsOffline(Boolean aOffline) {theOffline = aOffline;}
    /** недоступен */
    private Boolean theOffline ;

    /** Сессия */
    public Session getSession() {return theSession;}
    public void setSession(Session aSession) {theSession = aSession;}
    /** Сессия */
    private Session theSession ;

    /** Имя пользователя */
    public String getUsername() {return getSocket().getUsername();}

    /** Занят/свободен */
    public Boolean getIsBusy() {return theIsBusy;}
    public void setIsBusy(Boolean aIsBusy) {theIsBusy = aIsBusy;}
    /** Занят/свободен */
    private Boolean theIsBusy=false ;

    /** QueueWebSocket */
    public QueueWebSocket getSocket() {return theSocket;}
    public void setSocket(QueueWebSocket aSocket) {theSocket = aSocket;}
    /** QueueWebSocket */
    private QueueWebSocket theSocket ;

    /** Активный талон */
    public QueueTicket getTicket() {return theTicket;}
    public void setTicket(QueueTicket aTicket) {theTicket = aTicket;}
    /** Активный талон */
    private QueueTicket theTicket ;

    /** Рабочая функция исполнителя */
    public WorkFunction getWorkFunction() {return theWorkFunction;}
    public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}
    /** Рабочая функция исполнителя */
    private WorkFunction theWorkFunction ;

    public OperatorSession(QueueWebSocket aSocket) {setSocket(aSocket);setSession(aSocket.getSession());setIsOffline(true);setRole("");setIsBusy(true);}

    /** Номер окна */
    public String getWindow() {return theWindow;}
    public void setWindow(String aWindow) {theWindow = aWindow;}
    /** Номер окна */
    private String theWindow ;
}
