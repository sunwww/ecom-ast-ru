package ru.medos.ejb.persdata.domain;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.medos.ejb.persdata.domain.voc.VocComingDocument;
import ru.medos.ejb.persdata.domain.voc.VocStateStructure;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Входящий документ
	 */
	@Comment("Входящий документ")
@Entity
@Table(schema="SQLUser")
public class ComingDocument extends JournalData{
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
	@OneToMany(mappedBy="comingDocument", cascade=CascadeType.ALL)
	public List<DocumentFile> getFiles() {
		return theFiles;
	}
	public void setFiles(List<DocumentFile> aFiles) {
		theFiles = aFiles;
	}
	/**
	 * Файлы
	 */
	private List<DocumentFile> theFiles;
	/**
	 * Серия
	 */
	@Comment("Серия")
	
	public String getSeries() {
		return theSeries;
	}
	public void setSeries(String aSeries) {
		theSeries = aSeries;
	}
	/**
	 * Серия
	 */
	private String theSeries;
	/**
	 * Номер
	 */
	@Comment("Номер")
	
	public String getDocumentNumber() {
		return theDocumentNumber;
	}
	public void setDocumentNumber(String aDocumentNumber) {
		theDocumentNumber = aDocumentNumber;
	}
	/**
	 * Номер
	 */
	private String theDocumentNumber;
	/**
	 * Государственный орган
	 */
	@Comment("Государственный орган")
	@OneToOne
	public VocStateStructure getStateStructure() {
		return theStateStructure;
	}
	public void setStateStructure(VocStateStructure aStateStructure) {
		theStateStructure = aStateStructure;
	}
	/**
	 * Государственный орган
	 */
	private VocStateStructure theStateStructure;
	/**
	 * Фамилия
	 */
	@Comment("Фамилия")
	
	public String getLastname() {
		return theLastname;
	}
	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}
	/**
	 * Фамилия
	 */
	private String theLastname;
	/**
	 * Отчество
	 */
	@Comment("Отчество")
	
	public String getPatronymic() {
		return thePatronymic;
	}
	public void setPatronymic(String aPatronymic) {
		thePatronymic = aPatronymic;
	}
	/**
	 * Отчество
	 */
	private String thePatronymic;
	/**
	 * Имя
	 */
	@Comment("Имя")
	
	public String getFirstname() {
		return theFirstname;
	}
	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}
	/**
	 * Имя
	 */
	private String theFirstname;
	/**
	 * Дата рождения
	 */
	@Comment("Дата рождения")
	
	public Date getBirthdate() {
		return theBirthdate;
	}
	public void setBirthdate(Date aBirthdate) {
		theBirthdate = aBirthdate;
	}
	/**
	 * Дата рождения
	 */
	private Date theBirthdate;
	/**
	 * Тип документа
	 */
	@Comment("Тип документа")
	@OneToOne
	public VocComingDocument getType() {
		return theType;
	}
	public void setType(VocComingDocument aType) {
		theType = aType;
	}
	/**
	 * Тип документа
	 */
	private VocComingDocument theType;
	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Комментарий
	 */
	private String theComment;
	/**
	 * Идентификатор
	 */
	@Comment("Идентификатор")
	@OneToOne
	public Identifier getIdentifier() {
		return theIdentifier;
	}
	public void setIdentifier(Identifier aIdentifier) {
		theIdentifier = aIdentifier;
	}
	/**
	 * Идентификатор
	 */
	private Identifier theIdentifier;
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
}
