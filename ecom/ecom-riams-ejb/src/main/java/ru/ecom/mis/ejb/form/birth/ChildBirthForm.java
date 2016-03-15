package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.ChildBirth;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Роды
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz= ChildBirth.class)
@Comment("Роды")
@WebTrail(comment = "Роды", nameProperties= "id", view="entityParentView-preg_childBirth.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/ChildBirth")
public class ChildBirthForm extends IdEntityForm {
	
	/** Вес */
	@Comment("Вес")
	@Persist 
	public String getWeight() {return theWeight;}
	public void setWeight(String aBirthWeight) {theWeight = aBirthWeight;}
	
	/** Рост */
	@Comment("Рост")
	@Persist 
	public String getHeight() {return theHeight;}
	public void setHeight(String aHeight) {theHeight = aHeight;}
	
	/** Вес */
	private String theWeight;
	/** Рост */
	private String theHeight;

	/** Время начала потуг */
	@Comment("Время начала потуг")
	@Persist
	@TimeString @DoTimeString
	public String getTravailStartTime() {return theTravailStartTime;}
	public void setTravailStartTime(String aTravailStartTime) {theTravailStartTime = aTravailStartTime;}
	
	/** Дата начала потуг */
	@Comment("Дата начала потуг")
	@Persist
	@DateString @DoDateString
	public String getTravailStartDate() {return theTravailStartDate;}
	public void setTravailStartDate(String aTravailStartDate) {theTravailStartDate = aTravailStartDate;}

	/** Качество верхних вод */
	@Comment("Качество верхних вод")
	@Persist
	public String getUpperWatersQuality() {return theUpperWatersQuality;}
	public void setUpperWatersQuality(String aUpperWatersQuality) {theUpperWatersQuality = aUpperWatersQuality;}
	
	/** Качество нижних вод */
	@Comment("Качество нижних вод")
	@Persist
	public String getDownWatersQuality() {return theDownWatersQuality;}
	public void setDownWatersQuality(String aDownWatersQuality) {theDownWatersQuality = aDownWatersQuality;}

	/** Количество верхних вод (мл) */
	@Comment("Количество верхних вод (мл)")
	@Persist
	public Integer getUpperWatersAmount() {return theUpperWatersAmount;}
	public void setUpperWatersAmount(Integer aUpperWatersAmount) {theUpperWatersAmount = aUpperWatersAmount;}

	/** Количество нижних вод (мл) */
	@Comment("Количество нижних вод (мл)")
	@Persist
	public Integer getDownWatersAmount() {return theDownWatersAmount;}
	public void setDownWatersAmount(Integer aDownWatersAmount) {theDownWatersAmount = aDownWatersAmount;}
	
	/** Преждевременность отхождения вод */
	@Comment("Преждевременность отхождения вод")
	@Persist
	public Long getWatersPrematurity() {return theWatersPrematurity;}
	public void setWatersPrematurity(Long aWatersPrematurity) {theWatersPrematurity = aWatersPrematurity;}

	/** Время отхождения вод */
	@Comment("Время отхождения вод")
	@Persist
	@TimeString @DoTimeString
	public String getWatersTime() {return theWatersTime;}
	public void setWatersTime(String aWatersTime) {theWatersTime = aWatersTime;}

	/** Дата отхождения вод */
	@Comment("Дата отхождения вод")
	@Persist
	@DateString @DoDateString
	public String getWatersDate() {return theWatersDate;}
	public void setWatersDate(String aWatersDate) {theWatersDate = aWatersDate;}

	/** Время начала схваток */
	@Comment("Время начала схваток")
	@Persist
	@TimeString @DoTimeString
	public String getPangsStartTime() {return thePangsStartTime;}
	public void setPangsStartTime(String aPangsStartTime) {thePangsStartTime = aPangsStartTime;}
	
	/** Дата начала схваток */
	@Comment("Дата начала схваток")
	@Persist
	@DateString @DoDateString
	public String getPangsStartDate() {return thePangsStartDate;}
	public void setPangsStartDate(String aPangsStartDate) {thePangsStartDate = aPangsStartDate;}
	
	/** Отделение плаценты */
	@Comment("Отделение плаценты")
	@Persist
	public Long getPlacentaSeparation() {return thePlacentaSeparation;}
	public void setPlacentaSeparation(Long aPlacentaSeparation) {thePlacentaSeparation = aPlacentaSeparation;}
	
	/** Размеры плаценты */
	@Comment("Размеры плаценты")
	@Persist
	public String getPlacentaSize() {return thePlacentaSize;}
	public void setPlacentaSize(String aPlacentaSize) {thePlacentaSize = aPlacentaSize;}
	
	/** Целостность плаценты */
	@Comment("Целостность плаценты")
	@Persist
	public Long getPlacentaIntegrity() {return thePlacentaIntegrity;}
	public void setPlacentaIntegrity(Long aPlacentaIntegrity) {thePlacentaIntegrity = aPlacentaIntegrity;}
	
	/** Особенности плаценты */
	@Comment("Особенности плаценты")
	@Persist
	public String getPlacentaFeatures() {return thePlacentaFeatures;}
	public void setPlacentaFeatures(String aPlacentaFeatures) {thePlacentaFeatures = aPlacentaFeatures;}
	
	/** Масса плаценты (гр) */
	@Comment("Масса плаценты (гр)")
	@Persist
	public Integer getPlacentaWeight() {return thePlacentaWeight;}
	public void setPlacentaWeight(Integer aPlacentaWeight) {thePlacentaWeight = aPlacentaWeight;}
	
	/** Целостность оболочек */
	@Comment("Целостность оболочек")
	@Persist
	public Long getMembranesIntegrity() {return theMembranesIntegrity;}
	public void setMembranesIntegrity(Long aMembranesIntegrity) {theMembranesIntegrity = aMembranesIntegrity;}
	
	/** Место разрыва оболочек */
	@Comment("Место разрыва оболочек")
	@Persist
	public Long getMembranesBreakPlace() {return theMembranesBreakPlace;}
	public void setMembranesBreakPlace(Long aMembranesBreakPlace) {theMembranesBreakPlace = aMembranesBreakPlace;}
	
	/** Объем кровопотери (мл) */
	@Comment("Объем кровопотери (мл)")
	@Persist
	public Integer getHemorrhageVolume() {return theHemorrhageVolume;}
	public void setHemorrhageVolume(Integer aHemorrhageVolume) {theHemorrhageVolume = aHemorrhageVolume;}
	
	/** Продолжительность 1 периода (час) */
	@Comment("Продолжительность 1 периода (час)")
	@Persist
	public String getPeriod1Duration() {return thePeriod1Duration;}
	public void setPeriod1Duration(String aPeriod1Duration) {thePeriod1Duration = aPeriod1Duration;}
	
	/** Продолжительность 2 периода (час) */
	@Comment("Продолжительность 2 периода (час)")
	@Persist
	public String getPeriod2Duration() {return thePeriod2Duration;}
	public void setPeriod2Duration(String aPeriod2Duration) {thePeriod2Duration = aPeriod2Duration;}
	
	/** Продолжительность 3 периода (час) */
	@Comment("Продолжительность 3 периода (час)")
	@Persist
	public String getPeriod3Duration() {return thePeriod3Duration;}
	public void setPeriod3Duration(String aPeriod3Duration) {thePeriod3Duration = aPeriod3Duration;}
	
	/** Дата окончания родов */
	@Comment("Дата окончания родов")
	@Persist 
	@DateString @DoDateString
	public String getBirthFinishDate() {return theBirthFinishDate;}
	public void setBirthFinishDate(String aBirthFinishDate) {theBirthFinishDate = aBirthFinishDate;}
	
	/** Время окончания родов */
	@Comment("Время окончания родов")
	@Persist 
	@TimeString @DoTimeString
	public String getBirthFinishTime() {return theBirthFinishTime;}
	public void setBirthFinishTime(String aBirthFinishTime) {theBirthFinishTime = aBirthFinishTime;}
	
	/** Кто исследовал плаценту */
	@Comment("Кто исследовал плаценту")
	@Persist
	public Long getPlacentaInspector() {return thePlacentaInspector;}
	public void setPlacentaInspector(Long aPlacentaInspector) {thePlacentaInspector = aPlacentaInspector;}
	
	/** Состояние матери при выписке */
	@Comment("Состояние матери при выписке")
	@Persist
	public String getDischangeMotherCondition() {return theDischangeMotherCondition;}
	public void setDischangeMotherCondition(String aDischangeMotherCondition) {theDischangeMotherCondition = aDischangeMotherCondition;}
	
	/** Неправильный постнатальный период */
	@Comment("Неправильный постнатальный период")
	@Persist
	public Boolean getWrongPostNatalPeriod() {return theWrongPostNatalPeriod;}
	public void setWrongPostNatalPeriod(Boolean aWrongPostNatalPeriod) {theWrongPostNatalPeriod = aWrongPostNatalPeriod;}
	
	/** Трещины сосков */
	@Comment("Трещины сосков")
	@Persist
	public Boolean getMammillaChaps() {return theMammillaChaps;}
	public void setMammillaChaps(Boolean aMammillaChaps) {theMammillaChaps = aMammillaChaps;}
	
	/** Отек наружных половых органов */
	@Comment("Отек наружных половых органов")
	@Persist
	public String getVulvaEdema() {return theVulvaEdema;}
	public void setVulvaEdema(String aVulvaEdema) {theVulvaEdema = aVulvaEdema;}
	
	/** Отек промежности */
	@Comment("Отек промежности")
	@Persist
	public String getPerineumEdema() {return thePerineumEdema;}
	public void setPerineumEdema(String aPerineumEdema) {thePerineumEdema = aPerineumEdema;}
	
	/** Геморрой */
	@Comment("Геморрой")
	@Persist
	public String getHaemorrhoids() {return theHaemorrhoids;}
	public void setHaemorrhoids(String aHaemorrhoids) {theHaemorrhoids = aHaemorrhoids;}
	
	/** Послеродовая болезнь */
	@Comment("Послеродовая болезнь")
	@Persist
	public String getPostNatalDisease() {return thePostNatalDisease;}
	public void setPostNatalDisease(String aPostNatalDisease) {thePostNatalDisease = aPostNatalDisease;}
	
	/** Болезнь, не зависящая от родов */
	@Comment("Болезнь, не зависящая от родов")
	@Persist
	public String getNotPostNatalDisease() {return theNotPostNatalDisease;}
	public void setNotPostNatalDisease(String aNotPostNatalDisease) {theNotPostNatalDisease = aNotPostNatalDisease;}
	
	/** Повышение температуры без диагноза */
	@Comment("Повышение температуры без диагноза")
	@Persist
	public Long getFeverWithoutDiagnosis() {return theFeverWithoutDiagnosis;}
	public void setFeverWithoutDiagnosis(Long aFeverWithoutDiagnosis) {theFeverWithoutDiagnosis = aFeverWithoutDiagnosis;}
	
	/** Особенности лечения */
	@Comment("Особенности лечения")
	@Persist
	public String getTreatmentFeatures() {return theTreatmentFeatures;}
	public void setTreatmentFeatures(String aTreatmentFeatures) {theTreatmentFeatures = aTreatmentFeatures;}
	
	/** Направление плаценты на гистологию */
	@Comment("Направление плаценты на гистологию")
	@Persist
	public Boolean getPlacentaHistologyOrder() {return thePlacentaHistologyOrder;}
	public void setPlacentaHistologyOrder(Boolean aPlacentaHistologyOrder) {thePlacentaHistologyOrder = aPlacentaHistologyOrder;}
	
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Гистология плаценты */
	@Comment("Гистология плаценты")
	@Persist
	public Long getHistology() {return theHistology;}
	public void setHistology(Long aHistology) {theHistology = aHistology;}

	/** Гистология плаценты */
	private Long theHistology;
	/** СМО */
	private Long theMedCase;
	/** Время начала потуг */
	private String theTravailStartTime;
	/** Дата начала потуг */
	private String theTravailStartDate;
	/** Качество верхних вод */
	private String theUpperWatersQuality;
	/** Качество нижних вод */
	private String theDownWatersQuality;
	/** Количество верхних вод (мл) */
	private Integer theUpperWatersAmount;
	/** Количество нижних вод (мл) */
	private Integer theDownWatersAmount;
	/** Преждевременность отхождения вод */
	private Long theWatersPrematurity;
	/** Время отхождения вод */
	private String theWatersTime;
	/** Дата отхождения вод */
	private String theWatersDate;
	/** Время начала схваток */
	private String thePangsStartTime;
	/** Дата начала схваток */
	private String thePangsStartDate;
	/** Отделение плаценты */
	private Long thePlacentaSeparation;
	/** Размеры плаценты */
	private String thePlacentaSize;
	/** Целостность плаценты */
	private Long thePlacentaIntegrity;
	/** Особенности плаценты */
	private String thePlacentaFeatures;
	/** Масса плаценты (гр) */
	private Integer thePlacentaWeight;
	/** Целостность оболочек */
	private Long theMembranesIntegrity;
	/** Место разрыва оболочек */
	private Long theMembranesBreakPlace;
	/** Объем кровопотери (мл) */
	private Integer theHemorrhageVolume;
	/** Продолжительность 1 периода (час) */
	private String thePeriod1Duration;
	/** Продолжительность 2 периода (час) */
	private String thePeriod2Duration;
	/** Продолжительность 3 периода (час) */
	private String thePeriod3Duration;
	/** Дата окончания родов */
	private String theBirthFinishDate;
	/** Время окончания родов */
	private String theBirthFinishTime;
	/** Кто исследовал плаценту */
	private Long thePlacentaInspector;
	/** Состояние матери при выписке */
	private String theDischangeMotherCondition;
	/** Неправильный постнатальный период */
	private Boolean theWrongPostNatalPeriod;
	/** Трещины сосков */
	private Boolean theMammillaChaps;
	/** Отек наружных половых органов */
	private String theVulvaEdema;
	/** Отек промежности */
	private String thePerineumEdema;
	/** Геморрой */
	private String theHaemorrhoids;
	/** Послеродовая болезнь */
	private String thePostNatalDisease;
	/** Болезнь, не зависящая от родов */
	private String theNotPostNatalDisease;
	/** Повышение температуры без диагноза */
	private Long theFeverWithoutDiagnosis;
	/** Особенности лечения */
	private String theTreatmentFeatures;
	/** Направление плаценты на гистологию */
	private Boolean thePlacentaHistologyOrder;
	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return thePregnancy;}
	public void setPregnancy(Long aPregnancy) {thePregnancy = aPregnancy;}

	/** Беременность */
	private Long thePregnancy;
	/** Госпитализация */
	@Comment("Госпитализация")
	@Persist
	public Long getSls() {return theSls;}
	public void setSls(Long aSls) {theSls = aSls;}

	/** Госпитализация */
	private Long theSls;

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	/** pregnancy */
	@Comment("pregnancy")
	public PregnancyForm getPregnancyForm() {
		return thePregnancyForm;
	}

	public void setPregnancyForm(PregnancyForm aPregnancyForm) {
		thePregnancyForm = aPregnancyForm;
	}

	/** pregnancy */
	private PregnancyForm thePregnancyForm=new PregnancyForm();
	
	/** PregnanInspectionForm */
	@Comment("PregnanInspectionForm")
	public PregnanInspectionForm getPregnanInspectionForm() {
		return thePregnanInspectionForm;
	}

	public void setPregnanInspectionForm(PregnanInspectionForm aPregnanInspectionForm) {
		thePregnanInspectionForm = aPregnanInspectionForm;
	}

	/** PregnanInspectionForm */
	private PregnanInspectionForm thePregnanInspectionForm = new PregnanInspectionForm();
	
	/** PregnanExchangeCardForm */
	@Comment("PregnanExchangeCardForm")
	public PregnanExchangeCardForm getPregnanExchangeCardForm() {
		return thePregnanExchangeCardForm;
	}

	public void setPregnanExchangeCardForm(PregnanExchangeCardForm aPregnanExchangeCardForm) {
		thePregnanExchangeCardForm = aPregnanExchangeCardForm;
	}

	/** PregnanExchangeCardForm */
	private PregnanExchangeCardForm thePregnanExchangeCardForm = new PregnanExchangeCardForm();
	
	/** Новорожденный форма */
	@Comment("Новорожденный форма")
	public NewBornForm getNewBornForm() {
		return theNewBornForm;
	}

	public void setNewBornForm(NewBornForm aNewBornForm) {
		theNewBornForm = aNewBornForm;
	}

	/** Новорожденный форма */
	private NewBornForm theNewBornForm = new NewBornForm();
}
