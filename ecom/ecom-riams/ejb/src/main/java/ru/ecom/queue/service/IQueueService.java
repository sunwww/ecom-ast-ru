package ru.ecom.queue.service;

import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.queue.domain.QueueTicket;

public interface IQueueService {
    QueueTicket findTicketById(Long aTicketId);
    QueueTicket getNewTicket (Long aVocQueueId);
    String markTicketCompleted(Long aQueueTicketId, Long aWorkfunctionId);
    String markTicketCompleted(Long aQueueTicketId, String aUsername);
    QueueTicket getFirstTicketInQueue(Long aQueueId, Long aWorkfunctionId);
    QueueTicket getFirstTicketInQueue(Long aQueueId, String aUsername);
    QueueTicket getFirstTicketInQueue(String aVocQueueCode, String aUsername);
    void persistTicket(QueueTicket aTicket);
    void persistEntity(Object aEntity);
    QueueTicket getFirstTicketInQueue(String aUsername);
    WorkFunction getWorkFunctionByUsername(String aUsername);

}
