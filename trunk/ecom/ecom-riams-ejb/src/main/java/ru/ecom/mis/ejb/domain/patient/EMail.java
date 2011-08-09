package ru.ecom.mis.ejb.domain.patient;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.voc.VocEMailType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * E-mail
 * @author azviagin
 *
 */
@Comment("E-mail")
@Entity
@Table(schema="SQLUser")
public class EMail extends BaseEntity{
	
	/** Адрес */
	@Comment("Адрес")
	public String getAddress() {
		return theAddress;
	}

	public void setAddress(String aAddress) {
		theAddress = aAddress;
	}

	/** Адрес */
	private String theAddress;
	
	/** Тип адреса */
	@Comment("Тип адреса")
	@OneToOne
	public VocEMailType getEMailType() {
		return theEMailType;
	}

	public void setEMailType(VocEMailType aEMailType) {
		theEMailType = aEMailType;
	}

	/** Тип адреса */
	private VocEMailType theEMailType;
	
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
	
	/** E-mail сотрудников */
	@Comment("E-mail сотрудников")
	@OneToMany(mappedBy="email", cascade=CascadeType.ALL)
	public List<WorkerEMail> getWorkerEMail() {
		return theWorkerEMail;
	}

	public void setWorkerEMail(List<WorkerEMail> aWorkerEMail) {
		theWorkerEMail = aWorkerEMail;
	}

	/** E-mail сотрудников */
	private List<WorkerEMail> theWorkerEMail;

}
