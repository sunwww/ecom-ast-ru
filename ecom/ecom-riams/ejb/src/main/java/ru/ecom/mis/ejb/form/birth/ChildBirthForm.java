package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.birth.ChildBirth;
import ru.ecom.mis.ejb.form.birth.interceptors.ChildBirthPreCreateInterceptor;
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
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ChildBirthPreCreateInterceptor.class)
)
@Setter
public class ChildBirthForm extends IdEntityForm {
	
	/** Вес */
	@Comment("Вес")
	@Persist 
	public String getWeight() {return weight;}

	/** Рост */
	@Comment("Рост")
	@Persist 
	public String getHeight() {return height;}

	/** Вес */
	private String weight;
	/** Рост */
	private String height;

	/** Время начала потуг */
	@Comment("Время начала потуг")
	@Persist 
	@TimeString @DoTimeString
	public String getTravailStartTime() {return travailStartTime;}

	/** Дата начала потуг */
	@Comment("Дата начала потуг")
	@Persist 
	@DateString @DoDateString
	public String getTravailStartDate() {return travailStartDate;}

	/** Качество верхних вод */
	@Comment("Качество верхних вод")
	@Persist
	public String getUpperWatersQuality() {return upperWatersQuality;}

	/** Качество нижних вод */
	@Comment("Качество нижних вод")
	@Persist
	public String getDownWatersQuality() {return downWatersQuality;}

	/** Количество верхних вод (мл) */
	@Comment("Количество верхних вод (мл)")
	@Persist
	public Integer getUpperWatersAmount() {return upperWatersAmount;}

	/** Количество нижних вод (мл) */
	@Comment("Количество нижних вод (мл)")
	@Persist
	public Integer getDownWatersAmount() {return downWatersAmount;}

	/** Преждевременность отхождения вод */
	@Comment("Преждевременность отхождения вод")
	@Persist
	@Required
	public Long getWatersPrematurity() {return watersPrematurity;}

	/** Время отхождения вод */
	@Comment("Время отхождения вод")
	@Persist 
	@TimeString @DoTimeString
	public String getWatersTime() {return watersTime;}

	/** Дата отхождения вод */
	@Comment("Дата отхождения вод")
	@Persist 
	@DateString @DoDateString
	public String getWatersDate() {return watersDate;}

	/** Время начала схваток */
	@Comment("Время начала схваток")
	@Persist
	@TimeString @DoTimeString
	@Required
	public String getPangsStartTime() {return pangsStartTime;}

	/** Дата начала схваток */
	@Comment("Дата начала схваток")
	@Persist
	@DateString @DoDateString
	@Required
	public String getPangsStartDate() {return pangsStartDate;}

	/** Отделение плаценты */
	@Comment("Отделение плаценты")
	@Persist
	public Long getPlacentaSeparation() {return placentaSeparation;}

	/** Размеры плаценты */
	@Comment("Размеры плаценты")
	@Persist
	public String getPlacentaSize() {return placentaSize;}

	/** Целостность плаценты */
	@Comment("Целостность плаценты")
	@Persist
	public Long getPlacentaIntegrity() {return placentaIntegrity;}

	/** Особенности плаценты */
	@Comment("Особенности плаценты")
	@Persist
	public String getPlacentaFeatures() {return placentaFeatures;}

	/** Масса плаценты (гр) */
	@Comment("Масса плаценты (гр)")
	@Persist
	public Integer getPlacentaWeight() {return placentaWeight;}

	/** Целостность оболочек */
	@Comment("Целостность оболочек")
	@Persist
	public Long getMembranesIntegrity() {return membranesIntegrity;}

	/** Место разрыва оболочек */
	@Comment("Место разрыва оболочек")
	@Persist
	public Long getMembranesBreakPlace() {return membranesBreakPlace;}

	/** Объем кровопотери (мл) */
	@Comment("Объем кровопотери (мл)")
	@Persist
	public Integer getHemorrhageVolume() {return hemorrhageVolume;}

	/** Продолжительность 1 периода (час) */
	@Comment("Продолжительность 1 периода (час)")
	@Persist
	public String getPeriod1Duration() {return period1Duration;}

	/** Продолжительность 2 периода (час) */
	@Comment("Продолжительность 2 периода (час)")
	@Persist
	public String getPeriod2Duration() {return period2Duration;}

	/** Продолжительность 3 периода (час) */
	@Comment("Продолжительность 3 периода (час)")
	@Persist
	public String getPeriod3Duration() {return period3Duration;}

	/** Дата окончания родов */
	@Comment("Дата окончания родов")
	@Persist 
	@DateString @DoDateString @Required
	public String getBirthFinishDate() {return birthFinishDate;}

	/** Время окончания родов */
	@Comment("Время окончания родов")
	@Persist 
	@TimeString @DoTimeString @Required
	public String getBirthFinishTime() {return birthFinishTime;}

	/** Кто исследовал плаценту */
	@Comment("Кто исследовал плаценту")
	@Persist
	public Long getPlacentaInspector() {return placentaInspector;}

	/** Состояние матери при выписке */
	@Comment("Состояние матери при выписке")
	@Persist
	public String getDischangeMotherCondition() {return dischangeMotherCondition;}

	/** Неправильный постнатальный период */
	@Comment("Неправильный постнатальный период")
	@Persist
	public Boolean getWrongPostNatalPeriod() {return wrongPostNatalPeriod;}

	/** Трещины сосков */
	@Comment("Трещины сосков")
	@Persist
	public Boolean getMammillaChaps() {return mammillaChaps;}

	/** Отек наружных половых органов */
	@Comment("Отек наружных половых органов")
	@Persist
	public String getVulvaEdema() {return vulvaEdema;}

	/** Отек промежности */
	@Comment("Отек промежности")
	@Persist
	public String getPerineumEdema() {return perineumEdema;}

	/** Геморрой */
	@Comment("Геморрой")
	@Persist
	public String getHaemorrhoids() {return haemorrhoids;}

	/** Послеродовая болезнь */
	@Comment("Послеродовая болезнь")
	@Persist
	public String getPostNatalDisease() {return postNatalDisease;}

	/** Болезнь, не зависящая от родов */
	@Comment("Болезнь, не зависящая от родов")
	@Persist
	public String getNotPostNatalDisease() {return notPostNatalDisease;}

	/** Повышение температуры без диагноза */
	@Comment("Повышение температуры без диагноза")
	@Persist
	public Long getFeverWithoutDiagnosis() {return feverWithoutDiagnosis;}

	/** Особенности лечения */
	@Comment("Особенности лечения")
	@Persist
	public String getTreatmentFeatures() {return treatmentFeatures;}

	/** Направление плаценты на гистологию */
	@Comment("Направление плаценты на гистологию")
	@Persist
	public Boolean getPlacentaHistologyOrder() {return placentaHistologyOrder;}

	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Гистология плаценты */
	@Comment("Гистология плаценты")
	@Persist
	public Long getHistology() {return histology;}

	/** Гистология плаценты */
	private Long histology;
	/** СМО */
	private Long medCase;
	/** Время начала потуг */
	private String travailStartTime;
	/** Дата начала потуг */
	private String travailStartDate;
	/** Качество верхних вод */
	private String upperWatersQuality;
	/** Качество нижних вод */
	private String downWatersQuality;
	/** Количество верхних вод (мл) */
	private Integer upperWatersAmount;
	/** Количество нижних вод (мл) */
	private Integer downWatersAmount;
	/** Преждевременность отхождения вод */
	private Long watersPrematurity;
	/** Время отхождения вод */
	private String watersTime;
	/** Дата отхождения вод */
	private String watersDate;
	/** Время начала схваток */
	private String pangsStartTime;
	/** Дата начала схваток */
	private String pangsStartDate;
	/** Отделение плаценты */
	private Long placentaSeparation;
	/** Размеры плаценты */
	private String placentaSize;
	/** Целостность плаценты */
	private Long placentaIntegrity;
	/** Особенности плаценты */
	private String placentaFeatures;
	/** Масса плаценты (гр) */
	private Integer placentaWeight;
	/** Целостность оболочек */
	private Long membranesIntegrity;
	/** Место разрыва оболочек */
	private Long membranesBreakPlace;
	/** Объем кровопотери (мл) */
	private Integer hemorrhageVolume;
	/** Продолжительность 1 периода (час) */
	private String period1Duration;
	/** Продолжительность 2 периода (час) */
	private String period2Duration;
	/** Продолжительность 3 периода (час) */
	private String period3Duration;
	/** Дата окончания родов */
	private String birthFinishDate;
	/** Время окончания родов */
	private String birthFinishTime;
	/** Кто исследовал плаценту */
	private Long placentaInspector;
	/** Состояние матери при выписке */
	private String dischangeMotherCondition;
	/** Неправильный постнатальный период */
	private Boolean wrongPostNatalPeriod;
	/** Трещины сосков */
	private Boolean mammillaChaps;
	/** Отек наружных половых органов */
	private String vulvaEdema;
	/** Отек промежности */
	private String perineumEdema;
	/** Геморрой */
	private String haemorrhoids;
	/** Послеродовая болезнь */
	private String postNatalDisease;
	/** Болезнь, не зависящая от родов */
	private String notPostNatalDisease;
	/** Повышение температуры без диагноза */
	private Long feverWithoutDiagnosis;
	/** Особенности лечения */
	private String treatmentFeatures;
	/** Направление плаценты на гистологию */
	private Boolean placentaHistologyOrder;
	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return pregnancy;}

	/** Беременность */
	private Long pregnancy;
	/** Госпитализация */
	@Comment("Госпитализация")
	@Persist
	public Long getSls() {return sls;}

	/** Госпитализация */
	private Long sls;

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактирования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	/** pregnancy */
	@Comment("pregnancy")
	public PregnancyForm getPregnancyForm() {
		return pregnancyForm;
	}


	/** pregnancy */
	private PregnancyForm pregnancyForm=new PregnancyForm();
	
	/** PregnanInspectionForm */
	@Comment("PregnanInspectionForm")
	public PregnanInspectionForm getPregnanInspectionForm() {
		return pregnanInspectionForm;
	}


	/** PregnanInspectionForm */
	private PregnanInspectionForm pregnanInspectionForm = new PregnanInspectionForm();
	
	/** PregnanExchangeCardForm */
	@Comment("PregnanExchangeCardForm")
	public PregnanExchangeCardForm getPregnanExchangeCardForm() {
		return pregnanExchangeCardForm;
	}


	/** PregnanExchangeCardForm */
	private PregnanExchangeCardForm pregnanExchangeCardForm = new PregnanExchangeCardForm();
	
	/** Кол-во плодов */
	@Comment("Кол-во плодов")
	@Required
	public Long getNewBornAmount() {return newBornAmount;}

	/** Кол-во плодов */
	private Long newBornAmount;
	
	/** Роды тип (кесарево, самостоятельные) */
	@Comment("Роды тип (кесарево, самостоятельные)")
	@Persist @Required
	public Long getChildBirthType() {
		return childBirthType;
	}

	/** Роды тип (кесарево, самостоятельные) */
	private Long childBirthType;
	
	/** Где произошли роды */
	@Comment("Где произошли роды")
	@Persist @Required
	public Long getWhereBirthOccurred() {return whereBirthOccurred;}

	/** Другое место родов */
	@Comment("Другое место родов")
	@Persist
	public String getWhereBirthOccurredOther() {return whereBirthOccurredOther;}

	/** Другое место родов */
	private String whereBirthOccurredOther;
	/** Где произошли роды */
	private Long whereBirthOccurred;
	
	/** Срок беременности (нед) */
	@Comment("Срок беременности (нед)")
	@Persist @Required
	public String getDurationPregnancy() {return durationPregnancy;}

	/** Срок беременности (нед) */
	private String durationPregnancy;
	/** Послед выделился через (мин) */
	@Comment("Послед выделился через (мин)")
	@Persist
	public Long getPlacentaMinute() {return placentaMinute;}

	/** Послед выделился через (мин) */
	private Long placentaMinute;
	
	

	/** Ребенка принял */
	@Comment("Ребенка принял")
	@Persist
	public Long getChildTook() {return childTook;}

	/** Ребенка принял */
	private Long childTook;
	
	/** Длина пуповины */
	@Comment("Длина пуповины")
	@Persist
	@Required
	public String getCordLength() {return cordLength;}

	/** Длина пуповины */
	private String cordLength;
	
	/** Медикаментозное обезболивание */
	@Comment("Медикаментозное обезболивание")
	@Persist
	public Long getAnesthesiaMedication() {return anesthesiaMedication;}

	/** Медикаментозное обезболивание */
	private Long anesthesiaMedication;
	
	/** Эффект от медикаментозного обезболивания */
	@Comment("Эффект от медикаментозного обезболивания")
	@Persist
	public Long getAnesthesiaMedicationEffect() {return anesthesiaMedicationEffect;}

	/** Эффект от медикаментозного обезболивания */
	private Long anesthesiaMedicationEffect;
	
	/** список новорожденных */
	@Comment("список новорожденных")
	public String getNewBornsInfo() {return newBornsInfo;}

	/** список новорожденных */
	private String newBornsInfo;
	
	/** Дата полного открытия */
	@Comment("Дата полного открытия")
	@Persist @DateString @DoDateString 
	public String getFullOpenDate() {return fullOpenDate;}

	/** Дата полного открытия */
	private String fullOpenDate;
	/** Время полного открытия */
	@Comment("Время полного открытия")
	@Persist @TimeString @DoTimeString 
	public String getFullOpenTime() {return fullOpenTime;}

	/** Время полного открытия */
	private String fullOpenTime;
	
	/** Показания */
	@Comment("Показания")
	@Persist @Required
	public Long getEmergency() {return emergency;}

	/** Показания */
	private Long emergency;

	/** Паритет родов */
	@Comment("Паритет родов")
	@Persist @Required
	public Long getParitet() {return paritet;}

	/** Паритет родов */
	private Long paritet;

	/** ЭКО? */
	@Comment("ЭКО?")
	@Persist
	public Boolean getIsECO() {return isECO;}

	/** ЭКО? */
	private Boolean isECO;

	/** Состояла на учёте в ЖК? */
	@Comment("Состояла на учёте в ЖК?")
	@Persist
	public Boolean getIsRegisteredWithWomenConsultation() {return isRegisteredWithWomenConsultation;}

	/** Состояла на учёте в ЖК? */
	private Boolean isRegisteredWithWomenConsultation;

	/** Паритет беременностей*/
	@Comment("Паритет беременностей")
	@Persist @Required
	public Long getParitetPregn() {return paritetPregn;}

	/** Паритет беременностей */
	private Long paritetPregn;

	/** Классификация Робсона */
	@Comment("Классификация Робсона")
	public Long getRobsonClass() {return robsonClass;}
	/** Классификация Робсона */
	private Long robsonClass;

	/** Подгруппа классификации */
	@Comment("Подгруппа классификации")
	public Long getRobsonSub() {return robsonSub;}
	/** Подгруппа классификации */
	private Long robsonSub;


	/** Длительность безводного периода (часы)*/
	@Comment("Длительность безводного периода (часы)")
	@Persist
	public Long getWaterlessDurationHour() {return waterlessDurationHour;}
	/** Длительность безводного периода (часы)*/
	private Long waterlessDurationHour;

	/** Длительность безводного периода (минуты)*/
	@Comment("Длительность безводного периода (минуты)")
	@Persist
	public Long getWaterlessDurationMin() {return waterlessDurationMin;}
	/** Длительность безводного периода (минуты)*/
	private Long waterlessDurationMin;

	/** Диабет (браслет)*/
	@Comment("Диабет (браслет)")
	@Persist
	public Long getDiabetIdentity() {return diabetIdentity;}
	/** Диабет (браслет)*/
	private Long diabetIdentity;

	/** Женская консультация */
	@Comment("Женская консультация")
	@Persist
	public Long getWomenConsult() {return womenConsult;}

	/** Женская консультация */
	private Long womenConsult;
}
