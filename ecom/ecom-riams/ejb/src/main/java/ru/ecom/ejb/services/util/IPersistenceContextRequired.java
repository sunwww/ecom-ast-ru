package ru.ecom.ejb.services.util;

import javax.persistence.EntityManager;

/**
 *
 */
@Deprecated
public interface IPersistenceContextRequired {
    void setEntityManager(EntityManager aManager) ;
}
