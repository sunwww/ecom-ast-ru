package ru.ecom.mis.ejb.domain.licence;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentBiologAnalysis;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentMaterialBiologAnalysis;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentObjectBiologAnalysis;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHealthGroup;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
@Comment("Внутренние документы")
@AIndexes(value = {
		@AIndex(properties={"medCase"},table="Document"),
		@AIndex(properties={"ticket"},table="Document")
})
public class InternalDocuments extends Document {
	/** Обоснование */
	@Comment("Обоснование")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getHistory() {return theHistory;}
	public void setHistory(String aHistory) {theHistory = aHistory;}
	private String theHistory;

	/** Рекомендации */
	@Comment("Рекомендации")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getRecommendations() {return theRecommendations;}
	public void setRecommendations(String aRecommendations) {theRecommendations = aRecommendations;}
	private String theRecommendations;

	/** Куда направлен */
	@Comment("Куда направлен")
	@OneToOne
	public MisLpu getSentToLpu() {return theSentToLpu;}
	public void setSentToLpu(MisLpu aSentToLpu) {theSentToLpu = aSentToLpu;}
	private MisLpu theSentToLpu;

	/** Диагноз */
	@Comment("Диагноз")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}
	private String theDiagnosis;


	/** Код диагноза */
	@Comment("Код диагноза")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}
	private VocIdc10 theIdc10;

	/** Телефон */
	@Comment("Телефон")
	public String getPhonePatient() {return thePhonePatient;}
	public void setPhonePatient(String aPhonePatient) {thePhonePatient = aPhonePatient;}
	private String thePhonePatient;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}
	private VocServiceStream theServiceStream;


	
	/** Талон */
	@Comment("Талон")
	@OneToOne @Deprecated
	public Ticket getTicket() {return theTicket;}
	@Deprecated
	public void setTicket(Ticket aTicket) {theTicket = aTicket;}
	private Ticket theTicket;

	/** Цель биологического исследования */
	@Comment("Цель биологического исследования")
	@OneToOne
	public VocDocumentObjectBiologAnalysis getObjectBiologAnalysis() {
		return theObjectBiologAnalysis;
	}
	public void setObjectBiologAnalysis(VocDocumentObjectBiologAnalysis aObjectAnalysis) {theObjectBiologAnalysis = aObjectAnalysis;}
	private VocDocumentObjectBiologAnalysis theObjectBiologAnalysis;

	/** Исследование */
	@Comment("Исследование")
	@OneToOne
	public VocDocumentBiologAnalysis getBiologAnalysis() {
		return theBiologAnalysis;
	}
	public void setBiologAnalysis(VocDocumentBiologAnalysis aBiologAnalysis) {
		theBiologAnalysis = aBiologAnalysis;
	}
	private VocDocumentBiologAnalysis theBiologAnalysis;

	/** Материал для микробилогического исследования */
	@Comment("Материал для микробилогического исследования")
	@OneToOne
	public VocDocumentMaterialBiologAnalysis getMaterialBiologAnalysis() {
		return theMaterialBiologAnalysis;
	}
	public void setMaterialBiologAnalysis(VocDocumentMaterialBiologAnalysis aMaterialBiologAnalysis) {theMaterialBiologAnalysis = aMaterialBiologAnalysis;}
	private VocDocumentMaterialBiologAnalysis theMaterialBiologAnalysis;

	/** Услуги */
	@Comment("Услуги")
	public String getServicies() {return theServicies;}
	public void setServicies(String aServicies) {theServicies = aServicies;}
	private String theServicies;


	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
	private MisLpu theDepartment;

	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return theBedType;}
	public void setBedType(VocBedType aBedType) {theBedType = aBedType;}
	private VocBedType theBedType;

	/** Планируемая дата с */
	@Comment("Планируемая дата с")
	public Date getPlanDateFrom() {return thePlanDateFrom;}
	public void setPlanDateFrom(Date aPlanDateFrom) {thePlanDateFrom = aPlanDateFrom;}
	private Date thePlanDateFrom;

	/** Планируемая дата по */
	@Comment("Планируемая дата по")
	public Date getPlanDateTo() {return thePlanDateTo;}
	public void setPlanDateTo(Date aPlanDateTo) {thePlanDateTo = aPlanDateTo;}
	private Date thePlanDateTo;

	/** Планируется операция? */
	@Comment("Планируется операция?")
	public Boolean getIsPlanOperation() {return theIsPlanOperation;}
	public void setIsPlanOperation(Boolean aIsPlanOperation) {theIsPlanOperation = aIsPlanOperation;}
	private Boolean theIsPlanOperation;
	
	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}
	private VocBedSubType theBedSubType;

	/** Группа здоровья */
	@Comment("Группа здоровья")
	@OneToOne
	public VocHealthGroup getHealthGroup() {return theHealthGroup;}
	public void setHealthGroup(VocHealthGroup aHealthGroup) {theHealthGroup = aHealthGroup;}
	private VocHealthGroup theHealthGroup ;

}
