package ru.ecom.poly.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

// Медицинская карта

@Entity
@AIndexes({
	@AIndex(properties= ("number"))
    ,@AIndex(properties= ("person"))
})
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
@Setter
@Getter
public class Medcard extends BaseEntity {

    /**
     * Регистратор *
     */
    private String registrator;

    /** return Пациент */
    @OneToOne
    public Patient getPerson() {return person;}

    /** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	/** ЛПУ */
	private MisLpu lpu;
    /** Пациент */
    private Patient person;
    /** Номер карты */
    private String number;
    /** Дата заведения карты */
    private Date dateRegistration;

}
