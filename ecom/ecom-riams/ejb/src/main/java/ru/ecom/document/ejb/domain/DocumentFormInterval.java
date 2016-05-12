package ru.ecom.document.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Интервал бланков документов
 * @author azviagin
 *
 */
@Comment("Интервал бланков документов")
@Entity
@Table(schema="SQLUser")
public class DocumentFormInterval extends BaseEntity{
	
	/** Запись журнала учета бланков документов */
	@Comment("Запись журнала учета бланков документов")
	@ManyToOne
	public DocumentFormJournalRecord getDocumentFormJournalRecord() {
		return theDocumentFormJournalRecord;
	}

	public void setDocumentFormJournalRecord(DocumentFormJournalRecord aDocumentFormJournalRecord) {
		theDocumentFormJournalRecord = aDocumentFormJournalRecord;
	}

	/** Запись журнала учета бланков документов */
	private DocumentFormJournalRecord theDocumentFormJournalRecord;
	
	/** Конечный номер */
	@Comment("Конечный номер")
	public int getFinishNumber() {
		return theFinishNumber;
	}

	public void setFinishNumber(int aFinishNumber) {
		theFinishNumber = aFinishNumber;
	}

	/** Конечный номер */
	private int theFinishNumber;
	
	/** Начальный номер */
	@Comment("Начальный номер")
	public int getStartNumber() {
		return theStartNumber;
	}

	public void setStartNumber(int aStartNumber) {
		theStartNumber = aStartNumber;
	}

	/** Начальный номер */
	private int theStartNumber;
	
	/** Серия */
	@Comment("Серия")
	public String getSeries() {
		return theSeries;
	}

	public void setSeries(String aSeries) {
		theSeries = aSeries;
	}

	/** Серия */
	private String theSeries;

}
