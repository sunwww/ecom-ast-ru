package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.medos.ejb.persdata.domain.Person;
import ru.medos.ejb.persdata.domain.voc.VocIdentificationSystem;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Идентификатор
	 */
	@Comment("Идентификатор")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "person" }) 
		,@AIndex(properties = { "comingDocument" })
})
public class Identifier extends JournalData{
	/**
	 * Персона
	 */
	@Comment("Персона")
	@ManyToOne
	public Person getPerson() {
		return thePerson;
	}
	public void setPerson(Person aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Персона
	 */
	private Person thePerson;
	/**
	 * Идентифицирующая система
	 */
	@Comment("Идентифицирующая система")
	@OneToOne
	public VocIdentificationSystem getIdentificationSystem() {
		return theIdentificationSystem;
	}
	public void setIdentificationSystem(VocIdentificationSystem aIdentificationSystem) {
		theIdentificationSystem = aIdentificationSystem;
	}
	/**
	 * Идентифицирующая система
	 */
	private VocIdentificationSystem theIdentificationSystem;
	/**
	 * Временный
	 */
	@Comment("Временный")
	
	public Boolean getIsTransient() {
		return theIsTransient;
	}
	public void setIsTransient(Boolean aIsTransient) {
		theIsTransient = aIsTransient;
	}
	/**
	 * Временный
	 */
	private Boolean theIsTransient;
	/**
	 * Идентификационный номер
	 */
	@Comment("Идентификационный номер")
	
	public String getIdentificationNumber() {
		return theIdentificationNumber;
	}
	public void setIdentificationNumber(String aIdentificationNumber) {
		theIdentificationNumber = aIdentificationNumber;
	}
	/**
	 * Идентификационный номер
	 */
	private String theIdentificationNumber;

	/**
	 * Акт передачи копий
	 */
	@Comment("Акт передачи копий")
	@ManyToOne
	public CopiesTransferAct getTransferAct() {
		return theTransferAct;
	}
	public void setTransferAct(CopiesTransferAct aTransferAct) {
		theTransferAct = aTransferAct;
	}
	/**
	 * Акт передачи копий
	 */
	private CopiesTransferAct theTransferAct;
	/**
	 * Акт уничтожения копий
	 */
	@Comment("Акт уничтожения копий")
	@ManyToOne
	public CopiesDestructionAct getCopiesDestructionAct() {
		return theCopiesDestructionAct;
	}
	public void setCopiesDestructionAct(CopiesDestructionAct aCopiesDestructionAct) {
		theCopiesDestructionAct = aCopiesDestructionAct;
	}
	/**
	 * Акт уничтожения копий
	 */
	private CopiesDestructionAct theCopiesDestructionAct;
	
	/** Входящий документ */
	@Comment("Входящий документ")
	@OneToOne
	public ComingDocument getComingDocument() {
		return theComingDocument;
	}

	public void setComingDocument(ComingDocument aComingDocument) {
		theComingDocument = aComingDocument;
	}

	/** Входящий документ */
	private ComingDocument theComingDocument;
}
