package ru.ecom.expomc.ejb.uc.snils;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.nuzmsh.util.StringUtil;

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
	
//		System.out.println("np="+aPolicyNumber
//				+", sp="+aPolicySeries
//				+", sk="+aInsuranceCompany
//				+", f1="+lastname1
//				+", i1="+firstname1
//				+", o1="+middlename1
//				+", y="+birthYear);
		Query query = theManager.createNamedQuery("OmcSnils.findSnilsByPolicyAndPerson")
		 .setParameter("policyNumber", aPolicyNumber)
		 .setParameter("policySeries", aPolicySeries)
		 .setParameter("insuranceCompany", aInsuranceCompany)
		 .setParameter("lastname1", lastname1)
		 .setParameter("firstname1", firstname1)
		 .setParameter("middlename1", middlename1)
		 .setParameter("birthYear", birthYear) ;
		//System.out.println(query);
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
		//System.out.println("birthDate="+aBirthdate);
		//System.out.println("birthYear="+birthYear);
		List<OmcSnils> ret = new LinkedList<OmcSnils>() ;
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
		List<OmcSnils> ret = new LinkedList<OmcSnils>() ;
		List<OmcSnils> finded  = theManager.createNamedQuery("OmcSnils.findSnilsByPatientWithoutYear")
		.setParameter("lastname1", lastname1)
		.setParameter("firstname1", firstname1)
		 .setParameter("middlename1", middlename1)
		 .getResultList() ;
		add(finded, ret) ;
		return ret;
	}
	
	private static void add(List<OmcSnils> aSource, List<OmcSnils> aDest) {
		for(OmcSnils s:aSource) {
			aDest.add(s) ;
		}
	}

	private @PersistenceContext EntityManager theManager ;

	
}
