package ru.ecom.expomc.ejb.services.externalperson;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface IExternalPersonInfoService {

	String ID = "id" ;
	String POLICY_SERIES = "policySeries" ;
	String POLICY_NUMBER = "policyNumber" ;
	String POLICY_DATA_FROM = "policyDateFrom" ; // java.sql.Date
	String POLICY_DATA_TO = "policyDateTo" ; // java.sql.Date
	String ORG_CODE_OLD = "orgCodeOld" ;
	String ORG_CODE_NEW = "orgCodeNew" ;
	String INSURANCE_COMPANY = "insuranceCompany" ;
	String LASTNAME = "lastname" ;
	String FIRSTNAME = "firstname" ;
	String MIDDLENAME = "middlename" ;
	String BIRTHDATE = "birthdate" ;
	
	/**
	 * Поиск информации о персоне по ФИОД
	 * @param aLastname   фамилия
	 * @param aFirstname  имя
	 * @param aMiddlename отчество
	 * @param aBirthdate  дата рождения
	 * @return информацию
	 */
	Map<String,Object> findPersonInfo(String aLastname, String aFirstname
			, String aMiddlename, Date aBirthdate) ;
	
	Map<String, Object> findPersonInfoByPolicy(String aCompanyId, String aSeries, String aNumber) ;
	
	Collection<Map<String, Object>> findPersonInfos(String aLastname, String aFirstname, String aMiddlename) ;
	
}
