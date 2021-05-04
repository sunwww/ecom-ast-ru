package ru.ecom.mis.ejb.service.lpu;

import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcLpu;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;

import javax.persistence.EntityManager;
import java.util.Iterator;

public class LpuSync implements ISync {


    public void sync(SyncContext aContext) throws Exception {
    	
        String clause = " where time = "+aContext.getImportTime().getId();
        String queryString = " from OmcLpu" + clause;
        String countQueryString = "select count(*) from OmcLpu " + clause;
        entityManager = aContext.getEntityManager();
        long results = (Long) entityManager.createQuery(countQueryString).getSingleResult();

        IMonitor monitor = aContext.getMonitorService().startMonitor(aContext.getMonitorId(), "Синхронизация ", results);
    	
    //	List<OmcLpu> omcLpus = entityManager.createQuery("from OmcLpu "+clause).getResultList() ;
    	int i =0;
    	
        Iterator<OmcLpu> iterator = QueryIteratorUtil.iterate(OmcLpu.class, entityManager.createQuery(queryString));
        while (iterator.hasNext()) {
        	
            OmcLpu omcLpu = iterator.next();
            String code = omcLpu.getCode();
            if (i % 10 == 0 && monitor.isCancelled()) break;
            MisLpu lpu = QueryResultUtil.getFirst(MisLpu.class, entityManager.createQuery("from MisLpu where omcCode= :code and parent_id is null")
    				.setParameter("code", code));
            if (lpu == null) {
                lpu = new MisLpu();
                lpu.setOmcCode(code);
                
            }
            int lenMax= omcLpu.getName().length() ;
            if (lenMax>254) lenMax=254 ;
            lpu.setName(omcLpu.getName().substring(0,lenMax)) ;
			lpu.setOmcCode(omcLpu.getCode()) ;
			lpu.setEmail(omcLpu.getMail()) ;
			lpu.setDirector(omcLpu.getDirector());
			lpu.setPhone(omcLpu.getPhone());
			lpu.setCodef(omcLpu.getCodef()) ;
			
            entityManager.persist(lpu);

            if (++i % 30 == 0) {
                monitor.advice(30);

                String sb = i +
                        " " + omcLpu.getName() +
                        " " + omcLpu.getCode() ;
                monitor.setText(sb);
//                tx.commit();
//                tx.begin();
            }
            entityManager.flush();
            entityManager.clear();
        }

        monitor.finish(aContext.getImportTime().getId() + "");
        entityManager = null;

    }
    
    private EntityManager entityManager;
}