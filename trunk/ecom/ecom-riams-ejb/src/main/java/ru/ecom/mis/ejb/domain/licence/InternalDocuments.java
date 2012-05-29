package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
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

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Код диагноза */
	@Comment("Код диагноза")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	/** Код диагноза */
	private VocIdc10 theIdc10;
	/** СМО */
	private MedCase theMedCase;
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
	@OneToOne
	public Ticket getTicket() {return theTicket;}
	public void setTicket(Ticket aTicket) {theTicket = aTicket;}

	/** Талон */
	private Ticket theTicket;
}
