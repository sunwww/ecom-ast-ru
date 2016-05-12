package ru.ecom.expomc.ejb.uc.snils;

import java.util.Collection;

import javax.naming.NamingException;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

@Comment("Изменение СНИЛС у пациента")
public class ChangeSnilsChecker implements ICheck {

	public ChangeSnilsChecker() throws NamingException {
		theService = findService();
	}

	public CheckResult check(ICheckContext aContext) throws CheckException {
		CheckResult result = CheckResult.createAccepted(false);
		RegistryEntry entry = (RegistryEntry) aContext.getEntry();
		if (StringUtil.isNullOrEmpty(entry.getPatientSnils())
				|| "999-999-999 99".equals(entry.getPatientSnils())) {
			String snils = theService.findSnils(entry.getSPolis(), entry
					.getNPolis(), entry.getInsuranceCompany(), entry
					.getLastname(), entry.getFirstname(),
					entry.getMiddlename(), entry.getBirthDate());
			if(!StringUtil.isNullOrEmpty(snils)) {
				result.setAccepted(true) ;
				result.set("patientSnils", snils) ;
			}
		}
		return result;
	}

	public Collection<String> getBadProperties() {
		return BadPropertyUtil
				.create("patientSnils", "firstname", "lastname", "middlename",
						"birthDate", "nPolis", "sPolis",
						"insuranceCompany");
	}

	public static IOmcSnilsService findService() throws NamingException {
		return EjbInjection.getInstance().getLocalService(IOmcSnilsService.class) ;	
	}

	private final IOmcSnilsService theService;

}
