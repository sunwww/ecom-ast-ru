package ru.ecom.mis.ejb.service.contract;

import java.math.BigDecimal;
import java.text.ParseException;

public interface IContractService {
	Long getPriceMedService(Long aPriceList,Long aMedService) ;
	Long setSmoByAccount(Long aAccount,String aDtypeSmo, Long aIdSmo, boolean aDeleteServiceWithOtherAccount, boolean aPeresech) throws ParseException;
	void unionAccountsWithContract(Long aAccount1, Long aAccount2) ;
	Long getMedContractBySmo(String aDtypeSmo, Long aIdSmo, boolean aIsRecalc) ;
	void addMedServiceByAccount(Long aAccount,Long aPriceMedService, Integer aCount, BigDecimal aPrice, Long oldid) ;
	void accountCreation(long aMonitorId, String aDate, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber
			,boolean aIsHosp,int aIsPolyc, boolean aIsEmpty, boolean aDeleteServiceWithOtherAccount, boolean aIsPeresech) ;
}
