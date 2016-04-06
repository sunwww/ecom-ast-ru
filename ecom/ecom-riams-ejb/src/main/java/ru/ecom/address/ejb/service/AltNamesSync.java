package ru.ecom.address.ejb.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.address.ejb.domain.kladr.AltNames;
import ru.ecom.address.ejb.domain.kladr.Kladr;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

public class AltNamesSync implements ISync {
    private EntityManager theEntityManager;
	public void sync(SyncContext aContext) throws Exception {

    	//String clause = " where kladrCode like '30%' and time = "+aContext.getImportTime().getId();
        String clause = " where time = "+aContext.getImportTime().getId();
        String queryString = " from AltNames" + clause;
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
        	System.out.println("=== Start New "+maxResults+" cycle "+id);
        List<Object[]> names = theEntityManager.createNativeQuery(listSql+id+" order by id").setMaxResults(maxResults).getResultList();
        if (names.isEmpty()) {break;}
        System.out.println("=== Производим обновление данных КЛАДР в таблице адресов. "+names.size()+" записей");
        
       for (Object[] o: names) {	
        	if (++i % 100 == 0) {
                monitor.advice(100);
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(" ");
                sb.append(" ");
              monitor.setText(sb.toString());
            }
        	id = Long.valueOf(""+o[2]);
        	
        	System.out.println(i+" Заменяем устаревший код КЛАДРа "+o[0]+" на новый код "+o[1]);
        	String sql = "update address2 set kladr = '"+o[0]+"' where kladr = '"+o[1]+"'";
        	theEntityManager.createNativeQuery(sql).executeUpdate();
        }
       names.clear();
        }
	}

}
