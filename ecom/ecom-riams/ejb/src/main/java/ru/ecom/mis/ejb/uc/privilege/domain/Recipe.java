package ru.ecom.mis.ejb.uc.privilege.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Рецепт
 */
@Entity
@Table(schema="SQLUser")
abstract public class Recipe extends BaseEntity {
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {
		return theWorkFunction;
	}

	public void setWorkFunction(WorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}

	/** Рабочая функция */
	private WorkFunction theWorkFunction;

	/** Номер рецепта */
	@Comment("Номер рецепта")
	public String getRecipeNumber() {
		return theRecipeNumber;
	}

	public void setRecipeNumber(String aRecipeNumber) {
		theRecipeNumber = aRecipeNumber;
	}

	/** Дата выписки */
	@Comment("Дата выписки")
	public Date getRecipeDate() {
		return theRecipeDate;
	}

	public void setRecipeDate(Date aRecipeDate) {
		theRecipeDate = aRecipeDate;
	}
	
	/** МКБ10 */
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {
		return theIdc10;
	}

	public void setIdc10(VocIdc10 aIdc10) {
		theIdc10 = aIdc10;
	}

	/** Лекарство */
	@Comment("Лекарство")
	@OneToOne
	public VocDrugClassify getDrugClassify() {
		return theDrugClassify;
	}

	public void setDrugClassify(VocDrugClassify aDrugClassify) {
		theDrugClassify = aDrugClassify;
	}

	/** Количество ЛС по рецепту */
	@Comment("Количество ЛС по рецепту")
	public BigDecimal getDrugQuantity() {
		return theDrugQuantity;
	}

	public void setDrugQuantity(BigDecimal aDrugQuantity) {
		theDrugQuantity = aDrugQuantity;
	}
	
	/** Количество лекарства на прием */
	@Comment("Количество лекарства на прием")
	public BigDecimal getTakingDoseAmount() {
		return theTakingDoseAmount;
	}
	public void setTakingDoseAmount(BigDecimal aTakingDoseAmount) {
		theTakingDoseAmount = aTakingDoseAmount;
	}
	
	/** Количество приемов в день */
	@Comment("Количество приемов в день")
	public Integer getDayTakingAmount() {
		return theDayTakingAmount;
	}
	public void setDayTakingAmount(Integer aDayTakingAmount) {
		theDayTakingAmount = aDayTakingAmount;
	}

	/** Длительность приема */
	@Comment("Длительность приема")
	public String getUseDuration() {
		return theUseDuration;
	}
	public void setUseDuration(String aUseDuration) {
		theUseDuration = aUseDuration;
	}
	
	/** Процент оплаты */
	@Comment("Процент оплаты")
	public Long getPayPercent() {return thePayPercent;}
	public void setPayPercent(Long aPaymentPercent) {thePayPercent = aPaymentPercent;}
	
	/** Процент оплаты */
	private Long thePayPercent;
	/** Длительность приема */
	private String theUseDuration;
		/** Количество приемов в день */
	private Integer theDayTakingAmount;
	/** Количество лекарства на прием */
	private BigDecimal theTakingDoseAmount;
	/** Статус */
//	private VocRecipeStatus theStatus;
	/** Количество ЛС по рецепту */
	private BigDecimal theDrugQuantity;
	/** МКБ10 */
	private VocIdc10 theIdc10;
	/** Дата выписки */
	private Date theRecipeDate;
	/** Номер рецепта */
	private String theRecipeNumber;
	/** Лекарство */
	private VocDrugClassify theDrugClassify;
}
