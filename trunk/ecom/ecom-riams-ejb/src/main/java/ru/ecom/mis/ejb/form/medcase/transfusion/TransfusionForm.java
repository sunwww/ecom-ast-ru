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
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

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
	@Persist
	public String getReason() {return theReason;}
	public void setReason(String aReason) {theReason = aReason;}

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
	@Persist @DateString @DoDateString
	public String getPreparationDate() {return thePreparationDate;}
	public void setPreparationDate(String aPreparationDate) {thePreparationDate = aPreparationDate;}

	/** Изготовитель */
	@Comment("Изготовитель")
	@Persist @DoUpperCase
	public String getPreparator() {return thePreparator;}
	public void setPreparator(String aPreparator) {thePreparator = aPreparator;}

	/** Способ переливания */
	@Comment("Способ переливания")
	@Persist
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
	@Persist
	public String getComplications() {return theComplications;}
	public void setComplications(String aComplications) {theComplications = aComplications;}

	/** Номер в журнале */
	@Comment("Номер в журнале")
	@Persist
	public Integer getJournalNumber() {return theJournalNumber;}
	public void setJournalNumber(Integer aJournalNumber) {theJournalNumber = aJournalNumber;}

	/** Информация о трансфузионной среде */
	@Comment("Информация о трансфузионной среде")
	@Persist
	public String getInformation() {return theInformation;}
	public void setInformation(String aInformation) {theInformation = aInformation;}

	/** Информация о исполнителе */
	@Comment("Информация о исполнителе")
	@Persist
	public String getExecutorInfo() {return theExecutorInfo;}
	public void setExecutorInfo(String aExecutorInfo) {theExecutorInfo = aExecutorInfo;}

	/** Информация о исполнителе */
	private String theExecutorInfo;
	/** Информация о трансфузионной среде */
	private String theInformation;
	/** Дата начала */
	private String theStartDate;
	/** Первичное */
	private Boolean thePrimaryCase;
	/** СМО */
	private Long theMedCase;
	/** Показания к применению */
	private String theReason;
	/** Доза (мл) */
	private Integer theDoze;
	/** Серия */
	private String theSeries;
	/** Дата приготовления */
	private String thePreparationDate;
	/** Изготовитель */
	private String thePreparator;
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

}
