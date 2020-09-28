package ru.ecom.mis.ejb.service.contract;

import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface IContractService {
	Long getPriceMedService(Long aPriceList,Long aMedService) ;
	Long setSmoByAccount(Long aAccount,String aDtypeSmo, Long aIdSmo, boolean aDeleteServiceWithOtherAccount, boolean aPeresech) throws ParseException;
	void unionAccountsWithContract(Long aAccount1, Long aAccount2) ;
	Long getMedContractBySmo(String aDtypeSmo, Long aIdSmo, boolean aIsRecalc) ;
	void addMedServiceByAccount(Long aAccount,Long aPriceMedService, Integer aCount, BigDecimal aPrice, Long oldid) ;
	void accountCreation(long aMonitorId, String aDate, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber
			,boolean aIsHosp,int aIsPolyc, boolean aIsEmpty, boolean aDeleteServiceWithOtherAccount, boolean aIsPeresech) ;
	List getPaidMedServicesByPatient(Long aPatientId, String aMedserviceCode, Long aMedCaseId
			, String aServiceType, Long aServiceId, String aMedServiceType);
	Boolean isMedServicePayedByPatient(Long aPatientId, String aMedserviceCode, Long aMedCaseId
			, String aServiceType, Long aServiceId);
	void addMedServiceAccount(String typeService, Long idService, String medServiceCode, Long patientId, ContractGuarantee letter);
	void addMedServiceAccount(String typeService, Long idService, String medServiceCode, Long patientId, Long letter);
}
