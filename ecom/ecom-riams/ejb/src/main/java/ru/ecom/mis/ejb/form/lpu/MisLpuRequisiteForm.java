package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.MisLpuRequisite;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Произвольный реквизит ЛПУ")
@EntityForm
@EntityFormPersistance(clazz = MisLpuRequisite.class)
@WebTrail(comment = "Произвольный реквизит ЛПУ", nameProperties = "name", view = "entityView-mis_lpuRequisite.do")
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class MisLpuRequisiteForm extends IdEntityForm {
    /** ЛПУ */
    @Comment("ЛПУ")
    @Persist
    public Long getLpu() {return lpu;}
    /** ЛПУ */
    private Long lpu ;

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return name;}
    /** Название */
    private String name ;

    /** Код */
    @Comment("Код")
    @Persist @Required
    public String getCode() {return code;}
    /** Код */
    private String code ;

    /** Значение */
    @Comment("Значение")
    @Persist
    public String getValue() {return value;}
    /** Значение */
    private String value ;
}
