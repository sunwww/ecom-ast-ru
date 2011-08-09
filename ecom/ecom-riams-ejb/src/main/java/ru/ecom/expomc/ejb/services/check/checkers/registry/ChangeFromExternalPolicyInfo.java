package ru.ecom.expomc.ejb.services.check.checkers.registry;

import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.BIRTHDATE;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.FIRSTNAME;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.LASTNAME;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.MIDDLENAME;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.ORG_CODE_NEW;
import static ru.ecom.expomc.ejb.services.externalperson.IExternalPersonInfoService.ORG_CODE_OLD;

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

@Comment("Изменение информации о пациенте по полису")
public class ChangeFromExternalPolicyInfo implements ICheck {

	public CheckResult check(ICheckContext aContext) throws CheckException {
		RegistryEntry entry = (RegistryEntry) aContext.getEntry() ;
		Map<String, Object> map = getInfoService().findPersonInfoByPolicy(
				entry.getInsuranceCompany(), entry.getSPolis(), entry.getNPolis()) ;
		CheckResult result = CheckResult.createAccepted(false);
		if(map!=null) {
			
			update(map,ORG_CODE_OLD, result, "worksCode", aContext ) ;
			update(map,ORG_CODE_NEW, result, "worksOldCode", aContext ) ;
			update(map,FIRSTNAME, result, "firstname", aContext ) ;
			update(map,LASTNAME, result, "lastname", aContext ) ;
			update(map,MIDDLENAME, result, "middlename", aContext ) ;
			update(map,BIRTHDATE, result, "birthDate", aContext ) ;
				
				return result ;
		}
		return result;
	}
	
	private static void update(Map<String, Object> map, String aMapProperty, CheckResult aResult, String aProperty, ICheckContext aContext) {
		Object obj = map.get(aMapProperty) ;
		boolean canUpdate = false ;
		if(obj!=null) {
			if(obj instanceof String) {
				String str = (String) obj ;
				if(StringUtil.isNullOrEmpty(str)) {
					canUpdate = true ;
				}
			} else {
				canUpdate = true ;
			}
		}
		if(canUpdate) {
			aResult.set(aProperty, obj) ;
			aResult.setAccepted(true) ;
		}
	}
	
    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("sPolis","nPolis") ;
	}
	
	private static boolean iseq(String aStr, String aAnother) {
		if(aStr==null) return aAnother!=null ;
		return aStr.equals(aAnother) ;
	}
	
	private IExternalPersonInfoService getInfoService() {
			return EjbInjection.getInstance().getLocalService(IExternalPersonInfoService.class) ;
	}
	//private @EJB IExternalPersonInfoService theInfoService ; 

}
