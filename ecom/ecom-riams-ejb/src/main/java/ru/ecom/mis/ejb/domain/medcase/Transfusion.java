package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionMethod;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReaction;
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
	@ManyToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Показания к применению */
	@Comment("Показания к применению")
	public String getReason() {return theReason;}
	public void setReason(String aReason) {theReason = aReason;}

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
	public String getPreparator() {return thePreparator;}
	public void setPreparator(String aPreparator) {thePreparator = aPreparator;}

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

	/** Информация о трансфузионной среде */
	@Transient
	public String getInformation() {return "" ;}
	
	/** Информация об исполнителе */
	@Transient
	public String getExecutorInfo() {
		return theExecutor!=null? theExecutor.getWorkFunctionInfo(): "" ;
	}
	
	/** Дата начала */
	private Date theStartDate;
	/** Первичное */
	private Boolean thePrimaryCase;
	/** СМО */
	private MedCase theMedCase;
	/** Показания к применению */
	private String theReason;
	/** Доза (мл) */
	private Integer theDoze;
	/** Серия */
	private String theSeries;
	/** Дата приготовления */
	private Date thePreparationDate;
	/** Изготовитель */
	private String thePreparator;
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

}
