package ru.ecom.mis.ejb.form.medcase.voc;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliDefect;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocKiliDefect.class)
@Comment("Справочник дефектов КИЛИ")
@WebTrail(comment = "Справочник дефектов КИЛИ", nameProperties= "id", view="entityView-voc_kiliDefect.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocKiliDefect")
@Setter
public class VocKiliDefectForm extends IdEntityForm{
	 /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return name ; }
    private String name;
        
    /** Внешний код */
	@Comment("Внешний код")
	@Persist @Required
	public String getCode() {return code;}
	private String code;
}