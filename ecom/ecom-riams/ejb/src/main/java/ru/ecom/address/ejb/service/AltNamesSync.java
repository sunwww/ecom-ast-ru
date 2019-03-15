package ru.ecom.address.ejb.service;

import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

import javax.persistence.EntityManager;
import java.util.List;

public class AltNamesSync implements ISync {
    private EntityManager theEntityManager;
	public void sync(SyncContext aContext) {

        String clause = " where time = "+aContext.getImportTime().getId();
        String countQueryString = "select count(*) from AltNames " + clause;

        theEntityManager = aContext.getEntityManager();
//        UserTransaction tx = aContext.getTransactionManager();
//        tx.begin();
        long results = (Long) theEntityManager.createQuery(countQueryString).getSingleResult();

        IMonitor monitor = aContext.getMonitorService().startMonitor(aContext.getMonitorId(), "Синхронизация соответствий КЛАДР", results);
        long id =0;
        int i = 0;
        int maxResults = 1000;
        String listSql = "select oldCode, newCode, id from AltNames "+clause +" and id>";
        while (true) {
        List<Object[]> names = theEntityManager.createNativeQuery(listSql+id+" order by id").setMaxResults(maxResults).getResultList();
        if (names.isEmpty()) {break;}

       for (Object[] o: names) {	
        	if (++i % 100 == 0) {
                monitor.advice(100);
              monitor.setText(i+ " Заменяем устаревший код КЛАДРа "+o[0]+" на новый код "+o[1]);
            }
        	id = Long.valueOf(""+o[2]);
        	
        	String sql = "update address2 set kladr = '"+o[1]+"' where kladr = '"+o[0]+"'";

        	theEntityManager.createNativeQuery(sql).executeUpdate();
        }
       names.clear();
        }
        monitor.finish(aContext.getImportTime().getId() + "");
        theEntityManager = null;
	}

}
