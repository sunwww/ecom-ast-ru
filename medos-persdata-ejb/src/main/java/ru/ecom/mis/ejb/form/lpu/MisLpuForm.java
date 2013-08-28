package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("ЛПУ")
@EntityForm
@EntityFormPersistance(clazz = MisLpu.class)
@WebTrail(comment = "ЛПУ", nameProperties = "name", view = "/riams/entityView-mis_lpu.do")
@Parent(property = "parent", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MisLpu")
public class MisLpuForm extends IdEntityForm {
    /**
     * Родитель
     */
    @Persist
    public Long getParent() {
        return theParent;
    }

    public void setParent(Long aParent) {
        theParent = aParent;
    }

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название */
    private String theName ;
    /**
     * Родитель
     */
    private Long theParent;
}
