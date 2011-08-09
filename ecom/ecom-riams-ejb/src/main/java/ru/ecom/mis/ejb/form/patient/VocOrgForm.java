package ru.ecom.mis.ejb.form.patient;

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
public class VocOrgForm extends IdEntityForm {
    /** Старый код ФОНДА */
    @Comment("Старый код ФОНДА")
    @Persist
    public String getOldFondNumber() { return theOldFondNumber ; }
    public void setOldFondNumber(String aOldFondNumber) { theOldFondNumber = aOldFondNumber ; }

    /** Код ФОНДА */
    @Comment("Новый код ФОНДА")
    @Persist 
    public String getFondNumber() { return theFondNumber ; }
    public void setFondNumber(String aFondNumber) { theFondNumber = aFondNumber ; }

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
    /** Код ФОНДА */
    private String theFondNumber ;
    /** Старый код ФОНДА */
    private String theOldFondNumber ;
	

}
