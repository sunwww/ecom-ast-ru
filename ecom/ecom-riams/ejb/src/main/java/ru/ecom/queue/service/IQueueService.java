package ru.ecom.queue.service;

import ru.ecom.queue.domain.QueueTicket;

public interface IQueueService {
    QueueTicket getNewTicket (Long aVocQueueId);
}
