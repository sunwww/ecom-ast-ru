package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(name="journalPatientFondCheck", schema="SQLUser")

@Getter
@Setter
public class JournalPatientNotFoundFondCheck extends BaseEntity{ 
	/** Фамилия */
	private String lastname;
	/** Имя */
	private String firstname;
	/** Отчество */
	private String middlename;
	/** Дата рождения */
	private Date birthday;
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}
	private Patient patient;
	/** Проверка по базе ФОМС */
	@Comment("Проверка по базе ФОМС")
	@OneToOne
	public PatientFondCheckData getCheckTime() {return checkTime;}
	private PatientFondCheckData checkTime;
	/** Найден в базе */
	private Boolean isFound;
	/** ИД пациента (текст) */
	private String removedPatientId;
	/** Пациент удален */
	private Boolean isPatientRemoved;
}
