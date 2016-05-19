package ru.ecom.mis.ejb.service.contract;

import java.math.BigDecimal;
import java.text.ParseException;

public interface IContractService {
	public Long setSmoByAccount(Long aAccount,String aDtypeSmo, Long aIdSmo, boolean aIsRecalc) throws ParseException;
	public void unionServiceWithContract(String aDateFrom, String aDateTo) ;
	public Long getMedContractBySmo(String aDtypeSmo, Long aIdSmo, boolean aIsRecalc) ;
	public void addMedServiceByAccount(Long aAccount,Long aPriceMedService, Integer aCount, BigDecimal aPrice, Long oldid) ;
	public void accountCreation(long aMonitorId, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber) ;
}
