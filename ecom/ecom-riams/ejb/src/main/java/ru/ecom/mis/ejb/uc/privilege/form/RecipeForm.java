package ru.ecom.mis.ejb.uc.privilege.form;

import lombok.Setter;
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
@Subclasses(value = { PrivilegeRecipeForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/Person/Privilege/Recipe")
@Setter
public class RecipeForm extends IdEntityForm {
	
	/** Лекарство */
	@Comment("Лекарство")
	@Persist @Required
	public Long getDrugClassify() {
		return drugClassify;
	}

	/** ЛПУ, которое выписало рецепт */
	@Comment("ЛПУ, которое выписало рецепт")
	@Persist
	public Long getLpu() {
		return lpu;
	}

	/** Врач */
	@Comment("Врач")
	@Required @Persist
	public Long getDoctor() {
		return doctor;
	}

	/** Номер рецепта */
	@Comment("Номер рецепта")
	@Required @Persist
	public String getRecipeNumber() {
		return recipeNumber;
	}

	/** Дата выписки */
	@Comment("Дата выписки")
	@Required @Persist
	public String getRecipeDate() {
		return recipeDate;
	}

	/** Тип льготы */
	@Comment("Тип льготы")
	@Persist
	public Long getPrivilegeCategory() {
		return privilegeCategory;
	}

	/** МКБ10 */
	@Comment("МКБ10")
	@Persist
	public Long getIdc10() {
		return idc10;
	}

	/** Процент оплаты */
	@Comment("Процент оплаты")
	@Persist
	public Long getPayPercent() {
		return payPercent;
	}

	/** Решение КЭК */
	@Comment("Решение КЭК")
	@Persist
	public boolean getKekDesicion() {
		return kekDesicion;
	}

	/** Лекарственная форма */
	@Comment("Лекарственная форма")
	@Required @Persist
	public Long getDrugForm() {
		return drugForm;
	}

	/** СНИЛС по рецепту */
	@Comment("СНИЛС по рецепту")
	@Persist
	public String getRecSnils() {
		return recSnils;
	}

	/** Фамилия по рецепту */
	@Comment("Фамилия по рецепту")
	@Persist
	public String getRecLastname() {
		return recLastname;
	}

	/** Имя по рецепту */
	@Comment("Имя по рецепту")
	@Persist
	public String getRecFirstname() {
		return recFirstname;
	}

	/** Отчество по рецепту */
	@Comment("Отчество по рецепту")
	@Persist
	public String getRecMiddlename() {
		return recMiddlename;
	}

	/** Источник финансирования по рецепту */
	@Comment("Источник финансирования по рецепту")
	@Required @Persist
	public String getRecFinSource() {
		return recFinSource;
	}

	/** Торговое наименование лекарственного средства по рецепту */
	@Comment("Торговое наименование лекарственного средства по рецепту")
	@Required @Persist
	public String getRegDrugTradeName() {
		return regDrugTradeName;
	}

	/** Дозировка лекарственного средства */
	@Comment("Дозировка лекарственного средства")
	@Required @Persist
	public String getRegDrugDoze() {
		return regDrugDoze;
	}

	/** Количество ЛС по рецепту */
	@Comment("Количество ЛС по рецепту")
	@Required @Persist
	public String getRegDrugQuantity() {
		return regDrugQuantity;
	}

	/** Количество ЛС по рецепту */
	private String regDrugQuantity;
	/** Дозировка лекарственного средства */
	private String regDrugDoze;
	/** Торговое наименование лекарственного средства по рецепту */
	private String regDrugTradeName;	
	/** Источник финансирования по рецепту */
	private String recFinSource;
	/** Отчество по рецепту */
	private String recMiddlename;
	/** Имя по рецепту */
	private String recFirstname;
	/** Фамилия по рецепту */
	private String recLastname;
	/** СНИЛС по рецепту */
	private String recSnils;
	/** Лекарственная форма */
	private Long drugForm;
	/** Решение КЭК */
	private boolean kekDesicion;
	/** Процент оплаты */
	private Long payPercent;
	
	/** МКБ10 */
	private Long idc10;
	/** Тип льготы */
	private Long privilegeCategory;
	/** Дата выписки */
	private String recipeDate;
	/** Номер рецепта */
	private String recipeNumber;
	/** Врач */
	private Long doctor;
	/** ЛПУ, которое выписало рецепт */
	private Long lpu;
	/** Лекарство */
	private Long drugClassify;
}
