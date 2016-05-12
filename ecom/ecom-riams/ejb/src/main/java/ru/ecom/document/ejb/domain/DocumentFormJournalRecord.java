package ru.ecom.document.ejb.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.document.ejb.domain.voc.VocDocumentType;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *Запись журнала учета бланков документов
 * @author azviagin
 *
 */
@Comment("Запись журнала учета бланков документов")
@Entity
@Table(schema="SQLUser")
public class DocumentFormJournalRecord extends BaseEntity{
	
	/** Журнала учета бланков документов */
	@Comment("Журнала учета бланков документов")
	@ManyToOne
	public DocumentFormJournal getDocumentFormJournal() {
		return theDocumentFormJournal;
	}

	public void setDocumentFormJournal(DocumentFormJournal aDocumentFormJournal) {
		theDocumentFormJournal = aDocumentFormJournal;
	}

	/** Журнала учета бланков документов */
	private DocumentFormJournal theDocumentFormJournal;
	
	/** Интервалы возвращенных корешков документов */
	@Comment("Интервалы возвращенных корешков документов")
	@OneToMany(mappedBy="documentFormJournalRecord", cascade=CascadeType.ALL)
	public List<DocumentFormInterval> getReturnStubIntervals() {
		return theReturnStubIntervals;
	}

	public void setReturnStubIntervals(List<DocumentFormInterval> aReturnStubIntervals) {
		theReturnStubIntervals = aReturnStubIntervals;
	}

	/** Интервалы возвращенных корешков документов */
	private List<DocumentFormInterval> theReturnStubIntervals;
	
	/** Количество возвращенных корешков документов */
	@Comment("Количество возвращенных корешков документов")
	public int getReturnStubAmount() {
		return theReturnStubAmount;
	}

	public void setReturnStubAmount(int aReturnStubAmount) {
		theReturnStubAmount = aReturnStubAmount;
	}

	/** Количество возвращенных корешков документов */
	private int theReturnStubAmount;
	

	
	/** Тип документа */
	@Comment("Тип документа")
	@OneToOne
	public VocDocumentType getVocDocumentType() {
		return theVocDocumentType;
	}

	public void setVocDocumentType(VocDocumentType aVocDocumentType) {
		theVocDocumentType = aVocDocumentType;
	}

	/** Тип документа */
	private VocDocumentType theVocDocumentType;
	
	
	/** Интервал бланков */
	@Comment("Интервал бланков")
	@OneToMany(mappedBy="documentFormJournalRecord", cascade=CascadeType.ALL)
	public List<DocumentFormInterval> getDocumentFormIntervals() {
		return theDocumentFormIntervals;
	}

	public void setDocumentFormIntervals(List<DocumentFormInterval> aDocumentFormIntervals) {
		theDocumentFormIntervals = aDocumentFormIntervals;
	}

	/** Интервал бланков */
	private List<DocumentFormInterval> theDocumentFormIntervals;
	
	/** Количество испорченных бланков */
	@Comment("Количество испорченных бланков")
	@Transient
	public int getBrokenAmount() {
		return theBrokenAmount;
	}

	public void setBrokenAmount(int aBrokenAmount) {
		theBrokenAmount = aBrokenAmount;
	}

	/** Количество испорченных бланков */
	private int theBrokenAmount;
	
	/** Количество бланков в наличии */
	@Comment("Количество бланков в наличии")
	@Transient
	public int getPresenceAmount() {
		return thePresenceAmount;
	}

	public void setPresenceAmount(int aPresenceAmount) {
		thePresenceAmount = aPresenceAmount;
	}

	/** Количество бланков в наличии */
	private int thePresenceAmount;
	
	/** Остаток предыдущей партии */
	@Comment("Остаток предыдущей партии")
	public int getPreviousRemains() {
		return thePreviousRemains;
	}

	public void setPreviousRemains(int aPreviousRemains) {
		thePreviousRemains = aPreviousRemains;
	}

	/** Остаток предыдущей партии */
	private int thePreviousRemains;
	
	/** Количество израсходованных бланков */
	@Comment("Количество израсходованных бланков")
	@Transient
	public int getExpanceAmount() {
		return theExpanceAmount;
	}

	public void setExpanceAmount(int aExpanceAmount) {
		theExpanceAmount = aExpanceAmount;
	}

	/** Количество израсходованных бланков */
	private int theExpanceAmount;
	
	/** Количество поступивших бланков */
	@Comment("Количество поступивших бланков")
	public int getissueAmount() {
		return theissueAmount;
	}

	public void setissueAmount(int aissueAmount) {
		theissueAmount = aissueAmount;
	}

	/** Количество поступивших бланков */
	private int theissueAmount;
	
	/** Кто принял бланки */
	@Comment("Кто принял бланки")
	@OneToOne
	public Worker getRecieveWorker() {
		return theRecieveWorker;
	}

	public void setRecieveWorker(Worker aRecieveWorker) {
		theRecieveWorker = aRecieveWorker;
	}

	/** Кто принял бланки */
	private Worker theRecieveWorker;
	
	/** Кто выдал бланки */
	@Comment("Кто принял бланки")
	@OneToOne
	public Worker getIssueWorker() {
		return theIssueWorker;
	}

	public void setIssueWorker(Worker aIssueWorker) {
		theIssueWorker = aIssueWorker;
	}

	/** Кто выдал бланки */
	private Worker theIssueWorker;
	
	/** Дата поступления бланков */
	@Comment("Дата поступления бланков")
	public Date getissueDate() {
		return theissueDate;
	}

	public void setissueDate(Date aissueDate) {
		theissueDate = aissueDate;
	}

	/** Дата поступления бланков */
	private Date theissueDate;


}
