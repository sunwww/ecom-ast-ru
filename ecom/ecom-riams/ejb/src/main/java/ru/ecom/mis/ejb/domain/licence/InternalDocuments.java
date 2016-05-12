package ru.ecom.mis.ejb.domain.licence;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentBiologAnalysis;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentMaterialBiologAnalysis;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentObjectBiologAnalysis;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Внутренние документы")
@Table(schema="SQLUser")
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

	/** Рекомендации */
	@Comment("Рекомендации")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getRecommendations() {return theRecommendations;}
	public void setRecommendations(String aRecommendations) {theRecommendations = aRecommendations;}

	/** Куда направлен */
	@Comment("Куда направлен")
	@OneToOne
	public MisLpu getSentToLpu() {return theSentToLpu;}
	public void setSentToLpu(MisLpu aSentToLpu) {theSentToLpu = aSentToLpu;}

	/** Диагноз */
	@Comment("Диагноз")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}


	/** Код диагноза */
	@Comment("Код диагноза")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	/** Телефон */
	@Comment("Телефон")
	public String getPhonePatient() {return thePhonePatient;}
	public void setPhonePatient(String aPhonePatient) {thePhonePatient = aPhonePatient;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}

	
	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	/** Телефон */
	private String thePhonePatient;
	/** Код диагноза */
	private VocIdc10 theIdc10;
	/** Диагноз */
	private String theDiagnosis;
	/** Куда направлен */
	private MisLpu theSentToLpu;
	/** Рекомендации */
	private String theRecommendations;
	/** Обоснование */
	private String theHistory;
	
	/** Талон */
	@Comment("Талон")
	@OneToOne @Deprecated
	public Ticket getTicket() {return theTicket;}
	@Deprecated
	public void setTicket(Ticket aTicket) {theTicket = aTicket;}

	/** Цель биологического исследования */
	@Comment("Цель биологического исследования")
	@OneToOne
	public VocDocumentObjectBiologAnalysis getObjectBiologAnalysis() {
		return theObjectBiologAnalysis;
	}

	public void setObjectBiologAnalysis(VocDocumentObjectBiologAnalysis aObjectAnalysis) {
		theObjectBiologAnalysis = aObjectAnalysis;
	}

	/** Исследование */
	@Comment("Исследование")
	@OneToOne
	public VocDocumentBiologAnalysis getBiologAnalysis() {
		return theBiologAnalysis;
	}

	public void setBiologAnalysis(VocDocumentBiologAnalysis aBiologAnalysis) {
		theBiologAnalysis = aBiologAnalysis;
	}

	/** Материал для микробилогического исследования */
	@Comment("Материал для микробилогического исследования")
	@OneToOne
	public VocDocumentMaterialBiologAnalysis getMaterialBiologAnalysis() {
		return theMaterialBiologAnalysis;
	}

	public void setMaterialBiologAnalysis(VocDocumentMaterialBiologAnalysis aMaterialBiologAnalysis) {
		theMaterialBiologAnalysis = aMaterialBiologAnalysis;
	}

	/** Услуги */
	@Comment("Услуги")
	public String getServicies() {return theServicies;}
	public void setServicies(String aServicies) {theServicies = aServicies;}

	/** Услуги */
	private String theServicies;
	/** Материал для микробилогического исследования */
	private VocDocumentMaterialBiologAnalysis theMaterialBiologAnalysis;
	/** Исследование */
	private VocDocumentBiologAnalysis theBiologAnalysis;
	/** Цель биологического исследования */
	private VocDocumentObjectBiologAnalysis theObjectBiologAnalysis;
	/** Талон */
	private Ticket theTicket;
	
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}

	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return theBedType;}
	public void setBedType(VocBedType aBedType) {theBedType = aBedType;}

	/** Профиль коек */
	private VocBedType theBedType;
	/** Отделение */
	private MisLpu theDepartment;
	/** Планируемая дата с */
	@Comment("Планируемая дата с")
	public Date getPlanDateFrom() {return thePlanDateFrom;}
	public void setPlanDateFrom(Date aPlanDateFrom) {thePlanDateFrom = aPlanDateFrom;}

	/** Планируемая дата по */
	@Comment("Планируемая дата по")
	public Date getPlanDateTo() {return thePlanDateTo;}
	public void setPlanDateTo(Date aPlanDateTo) {thePlanDateTo = aPlanDateTo;}

	/** Планируемая дата по */
	private Date thePlanDateTo;
	/** Планируемая дата с */
	private Date thePlanDateFrom;
	
	/** Планируется операция? */
	@Comment("Планируется операция?")
	public Boolean getIsPlanOperation() {return theIsPlanOperation;}
	public void setIsPlanOperation(Boolean aIsPlanOperation) {theIsPlanOperation = aIsPlanOperation;}

	/** Планируется операция? */
	private Boolean theIsPlanOperation;
	
	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private VocBedSubType theBedSubType;

}
