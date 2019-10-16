package ru.ecom.mis.ejb.form.worker;

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
public class VocPostForm extends IdEntityForm {
	
    /** Код профиля отделения для стационара или специалиста для поликлиники */
	@Comment("Код профиля отделения для стационара или специалиста для поликлиники")
	@Persist
	public Long getOmcDepType() {return theOmcDepType;}
	public void setOmcDepType(Long aOmcDepType) {theOmcDepType = aOmcDepType;}
	/** Код профиля отделения для стационара или специалиста для поликлиники */
	private Long theOmcDepType;
	
	/** Врачебная должность по ОМС */
	@Comment("Врачебная должность по ОМС")
	@Persist
	public Long getOmcDoctorPost() {return theOmcDoctorPost;}
	public void setOmcDoctorPost(Long aOmcDoctorPost) {theOmcDoctorPost = aOmcDoctorPost;}
	/** Врачебная должность по ОМС */
	private Long theOmcDoctorPost;

    /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }
    
    /** Внешний код */
	@Comment("Внешний код")
	@Persist
	public String getCode() {
		return theCode;
	}

	public void setCode(String aCode) {
		theCode = aCode;
	}

	/** Внешний код */
	private String theCode;
    /** Название */
    private String theName ;
}
