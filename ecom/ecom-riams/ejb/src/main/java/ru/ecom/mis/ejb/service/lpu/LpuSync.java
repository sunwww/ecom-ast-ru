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
        theEntityManager = aContext.getEntityManager();
        long results = (Long) theEntityManager.createQuery(countQueryString).getSingleResult();

        IMonitor monitor = aContext.getMonitorService().startMonitor(aContext.getMonitorId(), "Синхронизация ", results);
    	
    //	List<OmcLpu> omcLpus = theEntityManager.createQuery("from OmcLpu "+clause).getResultList() ;
    	int i =0;
    	
        Iterator<OmcLpu> iterator = QueryIteratorUtil.iterate(OmcLpu.class, theEntityManager.createQuery(queryString));
        while (iterator.hasNext()) {
        	
            OmcLpu omcLpu = iterator.next();
            String code = omcLpu.getCode();
            if (i % 10 == 0 && monitor.isCancelled()) break;
            MisLpu lpu = QueryResultUtil.getFirst(MisLpu.class, theEntityManager.createQuery("from MisLpu where omcCode= :code and parent_id is null")
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
			
			//lpu.setOgrn(Long.valueOf(omcLpu.getOgrn())) ;
			try {
    			//lpu.setInn(Long.parseLong(omcLpu.getInn())) ;
    			//lpu.setOgrn(Long.valueOf(omcLpu.getOgrn())) ;
			} catch (Exception e) {
			}
            theEntityManager.persist(lpu);

            if (++i % 30 == 0) {
                monitor.advice(30);
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(" ");
                sb.append(omcLpu != null ? omcLpu.getName() : "");
                sb.append(" ");
                sb.append(omcLpu != null ? omcLpu.getCode() : "");
                
                monitor.setText(sb.toString());
//                tx.commit();
//                tx.begin();
            }
            theEntityManager.flush();
            theEntityManager.clear();
        }

        monitor.finish(aContext.getImportTime().getId() + "");
        theEntityManager = null;

    }
    
    private EntityManager theEntityManager;
}