package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubExist;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;

public class AdmissionCreateInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(AdmissionCreateInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		try {
		AdmissionMedCaseForm form = (AdmissionMedCaseForm)aForm;
		//HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		
		if (CAN_DEBUG) LOG.debug("Проверка даты поступления для ./CreateHour ...");
		SecPolicy.checkPolicyCreateHour(aContext.getSessionContext(), "/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateHour"
			        , form.getDateStart(), form.getEntranceTime());
		theEntityManager = aContext.getEntityManager() ;
		theMedCase = (HospitalMedCase)aEntity;
        if (CAN_DEBUG) LOG.debug("создание номера стат карты ...");
       // String oldStatNumber=form.getStatCardNumber() ;
        //medCase.setStatCardNumber("");
        boolean isCreateStatCardNumberByHand = aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardNumberByHand");
        if (CAN_DEBUG) LOG.debug(" aForm.getStatCardNumber() = " + form.getStatCardNumber());
        if (CAN_DEBUG) LOG.debug(" isCreateStatCardNumberByHand = " + isCreateStatCardNumberByHand);
        StatisticStubStac stub = new StatisticStubStac(theMedCase,aContext.getSessionContext(),aContext.getEntityManager(),"/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber");
        if (stub.isStatCardNumberMustCreate()) {
        	
    		if (CAN_DEBUG) LOG.debug("Номер карты должен создаваться...");
            // проверка на существование номера стат. карты
            if (isCreateStatCardNumberByHand && !isNullOrEmpty(form.getStatCardNumber())) {
                if (CAN_DEBUG) LOG.debug("  Создание номера стат карты вручную [ номер стат.карты = " + form.getStatCardNumber() + "]");
                Calendar cal = Calendar.getInstance();
                cal.setTime(theMedCase.getDateStart());
                if (stub.isStatCardNumberExists(form.getStatCardNumber(), cal.get(Calendar.YEAR))) {
                    throw new IllegalArgumentException("Номер стат. карты " + form.getStatCardNumber() + " уже существует за этот год");
                }
                StatisticStubExist statcard = new StatisticStubExist();
                statcard.setCode(form.getStatCardNumber());
                statcard.setYear(stub.getYear());
                statcard.setMedCase(theMedCase);
                theMedCase.setStatisticStub(statcard);
                theEntityManager.persist(statcard) ;
                //StringTokenizer st = new StringTokenizer(aForm.getStatCardNumber()," -");
                //int number = Integer.parseInt(st.nextToken()) ;
//            StacSTATNPrimaryKey statPk = new StacSTATNPrimaryKey(cal.get(Calendar.YEAR), number);
//            StacSTATNUtil.getLocalHome().create(statPk) ;
            } else {
                if (CAN_DEBUG) LOG.debug("Создание номера стат. карты автоматически");
                stub.createStacCardNumber();
            }
        }
		} catch (Exception e) {
			
			throw new IllegalArgumentException(e.getMessage());
		}

		
	}
	/*
 
    private static boolean isEmpty(Long aLong) {
        return (aLong == null)||(aLong == 0) ;
    }*/
    private static boolean isNullOrEmpty(String aStr) {
        return aStr==null || aStr.trim().equals("") ;
    }

	private EntityManager theEntityManager ;
    /** HospitalMedCase */
	private HospitalMedCase theMedCase;


}
