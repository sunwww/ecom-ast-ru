package ru.ecom.mis.web.dwr.medcase;

import java.text.ParseException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

/**
 * Сервис по случаю лечения в стационаре
 * @author Tkacheva Sveltana
 */
public class HospitalMedCaseServiceJs {
	//Получить данные диагноза по умолчанию для акушерства
	public String getIdc10ByDocDiag(Long aIdDocDiag,HttpServletRequest aRequest) throws NamingException { 
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getIdc10ByDocDiag(aIdDocDiag) ;
	}
	public String getTypeDiagByAccoucheur(HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getTypeDiagByAccoucheur() ;
	}
	public String getRW(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		return service.getRW(aMedCase) ;
	}
	public String setRW(Long aMedCase, String aRwDate, String aRwNumber, HttpServletRequest aRequest) throws NamingException, ParseException {
		IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		service.setRW(aMedCase, aRwDate, aRwNumber) ;
		return "" ;
	}
    public String changeStatCardNumber(String aNewStatCardNumber, Long aMedCase, HttpServletRequest aRequest) throws NamingException, JspException {
    	System.out.println(
    			new StringBuffer()
    			.append("Изменение номера стат. карты: ")
    			.append(aNewStatCardNumber)
    			.append(" случая лечения в стационаре #")
    			.append(aMedCase)
    		) ;
    	boolean always = RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber", aRequest) ;
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
        //return service.(aStatCardNumber, aDateStart, aEntranceTime, aPatient);
        return service.getChangeStatCardNumber(aMedCase, aNewStatCardNumber,always) ;
    }
    public String getListTemperatureCurve(Long aMedCase, HttpServletRequest aRequest) throws Exception {
    	System.out.println("Температурная кривая");
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	
    	return service.getTemperatureCurve(aMedCase) ;
    }
    
    public Long getPatient(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getPatient(aMedCase) ;
    }
    
    public String isOpenningSlo(Long aMedCase, HttpServletRequest aRequest) throws NamingException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.isOpenningSlo(aMedCase) ;
    }
    
    public String deleteDischarge(Long aMedCaseId, HttpServletRequest aRequest) throws NamingException  {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.deleteDataDischarge(aMedCaseId) ;
    }
    
    public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException{
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.findDoubleServiceByPatient(aMedService, aPatient, aService, aDate) ;
    }
    public String findDoubleOperationByPatient(Long aSurOperation, Long aPatient, Long aOperation, String aDate, HttpServletRequest aRequest) throws NamingException, ParseException{
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.findDoubleOperationByPatient(aSurOperation, aPatient, aOperation, aDate) ;
    }
    
    public String checkMedPolicy(Long aPatient, Long aServiceStream, HttpServletRequest aRequest) throws NamingException, ParseException{
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.checkMedPolicy(aPatient, aServiceStream);
    	
    }
    public String getOperations(Long aPatient, String aDateStart
    		,String aDateFinish, HttpServletRequest aRequest) throws NamingException, ParseException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getOperationsText(aPatient, aDateStart, aDateFinish);
    	
    }
    public String getLabInvestigations(Long aPatient, String aDateStart
			,String aDateFinish,boolean aLabsIs,boolean aFisioIs,boolean aFuncIs,boolean aConsIs, boolean aLuchIs, HttpServletRequest aRequest) throws NamingException, ParseException {
    	IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	return service.getnvestigationsTextDTM(aPatient, aDateStart, aDateFinish,aLabsIs,aFisioIs,aFuncIs,aConsIs, aLuchIs);
    }
}
