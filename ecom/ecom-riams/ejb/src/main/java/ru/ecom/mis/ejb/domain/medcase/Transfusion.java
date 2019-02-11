package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPreparatorBlood;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionDefinitionRhesus;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionMethod;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionPregnancyHang;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReaction;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReason;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionResearchAntibodies;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Переливание трансфузионных сред
 * @author azviagin
 *
 */

@Comment("Переливание трансфузионных сред")
@Entity
@AIndexes({
	@AIndex(properties="medCase")
    })
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public abstract class Transfusion extends BaseEntity{
	
	/** Дата начала */
	@Comment("Дата начала")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}

	
	/** Первичное */
	@Comment("Первичное")
	public Boolean getPrimaryCase() {return thePrimaryCase;}
	public void setPrimaryCase(Boolean aPrimary) {thePrimaryCase = aPrimary;}

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Показания к применению */
	@Comment("Показания к применению")
	@OneToOne
	public VocTransfusionReason getReason() {return theReason;}
	public void setReason(VocTransfusionReason aReason) {theReason = aReason;}

	/** Доза (мл) */
	@Comment("Доза (мл)")
	public Integer getDoze() {return theDoze;}
	public void setDoze(Integer aDoze) {theDoze = aDoze;}

	/** Серия */
	@Comment("Серия")
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}
	
	/** Дата приготовления */
	@Comment("Дата приготовления")
	public Date getPreparationDate() {return thePreparationDate;}
	public void setPreparationDate(Date aPreparationDate) {thePreparationDate = aPreparationDate;}

	/** Изготовитель */
	@Comment("Изготовитель")
	@OneToOne
	public VocPreparatorBlood getPreparator() {return thePreparator;}
	public void setPreparator(VocPreparatorBlood aPreparator) {thePreparator = aPreparator;}

	/** Способ переливания */
	@Comment("Способ переливания")
	@OneToOne
	public VocTransfusionMethod getTransfusionMethod() {return theTransfusionMethod;}
	public void setTransfusionMethod(VocTransfusionMethod aTransfusionMethod) {theTransfusionMethod = aTransfusionMethod;}

	/** Исполнитель */
	@Comment("Исполнитель")
	@OneToOne
	public WorkFunction getExecutor() {return theExecutor;}
	public void setExecutor(WorkFunction aExecutor) {theExecutor = aExecutor;}

	/** Трансфузионная реакция */
	@Comment("Трансфузионная реакция")
	@OneToOne
	public VocTransfusionReaction getTransfusionReaction() {return theTransfusionReaction;}
	public void setTransfusionReaction(VocTransfusionReaction aTransfusionReaction) {theTransfusionReaction = aTransfusionReaction;}

	/** Осложнения после переливания */
	@Comment("Осложнения после переливания")
	public String getComplications() {return theComplications;}
	public void setComplications(String aComplications) {theComplications = aComplications;}

	/** Номер в журнале */
	@Comment("Номер в журнале")
	public Integer getJournalNumber() {return theJournalNumber;}
	public void setJournalNumber(Integer aJournalNumber) {theJournalNumber = aJournalNumber;}

	/** Дата начала */
	private Date theStartDate;
	/** Первичное */
	private Boolean thePrimaryCase;
	/** СМО */
	private MedCase theMedCase;
	/** Показания к применению */
	private VocTransfusionReason theReason;
	/** Доза (мл) */
	private Integer theDoze;
	/** Серия */
	private String theSeries;
	/** Дата приготовления */
	private Date thePreparationDate;
	/** Изготовитель */
	private VocPreparatorBlood thePreparator;
	/** Способ переливания */
	private VocTransfusionMethod theTransfusionMethod;
	/** Исполнитель */
	private WorkFunction theExecutor;
	/** Трансфузионная реакция */
	private VocTransfusionReaction theTransfusionReaction;
	/** Осложнения после переливания */
	private String theComplications;
	/** Номер в журнале */
	private Integer theJournalNumber;
	
	/** Время переливание с */
	@Comment("Время переливание с")
	public Time getTimeFrom() {return theTimeFrom;}
	public void setTimeFrom(Time aTimeFrom) {theTimeFrom = aTimeFrom;}

	/** Время переливания по */
	@Comment("Время переливания по")
	public Time getTimeTo() {return theTimeTo;}
	public void setTimeTo(Time aTimeTo) {theTimeTo = aTimeTo;}

	/** Время переливания по */
	private Time theTimeTo;
	/** Время переливание с */
	private Time theTimeFrom;
	
	/** Фенотип */
	@Comment("Фенотип")
	public String getPhenotype() {return thePhenotype;}
	public void setPhenotype(String aPhenotype) {thePhenotype = aPhenotype;}

	/** Фенотип C */
	@Comment("Фенотип C")
	public Boolean getPhenotypeC() {return thePhenotypeC;}
	public void setPhenotypeC(Boolean aPhenotypeC) {thePhenotypeC = aPhenotypeC;}

	/** Фенотип с */
	@Comment("Фенотип с")
	public Boolean getPhenotypec1() {return thePhenotypec1;}
	public void setPhenotypec1(Boolean aPhenotypec1) {thePhenotypec1 = aPhenotypec1;}

	/** Фенотип Е */
	@Comment("Фенотип Е")
	public Boolean getPhenotypeD() {return thePhenotypeD;}
	public void setPhenotypeD(Boolean aPhenotypeD) {thePhenotypeD = aPhenotypeD;}

	/** Фенотип e */
	@Comment("Фенотип e")
	public Boolean getPhenotypeE() {return thePhenotypeE;}
	public void setPhenotypeE(Boolean aPhenotypeE) {thePhenotypeE = aPhenotypeE;}

	/** Фенотип E */
	@Comment("Фенотип E")
	public Boolean getPhenotypee1() {return thePhenotypee1;}
	public void setPhenotypee1(Boolean aPhenotypee1) {thePhenotypee1 = aPhenotypee1;}

	/** Фенотип не определялся */
	@Comment("Фенотип не определялся")
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
	public Date getExpirationDate() {return theExpirationDate;}
	public void setExpirationDate(Date aExpirationDate) {theExpirationDate = aExpirationDate;}

	/** Номер контейнера */
	@Comment("Номер контейнера")
	public String getContainerNumber() {return theContainerNumber;}
	public void setContainerNumber(String aContainerNumber) {theContainerNumber = aContainerNumber;}

	/** Исследования антител */
	@Comment("Исследования антител")
	@OneToOne
	public VocTransfusionResearchAntibodies getResearchAntibodies() {return theResearchAntibodies;}
	public void setResearchAntibodies(VocTransfusionResearchAntibodies aResearchAntibodies) {theResearchAntibodies = aResearchAntibodies;}

	/** Выявленные антитела */
	@Comment("Выявленные антитела")
	public String getAntibodiesList() {return theAntibodiesList;}
	public void setAntibodiesList(String aAntibodiesList) {theAntibodiesList = aAntibodiesList;}

	/** Трансфузионный анамнез */
	@Comment("Трансфузионный анамнез")
	@OneToOne
	public VocYesNo getTransfusionHistory() {return theTransfusionHistory;}
	public void setTransfusionHistory(VocYesNo aTransfusionHistory) {theTransfusionHistory = aTransfusionHistory;}

	
	/** Трансфузионный по индив. подбору в прошлом */
	@Comment("Трансфузионный по индив. подбору в прошлом")
	@OneToOne
	public VocYesNo getPersonalTransfusionHistory() {return thePersonalTransfusionHistory;}
	public void setPersonalTransfusionHistory(VocYesNo aTransfusionHistory) {thePersonalTransfusionHistory = aTransfusionHistory;}

	/** Кол-во беременностей */
	@Comment("Кол-во беременностей")
	public String getCountPregnancy() {return theCountPregnancy;}
	public void setCountPregnancy(String aCountPregnancy) {theCountPregnancy = aCountPregnancy;}

	/** Особенности беременности */
	@Comment("Особенности беременности")
	@OneToOne
	public VocTransfusionPregnancyHang getPregnancyHang() {return thePregnancyHang;}
	public void setPregnancyHang(VocTransfusionPregnancyHang aPregnancyHang) {thePregnancyHang = aPregnancyHang;}

	/** Особенности беременности */
	private VocTransfusionPregnancyHang thePregnancyHang;
	/** Кол-во беременностей */
	private String theCountPregnancy;
	/** Трансфузионный по индив. подбору в прошлом */
	private VocYesNo thePersonalTransfusionHistory;
	/** Трансфузионный анамнез */
	private VocYesNo theTransfusionHistory;
	/** Выявленные антитела */
	private String theAntibodiesList;
	/** Исследования антител */
	private VocTransfusionResearchAntibodies theResearchAntibodies;
	/** Номер контейнера */
	private String theContainerNumber;
	/** Срок годности */
	private Date theExpirationDate;

	/** Реакции на переливания в прошлом */
	@Comment("Реакции на переливания в прошлом")
	@OneToOne
	public VocYesNo getTransfusionReactionLast() {return theTransfusionReactionLast;}
	public void setTransfusionReactionLast(VocYesNo aTransfusionReactionLast) {theTransfusionReactionLast = aTransfusionReactionLast;}

	/** Реакции на переливания в прошлом */
	private VocYesNo theTransfusionReactionLast;
	
	/** Определение резус-принадлежности рециента производилось */
	@Comment("Определение резус-принадлежности рециента производилось")
	@OneToOne
	public VocTransfusionDefinitionRhesus getDefinitionRhesus() {return theDefinitionRhesus;}
	public void setDefinitionRhesus(VocTransfusionDefinitionRhesus aDefinitionRhesus) {theDefinitionRhesus = aDefinitionRhesus;}

	/** Определение резус-принадлежности рециента производилось */
	private VocTransfusionDefinitionRhesus theDefinitionRhesus;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;	}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	/** Пользователь, которые последним редактировал запись */
	@Comment("Пользователь, которые последним редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата редакции */
	@Comment("Дата редакции")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}

	/** Время редакции */
	
	@Comment("Время редакции")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}

	/** Время редакции */
	private Time theEditTime;
	/** Дата редакции */
	private Date theEditDate;
	/** Пользователь, которые последним редактировал запись */
	private String theEditUsername;
	/** Время создания */
	private Time theCreateTime;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	
}
//lastrelease milamesher 02.04.2018 #95