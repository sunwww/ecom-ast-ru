package ru.ecom.mis.ejb.form.patient;

import javax.persistence.OneToOne;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.ecom.mis.ejb.form.patient.interceptors.MedPolicyPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoInputNonLat;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.MaxLength;
import ru.nuzmsh.forms.validator.validators.MinLength;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Полис ОМС
 */
@EntityForm
@EntityFormPersistance(clazz = MedPolicyOmc.class)
@Comment("Полис ОМС")
@WebTrail(comment = "Полис ОМС", nameProperties = "polNumber", view = "entityView-mis_medPolicyOmc.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedPolicy/Omc")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedPolicyPreCreate.class)
)
public class MedPolicyOmcForm extends MedPolicyForm {

    /** Серия */
    @Comment("Серия")
    @Persist
    @Required @DoUpperCase @DoInputNonLat
    public String getSeries() { return super.getSeries() ; }

    /** Организация */
    @Persist
    public Long getOrg() { return theOrg ; }
    public void setOrg(Long aOrg) { theOrg = aOrg ; }

    /** Прикрепленное ЛПУ */
	@Comment("Прикрепленное ЛПУ")
	@Persist
	public Long getAttachedLpu() {
		return theAttachedLpu;
	}

	public void setAttachedLpu(Long aAttachedLpu) {
		theAttachedLpu = aAttachedLpu;
	}

	/** Тип полиса */
	@Comment("Тип полиса")
	@Persist @Required 
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Тип полиса */
	private Long theType;
	/** Страховая компания */
    @Comment("Страховая компания")
    @Persist @Required
    public Long getCompany() { return theCompany ; }
    public void setCompany(Long aCompany) { theCompany = aCompany ; }
    /** Страховая компания */
    private Long theCompany ;
	/** Прикрепленное ЛПУ */
	private Long theAttachedLpu;
    /** Организация */
    private Long theOrg ;
    

}