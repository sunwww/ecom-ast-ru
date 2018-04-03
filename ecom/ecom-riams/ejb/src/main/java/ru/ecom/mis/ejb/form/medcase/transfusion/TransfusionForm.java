package ru.ecom.mis.ejb.form.medcase.transfusion;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Transfusion;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Форма "Переливание трансфузионных сред"
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz= Transfusion.class)
@Comment("Переливание трансфузионных сред")
@WebTrail(comment = "Переливание трансфузионных сред", nameProperties= "id", view="entitySubclassView-trans_transfusion.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion")
@Subclasses({BloodTransfusionForm.class, OtherTransfusionForm.class})
public class TransfusionForm extends IdEntityForm {
	/** Дата начала */
	@Comment("Дата начала")
	@DateString @DoDateString @Persist @Required
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}

	
	/** Первичное */
	@Comment("Первичное")
	@Persist
	public Boolean getPrimaryCase() {return thePrimaryCase;}
	public void setPrimaryCase(Boolean aPrimaryCase) {thePrimaryCase = aPrimaryCase;}

	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Показания к применению */
	@Comment("Показания к применению")
	@Persist @Required
	public Long getReason() {return theReason;}
	public void setReason(Long aReason) {theReason = aReason;}

	/** Доза (мл) */
	@Comment("Доза (мл)")
	@Persist @Required
	public Integer getDoze() {return theDoze;}
	public void setDoze(Integer aDoze) {theDoze = aDoze;}

	/** Серия */
	@Comment("Серия")
	@Persist @DoUpperCase
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}
	
	/** Дата приготовления */
	@Comment("Дата приготовления")
	@Persist @DateString @DoDateString @Required
	public String getPreparationDate() {return thePreparationDate;}
	public void setPreparationDate(String aPreparationDate) {thePreparationDate = aPreparationDate;}

	/** Изготовитель */
	@Comment("Изготовитель")
	@Persist @DoUpperCase @Required
	public Long getPreparator() {return thePreparator;}
	public void setPreparator(Long aPreparator) {thePreparator = aPreparator;}

	/** Способ переливания */
	@Comment("Способ переливания")
	@Persist @Required
	public Long getTransfusionMethod() {return theTransfusionMethod;}
	public void setTransfusionMethod(Long aTransfusionMethod) {theTransfusionMethod = aTransfusionMethod;}

	/** Исполнитель */
	@Comment("Исполнитель")
	@Persist @Required
	public Long getExecutor() {return theExecutor;}
	public void setExecutor(Long aExecutor) {theExecutor = aExecutor;}

	/** Трансфузионная реакция */
	@Comment("Трансфузионная реакция")
	@Persist
	public Long getTransfusionReaction() {return theTransfusionReaction;}
	public void setTransfusionReaction(Long aTransfusionReaction) {theTransfusionReaction = aTransfusionReaction;}

	/** Осложнения после переливания */
	@Comment("Осложнения после переливания")
	public String getComplications() {return theComplications;}
	public void setComplications(String aComplications) {theComplications = aComplications;}

	/** Номер в журнале */
	@Comment("Номер в журнале")
	@Persist
	public Integer getJournalNumber() {return theJournalNumber;}
	public void setJournalNumber(Integer aJournalNumber) {theJournalNumber = aJournalNumber;}

	/** Дата начала */
	private String theStartDate;
	/** Первичное */
	private Boolean thePrimaryCase;
	/** СМО */
	private Long theMedCase;
	/** Показания к применению */
	private Long theReason;
	/** Доза (мл) */
	private Integer theDoze;
	/** Серия */
	private String theSeries;
	/** Дата приготовления */
	private String thePreparationDate;
	/** Изготовитель */
	private Long thePreparator;
	/** Способ переливания */
	private Long theTransfusionMethod;
	/** Исполнитель */
	private Long theExecutor;
	/** Трансфузионная реакция */
	private Long theTransfusionReaction;
	/** Осложнения после переливания */
	private String theComplications;
	/** Номер в журнале */
	private Integer theJournalNumber;	
	
	/** Время переливание с */
	@Comment("Время переливание с")
	@TimeString @DoTimeString @Persist @Required
	public String getTimeFrom() {return theTimeFrom;}
	public void setTimeFrom(String aTimeFrom) {theTimeFrom = aTimeFrom;}

	/** Время переливания по */
	@Comment("Время переливания по")
	@TimeString @DoTimeString @Persist @Required
	public String getTimeTo() {return theTimeTo;}
	public void setTimeTo(String aTimeTo) {theTimeTo = aTimeTo;}

	/** Время переливания по */
	private String theTimeTo;
	/** Время переливание с */
	private String theTimeFrom;
	
	/** Фенотип */
	@Comment("Фенотип")
	@Persist
	public String getPhenotype() {return thePhenotype;}
	public void setPhenotype(String aPhenotype) {thePhenotype = aPhenotype;}

	/** Фенотип C */
	@Comment("Фенотип C")
	@Persist
	public Boolean getPhenotypeC() {return thePhenotypeC;}
	public void setPhenotypeC(Boolean aPhenotypeC) {thePhenotypeC = aPhenotypeC;}

	/** Фенотип с */
	@Comment("Фенотип с")
	@Persist
	public Boolean getPhenotypec1() {return thePhenotypec1;}
	public void setPhenotypec1(Boolean aPhenotypec1) {thePhenotypec1 = aPhenotypec1;}

	/** Фенотип Е */
	@Comment("Фенотип Е")
	@Persist
	public Boolean getPhenotypeD() {return thePhenotypeD;}
	public void setPhenotypeD(Boolean aPhenotypeD) {thePhenotypeD = aPhenotypeD;}

	/** Фенотип e */
	@Comment("Фенотип e")
	@Persist
	public Boolean getPhenotypeE() {return thePhenotypeE;}
	public void setPhenotypeE(Boolean aPhenotypeE) {thePhenotypeE = aPhenotypeE;}

	/** Фенотип E */
	@Comment("Фенотип E")
	@Persist
	public Boolean getPhenotypee1() {return thePhenotypee1;}
	public void setPhenotypee1(Boolean aPhenotypee1) {thePhenotypee1 = aPhenotypee1;}

	/** Фенотип не определялся */
	@Comment("Фенотип не определялся")
	@Persist
	public Boolean getPhenotypeNone() {return thePhenotypeNone;}
	public void setPhenotypeNone(Boolean aPhenotypeNone) {thePhenotypeNone = aPhenotypeNone;}

	/** Фенотип E */
	private Boolean thePhenotypee1;	
	/** Фенотип e */
	private Boolean thePhenotypeE;
	/** Фенотип Е */
	private Boolean thePhenotypeD;
	/** Фенотип с */
	private Boolean thePhenotypec1;
	/** Фенотип C */
	private Boolean thePhenotypeC;
	/** Фенотип не определялся*/
	private Boolean thePhenotypeNone;
	/** Фенотип */
	private String thePhenotype;

	/** Срок годности */
	@Comment("Срок годности")
	@Persist @Required @DateString @DoDateString
	public String getExpirationDate() {return theExpirationDate;}
	public void setExpirationDate(String aExpirationDate) {theExpirationDate = aExpirationDate;}

	/** Номер контейнера */
	@Comment("Номер контейнера")
	@Persist @Required
	public String getContainerNumber() {return theContainerNumber;}
	public void setContainerNumber(String aContainerNumber) {theContainerNumber = aContainerNumber;}

	/** Исследования антител */
	@Comment("Исследования антител")
	@Persist @Required
	public Long getResearchAntibodies() {return theResearchAntibodies;}
	public void setResearchAntibodies(Long aResearchAntibodies) {theResearchAntibodies = aResearchAntibodies;}

	/** Выявленные антитела */
	@Comment("Выявленные антитела")
	@Persist
	public String getAntibodiesList() {return theAntibodiesList;}
	public void setAntibodiesList(String aAntibodiesList) {theAntibodiesList = aAntibodiesList;}

	/** Трансфузионный анамнез */
	@Comment("Трансфузионный анамнез")
	@Persist @Required
	public Long getTransfusionHistory() {return theTransfusionHistory;}
	public void setTransfusionHistory(Long aTransfusionHistory) {theTransfusionHistory = aTransfusionHistory;}

	
	/** Трансфузионный по индив. подбору в прошлом */
	@Comment("Трансфузионный по индив. подбору в прошлом")
	@Persist @Required
	public Long getPersonalTransfusionHistory() {return thePersonalTransfusionHistory;}
	public void setPersonalTransfusionHistory(Long aTransfusionHistory) {thePersonalTransfusionHistory = aTransfusionHistory;}

	/** Кол-во беременностей */
	@Comment("Кол-во беременностей")
	@Persist
	public String getCountPregnancy() {return theCountPregnancy;}
	public void setCountPregnancy(String aCountPregnancy) {theCountPregnancy = aCountPregnancy;}

	/** Особенности беременности */
	@Comment("Особенности беременности")
	@Persist
	public Long getPregnancyHang() {return thePregnancyHang;}
	public void setPregnancyHang(Long aPregnancyHang) {thePregnancyHang = aPregnancyHang;}

	/** Особенности беременности */
	private Long thePregnancyHang;
	/** Кол-во беременностей */
	private String theCountPregnancy;
	/** Трансфузионный по индив. подбору в прошлом */
	private Long thePersonalTransfusionHistory;
	/** Трансфузионный анамнез */
	private Long theTransfusionHistory;
	/** Выявленные антитела */
	private String theAntibodiesList;
	/** Исследования антител */
	private Long theResearchAntibodies;
	/** Номер контейнера */
	private String theContainerNumber;
	/** Срок годности */
	private String theExpirationDate;
	
	/** Реакции на переливания в прошлом */
	@Comment("Реакции на переливания в прошлом")
	@Persist @Required
	public Long getTransfusionReactionLast() {return theTransfusionReactionLast;}
	public void setTransfusionReactionLast(Long aTransfusionReactionLast) {theTransfusionReactionLast = aTransfusionReactionLast;}

	/** Реакции на переливания в прошлом */
	private Long theTransfusionReactionLast;
	
	/** Определение резус-принадлежности рециента производилось */
	@Comment("Определение резус-принадлежности рециента производилось")
	@Persist @Required
	public Long getDefinitionRhesus() {return theDefinitionRhesus;}
	public void setDefinitionRhesus(Long aDefinitionRhesus) {theDefinitionRhesus = aDefinitionRhesus;}

	/** Определение резус-принадлежности рециента производилось */
	private Long theDefinitionRhesus;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getCreateTime() {return theCreateTime;	}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}

	/** Пользователь, которые последним редактировал запись */
	@Comment("Пользователь, которые последним редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата редакции */
	@Comment("Дата редакции")
	@Persist @DateString @DoDateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

	/** Время редакции */
	@Comment("Время редакции")
	@Persist @TimeString @DoTimeString
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}

	/** Время редакции */
	private String theEditTime;
	/** Дата редакции */
	private String theEditDate;
	/** Пользователь, которые последним редактировал запись */
	private String theEditUsername;
	/** Время создания */
	private String theCreateTime;
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
}
