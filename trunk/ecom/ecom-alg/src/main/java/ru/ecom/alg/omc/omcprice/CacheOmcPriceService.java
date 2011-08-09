package ru.ecom.alg.omc.omcprice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class CacheOmcPriceService implements IOmcPriceService{

	public CacheOmcPriceService(IOmcPriceService aOmcPriceService) {
		theOmcPriceService = aOmcPriceService ;
	}
	
	public BigDecimal findAverageDaysByDailyHospital(Date aDischargeDate, String aDepartmentCode, int aDepartmentLevel, boolean aIsChild) {
		StringBuilder sb = new StringBuilder() ;
		sb.append(aDischargeDate).append('\0') ;
		sb.append(aDepartmentCode).append('\0') ;
		sb.append(aDepartmentLevel).append('\0') ;
		sb.append(aIsChild) ;
		String key = sb.toString() ;
		if(theDaily.containsKey(key)) {
			return theDaily.get(key) ;
		} else {
			BigDecimal value = theOmcPriceService.findAverageDaysByDailyHospital(aDischargeDate, aDepartmentCode, aDepartmentLevel, aIsChild) ;
			theDaily.put(key, value) ;
			return value ;
		}
	}


	public BigDecimal findKsgAverageDays(Date aDischargeDate, String aMkb, boolean aIsChild) {
		StringBuilder sb = new StringBuilder() ;
		sb.append(aDischargeDate).append('\0') ;
		sb.append(aMkb).append('\0') ;
		sb.append(aIsChild) ;
		String key = sb.toString() ;
		if(theKsgDays.containsKey(key)) {
			return theKsgDays.get(key) ;
		} else {
			BigDecimal value = theOmcPriceService.findKsgAverageDays(aDischargeDate, aMkb, aIsChild) ;
			theKsgDays.put(key, value) ;
			return value ;
		}
	}

	public Integer findKsgLevel(Date aDischargeDate, String aMkb, boolean aIsChild) {
		StringBuilder sb = new StringBuilder() ;
		sb.append(aDischargeDate).append('\0') ;
		sb.append(aMkb).append('\0') ;
		sb.append(aIsChild) ;
		String key = sb.toString() ;
		if(theKsgLevel.containsKey(key)) {
			return theKsgLevel.get(key) ;
		} else {
			Integer value = theOmcPriceService.findKsgLevel(aDischargeDate, aMkb, aIsChild) ;
			theKsgLevel.put(key, value) ;
			return value ;
		}
	}

	public BigDecimal findTariffByUsl(Date aActualDate, String aUslOmcCode, int aLevel, boolean aIsChild) {
		StringBuilder sb = new StringBuilder() ;
		sb.append(aActualDate).append('\0') ;
		sb.append(aUslOmcCode).append('\0') ;
		sb.append(aLevel).append('\0') ;
		sb.append(aIsChild) ;
		String key = sb.toString() ;
		if(theTariff.containsKey(key)) {
			return theTariff.get(key) ;
		} else {
			BigDecimal value = theOmcPriceService.findTariffByUsl(aActualDate, aUslOmcCode, aLevel, aIsChild) ;
			theTariff.put(key, value) ;
			return value ;
		}
	}

	public BigDecimal findAverageDaysByHospital(Date aDischargeDate, String aDepartmentCode, boolean aIsChild) {
		StringBuilder sb = new StringBuilder() ;
		sb.append(aDischargeDate).append('\0') ;
		sb.append(aDepartmentCode).append('\0') ;
		sb.append(aIsChild) ;
		String key = sb.toString() ;
		if(the24Hour.containsKey(key)) {
			return the24Hour.get(key) ;
		} else {
			BigDecimal value = theOmcPriceService.findAverageDaysByHospital(aDischargeDate, aDepartmentCode, aIsChild) ;
			the24Hour.put(key, value) ;
			return value ;
		}
	}
	public Integer findDepartmentLevel(Date aDischargeDate, int aKodLpu, String aOmcDepType) {
		return 1;
		}
	
	
	private final HashMap<String, BigDecimal> theDaily = new HashMap<String, BigDecimal>() ;  
	private final HashMap<String, BigDecimal> theKsgDays = new HashMap<String, BigDecimal>() ;  
	private final HashMap<String, Integer> theKsgLevel = new HashMap<String, Integer>() ;  
	private final HashMap<String, BigDecimal> theTariff = new HashMap<String, BigDecimal>() ;  
	private final HashMap<String, BigDecimal> the24Hour = new HashMap<String, BigDecimal>() ;  
	private final IOmcPriceService theOmcPriceService ;
}
