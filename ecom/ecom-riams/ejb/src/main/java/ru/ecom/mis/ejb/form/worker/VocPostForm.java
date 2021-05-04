package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.voc.VocPost;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= VocPost.class)
@Comment("Должность")
@WebTrail(comment = "Должность", nameProperties= "name", view="entityView-voc_post.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocPost")
@Setter
public class VocPostForm extends IdEntityForm {
	
    /** Код профиля отделения для стационара или специалиста для поликлиники */
	@Comment("Код профиля отделения для стационара или специалиста для поликлиники")
	@Persist
	public Long getOmcDepType() {return omcDepType;}
	/** Код профиля отделения для стационара или специалиста для поликлиники */
	private Long omcDepType;
	
	/** Врачебная должность по ОМС */
	@Comment("Врачебная должность по ОМС")
	@Persist
	public Long getOmcDoctorPost() {return omcDoctorPost;}
	/** Врачебная должность по ОМС */
	private Long omcDoctorPost;

    /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return name ; }

    /** Внешний код */
	@Comment("Внешний код")
	@Persist
	public String getCode() {
		return code;
	}

	/** Внешний код */
	private String code;
    /** Название */
    private String name ;
}
