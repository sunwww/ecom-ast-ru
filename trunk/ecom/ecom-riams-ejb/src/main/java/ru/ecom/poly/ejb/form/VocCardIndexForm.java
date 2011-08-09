package ru.ecom.poly.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.ecom.poly.ejb.domain.voc.VocCardIndex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Катротека")
@EntityForm
@EntityFormPersistance(clazz = VocCardIndex.class)
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@WebTrail(comment = "Картотека", nameProperties = "name"
	, view = "entityParentView-voc_cardIndex.do"
		, list = "entityParentList-voc_cardIndex.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocCardIndex")
public class VocCardIndexForm extends IdEntityForm {
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	   /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }
    
    /** Внешний код */
	@Comment("Внешний код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Внешний код */
	private String theCode;
    /** Название */
    private String theName ;
    /** ЛПУ */
	private Long theLpu;	
}
