package ru.ecom.mis.ejb.form.contract.interceptor;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.form.contract.PriceMedServiceForm;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;

public class PriceMedServiceSaveInterceptor  implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext)  {
		PriceMedServiceForm form = (PriceMedServiceForm) aForm ;
		PriceMedService priceMedService = (PriceMedService) aEntity ;
		EntityManager manager = aContext.getEntityManager() ;
		MedServiceForm msForm = form.getMedServiceForm() ;
		
		if (form.getMedServiceIsCreate()!=null&&form.getMedServiceIsCreate().equals(Boolean.TRUE)) {
			//try {
				if (msForm.getCode().trim().equals("")) {
					if (priceMedService.getPricePosition()!=null)
						msForm.setCode(priceMedService.getPricePosition().getCode().toUpperCase().trim()) ;
				} else {
					msForm.setCode(msForm.getCode().toUpperCase().trim()) ;
				}
				MedService medService = new MedService() ;
				List<MedService> list = manager.createQuery("from MedService where code=:code").setParameter("code", msForm.getCode()).getResultList() ;
				if (list.size()>0) {
					medService = list.get(0) ;
				} else {
					
				
					
					if (msForm.getName().trim().equals("")) {
						if (priceMedService.getPricePosition()!=null)
						msForm.setName(priceMedService.getPricePosition().getName().toUpperCase().trim()) ;
					} else {
						msForm.setName(msForm.getName().toUpperCase().trim()) ;
					}
					
					
						long medServiceId;
						try {
							/*boolean createVMS = false ;
							if (msForm.getVocMedServiceIsCreate()!=null &&msForm.getVocMedServiceIsCreate().equals(Boolean.TRUE)) {
								createVMS=true ;
								msForm.setVocMedServiceIsCreate(null) ;
							}*/
							medServiceId = EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
								.create(msForm);
							medService = aContext.getEntityManager().find(MedService.class, medServiceId) ;
							/*if (createVMS) {
								VocMedService vms = new VocMedService() ;
								List<VocMedService> listvms = manager.createQuery("from VocMedService where code=:code").setParameter("code", medService.getCode()).getResultList() ;
								if (listvms.size()>0) {
									vms = listvms.get(0) ;
								} else {
									vms.setName(medService.getName()) ;
									vms.setCode(medService.getCode()) ;
									vms.setComment("autogenerate") ;
									manager.persist(vms) ;
								}
								medService.setVocMedService(vms) ;
							}*/
						} catch (EntityFormException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						form.setDateFrom(msForm.getStartDate()) ;
						form.setDateTo(msForm.getFinishDate()) ;
						
						
					}
				if (medService!=null) {
					priceMedService.setMedService(medService) ;
					form.setMedService(medService.getId()) ;
					aContext.getEntityManager().persist(priceMedService) ;
				}
			//} catch (Exception e) {
			//	throw new IllegalStateException(e.getMessage());
			//}
			
						
			
		}
	}
}
