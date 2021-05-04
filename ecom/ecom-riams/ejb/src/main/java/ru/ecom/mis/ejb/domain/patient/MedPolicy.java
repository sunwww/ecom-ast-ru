package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.patient.voc.VocPolicyConfirmationType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Медицинский полис
 */
@Entity
@AIndexes({
	@AIndex(properties={"series"})
	,@AIndex(properties={"polNumber"})
	,@AIndex(properties={"patient"})		
	,@AIndex(properties={"actualDateTo"})
	,@AIndex(properties={"actualDateFrom"})
	,@AIndex(properties={"commonNumber"})
	,@AIndex(properties={"company"})
	,@AIndex(properties={"insuranceCompanyArea"})
})
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
abstract public class MedPolicy extends BaseEntity {

	/** Единый номер застрахованного */
	private String commonNumber;
	/** Отчество */
	private String middlename;
	/** Имя */
	private String firstname;
	/** Фамилия */
	private String lastname;
	/** Пациент */
	private Patient patient ;
	/** Дата действия по */
	private Date actualDateTo ;
	/** Дата действия с */
	private Date actualDateFrom ;
	/** Номер полиса */
	@Column(name = "number")
	private String polNumber;
	/** Серия полиса */
	private String series ;
	/** Страховая компания */
	private RegInsuranceCompany company ;
	/** Область нахождения СМО */
	private OmcKodTer insuranceCompanyArea;
	/** Тип подтверждения по полису */
	private VocPolicyConfirmationType confirmationType;
	/** Дата подтверждения */
	private Date confirmationDate;
	/** Дата рождения */
	private Date birthday;


    /** Страховая компания */
    @OneToOne
    public RegInsuranceCompany getCompany() { return company ; }

    /** Пациент */
    @ManyToOne
    public Patient getPatient() { return patient ; }

    /** Текст */
    @Transient
    public String getText() { return "Не переопределен метод getText() у класса "+getClass().getName() ; }
    public void setText(String aText) { }

    /** Область нахождения СМО */
	@Comment("Область нахождения СМО")
	@OneToOne
	public OmcKodTer getInsuranceCompanyArea() {
		return insuranceCompanyArea;
	}

	/** Тип подтверждения по полису */
	@Comment("Тип подтверждения по полису")
	@OneToOne
	public VocPolicyConfirmationType getConfirmationType() {
		return confirmationType;
	}
}
