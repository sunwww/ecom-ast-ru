package ru.ecom.mis.ejb.service.contract;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;

import java.math.BigDecimal;

@Stateless
@Remote(IContractService.class)
public class ContractServiceBean implements IContractService {
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
			//sql.append("insert into contractAccountMedservice (account_id, medService_id, cost, countmedservice) ");
			//sql.append("values ('");
			//sql.append(aAccount);sql.append("', '");
			//sql.append(aPriceMedService);sql.append("', '");
			//sql.append(aPrice);sql.append("', '");
			//sql.append(aCount);sql.append("')");
		}
		//theManager.createNativeQuery(sql.toString());
	}
	
	@PersistenceContext EntityManager theManager ;

}