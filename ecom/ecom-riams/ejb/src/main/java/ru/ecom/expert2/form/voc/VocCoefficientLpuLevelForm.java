package ru.ecom.expert2.form.voc;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocCoefficientLpuLevel;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


/**
 * Коэффицинт уровня оказания мед. помощи
 */
@EntityForm
@EntityFormPersistance(clazz = VocCoefficientLpuLevel.class)
@Comment("Коэффициент уровня ЛПУ (отделения)")
@WebTrail(comment = "Коэффициент уровня ЛПУ (отделения)", nameProperties = "id", view = "entityView-e2_vocCofficientLpuLevel.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VocCoefficientLpuLevelForm  extends VocCoefficientForm {
    
    /** Идентификатор отделения */
    @Comment("Идентификатор отделения")
    @Persist
    public Long getDepartment() {return department;}
    /** Идентификатор отделения */
    private Long department ;

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @Persist
    public Long getProfile() {return profile;}
    /** Профиль мед. помощи */
    private Long profile ;
}
