package ru.ecom.expomc.ejb.uc.snils;

import java.util.Collection;

import javax.naming.NamingException;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.ecom.expomc.ejb.services.check.checkers.registry.ChangeFromExternalPatientInfo;
import ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

@Comment("Проверка на существование полиса во внешних базах")
public class CheckCompanyPolicySeriesAndNumber implements ICheck {

	public CheckCompanyPolicySeriesAndNumber() throws NamingException {
		theSnilsService = ChangeSnilsChecker.findService() ;
		theExternalService = ChangeFromExternalPatientInfo.getInfoService() ;

	}
	public CheckResult check(ICheckContext aContext) throws CheckException {
		CheckResult result = CheckResult.createAccepted(false);
		RegistryEntry entry = (RegistryEntry) aContext.getEntry();
		if(!StringUtil.isNullOrEmpty(entry.getSPolis()) 
				|| !StringUtil.isNullOrEmpty(entry.getNPolis())
				|| !StringUtil.isNullOrEmpty(entry.getInsuranceCompany()) ){
			
			String snils = theSnilsService.findSnils(entry.getSPolis(), entry.getNPolis(), entry.getInsuranceCompany()) ;
			if(snils==null) {
				Object obj = theExternalService.findPersonInfoByPolicy(entry.getInsuranceCompany(), entry.getSPolis(), entry.getNPolis()) ;
				if(obj==null) {
					result.setAccepted(true) ;
				}
			}
		}
		return result ;
	}

	public Collection<String> getBadProperties() {
		return BadPropertyUtil.create("insuranceCompany"
				, "fio","birthDate", "patientSnils","policySeriesNumber");
	}
	

	private final IOmcSnilsService theSnilsService;
	private final IExternalPersonInfoService theExternalService;

	
}
