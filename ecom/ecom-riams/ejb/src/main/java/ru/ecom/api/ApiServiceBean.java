package ru.ecom.api;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** Created by rkurbanov on 16.05.2018. */
@Stateless
@Local(IApiService.class)
@Remote(IApiService.class)
public class ApiServiceBean implements IApiService{

    public void persistEntity(Object object){
        persistEntityObj(object);
    }

    public Object persistEntityObj(Object object){
        if (object!=null && manager!=null) {
            manager.persist(object);
        }
        return object;
    }

    private @PersistenceContext EntityManager manager;
    @Resource SessionContext context ;
}
