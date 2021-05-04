package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.patient.MedPolicyDmc;
import ru.ecom.mis.ejb.form.patient.interceptors.MedPolicyPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Полис ДМС
 */
@EntityForm
@EntityFormPersistance(clazz = MedPolicyDmc.class)
@Comment("Полис ДМС")
@WebTrail(comment = "Полис ДМС", nameProperties = "polNumber", view = "entityView-mis_medPolicyDmc.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedPolicy/Dmc")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedPolicyPreCreate.class)
)
@Setter
public class MedPolicyDmcForm extends MedPolicyForm {
    /** Страховая компания */
    @Comment("Страховая компания")
    @Persist @Required
    public Long getCompany() { return company ; }
    /** Страховая компания */
    private Long company ;
}