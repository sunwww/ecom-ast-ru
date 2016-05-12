package ru.ecom.expomc.ejb.services.externalperson;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface IExternalPersonInfoService {

	public static final String ID = "id" ;
	public static final String POLICY_SERIES = "policySeries" ;
	public static final String POLICY_NUMBER = "policyNumber" ;
	public static final String POLICY_DATA_FROM = "policyDateFrom" ; // java.sql.Date
	public static final String POLICY_DATA_TO = "policyDateTo" ; // java.sql.Date
	public static final String ORG_CODE_OLD = "orgCodeOld" ;
	public static final String ORG_CODE_NEW = "orgCodeNew" ;
	public static final String INSURANCE_COMPANY = "insuranceCompany" ;
	public static final String LASTNAME = "lastname" ;
	public static final String FIRSTNAME = "firstname" ;
	public static final String MIDDLENAME = "middlename" ;
	public static final String BIRTHDATE = "birthdate" ;
	
	/**
	 * Поиск информации о персоне по ФИОД
	 * @param aLastname   фамилия
	 * @param aFirstname  имя
	 * @param aMiddlename отчество
	 * @param aBirthdate  дата рождения
	 * @return информацию
	 */
	public Map<String,Object> findPersonInfo(String aLastname, String aFirstname
			, String aMiddlename, Date aBirthdate) ;
	
	public Map<String, Object> findPersonInfoByPolicy(String aCompanyId, String aSeries, String aNumber) ;
	
	public Collection<Map<String, Object>> findPersonInfos(String aLastname, String aFirstname, String aMiddlename) ;
	
}
