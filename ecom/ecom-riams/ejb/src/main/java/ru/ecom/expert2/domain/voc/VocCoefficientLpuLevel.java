package ru.ecom.expert2.domain.voc;

import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Коэффицинт уровня оказания мед. помощи
 */
@Entity
public class VocCoefficientLpuLevel extends VocCoefficient {
    
    /** Идентификатор отделения */
    @Comment("Идентификатор отделения")
    @OneToOne
    public MisLpu getDepartment() {return theDepartment;}
    public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
    /** Идентификатор отделения */
    private MisLpu theDepartment ;

    /** Профиль мед. помощи */
/*    @Comment("Профиль мед. помощи")
    @OneToOne
    public VocE2MedHelpProfile getHelpProfile() {return theHelpProfile;}
    public void setHelpProfile(VocE2MedHelpProfile aHelpProfile) {theHelpProfile = aHelpProfile;}
    /** Профиль мед. помощи */
//    private VocE2MedHelpProfile theHelpProfile ;

}
