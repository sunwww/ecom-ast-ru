package ru.ecom.mis.ejb.service.sync.vocomc_sprsmo;

import java.sql.Date;
import java.util.Iterator;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcSprSmo;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;

/**
 * 
 * @author VTsybulin 16.12.2014
 * синхронизация импортированного oms_sprsmo c RegInsuranseCompany
 *
 */
@Stateless
@Local(ISyncVocSprSmoService.class)
public class SyncVocSprSmoServiceBean implements ISyncVocSprSmoService {
	

	private @PersistenceContext EntityManager theManager;
	private @EJB ISyncLpuFondService theSyncService ;
	private @EJB ILocalMonitorService theMonitorService;
	IMonitor monitor = null; 
	private final static Logger LOG = Logger.getLogger(SyncVocSprSmoServiceBean.class) ;
	public void sync(long aMonitorId, long aTimeId) {

		RegInsuranceCompany regInsCompany;
		OmcSprSmo osSmo;
		//	Date current_date=new Date(new java.util.Date().getTime()) ;
			monitor = theMonitorService.startMonitor(aMonitorId, "Синхронизация с RegInsuranceCompany", getCount(aTimeId));
			try{
			Query query = theManager.createQuery("from OmcSprSmo oss where time = :time").setParameter("time", aTimeId);
			Iterator<OmcSprSmo> oss = QueryIteratorUtil.iterate(OmcSprSmo.class, query);
			int i =0;
			while (oss.hasNext()) {
				i++;
				if (monitor.isCancelled()) {
                    throw new IllegalMonitorStateException("Прервано пользователем");
                }
				osSmo =  oss.next();
				regInsCompany = (RegInsuranceCompany) theManager.createQuery("from RegInsuranceCompany rig where smocode = :smocode").setParameter("smocode", osSmo.getCode()).getSingleResult();
				if (regInsCompany !=null){ 
					// обновляем поля: ogrn 
					//TODO: где взять краткое наименование?
					if (regInsCompany.getOgrn().equals(osSmo.getCode())) {
						LOG.info(i+" Синхронизирован без обновления объект: \tid="+regInsCompany.getId());
						continue;
					} else { 
						regInsCompany.setOgrn(osSmo.getCode());
						LOG.info(i+" Обновлен объект: \tid="+regInsCompany.getId());
					}
					
				} else {
					regInsCompany = new RegInsuranceCompany();
					regInsCompany.setName(osSmo.getName());
					regInsCompany.setSmocode(osSmo.getCode());
					regInsCompany.setOgrn(osSmo.getCode());
					LOG.info(i+" Добавлен объект объект: \tid="+regInsCompany.getId());
				}
			}
			monitor.finish(""+aTimeId);
		} catch (Exception e) {
			
			monitor.error("Ошибка при синхронизации СМО: ", e);
			monitor.finish(""+aTimeId);
			throw new IllegalMonitorStateException("Ошибка: "+e);
		}
	}
	 private Long getCount(long aTimeId) {
	    	return (Long) theManager.createQuery("select count(*) from OmcSprSmo where time = :time")
	    			.setParameter("time", aTimeId).getSingleResult() ;
	    }
	
}
