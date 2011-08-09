package ru.ecom.mis.ejb.service.worker;

import ru.ecom.mis.ejb.domain.worker.Staff;

/**
 *
 */
public interface IStateListService {
    void onPersist(Staff aStateList) ;
}
