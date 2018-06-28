package ru.ecom.queue.service;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.Local;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.queue.domain.Queue;
import ru.ecom.queue.domain.QueueTicket;
import ru.ecom.queue.domain.voc.VocQueue;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@Stateless
//@Local(IQueueService.class)
@Remote(IQueueService.class)


public class QueueServiceBean implements IQueueService {
    private HashMap<Long, Integer> lastTicketNumberMap ;
    private final static Logger log = Logger.getLogger(QueueServiceBean.class);
    /**Создаем новый талон в очереди на текущий день */
    public QueueTicket getNewTicket (Long aVocQueueId) {
        Queue queue = getQueueByDate(aVocQueueId);
        int lastNumber = getFreeNumberByQueue(queue);
        QueueTicket ticket = new QueueTicket(queue,lastNumber);
        theManager.persist(ticket);
        return ticket;
    }

    /** Находим последний свободный номер в очереди при поднятии сервера хз, может, потом и переделаем..**/
    public QueueServiceBean(){
        lastTicketNumberMap= new HashMap<Long, Integer>();
    }
    /** ПОлучаем очередь по типу (и дню)*/
    private Queue getQueueByDate(Long aVocQueueId) {return getQueueByDate(aVocQueueId,null);}
    private Queue getQueueByDate(Long aVocQueueId, Date aDate){
        if (aDate==null){aDate = new java.sql.Date(System.currentTimeMillis());
        }
        List<Queue> list = theManager.createQuery("from Queue where type_id=:type and date=:date").setParameter("type",aVocQueueId).setParameter("date",aDate).getResultList();
        if (!list.isEmpty()) {
            if (list.size()>1) {
                log.warn("Найдено более одной очереди на день!");
                throw new IllegalStateException("Найдено более одной очереди на день! Type ="+aVocQueueId+", date = "+aDate);
            }
            return list.get(0);
        }
        VocQueue vocQueue = theManager.find(VocQueue.class,aVocQueueId);
        Queue queue = new Queue(vocQueue);

        theManager.persist(queue);
        log.info("Создана новая очередь с типом: "+vocQueue.getName()+"#"+vocQueue.getCode()+". ID = "+queue.getId());
        return queue;
    }

    /**Получаем свободный номер по очереди */
    private Integer getFreeNumberByQueue(Queue aQueue){
        Long key = aQueue.getId();

        Integer ret = lastTicketNumberMap.containsKey(key)?lastTicketNumberMap.get(key):1;
        log.info("key="+key+", value="+ret);
        lastTicketNumberMap.put(key,++ret);
        log.info("key="+key+", new value="+lastTicketNumberMap.get(key));
        return ret;
    }

    private String makeError(String aErrorName){
        JSONObject err = new JSONObject();
        try {
            err.put("status","error");
            err.put("errorName",aErrorName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return err.toString();
    }


    @EJB
    ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
    @Resource
    SessionContext theContext ;
}
