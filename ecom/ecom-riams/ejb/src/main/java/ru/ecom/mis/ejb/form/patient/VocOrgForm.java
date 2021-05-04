package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocOrg.class)
@Comment("Справочник организаций")
@WebTrail(comment = "Организация", nameProperties = "name", view = "entityView-mis_vocOrg.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocOrg")
@Setter
public class VocOrgForm extends IdEntityForm {
    /** Старый код ФОНДА */
    @Comment("Старый код ФОНДА")
    @Persist
    public String getOldFondNumber() { return oldFondNumber ; }

    /** Код ФОНДА */
    @Comment("Новый код ФОНДА")
    @Persist 
    public String getFondNumber() { return fondNumber ; }

    /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return name ; }

    /** Внешний код */
	@Comment("Внешний код")
	@Persist
	public String getCode() {return code;}

	/** Внешний код */
	private String code;
    /** Название */
    private String name ;
    /** Код ФОНДА */
    private String fondNumber ;
    /** Старый код ФОНДА */
    private String oldFondNumber ;
	

}
