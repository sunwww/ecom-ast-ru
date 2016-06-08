package ru.ecom.mis.ejb.service.contract;

import java.math.BigDecimal;
import java.text.ParseException;

public interface IContractService {
	public Long getPriceMedService(Long aPriceList,Long aMedService) ;
	public Long setSmoByAccount(Long aAccount,String aDtypeSmo, Long aIdSmo, boolean aDeleteServiceWithOtherAccount, boolean aPeresech) throws ParseException;
	public void unionAccountsWithContract(Long aAccount1, Long aAccount2) ;
	public Long getMedContractBySmo(String aDtypeSmo, Long aIdSmo, boolean aIsRecalc) ;
	public void addMedServiceByAccount(Long aAccount,Long aPriceMedService, Integer aCount, BigDecimal aPrice, Long oldid) ;
	public void accountCreation(long aMonitorId, String aDate, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber
			,boolean aIsHosp,int aIsPolyc, boolean aIsEmpty, boolean aDeleteServiceWithOtherAccount, boolean aIsPeresech) ;
}
