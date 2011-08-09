package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.patient.MedPolicyDmcForeign;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.ecom.mis.ejb.form.patient.interceptors.MedPolicyPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Полис ДМС иногороднего
 */
@EntityForm
@EntityFormPersistance(clazz = MedPolicyDmcForeign.class)
@Comment("Полис ДМС иногороднего")
@WebTrail(comment = "Полис ДМС иногороднего", nameProperties = "polNumber", view = "entityView-mis_medPolicyDmcForeign.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedPolicy/DmcForeign")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedPolicyPreCreate.class)
)
public class MedPolicyDmcForeignForm extends MedPolicyOmcForeignForm {
	/** Тип полиса */
	@Comment("Тип полиса")
	@Persist 
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Тип полиса */
	private Long theType;
}
