package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.BedFund;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Коечный фонд
 * @author oegorova
 *
 */

@Comment("Коечный фонд")
@EntityForm
@EntityFormPersistance(clazz = BedFund.class)
@WebTrail(comment = "Коечный фонд", nameProperties = "id", view = "entityParentView-mis_bedFund.do")
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/BedFund")
public class BedFundForm extends IdEntityForm {

	/** Количество коек */
	@Comment("Количество коек")
	@Persist
	public String getAmount() {
		return theAmount;
	}

	public void setAmount(String aAmount) {
		theAmount = aAmount;
	}

	/** Количество коек */
	private String theAmount;
	
	/** Тип развертывания-свертывания */
	@Comment("Тип развертывания-свертывания")
	@Persist @Required
	public Long getReductionType() {
		return theReductionType;
	}

	public void setReductionType(Long aReductionType) {
		theReductionType = aReductionType;
	}

	/** Тип развертывания-свертывания */
	private Long theReductionType;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {
		return theServiceStream;
	}

	public void setServiceStream(Long aServiceStream) {
		theServiceStream = aServiceStream;
	}

	/** Поток обслуживания */
	private Long theServiceStream;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist @Required
	public Long getBedType() {
		return theBedType;
	}

	public void setBedType(Long aBedType) {
		theBedType = aBedType;
	}

	/** Профиль коек */
	private Long theBedType;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private Long theLpu;
	
	/** Для детей */
	@Comment("Для детей")
	@Persist
	public Boolean getForChild() {
		return theForChild;
	}

	public void setForChild(Boolean aForChild) {
		theForChild = aForChild;
	}

	/** Без питания */
	@Comment("Без питания")
	@Persist
	public Boolean getNoFood() {
		return theNoFood;
	}

	public void setNoFood(Boolean aNoFood) {
		theNoFood = aNoFood;
	}

	/** Без питания */
	private Boolean theNoFood;
	/** Для детей */
	private Boolean theForChild;

	/** Тип свертывания (текст) */
	@Comment("Тип свертывания (текст)")
	@Persist
	public String getReductionTypeName() {
		return theReductionTypeName;
	}

	public void setReductionTypeName(String aReductionTypeName) {
		theReductionTypeName = aReductionTypeName;
	}

	/** Тип свертывания (текст) */
	private String theReductionTypeName;
	
	/** Поток обслуживания (текст) */
	@Comment("Поток обслуживания (текст)")
	@Persist
	public String getServiceStreamName() {
		return theServiceStreamName;
	}

	public void setServiceStreamName(String aServiceStreamName) {
		theServiceStreamName = aServiceStreamName;
	}

	/** Поток обслуживания (текст) */
	private String theServiceStreamName;
	
	/** Профиль коек (текст) */
	@Comment("Профиль коек (текст)")
	@Persist
	public String getBedTypeName() {
		return theBedTypeName;
	}

	public void setBedTypeName(String aBedTypeName) {
		theBedTypeName = aBedTypeName;
	}

	/** Профиль коек (текст) */
	private String theBedTypeName;
	
	/** Тип госпитального обслуживания (текст) */
	@Comment("Тип госпитального обслуживания (текст)")
	@Persist
	public String getServiceTypeName() {
		return theServiceTypeName;
	}

	public void setServiceTypeName(String aServiceTypeName) {
		theServiceTypeName = aServiceTypeName;
	}

	/** Тип госпитального обслуживания (текст) */
	private String theServiceTypeName;
	
	/** Время окончания актуальности */
	@Comment("Время окончания актуальности")
	@Persist
	public String getVTE() {
		return theVTE;
	}

	public void setVTE(String aVTE) {
		theVTE = aVTE;
	}

	/** Время окончания актуальности */
	private String theVTE;
	
	/** Время начала актуальности */
	@Comment("Время начала актуальности")
	@Persist
	public String getVTS() {
		return theVTS;
	}

	public void setVTS(String aVTS) {
		theVTS = aVTS;
	}

	/** Время начала актуальности */
	private String theVTS;

	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	@Persist
	@Required
	@DateString @DoDateString
	public String getDateStart() {
		return theDateStart;
	}

	public void setDateStart(String aDateStart) {
		theDateStart = aDateStart;
	}

	/** Дата начала актуальности */
	private String theDateStart;

	/** Время начала актуальности */
	@Comment("Время начала актуальности")
	@Persist
	@Required
	@TimeString @DoTimeString
	public String getTimeStart() {
		return theTimeStart;
	}

	public void setTimeStart(String aTimeStart) {
		theTimeStart = aTimeStart;
	}

	/** Время начала актуальности */
	private String theTimeStart;
	
	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	@Persist
	@DateString @DoDateString
	public String getDateFinish() {
		return theDateFinish;
	}

	public void setDateFinish(String aDateFinish) {
		theDateFinish = aDateFinish;
	}

	/** Дата окончания актуальности */
	private String theDateFinish;
	
	/** Время окончания актуальности */
	@Comment("Время окончания актуальности")
	@Persist
	@TimeString @DoTimeString
	public String getTimeFinish() {
		return theTimeFinish;
	}

	public void setTimeFinish(String aTimeFinish) {
		theTimeFinish = aTimeFinish;
	}

	/** Время окончания актуальности */
	private String theTimeFinish;
	
	/** Тип койки */
	@Comment("Тип койки")
	@Persist @Required
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;	}

	/** Тип койки */
	private Long theBedSubType;
	
	/** Тип койки (текст) */
	@Comment("Тип койки (текст)")
	@Persist
	public String getBedSubTypeName() {return theBedSubTypeName;}
	public void setBedSubTypeName(String aBedSubTypeName) {theBedSubTypeName = aBedSubTypeName;}

	/** Тип койки (текст) */
	private String theBedSubTypeName;
	/** День выписки и день поступления считать разными днями */
	@Comment("День выписки и день поступления считать разными днями")
	@Persist
	public Boolean getAddCaseDuration() {return theAddCaseDuration;}
	public void setAddCaseDuration(Boolean aAddCaseDuration) {theAddCaseDuration = aAddCaseDuration;}

	/** День выписки и день поступления считать разными днями */
	private Boolean theAddCaseDuration;
	
	/** Реабилитационные */
	@Comment("Реабилитационные")
	@Persist
	public Boolean getIsRehab() {return theIsRehab;}
	public void setIsRehab(Boolean aIsRehab) {theIsRehab = aIsRehab;}

	/** Реабилитационные */
	private Boolean theIsRehab;
	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	@Comment("По умолчанию снилс врача генерации направлений для 263 приказа")
	@Persist
	public String getSnilsDoctorDirect263() {return theSnilsDoctorDirect263;}
	public void setSnilsDoctorDirect263(String aSnilsDoctorDirect263) {theSnilsDoctorDirect263 = aSnilsDoctorDirect263;}

	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	private String theSnilsDoctorDirect263;

}
