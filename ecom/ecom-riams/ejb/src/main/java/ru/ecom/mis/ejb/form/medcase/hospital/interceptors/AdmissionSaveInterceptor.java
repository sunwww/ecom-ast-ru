package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import org.json.JSONObject;
import ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

public class AdmissionSaveInterceptor implements IFormInterceptor {

    private final static Logger LOG = Logger.getLogger(AdmissionSaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		AdmissionMedCaseForm form=(AdmissionMedCaseForm)aForm ;
		EntityManager manager = aContext.getEntityManager();
		if (CAN_DEBUG) LOG.debug("Проверка правильности введенных данных");
		
		HospitalMedCase medCase = (HospitalMedCase)aEntity ;
		Long id = medCase.getId() ;
		
		StatisticStubStac stub = new StatisticStubStac(medCase,aContext.getSessionContext(),manager);
		
		String statCardNumber = form.getStatCardNumber() ;
		String stubCode = medCase.getStatisticStub()!=null?medCase.getStatisticStub().getCode():null;
		if (statCardNumber!=null && !statCardNumber.equals("") && (id>Long.valueOf(0))
				&& stubCode!=null&&!stubCode.equals(statCardNumber)
				) {
			
			String year = form.getDateStart().substring(6) ;
			//throw ""+year ;
			List<Object[]> list = manager
					.createNativeQuery("select id from StatisticStub where medCase_id='"+id+"' and DTYPE='StatisticStubExist' and code=:number and year=:year ")
				.setParameter("number", statCardNumber).setParameter("year",java.lang.Long.valueOf(year)).getResultList() ;
			
			if (list.size()==0) {
				boolean alwaysCreate = aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber") ;
				if (!alwaysCreate) {
	    			if (form.getDeniedHospitalizating()!=null && form.getDeniedHospitalizating()>Long.valueOf(0)) {
	    				//throw new IllegalArgumentException("Нельзя изменить номер стат.карты при отказе госпитализации");
	    				StatisticStubStac.removeStatCardNumber(manager, aContext.getSessionContext(),medCase);
	    			} else {
	    				StatisticStubStac.createStacCardNumber(id, statCardNumber, manager, aContext.getSessionContext(),form);
	    			}
	    		} else {
	    			StatisticStubStac.changeStatCardNumber(id, statCardNumber, manager, aContext.getSessionContext());
	    		}
				
			} else {
				if (form.getDeniedHospitalizating().equals(Long.valueOf(0))) {
					StatisticStubStac.createStacCardNumber(id,statCardNumber, manager, aContext.getSessionContext(),form);
				} else {
					
					StatisticStubStac.removeStatCardNumber(manager, aContext.getSessionContext(),medCase);
					//throw "remove" ;
				}
			}
			
		} else {
			List<Object> list = manager.createNativeQuery("select id from StatisticStub where medCase_id='"+id+"' and DTYPE='StatisticStubExist'")
				//.setParameter("number", aStatCardNumber).setParameter("year",java.lang.Long.valueOf(year))
				.getResultList() ;
			if (list.size()==0) {
				//if (aForm.deniedHospitalizating==0) {
					StatisticStubStac.createStacCardNumber(id, statCardNumber, manager, aContext.getSessionContext(),form);
				//} else {
					
				//}	
			}
		}
		
		stub.removeStatCardNumber() ;
		
		boolean adding1is = (!isEmpty(form.getOrderDiagnos()) || (!isEmpty(form.getOrderMkb()))) ;
		boolean adding2is = (!isEmpty(form.getEntranceDiagnos()) || (!isEmpty(form.getEntranceMkb()))) ;
		if (adding1is || adding2is) {
			boolean adding1 = false ;
			if (!adding1is) adding1 = true ; 
			boolean adding2 = false ;
			if (!adding2is) adding2 = true ;
			VocDiagnosisRegistrationType vocTypeOrder = manager.find(VocDiagnosisRegistrationType.class, Long.valueOf(1));
			VocDiagnosisRegistrationType vocTypeEnter = manager.find(VocDiagnosisRegistrationType.class, Long.valueOf(2));
			List<Diagnosis> diagList = manager.createQuery("from Diagnosis where medCase=:med").setParameter("med", medCase).getResultList() ;
			if (diagList==null) diagList = new ArrayList<Diagnosis>(); 
			for(Diagnosis diag:diagList){
				if (!adding1) adding1=setDiagnosisByType(false,diag, vocTypeEnter, form.getOrderDiagnos(), form.getOrderDate(), form.getOrderMkb(), medCase, manager) ;
				if (!adding2) adding2=setDiagnosisByType(false,diag, vocTypeOrder, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, manager) ;
				if (adding1&&adding2) break ;
			}
			
			if (!adding1 || !adding2) {
				if (!adding1) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeEnter, form.getOrderDiagnos(), form.getOrderDate(), form.getOrderMkb(), medCase, manager) ;
					//diagList.add(diag);
				}
				if (!adding2) {
					Diagnosis diag = new Diagnosis();
					setDiagnosisByType(true,diag, vocTypeOrder, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, manager) ;
					//diagList.add(diag);
				}
				//medCase.setDiagnosis(diagList);
			}
		}
		List<Object[]> rec = manager.createNativeQuery("select pasmot.lastname||' '||substring(pat.firstname,1,1)||''||substring(coalesce(pat.middlename,' '),1,1) as f1_name , pr.phonenumber " +
				" from patientlistrecord pr left join patient pat on pat.id=pr.patient where pr.patient="+medCase.getPatient().getId()+" and pr.phoneNumber is not null").getResultList();
		if (rec.size()>0) {
			Object[] r = rec.get(0);
			try {
				EjbEcomConfig config = EjbEcomConfig.getInstance() ;
				String address =config.get("ru.amokb.patientcabinetaddress", null) ;
				if (address!=null){
					String method = "SendSms";
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
					String wfName = medCase.getDepartment().getName();
					String message = "Пациент "+r[0].toString()+" госпитализирован в "+wfName;
					String phone = r[1].toString();
					JSONObject json = new JSONObject();
					json.put("phonenumber",phone);
					json.put("message",message);
					TemplateProtocolServiceBean bean = new TemplateProtocolServiceBean();
					bean.makePOSTRequest(json.toString(),address,method,null,null,manager);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*PatientForm form = (PatientForm) aForm ;
		Patient patient = (Patient) aEntity ;

		if(form.isAttachedByPolicy()) {
			if(form.getCreateNewOmcPolicy()) {
				// новый полис
				MedPolicyOmcForm polForm = form.getPolicyOmcForm() ;
				polForm.setPatient(patient.getId());
				try {
					long policyId = EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
						.create(polForm);
					MedPolicyOmc medPolicyOmc = aManager.find(MedPolicyOmc.class, policyId) ;
					//System.out.println("medPolicyOmc="+medPolicyOmc);
					patient.setAttachedOmcPolicy(medPolicyOmc);
					if(patient.getMedPolicies()!=null) {
						patient.getMedPolicies().add(medPolicyOmc);
					}
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}			
			
		} else {
			// прикреплен по адресу
			try {
				patient.setAddress(aManager.find(Address.class, form.getAddress())) ;
				patient.setHouseNumber(form.getHouseNumber());
				patient.setHouseBuilding(form.getHouseBuilding());
				patient.setFlatNumber(form.getFlatNumber());
				new PatientListener().onUpdate(patient) ;
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}*/
		//System.out.println("save manager = "+aManager);
		//System.out.println(" address = "+patient.getAddressInfo());
		//if (CAN_DEBUG)
		//	LOG.debug("intercept: form.getPolicyOmcForm().getSeries() = " + form.getPolicyOmcForm().getSeries());
	
	private boolean setDiagnosisByType(boolean aNewIs, Diagnosis aDiag, VocDiagnosisRegistrationType aType, String aName, String aDate, Long aCode, HospitalMedCase aMedCase, EntityManager aManager) {
		boolean resault = false ;
		if (!aNewIs) {
			aNewIs = aDiag.getRegistrationType()!=null && aDiag.getRegistrationType().equals(aType) ; 
		}
		if (aNewIs) {
			aDiag.setName(aName);
			if (aCode!=null) {
				VocIdc10 mkb = aManager.find(VocIdc10.class, aCode) ;
				aDiag.setIdc10(mkb);
			}
			aDiag.setMedCase(aMedCase);
			try {aDiag.setEstablishDate(DateFormat.parseSqlDate(aDate));} 
			catch(Exception e) {
				
			}
			if (aDiag.getRegistrationType()==null) aDiag.setRegistrationType(aType);
			resault = true ;
		}
		aManager.persist(aDiag) ;
		return resault ;
		
	}
    private static boolean isEmpty(Long aLong) {
        return (aLong == null)||(aLong == 0) ;
    }
    private static boolean isEmpty(String aString) {
        return (aString == null)||(aString.trim().equals("")) ;
    }

	
}
