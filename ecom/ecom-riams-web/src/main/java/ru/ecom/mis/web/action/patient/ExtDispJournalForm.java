package ru.ecom.mis.web.action.patient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class ExtDispJournalForm extends BaseValidatorForm {

	/** Дата начала периода */
	@Comment("Дата начала периода")
	@DateString @DoDateString @Required
	public String getBeginDate() {return theBeginDate;}
	public void setBeginDate(String aBeginDate) {theBeginDate = aBeginDate;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	@DateString @DoDateString @Required
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}

	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;
	/** Рабочая функция */
	private Long theWorkFunction;
	/** Дата окончания периода */
	private String theFinishDate;
	/** Дата начала периода */
	private String theBeginDate;
	
	/** Социальный статус */
	@Comment("Социальный статус")
	public Long getSocialStatus() {return theSocialStatus;}
	public void setSocialStatus(Long aSocialStatus) {theSocialStatus = aSocialStatus;}

	/** Социальный статус */
	private Long theSocialStatus;
	
	/** Тип диспасеризации */
	@Comment("Тип диспасеризации")
	public Long getDispType() {return theDispType;}
	public void setDispType(Long aDispType) {theDispType = aDispType;}

	/** Тип диспасеризации */
	private Long theDispType;
	
	/** Возрасная категория */
	@Comment("Возрасная категория")
	public Long getAgeGroup() {return theAgeGroup;}
	public void setAgeGroup(Long aAgeGroup) {theAgeGroup = aAgeGroup;}

	/** Возрасная категория */
	private Long theAgeGroup;
	
	/** Группа здоровья */
	@Comment("Группа здоровья")
	public Long getHealthGroup() {return theHealthGroup;}
	public void setHealthGroup(Long aHealthGroup) {theHealthGroup = aHealthGroup;}

	/** Фактор риска */
	@Comment("Фактор риска")
	public Long getRisk() {return theRisk;}
	public void setRisk(Long aRisk) {theRisk = aRisk;}

	/** Фактор риска */
	private Long theRisk;
	/** Группа здоровья */
	private Long theHealthGroup;
	
	/** Услуга */
	@Comment("Услуга")
	public Long getService() {return theService;}
	public void setService(Long aService) {theService = aService;}

	/** Услуга */
	private Long theService;
	/** dateBeginYear */
	@Comment("dateBeginYear")
	public String getDateBeginYear() {return theDateBeginYear;}
	public void setDateBeginYear(String aDateBeginYear) {theDateBeginYear = aDateBeginYear;}

	/** dateBeginYear */
	private String theDateBeginYear;
	
	/** Файл для экспорта */
	@Comment("Файл для экспорта")
	public String getFilename() {
		return theFilename;
	}

	public void setFilename(String aFilename) {
		theFilename = aFilename;
	}

	/** Файл для экспорта */
	private String theFilename;

/** Группа для занятия физ. культурой (для экспорта) */
@Comment("Группа для занятия физ. культурой (для экспорта)")
public int getExpFizGroup() {
	return theExpFizGroup;
}

public void setExpFizGroup(int aExpFizGroup) {
	theExpFizGroup = aExpFizGroup;
}

/** Группа для занятия физ. культурой (для экспорта) */
private int theExpFizGroup;
	
	/** Рост (в см) (для экспорта) */
	@Comment("Рост (в см) (для экспорта)")
	public int getExpHeight() {
		return theExpHeight;
	}

	public void setExpHeight(int aExpHeight) {
		theExpHeight = aExpHeight;
	}

	/** Рост (в см) (для экспорта) */
	private int theExpHeight;
	
	/** Вес (в кг) (для экспорта) */
	@Comment("Вес (в кг) (для экспорта)")
	public int getExpWeight() {
		return theExpWeight;
	}

	public void setExpWeight(int aExpWeight) {
		theExpWeight = aExpWeight;
	}

	/** Вес (в кг) (для экспорта) */
	private int theExpWeight;
	
	/** Окружность головы (в см) (для экспорта) */
	@Comment("Окружность головы (в см) (для экспорта)")
	public int getExpHeadsize() {
		return theExpHeadsize;
	}

	public void setExpHeadsize(int aExpHeadsize) {
		theExpHeadsize = aExpHeadsize;
	}

	/** Окружность головы (в см) (для экспорта) */
	private int theExpHeadsize;
	
	/** Результат анализов (для экспорта) */
	@Comment("Результат анализов (для экспорта)")
	public String getExpResearchText() {
		return theExpResearchText;
	}

	public void setExpResearchText(String aExpResearchText) {
		theExpResearchText = aExpResearchText;
	}

	/** Результат анализов (для экспорта) */
	private String theExpResearchText;
	
	/** Рекомендации ЗОЖ (для экспорта) */
	@Comment("Рекомендации ЗОЖ (для экспорта)")
	public String getExpZOJRecommend() {
		return theExpZOJRecommend;
	}

	public void setExpZOJRecommend(String aExpZOJRecommend) {
		theExpZOJRecommend = aExpZOJRecommend;
	}

	/** Рекомендации ЗОЖ (для экспорта) */
	private String theExpZOJRecommend;
	
/** Рекомендации по дисп. наблюдению, лечению (для экспорта) */
@Comment("Рекомендации по дисп. наблюдению, лечению (для экспорта)")
public String getExpRecommend() {
	return theExpRecommend;
}

public void setExpRecommend(String aExpRecommend) {
	theExpRecommend = aExpRecommend;
}

/** Рекомендации по дисп. наблюдению, лечению (для экспорта) */
private String theExpRecommend;
}
