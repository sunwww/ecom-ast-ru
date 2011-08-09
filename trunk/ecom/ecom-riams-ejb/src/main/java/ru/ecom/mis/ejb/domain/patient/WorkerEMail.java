package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * E-mail сотрудника
 * @author azviagin
 *
 */
@Comment("E-mail сотрудника")
@Entity
@Table(schema="SQLUser")
public class WorkerEMail extends BaseEntity{
	
	/** Сотрудник */
	@Comment("Сотрудник")
	@ManyToOne
	public Worker getWorker() {
		return theWorker;
	}

	public void setWorker(Worker aWorker) {
		theWorker = aWorker;
	}

	/** Сотрудник */
	private Worker theWorker;
	
	/** E-mail */
	@Comment("E-mail")
	@ManyToOne
	public EMail getEmail() {
		return theEmail;
	}

	public void setEmail(EMail aEMail) {
		theEmail = aEMail;
	}

	/** E-mail */
	private EMail theEmail;

}
