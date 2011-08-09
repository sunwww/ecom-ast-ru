package ru.ecom.document.ejb.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.document.ejb.domain.voc.VocDocumentType;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Журнал учета бланков документов
 * @author azviagin
 *
 */

@Comment("Журнал учета бланков документов")
@Entity
@Table(schema="SQLUser")
public class DocumentFormJournal extends BaseEntity{
	
	/** Записи журнала */
	@Comment("Записи журнала")
	@OneToMany(mappedBy="documentFormJournal", cascade=CascadeType.ALL)
	public List<DocumentFormJournalRecord> getDocumentFormJournalRecords() {
		return theDocumentFormJournalRecords;
	}

	public void setDocumentFormJournalRecords(List<DocumentFormJournalRecord> aDocumentFormJournalRecords) {
		theDocumentFormJournalRecords = aDocumentFormJournalRecords;
	}

	/** Записи журнала */
	private List<DocumentFormJournalRecord> theDocumentFormJournalRecords;
	
	/** Тип документов по умолчанию */
	@Comment("Тип документов по умолчанию")
	@OneToOne
	public VocDocumentType getDefaultDocumentType() {
		return theDefaultDocumentType;
	}

	public void setDefaultDocumentType(VocDocumentType aDefaultDocumentType) {
		theDefaultDocumentType = aDefaultDocumentType;
	}

	/** Тип документов по умолчанию */
	private VocDocumentType theDefaultDocumentType;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getRecieveLpu() {
		return theRecieveLpu;
	}

	public void setRecieveLpu(MisLpu aRecieveLpu) {
		theRecieveLpu = aRecieveLpu;
	}

	/** ЛПУ */
	private MisLpu theRecieveLpu;
	
	/** Ответственный работник */
	@Comment("Ответственный работник")
	@ManyToOne
	public Worker getResponsibleWorker() {
		return theResponsibleWorker;
	}

	public void setResponsibleWorker(Worker aResponsableWorker) {
		theResponsibleWorker = aResponsableWorker;
	}

	/** Ответственный работник */
	private Worker theResponsibleWorker;
	
	/** Дата начала */
	@Comment("Дата начала")
	public Date getDateFrom() {
		return theDateFrom;
	}

	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}

	/** Дата начала */
	private Date theDateFrom;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateTo() {
		return theDateTo;
	}

	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}

	/** Дата окончания */
	private Date theDateTo;
	


}
