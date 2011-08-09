package ru.ecom.mis.ejb.domain.patient;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.voc.VocPhoneType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Телефон
 * @author azviagin
 *
 */
@Comment("Телефон")
@Entity
@Table(schema="SQLUser")
public class Phone extends BaseEntity{

	/** Номер */
	@Comment("Номер")
	public String getNumber() {
		return theNumber;
	}

	public void setNumber(String aNumber) {
		theNumber = aNumber;
	}

	/** Номер */
	private String theNumber;
	
	/** Тип */
	@Comment("Тип")
	public VocPhoneType getType() {
		return theType;
	}

	public void setType(VocPhoneType aType) {
		theType = aType;
	}

	/** Тип */
	private VocPhoneType theType;
	
	/** Телефоны персон*/
	@Comment("Телефоны персон")
	@OneToMany(mappedBy="phone", cascade=CascadeType.ALL)
	public List<PatientPhone> getPersonPhones() {
		return thePersonPhones;
	}

	public void setPersonPhones(List<PatientPhone> aPersonPhones) {
		thePersonPhones = aPersonPhones;
	}

	/** Телефоны персон */
	private List<PatientPhone> thePersonPhones;
	
	/** Телефоны сотрудников */
	@Comment("Телефоны сотрудников")
	@OneToMany(mappedBy="phone", cascade=CascadeType.ALL)
	public List<WorkerPhone> getWorkerPhones() {
		return theWorkerPhones;
	}

	public void setWorkerPhones(List<WorkerPhone> aWorkerPhones) {
		theWorkerPhones = aWorkerPhones;
	}

	/** Телефоны сотрудников */
	private List<WorkerPhone> theWorkerPhones;
	

	
	/** Комментарии */
	@Comment("Комментарии")
	public String getComments() {
		return theComments;
	}

	public void setComments(String aComments) {
		theComments = aComments;
	}

	/** Комментарии */
	private String theComments;

	

}
