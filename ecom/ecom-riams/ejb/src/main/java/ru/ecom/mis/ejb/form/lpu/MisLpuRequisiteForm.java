package ru.ecom.mis.ejb.form.lpu;

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
public class MisLpuRequisiteForm extends IdEntityForm {
    /** ЛПУ */
    @Comment("ЛПУ")
    @Persist
    public Long getLpu() {return theLpu;}
    public void setLpu(Long aLpu) {theLpu = aLpu;}
    /** ЛПУ */
    private Long theLpu ;

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName ;

    /** Код */
    @Comment("Код")
    @Persist @Required
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код */
    private String theCode ;

    /** Значение */
    @Comment("Значение")
    @Persist
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;}
    /** Значение */
    private String theValue ;
}
