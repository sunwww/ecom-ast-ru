package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.licence.voc.VocExternalDocumentType;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Внешние документы")
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "patient" },table="Document") })
public class ExternalDocument extends Document {
	/** Ссылка на файл */
	@Comment("Ссылка на файл")
	public String getReferenceTo() {return theReferenceTo;}
	public void setReferenceTo(String aLinkFile) {	theReferenceTo = aLinkFile;}

	/** Тип документа */
	@Comment("Тип документа")
	@OneToOne
	public VocExternalDocumentType getType() {return theType;}
	public void setType(VocExternalDocumentType aType) {theType = aType;}
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** Комментарий */
	@Comment("Комментарий")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	@Comment("Ссылка на сжатый файл")
	public String getReferenceCompTo() {
		return theReferenceCompTo;
	}

	public void setReferenceCompTo(String aReferenceCompTo) {
		theReferenceCompTo = aReferenceCompTo;
	}

	/** Ссылка на сжатый файл */
	private String theReferenceCompTo;
	/** Комментарий */
	private String theComment;
	/** Пациент */
	private Patient thePatient;
	/** Тип документа */
	private VocExternalDocumentType theType;
	/** Ссылка на файл */
	private String theReferenceTo;
}
