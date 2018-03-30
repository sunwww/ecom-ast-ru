package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocCoefficient;
import ru.ecom.expert2.domain.voc.VocCoefficientLpuLevel;
import ru.ecom.expert2.domain.voc.VocE2BaseTariff;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Коэффицинт уровня оказания мед. помощи
 */
@EntityForm
@EntityFormPersistance(clazz = VocCoefficientLpuLevel.class)
@Comment("Коэффициент уровня ЛПУ (отделения)")
@WebTrail(comment = "Коэффициент уровня ЛПУ (отделения)", nameProperties = "id", view = "entityView-e2_vocCofficientLpuLevel.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocCoefficientLpuLevelForm  extends VocCoefficientForm {
    
    /** Идентификатор отделения */
    @Comment("Идентификатор отделения")
    @Persist
    public Long getDepartment() {return theDepartment;}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
    /** Идентификатор отделения */
    private Long theDepartment ;

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @Persist
    public Long getHelpProfile() {return theHelpProfile;}
    public void setHelpProfile(Long aHelpProfile) {theHelpProfile = aHelpProfile;}
    /** Профиль мед. помощи */
    private Long theHelpProfile ;
}
