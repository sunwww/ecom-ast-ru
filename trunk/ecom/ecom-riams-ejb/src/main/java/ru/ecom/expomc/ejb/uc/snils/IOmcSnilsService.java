package ru.ecom.expomc.ejb.uc.snils;

import java.util.Collection;
import java.util.Date;

public interface IOmcSnilsService {

	public String findSnils(String aPolicySeries, String aPolicyNumber
			, String aInsuranceCompany
			, String aLastname1, String aFirstname1, String aMiddlename1
			, Date aBirthdate) ;
	
	public String findSnils(String aPolicySeries, String aPolicyNumber
			, String aInsuranceCompany) ;

	public Collection<OmcSnils> findSnils(String aLastname1, String aFirstname1, String aMiddlename1
			, Date aBirthdate) ;

	public Collection<OmcSnils> findSnilsByFio(String aLastname1, String aFirstname1, String aMiddlename1) ;
	
}
