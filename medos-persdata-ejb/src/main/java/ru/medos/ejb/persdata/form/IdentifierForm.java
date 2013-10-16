package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Identifier;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;

@EntityForm
@EntityFormPersistance(clazz = Identifier.class)
@Comment("Идентификатор")
@WebTrail(comment = "Идентификатор", nameProperties= "id", view="entityParentView-personaldata_identifier.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/PersData/Person/Identifier")
public class IdentifierForm extends JournalDataForm {
	/**
	 * Персона
	 */
	@Comment("Персона")
	@Persist
	public Long getPerson() {return thePerson;}
	public void setPerson(Long aPerson) {thePerson = aPerson;}
	/**
	 * Персона
	 */
	private Long thePerson;
	/**
	 * Идентифицирующая система
	 */
	@Comment("Идентифицирующая система")
	@Persist
	public Long getIdentificationSystem() {return theIdentificationSystem;}
	public void setIdentificationSystem(Long aIdentificationSystem) {theIdentificationSystem = aIdentificationSystem;}
	/**
	 * Идентифицирующая система
	 */
	private Long theIdentificationSystem;
	/**
	 * Временный
	 */
	@Comment("Временный")
	@Persist
	public Boolean getIsTransient() {return theIsTransient;}
	public void setIsTransient(Boolean aIsTransient) {theIsTransient = aIsTransient;}
	/**
	 * Временный
	 */
	private Boolean theIsTransient;
	/**
	 * Идентификационный номер
	 */
	@Comment("Идентификационный номер")
	@Persist @DoUpperCase
	public String getIdentificationNumber() {return theIdentificationNumber;}
	public void setIdentificationNumber(String aIdentificationNumber) {theIdentificationNumber = aIdentificationNumber;}
	/**
	 * Идентификационный номер
	 */
	private String theIdentificationNumber;
	/** Входящий документ */
	@Comment("Входящий документ")
	@Persist
	public Long getComingDocument() {return theComingDocument;}
	public void setComingDocument(Long aComingDocument) {theComingDocument = aComingDocument;}

	/** Входящий документ */
	private Long theComingDocument;
	
	/** Акт передачи копий */
	@Comment("Акт передачи копий")
	@Persist
	public Long getTransferAct() {return theTransferAct;}
	public void setTransferAct(Long aTransferAct) {theTransferAct = aTransferAct;}
	/** Акт передачи копий */
	private Long theTransferAct;
	
	/** Акт уничтожения копий */
	@Comment("Акт уничтожения копий")
	@Persist
	public Long getCopiesDestructionAct() {return theCopiesDestructionAct;}
	public void setCopiesDestructionAct(Long aCopiesDestructionAct) {theCopiesDestructionAct = aCopiesDestructionAct;}
	/** Акт уничтожения копий */
	private Long theCopiesDestructionAct;
}
