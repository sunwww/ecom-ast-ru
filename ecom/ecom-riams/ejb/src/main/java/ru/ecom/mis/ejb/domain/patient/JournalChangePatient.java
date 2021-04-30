package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Журнал изменений по пациенту
 * @author user
 *
 */
@Entity
@AIndexes({
	@AIndex(properties={"patient"})
    , @AIndex(properties= {"lastname","firstname","middlename", "birthday"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class JournalChangePatient extends BaseEntity {
	/** Фамилия */
	private String lastname;

	/** Адрес */
	@Comment("Адрес")
	@OneToOne
	public Address getAddress() {return address;}

	/** Адрес проживания */
	@Comment("Адрес проживания")
	@OneToOne
	public Address getRealAddress() {return realAddress;}

	/** Адрес проживания */
	private Address realAddress;
	/** Адрес */
	private Address address;
	/** Дата рождения */
	private Date birthday;
	/** Отчество */
	private String middlename;
	/** Имя */
	private String firstname;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Пациент */
	private Patient patient;
	
	/** Пользователь */
	private String changeUsername;
	/** Время изменения */
	private Time changeTime;
	/** Дата изменения */
	private Date changeDate;
}
