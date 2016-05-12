package ru.ecom.expomc.ejb.services.externalperson;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.expomc.ejb.domain.externalbase.ExternalPersonInfo;

@Remote(IExternalPersonInfoService.class)
@Local(IExternalPersonInfoService.class)
@Stateless
public class ExternalPersonInfoServiceBean implements IExternalPersonInfoService{

	@SuppressWarnings("unchecked")
	public Map<String, Object> findPersonInfo(String aLastname, String aFirstname, String aMiddlename, Date aBirthdate) {
		List<ExternalPersonInfo> list = theManager.createNamedQuery("externalPersonInfo.findByFiod")
			.setParameter("lastname", aLastname)
			.setParameter("firstname", aFirstname)
			.setParameter("middlename", aMiddlename)
			.setParameter("birthdate", aBirthdate)
			.getResultList() ;
		Map<String,Object> ret = null ;
		if(list!=null && list.size()==1) {
			ExternalPersonInfo info = list.iterator().next();
			ret = acqure(info) ;
		} else {
			list = theManager.createNamedQuery("externalPersonInfo.findByFio")
			.setParameter("lastname", aLastname)
			.setParameter("firstname", aFirstname)
			.setParameter("middlename", aMiddlename)
			.getResultList() ;
			ret = null ;
			if(list!=null && list.size()==1) {
				ExternalPersonInfo info = list.iterator().next();
				ret = acqure(info) ;
			}
			
		}
		
		return ret ;
	}

	@SuppressWarnings("unchecked")
	public Collection<Map<String, Object>> findPersonInfos(String aLastname, String aFirstname, String aMiddlename) {
		List<ExternalPersonInfo> list = theManager.createNamedQuery("externalPersonInfo.findByFio")
			.setParameter("lastname", aLastname)
			.setParameter("firstname", aFirstname)
			.setParameter("middlename", aMiddlename)
			.getResultList() ;
		LinkedList<Map<String,Object>> ret = new LinkedList<Map<String,Object>>() ;
		for(ExternalPersonInfo i:list) {
			ret.add(acqure(i)) ;
		}
		return ret ;
	}
	
	private static  HashMap<String, Object> acqure(ExternalPersonInfo info) {
		HashMap<String, Object> ret = new HashMap<String, Object>() ;
		ret.put(ID, info.getId() ) ;
		ret.put(POLICY_SERIES, info.getPolicySeries() ) ;
		ret.put(POLICY_NUMBER, info.getPolicyNumber() ) ;
		ret.put(POLICY_DATA_FROM, info.getPolicyDateFrom() ) ;
		ret.put(POLICY_DATA_TO, info.getPolicyDateTo() ) ;
		ret.put(ORG_CODE_OLD, info.getOrgCodeOld()) ;
		ret.put(ORG_CODE_NEW, info.getOrgCodeNew()) ;
		ret.put(INSURANCE_COMPANY, info.getInsuranceCompany()) ;
		ret.put(BIRTHDATE, info.getBirthdate()) ;
		ret.put(FIRSTNAME, info.getFirstname()) ;
		ret.put(LASTNAME, info.getLastname()) ;
		ret.put(MIDDLENAME, info.getMiddlename()) ;
		return ret ;
	}
	public Map<String, Object> findPersonInfoByPolicy(String aCompanyId, String aSeries, String aNumber) {
		List<ExternalPersonInfo> list = theManager.createNamedQuery("externalPersonInfo.findByPolicy")
		.setParameter("company", aCompanyId)
		.setParameter("series", aSeries)
		.setParameter("number", aNumber)
		.getResultList() ;
		Map<String,Object> ret = null ;
		if(list!=null && list.size()==1) {
			ExternalPersonInfo info = list.iterator().next();
			ret = acqure(info) ;
		}
		return ret ;
	}
	
    private @PersistenceContext EntityManager theManager;


}
