package ru.ecom.mis.ejb.service.worker;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.mis.ejb.domain.worker.Staff;

/**
 *
 */
@Stateless
@Local(IStateListService.class)
@Remote(IStateListService.class)
public class StateListServiceBean implements IStateListService{
    public void onPersist(Staff aStateList) {
       theManager.createNativeQuery("update StateList set fullRate=budjetRate+offBudjetRate where id = "+aStateList.getId())
            //.setParameter("id", aStateList.getId())
            .executeUpdate() ;
       /*  theManager.createNativeQuery("update StateList set fullRate=budjetRate+offBudjetRate")
            .executeUpdate() ;*/
    }

    @PersistenceContext private EntityManager theManager ;
}
