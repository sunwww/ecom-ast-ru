package ru.ecom.mis.ejb.form.medcase.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.medcase.MedServiceComplexLinkForm;

import java.util.List;

public class MedServiceComplexLinkSaveInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		MedServiceComplexLinkForm form = (MedServiceComplexLinkForm)aForm ;
		if (form.getComplexMedService()!=null) {
			List<Object> listExisting = aContext.getEntityManager().createNativeQuery("select id from MedServiceComplexLink where complexmedservice_id='"+
					form.getComplexMedService()+"' and innermedservice_id='" + form.getInnerMedService() + "'").getResultList();
			if (!listExisting.isEmpty()) {
				Long linkId = Long.valueOf(listExisting.get(0).toString());
				throw new IllegalStateException("Уже имеется эта услуга в комплексной программе! " +
						"<a href='entityParentEdit-mis_medServiceComplexLink.do?id="+linkId + "'>Настроить");
			}
		}
		if (form.getCountInnerMedService()<1)
			throw new IllegalArgumentException("Количество услуг не может быть меньше одной в комплексной программе!");
	}
}