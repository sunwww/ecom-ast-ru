package ru.ecom.expomc.ejb.uc.snils;

import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.nuzmsh.util.StringUtil;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Stateless
@Local(IOmcSnilsService.class)
@Remote(IOmcSnilsService.class)
public class OmcSnilsServiceBean implements IOmcSnilsService {

	/**
	 * Поиск СНИЛС
	 */
	public String findSnils(String aPolicySeries, String aPolicyNumber
			, String aInsuranceCompany) {
		Query query = theManager.createNamedQuery("OmcSnils.findSnilsByPolicy")
		 .setParameter("policyNumber", aPolicyNumber)
		 .setParameter("policySeries", aPolicySeries)
		 .setParameter("insuranceCompany", aInsuranceCompany) ;

		OmcSnils snils = QueryResultUtil.getFirst(OmcSnils.class, query) ;
		return snils!=null ? snils.getSnils() : null ;
	}
	
	
	public String findSnils(String aPolicySeries, String aPolicyNumber
			, String aInsuranceCompany
			, String aLastname1, String aFirstname1, String aMiddlename1
			, Date aBirthdate) {
		String lastname1 = getFirstLetter(aLastname1) ;
		String firstname1 = getFirstLetter(aFirstname1) ;
		String middlename1 = getFirstLetter(aMiddlename1) ;
		int birthYear = getYear(aBirthdate) ;
	
		Query query = theManager.createNamedQuery("OmcSnils.findSnilsByPolicyAndPerson")
		 .setParameter("policyNumber", aPolicyNumber)
		 .setParameter("policySeries", aPolicySeries)
		 .setParameter("insuranceCompany", aInsuranceCompany)
		 .setParameter("lastname1", lastname1)
		 .setParameter("firstname1", firstname1)
		 .setParameter("middlename1", middlename1)
		 .setParameter("birthYear", birthYear) ;
		OmcSnils snils = QueryResultUtil.getSingleResult(OmcSnils.class, query) ;
		return snils!=null ? snils.getSnils() : null ;
	}
	
	private static String getFirstLetter(String aStr) {
		if(StringUtil.isNullOrEmpty(aStr) || aStr.length()<1) {
			return "" ;
		} else {
			return String.valueOf(aStr.charAt(0)) ;
		}
	}
	
	private int getYear(Date aDate) {
		if(aDate==null) {
			return 0 ;
		} else {
			Calendar cal = Calendar.getInstance() ;
			cal.setTime(aDate) ;
			return cal.get(Calendar.YEAR) ;
		}
	}

    

	@SuppressWarnings("unchecked")
	public Collection<OmcSnils> findSnils(String aLastname1, String aFirstname1, String aMiddlename1, Date aBirthdate) {
		String lastname1 = getFirstLetter(aLastname1) ;
		String firstname1 = getFirstLetter(aFirstname1) ;
		String middlename1 = getFirstLetter(aMiddlename1) ;
		int birthYear = getYear(aBirthdate) ;
		List<OmcSnils> ret = new LinkedList<>() ;
		List<OmcSnils> finded  = theManager.createNamedQuery("OmcSnils.findSnilsByPatient")
		.setParameter("lastname1", lastname1)
		.setParameter("firstname1", firstname1)
		 .setParameter("middlename1", middlename1)
		 .setParameter("birthYear", birthYear)
		 .getResultList() ;
		add(finded, ret) ;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public Collection<OmcSnils> findSnilsByFio(String aLastname1, String aFirstname1, String aMiddlename1) {
		String lastname1 = getFirstLetter(aLastname1) ;
		String firstname1 = getFirstLetter(aFirstname1) ;
		String middlename1 = getFirstLetter(aMiddlename1) ;
		List<OmcSnils> ret = new LinkedList<>() ;
		List<OmcSnils> finded  = theManager.createNamedQuery("OmcSnils.findSnilsByPatientWithoutYear")
		.setParameter("lastname1", lastname1)
		.setParameter("firstname1", firstname1)
		 .setParameter("middlename1", middlename1)
		 .getResultList() ;
		add(finded, ret) ;
		return ret;
	}
	
	private static void add(List<OmcSnils> aSource, List<OmcSnils> aDest) {
		aDest.addAll(aSource);
	}

	private @PersistenceContext EntityManager theManager ;

	
}
