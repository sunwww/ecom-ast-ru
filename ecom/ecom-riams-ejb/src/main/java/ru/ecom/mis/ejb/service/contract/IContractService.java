package ru.ecom.mis.ejb.service.contract;

import java.math.BigDecimal;

public interface IContractService {
	public void addMedServiceByAccount(Long aAccount,Long aPriceMedService, Integer aCount, BigDecimal aPrice, Long oldid) ;
	public void accountCreation(long aMonitorId, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber) ;
}
