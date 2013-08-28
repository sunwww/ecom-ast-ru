package ru.medos.ejb.persdata.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Акт передачи копий
	 */
	@Comment("Акт передачи копий")
@Entity
@Table(schema="SQLUser")
public class CopiesTransferAct extends Act{
	@OneToMany(mappedBy="copiesTransferAct", cascade=CascadeType.ALL)
	public List<ComingDocument> getComingDocuments() {
		return theComingDocuments;
	}
	public void setComingDocuments(List<ComingDocument> aComingDocuments) {
		theComingDocuments = aComingDocuments;
	}
	/**
	 * Входящие документы
	 */
	private List<ComingDocument> theComingDocuments;
}
