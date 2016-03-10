package ru.ecom.mis.ejb.service.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.service.PolicyForm;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;
import ru.ecom.mis.ejb.domain.contract.PricePosition;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.nuzmsh.util.format.DateFormat;

import java.math.BigDecimal;

@Stateless
@Remote(IContractService.class)
public class ContractServiceBean implements IContractService {
	
	public void accountCreation(long aMonitorId, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber) {
		IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
		try {
			MedContract contract = theManager.find(MedContract.class, aContract) ;
			ContractAccount ac = new ContractAccount() ;
			ac.setContract(contract) ;
			ac.setDateFrom(DateFormat.parseSqlDate(aDateFrom)) ;
			ac.setDateTo(DateFormat.parseSqlDate(aDateTo)) ;
			ac.setAccountNumber(aAccountNumber) ;
			theManager.persist(ac) ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select mc.id as mcid,cp.id as cpid ,vss.id as vssid, rc.id as rcid,lpu.id as lpuid,mc.priceList_id as pricelist")
				.append(" from medcontract mc")
				.append(" left join contractperson cp on cp.id=mc.customer_id")
				.append(" left join VocServiceStream vss on vss.id=mc.serviceStream_id")
				.append(" left join Reg_ic rc on rc.id=cp.regcompany_id")
				.append(" left join MisLpu lpu on lpu.id=cp.lpu_id") 
				.append(" where mc.id=").append(aContract);
			List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (!list.isEmpty()) {
				Object[] par=list.get(0) ;
				sql = new StringBuilder() ;
				sql.append("select mc.id as mcid,mp.id as mpid,mc.patient_id from medcase mc")
					.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id")
					.append(" left join medpolicy mp on mp.id=mcmp.policies_id")
					.append(" where mc.dtype='HospitalMedCase' and mc.dateFinish between to_date('")
					.append(aDateFrom).append("','dd.mm.yyyy')")
					.append(" and to_date('")
					.append(aDateTo).append("','dd.mm.yyyy')") ;
				if (par[2]!=null) sql.append(" and mc.serviceStream_id=").append(par[2]) ;
				if (par[3]!=null) sql.append(" and mp.company_id=").append(par[3]) ;
				if (par[4]!=null) sql.append(" and mc.orderLpu_id=").append(par[3]) ;
				sql.append(" group by mc.id,mp.id,mc.patient_id") ;
				List<Object[]> listHosp = theManager.createNativeQuery(sql.toString()).getResultList() ;
				monitor = theMonitorService.startMonitor(aMonitorId, "Формирование счета по стационару. Найдено госпитализаций: "+listHosp.size(),listHosp.size()) ;
				
				for (Object[] hosp:listHosp) {
					// Койки
					sql = new StringBuilder() ;
					sql.append("select slo.id,max(pms.id) as ppcost")
						.append(" ,coalesce(slo.datefinish,slo.transferdate),slo.datestart")
						.append(" from medcase slo")
						.append(" left join medcase sls on sls.id=slo.parent_id")
						.append(" left join Vochosptype vht on vht.id=sls.hosptype_id")
						.append(" left join statisticstub ss on ss.id=sls.statisticStub_id")
						.append(" left join bedfund bf on bf.id=slo.bedfund_id")
						.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id")
						.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id")
						.append(" left join workPlace wp on wp.id=slo.roomNumber_id")
						.append(" left join Patient pat on pat.id=slo.patient_id")
						.append(" left join VocRoomType vrt on vrt.id=wp.roomType_id")
						.append(" left join mislpu ml on ml.id=slo.department_id")
						.append(" left join workfunctionservice wfs on wfs.lpu_id=slo.department_id")
						.append("     and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id")
						.append("     and wfs.roomType_id=wp.roomType_id")
						.append(" left join medservice ms on ms.id=wfs.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=wfs.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id")
						.append(" and (pp.isvat is null or pp.isvat='0')")
						.append(" where slo.parent_id='").append(hosp[0]).append("'")
						.append("  and ms.servicetype_id='").append(11).append("' and pp.priceList_id='").append(par[5]).append("'")
						.append("  group by slo.id,ml.name,vbt.name,vbst.name,vrt.name,slo.datefinish,slo.transferdate,slo.datestart,vht.code") ;
					List<Object[]> bedL= theManager.createNativeQuery(sql.toString()).getResultList() ; 
					for (Object[] bed:bedL) {
						//System.out.println("bed="+bed[0]+" pp="+bed[1]) ;
						ContractAccountMedService cams = new ContractAccountMedService() ;
						cams.setTypeService("BED") ;
						cams.setAccount(ac) ;
						PriceMedService pms = theManager.find(PriceMedService.class, ConvertSql.parseLong(bed[1])) ;
						cams.setMedService(pms) ;
						cams.setCost(pms.getPricePosition().getCost()) ;
						cams.setCountMedService(1) ;
						//cams.setDateFrom() ;
						//cams.setDateTo() ;
						ContractGuarantee sp = theManager.find(ContractGuarantee.class, hosp[3]);
						cams.setGuarantee(sp) ;
						//cams.setLastname() ;
						//cams.setFirstname() ;
						//cams.setMiddlename() ;
						//cams.setBirthday() ;
						cams.setSmo(ConvertSql.parseLong(bed[0])) ;
						theManager.persist(cams) ;
					}
				}
			}
			monitor.finish("Обработка завершена") ;
		} catch (Exception e) {
			monitor.error(e.getMessage(), e) ;
		}
	}
	public void addMedServiceByAccount(Long aAccount,Long aPriceMedService, Integer aCount, BigDecimal aPrice, Long oldid) {
		StringBuilder sql = new StringBuilder() ;
		ContractAccount account = theManager.find(ContractAccount.class, aAccount) ;
		PriceMedService service = theManager.find(PriceMedService.class, aPriceMedService);
		sql.append("from ContractAccountMedService where id=:CAMS");
		
		List<ContractAccountMedService> list = theManager.createQuery(sql.toString())
				.setParameter("CAMS", oldid)
				.getResultList() ;
		if (list.size()>0) {
			
			ContractAccountMedService ms = list.get(0) ;
			ms.setCountMedService(aCount) ;
			ms.setCost(aPrice) ;
			theManager.persist(ms) ;
		} else {
			ContractAccountMedService ms = new ContractAccountMedService() ;
			ms.setCountMedService(aCount) ;
			ms.setCost(aPrice) ;
			ms.setAccount(account) ;
			ms.setMedService(service) ;
			theManager.persist(ms) ;
			
		}
		
	}
	
	@PersistenceContext EntityManager theManager ;
	 private @EJB ILocalMonitorService theMonitorService ;
}