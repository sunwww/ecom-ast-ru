package ru.ecom.mis.ejb.service.pharmacy;

import javax.persistence.EntityManager;

/**
 * Created by rkurbanov on 18.09.2017.
 */
public interface IPharmOperationService {
    void setFunctionReserve(EntityManager aManager);
    void setFunctionEndPrescription();
}
