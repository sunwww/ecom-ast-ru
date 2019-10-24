package ru.ecom.mis.ejb.uc.privilege.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.uc.privilege.domain.Recipe;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Recipe.class)
@WebTrail(comment = "Рецепт", nameProperties= "info", view="entityParentView-mis_recipe.do")
//@Parent(property="patient", parentForm=PatientForm.class)
@Subclasses(value = { PrivilegeRecipeForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/Person/Privilege/Recipe")
public class RecipeForm extends IdEntityForm {
	
	/** Лекарство */
	@Comment("Лекарство")
	@Persist @Required
	public Long getDrugClassify() {
		return theDrugClassify;
	}

	/** ЛПУ, которое выписало рецепт */
	@Comment("ЛПУ, которое выписало рецепт")
	@Persist
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** Врач */
	@Comment("Врач")
	@Required @Persist
	public Long getDoctor() {
		return theDoctor;
	}

	public void setDoctor(Long aDoctor) {
		theDoctor = aDoctor;
	}

	/** Номер рецепта */
	@Comment("Номер рецепта")
	@Required @Persist
	public String getRecipeNumber() {
		return theRecipeNumber;
	}

	public void setRecipeNumber(String aRecipeNumber) {
		theRecipeNumber = aRecipeNumber;
	}

	/** Дата выписки */
	@Comment("Дата выписки")
	@Required @Persist
	public String getRecipeDate() {
		return theRecipeDate;
	}

	public void setRecipeDate(String aRecipeDate) {
		theRecipeDate = aRecipeDate;
	}

	/** Тип льготы */
	@Comment("Тип льготы")
	@Persist
	public Long getPrivilegeCategory() {
		return thePrivilegeCategory;
	}

	public void setPrivilegeCategory(Long aPrivilegeCategory) {
		thePrivilegeCategory = aPrivilegeCategory;
	}
	
	/** МКБ10 */
	@Comment("МКБ10")
	@Persist
	public Long getIdc10() {
		return theIdc10;
	}

	public void setIdc10(Long aIdc10) {
		theIdc10 = aIdc10;
	}

	/** Процент оплаты */
	@Comment("Процент оплаты")
	@Persist
	public Long getPayPercent() {
		return thePayPercent;
	}

	public void setPayPercent(Long aPaymentPercent) {
		thePayPercent = aPaymentPercent;
	}

	/** Решение КЭК */
	@Comment("Решение КЭК")
	@Persist
	public boolean getKekDesicion() {
		return theKekDesicion;
	}

	public void setKekDesicion(boolean aKekDesicion) {
		theKekDesicion = aKekDesicion;
	}

	/** Лекарственная форма */
	@Comment("Лекарственная форма")
	@Required @Persist
	public Long getDrugForm() {
		return theDrugForm;
	}

	public void setDrugForm(Long aDrugForm) {
		theDrugForm = aDrugForm;
	}

	/** СНИЛС по рецепту */
	@Comment("СНИЛС по рецепту")
	@Persist
	public String getRecSnils() {
		return theRecSnils;
	}

	public void setRecSnils(String aRecSnils) {
		theRecSnils = aRecSnils;
	}

	/** Фамилия по рецепту */
	@Comment("Фамилия по рецепту")
	@Persist
	public String getRecLastname() {
		return theRecLastname;
	}

	public void setRecLastname(String aRecLastname) {
		theRecLastname = aRecLastname;
	}

	/** Имя по рецепту */
	@Comment("Имя по рецепту")
	@Persist
	public String getRecFirstname() {
		return theRecFirstname;
	}

	public void setRecFirstname(String aRecFirstname) {
		theRecFirstname = aRecFirstname;
	}

	/** Отчество по рецепту */
	@Comment("Отчество по рецепту")
	@Persist
	public String getRecMiddlename() {
		return theRecMiddlename;
	}

	public void setRecMiddlename(String aRecMiddlename) {
		theRecMiddlename = aRecMiddlename;
	}

	/** Источник финансирования по рецепту */
	@Comment("Источник финансирования по рецепту")
	@Required @Persist
	public String getRecFinSource() {
		return theRecFinSource;
	}

	public void setRecFinSource(String aRecFinSource) {
		theRecFinSource = aRecFinSource;
	}

	/** Торговое наименование лекарственного средства по рецепту */
	@Comment("Торговое наименование лекарственного средства по рецепту")
	@Required @Persist
	public String getRegDrugTradeName() {
		return theRegDrugTradeName;
	}

	public void setRegDrugTradeName(String aRegDrugTradeName) {
		theRegDrugTradeName = aRegDrugTradeName;
	}

	/** Дозировка лекарственного средства */
	@Comment("Дозировка лекарственного средства")
	@Required @Persist
	public String getRegDrugDoze() {
		return theRegDrugDoze;
	}

	public void setRegDrugDoze(String aRegDrugDoze) {
		theRegDrugDoze = aRegDrugDoze;
	}

	/** Количество ЛС по рецепту */
	@Comment("Количество ЛС по рецепту")
	@Required @Persist
	public String getRegDrugQuantity() {
		return theRegDrugQuantity;
	}

	public void setRegDrugQuantity(String aRegDrugQuantity) {
		theRegDrugQuantity = aRegDrugQuantity;
	}

	/** Количество ЛС по рецепту */
	private String theRegDrugQuantity;
	/** Дозировка лекарственного средства */
	private String theRegDrugDoze;
	/** Торговое наименование лекарственного средства по рецепту */
	private String theRegDrugTradeName;	
	/** Источник финансирования по рецепту */
	private String theRecFinSource;
	/** Отчество по рецепту */
	private String theRecMiddlename;
	/** Имя по рецепту */
	private String theRecFirstname;
	/** Фамилия по рецепту */
	private String theRecLastname;
	/** СНИЛС по рецепту */
	private String theRecSnils;
	/** Лекарственная форма */
	private Long theDrugForm;
	/** Решение КЭК */
	private boolean theKekDesicion;
	/** Процент оплаты */
	private Long thePayPercent;
	
	/** МКБ10 */
	private Long theIdc10;
	/** Тип льготы */
	private Long thePrivilegeCategory;
	/** Дата выписки */
	private String theRecipeDate;
	/** Номер рецепта */
	private String theRecipeNumber;
	/** Врач */
	private Long theDoctor;
	/** ЛПУ, которое выписало рецепт */
	private Long theLpu;
	/** Лекарство */
	private Long theDrugClassify;
}
