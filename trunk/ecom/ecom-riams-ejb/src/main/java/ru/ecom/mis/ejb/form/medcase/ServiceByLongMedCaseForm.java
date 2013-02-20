package ru.ecom.mis.ejb.form.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
/**
 * Для создание визита по мед.услуге
 * @author stkacheva
 *
 */
public class ServiceByLongMedCaseForm extends ServiceMedCaseForm {
	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	@Persist
	public Integer getPrivilegeRecipeAmount() {return thePrivilegeRecipeAmount;	}
	public void setPrivilegeRecipeAmount(Integer aPrivilegeRecipeAmount) {thePrivilegeRecipeAmount = aPrivilegeRecipeAmount;	}

	/** Внешний направитель (ЛПУ) */
	@Comment("Внешний направитель (ЛПУ)")
	@Persist
	public Long getOrderLpu() {return theOrderLpu;}
	public void setOrderLpu(Long aOrderLpu) {theOrderLpu = aOrderLpu;}



	/** Результат визита */
	@Comment("Результат визита")
	@Persist
	public Long getVisitResult() {return theVisitResult;	}
	public void setVisitResult(Long aResult) {theVisitResult = aResult;}

	/** Цель визита */
	@Comment("Цель визита")
	@Persist
	public Long getVisitReason() {return theVisitReason;	}
	public void setVisitReason(Long aReason) {theVisitReason = aReason;	}

	/** Тип рабочего места обслуживания */
	@Comment("Тип рабочего места обслуживания")
	@Persist
	public Long getWorkPlaceType() {return theWorkPlaceType;}
	public void setWorkPlaceType(Long aWorkPlaceType) {theWorkPlaceType = aWorkPlaceType;	}

	/** Рабочая функция направителя */
	@Comment("Рабочая функция направителя")
	@Persist
	public Long getOrderWorkFunction() {return theOrderWorkFunction;}
	public void setOrderWorkFunction(Long aNewProperty) {theOrderWorkFunction = aNewProperty;}

	/** Диспансерный учет * */
	@Comment("Диспансерный учет")
    @Persist
    public Long getDispRegistration() {return theDispRegistration;}
    public void setDispRegistration(Long aVocDispanseryRegistration) {theDispRegistration = aVocDispanseryRegistration;}
    private Long theDispRegistration;

	/** Рабочая функция направителя */
	private Long theOrderWorkFunction;
	/** Тип рабочего места обслуживания */
	private Long theWorkPlaceType;
	/** Цель визита */
	private Long theVisitReason;
	/** Результат визита */
	private Long theVisitResult;
	/** Внешний направитель (ЛПУ) */
	private Long theOrderLpu;
	/** Количество выписанных льготных рецептов */
	private Integer thePrivilegeRecipeAmount;

}
