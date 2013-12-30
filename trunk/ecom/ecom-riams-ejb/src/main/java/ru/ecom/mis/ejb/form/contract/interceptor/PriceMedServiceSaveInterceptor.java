package ru.ecom.mis.ejb.form.contract.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.form.contract.PriceMedServiceForm;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;

public class PriceMedServiceSaveInterceptor  implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PriceMedServiceForm form = (PriceMedServiceForm) aForm ;
		PriceMedService priceMedService = (PriceMedService) aEntity ;
		//priceMedService.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		//priceMedService.setEditDate(new java.sql.Date(new java.util.Date().getTime())) ;

		if(form.getMedServiceIsCreate()!=null&&form.getMedServiceIsCreate().equals(Boolean.TRUE)) {
			MedServiceForm msForm = form.getMedServiceForm() ;
			//msForm.setParent(priceMedService.getId());
			msForm.setCode(msForm.getCode().toUpperCase().trim()) ;
			msForm.setName(msForm.getName().toUpperCase().trim()) ;
			try {
				long medServiceId = EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
					.create(msForm);
				System.out.println("---medService id----"+medServiceId) ;
				MedService medService = aContext.getEntityManager().find(MedService.class, medServiceId) ;
				System.out.println("---medService----"+medService) ;
				priceMedService.setMedService(medService) ;
				System.out.println("---priceMedService----"+priceMedService) ;
				form.setMedService(medServiceId) ;
				aContext.getEntityManager().persist(priceMedService) ;
			} catch (Exception e) {
				throw new IllegalStateException(e.getMessage());
			}
						
			
		}
	}
}
