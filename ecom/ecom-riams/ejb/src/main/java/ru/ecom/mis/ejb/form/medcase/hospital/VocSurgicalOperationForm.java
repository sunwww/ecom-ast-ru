package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOperation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocOperation.class)
@Comment("Справочник хир.операций")
@WebTrail(comment = "Справочник хир.операций", nameProperties= "id", view="entityParentView-voc_operation.do")
//@Parent(property="medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Voc/VocOperation")
public class VocSurgicalOperationForm extends IdEntityForm {
    /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }
    
    /** Внешний код */
	@Comment("Внешний код")
	@Persist @Required
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	@Persist @DateString @DoDateString
	public String getStartActualDate() {return theStartActualDate;}
	public void setStartActualDate(String aStartActualDate) {theStartActualDate = aStartActualDate;}

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	@Persist @DateString @DoDateString
	public String getFinishActualDate() {return theFinishActualDate;}
	public void setFinishActualDate(String aFinishActualDate) {theFinishActualDate = aFinishActualDate;}
	/** Уровонь сложности */
	@Comment("Уровонь сложности")
	@Persist
	public Long getComplexity() {return theComplexity;}
	public void setComplexity(Long aComplexity) {theComplexity = aComplexity;}

	/** Код федеральный */
	@Comment("Код федеральный")
	@Persist
	public String getCodeF() {return theCodeF;}
	public void setCodeF(String aCodeF) {theCodeF = aCodeF;}

	/** Отделения */
	@Comment("Отделения")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=MisLpu.class)
	public String getDepartments() {return theDepartments;}
	public void setDepartments(String aDepartments) {theDepartments = aDepartments;}

	
	/** Отделения */
	private String theDepartments;
	/** Код федеральный */
	private String theCodeF;
	/** Уровонь сложности */
	private Long theComplexity;
	/** Дата окончания актуальности */
	private String theFinishActualDate;
	/** Дата начала актуальности */
	private String theStartActualDate;
	/** Внешний код */
	private String theCode;
    /** Название */
    private String theName ;
}
