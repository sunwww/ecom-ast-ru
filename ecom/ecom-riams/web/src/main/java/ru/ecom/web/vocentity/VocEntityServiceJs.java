package ru.ecom.web.vocentity;

import ru.ecom.ejb.services.vocentity.IVocEntityService;
import ru.ecom.ejb.services.vocentity.VocEntityInfo;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

public class VocEntityServiceJs {

	public Patient getVocEntityInfo(String aClassname, HttpServletRequest aRequest) throws NamingException {
		IVocEntityService service = Injection.find(aRequest).getService(IVocEntityService.class) ;
		VocEntityInfo vocEntityInfo = service.getVocEntityInfo(aClassname);
		System.out.println("vocInfo = "+vocEntityInfo);
		Patient patient = new Patient(); patient.setLastname("TESTOFFF");
		System.out.println("ret = "+patient);
		return patient;
	}
	
	public Object setVocEntityValue(String aClassname, String aId, String aProperty, String aValue, HttpServletRequest aRequest) throws NamingException {
		IVocEntityService service = Injection.find(aRequest).getService(IVocEntityService.class) ;
		return service.setVocEntityValue(aClassname, aId, aProperty, aValue);
	}

	public void removeVocEntity(String aClassname, String aId, HttpServletRequest aRequest) throws NamingException {
		IVocEntityService service = Injection.find(aRequest).getService(IVocEntityService.class) ;
		service.removeVocEntity(aClassname, aId) ;
		
	}
	
	public boolean delete(String aClassname, HttpServletRequest aRequest) {
		return true ;
	}
	public boolean edit(String aClassname, HttpServletRequest aRequest) {
		return true ;
	}
	public boolean view(String aClassname, HttpServletRequest aRequest) {
		return true ;
	}
	public boolean create(String aClassname, HttpServletRequest aRequest) {
		return true ;
	}
	
	
}
