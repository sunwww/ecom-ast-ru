package ru.ecom.mis.ejb.domain.worker;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.voc.VocAttorneyType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Attorney extends BaseEntity {
	
	/** Лицо, на которое выдана доверенность */
	@Comment("Лицо, на которое выдана доверенность")
	@OneToOne
	public Patient getPerson() {return person;}
	private Patient person;
	
	/** ФИО в родительном падеже */
	private String altPersonInfo;
	
	/** Фамилия И.О. */
	@Comment("Фамилия И.О.")
	@Transient
	public String getShortPersonInfo() {
		if (getPerson()==null) {
			return null;
		}
		Patient p =  getPerson();
		return p.getLastname() +" "+p.getFirstname().charAt(0)+" "+p.getMiddlename().charAt(0);
	}
	
	/** Номер доверенности */
	private String attorneyNumber;
	
	/** Дата выдачи доверенности */
	private Date attorneyStartDate;
	
	/** Недействительна */
	private Boolean isArchive;
	
	/** Тип доверенности */
	@Comment("Тип доверенности")
	@OneToOne
	public VocAttorneyType getType() {return type;}
	/** Тип доверенности */
	private VocAttorneyType type;
	
	/** Использовать в системе по умолчанию */
	private Boolean isDefault;

}
