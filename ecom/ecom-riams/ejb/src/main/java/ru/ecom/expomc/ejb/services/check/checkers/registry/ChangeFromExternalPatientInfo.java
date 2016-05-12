package ru.ecom.expomc.ejb.services.check.checkers.registry;

import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.INSURANCE_COMPANY;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.ORG_CODE_NEW;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.ORG_CODE_OLD;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.POLICY_NUMBER;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.POLICY_SERIES;

import java.util.Collection;
import java.util.Map;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

@Comment("Изменение информации о пациенте из внешних источников по ФИО и дате рождению")
public class ChangeFromExternalPatientInfo implements ICheck {

	public CheckResult check(ICheckContext aContext) throws CheckException {
		RegistryEntry entry = (RegistryEntry) aContext.getEntry();
		Map<String, Object> map = getInfoService().findPersonInfo(
				entry.getLastname(), entry.getFirstname(),
				entry.getMiddlename(), entry.getBirthDate());

		if (map != null) {
			// String number = (String) map.get(POLICY_NUMBER) ;
			// String series = (String) map.get(POLICY_SERIES) ;

			if (StringUtil.isNullOrEmpty(entry.getSPolis())
					|| StringUtil.isNullOrEmpty(entry.getNPolis())
					|| StringUtil.isNullOrEmpty(entry.getWorksCode())
					|| StringUtil.isNullOrEmpty(entry.getWorksOldCode())) {

				CheckResult result = CheckResult.createAccepted(true);
				if (StringUtil.isNullOrEmpty(entry.getSPolis())) {
					result.set("sPolis", (String) map.get(POLICY_SERIES));
				}
				if (StringUtil.isNullOrEmpty(entry.getNPolis())) {
					result.set("nPolis", (String) map.get(POLICY_NUMBER));
				}
				if (StringUtil.isNullOrEmpty(entry.getWorksOldCode())) {
					String code = (String) map.get(ORG_CODE_OLD);
					if (!StringUtil.isNullOrEmpty(code)) {
						result.set("worksOldCode", code);
					}
				}
				if (StringUtil.isNullOrEmpty(entry.getWorksCode())) {
					String code = (String) map.get(ORG_CODE_NEW);
					if (!StringUtil.isNullOrEmpty(code)) {
						result.set("worksCode", code);
					}

				}
				if (StringUtil.isNullOrEmpty(entry.getInsuranceCompany())) {
					result.set("insuranceCompany", (String) map
							.get(INSURANCE_COMPANY));
				}

				return result;

			}
		}
		return CheckResult.createAccepted(false);
	}

	public Collection<String> getBadProperties() {
		return BadPropertyUtil.create("sPolis", "nPolis");
	}

	private static boolean iseq(String aStr, String aAnother) {
		if (aStr == null)
			return aAnother != null;
		return aStr.equals(aAnother);
	}

	public static IExternalPersonInfoService getInfoService() {
		try {
			return EjbInjection.getInstance().getLocalService(
					IExternalPersonInfoService.class);
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}

}
