package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Телефоны сотрудников
 * @author azviagin
 *
 */
@Comment("Телефоны сотрудников")
@Entity
@Table(schema="SQLUser")
public class WorkerPhone extends BaseEntity{
	
	/** Телефон */
	@Comment("Телефон")
	@ManyToOne
	public Phone getPhone() {
		return thePhone;
	}

	public void setPhone(Phone aPhone) {
		thePhone = aPhone;
	}

	/** Телефон */
	private Phone thePhone;
	
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

}
