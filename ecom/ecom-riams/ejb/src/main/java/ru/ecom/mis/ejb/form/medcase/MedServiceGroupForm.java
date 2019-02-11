package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.MedServiceGroup;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz=MedServiceGroup.class)
@Comment("Медицинская услуга")
@WebTrail(comment = "Медицинская услуга"
	, nameProperties= "name"
		, view="entityParentView-mis_medServiceGroup.do"
//			,list = "entityParentList-mis_medService.do"
				)
@Parent(property="parent", parentForm= MedServiceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService")
@ASaveInterceptors(
        @AEntityFormInterceptor(MedServiceSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(MedServiceViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(MedServiceSaveInterceptor.class)
	
})
public class MedServiceGroupForm extends MedServiceForm {

}
