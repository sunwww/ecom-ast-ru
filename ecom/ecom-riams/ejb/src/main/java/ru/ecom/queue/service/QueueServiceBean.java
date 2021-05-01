package ru.ecom.queue.service;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.queue.domain.Queue;
import ru.ecom.queue.domain.QueueTicket;
import ru.ecom.queue.domain.voc.VocQueue;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@Stateless
@Remote(IQueueService.class)
public class QueueServiceBean implements IQueueService {
    private static final HashMap<Long, Integer> lastTicketNumberMap =new HashMap<>();
    private static final Logger log = Logger.getLogger(QueueServiceBean.class);
    private static final String OKJSON =  "{\"status\":\"ok\"}";

    /**Возвращаем талон по его ИД*/
    public QueueTicket findTicketById(Long aTicketId) {return manager.find(QueueTicket.class,aTicketId);}
    /**Получаем текущую рабочую функцию по имени пользователя*/
    public WorkFunction getWorkFunctionByUsername(String aUsername) {
        List<BigInteger> list = manager.createNativeQuery("select wf.id from secuser su left join workfunction wf on wf.secuser_id=su.id where su.login=:login and wf.id is not null").setParameter("login",aUsername).getResultList();
        if (list.size() != 1) {
            log.error("Найдено "+list.size()+" рабочих функций по пользователю "+aUsername);
            return null;
        }
        return manager.find(WorkFunction.class,list.get(0).longValue());

    }

    /**Создаем новый талон в очереди на текущий день */
    public QueueTicket getNewTicket (Long aVocQueueId) {
        Queue queue = getQueueByDate(aVocQueueId);
        int lastNumber = getFreeNumberByQueue(queue);
        QueueTicket ticket = new QueueTicket(queue,lastNumber);
        manager.persist(ticket);
        return ticket;
    }

    /**Возвращаем первый номер талона в очереди по имени пользователя, помечаем его "в работе" */
    public QueueTicket getFirstTicketInQueue(String aUsername) {
        try {
            List<Object[]> list=manager.createNativeQuery("select wf.id as wfid, q.id as qid from secuser su" +
                    " left join workfunction wf on wf.secuser_id=su.id" +
                    " left join queue q on q.type_id=wf.queue_id and q.date=current_date" +
                    " where su.login=:login").setParameter("login",aUsername).getResultList();
            if (list.size() != 1) {
                log.error("Ошибка при нахождении очереди по имени пользователя. найдено "+list.size()+" записей");
                return null;
            } else {
                Object[] ret = list.get(0);
                long workfunctionId= Long.parseLong(ret[0] != null ? ret[0].toString() : "0");
                Long queueId=ret[1]!=null?Long.valueOf(ret[1].toString()):null;
                if (workfunctionId>0L) {
                    log.error("Нет соответствия между рабочей функцией и именем пользователя");
                    return null;
                }
                if (queueId==null) {
                    log.error("Не найдено очереди для этой рабочей функции "+workfunctionId);
                    return null;
                }

                return getFirstTicketInQueue(queueId,workfunctionId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Нет соответствия между рабочей функцией и именем пользователя");
        }

    }

    /**Возвращаем первый номер талона в очереди по ИД очереди и имени пользователя, помечаем его "в работе" */
    public QueueTicket getFirstTicketInQueue(Long aQueueId, String aUsername) {
        try {
        Long workfunctionId = (Long) manager.createNativeQuery("select wf.id from secuser su" +
                    " left join workfunction wf on wf.secuser_id=su.id" +
                    " where su.login=:login").setParameter("login",aUsername).getSingleResult();
        return getFirstTicketInQueue(aQueueId,workfunctionId);
        } catch (Exception e) {
            throw new IllegalStateException("Нет соответствия между рабочей функцией и именем пользователя");
        }

    }
    /**Возвращаем первый номер талона в очереди по типу очереди и текущей дате, помечаем его "в работе" */
    public QueueTicket getFirstTicketInQueue(String aVocQueueCode, String aUsername) {
        try {
            Long workfuntionId = ((BigInteger) manager.createNativeQuery("select wf.id from secuser su" +
                    " left join workfunction wf on wf.secuser_id=su.id" +
                    " where su.login=:login").setParameter("login",aUsername).getSingleResult()).longValue();

            List<BigInteger> list = manager.createNativeQuery("select q.id from queue q left join VocQueue vq on vq.id=q.type_id " +
                    " where vq.code=:code and q.date=current_date").setParameter("code",aVocQueueCode).getResultList();
            if (!list.isEmpty()) {
                Long queueId = list.get(0).longValue();
            return getFirstTicketInQueue(queueId,workfuntionId);
            } else {
                log.warn("Не найдена очередь по коду "+aVocQueueCode+" на текущий день");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Нет соответствия между рабочей функцией и именем пользователя");
        }

    }

    /** Основная функция для нахождения свободного номера талона*/
    public QueueTicket getFirstTicketInQueue(Long aQueueId, Long aWorkfunctionId) {
        if (aQueueId==null) {log.error("нет очереди на текйщий день");return null;}
        List<QueueTicket> list = manager.createQuery("from QueueTicket where queue_id=:id and startExecuteDate is null order by createDate").setParameter("id", aQueueId).getResultList();
        if (!list.isEmpty()) {
            QueueTicket ret =list.get(0);
            if (aWorkfunctionId!=null) {
                ret.setExecutor(manager.find(WorkFunction.class, aWorkfunctionId));
                ret.setStartExecuteDate(new java.util.Date());
                manager.persist(ret);
            }
            return ret;
        }
        return null;
    }

    public void persistTicket(QueueTicket aTicket) {manager.merge(aTicket);}
    public void persistEntity(Object aEntity) {manager.merge(aEntity);}

    /** Полмечаем талон как выполненный */
    public String markTicketCompleted(Long aQueueTicketId, String aUsername) {
        try {
            Long workfuntionId = (Long) manager.createNativeQuery("select wf.id from secuser su" +
                    " left join workfunction wf on wf.secuser_id=su.id" +
                    " where su.login=:login").setParameter("login",aUsername).getSingleResult();
            return markTicketCompleted(aQueueTicketId,workfuntionId);
        } catch (Exception e) {
            throw new IllegalStateException("Нет соответствия между рабочей функцией и именем пользователя");
        }
    }

    /** Помечаем талон как выполненный по ИД талона и ИД рабочей функции*/
    public String markTicketCompleted(Long aQueueTicketId, Long aWorkfunctionId) {
        QueueTicket ticket = manager.find(QueueTicket.class,aQueueTicketId) ;
        if (ticket.getFinishExecuteDate()!=null || ticket.getFinishExecutor()!=null) {
            return makeError("Талон УЖУ отмечен как выполненный","ALREADY_FINISHED");
        } else {
            log.info("Талон с ИД отмечен как исполненный");
            ticket.setFinishExecuteDate(new java.util.Date());
            ticket.setFinishExecutor(manager.find(WorkFunction.class,aWorkfunctionId));
            manager.persist(ticket);
            return OKJSON;
        }
    }

    /** ПОлучаем очередь по типу (и дню)*/
    private Queue getQueueByDate(Long aVocQueueId) {return getQueueByDate(aVocQueueId,null);}
    private Queue getQueueByDate(Long aVocQueueId, Date aDate){
        if (aDate==null){
            aDate = new java.sql.Date(System.currentTimeMillis());
        }
        List<Queue> list = manager.createQuery("from Queue where type_id=:type and date=:date").setParameter("type",aVocQueueId).setParameter("date",aDate).getResultList();
        if (!list.isEmpty()) {
            if (list.size()>1) {
                log.warn("Найдено более одной очереди на день!");
                throw new IllegalStateException("Найдено более одной очереди на день! Type ="+aVocQueueId+", date = "+aDate);
            }
            return list.get(0);
        }
        VocQueue vocQueue = manager.find(VocQueue.class,aVocQueueId);
        Queue queue = new Queue(vocQueue);
        manager.persist(queue);
        log.info("Создана новая очередь с типом: "+vocQueue.getName()+"#"+vocQueue.getCode()+". ID = "+queue.getId());
        return queue;
    }

    /**Получаем свободный номер по очереди */
    private Integer getFreeNumberByQueue(Queue aQueue){
        Long key = aQueue.getId();

        Integer ret = lastTicketNumberMap.getOrDefault(key,0);
        ret++;
        lastTicketNumberMap.put(key,ret);
        return ret;
    }

    private String makeError(String aErrorName, String aErrorCode){
        JSONObject err = new JSONObject();
        try {
            err.put("status","error");
            err.put("error_name",aErrorName);
            err.put("error_code",aErrorCode.toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return err.toString();
    }

    @PersistenceContext
    EntityManager manager ;
}